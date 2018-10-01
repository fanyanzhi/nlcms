package cn.gov.nlc.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JdbcService {

	@Autowired
	private JdbcDao jdbcDao;
	
	public void show1() {
		jdbcDao.show1();
	}
	
	public void show2() {
		jdbcDao.show2();
	}


}
