package cn.gov.nlc.vo;

/**
 * 个人访问详情的po
 * @author DAYI
 *
 */
public class PersonPo {

	private String name;
	
	private String detailid;
	
	private int pv;
	
	private int uv;
	
	public String getDetailid() {
		return detailid;
	}

	public void setDetailid(String detailid) {
		this.detailid = detailid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
	
}
