package cn.gov.nlc.service.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.LocalDate;
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
import cn.gov.nlc.mapper.AppstartMapper;
import cn.gov.nlc.mapper.AppstatistMapper;
import cn.gov.nlc.mapper.AppstatistMapperExt;
import cn.gov.nlc.mapper.NlcuserMapper;
import cn.gov.nlc.pojo.AppstartExample;
import cn.gov.nlc.pojo.AppstatistExample;
import cn.gov.nlc.pojo.NlcuserExample;
import cn.gov.nlc.pojo.NlcuserExample.Criteria;
import cn.gov.nlc.service.NlcStatisticService;
import cn.gov.nlc.vo.ApptjPo;
import cn.gov.nlc.vo.DyfxPo;
import cn.gov.nlc.vo.DyfxPoExt;
import cn.gov.nlc.vo.EasyUiDataGridResult;
import cn.gov.nlc.vo.GzyhxPo;
import cn.gov.nlc.vo.NewInstallUserDetail;
import cn.gov.nlc.vo.OsDetail;
import cn.gov.nlc.vo.PageStatistic;
import cn.gov.nlc.vo.RfwlfbPo;
import cn.gov.nlc.vo.SexDis;
import cn.gov.nlc.vo.SharePo;
import cn.gov.nlc.vo.StartCountDetail;
import cn.gov.nlc.vo.UserTypeDis;
import cn.gov.nlc.vo.VirtualUserDetail;
import cn.gov.nlc.vo.XsdfbPo;
import cn.gov.nlc.vo.Ydqk;
import cn.gov.nlc.vo.Ydyh;
import cn.gov.nlc.vo.ZyhxPo;
import cn.gov.nlc.vo.ZyhxTablePo;

@Service
public class NlcStatisticServiceImpl implements NlcStatisticService {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private AppstatistMapper appstatistMapper;
	@Autowired
	private NlcuserMapper nlcuserMapper;
	@Autowired
	private AppstartMapper appstartMapper;

	/**
	 * 实时统计今日新增用户数
	 * 
	 * @return
	 */
	@Override
	public Collection<Object> sstjXzyhsjr() {
		String sql = "select * from (SELECT count(1) a FROM nlcuser where inserttime >= DATE_ADD(curdate(),INTERVAL -1 hour) and inserttime < CURDATE()) b0, "
				+ "(SELECT count(1) b FROM nlcuser where inserttime >= CURDATE() and inserttime < DATE_ADD(curdate(),INTERVAL 1 hour)) b1, "
				+ "(SELECT count(1) c FROM nlcuser where inserttime >= DATE_ADD(curdate(),INTERVAL 1 hour) and inserttime < DATE_ADD(curdate(),INTERVAL 2 hour)) b2, "
				+ "(SELECT count(1) d FROM nlcuser where inserttime >= DATE_ADD(curdate(),INTERVAL 2 hour) and inserttime < DATE_ADD(curdate(),INTERVAL 3 hour)) b3, "
				+ "(SELECT count(1) e FROM nlcuser where inserttime >= DATE_ADD(curdate(),INTERVAL 3 hour) and inserttime < DATE_ADD(curdate(),INTERVAL 4 hour)) b4, "
				+ "(SELECT count(1) f FROM nlcuser where inserttime >= DATE_ADD(curdate(),INTERVAL 4 hour) and inserttime < DATE_ADD(curdate(),INTERVAL 5 hour)) b5, "
				+ "(SELECT count(1) g FROM nlcuser where inserttime >= DATE_ADD(curdate(),INTERVAL 5 hour) and inserttime < DATE_ADD(curdate(),INTERVAL 6 hour)) b6, "
				+ "(SELECT count(1) h FROM nlcuser where inserttime >= DATE_ADD(curdate(),INTERVAL 6 hour) and inserttime < DATE_ADD(curdate(),INTERVAL 7 hour)) b7, "
				+ "(SELECT count(1) i FROM nlcuser where inserttime >= DATE_ADD(curdate(),INTERVAL 7 hour) and inserttime < DATE_ADD(curdate(),INTERVAL 8 hour)) b8, "
				+ "(SELECT count(1) j FROM nlcuser where inserttime >= DATE_ADD(curdate(),INTERVAL 8 hour) and inserttime < DATE_ADD(curdate(),INTERVAL 9 hour)) b9, "
				+ "(SELECT count(1) k FROM nlcuser where inserttime >= DATE_ADD(curdate(),INTERVAL 9 hour) and inserttime < DATE_ADD(curdate(),INTERVAL 10 hour)) b10, "
				+ "(SELECT count(1) l FROM nlcuser where inserttime >= DATE_ADD(curdate(),INTERVAL 10 hour) and inserttime < DATE_ADD(curdate(),INTERVAL 11 hour)) b11, "
				+ "(SELECT count(1) m FROM nlcuser where inserttime >= DATE_ADD(curdate(),INTERVAL 11 hour) and inserttime < DATE_ADD(curdate(),INTERVAL 12 hour)) b12, "
				+ "(SELECT count(1) n FROM nlcuser where inserttime >= DATE_ADD(curdate(),INTERVAL 12 hour) and inserttime < DATE_ADD(curdate(),INTERVAL 13 hour)) b13, "
				+ "(SELECT count(1) o FROM nlcuser where inserttime >= DATE_ADD(curdate(),INTERVAL 13 hour) and inserttime < DATE_ADD(curdate(),INTERVAL 14 hour)) b14, "
				+ "(SELECT count(1) p FROM nlcuser where inserttime >= DATE_ADD(curdate(),INTERVAL 14 hour) and inserttime < DATE_ADD(curdate(),INTERVAL 15 hour)) b15, "
				+ "(SELECT count(1) q FROM nlcuser where inserttime >= DATE_ADD(curdate(),INTERVAL 15 hour) and inserttime < DATE_ADD(curdate(),INTERVAL 16 hour)) b16, "
				+ "(SELECT count(1) r FROM nlcuser where inserttime >= DATE_ADD(curdate(),INTERVAL 16 hour) and inserttime < DATE_ADD(curdate(),INTERVAL 17 hour)) b17, "
				+ "(SELECT count(1) s FROM nlcuser where inserttime >= DATE_ADD(curdate(),INTERVAL 17 hour) and inserttime < DATE_ADD(curdate(),INTERVAL 18 hour)) b18, "
				+ "(SELECT count(1) t FROM nlcuser where inserttime >= DATE_ADD(curdate(),INTERVAL 18 hour) and inserttime < DATE_ADD(curdate(),INTERVAL 19 hour)) b19, "
				+ "(SELECT count(1) u FROM nlcuser where inserttime >= DATE_ADD(curdate(),INTERVAL 19 hour) and inserttime < DATE_ADD(curdate(),INTERVAL 20 hour)) b20, "
				+ "(SELECT count(1) v FROM nlcuser where inserttime >= DATE_ADD(curdate(),INTERVAL 20 hour) and inserttime < DATE_ADD(curdate(),INTERVAL 21 hour)) b21, "
				+ "(SELECT count(1) w FROM nlcuser where inserttime >= DATE_ADD(curdate(),INTERVAL 21 hour) and inserttime < DATE_ADD(curdate(),INTERVAL 22 hour)) b22, "
				+ "(SELECT count(1) x FROM nlcuser where inserttime >= DATE_ADD(curdate(),INTERVAL 22 hour) and inserttime < DATE_ADD(curdate(),INTERVAL 23 hour)) b23, "
				+ "(SELECT count(1) y FROM nlcuser where inserttime >= DATE_ADD(curdate(),INTERVAL 23 hour) and inserttime < DATE_ADD(curdate(),INTERVAL 24 hour)) b24 ";
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
		Map<String, Object> map = list.get(0);
		Collection<Object> values = map.values();
		return values;
	}

	/**
	 * 实时统计今日新增用户总数
	 * 
	 * @return
	 */
	@SuppressWarnings("deprecation")
	@Override
	public Integer sstjXzyhsjr2() {
		String sql = "select count(1) from nlcuser where inserttime >= CURDATE() and inserttime < DATE_ADD(curdate(),INTERVAL 24 hour)"; // select
																																			// hour(time)
																																			// curhour,count(1)
																																			// from
																																			// (select
																																			// *
																																			// from
																																			// nlcuseronline
																																			// WHERE
																																			// time
																																			// >
																																			// curdate())
																																			// tab
																																			// GROUP
																																			// BY
																																			// curhour;
		int result = jdbcTemplate.queryForInt(sql);
		return result;
	}

	/**
	 * 实时统计今日启动次数select hour(time) curhour,count(1) from (select * from appstart
	 * WHERE time > curdate()) tab GROUP BY curhour
	 */
	@Override
	public Collection<Object> sstjQdcsjr() {
		String sql = "select * from (SELECT count(1) a FROM appstart where time >= DATE_ADD(curdate(),INTERVAL -1 hour) and time < CURDATE()) b0, "
				+ "(SELECT count(1) b FROM appstart where time >= CURDATE() and time < DATE_ADD(curdate(),INTERVAL 1 hour)) b1, "
				+ "(SELECT count(1) c FROM appstart where time >= DATE_ADD(curdate(),INTERVAL 1 hour) and time < DATE_ADD(curdate(),INTERVAL 2 hour)) b2, "
				+ "(SELECT count(1) d FROM appstart where time >= DATE_ADD(curdate(),INTERVAL 2 hour) and time < DATE_ADD(curdate(),INTERVAL 3 hour)) b3, "
				+ "(SELECT count(1) e FROM appstart where time >= DATE_ADD(curdate(),INTERVAL 3 hour) and time < DATE_ADD(curdate(),INTERVAL 4 hour)) b4, "
				+ "(SELECT count(1) f FROM appstart where time >= DATE_ADD(curdate(),INTERVAL 4 hour) and time < DATE_ADD(curdate(),INTERVAL 5 hour)) b5, "
				+ "(SELECT count(1) g FROM appstart where time >= DATE_ADD(curdate(),INTERVAL 5 hour) and time < DATE_ADD(curdate(),INTERVAL 6 hour)) b6, "
				+ "(SELECT count(1) h FROM appstart where time >= DATE_ADD(curdate(),INTERVAL 6 hour) and time < DATE_ADD(curdate(),INTERVAL 7 hour)) b7, "
				+ "(SELECT count(1) i FROM appstart where time >= DATE_ADD(curdate(),INTERVAL 7 hour) and time < DATE_ADD(curdate(),INTERVAL 8 hour)) b8, "
				+ "(SELECT count(1) j FROM appstart where time >= DATE_ADD(curdate(),INTERVAL 8 hour) and time < DATE_ADD(curdate(),INTERVAL 9 hour)) b9, "
				+ "(SELECT count(1) k FROM appstart where time >= DATE_ADD(curdate(),INTERVAL 9 hour) and time < DATE_ADD(curdate(),INTERVAL 10 hour)) b10, "
				+ "(SELECT count(1) l FROM appstart where time >= DATE_ADD(curdate(),INTERVAL 10 hour) and time < DATE_ADD(curdate(),INTERVAL 11 hour)) b11, "
				+ "(SELECT count(1) m FROM appstart where time >= DATE_ADD(curdate(),INTERVAL 11 hour) and time < DATE_ADD(curdate(),INTERVAL 12 hour)) b12, "
				+ "(SELECT count(1) n FROM appstart where time >= DATE_ADD(curdate(),INTERVAL 12 hour) and time < DATE_ADD(curdate(),INTERVAL 13 hour)) b13, "
				+ "(SELECT count(1) o FROM appstart where time >= DATE_ADD(curdate(),INTERVAL 13 hour) and time < DATE_ADD(curdate(),INTERVAL 14 hour)) b14, "
				+ "(SELECT count(1) p FROM appstart where time >= DATE_ADD(curdate(),INTERVAL 14 hour) and time < DATE_ADD(curdate(),INTERVAL 15 hour)) b15, "
				+ "(SELECT count(1) q FROM appstart where time >= DATE_ADD(curdate(),INTERVAL 15 hour) and time < DATE_ADD(curdate(),INTERVAL 16 hour)) b16, "
				+ "(SELECT count(1) r FROM appstart where time >= DATE_ADD(curdate(),INTERVAL 16 hour) and time < DATE_ADD(curdate(),INTERVAL 17 hour)) b17, "
				+ "(SELECT count(1) s FROM appstart where time >= DATE_ADD(curdate(),INTERVAL 17 hour) and time < DATE_ADD(curdate(),INTERVAL 18 hour)) b18, "
				+ "(SELECT count(1) t FROM appstart where time >= DATE_ADD(curdate(),INTERVAL 18 hour) and time < DATE_ADD(curdate(),INTERVAL 19 hour)) b19, "
				+ "(SELECT count(1) u FROM appstart where time >= DATE_ADD(curdate(),INTERVAL 19 hour) and time < DATE_ADD(curdate(),INTERVAL 20 hour)) b20, "
				+ "(SELECT count(1) v FROM appstart where time >= DATE_ADD(curdate(),INTERVAL 20 hour) and time < DATE_ADD(curdate(),INTERVAL 21 hour)) b21, "
				+ "(SELECT count(1) w FROM appstart where time >= DATE_ADD(curdate(),INTERVAL 21 hour) and time < DATE_ADD(curdate(),INTERVAL 22 hour)) b22, "
				+ "(SELECT count(1) x FROM appstart where time >= DATE_ADD(curdate(),INTERVAL 22 hour) and time < DATE_ADD(curdate(),INTERVAL 23 hour)) b23, "
				+ "(SELECT count(1) y FROM appstart where time >= DATE_ADD(curdate(),INTERVAL 23 hour) and time < DATE_ADD(curdate(),INTERVAL 24 hour)) b24 ";
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
		Map<String, Object> map = list.get(0);
		Collection<Object> values = map.values();
		return values;
	}

	/**
	 * 实时统计今日启动总次数
	 */
	@SuppressWarnings("deprecation")
	@Override
	public Integer sstjQdcsjr2() {
		String sql = "select count(1) from appstart where time >= CURDATE() and time < DATE_ADD(curdate(),INTERVAL 24 hour)";
		int result = jdbcTemplate.queryForInt(sql);
		return result;
	}

	/**
	 * 用户分析新增用户趋势
	 */
	@SuppressWarnings("deprecation")
	@Override
	public List<Integer> xzyhqs(Date startDate, Date endDate) {
		DateTime dstartDate = new DateTime(startDate);
		DateTime dendDate = new DateTime(endDate);
		int days = Days.daysBetween(dstartDate, dendDate).getDays();
		List<Integer> flowList = new ArrayList<Integer>();

		if (days <= 55) {
			String sql = "select sum(count) from schenewuser where time = ";
			for (int i = 0; i < days + 1; i++) {
				int res1 = jdbcTemplate.queryForInt(sql + "\"" + dstartDate.toString("yyyy-MM-dd") + "\"");
				flowList.add(res1);
				dstartDate = dstartDate.plusDays(1);
			}
		} else {
			String sql2 = "select sum(count) from schenewuser ";
			int base = ((Double) Math.ceil(days / 55.0)).intValue();
			for (int i = 0; i < (days / base) + 1; i++) {
				int res2 = jdbcTemplate.queryForInt(sql2 + "where time >= \"" + dstartDate.toString("yyyy-MM-dd")
						+ "\" and time < \"" + dstartDate.plusDays(base).toString("yyyy-MM-dd") + "\"");
				flowList.add(res2);
				dstartDate = dstartDate.plusDays(base);
			}
		}

		return flowList;
	}

	/**
	 * 用户分析新增用户明细
	 */
	@SuppressWarnings("deprecation")
	@Override
	public List<NewInstallUserDetail> xzyhmx(Date startDate, Date endDate) {
		DateTime dstartDate = new DateTime(startDate);
		DateTime dendDate = new DateTime(endDate);
		int days = Days.daysBetween(dstartDate, dendDate).getDays();
		List<NewInstallUserDetail> list = new ArrayList<NewInstallUserDetail>();

		String sql = "select sum(count) from schenewuser ";
		double allsum = jdbcTemplate.queryForInt(sql) + 0.0; // 总数
		NumberFormat format = NumberFormat.getPercentInstance();// 获取格式化类实例
		format.setMinimumFractionDigits(2);// 设置小数位

		if (days <= 4) {
			String sql2 = "select sum(count) from schenewuser where time = ";
			for (int i = 0; i < days + 1; i++) {
				NewInstallUserDetail userdetail = new NewInstallUserDetail();
				userdetail.setPeriod(dstartDate.toString("yyyy-MM-dd"));
				int res1 = jdbcTemplate.queryForInt(sql2 + "\"" + dstartDate.toString("yyyy-MM-dd") + "\"");
				if (0 == allsum) {
					userdetail.setRatio(res1 + " (0.00%)");
				} else {
					userdetail.setRatio(res1 + " (" + format.format(res1 / allsum) + ")");
				}
				list.add(userdetail);
				dstartDate = dstartDate.plusDays(1);
			}
		} else {
			int base = days / 5;
			for (int i = 1; i <= 5; i++) {
				NewInstallUserDetail userdetail = new NewInstallUserDetail();
				int res2 = 0;
				if (i != 5) {
					userdetail.setPeriod(dstartDate.toString("yyyy-MM-dd") + " ~ "
							+ dstartDate.plusDays(base).toString("yyyy-MM-dd"));
					res2 = jdbcTemplate.queryForInt(sql + "where time >= \"" + dstartDate.toString("yyyy-MM-dd")
							+ "\" and time < \"" + dstartDate.plusDays(base).toString("yyyy-MM-dd") + "\"");
				} else {
					userdetail.setPeriod(dstartDate.toString("yyyy-MM-dd") + " ~ " + dendDate.toString("yyyy-MM-dd"));
					res2 = jdbcTemplate.queryForInt(sql + "where time >= \"" + dstartDate.toString("yyyy-MM-dd")
							+ "\" and time < \"" + dendDate.toString("yyyy-MM-dd") + "\"");
				}
				if (0 == allsum) {
					userdetail.setRatio(res2 + " (0.00%)");
				} else {
					userdetail.setRatio(res2 + " (" + format.format(res2 / allsum) + ")");
				}
				list.add(userdetail);
				dstartDate = dstartDate.plusDays(base);
			}
		}

		return list;
	}

	/**
	 * 用户分析启动次数趋势 不去重的，启动次数
	 */
	@Override
	public List<Integer> qdcsqs(Date startDate, Date endDate) {
		DateTime dstartDate = new DateTime(startDate);
		DateTime dendDate = new DateTime(endDate);
		int days = Days.daysBetween(dstartDate, dendDate).getDays();
		List<Integer> flowList = new ArrayList<Integer>();

		if (days <= 55) {
			String sql = "select sum(num) from scheappstart where time = ";
			for (int i = 0; i < days + 1; i++) {
				int res1 = jdbcTemplate.queryForInt(sql + "\"" + dstartDate.toString("yyyy-MM-dd") + "\"");
				flowList.add(res1);
				dstartDate = dstartDate.plusDays(1);
			}
		} else {
			String sql2 = "select sum(num) from scheappstart ";
			int base = ((Double) Math.ceil(days / 55.0)).intValue();
			for (int i = 0; i < (days / base) + 1; i++) {
				int res2 = jdbcTemplate.queryForInt(sql2 + "where time >= \"" + dstartDate.toString("yyyy-MM-dd")
						+ "\" and time < \"" + dstartDate.plusDays(base).toString("yyyy-MM-dd") + "\"");
				flowList.add(res2);
				dstartDate = dstartDate.plusDays(base);
			}
		}

		return flowList;
	}

	/**
	 * 用户分析启动次数明细
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	@Override
	public List<StartCountDetail> qdcsmx(Date startDate, Date endDate) {
		DateTime dstartDate = new DateTime(startDate);
		DateTime dendDate = new DateTime(endDate);
		int days = Days.daysBetween(dstartDate, dendDate).getDays();
		List<StartCountDetail> list = new ArrayList<StartCountDetail>();

		if (days <= 4) {
			String sql = "select sum(num) from scheappstart where time = ";
			for (int i = 0; i < days + 1; i++) {
				StartCountDetail startDetail = new StartCountDetail();
				startDetail.setPeriod(dstartDate.toString("yyyy-MM-dd"));
				int res1 = jdbcTemplate.queryForInt(sql + "\"" + dstartDate.toString("yyyy-MM-dd") + "\"");
				startDetail.setNum(res1 + "");
				list.add(startDetail);
				dstartDate = dstartDate.plusDays(1);
			}
		} else {
			String sql2 = "select sum(num) from scheappstart ";
			int base = days / 5;
			for (int i = 1; i <= 5; i++) {
				StartCountDetail startDetail = new StartCountDetail();
				int res2 = 0;
				if (i != 5) {
					startDetail.setPeriod(dstartDate.toString("yyyy-MM-dd") + " ~ "
							+ dstartDate.plusDays(base).toString("yyyy-MM-dd"));
					res2 = jdbcTemplate.queryForInt(sql2 + "where time >= \"" + dstartDate.toString("yyyy-MM-dd")
							+ "\" and time < \"" + dstartDate.plusDays(base).toString("yyyy-MM-dd") + "\"");
				} else {
					startDetail.setPeriod(dstartDate.toString("yyyy-MM-dd") + " ~ " + dendDate.toString("yyyy-MM-dd"));
					res2 = jdbcTemplate.queryForInt(sql2 + "where time >= \"" + dstartDate.toString("yyyy-MM-dd")
							+ "\" and time < \"" + dendDate.toString("yyyy-MM-dd") + "\"");
				}
				startDetail.setNum(res2 + "");
				list.add(startDetail);
				dstartDate = dstartDate.plusDays(base);
			}
		}

		return list;
	}

	/**
	 * 用户数量/用户类型分布 饼图的数据 0000 虚拟 JS0001 实名 JS0002 物理卡(持卡用户)
	 * 
	 * @return
	 */
	@Override
	public Map<String, String> yhlxfb1(Date startDate, Date endDate) {
		Map<String, String> resmap = new HashMap<String, String>();
		NlcuserExample example = new NlcuserExample();
		Criteria criteria = example.createCriteria();
		criteria.andInserttimeBetween(startDate, endDate);
		double sum = nlcuserMapper.countByExample(example) + 0.0;

		NumberFormat format = NumberFormat.getPercentInstance();// 获取格式化类实例
		format.setMinimumFractionDigits(2);// 设置小数位

		NlcuserExample example1 = new NlcuserExample();
		Criteria criteria1 = example1.createCriteria();
		criteria1.andRdrolecodeEqualTo("0000");
		criteria1.andInserttimeBetween(startDate, endDate);
		int virnum = nlcuserMapper.countByExample(example1);
		if (0 == sum) {
			resmap.put("virnum", "0.0");
		} else {
			resmap.put("virnum", format.format(virnum / sum));
		}

		NlcuserExample example2 = new NlcuserExample();
		Criteria criteria2 = example2.createCriteria();
		criteria2.andRdrolecodeEqualTo("JS0001");
		criteria2.andInserttimeBetween(startDate, endDate);
		int realnum = nlcuserMapper.countByExample(example2);
		if (0 == sum) {
			resmap.put("realnum", "0.0");
		} else {
			resmap.put("realnum", format.format(realnum / sum));
		}

		NlcuserExample example3 = new NlcuserExample();
		Criteria criteria3 = example3.createCriteria();
		criteria3.andRdrolecodeEqualTo("JS0002");
		criteria3.andInserttimeBetween(startDate, endDate);
		int cardnum = nlcuserMapper.countByExample(example3);
		if (0 == sum) {
			resmap.put("cardnum", "0.0");
		} else {
			resmap.put("cardnum", format.format(cardnum / sum));
		}

		return resmap;
	}

	/**
	 * 用户数量/用户类型分布 各类型用户数据
	 * 
	 * @return
	 */
	@Override
	public List<Integer> yhlxfb2(Date startDate, Date endDate) {
		List<Integer> list = new ArrayList<Integer>();

		// 总数
		NlcuserExample example = new NlcuserExample();
		Criteria criteria = example.createCriteria();
		criteria.andInserttimeBetween(startDate, endDate);
		int sum = nlcuserMapper.countByExample(example);

		// 虚拟
		NlcuserExample example1 = new NlcuserExample();
		Criteria criteria1 = example1.createCriteria();
		criteria1.andRdrolecodeEqualTo("0000");
		criteria1.andInserttimeBetween(startDate, endDate);
		int virnum = nlcuserMapper.countByExample(example1);

		// 实名
		NlcuserExample example2 = new NlcuserExample();
		Criteria criteria2 = example2.createCriteria();
		criteria2.andRdrolecodeEqualTo("JS0001");
		criteria2.andInserttimeBetween(startDate, endDate);
		int realnum = nlcuserMapper.countByExample(example2);

		// 持卡
		NlcuserExample example3 = new NlcuserExample();
		Criteria criteria3 = example3.createCriteria();
		criteria3.andRdrolecodeEqualTo("JS0002");
		criteria3.andInserttimeBetween(startDate, endDate);
		int cardnum = nlcuserMapper.countByExample(example3);

		list.add(realnum);
		list.add(cardnum);
		list.add(virnum);
		list.add(sum);

		return list;
	}

	// /**
	// * 虚拟用户数量数据
	// */
	// @Override
	// public List<Integer> xnyhslDataList(Date startDate, Date endDate) {
	// List<Integer> dataList = new ArrayList<Integer>();
	// DateTime startDateJoda = new DateTime(startDate);
	// DateTime endDateJoda = new DateTime(endDate);
	//
	// String sql = "select sum(num) from schevirusernum ";
	//
	// while(startDateJoda.getMillis() <= endDateJoda.getMillis()) {
	// int res1 = jdbcTemplate.queryForInt(sql + " where time >= \"" +
	// startDateJoda.toString("yyyy-MM-01") + "\" and time < \"" +
	// startDateJoda.plusMonths(1).toString("yyyy-MM-01") + "\"");
	// dataList.add(res1);
	// startDateJoda = startDateJoda.plusMonths(1);
	// }
	//
	// return dataList;
	// }
	/**
	 * 虚拟用户数量数据 add by JJJ
	 */
	@Override
	public List<VirtualUserDetail>  xnyhslDataList(Date startDate, Date endDate, String type) {
	//	List<Integer> dataList = new ArrayList<Integer>();
		//List<String> labelsList = new ArrayList<String>();
		List<VirtualUserDetail> list2 = new ArrayList<VirtualUserDetail>();
		DateTime sdt = new DateTime(startDate);
		DateTime edt = new DateTime(endDate);
		int totalRow = 0;
		String sql = "";
		if ("week".equalsIgnoreCase(type)) {
			int minus = sdt.getDayOfWeek() - 1;
			sdt = sdt.minusDays(minus); // 得到刚刚好的本周的周一
			int p = edt.getDayOfWeek();
			edt = edt.plusDays(7 - p);
			int weeks = Weeks.weeksBetween(sdt, edt).getWeeks();
			totalRow = weeks + 1;
			int base = 1;
			if (totalRow > 55) {
				base = ((Double) Math.ceil(totalRow / 55.0)).intValue();
			}
			while (sdt.getMillis() <= edt.getMillis()) {
				String str = sdt.toString("yyyy") + "第" + sdt.getWeekOfWeekyear() + "周";
				sql = "SELECT IFNULL(sum(num),0) monthNewAddNum FROM schevirusernum where time >= \""
						+ sdt.toString("yyyy-MM-dd") + "\" and time < \"" + sdt.plusWeeks(base).toString("yyyy-MM-dd")
						+ "\" ";
				int res = jdbcTemplate.queryForObject(sql, Integer.class);
				VirtualUserDetail v = new VirtualUserDetail();
				v.setMtime(str);
				v.setMonthNewAddNum(res);
				list2.add(v);
				sdt = sdt.plusWeeks(base);
			}
		} else if ("day".equals(type)) {
			totalRow = Days.daysBetween(sdt, edt).getDays() + 1;
			int base = 1;
			if (totalRow > 55) {
				base = ((Double) Math.ceil(totalRow / 55.0)).intValue();
			}
			while (sdt.getMillis() <= edt.getMillis()) {
				sql = "SELECT \'" + sdt.toString("yyyy-MM-dd")
						+ "\' mtime, IFNULL(sum(num),0) monthNewAddNum FROM schevirusernum where time >= \""
						+ sdt.toString("yyyy-MM-dd") + "\" and time < \"" + sdt.plusDays(base).toString("yyyy-MM-dd")
						+ "\" ";
				VirtualUserDetail v = jdbcTemplate.queryForObject(sql,
						new BeanPropertyRowMapper<VirtualUserDetail>(VirtualUserDetail.class));
				list2.add(v);
				sdt = sdt.plusDays(base);
			}
		} else if ("year".equalsIgnoreCase(type)) {
			totalRow = Years.yearsBetween(sdt, edt).getYears() + 1;
			int base = 1;
			if (totalRow > 55) {
				base = ((Double) Math.ceil(totalRow / 55.0)).intValue();
			}
			while (sdt.getMillis() <= edt.getMillis()) {
				sql = "SELECT \'" + sdt.toString("yyyy")
						+ "\' mtime, IFNULL(sum(num),0) monthNewAddNum FROM schevirusernum where time >= \""
						+ sdt.toString("yyyy-01-01") + "\" and time < \"" + sdt.plusYears(base).toString("yyyy-01-01")
						+ "\" ";
				VirtualUserDetail v = jdbcTemplate.queryForObject(sql,new BeanPropertyRowMapper<VirtualUserDetail>(VirtualUserDetail.class));
				list2.add(v);
				sdt = sdt.plusYears(base);
			}
		} else if ("month".equalsIgnoreCase(type)) {
			totalRow = Months.monthsBetween(sdt, edt).getMonths() + 1;
			int base = 1;
			if (totalRow > 55) {
				base = ((Double) Math.ceil(totalRow / 55.0)).intValue();
			}
			while (sdt.getMillis() <= edt.getMillis()) {
				sql = "SELECT \'" + sdt.toString("yyyy-MM")
						+ "\' mtime, IFNULL(sum(num),0) monthNewAddNum FROM schevirusernum where time >= \""
						+ sdt.toString("yyyy-MM-01") + "\" and time < \"" + sdt.plusMonths(base).toString("yyyy-MM-01")
						+ "\" ";
				VirtualUserDetail v = jdbcTemplate.queryForObject(sql,new BeanPropertyRowMapper<VirtualUserDetail>(VirtualUserDetail.class));
				list2.add(v);
				sdt = sdt.plusMonths(base);
			}
		}
//		for (VirtualUserDetail virtualUserDetail : list2) {
//			dataList.add(virtualUserDetail.getMonthNewAddNum());
//			labelsList.add("\"" + virtualUserDetail.getMtime() + "\"");
//		}
		//String result = "{result:true,flow:" + dataList + ", labels:" + labelsList + "}";
		return list2;
	}

	/**
	 * 虚拟用户分页数据 
	 * add by JJJ
	 * update by JJJ 20170310
	 * @param page
	 * @param rows
	 * @return
	 */
	@Override
	public List<VirtualUserDetail>  xnyhtableList(Integer page, Integer rows, Date startDate, Date endDate, String type,
			Boolean getAll) {
		DateTime sdt = new DateTime(startDate);
		DateTime edt = new DateTime(endDate);
		List<VirtualUserDetail> list2 = new ArrayList<VirtualUserDetail>();
		int totalRow = 0;
		if (type.equals("year")) {
			totalRow = Years.yearsBetween(sdt, edt).getYears() + 1;
		} else if (type.equals("month")) {
			totalRow = Months.monthsBetween(sdt, edt).getMonths() + 1;
		} else if (type.equals("day")) {
			totalRow = Days.daysBetween(sdt, edt).getDays() + 1;
		} else if (type.equals("week")) {
			int minus = sdt.getDayOfWeek() - 1;
			sdt = sdt.minusDays(minus); // 得到刚刚好的本周的周一
			int p = edt.getDayOfWeek();
			edt = edt.plusDays(7 - p);
			int weeks = Weeks.weeksBetween(sdt, edt).getWeeks();
			totalRow = weeks + 1;
		} else {
			return null;
		}

		int start = 0, end = 0;
		if (getAll) {
			end = totalRow;
		} else {
			start = (page - 1) * rows;
			if ((start + rows) > totalRow) {
				end = totalRow;
			} else {
				end = start + rows;
			}
		}
		String sql = "";
		for (int i = start; i < end; i++) {
			VirtualUserDetail vud = new VirtualUserDetail();
			if (type.equals("year")) {
				vud.setMtime(edt.minusYears(i).toString("yyyy"));
				sql = "SELECT IFNULL(sum(num),0) count FROM schevirusernum where time >= \""
						+ edt.minusYears(i).toString("yyyy-01-01") + "\" and time < \"" + edt.minusYears(i).plusYears(1).toString("yyyy-01-01")
						+ "\"  ";
			} else if (type.equals("month")) {
				vud.setMtime(edt.minusMonths(i).toString("yyyy-MM"));
				sql = "SELECT IFNULL(sum(num),0) count FROM schevirusernum where time >= \"" + edt.minusMonths(i).toString("yyyy-MM-01") 
						+ "\" and time < \"" +  edt.minusMonths(i).plusMonths(1).toString("yyyy-MM-01")	+ "\" " ;
			} else if (type.equals("day")) {
				vud.setMtime(edt.minusDays(i).toString("yyyy-MM-dd"));
				sql = "SELECT IFNULL(sum(num),0) count FROM schevirusernum where time >= \""+ edt.minusDays(i).toString("yyyy-MM-dd") + "\" and time < \"" 
						+ edt.minusDays(i).plusDays(1).toString("yyyy-MM-dd") + "\" ";
			} else if (type.equals("week")) {//时间正序
				String str = "'第" + sdt.plusWeeks(i).getWeekOfWeekyear() + "周" + sdt.plusWeeks(i).toString("yyyy-MM-dd")
						+ "--" + sdt.plusWeeks(i).plusDays(6).toString("yyyy-MM-dd") + "'";
				vud.setMtime(str);
				sql = "SELECT IFNULL(sum(num),0) count FROM schevirusernum where time >= \""
						+ sdt.plusWeeks(i).toString("yyyy-MM-dd") + "\" and time < \""
						+ sdt.plusWeeks(i+1).toString("yyyy-MM-dd") + "\" ";
			}
			int count = jdbcTemplate.queryForObject(sql, Integer.class);
			vud.setMonthNewAddNum(count);
			list2.add(vud);
		
		}
	
//		DateTimeFormatter format = DateTimeFormat.forPattern("yyyy-MM-dd");
//		if (null != list2 && list2.size() > 0) {
//			for (VirtualUserDetail virtualUserDetail : list2) {
//				virtualUserDetail.setThisYearAccNum(0);
//				String mtime = virtualUserDetail.getMtime();
//				String tsql = "";
//				if (type.equals("year")) {
//					virtualUserDetail.setThisYearAccNum(virtualUserDetail.getMonthNewAddNum());
//				} else {
//					if (type.equals("month")) {
//						DateTime temDate = DateTime.parse(mtime + "-01", format);
//						tsql = "select sum(num) from schevirusernum where time >= \"" + temDate.toString("yyyy-01-01")
//								+ "\" and time < \"" + temDate.plusMonths(1).toString("yyyy-MM-dd") + "\"";
//					} else if (type.equals("day")) {
//						DateTime temDate = DateTime.parse(mtime, format);
//						tsql = "select sum(num) from schevirusernum where time >= \"" + temDate.toString("yyyy-01-01")
//								+ "\" and time < \"" + temDate.plusDays(1).toString("yyyy-MM-dd") + "\"";
//					} else if (type.equals("week")) {
//						mtime = mtime.substring(mtime.indexOf("--") + 2);
//						DateTime temDate = DateTime.parse(mtime, format);
//						tsql = "select sum(num) from schevirusernum where time >= \"" + temDate.toString("yyyy-01-01")
//								+ "\" and time < \"" + temDate.plusDays(1).toString("yyyy-MM-dd") + "\"";
//					}
//					int res = jdbcTemplate.queryForObject(tsql, Integer.class);
//					virtualUserDetail.setThisYearAccNum(res);
//				}
//			}
//		}
		//EasyUiDataGridResult result = new EasyUiDataGridResult(totalRow, list2);
		//return result;
		return list2;
	}
	// /**
	// * 虚拟用户分页数据 update by JJJ
	// * @param page
	// * @param rows
	// * @return
	// */
	// @SuppressWarnings("deprecation")
	// @Override
	// public EasyUiDataGridResult yhslPic4List(Integer page, Integer rows, Date
	// startDate, Date endDate) {
	// DateTime startDateJoda = new DateTime(startDate);
	// DateTime endDateJoda = new DateTime(endDate);
	//
	//
	// String sql = "select count(1) from (SELECT DATE_FORMAT(time,'%Y-%m')
	// mtime, sum(num) monthNewAddNum FROM schevirusernum where time >=
	// \""+startDateJoda.toString("yyyy-MM-01")+"\" and time <= \"" +
	// endDateJoda.plusMonths(1).minusDays(1).toString("yyyy-MM-dd") + "\" group
	// by DATE_FORMAT(time,'%Y-%m') ) temp";
	// int totalRow = jdbcTemplate.queryForInt(sql);
	//
	// int start = (page - 1) * rows;
	// int limit = rows;
	//
	// String sql2 = "SELECT DATE_FORMAT(time,'%Y-%m') mtime, sum(num)
	// monthNewAddNum FROM schevirusernum where time >=
	// \""+startDateJoda.toString("yyyy-MM-01")+"\" and time <= \"" +
	// endDateJoda.plusMonths(1).minusDays(1).toString("yyyy-MM-dd") + "\" group
	// by DATE_FORMAT(time,'%Y-%m') order by mtime desc limit "+start+" ,
	// "+limit;
	// List<VirtualUserDetail> list2 = jdbcTemplate.query(sql2, new
	// BeanPropertyRowMapper<VirtualUserDetail>(VirtualUserDetail.class));
	//
	// DateTimeFormatter format = DateTimeFormat.forPattern("yyyy-MM-dd");
	//
	// if(null != list2 && list2.size() > 0) {
	// for (VirtualUserDetail virtualUserDetail : list2) {
	// String mtime = virtualUserDetail.getMtime();
	// DateTime temDate = DateTime.parse(mtime + "-01", format);
	// String tsql = "select sum(num) from schevirusernum where time >=
	// \""+temDate.toString("yyyy-01-01")+"\" and time < \"" +
	// temDate.plusMonths(1).toString("yyyy-MM-dd") + "\"";
	// int res = jdbcTemplate.queryForInt(tsql);
	// virtualUserDetail.setThisYearAccNum(res);
	// }
	// }
	//
	//
	// EasyUiDataGridResult result = new EasyUiDataGridResult(totalRow, list2);
	// return result;
	// }

	// /**
	// * 虚拟用户数据导出excel的list
	// */
	// @Override
	// public List<VirtualUserDetail> xnyhExport(Date startDate, Date endDate) {
	// DateTime startDateJoda = new DateTime(startDate);
	// DateTime endDateJoda = new DateTime(endDate);
	//
	// String sql2 = "SELECT DATE_FORMAT(time,'%Y-%m') mtime, sum(num)
	// monthNewAddNum FROM schevirusernum where time >=
	// \""+startDateJoda.toString("yyyy-MM-01")+"\" and time <= \"" +
	// endDateJoda.plusMonths(1).minusDays(1).toString("yyyy-MM-dd") + "\" group
	// by DATE_FORMAT(time,'%Y-%m') order by mtime desc ";
	// List<VirtualUserDetail> list2 = jdbcTemplate.query(sql2, new
	// BeanPropertyRowMapper<VirtualUserDetail>(VirtualUserDetail.class));
	//
	// DateTimeFormatter format = DateTimeFormat.forPattern("yyyy-MM-dd");
	//
	// if(null != list2 && list2.size() > 0) {
	// for (VirtualUserDetail virtualUserDetail : list2) {
	// String mtime = virtualUserDetail.getMtime();
	// DateTime temDate = DateTime.parse(mtime + "-01", format);
	// String tsql = "select sum(num) from schevirusernum where time >=
	// \""+temDate.toString("yyyy-01-01")+"\" and time < \"" +
	// temDate.plusMonths(1).toString("yyyy-MM-dd") + "\"";
	// int res = jdbcTemplate.queryForInt(tsql);
	// virtualUserDetail.setThisYearAccNum(res);
	// }
	// }
	//
	// return list2;
	// }

	/**
	 * 跳转到用户分析/用户属性 第一个图的第一个小图男女占比的饼图数据 update by JJJ 20170222 pm
	 */
	@Override
	public Map<String, String> yhsx1p1(Date startDate, Date endDate) {
		Map<String, String> resmap = new HashMap<String, String>();
		NlcuserExample example = new NlcuserExample();
		Criteria criteria = example.createCriteria();
		criteria.andInserttimeBetween(startDate, endDate);
		double sum = nlcuserMapper.countByExample(example) + 0.0;

		NumberFormat format = NumberFormat.getPercentInstance();// 获取格式化类实例
		format.setMinimumFractionDigits(2);// 设置小数位

		NlcuserExample example1 = new NlcuserExample();
		Criteria criteria1 = example1.createCriteria();
		criteria1.andInserttimeBetween(startDate, endDate);
		criteria1.andSextypeNotEqualTo("女士");// 男士
		int malenum = nlcuserMapper.countByExample(example1);
		if (0 == sum) {
			resmap.put("maleratio", "0.0");
		} else {
			resmap.put("maleratio", format.format(malenum / sum));
		}

		NlcuserExample example2 = new NlcuserExample();
		Criteria criteria2 = example2.createCriteria();
		criteria2.andInserttimeBetween(startDate, endDate);
		criteria2.andSextypeEqualTo("女士"); // 女士
		int femalenum = nlcuserMapper.countByExample(example2);
		if (0 == sum) {
			resmap.put("femaleratio", "0.0");
		} else {
			resmap.put("femaleratio", format.format(femalenum / sum));
		}
		return resmap;
	}

	/**
	 * 跳转到用户分析/用户属性 第一个图的第二个小图男女饼图年龄占比的数据
	 */
	@Override
	public Map<String, String> yhsx1p2(Date startDate, Date endDate) {
		Map<String, String> resmap = new HashMap<String, String>();
		NumberFormat format = NumberFormat.getPercentInstance();// 获取格式化类实例
		format.setMinimumFractionDigits(2);// 设置小数位

		NlcuserExample feSumExample = new NlcuserExample();
		Criteria feSumcriteria = feSumExample.createCriteria();
		feSumcriteria.andSextypeEqualTo("女士");
		feSumcriteria.andInserttimeBetween(startDate, endDate);
		double feSum = nlcuserMapper.countByExample(feSumExample) + 0.0; // 女士总数

		NlcuserExample maleSumExample = new NlcuserExample();
		Criteria maleSumcriteria = maleSumExample.createCriteria();
		maleSumcriteria.andSextypeNotEqualTo("女士");
		maleSumcriteria.andInserttimeBetween(startDate, endDate);
		double maleSum = nlcuserMapper.countByExample(maleSumExample) + 0.0; // 男士总数

		DateTime now = new DateTime(endDate);
		DateTime year19 = now.minusYears(19);
		DateTime year24 = now.minusYears(24);
		DateTime year31 = now.minusYears(31);
		DateTime year41 = now.minusYears(41);

		NlcuserExample felt18Example = new NlcuserExample(); // 18岁及以下的女士
		Criteria felt18Criteria = felt18Example.createCriteria();
		felt18Criteria.andBirthdayGreaterThan(year19.toDate());
		felt18Criteria.andInserttimeBetween(startDate, endDate);
		felt18Criteria.andSextypeEqualTo("女士");
		int felt18num = nlcuserMapper.countByExample(felt18Example);
		if (0 == feSum) {
			resmap.put("felt18", "0.0");
		} else {
			resmap.put("felt18", format.format(felt18num / feSum));
		}

		NlcuserExample malelt18Example = new NlcuserExample(); // 18岁及以下的男士
		Criteria malelt18Criteria = malelt18Example.createCriteria();
		malelt18Criteria.andBirthdayGreaterThan(year19.toDate());
		malelt18Criteria.andSextypeNotEqualTo("女士");
		malelt18Criteria.andInserttimeBetween(startDate, endDate);
		int malelt18num = nlcuserMapper.countByExample(malelt18Example);
		if (0 == maleSum) {
			resmap.put("malelt18", "0.0");
		} else {
			resmap.put("malelt18", format.format(malelt18num / maleSum));
		}

		NlcuserExample febetween19and23Example = new NlcuserExample(); // 19到23岁的女士
		Criteria febetween19and23Criteria = febetween19and23Example.createCriteria();
		febetween19and23Criteria.andSextypeEqualTo("女士");
		febetween19and23Criteria.andBirthdayLessThanOrEqualTo(year19.toDate());
		febetween19and23Criteria.andBirthdayGreaterThan(year24.toDate());
		febetween19and23Criteria.andInserttimeBetween(startDate, endDate);
		int febetween19and23num = nlcuserMapper.countByExample(febetween19and23Example);
		if (0 == feSum) {
			resmap.put("febetween19and23", "0.0");
		} else {
			resmap.put("febetween19and23", format.format(febetween19and23num / feSum));
		}

		NlcuserExample malebetween19and23Example = new NlcuserExample(); // 19到23岁的男士
		Criteria malebetween19and23Criteria = malebetween19and23Example.createCriteria();
		malebetween19and23Criteria.andSextypeNotEqualTo("女士");
		malebetween19and23Criteria.andBirthdayLessThanOrEqualTo(year19.toDate());
		malebetween19and23Criteria.andBirthdayGreaterThan(year24.toDate());
		malebetween19and23Criteria.andInserttimeBetween(startDate, endDate);
		int malebetween19and23num = nlcuserMapper.countByExample(malebetween19and23Example);
		if (0 == maleSum) {
			resmap.put("malebetween19and23", "0.0");
		} else {
			resmap.put("malebetween19and23", format.format(malebetween19and23num / maleSum));
		}

		NlcuserExample febetween24and30Example = new NlcuserExample(); // 24到30岁的女士
		Criteria febetween24and30Criteria = febetween24and30Example.createCriteria();
		febetween24and30Criteria.andSextypeEqualTo("女士");
		febetween24and30Criteria.andBirthdayLessThanOrEqualTo(year24.toDate());
		febetween24and30Criteria.andBirthdayGreaterThan(year31.toDate());
		febetween24and30Criteria.andInserttimeBetween(startDate, endDate);
		int febetween24and30num = nlcuserMapper.countByExample(febetween24and30Example);
		if (0 == feSum) {
			resmap.put("febetween24and30", "0.0");
		} else {
			resmap.put("febetween24and30", format.format(febetween24and30num / feSum));
		}

		NlcuserExample malebetween24and30Example = new NlcuserExample(); // 24到30岁的男士
		Criteria malebetween24and30Criteria = malebetween24and30Example.createCriteria();
		malebetween24and30Criteria.andSextypeNotEqualTo("女士");
		malebetween24and30Criteria.andBirthdayLessThanOrEqualTo(year24.toDate());
		malebetween24and30Criteria.andBirthdayGreaterThan(year31.toDate());
		malebetween24and30Criteria.andInserttimeBetween(startDate, endDate);
		int malebetween24and30num = nlcuserMapper.countByExample(malebetween24and30Example);
		if (0 == maleSum) {
			resmap.put("malebetween24and30", "0.0");
		} else {
			resmap.put("malebetween24and30", format.format(malebetween24and30num / maleSum));
		}

		NlcuserExample febetween31and40Example = new NlcuserExample(); // 31到40岁的女士
		Criteria febetween31and40Criteria = febetween31and40Example.createCriteria();
		febetween31and40Criteria.andSextypeEqualTo("女士");
		febetween31and40Criteria.andBirthdayLessThanOrEqualTo(year31.toDate());
		febetween31and40Criteria.andBirthdayGreaterThan(year41.toDate());
		febetween31and40Criteria.andInserttimeBetween(startDate, endDate);
		int febetween31and40num = nlcuserMapper.countByExample(febetween31and40Example);
		if (0 == feSum) {
			resmap.put("febetween31and40", "0.0");
		} else {
			resmap.put("febetween31and40", format.format(febetween31and40num / feSum));
		}

		NlcuserExample malebetween31and40Example = new NlcuserExample(); // 31到40岁的男士
		Criteria malebetween31and40Criteria = malebetween31and40Example.createCriteria();
		malebetween31and40Criteria.andSextypeNotEqualTo("女士");
		malebetween31and40Criteria.andBirthdayLessThanOrEqualTo(year31.toDate());
		malebetween31and40Criteria.andBirthdayGreaterThan(year41.toDate());
		malebetween31and40Criteria.andInserttimeBetween(startDate, endDate);
		int malebetween31and40num = nlcuserMapper.countByExample(malebetween31and40Example);
		if (0 == maleSum) {
			resmap.put("malebetween31and40", "0.0");
		} else {
			resmap.put("malebetween31and40", format.format(malebetween31and40num / maleSum));
		}

		NlcuserExample feabove40Example = new NlcuserExample(); // 40岁以上的女士
		Criteria feabove40Criteria = feabove40Example.createCriteria();
		feabove40Criteria.andSextypeEqualTo("女士");
		feabove40Criteria.andBirthdayLessThanOrEqualTo(year41.toDate());
		feabove40Criteria.andInserttimeBetween(startDate, endDate);
		int feabove40num = nlcuserMapper.countByExample(feabove40Example);

		NlcuserExample fewExample = new NlcuserExample(); // 40岁以上的女士
		Criteria fewCriteria = fewExample.createCriteria();
		fewCriteria.andSextypeEqualTo("女士");
		fewCriteria.andBirthdayIsNull();
		fewCriteria.andInserttimeBetween(startDate, endDate);
		int fewnum = nlcuserMapper.countByExample(fewExample);
		feabove40num += fewnum;

		if (0 == feSum) {
			resmap.put("feabove40", "0.0");
		} else {
			resmap.put("feabove40", format.format(feabove40num / feSum));
		}

		NlcuserExample maleabove40Example = new NlcuserExample(); // 40岁以上的男士
		Criteria maleabove40Criteria = maleabove40Example.createCriteria();
		maleabove40Criteria.andSextypeNotEqualTo("女士");
		maleabove40Criteria.andBirthdayLessThanOrEqualTo(year41.toDate());
		maleabove40Criteria.andInserttimeBetween(startDate, endDate);
		int maleabove40num = nlcuserMapper.countByExample(maleabove40Example);

		NlcuserExample malewExample = new NlcuserExample(); // 40岁以上的男士
		Criteria malewCriteria = malewExample.createCriteria();
		malewCriteria.andSextypeNotEqualTo("女士");
		malewCriteria.andBirthdayIsNull();
		malewCriteria.andInserttimeBetween(startDate, endDate);
		int malewnum = nlcuserMapper.countByExample(malewExample);
		maleabove40num += malewnum;

		if (0 == maleSum) {
			resmap.put("maleabove40", "0.0");
		} else {
			resmap.put("maleabove40", format.format(maleabove40num / maleSum));
		}

		return resmap;
	}

	/**
	 * 跳转到用户分析/用户属性 第一个图的第三个小图年龄分布数据
	 */
	@Override
	public List<UserTypeDis> yhsx1p3(Date startDate, Date endDate) {
		List<UserTypeDis> resList = new ArrayList<UserTypeDis>();

		DateTime now = new DateTime(endDate);
		DateTime year19 = now.minusYears(19);
		DateTime year24 = now.minusYears(24);
		DateTime year31 = now.minusYears(31);
		DateTime year41 = now.minusYears(41);

		// 实名用户
		NlcuserExample smlt18Example = new NlcuserExample(); // 18岁以下
		Criteria smlt18Criteria = smlt18Example.createCriteria();
		smlt18Criteria.andRdrolecodeEqualTo("JS0001");
		smlt18Criteria.andBirthdayGreaterThan(year19.toDate());
		smlt18Criteria.andInserttimeBetween(startDate, endDate);
		int smlt18Num = nlcuserMapper.countByExample(smlt18Example);

		NlcuserExample smbt19And23Example = new NlcuserExample(); // 19~23岁
		Criteria smbt19And23Criteria = smbt19And23Example.createCriteria();
		smbt19And23Criteria.andRdrolecodeEqualTo("JS0001");
		smbt19And23Criteria.andBirthdayLessThanOrEqualTo(year19.toDate());
		smbt19And23Criteria.andBirthdayGreaterThan(year24.toDate());
		smbt19And23Criteria.andInserttimeBetween(startDate, endDate);
		int smbt19And23Num = nlcuserMapper.countByExample(smbt19And23Example);

		NlcuserExample smbt24And30Example = new NlcuserExample(); // 24~30岁
		Criteria smbt24And30Criteria = smbt24And30Example.createCriteria();
		smbt24And30Criteria.andRdrolecodeEqualTo("JS0001");
		smbt24And30Criteria.andBirthdayLessThanOrEqualTo(year24.toDate());
		smbt24And30Criteria.andBirthdayGreaterThan(year31.toDate());
		smbt24And30Criteria.andInserttimeBetween(startDate, endDate);
		int smbt24And30Num = nlcuserMapper.countByExample(smbt24And30Example);

		NlcuserExample smbt31And40Example = new NlcuserExample(); // 31~40岁
		Criteria smbt31And40Criteria = smbt31And40Example.createCriteria();
		smbt31And40Criteria.andRdrolecodeEqualTo("JS0001");
		smbt31And40Criteria.andBirthdayLessThanOrEqualTo(year31.toDate());
		smbt31And40Criteria.andBirthdayGreaterThan(year41.toDate());
		smbt31And40Criteria.andInserttimeBetween(startDate, endDate);
		int smbt31And40Num = nlcuserMapper.countByExample(smbt31And40Example);

		NlcuserExample smabove40Example = new NlcuserExample(); // 40岁以上
		Criteria smabove40Criteria = smabove40Example.createCriteria();
		smabove40Criteria.andRdrolecodeEqualTo("JS0001");
		smabove40Criteria.andBirthdayLessThanOrEqualTo(year41.toDate());
		smabove40Criteria.andInserttimeBetween(startDate, endDate);
		int smabove40Num = nlcuserMapper.countByExample(smabove40Example);

		NlcuserExample wexample = new NlcuserExample();
		Criteria wCriteria = wexample.createCriteria();
		wCriteria.andRdrolecodeEqualTo("JS0001");
		wCriteria.andBirthdayIsNull();
		wCriteria.andInserttimeBetween(startDate, endDate);
		int w = nlcuserMapper.countByExample(wexample);
		smabove40Num += w;

		NlcuserExample smzjExample = new NlcuserExample(); // 实名总计
		Criteria smzjCriteria = smzjExample.createCriteria();
		smzjCriteria.andRdrolecodeEqualTo("JS0001");
		smzjCriteria.andInserttimeBetween(startDate, endDate);
		int smzjNum = nlcuserMapper.countByExample(smzjExample);

		UserTypeDis smuserTypeDis = new UserTypeDis();
		smuserTypeDis.setText("实名用户");
		smuserTypeDis.setLevel1(smlt18Num);
		smuserTypeDis.setLevel2(smbt19And23Num);
		smuserTypeDis.setLevel3(smbt24And30Num);
		smuserTypeDis.setLevel4(smbt31And40Num);
		smuserTypeDis.setLevel5(smabove40Num);
		smuserTypeDis.setSum(smzjNum);
		resList.add(smuserTypeDis);

		// 持卡用户
		NlcuserExample cklt18Example = new NlcuserExample(); // 18岁以下
		Criteria cklt18Criteria = cklt18Example.createCriteria();
		cklt18Criteria.andRdrolecodeEqualTo("JS0002");
		cklt18Criteria.andBirthdayGreaterThan(year19.toDate());
		cklt18Criteria.andInserttimeBetween(startDate, endDate);
		int cklt18Num = nlcuserMapper.countByExample(cklt18Example);

		NlcuserExample ckbt19And23Example = new NlcuserExample(); // 19~23岁
		Criteria ckbt19And23Criteria = ckbt19And23Example.createCriteria();
		ckbt19And23Criteria.andRdrolecodeEqualTo("JS0002");
		ckbt19And23Criteria.andBirthdayLessThanOrEqualTo(year19.toDate());
		ckbt19And23Criteria.andBirthdayGreaterThan(year24.toDate());
		ckbt19And23Criteria.andInserttimeBetween(startDate, endDate);
		int ckbt19And23Num = nlcuserMapper.countByExample(ckbt19And23Example);

		NlcuserExample ckbt24And30Example = new NlcuserExample(); // 24~30岁
		Criteria ckbt24And30Criteria = ckbt24And30Example.createCriteria();
		ckbt24And30Criteria.andRdrolecodeEqualTo("JS0002");
		ckbt24And30Criteria.andBirthdayLessThanOrEqualTo(year24.toDate());
		ckbt24And30Criteria.andBirthdayGreaterThan(year31.toDate());
		ckbt24And30Criteria.andInserttimeBetween(startDate, endDate);
		int ckbt24And30Num = nlcuserMapper.countByExample(ckbt24And30Example);

		NlcuserExample ckbt31And40Example = new NlcuserExample(); // 31~40岁
		Criteria ckbt31And40Criteria = ckbt31And40Example.createCriteria();
		ckbt31And40Criteria.andRdrolecodeEqualTo("JS0002");
		ckbt31And40Criteria.andBirthdayLessThanOrEqualTo(year31.toDate());
		ckbt31And40Criteria.andBirthdayGreaterThan(year41.toDate());
		ckbt31And40Criteria.andInserttimeBetween(startDate, endDate);
		int ckbt31And40Num = nlcuserMapper.countByExample(ckbt31And40Example);

		NlcuserExample ckabove40Example = new NlcuserExample(); // 40岁以上
		Criteria ckabove40Criteria = ckabove40Example.createCriteria();
		ckabove40Criteria.andRdrolecodeEqualTo("JS0002");
		ckabove40Criteria.andBirthdayLessThanOrEqualTo(year41.toDate());
		ckabove40Criteria.andInserttimeBetween(startDate, endDate);
		int ckabove40Num = nlcuserMapper.countByExample(ckabove40Example);

		NlcuserExample ckwExample = new NlcuserExample(); // 40岁以上
		Criteria ckwCriteria = ckwExample.createCriteria();
		ckwCriteria.andRdrolecodeEqualTo("JS0002");
		ckwCriteria.andBirthdayIsNull();
		ckwCriteria.andInserttimeBetween(startDate, endDate);
		int ckwNum = nlcuserMapper.countByExample(ckwExample);
		ckabove40Num += ckwNum;

		NlcuserExample ckzjExample = new NlcuserExample(); // 持卡总计
		Criteria ckzjCriteria = ckzjExample.createCriteria();
		ckzjCriteria.andRdrolecodeEqualTo("JS0002");
		ckzjCriteria.andInserttimeBetween(startDate, endDate);
		int ckzjNum = nlcuserMapper.countByExample(ckzjExample);

		UserTypeDis ckuserTypeDis = new UserTypeDis();
		ckuserTypeDis.setText("持卡用户");
		ckuserTypeDis.setLevel1(cklt18Num);
		ckuserTypeDis.setLevel2(ckbt19And23Num);
		ckuserTypeDis.setLevel3(ckbt24And30Num);
		ckuserTypeDis.setLevel4(ckbt31And40Num);
		ckuserTypeDis.setLevel5(ckabove40Num);
		ckuserTypeDis.setSum(ckzjNum);
		resList.add(ckuserTypeDis);

		// 总计
		UserTypeDis zjuserTypeDis = new UserTypeDis();
		zjuserTypeDis.setText("总计");
		zjuserTypeDis.setLevel1(cklt18Num + smlt18Num);
		zjuserTypeDis.setLevel2(ckbt19And23Num + smbt19And23Num);
		zjuserTypeDis.setLevel3(ckbt24And30Num + smbt24And30Num);
		zjuserTypeDis.setLevel4(ckbt31And40Num + smbt31And40Num);
		zjuserTypeDis.setLevel5(ckabove40Num + smabove40Num);
		zjuserTypeDis.setSum(ckzjNum + smzjNum);
		resList.add(zjuserTypeDis);

		return resList;
	}

	/**
	 * 跳转到用户分析/用户属性 第一个图的第三个小图性别分布数据
	 */
	@Override
	public List<SexDis> yhsx1p3_2(Date startDate, Date endDate) {
		List<SexDis> resList = new ArrayList<SexDis>();

		// 实名用户
		NlcuserExample smmanExample = new NlcuserExample(); // 男
		Criteria smmanCriteria = smmanExample.createCriteria();
		smmanCriteria.andSextypeNotEqualTo("女士");
		smmanCriteria.andRdrolecodeEqualTo("JS0001");
		smmanCriteria.andInserttimeBetween(startDate, endDate);
		int smmanNum = nlcuserMapper.countByExample(smmanExample);

		NlcuserExample smwomanExample = new NlcuserExample(); // 女
		Criteria smwomanCriteria = smwomanExample.createCriteria();
		smwomanCriteria.andSextypeEqualTo("女士");
		smwomanCriteria.andRdrolecodeEqualTo("JS0001");
		smwomanCriteria.andInserttimeBetween(startDate, endDate);
		int smwomanNum = nlcuserMapper.countByExample(smwomanExample);

		NlcuserExample smzjExample = new NlcuserExample(); // 总计
		Criteria smzjCriteria = smzjExample.createCriteria();
		smzjCriteria.andRdrolecodeEqualTo("JS0001");
		smzjCriteria.andInserttimeBetween(startDate, endDate);
		int smzjNum = nlcuserMapper.countByExample(smzjExample);

		SexDis smSexDis = new SexDis();
		smSexDis.setText("实名用户");
		smSexDis.setLevel1(smmanNum);
		smSexDis.setLevel2(smwomanNum);
		smSexDis.setSum(smzjNum);
		resList.add(smSexDis);

		// 持卡用户
		NlcuserExample ckmanExample = new NlcuserExample(); // 男
		Criteria ckmanCriteria = ckmanExample.createCriteria();
		ckmanCriteria.andSextypeNotEqualTo("女士");
		ckmanCriteria.andRdrolecodeEqualTo("JS0002");
		ckmanCriteria.andInserttimeBetween(startDate, endDate);
		int ckmanNum = nlcuserMapper.countByExample(ckmanExample);

		NlcuserExample ckwomanExample = new NlcuserExample(); // 女
		Criteria ckwomanCriteria = ckwomanExample.createCriteria();
		ckwomanCriteria.andSextypeEqualTo("女士");
		ckwomanCriteria.andRdrolecodeEqualTo("JS0002");
		ckwomanCriteria.andInserttimeBetween(startDate, endDate);
		int ckwomanNum = nlcuserMapper.countByExample(ckwomanExample);

		NlcuserExample ckzjExample = new NlcuserExample(); // 总计
		Criteria ckzjCriteria = ckzjExample.createCriteria();
		ckzjCriteria.andRdrolecodeEqualTo("JS0002");
		ckzjCriteria.andInserttimeBetween(startDate, endDate);
		int ckzjNum = nlcuserMapper.countByExample(ckzjExample);

		SexDis ckSexDis = new SexDis();
		ckSexDis.setText("持卡用户");
		ckSexDis.setLevel1(ckmanNum);
		ckSexDis.setLevel2(ckwomanNum);
		ckSexDis.setSum(ckzjNum);
		resList.add(ckSexDis);

		SexDis zjSexDis = new SexDis();
		zjSexDis.setText("总计");
		zjSexDis.setLevel1(ckmanNum + smmanNum);
		zjSexDis.setLevel2(ckwomanNum + smwomanNum);
		zjSexDis.setSum(ckzjNum + smzjNum);
		resList.add(zjSexDis);

		return resList;
	}

	/**
	 * 用户分析/用户属性 学历分布的环形数据 update by JJJ 20170222 pm
	 * 
	 * @return
	 */
	@Override
	public Map<String, String> yhsxxl(Date startDate, Date endDate) {
		Map<String, String> resmap = new HashMap<String, String>();
		NlcuserExample example = new NlcuserExample();
		Criteria criteria = example.createCriteria();
		criteria.andInserttimeBetween(startDate, endDate);
		double sum = nlcuserMapper.countByExample(example) + 0.0; // 总人数

		NumberFormat format = NumberFormat.getPercentInstance();// 获取格式化类实例
		format.setMinimumFractionDigits(2);// 设置小数位

		// 本科
		NlcuserExample benkeExample = new NlcuserExample();
		Criteria benkeCriteria = benkeExample.createCriteria();
		benkeCriteria.andEducationEqualTo("本科");
		benkeCriteria.andInserttimeBetween(startDate, endDate);
		int benkeNum = nlcuserMapper.countByExample(benkeExample);
		if (0 == sum) {
			resmap.put("benkeRatio", "0.0");
		} else {
			resmap.put("benkeRatio", format.format(benkeNum / sum));
		}

		// 专科
		NlcuserExample zhuankeExample = new NlcuserExample();
		Criteria zhuankeCriteria = zhuankeExample.createCriteria();
		zhuankeCriteria.andEducationEqualTo("专科");
		zhuankeCriteria.andInserttimeBetween(startDate, endDate);
		int zhuankeNum = nlcuserMapper.countByExample(zhuankeExample);
		if (0 == sum) {
			resmap.put("zhuankeRatio", "0.0");
		} else {
			resmap.put("zhuankeRatio", format.format(zhuankeNum / sum));
		}

		// 硕士
		NlcuserExample shuoshiExample = new NlcuserExample();
		Criteria shuoshiCriteria = shuoshiExample.createCriteria();
		shuoshiCriteria.andEducationEqualTo("硕士");
		shuoshiCriteria.andInserttimeBetween(startDate, endDate);
		int shuoshiNum = nlcuserMapper.countByExample(shuoshiExample);
		if (0 == sum) {
			resmap.put("shuoshiRatio", "0.0");
		} else {
			resmap.put("shuoshiRatio", format.format(shuoshiNum / sum));
		}

		// 博士
		NlcuserExample boshiExample = new NlcuserExample();
		Criteria boshiCriteria = boshiExample.createCriteria();
		boshiCriteria.andEducationEqualTo("博士");
		boshiCriteria.andInserttimeBetween(startDate, endDate);
		int boshiNum = nlcuserMapper.countByExample(boshiExample);
		if (0 == sum) {
			resmap.put("boshiRatio", "0.0");
		} else {
			resmap.put("boshiRatio", format.format(boshiNum / sum));
		}

		// 专科以下
		NlcuserExample qitaExample = new NlcuserExample();
		Criteria qitaCriteria = qitaExample.createCriteria();
		List<String> xlList = new ArrayList<String>();
		xlList.add("专科");
		xlList.add("本科");
		xlList.add("硕士");
		xlList.add("博士");
		qitaCriteria.andEducationNotIn(xlList);
		qitaCriteria.andInserttimeBetween(startDate, endDate);
		int qitaNum = nlcuserMapper.countByExample(qitaExample);
		if (0 == sum) {
			resmap.put("qitaRatio", "0.0");
		} else {
			resmap.put("qitaRatio", format.format(qitaNum / sum));
		}

		return resmap;
	}

	/**
	 * 用户分析/用户属性 学历分布的table update by JJJ 20170222 pm
	 * 
	 * @return
	 */
	@Override
	public List<UserTypeDis> yhsxxlsj(Date startDate, Date endDate) {
		List<UserTypeDis> resList = new ArrayList<UserTypeDis>();

		List<String> xlList = new ArrayList<String>();
		xlList.add("专科");
		xlList.add("本科");
		xlList.add("硕士");
		xlList.add("博士");

		String[] userType = { "", "JS0001", "JS0002" };

		for (int i = 1; i <= 2; i++) {
			UserTypeDis ele = new UserTypeDis();

			for (int j = 1; j <= 7; j++) {
				if (j == 1 && i == 1) {
					ele.setText("实名用户");
					continue;
				} else if (j == 1 && i == 2) {
					ele.setText("持卡用户");
					continue;
				}

				NlcuserExample example = new NlcuserExample(); // 专科以下
				Criteria criteria = example.createCriteria();
				criteria.andInserttimeBetween(startDate, endDate);
				criteria.andRdrolecodeEqualTo(userType[i]);
				if (j == 2) {
					criteria.andEducationNotIn(xlList);
					int count = nlcuserMapper.countByExample(example);
					ele.setLevel1(count);
				} else if (j == 3) {
					criteria.andEducationEqualTo("专科");
					int count = nlcuserMapper.countByExample(example);
					ele.setLevel2(count);
				} else if (j == 4) {
					criteria.andEducationEqualTo("本科");
					ele.setLevel3(nlcuserMapper.countByExample(example));
				} else if (j == 5) {
					criteria.andEducationEqualTo("硕士");
					ele.setLevel4(nlcuserMapper.countByExample(example));
				} else if (j == 6) {
					criteria.andEducationEqualTo("博士");
					ele.setLevel5(nlcuserMapper.countByExample(example));
				} else {
					ele.setSum(nlcuserMapper.countByExample(example));
				}
			}

			resList.add(ele);
		}

		UserTypeDis lele = new UserTypeDis();
		lele.setText("总计");
		lele.setLevel1(resList.get(0).getLevel1() + resList.get(1).getLevel1());
		lele.setLevel2(resList.get(0).getLevel2() + resList.get(1).getLevel2());
		lele.setLevel3(resList.get(0).getLevel3() + resList.get(1).getLevel3());
		lele.setLevel4(resList.get(0).getLevel4() + resList.get(1).getLevel4());
		lele.setLevel5(resList.get(0).getLevel5() + resList.get(1).getLevel5());
		lele.setSum(resList.get(0).getSum() + resList.get(1).getSum());
		resList.add(lele);

		return resList;
	}

	/**
	 * 终端属性/新增用户 os1的数据 新增用户
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	@Override
	public Map<String, String> xzyhOs1(Date startDate, Date endDate) {
		Map<String, String> resmap = new HashMap<String, String>();

		DateTime dt = new DateTime(endDate);
		DateTime endMediumDay = dt.plusDays(1);
		NumberFormat format = NumberFormat.getPercentInstance();// 获取格式化类实例
		format.setMinimumFractionDigits(2);// 设置小数位

		AppstatistExample example = new AppstatistExample();
		cn.gov.nlc.pojo.AppstatistExample.Criteria sumCriteria = example.createCriteria();
		sumCriteria.andTimeBetween(startDate, endMediumDay.toDate());
		double sum = appstatistMapper.countByExample(example) + 0.0; // 装机量总人数

		// iphone的占比
		AppstatistExample iphoneExample = new AppstatistExample();
		cn.gov.nlc.pojo.AppstatistExample.Criteria iphoneCriteria = iphoneExample.createCriteria();
		iphoneCriteria.andBaseosLike("iphone%");
		iphoneCriteria.andTimeBetween(startDate, endMediumDay.toDate());
		int iphoneNum = appstatistMapper.countByExample(iphoneExample);
		if (sum == 0) {
			resmap.put("iphoneRatio", "0.0");
		} else {
			resmap.put("iphoneRatio", format.format(iphoneNum / sum));
		}

		// android的占比
		AppstatistExample androidExample = new AppstatistExample();
		cn.gov.nlc.pojo.AppstatistExample.Criteria androidCriteria = androidExample.createCriteria();
		androidCriteria.andBaseosLike("andro%");
		androidCriteria.andTimeBetween(startDate, endMediumDay.toDate());
		int androidNum = appstatistMapper.countByExample(androidExample);
		if (sum == 0) {
			resmap.put("androidRatio", "0.0");
		} else {
			resmap.put("androidRatio", format.format(androidNum / sum));
		}

		return resmap;
	}

	/**
	 * 终端属性/增加用户/数据明细的数据
	 */
	@Override
	public List<OsDetail> xzyhOsmx(Date startDate, Date endDate) {
		List<OsDetail> list = new ArrayList<OsDetail>();

		DateTime dt = new DateTime(endDate);
		DateTime endMediumDay = dt.plusDays(1);
		NumberFormat format = NumberFormat.getPercentInstance();// 获取格式化类实例
		format.setMinimumFractionDigits(2);// 设置小数位

		String[] seekos = { "iphone%", "andro%" };
		String[] type = { "iPhone", "Android" };

		AppstatistExample example = new AppstatistExample();
		cn.gov.nlc.pojo.AppstatistExample.Criteria sumCriteria = example.createCriteria();
		sumCriteria.andTimeBetween(startDate, endMediumDay.toDate());
		double sum = appstatistMapper.countByExample(example) + 0.0; // 装机量总人数

		AppstartExample sum2Example = new AppstartExample();
		cn.gov.nlc.pojo.AppstartExample.Criteria sum2Criteria = sum2Example.createCriteria();
		sum2Criteria.andTimeBetween(startDate, endMediumDay.toDate());
		double sum2 = appstartMapper.countByExample(sum2Example) + 0.0; // 启动次数的总人数

		for (int i = 0; i <= 1; i++) {
			OsDetail osd = new OsDetail();

			// 装机量
			AppstatistExample stemExample = new AppstatistExample();
			cn.gov.nlc.pojo.AppstatistExample.Criteria criteria2 = stemExample.createCriteria();
			criteria2.andBaseosLike(seekos[i]);
			criteria2.andTimeBetween(startDate, endMediumDay.toDate());
			int temNum = appstatistMapper.countByExample(stemExample);
			osd.setBaseos(type[i]);
			osd.setNewUserNum(temNum);
			if (sum == 0) {
				osd.setNewUserRatio("0.0");
			} else {
				osd.setNewUserRatio(format.format(temNum / sum));
			}

			// 启动次数
			AppstartExample apptemExample = new AppstartExample();
			cn.gov.nlc.pojo.AppstartExample.Criteria apptemCriteria = apptemExample.createCriteria();
			apptemCriteria.andBaseosLike(seekos[i]);
			apptemCriteria.andTimeBetween(startDate, endMediumDay.toDate());
			int apptemNum = appstartMapper.countByExample(apptemExample);
			osd.setNewStartNum(apptemNum);
			if (sum2 == 0) {
				osd.setNewStartRatio("0.0");
			} else {
				osd.setNewStartRatio(format.format(apptemNum / sum2));
			}

			list.add(osd);
		}

		return list;
	}

	/**
	 * 终端属性/新增用户 os2的数据 启动次数
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	@Override
	public Map<String, String> xzyhOs2(Date startDate, Date endDate) {
		Map<String, String> resmap = new HashMap<String, String>();

		DateTime dt = new DateTime(endDate);
		DateTime endMediumDay = dt.plusDays(1);
		NumberFormat format = NumberFormat.getPercentInstance();// 获取格式化类实例
		format.setMinimumFractionDigits(2);// 设置小数位

		AppstartExample sumExample = new AppstartExample();
		cn.gov.nlc.pojo.AppstartExample.Criteria sumCriteria = sumExample.createCriteria();
		sumCriteria.andTimeBetween(startDate, endMediumDay.toDate());
		double sum = appstartMapper.countByExample(sumExample) + 0.0; // 总人数

		// iphone的占比
		AppstartExample iphoneExample = new AppstartExample();
		cn.gov.nlc.pojo.AppstartExample.Criteria iphoneCriteria = iphoneExample.createCriteria();
		iphoneCriteria.andBaseosLike("iphone%");
		iphoneCriteria.andTimeBetween(startDate, endMediumDay.toDate());
		int iphoneNum = appstartMapper.countByExample(iphoneExample);
		if (sum == 0) {
			resmap.put("iphoneRatio", "0.0");
		} else {
			resmap.put("iphoneRatio", format.format(iphoneNum / sum));
		}

		// android的占比
		AppstartExample androidExample = new AppstartExample();
		cn.gov.nlc.pojo.AppstartExample.Criteria androidCriteria = androidExample.createCriteria();
		androidCriteria.andBaseosLike("andro%");
		androidCriteria.andTimeBetween(startDate, endMediumDay.toDate());
		int androidNum = appstartMapper.countByExample(androidExample);
		if (sum == 0) {
			resmap.put("androidRatio", "0.0");
		} else {
			resmap.put("androidRatio", format.format(androidNum / sum));
		}

		return resmap;
	}

	/**
	 * 地域分析条形图的数据
	 */
	@Override
	public List<DyfxPoExt> dyfxData(Date startDate, Date endDate) {
		DateTime sdt = new DateTime(startDate);
		DateTime edt = new DateTime(endDate);
		String sql = "SELECT sum(num) value, location name FROM `schelocation` where time >= '"
				+ sdt.toString("yyyy-MM-dd") + "' and time <= '" + edt.toString("yyyy-MM-dd")
				+ "' group by location order by sum(num) desc limit 10";
		List<DyfxPo> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<DyfxPo>(DyfxPo.class));
		List<DyfxPoExt> reslist = new ArrayList<DyfxPoExt>();
		if (null != list && list.size() > 0) {
			for (DyfxPo dyfxPo : list) {
				DyfxPoExt ele = new DyfxPoExt();
				ele.setValue(dyfxPo.getValue());
				ele.setName(dyfxPo.getName());
				ele.setColor("#a5c2d5");
				reslist.add(ele);
			}
		} else {
			DyfxPoExt ele = new DyfxPoExt();
			ele.setValue(0);
			ele.setName("北京市");
			ele.setColor("#a5c2d5");
			reslist.add(ele);
		}

		return reslist;
	}

	/**
	 * 地域分析中列表的list
	 */
	@Override
	public EasyUiDataGridResult dyfxList(Integer page, Integer rows, Date startDate, Date endDate) {
		DateTime sdt = new DateTime(startDate);
		DateTime edt = new DateTime(endDate);

		String sql = "select count(1) from (SELECT location FROM `schelocation` where time >= '"
				+ sdt.toString("yyyy-MM-dd") + "' and time <= '" + edt.toString("yyyy-MM-dd")
				+ "' group by location) temp";
		int totalRow = jdbcTemplate.queryForInt(sql);

		int start = (page - 1) * rows;
		int limit = rows;

		String sql2 = "SELECT sum(num) value, location name FROM `schelocation` where time >= '"
				+ sdt.toString("yyyy-MM-dd") + "' and time <= '" + edt.toString("yyyy-MM-dd")
				+ "' group by location order by sum(num) desc limit " + start + " , " + limit;
		List<DyfxPo> list = jdbcTemplate.query(sql2, new BeanPropertyRowMapper<DyfxPo>(DyfxPo.class));

		EasyUiDataGridResult result = new EasyUiDataGridResult(totalRow, list);
		return result;
	}

	/**
	 * 地域分析导出excel的数据
	 */
	@Override
	public List<DyfxPo> dyfxExportList(Date startDate, Date endDate) {
		DateTime sdt = new DateTime(startDate);
		DateTime edt = new DateTime(endDate);
		String sql2 = "SELECT sum(num) value, location name FROM `schelocation` where time >= '"
				+ sdt.toString("yyyy-MM-dd") + "' and time <= '" + edt.toString("yyyy-MM-dd")
				+ "' group by location order by sum(num) desc ";
		List<DyfxPo> list = jdbcTemplate.query(sql2, new BeanPropertyRowMapper<DyfxPo>(DyfxPo.class));
		return list;
	}

	/**
	 * 实名用户数量数据 update by JJJ
	 */
	@Override
	public List<VirtualUserDetail> smyhslDataList(Date startDate, Date endDate, String type) {
		DateTime sdt = new DateTime(startDate);
		DateTime edt = new DateTime(endDate);
	//	List<Integer> dataList = new ArrayList<Integer>();
	//	List<String> labelsList = new ArrayList<String>();
		List<VirtualUserDetail> list2 = new ArrayList<VirtualUserDetail>();
		int totalRow = 0;
		String sql = "";
		if ("week".equalsIgnoreCase(type)) {
			int minus = sdt.getDayOfWeek() - 1;
			sdt = sdt.minusDays(minus); // 得到刚刚好的本周的周一
			int p = edt.getDayOfWeek();
			edt = edt.plusDays(7 - p);
			int weeks = Weeks.weeksBetween(sdt, edt).getWeeks();
			totalRow = weeks + 1;
			int base = 1;
			if (totalRow > 55) {
				base = ((Double) Math.ceil(totalRow / 55.0)).intValue();
			}
			while (sdt.getMillis() <= edt.getMillis()) {
				String str = sdt.toString("yyyy") + "第" + sdt.getWeekOfWeekyear() + "周";
				sql = "SELECT IFNULL(sum(num),0) monthNewAddNum FROM scherealnameusernum where time >= \""
						+ sdt.toString("yyyy-MM-dd") + "\" and time < \"" + sdt.plusWeeks(base).toString("yyyy-MM-dd")
						+ "\" ";
				int res = jdbcTemplate.queryForObject(sql, Integer.class);
				VirtualUserDetail v = new VirtualUserDetail();
				v.setMtime(str);
				v.setMonthNewAddNum(res);
				list2.add(v);
				sdt = sdt.plusWeeks(base);
			}
		} else if ("day".equals(type)) {
			totalRow = Days.daysBetween(sdt, edt).getDays() + 1;
			int base = 1;
			if (totalRow > 55) {
				base = ((Double) Math.ceil(totalRow / 55.0)).intValue();
			}
			while (sdt.getMillis() <= edt.getMillis()) {
				sql = "SELECT \'" + sdt.toString("yyyy-MM-dd")
						+ "\' mtime, IFNULL(sum(num),0) monthNewAddNum FROM scherealnameusernum where time >= \""
						+ sdt.toString("yyyy-MM-dd") + "\" and time < \"" + sdt.plusDays(base).toString("yyyy-MM-dd")
						+ "\" ";
				VirtualUserDetail v = jdbcTemplate.queryForObject(sql,
						new BeanPropertyRowMapper<VirtualUserDetail>(VirtualUserDetail.class));
				list2.add(v);
				sdt = sdt.plusDays(base);
			}
		} else if ("year".equalsIgnoreCase(type)) {
			totalRow = Years.yearsBetween(sdt, edt).getYears() + 1;
			int base = 1;
			if (totalRow > 55) {
				base = ((Double) Math.ceil(totalRow / 55.0)).intValue();
			}
			while (sdt.getMillis() <= edt.getMillis()) {
				sql = "SELECT \'" + sdt.toString("yyyy")
						+ "\' mtime, IFNULL(sum(num),0) monthNewAddNum FROM scherealnameusernum where time >= \""
						+ sdt.toString("yyyy-01-01") + "\" and time < \"" + sdt.plusYears(base).toString("yyyy-01-01")
						+ "\" ";
				VirtualUserDetail v = jdbcTemplate.queryForObject(sql,new BeanPropertyRowMapper<VirtualUserDetail>(VirtualUserDetail.class));
				list2.add(v);
				sdt = sdt.plusYears(base);
			}
		} else if ("month".equalsIgnoreCase(type)) {
			totalRow = Months.monthsBetween(sdt, edt).getMonths() + 1;
			int base = 1;
			if (totalRow > 55) {
				base = ((Double) Math.ceil(totalRow / 55.0)).intValue();
			}
			while (sdt.getMillis() <= edt.getMillis()) {
				sql = "SELECT \'" + sdt.toString("yyyy-MM")
						+ "\' mtime, IFNULL(sum(num),0) monthNewAddNum FROM scherealnameusernum where time >= \""
						+ sdt.toString("yyyy-MM-01") + "\" and time < \"" + sdt.plusMonths(base).toString("yyyy-MM-01")
						+ "\" ";
				VirtualUserDetail v = jdbcTemplate.queryForObject(sql,new BeanPropertyRowMapper<VirtualUserDetail>(VirtualUserDetail.class));
				list2.add(v);
				sdt = sdt.plusMonths(base);
			}
		}
//		for (VirtualUserDetail virtualUserDetail : list2) {
//			dataList.add(virtualUserDetail.getMonthNewAddNum());
//			labelsList.add("\"" + virtualUserDetail.getMtime() + "\"");
//		}

		//String result = "{result:true,flow:" + dataList + ", labels:" + labelsList + "}";
		return list2;
	}

	// /**
	// * 实名用户的用户分页数据
	// * @param page
	// * @param rows
	// * @return
	// */
	// @Override
	// public EasyUiDataGridResult smyhs2List(Integer page, Integer rows, Date
	// startDate, Date endDate) {
	// DateTime startDateJoda = new DateTime(startDate);
	// DateTime endDateJoda = new DateTime(endDate);
	//
	// String sql = "select count(1) from (SELECT DATE_FORMAT(time,'%Y-%m')
	// mtime, sum(num) monthNewAddNum FROM scherealnameusernum where time >=
	// \""+startDateJoda.toString("yyyy-MM-01")+"\" and time <= \"" +
	// endDateJoda.plusMonths(1).minusDays(1).toString("yyyy-MM-dd") + "\" group
	// by DATE_FORMAT(time,'%Y-%m') ) temp";
	// int totalRow = jdbcTemplate.queryForInt(sql);
	//
	// int start = (page - 1) * rows;
	// int limit = rows;
	//
	// String sql2 = "SELECT DATE_FORMAT(time,'%Y-%m') mtime, sum(num)
	// monthNewAddNum FROM scherealnameusernum where time >=
	// \""+startDateJoda.toString("yyyy-MM-01")+"\" and time <= \"" +
	// endDateJoda.plusMonths(1).minusDays(1).toString("yyyy-MM-dd") + "\" group
	// by DATE_FORMAT(time,'%Y-%m') order by mtime desc limit "+start+" ,
	// "+limit;
	// List<VirtualUserDetail> list2 = jdbcTemplate.query(sql2, new
	// BeanPropertyRowMapper<VirtualUserDetail>(VirtualUserDetail.class));
	//
	// DateTimeFormatter format = DateTimeFormat.forPattern("yyyy-MM-dd");
	//
	// if(null != list2 && list2.size() > 0) {
	// for (VirtualUserDetail virtualUserDetail : list2) {
	// String mtime = virtualUserDetail.getMtime();
	// DateTime temDate = DateTime.parse(mtime + "-01", format);
	// String tsql = "select sum(num) from scherealnameusernum where time >=
	// \""+temDate.toString("yyyy-01-01")+"\" and time < \"" +
	// temDate.plusMonths(1).toString("yyyy-MM-dd") + "\"";
	// int res = jdbcTemplate.queryForInt(tsql);
	// virtualUserDetail.setThisYearAccNum(res);
	// }
	// }
	//
	// EasyUiDataGridResult result = new EasyUiDataGridResult(totalRow, list2);
	// return result;
	// }
	/**
	 * 实名用户的用户分页数据 add by JJJ
	 * 
	 * @param page
	 * @param rows
	 * @return
	 */
	@Override
	public List<VirtualUserDetail> smyhtableList(Integer page, Integer rows, Date startDate, Date endDate, String type,
			boolean getAll) {
		DateTime sdt = new DateTime(startDate);
		DateTime edt = new DateTime(endDate);
		List<VirtualUserDetail> list2 = new ArrayList<VirtualUserDetail>();
		int totalRow = 0;
		if (type.equals("year")) {
			totalRow = Years.yearsBetween(sdt, edt).getYears() + 1;
		} else if (type.equals("month")) {
			totalRow = Months.monthsBetween(sdt, edt).getMonths() + 1;
		} else if (type.equals("day")) {
			totalRow = Days.daysBetween(sdt, edt).getDays() + 1;
		} else if (type.equals("week")) {
			int minus = sdt.getDayOfWeek() - 1;
			sdt = sdt.minusDays(minus); // 得到刚刚好的本周的周一
			int p = edt.getDayOfWeek();
			edt = edt.plusDays(7 - p);
			int weeks = Weeks.weeksBetween(sdt, edt).getWeeks();
			totalRow = weeks + 1;
		} else {
			return null;
		}
		int start = 0, end = 0;
		if (getAll) {
			end = totalRow;
		} else {
			start = (page - 1) * rows;
			if ((start + rows) > totalRow) {
				end = totalRow;
			} else {
				end = start + rows;
			}
		}
		String sql = "";
		for (int i = start; i < end; i++) {
			VirtualUserDetail vud = new VirtualUserDetail();
			if (type.equals("year")) {
				vud.setMtime(edt.minusYears(i).toString("yyyy"));
				sql = "SELECT IFNULL(sum(num),0) count FROM scherealnameusernum where time >= \""
						+ edt.minusYears(i).toString("yyyy-01-01") + "\" and time < \"" + edt.minusYears(i).plusYears(1).toString("yyyy-01-01")
						+ "\"  ";
			} else if (type.equals("month")) {
				vud.setMtime(edt.minusMonths(i).toString("yyyy-MM"));
				sql = "SELECT IFNULL(sum(num),0) count FROM scherealnameusernum where time >= \"" + edt.minusMonths(i).toString("yyyy-MM-01") 
						+ "\" and time < \"" +  edt.minusMonths(i).plusMonths(1).toString("yyyy-MM-01")	+ "\" " ;
			} else if (type.equals("day")) {
				vud.setMtime(edt.minusDays(i).toString("yyyy-MM-dd"));
				sql = "SELECT IFNULL(sum(num),0) count FROM scherealnameusernum where time >= \""+ edt.minusDays(i).toString("yyyy-MM-dd") + "\" and time < \"" 
						+ edt.minusDays(i).plusDays(1).toString("yyyy-MM-dd") + "\" ";
			} else if (type.equals("week")) {
				String str = "'第" + sdt.plusWeeks(i).getWeekOfWeekyear() + "周" + sdt.plusWeeks(i).toString("yyyy-MM-dd")
						+ "--" + sdt.plusWeeks(i).plusDays(6).toString("yyyy-MM-dd") + "'";
				vud.setMtime(str);
				sql = "SELECT  IFNULL(sum(num),0) count FROM scherealnameusernum where time >= \""
						+ sdt.plusWeeks(i).toString("yyyy-MM-dd") + "\" and time < \""
						+ sdt.plusWeeks(i+1).toString("yyyy-MM-dd") + "\" ";
			}
			int count = jdbcTemplate.queryForObject(sql, Integer.class);
			vud.setMonthNewAddNum(count);
			list2.add(vud);
		
		}
		
//		DateTimeFormatter format = DateTimeFormat.forPattern("yyyy-MM-dd");
//		if (null != list2 && list2.size() > 0) {
//			for (VirtualUserDetail virtualUserDetail : list2) {
//				virtualUserDetail.setThisYearAccNum(0);
//				String mtime = virtualUserDetail.getMtime();
//				String tsql = "";
//				if (type.equals("year")) {
//					virtualUserDetail.setThisYearAccNum(virtualUserDetail.getMonthNewAddNum());
//				} else {
//					if (type.equals("month")) {
//						DateTime temDate = DateTime.parse(mtime + "-01", format);
//						tsql = "select sum(num) from schevirusernum where time >= \"" + temDate.toString("yyyy-01-01")
//								+ "\" and time < \"" + temDate.plusMonths(1).toString("yyyy-MM-dd") + "\"";
//					} else if (type.equals("day")) {
//						DateTime temDate = DateTime.parse(mtime, format);
//						tsql = "select sum(num) from schevirusernum where time >= \"" + temDate.toString("yyyy-01-01")
//								+ "\" and time < \"" + temDate.plusDays(1).toString("yyyy-MM-dd") + "\"";
//					} else if (type.equals("week")) {
//						mtime = mtime.substring(mtime.indexOf("--") + 2);
//						DateTime temDate = DateTime.parse(mtime, format);
//						tsql = "select sum(num) from schevirusernum where time >= \"" + temDate.toString("yyyy-01-01")
//								+ "\" and time < \"" + temDate.plusDays(1).toString("yyyy-MM-dd") + "\"";
//					}
//					int res = jdbcTemplate.queryForObject(tsql, Integer.class);
//					virtualUserDetail.setThisYearAccNum(res);
//				}
//			}
//
//		}
//		EasyUiDataGridResult result = new EasyUiDataGridResult(totalRow, list2);
//		return result;
		return list2;
	}
	// /**
	// * 实名用户数据导出excel的list
	// */
	// @Override
	// public List<VirtualUserDetail> smyhExport(Date startDate, Date endDate) {
	// DateTime startDateJoda = new DateTime(startDate);
	// DateTime endDateJoda = new DateTime(endDate);
	//
	// String sql2 = "SELECT DATE_FORMAT(time,'%Y-%m') mtime, sum(num)
	// monthNewAddNum FROM scherealnameusernum where time >=
	// \""+startDateJoda.toString("yyyy-MM-01")+"\" and time <= \"" +
	// endDateJoda.plusMonths(1).minusDays(1).toString("yyyy-MM-dd") + "\" group
	// by DATE_FORMAT(time,'%Y-%m') order by mtime desc ";
	// List<VirtualUserDetail> list2 = jdbcTemplate.query(sql2, new
	// BeanPropertyRowMapper<VirtualUserDetail>(VirtualUserDetail.class));
	//
	// DateTimeFormatter format = DateTimeFormat.forPattern("yyyy-MM-dd");
	//
	// if(null != list2 && list2.size() > 0) {
	// for (VirtualUserDetail virtualUserDetail : list2) {
	// String mtime = virtualUserDetail.getMtime();
	// DateTime temDate = DateTime.parse(mtime + "-01", format);
	// String tsql = "select sum(num) from scherealnameusernum where time >=
	// \""+temDate.toString("yyyy-01-01")+"\" and time < \"" +
	// temDate.plusMonths(1).toString("yyyy-MM-dd") + "\"";
	// int res = jdbcTemplate.queryForInt(tsql);
	// virtualUserDetail.setThisYearAccNum(res);
	// }
	// }
	//
	// return list2;
	// }

	// /**
	// * 持卡用户数量数据
	// */
	// @Override
	// public List<Integer> ckyhslDataList(Date startDate, Date endDate) {
	// List<Integer> dataList = new ArrayList<Integer>();
	// DateTime startDateJoda = new DateTime(startDate);
	// DateTime endDateJoda = new DateTime(endDate);
	//
	// String sql = "select sum(num) from schecardusernum ";
	//
	// while(startDateJoda.getMillis() <= endDateJoda.getMillis()) {
	// int res1 = jdbcTemplate.queryForInt(sql + " where time >= \"" +
	// startDateJoda.toString("yyyy-MM-01") + "\" and time < \"" +
	// startDateJoda.plusMonths(1).toString("yyyy-MM-01") + "\"");
	// dataList.add(res1);
	// startDateJoda = startDateJoda.plusMonths(1);
	// }
	//
	// return dataList;
	// }

	/**
	 * 持卡用户数量数据 add by JJJ
	 */
	@Override
	public List<VirtualUserDetail> ckyhslDataList(Date startDate, Date endDate, String type) {
		DateTime sdt = new DateTime(startDate);
		DateTime edt = new DateTime(endDate);
//		List<Integer> dataList = new ArrayList<Integer>();
//		List<String> labelsList = new ArrayList<String>();
		List<VirtualUserDetail> list2 = new ArrayList<VirtualUserDetail>();
		int totalRow = 0;
		String sql = "";
		if ("week".equalsIgnoreCase(type)) {
			int minus = sdt.getDayOfWeek() - 1;
			sdt = sdt.minusDays(minus); // 得到刚刚好的本周的周一
			int p = edt.getDayOfWeek();
			edt = edt.plusDays(7 - p);
			int weeks = Weeks.weeksBetween(sdt, edt).getWeeks();
			totalRow = weeks + 1;
			int base = 1;
			if (totalRow > 55) {
				base = ((Double) Math.ceil(totalRow / 55.0)).intValue();
			}
			while (sdt.getMillis() <= edt.getMillis()) {
				String str = sdt.toString("yyyy") + "第" + sdt.getWeekOfWeekyear() + "周";
				sql = "SELECT IFNULL(sum(num),0) monthNewAddNum FROM schecardusernum where time >= \""
						+ sdt.toString("yyyy-MM-dd") + "\" and time < \"" + sdt.plusWeeks(base).toString("yyyy-MM-dd")
						+ "\" ";
				int res = jdbcTemplate.queryForObject(sql, Integer.class);
				VirtualUserDetail v = new VirtualUserDetail();
				v.setMtime(str);
				v.setMonthNewAddNum(res);
				list2.add(v);
				sdt = sdt.plusWeeks(base);
			}
		} else if ("day".equals(type)) {
			totalRow = Days.daysBetween(sdt, edt).getDays() + 1;
			int base = 1;
			if (totalRow > 55) {
				base = ((Double) Math.ceil(totalRow / 55.0)).intValue();
			}
			while (sdt.getMillis() <= edt.getMillis()) {
				sql = "SELECT \'" + sdt.toString("yyyy-MM-dd")
						+ "\' mtime, IFNULL(sum(num),0) monthNewAddNum FROM schecardusernum where time >= \""
						+ sdt.toString("yyyy-MM-dd") + "\" and time < \"" + sdt.plusDays(base).toString("yyyy-MM-dd")
						+ "\" ";
				VirtualUserDetail v = jdbcTemplate.queryForObject(sql,
						new BeanPropertyRowMapper<VirtualUserDetail>(VirtualUserDetail.class));
				list2.add(v);
				sdt = sdt.plusDays(base);
			}
		} else if ("year".equalsIgnoreCase(type)) {
			totalRow = Years.yearsBetween(sdt, edt).getYears() + 1;
			int base = 1;
			if (totalRow > 55) {
				base = ((Double) Math.ceil(totalRow / 55.0)).intValue();
			}
			while (sdt.getMillis() <= edt.getMillis()) {
				sql = "SELECT \'" + sdt.toString("yyyy")
						+ "\' mtime, IFNULL(sum(num),0) monthNewAddNum FROM schecardusernum where time >= \""
						+ sdt.toString("yyyy-01-01") + "\" and time < \"" + sdt.plusYears(base).toString("yyyy-01-01")
						+ "\" ";
				VirtualUserDetail v = jdbcTemplate.queryForObject(sql,new BeanPropertyRowMapper<VirtualUserDetail>(VirtualUserDetail.class));
				list2.add(v);
				sdt = sdt.plusYears(base);
			}
		} else if ("month".equalsIgnoreCase(type)) {
			totalRow = Months.monthsBetween(sdt, edt).getMonths() + 1;
			int base = 1;
			if (totalRow > 55) {
				base = ((Double) Math.ceil(totalRow / 55.0)).intValue();
			}
			while (sdt.getMillis() <= edt.getMillis()) {
				sql = "SELECT \'" + sdt.toString("yyyy-MM")
						+ "\' mtime, IFNULL(sum(num),0) monthNewAddNum FROM schecardusernum where time >= \""
						+ sdt.toString("yyyy-MM-01") + "\" and time < \"" + sdt.plusMonths(base).toString("yyyy-MM-01")
						+ "\" ";
				VirtualUserDetail v = jdbcTemplate.queryForObject(sql,new BeanPropertyRowMapper<VirtualUserDetail>(VirtualUserDetail.class));
				list2.add(v);
				sdt = sdt.plusMonths(base);
			}
		}
//		for (VirtualUserDetail virtualUserDetail : list2) {
//			dataList.add(virtualUserDetail.getMonthNewAddNum());
//			labelsList.add("\"" + virtualUserDetail.getMtime() + "\"");
//		}
//		String result = "{result:true,flow:" + dataList + ", labels:" + labelsList + "}";
		return list2;
	}

	// /**
	// * 持卡用户的用户分页数据
	// * @param page
	// * @param rows
	// * @return
	// */
	// @Override
	// public EasyUiDataGridResult ckyhs2List(Integer page, Integer rows, Date
	// startDate, Date endDate) {
	// DateTime startDateJoda = new DateTime(startDate);
	// DateTime endDateJoda = new DateTime(endDate);
	//
	// String sql = "select count(1) from (SELECT DATE_FORMAT(time,'%Y-%m')
	// mtime, sum(num) monthNewAddNum FROM schecardusernum where time >=
	// \""+startDateJoda.toString("yyyy-MM-01")+"\" and time <= \"" +
	// endDateJoda.plusMonths(1).minusDays(1).toString("yyyy-MM-dd") + "\" group
	// by DATE_FORMAT(time,'%Y-%m') ) temp";
	// int totalRow = jdbcTemplate.queryForInt(sql);
	//
	// int start = (page - 1) * rows;
	// int limit = rows;
	//
	// String sql2 = "SELECT DATE_FORMAT(time,'%Y-%m') mtime, sum(num)
	// monthNewAddNum FROM schecardusernum where time >=
	// \""+startDateJoda.toString("yyyy-MM-01")+"\" and time <= \"" +
	// endDateJoda.plusMonths(1).minusDays(1).toString("yyyy-MM-dd") + "\" group
	// by DATE_FORMAT(time,'%Y-%m') order by mtime desc limit "+start+" ,
	// "+limit;
	// List<VirtualUserDetail> list2 = jdbcTemplate.query(sql2, new
	// BeanPropertyRowMapper<VirtualUserDetail>(VirtualUserDetail.class));
	//
	// DateTimeFormatter format = DateTimeFormat.forPattern("yyyy-MM-dd");
	//
	// if(null != list2 && list2.size() > 0) {
	// for (VirtualUserDetail virtualUserDetail : list2) {
	// String mtime = virtualUserDetail.getMtime();
	// DateTime temDate = DateTime.parse(mtime + "-01", format);
	// String tsql = "select sum(num) from schecardusernum where time >=
	// \""+temDate.toString("yyyy-01-01")+"\" and time < \"" +
	// temDate.plusMonths(1).toString("yyyy-MM-dd") + "\"";
	// int res = jdbcTemplate.queryForInt(tsql);
	// virtualUserDetail.setThisYearAccNum(res);
	// }
	// }
	//
	// EasyUiDataGridResult result = new EasyUiDataGridResult(totalRow, list2);
	// return result;
	// }
	/**
	 * 持卡用户的用户分页数据 add by JJJ
	 * 
	 * @param page
	 * @param rows
	 * @return
	 */
	@Override
	public List<VirtualUserDetail> ckyhtableList(Integer page, Integer rows, Date startDate, Date endDate, String type,
			boolean getAll) {
		DateTime sdt = new DateTime(startDate);
		DateTime edt = new DateTime(endDate);

		List<VirtualUserDetail> list2 = new ArrayList<VirtualUserDetail>();
		int totalRow = 0;
		if (type.equals("year")) {
			totalRow = Years.yearsBetween(sdt, edt).getYears() + 1;
		} else if (type.equals("month")) {
			totalRow = Months.monthsBetween(sdt, edt).getMonths() + 1;
		} else if (type.equals("day")) {
			totalRow = Days.daysBetween(sdt, edt).getDays() + 1;
		} else if (type.equals("week")) {
			int minus = sdt.getDayOfWeek() - 1;
			sdt = sdt.minusDays(minus); // 得到刚刚好的本周的周一
			int p = edt.getDayOfWeek();
			edt = edt.plusDays(7 - p);
			int weeks = Weeks.weeksBetween(sdt, edt).getWeeks();
			totalRow = weeks + 1;
		} else {
			return null;
		}
		int start = 0, end = 0;
		if (getAll) {
			end = totalRow;
		} else {
			start = (page - 1) * rows;
			if ((start + rows) > totalRow) {
				end = totalRow;
			} else {
				end = start + rows;
			}
		}
		String sql = "";
		for (int i = start; i < end; i++) {
			VirtualUserDetail vud = new VirtualUserDetail();
			if (type.equals("year")) {
				vud.setMtime(edt.minusYears(i).toString("yyyy"));
				sql = "SELECT IFNULL(sum(num),0) count FROM schecardusernum where time >= \""
						+ edt.minusYears(i).toString("yyyy-01-01") + "\" and time < \"" + edt.minusYears(i).plusYears(1).toString("yyyy-01-01")
						+ "\"  ";
			} else if (type.equals("month")) {
				vud.setMtime(edt.minusMonths(i).toString("yyyy-MM"));
				sql = "SELECT IFNULL(sum(num),0) count FROM schecardusernum where time >= \"" + edt.minusMonths(i).toString("yyyy-MM-01") 
						+ "\" and time < \"" +  edt.minusMonths(i).plusMonths(1).toString("yyyy-MM-01")	+ "\" " ;
			} else if (type.equals("day")) {
				vud.setMtime(edt.minusDays(i).toString("yyyy-MM-dd"));
				sql = "SELECT IFNULL(sum(num),0) count FROM schecardusernum where time >= \""+ edt.minusDays(i).toString("yyyy-MM-dd") + "\" and time < \"" 
						+ edt.minusDays(i).plusDays(1).toString("yyyy-MM-dd") + "\" ";
			} else if (type.equals("week")) {
				String str = "'第" + sdt.plusWeeks(i).getWeekOfWeekyear() + "周" + sdt.plusWeeks(i).toString("yyyy-MM-dd")
						+ "--" + sdt.plusWeeks(i).plusDays(6).toString("yyyy-MM-dd") + "'";
				vud.setMtime(str);
				sql = "SELECT IFNULL(sum(num),0) count FROM schecardusernum where time >= \""
						+ sdt.plusWeeks(i).toString("yyyy-MM-dd") + "\" and time < \""
						+ sdt.plusWeeks(i+1).toString("yyyy-MM-dd") + "\" ";
			}
			int count = jdbcTemplate.queryForObject(sql, Integer.class);
			vud.setMonthNewAddNum(count);
			list2.add(vud);
		
		}
//		DateTimeFormatter format = DateTimeFormat.forPattern("yyyy-MM-dd");
//		if (null != list2 && list2.size() > 0) {
//			for (VirtualUserDetail virtualUserDetail : list2) {
//				virtualUserDetail.setThisYearAccNum(0);
//				String mtime = virtualUserDetail.getMtime();
//				String tsql = "";
//				if (type.equals("year")) {
//					virtualUserDetail.setThisYearAccNum(virtualUserDetail.getMonthNewAddNum());
//				} else {
//					if (type.equals("month")) {
//						DateTime temDate = DateTime.parse(mtime + "-01", format);
//						tsql = "select sum(num) from schevirusernum where time >= \"" + temDate.toString("yyyy-01-01")
//								+ "\" and time < \"" + temDate.plusMonths(1).toString("yyyy-MM-dd") + "\"";
//					} else if (type.equals("day")) {
//						DateTime temDate = DateTime.parse(mtime, format);
//						tsql = "select sum(num) from schevirusernum where time >= \"" + temDate.toString("yyyy-01-01")
//								+ "\" and time < \"" + temDate.plusDays(1).toString("yyyy-MM-dd") + "\"";
//					} else if (type.equals("week")) {
//						mtime = mtime.substring(mtime.indexOf("--") + 2);
//						DateTime temDate = DateTime.parse(mtime, format);
//						tsql = "select sum(num) from schevirusernum where time >= \"" + temDate.toString("yyyy-01-01")
//								+ "\" and time < \"" + temDate.plusDays(1).toString("yyyy-MM-dd") + "\"";
//					}
//					int res = jdbcTemplate.queryForObject(tsql, Integer.class);
//					virtualUserDetail.setThisYearAccNum(res);
//				}
//			}
//
//		}

//		EasyUiDataGridResult result = new EasyUiDataGridResult(totalRow, list2);
//		return result;
		return list2;
	}
	// /**
	// * 持卡用户数据导出excel的list
	// */
	// @Override
	// public List<VirtualUserDetail> ckyhExport(Date startDate, Date endDate) {
	// DateTime startDateJoda = new DateTime(startDate);
	// DateTime endDateJoda = new DateTime(endDate);
	//
	// String sql2 = "SELECT DATE_FORMAT(time,'%Y-%m') mtime, sum(num)
	// monthNewAddNum FROM schecardusernum where time >=
	// \""+startDateJoda.toString("yyyy-MM-01")+"\" and time <= \"" +
	// endDateJoda.plusMonths(1).minusDays(1).toString("yyyy-MM-dd") + "\" group
	// by DATE_FORMAT(time,'%Y-%m') order by mtime desc ";
	// List<VirtualUserDetail> list2 = jdbcTemplate.query(sql2, new
	// BeanPropertyRowMapper<VirtualUserDetail>(VirtualUserDetail.class));
	//
	// DateTimeFormatter format = DateTimeFormat.forPattern("yyyy-MM-dd");
	//
	// if(null != list2 && list2.size() > 0) {
	// for (VirtualUserDetail virtualUserDetail : list2) {
	// String mtime = virtualUserDetail.getMtime();
	// DateTime temDate = DateTime.parse(mtime + "-01", format);
	// String tsql = "select sum(num) from schecardusernum where time >=
	// \""+temDate.toString("yyyy-01-01")+"\" and time < \"" +
	// temDate.plusMonths(1).toString("yyyy-MM-dd") + "\"";
	// int res = jdbcTemplate.queryForInt(tsql);
	// virtualUserDetail.setThisYearAccNum(res);
	// }
	// }
	//
	// return list2;
	// }

	/**
	 * 持卡用户统计图中的预约数据 holdrecord 表， 从scheholdrecord中取；续借数据
	 * renewrecord表，从scherenewrecord
	 */
	@Override
	public String ckyhyyxjList(Date startDate, Date endDate, String type, String flag) {
		DateTime sdt = new DateTime(startDate);
		DateTime edt = new DateTime(endDate);
		List<Integer> dataList = new ArrayList<Integer>();
		List<String> labelsList = new ArrayList<String>();
		String tableName;
		if ("1".equals(flag)) {
			tableName = "scheholdrecord";
		} else if ("2".equals(flag)) {
			tableName = "scherenewrecord";
		} else {
			return null;
		}
		int totalRow = 0;
		String sql = "";
		if ("week".equalsIgnoreCase(type)) {
			int minus = sdt.getDayOfWeek() - 1;
			sdt = sdt.minusDays(minus); // 得到刚刚好的本周的周一
			int p = edt.getDayOfWeek();
			edt = edt.plusDays(7 - p);
			int weeks = Weeks.weeksBetween(sdt, edt).getWeeks();
			totalRow = weeks + 1;
			int base = 1;
			if (totalRow > 55) {
				base = ((Double) Math.ceil(totalRow / 55.0)).intValue();
			}
			while (sdt.getMillis() <= edt.getMillis()) {
				String str = sdt.toString("yyyy") + "第" + sdt.getWeekOfWeekyear() + "周";
				sql = "SELECT IFNULL(sum(num),0) count  FROM " + tableName + " where time >= \""
						+ sdt.toString("yyyy-MM-dd") + "\" and time < \"" + sdt.plusWeeks(base).toString("yyyy-MM-dd")+ "\" ";
				int res = jdbcTemplate.queryForObject(sql, Integer.class);
				labelsList.add(str);
				dataList.add(res);
				sdt = sdt.plusWeeks(base);
			}
		} else if ("day".equals(type)) {
			totalRow = Days.daysBetween(sdt, edt).getDays() + 1;
			int base = 1;
			if (totalRow > 55) {
				base = ((Double) Math.ceil(totalRow / 55.0)).intValue();
			}
			while (sdt.getMillis() <= edt.getMillis()) {
				sql = "SELECT  IFNULL(sum(num),0) count FROM " + tableName + " where time >= \""
						+ sdt.toString("yyyy-MM-dd") + "\" and time < \"" + sdt.plusDays(base).toString("yyyy-MM-dd")+ "\" ";
				int res = jdbcTemplate.queryForObject(sql, Integer.class);
				labelsList.add(sdt.toString("yyyy-MM-dd"));
				dataList.add(res);
				sdt = sdt.plusDays(base);
			}
		} else if ("year".equalsIgnoreCase(type)) {
			totalRow = Years.yearsBetween(sdt, edt).getYears() + 1;
			int base = 1;
			if (totalRow > 55) {
				base = ((Double) Math.ceil(totalRow / 55.0)).intValue();
			}
			while (sdt.getMillis() <= edt.getMillis()) {
				sql = "SELECT IFNULL(sum(num),0) count FROM " + tableName+ "  where time >= \"" 
						+ sdt.toString("yyyy-01-01") + "\" and time < \"" + sdt.plusYears(base).toString("yyyy-01-01")+ "\"";
				int res = jdbcTemplate.queryForObject(sql, Integer.class);
				labelsList.add(sdt.toString("yyyy"));
				dataList.add(res);
				sdt = sdt.plusYears(base);
			}
		} else if ("month".equalsIgnoreCase(type)) {
			totalRow = Months.monthsBetween(sdt, edt).getMonths() + 1;
			int base = 1;
			if (totalRow > 55) {
				base = ((Double) Math.ceil(totalRow / 55.0)).intValue();
			}
			while (sdt.getMillis() <= edt.getMillis()) {
				sql = "SELECT IFNULL(sum(num),0) count FROM " + tableName+ "  where time >= \"" 
						+ sdt.toString("yyyy-MM-01") + "\" and time < \"" + sdt.plusMonths(base).toString("yyyy-MM-01")+ "\"";
				int res = jdbcTemplate.queryForObject(sql, Integer.class);
				labelsList.add(sdt.toString("yyyy-MM"));
				dataList.add(res);
				sdt = sdt.plusMonths(base);
			}
			
		}
		String result = "{result:true,flow:" + dataList + ", labels:" + labelsList + "}";
		return result;
	}
	// /**
	// * 持卡用户统计图中的预约数据 holdrecord 表， 从scheholdrecord中取
	// */
	// @Override
	// public List<Integer> ckholdList(Date startDate, Date endDate) {
	// List<Integer> dataList = new ArrayList<Integer>();
	// DateTime startDateJoda = new DateTime(startDate);
	// DateTime endDateJoda = new DateTime(endDate);
	//
	// String sql = "select sum(num) from scheholdrecord ";
	//
	// while(startDateJoda.getMillis() <= endDateJoda.getMillis()) {
	// int res1 = jdbcTemplate.queryForInt(sql + " where time >= \"" +
	// startDateJoda.toString("yyyy-MM-01") + "\" and time < \"" +
	// startDateJoda.plusMonths(1).toString("yyyy-MM-01") + "\"");
	// dataList.add(res1);
	// startDateJoda = startDateJoda.plusMonths(1);
	// }
	//
	// return dataList;
	// }
	//
	// /**
	// * 持卡用户统计图中的续借数据 renewrecord表，从scherenewrecord
	// */
	// @Override
	// public List<Integer> renewList(Date startDate, Date endDate) {
	// List<Integer> dataList = new ArrayList<Integer>();
	// DateTime startDateJoda = new DateTime(startDate);
	// DateTime endDateJoda = new DateTime(endDate);
	//
	// String sql = "select sum(num) from scherenewrecord ";
	//
	// while(startDateJoda.getMillis() <= endDateJoda.getMillis()) {
	// int res1 = jdbcTemplate.queryForInt(sql + " where time >= \"" +
	// startDateJoda.toString("yyyy-MM-01") + "\" and time < \"" +
	// startDateJoda.plusMonths(1).toString("yyyy-MM-01") + "\"");
	// dataList.add(res1);
	// startDateJoda = startDateJoda.plusMonths(1);
	// }
	//
	// return dataList;
	// }

	/**
	 * 实时统计/页面统计/表格的页面的list
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	@Override
	public List<PageStatistic> ymtjtable(Date startDate, Date endDate) {
		DateTime startDateJoda = new DateTime(startDate);
		DateTime endDateJoda = new DateTime(endDate);
		String strstartdate = startDateJoda.toString("yyyy-MM-01 00:00:00");
		String strenddate = endDateJoda.plusMonths(1).toString("yyyy-MM-01 00:00:00");

		String sql = "select z.module, l.listnum, l.listRemainTime, d.detailnum, d.detailRemainTime from "
				+ " (select module from accesslog where time >= '" + strstartdate + "' and time < '" + strenddate
				+ "' group by module) z " + " left outer JOIN "
				+ " (select module, count(1) listnum, sum(IFNULL(waittime,0)) listRemainTime from accesslog where time >= '"
				+ strstartdate + "' and time < '" + strenddate + "' and type = 0 group by module) l "
				+ " on z.module = l.module " + " left outer JOIN "
				+ " (select module, count(1) detailnum, sum(IFNULL(waittime,0)) detailRemainTime from accesslog where time >= '"
				+ strstartdate + "' and time < '" + strenddate + "' and type = 1 group by module) d "
				+ " on z.module = d.module";
		List<PageStatistic> list = jdbcTemplate.query(sql,
				new BeanPropertyRowMapper<PageStatistic>(PageStatistic.class));
		return list;
	}

	/**
	 * 启动次数含重复的数据 启动次数
	 */
	@Override
	public Collection<Object> qdcsycList(Date calcdate) {
		DateTime datetime = new DateTime(calcdate);

		String sql = "select * from (SELECT count(1) a FROM appstart where time >= '"
				+ datetime.minusHours(1).toString("yyyy-MM-dd HH:mm:ss") + "' and time < '"
				+ datetime.toString("yyyy-MM-dd HH:mm:ss") + "') b0, "
				+ "(SELECT count(1) b FROM appstart where time >= '" + datetime.toString("yyyy-MM-dd HH:mm:ss")
				+ "' and time < '" + datetime.plusHours(1).toString("yyyy-MM-dd HH:mm:ss") + "') b1, "
				+ "(SELECT count(1) c FROM appstart where time >= '"
				+ datetime.plusHours(1).toString("yyyy-MM-dd HH:mm:ss") + "' and time < '"
				+ datetime.plusHours(2).toString("yyyy-MM-dd HH:mm:ss") + "') b2, "
				+ "(SELECT count(1) d FROM appstart where time >= '"
				+ datetime.plusHours(2).toString("yyyy-MM-dd HH:mm:ss") + "' and time < '"
				+ datetime.plusHours(3).toString("yyyy-MM-dd HH:mm:ss") + "') b3, "
				+ "(SELECT count(1) e FROM appstart where time >= '"
				+ datetime.plusHours(3).toString("yyyy-MM-dd HH:mm:ss") + "' and time < '"
				+ datetime.plusHours(4).toString("yyyy-MM-dd HH:mm:ss") + "') b4, "
				+ "(SELECT count(1) f FROM appstart where time >= '"
				+ datetime.plusHours(4).toString("yyyy-MM-dd HH:mm:ss") + "' and time < '"
				+ datetime.plusHours(5).toString("yyyy-MM-dd HH:mm:ss") + "') b5, "
				+ "(SELECT count(1) g FROM appstart where time >= '"
				+ datetime.plusHours(5).toString("yyyy-MM-dd HH:mm:ss") + "' and time < '"
				+ datetime.plusHours(6).toString("yyyy-MM-dd HH:mm:ss") + "') b6, "
				+ "(SELECT count(1) h FROM appstart where time >= '"
				+ datetime.plusHours(6).toString("yyyy-MM-dd HH:mm:ss") + "' and time < '"
				+ datetime.plusHours(7).toString("yyyy-MM-dd HH:mm:ss") + "') b7, "
				+ "(SELECT count(1) i FROM appstart where time >= '"
				+ datetime.plusHours(7).toString("yyyy-MM-dd HH:mm:ss") + "' and time < '"
				+ datetime.plusHours(8).toString("yyyy-MM-dd HH:mm:ss") + "') b8, "
				+ "(SELECT count(1) j FROM appstart where time >= '"
				+ datetime.plusHours(8).toString("yyyy-MM-dd HH:mm:ss") + "' and time < '"
				+ datetime.plusHours(9).toString("yyyy-MM-dd HH:mm:ss") + "') b9, "
				+ "(SELECT count(1) k FROM appstart where time >= '"
				+ datetime.plusHours(9).toString("yyyy-MM-dd HH:mm:ss") + "' and time < '"
				+ datetime.plusHours(10).toString("yyyy-MM-dd HH:mm:ss") + "') b10, "
				+ "(SELECT count(1) l FROM appstart where time >= '"
				+ datetime.plusHours(10).toString("yyyy-MM-dd HH:mm:ss") + "' and time < '"
				+ datetime.plusHours(11).toString("yyyy-MM-dd HH:mm:ss") + "') b11, "
				+ "(SELECT count(1) m FROM appstart where time >= '"
				+ datetime.plusHours(11).toString("yyyy-MM-dd HH:mm:ss") + "' and time < '"
				+ datetime.plusHours(12).toString("yyyy-MM-dd HH:mm:ss") + "') b12, "
				+ "(SELECT count(1) n FROM appstart where time >= '"
				+ datetime.plusHours(12).toString("yyyy-MM-dd HH:mm:ss") + "' and time < '"
				+ datetime.plusHours(13).toString("yyyy-MM-dd HH:mm:ss") + "') b13, "
				+ "(SELECT count(1) o FROM appstart where time >= '"
				+ datetime.plusHours(13).toString("yyyy-MM-dd HH:mm:ss") + "' and time < '"
				+ datetime.plusHours(14).toString("yyyy-MM-dd HH:mm:ss") + "') b14, "
				+ "(SELECT count(1) p FROM appstart where time >= '"
				+ datetime.plusHours(14).toString("yyyy-MM-dd HH:mm:ss") + "' and time < '"
				+ datetime.plusHours(15).toString("yyyy-MM-dd HH:mm:ss") + "') b15, "
				+ "(SELECT count(1) q FROM appstart where time >= '"
				+ datetime.plusHours(15).toString("yyyy-MM-dd HH:mm:ss") + "' and time < '"
				+ datetime.plusHours(16).toString("yyyy-MM-dd HH:mm:ss") + "') b16, "
				+ "(SELECT count(1) r FROM appstart where time >= '"
				+ datetime.plusHours(16).toString("yyyy-MM-dd HH:mm:ss") + "' and time < '"
				+ datetime.plusHours(17).toString("yyyy-MM-dd HH:mm:ss") + "') b17, "
				+ "(SELECT count(1) s FROM appstart where time >= '"
				+ datetime.plusHours(17).toString("yyyy-MM-dd HH:mm:ss") + "' and time < '"
				+ datetime.plusHours(18).toString("yyyy-MM-dd HH:mm:ss") + "') b18, "
				+ "(SELECT count(1) t FROM appstart where time >= '"
				+ datetime.plusHours(18).toString("yyyy-MM-dd HH:mm:ss") + "' and time < '"
				+ datetime.plusHours(19).toString("yyyy-MM-dd HH:mm:ss") + "') b19, "
				+ "(SELECT count(1) u FROM appstart where time >= '"
				+ datetime.plusHours(19).toString("yyyy-MM-dd HH:mm:ss") + "' and time < '"
				+ datetime.plusHours(20).toString("yyyy-MM-dd HH:mm:ss") + "') b20, "
				+ "(SELECT count(1) v FROM appstart where time >= '"
				+ datetime.plusHours(20).toString("yyyy-MM-dd HH:mm:ss") + "' and time < '"
				+ datetime.plusHours(21).toString("yyyy-MM-dd HH:mm:ss") + "') b21, "
				+ "(SELECT count(1) w FROM appstart where time >= '"
				+ datetime.plusHours(21).toString("yyyy-MM-dd HH:mm:ss") + "' and time < '"
				+ datetime.plusHours(22).toString("yyyy-MM-dd HH:mm:ss") + "') b22, "
				+ "(SELECT count(1) x FROM appstart where time >= '"
				+ datetime.plusHours(22).toString("yyyy-MM-dd HH:mm:ss") + "' and time < '"
				+ datetime.plusHours(23).toString("yyyy-MM-dd HH:mm:ss") + "') b23, "
				+ "(SELECT count(1) y FROM appstart where time >= '"
				+ datetime.plusHours(23).toString("yyyy-MM-dd HH:mm:ss") + "' and time < '"
				+ datetime.plusHours(24).toString("yyyy-MM-dd HH:mm:ss") + "') b24 ";
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
		Map<String, Object> map = list.get(0);
		Collection<Object> values = map.values();
		return values;
	}

	/**
	 * 启动次数去重的数据 用户数量
	 */
	@Override
	public Collection<Object> qdcsqcList(Date calcdate) {
		DateTime datetime = new DateTime(calcdate);

		String sql = "select * from (SELECT count(1) a FROM (SELECT clientid FROM appstart where time >= '"
				+ datetime.minusHours(1).toString("yyyy-MM-dd HH:mm:ss") + "' and time < '"
				+ datetime.toString("yyyy-MM-dd HH:mm:ss") + "' group by clientid) aa0) b0, "
				+ "(SELECT count(1) b FROM (SELECT clientid FROM appstart where time >= '"
				+ datetime.toString("yyyy-MM-dd HH:mm:ss") + "' and time < '"
				+ datetime.plusHours(1).toString("yyyy-MM-dd HH:mm:ss") + "' group by clientid) aa) b1, "
				+ "(SELECT count(1) c FROM (SELECT clientid FROM appstart where time >= '"
				+ datetime.plusHours(1).toString("yyyy-MM-dd HH:mm:ss") + "' and time < '"
				+ datetime.plusHours(2).toString("yyyy-MM-dd HH:mm:ss") + "' group by clientid) bb) b2, "
				+ "(SELECT count(1) d FROM (SELECT clientid FROM appstart where time >= '"
				+ datetime.plusHours(2).toString("yyyy-MM-dd HH:mm:ss") + "' and time < '"
				+ datetime.plusHours(3).toString("yyyy-MM-dd HH:mm:ss") + "' group by clientid) cc) b3, "
				+ "(SELECT count(1) e FROM (SELECT clientid FROM appstart where time >= '"
				+ datetime.plusHours(3).toString("yyyy-MM-dd HH:mm:ss") + "' and time < '"
				+ datetime.plusHours(4).toString("yyyy-MM-dd HH:mm:ss") + "' group by clientid) dd) b4, "
				+ "(SELECT count(1) f FROM (SELECT clientid FROM appstart where time >= '"
				+ datetime.plusHours(4).toString("yyyy-MM-dd HH:mm:ss") + "' and time < '"
				+ datetime.plusHours(5).toString("yyyy-MM-dd HH:mm:ss") + "' group by clientid) ee) b5, "
				+ "(SELECT count(1) g FROM (SELECT clientid FROM appstart where time >= '"
				+ datetime.plusHours(5).toString("yyyy-MM-dd HH:mm:ss") + "' and time < '"
				+ datetime.plusHours(6).toString("yyyy-MM-dd HH:mm:ss") + "' group by clientid) ff) b6, "
				+ "(SELECT count(1) h FROM (SELECT clientid FROM appstart where time >= '"
				+ datetime.plusHours(6).toString("yyyy-MM-dd HH:mm:ss") + "' and time < '"
				+ datetime.plusHours(7).toString("yyyy-MM-dd HH:mm:ss") + "' group by clientid) gg) b7, "
				+ "(SELECT count(1) i FROM (SELECT clientid FROM appstart where time >= '"
				+ datetime.plusHours(7).toString("yyyy-MM-dd HH:mm:ss") + "' and time < '"
				+ datetime.plusHours(8).toString("yyyy-MM-dd HH:mm:ss") + "' group by clientid) hh) b8, "
				+ "(SELECT count(1) j FROM (SELECT clientid FROM appstart where time >= '"
				+ datetime.plusHours(8).toString("yyyy-MM-dd HH:mm:ss") + "' and time < '"
				+ datetime.plusHours(9).toString("yyyy-MM-dd HH:mm:ss") + "' group by clientid) ii) b9, "
				+ "(SELECT count(1) k FROM (SELECT clientid FROM appstart where time >= '"
				+ datetime.plusHours(9).toString("yyyy-MM-dd HH:mm:ss") + "' and time < '"
				+ datetime.plusHours(10).toString("yyyy-MM-dd HH:mm:ss") + "' group by clientid) jj) b10, "
				+ "(SELECT count(1) l FROM (SELECT clientid FROM appstart where time >= '"
				+ datetime.plusHours(10).toString("yyyy-MM-dd HH:mm:ss") + "' and time < '"
				+ datetime.plusHours(11).toString("yyyy-MM-dd HH:mm:ss") + "' group by clientid) kk) b11, "
				+ "(SELECT count(1) m FROM (SELECT clientid FROM appstart where time >= '"
				+ datetime.plusHours(11).toString("yyyy-MM-dd HH:mm:ss") + "' and time < '"
				+ datetime.plusHours(12).toString("yyyy-MM-dd HH:mm:ss") + "' group by clientid) ll) b12, "
				+ "(SELECT count(1) n FROM (SELECT clientid FROM appstart where time >= '"
				+ datetime.plusHours(12).toString("yyyy-MM-dd HH:mm:ss") + "' and time < '"
				+ datetime.plusHours(13).toString("yyyy-MM-dd HH:mm:ss") + "' group by clientid) mm) b13, "
				+ "(SELECT count(1) o FROM (SELECT clientid FROM appstart where time >= '"
				+ datetime.plusHours(13).toString("yyyy-MM-dd HH:mm:ss") + "' and time < '"
				+ datetime.plusHours(14).toString("yyyy-MM-dd HH:mm:ss") + "' group by clientid) nn) b14, "
				+ "(SELECT count(1) p FROM (SELECT clientid FROM appstart where time >= '"
				+ datetime.plusHours(14).toString("yyyy-MM-dd HH:mm:ss") + "' and time < '"
				+ datetime.plusHours(15).toString("yyyy-MM-dd HH:mm:ss") + "' group by clientid) oo) b15, "
				+ "(SELECT count(1) q FROM (SELECT clientid FROM appstart where time >= '"
				+ datetime.plusHours(15).toString("yyyy-MM-dd HH:mm:ss") + "' and time < '"
				+ datetime.plusHours(16).toString("yyyy-MM-dd HH:mm:ss") + "' group by clientid) pp) b16, "
				+ "(SELECT count(1) r FROM (SELECT clientid FROM appstart where time >= '"
				+ datetime.plusHours(16).toString("yyyy-MM-dd HH:mm:ss") + "' and time < '"
				+ datetime.plusHours(17).toString("yyyy-MM-dd HH:mm:ss") + "' group by clientid) qq) b17, "
				+ "(SELECT count(1) s FROM (SELECT clientid FROM appstart where time >= '"
				+ datetime.plusHours(17).toString("yyyy-MM-dd HH:mm:ss") + "' and time < '"
				+ datetime.plusHours(18).toString("yyyy-MM-dd HH:mm:ss") + "' group by clientid) rr) b18, "
				+ "(SELECT count(1) t FROM (SELECT clientid FROM appstart where time >= '"
				+ datetime.plusHours(18).toString("yyyy-MM-dd HH:mm:ss") + "' and time < '"
				+ datetime.plusHours(19).toString("yyyy-MM-dd HH:mm:ss") + "' group by clientid) ss) b19, "
				+ "(SELECT count(1) u FROM (SELECT clientid FROM appstart where time >= '"
				+ datetime.plusHours(19).toString("yyyy-MM-dd HH:mm:ss") + "' and time < '"
				+ datetime.plusHours(20).toString("yyyy-MM-dd HH:mm:ss") + "' group by clientid) tt) b20, "
				+ "(SELECT count(1) v FROM (SELECT clientid FROM appstart where time >= '"
				+ datetime.plusHours(20).toString("yyyy-MM-dd HH:mm:ss") + "' and time < '"
				+ datetime.plusHours(21).toString("yyyy-MM-dd HH:mm:ss") + "' group by clientid) uu) b21, "
				+ "(SELECT count(1) w FROM (SELECT clientid FROM appstart where time >= '"
				+ datetime.plusHours(21).toString("yyyy-MM-dd HH:mm:ss") + "' and time < '"
				+ datetime.plusHours(22).toString("yyyy-MM-dd HH:mm:ss") + "' group by clientid) vv) b22, "
				+ "(SELECT count(1) x FROM (SELECT clientid FROM appstart where time >= '"
				+ datetime.plusHours(22).toString("yyyy-MM-dd HH:mm:ss") + "' and time < '"
				+ datetime.plusHours(23).toString("yyyy-MM-dd HH:mm:ss") + "' group by clientid) ww) b23, "
				+ "(SELECT count(1) y FROM (SELECT clientid FROM appstart where time >= '"
				+ datetime.plusHours(23).toString("yyyy-MM-dd HH:mm:ss") + "' and time < '"
				+ datetime.plusHours(24).toString("yyyy-MM-dd HH:mm:ss") + "' group by clientid) xx) b24 ";
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
		Map<String, Object> map = list.get(0);
		Collection<Object> values = map.values();
		return values;
	}

	/**
	 * 小时段分布table的数据
	 * 
	 * @param calcdate
	 * @return
	 */
	@Override
	public List<XsdfbPo> xsdfbTableDate(Date calcdate) {
		Collection<Object> qdcsycList = qdcsycList(calcdate); // 启动次数
		Collection<Object> qdcsqcList = qdcsqcList(calcdate); // 用户数量
		List<Object> qdlist = new ArrayList<Object>(qdcsycList);
		List<Object> yhlist = new ArrayList<Object>(qdcsqcList);

		DateTime datetime = new DateTime(calcdate);

		List<XsdfbPo> reslist = new ArrayList<XsdfbPo>();
		for (int i = 0; i <= 24; i++) {
			XsdfbPo po = new XsdfbPo();
			po.setTime(datetime.toString("yyyy-MM-dd HH:mm:ss"));

			Object qd = qdlist.get(i);
			if (qd instanceof Integer) {
				po.setQdcs(Long.valueOf((Integer) qd + ""));
			} else {
				po.setQdcs((Long) qd);
			}

			Object yh = yhlist.get(i);
			if (yh instanceof Integer) {
				po.setYhsl(Long.valueOf((Integer) yh + ""));
			} else {
				po.setYhsl((Long) yh);
			}

			reslist.add(po);
			datetime = datetime.plusHours(1);
		}

		return reslist;
	}

	// ==========================================

	/**
	 * 日访问量分布，用户数量 去重的
	 */
	@Override
	public List<Integer> rfwlllList(Date startDate, Date endDate) {
		DateTime dstartDate = new DateTime(startDate);
		DateTime dendDate = new DateTime(endDate);
		int days = Days.daysBetween(dstartDate, dendDate).getDays();
		List<Integer> flowList = new ArrayList<Integer>();

		if (days <= 55) {
			String sql = "select sum(num) from scheappstartqc where time = ";
			for (int i = 0; i < days + 1; i++) {
				int res1 = jdbcTemplate.queryForInt(sql + "\"" + dstartDate.toString("yyyy-MM-dd") + "\"");
				flowList.add(res1);
				dstartDate = dstartDate.plusDays(1);
			}
		} else {
			String sql2 = "select sum(num) from scheappstartqc ";
			int base = ((Double) Math.ceil(days / 55.0)).intValue();
			for (int i = 0; i < (days / base) + 1; i++) {
				int res2 = jdbcTemplate.queryForInt(sql2 + "where time >= \"" + dstartDate.toString("yyyy-MM-dd")
						+ "\" and time < \"" + dstartDate.plusDays(base).toString("yyyy-MM-dd") + "\"");
				flowList.add(res2);
				dstartDate = dstartDate.plusDays(base);
			}
		}

		return flowList;
	}

	/**
	 * add by jjj app统计iphone和Android折线图数据，(下载量 更新量 )
	 */
	public List<List<?>> apptjLine(Date startDate, Date endDate, String type, String flag) {
		List<Integer> dataList1 = new ArrayList<Integer>();
		List<Integer> dataList2 = new ArrayList<Integer>();
		DateTime startDateJoda = new DateTime(startDate);
		DateTime endDateJoda = new DateTime(endDate);

		List<String> labelsList = new ArrayList<String>();

		String sql = "", select = "";
		String groupby = "GROUP BY type";
		if (flag.equals("1")) {
			select = "select sum(num),type from scheappxz ";
		} else if (flag.equals("2")) {
			select = "select sum(num),type from scheappgx ";
		} else if (flag.equals("3")) {
			select = "select sum(waittime),baseos from usestatist";
			groupby = "GROUP BY baseos";
		} else {
			return null;
		}
		while (startDateJoda.getMillis() <= endDateJoda.getMillis()) {
			if (type.equalsIgnoreCase("year")) {
				labelsList.add(startDateJoda.toString("yyyy"));// label
				sql = select + " where  time >= \"" + startDateJoda.toString("yyyy-01-01") + "\" and time < \""
						+ startDateJoda.plusYears(1).toString("yyyy-01-01") + "\" " + groupby;
				startDateJoda = startDateJoda.plusYears(1);
			} else if (type.equalsIgnoreCase("month")) {
				labelsList.add(startDateJoda.toString("yyyy-MM"));// label
				sql = select + " where time >= \"" + startDateJoda.toString("yyyy-MM-01") + "\" and time < \""
						+ startDateJoda.plusMonths(1).toString("yyyy-MM-01") + "\" " + groupby;
				startDateJoda = startDateJoda.plusMonths(1);
			} else if (type.equalsIgnoreCase("day")) {
				labelsList.add(startDateJoda.toString("yyyy-MM-dd"));// label
				sql = select + " where time = \"" + startDateJoda.toString("yyyy-MM-dd") + "\" " + groupby;
				startDateJoda = startDateJoda.plusDays(1);
			} else if (type.equalsIgnoreCase("week")) {
				int minus = startDateJoda.getDayOfWeek() - 1;
				startDateJoda = startDateJoda.minusDays(minus); // 得到刚刚好的本周的周一
				String str = "\"第" + startDateJoda.getWeekOfWeekyear() + "周" + startDateJoda.toString("yyMMdd") + "\"";
				labelsList.add(str);// label
				sql = select + " where time >= \"" + startDateJoda.toString("yyyy-MM-dd") + "\" and time < \""
						+ startDateJoda.plusWeeks(1).toString("yyyy-MM-dd") + "\" " + groupby;
				startDateJoda = startDateJoda.plusWeeks(1);
			} else {
				return null;
			}

			List<Object[]> qlist = jdbcTemplate.query(sql, new RowMapper<Object[]>() {
				@Override
				public Object[] mapRow(ResultSet rs, int arg1) throws SQLException {
					Object[] intarr = new Object[2];
					intarr[0] = rs.getInt(1);
					if (null == intarr[0]) {
						intarr[0] = 0;
					}
					intarr[1] = rs.getString(2);
					return intarr;
				}
			});
			int ios = 0, android = 0;
			for (Object[] objects : qlist) {
				if (null != objects[1]) {
					if (objects[1].toString().equalsIgnoreCase("iphone"))
						ios = (Integer) objects[0];
					if (objects[1].toString().equalsIgnoreCase("android"))
						android = (Integer) objects[0];
				}
			}
			dataList1.add(ios);
			dataList2.add(android);

		}
		List<List<?>> list = new ArrayList<>();
		list.add(labelsList);
		list.add(dataList1);
		list.add(dataList2);
		return list;
	}

	/**
	 * app统计iphone下载量折线图数据，表scheappxz
	 */
	// public List<Integer> apptjiphonexzList(Date startDate, Date endDate) {
	// List<Integer> dataList = new ArrayList<Integer>();
	// DateTime startDateJoda = new DateTime(startDate);
	// DateTime endDateJoda = new DateTime(endDate);
	//
	// String sql = "select sum(num) from scheappxz ";
	//
	// while(startDateJoda.getMillis() <= endDateJoda.getMillis()) {
	// int res1 = jdbcTemplate.queryForInt(sql + " where type = 'iphone' and
	// time >= \"" + startDateJoda.toString("yyyy-MM-01") + "\" and time < \"" +
	// startDateJoda.plusMonths(1).toString("yyyy-MM-01") + "\"");
	// dataList.add(res1);
	// startDateJoda = startDateJoda.plusMonths(1);
	// }
	//
	// return dataList;
	// }
	//
	/**
	 * app统计android下载量折线图数据，表scheappxz
	 */
	// public List<Integer> apptjandroidxzList(Date startDate, Date endDate) {
	// List<Integer> dataList = new ArrayList<Integer>();
	// DateTime startDateJoda = new DateTime(startDate);
	// DateTime endDateJoda = new DateTime(endDate);
	//
	// String sql = "select sum(num) from scheappxz ";
	//
	// while(startDateJoda.getMillis() <= endDateJoda.getMillis()) {
	// int res1 = jdbcTemplate.queryForInt(sql + " where type = 'android' and
	// time >= \"" + startDateJoda.toString("yyyy-MM-01") + "\" and time < \"" +
	// startDateJoda.plusMonths(1).toString("yyyy-MM-01") + "\"");
	// dataList.add(res1);
	// startDateJoda = startDateJoda.plusMonths(1);
	// }
	//
	// return dataList;
	// }

	/**
	 * update by jjj 已被tableDate替换 APP统计下载量的表格
	 */
	// @Override
	// public EasyUiDataGridResult xztableData(Integer page, Integer rows,Date
	// startDate, Date endDate ,String type,Boolean getAll) {
	// List<ApptjPo> reslist = new ArrayList<ApptjPo>();
	// DateTime sdt = new DateTime(startDate);
	// DateTime edt = new DateTime(endDate);
	// int totalRow = 0;
	// if(type.equals("year")){
	// totalRow = Years.yearsBetween(sdt, edt).getYears() + 1;
	// }else if(type.equals("month")){
	// totalRow = Months.monthsBetween(sdt, edt).getMonths() + 1;
	// }else if(type.equals("day")){
	// totalRow = Days.daysBetween(sdt, edt).getDays() + 1;
	// }else if(type.equals("week")){
	// int minus = sdt.getDayOfWeek() - 1;
	// sdt = sdt.minusDays(minus); // 得到刚刚好的本周的周一
	// int p = edt.getDayOfWeek();
	// edt = edt.plusDays(7 - p);
	// int weeks = Weeks.weeksBetween(sdt, edt).getWeeks();
	// totalRow = weeks + 1;
	// }else{
	// return null;
	// }
	//
	// int start=0,end = 0;
	// if(getAll){
	// end = totalRow ;
	// }else{
	// start = (page - 1) * rows;
	// int limit = rows;
	// if((start + limit) > totalRow) {
	// end = totalRow;
	// }else {
	// end = start + limit;
	// }
	// }
	//
	// String sql = "";
	//
	// for(int i = start; i < end; i++) {
	// ApptjPo apptjPo = new ApptjPo();
	// if(type.equals("year")){
	// apptjPo.setMonthdate(edt.minusYears(i).toString("yyyy"));
	// sql = "select sum(num),type from scheappxz where time >= \"" +
	// edt.minusYears(i).toString("yyyy-01-01") + "\" and time < \"" +
	// edt.minusYears(i).plusYears(1).toString("yyyy-01-01") + "\" GROUP BY type
	// ORDER BY type";
	// }else if(type.equals("month")){
	// apptjPo.setMonthdate(edt.minusMonths(i).toString("yyyy-MM"));
	// sql = "select sum(num),type from scheappxz where time >= \"" +
	// edt.minusMonths(i).toString("yyyy-MM-01") + "\" and time < \"" +
	// edt.minusMonths(i).plusMonths(1).toString("yyyy-MM-01") + "\" GROUP BY
	// type ORDER BY type" ;
	// }else if(type.equals("day")){
	// apptjPo.setMonthdate(edt.minusDays(i).toString("yyyy-MM-dd"));
	// sql = "select sum(num),type from scheappxz where time = \"" +
	// edt.minusDays(i).toString("yyyy-MM-dd") + "\" GROUP BY type ORDER BY
	// type" ;
	// }else if(type.equals("week")){
	// String str = "第" + edt.minusWeeks(i).getWeekOfWeekyear() + "周 " +
	// edt.minusWeeks(i).toString("yyyy-MM-dd") + " -- " +
	// edt.minusWeeks(i).plusDays(6).toString("yyyy-MM-dd");
	// apptjPo.setMonthdate(str);
	// sql = "select sum(num),type from scheappxz where time >= \"" +
	// edt.minusWeeks(i).toString("yyyy-MM-dd") + "\" and time < \"" +
	// edt.minusWeeks(i).plusWeeks(1).toString("yyyy-MM-dd") + "\" GROUP BY type
	// ORDER BY type" ;
	// }
	//
	// List<Object[]> qlist = jdbcTemplate.query(sql, new RowMapper<Object[]>(){
	// @Override
	// public Object[] mapRow(ResultSet rs, int index) throws SQLException {
	// Object[] intarr = new Object[2];
	// intarr[0] = rs.getInt(1);
	// if(null == intarr[0]) {
	// intarr[0]=0;
	// }
	// intarr[1] = rs.getString(2);
	// return intarr;
	// }});
	// int ios =0,android=0;
	// for (Object[] objects : qlist) {
	// if(null != objects[1]) {
	// if(objects[1].toString().equalsIgnoreCase("iphone"))
	// ios = (Integer) objects[0];
	// if(objects[1].toString().equalsIgnoreCase("android"))
	// android = (Integer) objects[0];
	// }
	// }
	// apptjPo.setAndroidnum(android);
	// apptjPo.setIosnum(ios);
	// reslist.add(apptjPo);
	// }
	// EasyUiDataGridResult result = new EasyUiDataGridResult(totalRow,
	// reslist);
	// return result;
	// }
	/**
	 * add by jjj APP统计的表格(下载量 更新量)
	 */
//	@Override
//	public EasyUiDataGridResult tableData(Integer page, Integer rows, Date startDate, Date endDate, String type,
//			String flag, Boolean getAll) {
//		List<ApptjPo> reslist = new ArrayList<ApptjPo>();
//		DateTime sdt = new DateTime(startDate);
//		DateTime edt = new DateTime(endDate);
//		int totalRow = 0;
//		if (type.equals("year")) {
//			totalRow = Years.yearsBetween(sdt, edt).getYears() + 1;
//		} else if (type.equals("month")) {
//			totalRow = Months.monthsBetween(sdt, edt).getMonths() + 1;
//		} else if (type.equals("day")) {
//			totalRow = Days.daysBetween(sdt, edt).getDays() + 1;
//		} else if (type.equals("week")) {
//			int minus = sdt.getDayOfWeek() - 1;
//			sdt = sdt.minusDays(minus); // 得到刚刚好的本周的周一
//			int p = edt.getDayOfWeek();
//			edt = edt.plusDays(7 - p);
//			int weeks = Weeks.weeksBetween(sdt, edt).getWeeks();
//			totalRow = weeks + 1;
//		} else {
//			return null;
//		}
//
//		int start = (page-1)*10, end = 0;
//		if (getAll) {
//			end = totalRow;
//		} else {
//			start = (page - 1) * rows;
//			int limit = rows;
//			if ((start + limit) > totalRow) {
//				end = totalRow;
//			} else {
//				end = start + limit;
//			}
//		}
//
//		String sql = "", select = "";
//		String groupby = "GROUP BY type";
//		if (flag.equals("1")) {
//			select = "select sum(num),type from scheappxz ";
//		} else if (flag.equals("2")) {
//			select = "select sum(num),type from scheappgx ";
//		} else if (flag.equals("3")) {
//			select = "select sum(waittime),baseos from usestatist";
//			groupby = "GROUP BY baseos";
//		} else {
//			return null;
//		}
//		for (int i = start; i < end; i++) {
//			ApptjPo apptjPo = new ApptjPo();
//			if (type.equals("year")) {
//				apptjPo.setMonthdate(edt.minusYears(i).toString("yyyy"));
//				sql = select + "  where time >= \"" + edt.minusYears(i).toString("yyyy-01-01") + "\" and time < \""
//						+ edt.minusYears(i).plusYears(1).toString("yyyy-01-01") + "\" " + groupby;
//			} else if (type.equals("month")) {
//				apptjPo.setMonthdate(edt.minusMonths(i).toString("yyyy-MM"));
//				sql = select + " where time >= \"" + edt.minusMonths(i).toString("yyyy-MM-01") + "\" and time < \""
//						+ edt.minusMonths(i).plusMonths(1).toString("yyyy-MM-01") + "\" " + groupby;
//			} else if (type.equals("day")) {
//				apptjPo.setMonthdate(edt.minusDays(i).toString("yyyy-MM-dd"));
//				sql = select + " where time = \"" + edt.minusDays(i).toString("yyyy-MM-dd") + "\" " + groupby;
//			} else if (type.equals("week")) {
//				String str = "第" + edt.minusWeeks(i).getWeekOfWeekyear() + "周  "
//						+ edt.minusWeeks(i).toString("yyyy-MM-dd") + " -- "
//						+ edt.minusWeeks(i).plusDays(6).toString("yyyy-MM-dd");
//				apptjPo.setMonthdate(str);
//				sql = select + " where time >= \"" + edt.minusWeeks(i).toString("yyyy-MM-dd") + "\" and time < \""
//						+ edt.minusWeeks(i).plusWeeks(1).toString("yyyy-MM-dd") + "\" " + groupby;
//			}
//
//			List<Object[]> qlist = jdbcTemplate.query(sql, new RowMapper<Object[]>() {
//				@Override
//				public Object[] mapRow(ResultSet rs, int index) throws SQLException {
//					Object[] intarr = new Object[2];
//					intarr[0] = rs.getInt(1);
//					if (null == intarr[0]) {
//						intarr[0] = 0;
//					}
//					intarr[1] = rs.getString(2);
//					return intarr;
//				}
//			});
//			int ios = 0, android = 0;
//			for (Object[] objects : qlist) {
//				if (null != objects[1]) {
//					if ("iphone".equalsIgnoreCase(objects[1].toString())
//							|| "IOS".equalsIgnoreCase(objects[1].toString()))
//						ios = (Integer) objects[0];
//					if ("android".equalsIgnoreCase(objects[1].toString()))
//						android = (Integer) objects[0];
//				}
//			}
//			apptjPo.setAndroidnum(android);
//			apptjPo.setIosnum(ios);
//			reslist.add(apptjPo);
//		}
//		EasyUiDataGridResult result = new EasyUiDataGridResult(totalRow, reslist);
//		return result;
//	}
	/**
	 * app统计iphone更新量折线图数据，表scheappgx
	 */
	// public List<Integer> apptjiphonegxList(Date startDate, Date endDate) {
	// List<Integer> dataList = new ArrayList<Integer>();
	// DateTime startDateJoda = new DateTime(startDate);
	// DateTime endDateJoda = new DateTime(endDate);
	//
	// String sql = "select sum(num) from scheappgx ";
	//
	// while(startDateJoda.getMillis() <= endDateJoda.getMillis()) {
	// int res1 = jdbcTemplate.queryForInt(sql + " where type = 'iphone' and
	// time >= \"" + startDateJoda.toString("yyyy-MM-01") + "\" and time < \"" +
	// startDateJoda.plusMonths(1).toString("yyyy-MM-01") + "\"");
	// dataList.add(res1);
	// startDateJoda = startDateJoda.plusMonths(1);
	// }
	//
	// return dataList;
	// }

	/**
	 * app统计android更新量折线图数据，表scheappgx
	 */
	// public List<Integer> apptjandroidgxList(Date startDate, Date endDate) {
	// List<Integer> dataList = new ArrayList<Integer>();
	// DateTime startDateJoda = new DateTime(startDate);
	// DateTime endDateJoda = new DateTime(endDate);
	//
	// String sql = "select sum(num) from scheappgx ";
	//
	// while(startDateJoda.getMillis() <= endDateJoda.getMillis()) {
	// int res1 = jdbcTemplate.queryForInt(sql + " where type = 'android' and
	// time >= \"" + startDateJoda.toString("yyyy-MM-01") + "\" and time < \"" +
	// startDateJoda.plusMonths(1).toString("yyyy-MM-01") + "\"");
	// dataList.add(res1);
	// startDateJoda = startDateJoda.plusMonths(1);
	// }
	//
	// return dataList;
	// }
	// /**
	// * add by jjj 已被apptjLine代替
	// * app统计iphone和Android更新量折线图数据，表scheappgx
	// */
	// public List<List<?>> apptjgxList(Date startDate, Date endDate,String
	// type) {
	// List<Integer> dataList1 = new ArrayList<Integer>();
	// List<Integer> dataList2 = new ArrayList<Integer>();
	// DateTime startDateJoda = new DateTime(startDate);
	// DateTime endDateJoda = new DateTime(endDate);
	//
	// List<String> labelsList = new ArrayList<String>();
	//
	// String sql = " ";
	// while (startDateJoda.getMillis() <= endDateJoda.getMillis()) {
	// if(type.equalsIgnoreCase("year")){
	// labelsList.add(startDateJoda.toString("yyyy"));//label
	// sql = "select sum(num),type from scheappgx where time >= \"" +
	// startDateJoda.toString("yyyy-01-01") + "\" and time < \"" +
	// startDateJoda.plusYears(1).toString("yyyy-01-01") + "\" GROUP BY type";
	// startDateJoda = startDateJoda.plusYears(1);
	// }else if(type.equalsIgnoreCase("month")){
	// labelsList.add(startDateJoda.toString("yyyy-MM"));//label
	// sql = "select sum(num),type from scheappgx where time >= \"" +
	// startDateJoda.toString("yyyy-MM-01") + "\" and time < \"" +
	// startDateJoda.plusMonths(1).toString("yyyy-MM-01") + "\" GROUP BY type";
	// startDateJoda = startDateJoda.plusMonths(1);
	// }else if(type.equalsIgnoreCase("day")){
	// labelsList.add(startDateJoda.toString("yyyy-MM-dd"));//label
	// sql = "select sum(num),type from scheappgx where time = \"" +
	// startDateJoda.toString("yyyy-MM-dd") + "\" GROUP BY type";
	// startDateJoda = startDateJoda.plusDays(1);
	// }else if(type.equalsIgnoreCase("week")){
	// String str = "\"第" + startDateJoda.getWeekOfWeekyear() + "周 " +
	// startDateJoda.toString("yyMMdd")+"\"";
	// labelsList.add(str);//label
	// sql = "select sum(num),type from scheappgx where time >= \"" +
	// startDateJoda.toString("yyyy-MM-dd") + "\" and time < \"" +
	// startDateJoda.plusWeeks(1).toString("yyyy-MM-dd") + "\" GROUP BY type
	// ORDER BY type" ;
	// startDateJoda = startDateJoda.plusWeeks(1);
	// }else{
	// return null;
	// }
	//
	// List<Object[]> qlist = jdbcTemplate.query(sql ,new RowMapper<Object[]>()
	// {
	// @Override
	// public Object[] mapRow(ResultSet rs, int arg1) throws SQLException {
	// Object[] intarr = new Object[2];
	// intarr[0] = rs.getInt(1);
	// if(null == intarr[0]) {
	// intarr[0]=0;
	// }
	// intarr[1] = rs.getString(2);
	// return intarr;
	// }
	// });
	// int ios =0,android=0;
	// for (Object[] objects : qlist) {
	// if(null != objects[1]) {
	// if(objects[1].toString().equalsIgnoreCase("iphone"))
	// ios = (Integer) objects[0];
	// if(objects[1].toString().equalsIgnoreCase("android"))
	// android = (Integer) objects[0];
	// }
	// }
	// dataList1.add(ios);
	// dataList2.add(android);
	//
	// }
	// List<List<?>> list = new ArrayList<>();
	// list.add(labelsList);
	// list.add(dataList1);
	// list.add(dataList2);
	// return list;
	// }
	// /**
	// * APP统计更新量的表格 已被tableDate代替
	// */
	// @Override
	// public EasyUiDataGridResult gxtableData(Integer page, Integer rows,Date
	// startDate, Date endDate,String type,Boolean getAll) {
	// List<ApptjPo> reslist = new ArrayList<ApptjPo>();
	// DateTime sdt = new DateTime(startDate);
	// DateTime edt = new DateTime(endDate);
	// int totalRow = 0;
	// if(type.equals("year")){
	// totalRow = Years.yearsBetween(sdt, edt).getYears() + 1;
	// }else if(type.equals("month")){
	// totalRow = Months.monthsBetween(sdt, edt).getMonths() + 1;
	// }else if(type.equals("day")){
	// totalRow = Days.daysBetween(sdt, edt).getDays() + 1;
	// }else if(type.equals("week")){
	// int minus = sdt.getDayOfWeek() - 1;
	// sdt = sdt.minusDays(minus); // 得到刚刚好的本周的周一
	// int p = edt.getDayOfWeek();
	// edt = edt.plusDays(7 - p);
	// int weeks = Weeks.weeksBetween(sdt, edt).getWeeks();
	// totalRow = weeks + 1;
	// }else{
	// return null;
	// }
	//
	// int start=0,end = 0;
	// if(getAll){
	// end = totalRow ;
	// }else{
	// start = (page - 1) * rows;
	// int limit = rows;
	// if((start + limit) > totalRow) {
	// end = totalRow;
	// }else {
	// end = start + limit;
	// }
	// }
	//
	// String sql = " ";
	// for(int i = start; i < end; i++) {
	// ApptjPo apptjPo = new ApptjPo();
	// if(type.equals("year")){
	// apptjPo.setMonthdate(edt.minusYears(i).toString("yyyy"));
	// sql = "select sum(num),type from scheappgx where time >= \"" +
	// edt.minusYears(i).toString("yyyy-01-01") + "\" and time < \"" +
	// edt.minusYears(i).plusYears(1).toString("yyyy-01-01") + "\" GROUP BY type
	// ORDER BY type";
	// }else if(type.equals("month")){
	// apptjPo.setMonthdate(edt.minusMonths(i).toString("yyyy-MM"));
	// sql = "select sum(num),type from scheappgx where time >= \"" +
	// edt.minusMonths(i).toString("yyyy-MM-01") + "\" and time < \"" +
	// edt.minusMonths(i).plusMonths(1).toString("yyyy-MM-01") + "\" GROUP BY
	// type ORDER BY type" ;
	// }else if(type.equals("day")){
	// apptjPo.setMonthdate(edt.minusDays(i).toString("yyyy-MM-dd"));
	// sql = "select sum(num),type from scheappgx where time = \"" +
	// edt.minusDays(i).toString("yyyy-MM-dd") + "\" GROUP BY type ORDER BY
	// type" ;
	// }else if(type.equals("week")){
	// String str = "第" + edt.minusWeeks(i).getWeekOfWeekyear() + "周 " +
	// edt.minusWeeks(i).toString("yyyy-MM-dd") + " -- " +
	// edt.minusWeeks(i).plusDays(6).toString("yyyy-MM-dd");
	// apptjPo.setMonthdate(str);
	// sql = "select sum(num),type from scheappgx where time >= \"" +
	// edt.minusWeeks(i).toString("yyyy-MM-dd") + "\" and time < \"" +
	// edt.minusWeeks(i).plusWeeks(1).toString("yyyy-MM-dd") + "\" GROUP BY type
	// ORDER BY type" ;
	// }
	//
	// List<Object[]> qlist = jdbcTemplate.query(sql, new RowMapper<Object[]>(){
	// @Override
	// public Object[] mapRow(ResultSet rs, int index) throws SQLException {
	// Object[] intarr = new Object[2];
	// intarr[0] = rs.getInt(1);
	// if(null == intarr[0]) {
	// intarr[0]=0;
	// }
	// intarr[1] = rs.getString(2);
	// return intarr;
	// }});
	// int ios =0,android=0;
	// for (Object[] objects : qlist) {
	// if(null != objects[1]) {
	// if(objects[1].toString().equalsIgnoreCase("iphone"))
	// ios = (Integer) objects[0];
	// if(objects[1].toString().equalsIgnoreCase("android"))
	// android = (Integer) objects[0];
	// }
	// }
	// apptjPo.setAndroidnum(android);
	// apptjPo.setIosnum(ios);
	// reslist.add(apptjPo);
	// }
	// EasyUiDataGridResult result = new EasyUiDataGridResult(totalRow,
	// reslist);
	//
	// return result;
	// }

	/**
	 * 资源画像浏览量 update by JJJ 20170223 pm flag : lll 浏览量 ，xzl 下载量，scl 收藏量
	 */
	@Override
	public List<ZyhxPo> zyhxDataList(Date startDate, Date endDate, String type, String zytype, String flag) {
		List<ZyhxPo> reslist = new ArrayList<ZyhxPo>();
		DateTime sdt = new DateTime(startDate);
		DateTime edt = new DateTime(endDate);

		int totalRow = 0;
		String table = "";
		if ("0".equals(zytype)) {// 期刊
			if ("lll".equals(flag)) {
				table = " browsereadtx where ";
			} else if ("xzl".equals(flag)) {
				table = " downreadtx where ";
			} else if ("scl".equals(flag)) {
				table = " collectreadtx where ";
			}
		} else if ("1".equals(zytype)) {// 听书
			if ("lll".equals(flag)) {
				table = " browselistenbook where ";
			} else if ("xzl".equals(flag)) {
				table = " downlistenbook where ";
			} else if ("scl".equals(flag)) {
				table = " collectlistenbook where ";
			}
		} else if ("2".equals(zytype)) {
			// 文津诵读浏览量
			if ("lll".equals(flag)) {
				table = " accesslog where kind =9 and ";
			}
		} else if ("3".equals(zytype)) {
			if ("lll".equals(flag)) {
				table = " accesslog where kind = 10 and";
			} else if ("scl".equals(flag)) {
				table = " nlcsubjectcollect where ";
			}
		} else {
			return null;
		}
		if ("week".equalsIgnoreCase(type)) {
			int minus = sdt.getDayOfWeek() - 1;
			sdt = sdt.minusDays(minus); // 得到刚刚好的本周的周一
			int p = edt.getDayOfWeek();
			edt = edt.plusDays(7 - p);
			int weeks = Weeks.weeksBetween(sdt, edt).getWeeks();
			totalRow = weeks + 1;
			int base = 1;
			if (totalRow > 55) {
				base = ((Double) Math.ceil(totalRow / 55.0)).intValue();
			}
			while (sdt.getMillis() <= edt.getMillis()) {
				String str = sdt.toString("yyyy") + "第" + sdt.getWeekOfWeekyear() + "周";
				String sql = "select count(1) from " + table + " time >= \"" + sdt.toString("yyyy-MM-dd")
						+ "\" and time < \"" + sdt.plusWeeks(base).toString("yyyy-MM-dd") + "\" ";
				int res = jdbcTemplate.queryForObject(sql, Integer.class);
				ZyhxPo po = new ZyhxPo();
				po.setName(str);
				po.setValue(res + "");
				po.setColor("#cbab4f");
				reslist.add(po);
				sdt = sdt.plusWeeks(base);
			}
		} else if ("day".equals(type)) {
			totalRow = Days.daysBetween(sdt, edt).getDays() + 1;
			int base = 1;
			if (totalRow > 55) {
				base = ((Double) Math.ceil(totalRow / 55.0)).intValue();
			}
			while (sdt.getMillis() <= edt.getMillis()) {
				String sql = "select count(1) from " + table + " time >= \"" + sdt.toString("yyyy-MM-dd")
						+ "\" and time < \"" + sdt.plusDays(base).toString("yyyy-MM-dd") + "\" ";
				int res = jdbcTemplate.queryForObject(sql, Integer.class);
				ZyhxPo po = new ZyhxPo();
				po.setName(sdt.toString("yyyy-MM-dd"));
				po.setValue(res + "");
				po.setColor("#cbab4f");
				reslist.add(po);
				sdt = sdt.plusDays(base);
			}
		} else if ("year".equalsIgnoreCase(type)) {
			totalRow = Years.yearsBetween(sdt, edt).getYears() + 1;
			int base = 1;
			if (totalRow > 55) {
				base = ((Double) Math.ceil(totalRow / 55.0)).intValue();
			}
			while (sdt.getMillis() <= edt.getMillis()) {
				String sql = "select count(1) from " + table + " time >= \"" + sdt.toString("yyyy-MM-dd")
						+ "\" and time < \"" + sdt.plusYears(base).toString("yyyy-MM-dd") + "\" ";
				int res = jdbcTemplate.queryForObject(sql, Integer.class);
				ZyhxPo po = new ZyhxPo();
				po.setName(sdt.toString("yyyy"));
				po.setValue(res + "");
				po.setColor("#cbab4f");
				reslist.add(po);
				sdt = sdt.plusYears(base);
			}
		} else if ("month".equalsIgnoreCase(type)) {
			totalRow = Months.monthsBetween(sdt, edt).getMonths() + 1;
			int base = 1;
			if (totalRow > 55) {
				base = ((Double) Math.ceil(totalRow / 55.0)).intValue();
			}
			while (sdt.getMillis() <= edt.getMillis()) {
				String sql = "select count(1) from " + table + " time >= \"" + sdt.toString("yyyy-MM-dd")
						+ "\" and time < \"" + sdt.plusMonths(base).toString("yyyy-MM-dd") + "\" ";
				int res = jdbcTemplate.queryForObject(sql, Integer.class);
				ZyhxPo po = new ZyhxPo();
				po.setName(sdt.toString("yyyy-MM"));
				po.setValue(res + "");
				po.setColor("#cbab4f");
				reslist.add(po);
				sdt = sdt.plusMonths(base);
			}
		}
		return reslist;
	}

	// /**
	// * 资源画像期刊下载量
	// */
	// @Override
	// public List<ZyhxPo> zyhxqkxzList(Date startDate, Date endDate) {
	// List<ZyhxPo> reslist = new ArrayList<ZyhxPo>();
	// DateTime startDateJoda = new DateTime(startDate);
	// DateTime endDateJoda = new DateTime(endDate);
	//
	// String sql = "select count(1) from downreadtx ";
	//
	// while(startDateJoda.getMillis() <= endDateJoda.getMillis()) {
	// ZyhxPo po = new ZyhxPo();
	// po.setName(startDateJoda.toString("yyyy-MM"));
	// int res1 = jdbcTemplate.queryForInt(sql + " where time >= \"" +
	// startDateJoda.toString("yyyy-MM-01") + "\" and time < \"" +
	// startDateJoda.plusMonths(1).toString("yyyy-MM-01") + "\"");
	// po.setValue(res1+"");
	// po.setColor("#cbab4f");
	// reslist.add(po);
	// startDateJoda = startDateJoda.plusMonths(1);
	// }
	//
	// return reslist;
	// }

	// /**
	// * 资源画像期刊收藏量
	// */
	// @Override
	// public List<ZyhxPo> zyhxqkscList(Date startDate, Date endDate) {
	// List<ZyhxPo> reslist = new ArrayList<ZyhxPo>();
	// DateTime startDateJoda = new DateTime(startDate);
	// DateTime endDateJoda = new DateTime(endDate);
	//
	// String sql = "select count(1) from collectreadtx ";
	//
	// while(startDateJoda.getMillis() <= endDateJoda.getMillis()) {
	// ZyhxPo po = new ZyhxPo();
	// po.setName(startDateJoda.toString("yyyy-MM"));
	// int res1 = jdbcTemplate.queryForInt(sql + " where time >= \"" +
	// startDateJoda.toString("yyyy-MM-01") + "\" and time < \"" +
	// startDateJoda.plusMonths(1).toString("yyyy-MM-01") + "\"");
	// po.setValue(res1+"");
	// po.setColor("#cbab4f");
	// reslist.add(po);
	// startDateJoda = startDateJoda.plusMonths(1);
	// }
	//
	// return reslist;
	// }

	// /**
	// * 资源画像听书浏览量
	// */
	// @Override
	// public List<ZyhxPo> zyhxtsllList(Date startDate, Date endDate) {
	// List<ZyhxPo> reslist = new ArrayList<ZyhxPo>();
	// DateTime startDateJoda = new DateTime(startDate);
	// DateTime endDateJoda = new DateTime(endDate);
	//
	// String sql = "select count(1) from browselistenbook ";
	//
	// while(startDateJoda.getMillis() <= endDateJoda.getMillis()) {
	// ZyhxPo po = new ZyhxPo();
	// po.setName(startDateJoda.toString("yyyy-MM"));
	// int res1 = jdbcTemplate.queryForInt(sql + " where time >= \"" +
	// startDateJoda.toString("yyyy-MM-01") + "\" and time < \"" +
	// startDateJoda.plusMonths(1).toString("yyyy-MM-01") + "\"");
	// po.setValue(res1+"");
	// po.setColor("#cbab4f");
	// reslist.add(po);
	// startDateJoda = startDateJoda.plusMonths(1);
	// }
	//
	// return reslist;
	// }

	// /**
	// * 资源画像听书下载量
	// */
	// @Override
	// public List<ZyhxPo> zyhxtsxzList(Date startDate, Date endDate) {
	// List<ZyhxPo> reslist = new ArrayList<ZyhxPo>();
	// DateTime startDateJoda = new DateTime(startDate);
	// DateTime endDateJoda = new DateTime(endDate);
	//
	// String sql = "select count(1) from downlistenbook ";
	//
	// while(startDateJoda.getMillis() <= endDateJoda.getMillis()) {
	// ZyhxPo po = new ZyhxPo();
	// po.setName(startDateJoda.toString("yyyy-MM"));
	// int res1 = jdbcTemplate.queryForInt(sql + " where time >= \"" +
	// startDateJoda.toString("yyyy-MM-01") + "\" and time < \"" +
	// startDateJoda.plusMonths(1).toString("yyyy-MM-01") + "\"");
	// po.setValue(res1+"");
	// po.setColor("#cbab4f");
	// reslist.add(po);
	// startDateJoda = startDateJoda.plusMonths(1);
	// }
	//
	// return reslist;
	// }

	// /**
	// * 资源画像听书收藏量
	// */
	// @Override
	// public List<ZyhxPo> zyhxtsscList(Date startDate, Date endDate) {
	// List<ZyhxPo> reslist = new ArrayList<ZyhxPo>();
	// DateTime startDateJoda = new DateTime(startDate);
	// DateTime endDateJoda = new DateTime(endDate);
	//
	// String sql = "select count(1) from collectlistenbook ";
	//
	// while(startDateJoda.getMillis() <= endDateJoda.getMillis()) {
	// ZyhxPo po = new ZyhxPo();
	// po.setName(startDateJoda.toString("yyyy-MM"));
	// int res1 = jdbcTemplate.queryForInt(sql + " where time >= \"" +
	// startDateJoda.toString("yyyy-MM-01") + "\" and time < \"" +
	// startDateJoda.plusMonths(1).toString("yyyy-MM-01") + "\"");
	// po.setValue(res1+"");
	// po.setColor("#cbab4f");
	// reslist.add(po);
	// startDateJoda = startDateJoda.plusMonths(1);
	// }
	//
	// return reslist;
	// }

	// /**
	// * 资源画像文津诵读浏览量
	// */
	// @Override
	// public List<ZyhxPo> zyhxwjllList(Date startDate, Date endDate) {
	// List<ZyhxPo> reslist = new ArrayList<ZyhxPo>();
	// DateTime startDateJoda = new DateTime(startDate);
	// DateTime endDateJoda = new DateTime(endDate);
	//
	// String sql = "select count(1) from accesslog ";
	//
	// while(startDateJoda.getMillis() <= endDateJoda.getMillis()) {
	// ZyhxPo po = new ZyhxPo();
	// po.setName(startDateJoda.toString("yyyy-MM"));
	// int res1 = jdbcTemplate.queryForInt(sql + " where module = '文津经典诵读' and
	// time >= \"" + startDateJoda.toString("yyyy-MM-01") + "\" and time < \"" +
	// startDateJoda.plusMonths(1).toString("yyyy-MM-01") + "\"");
	// po.setValue(res1+"");
	// po.setColor("#cbab4f");
	// reslist.add(po);
	// startDateJoda = startDateJoda.plusMonths(1);
	// }
	//
	// return reslist;
	// }

	// /**
	// * 资源画像特色专题浏览量
	// */
	// @Override
	// public List<ZyhxPo> zyhxztllList(Date startDate, Date endDate) {
	// List<ZyhxPo> reslist = new ArrayList<ZyhxPo>();
	// DateTime startDateJoda = new DateTime(startDate);
	// DateTime endDateJoda = new DateTime(endDate);
	//
	// String sql = "select count(1) from accesslog ";
	//
	// while(startDateJoda.getMillis() <= endDateJoda.getMillis()) {
	// ZyhxPo po = new ZyhxPo();
	// po.setName(startDateJoda.toString("yyyy-MM"));
	// int res1 = jdbcTemplate.queryForInt(sql + " where module = '专题' and time
	// >= \"" + startDateJoda.toString("yyyy-MM-01") + "\" and time < \"" +
	// startDateJoda.plusMonths(1).toString("yyyy-MM-01") + "\"");
	// po.setValue(res1+"");
	// po.setColor("#cbab4f");
	// reslist.add(po);
	// startDateJoda = startDateJoda.plusMonths(1);
	// }
	//
	// return reslist;
	// }

	// /**
	// * 资源画像特色专题收藏量
	// */
	// @Override
	// public List<ZyhxPo> zyhxztscList(Date startDate, Date endDate) {
	// List<ZyhxPo> reslist = new ArrayList<ZyhxPo>();
	// DateTime startDateJoda = new DateTime(startDate);
	// DateTime endDateJoda = new DateTime(endDate);
	//
	// String sql = "select count(1) from nlcsubjectcollect ";
	//
	// while(startDateJoda.getMillis() <= endDateJoda.getMillis()) {
	// ZyhxPo po = new ZyhxPo();
	// po.setName(startDateJoda.toString("yyyy-MM"));
	// int res1 = jdbcTemplate.queryForInt(sql + " where time >= \"" +
	// startDateJoda.toString("yyyy-MM-01") + "\" and time < \"" +
	// startDateJoda.plusMonths(1).toString("yyyy-MM-01") + "\"");
	// po.setValue(res1+"");
	// po.setColor("#cbab4f");
	// reslist.add(po);
	// startDateJoda = startDateJoda.plusMonths(1);
	// }
	//
	// return reslist;
	// }

	/**
	 * 资源画像总访问量 update by JJJ 20170224 am
	 */
	@Override
	public List<ZyhxPo> zyhxzfwlList(Date startDate, Date endDate, String type, String flag) {
		List<ZyhxPo> reslist = new ArrayList<ZyhxPo>();
		DateTime sdt = new DateTime(startDate);
		DateTime edt = new DateTime(endDate);

		int totalRow = 0;
		// 期刊浏览量
		String qksql = "";
		// 听书浏览量
		String tssql = "";
		// 文津 专题
		String sql = "";
		if ("lll".equals(flag)) {
			qksql = "select count(1) from browsereadtx ";
			tssql = "select count(1) from browselistenbook ";
			sql = "select count(1) from accesslog where (kind =9 or kind = 10) and  ";
		} else if ("xzl".equals(flag)) {
			qksql = "select count(1) from downreadtx ";
			tssql = "select count(1) from downlistenbook ";
		} else if ("scl".equals(flag)) {
			qksql = "select count(1) from collectreadtx ";
			tssql = "select count(1) from collectlistenbook ";
			sql = "select count(1) from nlcsubjectcollect where ";// 专题收藏量
		} else {
			return null;
		}
		if ("week".equalsIgnoreCase(type)) {
			int minus = sdt.getDayOfWeek() - 1;
			sdt = sdt.minusDays(minus); // 得到刚刚好的本周的周一
			int p = edt.getDayOfWeek();
			edt = edt.plusDays(7 - p);
			int weeks = Weeks.weeksBetween(sdt, edt).getWeeks();
			totalRow = weeks + 1;
			int base = 1;
			if (totalRow > 55) {
				base = ((Double) Math.ceil(totalRow / 55.0)).intValue();
			}
			while (sdt.getMillis() <= edt.getMillis()) {
				String str = sdt.toString("yyyy") + "第" + sdt.getWeekOfWeekyear() + "周";
				int res1 = 0, res2 = 0, res3 = 0;
				if (!"".equals(qksql)) {
					String sql1 = qksql + " where time >= \"" + sdt.toString("yyyy-MM-dd") + "\" and time < \""
							+ sdt.plusWeeks(base).toString("yyyy-MM-dd") + "\" ";
					res1 = jdbcTemplate.queryForObject(sql1, Integer.class);
				}
				if (!"".equals(tssql)) {
					String sql2 = tssql + " where time >= \"" + sdt.toString("yyyy-MM-dd") + "\" and time < \""
							+ sdt.plusWeeks(base).toString("yyyy-MM-dd") + "\" ";
					res2 = jdbcTemplate.queryForObject(sql2, Integer.class);
				}
				if (!"".equals(sql)) {
					String sql3 = sql + " time >= \"" + sdt.toString("yyyy-MM-dd") + "\" and time < \""
							+ sdt.plusWeeks(base).toString("yyyy-MM-dd") + "\" ";
					res3 = jdbcTemplate.queryForObject(sql3, Integer.class);
				}
				ZyhxPo po = new ZyhxPo();
				po.setName(str);
				int count = res1 + res2 + res3;
				po.setValue(count + "");
				po.setColor("#cbab4f");
				reslist.add(po);
				sdt = sdt.plusWeeks(base);
			}
		} else if ("day".equals(type)) {
			totalRow = Days.daysBetween(sdt, edt).getDays() + 1;
			int base = 1;
			if (totalRow > 55) {
				base = ((Double) Math.ceil(totalRow / 55.0)).intValue();
			}
			while (sdt.getMillis() <= edt.getMillis()) {
				int res1 = 0, res2 = 0, res3 = 0;
				if (!"".equals(qksql)) {
					String sql1 = qksql + " where time >= \"" + sdt.toString("yyyy-MM-dd") + "\" and time < \""
							+ sdt.plusDays(base).toString("yyyy-MM-dd") + "\" ";
					res1 = jdbcTemplate.queryForObject(sql1, Integer.class);
				}
				if (!"".equals(tssql)) {
					String sql2 = tssql + " where time >= \"" + sdt.toString("yyyy-MM-dd") + "\" and time < \""
							+ sdt.plusDays(base).toString("yyyy-MM-dd") + "\" ";
					res2 = jdbcTemplate.queryForObject(sql2, Integer.class);
				}
				if (!"".equals(sql)) {
					String sql3 = sql + " time >= \"" + sdt.toString("yyyy-MM-dd") + "\" and time < \""
							+ sdt.plusDays(base).toString("yyyy-MM-dd") + "\" ";
					res3 = jdbcTemplate.queryForObject(sql3, Integer.class);
				}
				ZyhxPo po = new ZyhxPo();
				po.setName(sdt.toString("yyyy-MM-dd"));
				int count = res1 + res2 + res3;
				po.setValue(count + "");
				po.setColor("#cbab4f");
				reslist.add(po);
				sdt = sdt.plusDays(base);
			}
		} else if ("year".equalsIgnoreCase(type)) {
			totalRow = Years.yearsBetween(sdt, edt).getYears() + 1;
			int base = 1;
			if (totalRow > 55) {
				base = ((Double) Math.ceil(totalRow / 55.0)).intValue();
			}
			while (sdt.getMillis() <= edt.getMillis()) {
				int res1 = 0, res2 = 0, res3 = 0;
				if (!"".equals(qksql)) {
					String sql1 = qksql + " where time >= \"" + sdt.toString("yyyy-MM-dd") + "\" and time < \""
							+ sdt.plusYears(base).toString("yyyy-MM-dd") + "\" ";
					res1 = jdbcTemplate.queryForObject(sql1, Integer.class);
				}
				if (!"".equals(tssql)) {
					String sql2 = tssql + " where time >= \"" + sdt.toString("yyyy-MM-dd") + "\" and time < \""
							+ sdt.plusYears(base).toString("yyyy-MM-dd") + "\" ";
					res2 = jdbcTemplate.queryForObject(sql2, Integer.class);
				}
				if (!"".equals(sql)) {
					String sql3 = sql + " time >= \"" + sdt.toString("yyyy-MM-dd") + "\" and time < \""
							+ sdt.plusYears(base).toString("yyyy-MM-dd") + "\" ";
					res3 = jdbcTemplate.queryForObject(sql3, Integer.class);
				}
				ZyhxPo po = new ZyhxPo();
				po.setName(sdt.toString("yyyy"));
				int count = res1 + res2 + res3;
				po.setValue(count + "");
				po.setColor("#cbab4f");
				reslist.add(po);
				sdt = sdt.plusYears(base);
			}
		} else if ("month".equalsIgnoreCase(type)) {
			totalRow = Months.monthsBetween(sdt, edt).getMonths() + 1;
			int base = 1;
			if (totalRow > 55) {
				base = ((Double) Math.ceil(totalRow / 55.0)).intValue();
			}
			while (sdt.getMillis() <= edt.getMillis()) {
				int res1 = 0, res2 = 0, res3 = 0;
				if (!"".equals(qksql)) {
					String sql1 = qksql + " where time >= \"" + sdt.toString("yyyy-MM-dd") + "\" and time < \""
							+ sdt.plusMonths(base).toString("yyyy-MM-dd") + "\" ";
					res1 = jdbcTemplate.queryForObject(sql1, Integer.class);
				}
				if (!"".equals(tssql)) {
					String sql2 = tssql + " where time >= \"" + sdt.toString("yyyy-MM-dd") + "\" and time < \""
							+ sdt.plusMonths(base).toString("yyyy-MM-dd") + "\" ";
					res2 = jdbcTemplate.queryForObject(sql2, Integer.class);
				}
				if (!"".equals(sql)) {
					String sql3 = sql + " time >= \"" + sdt.toString("yyyy-MM-dd") + "\" and time < \""
							+ sdt.plusMonths(base).toString("yyyy-MM-dd") + "\" ";
					res3 = jdbcTemplate.queryForObject(sql3, Integer.class);
				}
				ZyhxPo po = new ZyhxPo();
				po.setName(sdt.toString("yyyy-MM"));
				int count = res1 + res2 + res3;
				po.setValue(count + "");
				po.setColor("#cbab4f");
				reslist.add(po);
				sdt = sdt.plusMonths(base);
			}
		}
		return reslist;
	}

	// /**
	// * 资源画像总访问量下载量
	// */
	// @Override
	// public List<ZyhxPo> zyhxzxzList(Date startDate, Date endDate) {
	// List<ZyhxPo> reslist = new ArrayList<ZyhxPo>();
	// DateTime startDateJoda = new DateTime(startDate);
	// DateTime endDateJoda = new DateTime(endDate);
	//
	// //期刊下载量
	// String sql1 = "select count(1) from downreadtx ";
	// //听书下载量
	// String sql2 = "select count(1) from downlistenbook ";
	//
	// while(startDateJoda.getMillis() <= endDateJoda.getMillis()) {
	// ZyhxPo po = new ZyhxPo();
	// po.setName(startDateJoda.toString("yyyy-MM"));
	//
	// int res1 = jdbcTemplate.queryForInt(sql1 + " where time >= \"" +
	// startDateJoda.toString("yyyy-MM-01") + "\" and time < \"" +
	// startDateJoda.plusMonths(1).toString("yyyy-MM-01") + "\"");
	// int res2 = jdbcTemplate.queryForInt(sql2 + " where time >= \"" +
	// startDateJoda.toString("yyyy-MM-01") + "\" and time < \"" +
	// startDateJoda.plusMonths(1).toString("yyyy-MM-01") + "\"");
	// po.setValue(res1+res2+"");
	// po.setColor("#cbab4f");
	// reslist.add(po);
	// startDateJoda = startDateJoda.plusMonths(1);
	// }
	//
	// return reslist;
	// }
	//
	// /**
	// * 资源画像总访问量收藏量
	// */
	// @Override
	// public List<ZyhxPo> zyhxzscList(Date startDate, Date endDate) {
	// List<ZyhxPo> reslist = new ArrayList<ZyhxPo>();
	// DateTime startDateJoda = new DateTime(startDate);
	// DateTime endDateJoda = new DateTime(endDate);
	//
	// //期刊收藏量
	// String sql1 = "select count(1) from collectreadtx ";
	// //听书收藏量
	// String sql2 = "select count(1) from collectlistenbook ";
	// //特色专题收藏量
	// String sql3 = "select count(1) from nlcsubjectcollect ";
	//
	//
	// while(startDateJoda.getMillis() <= endDateJoda.getMillis()) {
	// ZyhxPo po = new ZyhxPo();
	// po.setName(startDateJoda.toString("yyyy-MM"));
	//
	// int res1 = jdbcTemplate.queryForInt(sql1 + " where time >= \"" +
	// startDateJoda.toString("yyyy-MM-01") + "\" and time < \"" +
	// startDateJoda.plusMonths(1).toString("yyyy-MM-01") + "\"");
	// int res2 = jdbcTemplate.queryForInt(sql2 + " where time >= \"" +
	// startDateJoda.toString("yyyy-MM-01") + "\" and time < \"" +
	// startDateJoda.plusMonths(1).toString("yyyy-MM-01") + "\"");
	// int res3 = jdbcTemplate.queryForInt(sql3 + " where time >= \"" +
	// startDateJoda.toString("yyyy-MM-01") + "\" and time < \"" +
	// startDateJoda.plusMonths(1).toString("yyyy-MM-01") + "\"");
	// po.setValue(res1+res2+res3+"");
	// po.setColor("#cbab4f");
	// reslist.add(po);
	// startDateJoda = startDateJoda.plusMonths(1);
	// }
	//
	// return reslist;
	// }

	/**
	 * 资源画像中的期刊的访问数据的列表 update by jjj 20170223 pm
	 * 
	 * @param zytype
	 *            期刊 听书 文津阅读 特色专题
	 * @return
	 */
	@Override
	public EasyUiDataGridResult zyhxtableData(Integer page, Integer rows, Date startDate, Date endDate, String type,
			String zytype, boolean getAll) {
		DateTime sdt = new DateTime(startDate);
		DateTime edt = new DateTime(endDate);
		List<ZyhxTablePo> reslist = new ArrayList<ZyhxTablePo>();

		int totalRow = 0;
		if (type.equals("year")) {
			totalRow = Years.yearsBetween(sdt, edt).getYears() + 1;
		} else if (type.equals("month")) {
			totalRow = Months.monthsBetween(sdt, edt).getMonths() + 1;
		} else if (type.equals("day")) {
			totalRow = Days.daysBetween(sdt, edt).getDays() + 1;
		} else if (type.equals("week")) {
			int minus = sdt.getDayOfWeek() - 1;
			sdt = sdt.minusDays(minus); // 得到刚刚好的本周的周一
			int p = edt.getDayOfWeek();
			edt = edt.plusDays(7 - p);
			int weeks = Weeks.weeksBetween(sdt, edt).getWeeks();
			totalRow = weeks + 1;
		} else {
			return null;
		}
		String ll_sql = "", xz_sql = "", sc_sql = "";
		if ("0".equals(zytype)) {// 期刊
			// 浏览量
			ll_sql = "select count(1) from browsereadtx where ";
			// 下载量
			xz_sql = "select count(1) from downreadtx where ";
			// 收藏量
			sc_sql = "select count(1) from collectreadtx where ";
		} else if ("1".equals(zytype)) {// 听书
			// 浏览量
			ll_sql = "select count(1) from browselistenbook where ";
			// 听书下载量
			xz_sql = "select count(1) from downlistenbook where ";
			// 听书收藏量
			sc_sql = "select count(1) from collectlistenbook where ";

		} else if ("2".equals(zytype)) {
			// 文津诵读浏览量
			ll_sql = "select count(1) from accesslog where kind = 9 and ";
		} else if ("3".equals(zytype)) {
			// 特色专题浏览量
			ll_sql = "select count(1) from accesslog where kind = 10 and";
			// 特色专题收藏量
			sc_sql = "select count(1) from nlcsubjectcollect where ";
		} else {
			return null;
		}

		int start = 0, end = 0;
		if (getAll) {
			end = totalRow;
		} else {
			start = (page - 1) * rows;
			int limit = rows;
			if ((start + limit) > totalRow) {
				end = totalRow;
			} else {
				end = start + limit;
			}
		}

		for (int i = start; i < end; i++) {
			ZyhxTablePo po = new ZyhxTablePo();
			if (type.equals("year")) {
				po.setDate(sdt.plusYears(i).toString("yyyy"));
				String str = "  time >= \"" + sdt.plusYears(i).toString("yyyy-01-01") + "\" and time < \""
						+ sdt.plusYears(i+1).toString("yyyy-01-01") + "\" ";
				// 浏览量
				int lll = jdbcTemplate.queryForObject(ll_sql + str, Integer.class);
				po.setLlnum(lll + "");
				// 下载量
				if (!xz_sql.equals("")) {
					int xzl = jdbcTemplate.queryForObject(xz_sql + str, Integer.class);
					po.setXznum(xzl + "");
				} else {
					po.setXznum("");
				}
				// 收藏量
				if (!sc_sql.equals("")) {
					int scl = jdbcTemplate.queryForObject(sc_sql + str, Integer.class);
					po.setScnum(scl + "");
				} else {
					po.setScnum("");
				}
				reslist.add(po);
			} else if (type.equals("month")) {
				po.setDate(sdt.plusMonths(i).toString("yyyy-MM"));
				String str = " time >= \"" + sdt.plusMonths(i).toString("yyyy-MM-01") + "\" and time < \""
						+ sdt.plusMonths(i+1).toString("yyyy-MM-01") + "\" ";
				// 浏览量
				int lll = jdbcTemplate.queryForObject(ll_sql + str, Integer.class);
				po.setLlnum(lll + "");
				// 下载量
				if (!xz_sql.equals("")) {
					int xzl = jdbcTemplate.queryForObject(xz_sql + str, Integer.class);
					po.setXznum(xzl + "");
				} else {
					po.setXznum("");
				}
				// 收藏量
				if (!sc_sql.equals("")) {
					int scl = jdbcTemplate.queryForObject(sc_sql + str, Integer.class);
					po.setScnum(scl + "");
				} else {
					po.setScnum("");
				}
				reslist.add(po);
			} else if (type.equals("day")) {
				po.setDate(sdt.plusDays(i).toString("yyyy-MM-dd"));
				String str = " time >= \"" + sdt.plusDays(i).toString("yyyy-MM-dd") + "\" and time < \""
						+ sdt.plusDays(i+1).toString("yyyy-MM-dd") + "\" ";
				// 浏览量
				int lll = jdbcTemplate.queryForObject(ll_sql + str, Integer.class);
				po.setLlnum(lll + "");
				// 下载量
				if (!xz_sql.equals("")) {
					int xzl = jdbcTemplate.queryForObject(xz_sql + str, Integer.class);
					po.setXznum(xzl + "");
				} else {
					po.setXznum("");
				}
				// 收藏量
				if (!sc_sql.equals("")) {
					int scl = jdbcTemplate.queryForObject(sc_sql + str, Integer.class);
					po.setScnum(scl + "");
				} else {
					po.setScnum("");
				}
				reslist.add(po);
			} else if (type.equals("week")) {
				String label = sdt.plusWeeks(i).toString("yyyy") + "年第" + sdt.plusWeeks(i).getWeekOfWeekyear() + "周";
				po.setDate(label);
				String str = " time >= \"" + sdt.plusWeeks(i).toString("yyyy-MM-dd") + "\" and time < \""
						+ sdt.plusWeeks(i+1).toString("yyyy-MM-dd") + "\" ";
				// 浏览量
				int lll = jdbcTemplate.queryForObject(ll_sql + str, Integer.class);
				po.setLlnum(lll + "");
				// 下载量
				if (!xz_sql.equals("")) {
					int xzl = jdbcTemplate.queryForObject(xz_sql + str, Integer.class);
					po.setXznum(xzl + "");
				} else {
					po.setXznum("");
				}
				// 收藏量
				if (!sc_sql.equals("")) {
					int scl = jdbcTemplate.queryForObject(sc_sql + str, Integer.class);
					po.setScnum(scl + "");
				} else {
					po.setScnum("");
				}
				reslist.add(po);
			}
		}

		EasyUiDataGridResult result = new EasyUiDataGridResult(totalRow, reslist);
		return result;
	}

	// /**
	// * 资源画像中的听书的访问数据的列表
	// * @param startDate
	// * @param endDate
	// * @return
	// */
	// @Override
	// public List<ZyhxTablePo> zyhxtstableData(Date startDate, Date endDate) {
	// DateTime startDateJoda = new DateTime(startDate);
	// DateTime endDateJoda = new DateTime(endDate);
	// List<ZyhxTablePo> reslist = new ArrayList<ZyhxTablePo>();
	//
	// //听书浏览量
	// String sql0 = "select count(1) from browselistenbook ";
	// //听书下载量
	// String sql = "select count(1) from downlistenbook ";
	// //听书收藏量
	// String sql2 = "select count(1) from collectlistenbook ";
	//
	// while(startDateJoda.getMillis() <= endDateJoda.getMillis()) {
	// ZyhxTablePo po = new ZyhxTablePo();
	// po.setDate(startDateJoda.toString("yyyy-MM"));
	// int res0 = jdbcTemplate.queryForInt(sql0 + " where time >= \"" +
	// startDateJoda.toString("yyyy-MM-01") + "\" and time < \"" +
	// startDateJoda.plusMonths(1).toString("yyyy-MM-01") + "\"");
	// po.setLlnum(res0+"");
	// int res1 = jdbcTemplate.queryForInt(sql + " where time >= \"" +
	// startDateJoda.toString("yyyy-MM-01") + "\" and time < \"" +
	// startDateJoda.plusMonths(1).toString("yyyy-MM-01") + "\"");
	// po.setXznum(res1+"");
	// int res2 = jdbcTemplate.queryForInt(sql2 + " where time >= \"" +
	// startDateJoda.toString("yyyy-MM-01") + "\" and time < \"" +
	// startDateJoda.plusMonths(1).toString("yyyy-MM-01") + "\"");
	// po.setScnum(res2+"");
	// reslist.add(po);
	// startDateJoda = startDateJoda.plusMonths(1);
	// }
	//
	// return reslist;
	// }

	// /**
	// * 资源画像中的文津诵读的访问数据的列表
	// * @param startDate
	// * @param endDate
	// * @return
	// */
	// @Override
	// public List<ZyhxTablePo> zyhxwjtableData(Date startDate, Date endDate) {
	// DateTime startDateJoda = new DateTime(startDate);
	// DateTime endDateJoda = new DateTime(endDate);
	// List<ZyhxTablePo> reslist = new ArrayList<ZyhxTablePo>();
	//
	// //文津诵读浏览量
	// String sql = "select count(1) from accesslog ";
	//
	// while(startDateJoda.getMillis() <= endDateJoda.getMillis()) {
	// ZyhxTablePo po = new ZyhxTablePo();
	// po.setDate(startDateJoda.toString("yyyy-MM"));
	// int res1 = jdbcTemplate.queryForInt(sql + " where module = '文津经典诵读' and
	// time >= \"" + startDateJoda.toString("yyyy-MM-01") + "\" and time < \"" +
	// startDateJoda.plusMonths(1).toString("yyyy-MM-01") + "\"");
	// po.setLlnum(res1+"");
	// po.setXznum("");
	// po.setScnum("");
	// reslist.add(po);
	// startDateJoda = startDateJoda.plusMonths(1);
	// }
	//
	// return reslist;
	// }

	// /**
	// * 资源画像中的特色专题的访问数据的列表
	// * @param startDate
	// * @param endDate
	// * @return
	// */
	// @Override
	// public List<ZyhxTablePo> zyhxzttableData(Date startDate, Date endDate) {
	// DateTime startDateJoda = new DateTime(startDate);
	// DateTime endDateJoda = new DateTime(endDate);
	// List<ZyhxTablePo> reslist = new ArrayList<ZyhxTablePo>();
	//
	// //特色专题浏览量
	// String sql = "select count(1) from accesslog ";
	// //特色专题收藏量
	// String sql2 = "select count(1) from nlcsubjectcollect ";
	//
	// while(startDateJoda.getMillis() <= endDateJoda.getMillis()) {
	// ZyhxTablePo po = new ZyhxTablePo();
	// po.setDate(startDateJoda.toString("yyyy-MM"));
	// int res1 = jdbcTemplate.queryForInt(sql + " where module = '专题' and time
	// >= \"" + startDateJoda.toString("yyyy-MM-01") + "\" and time < \"" +
	// startDateJoda.plusMonths(1).toString("yyyy-MM-01") + "\"");
	// po.setLlnum(res1+"");
	// int res2 = jdbcTemplate.queryForInt(sql2 + " where time >= \"" +
	// startDateJoda.toString("yyyy-MM-01") + "\" and time < \"" +
	// startDateJoda.plusMonths(1).toString("yyyy-MM-01") + "\"");
	// po.setXznum("");
	// po.setScnum(res2+"");
	// reslist.add(po);
	// startDateJoda = startDateJoda.plusMonths(1);
	// }
	//
	// return reslist;
	// }

	/**
	 * 资源画像中的总访问量数据的列表
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	@Override
	public EasyUiDataGridResult zyhxzfwltableData(Integer page, Integer rows, Date startDate, Date endDate, String type,
			boolean getAll) {
		DateTime sdt = new DateTime(startDate);
		DateTime edt = new DateTime(endDate);

		List<ZyhxTablePo> reslist = new ArrayList<ZyhxTablePo>();

		int totalRow = 0;
		if (type.equals("year")) {
			totalRow = Years.yearsBetween(sdt, edt).getYears() + 1;
		} else if (type.equals("month")) {
			totalRow = Months.monthsBetween(sdt, edt).getMonths() + 1;
		} else if (type.equals("day")) {
			totalRow = Days.daysBetween(sdt, edt).getDays() + 1;
		} else if (type.equals("week")) {
			int minus = sdt.getDayOfWeek() - 1;
			sdt = sdt.minusDays(minus); // 得到刚刚好的本周的周一
			int p = edt.getDayOfWeek();
			edt = edt.plusDays(7 - p);
			int weeks = Weeks.weeksBetween(sdt, edt).getWeeks();
			totalRow = weeks + 1;
		} else {
			return null;
		}
		int start = 0, end = 0;
		if (getAll) {
			end = totalRow;
		} else {
			start = (page - 1) * rows;
			int limit = rows;
			if ((start + limit) > totalRow) {
				end = totalRow;
			} else {
				end = start + limit;
			}
		}

		for (int i = start; i < end; i++) {
			ZyhxTablePo po = new ZyhxTablePo();
			if (type.equals("year")) {
				po.setDate(sdt.plusYears(i).toString("yyyy"));
				// 浏览量
				String str1 = "select count(1) from browsereadtx where time >= \""
						+ sdt.plusYears(i).toString("yyyy-MM-dd") + "\" and time < \""
						+ sdt.plusYears(i).plusYears(1).toString("yyyy-MM-dd") + "\" ";
				int qklll = jdbcTemplate.queryForObject(str1, Integer.class);
				String str2 = "select count(1) from browselistenbook where time >= \""
						+ sdt.plusYears(i).toString("yyyy-MM-dd") + "\" and time < \""
						+ sdt.plusYears(i).plusYears(1).toString("yyyy-MM-dd") + "\" ";
				int tslll = jdbcTemplate.queryForObject(str2, Integer.class);
				String str3 = "select count(1) from accesslog where (kind=9 or kind=10) and time >= \""
						+ sdt.plusYears(i).toString("yyyy-MM-dd") + "\" and time < \""
						+ sdt.plusYears(i).plusYears(1).toString("yyyy-MM-dd") + "\" ";
				int lll = jdbcTemplate.queryForObject(str3, Integer.class);
				int count1 = qklll + tslll + lll;
				po.setLlnum(count1 + "");
				// 下载量
				String str4 = "select count(1) from downreadtx where time >= \""
						+ sdt.plusYears(i).toString("yyyy-MM-dd") + "\" and time < \""
						+ sdt.plusYears(i).plusYears(1).toString("yyyy-MM-dd") + "\" ";
				int qkxzl = jdbcTemplate.queryForObject(str4, Integer.class);
				String str5 = "select count(1) from downlistenbook where time >= \""
						+ sdt.plusYears(i).toString("yyyy-MM-dd") + "\" and time < \""
						+ sdt.plusYears(i).plusYears(1).toString("yyyy-MM-dd") + "\" ";
				int tsxzl = jdbcTemplate.queryForObject(str5, Integer.class);
				int count2 = qkxzl + tsxzl;
				po.setXznum(count2 + "");
				// 收藏量
				String str6 = "select count(1) from collectreadtx where time >= \""
						+ sdt.plusYears(i).toString("yyyy-MM-dd") + "\" and time < \""
						+ sdt.plusYears(i).plusYears(1).toString("yyyy-MM-dd") + "\" ";
				int qkscl = jdbcTemplate.queryForObject(str6, Integer.class);
				String str7 = "select count(1) from collectlistenbook where  time >= \""
						+ sdt.plusYears(i).toString("yyyy-MM-dd") + "\" and time < \""
						+ sdt.plusYears(i).plusYears(1).toString("yyyy-MM-dd") + "\" ";
				int tsscl = jdbcTemplate.queryForObject(str7, Integer.class);
				String str8 = "select count(1) from nlcsubjectcollect where time >= \""
						+ sdt.plusYears(i).toString("yyyy-MM-dd") + "\" and time < \""
						+ sdt.plusYears(i).plusYears(1).toString("yyyy-MM-dd") + "\" ";
				int ztscl = jdbcTemplate.queryForObject(str8, Integer.class);
				int count3 = qkscl + tsscl + ztscl;
				po.setScnum(count3 + "");
				reslist.add(po);
			} else if (type.equals("month")) {
				po.setDate(sdt.plusMonths(i).toString("yyyy-MM"));
				// 浏览量
				String str1 = "select count(1) from browsereadtx where time >= \""
						+ sdt.plusMonths(i).toString("yyyy-MM-dd") + "\" and time < \""
						+ sdt.plusMonths(i).plusMonths(1).toString("yyyy-MM-dd") + "\" ";
				int qklll = jdbcTemplate.queryForObject(str1, Integer.class);
				String str2 = "select count(1) from browselistenbook where time >= \""
						+ sdt.plusMonths(i).toString("yyyy-MM-dd") + "\" and time < \""
						+ sdt.plusMonths(i).plusMonths(1).toString("yyyy-MM-dd") + "\" ";
				int tslll = jdbcTemplate.queryForObject(str2, Integer.class);
				String str3 = "select count(1) from accesslog where (kind=9 or kind=10) and time >= \""
						+ sdt.plusMonths(i).toString("yyyy-MM-dd") + "\" and time < \""
						+ sdt.plusMonths(i).plusMonths(1).toString("yyyy-MM-dd") + "\" ";
				int lll = jdbcTemplate.queryForObject(str3, Integer.class);
				int count1 = qklll + tslll + lll;
				po.setLlnum(count1 + "");
				// 下载量
				String str4 = "select count(1) from downreadtx where time >= \""
						+ sdt.plusMonths(i).toString("yyyy-MM-dd") + "\" and time < \""
						+ sdt.plusMonths(i).plusMonths(1).toString("yyyy-MM-dd") + "\" ";
				int qkxzl = jdbcTemplate.queryForObject(str4, Integer.class);
				String str5 = "select count(1) from downlistenbook where time >= \""
						+ sdt.plusMonths(i).toString("yyyy-MM-dd") + "\" and time < \""
						+ sdt.plusMonths(i).plusMonths(1).toString("yyyy-MM-dd") + "\" ";
				int tsxzl = jdbcTemplate.queryForObject(str5, Integer.class);
				int count2 = qkxzl + tsxzl;
				po.setXznum(count2 + "");
				// 收藏量
				String str6 = "select count(1) from collectreadtx where time >= \""
						+ sdt.plusMonths(i).toString("yyyy-MM-dd") + "\" and time < \""
						+ sdt.plusMonths(i).plusMonths(1).toString("yyyy-MM-dd") + "\" ";
				int qkscl = jdbcTemplate.queryForObject(str6, Integer.class);
				String str7 = "select count(1) from collectlistenbook where  time >= \""
						+ sdt.plusMonths(i).toString("yyyy-MM-dd") + "\" and time < \""
						+ sdt.plusMonths(i).plusMonths(1).toString("yyyy-MM-dd") + "\" ";
				int tsscl = jdbcTemplate.queryForObject(str7, Integer.class);
				String str8 = "select count(1) from nlcsubjectcollect where time >= \""
						+ sdt.plusMonths(i).toString("yyyy-MM-dd") + "\" and time < \""
						+ sdt.plusMonths(i).plusMonths(1).toString("yyyy-MM-dd") + "\" ";
				int ztscl = jdbcTemplate.queryForObject(str8, Integer.class);
				int count3 = qkscl + tsscl + ztscl;
				po.setScnum(count3 + "");
				reslist.add(po);
			} else if (type.equals("day")) {
				po.setDate(sdt.plusDays(i).toString("yyyy-MM-dd"));
				// 浏览量
				String str1 = "select count(1) from browsereadtx where time >= \""
						+ sdt.plusDays(i).toString("yyyy-MM-dd") + "\" and time < \""
						+ sdt.plusDays(i).plusDays(1).toString("yyyy-MM-dd") + "\" ";
				int qklll = jdbcTemplate.queryForObject(str1, Integer.class);
				String str2 = "select count(1) from browselistenbook where time >= \""
						+ sdt.plusDays(i).toString("yyyy-MM-dd") + "\" and time < \""
						+ sdt.plusDays(i).plusDays(1).toString("yyyy-MM-dd") + "\" ";
				int tslll = jdbcTemplate.queryForObject(str2, Integer.class);
				String str3 = "select count(1) from accesslog where (kind=9 or kind=10) and time >= \""
						+ sdt.plusDays(i).toString("yyyy-MM-dd") + "\" and time < \""
						+ sdt.plusDays(i).plusDays(1).toString("yyyy-MM-dd") + "\" ";
				int lll = jdbcTemplate.queryForObject(str3, Integer.class);
				int count1 = qklll + tslll + lll;
				po.setLlnum(count1 + "");
				// 下载量
				String str4 = "select count(1) from downreadtx where time >= \""
						+ sdt.plusDays(i).toString("yyyy-MM-dd") + "\" and time < \""
						+ sdt.plusDays(i).plusDays(1).toString("yyyy-MM-dd") + "\" ";
				int qkxzl = jdbcTemplate.queryForObject(str4, Integer.class);
				String str5 = "select count(1) from downlistenbook where time >= \""
						+ sdt.plusDays(i).toString("yyyy-MM-dd") + "\" and time < \""
						+ sdt.plusDays(i).plusDays(1).toString("yyyy-MM-dd") + "\" ";
				int tsxzl = jdbcTemplate.queryForObject(str5, Integer.class);
				int count2 = qkxzl + tsxzl;
				po.setXznum(count2 + "");
				// 收藏量
				String str6 = "select count(1) from collectreadtx where time >= \""
						+ sdt.plusDays(i).toString("yyyy-MM-dd") + "\" and time < \""
						+ sdt.plusDays(i).plusDays(1).toString("yyyy-MM-dd") + "\" ";
				int qkscl = jdbcTemplate.queryForObject(str6, Integer.class);
				String str7 = "select count(1) from collectlistenbook where  time >= \""
						+ sdt.plusDays(i).toString("yyyy-MM-dd") + "\" and time < \""
						+ sdt.plusDays(i).plusDays(1).toString("yyyy-MM-dd") + "\" ";
				int tsscl = jdbcTemplate.queryForObject(str7, Integer.class);
				String str8 = "select count(1) from nlcsubjectcollect where time >= \""
						+ sdt.plusDays(i).toString("yyyy-MM-dd") + "\" and time < \""
						+ sdt.plusDays(i).plusDays(1).toString("yyyy-MM-dd") + "\" ";
				int ztscl = jdbcTemplate.queryForObject(str8, Integer.class);
				int count3 = qkscl + tsscl + ztscl;
				po.setScnum(count3 + "");
				reslist.add(po);
			} else if (type.equals("week")) {
				String label = sdt.plusWeeks(i).toString("yyyy") + "年第" + sdt.plusWeeks(i).getWeekOfWeekyear() + "周";
				po.setDate(label);
				// 浏览量
				String str1 = "select count(1) from browsereadtx where time >= \""
						+ sdt.plusWeeks(i).toString("yyyy-MM-dd") + "\" and time < \""
						+ sdt.plusWeeks(i).plusWeeks(1).toString("yyyy-MM-dd") + "\" ";
				int qklll = jdbcTemplate.queryForObject(str1, Integer.class);
				String str2 = "select count(1) from browselistenbook where time >= \""
						+ sdt.plusWeeks(i).toString("yyyy-MM-dd") + "\" and time < \""
						+ sdt.plusWeeks(i).plusWeeks(1).toString("yyyy-MM-dd") + "\" ";
				int tslll = jdbcTemplate.queryForObject(str2, Integer.class);
				String str3 = "select count(1) from accesslog where (kind=9 or kind=10) and time >= \""
						+ sdt.plusWeeks(i).toString("yyyy-MM-dd") + "\" and time < \""
						+ sdt.plusWeeks(i+1).toString("yyyy-MM-dd") + "\" ";
				int lll = jdbcTemplate.queryForObject(str3, Integer.class);
				int count1 = qklll + tslll + lll;
				po.setLlnum(count1 + "");
				// 下载量
				String str4 = "select count(1) from downreadtx where time >= \""
						+ sdt.plusWeeks(i).toString("yyyy-MM-dd") + "\" and time < \""
						+ sdt.plusWeeks(i+1).toString("yyyy-MM-dd") + "\" ";
				int qkxzl = jdbcTemplate.queryForObject(str4, Integer.class);
				String str5 = "select count(1) from downlistenbook where time >= \""
						+ sdt.plusWeeks(i).toString("yyyy-MM-dd") + "\" and time < \""
						+ sdt.plusWeeks(i+1).toString("yyyy-MM-dd") + "\" ";
				int tsxzl = jdbcTemplate.queryForObject(str5, Integer.class);
				int count2 = qkxzl + tsxzl;
				po.setXznum(count2 + "");
				// 收藏量
				String str6 = "select count(1) from collectreadtx where time >= \""
						+ sdt.plusWeeks(i).toString("yyyy-MM-dd") + "\" and time < \""
						+ sdt.plusWeeks(i+1).toString("yyyy-MM-dd") + "\" ";
				int qkscl = jdbcTemplate.queryForObject(str6, Integer.class);
				String str7 = "select count(1) from collectlistenbook where  time >= \""
						+ sdt.plusWeeks(i).toString("yyyy-MM-dd") + "\" and time < \""
						+ sdt.plusWeeks(i+1).toString("yyyy-MM-dd") + "\" ";
				int tsscl = jdbcTemplate.queryForObject(str7, Integer.class);
				String str8 = "select count(1) from nlcsubjectcollect where time >= \""
						+ sdt.plusWeeks(i).toString("yyyy-MM-dd") + "\" and time < \""
						+ sdt.plusWeeks(i+1).toString("yyyy-MM-dd") + "\" ";
				int ztscl = jdbcTemplate.queryForObject(str8, Integer.class);
				int count3 = qkscl + tsscl + ztscl;
				po.setScnum(count3 + "");
				reslist.add(po);
			}
		}

		EasyUiDataGridResult result = new EasyUiDataGridResult(totalRow, reslist);
		return result;
	}

	/**
	 * 各资源画像的list数据 update by JJJ 20170224 pm
	 * 
	 * @param page
	 * @param rows
	 * @return
	 */
	@Override
	public EasyUiDataGridResult gzyhxList(Integer page, Integer rows, String sort, String order, String type,
			String magazineid, String title, Date startDate, Date endDate,boolean getAll) {
		DateTime sdt = new DateTime(startDate);
		DateTime edt = new DateTime(endDate);

		String timeTJ = " WHERE time >='" + sdt.toString("yyyy-MM-dd") + "' and time <'"
				+ edt.plusDays(1).toString("yyyy-MM-dd") + "' ";
		StringBuilder sumsql = new StringBuilder();
		sumsql.append(" from ( " + " select 'r' as type, rt.magazineid, rt.title,IFNULL(dr.down,0) down,IFNULL(cr.collect,0) collect,IFNULL(br.browse,0) browse "
				+ " from readtx rt LEFT JOIN (select magazineid,count(*) as down from downreadtx " + timeTJ
				+ " GROUP BY magazineid) dr on rt.magazineid= dr.magazineid "
				+ " left join (select magazineid,count(*) as collect from collectreadtx " + timeTJ
				+ " GROUP BY magazineid) cr on rt.magazineid= cr.magazineid "
				+ " left join (select magazineid,count(*) as browse from browsereadtx " + timeTJ
				+ " GROUP BY magazineid) br on rt.magazineid= br.magazineid " + " UNION all "
				+ " select 'l' as type,lb.bookid as magazineid, lb.bookname as title,IFNULL(dl.down,0) down,IFNULL(cl.collect,0) collect,IFNULL(bl.browse,0) browse"
				+ " from listenbook lb LEFT JOIN (select bookid,count(*) as down from downlistenbook " + timeTJ
				+ " GROUP BY bookid) dl on lb.bookid= dl.bookid "
				+ " left join (select bookid,count(*) as collect from collectlistenbook " + timeTJ
				+ " GROUP BY bookid) cl on lb.bookid= cl.bookid "
				+ " left join (select bookid,count(*) as browse from browselistenbook " + timeTJ
				+ " GROUP BY bookid) bl on lb.bookid= bl.bookid " + " ) tj where 1=1 ");
		List<String> listArgs = new ArrayList<String>();
		if (StringUtils.isNotBlank(type)) {
			sumsql.append(" and type = ? ");
			listArgs.add(type);
		}

		if (StringUtils.isNotBlank(magazineid)) {
			sumsql.append(" and magazineid = ? ");
			listArgs.add(magazineid.trim());
		}

		if (StringUtils.isNotBlank(title)) {
			sumsql.append(" and title like ? ");
			listArgs.add(title.trim()+"%");
		}
		
		Object[] sarr = listArgs.toArray();
		int totalRow = 0 ;
		if(!getAll){
			totalRow = jdbcTemplate.queryForObject("SELECT count(1) " + sumsql.toString(),sarr, Integer.class);
			int start = (page - 1) * rows;
			int limit = rows;
			
			if (StringUtils.isNotBlank(sort)) {
				sumsql.append(" order by " + sort + " " + order + " limit " + start + " , " + limit);
			} else {
				sumsql.append("order by magazineid limit " + start + " , " + limit);
			}
		}

		List<GzyhxPo> list2 = jdbcTemplate.query("SELECT * " + sumsql.toString(),sarr,
				new BeanPropertyRowMapper<GzyhxPo>(GzyhxPo.class));
		EasyUiDataGridResult result = new EasyUiDataGridResult(totalRow, list2);
		return result;
	}

	@Override
	public EasyUiDataGridResult ydyhList(Integer page, Integer rows, String type, String magazineid, Date startDate,
			Date endDate ,boolean getAll) {
		DateTime sdt = new DateTime(startDate);
		DateTime edt = new DateTime(endDate);
		List<Ydyh> list = new ArrayList<Ydyh>();
		EasyUiDataGridResult result = null;
		int start = 0, end = 0;

		String timeTJ = " time >='" + sdt.toString("yyyy-MM-dd") + "' and time <'"
				+ edt.plusDays(1).toString("yyyy-MM-dd") + "' ";
		if ("r".equalsIgnoreCase(type)) { // 期刊 
			String sql = " SELECT username ,address from accesslog where username in (select username from browsereadtx where " + timeTJ
					+ " and magazineid = '" + magazineid + "') and detailid='" + magazineid + "'  and  " + timeTJ
					+ "  GROUP BY username ";

			int totalRow = jdbcTemplate.queryForObject("SELECT count(*) from (" + sql+") temp", Integer.class);
			if(getAll){
				end = totalRow;
			}else{
				start = (page - 1) * rows;
				if ((start + rows) > totalRow) {
					end = totalRow;
				} else {
					end = start + rows;
				}
			}
			list = jdbcTemplate.query( sql + " LIMIT " + start + "," + end + " ",
					new BeanPropertyRowMapper<Ydyh>(Ydyh.class));
			result = new EasyUiDataGridResult(totalRow, list);
		} else if ("l".equalsIgnoreCase(type)) { // 听书
			String sql = " SELECT username ,address from accesslog where username in (select username from browselistenbook where " + timeTJ
					+ " and bookid = '" + magazineid + "') and detailid='" + magazineid + "'  and  " + timeTJ
					+ "  GROUP BY username ";
			int totalRow = jdbcTemplate.queryForObject("SELECT count(1) from (" + sql+") temp", Integer.class);
			if(getAll){
				end = totalRow;
			}else{
				start = (page - 1) * rows;
				if ((start + rows) > totalRow) {
					end = totalRow;
				} else {
					end = start + rows;
				}
			}
			list = jdbcTemplate.query(  sql + " LIMIT " + start + "," + end + " ",
					new BeanPropertyRowMapper<Ydyh>(Ydyh.class));
			result = new EasyUiDataGridResult(totalRow, list);
		} else {
			return null;
		}
		
		return result;
	}
	/**
	 *  阅读情况List add by JJJ 20170228 pm
	 */
	@Override
	public EasyUiDataGridResult readDetailList(Integer page, Integer rows, String username, Date startDate,
			Date endDate, boolean getAll) {
		DateTime sdt = new DateTime(startDate);
		DateTime edt = new DateTime(endDate);
		List<Ydqk> list = new ArrayList<Ydqk>();

		String timeTJ = " time >='" + sdt.toString("yyyy-MM-dd") + "' and time <'"
				+ edt.plusDays(1).toString("yyyy-MM-dd") + "' ";

		String sql_qk ="SELECT a.detailid zyID,r.title zymc, COUNT(1) ydcs,SUM(IFNULL(a.waittime,0)) ydsc  from accesslog a LEFT JOIN readtx r ON a.detailid= r.magazineid "
			+ " where username ='"+username+"' and kind =11 and "+timeTJ+" GROUP BY  zyID";
		List<Ydqk> list1 = jdbcTemplate.query( sql_qk ,new BeanPropertyRowMapper<Ydqk>(Ydqk.class));
		String sql_ts ="SELECT a.detailid zyID,l.bookname zymc, COUNT(1) ydcs,SUM(IFNULL(a.waittime,0)) ydsc from accesslog a LEFT JOIN listenbook l ON a.detailid= l.bookid"
				+ " where username ='"+username+"' and kind =12 GROUP BY  zyID";
		List<Ydqk> list2 = jdbcTemplate.query( sql_ts ,new BeanPropertyRowMapper<Ydqk>(Ydqk.class));
		list1.addAll(list2);
		int totalRow = list1.size();
		
		if(getAll){
			list = list1;
		}else{
			int start = (page - 1) * rows;
			if ((start + rows) > totalRow) {
				list = list1.subList(start, totalRow);
			} else {
				list = list1.subList(start, start + rows);
			}
		}
		EasyUiDataGridResult result = new EasyUiDataGridResult(totalRow, list);
		return result;
	}
	/**
	 * 各资源画像--查看资源画像--性别分布的数据 update by JJJ 20170224 pm
	 */
	@Override
	public Map<String, String> ckzyhxxbfb(String type, String magazineid, Date startDate, Date endDate) {
		DateTime sdt = new DateTime(startDate);
		DateTime edt = new DateTime(endDate);
		String timeTJ = " time >='" + sdt.toString("yyyy-MM-dd") + "' and time <'"
				+ edt.plusDays(1).toString("yyyy-MM-dd") + "' ";

		Map<String, String> resmap = new HashMap<String, String>();
		NumberFormat format = NumberFormat.getPercentInstance();// 获取格式化类实例
		format.setMinimumFractionDigits(2);// 设置小数位

		if ("r".equalsIgnoreCase(type)) { // 期刊

			String sumsql = "select count(1) from " + " (select nlcuser.* from "
					+ " (select username from browsereadtx where " + timeTJ + " and magazineid = '" + magazineid
					+ "' UNION " + " select username from collectreadtx where " + timeTJ + " and magazineid = '"
					+ magazineid + "' UNION " + " select username from downreadtx where " + timeTJ
					+ " and magazineid = '" + magazineid + "') txread "
					+ " , nlcuser where txread.username=nlcuser.loginAccount) temp";
			int sum = jdbcTemplate.queryForObject(sumsql, Integer.class);
			resmap.put("sum", sum + "");

			// 女士
			String fesql = "select count(1) from " + " (select nlcuser.* from "
					+ " (select username from browsereadtx where " + timeTJ + " and magazineid = '" + magazineid
					+ "' UNION " + " select username from collectreadtx where " + timeTJ + " and magazineid = '"
					+ magazineid + "' UNION " + " select username from downreadtx where " + timeTJ
					+ " and magazineid = '" + magazineid + "') txread "
					+ " , nlcuser where txread.username=nlcuser.loginAccount and nlcuser.sexType = '女士') temp";
			int fenum = jdbcTemplate.queryForObject(fesql, Integer.class);
			resmap.put("fenum", fenum + "");
			if (0 == sum) {
				resmap.put("feratio", "0.0");
			} else {
				resmap.put("feratio", format.format(fenum / (sum + 0.0))); // 女士所占的比例
			}

			// 男士
			String masql = "select count(1) from " + " (select nlcuser.* from "
					+ " (select username from browsereadtx where " + timeTJ + " and magazineid = '" + magazineid
					+ "' UNION " + " select username from collectreadtx where " + timeTJ + " and magazineid = '"
					+ magazineid + "' UNION " + " select username from downreadtx where " + timeTJ
					+ " and magazineid = '" + magazineid + "') txread "
					+ " , nlcuser where txread.username=nlcuser.loginAccount and nlcuser.sexType != '女士') temp";
			int manum = jdbcTemplate.queryForObject(masql, Integer.class);
			resmap.put("manum", manum + "");
			if (0 == sum) {
				resmap.put("maratio", "0.0");
			} else {
				resmap.put("maratio", format.format(manum / (sum + 0.0))); // 男士所占的比例
			}

		} else { // 听书

			String sumsql = "select count(1) from " + " (select nlcuser.* from "
					+ " (select username from browselistenbook where " + timeTJ + " and bookid = '" + magazineid
					+ "' UNION " + " select username from collectlistenbook where " + timeTJ + " and bookid = '"
					+ magazineid + "' UNION " + " select username from downlistenbook where " + timeTJ
					+ " and bookid = '" + magazineid + "') booklisten "
					+ " , nlcuser where booklisten.username=nlcuser.loginAccount) temp";
			int sum = jdbcTemplate.queryForObject(sumsql, Integer.class);
			resmap.put("sum", sum + "");

			// 女士
			String fesql = "select count(1) from " + " (select nlcuser.* from "
					+ " (select username from browselistenbook where " + timeTJ + " and bookid = '" + magazineid
					+ "' UNION " + " select username from collectlistenbook where " + timeTJ + " and bookid = '"
					+ magazineid + "' UNION " + " select username from downlistenbook where " + timeTJ
					+ " and bookid = '" + magazineid + "') booklisten "
					+ " , nlcuser where booklisten.username=nlcuser.loginAccount and nlcuser.sexType = '女士') temp";
			int fenum = jdbcTemplate.queryForObject(fesql, Integer.class);
			resmap.put("fenum", fenum + "");
			if (0 == sum) {
				resmap.put("feratio", "0.0");
			} else {
				resmap.put("feratio", format.format(fenum / (sum + 0.0))); // 女士所占的比例
			}

			// 男士
			String masql = "select count(1) from " + " (select nlcuser.* from "
					+ " (select username from browselistenbook where " + timeTJ + " and bookid = '" + magazineid
					+ "' UNION " + " select username from collectlistenbook where " + timeTJ + " and bookid = '"
					+ magazineid + "' UNION " + " select username from downlistenbook where " + timeTJ
					+ " and bookid = '" + magazineid + "') booklisten "
					+ " , nlcuser where booklisten.username=nlcuser.loginAccount and nlcuser.sexType != '女士') temp";
			int manum = jdbcTemplate.queryForObject(masql, Integer.class);
			resmap.put("manum", manum + "");
			if (0 == sum) {
				resmap.put("maratio", "0.0");
			} else {
				resmap.put("maratio", format.format(manum / (sum + 0.0))); // 男士所占的比例
			}

		}
		return resmap;
	}

	/**
	 * 查看资源画像--年龄分布2D条形图数据 update by JJJ 20170224 pm
	 * 
	 * @param type
	 * @param magazineid
	 * @return
	 */
	@Override
	public List<DyfxPoExt> ckzyhxnlfb1(String type, String magazineid, Map<String, String> resmap, Date startDate,
			Date endDate) {
		List<DyfxPoExt> reslist = new ArrayList<DyfxPoExt>();
		DateTime now = new DateTime(endDate);
		DateTime year19 = now.minusYears(19);
		DateTime year24 = now.minusYears(24);
		DateTime year31 = now.minusYears(31);
		DateTime year41 = now.minusYears(41);

		DateTime sdt = new DateTime(startDate);
		DateTime edt = new DateTime(endDate);
		String timeTJ = " time >='" + sdt.toString("yyyy-MM-dd") + "' and time <'"
				+ edt.plusDays(1).toString("yyyy-MM-dd") + "' ";

		if ("r".equalsIgnoreCase(type)) { // 期刊

			// 其他，生日统计不到的
			String qtsql = "select count(1) from " + " (select nlcuser.* from "
					+ " (select username from browsereadtx where " + timeTJ + " and magazineid = '" + magazineid
					+ "' UNION " + " select username from collectreadtx where " + timeTJ + " and magazineid = '"
					+ magazineid + "' UNION " + " select username from downreadtx where " + timeTJ
					+ " and magazineid = '" + magazineid + "') txread "
					+ " , nlcuser where txread.username=nlcuser.loginAccount and birthday is null) temp";
			int qtnum = jdbcTemplate.queryForObject(qtsql, Integer.class);
			DyfxPoExt d1 = new DyfxPoExt();
			d1.setName("信息缺失");
			d1.setValue(qtnum);
			d1.setColor("#a5c2d5");
			reslist.add(d1);
			resmap.put("xxqs", qtnum + "");

			// 40岁以上
			String sql1 = "select count(1) from " + " (select nlcuser.* from "
					+ " (select username from browsereadtx where " + timeTJ + " and magazineid = '" + magazineid
					+ "' UNION " + " select username from collectreadtx where " + timeTJ + " and magazineid = '"
					+ magazineid + "' UNION " + " select username from downreadtx where " + timeTJ
					+ " and magazineid = '" + magazineid + "') txread "
					+ " , nlcuser where txread.username=nlcuser.loginAccount and birthday <= '"
					+ year41.toString("yyyy-MM-dd") + "') temp";
			int num1 = jdbcTemplate.queryForObject(sql1, Integer.class);
			DyfxPoExt d2 = new DyfxPoExt();
			d2.setName("40岁以上");
			d2.setValue(num1);
			d2.setColor("#a5c2d5");
			reslist.add(d2);
			resmap.put("res1", num1 + "");

			// 31~40岁
			String sql2 = "select count(1) from " + " (select nlcuser.* from "
					+ " (select username from browsereadtx where " + timeTJ + " and magazineid = '" + magazineid
					+ "' UNION " + " select username from collectreadtx where " + timeTJ + " and magazineid = '"
					+ magazineid + "' UNION " + " select username from downreadtx where " + timeTJ
					+ " and magazineid = '" + magazineid + "') txread "
					+ " , nlcuser where txread.username=nlcuser.loginAccount and birthday > '"
					+ year41.toString("yyyy-MM-dd") + "' and birthday <= '" + year31.toString("yyyy-MM-dd") + "') temp";
			int num2 = jdbcTemplate.queryForObject(sql2, Integer.class);
			DyfxPoExt d3 = new DyfxPoExt();
			d3.setName("31~40岁");
			d3.setValue(num2);
			d3.setColor("#a5c2d5");
			reslist.add(d3);
			resmap.put("res2", num2 + "");

			// 24~30岁
			String sql3 = "select count(1) from " + " (select nlcuser.* from "
					+ " (select username from browsereadtx where " + timeTJ + " and magazineid = '" + magazineid
					+ "' UNION " + " select username from collectreadtx where " + timeTJ + " and magazineid = '"
					+ magazineid + "' UNION " + " select username from downreadtx where " + timeTJ
					+ " and magazineid = '" + magazineid + "') txread "
					+ " , nlcuser where txread.username=nlcuser.loginAccount and birthday > '"
					+ year31.toString("yyyy-MM-dd") + "' and birthday <= '" + year24.toString("yyyy-MM-dd") + "') temp";
			int num3 = jdbcTemplate.queryForObject(sql3, Integer.class);
			DyfxPoExt d4 = new DyfxPoExt();
			d4.setName("24~30岁");
			d4.setValue(num3);
			d4.setColor("#a5c2d5");
			reslist.add(d4);
			resmap.put("res3", num3 + "");

			// 19~23岁
			String sql4 = "select count(1) from " + " (select nlcuser.* from "
					+ " (select username from browsereadtx where " + timeTJ + " and magazineid = '" + magazineid
					+ "' UNION " + " select username from collectreadtx where " + timeTJ + " and magazineid = '"
					+ magazineid + "' UNION " + " select username from downreadtx where " + timeTJ
					+ " and magazineid = '" + magazineid + "') txread "
					+ " , nlcuser where txread.username=nlcuser.loginAccount and birthday > '"
					+ year24.toString("yyyy-MM-dd") + "' and birthday <= '" + year19.toString("yyyy-MM-dd") + "') temp";
			int num4 = jdbcTemplate.queryForObject(sql4, Integer.class);
			DyfxPoExt d5 = new DyfxPoExt();
			d5.setName("19~23岁");
			d5.setValue(num4);
			d5.setColor("#a5c2d5");
			reslist.add(d5);
			resmap.put("res4", num4 + "");

			// 18岁以下
			String sql5 = "select count(1) from " + " (select nlcuser.* from "
					+ " (select username from browsereadtx where " + timeTJ + " and magazineid = '" + magazineid
					+ "' UNION " + " select username from collectreadtx where " + timeTJ + " and magazineid = '"
					+ magazineid + "' UNION " + " select username from downreadtx where " + timeTJ
					+ " and magazineid = '" + magazineid + "') txread "
					+ " , nlcuser where txread.username=nlcuser.loginAccount and birthday > '"
					+ year19.toString("yyyy-MM-dd") + "') temp";
			int num5 = jdbcTemplate.queryForObject(sql5, Integer.class);
			DyfxPoExt d6 = new DyfxPoExt();
			d6.setName("18岁以下");
			d6.setValue(num5);
			d6.setColor("#a5c2d5");
			reslist.add(d6);
			resmap.put("res5", num5 + "");

			// 总计
			String sumsql = "select count(1) from " + " (select nlcuser.* from "
					+ " (select username from browsereadtx where " + timeTJ + " and magazineid = '" + magazineid
					+ "' UNION " + " select username from collectreadtx where " + timeTJ + " and magazineid = '"
					+ magazineid + "' UNION " + " select username from downreadtx where " + timeTJ
					+ " and magazineid = '" + magazineid + "') txread "
					+ " , nlcuser where txread.username=nlcuser.loginAccount) temp";
			int sum = jdbcTemplate.queryForObject(sumsql, Integer.class);
			resmap.put("sum", sum + "");

		} else { // 听书
			// 其他，生日统计不到的
			String qtsql = "select count(1) from " + " (select nlcuser.* from "
					+ " (select username from browselistenbook where " + timeTJ + " and bookid = '" + magazineid
					+ "' UNION " + " select username from collectlistenbook where " + timeTJ + " and bookid = '"
					+ magazineid + "' UNION " + " select username from downlistenbook where " + timeTJ
					+ " and bookid = '" + magazineid + "') txread "
					+ " , nlcuser where txread.username=nlcuser.loginAccount and birthday is null) temp";
			int qtnum = jdbcTemplate.queryForObject(qtsql, Integer.class);
			DyfxPoExt d1 = new DyfxPoExt();
			d1.setName("信息缺失");
			d1.setValue(qtnum);
			d1.setColor("#a5c2d5");
			reslist.add(d1);
			resmap.put("xxqs", qtnum + "");

			// 40岁以上
			String sql1 = "select count(1) from " + " (select nlcuser.* from "
					+ " (select username from browselistenbook where " + timeTJ + " and bookid = '" + magazineid
					+ "' UNION " + " select username from collectlistenbook where " + timeTJ + " and bookid = '"
					+ magazineid + "' UNION " + " select username from downlistenbook where " + timeTJ
					+ " and bookid = '" + magazineid + "') txread "
					+ " , nlcuser where txread.username=nlcuser.loginAccount and birthday <= '"
					+ year41.toString("yyyy-MM-dd") + "') temp";
			int num1 = jdbcTemplate.queryForObject(sql1, Integer.class);
			DyfxPoExt d2 = new DyfxPoExt();
			d2.setName("40岁以上");
			d2.setValue(num1);
			d2.setColor("#a5c2d5");
			reslist.add(d2);
			resmap.put("res1", num1 + "");

			// 31~40岁
			String sql2 = "select count(1) from " + " (select nlcuser.* from "
					+ " (select username from browselistenbook where " + timeTJ + " and bookid = '" + magazineid
					+ "' UNION " + " select username from collectlistenbook where " + timeTJ + " and bookid = '"
					+ magazineid + "' UNION " + " select username from downlistenbook where " + timeTJ
					+ " and bookid = '" + magazineid + "') txread "
					+ " , nlcuser where txread.username=nlcuser.loginAccount and birthday > '"
					+ year41.toString("yyyy-MM-dd") + "' and birthday <= '" + year31.toString("yyyy-MM-dd") + "') temp";
			int num2 = jdbcTemplate.queryForObject(sql2, Integer.class);
			DyfxPoExt d3 = new DyfxPoExt();
			d3.setName("31~40岁");
			d3.setValue(num2);
			d3.setColor("#a5c2d5");
			reslist.add(d3);
			resmap.put("res2", num2 + "");

			// 24~30岁
			String sql3 = "select count(1) from " + " (select nlcuser.* from "
					+ " (select username from browselistenbook where " + timeTJ + " and bookid = '" + magazineid
					+ "' UNION " + " select username from collectlistenbook where " + timeTJ + " and bookid = '"
					+ magazineid + "' UNION " + " select username from downlistenbook where " + timeTJ
					+ " and bookid = '" + magazineid + "') txread "
					+ " , nlcuser where txread.username=nlcuser.loginAccount and birthday > '"
					+ year31.toString("yyyy-MM-dd") + "' and birthday <= '" + year24.toString("yyyy-MM-dd") + "') temp";
			int num3 = jdbcTemplate.queryForObject(sql3, Integer.class);
			DyfxPoExt d4 = new DyfxPoExt();
			d4.setName("24~30岁");
			d4.setValue(num3);
			d4.setColor("#a5c2d5");
			reslist.add(d4);
			resmap.put("res3", num3 + "");

			// 19~23岁
			String sql4 = "select count(1) from " + " (select nlcuser.* from "
					+ " (select username from browselistenbook where " + timeTJ + " and bookid = '" + magazineid
					+ "' UNION " + " select username from collectlistenbook where " + timeTJ + " and bookid = '"
					+ magazineid + "' UNION " + " select username from downlistenbook where " + timeTJ
					+ " and bookid = '" + magazineid + "') txread "
					+ " , nlcuser where txread.username=nlcuser.loginAccount and birthday > '"
					+ year24.toString("yyyy-MM-dd") + "' and birthday <= '" + year19.toString("yyyy-MM-dd") + "') temp";
			int num4 = jdbcTemplate.queryForObject(sql4, Integer.class);
			DyfxPoExt d5 = new DyfxPoExt();
			d5.setName("19~23岁");
			d5.setValue(num4);
			d5.setColor("#a5c2d5");
			reslist.add(d5);
			resmap.put("res4", num4 + "");

			// 18岁以下
			String sql5 = "select count(1) from " + " (select nlcuser.* from "
					+ " (select username from browselistenbook where " + timeTJ + " and bookid = '" + magazineid
					+ "' UNION " + " select username from collectlistenbook where " + timeTJ + " and bookid = '"
					+ magazineid + "' UNION " + " select username from downlistenbook where " + timeTJ
					+ " and bookid = '" + magazineid + "') txread "
					+ " , nlcuser where txread.username=nlcuser.loginAccount and birthday > '"
					+ year19.toString("yyyy-MM-dd") + "') temp";
			int num5 = jdbcTemplate.queryForObject(sql5, Integer.class);
			DyfxPoExt d6 = new DyfxPoExt();
			d6.setName("18岁以下");
			d6.setValue(num5);
			d6.setColor("#a5c2d5");
			reslist.add(d6);
			resmap.put("res5", num5 + "");

			String sumsql = "select count(1) from " + " (select nlcuser.* from "
					+ " (select username from browselistenbook where " + timeTJ + " and bookid = '" + magazineid
					+ "' UNION " + " select username from collectlistenbook where " + timeTJ + " and bookid = '"
					+ magazineid + "' UNION " + " select username from downlistenbook where " + timeTJ
					+ " and bookid = '" + magazineid + "') booklisten "
					+ " , nlcuser where booklisten.username=nlcuser.loginAccount) temp";
			int sum = jdbcTemplate.queryForObject(sumsql, Integer.class);
			resmap.put("sum", sum + "");
		}

		return reslist;
	}

	/**
	 * 各资源画像/查看各资源画像中学历分布的图 update by JJJ 20170224 pm
	 */
	@Override
	public Map<String, String> ckzyhxxlfb(String type, String magazineid, Date startDate, Date endDate) {
		Map<String, String> resmap = new HashMap<String, String>();
		NumberFormat format = NumberFormat.getPercentInstance();// 获取格式化类实例
		format.setMinimumFractionDigits(2);// 设置小数位

		DateTime sdt = new DateTime(startDate);
		DateTime edt = new DateTime(endDate);
		String timeTJ = " time >='" + sdt.toString("yyyy-MM-dd") + "' and time <'"
				+ edt.plusDays(1).toString("yyyy-MM-dd") + "' ";

		if ("r".equalsIgnoreCase(type)) { // 期刊

			// 总计
			String sumsql = "select count(1) from " + " (select nlcuser.* from "
					+ " (select username from browsereadtx where " + timeTJ + " and magazineid = '" + magazineid
					+ "' UNION " + " select username from collectreadtx where " + timeTJ + " and magazineid = '"
					+ magazineid + "' UNION " + " select username from downreadtx where " + timeTJ
					+ " and magazineid = '" + magazineid + "') txread "
					+ " , nlcuser where txread.username=nlcuser.loginAccount) temp";
			int sum = jdbcTemplate.queryForObject(sumsql, Integer.class);
			resmap.put("sum", sum + "");

			// 本科
			String sql1 = "select count(1) from " + " (select nlcuser.* from "
					+ " (select username from browsereadtx where " + timeTJ + " and magazineid = '" + magazineid
					+ "' UNION " + " select username from collectreadtx where " + timeTJ + " and magazineid = '"
					+ magazineid + "' UNION " + " select username from downreadtx where " + timeTJ
					+ " and magazineid = '" + magazineid + "') txread "
					+ " , nlcuser where txread.username=nlcuser.loginAccount and education = '本科') temp";
			int benkeNum = jdbcTemplate.queryForObject(sql1, Integer.class);
			resmap.put("benkeNum", benkeNum + "");
			if (0 == sum) {
				resmap.put("benkeRatio", "0.0");
			} else {
				resmap.put("benkeRatio", format.format(benkeNum / (sum + 0.0)));
			}

			// 专科
			String sql2 = "select count(1) from " + " (select nlcuser.* from "
					+ " (select username from browsereadtx where " + timeTJ + " and magazineid = '" + magazineid
					+ "' UNION " + " select username from collectreadtx where " + timeTJ + " and magazineid = '"
					+ magazineid + "' UNION " + " select username from downreadtx where " + timeTJ
					+ " and magazineid = '" + magazineid + "') txread "
					+ " , nlcuser where txread.username=nlcuser.loginAccount and education = '专科') temp";
			int zhuankeNum = jdbcTemplate.queryForObject(sql2, Integer.class);
			resmap.put("zhuankeNum", zhuankeNum + "");
			if (0 == sum) {
				resmap.put("zhuankeRatio", "0.0");
			} else {
				resmap.put("zhuankeRatio", format.format(zhuankeNum / (sum + 0.0)));
			}

			// 硕士
			String sql3 = "select count(1) from " + " (select nlcuser.* from "
					+ " (select username from browsereadtx where " + timeTJ + " and magazineid = '" + magazineid
					+ "' UNION " + " select username from collectreadtx where " + timeTJ + " and magazineid = '"
					+ magazineid + "' UNION " + " select username from downreadtx where " + timeTJ
					+ " and magazineid = '" + magazineid + "') txread "
					+ " , nlcuser where txread.username=nlcuser.loginAccount and education = '硕士') temp";
			int shuoshiNum = jdbcTemplate.queryForObject(sql3, Integer.class);
			resmap.put("shuoshiNum", shuoshiNum + "");
			if (0 == sum) {
				resmap.put("shuoshiRatio", "0.0");
			} else {
				resmap.put("shuoshiRatio", format.format(shuoshiNum / (sum + 0.0)));
			}

			// 博士
			String sql4 = "select count(1) from " + " (select nlcuser.* from "
					+ " (select username from browsereadtx where " + timeTJ + " and magazineid = '" + magazineid
					+ "' UNION " + " select username from collectreadtx where " + timeTJ + " and magazineid = '"
					+ magazineid + "' UNION " + " select username from downreadtx where " + timeTJ
					+ " and magazineid = '" + magazineid + "') txread "
					+ " , nlcuser where txread.username=nlcuser.loginAccount and education = '博士') temp";
			int boshiNum = jdbcTemplate.queryForObject(sql4, Integer.class);
			resmap.put("boshiNum", boshiNum + "");
			if (0 == sum) {
				resmap.put("boshiRatio", "0.0");
			} else {
				resmap.put("boshiRatio", format.format(boshiNum / (sum + 0.0)));
			}

			// 专科以下
			String sql5 = "select count(1) from " + " (select nlcuser.* from "
					+ " (select username from browsereadtx where " + timeTJ + " and magazineid = '" + magazineid
					+ "' UNION " + " select username from collectreadtx where " + timeTJ + " and magazineid = '"
					+ magazineid + "' UNION " + " select username from downreadtx where " + timeTJ
					+ " and magazineid = '" + magazineid + "') txread "
					+ " , nlcuser where txread.username=nlcuser.loginAccount and (education not in ('专科', '本科', '硕士', '博士') or education is null) ) temp";
			int qitaNum = jdbcTemplate.queryForObject(sql5, Integer.class);
			resmap.put("qitaNum", qitaNum + "");
			if (0 == sum) {
				resmap.put("qitaRatio", "0.0");
			} else {
				resmap.put("qitaRatio", format.format(qitaNum / (sum + 0.0)));
			}
		} else { // 听书

			// ================================
			// 总计
			String sumsql = "select count(1) from " + " (select nlcuser.* from "
					+ " (select username from browselistenbook where " + timeTJ + " and bookid = '" + magazineid
					+ "' UNION " + " select username from collectlistenbook where " + timeTJ + " and bookid = '"
					+ magazineid + "' UNION " + " select username from downlistenbook where " + timeTJ
					+ " and bookid = '" + magazineid + "') txread "
					+ " , nlcuser where txread.username=nlcuser.loginAccount) temp";
			int sum = jdbcTemplate.queryForObject(sumsql, Integer.class);
			resmap.put("sum", sum + "");

			// 本科
			String sql1 = "select count(1) from " + " (select nlcuser.* from "
					+ " (select username from browselistenbook where " + timeTJ + " and bookid = '" + magazineid
					+ "' UNION " + " select username from collectlistenbook where " + timeTJ + " and bookid = '"
					+ magazineid + "' UNION " + " select username from downlistenbook where " + timeTJ
					+ " and bookid = '" + magazineid + "') txread "
					+ " , nlcuser where txread.username=nlcuser.loginAccount and education = '本科') temp";
			int benkeNum = jdbcTemplate.queryForObject(sql1, Integer.class);
			resmap.put("benkeNum", benkeNum + "");
			if (0 == sum) {
				resmap.put("benkeRatio", "0.0");
			} else {
				resmap.put("benkeRatio", format.format(benkeNum / (sum + 0.0)));
			}

			// 专科
			String sql2 = "select count(1) from " + " (select nlcuser.* from "
					+ " (select username from browselistenbook where " + timeTJ + " and bookid = '" + magazineid
					+ "' UNION " + " select username from collectlistenbook where " + timeTJ + " and bookid = '"
					+ magazineid + "' UNION " + " select username from downlistenbook where " + timeTJ
					+ " and bookid = '" + magazineid + "') txread "
					+ " , nlcuser where txread.username=nlcuser.loginAccount and education = '专科') temp";
			int zhuankeNum = jdbcTemplate.queryForObject(sql2, Integer.class);
			resmap.put("zhuankeNum", zhuankeNum + "");
			if (0 == sum) {
				resmap.put("zhuankeRatio", "0.0");
			} else {
				resmap.put("zhuankeRatio", format.format(zhuankeNum / (sum + 0.0)));
			}

			// 硕士
			String sql3 = "select count(1) from " + " (select nlcuser.* from "
					+ " (select username from browselistenbook where " + timeTJ + " and bookid = '" + magazineid
					+ "' UNION " + " select username from collectlistenbook where " + timeTJ + " and bookid = '"
					+ magazineid + "' UNION " + " select username from downlistenbook where " + timeTJ
					+ " and bookid = '" + magazineid + "') txread "
					+ " , nlcuser where txread.username=nlcuser.loginAccount and education = '硕士') temp";
			int shuoshiNum = jdbcTemplate.queryForObject(sql3, Integer.class);
			resmap.put("shuoshiNum", shuoshiNum + "");
			if (0 == sum) {
				resmap.put("shuoshiRatio", "0.0");
			} else {
				resmap.put("shuoshiRatio", format.format(shuoshiNum / (sum + 0.0)));
			}

			// 博士
			String sql4 = "select count(1) from " + " (select nlcuser.* from "
					+ " (select username from browselistenbook where " + timeTJ + " and bookid = '" + magazineid
					+ "' UNION " + " select username from collectlistenbook where " + timeTJ + " and bookid = '"
					+ magazineid + "' UNION " + " select username from downlistenbook where " + timeTJ
					+ " and bookid = '" + magazineid + "') txread "
					+ " , nlcuser where txread.username=nlcuser.loginAccount and education = '博士') temp";
			int boshiNum = jdbcTemplate.queryForObject(sql4, Integer.class);
			resmap.put("boshiNum", boshiNum + "");
			if (0 == sum) {
				resmap.put("boshiRatio", "0.0");
			} else {
				resmap.put("boshiRatio", format.format(boshiNum / (sum + 0.0)));
			}

			// 专科以下
			String sql5 = "select count(1) from " + " (select nlcuser.* from "
					+ " (select username from browselistenbook where " + timeTJ + " and bookid = '" + magazineid
					+ "' UNION " + " select username from collectlistenbook where " + timeTJ + " and bookid = '"
					+ magazineid + "' UNION " + " select username from downlistenbook where " + timeTJ
					+ " and bookid = '" + magazineid + "') txread "
					+ " , nlcuser where txread.username=nlcuser.loginAccount and (education not in ('专科', '本科', '硕士', '博士') or education is null) ) temp";
			int qitaNum = jdbcTemplate.queryForObject(sql5, Integer.class);
			resmap.put("qitaNum", qitaNum + "");
			if (0 == sum) {
				resmap.put("qitaRatio", "0.0");
			} else {
				resmap.put("qitaRatio", format.format(qitaNum / (sum + 0.0)));
			}
		}

		return resmap;
	}

	/**
	 * app统计iphone时长折线图数据
	 */
	public List<Integer> apptjiphonescList(Date startDate, Date endDate) {
		List<Integer> dataList = new ArrayList<Integer>();
		DateTime startDateJoda = new DateTime(startDate);
		DateTime endDateJoda = new DateTime(endDate);

		String sql = "select sum(waittime) from usestatist ";

		while (startDateJoda.getMillis() <= endDateJoda.getMillis()) {
			int res1 = jdbcTemplate
					.queryForInt(sql + " where baseos = 'iOS' and time >= \"" + startDateJoda.toString("yyyy-MM-01")
							+ "\" and time < \"" + startDateJoda.plusMonths(1).toString("yyyy-MM-01") + "\"");
			dataList.add(res1);
			startDateJoda = startDateJoda.plusMonths(1);
		}

		return dataList;
	}

	/**
	 * app统计android更新量折线图数据，表scheappgx
	 */
	public List<Integer> apptjandroidscList(Date startDate, Date endDate) {
		List<Integer> dataList = new ArrayList<Integer>();
		DateTime startDateJoda = new DateTime(startDate);
		DateTime endDateJoda = new DateTime(endDate);

		String sql = "select sum(waittime) from usestatist ";

		while (startDateJoda.getMillis() <= endDateJoda.getMillis()) {
			int res1 = jdbcTemplate
					.queryForInt(sql + " where baseos = 'Android' and time >= \"" + startDateJoda.toString("yyyy-MM-01")
							+ "\" and time < \"" + startDateJoda.plusMonths(1).toString("yyyy-MM-01") + "\"");
			dataList.add(res1);
			startDateJoda = startDateJoda.plusMonths(1);
		}

		return dataList;
	}

	/**
	 * APP统计更新量的表格
	 */
	@Override
	public List<ApptjPo> sctableData(Date startDate, Date endDate) {
		List<ApptjPo> reslist = new ArrayList<ApptjPo>();
		DateTime startDateJoda = new DateTime(startDate);
		DateTime endDateJoda = new DateTime(endDate);

		String sql = "select sum(waittime) from usestatist ";

		while (startDateJoda.getMillis() <= endDateJoda.getMillis()) {
			ApptjPo apptjPo = new ApptjPo();
			apptjPo.setMonthdate(startDateJoda.toString("yyyy-MM"));
			int res1 = jdbcTemplate
					.queryForInt(sql + " where baseos = 'iOS' and time >= \"" + startDateJoda.toString("yyyy-MM-01")
							+ "\" and time < \"" + startDateJoda.plusMonths(1).toString("yyyy-MM-01") + "\"");
			int res2 = jdbcTemplate
					.queryForInt(sql + " where baseos = 'Android' and time >= \"" + startDateJoda.toString("yyyy-MM-01")
							+ "\" and time < \"" + startDateJoda.plusMonths(1).toString("yyyy-MM-01") + "\"");
			startDateJoda = startDateJoda.plusMonths(1);
			apptjPo.setIosnum(res1);
			apptjPo.setAndroidnum(res2);
			reslist.add(apptjPo);
		}
		return reslist;
	}

	/**
	 * 日访问量分布的list数据按天的统计
	 * 
	 * @return
	 */
	@Override
	public EasyUiDataGridResult rfwlfbList(Integer page, Integer rows, Date startDate, Date endDate) {
		List<RfwlfbPo> list = new ArrayList<RfwlfbPo>();

		DateTime sdt = new DateTime(startDate);
		DateTime edt = new DateTime(endDate);

		int totalRow = Days.daysBetween(sdt, edt).getDays() + 1;

		int start = (page - 1) * rows;
		int limit = rows;

		int end = 0;
		if ((start + limit) > totalRow) {
			end = totalRow;
		} else {
			end = start + limit;
		}

		for (int i = start; i < end; i++) {
			RfwlfbPo xpo = new RfwlfbPo();
			xpo.setTime(edt.minusDays(i).toString("yyyy-MM-dd"));
			String sql1 = "select count(1) from scheappstart where time = '" + edt.minusDays(i).toString("yyyy-MM-dd")
					+ "'"; // 启动次数
			int res1 = jdbcTemplate.queryForInt(sql1);
			if (res1 == 0) {
				xpo.setQdcs(0);
			} else {
				String sql2 = "select num from scheappstart where time = '" + edt.minusDays(i).toString("yyyy-MM-dd")
						+ "'"; // 启动次数
				int res2 = jdbcTemplate.queryForInt(sql2);
				xpo.setQdcs(res2);
			}

			String sql3 = "select count(1) from scheappstartqc where time = '" + edt.minusDays(i).toString("yyyy-MM-dd")
					+ "'"; // 用户数量
			int res3 = jdbcTemplate.queryForInt(sql3);
			if (res3 == 0) {
				xpo.setYhsl(0);
			} else {
				String sql4 = "select num from scheappstartqc where time = '" + edt.minusDays(i).toString("yyyy-MM-dd")
						+ "'"; // 用户数量
				int res4 = jdbcTemplate.queryForInt(sql4);
				xpo.setYhsl(res4);
			}

			list.add(xpo);
		}

		EasyUiDataGridResult result = new EasyUiDataGridResult(totalRow, list);
		return result;
	}

	/**
	 * 日访问量导出每日的
	 */
	@Override
	public List<RfwlfbPo> rfwlExport1(Date startDate, Date endDate) {
		List<RfwlfbPo> list = new ArrayList<RfwlfbPo>();

		DateTime sdt = new DateTime(startDate);
		DateTime edt = new DateTime(endDate);

		int totalRow = Days.daysBetween(sdt, edt).getDays() + 1;

		for (int i = 0; i < totalRow; i++) {
			RfwlfbPo xpo = new RfwlfbPo();
			xpo.setTime(edt.minusDays(i).toString("yyyy-MM-dd"));
			String sql1 = "select count(1) from scheappstart where time = '" + edt.minusDays(i).toString("yyyy-MM-dd")
					+ "'"; // 启动次数
			int res1 = jdbcTemplate.queryForInt(sql1);
			if (res1 == 0) {
				xpo.setQdcs(0);
			} else {
				String sql2 = "select num from scheappstart where time = '" + edt.minusDays(i).toString("yyyy-MM-dd")
						+ "'"; // 启动次数
				int res2 = jdbcTemplate.queryForInt(sql2);
				xpo.setQdcs(res2);
			}

			String sql3 = "select count(1) from scheappstartqc where time = '" + edt.minusDays(i).toString("yyyy-MM-dd")
					+ "'"; // 用户数量
			int res3 = jdbcTemplate.queryForInt(sql3);
			if (res3 == 0) {
				xpo.setYhsl(0);
			} else {
				String sql4 = "select num from scheappstartqc where time = '" + edt.minusDays(i).toString("yyyy-MM-dd")
						+ "'"; // 用户数量
				int res4 = jdbcTemplate.queryForInt(sql4);
				xpo.setYhsl(res4);
			}

			list.add(xpo);
		}

		return list;
	}

	/**
	 * 用户分析启动次数趋势按月统计 不去重的，启动次数
	 */
	@Override
	public List<Integer> monQdcsqs(Date startDate, Date endDate) {
		DateTime dstartDate = new DateTime(startDate);
		DateTime dendDate = new DateTime(endDate);
		int months = Months.monthsBetween(dstartDate, dendDate).getMonths();
		List<Integer> flowList = new ArrayList<Integer>();

		if (months <= 55) {
			String sql = "select sum(num) from scheappstart where time >= ";
			for (int i = 0; i < months + 1; i++) {
				int res1 = jdbcTemplate.queryForInt(sql + "\"" + dstartDate.toString("yyyy-MM-01") + "\" and time < \""
						+ dstartDate.plusMonths(1).toString("yyyy-MM-01") + "\"");
				flowList.add(res1);
				dstartDate = dstartDate.plusMonths(1);
			}
		} else {
			String sql2 = "select sum(num) from scheappstart ";
			int base = ((Double) Math.ceil(months / 55.0)).intValue();
			for (int i = 0; i < (months / base) + 1; i++) {
				int res2 = jdbcTemplate.queryForInt(sql2 + "where time >= \"" + dstartDate.toString("yyyy-MM-01")
						+ "\" and time < \"" + dstartDate.plusMonths(base).toString("yyyy-MM-01") + "\"");
				flowList.add(res2);
				dstartDate = dstartDate.plusMonths(base);
			}
		}

		return flowList;
	}

	/**
	 * 日访问量用户数量趋势按月统计的 去重的
	 */
	@Override
	public List<Integer> monyhslqs(Date startDate, Date endDate) {
		DateTime dstartDate = new DateTime(startDate);
		DateTime dendDate = new DateTime(endDate);
		int months = Months.monthsBetween(dstartDate, dendDate).getMonths();
		List<Integer> flowList = new ArrayList<Integer>();

		if (months <= 55) {
			String sql = "select sum(num) from scheappstartqc where time >= ";
			for (int i = 0; i < months + 1; i++) {
				int res1 = jdbcTemplate.queryForInt(sql + "\"" + dstartDate.toString("yyyy-MM-01") + "\" and time < \""
						+ dstartDate.plusMonths(1).toString("yyyy-MM-01") + "\"");
				flowList.add(res1);
				dstartDate = dstartDate.plusMonths(1);
			}
		} else {
			String sql2 = "select sum(num) from scheappstartqc ";
			int base = ((Double) Math.ceil(months / 55.0)).intValue();
			for (int i = 0; i < (months / base) + 1; i++) {
				int res2 = jdbcTemplate.queryForInt(sql2 + "where time >= \"" + dstartDate.toString("yyyy-MM-01")
						+ "\" and time < \"" + dstartDate.plusMonths(base).toString("yyyy-MM-01") + "\"");
				flowList.add(res2);
				dstartDate = dstartDate.plusMonths(base);
			}
		}

		return flowList;
	}

	/**
	 * 日访问量分布的list数据按月统计的
	 * 
	 * @return
	 */
	@Override
	public EasyUiDataGridResult monRfwlfbList(Integer page, Integer rows, Date startDate, Date endDate) {
		List<RfwlfbPo> list = new ArrayList<RfwlfbPo>();

		DateTime sdt = new DateTime(startDate);
		DateTime edt = new DateTime(endDate);

		int totalRow = Months.monthsBetween(sdt, edt).getMonths() + 1;

		int start = (page - 1) * rows;
		int limit = rows;

		int end = 0;
		if ((start + limit) > totalRow) {
			end = totalRow;
		} else {
			end = start + limit;
		}

		for (int i = start; i < end; i++) {
			RfwlfbPo xpo = new RfwlfbPo();
			xpo.setTime(edt.minusMonths(i).toString("yyyy-MM"));

			String sql2 = "select sum(num) from scheappstart where time >= '"
					+ edt.minusMonths(i).toString("yyyy-MM-01") + "' and time < '"
					+ edt.minusMonths(i).plusMonths(1).toString("yyyy-MM-01") + "'"; // 启动次数
			int res2 = jdbcTemplate.queryForInt(sql2);
			xpo.setQdcs(res2);

			String sql3 = "select sum(num) from scheappstartqc where time >= '"
					+ edt.minusMonths(i).toString("yyyy-MM-01") + "' and time < '"
					+ edt.minusMonths(i).plusMonths(1).toString("yyyy-MM-01") + "'"; // 用户数量
			// 用户数量
			int res3 = jdbcTemplate.queryForInt(sql3);
			xpo.setYhsl(res3);

			list.add(xpo);
		}

		EasyUiDataGridResult result = new EasyUiDataGridResult(totalRow, list);
		return result;
	}

	/**
	 * 日访问量导出每月的
	 */
	@Override
	public List<RfwlfbPo> monRfwlExport(Date startDate, Date endDate) {
		List<RfwlfbPo> list = new ArrayList<RfwlfbPo>();

		DateTime sdt = new DateTime(startDate);
		DateTime edt = new DateTime(endDate);

		int totalRow = Months.monthsBetween(sdt, edt).getMonths() + 1;

		for (int i = 0; i < totalRow; i++) {
			RfwlfbPo xpo = new RfwlfbPo();
			xpo.setTime(edt.minusMonths(i).toString("yyyy-MM"));

			String sql2 = "select sum(num) from scheappstart where time >= '"
					+ edt.minusMonths(i).toString("yyyy-MM-01") + "' and time < '"
					+ edt.minusMonths(i).plusMonths(1).toString("yyyy-MM-01") + "'"; // 启动次数
			int res2 = jdbcTemplate.queryForInt(sql2);
			xpo.setQdcs(res2);

			String sql3 = "select sum(num) from scheappstartqc where time >= '"
					+ edt.minusMonths(i).toString("yyyy-MM-01") + "' and time < '"
					+ edt.minusMonths(i).plusMonths(1).toString("yyyy-MM-01") + "'"; // 用户数量
			// 用户数量
			int res3 = jdbcTemplate.queryForInt(sql3);
			xpo.setYhsl(res3);

			list.add(xpo);
		}

		return list;
	}

	/**
	 * 用户分析启动次数趋势按年统计 不去重的，启动次数
	 */
	@Override
	public List<Integer> nQdcsqs(Date startDate, Date endDate) {
		DateTime dstartDate = new DateTime(startDate);
		DateTime dendDate = new DateTime(endDate);
		int years = Years.yearsBetween(dstartDate, dendDate).getYears();
		List<Integer> flowList = new ArrayList<Integer>();

		String sql = "select sum(num) from scheappstart where time >= ";
		for (int i = 0; i < years + 1; i++) {
			int res1 = jdbcTemplate.queryForInt(sql + "\"" + dstartDate.toString("yyyy-01-01") + "\" and time < \""
					+ dstartDate.plusYears(1).toString("yyyy-01-01") + "\"");
			flowList.add(res1);
			dstartDate = dstartDate.plusYears(1);
		}

		return flowList;
	}

	/**
	 * 日访问量用户数量趋势按年统计的 去重的
	 */
	@Override
	public List<Integer> nyhslqs(Date startDate, Date endDate) {
		DateTime dstartDate = new DateTime(startDate);
		DateTime dendDate = new DateTime(endDate);
		int years = Years.yearsBetween(dstartDate, dendDate).getYears();
		List<Integer> flowList = new ArrayList<Integer>();

		String sql = "select sum(num) from scheappstartqc where time >= ";
		for (int i = 0; i < years + 1; i++) {
			int res1 = jdbcTemplate.queryForInt(sql + "\"" + dstartDate.toString("yyyy-01-01") + "\" and time < \""
					+ dstartDate.plusYears(1).toString("yyyy-01-01") + "\"");
			flowList.add(res1);
			dstartDate = dstartDate.plusYears(1);
		}

		return flowList;
	}

	/**
	 * 日访问量分布的list数据按年统计的
	 * 
	 * @return
	 */
	@Override
	public EasyUiDataGridResult nRfwlfbList(Integer page, Integer rows, Date startDate, Date endDate) {
		List<RfwlfbPo> list = new ArrayList<RfwlfbPo>();

		DateTime sdt = new DateTime(startDate);
		DateTime edt = new DateTime(endDate);

		int totalRow = Years.yearsBetween(sdt, edt).getYears() + 1;

		int start = (page - 1) * rows;
		int limit = rows;

		int end = 0;
		if ((start + limit) > totalRow) {
			end = totalRow;
		} else {
			end = start + limit;
		}

		for (int i = start; i < end; i++) {
			RfwlfbPo xpo = new RfwlfbPo();
			xpo.setTime(edt.minusYears(i).toString("yyyy"));

			String sql2 = "select sum(num) from scheappstart where time >= '" + edt.minusYears(i).toString("yyyy-01-01")
					+ "' and time < '" + edt.minusYears(i).plusYears(1).toString("yyyy-01-01") + "'"; // 启动次数
			int res2 = jdbcTemplate.queryForInt(sql2);
			xpo.setQdcs(res2);

			String sql3 = "select sum(num) from scheappstartqc where time >= '"
					+ edt.minusYears(i).toString("yyyy-01-01") + "' and time < '"
					+ edt.minusYears(i).plusYears(1).toString("yyyy-01-01") + "'"; // 用户数量
			int res3 = jdbcTemplate.queryForInt(sql3);
			xpo.setYhsl(res3);

			list.add(xpo);
		}

		EasyUiDataGridResult result = new EasyUiDataGridResult(totalRow, list);
		return result;
	}

	/**
	 * 日访问量导出每年的
	 */
	@Override
	public List<RfwlfbPo> nRfwlExport(Date startDate, Date endDate) {
		List<RfwlfbPo> list = new ArrayList<RfwlfbPo>();

		DateTime sdt = new DateTime(startDate);
		DateTime edt = new DateTime(endDate);

		int totalRow = Years.yearsBetween(sdt, edt).getYears() + 1;

		for (int i = 0; i < totalRow; i++) {
			RfwlfbPo xpo = new RfwlfbPo();
			xpo.setTime(edt.minusYears(i).toString("yyyy"));

			String sql2 = "select sum(num) from scheappstart where time >= '" + edt.minusYears(i).toString("yyyy-01-01")
					+ "' and time < '" + edt.minusYears(i).plusYears(1).toString("yyyy-01-01") + "'"; // 启动次数
			int res2 = jdbcTemplate.queryForInt(sql2);
			xpo.setQdcs(res2);

			String sql3 = "select sum(num) from scheappstartqc where time >= '"
					+ edt.minusYears(i).toString("yyyy-01-01") + "' and time < '"
					+ edt.minusYears(i).plusYears(1).toString("yyyy-01-01") + "'"; // 用户数量
			int res3 = jdbcTemplate.queryForInt(sql3);
			xpo.setYhsl(res3);

			list.add(xpo);
		}

		return list;
	}

	/**
	 * 用户分析启动次数趋势按周统计 不去重的，启动次数
	 */
	@Override
	public List<Integer> zQdcsqs(Date startDate, Date endDate) {
		DateTime ds = new DateTime(startDate);
		DateTime de = new DateTime(endDate);
		List<Integer> flowList = new ArrayList<Integer>();
		
		int minus = ds.getDayOfWeek() - 1;
		DateTime m = ds.minusDays(minus); // 得到刚刚好的本周的周一
		int p = de.getDayOfWeek();
		DateTime e = de.plusDays(7 - p);
		int weeks = Weeks.weeksBetween(m, e).getWeeks();

		if (weeks <= 55) {
			String sql = "select sum(num) from scheappstart where time >= ";
			for (int i = 0; i < weeks + 1; i++) {
				int res1 = jdbcTemplate.queryForInt(sql + "\"" + m.toString("yyyy-MM-dd") + "\" and time < \"" + m.plusWeeks(1).toString("yyyy-MM-dd") + "\"");
				flowList.add(res1);
				m = m.plusWeeks(1);
			}
		} else {
			String sql = "select sum(num) from scheappstart where time >= ";
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
	 * 日访问量用户数量趋势按周统计的 去重的
	 */
	@Override
	public List<Integer> zyhslqs(Date startDate, Date endDate) {
		DateTime ds = new DateTime(startDate);
		DateTime de = new DateTime(endDate);
		List<Integer> flowList = new ArrayList<Integer>();

		int minus = ds.getDayOfWeek() - 1;
		DateTime m = ds.minusDays(minus); // 得到刚刚好的本周的周一
		int p = de.getDayOfWeek();
		DateTime e = de.plusDays(7 - p);
		int weeks = Weeks.weeksBetween(m, e).getWeeks();
		
		if (weeks <= 55) {
			String sql = "select sum(num) from scheappstartqc where time >= ";
			for (int i = 0; i < weeks + 1; i++) {
				int res1 = jdbcTemplate.queryForInt(sql + "\"" + m.toString("yyyy-MM-dd") + "\" and time < \"" + m.plusWeeks(1).toString("yyyy-MM-dd") + "\"");
				flowList.add(res1);
				m = m.plusWeeks(1);
			}
		} else {
			String sql = "select sum(num) from scheappstartqc where time >= ";
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
	 * 日访问量分布的list数据按周统计的
	 * 
	 * @return
	 */
	@Override
	public EasyUiDataGridResult zRfwlfbList(Integer page, Integer rows, Date startDate, Date endDate) {
		List<RfwlfbPo> list = new ArrayList<RfwlfbPo>();

		DateTime ds = new DateTime(startDate);
		DateTime de = new DateTime(endDate);

		int weeks = Weeks.weeksBetween(ds, de).getWeeks();

		int minus = ds.getDayOfWeek() - 1;
		DateTime m = ds.minusDays(minus); // 得到刚刚好的本周的周一

		int totalRow = weeks + 1;

		int start = (page - 1) * rows;
		int limit = rows;

		int end = 0;
		if ((start + limit) > totalRow) {
			end = totalRow;
		} else {
			end = start + limit;
		}

		for (int i = start; i < end; i++) {
			RfwlfbPo xpo = new RfwlfbPo();
			String str = "第" + m.plusWeeks(i).getWeekOfWeekyear() + "周  " + m.plusWeeks(i).toString("yyyy-MM-dd")
					+ " -- " + m.plusWeeks(i).plusDays(6).toString("yyyy-MM-dd");
			xpo.setTime(str);

			String sql2 = "select sum(num) from scheappstart where time >= '" + m.plusWeeks(i).toString("yyyy-MM-dd")
					+ "' and time < '" + m.plusWeeks(i + 1).toString("yyyy-MM-dd") + "'"; // 启动次数
			int res2 = jdbcTemplate.queryForInt(sql2);
			xpo.setQdcs(res2);

			String sql3 = "select sum(num) from scheappstartqc where time >= '" + m.plusWeeks(i).toString("yyyy-MM-dd")
					+ "' and time < '" + m.plusWeeks(i + 1).toString("yyyy-MM-dd") + "'"; // 用户数量

			int res3 = jdbcTemplate.queryForInt(sql3);
			xpo.setYhsl(res3);

			list.add(xpo);
		}

		EasyUiDataGridResult result = new EasyUiDataGridResult(totalRow, list);
		return result;
	}

	/**
	 * 日访问量导出每周的
	 */
	@Override
	public List<RfwlfbPo> zRfwlExport(Date startDate, Date endDate) {
		List<RfwlfbPo> list = new ArrayList<RfwlfbPo>();

		DateTime ds = new DateTime(startDate);
		DateTime de = new DateTime(endDate);

		int weeks = Weeks.weeksBetween(ds, de).getWeeks();

		int minus = ds.getDayOfWeek() - 1;
		DateTime m = ds.minusDays(minus); // 得到刚刚好的本周的周一

		int totalRow = weeks + 1;

		for (int i = 0; i < totalRow; i++) {
			RfwlfbPo xpo = new RfwlfbPo();
			String str = "第" + m.plusWeeks(i).getWeekOfWeekyear() + "周  " + m.plusWeeks(i).toString("yyyy-MM-dd")
					+ " -- " + m.plusWeeks(i).plusDays(6).toString("yyyy-MM-dd");
			xpo.setTime(str);

			String sql2 = "select sum(num) from scheappstart where time >= '" + m.plusWeeks(i).toString("yyyy-MM-dd")
					+ "' and time < '" + m.plusWeeks(i + 1).toString("yyyy-MM-dd") + "'"; // 启动次数
			int res2 = jdbcTemplate.queryForInt(sql2);
			xpo.setQdcs(res2);

			String sql3 = "select sum(num) from scheappstartqc where time >= '" + m.plusWeeks(i).toString("yyyy-MM-dd")
					+ "' and time < '" + m.plusWeeks(i + 1).toString("yyyy-MM-dd") + "'"; // 用户数量

			int res3 = jdbcTemplate.queryForInt(sql3);
			xpo.setYhsl(res3);

			list.add(xpo);
		}

		return list;
	}

	// ====================新增用户

	/**
	 * 新增用户中的list数据 按天统计
	 *  update by JJJ 20170310
	 * @return
	 */
	@Override
	public EasyUiDataGridResult rxzyhList(Integer page, Integer rows, Date startDate, Date endDate) {
		List<NewInstallUserDetail> list = new ArrayList<NewInstallUserDetail>();

		String sql = "select sum(count) from schenewuser ";
		double allsum = jdbcTemplate.queryForInt(sql) + 0.0; // 总数
		NumberFormat format = NumberFormat.getPercentInstance();// 获取格式化类实例
		format.setMinimumFractionDigits(2);// 设置小数位

		DateTime sdt = new DateTime(startDate);
		DateTime edt = new DateTime(endDate);

		int totalRow = Days.daysBetween(sdt, edt).getDays() + 1;

		int start = (page - 1) * rows;
		int limit = rows;

		int end = 0;
		if ((start + limit) > totalRow) {
			end = totalRow;
		} else {
			end = start + limit;
		}

		for (int i = start; i < end; i++) {
			NewInstallUserDetail userdetail = new NewInstallUserDetail();
			userdetail.setPeriod(edt.minusDays(i).toString("yyyy-MM-dd"));

			String sql0 = "select sum(count) from schenewuser where time = '" + edt.minusDays(i).toString("yyyy-MM-dd")
					+ "'";
			int res0 = jdbcTemplate.queryForInt(sql0);
			if (0 == allsum) {
				userdetail.setRatio(res0 + " (0.00%)");
			} else {
				userdetail.setRatio(res0 + " (" + format.format(res0 / allsum) + ")");
			}

			list.add(userdetail);
		}

		EasyUiDataGridResult result = new EasyUiDataGridResult(totalRow, list);
		return result;
	}

	/**
	 * 新增用户导出每天的统计
	 */
	@Override
	public List<NewInstallUserDetail> rxzyhExport(Date startDate, Date endDate) {
		List<NewInstallUserDetail> list = new ArrayList<NewInstallUserDetail>();

		String sql = "select sum(count) from schenewuser ";
		double allsum = jdbcTemplate.queryForInt(sql) + 0.0; // 总数
		NumberFormat format = NumberFormat.getPercentInstance();// 获取格式化类实例
		format.setMinimumFractionDigits(2);// 设置小数位

		DateTime sdt = new DateTime(startDate);
		DateTime edt = new DateTime(endDate);
		int totalRow = Days.daysBetween(sdt, edt).getDays() + 1;

		for (int i = 0; i < totalRow; i++) {
			NewInstallUserDetail userdetail = new NewInstallUserDetail();
			userdetail.setPeriod(edt.minusDays(i).toString("yyyy-MM-dd"));

			String sql0 = "select sum(count) from schenewuser where time = '" + edt.minusDays(i).toString("yyyy-MM-dd")
					+ "'";
			int res0 = jdbcTemplate.queryForInt(sql0);
			if (0 == allsum) {
				userdetail.setRatio(res0 + " (0.00%)");
			} else {
				userdetail.setRatio(res0 + " (" + format.format(res0 / allsum) + ")");
			}

			list.add(userdetail);
		}

		return list;
	}

	/**
	 * 用户分析新增用户趋势 按月统计
	 */
	@Override
	public List<Integer> monxzyhqs(Date startDate, Date endDate) {
		DateTime dstartDate = new DateTime(startDate);
		DateTime dendDate = new DateTime(endDate);
		int months = Months.monthsBetween(dstartDate, dendDate).getMonths();
		List<Integer> flowList = new ArrayList<Integer>();

		if (months <= 55) {
			String sql = "select sum(count) from schenewuser where time >= ";
			for (int i = 0; i < months + 1; i++) {
				int res1 = jdbcTemplate.queryForInt(sql + "\"" + dstartDate.toString("yyyy-MM-01") + "\" and time < \""
						+ dstartDate.plusMonths(1).toString("yyyy-MM-01") + "\"");
				flowList.add(res1);
				dstartDate = dstartDate.plusMonths(1);
			}
		} else {
			String sql2 = "select sum(count) from schenewuser ";
			int base = ((Double) Math.ceil(months / 55.0)).intValue();
			for (int i = 0; i < (months / base) + 1; i++) {
				int res2 = jdbcTemplate.queryForInt(sql2 + "where time >= \"" + dstartDate.toString("yyyy-MM-01")
						+ "\" and time < \"" + dstartDate.plusMonths(base).toString("yyyy-MM-01") + "\"");
				flowList.add(res2);
				dstartDate = dstartDate.plusMonths(base);
			}
		}

		return flowList;
	}

	/**
	 * 新增用户中的list数据 按月统计
	 * 
	 * @return
	 */
	@Override
	public EasyUiDataGridResult monrxzyhList(Integer page, Integer rows, Date startDate, Date endDate) {
		List<NewInstallUserDetail> list = new ArrayList<NewInstallUserDetail>();

		String sql = "select sum(count) from schenewuser ";
		double allsum = jdbcTemplate.queryForInt(sql) + 0.0; // 总数
		NumberFormat format = NumberFormat.getPercentInstance();// 获取格式化类实例
		format.setMinimumFractionDigits(2);// 设置小数位

		DateTime sdt = new DateTime(startDate);
		DateTime edt = new DateTime(endDate);

		int totalRow = Months.monthsBetween(sdt, edt).getMonths() + 1;

		int start = (page - 1) * rows;
		int limit = rows;

		int end = 0;
		if ((start + limit) > totalRow) {
			end = totalRow;
		} else {
			end = start + limit;
		}

		for (int i = start; i < end; i++) {
			NewInstallUserDetail userdetail = new NewInstallUserDetail();
			userdetail.setPeriod(edt.minusMonths(i).toString("yyyy-MM"));

			String sql0 = "select sum(count) from schenewuser where time >= '"
					+ edt.minusMonths(i).toString("yyyy-MM-01") + "' and time < '"
					+ edt.minusMonths(i).plusMonths(1).toString("yyyy-MM-01") + "'";
			int res0 = jdbcTemplate.queryForInt(sql0);
			if (0 == allsum) {
				userdetail.setRatio(res0 + " (0.00%)");
			} else {
				userdetail.setRatio(res0 + " (" + format.format(res0 / allsum) + ")");
			}

			list.add(userdetail);
		}

		EasyUiDataGridResult result = new EasyUiDataGridResult(totalRow, list);
		return result;
	}

	/**
	 * 新增用户导出每月的统计
	 */
	@Override
	public List<NewInstallUserDetail> monrxzyhExport(Date startDate, Date endDate) {
		List<NewInstallUserDetail> list = new ArrayList<NewInstallUserDetail>();

		String sql = "select sum(count) from schenewuser ";
		double allsum = jdbcTemplate.queryForInt(sql) + 0.0; // 总数
		NumberFormat format = NumberFormat.getPercentInstance();// 获取格式化类实例
		format.setMinimumFractionDigits(2);// 设置小数位

		DateTime sdt = new DateTime(startDate);
		DateTime edt = new DateTime(endDate);
		int totalRow = Months.monthsBetween(sdt, edt).getMonths() + 1;

		for (int i = 0; i < totalRow; i++) {
			NewInstallUserDetail userdetail = new NewInstallUserDetail();
			userdetail.setPeriod(edt.minusMonths(i).toString("yyyy-MM"));

			String sql0 = "select sum(count) from schenewuser where time >= '"
					+ edt.minusMonths(i).toString("yyyy-MM-01") + "' and time < '"
					+ edt.minusMonths(i).plusMonths(1).toString("yyyy-MM-01") + "'"; // 启动次数
			int res0 = jdbcTemplate.queryForInt(sql0);
			if (0 == allsum) {
				userdetail.setRatio(res0 + " (0.00%)");
			} else {
				userdetail.setRatio(res0 + " (" + format.format(res0 / allsum) + ")");
			}

			list.add(userdetail);
		}

		return list;
	}

	/**
	 * 第三方分享table数据 add by JJJ
	 */
	@SuppressWarnings("deprecation")
	@Override
	public EasyUiDataGridResult sffxtableList(Integer page, Integer rows, Date startDate, Date endDate, String type,
			boolean getAll) {
		DateTime sdt = new DateTime(startDate);
		DateTime edt = new DateTime(endDate);
		List<String> timeList = new ArrayList<String>();
		Map<String, List<DyfxPo>> map = new HashMap<String, List<DyfxPo>>();
		int totalRow = 0;
		if (type.equals("year")) {
			totalRow = Years.yearsBetween(sdt, edt).getYears() + 1;
		} else if (type.equals("month")) {
			totalRow = Months.monthsBetween(sdt, edt).getMonths() + 1;
		} else if (type.equals("day")) {
			totalRow = Days.daysBetween(sdt, edt).getDays() + 1;
		} else if (type.equals("week")) {
			int minus = sdt.getDayOfWeek() - 1;
			sdt = sdt.minusDays(minus); // 得到刚刚好的本周的周一
			int p = edt.getDayOfWeek();
			edt = edt.plusDays(7 - p);
			int weeks = Weeks.weeksBetween(sdt, edt).getWeeks();
			totalRow = weeks + 1;
		} else {
			return null;
		}

		int start = 0, end = 0;
		if (getAll) {
			end = totalRow;
		} else {
			start = (page - 1) * rows;
			int limit = rows;
			if ((start + limit) > totalRow) {
				end = totalRow;
			} else {
				end = start + limit;
			}
		}
		for (int i = start; i < end; i++) {
			if (type.equals("year")) {
				String sqly = "select count(*) value,platform name from shareinfo where time>='"
						+ sdt.plusYears(i).toString("yyyy-MM-dd") + "' and time <'"
						+ sdt.plusYears(i).plusYears(1).toString("yyyy-MM-dd") + "' GROUP BY name";
				List<DyfxPo> list = jdbcTemplate.query(sqly, new BeanPropertyRowMapper<DyfxPo>(DyfxPo.class));
				timeList.add(sdt.plusYears(i).toString("yyyy"));
				map.put(sdt.plusYears(i).toString("yyyy"), list);
			} else if (type.equals("month")) {
				String sqlm = "select count(*) value,platform name from shareinfo where time>='"
						+ sdt.plusMonths(i).toString("yyyy-MM-dd") + "' and time <'"
						+ sdt.plusMonths(i).plusMonths(1).toString("yyyy-MM-dd") + "' GROUP BY name";
				List<DyfxPo> list = jdbcTemplate.query(sqlm, new BeanPropertyRowMapper<DyfxPo>(DyfxPo.class));
				timeList.add(sdt.plusMonths(i).toString("yyyy-MM"));
				map.put(sdt.plusMonths(i).toString("yyyy-MM"), list);
			} else if (type.equals("day")) {
				String sqld = "select count(*) value,platform name from shareinfo where time>='"
						+ sdt.plusDays(i).toString("yyyy-MM-dd") + "' and time <'"
						+ sdt.plusDays(i).plusDays(1).toString("yyyy-MM-dd") + "' GROUP BY name";
				List<DyfxPo> list = jdbcTemplate.query(sqld, new BeanPropertyRowMapper<DyfxPo>(DyfxPo.class));
				timeList.add(sdt.plusDays(i).toString("yyyy-MM-dd"));
				map.put(sdt.plusDays(i).toString("yyyy-MM-dd"), list);
			} else if (type.equals("week")) {
				String str = "第" + sdt.plusWeeks(i).getWeekOfWeekyear() + "周 " + sdt.plusWeeks(i).toString("yyyyMMdd")
						+ "--" + sdt.plusWeeks(i).plusDays(6).toString("yyyyMMdd");
				String sqlw = "select count(*) value,platform name from shareinfo where time>='"
						+ sdt.plusWeeks(i).toString("yyyy-MM-dd") + "' and time <'"
						+ sdt.plusWeeks(i).plusWeeks(1).toString("yyyy-MM-dd") + "' GROUP BY name";
				List<DyfxPo> list = jdbcTemplate.query(sqlw, new BeanPropertyRowMapper<DyfxPo>(DyfxPo.class));
				timeList.add(str);
				map.put(str, list);
			}
		}

		List<SharePo> rest = new ArrayList<SharePo>();
		for (String time : timeList) {
			SharePo sharePo = new SharePo();
			sharePo.setMonth(time);
			/*
			 * shareCount.put("QQ好友", "0"); shareCount.put("QQ空间", "0");
			 * shareCount.put("微信好友", "0"); shareCount.put("微信收藏", "0");
			 * shareCount.put("微信朋友圈", "0"); shareCount.put("新浪微博", "0");
			 */
			List<DyfxPo> list = map.get(time);
			for (DyfxPo df : list) {
				if (df.getName().equals("QQ好友")) {
					sharePo.setQqfriend(df.getValue().toString());
				}
				if (df.getName().equals("QQ空间")) {
					sharePo.setQqzone(df.getValue().toString());
				}
				if (df.getName().equals("微信好友")) {
					sharePo.setWxfriend(df.getValue().toString());
				}
				if (df.getName().equals("微信收藏")) {
					sharePo.setWxfavorite(df.getValue().toString());
				}
				if (df.getName().equals("微信朋友圈")) {
					sharePo.setWxquanzi(df.getValue().toString());
				}
				if (df.getName().equals("新浪微博")) {
					sharePo.setSinaweibo(df.getValue().toString());
				}
			}
			rest.add(sharePo);
		}
		EasyUiDataGridResult result = new EasyUiDataGridResult(totalRow, rest);
		return result;
	}
	// /**
	// *
	// */
	// @SuppressWarnings("deprecation")
	// @Override
	// public EasyUiDataGridResult sffxlb(Integer page, Integer rows, Date
	// startDate, Date endDate) {
	// DateTime startDateJoda = new DateTime(startDate);
	// DateTime endDateJoda = new DateTime(endDate);
	//
	// String countSql = "select count(*) from (SELECT DATE_FORMAT(time,'%Y-%m')
	// mtime FROM shareinfo where
	// time>='"+startDateJoda.toString("yyyy-MM-01")+"' and time
	// <'"+endDateJoda.plusMonths(1).minusDays(1).toString("yyyy-MM-dd")+"'
	// GROUP BY mtime)tab ";
	// int total = jdbcTemplate.queryForInt(countSql);
	//
	// int start = (page - 1) * rows;
	// int limit = rows;
	//
	// String monSql = "SELECT DATE_FORMAT(time,'%Y-%m') mtime FROM shareinfo
	// where time>='"+startDateJoda.toString("yyyy-MM-01")+"' and time
	// <'"+endDateJoda.plusMonths(1).minusDays(1).toString("yyyy-MM-dd")+"'
	// GROUP BY mtime order by mtime asc limit "+ start +" , "+ limit;
	// List<Object> query = jdbcTemplate.query(monSql, new RowMapper<Object>(){
	// @Override
	// public Object mapRow(ResultSet rs, int arg1) throws SQLException {
	// return rs.getObject(1);
	// }
	// });
	// String sql = "";
	// LocalDate startTime = null;
	// List<SharePo> rest = new ArrayList<SharePo>();
	// Map<String, String> shareCount = null;
	// if(query !=null &&query.size()>0){
	// for(Object motlst:query){
	// SharePo sharePo = new SharePo();
	// sharePo.setMonth(motlst.toString());
	// /*shareCount.put("QQ好友", "0");
	// shareCount.put("QQ空间", "0");
	// shareCount.put("微信好友", "0");
	// shareCount.put("微信收藏", "0");
	// shareCount.put("微信朋友圈", "0");
	// shareCount.put("新浪微博", "0");*/
	// startTime = LocalDate.parse(motlst+"-1");
	// sql = "select count(*) value,platform name from shareinfo where
	// time>='"+startTime.toString("yyyy-MM-01")+"' and time
	// <'"+startTime.plusMonths(1).toString("yyyy-MM-dd")+"' GROUP BY name";
	// List<DyfxPo> list = jdbcTemplate.query(sql, new
	// BeanPropertyRowMapper<DyfxPo>(DyfxPo.class));
	// for(DyfxPo df:list){
	// if(df.getName().equals("QQ好友")){
	// sharePo.setQqfriend(df.getValue().toString());
	// }
	// if(df.getName().equals("QQ空间")){
	// sharePo.setQqzone(df.getValue().toString());
	// }
	// if(df.getName().equals("微信好友")){
	// sharePo.setWxfriend(df.getValue().toString());
	// }
	// if(df.getName().equals("微信收藏")){
	// sharePo.setWxfavorite(df.getValue().toString());
	// }
	// if(df.getName().equals("微信朋友圈")){
	// sharePo.setWxquanzi(df.getValue().toString());
	// }
	// if(df.getName().equals("新浪微博")){
	// sharePo.setSinaweibo(df.getValue().toString());
	// }
	// }
	// rest.add(sharePo);
	// }
	// }
	// EasyUiDataGridResult result = new EasyUiDataGridResult(total, rest);
	// return result;
	// }

	@Override
	public List<Integer> sffxDataList(Date startDate, Date endDate) {
		List<Integer> dataList = new ArrayList<Integer>();
		DateTime startDateJoda = new DateTime(startDate);
		DateTime endDateJoda = new DateTime(endDate);

		String sql = "select count(1) from shareinfo ";

		while (startDateJoda.getMillis() <= endDateJoda.getMillis()) {
			int res1 = jdbcTemplate.queryForInt(sql + " where time >= \"" + startDateJoda.toString("yyyy-MM-01")
					+ "\" and time < \"" + startDateJoda.plusMonths(1).toString("yyyy-MM-01") + "\"");
			dataList.add(res1);
			startDateJoda = startDateJoda.plusMonths(1);
		}

		return dataList;
	}

	/**
	 * 第三方分享饼图数据 update by JJJ
	 */
	@Override
	public Map<String, String> sffxsj(Date startDate, Date endDate) {
		Map<String, String> resmap = new HashMap<String, String>();
		DateTime startDateJoda = new DateTime(startDate);
		DateTime endDateJoda = new DateTime(endDate);
		if (startDateJoda.getMillis() > endDateJoda.getMillis()) {
			return resmap;
		}
		String consql = "select count(*) from shareinfo where time>='" + startDateJoda.toString("yyyy-MM-dd")
				+ "' and time <'" + endDateJoda.toString("yyyy-MM-dd") + "'";
		int total = jdbcTemplate.queryForInt(consql);
		resmap.put("sum", total + "");
		double sum = total + 0.0; // 总人数

		NumberFormat format = NumberFormat.getPercentInstance();// 获取格式化类实例
		format.setMinimumFractionDigits(2);// 设置小数位

		String sql = "select count(*) value,platform name from shareinfo where time>='"
				+ startDateJoda.toString("yyyy-MM-dd") + "' and time <'" + endDateJoda.toString("yyyy-MM-dd")
				+ "' GROUP BY name";
		List<DyfxPo> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<DyfxPo>(DyfxPo.class));
		for (DyfxPo df : list) {
			if (df.getName().equals("QQ好友")) {
				if (0 == sum) {
					resmap.put("qqfriend", "0.0");
				} else {
					resmap.put("qqfriend", format.format(df.getValue() / sum));
				}
			}
			if (df.getName().equals("QQ空间")) {
				if (0 == sum) {
					resmap.put("qqzone", "0.0");
				} else {
					resmap.put("qqzone", format.format(df.getValue() / sum));
				}
			}
			if (df.getName().equals("微信好友")) {
				if (0 == sum) {
					resmap.put("wxfriend", "0.0");
				} else {
					resmap.put("wxfriend", format.format(df.getValue() / sum));
				}
			}
			if (df.getName().equals("微信收藏")) {
				if (0 == sum) {
					resmap.put("wxfavorite", "0.0");
				} else {
					resmap.put("wxfavorite", format.format(df.getValue() / sum));
				}
			}
			if (df.getName().equals("微信朋友圈")) {
				if (0 == sum) {
					resmap.put("wxquanzi", "0.0");
				} else {
					resmap.put("wxquanzi", format.format(df.getValue() / sum));
				}
			}
			if (df.getName().equals("新浪微博")) {
				if (0 == sum) {
					resmap.put("sinaweibo", "0.0");
				} else {
					resmap.put("sinaweibo", format.format(df.getValue() / sum));
				}
			}
		}
		return resmap;
	}

	public static void main(String[] args) {
		NumberFormat format = NumberFormat.getPercentInstance();// 获取格式化类实例
		format.setMinimumFractionDigits(2);// 设置小数位
		int total = 6;
		double sum = total + 0.0; // 总人数
	}

	public List<SharePo> sffxExport(Date startDate, Date endDate) {
		DateTime startDateJoda = new DateTime(startDate);
		DateTime endDateJoda = new DateTime(endDate);

		String monSql = "SELECT DATE_FORMAT(time,'%Y-%m') mtime FROM shareinfo where time>='"
				+ startDateJoda.toString("yyyy-MM-01") + "' and time <'"
				+ endDateJoda.plusMonths(1).minusDays(1).toString("yyyy-MM-dd") + "' GROUP BY mtime order by mtime asc";
		List<Object> query = jdbcTemplate.query(monSql, new RowMapper<Object>() {
			@Override
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				return rs.getObject(1);
			}
		});
		String sql = "";
		LocalDate startTime = null;
		List<SharePo> rest = new ArrayList<SharePo>();
		Map<String, String> shareCount = null;
		if (query != null && query.size() > 0) {
			for (Object motlst : query) {
				SharePo sharePo = new SharePo();
				sharePo.setMonth(motlst.toString());
				startTime = LocalDate.parse(motlst + "-1");
				sql = "select count(*) value,platform name from shareinfo where time>='"
						+ startTime.toString("yyyy-MM-01") + "' and time <'"
						+ startTime.plusMonths(1).toString("yyyy-MM-dd") + "' GROUP BY name";
				List<DyfxPo> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<DyfxPo>(DyfxPo.class));
				for (DyfxPo df : list) {
					if (df.getName().equals("QQ好友")) {
						sharePo.setQqfriend(df.getValue().toString());
					}
					if (df.getName().equals("QQ空间")) {
						sharePo.setQqzone(df.getValue().toString());
					}
					if (df.getName().equals("微信好友")) {
						sharePo.setWxfriend(df.getValue().toString());
					}
					if (df.getName().equals("微信收藏")) {
						sharePo.setWxfavorite(df.getValue().toString());
					}
					if (df.getName().equals("微信朋友圈")) {
						sharePo.setWxquanzi(df.getValue().toString());
					}
					if (df.getName().equals("新浪微博")) {
						sharePo.setSinaweibo(df.getValue().toString());
					}
				}
				rest.add(sharePo);
			}
		}
		return rest;
	}

	/**
	 * 用户分析新增用户趋势 按年统计
	 */
	@Override
	public List<Integer> nxzyhqs(Date startDate, Date endDate) {
		DateTime dstartDate = new DateTime(startDate);
		DateTime dendDate = new DateTime(endDate);
		int years = Years.yearsBetween(dstartDate, dendDate).getYears();
		List<Integer> flowList = new ArrayList<Integer>();

		String sql = "select sum(count) from schenewuser where time >= ";
		for (int i = 0; i < years + 1; i++) {
			int res1 = jdbcTemplate.queryForInt(sql + "\"" + dstartDate.toString("yyyy-01-01") + "\" and time < \""
					+ dstartDate.plusYears(1).toString("yyyy-01-01") + "\"");
			flowList.add(res1);
			dstartDate = dstartDate.plusYears(1);
		}

		return flowList;
	}

	/**
	 * 新增用户中的list数据 按年统计
	 * 
	 * @param page
	 * @param rows
	 * @return
	 */
	@Override
	public EasyUiDataGridResult nrxzyhList(Integer page, Integer rows, Date startDate, Date endDate) {
		List<NewInstallUserDetail> list = new ArrayList<NewInstallUserDetail>();

		String sql = "select sum(count) from schenewuser ";
		double allsum = jdbcTemplate.queryForInt(sql) + 0.0; // 总数
		NumberFormat format = NumberFormat.getPercentInstance();// 获取格式化类实例
		format.setMinimumFractionDigits(2);// 设置小数位

		DateTime sdt = new DateTime(startDate);
		DateTime edt = new DateTime(endDate);

		int totalRow = Years.yearsBetween(sdt, edt).getYears() + 1;

		int start = (page - 1) * rows;
		int limit = rows;

		int end = 0;
		if ((start + limit) > totalRow) {
			end = totalRow;
		} else {
			end = start + limit;
		}

		for (int i = start; i < end; i++) {
			NewInstallUserDetail userdetail = new NewInstallUserDetail();
			userdetail.setPeriod(edt.minusYears(i).toString("yyyy"));

			String sql0 = "select sum(count) from schenewuser where time >= '"
					+ edt.minusYears(i).toString("yyyy-01-01") + "' and time < '"
					+ edt.minusYears(i).plusYears(1).toString("yyyy-01-01") + "'"; // 启动次数
			int res0 = jdbcTemplate.queryForInt(sql0);
			if (0 == allsum) {
				userdetail.setRatio(res0 + " (0.00%)");
			} else {
				userdetail.setRatio(res0 + " (" + format.format(res0 / allsum) + ")");
			}

			list.add(userdetail);
		}

		EasyUiDataGridResult result = new EasyUiDataGridResult(totalRow, list);
		return result;
	}

	/**
	 * 新增用户导出每年的统计
	 */
	@Override
	public List<NewInstallUserDetail> nrxzyhExport(Date startDate, Date endDate) {
		List<NewInstallUserDetail> list = new ArrayList<NewInstallUserDetail>();

		String sql = "select sum(count) from schenewuser ";
		double allsum = jdbcTemplate.queryForInt(sql) + 0.0; // 总数
		NumberFormat format = NumberFormat.getPercentInstance();// 获取格式化类实例
		format.setMinimumFractionDigits(2);// 设置小数位

		DateTime sdt = new DateTime(startDate);
		DateTime edt = new DateTime(endDate);
		int totalRow = Years.yearsBetween(sdt, edt).getYears() + 1;

		for (int i = 0; i < totalRow; i++) {
			NewInstallUserDetail userdetail = new NewInstallUserDetail();
			userdetail.setPeriod(edt.minusYears(i).toString("yyyy"));

			String sql0 = "select sum(count) from schenewuser where time >= '"
					+ edt.minusYears(i).toString("yyyy-01-01") + "' and time < '"
					+ edt.minusYears(i).plusYears(1).toString("yyyy-01-01") + "'";
			int res0 = jdbcTemplate.queryForInt(sql0);
			if (0 == allsum) {
				userdetail.setRatio(res0 + " (0.00%)");
			} else {
				userdetail.setRatio(res0 + " (" + format.format(res0 / allsum) + ")");
			}

			list.add(userdetail);
		}

		return list;
	}

	/**
	 * 用户分析新增用户趋势 按周统计
	 */
	@Override
	public List<Integer> zxzyhqs(Date startDate, Date endDate) {
		DateTime ds = new DateTime(startDate);
		DateTime de = new DateTime(endDate);
		List<Integer> flowList = new ArrayList<Integer>();

		int weeks = Weeks.weeksBetween(ds, de).getWeeks();

		int minus = ds.getDayOfWeek() - 1;
		DateTime m = ds.minusDays(minus); // 得到刚刚好的本周的周一

		String sql = "select sum(count) from schenewuser where time >= ";
		for (int i = 0; i < weeks + 1; i++) {
			int res1 = jdbcTemplate.queryForInt(sql + "\"" + m.toString("yyyy-MM-dd") + "\" and time < \""
					+ m.plusWeeks(1).toString("yyyy-MM-dd") + "\"");
			flowList.add(res1);
			m = m.plusWeeks(1);
		}

		return flowList;
	}

	/**
	 * 新增用户中的list数据 按周统计
	 * 
	 * @param page
	 * @param rows
	 * @return
	 */
	@Override
	public EasyUiDataGridResult zxzyhList(Integer page, Integer rows, Date startDate, Date endDate) {
		List<NewInstallUserDetail> list = new ArrayList<NewInstallUserDetail>();

		String sql = "select sum(count) from schenewuser ";
		double allsum = jdbcTemplate.queryForInt(sql) + 0.0; // 总数
		NumberFormat format = NumberFormat.getPercentInstance();// 获取格式化类实例
		format.setMinimumFractionDigits(2);// 设置小数位

		DateTime ds = new DateTime(startDate);
		DateTime de = new DateTime(endDate);

		int weeks = Weeks.weeksBetween(ds, de).getWeeks();

		int minus = ds.getDayOfWeek() - 1;
		DateTime m = ds.minusDays(minus); // 得到刚刚好的本周的周一

		int totalRow = weeks + 1;

		int start = (page - 1) * rows;
		int limit = rows;

		int end = 0;
		if ((start + limit) > totalRow) {
			end = totalRow;
		} else {
			end = start + limit;
		}

		for (int i = start; i < end; i++) {
			NewInstallUserDetail userdetail = new NewInstallUserDetail();
			String str = "第" + m.plusWeeks(i).getWeekOfWeekyear() + "周  " + m.plusWeeks(i).toString("yyyy-MM-dd")
					+ " -- " + m.plusWeeks(i).plusDays(6).toString("yyyy-MM-dd");
			userdetail.setPeriod(str);

			String sql0 = "select sum(count) from schenewuser where time >= '" + m.plusWeeks(i).toString("yyyy-MM-dd")
					+ "' and time < '" + m.plusWeeks(i + 1).toString("yyyy-MM-dd") + "'";
			int res0 = jdbcTemplate.queryForInt(sql0);
			if (0 == allsum) {
				userdetail.setRatio(res0 + " (0.00%)");
			} else {
				userdetail.setRatio(res0 + " (" + format.format(res0 / allsum) + ")");
			}

			list.add(userdetail);
		}

		EasyUiDataGridResult result = new EasyUiDataGridResult(totalRow, list);
		return result;
	}

	/**
	 * 新增用户导出每周的统计
	 */
	@Override
	public List<NewInstallUserDetail> zxzyhExport(Date startDate, Date endDate) {
		List<NewInstallUserDetail> list = new ArrayList<NewInstallUserDetail>();

		String sql = "select sum(count) from schenewuser ";
		double allsum = jdbcTemplate.queryForInt(sql) + 0.0; // 总数
		NumberFormat format = NumberFormat.getPercentInstance();// 获取格式化类实例
		format.setMinimumFractionDigits(2);// 设置小数位

		DateTime ds = new DateTime(startDate);
		DateTime de = new DateTime(endDate);

		int weeks = Weeks.weeksBetween(ds, de).getWeeks();

		int minus = ds.getDayOfWeek() - 1;
		DateTime m = ds.minusDays(minus); // 得到刚刚好的本周的周一

		int totalRow = weeks + 1;

		for (int i = 0; i < totalRow; i++) {
			NewInstallUserDetail userdetail = new NewInstallUserDetail();
			String str = "第" + m.plusWeeks(i).getWeekOfWeekyear() + "周  " + m.plusWeeks(i).toString("yyyy-MM-dd")
					+ " -- " + m.plusWeeks(i).plusDays(6).toString("yyyy-MM-dd");
			userdetail.setPeriod(str);

			String sql0 = "select sum(count) from schenewuser where time >= '" + m.plusWeeks(i).toString("yyyy-MM-dd")
					+ "' and time < '" + m.plusWeeks(i + 1).toString("yyyy-MM-dd") + "'";
			int res0 = jdbcTemplate.queryForInt(sql0);
			if (0 == allsum) {
				userdetail.setRatio(res0 + " (0.00%)");
			} else {
				userdetail.setRatio(res0 + " (" + format.format(res0 / allsum) + ")");
			}

			list.add(userdetail);
		}

		return list;
	}

}
