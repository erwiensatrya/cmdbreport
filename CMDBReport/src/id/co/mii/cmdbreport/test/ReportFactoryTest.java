package id.co.mii.cmdbreport.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import id.co.mii.cmdbreport.CMDBReport;
import id.co.mii.cmdbreport.SystemProperties;
import id.co.mii.cmdbreport.bo.ApplicationInfo;
import id.co.mii.cmdbreport.jasper.AppInfoReport;

import org.junit.Before;
import org.junit.Test;

import com.bmc.arsys.api.ARServerUser;
import com.bmc.cmdb.api.CMDBClassNameKey;
import com.bmc.cmdb.api.CMDBInstance;

public class ReportFactoryTest {

	CMDBReport factory;
	AppInfoReport appInfoReport;
	
	@Before
	public void setUp() throws Exception {
		SystemProperties.loadProperties("properties/cmdbreport.properties");
		factory = new CMDBReport();
		appInfoReport = new AppInfoReport();
	}

	@Test
	public void testSearchComponent(){
		fail("dunno");
//		factory.connect();
//		CMDBClassNameKey processor = new CMDBClassNameKey("BMC_Processor", "BMC.CORE");
//		CMDBInstance result = factory.searchItemById("OI-0BA932916C274459B87348CC2EFB9F59", processor);
//		assertNotNull(result);
	}
	
	@Test
	public void testWalkByComponent() {
//		factory.connect();
//		CMDBClassNameKey classToFind = new CMDBClassNameKey("BMC_CONCRETECOLLECTION", "BMC.CORE");
//		CMDBClassNameKey relToFind = new CMDBClassNameKey("BMC_BaseRelationship", "BMC.CORE");
//		factory.walkByComponent(, classToFind, relToFind)
		
		String str = "hei";
		str += " hallo";
		
		System.out.println(str);
		assertEquals("hei hallo", str);
	}
	
	@Test
	public void testHorizontalFlow(){
		ApplicationInfo appInfo = new ApplicationInfo();
		appInfo.setApplicationName("Funbike");
		appInfo.setApplicationType("Other");
		appInfo.setVersion("1.0.1");
		appInfo.setServicePack("SP 0001");
		
		List<ApplicationInfo> applicationInfos = new ArrayList<ApplicationInfo>();
		applicationInfos.add(appInfo);
		appInfoReport.setDataSource(applicationInfos);
		appInfoReport.build();
		appInfoReport.printToPdf();
		assertNotNull(appInfoReport.getDataSource());
	}

}
