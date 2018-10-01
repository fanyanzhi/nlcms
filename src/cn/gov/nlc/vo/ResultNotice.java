package cn.gov.nlc.vo;

import java.util.List;

public class ResultNotice {

	private boolean result;
	
	private String count;
	
	private List<?> rows;

	public boolean isResult() {
		return result;
	}

	public void setResult(boolean result) {
		this.result = result;
	}

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public List<?> getRows() {
		return rows;
	}

	public void setRows(List<?> rows) {
		this.rows = rows;
	}
	
}
