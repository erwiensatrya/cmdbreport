package id.co.mii.cmdbreport.bo;

public class BusinessService {

	private String instanceID;
	private String name;
	
	public BusinessService() {
		// TODO Auto-generated constructor stub
	}
	
	public BusinessService(String instanceID) {
		this.instanceID = instanceID;
	}
	
	public void setInstanceID(String instanceID) {
		this.instanceID = instanceID;
	}
	
	public String getInstanceID() {
		return instanceID;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	@Override
	public String toString(){
		return this.name;
	}
}
