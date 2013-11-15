package id.co.mii.cmdbreport.bo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DatabaseInfo {

	private String databaseName;
    private String description;
    private String databaseAdmin;
    private String databaseSoftwareName;
    private String databaseSoftwareVersion;
    private List<String> serverHosting;
    
    public DatabaseInfo() {
		serverHosting = new ArrayList<String>();
    }
    
    public void setDatabaseName(String databaseName) {
		this.databaseName = databaseName;
	}
    
    public String getDatabaseName() {
		return databaseName;
	}
    
    public void setDatabaseAdmin(String databaseAdmin) {
		this.databaseAdmin = databaseAdmin;
	}
    
    public void setDescription(String description) {
		this.description = description;
	}
    
    public String getDescription() {
		return description;
	}
    
    public String getDatabaseAdmin() {
		return databaseAdmin;
	}
    
    public void setDatabaseSoftwareName(String databaseSoftwareName) {
		this.databaseSoftwareName = databaseSoftwareName;
	}
    
    public String getDatabaseSoftwareName() {
		return databaseSoftwareName;
	}
    
    public void setDatabaseSoftwareVersion(String databaseSoftwareVersion) {
		this.databaseSoftwareVersion = databaseSoftwareVersion;
	}
    
    public String getDatabaseSoftwareVersion() {
		return databaseSoftwareVersion;
	}
    
    public List<String> getServerHostingAsList() {
		return serverHosting;
	}

	public String getServerHosting(){
    	StringBuilder sb = new StringBuilder();
    	for (String s : serverHosting) {
			sb.append(s+" ");
		}
    	return sb.toString();
    }
}
