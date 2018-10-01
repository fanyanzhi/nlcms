package cn.gov.nlc.vo;

/**
 * 沉默用户
 * @author DAYI
 *
 */
public class Cmyh {

	private String time;
	//新增用户
	private int newAmount;
	//沉默用户
	private int silAmount;
	
	private String percent;

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public int getNewAmount() {
		return newAmount;
	}

	public void setNewAmount(int newAmount) {
		this.newAmount = newAmount;
	}

	public int getSilAmount() {
		return silAmount;
	}

	public void setSilAmount(int silAmount) {
		this.silAmount = silAmount;
	}

	public String getPercent() {
		return percent;
	}

	public void setPercent(String percent) {
		this.percent = percent;
	}
	
}
