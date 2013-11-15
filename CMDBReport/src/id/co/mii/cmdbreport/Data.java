package id.co.mii.cmdbreport;

public class Data {
	private String key;
	private int quantity;
	private String hostname;
	public String getHostname() {
		return hostname;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	public String getOs() {
		return os;
	}

	public void setOs(String os) {
		this.os = os;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	private String os;
	private String model;

	public Data(String item, int quantity) {
		this.key = item;
		this.quantity = quantity;
	}
	
	public Data(String hostname, String os, String model){
		this.hostname = hostname;
		this.os = os;
		this.model = model;
	}

	public String getItem() {
		return key;
	}

	public void setItem(String item) {
		this.key = item;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
}