package cn.gov.nlc.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import cn.gov.nlc.mapper.CollectreadtxMapper;
import cn.gov.nlc.pojo.Collectreadtx;
import cn.gov.nlc.pojo.CollectreadtxExample;
import cn.gov.nlc.pojo.Readtx;
import cn.gov.nlc.pojo.CollectreadtxExample.Criteria;
import cn.gov.nlc.service.CollectreadtxService;

@Service
public class CollectreadtxServiceImpl implements CollectreadtxService {

	@Autowired
	CollectreadtxMapper collectreadtxMapper;
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public void insertCollectreadtx(Collectreadtx collectreadtx) {
		collectreadtxMapper.insert(collectreadtx);
	}

	@Override
	public void deleteCollectreadtx(String loginAccount, String magazineId) {
		Collectreadtx record = new Collectreadtx();
		record.setStatus(0);
		CollectreadtxExample example = new CollectreadtxExample();
		Criteria criteria = example.createCriteria();
		criteria.andUsernameEqualTo(loginAccount);
		criteria.andMagazineidEqualTo(magazineId);
		collectreadtxMapper.updateByExampleSelective(record, example);
	}

	@Override
	public List<Readtx> getCollectreadtx(String loginAccount) {
		String sql = "select r.magazineid, r.title, r.issue, r.thumbnail, r.thumbnailsmall from collectreadtx c, readtx r where c.magazineid = r.magazineid and c.username='"
				+ loginAccount + "' and c.status = 1";
		List<Readtx> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Readtx>(Readtx.class));
		return list;
	}

	@Override
	public boolean existCollectreadtx(String loginAccount, String magazineId) {
		CollectreadtxExample example = new CollectreadtxExample();
		Criteria criteria = example.createCriteria();
		criteria.andUsernameEqualTo(loginAccount);
		criteria.andMagazineidEqualTo(magazineId);
		if (collectreadtxMapper.countByExample(example) > 0)
			return true;
		return false;
	}

	@Override
	public void updateStatus(String loginAccount, String magazineId, Integer status) {
		CollectreadtxExample example = new CollectreadtxExample();
		Criteria criteria = example.createCriteria();
		criteria.andUsernameEqualTo(loginAccount);
		criteria.andMagazineidEqualTo(magazineId);
		Collectreadtx record = new Collectreadtx();
		record.setStatus(status);
		record.setTime(new Date());
		collectreadtxMapper.updateByExampleSelective(record, example);
	}

}
