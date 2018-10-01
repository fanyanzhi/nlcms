package cn.gov.nlc.pojo;

import java.util.List;

public class NlcnoticeVo {
	private Nlcnotice nlcnotice;
	private List<Nlcnoticeannex> atlist;

	public Nlcnotice getNlcnotice() {
		return nlcnotice;
	}

	public void setNlcnotice(Nlcnotice nlcnotice) {
		this.nlcnotice = nlcnotice;
	}

	public List<Nlcnoticeannex> getAtlist() {
		return atlist;
	}

	public void setAtlist(List<Nlcnoticeannex> atlist) {
		this.atlist = atlist;
	}

}
