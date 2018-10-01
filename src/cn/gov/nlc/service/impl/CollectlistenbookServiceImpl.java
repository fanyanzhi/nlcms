package cn.gov.nlc.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import cn.gov.nlc.mapper.CollectlistenbookMapper;
import cn.gov.nlc.pojo.Collectlistenbook;
import cn.gov.nlc.pojo.CollectlistenbookExample;
import cn.gov.nlc.pojo.Listenbook;
import cn.gov.nlc.pojo.CollectlistenbookExample.Criteria;
import cn.gov.nlc.service.CollectlistenbookService;

@Service
public class CollectlistenbookServiceImpl implements CollectlistenbookService {

	@Autowired
	CollectlistenbookMapper collectlistenbookMapper;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public void insertCollectlistenbook(Collectlistenbook collectlistenbook) {
		collectlistenbookMapper.insert(collectlistenbook);
	}

	@Override
	public void deleteCollectlistenbook(String loginAccount, String bookid) {
		Collectlistenbook record = new Collectlistenbook();
		record.setStatus(0);
		CollectlistenbookExample example = new CollectlistenbookExample();
		Criteria criteria = example.createCriteria();
		criteria.andUsernameEqualTo(loginAccount);
		criteria.andBookidEqualTo(bookid);
		collectlistenbookMapper.updateByExampleSelective(record, example);
	}

	public boolean existcollectlistenbook(String loginAccount, String bookid) {
		CollectlistenbookExample example = new CollectlistenbookExample();
		Criteria criteria = example.createCriteria();
		criteria.andUsernameEqualTo(loginAccount);
		criteria.andBookidEqualTo(bookid);
		if (collectlistenbookMapper.countByExample(example) > 0)
			return true;
		return false;
	}

	@Override
	public List<Listenbook> getCollectlistenbook(String loginAccount) {
		String sql = "select l.bookid, l.bookname, l.bookPic, l.url, l.path from collectlistenbook c, listenbook l where c.bookid = l.bookid and c.username='"
				+ loginAccount + "' and c.status = 1";
		List<Listenbook> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Listenbook>(Listenbook.class));
		return list;
	}

	@Override
	public void updateStatus(String loginAccount, String bookid, Integer status) {
		CollectlistenbookExample example = new CollectlistenbookExample();
		Criteria criteria = example.createCriteria();
		criteria.andUsernameEqualTo(loginAccount);
		criteria.andBookidEqualTo(bookid);
		Collectlistenbook record = new Collectlistenbook();
		record.setStatus(status);
		record.setTime(new Date());
		collectlistenbookMapper.updateByExampleSelective(record, example);
	}

}
