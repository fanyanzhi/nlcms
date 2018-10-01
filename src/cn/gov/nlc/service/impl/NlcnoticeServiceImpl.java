package cn.gov.nlc.service.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.gov.nlc.base.converter.BeanDateConnverter;
import cn.gov.nlc.mapper.ExtNlcnoticeMapper;
import cn.gov.nlc.mapper.NlcnoticeMapper;
import cn.gov.nlc.mapper.NlcnoticecollectMapper;
import cn.gov.nlc.mapper.NlcnoticepraiseMapper;
import cn.gov.nlc.pojo.Nlcnews;
import cn.gov.nlc.pojo.NlcnewscollectExample;
import cn.gov.nlc.pojo.Nlcnotice;
import cn.gov.nlc.pojo.NlcnoticeExample;
import cn.gov.nlc.pojo.NlcnoticeExample.Criteria;
import cn.gov.nlc.pojo.NlcnoticeExt;
import cn.gov.nlc.pojo.NlcnoticeVo;
import cn.gov.nlc.pojo.Nlcnoticeannex;
import cn.gov.nlc.pojo.Nlcnoticecollect;
import cn.gov.nlc.pojo.NlcnoticecollectExample;
import cn.gov.nlc.pojo.Nlcnoticepraise;
import cn.gov.nlc.pojo.NlcnoticepraiseExample;
import cn.gov.nlc.service.NlcnoticeService;
import cn.gov.nlc.service.NlcnoticeannexService;
import cn.gov.nlc.service.SysmessageService;
import cn.gov.nlc.service.SyswindowService;
import cn.gov.nlc.test.mobileapi;
import cn.gov.nlc.util.Common;
import cn.gov.nlc.vo.EasyUiDataGridResult;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service
public class NlcnoticeServiceImpl implements NlcnoticeService {
	
	private static final Logger logger = Logger.getLogger(cn.gov.nlc.service.impl.NlcnoticeServiceImpl.class);

	@Autowired
	private NlcnoticeMapper nlcnoticeMapper;

	@Autowired
	private NlcnoticeannexService nlcnoticeannexSerivce;

	@Autowired
	private NlcnoticecollectMapper nlcnoticecollectMapper;

	@Autowired
	private NlcnoticepraiseMapper nlcnoticepraiseMapper;

	@Autowired
	private ExtNlcnoticeMapper extNlcnoticeMapper;

	@Autowired
	private SysmessageService sysmessageService;
	@Autowired
	private SyswindowService syswindowService;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private NlcnoticeannexService nlcnoticeannexService;

	/**
	 * 公告展示的分页查询
	 * 
	 * @param page
	 * @param rows
	 * @param nlcnoticeExt
	 * @return
	 */
	@Override
	public EasyUiDataGridResult getNoticeList(Integer page, Integer rows, NlcnoticeExt nlcnoticeExt) {
		String title = nlcnoticeExt.getTitle();
		String subPerson = nlcnoticeExt.getSubPerson();
		Date pstartDate = nlcnoticeExt.getPstartDate();
		Date pendDate = nlcnoticeExt.getPendDate();
		Date sstartDate = nlcnoticeExt.getSstartDate();
		Date sendDate = nlcnoticeExt.getSendDate();
		String status = nlcnoticeExt.getStatus();

		// 分页
		PageHelper.startPage(page, rows);
		NlcnoticeExample example = new NlcnoticeExample();
		Criteria criteria = example.createCriteria();

		if (StringUtils.isNotBlank(title)) {
			criteria.andTitleLike("%" + title + "%");
		}

		if (StringUtils.isNotBlank(subPerson)) {
			criteria.andSubPersonEqualTo(subPerson);
		}

		if (StringUtils.isNotBlank(status)) {
			criteria.andStatusEqualTo(status);
		}

		if (null != pstartDate && null != pendDate) {
			criteria.andPubTimeBetween(pstartDate, pendDate);
		} else if (null != pstartDate) {
			criteria.andPubTimeGreaterThanOrEqualTo(pstartDate);
		} else if (null != pendDate) {
			criteria.andPubTimeLessThanOrEqualTo(pendDate);
		}

		if (null != sstartDate && null != sendDate) {
			criteria.andSubTimeBetween(sstartDate, sendDate);
		} else if (null != sstartDate) {
			criteria.andSubTimeGreaterThanOrEqualTo(sstartDate);
		} else if (null != sendDate) {
			criteria.andSubTimeLessThanOrEqualTo(sendDate);
		}

		example.setOrderByClause("tops desc, pub_time desc");

		List<Nlcnotice> list = nlcnoticeMapper.selectByExample(example);
		PageInfo<Nlcnotice> pageInfo = new PageInfo<>(list);
		long total = pageInfo.getTotal();
		EasyUiDataGridResult result = new EasyUiDataGridResult(total, list);

		return result;
	}

	@Override
	public EasyUiDataGridResult getNoticeList(Integer page, Integer rows) {
		// 分页
		PageHelper.startPage(page, rows);
		NlcnoticeExample example = new NlcnoticeExample();
		example.setOrderByClause("pub_time desc");

		List<Nlcnotice> list = nlcnoticeMapper.selectByExample(example);
		PageInfo<Nlcnotice> pageInfo = new PageInfo<>(list);
		long total = pageInfo.getTotal();
		EasyUiDataGridResult result = new EasyUiDataGridResult(total, list);

		return result;
	}

	/**
	 * 删除新闻信息，单个删除
	 */
	@Override
	public int deleteSingleById(String id) {
		int result = nlcnoticeMapper.deleteByPrimaryKey(id);
		return result;
	}

	/**
	 * 查询所有的公告
	 */
	@Override
	public List<Nlcnotice> getAll() {
		NlcnoticeExample example = new NlcnoticeExample();
		example.setOrderByClause("pub_time desc");
		List<Nlcnotice> list = nlcnoticeMapper.selectByExample(example);
		return list;
	}

	/**
	 * 添加公告
	 */
	@Override
	public void insertNotice(Nlcnotice nlcnotice) {
		nlcnoticeMapper.insert(nlcnotice);
	}

	/**
	 * 通过主键查询
	 */
	@Override
	public Nlcnotice selectByPrimaryKey(String id) {
		Nlcnotice nlcnotice = nlcnoticeMapper.selectByPrimaryKey(id);
		return nlcnotice;
	}

	/**
	 * 修改公告
	 */
	@Override
	public void updateNotice(Map<String, String[]> parameterMap) throws Exception {
		String[] ids = parameterMap.get("id");
		String[] pushmethods = parameterMap.get("pushmethod");
		String newpushmethod = pushmethods[0]; // 新的推送方式
		Nlcnotice nlcnotice = nlcnoticeMapper.selectByPrimaryKey(ids[0]);
		String oldpushmethod = nlcnotice.getPushmethod(); // 旧有的推送方式
		ConvertUtils.register(new BeanDateConnverter(), Date.class);
		BeanUtils.populate(nlcnotice, parameterMap);
		nlcnotice.setUpdTime(new Date());
		nlcnoticeMapper.updateByPrimaryKeyWithBLOBs(nlcnotice);
//		String resmethod = remainMethod(newpushmethod, oldpushmethod);
		
		if("已发布".equals(nlcnotice.getStatus())) {	//已发布状态，才插入站内信、弹窗表
			sysmessageService.insertMessageThfour(newpushmethod, Byte.valueOf("0"), "",
					nlcnotice.getNoticeid(), new Date(), nlcnotice.getSubPerson(), nlcnotice.getTitle(), "notice");
			syswindowService.insertWindowThfour(newpushmethod, Byte.valueOf("5"), nlcnotice.getTitle(), nlcnotice.getNoticeid(),
					new Date(), nlcnotice.getSubPerson());
		}
	}

	// 推送方式只会增加，不会减少
	private String remainMethod(String newpushmethod, String oldpushmethod) {
		if (StringUtils.isBlank(oldpushmethod)) {
			return newpushmethod;
		}
		String[] oldarr = oldpushmethod.split(","); // 旧的
		List<String> oldlist = new ArrayList(Arrays.asList(oldarr));
		String[] newarr = newpushmethod.split(",");
		List<String> newlist = new ArrayList(Arrays.asList(newarr));
		for (String str : oldlist) {
			if (newlist.contains(str)) {
				newlist.remove(str);
			}
		}

		String res = "";
		if (null != newlist && newlist.size() > 0) {
			for (String s : newlist) {
				res += s + ",";
			}
			res = res.substring(0, res.length() - 1);
		}
		return res;
	}

	/**
	 * 获取公告详情
	 */
	@Override
	public NlcnoticeVo selectByNoticeId(String noticeId) {
		NlcnoticeExample example = new NlcnoticeExample();
		Criteria criteria = example.createCriteria();
		criteria.andNoticeidEqualTo(noticeId);
		List<Nlcnotice> list = nlcnoticeMapper.selectByExampleWithBLOBs(example);
		NlcnoticeVo nlcnoticeVo = new NlcnoticeVo();
		nlcnoticeVo.setNlcnotice(list.get(0));
		nlcnoticeVo.setAtlist(nlcnoticeannexSerivce.selectByNoticeId(noticeId));
		return nlcnoticeVo;
	}

	@Override
	public boolean isExistCollect(String id, String loginAccount) {
		NlcnoticecollectExample example = new NlcnoticecollectExample();
		cn.gov.nlc.pojo.NlcnoticecollectExample.Criteria criteria = example.createCriteria();
		criteria.andNoticeidEqualTo(id);
		criteria.andLoginaccountEqualTo(loginAccount);
		if (nlcnoticecollectMapper.countByExample(example) > 0)
			return true;
		return false;
	}

	@Override
	public void addNoticeCollect(String id, String loginAccount) {
		Nlcnoticecollect nlcnoticecollect = new Nlcnoticecollect();
		nlcnoticecollect.setNoticeid(id);
		nlcnoticecollect.setLoginaccount(loginAccount);
		nlcnoticecollect.setTime(new Date());
		nlcnoticecollectMapper.insert(nlcnoticecollect);
		
		extNlcnoticeMapper.updateCollectCount(id);
	}

	@Override
	public void cancelNoticeCollect(String id, String loginAccount) {
		if(StringUtils.isBlank(id)) {
			return ;
		}
		List<String> list = java.util.Arrays.asList(id.split(","));
		if(null == list || list.size() == 0) {
			return ;
		}
		
		NlcnoticecollectExample example = new NlcnoticecollectExample();
		cn.gov.nlc.pojo.NlcnoticecollectExample.Criteria criteria = example.createCriteria();
		criteria.andLoginaccountEqualTo(loginAccount);
		criteria.andNoticeidIn(list);
		int res = nlcnoticecollectMapper.deleteByExample(example);
		if(res != 0) {
			extNlcnoticeMapper.updateCollectCountDecrease(list);
		}
	}

	@Override
	public List<NlcnoticeExt> getUserCollect(String loginAccount) {
		return extNlcnoticeMapper.getMyCollect(loginAccount);
	}

	@Override
	public boolean isExistPraise(String id, String loginAccount) {
		NlcnoticepraiseExample example = new NlcnoticepraiseExample();
		cn.gov.nlc.pojo.NlcnoticepraiseExample.Criteria criteria = example.createCriteria();
		criteria.andNoticeidEqualTo(id);
		criteria.andLoginaccountEqualTo(loginAccount);
		if (nlcnoticepraiseMapper.countByExample(example) > 0)
			return true;
		return false;
	}

	@Override
	public void addNoticePraise(String id, String loginAccount) {
		Nlcnoticepraise nlcnoticepraise = new Nlcnoticepraise();
		nlcnoticepraise.setNoticeid(id);
		nlcnoticepraise.setLoginaccount(loginAccount);
		nlcnoticepraise.setTime(new Date());
		nlcnoticepraiseMapper.insert(nlcnoticepraise);
	}

	@Override
	public void cancleNewsPraise(String id, String loginAccount) {
		NlcnoticepraiseExample example = new NlcnoticepraiseExample();
		cn.gov.nlc.pojo.NlcnoticepraiseExample.Criteria criteria = example.createCriteria();
		criteria.andNoticeidEqualTo(id);
		criteria.andLoginaccountEqualTo(loginAccount);
		nlcnoticepraiseMapper.deleteByExample(example);
	}

	@Override
	public List<NlcnoticeExt> getUserPraise(String loginAccount) {
		return extNlcnoticeMapper.getMyPraise(loginAccount);
	}

	@Override
	public void clearPraise(String loginAccount) {
		NlcnoticepraiseExample example = new NlcnoticepraiseExample();
		cn.gov.nlc.pojo.NlcnoticepraiseExample.Criteria criteria = example.createCriteria();
		criteria.andLoginaccountEqualTo(loginAccount);
		nlcnoticepraiseMapper.deleteByExample(example);
	}

	@Override
	public void updatePraiseCount(String newsId) {
		extNlcnoticeMapper.updatePraiseCount(newsId);
	}

	@Override
	public List<Nlcnotice> seaNoticeList(Integer page, Integer rows, String keyword) {
		int iStart = (page - 1) * rows;
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("start", iStart);
		paramMap.put("length", rows);
		paramMap.put("keyword", keyword);
		return extNlcnoticeMapper.seaNoticeList(paramMap);
	}

	@Override
	public Map<String, Object> getIndexNotice() {
		Map<String, Object> res1 = extNlcnoticeMapper.getIndexNotice();
		if(null == res1) {
			return extNlcnoticeMapper.getIndex2Notice();
		}
		
		return res1;
	}

	/**
	 * 公告发布与取消 status 1发布，0取消
	 */
	@Override
	public void publish(String noticeid, String status) {
		Nlcnotice nlcnotice = nlcnoticeMapper.selectByPrimaryKey(noticeid);

		if ("1".equals(status)) {
			nlcnotice.setStatus("已发布");
			nlcnotice.setBkpbtime(new Date());
		} else {
			nlcnotice.setStatus("未发布");
			nlcnotice.setBkpbtime(null);
			nlcnotice.setTops(0);   //下架的时候，取消置顶状态
		}

		nlcnoticeMapper.updateByPrimaryKey(nlcnotice);
		if("已发布".equals(nlcnotice.getStatus())) {	//已发布状态，才插入站内信、弹窗表
			sysmessageService.insertMessageThfour(nlcnotice.getPushmethod(), Byte.valueOf("0"), "", nlcnotice.getNoticeid(), new Date(), nlcnotice.getSubPerson(), nlcnotice.getTitle(), "notice");
			syswindowService.insertWindowThfour(nlcnotice.getPushmethod(), Byte.valueOf("5"), nlcnotice.getTitle(), nlcnotice.getNoticeid(), new Date(), nlcnotice.getSubPerson());
		}
		
		/*String csql = "select count(1) from nlcnotice where tops = 1";
		int result = jdbcTemplate.queryForInt(csql);
		
		if(0 == result) {	//若当前没有置顶项，这置顶最新已发布的公告
			String sqls = "SELECT noticeid FROM `nlcnotice` where status = '已发布' order by pub_time desc limit 1";
			List<Object> res = jdbcTemplate.query(sqls, new RowMapper<Object>(){

				@Override
				public Object mapRow(ResultSet rs, int arg1) throws SQLException {
					return rs.getObject(1);
				}
				
			});
			
			if(res != null && res.size() > 0) {
				String mid = (String) res.get(0);
				//设置最新添加的已发布的公告为置顶项
				String sql1 = "update nlcnotice set tops = 1 where noticeid = '"+mid+"'";
				jdbcTemplate.execute(sql1);
			}
		}*/
	}

	/**
	 * 公告分享页面的分页查询
	 */
	@Override
	public EasyUiDataGridResult getShareNoticeList(String noticeid, int rows) {
		List<Nlcnotice> list = extNlcnoticeMapper.getShareNoticeList(noticeid, rows);
		EasyUiDataGridResult result = new EasyUiDataGridResult(0, list);
		return result;
	}

	@Override
	public int getNlcnoticeByNoticeId(String nlcnoticeId) {
		NlcnoticeExample example = new NlcnoticeExample();
		Criteria criteria = example.createCriteria();
		criteria.andNoticeidEqualTo(nlcnoticeId);
		int count = nlcnoticeMapper.countByExample(example);
		return count;
	}

	/**
	 * app公告
	 */
	@Override
	public EasyUiDataGridResult getAppNoticeList(Integer page, Integer rows) {
		// 分页
		PageHelper.startPage(page, rows);
		NlcnoticeExample example = new NlcnoticeExample();
		Criteria criteria = example.createCriteria();
		criteria.andStatusEqualTo("已发布");
		example.setOrderByClause("tops desc, pub_time desc");

		List<Nlcnotice> list = nlcnoticeMapper.selectByExample(example);
		PageInfo<Nlcnotice> pageInfo = new PageInfo<>(list);
		long total = pageInfo.getTotal();
		EasyUiDataGridResult result = new EasyUiDataGridResult(total, list);
		return result;
	}
	
	/**
	 * 置顶公告
	 */
	@Override
	public void settop(String noticeid) {
		String sql = "update nlcnotice set tops = 0";
		jdbcTemplate.execute(sql);
		
		String sql1 = "update nlcnotice set tops = 1 where noticeid = '"+noticeid+"'";
		jdbcTemplate.execute(sql1);
	}
	
	/**
	 * 取消置顶新闻
	 */
	@Override
	public void cantop(String noticeid) {
		String sql = "update nlcnotice set tops = 0 where noticeid = '"+noticeid+"'";
		jdbcTemplate.execute(sql);
		
		/*String sqls = "SELECT noticeid FROM `nlcnotice` where status = '已发布' and noticeid != '"+noticeid+"' order by pub_time desc limit 1";
		List<Object> res = jdbcTemplate.query(sqls, new RowMapper<Object>(){

			@Override
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				return rs.getObject(1);
			}
			
		});
		
		if(res != null && res.size() > 0) {
			String mid = (String) res.get(0);
			//设置最新添加的已发布的公告为置顶项
			String sql1 = "update nlcnotice set tops = 1 where noticeid = '"+mid+"'";
			jdbcTemplate.execute(sql1);
		}*/
		
	}

	@Override
	public JSONObject pull() {
		JSONObject result = new JSONObject();
		
		int curpage = 1;
		boolean bcontinue = true;
		while (bcontinue) {
			String strNotice = mobileapi.testRPC("getNoticeTitles", "http://webservice.nlcm.neusoft.com",
					new Object[] { curpage, 10 });
			if (StringUtils.isBlank(strNotice)) {
				result.put("result", false);
				result.put("message", "接口连接失败");
				return result;
			}

			JSONArray arrNotice = JSONArray.fromObject(strNotice);
			if (arrNotice.size() > 0) {
				for (int i = 0; i < arrNotice.size(); i++) {
					JSONObject jsonNotice = JSONObject.fromObject(arrNotice.get(i));
					String detail = mobileapi.testRPC("getNoticeDetails", "http://webservice.nlcm.neusoft.com", new Object[] { jsonNotice.get("id") });
					
					if(StringUtils.isBlank(detail)) {
						continue;
					}
					JSONObject detailNotice = JSONObject.fromObject(detail);
					Nlcnotice nlcnotice = new Nlcnotice();
					nlcnotice.setNoticeid(jsonNotice.get("id").toString());
					nlcnotice.setTitle(jsonNotice.get("title").toString());
					nlcnotice.setSrc(jsonNotice.get("src").toString().indexOf("http://") > -1
							? jsonNotice.get("src").toString() : "http://m.nlc.cn" + jsonNotice.get("src").toString());
					nlcnotice.setPubTime(
							Common.ConvertToDate(detailNotice.get("pub_time").toString(), "yyyy-MM-dd HH:mm"));
					nlcnotice.setSubTime(
							Common.ConvertToDate(detailNotice.get("sub_time").toString(), "yyyy-MM-dd HH:mm"));
					nlcnotice.setUpdTime(
							Common.ConvertToDate(detailNotice.get("upd_time").toString(), "yyyy-MM-dd HH:mm"));
					nlcnotice.setBoard(detailNotice.get("board").toString());
					nlcnotice.setSubPerson(detailNotice.get("sub_person").toString());
					nlcnotice.setStatus("已发布");
					nlcnotice.setBkpbtime(new Date());
					nlcnotice.setContent(detailNotice.get("content").toString().replace("\n", "").replace("\r", "")
							.replace("\n\r", "").replace("'", "\\\'"));
					nlcnotice.setSource("手机门户");
					nlcnotice.setPraisecount(praiseCountBynoticeid(nlcnotice.getNoticeid()));
					nlcnotice.setCollectcount(collectCountBynoticeid(nlcnotice.getNoticeid()));
					nlcnotice.setTops(0);
					Date subTime = nlcnotice.getSubTime();
					Date pubTime = nlcnotice.getPubTime();
					if (null == subTime) {
						nlcnotice.setSubTime(new Date());
					}
					if (null == pubTime) {
						nlcnotice.setPubTime(new Date());
					}
					try {
						if (getNlcnoticeByNoticeId(nlcnotice.getNoticeid()) > 0) {
							bcontinue = false;
							break;
						}
						insertNotice(nlcnotice);
					} catch (Exception e) {
						logger.error(jsonNotice.get("id").toString() + "-->" + e.getMessage());
					}
					// {"annexs":[{"name":"民国时期文献整理出版项目实施方案.doc","url":"upload/20160118/63929dc4533b42f580ac6a5ef95a7d99.doc"},{"name":"民国时期文献整理出版项目申报表.doc","url":"upload/20160118/fd09107fb2af4e2f86d5bc2e804210b1.doc"}],
					try {
						JSONArray recordArray = JSONArray.fromObject(detailNotice.get("annexs"));
						if (recordArray.size() > 0) {
							for (int j = 0; j < recordArray.size(); j++) {
								JSONObject annexJson = JSONObject.fromObject(recordArray.get(j));
								Nlcnoticeannex nlcnoticeannex = new Nlcnoticeannex();
								nlcnoticeannex.setNoticeid(jsonNotice.get("id").toString());
								nlcnoticeannex.setTitle(annexJson.getString("name"));
								nlcnoticeannex.setUrl(annexJson.getString("url").indexOf("http://") > -1
										? annexJson.getString("url") : "http://m.nlc.cn/" + annexJson.getString("url"));
								try {
									nlcnoticeannexService.insertNoticeAnnex(nlcnoticeannex);
								} catch (Exception e) {
									logger.info(jsonNotice.get("id").toString() + "-->" + e.getMessage());
								}

							}
						}
					} catch (Exception e) {
						logger.error(e.getMessage(), e);
					}
				}

			} else {
				break;
			}
			curpage++;
		}
		
		result.put("result", true);
		return result;
	}
	
	private int collectCountBynoticeid(String noticeid) {
		NlcnoticecollectExample example = new NlcnoticecollectExample();
		cn.gov.nlc.pojo.NlcnoticecollectExample.Criteria criteria = example.createCriteria();
		criteria.andNoticeidEqualTo(noticeid);
		int res = nlcnoticecollectMapper.countByExample(example);
		return res;
	}
	
	private int praiseCountBynoticeid(String noticeid) {
		NlcnoticepraiseExample example = new NlcnoticepraiseExample();
		cn.gov.nlc.pojo.NlcnoticepraiseExample.Criteria criteria = example.createCriteria();
		criteria.andNoticeidEqualTo(noticeid);
		int res = nlcnoticepraiseMapper.countByExample(example);
		return res;
	}
}
