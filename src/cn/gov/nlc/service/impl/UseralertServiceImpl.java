package cn.gov.nlc.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.gov.nlc.mapper.UseralertMapper;
import cn.gov.nlc.pojo.Useralert;
import cn.gov.nlc.pojo.UseralertExample;
import cn.gov.nlc.pojo.UseralertExample.Criteria;
import cn.gov.nlc.service.UseralertService;

@Service
public class UseralertServiceImpl implements UseralertService {
	@Autowired
	UseralertMapper useralertMapper;

	@Override
	public void insertUseralert(Useralert useralert) {
		useralertMapper.insert(useralert);
	}

	@Override
	public boolean existThisAlert(String loginAccount, String type, String fileid) {
		UseralertExample example = new UseralertExample();
		Criteria criteria = example.createCriteria();
		criteria.andUsernameEqualTo(loginAccount);
		criteria.andTypeEqualTo(type);
		criteria.andFileidEqualTo(fileid);
		List<Useralert> list = useralertMapper.selectByExample(example);
		if (list != null && list.size() > 0)
			return true;
		return false;
	}

	@Override
	public List<Useralert> getUseralert() {
		UseralertExample example = new UseralertExample();
		Criteria criteria = example.createCriteria();
		DateTime dt = new DateTime();
		dt = dt.plusDays(7);
		String s = dt.toString("yyyy-MM-dd");
		DateTime dts = new DateTime(s);
		criteria.andEndtimeLessThanOrEqualTo(dts.toDate());
		
		DateTime dt2 = new DateTime();
		String s2 = dt2.toString("yyyy-MM-dd");
		DateTime dts2 = new DateTime(s2);
		criteria.andEndtimeGreaterThanOrEqualTo(dts2.toDate());
		List<Useralert> list = useralertMapper.selectByExample(example);
		return list;
	}

	public boolean cancelUseralert(String loginAccount, String type, String fileid) {
		UseralertExample example = new UseralertExample();
		Criteria criteria = example.createCriteria();
		criteria.andUsernameEqualTo(loginAccount);
		criteria.andTypeEqualTo(type);
		criteria.andFileidEqualTo(fileid);
		try {
			useralertMapper.deleteByExample(example);
			return true;
		} catch (Exception e) {

		}
		return false;
	}
	
}
