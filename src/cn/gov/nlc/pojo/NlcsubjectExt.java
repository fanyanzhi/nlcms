package cn.gov.nlc.pojo;

import java.util.Date;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import cn.gov.nlc.base.converter.CustomDateSerializer;

public class NlcsubjectExt extends Nlcsubject {
	private Date time;

	@JsonSerialize(using = CustomDateSerializer.class)
	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

}
