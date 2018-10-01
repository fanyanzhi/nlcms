package cn.gov.nlc.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import cn.gov.nlc.mapper.ReaderpicsetMapper;
import cn.gov.nlc.pojo.Readerpicset;
import cn.gov.nlc.pojo.ReaderpicsetExample;
import cn.gov.nlc.service.ReaderpicsetService;

@Service
public class ReaderpicsetServiceImpl implements ReaderpicsetService{

	@Autowired
	private ReaderpicsetMapper readerpicsetMapper;
	@Autowired
	private JdbcTemplate jdbcTemplate;

	/**
	 * 查出所有的读者画像展示设置
	 */
	@Override
	public List<Readerpicset> getAll() {
		ReaderpicsetExample example = new ReaderpicsetExample();
		example.setOrderByClause("sequ asc");
		List<Readerpicset> list = readerpicsetMapper.selectByExample(example);
		return list;
	}

	/**
	 * 修改读者画像展示设置状态位
	 */
	@Override
	public void modifyStatus(String typeidarr) {
		String sql = "update readerpicset set status = 0 ";
		jdbcTemplate.execute(sql);
		
		if(StringUtils.isNotBlank(typeidarr)) {
			String[] strArr = typeidarr.split(",");
			for (String str : strArr) {
				String sql2 = "update readerpicset set status = 1 where typeid = '" +str+"'" ;
				jdbcTemplate.execute(sql2);
			}
		}
	}
}
