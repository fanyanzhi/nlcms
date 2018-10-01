package cn.gov.nlc.vo;

/**
 * 页面统计的实体类
 * @author DAYI
 *
 */
public class PageStatistic {

	private String module;	//页面名
	
	private Integer listnum;	//列表页访问次数
	
	private Integer detailnum;	//详情页访问次数
	
	private Long listRemainTime;	//列表页停留时间
	
	private Long detailRemainTime;	//详情页停留时间


	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public Integer getListnum() {
		return listnum;
	}

	public void setListnum(Integer listnum) {
		this.listnum = listnum;
	}

	public Integer getDetailnum() {
		return detailnum;
	}

	public void setDetailnum(Integer detailnum) {
		this.detailnum = detailnum;
	}

	public Long getListRemainTime() {
		return listRemainTime;
	}

	public void setListRemainTime(Long listRemainTime) {
		this.listRemainTime = listRemainTime;
	}

	public Long getDetailRemainTime() {
		return detailRemainTime;
	}

	public void setDetailRemainTime(Long detailRemainTime) {
		this.detailRemainTime = detailRemainTime;
	}
	
}
