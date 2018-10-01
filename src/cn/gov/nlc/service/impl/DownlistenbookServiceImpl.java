package cn.gov.nlc.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import cn.gov.nlc.mapper.DownlistenbookMapper;
import cn.gov.nlc.pojo.Downlistenbook;
import cn.gov.nlc.pojo.DownlistenbookExample;
import cn.gov.nlc.pojo.Listenbook;
import cn.gov.nlc.pojo.DownlistenbookExample.Criteria;
import cn.gov.nlc.service.DownlistenbookService;

@Service
public class DownlistenbookServiceImpl implements DownlistenbookService {

	@Autowired
	DownlistenbookMapper downlistenbookMapper;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public void insertDownlistenbook(Downlistenbook downlistenbook) {
		downlistenbookMapper.insert(downlistenbook);
	}

	@Override
	public void deleteDownlistenbook(String loginAccount, String bookid) {
		Downlistenbook record = new Downlistenbook();
		record.setStatus(0);
		DownlistenbookExample example = new DownlistenbookExample();
		Criteria criteria = example.createCriteria();
		criteria.andUsernameEqualTo(loginAccount);
		criteria.andBookidEqualTo(bookid);
		downlistenbookMapper.updateByExampleSelective(record, example);
	}

	@Override
	public boolean existdownlistenbook(String loginAccount, String bookid) {
		DownlistenbookExample example = new DownlistenbookExample();
		Criteria criteria = example.createCriteria();
		criteria.andUsernameEqualTo(loginAccount);
		criteria.andBookidEqualTo(bookid);
		if (downlistenbookMapper.countByExample(example) > 0)
			return true;
		return false;
	}

	@Override
	public List<Listenbook> getDownlistenbook(String loginAccount) {
		String sql = "select l.bookid, l.bookname, l.bookPic, l.url, l.path from downlistenbook d ,listenbook l where d.bookid = l.bookid and d.username='"
				+ loginAccount + "' and d.status =1";
		List<Listenbook> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Listenbook>(Listenbook.class));
		return list;
	}

	@Override
	public void updateStatus(String loginAccount, String bookid, Integer status) {
		DownlistenbookExample example = new DownlistenbookExample();
		Criteria criteria = example.createCriteria();
		criteria.andUsernameEqualTo(loginAccount);
		criteria.andBookidEqualTo(bookid);
		Downlistenbook record = new Downlistenbook();
		record.setStatus(status);
		record.setTime(new Date());
		downlistenbookMapper.updateByExampleSelective(record, example);
	}

}
