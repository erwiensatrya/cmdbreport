package id.co.mii.cmdbreport.bo;

public class Memory {
	
	private long capacity;

	public Memory(long capacity){
		this.capacity = capacity;		
	}
	
	public void setCapacity(long capacity) {
		this.capacity = capacity;
	}
	
	public long getCapacity() {
		return capacity;
	}
	
	@Override
	public String toString() {
//		super.toString();
		long temp = this.capacity / 1024;
		return String.valueOf(temp)+" Gb";
//		return temp.toString("#,##0.00") + " Gb";
	}
	
	
}