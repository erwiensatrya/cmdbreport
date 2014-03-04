package id.co.mii.cmdbreport;

import id.co.mii.cmdbreport.bo.ApplicationInfo;
import id.co.mii.cmdbreport.bo.BusinessService;
import id.co.mii.cmdbreport.bo.DatabaseInfo;
import id.co.mii.cmdbreport.bo.NetworkInfo;
import id.co.mii.cmdbreport.bo.OperatingSystem;
import id.co.mii.cmdbreport.bo.Person;
import id.co.mii.cmdbreport.bo.ServerInfo;
import id.co.mii.cmdbreport.jasper.AppInfoReport;
import id.co.mii.cmdbreport.jasper.DatabaseInfoReport;
import id.co.mii.cmdbreport.jasper.NetworkInfoReport;
import id.co.mii.cmdbreport.jasper.ReportBuilder;
import id.co.mii.cmdbreport.jasper.ServerInfoReport;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import net.sf.dynamicreports.examples.Templates;
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.builder.DynamicReports;
import net.sf.dynamicreports.report.builder.FieldBuilder;
import net.sf.dynamicreports.report.builder.column.Columns;
import net.sf.dynamicreports.report.builder.component.Components;
import net.sf.dynamicreports.report.builder.component.HorizontalListBuilder;
import net.sf.dynamicreports.report.builder.component.HorizontalListCellBuilder;
import net.sf.dynamicreports.report.builder.datatype.DataTypes;
import net.sf.dynamicreports.report.constant.HorizontalCellComponentAlignment;
import net.sf.dynamicreports.report.constant.PageOrientation;
import net.sf.dynamicreports.report.constant.PageType;
import net.sf.dynamicreports.report.exception.DRException;
import net.sf.jasperreports.components.list.HorizontalFillList;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class SimpleReport {

	static JasperReportBuilder report;
	/**
	 * @param args
	 * @throws DRException 
	 */
	public static void main(String[] args) throws Exception {
		SimpleReport sr = new SimpleReport();
		SystemProperties.loadProperties("properties/cmdbreport.properties");
		sr.createAppInfoReport();
		sr.createDbInfoReport();
//		sr.createSvrInfoReport();
//		sr.createNetworkInfoReport();
	}

	private void createAppInfoReport() {
		AppInfoReport appInfoReport = new AppInfoReport();
		
		ApplicationInfo appInfo = new ApplicationInfo();
		BusinessService service = new BusinessService();
		service.setName("Middleware");
		
		appInfo.setBusinessService(service);
		appInfo.setApplicationName("Custom Reports");
		appInfo.setApplicationType("Other");
		appInfo.setVersion("1.0.1");
		appInfo.setServicePack("SP 0001");
		appInfo.setServerHosting("Very easy to use, which not only saves a lot of your time and money but also " +
				"increases reporting productivity and decreases training time. Only a very small amount of code is " +
				"needed to create a report and even more complex reports will be very clear, easy to maintain and understandable.");
		appInfo.setTotalPhysicalServer(10);
		appInfo.setApplicationIntegration("JSP, J2EE, Jerapah");
		List<ApplicationInfo> applicationInfos = new ArrayList<ApplicationInfo>();
		appInfo.setApplicationOwner(new Person("erwin", "erwin@mii.co.id", "BSM"));
		applicationInfos.add(appInfo);
		
		appInfoReport.setDataSource(applicationInfos);
		appInfoReport.build();
		appInfoReport.show();
//		appInfoReport.printToPdf();
	}
	
	private void createDbInfoReport(){
		DatabaseInfoReport dbInfoReport = new DatabaseInfoReport();
		DatabaseInfo dbInfo = new DatabaseInfo();
		dbInfo.setDatabaseName("XLAPPC");
		dbInfo.setDatabaseSoftwareName("Oracle 11g");
		dbInfo.setDatabaseSoftwareVersion("11g");
		dbInfo.getServerHostingAsList().add("jktbtrdb1");
		
		DatabaseInfo db2 = new DatabaseInfo();
		db2.setDatabaseName("ABCDE");
		
		List<DatabaseInfo> dbInfos = new ArrayList<DatabaseInfo>();
		dbInfos.add(dbInfo);
		dbInfos.add(db2);
		
		dbInfoReport.setDataSource(dbInfos);
		dbInfoReport.build();
		dbInfoReport.show();
	}
	
	private void createSvrInfoReport(){
		ServerInfoReport report = new ServerInfoReport();
		ServerInfo serverInfo = new ServerInfo();
		serverInfo.setHostname("appbrddev01");
		serverInfo.setOs(new OperatingSystem("Windows 7","7201","-"));
		serverInfo.setDescription("This is testing server");
		serverInfo.setIsVirtualServer(Servant.YES);
		serverInfo.setRackName("Rack Piring");
		serverInfo.setModel("Model jadul");
		serverInfo.setServerAdmin(new Person("erwin","erwien@gundam.com","R&D"));
		
		List<String> ipList = new ArrayList<String>();
		ipList.add("10.1.70.135");
		ipList.add("10.2.76.151");
		serverInfo.setIpAddress(ipList);
		
		ServerInfo devServer = new ServerInfo();
		devServer.setHostname("Dev server");
		
		List<ServerInfo> serverInfos = new ArrayList<ServerInfo>();
		serverInfos.add(serverInfo);
		serverInfos.add(devServer);
		
		report.setDataSource(serverInfos);
		report.build();
		report.show();
		
	}
	
	private void createNetworkInfoReport(){
		ReportBuilder nir = new NetworkInfoReport();
		
		NetworkInfo info = new NetworkInfo();
		info.setHostname("switch core 1");
		info.setDescription("suit kor");
		info.getIpAddressAsList().add("10.1.x.x");
		info.getIpAddressAsList().add("111.20.0.xx");
		info.setModel("Cisco switch");
		info.setSerialNumber("GAT1101X");
		info.setManufactureName("Cisco System");
		
		List<NetworkInfo> networkInfos = new ArrayList<NetworkInfo>();
		networkInfos.add(info);
		nir.setDataSource(networkInfos);
		nir.build();
		nir.show();
	}
	
}
