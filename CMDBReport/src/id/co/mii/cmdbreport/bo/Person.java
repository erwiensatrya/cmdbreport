package id.co.mii.cmdbreport.bo;

public class Person {

	private String name;
	private String email;
	private String department;
	
	public Person() {
	
	}
	
	public Person (String name, String email, String department){
		this.name = name;
		this.email = email;
		this.department = department;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setDepartment(String department) {
		this.department = department;
	}
	
	public String getDepartment() {
		return department;
	}
	
	@Override
	public String toString() {
		return this.getName();
	}
}
