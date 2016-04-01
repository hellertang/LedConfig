package gui.sendtab;

public class videoAttribute {
	
	private int times;
	private String ratio;
	
	public int getTimes() {
		return times;
	}

	public void setTimes(int times) {
		this.times = times;
	}

	public String getRatio() {
		return ratio;
	}

	public void setRatio(String ratio) {
		this.ratio = ratio;
	}
	
	public videoAttribute(){
		
	}
	
	public void initVideoAttr(){
		setTimes(0);
		setRatio("Ô­Ê¼±ÈÀý");
	}
}
