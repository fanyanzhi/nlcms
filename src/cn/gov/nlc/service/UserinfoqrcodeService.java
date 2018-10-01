package cn.gov.nlc.service;

import cn.gov.nlc.pojo.Userinfoqrcode;
import net.sf.json.JSONObject;

public interface UserinfoqrcodeService {

	public void insertUserinfoqrcode(Userinfoqrcode userinfoqrcode);
	
	public Userinfoqrcode selectByQrcode(String qrcode);
	
	public JSONObject matchUserinfo(String qrcode) throws Exception;
}
