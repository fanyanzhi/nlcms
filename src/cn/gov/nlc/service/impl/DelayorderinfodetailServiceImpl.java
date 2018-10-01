package cn.gov.nlc.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.gov.nlc.mapper.DelayorderinfodetailMapper;
import cn.gov.nlc.pojo.Delayorderinfodetail;
import cn.gov.nlc.pojo.DelayorderinfodetailExample;
import cn.gov.nlc.pojo.DelayorderinfodetailExample.Criteria;
import cn.gov.nlc.service.DelayorderinfodetailService;

@Service
public class DelayorderinfodetailServiceImpl implements DelayorderinfodetailService{

	@Autowired
	private DelayorderinfodetailMapper delayorderinfodetailMapper;

	/**
	 * 插入
	 * @param delayorderinfodetail
	 */
	@Override
	public void insert(Delayorderinfodetail delayorderinfodetail) {
		delayorderinfodetailMapper.insert(delayorderinfodetail);
	}

	/**
	 * 通过商户订单号查详情
	 * @param orderno
	 * @return
	 */
	@Override
	public List<Delayorderinfodetail> getListByOrderno(String orderno) {
		DelayorderinfodetailExample example = new DelayorderinfodetailExample();
		Criteria criteria = example.createCriteria();
		criteria.andOrdernoEqualTo(orderno);
		List<Delayorderinfodetail> list = delayorderinfodetailMapper.selectByExample(example);
		return list;
	}
}
