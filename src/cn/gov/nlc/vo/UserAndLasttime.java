package cn.gov.nlc.vo;

import java.util.Date;

//用户及最后登录时间
public class UserAndLasttime {

	//读者类别
	private String rdrolecode;

	//读者姓名
	private String name;
	
	//读者卡号码
	private String loginaccount;
	
	//证件类别
	private String cardtype;
	
	//读者证件号码
	private String cardno;
	
	//最后登录时间
	private Date lasttime;

	public String getRdrolecode() {
		return rdrolecode;
	}

	public void setRdrolecode(String rdrolecode) {
		this.rdrolecode = rdrolecode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLoginaccount() {
		return loginaccount;
	}

	public void setLoginaccount(String loginaccount) {
		this.loginaccount = loginaccount;
	}

	public String getCardtype() {
		return cardtype;
	}

	public void setCardtype(String cardtype) {
		this.cardtype = cardtype;
	}

	public String getCardno() {
		return cardno;
	}

	public void setCardno(String cardno) {
		this.cardno = cardno;
	}

	public Date getLasttime() {
		return lasttime;
	}

	public void setLasttime(Date lasttime) {
		this.lasttime = lasttime;
	}
	
	
}
