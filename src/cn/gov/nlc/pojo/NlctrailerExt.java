package cn.gov.nlc.pojo;

import java.util.Date;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import cn.gov.nlc.base.converter.CustomDateSerializer;

public class NlctrailerExt extends Nlctrailer{

	private Date zstarttime;	//开始时间的起始
	
	private Date ystarttime;	//开始时间的结束
	
	private Date zendtime;		//结束时间的起始
	
	private Date yendtime;		//结束时间的结束
	
	private Date zcretime;		//创建时间的起始
	
	private Date ycretime;		//创建时间的结束
	
	private Date time;

	@JsonSerialize(using = CustomDateSerializer.class)
	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public Date getZstarttime() {
		return zstarttime;
	}

	public void setZstarttime(Date zstarttime) {
		this.zstarttime = zstarttime;
	}

	public Date getYstarttime() {
		return ystarttime;
	}

	public void setYstarttime(Date ystarttime) {
		this.ystarttime = ystarttime;
	}

	public Date getZendtime() {
		return zendtime;
	}

	public void setZendtime(Date zendtime) {
		this.zendtime = zendtime;
	}

	public Date getYendtime() {
		return yendtime;
	}

	public void setYendtime(Date yendtime) {
		this.yendtime = yendtime;
	}

	public Date getZcretime() {
		return zcretime;
	}

	public void setZcretime(Date zcretime) {
		this.zcretime = zcretime;
	}

	public Date getYcretime() {
		return ycretime;
	}

	public void setYcretime(Date ycretime) {
		this.ycretime = ycretime;
	}
	
}
