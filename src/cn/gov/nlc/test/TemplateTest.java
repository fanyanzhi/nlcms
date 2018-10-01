package cn.gov.nlc.test;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/applicationContext-*.xml"})
public class TemplateTest {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Test
	public void t1() {
		String sql = "select loginAccount from nlcuser where rdRoleCode = 'JS0002' and aliasstatus = '1'";
		List<String> list = jdbcTemplate.queryForList(sql, String.class);
		System.out.println(list);
	}
	
	@Test
	public void t2() {
		String sql = "SELECT max(leaforder) FROM `nlcsubjectcatalog` where subjectid = '4aeafd245b3bcb33015b3cff74ee09e8' and pid = 'root'";
		List<Integer> list = jdbcTemplate.queryForList(sql, Integer.class);
		System.out.println(list);
	}
}
