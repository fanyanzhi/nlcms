package cn.gov.nlc.vo;

/**
 * 各资源画像的实体类
 * @author DAYI
 *
 */
public class GzyhxPo {
	//资源类别
	private String type;
	//资源id
	private String magazineid;
	//名称
	private String title;
	//浏览量
	private String browse;
	//收藏量
	private String collect;
	//下载量
	private String down;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getMagazineid() {
		return magazineid;
	}
	public void setMagazineid(String magazineid) {
		this.magazineid = magazineid;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getBrowse() {
		return browse;
	}
	public void setBrowse(String browse) {
		this.browse = browse;
	}
	public String getCollect() {
		return collect;
	}
	public void setCollect(String collect) {
		this.collect = collect;
	}
	public String getDown() {
		return down;
	}
	public void setDown(String down) {
		this.down = down;
	}
	
}
