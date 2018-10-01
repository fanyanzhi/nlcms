package cn.gov.nlc.vo;

import java.util.List;

public class EasyUiDataGridResultFoot extends EasyUiDataGridResult{

	public EasyUiDataGridResultFoot(long total, List<?> rows, List<?> footer) {
		super(total, rows);
		this.footer = footer;
	}

	private List<?> footer;

	public List<?> getFooter() {
		return footer;
	}

	public void setFooter(List<?> footer) {
		this.footer = footer;
	}
}
