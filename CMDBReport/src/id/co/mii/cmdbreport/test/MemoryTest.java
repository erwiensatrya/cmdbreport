package id.co.mii.cmdbreport.test;

import static org.junit.Assert.*;

import java.io.File;

import id.co.mii.cmdbreport.bo.ApplicationInfo;
import id.co.mii.cmdbreport.bo.Memory;
import id.co.mii.cmdbreport.bo.OperatingSystem;
import id.co.mii.cmdbreport.bo.ServerInfo;

import org.junit.Before;
import org.junit.Test;

public class MemoryTest {

	Memory m;
	
	@Before
	public void setUp() throws Exception {
		m = new Memory(16384);
	}
	
	@Test
	public void testGetCapacity() {
		assertEquals(16384, m.getCapacity());
	}

	@Test
	public void testToString() {
		assertEquals("16 Gb", m.toString());
	}
	
	@Test
	public void testOsToString(){
		OperatingSystem os = new OperatingSystem("Windows 7", "6201", "SP1");
		assertEquals("Windows 7", os.toString());
	}
	
	@Test
	public void testTotalMemory(){
		ServerInfo server = new ServerInfo();
		server.getMemories().add(new Memory(8192));
		server.getMemories().add(new Memory(2048));
		
		assertEquals("10240", server.getTotalMemory());
	}
	
	@Test
	public void testPhysicalServer(){
		ApplicationInfo appInfo = new ApplicationInfo();
		appInfo.addPhysicalServer();
		assertEquals(1, appInfo.getTotalPhysicalServer());
	}
	
	@Test
	public void testFilePath(){
		File file = new File("E:\\Temp\\report.pdf");
		assertEquals("E:", file.getAbsolutePath());
	}

}
