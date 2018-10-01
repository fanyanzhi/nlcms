package cn.gov.nlc.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import cn.gov.nlc.pojo.Olcclib;

public class AppOlcc {
	public static Map<String, Olcclib> mapOlcc = null;
	static {
		JdbcTemplate jdbcTemplate = (JdbcTemplate) AppUtil.getBean("jdbcTemplate");
		mapOlcc = new HashMap<String, Olcclib>();
		String sql = "select * from olcclib";
		List<Olcclib> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Olcclib>(Olcclib.class));
		for (Olcclib olcclib : list) {
			mapOlcc.put(olcclib.getCode(), olcclib);
		}
	}
}
