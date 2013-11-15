package id.co.mii.cmdbreport.bo;

public class ApplicationInfo {
	 private String applicationName;
     private String applicationType;
     private String description;
     private String version;
     private String servicePack;
     private String patchNumber;
     private Person applicationCustodian;
     private Person applicationOwner;
     private Person applicationDeveloper;
     private String ownerEmail;
     private String serverHosting;
     private String applicationInfrastructure;
     private String applicationIntegration;
     private String manufactureName;
     private String vendorName;
     private String vendorPICEmail;
     private String useMiddleware;
     private String supplyDataTo;
     private BusinessService businessService;
     private int totalPhysicalServer;
     
     public ApplicationInfo() {
    	 this.totalPhysicalServer = 0;
	}
     
     public void addPhysicalServer(){
    	 this.totalPhysicalServer++;
     }
     public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}
     
     public String getApplicationName() {
		return applicationName;
	}
     
     public void setDescription(String description) {
		this.description = description;
	}
     
     public String getDescription() {
		return description;
	}

     public void setApplicationType(String applicationType) {
		this.applicationType = applicationType;
	}
     
     public String getApplicationType() {
		return applicationType;
	}
     
     public void setServicePack(String servicePack) {
		this.servicePack = servicePack;
	}
     
     public String getServicePack() {
		return servicePack;
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
     
     public void setServerHosting(String serverHosting) {
		this.serverHosting = serverHosting;
	}
     
     public String getServerHosting() {
		return serverHosting;
	}
     
     public void setApplicationInfrastructure(String applicationInfrastructure) {
		this.applicationInfrastructure = applicationInfrastructure;
	}
     
     public String getApplicationInfrastructure() {
		return applicationInfrastructure;
	}
     
     public void setApplicationIntegration(String applicationIntegration) {
		this.applicationIntegration = applicationIntegration;
	}
     
     public String getApplicationIntegration() {
		return applicationIntegration;
	}
     
     public void setManufactureName(String manufactureName) {
		this.manufactureName = manufactureName;
	}
     
     public String getManufactureName() {
		return manufactureName;
	}
     
     public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}
     
     public String getVendorName() {
		return vendorName;
	}
     
     public void setVendorPICEmail(String vendorPICEmail) {
		this.vendorPICEmail = vendorPICEmail;
	}
     
     public String getVendorPICEmail() {
		return vendorPICEmail;
	}
     
     public void setUseMiddleware(String useMiddleware) {
		this.useMiddleware = useMiddleware;
	}
     
     public String getUseMiddleware() {
		return useMiddleware;
	}
     
     public void setSupplyDataTo(String supplyDataTo) {
		this.supplyDataTo = supplyDataTo;
	}
     
     public String getSupplyDataTo() {
		return supplyDataTo;
	}
     
     public void setTotalPhysicalServer(int totalPhysicalServer) {
		this.totalPhysicalServer = totalPhysicalServer;
	}
     
     public int getTotalPhysicalServer() {
		return totalPhysicalServer;
	}
     
     public void setApplicationOwner(Person applicationOwner) {
		this.applicationOwner = applicationOwner;
		this.ownerEmail = applicationOwner.getEmail();
	}
     
     public Person getApplicationOwner() {
		return applicationOwner;
	}
     
     public void setApplicationCustodian(Person applicationCustodian) {
		this.applicationCustodian = applicationCustodian;
	}
     
     public Person getApplicationCustodian() {
		return applicationCustodian;
	}
     
     public void setApplicationDeveloper(Person applicationDeveloper) {
		this.applicationDeveloper = applicationDeveloper;
	}
     
     public Person getApplicationDeveloper() {
		return applicationDeveloper;
	}

	public BusinessService getBusinessService() {
		return businessService;
	}

	public void setBusinessService(BusinessService businessService) {
		this.businessService = businessService;
	}

	public String getOwnerEmail() {
		return this.ownerEmail;
	}

	public void setOwnerEmail(String ownerEmail) {
		this.ownerEmail = ownerEmail;
	}
     
	
     
     
}
