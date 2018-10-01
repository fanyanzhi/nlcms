package cn.gov.nlc.service.impl;

import java.lang.reflect.InvocationTargetException;
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
import cn.gov.nlc.mapper.ExtNlcnewsMapper;
import cn.gov.nlc.mapper.NlcnewsMapper;
import cn.gov.nlc.mapper.NlcnewscollectMapper;
import cn.gov.nlc.mapper.NlcnewspraiseMapper;
import cn.gov.nlc.pojo.Nlcnews;
import cn.gov.nlc.pojo.NlcnewsExample;
import cn.gov.nlc.pojo.NlcnewsExample.Criteria;
import cn.gov.nlc.pojo.NlcnewsExt;
import cn.gov.nlc.pojo.Nlcnewscollect;
import cn.gov.nlc.pojo.NlcnewscollectExample;
import cn.gov.nlc.pojo.Nlcnewspraise;
import cn.gov.nlc.pojo.NlcnewspraiseExample;
import cn.gov.nlc.service.NlcnewsService;
import cn.gov.nlc.service.SysmessageService;
import cn.gov.nlc.service.SyswindowService;
import cn.gov.nlc.test.mobileapi;
import cn.gov.nlc.util.Common;
import cn.gov.nlc.vo.EasyUiDataGridResult;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service
public class NlcnewsServiceImpl implements NlcnewsService {

	private static final Logger logger = Logger.getLogger(cn.gov.nlc.service.impl.NlcnewsServiceImpl.class);
	
	@Autowired
	private NlcnewsMapper nlcnewsMapper;

	@Autowired
	private ExtNlcnewsMapper extNlcnewsMapper;

	@Autowired
	private NlcnewscollectMapper nlcnewscollectMapper;

	@Autowired
	private NlcnewspraiseMapper nlcnewspraiseMapper;

	@Autowired
	private SysmessageService sysmessageService;
	@Autowired
	private SyswindowService syswindowService;
	@Autowired
	private JdbcTemplate jdbcTemplate;

	/**
	 * 新闻管理展示的分页查询
	 * 
	 * @param page
	 * @param rows
	 * @return
	 */
	@Override
	public EasyUiDataGridResult getnewsList(int page, int rows, NlcnewsExt nlcnewsExt, String sort, String order) {
		String title = nlcnewsExt.getTitle();
		String subPerson = nlcnewsExt.getSubPerson();
		Date pstartDate = nlcnewsExt.getPstartDate();
		Date pendDate = nlcnewsExt.getPendDate();
		Date sstartDate = nlcnewsExt.getSstartDate();
		Date sendDate = nlcnewsExt.getSendDate();
		String status = nlcnewsExt.getStatus();
		String source = nlcnewsExt.getSource();

		// 分页
		PageHelper.startPage(page, rows);

		NlcnewsExample example = new NlcnewsExample();
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

		if (StringUtils.isNotBlank(source)) {
			criteria.andSourceEqualTo(source);
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

		if (StringUtils.isNotBlank(sort)) {
			if ("pubTime".equalsIgnoreCase(sort)) {
				example.setOrderByClause("pub_time " + order);
			} else {
				example.setOrderByClause(sort + " " + order);
			}
		} else {
			example.setOrderByClause("tops desc, pub_time desc");
		}

		List<Nlcnews> list = nlcnewsMapper.selectByExample(example);
		PageInfo<Nlcnews> pageInfo = new PageInfo<>(list);
		long total = pageInfo.getTotal();
		EasyUiDataGridResult result = new EasyUiDataGridResult(total, list);

		return result;
	}

	/**
	 * 新闻管理页面分享页面的分页查询
	 */
	@Override
	public EasyUiDataGridResult getnewsList(int page, int rows, Integer id) {
		// 分页
		PageHelper.startPage(page, rows);
		NlcnewsExample example = new NlcnewsExample();
		Criteria criteria = example.createCriteria();
		criteria.andStatusEqualTo("已发布");
		example.setOrderByClause("pub_time desc");
		List<Nlcnews> list = nlcnewsMapper.selectByExample(example);
		PageInfo<Nlcnews> pageInfo = new PageInfo<>(list);
		long total = pageInfo.getTotal();
		EasyUiDataGridResult result = new EasyUiDataGridResult(total, list);
		return result;
	}

	/**
	 * 添加新闻
	 */
	public void insertNews(Nlcnews nlcnews) {
		nlcnewsMapper.insert(nlcnews);
	}

	/**
	 * 删除新闻信息，单个删除
	 */
	@Override
	public int deleteSingleById(Integer id) {
		int result = nlcnewsMapper.deleteByPrimaryKey(id);
		return result;
	}

	/**
	 * 查询所有的日志信息
	 */
	@Override
	public List<Nlcnews> getAll() {
		NlcnewsExample example = new NlcnewsExample();
		example.setOrderByClause("pub_time desc");
		List<Nlcnews> list = nlcnewsMapper.selectByExample(example);
		return list;
	}

	/**
	 * 通过主键查找新闻
	 */
	@Override
	public Nlcnews selectByPrimaryKey(Integer id) {
		Nlcnews nlcnews = nlcnewsMapper.selectByPrimaryKey(id);
		return nlcnews;
	}

	/**
	 * 修改新闻
	 * 
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	@Override
	public void updateNews(Map<String, String[]> parameterMap) throws Exception {
		String[] ids = parameterMap.get("id");
		String[] pushmethods = parameterMap.get("pushmethod");
		Integer id = Integer.parseInt(ids[0]);
		String newpushmethod = pushmethods[0]; // 新的推送方式
		Nlcnews oldNlcnews = nlcnewsMapper.selectByPrimaryKey(id); // 得到原有的新闻数据
		String oldpushmethod = oldNlcnews.getPushmethod(); // 旧有的推送方式
		ConvertUtils.register(new BeanDateConnverter(), Date.class);
		BeanUtils.populate(oldNlcnews, parameterMap);
		oldNlcnews.setUpdTime(new Date());
		nlcnewsMapper.updateByPrimaryKeyWithBLOBs(oldNlcnews);
//		String resmethod = remainMethod(newpushmethod, oldpushmethod);

		if("已发布".equals(oldNlcnews.getStatus())) {	//已发布状态，才插入站内信、弹窗表
			sysmessageService.insertMessageThfour(newpushmethod, Byte.valueOf("0"), "",
				oldNlcnews.getNewsid(), new Date(), oldNlcnews.getSubPerson(), oldNlcnews.getTitle(), "news");
			syswindowService.insertWindowThfour(newpushmethod, Byte.valueOf("4"), oldNlcnews.getTitle(), oldNlcnews.getNewsid(),
					new Date(), oldNlcnews.getSubPerson());
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

	@Override
	public Nlcnews getNlcnewsByNewsId(String newsId) {
		NlcnewsExample example = new NlcnewsExample();
		Criteria criteria = example.createCriteria();
		criteria.andNewsidEqualTo(newsId);
		List<Nlcnews> list = nlcnewsMapper.selectByExampleWithBLOBs(example);
		if (null != list && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public List<Nlcnews> getRelatedNews(String id) {
		return extNlcnewsMapper.getRelatedNews(id);
	}

	/**
	 * 添加新闻收藏
	 */
	@Override
	public void addNewsCollect(String id, String loginAccount) {
		Nlcnewscollect nlcnewscollect = new Nlcnewscollect();
		nlcnewscollect.setNewsid(id);
		nlcnewscollect.setLoginaccount(loginAccount);
		nlcnewscollect.setTime(new Date());
		nlcnewscollectMapper.insert(nlcnewscollect);
		
		extNlcnewsMapper.updateCollectCount(id);
	}

	/**
	 * 取消新闻收藏
	 */
	@Override
	public void cancelNewsCollect(String id, String loginAccount) {
		if(StringUtils.isBlank(id)) {
			return ;
		}
		List<String> list = java.util.Arrays.asList(id.split(","));
		if(null == list || list.size() == 0) {
			return ;
		}
		
		NlcnewscollectExample example = new NlcnewscollectExample();
		cn.gov.nlc.pojo.NlcnewscollectExample.Criteria criteria = example.createCriteria();
		criteria.andNewsidIn(list);
		criteria.andLoginaccountEqualTo(loginAccount);
		int res = nlcnewscollectMapper.deleteByExample(example);
		if(res != 0) {
			extNlcnewsMapper.updateCollectCountDecrease(list);
		}
	}

	/**
	 * 获取我收藏的新闻
	 */
	@Override
	public List<NlcnewsExt> getUserCollect(String loginAccount) {
		return extNlcnewsMapper.getMyCollect(loginAccount);
	}

	@Override
	public List<NlcnewsExt> getUserPraise(String loginAccount) {
		return extNlcnewsMapper.getMyPraise(loginAccount);
	}

	@Override
	public void clearPraise(String loginAccount) {
		NlcnewspraiseExample example = new NlcnewspraiseExample();
		cn.gov.nlc.pojo.NlcnewspraiseExample.Criteria criteria = example.createCriteria();
		criteria.andLoginaccountEqualTo(loginAccount);
		nlcnewspraiseMapper.deleteByExample(example);
	}

	@Override
	public boolean isExistCollect(String id, String loginAccount) {
		NlcnewscollectExample example = new NlcnewscollectExample();
		cn.gov.nlc.pojo.NlcnewscollectExample.Criteria criteria = example.createCriteria();
		criteria.andNewsidEqualTo(id);
		criteria.andLoginaccountEqualTo(loginAccount);
		if (nlcnewscollectMapper.countByExample(example) > 0)
			return true;
		return false;

	}

	@Override
	public boolean isExistPraise(String id, String loginAccount) {
		NlcnewspraiseExample example = new NlcnewspraiseExample();
		cn.gov.nlc.pojo.NlcnewspraiseExample.Criteria criteria = example.createCriteria();
		criteria.andNewsidEqualTo(id);
		criteria.andLoginaccountEqualTo(loginAccount);
		if (nlcnewspraiseMapper.countByExample(example) > 0)
			return true;
		return false;
	}

	@Override
	public void addNewsPraise(String id, String loginAccount) {
		Nlcnewspraise nlcnewspraise = new Nlcnewspraise();
		nlcnewspraise.setNewsid(id);
		nlcnewspraise.setLoginaccount(loginAccount);
		nlcnewspraise.setTime(new Date());
		nlcnewspraiseMapper.insert(nlcnewspraise);
	}

	@Override
	public void cancleNewsPraise(String id, String loginAccount) {
		NlcnewspraiseExample example = new NlcnewspraiseExample();
		cn.gov.nlc.pojo.NlcnewspraiseExample.Criteria criteria = example.createCriteria();
		criteria.andNewsidEqualTo(id);
		criteria.andLoginaccountEqualTo(loginAccount);
		nlcnewspraiseMapper.deleteByExample(example);
	}

	@Override
	public void updatePraiseCount(String newsId) {
		extNlcnewsMapper.updatePraiseCount(newsId);
	}

	@Override
	public List<Nlcnews> seaNewsList(int page, int rows, String keyword) {
		int iStart = (page - 1) * rows;
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("start", iStart);
		paramMap.put("length", rows);
		paramMap.put("keyword", keyword);
		return extNlcnewsMapper.seaNewsList(paramMap);
	}

	@Override
	public Map<String, Object> getIndexNews() {
		Map<String, Object> res1 = extNlcnewsMapper.getIndexNews();
		if(null == res1) {
			return extNlcnewsMapper.getIndex2News();
		}
		return res1;
	}

	/**
	 * 新闻发布与取消 status 1发布，0取消
	 */
	@Override
	public void publish(Integer id, String status) {
		Nlcnews nlcnews = nlcnewsMapper.selectByPrimaryKey(id);
		if ("1".equals(status)) {
			nlcnews.setStatus("已发布");
			nlcnews.setBkpbtime(new Date());
		} else {
			nlcnews.setStatus("未发布");
			nlcnews.setBkpbtime(null);
			nlcnews.setTops(0);   //下架的时候，取消置顶状态
		}

		nlcnewsMapper.updateByPrimaryKey(nlcnews);
		if("已发布".equals(nlcnews.getStatus())) {	//已发布状态，才插入站内信、弹窗表
			sysmessageService.insertMessageThfour(nlcnews.getPushmethod(), Byte.valueOf("0"), "", nlcnews.getNewsid(), new Date(), nlcnews.getSubPerson(), nlcnews.getTitle(), "news");
			syswindowService.insertWindowThfour(nlcnews.getPushmethod(), Byte.valueOf("4"), nlcnews.getTitle(), nlcnews.getNewsid(), new Date(), nlcnews.getSubPerson());
		}
		
		/*String csql = "select count(1) from nlcnews where tops = 1";
		int result = jdbcTemplate.queryForInt(csql);
		
		if(0 == result) {	//若当前没有置顶项，这置顶最新已发布的新闻
			String sqls = "SELECT id FROM `nlcnews` where status = '已发布' order by pub_time desc limit 1";
			List<Object> res = jdbcTemplate.query(sqls, new RowMapper<Object>(){

				@Override
				public Object mapRow(ResultSet rs, int arg1) throws SQLException {
					return rs.getObject(1);
				}
				
			});
			
			if(res != null && res.size() > 0) {
				Long mid = (Long) res.get(0);
				//设置最新添加的已发布的新闻为置顶项
				String sql1 = "update nlcnews set tops = 1 where id = " + mid;
				jdbcTemplate.execute(sql1);
			}
		}*/
	}

	/**
	 * 新闻管理页面分享页面的分页查询
	 */
	@Override
	public EasyUiDataGridResult getShareNewsList(Integer id, int rows) {
		List<NlcnewsExt> list = extNlcnewsMapper.getShareNewsList(id, rows);
		EasyUiDataGridResult result = new EasyUiDataGridResult(0, list);
		return result;
	}

	/**
	 * app新闻页面
	 * 
	 * @param page
	 * @param rows
	 * @return
	 */
	public EasyUiDataGridResult getnewsList(int page, int rows) {
		// 分页
		PageHelper.startPage(page, rows);

		NlcnewsExample example = new NlcnewsExample();
		Criteria criteria = example.createCriteria();
		criteria.andStatusEqualTo("已发布");
		example.setOrderByClause("tops desc,pub_time desc");
		List<Nlcnews> list = nlcnewsMapper.selectByExample(example);
		PageInfo<Nlcnews> pageInfo = new PageInfo<>(list);
		long total = pageInfo.getTotal();
		EasyUiDataGridResult result = new EasyUiDataGridResult(total, list);
		return result;
	}

	/**
	 * 置顶新闻
	 */
	@Override
	public void settop(Integer id) {
		String sql = "update nlcnews set tops = 0";
		jdbcTemplate.execute(sql);
		
		String sql1 = "update nlcnews set tops = 1 where id = " + id;
		jdbcTemplate.execute(sql1);
	}
	
	/**
	 * 取消置顶新闻
	 */
	@Override
	public void cantop(Integer id) {
		String sql = "update nlcnews set tops = 0 where id = " + id;
		jdbcTemplate.execute(sql);
		
		/*String sqls = "SELECT id FROM `nlcnews` where status = '已发布' and id != '"+id+"' order by pub_time desc limit 1";
		List<Object> res = jdbcTemplate.query(sqls, new RowMapper<Object>(){

			@Override
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				return rs.getObject(1);
			}
			
		});
		
		if(res != null && res.size() > 0) {
			Long mid = (Long) res.get(0);
			//设置最新添加的已发布的新闻为置顶项
			String sql1 = "update nlcnews set tops = 1 where id = " + mid;
			jdbcTemplate.execute(sql1);
		}*/
		
	}

	@Override
	public JSONObject pull() {
		JSONObject result = new JSONObject();
		
		int curpage = 1;
		boolean bContinue = true;
		while (bContinue) {
			String strNews = mobileapi.testRPC("getNewsTitles", "http://webservice.nlcm.neusoft.com", new Object[] { curpage, 10 });
			
			JSONArray arrNews = null;
			if(StringUtils.isNotBlank(strNews)) {
				arrNews = JSONArray.fromObject(strNews);
			}else {
				result.put("result", false);
				result.put("message", "接口连接失败");
				return result;
			}
			
			if (null != arrNews && arrNews.size() > 0) {
				for (int i = 0; i < arrNews.size(); i++) {
					JSONObject jsonNews = JSONObject.fromObject(arrNews.get(i));
					String detail = mobileapi.testRPC("getNewsDetails", "http://webservice.nlcm.neusoft.com", new Object[] { jsonNews.get("id") });
					
					if(StringUtils.isBlank(detail)) {
						continue;
					}
					JSONObject detailNews = JSONObject.fromObject(detail);
					Nlcnews nlcnews = new Nlcnews();
					nlcnews.setNewsid(jsonNews.get("id").toString());
					nlcnews.setTitle(jsonNews.get("title").toString());
					nlcnews.setSrc(jsonNews.get("src").toString().indexOf("http://") > -1
							? jsonNews.get("src").toString() : "http://m.nlc.cn" + jsonNews.get("src").toString());
					if (!Common.IsNullOrEmpty(jsonNews.getString("pub_time")))
						nlcnews.setPubTime(
								Common.ConvertToDate(jsonNews.get("pub_time").toString(), "yyyy-MM-dd HH:mm"));
					nlcnews.setSource("手机门户");
					nlcnews.setStatus("已发布");
					nlcnews.setSubPerson(detailNews.get("sub_person").toString());
					nlcnews.setContent(detailNews.get("content").toString().replace("\n", "").replace("\r", "")
							.replace("\n\r", "").replace("'", "\\\'"));
					if (!Common.IsNullOrEmpty(detailNews.getString("sub_time")))
						nlcnews.setSubTime(
								Common.ConvertToDate(detailNews.get("sub_time").toString(), "yyyy-MM-dd HH:mm"));
					if (!Common.IsNullOrEmpty(detailNews.getString("upd_time")))
						nlcnews.setUpdTime(
								Common.ConvertToDate(detailNews.get("upd_time").toString(), "yyyy-MM-dd HH:mm"));
					
					nlcnews.setTime(new Date());
					nlcnews.setBkpbtime(new Date());

					Date subTime = nlcnews.getSubTime();
					Date pubTime = nlcnews.getPubTime();
					if (null == subTime) {
						nlcnews.setSubTime(new Date());
					}
					if (null == pubTime) {
						nlcnews.setPubTime(new Date());
					}
					try {
						if (getNlcnewsByNewsId(nlcnews.getNewsid()) != null) {
							bContinue = false;
							break;
						}
						
						nlcnews.setPraisecount(praiseCountByNewsid(nlcnews.getNewsid()));
						nlcnews.setCollectcount(collectCountByNewsid(nlcnews.getNewsid()));
						nlcnews.setTops(0);
						insertNews(nlcnews);
					} catch (Exception e) {
						logger.error("nlcnews-->" + nlcnews.getNewsid() + "-->" + nlcnews.getTitle() + e.getMessage());
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
	
	private int collectCountByNewsid(String newsid) {
		NlcnewscollectExample example = new NlcnewscollectExample();
		cn.gov.nlc.pojo.NlcnewscollectExample.Criteria criteria = example.createCriteria();
		criteria.andNewsidEqualTo(newsid);
		int res = nlcnewscollectMapper.countByExample(example);
		return res;
	}
	
	private int praiseCountByNewsid(String newsid) {
		NlcnewspraiseExample example = new NlcnewspraiseExample();
		cn.gov.nlc.pojo.NlcnewspraiseExample.Criteria criteria = example.createCriteria();
		criteria.andNewsidEqualTo(newsid);
		int res = nlcnewspraiseMapper.countByExample(example);
		return res;
	}

}
