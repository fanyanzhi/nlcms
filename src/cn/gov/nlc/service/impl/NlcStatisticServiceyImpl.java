package cn.gov.nlc.service.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Months;
import org.joda.time.Weeks;
import org.joda.time.Years;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import cn.gov.nlc.service.NlcStatisticServicey;
import cn.gov.nlc.vo.Apptj;
import cn.gov.nlc.vo.Cmyh;
import cn.gov.nlc.vo.Dyfx;
import cn.gov.nlc.vo.DyfxPo;
import cn.gov.nlc.vo.DyfxPoExt;
import cn.gov.nlc.vo.EasyUiDataGridResult;
import cn.gov.nlc.vo.Edition;
import cn.gov.nlc.vo.Hyyh;
import cn.gov.nlc.vo.Modelx;
import cn.gov.nlc.vo.Yhhx;
import cn.gov.nlc.vo.YhhxDetail;

@Service
public class NlcStatisticServiceyImpl implements NlcStatisticServicey{

	@Autowired
	private JdbcTemplate jdbcTemplate;

	/**
	 * 活跃用户按天统计的list
	 * @param page
	 * @param rows
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	@SuppressWarnings("deprecation")
	@Override
	public EasyUiDataGridResult dayHyyhList(Integer page, Integer rows, Date startDate, Date endDate) {
		List<Hyyh> reslist = new ArrayList<Hyyh>();
		
		DateTime sdt = new DateTime(startDate);
		DateTime edt = new DateTime(endDate);
		int totalRow = Days.daysBetween(sdt, edt).getDays() + 1; 
		
		int start = 0;
		int end = 0;
		if(null != page && null != rows) {
			start = (page - 1) * rows;
			int limit = rows;
			if((start + limit) > totalRow) {
				end = totalRow;
			}else {
				end = start + limit;
			}
		}else {
			end = totalRow;
		}
		
		for(int i = start; i < end; i++) {
			Hyyh xpo = new Hyyh();
			xpo.setTime(edt.minusDays(i).toString("yyyy-MM-dd"));
			String sql = "select count(1) from (select clientid from appstart where time >= ? and time < ? group by clientid) temp";
			Object[] sarr = {edt.minusDays(i).toString("yyyy-MM-dd"), edt.minusDays(i).plusDays(1).toString("yyyy-MM-dd")};
			int res = jdbcTemplate.queryForInt(sql, sarr);
			xpo.setAmount(res);
			reslist.add(xpo);
		}
		
		EasyUiDataGridResult result = new EasyUiDataGridResult(totalRow, reslist);
		return result;
	}

	/**
	 * 活跃用户按星期统计的list
	 * @param page
	 * @param rows
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	@Override
	public EasyUiDataGridResult weekHyyhList(Integer page, Integer rows, Date startDate, Date endDate) {
		List<Hyyh> list = new ArrayList<Hyyh>();
		
		DateTime ds = new DateTime(startDate);
		DateTime de = new DateTime(endDate);
		
		int minus = ds.getDayOfWeek() - 1;
		DateTime m = ds.minusDays(minus); // 得到刚刚好的本周的周一
		int p = de.getDayOfWeek();
		DateTime e = de.plusDays(7 - p);
		int weeks = Weeks.weeksBetween(m, e).getWeeks();
		int totalRow = weeks + 1;
		
		int start = 0;
		int end = 0;
		if(null != page && null != rows) {
			start = (page - 1) * rows;
			int limit = rows;
			if((start + limit) > totalRow) {
				end = totalRow;
			}else {
				end = start + limit;
			}
		}else {
			end = totalRow;
		}
		
		for(int i = start; i < end; i++) {
			Hyyh xpo = new Hyyh();
			String str = "第" + m.plusWeeks(i).getWeekOfWeekyear() + "周  " + m.plusWeeks(i).toString("yyyy-MM-dd") + " -- " + m.plusWeeks(i).plusDays(6).toString("yyyy-MM-dd");
			xpo.setTime(str);
			String sql = "select count(1) from (select clientid from appstart where time >= ? and time < ? group by clientid) temp";
			Object[] sarr = {m.plusWeeks(i).toString("yyyy-MM-dd"), m.plusWeeks(i + 1).toString("yyyy-MM-dd")};
			int res = jdbcTemplate.queryForInt(sql, sarr);
			xpo.setAmount(res);
			list.add(xpo);
		}
		
		EasyUiDataGridResult result = new EasyUiDataGridResult(totalRow, list);
		return result;
	}

	/**
	 * 活跃用户按月统计的list
	 * @param page
	 * @param rows
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	@Override
	public EasyUiDataGridResult monHyyhList(Integer page, Integer rows, Date startDate, Date endDate) {
		List<Hyyh> list = new ArrayList<Hyyh>();
		DateTime sdt = new DateTime(startDate);
		DateTime sdtx = new DateTime(sdt.toString("yyyy-MM-01"));
		DateTime edt = new DateTime(endDate);
		DateTime edtx = new DateTime(edt.toString("yyyy-MM-01"));
		int totalRow = Months.monthsBetween(sdtx, edtx).getMonths() + 1;
		
		int start = 0;
		int end = 0;
		if(null != page && null != rows) {
			start = (page - 1) * rows;
			int limit = rows;
			if((start + limit) > totalRow) {
				end = totalRow;
			}else {
				end = start + limit;
			}
		}else {
			end = totalRow;
		}
		
		for(int i = start; i < end; i++) {
			Hyyh xpo = new Hyyh();
			xpo.setTime(edt.minusMonths(i).toString("yyyy-MM"));
			String sql = "select count(1) from (select clientid from appstart where time >= ? and time < ? group by clientid) temp";
			Object[] sarr = {edt.minusMonths(i).toString("yyyy-MM-01"), edt.minusMonths(i).plusMonths(1).toString("yyyy-MM-01")};
			int res = jdbcTemplate.queryForInt(sql, sarr);
			xpo.setAmount(res);
			list.add(xpo);
		}
		
		EasyUiDataGridResult result = new EasyUiDataGridResult(totalRow, list);
		return result;
	}

	/**
	 * 沉默用户按周统计的list
	 * @param page
	 * @param rows
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	@Override
	public EasyUiDataGridResult weekCmyhList(Integer page, Integer rows, Date startDate, Date endDate) {
		List<Cmyh> list = new ArrayList<Cmyh>();
		
		DateTime ds = new DateTime(startDate);
		DateTime de = new DateTime(endDate);
		
		int minus = ds.getDayOfWeek() - 1;
		DateTime m = ds.minusDays(minus); // 得到刚刚好的本周的周一
		int p = de.getDayOfWeek();
		DateTime e = de.plusDays(7 - p);
		int weeks = Weeks.weeksBetween(m, e).getWeeks();
		int totalRow = weeks + 1;
		
		int start = 0;
		int end = 0;
		if(null != page && null != rows) {
			start = (page - 1) * rows;
			int limit = rows;
			if((start + limit) > totalRow) {
				end = totalRow;
			}else {
				end = start + limit;
			}
		}else {
			end = totalRow;
		}
		
		NumberFormat format = NumberFormat.getPercentInstance();// 获取格式化类实例
        format.setMinimumFractionDigits(2);// 设置小数位
		
		for(int i = start; i < end; i++) {
			Cmyh xpo = new Cmyh();
			String str = "第" + m.plusWeeks(i).getWeekOfWeekyear() + "周  " + m.plusWeeks(i).toString("yyyy-MM-dd") + " -- " + m.plusWeeks(i).plusDays(6).toString("yyyy-MM-dd");
			xpo.setTime(str);
			String sql = "select count(1) from (SELECT * FROM `nlcuser` where inserttime >= ? and inserttime < ?) t";
			Object[] sarr = {m.plusWeeks(i).toString("yyyy-MM-dd"), m.plusWeeks(i + 1).toString("yyyy-MM-dd")};
			int res = jdbcTemplate.queryForInt(sql, sarr);
			xpo.setNewAmount(res);
			
			String sql2 = "select count(1) from "
					+ " (select n.username from nlcuserloginlog n, (select loginAccount, inserttime FROM `nlcuser` where inserttime >= ? and inserttime < ?) t "
					+ " where n.username = t.loginAccount and date_format(n.time, '%Y-%m-%d') > DATE_ADD(date_format(t.inserttime, '%Y-%m-%d'),INTERVAL 1 day) group by n.username) x ";
			int res2 = jdbcTemplate.queryForInt(sql2, sarr);
			xpo.setSilAmount(res - res2);
			
			if(res == 0) {
				xpo.setPercent("--");
			}else {
				xpo.setPercent(format.format((res - res2 + 0.0) / res));
			}
			
			
			list.add(xpo);
		}
		
		EasyUiDataGridResult result = new EasyUiDataGridResult(totalRow, list);
		return result;
	}

	/**
	 * 用户画像的统计
	 * @param page
	 * @param rows
	 * @param startDate
	 * @param endDate
	 * @param yhhx
	 * @return
	 */
	@Override
	public EasyUiDataGridResult yhhxList(Integer page, Integer rows, Date startDate, Date endDate, Yhhx yhhx) {
		DateTime sdt = new DateTime(startDate);
		DateTime edt = new DateTime(endDate);
		
		String name = yhhx.getName();
		String loginaccount = yhhx.getLoginaccount();
		String rdrolecode = yhhx.getRdrolecode();
		String cardtype = yhhx.getCardtype();
		String cardno = yhhx.getCardno();
		
		String sql = "select count(1) from nlcuser where date_format(inserttime, '%Y-%m-%d') <= '"+edt.toString("yyyy-MM-dd")+"'";
		
		List<String> listArgs = new ArrayList<String>();
		if(StringUtils.isNotBlank(name)) {
			sql += " and name like ? "; 
			listArgs.add("%"+name.trim()+"%");
		}
		if(StringUtils.isNotBlank(loginaccount)) {
			sql += " and loginaccount = ? ";
			listArgs.add(loginaccount.trim());
		}
		if(StringUtils.isNotBlank(rdrolecode)) {
			sql += " and rdrolecode = ? ";
			listArgs.add(rdrolecode.trim());
		}
		if(StringUtils.isNotBlank(cardtype)) {
			sql += " and cardtype = ?";
			listArgs.add( cardtype.trim());
		}
		if(StringUtils.isNotBlank(cardno)) {
			sql += " and cardno = ?";
			listArgs.add( cardno.trim());
		}
		Object[] sarr0 =listArgs.toArray();
		int totalRow = jdbcTemplate.queryForInt(sql,sarr0);
		
		int start = 0;
		int limit = 0;
		if(null == page && null == rows) {
			start = 0;
			limit = totalRow;
		}else {
			start = (page - 1) * rows;
			limit = rows;
		}
		
		String sql2 = " select loginAccount, rdRoleCode, name, cardType, cardNo, sexType, cs, sc, pv, uv, ds from "
				+ " (select loginAccount, rdRoleCode, name, cardType, cardNo, sexType from nlcuser where date_format(inserttime, '%Y-%m-%d')<= ?) s "
				+ " left outer JOIN "
				+ " (select username, sum(cs) cs, sum(sc) sc, sum(pv) pv, sum(uv) uv, sum(ds) ds from scheuserpaint where time >= ? and time <= ? group by username "
				+ " ) x on s.loginAccount = x.username where 1 = 1 ";
		List<String> listArgs2 = new ArrayList<String>();
		listArgs2.add(edt.toString("yyyy-MM-dd"));
		listArgs2.add(sdt.toString("yyyy-MM-dd"));
		listArgs2.add(edt.toString("yyyy-MM-dd"));
		if(StringUtils.isNotBlank(name)) {
			sql2 += " and name like ? "; 
			listArgs2.add("%"+name.trim()+"%");
		}
		if(StringUtils.isNotBlank(loginaccount)) {
			sql2 += " and loginaccount = ? ";
			listArgs2.add(loginaccount.trim());
		}
		if(StringUtils.isNotBlank(rdrolecode)) {
			sql2 += " and rdrolecode = ? ";
			listArgs2.add(rdrolecode.trim());
		}
		if(StringUtils.isNotBlank(cardtype)) {
			sql2 += " and cardtype = ? ";
			listArgs2.add(cardtype.trim());
		}
		if(StringUtils.isNotBlank(cardno)) {
			sql2 += " and cardno = ? ";
			listArgs2.add(cardno.trim());
		}
		
		String tail = " order by loginAccount desc limit "+start+" , "+limit;
		sql2 += tail;
		
		
		Object[] sarr = listArgs2.toArray();
		List<Yhhx> list = jdbcTemplate.query(sql2, sarr, new BeanPropertyRowMapper<Yhhx>(Yhhx.class));
		
		DateTime now = new DateTime(new Date());
		DateTimeFormatter format = DateTimeFormat.forPattern("yyyyMMdd");  
		DateTimeFormatter format2 = DateTimeFormat.forPattern("yyMMdd");  
		
		for (Yhhx po : list) {
			if("01".equals(po.getCardtype())) {
				String sfz = po.getCardno();
				if(StringUtils.isBlank(sfz)) {
					continue;
				}
				String identityreg = "^(^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$)|(^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])((\\d{4})|\\d{3}[Xx])$)$";
				Pattern idpattern = Pattern.compile(identityreg);
				Matcher matcher = idpattern.matcher(sfz);
				boolean b = matcher.matches();
				if(!b) {
					continue;
				}
				
				String birthday = "";
				DateTime d = null;
				if(sfz.length() > 15) {
					birthday = sfz.substring(6, 14);
					d = DateTime.parse(birthday, format); 
				}else {
					birthday = sfz.substring(6, 12);
					d = DateTime.parse(birthday, format2); 
				}
				int age = Years.yearsBetween(d, now).getYears();
				po.setAge(age);
			}
		}
		
		EasyUiDataGridResult result = new EasyUiDataGridResult(totalRow, list);
		return result;
	}

	/**
	 * 用户画像详情的list
	 * @param page
	 * @param rows
	 * @param startDate
	 * @param endDate
	 * @param loginaccount
	 * @return
	 */
	@Override
	public EasyUiDataGridResult detailList(Integer page, Integer rows, Date startDate, Date endDate, String loginaccount) {
		DateTime sdt = new DateTime(startDate);
		DateTime edt = new DateTime(endDate);
		
		String sql = "SELECT count(1) FROM `accesslog` where time >= ? and date_format(time, '%Y-%m-%d') <= ? and username = ?";
		String[] sarr = new String[]{sdt.toString("yyyy-MM-dd"), edt.toString("yyyy-MM-dd"), loginaccount};
		int totalRow = jdbcTemplate.queryForInt(sql, sarr);
		
		int start = 0;
		int limit = 0;
		if(null == page && null == rows) {
			start = 0;
			limit = totalRow;
		}else {
			start = (page - 1) * rows;
			limit = rows;
		}
		
		String sql2 = "SELECT time, title detail, waittime duration FROM `accesslog` where time >= ? and date_format(time, '%Y-%m-%d') <= ? and username = ? order by time desc limit "+start+" , "+limit;
		List<YhhxDetail> list = jdbcTemplate.query(sql2, sarr, new BeanPropertyRowMapper<YhhxDetail>(YhhxDetail.class));
		
		EasyUiDataGridResult result = new EasyUiDataGridResult(totalRow, list);
		return result;	
	}

	/**
	 * app统计按天统计的list
	 */
	@Override
	public EasyUiDataGridResult dayApptjList(Integer page, Integer rows, Date startDate, Date endDate) {
		List<Apptj> reslist = new ArrayList<Apptj>();
		
		DateTime sdt = new DateTime(startDate);
		DateTime edt = new DateTime(endDate);
		int totalRow = Days.daysBetween(sdt, edt).getDays() + 1; 
		
		int start = 0;
		int end = 0;
		if(null != page && null != rows) {
			start = (page - 1) * rows;
			int limit = rows;
			if((start + limit) > totalRow) {
				end = totalRow;
			}else {
				end = start + limit;
			}
		}else {
			end = totalRow;
		}
		
		for(int i = start; i < end; i++) {
			Apptj xpo = new Apptj();
			xpo.setTime(edt.minusDays(i).toString("yyyy-MM-dd"));
			String sql = "select iosnew, andnew, iosqd, andqd, iosxzl, andxzl, iosgx, andgx, iossc, andsc from scheapp where time = '"+edt.minusDays(i).toString("yyyy-MM-dd")+"'";
			List<Integer[]> qlist = jdbcTemplate.query(sql, new RowMapper<Integer[]>(){
				@Override
				public Integer[] mapRow(ResultSet rs, int index) throws SQLException {
					Integer[] intarr = new Integer[10];
					intarr[0] = rs.getInt(1);
					intarr[1] = rs.getInt(2);
					intarr[2] = rs.getInt(3);
					intarr[3] = rs.getInt(4);
					intarr[4] = rs.getInt(5);
					intarr[5] = rs.getInt(6);
					intarr[6] = rs.getInt(7);
					intarr[7] = rs.getInt(8);
					intarr[8] = rs.getInt(9);
					intarr[9] = rs.getInt(10);
					return intarr;
				}});
		
			if(null != qlist && qlist.size() > 0) {
				Integer[] integers = qlist.get(0);
				if(null != integers[0]) {
					xpo.setIosnew(integers[0]);
				}else {
					xpo.setIosnew(0);
				}
				
				if(null != integers[1]) {
					xpo.setAndnew(integers[1]);
				}else {
					xpo.setAndnew(0);
				}
				
				if(null != integers[2]) {
					xpo.setIosqd(integers[2]);
				}else {
					xpo.setIosqd(0);
				}
				
				if(null != integers[3]) {
					xpo.setAndqd(integers[3]);
				}else {
					xpo.setAndqd(0);
				}
				
				if(null != integers[4]) {
					xpo.setIosxzl(integers[4]);
				}else {
					xpo.setIosxzl(0);
				}
				
				if(null != integers[5]) {
					xpo.setAndxzl(integers[5]);
				}else {
					xpo.setAndxzl(0);
				}
				
				if(null != integers[6]) {
					xpo.setIosgx(integers[6]);
				}else {
					xpo.setIosgx(0);
				}
				
				if(null != integers[7]) {
					xpo.setAndgx(integers[7]);
				}else {
					xpo.setAndgx(0);
				}
				
				if(null != integers[8]) {
					xpo.setIossc(integers[8]);
				}else {
					xpo.setIossc(0);
				}
				
				if(null != integers[9]) {
					xpo.setAndsc(integers[9]);
				}else {
					xpo.setAndsc(0);
				}
				
			}else {
				xpo.setIosnew(0);
				xpo.setAndnew(0);
				xpo.setIosqd(0);
				xpo.setAndqd(0);
				xpo.setIosxzl(0);
				xpo.setAndxzl(0);
				xpo.setIosgx(0);
				xpo.setAndgx(0);
				xpo.setIossc(0);
				xpo.setAndsc(0);
			}
			
			reslist.add(xpo);
		}
		
		EasyUiDataGridResult result = new EasyUiDataGridResult(totalRow, reslist);
		return result;
	}

	/**
	 * app统计按周统计的list
	 */
	@Override
	public EasyUiDataGridResult weekApptjList(Integer page, Integer rows, Date startDate, Date endDate) {
		List<Apptj> list = new ArrayList<Apptj>();
		
		DateTime ds = new DateTime(startDate);
		DateTime de = new DateTime(endDate);
		
		int minus = ds.getDayOfWeek() - 1;
		DateTime m = ds.minusDays(minus); // 得到刚刚好的本周的周一
		int p = de.getDayOfWeek();
		DateTime e = de.plusDays(7 - p);
		int weeks = Weeks.weeksBetween(m, e).getWeeks();
		int totalRow = weeks + 1;
		
		int start = 0;
		int end = 0;
		if(null != page && null != rows) {
			start = (page - 1) * rows;
			int limit = rows;
			if((start + limit) > totalRow) {
				end = totalRow;
			}else {
				end = start + limit;
			}
		}else {
			end = totalRow;
		}
		
		for(int i = start; i < end; i++) {
			Apptj xpo = new Apptj();
			String str = "第" + m.plusWeeks(i).getWeekOfWeekyear() + "周  " + m.plusWeeks(i).toString("yyyy-MM-dd") + " -- " + m.plusWeeks(i).plusDays(6).toString("yyyy-MM-dd");
			xpo.setTime(str);
			
			String sql = "select sum(iosnew), sum(andnew), sum(iosqd), sum(andqd), sum(iosxzl), sum(andxzl), sum(iosgx), sum(andgx), sum(iossc), sum(andsc) from scheapp where time >= ? and time < ? ";
			Object[] sarr = {m.plusWeeks(i).toString("yyyy-MM-dd"), m.plusWeeks(i + 1).toString("yyyy-MM-dd")};
			List<Integer[]> qlist = jdbcTemplate.query(sql, sarr, new RowMapper<Integer[]>(){
				@Override
				public Integer[] mapRow(ResultSet rs, int index) throws SQLException {
					Integer[] intarr = new Integer[10];
					intarr[0] = rs.getInt(1);
					intarr[1] = rs.getInt(2);
					intarr[2] = rs.getInt(3);
					intarr[3] = rs.getInt(4);
					intarr[4] = rs.getInt(5);
					intarr[5] = rs.getInt(6);
					intarr[6] = rs.getInt(7);
					intarr[7] = rs.getInt(8);
					intarr[8] = rs.getInt(9);
					intarr[9] = rs.getInt(10);
					return intarr;
				}});
			
			if(null != qlist && qlist.size() > 0) {
				Integer[] integers = qlist.get(0);
				if(null != integers[0]) {
					xpo.setIosnew(integers[0]);
				}else {
					xpo.setIosnew(0);
				}
				
				if(null != integers[1]) {
					xpo.setAndnew(integers[1]);
				}else {
					xpo.setAndnew(0);
				}
				
				if(null != integers[2]) {
					xpo.setIosqd(integers[2]);
				}else {
					xpo.setIosqd(0);
				}
				
				if(null != integers[3]) {
					xpo.setAndqd(integers[3]);
				}else {
					xpo.setAndqd(0);
				}
				
				if(null != integers[4]) {
					xpo.setIosxzl(integers[4]);
				}else {
					xpo.setIosxzl(0);
				}
				
				if(null != integers[5]) {
					xpo.setAndxzl(integers[5]);
				}else {
					xpo.setAndxzl(0);
				}
				
				if(null != integers[6]) {
					xpo.setIosgx(integers[6]);
				}else {
					xpo.setIosgx(0);
				}
				
				if(null != integers[7]) {
					xpo.setAndgx(integers[7]);
				}else {
					xpo.setAndgx(0);
				}
				
				if(null != integers[8]) {
					xpo.setIossc(integers[8]);
				}else {
					xpo.setIossc(0);
				}
				
				if(null != integers[9]) {
					xpo.setAndsc(integers[9]);
				}else {
					xpo.setAndsc(0);
				}
				
			}else {
				xpo.setIosnew(0);
				xpo.setAndnew(0);
				xpo.setIosqd(0);
				xpo.setAndqd(0);
				xpo.setIosxzl(0);
				xpo.setAndxzl(0);
				xpo.setIosgx(0);
				xpo.setAndgx(0);
				xpo.setIossc(0);
				xpo.setAndsc(0);
			}
			
			list.add(xpo);
		}
		
		EasyUiDataGridResult result = new EasyUiDataGridResult(totalRow, list);
		return result;
	}

	/**
	 * app统计按月统计的list
	 */
	@Override
	public EasyUiDataGridResult monthApptjList(Integer page, Integer rows, Date startDate, Date endDate) {
		List<Apptj> list = new ArrayList<Apptj>();
		DateTime sdt = new DateTime(startDate);
		DateTime sdtx = new DateTime(sdt.toString("yyyy-MM-01"));
		DateTime edt = new DateTime(endDate);
		DateTime edtx = new DateTime(edt.toString("yyyy-MM-01"));
		int totalRow = Months.monthsBetween(sdtx, edtx).getMonths() + 1;
		
		int start = 0;
		int end = 0;
		if(null != page && null != rows) {
			start = (page - 1) * rows;
			int limit = rows;
			if((start + limit) > totalRow) {
				end = totalRow;
			}else {
				end = start + limit;
			}
		}else {
			end = totalRow;
		}
		
		for(int i = start; i < end; i++) {
			Apptj xpo = new Apptj();
			xpo.setTime(edt.minusMonths(i).toString("yyyy-MM"));
			String sql = "select sum(iosnew), sum(andnew), sum(iosqd), sum(andqd), sum(iosxzl), sum(andxzl), sum(iosgx), sum(andgx), sum(iossc), sum(andsc) from scheapp where time >= ? and time < ? ";
			Object[] sarr = {edt.minusMonths(i).toString("yyyy-MM-01"), edt.minusMonths(i).plusMonths(1).toString("yyyy-MM-01")};
			List<Integer[]> qlist = jdbcTemplate.query(sql, sarr, new RowMapper<Integer[]>(){
				@Override
				public Integer[] mapRow(ResultSet rs, int index) throws SQLException {
					Integer[] intarr = new Integer[10];
					intarr[0] = rs.getInt(1);
					intarr[1] = rs.getInt(2);
					intarr[2] = rs.getInt(3);
					intarr[3] = rs.getInt(4);
					intarr[4] = rs.getInt(5);
					intarr[5] = rs.getInt(6);
					intarr[6] = rs.getInt(7);
					intarr[7] = rs.getInt(8);
					intarr[8] = rs.getInt(9);
					intarr[9] = rs.getInt(10);
					return intarr;
				}});
			
			if(null != qlist && qlist.size() > 0) {
				Integer[] integers = qlist.get(0);
				if(null != integers[0]) {
					xpo.setIosnew(integers[0]);
				}else {
					xpo.setIosnew(0);
				}
				
				if(null != integers[1]) {
					xpo.setAndnew(integers[1]);
				}else {
					xpo.setAndnew(0);
				}
				
				if(null != integers[2]) {
					xpo.setIosqd(integers[2]);
				}else {
					xpo.setIosqd(0);
				}
				
				if(null != integers[3]) {
					xpo.setAndqd(integers[3]);
				}else {
					xpo.setAndqd(0);
				}
				
				if(null != integers[4]) {
					xpo.setIosxzl(integers[4]);
				}else {
					xpo.setIosxzl(0);
				}
				
				if(null != integers[5]) {
					xpo.setAndxzl(integers[5]);
				}else {
					xpo.setAndxzl(0);
				}
				
				if(null != integers[6]) {
					xpo.setIosgx(integers[6]);
				}else {
					xpo.setIosgx(0);
				}
				
				if(null != integers[7]) {
					xpo.setAndgx(integers[7]);
				}else {
					xpo.setAndgx(0);
				}
				
				if(null != integers[8]) {
					xpo.setIossc(integers[8]);
				}else {
					xpo.setIossc(0);
				}
				
				if(null != integers[9]) {
					xpo.setAndsc(integers[9]);
				}else {
					xpo.setAndsc(0);
				}
				
			}else {
				xpo.setIosnew(0);
				xpo.setAndnew(0);
				xpo.setIosqd(0);
				xpo.setAndqd(0);
				xpo.setIosxzl(0);
				xpo.setAndxzl(0);
				xpo.setIosgx(0);
				xpo.setAndgx(0);
				xpo.setIossc(0);
				xpo.setAndsc(0);
			}
			
			list.add(xpo);
		}
		
		EasyUiDataGridResult result = new EasyUiDataGridResult(totalRow, list);
		return result;
	}

	/**
	 * app统计按年统计的list
	 */
	@Override
	public EasyUiDataGridResult yearApptjList(Integer page, Integer rows, Date startDate, Date endDate) {
		List<Apptj> list = new ArrayList<Apptj>();
		DateTime sdt = new DateTime(startDate);
		DateTime sdtx = new DateTime(sdt.toString("yyyy-01-01"));
		DateTime edt = new DateTime(endDate);
		DateTime edtx = new DateTime(edt.toString("yyyy-01-01"));
		int totalRow = Years.yearsBetween(sdtx, edtx).getYears() + 1;
		
		int start = 0;
		int end = 0;
		if(null != page && null != rows) {
			start = (page - 1) * rows;
			int limit = rows;
			if((start + limit) > totalRow) {
				end = totalRow;
			}else {
				end = start + limit;
			}
		}else {
			end = totalRow;
		}
		
		for(int i = start; i < end; i++) {
			Apptj xpo = new Apptj();
			xpo.setTime(edt.minusYears(i).toString("yyyy"));
			String sql = "select sum(iosnew), sum(andnew), sum(iosqd), sum(andqd), sum(iosxzl), sum(andxzl), sum(iosgx), sum(andgx), sum(iossc), sum(andsc) from scheapp where time >= ? and time < ? ";
			Object[] sarr = {edt.minusYears(i).toString("yyyy-01-01"), edt.minusYears(i).plusYears(1).toString("yyyy-01-01")};
			List<Integer[]> qlist = jdbcTemplate.query(sql, sarr, new RowMapper<Integer[]>(){
				@Override
				public Integer[] mapRow(ResultSet rs, int index) throws SQLException {
					Integer[] intarr = new Integer[10];
					intarr[0] = rs.getInt(1);
					intarr[1] = rs.getInt(2);
					intarr[2] = rs.getInt(3);
					intarr[3] = rs.getInt(4);
					intarr[4] = rs.getInt(5);
					intarr[5] = rs.getInt(6);
					intarr[6] = rs.getInt(7);
					intarr[7] = rs.getInt(8);
					intarr[8] = rs.getInt(9);
					intarr[9] = rs.getInt(10);
					return intarr;
				}});
			
			if(null != qlist && qlist.size() > 0) {
				Integer[] integers = qlist.get(0);
				if(null != integers[0]) {
					xpo.setIosnew(integers[0]);
				}else {
					xpo.setIosnew(0);
				}
				
				if(null != integers[1]) {
					xpo.setAndnew(integers[1]);
				}else {
					xpo.setAndnew(0);
				}
				
				if(null != integers[2]) {
					xpo.setIosqd(integers[2]);
				}else {
					xpo.setIosqd(0);
				}
				
				if(null != integers[3]) {
					xpo.setAndqd(integers[3]);
				}else {
					xpo.setAndqd(0);
				}
				
				if(null != integers[4]) {
					xpo.setIosxzl(integers[4]);
				}else {
					xpo.setIosxzl(0);
				}
				
				if(null != integers[5]) {
					xpo.setAndxzl(integers[5]);
				}else {
					xpo.setAndxzl(0);
				}
				
				if(null != integers[6]) {
					xpo.setIosgx(integers[6]);
				}else {
					xpo.setIosgx(0);
				}
				
				if(null != integers[7]) {
					xpo.setAndgx(integers[7]);
				}else {
					xpo.setAndgx(0);
				}
				
				if(null != integers[8]) {
					xpo.setIossc(integers[8]);
				}else {
					xpo.setIossc(0);
				}
				
				if(null != integers[9]) {
					xpo.setAndsc(integers[9]);
				}else {
					xpo.setAndsc(0);
				}
				
			}else {
				xpo.setIosnew(0);
				xpo.setAndnew(0);
				xpo.setIosqd(0);
				xpo.setAndqd(0);
				xpo.setIosxzl(0);
				xpo.setAndxzl(0);
				xpo.setIosgx(0);
				xpo.setAndgx(0);
				xpo.setIossc(0);
				xpo.setAndsc(0);
			}
			
			list.add(xpo);
		}
		
		EasyUiDataGridResult result = new EasyUiDataGridResult(totalRow, list);
		return result;
	}

	/**
	 * 版本安装量table的数据
	 * @param startDate
	 * @param endDate
	 * @param status
	 * @return
	 */
	@Override
	public List<Edition> EditionTableData(Date startDate, Date endDate, String status) {
		List<Edition> reslist = new ArrayList<Edition>();
		
		if ("day".equalsIgnoreCase(status)) {
			DateTime sdt = new DateTime(startDate);
			DateTime edt = new DateTime(endDate);
			String sql = "select time, version, num, type from scheappedition where time >= ? and time <= ? order by time desc, version asc ";
			Object[] sarr = {sdt.toString("yyyy-MM-dd"), edt.toString("yyyy-MM-dd")};
			reslist = jdbcTemplate.query(sql, sarr, new BeanPropertyRowMapper<Edition>(Edition.class));
		}else if ("week".equalsIgnoreCase(status)) {
			DateTime ds = new DateTime(startDate);
			DateTime de = new DateTime(endDate);
			int minus = ds.getDayOfWeek() - 1;
			DateTime m = ds.minusDays(minus); // 得到刚刚好的本周的周一
			int p = de.getDayOfWeek();
			DateTime e = de.plusDays(7 - p);
			int weeks = Weeks.weeksBetween(m, e).getWeeks();
			int totalRow = weeks + 1;
			
			for(int i = 0; i < totalRow; i++) {
				final String str = "第" + m.plusWeeks(i).getWeekOfWeekyear() + "周  " + m.plusWeeks(i).toString("yyyy-MM-dd") + " -- " + m.plusWeeks(i).plusDays(6).toString("yyyy-MM-dd");
				String sql = "SELECT type, version, sum(num) FROM `scheappedition` where time >= ? and time < ? group by type, version";
				Object[] sarr = {m.plusWeeks(i).toString("yyyy-MM-dd"), m.plusWeeks(i + 1).toString("yyyy-MM-dd")};
				List<Edition> list = jdbcTemplate.query(sql, sarr, new RowMapper<Edition>(){
					@Override
					public Edition mapRow(ResultSet rs, int index) throws SQLException {
						Edition po = new Edition();
						po.setType(rs.getString(1));
						po.setVersion(rs.getString(2));
						po.setNum(rs.getInt(3));
						po.setTime(str);
						return po;
					}
					
				});
				reslist.addAll(list);
			}
		} else if ("month".equalsIgnoreCase(status)) {
			DateTime sdt = new DateTime(startDate);
			DateTime sdtx = new DateTime(sdt.toString("yyyy-MM-01"));
			DateTime edt = new DateTime(endDate);
			DateTime edtx = new DateTime(edt.toString("yyyy-MM-01"));
			int totalRow = Months.monthsBetween(sdtx, edtx).getMonths() + 1;
			
			for(int i = 0; i < totalRow; i++) {
				final String str = edt.minusMonths(i).toString("yyyy-MM");
				String sql = "SELECT type, version, sum(num) FROM `scheappedition` where time >= ? and time < ? group by type, version";
				Object[] sarr = {edt.minusMonths(i).toString("yyyy-MM-01"), edt.minusMonths(i).plusMonths(1).toString("yyyy-MM-01")};
				List<Edition> list = jdbcTemplate.query(sql, sarr, new RowMapper<Edition>(){
					@Override
					public Edition mapRow(ResultSet rs, int index) throws SQLException {
						Edition po = new Edition();
						po.setType(rs.getString(1));
						po.setVersion(rs.getString(2));
						po.setNum(rs.getInt(3));
						po.setTime(str);
						return po;
					}
					
				});
				reslist.addAll(list);
			}
			
		} else if ("year".equalsIgnoreCase(status)) {
			DateTime sdt = new DateTime(startDate);
			DateTime sdtx = new DateTime(sdt.toString("yyyy-01-01"));
			DateTime edt = new DateTime(endDate);
			DateTime edtx = new DateTime(edt.toString("yyyy-01-01"));
			int totalRow = Years.yearsBetween(sdtx, edtx).getYears() + 1;
			
			for(int i = 0; i < totalRow; i++) {
				final String str = edt.minusYears(i).toString("yyyy");
				String sql = "SELECT type, version, sum(num) FROM `scheappedition` where time >= ? and time < ? group by type, version";
				Object[] sarr = {edt.minusYears(i).toString("yyyy-01-01"), edt.minusYears(i).plusYears(1).toString("yyyy-01-01")};
				List<Edition> list = jdbcTemplate.query(sql, sarr, new RowMapper<Edition>(){
					@Override
					public Edition mapRow(ResultSet rs, int index) throws SQLException {
						Edition po = new Edition();
						po.setType(rs.getString(1));
						po.setVersion(rs.getString(2));
						po.setNum(rs.getInt(3));
						po.setTime(str);
						return po;
					}
					
				});
				reslist.addAll(list);
			}
			
		}
		
		return reslist;
	}

	/**
	 * 装机详情的table的数据
	 * @param startDate
	 * @param endDate
	 * @param status
	 * @return
	 */
	@Override
	public List<Modelx> modelTableData(Date startDate, Date endDate, String status) {
		List<Modelx> reslist = new ArrayList<Modelx>();
		if ("day".equalsIgnoreCase(status)) {
			DateTime sdt = new DateTime(startDate);
			DateTime edt = new DateTime(endDate);
			String sql = "select time, model, addInstallNum, startNum from scheappmodel where time >= ? and time <= ? order by time desc, model desc";
			Object[] sarr = {sdt.toString("yyyy-MM-dd"), edt.toString("yyyy-MM-dd")};
			reslist = jdbcTemplate.query(sql, sarr, new RowMapper<Modelx>(){
				@Override
				public Modelx mapRow(ResultSet rs, int index) throws SQLException {
					Modelx po = new Modelx();
					po.setTime(rs.getString(1));
					po.setModel(rs.getString(2));
					po.setAddInstallNum(rs.getInt(3));
					po.setStartNum(rs.getInt(4));
					return po;
				}
				
			});
		}else if ("week".equalsIgnoreCase(status)) {
			DateTime ds = new DateTime(startDate);
			DateTime de = new DateTime(endDate);
			int minus = ds.getDayOfWeek() - 1;
			DateTime m = ds.minusDays(minus); // 得到刚刚好的本周的周一
			int p = de.getDayOfWeek();
			DateTime e = de.plusDays(7 - p);
			int weeks = Weeks.weeksBetween(m, e).getWeeks();
			int totalRow = weeks + 1;
			
			for(int i = 0; i < totalRow; i++) {
				final String str = "第" + m.plusWeeks(i).getWeekOfWeekyear() + "周  " + m.plusWeeks(i).toString("yyyy-MM-dd") + " -- " + m.plusWeeks(i).plusDays(6).toString("yyyy-MM-dd");
				String sql = "SELECT model, sum(addInstallNum), sum(startNum) FROM `scheappmodel` where time >= ? and time < ? group by model order by model desc";
				Object[] sarr = {m.plusWeeks(i).toString("yyyy-MM-dd"), m.plusWeeks(i + 1).toString("yyyy-MM-dd")};
				List<Modelx> list = jdbcTemplate.query(sql, sarr, new RowMapper<Modelx>(){
					@Override
					public Modelx mapRow(ResultSet rs, int index) throws SQLException {
						Modelx po = new Modelx();
						po.setModel(rs.getString(1));
						po.setAddInstallNum(rs.getInt(2));
						po.setStartNum(rs.getInt(3));
						po.setTime(str);
						return po;
					}
					
				});
				reslist.addAll(list);
			}
		} else if ("month".equalsIgnoreCase(status)) {
			DateTime sdt = new DateTime(startDate);
			DateTime sdtx = new DateTime(sdt.toString("yyyy-MM-01"));
			DateTime edt = new DateTime(endDate);
			DateTime edtx = new DateTime(edt.toString("yyyy-MM-01"));
			int totalRow = Months.monthsBetween(sdtx, edtx).getMonths() + 1;
			
			for(int i = 0; i < totalRow; i++) {
				final String str = edt.minusMonths(i).toString("yyyy-MM");
				String sql = "SELECT model, sum(addInstallNum), sum(startNum) FROM `scheappmodel` where time >= ? and time < ? group by model order by model desc";
				Object[] sarr = {edt.minusMonths(i).toString("yyyy-MM-01"), edt.minusMonths(i).plusMonths(1).toString("yyyy-MM-01")};
				List<Modelx> list = jdbcTemplate.query(sql, sarr, new RowMapper<Modelx>(){
					@Override
					public Modelx mapRow(ResultSet rs, int index) throws SQLException {
						Modelx po = new Modelx();
						po.setModel(rs.getString(1));
						po.setAddInstallNum(rs.getInt(2));
						po.setStartNum(rs.getInt(3));
						po.setTime(str);
						return po;
					}
					
				});
				reslist.addAll(list);
			}
			
		} else if ("year".equalsIgnoreCase(status)) {
			DateTime sdt = new DateTime(startDate);
			DateTime sdtx = new DateTime(sdt.toString("yyyy-01-01"));
			DateTime edt = new DateTime(endDate);
			DateTime edtx = new DateTime(edt.toString("yyyy-01-01"));
			int totalRow = Years.yearsBetween(sdtx, edtx).getYears() + 1;
			
			for(int i = 0; i < totalRow; i++) {
				final String str = edt.minusYears(i).toString("yyyy");
				String sql = "SELECT model, sum(addInstallNum), sum(startNum) FROM `scheappmodel` where time >= ? and time < ? group by model order by model desc";
				Object[] sarr = {edt.minusYears(i).toString("yyyy-01-01"), edt.minusYears(i).plusYears(1).toString("yyyy-01-01")};
				List<Modelx> list = jdbcTemplate.query(sql, sarr, new RowMapper<Modelx>(){
					@Override
					public Modelx mapRow(ResultSet rs, int index) throws SQLException {
						Modelx po = new Modelx();
						po.setModel(rs.getString(1));
						po.setAddInstallNum(rs.getInt(2));
						po.setStartNum(rs.getInt(3));
						po.setTime(str);
						return po;
					}
					
				});
				reslist.addAll(list);
			}
			
		}
		
		if(reslist != null && reslist.size() > 0) {
			for (Modelx modelx : reslist) {
				String model = modelx.getModel();
				switch(model) {
					case "iPhone1,1":
						modelx.setModelName("iPhone 2G");
						break;
					case "iPhone1,2":
						modelx.setModelName("iPhone 2G");
						break;
					case "iPhone2,1":
						modelx.setModelName("iPhone 3GS");
						break;
					case "iPhone3,1":
						modelx.setModelName("iPhone 4");
						break;
					case "iPhone3,2":
						modelx.setModelName("iPhone 4");
						break;
					case "iPhone3,3":
						modelx.setModelName("iPhone 4");
						break;
					case "iPhone4,1":
						modelx.setModelName("iPhone 4S");
						break;
					case "iPhone5,1":
						modelx.setModelName("iPhone 5");
						break;
					case "iPhone5,2":
						modelx.setModelName("iPhone 5");
						break;
					case "iPhone5,3":
						modelx.setModelName("iPhone 5c");
						break;
					case "iPhone5,4":
						modelx.setModelName("iPhone 5c");
						break;
					case "iPhone6,1":
						modelx.setModelName("iPhone 5s");
						break;
					case "iPhone6,2":
						modelx.setModelName("iPhone 5s");
						break;
					case "iPhone7,1":
						modelx.setModelName("iPhone 6 Plus");
						break;
					case "iPhone7,2":
						modelx.setModelName("iPhone 6");
						break;
					case "iPhone8,1":
						modelx.setModelName("iPhone 6s");
						break;
					case "iPhone8,2":
						modelx.setModelName("iPhone 6s Plus");
						break;
					case "iPhone8,4":
						modelx.setModelName("iPhone SE");
						break;
					case "iPhone9,1":
						modelx.setModelName("iPhone 7");
						break;
					case "iPhone9,2":
						modelx.setModelName("iPhone 7 Plus");
						break;
					case "iPod1,1":
						modelx.setModelName("iPod Touch 1G");
						break;
					case "iPod2,1":
						modelx.setModelName("iPod Touch 2G");
						break;
					case "iPod3,1":
						modelx.setModelName("iPod Touch 3G");
						break;
					case "iPod4,1":
						modelx.setModelName("iPod Touch 4G");
						break;
					case "iPod5,1":
						modelx.setModelName("iPod Touch 5G");
						break;
					case "iPad1,1":
						modelx.setModelName("iPad 1G");
						break;
					case "iPad2,1":
						modelx.setModelName("iPad 2");
						break;
					case "iPad2,2":
						modelx.setModelName("iPad 2");
						break;
					case "iPad2,3":
						modelx.setModelName("iPad 2");
						break;
					case "iPad2,4":
						modelx.setModelName("iPad 2");
						break;
					case "iPad2,5":
						modelx.setModelName("iPad Mini 1G");
						break;
					case "iPad2,6":
						modelx.setModelName("iPad Mini 1G");
						break;
					case "iPad2,7":
						modelx.setModelName("iPad Mini 1G");
						break;
					case "iPad3,1":
						modelx.setModelName("iPad 3");
						break;
					case "iPad3,2":
						modelx.setModelName("iPad 3");
						break;
					case "iPad3,3":
						modelx.setModelName("iPad 3");
						break;
					case "iPad3,4":
						modelx.setModelName("iPad 4");
						break;
					case "iPad3,5":
						modelx.setModelName("iPad 4");
						break;
					case "iPad3,6":
						modelx.setModelName("iPad 4");
						break;
					case "iPad4,1":
						modelx.setModelName("iPad Air");
						break;
					case "iPad4,2":
						modelx.setModelName("iPad Air");
						break;
					case "iPad4,3":
						modelx.setModelName("iPad Air");
						break;
					case "iPad4,4":
						modelx.setModelName("iPad Mini 2G");
						break;
					case "iPad4,5":
						modelx.setModelName("iPad Mini 2G");
						break;
					case "iPad4,6":
						modelx.setModelName("iPad Mini 2G");
						break;
					case "i386":
						modelx.setModelName("iPhone Simulator");
						break;
					case "x86_64":
						modelx.setModelName("iPhone Simulator");
						break;
				}
			}
		}
		
		return reslist;
	}

	/**
	 * 地域分析条形图的数据
	 */
	@Override
	public List<DyfxPoExt> dyfxData(Date startDate, Date endDate, String status) {
		List<DyfxPoExt> reslist = new ArrayList<DyfxPoExt>();
		
		for(int i = 0; i <= 1; i++) {
			int type = i;
			
			if ("day".equalsIgnoreCase(status)) {
				DateTime sdt = new DateTime(startDate);
				DateTime edt = new DateTime(endDate);
				String sql = "SELECT sum(num) value, location name, '#a5c2d5' color FROM `schelocation` where time >= ? and time <= ? and type = "+type+" group by location order by sum(num) desc limit 10";
				Object[] sarr = {sdt.toString("yyyy-MM-dd"), edt.toString("yyyy-MM-dd")};
				List<DyfxPoExt> list = jdbcTemplate.query(sql, sarr, new BeanPropertyRowMapper<DyfxPoExt>(DyfxPoExt.class));
				reslist.addAll(list);
				
			}else if ("week".equalsIgnoreCase(status)) {
				DateTime ds = new DateTime(startDate);
				DateTime de = new DateTime(endDate);
				int minus = ds.getDayOfWeek() - 1;
				DateTime m = ds.minusDays(minus); // 得到刚刚好的本周的周一
				int p = de.getDayOfWeek();
				DateTime e = de.plusDays(7 - p);
				String sql = "SELECT sum(num) value, location name, '#a5c2d5' color FROM `schelocation` where time >= ? and time <= ? and type = "+type+" group by location order by sum(num) desc limit 10";
				Object[] sarr = {m.toString("yyyy-MM-dd"), e.toString("yyyy-MM-dd")};
				List<DyfxPoExt> list = jdbcTemplate.query(sql, sarr, new BeanPropertyRowMapper<DyfxPoExt>(DyfxPoExt.class));
				reslist.addAll(list);
				
			}else if ("month".equalsIgnoreCase(status)) {
				DateTime sdt = new DateTime(startDate);
				DateTime edt = new DateTime(endDate);
				String sql = "SELECT sum(num) value, location name, '#a5c2d5' color FROM `schelocation` where time >= ? and time < ? and type = "+type+" group by location order by sum(num) desc limit 10";
				Object[] sarr = {sdt.toString("yyyy-MM-01"), edt.plusMonths(1).toString("yyyy-MM-01")};
				List<DyfxPoExt> list = jdbcTemplate.query(sql, sarr, new BeanPropertyRowMapper<DyfxPoExt>(DyfxPoExt.class));
				reslist.addAll(list);
				
			}else if ("year".equalsIgnoreCase(status)) {
				DateTime sdt = new DateTime(startDate);
				DateTime edt = new DateTime(endDate);
				String sql = "SELECT sum(num) value, location name, '#a5c2d5' color FROM `schelocation` where time >= ? and time < ? and type = "+type+" group by location order by sum(num) desc limit 10";
				Object[] sarr = {sdt.toString("yyyy-01-01"), edt.plusYears(1).toString("yyyy-01-01")};
				List<DyfxPoExt> list = jdbcTemplate.query(sql, sarr, new BeanPropertyRowMapper<DyfxPoExt>(DyfxPoExt.class));
				reslist.addAll(list);
			}
		}
		
		List<DyfxPoExt> finlist = new ArrayList<DyfxPoExt>();
		
		if(null == reslist || reslist.size() == 0) {
			DyfxPoExt ele = new DyfxPoExt();
			ele.setValue(0);
			ele.setName("北京市");
			ele.setColor("#a5c2d5");
			finlist.add(ele);
		}else {
			
			Collections.sort(reslist, new Comparator<DyfxPoExt>(){
				@Override
				public int compare(DyfxPoExt o1, DyfxPoExt o2) {
					Integer value1 = o1.getValue();
					Integer value2 = o2.getValue();
					if(value1 != null && value2 != null) {
						if(value1 > value2) {
							return 1;
						}else if(value1 < value2) {
							return -1;
						}else {
							return 0;
						}
					}else if(value1 == null) {
						return -1;
					}else {
						return 1;
					}
				}
			});
			
			HashMap<String, Integer> map = new HashMap<String, Integer>();
			for (DyfxPoExt dyfxPoExt : reslist) {
				String name = dyfxPoExt.getName();
				Integer value = dyfxPoExt.getValue();
				map.put(name, value);
			}
			
			Set<String> keySet = map.keySet();
			for (String str : keySet) {
				DyfxPoExt ele = new DyfxPoExt();
				ele.setColor("#a5c2d5");
				ele.setName(str);
				ele.setValue(map.get(str));
				finlist.add(ele);
			}
			
			Collections.sort(finlist, new Comparator<DyfxPoExt>(){
				@Override
				public int compare(DyfxPoExt o2, DyfxPoExt o1) {
					Integer value1 = o1.getValue();
					Integer value2 = o2.getValue();
					if(value1 != null && value2 != null) {
						if(value1 > value2) {
							return 1;
						}else if(value1 < value2) {
							return -1;
						}else {
							return 0;
						}
					}else if(value1 == null) {
						return -1;
					}else {
						return 1;
					}
				}
			});
		}
		
		if(finlist.size() >= 10) {
			return finlist.subList(0, 10);
		}
		return finlist;
	}

	/**
	 * 地域分析省的table数据
	 * @param startDate
	 * @param endDate
	 * @param status
	 * @return
	 */
	@Override
	public List<Dyfx> dyfxTableData(Date startDate, Date endDate, String status, String type) {
		List<Dyfx> reslist = new ArrayList<Dyfx>();
		
		if ("day".equalsIgnoreCase(status)) {
			DateTime sdt = new DateTime(startDate);
			DateTime edt = new DateTime(endDate);
			String sql = "SELECT time, num, location FROM `schelocation` where type = "+type+" and time >= ? and time <= ? order by time desc, num desc";
			Object[] sarr = {sdt.toString("yyyy-MM-dd"), edt.toString("yyyy-MM-dd")};
			reslist = jdbcTemplate.query(sql, sarr, new RowMapper<Dyfx>(){
				@Override
				public Dyfx mapRow(ResultSet rs, int index) throws SQLException {
					Dyfx po = new Dyfx();
					po.setTime(rs.getString(1));
					po.setNum(rs.getInt(2));
					po.setLocation(rs.getString(3));
					return po;
				}
			});
		}else if ("week".equalsIgnoreCase(status)) {
			DateTime ds = new DateTime(startDate);
			DateTime de = new DateTime(endDate);
			int minus = ds.getDayOfWeek() - 1;
			DateTime m = ds.minusDays(minus); // 得到刚刚好的本周的周一
			int p = de.getDayOfWeek();
			DateTime e = de.plusDays(7 - p);
			int weeks = Weeks.weeksBetween(m, e).getWeeks();
			int totalRow = weeks + 1;
			
			for(int i = 0; i < totalRow; i++) {
				final String str = "第" + m.plusWeeks(i).getWeekOfWeekyear() + "周  " + m.plusWeeks(i).toString("yyyy-MM-dd") + " -- " + m.plusWeeks(i).plusDays(6).toString("yyyy-MM-dd");
				String sql = "SELECT sum(num) ss, location FROM `schelocation` where type = "+type+" and time >= ? and time < ? group by location order by ss desc";
				Object[] sarr = {m.plusWeeks(i).toString("yyyy-MM-dd"), m.plusWeeks(i + 1).toString("yyyy-MM-dd")};
				List<Dyfx> list = jdbcTemplate.query(sql, sarr, new RowMapper<Dyfx>(){
					@Override
					public Dyfx mapRow(ResultSet rs, int index) throws SQLException {
						Dyfx po = new Dyfx();
						po.setTime(str);
						po.setNum(rs.getInt(1));
						po.setLocation(rs.getString(2));
						return po;
					}
				});
				reslist.addAll(list);
			}
		} else if ("month".equalsIgnoreCase(status)) {
			DateTime sdt = new DateTime(startDate);
			DateTime sdtx = new DateTime(sdt.toString("yyyy-MM-01"));
			DateTime edt = new DateTime(endDate);
			DateTime edtx = new DateTime(edt.toString("yyyy-MM-01"));
			int totalRow = Months.monthsBetween(sdtx, edtx).getMonths() + 1;
			
			for(int i = 0; i < totalRow; i++) {
				final String str = edt.minusMonths(i).toString("yyyy-MM");
				String sql = "SELECT sum(num) ss, location FROM `schelocation` where type = "+type+" and time >= ? and time < ? group by location order by ss desc";
				Object[] sarr = {edt.minusMonths(i).toString("yyyy-MM-01"), edt.minusMonths(i).plusMonths(1).toString("yyyy-MM-01")};
				List<Dyfx> list = jdbcTemplate.query(sql, sarr, new RowMapper<Dyfx>(){
					@Override
					public Dyfx mapRow(ResultSet rs, int index) throws SQLException {
						Dyfx po = new Dyfx();
						po.setTime(str);
						po.setNum(rs.getInt(1));
						po.setLocation(rs.getString(2));
						return po;
					}
				});
				reslist.addAll(list);
			}
		} else if ("year".equalsIgnoreCase(status)) {
			DateTime sdt = new DateTime(startDate);
			DateTime sdtx = new DateTime(sdt.toString("yyyy-01-01"));
			DateTime edt = new DateTime(endDate);
			DateTime edtx = new DateTime(edt.toString("yyyy-01-01"));
			int totalRow = Years.yearsBetween(sdtx, edtx).getYears() + 1;
			
			for(int i = 0; i < totalRow; i++) {
				final String str = edt.minusYears(i).toString("yyyy");
				String sql = "SELECT sum(num) ss, location FROM `schelocation` where type = "+type+" and time >= ? and time < ? group by location order by ss desc";
				Object[] sarr = {edt.minusYears(i).toString("yyyy-01-01"), edt.minusYears(i).plusYears(1).toString("yyyy-01-01")};
				List<Dyfx> list = jdbcTemplate.query(sql, sarr, new RowMapper<Dyfx>(){
					@Override
					public Dyfx mapRow(ResultSet rs, int index) throws SQLException {
						Dyfx po = new Dyfx();
						po.setTime(str);
						po.setNum(rs.getInt(1));
						po.setLocation(rs.getString(2));
						return po;
					}
				});
				reslist.addAll(list);
			}
		} 
		
		
		return reslist;
	}
	
}
