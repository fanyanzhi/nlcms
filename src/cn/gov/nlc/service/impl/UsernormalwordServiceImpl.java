package cn.gov.nlc.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import cn.gov.nlc.mapper.UsernormalwordMapper;
import cn.gov.nlc.pojo.Hotword;
import cn.gov.nlc.pojo.Usernormalword;
import cn.gov.nlc.pojo.UsernormalwordExample;
import cn.gov.nlc.pojo.UsernormalwordExample.Criteria;
import cn.gov.nlc.service.UsernormalwordService;

@Service
public class UsernormalwordServiceImpl implements UsernormalwordService {

	@Autowired
	UsernormalwordMapper usernormalwordMapperMapper;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public void setUsernormalword(String userName, String word) {
		int id = getUsernormalword(userName, word);
		if (id == -1) {
			Usernormalword usernormalword = new Usernormalword();
			usernormalword.setUsername(userName);
			usernormalword.setHotword(word);
			usernormalword.setSeacount(1);
			try {
				insertUsernormalword(usernormalword);
			} catch (Exception e) {

			}
		} else {
			try {
				updSeaCount(id);
			} catch (Exception e) {

			}
		}
	}

	@Override
	public void insertUsernormalword(Usernormalword normalword) {
		usernormalwordMapperMapper.insert(normalword);
	}

	@Override
	public int getUsernormalword(String userName, String word) {
		UsernormalwordExample example = new UsernormalwordExample();
		Criteria criteria = example.createCriteria();
		criteria.andHotwordEqualTo(word);
		criteria.andUsernameEqualTo(userName);
		List<Usernormalword> list = usernormalwordMapperMapper.selectByExample(example);
		if (list == null || list.size() == 0)
			return -1;
		return list.get(0).getId();
	}

	@Override
	public void updSeaCount(int id) {
		String sql = "update usernormalword set seacount=seacount+1 where id=" + id;
		jdbcTemplate.execute(sql);

	}

	@Override
	public List<Usernormalword> getHotword(String userName) {
		UsernormalwordExample example = new UsernormalwordExample();
		example.setOrderByClause("seacount desc");
		List<Usernormalword> list = usernormalwordMapperMapper.selectByExample(example);
		return list;
	}

}
