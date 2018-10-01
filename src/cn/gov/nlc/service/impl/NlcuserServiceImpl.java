package cn.gov.nlc.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import cn.gov.nlc.util.Common;
import cn.gov.nlc.mapper.NlcuserMapper;
import cn.gov.nlc.mapper.NlcuserloginlogMapper;
import cn.gov.nlc.mapper.NlcuseronlineMapper;
import cn.gov.nlc.pojo.Nlcuser;
import cn.gov.nlc.pojo.NlcuserExample;
import cn.gov.nlc.pojo.NlcuserExample.Criteria;
import cn.gov.nlc.pojo.Nlcuserloginlog;
import cn.gov.nlc.service.NlcuserService;
import cn.gov.nlc.vo.EasyUiDataGridResult;

@Service
public class NlcuserServiceImpl implements NlcuserService {

	private static String TokenPassWord = "@a3k9#-;jdiu$98JH-03H~kpb59akj8j";

	@Autowired
	private NlcuserMapper nlcuserMapper;

	@Autowired
	private NlcuserloginlogMapper nlcuserloginlogMapper;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public void accountLogin(String userName, String password, String ip) {
		/*
		 * try { LoginProcessUtil.sendRequestString(userName, password, ip,
		 * "http://202.106.125.33:9080/sso/user-login",
		 * "http://localhost:8080/nlcmngr/user/loginhandler.do", request,
		 * response); } catch (Exception e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); } int i=200; if(i==200){
		 * 
		 * } String xml = ""; Nlcuser nlcuser = new Nlcuser();
		 * nlcuser.setAddress(""); insertAccount();
		 */
	}

	@Override
	public boolean isExistNlcuser(String loginAccount) {
		boolean boolExist = false;
		NlcuserExample example = new NlcuserExample();
		Criteria criteria = example.createCriteria();
		criteria.andLoginaccountEqualTo(loginAccount);
		int iCount = nlcuserMapper.countByExample(example);
		if (iCount > 0)
			boolExist = true;
		return boolExist;
	}

	@Override
	public void insertNlcuser(Nlcuser nlcuser) {
		nlcuserMapper.insert(nlcuser);
	}

	@Override
	public void updateNlcuser(Nlcuser nlcuser) {
		NlcuserExample example = new NlcuserExample();
		Criteria criteria = example.createCriteria();

		criteria.andLoginaccountEqualTo(nlcuser.getLoginaccount());
		// nlcuserMapper.updateByExample(nlcuser, example);
		nlcuserMapper.updateByExampleSelective(nlcuser, example);

	}

	/**
	 * 用户展示页面，包括高级查询
	 */
	@Override
	public EasyUiDataGridResult getUserList(Integer page, Integer rows, Nlcuser nlcuser, Date pstartDate, Date pendDate) {
		String name = nlcuser.getName();
		String loginaccount = nlcuser.getLoginaccount();
		String cardno = nlcuser.getCardno();
		String rdrolecode = nlcuser.getRdrolecode();
		String cardtype = nlcuser.getCardtype();

		StringBuilder sb = new StringBuilder();
		sb.append("select count(*) from nlcuser where 1=1 ");
		
		StringBuilder sb2 = new StringBuilder();
		sb2.append("select * from nlcuser where 1=1 ");
		
		List<Object> paralist = new ArrayList<Object>();
		
		if (StringUtils.isNotBlank(name)) {
			paralist.add(name+"%");
			sb.append(" and name like ?");
			sb2.append(" and name like ?");
		}
		
		if (StringUtils.isNotBlank(loginaccount)) {
			paralist.add(loginaccount);
			sb.append(" and loginaccount = ?");
			sb2.append(" and loginaccount = ?");
		}
		
		if (StringUtils.isNotBlank(cardno)) {
			paralist.add(cardno);
			sb.append(" and cardno = ?");
			sb2.append(" and cardno = ?");
		}
		
		if (StringUtils.isNotBlank(rdrolecode)) {
			paralist.add(rdrolecode);
			sb.append(" and rdrolecode = ?");
			sb2.append(" and rdrolecode = ?");
		}
		
		if (StringUtils.isNotBlank(cardtype)) {
			paralist.add(cardtype);
			sb.append(" and cardtype = ?");
			sb2.append(" and cardtype = ?");
		}
		
		if(null != pstartDate && null != pendDate) {
			DateTime sdt = new DateTime(pstartDate);
			DateTime edt = new DateTime(pendDate);
			edt = edt.plusDays(1);
			paralist.add(sdt.toString("yyyy-MM-dd"));
			paralist.add(edt.toString("yyyy-MM-dd"));
			sb.append(" and loginAccount in (SELECT username FROM `nlcuserloginlog` where DATE_FORMAT(time,'%Y-%m-%d') >= ? and DATE_FORMAT(time,'%Y-%m-%d') < ? group by username)");
			sb2.append(" and loginAccount in (SELECT username FROM `nlcuserloginlog` where DATE_FORMAT(time,'%Y-%m-%d') >= ? and DATE_FORMAT(time,'%Y-%m-%d') < ? group by username)");
		}else if(null != pendDate) {
			DateTime edt = new DateTime(pendDate);
			edt = edt.plusDays(1);
			paralist.add(edt.toString("yyyy-MM-dd"));
			sb.append(" and loginAccount in (SELECT username FROM `nlcuserloginlog` where DATE_FORMAT(time,'%Y-%m-%d') < ? group by username)");
			sb2.append(" and loginAccount in (SELECT username FROM `nlcuserloginlog` where DATE_FORMAT(time,'%Y-%m-%d') < ? group by username)");
		}else if(null != pstartDate) {
			DateTime sdt = new DateTime(pstartDate);
			paralist.add(sdt.toString("yyyy-MM-dd"));
			sb.append(" and loginAccount in (SELECT username FROM `nlcuserloginlog` where DATE_FORMAT(time,'%Y-%m-%d') >= ? group by username)");
			sb2.append(" and loginAccount in (SELECT username FROM `nlcuserloginlog` where DATE_FORMAT(time,'%Y-%m-%d') >= ? group by username)");
		}
		sb.append(" order by inserttime desc");
		Object[] param = paralist.toArray();
		
		int totalRow = jdbcTemplate.queryForInt(sb.toString(), param);
		
		int start = 0;
		int end = 0;
		if(null != page && null != rows) {
			start = (page - 1) * rows;
			end = rows;
		}else {
			end = totalRow;
		}
		
		sb2.append(" order by inserttime desc limit " + start + " , " + end);
		List<Nlcuser> list = jdbcTemplate.query(sb2.toString(), param, new BeanPropertyRowMapper<Nlcuser>(Nlcuser.class));
		EasyUiDataGridResult result = new EasyUiDataGridResult(totalRow, list);
		return result;
	}
	

	/**
	 * 根据id单个删除
	 */
	@Override
	public int deleteSingleById(Integer id) {
		int result = nlcuserMapper.deleteByPrimaryKey(id);
		return result;
	}

	/**
	 * 查询所有nlcuser
	 */
	@Override
	public List<Nlcuser> getAll() {
		NlcuserExample example = new NlcuserExample();
		example.setOrderByClause("inserttime desc");
		List<Nlcuser> list = nlcuserMapper.selectByExample(example);
		return list;
	}

	/**
	 * 添加登录日志
	 * 
	 * @param nlcuserloginlog
	 */
	@Override
	public void addNlcuserloginlog(Nlcuserloginlog nlcuserloginlog) {
		nlcuserloginlogMapper.insert(nlcuserloginlog);
	}

	@Override
	public String CreateUserToken(String UserName) {
		String strInfo = UserName.concat(String.valueOf(new Date().getTime()));
		String strMD5 = Common.EnCodeMD5(strInfo);
		String strEncrypt = Common.EncryptData(strInfo, TokenPassWord);
		return strMD5.concat(strEncrypt);
	}

	/**
	 * 根据id得到user
	 */
	@Override
	public Nlcuser getById(Integer id) {
		Nlcuser nlcuser = nlcuserMapper.selectByPrimaryKey(id);
		return nlcuser;
	}

	@Override
	public Nlcuser getByAccount(String loginAccount) {
		NlcuserExample example = new NlcuserExample();
		Criteria criteria = example.createCriteria();
		criteria.andLoginaccountEqualTo(loginAccount);
		List<Nlcuser> list = nlcuserMapper.selectByExample(example);
		if (list != null && list.size() > 0)
			return list.get(0);
		return null;
	}

	/***
	 * 获取持卡用户
	 */
	@Override
	public List<String> getCardUser() {
		String sql = "select loginAccount from nlcuser where rdRoleCode = 'JS0002' and aliasstatus = '1'";
		List<String> list = jdbcTemplate.queryForList(sql, String.class);
		return list;
	}

	@Override
	public void updateByPrimaryKey(Nlcuser nlcuser) {
		nlcuserMapper.updateByPrimaryKey(nlcuser);
	}

	@Override
	public boolean checkPhone(String phone) {
		NlcuserExample example = new NlcuserExample();
		Criteria criteria1 = example.createCriteria();
		criteria1.andTelephoneEqualTo(phone);
		Criteria criteria2 = example.createCriteria();
		criteria2.andMobileEqualTo(phone);
		example.or(criteria2);
		List<Nlcuser> list = nlcuserMapper.selectByExample(example);
		if(null != list && list.size() > 0) {
			return true;
		}
		return false;
	}

}
