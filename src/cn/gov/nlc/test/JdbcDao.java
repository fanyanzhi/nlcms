package cn.gov.nlc.test;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class JdbcDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public void show1() {
		jdbcTemplate.execute("call t1('谢耳朵', 'virgin')");
	}
	
	public void show2() {
		String res = (String)jdbcTemplate.execute(new CallableStatementCreator(){

			@Override
			public CallableStatement createCallableStatement(Connection con) throws SQLException {
				String storedProc = "{call t2(?, ?)}";
				CallableStatement cs = con.prepareCall(storedProc);
				cs.setInt(1, 15);
				cs.registerOutParameter(2, java.sql.Types.VARCHAR);
				return cs;
			}
			
		}, new CallableStatementCallback(){
			@Override
			public Object doInCallableStatement(CallableStatement cs) throws SQLException, DataAccessException {
				cs.execute();
				return cs.getString(2);
			}
		});
		
		System.out.println("结果是" + res);
		
	}

}
