package com.baidu.edit.diy;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import com.baidu.edit.define.State;

public class ModUploader {
	private HttpServletRequest request = null;
	private Map<String, Object> conf = null;
	 
	public ModUploader(HttpServletRequest request, Map<String, Object> conf) {
	   this.request = request;
	   this.conf = conf;
	}
	
	public final State doExec(String num) {
		State state = null;
		if("1".equals(num)) {	//图片
			state = ModBinaryUploader.save(this.request, this.conf);
		}else if("2".equals(num)) {	//附件
			state = ModAttachUploader.save(this.request, this.conf);
		}else if("3".equals(num)) { //视屏
			state = ModVideoUploader.save(this.request, this.conf);
		}
		return state;
	}
}
