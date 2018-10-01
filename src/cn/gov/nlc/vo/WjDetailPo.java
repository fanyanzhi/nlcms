package cn.gov.nlc.vo;

/**
 * 文津页面的详情
 * @author DAYI
 *
 */
public class WjDetailPo extends DetailPo{

	private int audio;
	
	private String detailid;
	
	public String getDetailid() {
		return detailid;
	}

	public void setDetailid(String detailid) {
		this.detailid = detailid;
	}

	public int getAudio() {
		return audio;
	}

	public void setAudio(int audio) {
		this.audio = audio;
	}
}
