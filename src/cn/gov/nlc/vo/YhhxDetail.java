package cn.gov.nlc.vo;

/**
 * 用户画像的详情
 * @author DAYI
 *
 */
public class YhhxDetail {
	//浏览时间
	private String time;
	//浏览详情
	private String detail;
	//阅读时间
	private Integer duration;
	
	private Integer kind;
	
	private String detailid;
	
	public Integer getKind() {
		return kind;
	}

	public void setKind(Integer kind) {
		this.kind = kind;
	}

	public String getDetailid() {
		return detailid;
	}

	public void setDetailid(String detailid) {
		this.detailid = detailid;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public Integer getDuration() {
		return duration;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
	}
	
}
