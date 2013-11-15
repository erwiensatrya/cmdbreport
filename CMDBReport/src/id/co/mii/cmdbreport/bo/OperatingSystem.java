package id.co.mii.cmdbreport.bo;

public class OperatingSystem {

	private String name;
	private String version;
	private String patchNumber;
	
	public OperatingSystem(String name, String version, String patchNumber) {
		this.name = name;
		this.version = version;
		this.patchNumber = patchNumber;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setVersion(String version) {
		this.version = version;
	}
	
	public String getVersion() {
		return version;
	}
	
	public void setPatchNumber(String patchNumber) {
		this.patchNumber = patchNumber;
	}
	
	public String getPatchNumber() {
		return patchNumber;
	}
	
	@Override
	public String toString() {
		this.version = "ver:"+version;
//		this.patchNumber = "patch:"+patchNumber; 
		return this.name +" ("+this.version +" "+ this.patchNumber+")";
	}
}
