package cn.gov.nlc.vo;

public class VirtualUserDetail {

	private String mtime;	//月份
	
	private Integer monthNewAddNum;	//当月虚拟用户新增量
	
	private Integer thisYearAccNum;	//本年虚拟用户累计新增量

	public String getMtime() {
		return mtime;
	}

	public void setMtime(String mtime) {
		this.mtime = mtime;
	}

	public Integer getMonthNewAddNum() {
		return monthNewAddNum;
	}

	public void setMonthNewAddNum(Integer monthNewAddNum) {
		this.monthNewAddNum = monthNewAddNum;
	}

	public Integer getThisYearAccNum() {
		return thisYearAccNum;
	}

	public void setThisYearAccNum(Integer thisYearAccNum) {
		this.thisYearAccNum = thisYearAccNum;
	}

	@Override
	public String toString() {
		return "VirtualUserDetail [mtime=" + mtime + ", monthNewAddNum=" + monthNewAddNum + ", thisYearAccNum="
				+ thisYearAccNum + "]";
	}

	
}
