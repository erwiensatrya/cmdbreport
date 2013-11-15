package id.co.mii.cmdbreport;

import com.bmc.cmdb.api.CMDBClassNameKey;

public class Servant {

	public static final String REPLACE_PATTERN = "[\\s\\[\\]]";
	public static final String EMPTY_VALUE = "-";
	public static final String YES = "Yes";
	public static final String NO = "No";
	
	public static final int PRIMARYCAB_SERVER = 14;
	public static final int PRIMARYCAB_SWITCH = 5;
	
	
	public static final CMDBClassNameKey BMC_BASEELEMENT = 
			new CMDBClassNameKey("BMC_BaseElement","BMC.CORE");
	public static final CMDBClassNameKey BMC_PROCESSOR = 
			new CMDBClassNameKey("BMC_Processor","BMC.CORE");
	public static final CMDBClassNameKey BMC_IPENDPOINT = 
			new CMDBClassNameKey("BMC_IPEndpoint", "BMC.CORE");
	public static final CMDBClassNameKey BMC_HOSTEDACCESSPOINT = 
			new CMDBClassNameKey("BMC_HostedAccessPoint","BMC.CORE");
	public static final CMDBClassNameKey BMC_DEPENDENCY= 
			new CMDBClassNameKey("BMC_Dependency","BMC.CORE");
	public static final CMDBClassNameKey BMC_OPERATINGSYSTEM = 
			new CMDBClassNameKey("BMC_OperatingSystem","BMC.CORE");
	public static final CMDBClassNameKey BMC_HOSTEDSYSTEMCOMPONENTS = 
			new CMDBClassNameKey("BMC_HostedSystemComponents","BMC.CORE");
	public static final CMDBClassNameKey BMC_SOFTWARESERVER = 
			new CMDBClassNameKey("BMC_SoftwareServer", "BMC.CORE");
	public static final CMDBClassNameKey BMC_DATABASE = 
			new CMDBClassNameKey("BMC_DataBase","BMC.CORE");
	public static final CMDBClassNameKey BMC_RACK = 
			new CMDBClassNameKey("BMC_Rack","BMC.CORE");
	public static final CMDBClassNameKey BMC_COMPUTERSYSTEM = 
			new CMDBClassNameKey("BMC_ComputerSystem", "BMC.CORE");
	public static final CMDBClassNameKey BMC_COMPONENT = 
			new CMDBClassNameKey("BMC_Component", "BMC.CORE");
	public static final CMDBClassNameKey BMC_APPLICATION = 
			new CMDBClassNameKey("BMC_Application", "BMC.CORE");
	public static final CMDBClassNameKey BMC_BUSINESSSERVICE = 
			new CMDBClassNameKey("BMC_BusinessService", "BMC.CORE");
	public static final CMDBClassNameKey BMC_PERSON = 
			new CMDBClassNameKey("BMC_Person", "BMC.CORE");
	
	
}
