package cn.gov.nlc.vo;

/**
 * 页面访问，详情访问量含分享
 * @author DAYI
 *
 */
public class DetailPo {

	private String title;
	
	private int pv;
	
	private int uv;
	
	private int share;
	
	private Integer collectcount;
	
	private Integer praisecount;

	public Integer getCollectcount() {
		return collectcount;
	}

	public void setCollectcount(Integer collectcount) {
		this.collectcount = collectcount;
	}

	public Integer getPraisecount() {
		return praisecount;
	}

	public void setPraisecount(Integer praisecount) {
		this.praisecount = praisecount;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getPv() {
		return pv;
	}

	public void setPv(int pv) {
		this.pv = pv;
	}

	public int getUv() {
		return uv;
	}

	public void setUv(int uv) {
		this.uv = uv;
	}

	public int getShare() {
		return share;
	}

	public void setShare(int share) {
		this.share = share;
	}
	
}
