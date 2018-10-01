package cn.gov.nlc.service;

import java.util.List;
import cn.gov.nlc.pojo.Delayorderinfodetail;

public interface DelayorderinfodetailService {

	/**
	 * 插入
	 * @param delayorderinfodetail
	 */
	public void insert(Delayorderinfodetail delayorderinfodetail);
	
	/**
	 * 通过商户订单号查详情
	 * @param orderno
	 * @return
	 */
	public List<Delayorderinfodetail> getListByOrderno(String orderno);
}
