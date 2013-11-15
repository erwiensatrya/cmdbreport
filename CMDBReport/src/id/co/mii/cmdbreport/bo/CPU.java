package id.co.mii.cmdbreport.bo;

public class CPU {
	
	private int maxClockSpeed;
	
	public CPU(int maxClockSpeed) {
		this.maxClockSpeed = maxClockSpeed;
	}

	public int getMaxClockSpeed() {
		return maxClockSpeed;
	}
	
	public void setMaxClockSpeed(int maxClockSpeed) {
		this.maxClockSpeed = maxClockSpeed;
	}
	
	@Override
	public String toString() {		
		return this.maxClockSpeed+" Mhz";		
	}
}
