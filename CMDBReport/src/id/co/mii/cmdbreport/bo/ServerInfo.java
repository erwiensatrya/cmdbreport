package id.co.mii.cmdbreport.bo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ServerInfo implements Serializable{

	private String hostname;
	private String fqdn;
    private String description;
    private OperatingSystem os;
    private List<String> ipAddress;
    private List<String> _switch;
    private Person serverAdmin;
    private String serverOwner;
    private String rackName;
    private String dataCenterLocation;
    private String useLoadBalance;
    private String isVirtualServer;
    private String serialNumber;
    private String model;
    private List<Memory> memories;
    private String totalMemory;
    private List<CPU> cpu;
//    private List<Media> media;
    private String serverType;
    private String IPAddressCache;
    private List<String> storage;
//    private List<SanSwitch> sanSwitch;
    private String serverToStoragePath;
	
	public ServerInfo() {

		cpu = new ArrayList<CPU>();
		memories = new ArrayList<Memory>();
		ipAddress = new ArrayList<>();
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}
	
	public String getHostname() {
		return hostname;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setMemories(List<Memory> memories) {
		this.memories = memories;
	}
	
	public List<Memory> getMemories() {
		return memories;
	}
	
	public void setIsVirtualServer(String isVirtualServer) {
		this.isVirtualServer = isVirtualServer;
	}
	
	public String getIsVirtualServer() {
		return isVirtualServer;
	}
	
	public void setOs(OperatingSystem os) {
		this.os = os;
	}
	
	public OperatingSystem getOs() {
		return os;
	}
	
	public void setCpu(List<CPU> cpu) {
		this.cpu = cpu;
	}
	
	public List<CPU> getCpuAsList() {
		return cpu;
	}
	
	public String getCpu(){
		int temp = 0;
		for (CPU c : this.cpu) {
			temp = c.getMaxClockSpeed();
		}
		return temp+" Mhz ("+this.cpu.size()+")";
	}

	public void setModel(String model) {
		this.model = model;		
	}
	
	public String getModel() {
		return this.model;
	}
	
	public void setIpAddress(List<String> ipAddress) {
		this.ipAddress = ipAddress;
	}
	
	public List<String> getIpAddressAsList() {
		return ipAddress;
	}
	
	public String getIpAddress(){
		String iplist = "";
		for (String ip : this.ipAddress) {
			iplist += ip+" ";
		}
		return iplist;
	}
	
	public void setRackName(String rackName) {
		this.rackName = rackName;
	}
	
	public String getRackName() {
		return rackName;
	}
	
	public String getTotalMemory() {
		Long c = (long) 0;
		for (Memory m : this.memories) {
			System.out.println(c);
			c += m.getCapacity();
		}
		return String.valueOf(c/1024)+" Gb";
	}

	public Person getServerAdmin() {
		return serverAdmin;
	}

	public void setServerAdmin(Person serverAdmin) {
		this.serverAdmin = serverAdmin;
	}

	public String getServerOwner() {
		return serverOwner;
	}

	public void setServerOwner(String serverOwner) {
		this.serverOwner = serverOwner;
	}
		
}
