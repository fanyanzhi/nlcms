package cn.gov.nlc.vo;

import java.util.List;

public class EasyUiDataGridResult {

	private long total;
	private List<?> rows;
	
	public EasyUiDataGridResult(long total, List<?> rows) {
		this.total = total;
		this.rows = rows;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public List<?> getRows() {
		return rows;
	}

	public void setRows(List<?> rows) {
		this.rows = rows;
	}
	
}
