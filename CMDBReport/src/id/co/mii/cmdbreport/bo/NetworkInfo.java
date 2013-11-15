package id.co.mii.cmdbreport.bo;

import java.util.ArrayList;
import java.util.List;


public class NetworkInfo {

	private String hostname;
    private String description;
    private String type;
    private List<String> ipAddress;
    private String serialNumber;
    private String model;
    private String manufactureName;
    private Person networkAdmin;
    
    public NetworkInfo() {
    	ipAddress = new ArrayList<String>();
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
    
    public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setIpAddress(List<String> ipAddress) {
		this.ipAddress = ipAddress;
	}
    
    public List<String> getIpAddressAsList() {
		return ipAddress;
	}
    
    public String getIpAddress(){
    	String ipList = "";
    	for (String ip : this.ipAddress){
    		ipList += ip+" ";
    	}
    	return ipList;
    }
    public void setManufactureName(String manufactureName) {
		this.manufactureName = manufactureName;
	}
    
    public String getManufactureName() {
		return manufactureName;
	}
    
    public void setModel(String model) {
		this.model = model;
	}
    
    public String getModel() {
		return model;
	}
    
    public void setNetworkAdmin(Person networkAdmin) {
		this.networkAdmin = networkAdmin;
	}
    
    public Person getNetworkAdmin() {
		return networkAdmin;
	}
    
    public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
    
    public String getSerialNumber() {
		return serialNumber;
	}
    
}
