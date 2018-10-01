package cn.gov.nlc.vo;

public class Yhhx {
	//读者类别
	private String rdrolecode;
	//读者姓名
	private String name;
	//读者卡号码
	private String loginaccount;
	//证件类别
	private String cardtype;
	//证件号
	private String cardno;
	//年龄
	private Integer age;
	//性别
	private String sextype;
	//使用次数
	private Integer cs;
	//使用时长
	private Integer sc;
	//页面访问数
	private Integer pv;
	//唯一页面访问数
	private Integer uv;
	//下载次数
	private Integer ds;
	
	
	public String getCardtype() {
		return cardtype;
	}
	public void setCardtype(String cardtype) {
		this.cardtype = cardtype;
	}
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
	public String getCardno() {
		return cardno;
	}
	public void setCardno(String cardno) {
		this.cardno = cardno;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public String getSextype() {
		return sextype;
	}
	public void setSextype(String sextype) {
		this.sextype = sextype;
	}
	public Integer getCs() {
		return cs;
	}
	public void setCs(Integer cs) {
		this.cs = cs;
	}
	public Integer getSc() {
		return sc;
	}
	public void setSc(Integer sc) {
		this.sc = sc;
	}
	public Integer getPv() {
		return pv;
	}
	public void setPv(Integer pv) {
		this.pv = pv;
	}
	public Integer getUv() {
		return uv;
	}
	public void setUv(Integer uv) {
		this.uv = uv;
	}
	public Integer getDs() {
		return ds;
	}
	public void setDs(Integer ds) {
		this.ds = ds;
	}
	
}
