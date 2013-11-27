package id.co.mii.cmdbreport;

import id.co.mii.cmdbreport.bo.ApplicationInfo;
import id.co.mii.cmdbreport.bo.BusinessService;
import id.co.mii.cmdbreport.bo.CPU;
import id.co.mii.cmdbreport.bo.DatabaseInfo;
import id.co.mii.cmdbreport.bo.Memory;
import id.co.mii.cmdbreport.bo.NetworkInfo;
import id.co.mii.cmdbreport.bo.OperatingSystem;
import id.co.mii.cmdbreport.bo.Person;
import id.co.mii.cmdbreport.bo.ServerInfo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.bmc.arsys.api.ARException;
import com.bmc.arsys.api.ARServerUser;
import com.bmc.arsys.api.QualifierInfo;
import com.bmc.cmdb.api.CMDBAttribute;
import com.bmc.cmdb.api.CMDBAttributeValue;
import com.bmc.cmdb.api.CMDBClassNameKey;
import com.bmc.cmdb.api.CMDBGraphWalkOutput;
import com.bmc.cmdb.api.CMDBGraphWalkQuery;
import com.bmc.cmdb.api.CMDBGraphWalkRelation;
import com.bmc.cmdb.api.CMDBGraphWalkRelationList;
import com.bmc.cmdb.api.CMDBGraphWalkSelectorAndFilter;
import com.bmc.cmdb.api.CMDBGraphWalkSelectorAndFilterList;
import com.bmc.cmdb.api.CMDBGraphWalkState;
import com.bmc.cmdb.api.CMDBInstance;
import com.bmc.cmdb.api.CMDBInstanceList;
import com.bmc.cmdb.api.CMDBUtil;

public class CMDBReport {

	ARServerUser context;
	Logger log = Logger.getLogger(Main.class.getName());
	CMDBInstanceList instanceList;
	
	private ApplicationInfo appInfo;
	private ServerInfo serverInfo;
	private String startNodeId;
	private BusinessService businessService;
	private List<ApplicationInfo> applicationInfos;
	private List<ServerInfo> serverList;
	private List<DatabaseInfo> databaseInfos;
	private List<NetworkInfo> networkInfos;
	private CMDBInstanceList ipList;

	public CMDBReport() {
		databaseInfos = new ArrayList<DatabaseInfo>();
		serverList = new ArrayList<ServerInfo>();
		applicationInfos = new ArrayList<ApplicationInfo>();
		networkInfos = new ArrayList<NetworkInfo>();
	}
		
	
	public ARServerUser getContext() {
		return context;
	}


	public List<ApplicationInfo> getApplicationInfos() {
		return applicationInfos;
	}

	public void setApplicationInfos(List<ApplicationInfo> applicationInfos) {
		this.applicationInfos = applicationInfos;
	}

	public BusinessService getBusinessService() {
		return businessService;
	}

	public void setBusinessService(BusinessService businessService) {
		this.businessService = businessService;
	}

	public List<ServerInfo> getServerList() {
		return serverList;
	}

	public void setServerList(List<ServerInfo> serverList) {
		this.serverList = serverList;
	}

	public List<DatabaseInfo> getDatabaseList() {
		return databaseInfos;
	}

	public void setDatabaseList(List<DatabaseInfo> databaseList) {
		this.databaseInfos = databaseList;
	}
	
	public List<DatabaseInfo> getDatabaseInfos() {
		return databaseInfos;
	}

	public void setDatabaseInfos(List<DatabaseInfo> databaseInfos) {
		this.databaseInfos = databaseInfos;
	}

	public List<NetworkInfo> getNetworkInfos() {
		return networkInfos;
	}

	public void setNetworkInfos(List<NetworkInfo> networkInfos) {
		this.networkInfos = networkInfos;
	}

	/***
	 * connect to AR System server
	 */
	public void connect(){
		context = new ARServerUser();
		context.setUser(SystemProperties.getString("ARS.username"));		
		context.setPassword(SystemProperties.getString("ARS.password"));
		context.setServer(SystemProperties.getString("ARS.server"));
		context.setPort(SystemProperties.getInt("ARS.port",0));
		try {
			log.info("Trying to login...");
			context.login();
		} catch (ARException e) {
			e.printStackTrace();
		}		
	}
	
	public void disconnect(){
		this.context.logout();
	}

	private CMDBInstance searchItemById(String instanceId, CMDBClassNameKey nameKey){
		CMDBInstance theInstance = null;
		try {
			theInstance = CMDBInstance.findByKey(context, instanceId, nameKey, null);
		} catch (ARException e) {
			e.printStackTrace();
		}
		return theInstance;
	}

	/***
	 * Walk the graph starting from given node ID (InstanceID) 
	 * and put the result into instanceList variable
	 * @param startNodeId instanceId of starting CI
	 * @param depth maximum depth for graph search
	 * @throws ARException 
	 */
	public CMDBInstanceList walkTheGraph(String startNodeId, int depth) throws ARException {
		List<String> attrList = new ArrayList<String>();
		attrList.add("Name");
		this.startNodeId = startNodeId;

		CMDBGraphWalkQuery query = new CMDBGraphWalkQuery("BMC.ASSET");

		CMDBGraphWalkRelation relation = new CMDBGraphWalkRelation();
		CMDBClassNameKey relClassNameId = new CMDBClassNameKey("BMC_Dependency","BMC.CORE");

		relation.setDirection(CMDBGraphWalkRelation.CMDB_RELATIONSHIP_DIRECTION_OUT);
		relation.setRelationshipClassNameId(relClassNameId);
		relation.setQualification(new QualifierInfo());
		relation.setGetAttributeList(attrList);

		CMDBGraphWalkRelationList relList = new CMDBGraphWalkRelationList();

		relList.setAttributeFlag
		(CMDBGraphWalkRelationList.CMDB_GRAPH_WALK_ATTRIBUTE_FLAG_DEFAULT_ALL);
		relList.add(relation);

		CMDBClassNameKey classNameId = new CMDBClassNameKey("BMC_BaseElement","BMC.CORE");

		CMDBGraphWalkSelectorAndFilter sf = new CMDBGraphWalkSelectorAndFilter(classNameId);
		sf.setAttributes(attrList);      

		CMDBGraphWalkSelectorAndFilterList sfList = new CMDBGraphWalkSelectorAndFilterList();
		sfList.add(sf);

		query.setDatasetMask(0);
		query.setMaxRetrieve(0);
		query.setNumLevels(depth);
		query.setObjSelectorAndFilterList(sfList);

		query.setQueryMode(CMDBGraphWalkQuery.CMDB_GRAPH_WALK_WALK_COMPLETE_GRAPH);
		query.setWalkRelationList(relList);
		CMDBGraphWalkState state = null;
		CMDBGraphWalkOutput output = null;

		state = CMDBUtil.CMDBGraphWalkBegin(context,classNameId,startNodeId,query);

		CMDBInstanceList list = null;

		while(state.hasNext()){
			output = CMDBUtil.CMDBGraphWalkNext(context, state);
			list = output.getGraphWalkResult().getObjectList();
			state = output.getGraphWalkState();
		}

		state = CMDBUtil.CMDBGraphWalkEnd(context, state);
		return list;
	}

	/***
	 * Walk graph using specified classId and relationship
	 * @param startNodeId
	 * @param classToFind
	 * @param relToFind
	 * @param graphWalkDirection 
	 * @param depth 
	 * @return
	 * @throws ARException
	 */
	public CMDBInstanceList walkByComponent(String startNodeId, 
			CMDBClassNameKey classToFind, CMDBClassNameKey relToFind, int graphWalkDirection, int depth) throws ARException{
		
		List<String> attrList = new ArrayList<String>();
		attrList.add("Name");

		CMDBGraphWalkRelation relation = new CMDBGraphWalkRelation();
		relation.setRelationshipClassNameId(relToFind);
		relation.setDirection(CMDBGraphWalkRelation.CMDB_RELATIONSHIP_DIRECTION_BOTH);
		relation.setQualification(new QualifierInfo());
		relation.setGetAttributeList(attrList);

		CMDBGraphWalkRelationList relList = new CMDBGraphWalkRelationList();

		relList.setAttributeFlag(CMDBGraphWalkRelationList.CMDB_GRAPH_WALK_ATTRIBUTE_FLAG_DEFAULT_ALL);
		relList.add(relation);

		CMDBGraphWalkSelectorAndFilter sf = new CMDBGraphWalkSelectorAndFilter(classToFind);
		sf.setAttributes(attrList);      

		CMDBGraphWalkSelectorAndFilterList sfList = new CMDBGraphWalkSelectorAndFilterList();
		sfList.add(sf);

		CMDBGraphWalkQuery query = new CMDBGraphWalkQuery("BMC.ASSET");
		query.setDatasetMask(0);
		query.setMaxRetrieve(0);
		query.setNumLevels(depth);
		query.setObjSelectorAndFilterList(sfList);

		query.setQueryMode(CMDBGraphWalkQuery.CMDB_GRAPH_WALK_WALK_COMPLETE_GRAPH);
		query.setWalkRelationList(relList);
		CMDBGraphWalkState state = null;
		CMDBGraphWalkOutput output = null;

		state = CMDBUtil.CMDBGraphWalkBegin(context,classToFind,startNodeId,query);

		CMDBInstanceList list = null;
		
		while(state.hasNext()){

			output = CMDBUtil.CMDBGraphWalkNext(context, state);
			list = output.getGraphWalkResult().getObjectList();
			state = output.getGraphWalkState();
		}

		state = CMDBUtil.CMDBGraphWalkEnd(context, state);
		return list;
	}

	/***
	 * Classify configuration item(s) inside the list
	 * @param theList
	 * @throws ARException
	 */
	@SuppressWarnings("unchecked")
	public void classifyInstance(CMDBInstanceList theList) throws ARException{

		String tempHosting = ""; //temporary container for serverhosting
		Map<CMDBAttribute,CMDBAttributeValue> map = null;
		int primaryCapability = 0;

		for (CMDBInstance cmdbInstance : theList) {
			map = cmdbInstance.getAttributeValues();
			
			CMDBInstance bs = this.searchItemById(this.startNodeId, Servant.BMC_BUSINESSSERVICE);
			this.businessService = new BusinessService(startNodeId);
			this.businessService.setName(bs.getAttributeValueByName("Name").getAttributeValue().toString());

			switch (cmdbInstance.getClassKey().getClassName()) {

			case "BMC_Application":
				appInfo = new ApplicationInfo();
				appInfo.setApplicationName(map.get("Name").getAttributeValue().toString());
				CMDBInstance appInst = this.searchItemById(cmdbInstance.getId(), Servant.BMC_APPLICATION);		
				appInfo.setBusinessService(this.businessService);
				
				appInfo.setDescription(appInst.getAttributeValueByName("ShortDescription").getAttributeValue().toString());
				appInfo.setPatchNumber(appInst.getAttributeValueByName("PatchNumber").getAttributeValue().toString());
				appInfo.setVersion(appInst.getAttributeValueByName("VersionNumber").getAttributeValue().toString());
				appInfo.setApplicationType(appInst.getAttributeValueByName("Type").getAttributeValue().toString());
				
				if (map.get("Type") == null) {
					appInfo.setApplicationType(Servant.EMPTY_VALUE);				
				} else if (map.get("ServicePack") == null)
					appInfo.setServicePack(Servant.EMPTY_VALUE);

				applicationInfos.add(appInfo);
				//owner TODO: masih null
				String ownerEmail = appInst.getAttributeValueByName("OwnerContact").getAttributeValue().toString();
				String ownerName = ownerEmail;
				String ownerDepartment = appInst.getAttributeValueByName("OwnerName").getAttributeValue().toString();
				appInfo.setApplicationOwner(new Person(ownerName,ownerEmail,ownerDepartment));
				
				CMDBInstanceList appIntegrations = this.walkByComponent(appInst.getId(), Servant.BMC_APPLICATION, 
						Servant.BMC_DEPENDENCY, CMDBGraphWalkRelation.CMDB_RELATIONSHIP_DIRECTION_OUT, 1);
				if (!appIntegrations.isEmpty()){
					String appIntgStr ="";
					for (CMDBInstance appInternal : appIntegrations) {
						appIntgStr += appInternal.getAttributeValueByName("Name").getAttributeValue().toString()+", ";
					}
					appInfo.setApplicationIntegration(appIntgStr);
				}
				
				CMDBInstanceList serverHostings = this.walkByComponent(appInst.getId(), Servant.BMC_COMPUTERSYSTEM, 
						Servant.BMC_DEPENDENCY, CMDBGraphWalkRelation.CMDB_RELATIONSHIP_DIRECTION_OUT, 1);
				if (!serverHostings.isEmpty()){
					String serverHostingStr = "";
					for (CMDBInstance serverHosting : serverHostings) {
						CMDBInstance svr = this.searchItemById(serverHosting.getId(), Servant.BMC_COMPUTERSYSTEM);
						serverHostingStr += "#"+svr.getAttributeValueByName("Name").getAttributeValue().toString()+" ";
						String isVirtual = svr.getAttributeValueByName("isVirtual").getAttributeValue().toString();
						if (svr.getAttributeValueByName("isVirtual").getAttributeValue().toString().equalsIgnoreCase("0"))
							appInfo.addPhysicalServer();
					}
					appInfo.setServerHosting(serverHostingStr);
				}
				break;

			case "BMC_ComputerSystem":
				serverInfo = new ServerInfo();	
				
//				System.out.println(cmdbInstance.getId()+" "+map.get("Name").getAttributeValue().toString());
				CMDBInstance csInstance = this.searchItemById(cmdbInstance.getId(), Servant.BMC_COMPUTERSYSTEM);
				primaryCapability = csInstance.getAttributeValueByName("PrimaryCapability")
						.getAttributeValue().getIntValue();
				//primary capability = Server(14)
				if ( primaryCapability == Servant.PRIMARYCAB_SERVER) {
					
					String ramStr = csInstance.getAttributeValueByName("TotalPhysicalMemory").getAttributeValue().toString();
//					System.out.println("RAM : "+ramStr);
					Memory ram = new Memory(Long.parseLong(ramStr));
					serverInfo.getMemories().add(ram);

					serverInfo.setHostname(map.get("Name").getAttributeValue().toString());
//					tempHosting += "#"+serverInfo.getHostname()+" ";
//					appInfo.setServerHosting(tempHosting);

					//find serverAdmin
					CMDBInstanceList persons = walkByComponent(csInstance.getId(), Servant.BMC_PERSON, Servant.BMC_DEPENDENCY, 
							CMDBGraphWalkRelation.CMDB_RELATIONSHIP_DIRECTION_BOTH, 1);
					if (!persons.isEmpty()){
						for (CMDBInstance person: persons) {
							CMDBInstance i = this.searchItemById(person.getId(), Servant.BMC_PERSON);
							serverInfo.setServerAdmin(
									new Person(i.getAttributeValueByName("Name").getAttributeValue().toString(), 
											i.getAttributeValueByName("Email").getAttributeValue().toString(),
											i.getAttributeValueByName("PersonDepartment").getAttributeValue().toString()
											)
									);
						}
						
					}//primary capability = server
					
					String strIsVm = "";
					try {
						strIsVm = csInstance.getAttributeValueByName("isVirtual").getAttributeValue().toString();
					} catch (Exception en){
						System.err.println("exception caught");						
					} finally {
						if (strIsVm.equalsIgnoreCase("1"))
							serverInfo.setIsVirtualServer(Servant.YES);
						else {
							serverInfo.setIsVirtualServer(Servant.NO);
						}
					}
					
					//attributes
					serverInfo.setModel(csInstance.getAttributeValueByName("Model").getAttributeValue().toString());
					serverInfo.setDescription(csInstance.getAttributeValueByName("Description").getAttributeValue().toString());

					//find processor (CPU)
					CMDBInstanceList list = this.walkByComponent(cmdbInstance.getId(), 
							Servant.BMC_PROCESSOR, Servant.BMC_HOSTEDSYSTEMCOMPONENTS, 
							CMDBGraphWalkRelation.CMDB_RELATIONSHIP_DIRECTION_OUT, 1);

					for (CMDBInstance component : list) {
						//					System.out.println(component.getId()+" "+component.getAttributeValueByName("Name").getAttributeValue().toString());
						CMDBInstance internal = this.searchItemById(component.getId(),Servant.BMC_PROCESSOR);
						int clockSpeed = Integer.valueOf(internal.getAttributeValueByName("MaxClockSpeed").getAttributeValue().toString()); 
						serverInfo.getCpuAsList().add(new CPU(clockSpeed));					
					}

					//OS
					OperatingSystem os;

					CMDBInstanceList osList = this.walkByComponent(cmdbInstance.getId(), 
							Servant.BMC_OPERATINGSYSTEM, Servant.BMC_HOSTEDSYSTEMCOMPONENTS, 
							CMDBGraphWalkRelation.CMDB_RELATIONSHIP_DIRECTION_OUT, 1);
					if (!osList.isEmpty()) {
						for (CMDBInstance osInstance : osList) {
							CMDBInstance osInstance2 = this.searchItemById(osInstance.getId(), Servant.BMC_OPERATINGSYSTEM);
							String osName = osInstance2.getAttributeValueByName("Name").getAttributeValue().toString();
							String osVersion = osInstance2.getAttributeValueByName("VersionNumber").getAttributeValue().toString();
							String osPatch = osInstance2.getAttributeValueByName("PatchNumber").getAttributeValue().toString();
							os = new OperatingSystem(osName, osVersion, osPatch);
							serverInfo.setOs(os);
						}				
					}
					//IP Address
					ipList = this.walkByComponent(cmdbInstance.getId(), 
							Servant.BMC_IPENDPOINT, Servant.BMC_HOSTEDACCESSPOINT, 
							CMDBGraphWalkRelation.CMDB_RELATIONSHIP_DIRECTION_OUT, 1);
					for (CMDBInstance ipInstance : ipList) {
						String ipAddress = ipInstance.getAttributeValueByName("Name").getAttributeValue().toString();
//						System.out.println("IP Address : "+ipAddress);
						serverInfo.getIpAddressAsList().add(ipAddress);
					}

					ipList.clear();//clear the list before used to contain IP address for switch
					
					//Rack
					CMDBInstanceList rackList = this.walkByComponent(cmdbInstance.getId(), 
							Servant.BMC_RACK, Servant.BMC_COMPONENT, 
							CMDBGraphWalkRelation.CMDB_RELATIONSHIP_DIRECTION_OUT, 1);
					String rack = null;
					if (!rackList.isEmpty()){					
						for (CMDBInstance rackInstance : rackList) {
							rack = rackInstance.getAttributeValueByName("Name").getAttributeValue().toString();
						}
					} else {
						rack = Servant.EMPTY_VALUE;
					}

					serverInfo.setRackName(rack);
					//add server to the list
					this.serverList.add(serverInfo);
				}
				//ENDOF:primary capability = Server(14)
				
				//find switch
				CMDBInstanceList switchList = this.walkByComponent(csInstance.getId(), Servant.BMC_COMPUTERSYSTEM, 
						Servant.BMC_DEPENDENCY, CMDBGraphWalkRelation.CMDB_RELATIONSHIP_DIRECTION_IN, 1);
				for (CMDBInstance switchInst : switchList) {
					CMDBInstance ci = this.searchItemById(switchInst.getId(), Servant.BMC_COMPUTERSYSTEM);

					if(ci.getAttributeValueByName("Category").getAttributeValue().toString().equalsIgnoreCase("network")){
						NetworkInfo networkInfo = new NetworkInfo();
						networkInfo.setHostname(ci.getAttributeValueByName("Name").getAttributeValue().toString());
						networkInfo.setDescription(ci.getAttributeValueByName("ShortDescription").getAttributeValue().toString());
						networkInfo.setModel(ci.getAttributeValueByName("Model").getAttributeValue().toString());
						networkInfo.setManufactureName(ci.getAttributeValueByName("ManufacturerName").getAttributeValue().toString());
						networkInfo.setSerialNumber(ci.getAttributeValueByName("SerialNumber").getAttributeValue().toString());
						networkInfo.setType(ci.getAttributeValueByName("Type").getAttributeValue().toString());
						
						ipList = this.walkByComponent(ci.getId(), Servant.BMC_IPENDPOINT, Servant.BMC_HOSTEDACCESSPOINT, 
								CMDBGraphWalkRelation.CMDB_RELATIONSHIP_DIRECTION_OUT, 1);
						for (CMDBInstance ipInst : ipList) {
							networkInfo.getIpAddressAsList().add(ipInst.getAttributeValueByName("Name").getAttributeValue().toString());
						}
						networkInfos.add(networkInfo);					
					}
					ipList.clear();
				}
				
				
				break;
				
			case "BMC_SoftwareServer":
//				System.out.println(map.get("Name").getAttributeValue().toString()); 
				String dbServerId = cmdbInstance.getId();
				CMDBInstance swServerInst = this.searchItemById(dbServerId, Servant.BMC_SOFTWARESERVER);
				DatabaseInfo dbInfo = null;

				if (swServerInst.getAttributeValueByName("SoftwareServerType").getAttributeValue().toString().equalsIgnoreCase("2")){
					CMDBInstanceList dbInstLlist = this.walkByComponent(dbServerId, 
							Servant.BMC_DATABASE, Servant.BMC_DEPENDENCY, 
							CMDBGraphWalkRelation.CMDB_RELATIONSHIP_DIRECTION_OUT, 1);
//					System.out.println("size : "+dbInstLlist.size());
					for (CMDBInstance db : dbInstLlist) {	
						dbInfo = new DatabaseInfo();
						dbInfo.setDatabaseName(db.getAttributeValueByName("Name").getAttributeValue().toString());
						dbInfo.setDatabaseSoftwareName(swServerInst.getAttributeValueByName("Name").getAttributeValue().toString());
						dbInfo.setDatabaseSoftwareVersion(swServerInst.getAttributeValueByName("VersionNumber").getAttributeValue().toString());
												
						//search serverhosting
						CMDBInstanceList dbSvrList = this.walkByComponent(db.getId(), Servant.BMC_BASEELEMENT, 
								Servant.BMC_DEPENDENCY, CMDBGraphWalkRelation.CMDB_RELATIONSHIP_DIRECTION_IN, 3);
//						System.out.println("dbId = "+db.getAttributeValueByName("Name").getAttributeValue().toString()
//								+" size = "+dbSvrList.size());
						for (CMDBInstance in : dbSvrList) {
							if (in.getClassKey().getClassName().equals(Servant.BMC_COMPUTERSYSTEM.getClassName())) {
								CMDBInstance x = this.searchItemById(in.getId(), Servant.BMC_COMPUTERSYSTEM);
								if (x.getAttributeValueByName("PrimaryCapability").getAttributeValue().getIntValue() == Servant.PRIMARYCAB_SERVER) {
//									System.out.println(in.getAttributeValueByName("Name").getAttributeValue().toString());
									dbInfo.getServerHostingAsList().add(x.getAttributeValueByName("Name").getAttributeValue().toString());
								}
							}
						}
						this.databaseInfos.add(dbInfo);
						
						//TODO: server hosting belum dicari
					}					
				}
		
			default:
				break;
			}
		}

		//construct the report
//		System.out.println("Server hosting "+appInfo.getServerHosting());
	}
//	
//	public void printReport(){
//		System.out.println("Custom Report for : \n"+this.businessService.getName());
//		System.out.println("============================================");
//		System.out.println("Application Name : "+this.appInfo.getApplicationName());
//		for (ServerInfo server : this.serverList) {
//			System.out.println(server.getHostname());
//		}		
//	}
	
	public void generateReport(){
		
	}
}
