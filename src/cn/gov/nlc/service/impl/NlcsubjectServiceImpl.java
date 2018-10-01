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
import cn.gov.nlc.mapper.ExtNlcsubjectMapper;
import cn.gov.nlc.mapper.ExtWjreaderMapper;
import cn.gov.nlc.mapper.NlcsubjectMapper;
import cn.gov.nlc.mapper.NlcsubjectcatalogMapper;
import cn.gov.nlc.mapper.NlcsubjectcollectMapper;
import cn.gov.nlc.mapper.NlcsubjectpraiseMapper;
import cn.gov.nlc.pojo.Nlcsubject;
import cn.gov.nlc.pojo.NlcsubjectExample;
import cn.gov.nlc.pojo.NlcsubjectExt;
import cn.gov.nlc.pojo.Nlcsubjectcatalog;
import cn.gov.nlc.pojo.NlcsubjectcatalogExample;
import cn.gov.nlc.pojo.NlcsubjectcatalogExample.Criteria;
import cn.gov.nlc.pojo.Nlcsubjectcollect;
import cn.gov.nlc.pojo.NlcsubjectcollectExample;
import cn.gov.nlc.pojo.Nlcsubjectpraise;
import cn.gov.nlc.pojo.NlcsubjectpraiseExample;
import cn.gov.nlc.pojo.Subindexnum;
import cn.gov.nlc.pojo.Wjreader;
import cn.gov.nlc.service.NlcsubjectService;
import cn.gov.nlc.service.NlcsubjectcatalogService;
import cn.gov.nlc.service.SubindexnumService;
import cn.gov.nlc.service.SysmessageService;
import cn.gov.nlc.service.SyswindowService;
import cn.gov.nlc.util.Common;
import cn.gov.nlc.util.UUIDGenerator;
import cn.gov.nlc.vo.EasyUiDataGridResult;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service
public class NlcsubjectServiceImpl implements NlcsubjectService {

	private static final Logger logger = Logger.getLogger(cn.gov.nlc.service.impl.NlcsubjectServiceImpl.class);

	@Autowired
	private NlcsubjectMapper nlcsubjectMapper;
	@Autowired
	private NlcsubjectcatalogMapper nlcsubjectcatalogMapper;
	@Autowired
	private NlcsubjectpraiseMapper nlcsubjectpraiseMapper;
	@Autowired
	private NlcsubjectcollectMapper nlcsubjectcollectMapper;
	@Autowired
	private ExtNlcsubjectMapper extNlcsubjectMapper;
	@Autowired
	private ExtWjreaderMapper extWjreaderMapper;
	@Autowired
	private SysmessageService sysmessageService;
	@Autowired
	private SyswindowService syswindowService;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private NlcsubjectcatalogService nlcsubjectcatalogService;
	@Autowired
	private SubindexnumService subindexnumService;

	/**
	 * 专题分页展示
	 */
	@Override
	public EasyUiDataGridResult getSubjectList(Integer page, Integer rows) {
		// 分页
		PageHelper.startPage(page, rows);
		NlcsubjectExample example = new NlcsubjectExample();
		example.setOrderByClause("sort asc, pubtime desc, id desc");
		List<Nlcsubject> list = nlcsubjectMapper.selectByExample(example);
		PageInfo<Nlcsubject> pageInfo = new PageInfo<>(list);
		long total = pageInfo.getTotal();
		EasyUiDataGridResult result = new EasyUiDataGridResult(total, list);

		return result;
	}

	@Override
	public EasyUiDataGridResult getSubjectListWithBlobtt(Integer page, Integer rows) {
		PageHelper.startPage(page, rows);
		NlcsubjectExample example = new NlcsubjectExample();
		cn.gov.nlc.pojo.NlcsubjectExample.Criteria criteria = example.createCriteria();
		criteria.andStatusEqualTo("已发布");
		example.setOrderByClause("sort asc, pubtime desc, id desc");
		List<Nlcsubject> list = nlcsubjectMapper.selectByExampleWithBLOBs(example);
		PageInfo<Nlcsubject> pageInfo = new PageInfo<>(list);
		long total = pageInfo.getTotal();
		EasyUiDataGridResult result = new EasyUiDataGridResult(total, list);

		return result;
	}

	/**
	 * 删除专题，单个删除 并删除专题目录项
	 */
	@Override
	public int deleteSingleById(Integer id) {
		Nlcsubject nlcsubject = nlcsubjectMapper.selectByPrimaryKey(id);
		String subjectid = nlcsubject.getSubjectid();

		NlcsubjectcatalogExample example = new NlcsubjectcatalogExample();
		Criteria criteria = example.createCriteria();
		criteria.andSubjectidEqualTo(subjectid);
		int result = nlcsubjectMapper.deleteByPrimaryKey(id);
		nlcsubjectcatalogMapper.deleteByExample(example);
		return result;
	}

	/**
	 * 添加专题信息和专题目录的root项
	 */
	@Override
	public void inertNlcsubjectAndCatalogRoot(Nlcsubject nlcsubject) {

		Nlcsubjectcatalog catalog = new Nlcsubjectcatalog();
		catalog.setCatalogid("root");
		catalog.setSubjectid(nlcsubject.getSubjectid());
		catalog.setTitle("目录");
		catalog.setIconcls("icon_folder");
		catalog.setChecked("false");
		catalog.setState("open");
		catalog.setCseq(1);
		catalog.setIsdir("1"); // 是目录
		catalog.setStatus("已上架");
		nlcsubjectMapper.insert(nlcsubject);
		nlcsubjectcatalogMapper.insert(catalog);
	}

	/**
	 * 通过主键查询
	 */
	@Override
	public Nlcsubject selectByPrimaryKey(Integer id) {
		Nlcsubject nlcsubject = nlcsubjectMapper.selectByPrimaryKey(id);
		return nlcsubject;
	}

	/**
	 * 修改专题
	 */
	@Override
	public void updateSubject(Map<String, String[]> parameterMap) throws Exception {
		String[] ids = parameterMap.get("id");
		String[] pushmethods = parameterMap.get("pushmethod");
		Integer id = Integer.parseInt(ids[0]);
		String newpushmethod = pushmethods[0]; // 新的推送方式
		Nlcsubject nlcsubject = nlcsubjectMapper.selectByPrimaryKey(id);
		String oldpushmethod = nlcsubject.getPushmethod(); // 旧的推送方式
		ConvertUtils.register(new BeanDateConnverter(), Date.class);
		BeanUtils.populate(nlcsubject, parameterMap);
		nlcsubjectMapper.updateByPrimaryKeyWithBLOBs(nlcsubject);
		// String resmethod = remainMethod(newpushmethod, oldpushmethod);

		if ("已发布".equals(nlcsubject.getStatus())) { // 已发布状态，才插入站内信、弹窗表
			sysmessageService.insertMessageThfour(newpushmethod, Byte.valueOf("0"), "", nlcsubject.getSubjectid(),
					new Date(), nlcsubject.getCreater(), nlcsubject.getTitle(), "subject");
			syswindowService.insertWindowThfour(newpushmethod, Byte.valueOf("7"), nlcsubject.getTitle(),
					nlcsubject.getSubjectid(), new Date(), nlcsubject.getCreater());
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
	 * 通过subjectid查询
	 */
	@Override
	public Nlcsubject selectBySubjectid(String subjectid) {
		NlcsubjectExample example = new NlcsubjectExample();
		cn.gov.nlc.pojo.NlcsubjectExample.Criteria criteria = example.createCriteria();
		criteria.andSubjectidEqualTo(subjectid);
		List<Nlcsubject> list = nlcsubjectMapper.selectByExampleWithBLOBs(example);
		if (null != list && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public Nlcsubject getNlcSubjectBySubjectId(String subjectId) {
		NlcsubjectExample example = new NlcsubjectExample();
		cn.gov.nlc.pojo.NlcsubjectExample.Criteria criteria = example.createCriteria();
		criteria.andSubjectidEqualTo(subjectId);
		List<Nlcsubject> list = nlcsubjectMapper.selectByExampleWithBLOBs(example);
		if (null != list && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public boolean isExistCollect(String id, String loginAccount) {
		NlcsubjectcollectExample example = new NlcsubjectcollectExample();
		cn.gov.nlc.pojo.NlcsubjectcollectExample.Criteria criteria = example.createCriteria();
		criteria.andSubjectidEqualTo(id);
		criteria.andLoginaccountEqualTo(loginAccount);
		if (nlcsubjectcollectMapper.countByExample(example) > 0)
			return true;
		return false;
	}

	@Override
	public void addSubjectCollect(String id, String loginAccount) {
		Nlcsubjectcollect nlcsubjectcollect = new Nlcsubjectcollect();
		nlcsubjectcollect.setSubjectid(id);
		nlcsubjectcollect.setLoginaccount(loginAccount);
		nlcsubjectcollect.setTime(new Date());
		nlcsubjectcollectMapper.insert(nlcsubjectcollect);
		
		extNlcsubjectMapper.updateCollectCount(id);
	}

	@Override
	public void cancelCollect(String id, String loginAccount) {
		if(StringUtils.isBlank(id)) {
			return ;
		}
		List<String> list = java.util.Arrays.asList(id.split(","));
		if(null == list || list.size() == 0) {
			return ;
		}
		
		NlcsubjectcollectExample example = new NlcsubjectcollectExample();
		cn.gov.nlc.pojo.NlcsubjectcollectExample.Criteria criteria = example.createCriteria();
		criteria.andSubjectidIn(list);
		criteria.andLoginaccountEqualTo(loginAccount);
		int res = nlcsubjectcollectMapper.deleteByExample(example);
		if(res != 0) {
			extNlcsubjectMapper.updateCollectCountDecrease(list);
		}
	}

	@Override
	public List<NlcsubjectExt> getUserCollect(String loginAccount) {
		return extNlcsubjectMapper.getMyCollect(loginAccount);
	}

	@Override
	public List<NlcsubjectExt> getUserPraise(String loginAccount) {
		return extNlcsubjectMapper.getMyPraise(loginAccount);
	}

	@Override
	public void clearPraise(String loginAccount) {
		NlcsubjectpraiseExample example = new NlcsubjectpraiseExample();
		cn.gov.nlc.pojo.NlcsubjectpraiseExample.Criteria criteria = example.createCriteria();
		criteria.andLoginaccountEqualTo(loginAccount);
		nlcsubjectpraiseMapper.deleteByExample(example);
	}

	@Override
	public boolean isExistPraise(String id, String loginAccount) {
		NlcsubjectpraiseExample example = new NlcsubjectpraiseExample();
		cn.gov.nlc.pojo.NlcsubjectpraiseExample.Criteria criteria = example.createCriteria();
		criteria.andSubjectidEqualTo(id);
		criteria.andLoginaccountEqualTo(loginAccount);
		if (nlcsubjectpraiseMapper.countByExample(example) > 0)
			return true;
		return false;
	}

	@Override
	public void addSubjectPraise(String id, String loginAccount) {
		Nlcsubjectpraise nlcsubjectpraise = new Nlcsubjectpraise();
		nlcsubjectpraise.setSubjectid(id);
		nlcsubjectpraise.setLoginaccount(loginAccount);
		nlcsubjectpraise.setTime(new Date());
		nlcsubjectpraiseMapper.insert(nlcsubjectpraise);

	}

	@Override
	public void cancleSubjectPraise(String id, String loginAccount) {
		List<String> list = java.util.Arrays.asList(id.split(","));
		NlcsubjectpraiseExample example = new NlcsubjectpraiseExample();
		cn.gov.nlc.pojo.NlcsubjectpraiseExample.Criteria criteria = example.createCriteria();
		criteria.andSubjectidIn(list);
		criteria.andLoginaccountEqualTo(loginAccount);
		nlcsubjectpraiseMapper.deleteByExample(example);
	}

	@Override
	public void updatePraiseCount(String subjectId) {
		extNlcsubjectMapper.updatePraiseCount(subjectId);

	}

	@Override
	public List<Nlcsubject> seaSubjectList(Integer page, Integer rows, String keyword) {
		int iStart = (page - 1) * rows;
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("start", iStart);
		paramMap.put("length", rows);
		paramMap.put("keyword", keyword);
		return extNlcsubjectMapper.seaSubjectList(paramMap);
	}

	@Override
	public List<Wjreader> seaWenJinList(Integer page, Integer rows, String keyword) {
		int iStart = (page - 1) * rows;
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("start", iStart);
		paramMap.put("length", rows);
		paramMap.put("keyword", keyword);
		return extWjreaderMapper.seaWenJinList(paramMap);
	}

	/**
	 * 特色专题上下架
	 */
	@Override
	public void shelfAds(Integer id) {
		Nlcsubject nlcsubject = nlcsubjectMapper.selectByPrimaryKey(id);
		String status = nlcsubject.getStatus();
		if ("未发布".equals(status)) {
			nlcsubject.setStatus("已发布");
			nlcsubject.setPubtime(new Date());
		} else {
			nlcsubject.setStatus("未发布");
			nlcsubject.setPubtime(null);
			nlcsubject.setSort(10000); // 下架的时候改为默认值10000
		}

		nlcsubjectMapper.updateByPrimaryKey(nlcsubject);
		if ("已发布".equals(nlcsubject.getStatus())) { // 已发布状态，才插入站内信、弹窗表
			sysmessageService.insertMessageThfour(nlcsubject.getPushmethod(), Byte.valueOf("0"), "",
					nlcsubject.getSubjectid(), new Date(), nlcsubject.getCreater(), nlcsubject.getTitle(), "subject");
			syswindowService.insertWindowThfour(nlcsubject.getPushmethod(), Byte.valueOf("7"), nlcsubject.getTitle(),
					nlcsubject.getSubjectid(), new Date(), nlcsubject.getCreater());
		}

		String csql = "select count(1) from nlcsubject where sort < 10000";
		int result = jdbcTemplate.queryForInt(csql);

		if (result < 4) {
			String msql = "select max(sort) from nlcsubject where sort < 10000";
			int maxnum = jdbcTemplate.queryForInt(msql);

			for (int i = 1; i <= (4 - result); i++) {
				++maxnum;

				String sqls = "SELECT id FROM `nlcsubject` where status = '已发布' and sort = 10000 order by pubtime desc limit 1";

				List<Object> res = jdbcTemplate.query(sqls, new RowMapper<Object>() {

					@Override
					public Object mapRow(ResultSet rs, int arg1) throws SQLException {
						return rs.getObject(1);
					}

				});

				if (res != null && res.size() > 0) {
					Long mid = (Long) res.get(0);
					// 设置最新添加的已发布的专题为置顶项
					String sql1 = "update nlcsubject set sort = " + maxnum + " where id = " + mid;
					jdbcTemplate.execute(sql1);
				}
			}

		}

		recommendRank();
	}

	// 推荐排序
	private void recommendRank() {
		String sql = "select max(sort) from nlcsubject where sort < 10000";
		int maxnum = jdbcTemplate.queryForInt(sql);

		if (maxnum > 4) {
			String sqlx = "select id from nlcsubject where status = '已发布' and sort < 10000 order by sort asc";
			List<Object> res = jdbcTemplate.query(sqlx, new RowMapper<Object>() {
				@Override
				public Object mapRow(ResultSet rs, int arg1) throws SQLException {
					return rs.getObject(1);
				}

			});

			if (res != null && res.size() > 0) {
				for (int i = 0; i < res.size(); i++) {
					Long mid = (Long) res.get(i);
					String sql1 = "update nlcsubject set sort = " + (i + 1) + " where id = " + mid;
					jdbcTemplate.execute(sql1);

				}
			}

		}

	}

	@Override
	public List<Nlcsubject> getIndexSubject() {
		Subindexnum po = subindexnumService.getpo();
		return extNlcsubjectMapper.getIndexSubject(po.getNum());
	}

	public int getPraiseCount(String subjectId) {
		NlcsubjectExample example = new NlcsubjectExample();
		cn.gov.nlc.pojo.NlcsubjectExample.Criteria criteria = example.createCriteria();
		criteria.andSubjectidEqualTo(subjectId);
		List<Nlcsubject> list = nlcsubjectMapper.selectByExampleWithBLOBs(example);
		if (null != list && list.size() > 0) {
			return list.get(0).getPraisecount();
		}
		return 0;
	}

	/**
	 * 获取全部的专题
	 */
	@Override
	public List<Nlcsubject> getAllList() {
		NlcsubjectExample example = new NlcsubjectExample();
		cn.gov.nlc.pojo.NlcsubjectExample.Criteria criteria = example.createCriteria();
		criteria.andStatusEqualTo("已发布");
		List<Nlcsubject> list = nlcsubjectMapper.selectByExample(example);
		return list;
	}

	/**
	 * 专题排序的保存
	 * 
	 * @param id
	 * @param sort
	 */
	@Override
	public void sortSubject(Integer id, Integer sort) {
		String sqls = "select id from nlcsubject where sort = " + sort;
		List<Object> res = jdbcTemplate.query(sqls, new RowMapper<Object>() {

			@Override
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				return rs.getObject(1);
			}

		});

		Long oldid = null;
		if (res != null && res.size() > 0) {
			oldid = (Long) res.get(0);
			if (id.intValue() == oldid.intValue()) {
				return;
			}
		}

		Nlcsubject subject = new Nlcsubject();
		subject.setId(id);
		subject.setSort(sort);
		nlcsubjectMapper.updateByPrimaryKeySelective(subject);

		if (oldid != null) {
			Nlcsubject oldsubject = new Nlcsubject();
			oldsubject.setId(Integer.valueOf(oldid + ""));
			oldsubject.setSort(10000);
			nlcsubjectMapper.updateByPrimaryKeySelective(oldsubject);
		}
	}

	@Override
	public JSONObject pull() {
		JSONObject result = new JSONObject();
		
		int curpage = 1;
		boolean bcontinue = true;
		while (bcontinue) {
			String strSubject = Common.sendPost("http://m.nlc.cn/nlcm/client/subjectL?page=" + curpage + "&rows=10",
					"");

			JSONArray arrSubject = null;
			if (StringUtils.isNotBlank(strSubject)) {
				arrSubject = JSONArray.fromObject(strSubject);
			} else {
				result.put("result", false);
				result.put("message", "接口连接失败");
				return result;
			}

			if (arrSubject != null && arrSubject.size() > 0) {
				for (int i = 0; i < arrSubject.size(); i++) {
					JSONObject jsonSubject = JSONObject.fromObject(arrSubject.get(i));

					Nlcsubject nlcsubject = new Nlcsubject();
					nlcsubject.setSubjectid(jsonSubject.getString("id"));
					if (jsonSubject.getString("intro").length() > 5) {
						nlcsubject.setIntroduce(
								jsonSubject.getString("intro").replace("\n", "").replace("\r", "").replace("\n\r", ""));
					}
					nlcsubject.setTitle(jsonSubject.getString("title"));
					nlcsubject.setCreatetime(new Date());
					nlcsubject.setSource("手机门户");
					nlcsubject.setStatus("未发布");
					nlcsubject.setPraisecount(praiseCountBySubjectid(nlcsubject.getSubjectid()));
					nlcsubject.setCollectcount(collectCountBySubjectid(nlcsubject.getSubjectid()));
					nlcsubject.setSort(10000);
					try {
						if (getNlcSubjectBySubjectId(nlcsubject.getSubjectid()) != null) {
							bcontinue = false;
							break;
						}
						inertNlcsubjectAndCatalogRoot(nlcsubject);
					} catch (Exception e) {
						logger.error(e.getMessage(), e);
						continue;
					}

					Nlcsubjectcatalog nlcsubcata = new Nlcsubjectcatalog();
					nlcsubcata.setCseq(1);
					nlcsubcata.setSubjectid(jsonSubject.getString("id"));
					nlcsubcata.setCatalogid(UUIDGenerator.getUUID());
					nlcsubcata.setPid("root");
					nlcsubcata.setTitle("简介");
					nlcsubcata.setState("true");
					nlcsubcata.setIsdir("0");
					nlcsubcata.setStatus("已上架");
					String introduce = nlcsubject.getIntroduce();
					if (StringUtils.isNotBlank(introduce)) {
						nlcsubcata.setContent(introduce);
					} else {
						nlcsubcata.setContent("");
					}
					nlcsubjectcatalogService.insertCatalog(nlcsubcata);

					String strCatalog = Common.sendPost(
							"http://m.nlc.cn/nlcm/client/subjectCatalog?id=" + jsonSubject.getString("id"), "");
					JSONArray arrCatalog = null;
					if (StringUtils.isNotBlank(strCatalog)) {
						arrCatalog = JSONArray.fromObject(strCatalog);
					} else {
						continue;
					}

					if (null != arrCatalog && arrCatalog.size() > 0) {
						int k = 2;
						for (int j = 0; j < arrCatalog.size(); j++) {
							JSONObject jsonCatalog = JSONObject.fromObject(arrCatalog.get(j));
							Nlcsubjectcatalog nlcsubjectcatalog = new Nlcsubjectcatalog();
							nlcsubjectcatalog.setCseq(k);
							k++;
							nlcsubjectcatalog.setSubjectid(jsonSubject.getString("id"));
							nlcsubjectcatalog.setCatalogid(jsonCatalog.getString("id"));
							nlcsubjectcatalog.setPid(Common.IsNullOrEmpty(jsonCatalog.getString("pId")) ? "root"
									: jsonCatalog.getString("pId"));

							nlcsubjectcatalog.setTitle(jsonCatalog.getString("name"));
							nlcsubjectcatalog.setState(jsonCatalog.getString("open"));
							String strContent = Common.sendPost(
									"http://m.nlc.cn/nlcm/client/subjectContent?id=" + jsonCatalog.getString("id"), "");

							String con = "";
							if (StringUtils.isNotBlank(strContent)) {
								JSONObject jsonContent = JSONObject.fromObject(strContent);
								con = jsonContent.containsKey("content") ? jsonContent.getString("content") : "";
								con = con.replace("wap.nlc.gov.cn", "wap.nlc.cn");
							}

							nlcsubjectcatalog.setContent(con);
							nlcsubjectcatalog.setIsdir("0");
							nlcsubjectcatalog.setStatus("已上架");
							try {
								nlcsubjectcatalogService.insertCatalog(nlcsubjectcatalog);
							} catch (Exception e) {
								logger.error(e.getMessage(), e);
							}
						}
					}
				}
			} else {
				break;
			}

			curpage++;
		}

		updateSubChildDir();
		backSearch();

		result.put("result", true);
		return result;
	}

	//修改专题的isdir状态
	public void updateSubChildDir() {
		List<Nlcsubjectcatalog> catalist = nlcsubjectcatalogService.getAllWithoutRoot();
		if (null != catalist && catalist.size() > 0) {
			for (Nlcsubjectcatalog nlcsubjectcatalog : catalist) {
				nlcsubjectcatalogService.judgeIsDir(nlcsubjectcatalog.getSubjectid(), nlcsubjectcatalog.getCatalogid());
			}
		}
	}

	//专题leaforder
	public void backSearch() {
		nlcsubjectcatalogService.backSearch();
	}
	
	private int collectCountBySubjectid(String subjectid) {
		NlcsubjectcollectExample example = new NlcsubjectcollectExample();
		cn.gov.nlc.pojo.NlcsubjectcollectExample.Criteria criteria = example.createCriteria();
		criteria.andSubjectidEqualTo(subjectid);
		int res = nlcsubjectcollectMapper.countByExample(example);
		return res;
	}
	
	private int praiseCountBySubjectid(String subjectid) {
		NlcsubjectpraiseExample example = new NlcsubjectpraiseExample();
		cn.gov.nlc.pojo.NlcsubjectpraiseExample.Criteria criteria = example.createCriteria();
		criteria.andSubjectidEqualTo(subjectid);
		int res = nlcsubjectpraiseMapper.countByExample(example);
		return res;
	}
}
