package cn.gov.nlc.service.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Months;
import org.joda.time.Weeks;
import org.joda.time.Years;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import cn.gov.nlc.pojo.GgwDetailPo;
import cn.gov.nlc.pojo.Hotword;
import cn.gov.nlc.service.NlcStatisticServicex;
import cn.gov.nlc.vo.DetailPo;
import cn.gov.nlc.vo.EasyUiDataGridResult;
import cn.gov.nlc.vo.EasyUiDataGridResultFoot;
import cn.gov.nlc.vo.PersonPo;
import cn.gov.nlc.vo.WjDetailPo;
import cn.gov.nlc.vo.WjYmfwPo;
import cn.gov.nlc.vo.YmfwPo;
import cn.gov.nlc.vo.YmfwfxPo;

@Service
public class NlcStatisticServicexImpl implements NlcStatisticServicex{

	@Autowired
	private JdbcTemplate jdbcTemplate;


	/**
	 * 页面访问量按天统计的pv
	 */
	@Override
	public List<Integer> dayymfwpv(Date startDate, Date endDate) {
		DateTime dstartDate = new DateTime(startDate);
		DateTime dendDate = new DateTime(endDate);
		int days = Days.daysBetween(dstartDate, dendDate).getDays(); 
		List<Integer> flowList = new ArrayList<Integer>();
		
		if(days <= 55) {
			String sql = "select sum(pv) from schepageview where time = ";
			for(int i = 0; i < days + 1; i++) {
				int res1 = jdbcTemplate.queryForInt(sql + "\"" + dstartDate.toString("yyyy-MM-dd") + "\"");
				flowList.add(res1);
				dstartDate = dstartDate.plusDays(1);
			}
		}else {
			String sql2 = "select sum(pv) from schepageview ";
			int base = ((Double)Math.ceil(days / 55.0)).intValue();
			for(int i = 0; i < (days / base) + 1; i++) {
				int res2 = jdbcTemplate.queryForInt(sql2 + "where time >= \"" + dstartDate.toString("yyyy-MM-dd") + "\" and time < \"" + dstartDate.plusDays(base).toString("yyyy-MM-dd") + "\"");
				flowList.add(res2);
				dstartDate = dstartDate.plusDays(base);
			}
		}
		
		return flowList;
	}
	
	/**
	 * 页面访问量按天统计的uv
	 */
	@Override
	public List<Integer> dayymfwuv(Date startDate, Date endDate) {
		DateTime dstartDate = new DateTime(startDate);
		DateTime dendDate = new DateTime(endDate);
		int days = Days.daysBetween(dstartDate, dendDate).getDays(); 
		List<Integer> flowList = new ArrayList<Integer>();
		
		if(days <= 55) {
			String sql = "select sum(uv) from schepageview where time = ";
			for(int i = 0; i < days + 1; i++) {
				int res1 = jdbcTemplate.queryForInt(sql + "\"" + dstartDate.toString("yyyy-MM-dd") + "\"");
				flowList.add(res1);
				dstartDate = dstartDate.plusDays(1);
			}
		}else {
			String sql2 = "select sum(uv) from schepageview ";
			int base = ((Double)Math.ceil(days / 55.0)).intValue();
			for(int i = 0; i < (days / base) + 1; i++) {
				int res2 = jdbcTemplate.queryForInt(sql2 + "where time >= \"" + dstartDate.toString("yyyy-MM-dd") + "\" and time < \"" + dstartDate.plusDays(base).toString("yyyy-MM-dd") + "\"");
				flowList.add(res2);
				dstartDate = dstartDate.plusDays(base);
			}
		}
		
		return flowList;
	}
	
	/**
	 * 按天统计的pv、uv做foot时求和用
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	private Integer[] daypvuvsum(Date startDate, Date endDate, String tableName) {
		DateTime sdt = new DateTime(startDate);
		DateTime edt = new DateTime(endDate);
		
		String sql1 = "select sum(pv), sum(uv) from "+tableName+" where time >= '"+sdt.toString("yyyy-MM-dd")+"' and time <= '" +edt.toString("yyyy-MM-dd") + "'"; 
		List<YmfwPo> list = jdbcTemplate.query(sql1, new RowMapper<YmfwPo>(){

			@Override
			public YmfwPo mapRow(ResultSet rs, int index) throws SQLException {
				YmfwPo po = new YmfwPo();
				po.setPv(rs.getInt(1));
				po.setUv(rs.getInt(2));
				return po;
			}
		});
		
		Integer[] res = new Integer[]{0, 0};
		if(list != null && list.size() > 0) {
			res[0] = list.get(0).getPv();
			res[1] = list.get(0).getUv();
		}
		
		return res;
	}

	/**
	 * 页面访问量按天的list数据
	 * @param page
	 * @param rows
	 * @return
	 */
	@Override
	public EasyUiDataGridResult dayymfwList(Integer page, Integer rows, Date startDate, Date endDate, String tableName) {
		List<YmfwPo> list = new ArrayList<YmfwPo>();
		DateTime sdt = new DateTime(startDate);
		DateTime edt = new DateTime(endDate);
		
		int totalRow = Days.daysBetween(sdt, edt).getDays() + 1; 
		
		int start = (page - 1) * rows;
		int limit = rows;
		
		int end = 0;
		if((start + limit) > totalRow) {
			end = totalRow;
		}else {
			end = start + limit;
		}
		
		for(int i = start; i < end; i++) {
			YmfwPo xpo = new YmfwPo();
			xpo.setTime(edt.minusDays(i).toString("yyyy-MM-dd"));
			String sql = "select pv, uv from "+tableName+" where time = '"+edt.minusDays(i).toString("yyyy-MM-dd")+"'";
			List<Integer[]> qlist = jdbcTemplate.query(sql, new RowMapper<Integer[]>(){
				@Override
				public Integer[] mapRow(ResultSet rs, int index) throws SQLException {
					Integer[] intarr = new Integer[2];
					intarr[0] = rs.getInt(1);
					intarr[1] = rs.getInt(2);
					return intarr;
				}});
			
			if(null != qlist && qlist.size() > 0) {
				Integer[] integers = qlist.get(0);
				if(null != integers[0]) {
					xpo.setPv(integers[0]);
				}else {
					xpo.setPv(0);
				}
				
				if(null != integers[1]) {
					xpo.setUv(integers[1]);
				}else {
					xpo.setUv(0);
				}
			}else {
				xpo.setPv(0);
				xpo.setUv(0);
			}
			
			list.add(xpo);
		}
		
		Integer[] intarr = daypvuvsum(startDate, endDate, tableName);
		Map<String, String> map = new HashMap<String, String>();
		map.put("time", "总计");
		map.put("pv", intarr[0] + "");
		map.put("uv", intarr[1] + "");
		List<Map<String, String>> list2 = new ArrayList<Map<String, String>>();
		list2.add(map);
		EasyUiDataGridResultFoot result = new EasyUiDataGridResultFoot(totalRow, list, list2);
		return result;
	}

	/**
	 * 页面访问导出每天的
	 */
	@Override
	public List<YmfwPo> dayymfwExport(Date startDate, Date endDate, String tableName) {
		List<YmfwPo> list = new ArrayList<YmfwPo>();
		DateTime sdt = new DateTime(startDate);
		DateTime edt = new DateTime(endDate);
		int totalRow = Days.daysBetween(sdt, edt).getDays() + 1; 
		
		Integer pvsum = new Integer(0);
		Integer uvsum = new Integer(0);
		
		for(int i = 0; i < totalRow; i++) {
			YmfwPo xpo = new YmfwPo();
			xpo.setTime(edt.minusDays(i).toString("yyyy-MM-dd"));
			String sql = "select pv, uv from "+tableName+" where time = '"+edt.minusDays(i).toString("yyyy-MM-dd")+"'";
			List<Integer[]> qlist = jdbcTemplate.query(sql, new RowMapper<Integer[]>(){
				@Override
				public Integer[] mapRow(ResultSet rs, int index) throws SQLException {
					Integer[] intarr = new Integer[2];
					intarr[0] = rs.getInt(1);
					intarr[1] = rs.getInt(2);
					return intarr;
				}});
			
			if(null != qlist && qlist.size() > 0) {
				Integer[] integers = qlist.get(0);
				if(null != integers[0]) {
					xpo.setPv(integers[0]);
					pvsum += integers[0];
				}else {
					xpo.setPv(0);
					pvsum += 0;
				}
				
				if(null != integers[1]) {
					xpo.setUv(integers[1]);
					uvsum += integers[1];
				}else {
					xpo.setUv(0);
					uvsum += 0;
				}
			}else {
				xpo.setPv(0);
				xpo.setUv(0);
				pvsum += 0;
				uvsum += 0;
			}
			
			list.add(xpo);
		}
		
		YmfwPo xpo = new YmfwPo();
		xpo.setTime("总计");
		xpo.setPv(pvsum);
		xpo.setUv(uvsum);
		list.add(xpo);
		
		return list;
	}
	
	//===============按月========================
	
	/**
	 * 页面访问量按月统计的pv
	 */
	@Override
	public List<Integer> monymfwpv(Date startDate, Date endDate) {
		DateTime dstartDate = new DateTime(startDate);
		DateTime dstartDatex = new DateTime(dstartDate.toString("yyyy-MM-01"));
		DateTime dendDate = new DateTime(endDate);
		DateTime dendDatex = new DateTime(dendDate.toString("yyyy-MM-01"));
		int months = Months.monthsBetween(dstartDatex, dendDatex).getMonths();
		List<Integer> flowList = new ArrayList<Integer>();
		
		if(months <= 55) {
			String sql = "select sum(pv) from schepageview where time >= ";
			for(int i = 0; i < months + 1; i++) {
				int res1 = jdbcTemplate.queryForInt(sql + "\"" + dstartDate.toString("yyyy-MM-01") + "\" and time < \"" + dstartDate.plusMonths(1).toString("yyyy-MM-01") + "\"");
				flowList.add(res1);
				dstartDate = dstartDate.plusMonths(1);
			}
		}else {
			String sql2 = "select sum(pv) from schepageview ";
			int base = ((Double)Math.ceil(months / 55.0)).intValue();
			for(int i = 0; i < (months / base) + 1; i++) {
				int res2 = jdbcTemplate.queryForInt(sql2 + "where time >= \"" + dstartDate.toString("yyyy-MM-01") + "\" and time < \"" + dstartDate.plusMonths(base).toString("yyyy-MM-01") + "\"");
				flowList.add(res2);
				dstartDate = dstartDate.plusMonths(base);
			}
		}
		
		return flowList;
	}
	
	/**
	 * 页面访问量按月统计的uv
	 */
	@Override
	public List<Integer> monymfwuv(Date startDate, Date endDate) {
		DateTime dstartDate = new DateTime(startDate);
		DateTime dstartDatex = new DateTime(dstartDate.toString("yyyy-MM-01"));
		DateTime dendDate = new DateTime(endDate);
		DateTime dendDatex = new DateTime(dendDate.toString("yyyy-MM-01"));
		int months = Months.monthsBetween(dstartDatex, dendDatex).getMonths();
		List<Integer> flowList = new ArrayList<Integer>();
		
		if(months <= 55) {
			String sql = "select sum(uv) from schepageview where time >= ";
			for(int i = 0; i < months + 1; i++) {
				int res1 = jdbcTemplate.queryForInt(sql + "\"" + dstartDate.toString("yyyy-MM-01") + "\" and time < \"" + dstartDate.plusMonths(1).toString("yyyy-MM-01") + "\"");
				flowList.add(res1);
				dstartDate = dstartDate.plusMonths(1);
			}
		}else {
			String sql2 = "select sum(uv) from schepageview ";
			int base = ((Double)Math.ceil(months / 55.0)).intValue();
			for(int i = 0; i < (months / base) + 1; i++) {
				int res2 = jdbcTemplate.queryForInt(sql2 + "where time >= \"" + dstartDate.toString("yyyy-MM-01") + "\" and time < \"" + dstartDate.plusMonths(base).toString("yyyy-MM-01") + "\"");
				flowList.add(res2);
				dstartDate = dstartDate.plusMonths(base);
			}
		}
		
		return flowList;
	}

	/**
	 * 页面访问量按月的list数据
	 * @param page
	 * @param rows
	 * @return
	 */
	@Override
	public EasyUiDataGridResult monymfwList(Integer page, Integer rows, Date startDate, Date endDate, String tableName) {
		List<YmfwPo> list = new ArrayList<YmfwPo>();
		DateTime sdt = new DateTime(startDate);
		DateTime sdtx = new DateTime(sdt.toString("yyyy-MM-01"));
		DateTime edt = new DateTime(endDate);
		DateTime edtx = new DateTime(edt.toString("yyyy-MM-01"));
		int totalRow = Months.monthsBetween(sdtx, edtx).getMonths() + 1;
		
		int start = (page - 1) * rows;
		int limit = rows;
		
		int end = 0;
		if((start + limit) > totalRow) {
			end = totalRow;
		}else {
			end = start + limit;
		}
		
		for(int i = start; i < end; i++) {
			YmfwPo xpo = new YmfwPo();
			xpo.setTime(edt.minusMonths(i).toString("yyyy-MM"));
			
			String sql = "select sum(pv), sum(uv) from "+tableName+" where time >= '"+edt.minusMonths(i).toString("yyyy-MM-01")+"' and time < '" + edt.minusMonths(i).plusMonths(1).toString("yyyy-MM-01") + "'";
			List<Integer[]> qlist = jdbcTemplate.query(sql, new RowMapper<Integer[]>(){
				@Override
				public Integer[] mapRow(ResultSet rs, int index) throws SQLException {
					Integer[] intarr = new Integer[2];
					intarr[0] = rs.getInt(1);
					intarr[1] = rs.getInt(2);
					return intarr;
				}});
			
			Integer[] integers = qlist.get(0);
			xpo.setPv(integers[0]);
			xpo.setUv(integers[1]);
			list.add(xpo);
		}
		
		Integer[] intarr = monpvuvsum(startDate, endDate, tableName);
		Map<String, String> map = new HashMap<String, String>();
		map.put("time", "总计");
		map.put("pv", intarr[0] + "");
		map.put("uv", intarr[1] + "");
		List<Map<String, String>> list2 = new ArrayList<Map<String, String>>();
		list2.add(map);
		EasyUiDataGridResultFoot result = new EasyUiDataGridResultFoot(totalRow, list, list2);
		return result;
	}
	
	/**
	 * 按月统计的pv、uv做foot时求和用
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	private Integer[] monpvuvsum(Date startDate, Date endDate, String tableName) {
		DateTime sdt = new DateTime(startDate);
		DateTime edt = new DateTime(endDate);
		
		String sql1 = "select sum(pv), sum(uv) from "+tableName+" where time >= '"+sdt.toString("yyyy-MM-01")+"' and time < '" +edt.plusMonths(1).toString("yyyy-MM-01") + "'"; 
		List<YmfwPo> list = jdbcTemplate.query(sql1, new RowMapper<YmfwPo>(){
			@Override
			public YmfwPo mapRow(ResultSet rs, int index) throws SQLException {
				YmfwPo po = new YmfwPo();
				po.setPv(rs.getInt(1));
				po.setUv(rs.getInt(2));
				return po;
			}
		});
		
		Integer[] res = new Integer[]{0, 0};
		if(list != null && list.size() > 0) {
			res[0] = list.get(0).getPv();
			res[1] = list.get(0).getUv();
		}
		
		return res;
	}
	
	/**
	 * 页面访问导出每月的
	 */
	@Override
	public List<YmfwPo> monymfwExport(Date startDate, Date endDate, String tableName) {
		List<YmfwPo> list = new ArrayList<YmfwPo>();
		DateTime sdt = new DateTime(startDate);
		DateTime sdtx = new DateTime(sdt.toString("yyyy-MM-01"));
		DateTime edt = new DateTime(endDate);
		DateTime edtx = new DateTime(edt.toString("yyyy-MM-01"));
		int totalRow = Months.monthsBetween(sdtx, edtx).getMonths() + 1;
		
		Integer pvsum = new Integer(0);
		Integer uvsum = new Integer(0);
		
		for(int i = 0; i < totalRow; i++) {
			YmfwPo xpo = new YmfwPo();
			xpo.setTime(edt.minusMonths(i).toString("yyyy-MM"));

			String sql = "select sum(pv), sum(uv) from "+tableName+" where time >= '"+edt.minusMonths(i).toString("yyyy-MM-01")+"' and time < '" + edt.minusMonths(i).plusMonths(1).toString("yyyy-MM-01") + "'";
			List<Integer[]> qlist = jdbcTemplate.query(sql, new RowMapper<Integer[]>(){
				@Override
				public Integer[] mapRow(ResultSet rs, int index) throws SQLException {
					Integer[] intarr = new Integer[2];
					intarr[0] = rs.getInt(1);
					intarr[1] = rs.getInt(2);
					return intarr;
				}});
			
			Integer[] integers = qlist.get(0);
			xpo.setPv(integers[0]);
			xpo.setUv(integers[1]);
			pvsum += integers[0];
			uvsum += integers[1];
			list.add(xpo);
		}
		
		YmfwPo xpo = new YmfwPo();
		xpo.setTime("总计");
		xpo.setPv(pvsum);
		xpo.setUv(uvsum);
		list.add(xpo);
		
		return list;
	}
	
	//=============页面访问按年==================
	
	/**
	 * 页面访问量按年统计的pv
	 */
	@Override
	public List<Integer> yearymfwpv(Date startDate, Date endDate) {
		DateTime dstartDate = new DateTime(startDate);
		DateTime dendDate = new DateTime(endDate);
		int years = Years.yearsBetween(dstartDate, dendDate).getYears();
		List<Integer> flowList = new ArrayList<Integer>();
		
		String sql = "select sum(pv) from schepageview where time >= ";
		for(int i = 0; i < years + 1; i++) {
			int res1 = jdbcTemplate.queryForInt(sql + "\"" + dstartDate.toString("yyyy-01-01") + "\" and time < \"" + dstartDate.plusYears(1).toString("yyyy-01-01") + "\"");
			flowList.add(res1);
			dstartDate = dstartDate.plusYears(1);
		}
		
		return flowList;
	}
	
	/**
	 * 页面访问量按年统计的uv
	 */
	@Override
	public List<Integer> yearymfwuv(Date startDate, Date endDate) {
		DateTime dstartDate = new DateTime(startDate);
		DateTime dendDate = new DateTime(endDate);
		int years = Years.yearsBetween(dstartDate, dendDate).getYears();
		List<Integer> flowList = new ArrayList<Integer>();
		
		String sql = "select sum(uv) from schepageview where time >= ";
		for(int i = 0; i < years + 1; i++) {
			int res1 = jdbcTemplate.queryForInt(sql + "\"" + dstartDate.toString("yyyy-01-01") + "\" and time < \"" + dstartDate.plusYears(1).toString("yyyy-01-01") + "\"");
			flowList.add(res1);
			dstartDate = dstartDate.plusYears(1);
		}
		
		return flowList;
	}
	
	/**
	 * 按年统计的pv、uv做foot时求和用
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	private Integer[] yearpvuvsum(Date startDate, Date endDate, String tableName) {
		DateTime sdt = new DateTime(startDate);
		DateTime edt = new DateTime(endDate);
		
		String sql1 = "select sum(pv), sum(uv) from "+tableName+" where time >= '"+sdt.toString("yyyy-01-01")+"' and time < '" +edt.plusYears(1).toString("yyyy-01-01") + "'"; 
		List<YmfwPo> list = jdbcTemplate.query(sql1, new RowMapper<YmfwPo>(){

			@Override
			public YmfwPo mapRow(ResultSet rs, int index) throws SQLException {
				YmfwPo po = new YmfwPo();
				po.setPv(rs.getInt(1));
				po.setUv(rs.getInt(2));
				return po;
			}
		});
		
		Integer[] res = new Integer[]{0, 0};
		if(list != null && list.size() > 0) {
			res[0] = list.get(0).getPv();
			res[1] = list.get(0).getUv();
		}
		
		return res;
	}
	
	/**
	 * 页面访问量按年的list数据
	 * @param page
	 * @param rows
	 * @return
	 */
	@Override
	public EasyUiDataGridResult yearymfwList(Integer page, Integer rows, Date startDate, Date endDate, String tableName) {
		List<YmfwPo> list = new ArrayList<YmfwPo>();
		DateTime sdt = new DateTime(startDate);
		DateTime edt = new DateTime(endDate);
		
		int totalRow = Years.yearsBetween(sdt, edt).getYears() + 1;
		
		int start = (page - 1) * rows;
		int limit = rows;
		
		int end = 0;
		if((start + limit) > totalRow) {
			end = totalRow;
		}else {
			end = start + limit;
		}
		
		for(int i = start; i < end; i++) {
			YmfwPo xpo = new YmfwPo();
			xpo.setTime(edt.minusYears(i).toString("yyyy"));
			String sql = "select sum(pv), sum(uv) from "+tableName+" where time >= '"+edt.minusYears(i).toString("yyyy-01-01")+"' and time < '" + edt.minusYears(i).plusYears(1).toString("yyyy-01-01") + "'";
			List<Integer[]> qlist = jdbcTemplate.query(sql, new RowMapper<Integer[]>(){
				@Override
				public Integer[] mapRow(ResultSet rs, int index) throws SQLException {
					Integer[] intarr = new Integer[2];
					intarr[0] = rs.getInt(1);
					intarr[1] = rs.getInt(2);
					return intarr;
				}});
			
			Integer[] integers = qlist.get(0);
			xpo.setPv(integers[0]);
			xpo.setUv(integers[1]);
			list.add(xpo);
		}
		
		Integer[] intarr = yearpvuvsum(startDate, endDate, tableName);
		Map<String, String> map = new HashMap<String, String>();
		map.put("time", "总计");
		map.put("pv", intarr[0] + "");
		map.put("uv", intarr[1] + "");
		List<Map<String, String>> list2 = new ArrayList<Map<String, String>>();
		list2.add(map);
		EasyUiDataGridResultFoot result = new EasyUiDataGridResultFoot(totalRow, list, list2);
		return result;
	}
	
	/**
	 * 页面访问导出每年的
	 */
	@Override
	public List<YmfwPo> yearymfwExport(Date startDate, Date endDate, String tableName) {
		List<YmfwPo> list = new ArrayList<YmfwPo>();
		DateTime sdt = new DateTime(startDate);
		DateTime edt = new DateTime(endDate);
		int totalRow = Years.yearsBetween(sdt, edt).getYears() + 1;
		
		Integer pvsum = new Integer(0);
		Integer uvsum = new Integer(0);
		
		for(int i = 0; i < totalRow; i++) {
			YmfwPo xpo = new YmfwPo();
			xpo.setTime(edt.minusYears(i).toString("yyyy"));
			
			String sql = "select sum(pv), sum(uv) from "+tableName+" where time >= '"+edt.minusYears(i).toString("yyyy-01-01")+"' and time < '" + edt.minusYears(i).plusYears(1).toString("yyyy-01-01") + "'";
			List<Integer[]> qlist = jdbcTemplate.query(sql, new RowMapper<Integer[]>(){
				@Override
				public Integer[] mapRow(ResultSet rs, int index) throws SQLException {
					Integer[] intarr = new Integer[2];
					intarr[0] = rs.getInt(1);
					intarr[1] = rs.getInt(2);
					return intarr;
				}});
			
			Integer[] integers = qlist.get(0);
			xpo.setPv(integers[0]);
			pvsum += integers[0];
			xpo.setUv(integers[1]);
			uvsum += integers[1];
			list.add(xpo);
		}
		
		YmfwPo xpo = new YmfwPo();
		xpo.setTime("总计");
		xpo.setPv(pvsum);
		xpo.setUv(uvsum);
		list.add(xpo);
		
		return list;
	}
	
	//=========================页面访问-周=====
	
	/**
	 * 页面访问量按周统计的pv
	 */
	@Override
	public List<Integer> weekymfwpv(Date startDate, Date endDate) {
		DateTime ds = new DateTime(startDate);
		DateTime de = new DateTime(endDate);
		List<Integer> flowList = new ArrayList<Integer>();
		
		int minus = ds.getDayOfWeek() - 1;
		DateTime m = ds.minusDays(minus); // 得到刚刚好的本周的周一
		int p = de.getDayOfWeek();
		DateTime e = de.plusDays(7 - p);
		int weeks = Weeks.weeksBetween(m, e).getWeeks();
		
		if (weeks <= 55) {
			String sql = "select sum(pv) from schepageview where time >= ";
			for (int i = 0; i < weeks + 1; i++) {
				int res1 = jdbcTemplate.queryForInt(sql + "\"" + m.toString("yyyy-MM-dd") + "\" and time < \"" + m.plusWeeks(1).toString("yyyy-MM-dd") + "\"");
				flowList.add(res1);
				m = m.plusWeeks(1);
			}
		} else {
			String sql = "select sum(pv) from schepageview where time >= ";
			int base = ((Double) Math.ceil(weeks / 55.0)).intValue();
			for (int i = 0; i < (weeks / base) + 1; i++) {
				int res1 = jdbcTemplate.queryForInt(sql + "\"" + m.toString("yyyy-MM-dd") + "\" and time < \"" + m.plusWeeks(base).toString("yyyy-MM-dd") + "\"");
				flowList.add(res1);
				m = m.plusWeeks(base);
			}
		}
		
		return flowList;
	}
	
	/**
	 * 页面访问量按周统计的uv
	 */
	@Override
	public List<Integer> weekymfwuv(Date startDate, Date endDate) {
		DateTime ds = new DateTime(startDate);
		DateTime de = new DateTime(endDate);
		List<Integer> flowList = new ArrayList<Integer>();
		
		int minus = ds.getDayOfWeek() - 1;
		DateTime m = ds.minusDays(minus); // 得到刚刚好的本周的周一
		int p = de.getDayOfWeek();
		DateTime e = de.plusDays(7 - p);
		int weeks = Weeks.weeksBetween(m, e).getWeeks();
		
		if (weeks <= 55) {
			String sql = "select sum(uv) from schepageview where time >= ";
			for (int i = 0; i < weeks + 1; i++) {
				int res1 = jdbcTemplate.queryForInt(sql + "\"" + m.toString("yyyy-MM-dd") + "\" and time < \"" + m.plusWeeks(1).toString("yyyy-MM-dd") + "\"");
				flowList.add(res1);
				m = m.plusWeeks(1);
			}
		} else {
			String sql = "select sum(uv) from schepageview where time >= ";
			int base = ((Double) Math.ceil(weeks / 55.0)).intValue();
			for (int i = 0; i < (weeks / base) + 1; i++) {
				int res1 = jdbcTemplate.queryForInt(sql + "\"" + m.toString("yyyy-MM-dd") + "\" and time < \"" + m.plusWeeks(base).toString("yyyy-MM-dd") + "\"");
				flowList.add(res1);
				m = m.plusWeeks(base);
			}
		}
		
		return flowList;
	}
	
	/**
	 * 页面访问量按周的list数据
	 * @param page
	 * @param rows
	 * @return
	 */
	@Override
	public EasyUiDataGridResult weekymfwList(Integer page, Integer rows, Date startDate, Date endDate, String tableName) {
		List<YmfwPo> list = new ArrayList<YmfwPo>();
		DateTime ds = new DateTime(startDate);
		DateTime de = new DateTime(endDate);
		
		int minus = ds.getDayOfWeek() - 1;
		DateTime m = ds.minusDays(minus); // 得到刚刚好的本周的周一
		int p = de.getDayOfWeek();
		DateTime e = de.plusDays(7 - p);
		int weeks = Weeks.weeksBetween(m, e).getWeeks();
		
		int totalRow = weeks + 1;
		int start = (page - 1) * rows;
		int limit = rows;
		int end = 0;
		if((start + limit) > totalRow) {
			end = totalRow;
		}else {
			end = start + limit;
		}
		
		for(int i = start; i < end; i++) {
			YmfwPo xpo = new YmfwPo();
			String str = "第" + m.plusWeeks(i).getWeekOfWeekyear() + "周  " + m.plusWeeks(i).toString("yyyy-MM-dd") + " -- " + m.plusWeeks(i).plusDays(6).toString("yyyy-MM-dd");
			xpo.setTime(str);
			String sql = "select sum(pv), sum(uv) from "+tableName+" where time >= '"+m.plusWeeks(i).toString("yyyy-MM-dd")+"' and time < '" + m.plusWeeks(i + 1).toString("yyyy-MM-dd") + "'";
			List<Integer[]> qlist = jdbcTemplate.query(sql, new RowMapper<Integer[]>(){
				@Override
				public Integer[] mapRow(ResultSet rs, int index) throws SQLException {
					Integer[] intarr = new Integer[2];
					intarr[0] = rs.getInt(1);
					intarr[1] = rs.getInt(2);
					return intarr;
				}});
			
			Integer[] integers = qlist.get(0);
			xpo.setPv(integers[0]);
			xpo.setUv(integers[1]);
			list.add(xpo);
		}
		
		Integer[] intarr = weekpvuvsum(startDate, endDate, tableName);
		Map<String, String> map = new HashMap<String, String>();
		map.put("time", "总计");
		map.put("pv", intarr[0] + "");
		map.put("uv", intarr[1] + "");
		List<Map<String, String>> list2 = new ArrayList<Map<String, String>>();
		list2.add(map);
		EasyUiDataGridResultFoot result = new EasyUiDataGridResultFoot(totalRow, list, list2);
		return result;
	}
	
	/**
	 * 按周统计的pv、uv做foot时求和用
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	private Integer[] weekpvuvsum(Date startDate, Date endDate, String tableName) {
		DateTime ds = new DateTime(startDate);
		DateTime de = new DateTime(endDate);
		
		int minus = ds.getDayOfWeek() - 1;
		DateTime m = ds.minusDays(minus); // 得到刚刚好的本周的周一
		int p = de.getDayOfWeek();
		DateTime e = de.plusDays(7 - p);
		int weeks = Weeks.weeksBetween(m, e).getWeeks();
		
		int totalRow = weeks + 1;
		Integer[] res = new Integer[]{0, 0};
		
		for(int i = 0; i < totalRow; i++) {
			String sql = "select sum(pv), sum(uv) from "+tableName+" where time >= '"+m.plusWeeks(i).toString("yyyy-MM-dd")+"' and time < '" + m.plusWeeks(i + 1).toString("yyyy-MM-dd") + "'";
			List<Integer[]> qlist = jdbcTemplate.query(sql, new RowMapper<Integer[]>(){
				@Override
				public Integer[] mapRow(ResultSet rs, int index) throws SQLException {
					Integer[] intarr = new Integer[2];
					intarr[0] = rs.getInt(1);
					intarr[1] = rs.getInt(2);
					return intarr;
				}});
			
			Integer[] integers = qlist.get(0);
			res[0] += integers[0];
			res[1] += integers[1];
		}
		
		return res;
	}

	/**
	 * 页面访问导出每周的
	 */
	@Override
	public List<YmfwPo> weekymfwExport(Date startDate, Date endDate, String tableName) {
		List<YmfwPo> list = new ArrayList<YmfwPo>();
		
		DateTime ds = new DateTime(startDate);
		DateTime de = new DateTime(endDate);
		
		int minus = ds.getDayOfWeek() - 1;
		DateTime m = ds.minusDays(minus); // 得到刚刚好的本周的周一
		int p = de.getDayOfWeek();
		DateTime e = de.plusDays(7 - p);
		int weeks = Weeks.weeksBetween(m, e).getWeeks();
		
		int totalRow = weeks + 1;
		Integer pvsum = new Integer(0);
		Integer uvsum = new Integer(0);
		
		for(int i = 0; i < totalRow; i++) {
			YmfwPo xpo = new YmfwPo();
			String str = "第" + m.plusWeeks(i).getWeekOfWeekyear() + "周  " + m.plusWeeks(i).toString("yyyy-MM-dd") + " -- " + m.plusWeeks(i).plusDays(6).toString("yyyy-MM-dd");
			xpo.setTime(str);
			
			String sql = "select sum(pv), sum(uv) from "+tableName+" where time >= '"+m.plusWeeks(i).toString("yyyy-MM-dd")+"' and time < '" + m.plusWeeks(i + 1).toString("yyyy-MM-dd") + "'";
			List<Integer[]> qlist = jdbcTemplate.query(sql, new RowMapper<Integer[]>(){
				@Override
				public Integer[] mapRow(ResultSet rs, int index) throws SQLException {
					Integer[] intarr = new Integer[2];
					intarr[0] = rs.getInt(1);
					intarr[1] = rs.getInt(2);
					return intarr;
				}});
			
			Integer[] integers = qlist.get(0);
			xpo.setPv(integers[0]);
			pvsum += integers[0];
			xpo.setUv(integers[1]);
			uvsum += integers[1];
			list.add(xpo);
		}
		
		YmfwPo xpo = new YmfwPo();
		xpo.setTime("总计");
		xpo.setPv(pvsum);
		xpo.setUv(uvsum);
		list.add(xpo);
		
		return list;
	}
	
	//===================hotword=================

	/**
	 * hotword的list数据按天的
	 */
	@Override
	public EasyUiDataGridResult dayHotwordList(Integer page, Integer rows, Date startDate, Date endDate, String tableName) {
		DateTime startDateJoda = new DateTime(startDate);	
		DateTime endDateJoda = new DateTime(endDate);
		
		String sql0 = "select count(1) from (select count(1) from "+tableName+" where time >= '"+startDateJoda.toString("yyyy-MM-dd")+"' and time <= '"+endDateJoda.toString("yyyy-MM-dd")+"' group by hotword) x";
		int totalRow = jdbcTemplate.queryForInt(sql0);
		int start = (page - 1) * rows;
		
		String sql = "select hotword, max(seacount) mnum from "+tableName+" where time >= ? and time <= ? group by hotword order by mnum desc limit " + start + ", " + rows;
		String[] sarr = {startDateJoda.toString("yyyy-MM-dd"), endDateJoda.toString("yyyy-MM-dd")};
		
		List<Hotword> list = jdbcTemplate.query(sql, sarr, new RowMapper<Hotword>() {
			@Override
			public Hotword mapRow(ResultSet rs, int index) throws SQLException {
				Hotword hw = new Hotword();
				hw.setHotword(rs.getString("hotword"));
				hw.setSeacount(rs.getInt("mnum"));
				return hw;
			}

		});
		
		EasyUiDataGridResult result = new EasyUiDataGridResult(totalRow, list);
		return result;
	}
	
	/**
	 * hotword的list数据按月的
	 */
	@Override
	public EasyUiDataGridResult monHotwordList(Integer page, Integer rows, Date startDate, Date endDate, String tableName) {
		DateTime startDateJoda = new DateTime(startDate);	
		DateTime endDateJoda = new DateTime(endDate);
		
		String sql0 = "select count(1) from (select count(1) from "+tableName+" where time >= '"+startDateJoda.toString("yyyy-MM-01")+"' and time < '"+endDateJoda.plusMonths(1).toString("yyyy-MM-01")+"' group by hotword) x";
		int totalRow = jdbcTemplate.queryForInt(sql0);
		int start = (page - 1) * rows;
		
		String sql = "select hotword, max(seacount) mnum from "+tableName+" where time >= ? and time < ? group by hotword order by mnum desc limit " + start + ", " + rows;
		String[] sarr = {startDateJoda.toString("yyyy-MM-01"), endDateJoda.plusMonths(1).toString("yyyy-MM-01")};
		
		List<Hotword> list = jdbcTemplate.query(sql, sarr, new RowMapper<Hotword>() {
			@Override
			public Hotword mapRow(ResultSet rs, int index) throws SQLException {
				Hotword hw = new Hotword();
				hw.setHotword(rs.getString("hotword"));
				hw.setSeacount(rs.getInt("mnum"));
				return hw;
			}

		});
		
		EasyUiDataGridResult result = new EasyUiDataGridResult(totalRow, list);
		return result;
	}
	
	/**
	 * hotword的list数据按周的
	 */
	@Override
	public EasyUiDataGridResult weekHotwordList(Integer page, Integer rows, Date startDate, Date endDate, String tableName) {
		DateTime ds = new DateTime(startDate);	
		DateTime de = new DateTime(endDate);
		
		int minus = ds.getDayOfWeek() - 1;
		DateTime m = ds.minusDays(minus); //得到刚刚好的本周的周一
		int p = de.getDayOfWeek();
		DateTime e = de.plusDays(7 - p);
		
		String sql0 = "select count(1) from (select count(1) from "+tableName+" where time >= '"+m.toString("yyyy-MM-dd")+"' and time <= '"+e.toString("yyyy-MM-dd")+"' group by hotword) x";
		int totalRow = jdbcTemplate.queryForInt(sql0);
		int start = (page - 1) * rows;
		
		String sql = "select hotword, max(seacount) mnum from "+tableName+" where time >= ? and time <= ? group by hotword order by mnum desc limit " + start + ", " + rows;
		String[] sarr = {m.toString("yyyy-MM-dd"), e.toString("yyyy-MM-dd")};
		
		List<Hotword> list = jdbcTemplate.query(sql, sarr, new RowMapper<Hotword>() {
			@Override
			public Hotword mapRow(ResultSet rs, int index) throws SQLException {
				Hotword hw = new Hotword();
				hw.setHotword(rs.getString("hotword"));
				hw.setSeacount(rs.getInt("mnum"));
				return hw;
			}

		});
		
		EasyUiDataGridResult result = new EasyUiDataGridResult(totalRow, list);
		return result;
	}
	
	/**
	 * hotword的list数据按年的
	 */
	@Override
	public EasyUiDataGridResult yearHotwordList(Integer page, Integer rows, Date startDate, Date endDate, String tableName) {
		DateTime startDateJoda = new DateTime(startDate);	
		DateTime endDateJoda = new DateTime(endDate);
		
		String sql0 = "select count(1) from (select count(1) from "+tableName+" where time >= '"+startDateJoda.toString("yyyy-01-01")+"' and time < '"+endDateJoda.plusYears(1).toString("yyyy-01-01")+"' group by hotword) x";
		int totalRow = jdbcTemplate.queryForInt(sql0);
		int start = (page - 1) * rows;
		
		String sql = "select hotword, max(seacount) mnum from "+tableName+" where time >= ? and time < ? group by hotword order by mnum desc limit " + start + ", " + rows;
		String[] sarr = {startDateJoda.toString("yyyy-01-01"), endDateJoda.plusYears(1).toString("yyyy-01-01")};
		
		List<Hotword> list = jdbcTemplate.query(sql, sarr, new RowMapper<Hotword>() {
			@Override
			public Hotword mapRow(ResultSet rs, int index) throws SQLException {
				Hotword hw = new Hotword();
				hw.setHotword(rs.getString("hotword"));
				hw.setSeacount(rs.getInt("mnum"));
				return hw;
			}

		});
		
		EasyUiDataGridResult result = new EasyUiDataGridResult(totalRow, list);
		return result;
	}

	/**
	 * 热词导出每天的
	 */
	@Override
	public List<Hotword> dayHotwordExport(Date startDate, Date endDate, String tableName) {
		DateTime startDateJoda = new DateTime(startDate);	
		DateTime endDateJoda = new DateTime(endDate);
		
		String sql = "select hotword, max(seacount) mnum from "+tableName+" where time >= ? and time <= ? group by hotword order by mnum desc ";
		String[] sarr = {startDateJoda.toString("yyyy-MM-dd"), endDateJoda.toString("yyyy-MM-dd")};
		
		List<Hotword> list = jdbcTemplate.query(sql, sarr, new RowMapper<Hotword>() {
			@Override
			public Hotword mapRow(ResultSet rs, int index) throws SQLException {
				Hotword hw = new Hotword();
				hw.setHotword(rs.getString("hotword"));
				hw.setSeacount(rs.getInt("mnum"));
				return hw;
			}

		});
		return list;
	}
	
	/**
	 * 热词导出每周的
	 */
	@Override
	public List<Hotword> weekHotwordExport(Date startDate, Date endDate, String tableName) {
		DateTime ds = new DateTime(startDate);	
		DateTime de = new DateTime(endDate);
		
		int minus = ds.getDayOfWeek() - 1;
		DateTime m = ds.minusDays(minus); //得到刚刚好的本周的周一
		int p = de.getDayOfWeek();
		DateTime e = de.plusDays(7 - p);
		
		String sql = "select hotword, max(seacount) mnum from "+tableName+" where time >= ? and time <= ? group by hotword order by mnum desc ";
		String[] sarr = {m.toString("yyyy-MM-dd"), e.toString("yyyy-MM-dd")};
		
		List<Hotword> list = jdbcTemplate.query(sql, sarr, new RowMapper<Hotword>() {
			@Override
			public Hotword mapRow(ResultSet rs, int index) throws SQLException {
				Hotword hw = new Hotword();
				hw.setHotword(rs.getString("hotword"));
				hw.setSeacount(rs.getInt("mnum"));
				return hw;
			}

		});
		return list;
	}
	
	/**
	 * 热词导出每月的
	 */
	@Override
	public List<Hotword> monHotwordExport(Date startDate, Date endDate, String tableName) {
		DateTime startDateJoda = new DateTime(startDate);	
		DateTime endDateJoda = new DateTime(endDate);
		
		String sql = "select hotword, max(seacount) mnum from "+tableName+" where time >= ? and time < ? group by hotword order by mnum desc ";
		String[] sarr = {startDateJoda.toString("yyyy-MM-01"), endDateJoda.plusMonths(1).toString("yyyy-MM-01")};
		
		List<Hotword> list = jdbcTemplate.query(sql, sarr, new RowMapper<Hotword>() {
			@Override
			public Hotword mapRow(ResultSet rs, int index) throws SQLException {
				Hotword hw = new Hotword();
				hw.setHotword(rs.getString("hotword"));
				hw.setSeacount(rs.getInt("mnum"));
				return hw;
			}

		});
		return list;
	}

	/**
	 * 热词导出每年的
	 */
	@Override
	public List<Hotword> yearHotwordExport(Date startDate, Date endDate, String tableName) {
		DateTime startDateJoda = new DateTime(startDate);	
		DateTime endDateJoda = new DateTime(endDate);
		
		String sql = "select hotword, max(seacount) mnum from "+tableName+" where time >= ? and time < ? group by hotword order by mnum desc ";
		String[] sarr = {startDateJoda.toString("yyyy-01-01"), endDateJoda.plusYears(1).toString("yyyy-01-01")};
		
		List<Hotword> list = jdbcTemplate.query(sql, sarr, new RowMapper<Hotword>() {
			@Override
			public Hotword mapRow(ResultSet rs, int index) throws SQLException {
				Hotword hw = new Hotword();
				hw.setHotword(rs.getString("hotword"));
				hw.setSeacount(rs.getInt("mnum"));
				return hw;
			}

		});
		return list;
	}
	
	//==========================广告位详情页面访问量=====================
	
	/**
	 * 广告位页面访问量详情按天的list数据
	 * @param page
	 * @param rows
	 * @return
	 */
	@Override
	public EasyUiDataGridResult dayGgwDetailList(Integer page, Integer rows, Date startDate, Date endDate) {
		DateTime startDateJoda = new DateTime(startDate);	
		DateTime endDateJoda = new DateTime(endDate);
		
		String sql0 = "select count(1) from (select count(1) from scheadsdetail where time >= '"+startDateJoda.toString("yyyy-MM-dd")+"' and time <= '"+endDateJoda.toString("yyyy-MM-dd")+"' group by adsid) x";
		int totalRow = jdbcTemplate.queryForInt(sql0);
		int start = (page - 1) * rows;
		
		String sql = "SELECT title, sum(pv) pv, sum(uv) uv FROM `scheadsdetail` where time >= ? and time <= ? group by adsid order by pv desc limit " + start + ", " + rows;
		String[] sarr = {startDateJoda.toString("yyyy-MM-dd"), endDateJoda.toString("yyyy-MM-dd")};
		
		List<GgwDetailPo> list = jdbcTemplate.query(sql, sarr, new RowMapper<GgwDetailPo>() {
			@Override
			public GgwDetailPo mapRow(ResultSet rs, int index) throws SQLException {
				GgwDetailPo po = new GgwDetailPo();
				po.setTitle(rs.getString("title"));
				po.setPv(rs.getInt("pv"));
				po.setUv(rs.getInt("uv"));
				return po;
			}});
		
		EasyUiDataGridResult result = new EasyUiDataGridResult(totalRow, list);
		return result;
	}
	
	/**
	 * 广告位页面访问量详情按周的list数据
	 * @param page
	 * @param rows
	 * @return
	 */
	@Override
	public EasyUiDataGridResult weekGgwDetailList(Integer page, Integer rows, Date startDate, Date endDate) {
		DateTime ds = new DateTime(startDate);	
		DateTime de = new DateTime(endDate);
		
		int minus = ds.getDayOfWeek() - 1;
		DateTime m = ds.minusDays(minus); //得到刚刚好的本周的周一
		int p = de.getDayOfWeek();
		DateTime e = de.plusDays(7 - p);
		
		String sql0 = "select count(1) from (select count(1) from scheadsdetail where time >= '"+m.toString("yyyy-MM-dd")+"' and time <= '"+e.toString("yyyy-MM-dd")+"' group by adsid) x";
		int totalRow = jdbcTemplate.queryForInt(sql0);
		int start = (page - 1) * rows;
		
		String sql = "SELECT title, sum(pv) pv, sum(uv) uv FROM `scheadsdetail` where time >= ? and time <= ? group by adsid order by pv desc limit " + start + ", " + rows;
		String[] sarr = {m.toString("yyyy-MM-dd"), e.toString("yyyy-MM-dd")};
		
		List<GgwDetailPo> list = jdbcTemplate.query(sql, sarr, new RowMapper<GgwDetailPo>() {
			@Override
			public GgwDetailPo mapRow(ResultSet rs, int index) throws SQLException {
				GgwDetailPo po = new GgwDetailPo();
				po.setTitle(rs.getString("title"));
				po.setPv(rs.getInt("pv"));
				po.setUv(rs.getInt("uv"));
				return po;
			}});
		
		EasyUiDataGridResult result = new EasyUiDataGridResult(totalRow, list);
		return result;
	}
	
	/**
	 * 广告位页面访问量详情按月的list数据
	 * @param page
	 * @param rows
	 * @return
	 */
	@Override
	public EasyUiDataGridResult monGgwDetailList(Integer page, Integer rows, Date startDate, Date endDate) {
		DateTime startDateJoda = new DateTime(startDate);	
		DateTime endDateJoda = new DateTime(endDate);
		
		String sql0 = "select count(1) from (select count(1) from scheadsdetail where time >= '"+startDateJoda.toString("yyyy-MM-01")+"' and time < '"+endDateJoda.plusMonths(1).toString("yyyy-MM-01")+"' group by adsid) x";
		int totalRow = jdbcTemplate.queryForInt(sql0);
		int start = (page - 1) * rows;
		
		String sql = "SELECT title, sum(pv) pv, sum(uv) uv FROM `scheadsdetail` where time >= ? and time < ? group by adsid order by pv desc limit " + start + ", " + rows;
		String[] sarr = {startDateJoda.toString("yyyy-MM-01"), endDateJoda.plusMonths(1).toString("yyyy-MM-01")};
		
		List<GgwDetailPo> list = jdbcTemplate.query(sql, sarr, new RowMapper<GgwDetailPo>() {
			@Override
			public GgwDetailPo mapRow(ResultSet rs, int index) throws SQLException {
				GgwDetailPo po = new GgwDetailPo();
				po.setTitle(rs.getString("title"));
				po.setPv(rs.getInt("pv"));
				po.setUv(rs.getInt("uv"));
				return po;
			}});
		
		EasyUiDataGridResult result = new EasyUiDataGridResult(totalRow, list);
		return result;
	}

	/**
	 * 广告位页面访问量详情按年的list数据
	 * @param page
	 * @param rows
	 * @return
	 */
	@Override
	public EasyUiDataGridResult yearGgwDetailList(Integer page, Integer rows, Date startDate, Date endDate) {
		DateTime startDateJoda = new DateTime(startDate);	
		DateTime endDateJoda = new DateTime(endDate);
		
		String sql0 = "select count(1) from (select count(1) from scheadsdetail where time >= '"+startDateJoda.toString("yyyy-01-01")+"' and time < '"+endDateJoda.plusYears(1).toString("yyyy-01-01")+"' group by adsid) x";
		int totalRow = jdbcTemplate.queryForInt(sql0);
		int start = (page - 1) * rows;
		
		String sql = "SELECT title, sum(pv) pv, sum(uv) uv FROM `scheadsdetail` where time >= ? and time < ? group by adsid order by pv desc limit " + start + ", " + rows;
		String[] sarr = {startDateJoda.toString("yyyy-01-01"), endDateJoda.plusYears(1).toString("yyyy-01-01")};
		
		List<GgwDetailPo> list = jdbcTemplate.query(sql, sarr, new RowMapper<GgwDetailPo>() {
			@Override
			public GgwDetailPo mapRow(ResultSet rs, int index) throws SQLException {
				GgwDetailPo po = new GgwDetailPo();
				po.setTitle(rs.getString("title"));
				po.setPv(rs.getInt("pv"));
				po.setUv(rs.getInt("uv"));
				return po;
			}});
		
		EasyUiDataGridResult result = new EasyUiDataGridResult(totalRow, list);
		return result;
	}

	/**
	 * 广告位页面访问量详情导出按天
	 */
	@Override
	public List<GgwDetailPo> dayGgwDetailExport(Date startDate, Date endDate) {
		DateTime startDateJoda = new DateTime(startDate);	
		DateTime endDateJoda = new DateTime(endDate);
		
		String sql = "SELECT title, sum(pv) pv, sum(uv) uv FROM `scheadsdetail` where time >= ? and time <= ? group by adsid order by pv desc ";
		String[] sarr = {startDateJoda.toString("yyyy-MM-dd"), endDateJoda.toString("yyyy-MM-dd")};
		
		List<GgwDetailPo> list = jdbcTemplate.query(sql, sarr, new RowMapper<GgwDetailPo>() {
			@Override
			public GgwDetailPo mapRow(ResultSet rs, int index) throws SQLException {
				GgwDetailPo po = new GgwDetailPo();
				po.setTitle(rs.getString("title"));
				po.setPv(rs.getInt("pv"));
				po.setUv(rs.getInt("uv"));
				return po;
			}});
		
		return list;
	}
	
	/**
	 * 广告位页面访问量详情导出按周
	 */
	@Override
	public List<GgwDetailPo> weekGgwDetailExport(Date startDate, Date endDate) {
		DateTime ds = new DateTime(startDate);	
		DateTime de = new DateTime(endDate);
		
		int minus = ds.getDayOfWeek() - 1;
		DateTime m = ds.minusDays(minus); //得到刚刚好的本周的周一
		int p = de.getDayOfWeek();
		DateTime e = de.plusDays(7 - p);
		
		String sql = "SELECT title, sum(pv) pv, sum(uv) uv FROM `scheadsdetail` where time >= ? and time <= ? group by adsid order by pv desc ";
		String[] sarr = {m.toString("yyyy-MM-dd"), e.toString("yyyy-MM-dd")};
		
		List<GgwDetailPo> list = jdbcTemplate.query(sql, sarr, new RowMapper<GgwDetailPo>() {
			@Override
			public GgwDetailPo mapRow(ResultSet rs, int index) throws SQLException {
				GgwDetailPo po = new GgwDetailPo();
				po.setTitle(rs.getString("title"));
				po.setPv(rs.getInt("pv"));
				po.setUv(rs.getInt("uv"));
				return po;
			}});
		
		return list;
	}
	
	/**
	 * 广告位页面访问量详情导出按月
	 */
	@Override
	public List<GgwDetailPo> monGgwDetailExport(Date startDate, Date endDate) {
		DateTime startDateJoda = new DateTime(startDate);	
		DateTime endDateJoda = new DateTime(endDate);
		
		String sql = "SELECT title, sum(pv) pv, sum(uv) uv FROM `scheadsdetail` where time >= ? and time < ? group by adsid order by pv desc";
		String[] sarr = {startDateJoda.toString("yyyy-MM-01"), endDateJoda.plusMonths(1).toString("yyyy-MM-01")};
		
		List<GgwDetailPo> list = jdbcTemplate.query(sql, sarr, new RowMapper<GgwDetailPo>() {
			@Override
			public GgwDetailPo mapRow(ResultSet rs, int index) throws SQLException {
				GgwDetailPo po = new GgwDetailPo();
				po.setTitle(rs.getString("title"));
				po.setPv(rs.getInt("pv"));
				po.setUv(rs.getInt("uv"));
				return po;
			}});
		
		return list;
	}
	
	/**
	 * 广告位页面访问量详情导出按年
	 */
	@Override
	public List<GgwDetailPo> yearGgwDetailExport(Date startDate, Date endDate) {
		DateTime startDateJoda = new DateTime(startDate);	
		DateTime endDateJoda = new DateTime(endDate);
		
		String sql = "SELECT title, sum(pv) pv, sum(uv) uv FROM `scheadsdetail` where time >= ? and time < ? group by adsid order by pv desc";
		String[] sarr = {startDateJoda.toString("yyyy-01-01"), endDateJoda.plusYears(1).toString("yyyy-01-01")};
		
		List<GgwDetailPo> list = jdbcTemplate.query(sql, sarr, new RowMapper<GgwDetailPo>() {
			@Override
			public GgwDetailPo mapRow(ResultSet rs, int index) throws SQLException {
				GgwDetailPo po = new GgwDetailPo();
				po.setTitle(rs.getString("title"));
				po.setPv(rs.getInt("pv"));
				po.setUv(rs.getInt("uv"));
				return po;
			}});
		
		return list;
	}
	
	//=============带分享量的===============

	/**
	 * 按天统计的页面访问量
	 */
	@Override
	public EasyUiDataGridResult dayymfwfxList(Integer page, Integer rows, Date startDate, Date endDate, String tableName) {
		List<YmfwfxPo> list = new ArrayList<YmfwfxPo>();
		DateTime sdt = new DateTime(startDate);
		DateTime edt = new DateTime(endDate);
		
		int totalRow = Days.daysBetween(sdt, edt).getDays() + 1; 
		
		int start = (page - 1) * rows;
		int limit = rows;
		
		int end = 0;
		if((start + limit) > totalRow) {
			end = totalRow;
		}else {
			end = start + limit;
		}
		
		for(int i = start; i < end; i++) {
			YmfwfxPo xpo = new YmfwfxPo();
			xpo.setTime(edt.minusDays(i).toString("yyyy-MM-dd"));
			String sql = "select pv, uv, share from "+tableName+" where time = '"+edt.minusDays(i).toString("yyyy-MM-dd")+"'";
			List<Integer[]> qlist = jdbcTemplate.query(sql, new RowMapper<Integer[]>(){
				@Override
				public Integer[] mapRow(ResultSet rs, int index) throws SQLException {
					Integer[] intarr = new Integer[3];
					intarr[0] = rs.getInt(1);
					intarr[1] = rs.getInt(2);
					intarr[2] = rs.getInt(3);
					return intarr;
				}});
			
			if(null != qlist && qlist.size() > 0) {
				Integer[] integers = qlist.get(0);
				if(null != integers[0]) {
					xpo.setPv(integers[0]);
				}else {
					xpo.setPv(0);
				}
				
				if(null != integers[1]) {
					xpo.setUv(integers[1]);
				}else {
					xpo.setUv(0);
				}
				
				if(null != integers[2]) {
					xpo.setShare(integers[2]);
				}else {
					xpo.setShare(0);
				}
			}else {
				xpo.setPv(0);
				xpo.setUv(0);
				xpo.setShare(0);
			}
			
			list.add(xpo);
		}
		
		Integer[] intarr = daypvuvgxsum(startDate, endDate, tableName);
		Map<String, String> map = new HashMap<String, String>();
		map.put("time", "总计");
		map.put("pv", intarr[0] + "");
		map.put("uv", intarr[1] + "");
		map.put("share", intarr[2] + "");
		List<Map<String, String>> list2 = new ArrayList<Map<String, String>>();
		list2.add(map);
		EasyUiDataGridResultFoot result = new EasyUiDataGridResultFoot(totalRow, list, list2);
		return result;
	}

	/**
	 * 按天统计的pv、uv、share做foot时求和用
	 */
	private Integer[] daypvuvgxsum(Date startDate, Date endDate, String tableName) {
		DateTime sdt = new DateTime(startDate);
		DateTime edt = new DateTime(endDate);
		
		String sql1 = "select sum(pv), sum(uv), sum(share) from "+tableName+" where time >= '"+sdt.toString("yyyy-MM-dd")+"' and time <= '" +edt.toString("yyyy-MM-dd") + "'"; 
		List<YmfwfxPo> list = jdbcTemplate.query(sql1, new RowMapper<YmfwfxPo>(){

			@Override
			public YmfwfxPo mapRow(ResultSet rs, int index) throws SQLException {
				YmfwfxPo po = new YmfwfxPo();
				po.setPv(rs.getInt(1));
				po.setUv(rs.getInt(2));
				po.setShare(rs.getInt(3));
				return po;
			}
		});
		
		Integer[] res = new Integer[]{0, 0, 0};
		if(list != null && list.size() > 0) {
			res[0] = list.get(0).getPv();
			res[1] = list.get(0).getUv();
			res[2] = list.get(0).getShare();
		}
		return res;
	}

	/**
	 * 按月统计的页面访问量含分享
	 */
	@Override
	public EasyUiDataGridResult monymfwfxList(Integer page, Integer rows, Date startDate, Date endDate, String tableName) {
		List<YmfwfxPo> list = new ArrayList<YmfwfxPo>();
		DateTime sdt = new DateTime(startDate);
		DateTime edt = new DateTime(endDate);
		
		int totalRow = Months.monthsBetween(sdt, edt).getMonths() + 1;
		
		int start = (page - 1) * rows;
		int limit = rows;
		
		int end = 0;
		if((start + limit) > totalRow) {
			end = totalRow;
		}else {
			end = start + limit;
		}
		
		for(int i = start; i < end; i++) {
			YmfwfxPo xpo = new YmfwfxPo();
			xpo.setTime(edt.minusMonths(i).toString("yyyy-MM"));
			
			String sql = "select sum(pv), sum(uv), sum(share) from "+tableName+" where time >= '"+edt.minusMonths(i).toString("yyyy-MM-01")+"' and time < '" + edt.minusMonths(i).plusMonths(1).toString("yyyy-MM-01") + "'";
			List<Integer[]> qlist = jdbcTemplate.query(sql, new RowMapper<Integer[]>(){
				@Override
				public Integer[] mapRow(ResultSet rs, int index) throws SQLException {
					Integer[] intarr = new Integer[3];
					intarr[0] = rs.getInt(1);
					intarr[1] = rs.getInt(2);
					intarr[2] = rs.getInt(3);
					return intarr;
				}});
			
			Integer[] integers = qlist.get(0);
			xpo.setPv(integers[0]);
			xpo.setUv(integers[1]);
			xpo.setShare(integers[2]);
			list.add(xpo);
		}
		
		Integer[] intarr = monpvuvgxsum(startDate, endDate, tableName);
		Map<String, String> map = new HashMap<String, String>();
		map.put("time", "总计");
		map.put("pv", intarr[0] + "");
		map.put("uv", intarr[1] + "");
		map.put("share", intarr[2] + "");
		List<Map<String, String>> list2 = new ArrayList<Map<String, String>>();
		list2.add(map);
		EasyUiDataGridResultFoot result = new EasyUiDataGridResultFoot(totalRow, list, list2);
		return result;
	}
	
	/**
	 * 按月统计的pv、uv、share做foot时求和用
	 */
	private Integer[] monpvuvgxsum(Date startDate, Date endDate, String tableName) {
		DateTime sdt = new DateTime(startDate);
		DateTime edt = new DateTime(endDate);
		
		String sql1 = "select sum(pv), sum(uv), sum(share) from "+tableName+" where time >= '"+sdt.toString("yyyy-MM-01")+"' and time < '" +edt.plusMonths(1).toString("yyyy-MM-01") + "'"; 
		List<YmfwfxPo> list = jdbcTemplate.query(sql1, new RowMapper<YmfwfxPo>(){
			@Override
			public YmfwfxPo mapRow(ResultSet rs, int index) throws SQLException {
				YmfwfxPo po = new YmfwfxPo();
				po.setPv(rs.getInt(1));
				po.setUv(rs.getInt(2));
				po.setShare(rs.getInt(3));
				return po;
			}
		});
		
		Integer[] res = new Integer[]{0, 0, 0};
		if(list != null && list.size() > 0) {
			res[0] = list.get(0).getPv();
			res[1] = list.get(0).getUv();
			res[2] = list.get(0).getShare();
		}
		
		return res;
	}

	/**
	 * 页面访问量带分享量按周的list数据
	 */
	@Override
	public EasyUiDataGridResult weekymfwfxList(Integer page, Integer rows, Date startDate, Date endDate, String tableName) {
		List<YmfwfxPo> list = new ArrayList<YmfwfxPo>();
		DateTime ds = new DateTime(startDate);
		DateTime de = new DateTime(endDate);
		
		int minus = ds.getDayOfWeek() - 1;
		DateTime m = ds.minusDays(minus); // 得到刚刚好的本周的周一
		int p = de.getDayOfWeek();
		DateTime e = de.plusDays(7 - p);
		int weeks = Weeks.weeksBetween(m, e).getWeeks();
		
		int totalRow = weeks + 1;
		int start = (page - 1) * rows;
		int limit = rows;
		int end = 0;
		if((start + limit) > totalRow) {
			end = totalRow;
		}else {
			end = start + limit;
		}
		
		for(int i = start; i < end; i++) {
			YmfwfxPo xpo = new YmfwfxPo();
			String str = "第" + m.plusWeeks(i).getWeekOfWeekyear() + "周  " + m.plusWeeks(i).toString("yyyy-MM-dd") + " -- " + m.plusWeeks(i).plusDays(6).toString("yyyy-MM-dd");
			xpo.setTime(str);
			String sql = "select sum(pv), sum(uv), sum(share) from "+tableName+" where time >= '"+m.plusWeeks(i).toString("yyyy-MM-dd")+"' and time < '" + m.plusWeeks(i + 1).toString("yyyy-MM-dd") + "'";
			List<Integer[]> qlist = jdbcTemplate.query(sql, new RowMapper<Integer[]>(){
				@Override
				public Integer[] mapRow(ResultSet rs, int index) throws SQLException {
					Integer[] intarr = new Integer[3];
					intarr[0] = rs.getInt(1);
					intarr[1] = rs.getInt(2);
					intarr[2] = rs.getInt(3);
					return intarr;
				}});
			
			Integer[] integers = qlist.get(0);
			xpo.setPv(integers[0]);
			xpo.setUv(integers[1]);
			xpo.setShare(integers[2]);
			list.add(xpo);
		}
		
		Integer[] intarr = weekpvuvgxsum(startDate, endDate, tableName);
		Map<String, String> map = new HashMap<String, String>();
		map.put("time", "总计");
		map.put("pv", intarr[0] + "");
		map.put("uv", intarr[1] + "");
		map.put("share", intarr[2] + "");
		List<Map<String, String>> list2 = new ArrayList<Map<String, String>>();
		list2.add(map);
		EasyUiDataGridResultFoot result = new EasyUiDataGridResultFoot(totalRow, list, list2);
		return result;
	}
	
	/**
	 * 按周统计的pv、uv、share做foot时求和用
	 */
	private Integer[] weekpvuvgxsum(Date startDate, Date endDate, String tableName) {
		DateTime ds = new DateTime(startDate);
		DateTime de = new DateTime(endDate);
		
		int minus = ds.getDayOfWeek() - 1;
		DateTime m = ds.minusDays(minus); // 得到刚刚好的本周的周一
		int p = de.getDayOfWeek();
		DateTime e = de.plusDays(7 - p);
		int weeks = Weeks.weeksBetween(m, e).getWeeks();
		
		int totalRow = weeks + 1;
		Integer[] res = new Integer[]{0, 0, 0};
		
		for(int i = 0; i < totalRow; i++) {
			String sql = "select sum(pv), sum(uv), sum(share) from "+tableName+" where time >= '"+m.plusWeeks(i).toString("yyyy-MM-dd")+"' and time < '" + m.plusWeeks(i + 1).toString("yyyy-MM-dd") + "'";
			List<Integer[]> qlist = jdbcTemplate.query(sql, new RowMapper<Integer[]>(){
				@Override
				public Integer[] mapRow(ResultSet rs, int index) throws SQLException {
					Integer[] intarr = new Integer[3];
					intarr[0] = rs.getInt(1);
					intarr[1] = rs.getInt(2);
					intarr[2] = rs.getInt(3);
					return intarr;
				}});
			
			Integer[] integers = qlist.get(0);
			res[0] += integers[0];
			res[1] += integers[1];
			res[2] += integers[2];
		}
		
		return res;
	}

	/**
	 * 页面访问量带分享量按年的list数据
	 */
	@Override
	public EasyUiDataGridResult yearymfwfxList(Integer page, Integer rows, Date startDate, Date endDate, String tableName) {
		List<YmfwfxPo> list = new ArrayList<YmfwfxPo>();
		DateTime sdt = new DateTime(startDate);
		DateTime edt = new DateTime(endDate);
		
		int totalRow = Years.yearsBetween(sdt, edt).getYears() + 1;
		
		int start = (page - 1) * rows;
		int limit = rows;
		
		int end = 0;
		if((start + limit) > totalRow) {
			end = totalRow;
		}else {
			end = start + limit;
		}
		
		for(int i = start; i < end; i++) {
			YmfwfxPo xpo = new YmfwfxPo();
			xpo.setTime(edt.minusYears(i).toString("yyyy"));
			String sql = "select sum(pv), sum(uv), sum(share) from "+tableName+" where time >= '"+edt.minusYears(i).toString("yyyy-01-01")+"' and time < '" + edt.minusYears(i).plusYears(1).toString("yyyy-01-01") + "'";
			List<Integer[]> qlist = jdbcTemplate.query(sql, new RowMapper<Integer[]>(){
				@Override
				public Integer[] mapRow(ResultSet rs, int index) throws SQLException {
					Integer[] intarr = new Integer[3];
					intarr[0] = rs.getInt(1);
					intarr[1] = rs.getInt(2);
					intarr[2] = rs.getInt(3);
					return intarr;
				}});
			
			Integer[] integers = qlist.get(0);
			xpo.setPv(integers[0]);
			xpo.setUv(integers[1]);
			xpo.setShare(integers[2]);
			list.add(xpo);
		}
		
		Integer[] intarr = yearpvuvgxsum(startDate, endDate, tableName);
		Map<String, String> map = new HashMap<String, String>();
		map.put("time", "总计");
		map.put("pv", intarr[0] + "");
		map.put("uv", intarr[1] + "");
		map.put("share", intarr[2] + "");
		List<Map<String, String>> list2 = new ArrayList<Map<String, String>>();
		list2.add(map);
		EasyUiDataGridResultFoot result = new EasyUiDataGridResultFoot(totalRow, list, list2);
		return result;
	}
	
	/**
	 * 按年统计的pv、uv、share做foot时求和用
	 */
	private Integer[] yearpvuvgxsum(Date startDate, Date endDate, String tableName) {
		DateTime sdt = new DateTime(startDate);
		DateTime edt = new DateTime(endDate);
		
		String sql1 = "select sum(pv), sum(uv), sum(share) from "+tableName+" where time >= '"+sdt.toString("yyyy-01-01")+"' and time < '" +edt.plusYears(1).toString("yyyy-01-01") + "'"; 
		List<YmfwfxPo> list = jdbcTemplate.query(sql1, new RowMapper<YmfwfxPo>(){

			@Override
			public YmfwfxPo mapRow(ResultSet rs, int index) throws SQLException {
				YmfwfxPo po = new YmfwfxPo();
				po.setPv(rs.getInt(1));
				po.setUv(rs.getInt(2));
				po.setShare(rs.getInt(3));
				return po;
			}
		});
		
		Integer[] res = new Integer[]{0, 0, 0};
		if(list != null && list.size() > 0) {
			res[0] = list.get(0).getPv();
			res[1] = list.get(0).getUv();
			res[2] = list.get(0).getShare();
		}
		
		return res;
	}
	
	/**
	 * 页面访问导出每天的含分享量
	 */
	@Override
	public List<YmfwfxPo> dayymfwfxExport(Date startDate, Date endDate, String tableName) {
		List<YmfwfxPo> list = new ArrayList<YmfwfxPo>();
		DateTime sdt = new DateTime(startDate);
		DateTime edt = new DateTime(endDate);
		int totalRow = Days.daysBetween(sdt, edt).getDays() + 1; 
		
		Integer pvsum = new Integer(0);
		Integer uvsum = new Integer(0);
		Integer sharesum = new Integer(0);
		
		for(int i = 0; i < totalRow; i++) {
			YmfwfxPo xpo = new YmfwfxPo();
			xpo.setTime(edt.minusDays(i).toString("yyyy-MM-dd"));
			String sql = "select pv, uv, share from "+tableName+" where time = '"+edt.minusDays(i).toString("yyyy-MM-dd")+"'";
			List<Integer[]> qlist = jdbcTemplate.query(sql, new RowMapper<Integer[]>(){
				@Override
				public Integer[] mapRow(ResultSet rs, int index) throws SQLException {
					Integer[] intarr = new Integer[3];
					intarr[0] = rs.getInt(1);
					intarr[1] = rs.getInt(2);
					intarr[2] = rs.getInt(3);
					return intarr;
				}});
			
			if(null != qlist && qlist.size() > 0) {
				Integer[] integers = qlist.get(0);
				if(null != integers[0]) {
					xpo.setPv(integers[0]);
					pvsum += integers[0];
				}else {
					xpo.setPv(0);
					pvsum += 0;
				}
				
				if(null != integers[1]) {
					xpo.setUv(integers[1]);
					uvsum += integers[1];
				}else {
					xpo.setUv(0);
					uvsum += 0;
				}
				
				if(null != integers[2]) {
					xpo.setShare(integers[2]);
					sharesum += integers[2];
				}else {
					xpo.setShare(0);
					sharesum += 0;
				}
			}else {
				xpo.setPv(0);
				xpo.setUv(0);
				xpo.setShare(0);
				pvsum += 0;
				uvsum += 0;
				sharesum += 0;
			}
			
			list.add(xpo);
		}
		
		YmfwfxPo xpo = new YmfwfxPo();
		xpo.setTime("总计");
		xpo.setPv(pvsum);
		xpo.setUv(uvsum);
		xpo.setShare(sharesum);
		list.add(xpo);
		
		return list;
	}
	
	/**
	 * 页面访问导出每周的含分享量
	 */
	@Override
	public List<YmfwfxPo> weekymfwfxExport(Date startDate, Date endDate, String tableName) {
		List<YmfwfxPo> list = new ArrayList<YmfwfxPo>();
		
		DateTime ds = new DateTime(startDate);
		DateTime de = new DateTime(endDate);
		
		int minus = ds.getDayOfWeek() - 1;
		DateTime m = ds.minusDays(minus); // 得到刚刚好的本周的周一
		int p = de.getDayOfWeek();
		DateTime e = de.plusDays(7 - p);
		int weeks = Weeks.weeksBetween(m, e).getWeeks();
		
		int totalRow = weeks + 1;
		Integer pvsum = new Integer(0);
		Integer uvsum = new Integer(0);
		Integer sharesum = new Integer(0);
		
		for(int i = 0; i < totalRow; i++) {
			YmfwfxPo xpo = new YmfwfxPo();
			String str = "第" + m.plusWeeks(i).getWeekOfWeekyear() + "周  " + m.plusWeeks(i).toString("yyyy-MM-dd") + " -- " + m.plusWeeks(i).plusDays(6).toString("yyyy-MM-dd");
			xpo.setTime(str);
			
			String sql = "select sum(pv), sum(uv), sum(share) from "+tableName+" where time >= '"+m.plusWeeks(i).toString("yyyy-MM-dd")+"' and time < '" + m.plusWeeks(i + 1).toString("yyyy-MM-dd") + "'";
			List<Integer[]> qlist = jdbcTemplate.query(sql, new RowMapper<Integer[]>(){
				@Override
				public Integer[] mapRow(ResultSet rs, int index) throws SQLException {
					Integer[] intarr = new Integer[3];
					intarr[0] = rs.getInt(1);
					intarr[1] = rs.getInt(2);
					intarr[2] = rs.getInt(3);
					return intarr;
				}});
			
			Integer[] integers = qlist.get(0);
			xpo.setPv(integers[0]);
			xpo.setUv(integers[1]);
			xpo.setShare(integers[2]);
			pvsum += integers[0];
			uvsum += integers[1];
			sharesum += integers[2];
			list.add(xpo);
		}
		
		YmfwfxPo xpo = new YmfwfxPo();
		xpo.setTime("总计");
		xpo.setPv(pvsum);
		xpo.setUv(uvsum);
		xpo.setShare(sharesum);
		list.add(xpo);
		
		return list;
	}
	
	/**
	 * 页面访问含分享导出每月的
	 */
	@Override
	public List<YmfwfxPo> monymfwfxExport(Date startDate, Date endDate, String tableName) {
		List<YmfwfxPo> list = new ArrayList<YmfwfxPo>();
		DateTime sdt = new DateTime(startDate);
		DateTime edt = new DateTime(endDate);
		int totalRow = Months.monthsBetween(sdt, edt).getMonths() + 1;
		
		Integer pvsum = new Integer(0);
		Integer uvsum = new Integer(0);
		Integer sharesum = new Integer(0);
		
		for(int i = 0; i < totalRow; i++) {
			YmfwfxPo xpo = new YmfwfxPo();
			xpo.setTime(edt.minusMonths(i).toString("yyyy-MM"));

			String sql = "select sum(pv), sum(uv), sum(share) from "+tableName+" where time >= '"+edt.minusMonths(i).toString("yyyy-MM-01")+"' and time < '" + edt.minusMonths(i).plusMonths(1).toString("yyyy-MM-01") + "'";
			List<Integer[]> qlist = jdbcTemplate.query(sql, new RowMapper<Integer[]>(){
				@Override
				public Integer[] mapRow(ResultSet rs, int index) throws SQLException {
					Integer[] intarr = new Integer[3];
					intarr[0] = rs.getInt(1);
					intarr[1] = rs.getInt(2);
					intarr[2] = rs.getInt(3);
					return intarr;
				}});
			
			Integer[] integers = qlist.get(0);
			xpo.setPv(integers[0]);
			xpo.setUv(integers[1]);
			xpo.setShare(integers[2]);
			pvsum += integers[0];
			uvsum += integers[1];
			sharesum += integers[2];
			list.add(xpo);
		}
		
		YmfwfxPo xpo = new YmfwfxPo();
		xpo.setTime("总计");
		xpo.setPv(pvsum);
		xpo.setUv(uvsum);
		xpo.setShare(sharesum);
		list.add(xpo);
		
		return list;
	}
	
	/**
	 * 页面访问含分享量导出每年的
	 */
	@Override
	public List<YmfwfxPo> yearymfwfxExport(Date startDate, Date endDate, String tableName) {
		List<YmfwfxPo> list = new ArrayList<YmfwfxPo>();
		DateTime sdt = new DateTime(startDate);
		DateTime edt = new DateTime(endDate);
		int totalRow = Years.yearsBetween(sdt, edt).getYears() + 1;
		
		Integer pvsum = new Integer(0);
		Integer uvsum = new Integer(0);
		Integer sharesum = new Integer(0);
		
		for(int i = 0; i < totalRow; i++) {
			YmfwfxPo xpo = new YmfwfxPo();
			xpo.setTime(edt.minusYears(i).toString("yyyy"));
			
			String sql = "select sum(pv), sum(uv), sum(share) from "+tableName+" where time >= '"+edt.minusYears(i).toString("yyyy-01-01")+"' and time < '" + edt.minusYears(i).plusYears(1).toString("yyyy-01-01") + "'";
			List<Integer[]> qlist = jdbcTemplate.query(sql, new RowMapper<Integer[]>(){
				@Override
				public Integer[] mapRow(ResultSet rs, int index) throws SQLException {
					Integer[] intarr = new Integer[3];
					intarr[0] = rs.getInt(1);
					intarr[1] = rs.getInt(2);
					intarr[2] = rs.getInt(3);
					return intarr;
				}});
			
			Integer[] integers = qlist.get(0);
			xpo.setPv(integers[0]);
			xpo.setUv(integers[1]);
			xpo.setShare(integers[2]);
			pvsum += integers[0];
			uvsum += integers[1];
			sharesum += integers[2];
			list.add(xpo);
		}
		
		YmfwfxPo xpo = new YmfwfxPo();
		xpo.setTime("总计");
		xpo.setPv(pvsum);
		xpo.setUv(uvsum);
		xpo.setShare(sharesum);
		list.add(xpo);
		
		return list;
	}
	
	/**
	 * 页面访问量详情含分享按天的list数据
	 */
	@Override
	public EasyUiDataGridResult dayDetailFxList(Integer page, Integer rows, Date startDate, Date endDate, String tableName, String tableName2, String segment) {
		DateTime startDateJoda = new DateTime(startDate);	
		DateTime endDateJoda = new DateTime(endDate);
		
		String sql0 = "select count(1) from (select count(1) from "+tableName+" a, "+ tableName2 +" b  where a.detailid = b."+segment+" and a.time >= '"+startDateJoda.toString("yyyy-MM-dd")+"' and a.time <= '"+endDateJoda.toString("yyyy-MM-dd")+"' group by a.detailid) x";
		int totalRow = jdbcTemplate.queryForInt(sql0);
		int start = (page - 1) * rows;
		
		String sql = "SELECT a.title, sum(pv) pv, sum(uv) uv, sum(share) share, b.praisecount, b.collectcount FROM "+tableName+" a, "+ tableName2 +" b where a.detailid = b."+segment+" and a.time >= ? and a.time <= ? group by a.detailid order by pv desc limit " + start + ", " + rows;
		String[] sarr = {startDateJoda.toString("yyyy-MM-dd"), endDateJoda.toString("yyyy-MM-dd")};
		
		List<DetailPo> list = jdbcTemplate.query(sql, sarr, new RowMapper<DetailPo>() {
			@Override
			public DetailPo mapRow(ResultSet rs, int index) throws SQLException {
				DetailPo po = new DetailPo();
				po.setTitle(rs.getString("title"));
				po.setPv(rs.getInt("pv"));
				po.setUv(rs.getInt("uv"));
				po.setShare(rs.getInt("share"));
				po.setCollectcount(rs.getInt("collectcount"));
				po.setPraisecount(rs.getInt("praisecount"));
				return po;
			}});
		
		EasyUiDataGridResult result = new EasyUiDataGridResult(totalRow, list);
		return result;
	}
	
	/**
	 * 页面访问量详情含分享按周的list数据
	 */
	@Override
	public EasyUiDataGridResult weekDetailFxList(Integer page, Integer rows, Date startDate, Date endDate, String tableName, String tableName2, String segment) {
		DateTime ds = new DateTime(startDate);	
		DateTime de = new DateTime(endDate);
		
		int minus = ds.getDayOfWeek() - 1;
		DateTime m = ds.minusDays(minus); //得到刚刚好的本周的周一
		int p = de.getDayOfWeek();
		DateTime e = de.plusDays(7 - p);
		
		String sql0 = "select count(1) from (select count(1) from "+tableName+" a, "+ tableName2 +" b  where a.detailid = b."+segment+" and a.time >= '"+m.toString("yyyy-MM-dd")+"' and a.time <= '"+e.toString("yyyy-MM-dd")+"' group by a.detailid) x";
		int totalRow = jdbcTemplate.queryForInt(sql0);
		int start = (page - 1) * rows;
		
		String sql = "SELECT a.title, sum(pv) pv, sum(uv) uv, sum(share) share, b.praisecount, b.collectcount FROM "+tableName+" a, "+ tableName2 +" b where a.detailid = b."+segment+" and a.time >= ? and a.time <= ? group by a.detailid order by pv desc limit " + start + ", " + rows;
		String[] sarr = {m.toString("yyyy-MM-dd"), e.toString("yyyy-MM-dd")};
		
		List<DetailPo> list = jdbcTemplate.query(sql, sarr, new RowMapper<DetailPo>() {
			@Override
			public DetailPo mapRow(ResultSet rs, int index) throws SQLException {
				DetailPo po = new DetailPo();
				po.setTitle(rs.getString("title"));
				po.setPv(rs.getInt("pv"));
				po.setUv(rs.getInt("uv"));
				po.setShare(rs.getInt("share"));
				po.setCollectcount(rs.getInt("collectcount"));
				po.setPraisecount(rs.getInt("praisecount"));
				return po;
			}});
		
		EasyUiDataGridResult result = new EasyUiDataGridResult(totalRow, list);
		return result;
	}
	
	/**
	 * 页面访问量详情含分享按月的list数据
	 */
	@Override
	public EasyUiDataGridResult monDetailFxList(Integer page, Integer rows, Date startDate, Date endDate, String tableName, String tableName2, String segment) {
		DateTime startDateJoda = new DateTime(startDate);	
		DateTime endDateJoda = new DateTime(endDate);
		
		String sql0 = "select count(1) from (select count(1) from  "+tableName+" a, "+ tableName2 +" b  where a.detailid = b."+segment+" and a.time >= '"+startDateJoda.toString("yyyy-MM-01")+"' and a.time < '"+endDateJoda.plusMonths(1).toString("yyyy-MM-01")+"' group by a.detailid) x";
		int totalRow = jdbcTemplate.queryForInt(sql0);
		int start = (page - 1) * rows;
		
		String sql = "SELECT a.title, sum(pv) pv, sum(uv) uv, sum(share) share, b.praisecount, b.collectcount FROM "+tableName+" a, "+ tableName2 +" b where a.detailid = b."+segment+" and a.time >= ? and a.time < ? group by a.detailid order by pv desc limit " + start + ", " + rows;
		String[] sarr = {startDateJoda.toString("yyyy-MM-01"), endDateJoda.plusMonths(1).toString("yyyy-MM-01")};
		
		List<DetailPo> list = jdbcTemplate.query(sql, sarr, new RowMapper<DetailPo>() {
			@Override
			public DetailPo mapRow(ResultSet rs, int index) throws SQLException {
				DetailPo po = new DetailPo();
				po.setTitle(rs.getString("title"));
				po.setPv(rs.getInt("pv"));
				po.setUv(rs.getInt("uv"));
				po.setShare(rs.getInt("share"));
				po.setCollectcount(rs.getInt("collectcount"));
				po.setPraisecount(rs.getInt("praisecount"));
				return po;
			}});
		
		EasyUiDataGridResult result = new EasyUiDataGridResult(totalRow, list);
		return result;
	}
	
	/**
	 * 页面访问量详情含分享按年的list数据
	 */
	@Override
	public EasyUiDataGridResult yearDetailFxList(Integer page, Integer rows, Date startDate, Date endDate, String tableName, String tableName2, String segment) {
		DateTime startDateJoda = new DateTime(startDate);	
		DateTime endDateJoda = new DateTime(endDate);
		
		String sql0 = "select count(1) from (select count(1) from "+tableName+" a, "+ tableName2 +" b  where a.detailid = b."+segment+" and a.time >= '"+startDateJoda.toString("yyyy-01-01")+"' and a.time < '"+endDateJoda.plusYears(1).toString("yyyy-01-01")+"' group by a.detailid) x";
		int totalRow = jdbcTemplate.queryForInt(sql0);
		int start = (page - 1) * rows;
		
		String sql = "SELECT a.title, sum(pv) pv, sum(uv) uv, sum(share) share, b.praisecount, b.collectcount FROM "+tableName+" a, "+ tableName2 +" b where a.detailid = b."+segment+" and a.time >= ? and a.time < ? group by a.detailid order by pv desc limit " + start + ", " + rows;
		String[] sarr = {startDateJoda.toString("yyyy-01-01"), endDateJoda.plusYears(1).toString("yyyy-01-01")};
		
		List<DetailPo> list = jdbcTemplate.query(sql, sarr, new RowMapper<DetailPo>() {
			@Override
			public DetailPo mapRow(ResultSet rs, int index) throws SQLException {
				DetailPo po = new DetailPo();
				po.setTitle(rs.getString("title"));
				po.setPv(rs.getInt("pv"));
				po.setUv(rs.getInt("uv"));
				po.setShare(rs.getInt("share"));
				po.setCollectcount(rs.getInt("collectcount"));
				po.setPraisecount(rs.getInt("praisecount"));
				return po;
			}});
		
		EasyUiDataGridResult result = new EasyUiDataGridResult(totalRow, list);
		return result;
	}
	
	/**
	 * 页面访问量详情含分享导出按天
	 */
	@Override
	public List<DetailPo> dayDetailFxExport(Date startDate, Date endDate, String tableName) {
		DateTime startDateJoda = new DateTime(startDate);	
		DateTime endDateJoda = new DateTime(endDate);
		
		String sql = "SELECT title, sum(pv) pv, sum(uv) uv, sum(share) share FROM "+tableName+" where time >= ? and time <= ? group by detailid order by pv desc ";
		String[] sarr = {startDateJoda.toString("yyyy-MM-dd"), endDateJoda.toString("yyyy-MM-dd")};
		
		List<DetailPo> list = jdbcTemplate.query(sql, sarr, new RowMapper<DetailPo>() {
			@Override
			public DetailPo mapRow(ResultSet rs, int index) throws SQLException {
				DetailPo po = new DetailPo();
				po.setTitle(rs.getString("title"));
				po.setPv(rs.getInt("pv"));
				po.setUv(rs.getInt("uv"));
				po.setShare(rs.getInt("share"));
				return po;
			}});
		
		return list;
	}
	
	/**
	 * 页面访问量详情含分享导出按周
	 */
	@Override
	public List<DetailPo> weekDetailFxExport(Date startDate, Date endDate, String tableName) {
		DateTime ds = new DateTime(startDate);	
		DateTime de = new DateTime(endDate);
		
		int minus = ds.getDayOfWeek() - 1;
		DateTime m = ds.minusDays(minus); //得到刚刚好的本周的周一
		int p = de.getDayOfWeek();
		DateTime e = de.plusDays(7 - p);
		
		String sql = "SELECT title, sum(pv) pv, sum(uv) uv, sum(share) share FROM "+tableName+" where time >= ? and time <= ? group by detailid order by pv desc ";
		String[] sarr = {m.toString("yyyy-MM-dd"), e.toString("yyyy-MM-dd")};
		
		List<DetailPo> list = jdbcTemplate.query(sql, sarr, new RowMapper<DetailPo>() {
			@Override
			public DetailPo mapRow(ResultSet rs, int index) throws SQLException {
				DetailPo po = new DetailPo();
				po.setTitle(rs.getString("title"));
				po.setPv(rs.getInt("pv"));
				po.setUv(rs.getInt("uv"));
				po.setShare(rs.getInt("share"));
				return po;
			}});
		
		return list;
	}
	
	/**
	 * 页面访问量详情含分享导出按月
	 */
	@Override
	public List<DetailPo> monDetailFxExport(Date startDate, Date endDate, String tableName) {
		DateTime startDateJoda = new DateTime(startDate);	
		DateTime endDateJoda = new DateTime(endDate);
		
		String sql = "SELECT title, sum(pv) pv, sum(uv) uv, sum(share) share FROM "+tableName+" where time >= ? and time < ? group by detailid order by pv desc";
		String[] sarr = {startDateJoda.toString("yyyy-MM-01"), endDateJoda.plusMonths(1).toString("yyyy-MM-01")};
		
		List<DetailPo> list = jdbcTemplate.query(sql, sarr, new RowMapper<DetailPo>() {
			@Override
			public DetailPo mapRow(ResultSet rs, int index) throws SQLException {
				DetailPo po = new DetailPo();
				po.setTitle(rs.getString("title"));
				po.setPv(rs.getInt("pv"));
				po.setUv(rs.getInt("uv"));
				po.setShare(rs.getInt("share"));
				return po;
			}});
		
		return list;
	}
	
	/**
	 * 广告位页面访问量详情导出按年
	 */
	@Override
	public List<DetailPo> yearDetailFxExport(Date startDate, Date endDate, String tableName) {
		DateTime startDateJoda = new DateTime(startDate);	
		DateTime endDateJoda = new DateTime(endDate);
		
		String sql = "SELECT title, sum(pv) pv, sum(uv) uv, sum(share) share FROM "+tableName+" where time >= ? and time < ? group by detailid order by pv desc";
		String[] sarr = {startDateJoda.toString("yyyy-01-01"), endDateJoda.plusYears(1).toString("yyyy-01-01")};
		
		List<DetailPo> list = jdbcTemplate.query(sql, sarr, new RowMapper<DetailPo>() {
			@Override
			public DetailPo mapRow(ResultSet rs, int index) throws SQLException {
				DetailPo po = new DetailPo();
				po.setTitle(rs.getString("title"));
				po.setPv(rs.getInt("pv"));
				po.setUv(rs.getInt("uv"));
				po.setShare(rs.getInt("share"));
				return po;
			}});
		
		return list;
	}

	/**
	 * 文津页面访问总量按天的list数据
	 */
	@Override
	public EasyUiDataGridResult dayymfwWjList(Integer page, Integer rows, Date startDate, Date endDate) {
		List<WjYmfwPo> list = new ArrayList<WjYmfwPo>();
		DateTime sdt = new DateTime(startDate);
		DateTime edt = new DateTime(endDate);
		
		int totalRow = Days.daysBetween(sdt, edt).getDays() + 1; 
		
		int start = (page - 1) * rows;
		int limit = rows;
		
		int end = 0;
		if((start + limit) > totalRow) {
			end = totalRow;
		}else {
			end = start + limit;
		}
		
		for(int i = start; i < end; i++) {
			WjYmfwPo xpo = new WjYmfwPo();
			xpo.setTime(edt.minusDays(i).toString("yyyy-MM-dd"));
			String sql = "select pv, uv, share, audio from schewjamount where time = '"+edt.minusDays(i).toString("yyyy-MM-dd")+"'";
			List<Integer[]> qlist = jdbcTemplate.query(sql, new RowMapper<Integer[]>(){
				@Override
				public Integer[] mapRow(ResultSet rs, int index) throws SQLException {
					Integer[] intarr = new Integer[4];
					intarr[0] = rs.getInt(1);
					intarr[1] = rs.getInt(2);
					intarr[2] = rs.getInt(3);
					intarr[3] = rs.getInt(4);
					return intarr;
				}});
			
			if(null != qlist && qlist.size() > 0) {
				Integer[] integers = qlist.get(0);
				if(null != integers[0]) {
					xpo.setPv(integers[0]);
				}else {
					xpo.setPv(0);
				}
				
				if(null != integers[1]) {
					xpo.setUv(integers[1]);
				}else {
					xpo.setUv(0);
				}
				
				if(null != integers[2]) {
					xpo.setShare(integers[2]);
				}else {
					xpo.setShare(0);
				}
				
				if(null != integers[3]) {
					xpo.setAudio(integers[3]);
				}else {
					xpo.setAudio(0);
				}
				
			}else {
				xpo.setPv(0);
				xpo.setUv(0);
				xpo.setShare(0);
				xpo.setAudio(0);
			}
			
			list.add(xpo);
		}
		
		Integer[] intarr = daypvuvgxausum(startDate, endDate);
		Map<String, String> map = new HashMap<String, String>();
		map.put("time", "总计");
		map.put("pv", intarr[0] + "");
		map.put("uv", intarr[1] + "");
		map.put("share", intarr[2] + "");
		map.put("audio", intarr[3] + "");
		List<Map<String, String>> list2 = new ArrayList<Map<String, String>>();
		list2.add(map);
		EasyUiDataGridResultFoot result = new EasyUiDataGridResultFoot(totalRow, list, list2);
		return result;
	}
	
	/**
	 * 按天统计的pv、uv、share、audio做foot时求和用
	 */
	private Integer[] daypvuvgxausum(Date startDate, Date endDate) {
		DateTime sdt = new DateTime(startDate);
		DateTime edt = new DateTime(endDate);
		
		String sql1 = "select sum(pv), sum(uv), sum(share), sum(audio) from schewjamount where time >= '"+sdt.toString("yyyy-MM-dd")+"' and time <= '" +edt.toString("yyyy-MM-dd") + "'"; 
		List<WjYmfwPo> list = jdbcTemplate.query(sql1, new RowMapper<WjYmfwPo>(){

			@Override
			public WjYmfwPo mapRow(ResultSet rs, int index) throws SQLException {
				WjYmfwPo po = new WjYmfwPo();
				po.setPv(rs.getInt(1));
				po.setUv(rs.getInt(2));
				po.setShare(rs.getInt(3));
				po.setAudio(rs.getInt(4));
				return po;
			}
		});
		
		Integer[] res = new Integer[]{0, 0, 0, 0};
		if(list != null && list.size() > 0) {
			res[0] = list.get(0).getPv();
			res[1] = list.get(0).getUv();
			res[2] = list.get(0).getShare();
			res[3] = list.get(0).getAudio();
		}
		return res;
	}

	/**
	 * 文津页面访问总量按月的list数据
	 */
	@Override
	public EasyUiDataGridResult monymfwWjList(Integer page, Integer rows, Date startDate, Date endDate) {
		List<WjYmfwPo> list = new ArrayList<WjYmfwPo>();
		DateTime sdt = new DateTime(startDate);
		DateTime edt = new DateTime(endDate);
		
		int totalRow = Months.monthsBetween(sdt, edt).getMonths() + 1;
		
		int start = (page - 1) * rows;
		int limit = rows;
		
		int end = 0;
		if((start + limit) > totalRow) {
			end = totalRow;
		}else {
			end = start + limit;
		}
		
		for(int i = start; i < end; i++) {
			WjYmfwPo xpo = new WjYmfwPo();
			xpo.setTime(edt.minusMonths(i).toString("yyyy-MM"));
			
			String sql = "select sum(pv), sum(uv), sum(share), sum(audio) from schewjamount where time >= '"+edt.minusMonths(i).toString("yyyy-MM-01")+"' and time < '" + edt.minusMonths(i).plusMonths(1).toString("yyyy-MM-01") + "'";
			List<Integer[]> qlist = jdbcTemplate.query(sql, new RowMapper<Integer[]>(){
				@Override
				public Integer[] mapRow(ResultSet rs, int index) throws SQLException {
					Integer[] intarr = new Integer[4];
					intarr[0] = rs.getInt(1);
					intarr[1] = rs.getInt(2);
					intarr[2] = rs.getInt(3);
					intarr[3] = rs.getInt(4);
					return intarr;
				}});
			
			Integer[] integers = qlist.get(0);
			xpo.setPv(integers[0]);
			xpo.setUv(integers[1]);
			xpo.setShare(integers[2]);
			xpo.setAudio(integers[3]);
			list.add(xpo);
		}
		
		Integer[] intarr = monpvuvgxausum(startDate, endDate);
		Map<String, String> map = new HashMap<String, String>();
		map.put("time", "总计");
		map.put("pv", intarr[0] + "");
		map.put("uv", intarr[1] + "");
		map.put("share", intarr[2] + "");
		map.put("audio", intarr[3] + "");
		List<Map<String, String>> list2 = new ArrayList<Map<String, String>>();
		list2.add(map);
		EasyUiDataGridResultFoot result = new EasyUiDataGridResultFoot(totalRow, list, list2);
		return result;
	}
	
	/**
	 * 按月统计的pv、uv、share、audio做foot时求和用
	 */
	private Integer[] monpvuvgxausum(Date startDate, Date endDate) {
		DateTime sdt = new DateTime(startDate);
		DateTime edt = new DateTime(endDate);
		
		String sql1 = "select sum(pv), sum(uv), sum(share), sum(audio) from schewjamount where time >= '"+sdt.toString("yyyy-MM-01")+"' and time < '" +edt.plusMonths(1).toString("yyyy-MM-01") + "'"; 
		List<WjYmfwPo> list = jdbcTemplate.query(sql1, new RowMapper<WjYmfwPo>(){
			@Override
			public WjYmfwPo mapRow(ResultSet rs, int index) throws SQLException {
				WjYmfwPo po = new WjYmfwPo();
				po.setPv(rs.getInt(1));
				po.setUv(rs.getInt(2));
				po.setShare(rs.getInt(3));
				po.setAudio(rs.getInt(4));
				return po;
			}
		});
		
		Integer[] res = new Integer[]{0, 0, 0, 0};
		if(list != null && list.size() > 0) {
			res[0] = list.get(0).getPv();
			res[1] = list.get(0).getUv();
			res[2] = list.get(0).getShare();
			res[3] = list.get(0).getAudio();
		}
		
		return res;
	}

	/**
	 * 文津页面访问总量按周的list数据
	 */
	@Override
	public EasyUiDataGridResult weekymfwWjList(Integer page, Integer rows, Date startDate, Date endDate) {
		List<WjYmfwPo> list = new ArrayList<WjYmfwPo>();
		DateTime ds = new DateTime(startDate);
		DateTime de = new DateTime(endDate);
		
		int minus = ds.getDayOfWeek() - 1;
		DateTime m = ds.minusDays(minus); // 得到刚刚好的本周的周一
		int p = de.getDayOfWeek();
		DateTime e = de.plusDays(7 - p);
		int weeks = Weeks.weeksBetween(m, e).getWeeks();
		
		int totalRow = weeks + 1;
		int start = (page - 1) * rows;
		int limit = rows;
		int end = 0;
		if((start + limit) > totalRow) {
			end = totalRow;
		}else {
			end = start + limit;
		}
		
		for(int i = start; i < end; i++) {
			WjYmfwPo xpo = new WjYmfwPo();
			String str = "第" + m.plusWeeks(i).getWeekOfWeekyear() + "周  " + m.plusWeeks(i).toString("yyyy-MM-dd") + " -- " + m.plusWeeks(i).plusDays(6).toString("yyyy-MM-dd");
			xpo.setTime(str);
			String sql = "select sum(pv), sum(uv), sum(share), sum(audio) from schewjamount where time >= '"+m.plusWeeks(i).toString("yyyy-MM-dd")+"' and time < '" + m.plusWeeks(i + 1).toString("yyyy-MM-dd") + "'";
			List<Integer[]> qlist = jdbcTemplate.query(sql, new RowMapper<Integer[]>(){
				@Override
				public Integer[] mapRow(ResultSet rs, int index) throws SQLException {
					Integer[] intarr = new Integer[4];
					intarr[0] = rs.getInt(1);
					intarr[1] = rs.getInt(2);
					intarr[2] = rs.getInt(3);
					intarr[3] = rs.getInt(4);
					return intarr;
				}});
			
			Integer[] integers = qlist.get(0);
			xpo.setPv(integers[0]);
			xpo.setUv(integers[1]);
			xpo.setShare(integers[2]);
			xpo.setAudio(integers[3]);
			list.add(xpo);
		}
		
		Integer[] intarr = weekpvuvgxausum(startDate, endDate);
		Map<String, String> map = new HashMap<String, String>();
		map.put("time", "总计");
		map.put("pv", intarr[0] + "");
		map.put("uv", intarr[1] + "");
		map.put("share", intarr[2] + "");
		map.put("audio", intarr[3] + "");
		List<Map<String, String>> list2 = new ArrayList<Map<String, String>>();
		list2.add(map);
		EasyUiDataGridResultFoot result = new EasyUiDataGridResultFoot(totalRow, list, list2);
		return result;
	}
	
	/**
	 * 按周统计的pv、uv、share、audio做foot时求和用
	 */
	private Integer[] weekpvuvgxausum(Date startDate, Date endDate) {
		DateTime ds = new DateTime(startDate);
		DateTime de = new DateTime(endDate);
		
		int minus = ds.getDayOfWeek() - 1;
		DateTime m = ds.minusDays(minus); // 得到刚刚好的本周的周一
		int p = de.getDayOfWeek();
		DateTime e = de.plusDays(7 - p);
		int weeks = Weeks.weeksBetween(m, e).getWeeks();
		
		int totalRow = weeks + 1;
		Integer[] res = new Integer[]{0, 0, 0, 0};
		
		for(int i = 0; i < totalRow; i++) {
			String sql = "select sum(pv), sum(uv), sum(share), sum(audio) from schewjamount where time >= '"+m.plusWeeks(i).toString("yyyy-MM-dd")+"' and time < '" + m.plusWeeks(i + 1).toString("yyyy-MM-dd") + "'";
			List<Integer[]> qlist = jdbcTemplate.query(sql, new RowMapper<Integer[]>(){
				@Override
				public Integer[] mapRow(ResultSet rs, int index) throws SQLException {
					Integer[] intarr = new Integer[4];
					intarr[0] = rs.getInt(1);
					intarr[1] = rs.getInt(2);
					intarr[2] = rs.getInt(3);
					intarr[3] = rs.getInt(4);
					return intarr;
				}});
			
			Integer[] integers = qlist.get(0);
			res[0] += integers[0];
			res[1] += integers[1];
			res[2] += integers[2];
			res[3] += integers[3];
		}
		
		return res;
	}

	/**
	 * 文津页面访问总量按年的list数据
	 */
	@Override
	public EasyUiDataGridResult yearymfwWjList(Integer page, Integer rows, Date startDate, Date endDate) {
		List<WjYmfwPo> list = new ArrayList<WjYmfwPo>();
		DateTime sdt = new DateTime(startDate);
		DateTime edt = new DateTime(endDate);
		
		int totalRow = Years.yearsBetween(sdt, edt).getYears() + 1;
		
		int start = (page - 1) * rows;
		int limit = rows;
		
		int end = 0;
		if((start + limit) > totalRow) {
			end = totalRow;
		}else {
			end = start + limit;
		}
		
		for(int i = start; i < end; i++) {
			WjYmfwPo xpo = new WjYmfwPo();
			xpo.setTime(edt.minusYears(i).toString("yyyy"));
			String sql = "select sum(pv), sum(uv), sum(share), sum(audio) from schewjamount where time >= '"+edt.minusYears(i).toString("yyyy-01-01")+"' and time < '" + edt.minusYears(i).plusYears(1).toString("yyyy-01-01") + "'";
			List<Integer[]> qlist = jdbcTemplate.query(sql, new RowMapper<Integer[]>(){
				@Override
				public Integer[] mapRow(ResultSet rs, int index) throws SQLException {
					Integer[] intarr = new Integer[4];
					intarr[0] = rs.getInt(1);
					intarr[1] = rs.getInt(2);
					intarr[2] = rs.getInt(3);
					intarr[3] = rs.getInt(4);
					return intarr;
				}});
			
			Integer[] integers = qlist.get(0);
			xpo.setPv(integers[0]);
			xpo.setUv(integers[1]);
			xpo.setShare(integers[2]);
			xpo.setAudio(integers[3]);
			list.add(xpo);
		}
		
		Integer[] intarr = yearpvuvgxausum(startDate, endDate);
		Map<String, String> map = new HashMap<String, String>();
		map.put("time", "总计");
		map.put("pv", intarr[0] + "");
		map.put("uv", intarr[1] + "");
		map.put("share", intarr[2] + "");
		map.put("audio", intarr[3] + "");
		List<Map<String, String>> list2 = new ArrayList<Map<String, String>>();
		list2.add(map);
		EasyUiDataGridResultFoot result = new EasyUiDataGridResultFoot(totalRow, list, list2);
		return result;
	}
	
	/**
	 * 按年统计的pv、uv、share、audio做foot时求和用
	 */
	private Integer[] yearpvuvgxausum(Date startDate, Date endDate) {
		DateTime sdt = new DateTime(startDate);
		DateTime edt = new DateTime(endDate);
		
		String sql1 = "select sum(pv), sum(uv), sum(share), sum(audio) from schewjamount where time >= '"+sdt.toString("yyyy-01-01")+"' and time < '" +edt.plusYears(1).toString("yyyy-01-01") + "'"; 
		List<WjYmfwPo> list = jdbcTemplate.query(sql1, new RowMapper<WjYmfwPo>(){

			@Override
			public WjYmfwPo mapRow(ResultSet rs, int index) throws SQLException {
				WjYmfwPo po = new WjYmfwPo();
				po.setPv(rs.getInt(1));
				po.setUv(rs.getInt(2));
				po.setShare(rs.getInt(3));
				po.setAudio(rs.getInt(4));
				return po;
			}
		});
		
		Integer[] res = new Integer[]{0, 0, 0, 0};
		if(list != null && list.size() > 0) {
			res[0] = list.get(0).getPv();
			res[1] = list.get(0).getUv();
			res[2] = list.get(0).getShare();
			res[3] = list.get(0).getAudio();
		}
		
		return res;
	}

	/**
	 * 文津页面访问导出每天的
	 */
	@Override
	public List<WjYmfwPo> dayWjymfwfxExport(Date startDate, Date endDate) {
		List<WjYmfwPo> list = new ArrayList<WjYmfwPo>();
		DateTime sdt = new DateTime(startDate);
		DateTime edt = new DateTime(endDate);
		int totalRow = Days.daysBetween(sdt, edt).getDays() + 1; 
		
		Integer pvsum = new Integer(0);
		Integer uvsum = new Integer(0);
		Integer sharesum = new Integer(0);
		Integer audiosum = new Integer(0);
		
		for(int i = 0; i < totalRow; i++) {
			WjYmfwPo xpo = new WjYmfwPo();
			xpo.setTime(edt.minusDays(i).toString("yyyy-MM-dd"));
			String sql = "select pv, uv, share, audio from schewjamount where time = '"+edt.minusDays(i).toString("yyyy-MM-dd")+"'";
			List<Integer[]> qlist = jdbcTemplate.query(sql, new RowMapper<Integer[]>(){
				@Override
				public Integer[] mapRow(ResultSet rs, int index) throws SQLException {
					Integer[] intarr = new Integer[4];
					intarr[0] = rs.getInt(1);
					intarr[1] = rs.getInt(2);
					intarr[2] = rs.getInt(3);
					intarr[3] = rs.getInt(4);
					return intarr;
				}});
			
			if(null != qlist && qlist.size() > 0) {
				Integer[] integers = qlist.get(0);
				if(null != integers[0]) {
					xpo.setPv(integers[0]);
					pvsum += integers[0];
				}else {
					xpo.setPv(0);
					pvsum += 0;
				}
				
				if(null != integers[1]) {
					xpo.setUv(integers[1]);
					uvsum += integers[1];
				}else {
					xpo.setUv(0);
					uvsum += 0;
				}
				
				if(null != integers[2]) {
					xpo.setShare(integers[2]);
					sharesum += integers[2];
				}else {
					xpo.setShare(0);
					sharesum += 0;
				}
				
				if(null != integers[3]) {
					xpo.setAudio(integers[3]);
					audiosum += integers[3];
				}else {
					xpo.setAudio(0);
					audiosum += 0;
				}
				
			}else {
				xpo.setPv(0);
				xpo.setUv(0);
				xpo.setShare(0);
				xpo.setAudio(0);
				pvsum += 0;
				uvsum += 0;
				sharesum += 0;
				audiosum += 0;
			}
			
			list.add(xpo);
		}
		
		WjYmfwPo xpo = new WjYmfwPo();
		xpo.setTime("总计");
		xpo.setPv(pvsum);
		xpo.setUv(uvsum);
		xpo.setShare(sharesum);
		xpo.setAudio(audiosum);
		list.add(xpo);
		
		return list;
	}

	/**
	 * 文津页面访问导出每周的
	 */
	@Override
	public List<WjYmfwPo> weekWjymfwfxExport(Date startDate, Date endDate) {
		List<WjYmfwPo> list = new ArrayList<WjYmfwPo>();
		
		DateTime ds = new DateTime(startDate);
		DateTime de = new DateTime(endDate);
		
		int minus = ds.getDayOfWeek() - 1;
		DateTime m = ds.minusDays(minus); // 得到刚刚好的本周的周一
		int p = de.getDayOfWeek();
		DateTime e = de.plusDays(7 - p);
		int weeks = Weeks.weeksBetween(m, e).getWeeks();
		
		int totalRow = weeks + 1;
		Integer pvsum = new Integer(0);
		Integer uvsum = new Integer(0);
		Integer sharesum = new Integer(0);
		Integer audiosum = new Integer(0);
		
		for(int i = 0; i < totalRow; i++) {
			WjYmfwPo xpo = new WjYmfwPo();
			String str = "第" + m.plusWeeks(i).getWeekOfWeekyear() + "周  " + m.plusWeeks(i).toString("yyyy-MM-dd") + " -- " + m.plusWeeks(i).plusDays(6).toString("yyyy-MM-dd");
			xpo.setTime(str);
			
			String sql = "select sum(pv), sum(uv), sum(share), sum(audio) from schewjamount where time >= '"+m.plusWeeks(i).toString("yyyy-MM-dd")+"' and time < '" + m.plusWeeks(i + 1).toString("yyyy-MM-dd") + "'";
			List<Integer[]> qlist = jdbcTemplate.query(sql, new RowMapper<Integer[]>(){
				@Override
				public Integer[] mapRow(ResultSet rs, int index) throws SQLException {
					Integer[] intarr = new Integer[4];
					intarr[0] = rs.getInt(1);
					intarr[1] = rs.getInt(2);
					intarr[2] = rs.getInt(3);
					intarr[3] = rs.getInt(4);
					return intarr;
				}});
			
			Integer[] integers = qlist.get(0);
			xpo.setPv(integers[0]);
			xpo.setUv(integers[1]);
			xpo.setShare(integers[2]);
			xpo.setAudio(integers[3]);
			pvsum += integers[0];
			uvsum += integers[1];
			sharesum += integers[2];
			audiosum += integers[3];
			list.add(xpo);
		}
		
		WjYmfwPo xpo = new WjYmfwPo();
		xpo.setTime("总计");
		xpo.setPv(pvsum);
		xpo.setUv(uvsum);
		xpo.setShare(sharesum);
		xpo.setAudio(audiosum);
		list.add(xpo);
		
		return list;
	}

	/**
	 * 文津页面访问导出每月的
	 */
	@Override
	public List<WjYmfwPo> monWjymfwfxExport(Date startDate, Date endDate) {
		List<WjYmfwPo> list = new ArrayList<WjYmfwPo>();
		DateTime sdt = new DateTime(startDate);
		DateTime edt = new DateTime(endDate);
		int totalRow = Months.monthsBetween(sdt, edt).getMonths() + 1;
		
		Integer pvsum = new Integer(0);
		Integer uvsum = new Integer(0);
		Integer sharesum = new Integer(0);
		Integer audiosum = new Integer(0);
		
		for(int i = 0; i < totalRow; i++) {
			WjYmfwPo xpo = new WjYmfwPo();
			xpo.setTime(edt.minusMonths(i).toString("yyyy-MM"));

			String sql = "select sum(pv), sum(uv), sum(share), sum(audio) from schewjamount where time >= '"+edt.minusMonths(i).toString("yyyy-MM-01")+"' and time < '" + edt.minusMonths(i).plusMonths(1).toString("yyyy-MM-01") + "'";
			List<Integer[]> qlist = jdbcTemplate.query(sql, new RowMapper<Integer[]>(){
				@Override
				public Integer[] mapRow(ResultSet rs, int index) throws SQLException {
					Integer[] intarr = new Integer[4];
					intarr[0] = rs.getInt(1);
					intarr[1] = rs.getInt(2);
					intarr[2] = rs.getInt(3);
					intarr[3] = rs.getInt(4);
					return intarr;
				}});
			
			Integer[] integers = qlist.get(0);
			xpo.setPv(integers[0]);
			xpo.setUv(integers[1]);
			xpo.setShare(integers[2]);
			xpo.setAudio(integers[3]);
			pvsum += integers[0];
			uvsum += integers[1];
			sharesum += integers[2];
			audiosum += integers[3];
			list.add(xpo);
		}
		
		WjYmfwPo xpo = new WjYmfwPo();
		xpo.setTime("总计");
		xpo.setPv(pvsum);
		xpo.setUv(uvsum);
		xpo.setShare(sharesum);
		xpo.setAudio(audiosum);
		list.add(xpo);
		
		return list;
	}

	/**
	 * 文津页面访问导出每年的
	 */
	@Override
	public List<WjYmfwPo> yearWjymfwfxExport(Date startDate, Date endDate) {
		List<WjYmfwPo> list = new ArrayList<WjYmfwPo>();
		DateTime sdt = new DateTime(startDate);
		DateTime edt = new DateTime(endDate);
		int totalRow = Years.yearsBetween(sdt, edt).getYears() + 1;
		
		Integer pvsum = new Integer(0);
		Integer uvsum = new Integer(0);
		Integer sharesum = new Integer(0);
		Integer audiosum = new Integer(0);
		
		for(int i = 0; i < totalRow; i++) {
			WjYmfwPo xpo = new WjYmfwPo();
			xpo.setTime(edt.minusYears(i).toString("yyyy"));
			
			String sql = "select sum(pv), sum(uv), sum(share), sum(audio) from schewjamount where time >= '"+edt.minusYears(i).toString("yyyy-01-01")+"' and time < '" + edt.minusYears(i).plusYears(1).toString("yyyy-01-01") + "'";
			List<Integer[]> qlist = jdbcTemplate.query(sql, new RowMapper<Integer[]>(){
				@Override
				public Integer[] mapRow(ResultSet rs, int index) throws SQLException {
					Integer[] intarr = new Integer[3];
					intarr[0] = rs.getInt(1);
					intarr[1] = rs.getInt(2);
					intarr[2] = rs.getInt(3);
					intarr[3] = rs.getInt(4);
					return intarr;
				}});
			
			Integer[] integers = qlist.get(0);
			xpo.setPv(integers[0]);
			xpo.setUv(integers[1]);
			xpo.setShare(integers[2]);
			xpo.setAudio(integers[3]);
			pvsum += integers[0];
			uvsum += integers[1];
			sharesum += integers[2];
			audiosum += integers[3];
			list.add(xpo);
		}
		
		WjYmfwPo xpo = new WjYmfwPo();
		xpo.setTime("总计");
		xpo.setPv(pvsum);
		xpo.setUv(uvsum);
		xpo.setShare(sharesum);
		xpo.setAudio(audiosum);
		list.add(xpo);
		return list;
	}

	/**
	 * 文津页面访问量详情按天的list数据
	 */
	@Override
	public EasyUiDataGridResult dayWjDetailFxList(Integer page, Integer rows, Date startDate, Date endDate) {
		DateTime startDateJoda = new DateTime(startDate);	
		DateTime endDateJoda = new DateTime(endDate);
		
		String sql0 = "select count(1) from (select count(1) from schewjdetail where time >= '"+startDateJoda.toString("yyyy-MM-dd")+"' and time <= '"+endDateJoda.toString("yyyy-MM-dd")+"' group by detailid) x";
		int totalRow = jdbcTemplate.queryForInt(sql0);
		int start = (page - 1) * rows;
		
		String sql = "SELECT title, sum(pv) pv, sum(uv) uv, sum(share) share, sum(audio) audio, detailid FROM schewjdetail where time >= ? and time <= ? group by detailid order by pv desc limit " + start + ", " + rows;
		String[] sarr = {startDateJoda.toString("yyyy-MM-dd"), endDateJoda.toString("yyyy-MM-dd")};
		
		List<WjDetailPo> list = jdbcTemplate.query(sql, sarr, new RowMapper<WjDetailPo>() {
			@Override
			public WjDetailPo mapRow(ResultSet rs, int index) throws SQLException {
				WjDetailPo po = new WjDetailPo();
				po.setTitle(rs.getString("title"));
				po.setPv(rs.getInt("pv"));
				po.setUv(rs.getInt("uv"));
				po.setShare(rs.getInt("share"));
				po.setAudio(rs.getInt("audio"));
				po.setDetailid(rs.getString("detailid"));
				return po;
			}});
		
		EasyUiDataGridResult result = new EasyUiDataGridResult(totalRow, list);
		return result;
	}

	/**
	 * 文津页面访问量详情按周的list数据
	 */
	@Override
	public EasyUiDataGridResult weekWjDetailFxList(Integer page, Integer rows, Date startDate, Date endDate) {
		DateTime ds = new DateTime(startDate);	
		DateTime de = new DateTime(endDate);
		
		int minus = ds.getDayOfWeek() - 1;
		DateTime m = ds.minusDays(minus); //得到刚刚好的本周的周一
		int p = de.getDayOfWeek();
		DateTime e = de.plusDays(7 - p);
		
		String sql0 = "select count(1) from (select count(1) from schewjdetail where time >= '"+m.toString("yyyy-MM-dd")+"' and time <= '"+e.toString("yyyy-MM-dd")+"' group by detailid) x";
		int totalRow = jdbcTemplate.queryForInt(sql0);
		int start = (page - 1) * rows;
		
		String sql = "SELECT title, sum(pv) pv, sum(uv) uv, sum(share) share, sum(audio) audio, detailid FROM schewjdetail where time >= ? and time <= ? group by detailid order by pv desc limit " + start + ", " + rows;
		String[] sarr = {m.toString("yyyy-MM-dd"), e.toString("yyyy-MM-dd")};
		
		List<WjDetailPo> list = jdbcTemplate.query(sql, sarr, new RowMapper<WjDetailPo>() {
			@Override
			public WjDetailPo mapRow(ResultSet rs, int index) throws SQLException {
				WjDetailPo po = new WjDetailPo();
				po.setTitle(rs.getString("title"));
				po.setPv(rs.getInt("pv"));
				po.setUv(rs.getInt("uv"));
				po.setShare(rs.getInt("share"));
				po.setAudio(rs.getInt("audio"));
				po.setDetailid(rs.getString("detailid"));
				return po;
			}});
		
		EasyUiDataGridResult result = new EasyUiDataGridResult(totalRow, list);
		return result;
	}

	/**
	 * 文津页面访问量详情按月的list数据
	 */
	@Override
	public EasyUiDataGridResult monWjDetailFxList(Integer page, Integer rows, Date startDate, Date endDate) {
		DateTime startDateJoda = new DateTime(startDate);	
		DateTime endDateJoda = new DateTime(endDate);
		
		String sql0 = "select count(1) from (select count(1) from schewjdetail where time >= '"+startDateJoda.toString("yyyy-MM-01")+"' and time < '"+endDateJoda.plusMonths(1).toString("yyyy-MM-01")+"' group by detailid) x";
		int totalRow = jdbcTemplate.queryForInt(sql0);
		int start = (page - 1) * rows;
		
		String sql = "SELECT title, sum(pv) pv, sum(uv) uv, sum(share) share, sum(audio) audio, detailid FROM schewjdetail where time >= ? and time < ? group by detailid order by pv desc limit " + start + ", " + rows;
		String[] sarr = {startDateJoda.toString("yyyy-MM-01"), endDateJoda.plusMonths(1).toString("yyyy-MM-01")};
		
		List<WjDetailPo> list = jdbcTemplate.query(sql, sarr, new RowMapper<WjDetailPo>() {
			@Override
			public WjDetailPo mapRow(ResultSet rs, int index) throws SQLException {
				WjDetailPo po = new WjDetailPo();
				po.setTitle(rs.getString("title"));
				po.setPv(rs.getInt("pv"));
				po.setUv(rs.getInt("uv"));
				po.setShare(rs.getInt("share"));
				po.setAudio(rs.getInt("audio"));
				po.setDetailid(rs.getString("detailid"));
				return po;
			}});
		
		EasyUiDataGridResult result = new EasyUiDataGridResult(totalRow, list);
		return result;
	}

	/**
	 * 文津页面访问量详情按年的list数据
	 */
	@Override
	public EasyUiDataGridResult yearWjDetailFxList(Integer page, Integer rows, Date startDate, Date endDate) {
		DateTime startDateJoda = new DateTime(startDate);	
		DateTime endDateJoda = new DateTime(endDate);
		
		String sql0 = "select count(1) from (select count(1) from schewjdetail where time >= '"+startDateJoda.toString("yyyy-01-01")+"' and time < '"+endDateJoda.plusYears(1).toString("yyyy-01-01")+"' group by detailid) x";
		int totalRow = jdbcTemplate.queryForInt(sql0);
		int start = (page - 1) * rows;
		
		String sql = "SELECT title, sum(pv) pv, sum(uv) uv, sum(share) share, sum(audio) audio, detailid FROM schewjdetail where time >= ? and time < ? group by detailid order by pv desc limit " + start + ", " + rows;
		String[] sarr = {startDateJoda.toString("yyyy-01-01"), endDateJoda.plusYears(1).toString("yyyy-01-01")};
		
		List<WjDetailPo> list = jdbcTemplate.query(sql, sarr, new RowMapper<WjDetailPo>() {
			@Override
			public WjDetailPo mapRow(ResultSet rs, int index) throws SQLException {
				WjDetailPo po = new WjDetailPo();
				po.setTitle(rs.getString("title"));
				po.setPv(rs.getInt("pv"));
				po.setUv(rs.getInt("uv"));
				po.setShare(rs.getInt("share"));
				po.setAudio(rs.getInt("audio"));
				po.setDetailid(rs.getString("detailid"));
				return po;
			}});
		
		EasyUiDataGridResult result = new EasyUiDataGridResult(totalRow, list);
		return result;
	}

	/**
	 * 文津页面访问量详情导出按天
	 */
	@Override
	public List<WjDetailPo> dayWjDetailFxExport(Date startDate, Date endDate) {
		DateTime startDateJoda = new DateTime(startDate);	
		DateTime endDateJoda = new DateTime(endDate);
		
		String sql = "SELECT title, sum(pv) pv, sum(uv) uv, sum(share) share, sum(audio) audio FROM schewjdetail where time >= ? and time <= ? group by detailid order by pv desc ";
		String[] sarr = {startDateJoda.toString("yyyy-MM-dd"), endDateJoda.toString("yyyy-MM-dd")};
		
		List<WjDetailPo> list = jdbcTemplate.query(sql, sarr, new RowMapper<WjDetailPo>() {
			@Override
			public WjDetailPo mapRow(ResultSet rs, int index) throws SQLException {
				WjDetailPo po = new WjDetailPo();
				po.setTitle(rs.getString("title"));
				po.setPv(rs.getInt("pv"));
				po.setUv(rs.getInt("uv"));
				po.setShare(rs.getInt("share"));
				po.setAudio(rs.getInt("audio"));
				return po;
			}});
		
		return list;
	}

	/**
	 * 文津页面访问量详情导出按周
	 */
	@Override
	public List<WjDetailPo> weekWjDetailFxExport(Date startDate, Date endDate) {
		DateTime ds = new DateTime(startDate);	
		DateTime de = new DateTime(endDate);
		
		int minus = ds.getDayOfWeek() - 1;
		DateTime m = ds.minusDays(minus); //得到刚刚好的本周的周一
		int p = de.getDayOfWeek();
		DateTime e = de.plusDays(7 - p);
		
		String sql = "SELECT title, sum(pv) pv, sum(uv) uv, sum(share) share, sum(audio) audio FROM schewjdetail where time >= ? and time <= ? group by detailid order by pv desc ";
		String[] sarr = {m.toString("yyyy-MM-dd"), e.toString("yyyy-MM-dd")};
		
		List<WjDetailPo> list = jdbcTemplate.query(sql, sarr, new RowMapper<WjDetailPo>() {
			@Override
			public WjDetailPo mapRow(ResultSet rs, int index) throws SQLException {
				WjDetailPo po = new WjDetailPo();
				po.setTitle(rs.getString("title"));
				po.setPv(rs.getInt("pv"));
				po.setUv(rs.getInt("uv"));
				po.setShare(rs.getInt("share"));
				po.setAudio(rs.getInt("audio"));
				return po;
			}});
		
		return list;
	}

	/**
	 * 文津页面访问量详情导出按月
	 */
	@Override
	public List<WjDetailPo> monWjDetailFxExport(Date startDate, Date endDate) {
		DateTime startDateJoda = new DateTime(startDate);	
		DateTime endDateJoda = new DateTime(endDate);
		
		String sql = "SELECT title, sum(pv) pv, sum(uv) uv, sum(share) share, sum(audio) audio FROM schewjdetail where time >= ? and time < ? group by detailid order by pv desc";
		String[] sarr = {startDateJoda.toString("yyyy-MM-01"), endDateJoda.plusMonths(1).toString("yyyy-MM-01")};
		
		List<WjDetailPo> list = jdbcTemplate.query(sql, sarr, new RowMapper<WjDetailPo>() {
			@Override
			public WjDetailPo mapRow(ResultSet rs, int index) throws SQLException {
				WjDetailPo po = new WjDetailPo();
				po.setTitle(rs.getString("title"));
				po.setPv(rs.getInt("pv"));
				po.setUv(rs.getInt("uv"));
				po.setShare(rs.getInt("share"));
				po.setAudio(rs.getInt("audio"));
				return po;
			}});
		
		return list;
	}

	/**
	 * 文津页面访问量详情导出按年
	 */
	@Override
	public List<WjDetailPo> yearWjDetailFxExport(Date startDate, Date endDate) {
		DateTime startDateJoda = new DateTime(startDate);	
		DateTime endDateJoda = new DateTime(endDate);
		
		String sql = "SELECT title, sum(pv) pv, sum(uv) uv, sum(share) share, sum(audio) audio FROM schewjdetail where time >= ? and time < ? group by detailid order by pv desc";
		String[] sarr = {startDateJoda.toString("yyyy-01-01"), endDateJoda.plusYears(1).toString("yyyy-01-01")};
		
		List<WjDetailPo> list = jdbcTemplate.query(sql, sarr, new RowMapper<WjDetailPo>() {
			@Override
			public WjDetailPo mapRow(ResultSet rs, int index) throws SQLException {
				WjDetailPo po = new WjDetailPo();
				po.setTitle(rs.getString("title"));
				po.setPv(rs.getInt("pv"));
				po.setUv(rs.getInt("uv"));
				po.setShare(rs.getInt("share"));
				po.setAudio(rs.getInt("audio"));
				return po;
			}});
		
		return list;
	}

	/**
	 * 个人访问量详情
	 */
	@Override
	public List<PersonPo> personDetailtableData(Date startDate, Date endDate, String status) {
		List<PersonPo> reslist = null;
		String[] sarr = new String[2];
		String sql = "";
		
		if ("day".equalsIgnoreCase(status)) {
			DateTime startDateJoda = new DateTime(startDate);	
			DateTime endDateJoda = new DateTime(endDate);
			sarr = new String[]{startDateJoda.toString("yyyy-MM-dd"), endDateJoda.toString("yyyy-MM-dd")};
			sql = "SELECT detailid, sum(pv) pv, sum(uv) uv FROM schepersondetail where time >= ? and time <= ? group by detailid ";
			
		} else if ("week".equalsIgnoreCase(status)) {
			DateTime ds = new DateTime(startDate);	
			DateTime de = new DateTime(endDate);
			int minus = ds.getDayOfWeek() - 1;
			DateTime m = ds.minusDays(minus); //得到刚刚好的本周的周一
			int p = de.getDayOfWeek();
			DateTime e = de.plusDays(7 - p);
			sarr = new String[]{m.toString("yyyy-MM-dd"), e.toString("yyyy-MM-dd")};
			sql = "SELECT detailid, sum(pv) pv, sum(uv) uv FROM schepersondetail where time >= ? and time <= ? group by detailid ";
			
		} else if ("month".equalsIgnoreCase(status)) {
			DateTime startDateJoda = new DateTime(startDate);	
			DateTime endDateJoda = new DateTime(endDate);
			sarr = new String[]{startDateJoda.toString("yyyy-MM-01"), endDateJoda.plusMonths(1).toString("yyyy-MM-01")};
			sql = "SELECT detailid, sum(pv) pv, sum(uv) uv FROM schepersondetail where time >= ? and time < ? group by detailid ";
			
		} else if ("year".equalsIgnoreCase(status)) {
			DateTime startDateJoda = new DateTime(startDate);	
			DateTime endDateJoda = new DateTime(endDate);
			sarr = new String[]{startDateJoda.toString("yyyy-01-01"), endDateJoda.plusYears(1).toString("yyyy-01-01")};
			sql = "SELECT detailid, sum(pv) pv, sum(uv) uv FROM schepersondetail where time >= ? and time < ? group by detailid ";
			
		}
		
		reslist = jdbcTemplate.query(sql, sarr, new RowMapper<PersonPo>() {
			@Override
			public PersonPo mapRow(ResultSet rs, int index) throws SQLException {
				PersonPo po = new PersonPo();
				po.setDetailid(rs.getString("detailid"));
				po.setPv(rs.getInt("pv"));
				po.setUv(rs.getInt("uv"));
				return po;
			}});
		
		if(null != reslist && reslist.size() > 0) {
			for (PersonPo po : reslist) {
				String detailid = po.getDetailid();
				switch(detailid) {
					case "1": po.setName("登录"); break;
					case "2": po.setName("支付中心"); break;
					case "3": po.setName("我的借阅"); break;
					case "4": po.setName("我的书单"); break;
					case "5": po.setName("我的收藏"); break;
					case "6": po.setName("我的点赞"); break;
					case "7": po.setName("我的足迹"); break;
					case "8": po.setName("云同步"); break;
					case "9": po.setName("读者指南"); break;
					case "10": po.setName("意见反馈"); break;
				}
			}
		}
		
		return reslist;
	}
	
}
