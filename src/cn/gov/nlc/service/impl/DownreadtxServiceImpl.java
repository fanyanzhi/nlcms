package cn.gov.nlc.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import cn.gov.nlc.pojo.Readtx;
import cn.gov.nlc.pojo.DownreadtxExample.Criteria;
import cn.gov.nlc.mapper.DownreadtxMapper;
import cn.gov.nlc.pojo.Downreadtx;
import cn.gov.nlc.pojo.DownreadtxExample;
import cn.gov.nlc.service.DownreadtxService;

@Service
public class DownreadtxServiceImpl implements DownreadtxService {

	@Autowired
	DownreadtxMapper downreadtxMapper;
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public void insertDownreadtx(Downreadtx downreadtx) {
		downreadtxMapper.insert(downreadtx);
	}

	@Override
	public void deleteDownreadtx(String loginAccount, String magazineId) {
		Downreadtx record = new Downreadtx();
		record.setStatus(0);
		DownreadtxExample example = new DownreadtxExample();
		Criteria criteria = example.createCriteria();
		criteria.andUsernameEqualTo(loginAccount);
		criteria.andMagazineidEqualTo(magazineId);
		downreadtxMapper.updateByExampleSelective(record, example);
	}

	@Override
	public List<Readtx> getDownreadtx(String loginAccount) {
		String sql = "select r.magazineid, r.title, r.issue, r.thumbnail, r.thumbnailsmall from downreadtx c, readtx r where c.magazineid = r.magazineid and c.username='"
				+ loginAccount + "' and c.status = 1";
		List<Readtx> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Readtx>(Readtx.class));
		return list;
	}

	public boolean existDownreadtx(String loginAccount, String magazineId) {
		DownreadtxExample example = new DownreadtxExample();
		Criteria criteria = example.createCriteria();
		criteria.andUsernameEqualTo(loginAccount);
		criteria.andMagazineidEqualTo(magazineId);
		if (downreadtxMapper.countByExample(example) > 0)
			return true;
		return false;
	}

	@Override
	public void updateStatus(String loginAccount, String magazineId, Integer status) {
		DownreadtxExample example = new DownreadtxExample();
		Criteria criteria = example.createCriteria();
		criteria.andUsernameEqualTo(loginAccount);
		criteria.andMagazineidEqualTo(magazineId);
		Downreadtx record = new Downreadtx();
		record.setStatus(status);
		record.setTime(new Date());
		downreadtxMapper.updateByExampleSelective(record, example);
	}
}
