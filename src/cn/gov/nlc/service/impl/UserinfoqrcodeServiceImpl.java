package cn.gov.nlc.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.gov.nlc.mapper.UserinfoqrcodeMapper;
import cn.gov.nlc.pojo.Userinfoqrcode;
import cn.gov.nlc.pojo.UserinfoqrcodeExample;
import cn.gov.nlc.pojo.UserinfoqrcodeExample.Criteria;
import cn.gov.nlc.service.UserinfoqrcodeService;
import cn.gov.nlc.util.AesUtil;
import net.sf.json.JSONObject;

@Service
public class UserinfoqrcodeServiceImpl implements UserinfoqrcodeService{

	private static final Logger logger = Logger.getLogger(cn.gov.nlc.service.impl.UserinfoqrcodeServiceImpl.class);
	
	@Autowired
	private UserinfoqrcodeMapper userinfoqrcodeMapper;

	@Override
	public void insertUserinfoqrcode(Userinfoqrcode userinfoqrcode) {
		userinfoqrcodeMapper.insert(userinfoqrcode);
	}

	@Override
	public Userinfoqrcode selectByQrcode(String qrcode) {
		UserinfoqrcodeExample example = new UserinfoqrcodeExample();
		Criteria criteria = example.createCriteria();
		criteria.andQrcodeEqualTo(qrcode);
		List<Userinfoqrcode> list = userinfoqrcodeMapper.selectByExample(example);
		if(null != list && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	//通过二维码去匹配
	@Override
	public JSONObject matchUserinfo(String qrcode) throws Exception{
		JSONObject res = new JSONObject();
		String deQrcode = AesUtil.decodeAES(qrcode);
		String[] arrQrcode = deQrcode.split("@");
		if(!deQrcode.contains("@") || arrQrcode.length != 2) {
			res.put("result", false);
			res.put("message", "二维码格式错误");
			res.put("errcode", 2);
			return res;
		}
		
		long qrtime = Long.parseLong(arrQrcode[1]);
		long curTime = System.currentTimeMillis();
		if((curTime - qrtime) > 60 * 1000 * 3) {
			res.put("result", false);
			res.put("message", "二维码过期");
			res.put("errcode", 3);
			return res;
		}
		
		Userinfoqrcode userinfoqrcode = selectByQrcode(deQrcode);
		if(null == userinfoqrcode) {
			res.put("result", false);
			res.put("message", "二维码不存在");
			res.put("errcode", 4);
			return res;
		}
		res.put("result", true);
		res.put("info", userinfoqrcode.getUsername());
		return res;
	}
	
	
}
