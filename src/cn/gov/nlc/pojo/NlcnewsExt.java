package cn.gov.nlc.pojo;

import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import cn.gov.nlc.base.converter.CustomDateSerializer;

public class NlcnewsExt extends Nlcnews{

	private Date pstartDate;
	
	private Date pendDate;
	
	private Date sstartDate;
	
	private Date sendDate;
	
	private Date time;

	@JsonSerialize(using = CustomDateSerializer.class)
	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public Date getPstartDate() {
		return pstartDate;
	}

	public void setPstartDate(Date pstartDate) {
		this.pstartDate = pstartDate;
	}

	public Date getPendDate() {
		return pendDate;
	}

	public void setPendDate(Date pendDate) {
		this.pendDate = pendDate;
	}

	public Date getSstartDate() {
		return sstartDate;
	}

	public void setSstartDate(Date sstartDate) {
		this.sstartDate = sstartDate;
	}

	public Date getSendDate() {
		return sendDate;
	}

	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}
	
	
}
