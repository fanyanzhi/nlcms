package cn.gov.nlc.vo;

/**
 * 资源画像展示表格的po
 * @author DAYI
 *
 */
public class ZyhxTablePo {
	//月份
	private String date;
	//浏览量
	private String llnum;
	//收藏量
	private String scnum;
	//下载量
	private String xznum;
	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getLlnum() {
		return llnum;
	}
	public void setLlnum(String llnum) {
		this.llnum = llnum;
	}
	public String getScnum() {
		return scnum;
	}
	public void setScnum(String scnum) {
		this.scnum = scnum;
	}
	public String getXznum() {
		return xznum;
	}
	public void setXznum(String xznum) {
		this.xznum = xznum;
	}
	
}
