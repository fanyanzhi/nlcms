package cn.gov.nlc.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.gov.nlc.base.converter.BeanDateConnverter;
import cn.gov.nlc.mapper.ExtNlctrailerMapper;
import cn.gov.nlc.mapper.NlctrailerMapper;
import cn.gov.nlc.mapper.NlctrailercollectMapper;
import cn.gov.nlc.mapper.NlctrailerpraiseMapper;
import cn.gov.nlc.pojo.Nlcnotice;
import cn.gov.nlc.pojo.NlcnoticeExample;
import cn.gov.nlc.pojo.NlcnoticeExt;
import cn.gov.nlc.pojo.NlcnoticepraiseExample;
import cn.gov.nlc.pojo.Nlctrailer;
import cn.gov.nlc.pojo.NlctrailerExample;
import cn.gov.nlc.pojo.NlctrailerExample.Criteria;
import cn.gov.nlc.pojo.NlctrailerExt;
import cn.gov.nlc.pojo.Nlctrailercollect;
import cn.gov.nlc.pojo.NlctrailercollectExample;
import cn.gov.nlc.pojo.Nlctrailerpraise;
import cn.gov.nlc.pojo.NlctrailerpraiseExample;
import cn.gov.nlc.service.NlctrailerService;
import cn.gov.nlc.service.SysmessageService;
import cn.gov.nlc.service.SyswindowService;
import cn.gov.nlc.test.mobileapi;
import cn.gov.nlc.util.Common;
import cn.gov.nlc.vo.EasyUiDataGridResult;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service
public class NlctrailerServiceImpl implements NlctrailerService {

	private static final Logger logger = Logger.getLogger(cn.gov.nlc.service.impl.NlctrailerServiceImpl.class);
	
	@Autowired
	private NlctrailerMapper nlctrailerMapper;

	@Autowired
	private NlctrailercollectMapper nlctrailercollectMapper;

	@Autowired
	private NlctrailerpraiseMapper nlctrailerpraiseMapper;

	@Autowired
	private ExtNlctrailerMapper extNlctrailerMapper;
	
	@Autowired
	private SysmessageService sysmessageService;
	@Autowired
	private SyswindowService syswindowService;

	/**
	 * 
	 * 讲座分页展示
	 */
	@Override
	public EasyUiDataGridResult getTrailerList(Integer page, Integer rows, NlctrailerExt nlctrailerExt, String sort, String order) {
		String title = nlctrailerExt.getTitle();
		String speakername = nlctrailerExt.getSpeakername();
		String status = nlctrailerExt.getStatus();
		String guanqu = nlctrailerExt.getGuanqu();
		String columnid = nlctrailerExt.getColumnid();
		String source = nlctrailerExt.getSource();

		Date zstarttime = nlctrailerExt.getZstarttime();
		Date ystarttime = nlctrailerExt.getYstarttime();

		Date zendtime = nlctrailerExt.getZendtime();
		Date yendtime = nlctrailerExt.getYendtime();

		Date zcretime = nlctrailerExt.getZcretime();
		Date ycretime = nlctrailerExt.getYcretime();

		// 分页
		PageHelper.startPage(page, rows);
		NlctrailerExample example = new NlctrailerExample();
		Criteria criteria = example.createCriteria();

		if (StringUtils.isNotBlank(title)) {
			criteria.andTitleLike("%" + title + "%");
		}

		if (StringUtils.isNotBlank(speakername)) {
			criteria.andSpeakernameLike("%" + speakername + "%");
		}

		if (StringUtils.isNotBlank(status)) {
			criteria.andStatusEqualTo(status);
		}

		if (StringUtils.isNotBlank(source)) {
			criteria.andSourceEqualTo(source);
		}

		if (StringUtils.isNotBlank(columnid)) {
			criteria.andColumnidEqualTo(columnid);
		}

		if (StringUtils.isNotBlank(guanqu)) {
			criteria.andGuanquEqualTo(guanqu);
		}

		if (null != zcretime && null != ycretime) {
			criteria.andTimeBetween(zcretime, ycretime);
		} else if (null != zcretime) {
			criteria.andTimeGreaterThanOrEqualTo(zcretime);
		} else if (null != ycretime) {
			criteria.andTimeLessThanOrEqualTo(ycretime);
		}

		if (null != zstarttime && null != ystarttime) {
			criteria.andStarttimeBetween(zstarttime, ystarttime);
		} else if (null != zstarttime) {
			criteria.andStarttimeGreaterThanOrEqualTo(zstarttime);
		} else if (null != ystarttime) {
			criteria.andStarttimeLessThanOrEqualTo(ystarttime);
		}

		if (null != zendtime && null != yendtime) {
			criteria.andEndtimeBetween(zendtime, yendtime);
		} else if (null != zendtime) {
			criteria.andEndtimeGreaterThanOrEqualTo(zendtime);
		} else if (null != yendtime) {
			criteria.andEndtimeLessThanOrEqualTo(yendtime);
		}

		if(StringUtils.isNotBlank(sort)) {
			example.setOrderByClause(sort + " " + order);
		}else {
			example.setOrderByClause("starttime desc");
		}

		List<Nlctrailer> list = nlctrailerMapper.selectByExample(example);
		PageInfo<Nlctrailer> pageInfo = new PageInfo<>(list);
		long total = pageInfo.getTotal();
		EasyUiDataGridResult result = new EasyUiDataGridResult(total, list);

		return result;
	}

	/**
	 * 删除讲座，单个删除
	 */
	@Override
	public int deleteSingleById(Integer id) {
		int result = nlctrailerMapper.deleteByPrimaryKey(id);
		return result;
	}

	/**
	 * 查询所有讲座信息
	 */
	@Override
	public List<Nlctrailer> getAll() {
		NlctrailerExample example = new NlctrailerExample();
		example.setOrderByClause("time desc");
		List<Nlctrailer> list = nlctrailerMapper.selectByExample(example);
		return list;
	}

	/**
	 * 添加讲座信息
	 */
	@Override
	public void insertNlctrailer(Nlctrailer nlctrailer) {
		nlctrailerMapper.insert(nlctrailer);
	}

	/**
	 * 通过主键查询
	 */
	@Override
	public Nlctrailer selectByPrimaryKey(Integer id) {
		Nlctrailer nlctrailer = nlctrailerMapper.selectByPrimaryKey(id);
		return nlctrailer;
	}

	/**
	 * 修改讲座
	 */
	@Override
	public void updateTrailer(Map<String, String[]> parameterMap, String username) throws Exception {
		String[] ids = parameterMap.get("id");
		String[] pushmethods = parameterMap.get("pushmethod");
		Integer id = Integer.parseInt(ids[0]);
		String newpushmethod = pushmethods[0];	//新的推送方式
		Nlctrailer nlctrailer = nlctrailerMapper.selectByPrimaryKey(id);
		String oldpushmethod = nlctrailer.getPushmethod();	//旧的推送方式
		ConvertUtils.register(new BeanDateConnverter(), Date.class);
		BeanUtils.populate(nlctrailer, parameterMap);
		String[] str1 = parameterMap.get("starttime");
		String[] str2 = parameterMap.get("endtime");
		String s1 = str1[0];
		String s2 = str2[0];
		if (s1 != null && !"".equals(s1) && s2 != null && !"".equals(s2)) {
			String mergeTime = Common.mergeTime(s1, s2);
			nlctrailer.setSpeaktime(mergeTime);
		}

		nlctrailerMapper.updateByPrimaryKey(nlctrailer);
//		String resmethod = remainMethod(newpushmethod, oldpushmethod);
		
		if("已发布".equals(nlctrailer.getStatus())) {	//已发布状态，才插入站内信、弹窗表
		sysmessageService.insertMessageThfour(newpushmethod, Byte.valueOf("0"), "", nlctrailer.getTrailerid(), new Date(), username, nlctrailer.getTitle(), "trailer");
		syswindowService.insertWindowThfour(newpushmethod, Byte.valueOf("6"), "讲座预告："+nlctrailer.getTitle(), nlctrailer.getTrailerid(), new Date(), username);
		}
	}
	
	//推送方式只会增加，不会减少
		private String remainMethod(String newpushmethod, String oldpushmethod) {
			if(StringUtils.isBlank(oldpushmethod)) {
				return newpushmethod;
			}
			String[] oldarr = oldpushmethod.split(","); //旧的
			List<String> oldlist = new ArrayList(Arrays.asList(oldarr));
			String[] newarr = newpushmethod.split(",");
			List<String> newlist = new ArrayList(Arrays.asList(newarr));
			for (String str : oldlist) {
				if(newlist.contains(str)) {
					newlist.remove(str);
				}
			}
			
			String res = "";
			if(null != newlist && newlist.size() > 0) {
				for (String s : newlist) {
					res += s + ",";
				}
				res = res.substring(0, res.length() - 1);
			}
			return res;
		}

	public EasyUiDataGridResult getAppTrailer(Integer page, Integer rows, String guanqu, Integer days) {

		// 分页
		PageHelper.startPage(page, rows);
		NlctrailerExample example = new NlctrailerExample();
		Criteria criteria = example.createCriteria();
		if (StringUtils.isNotBlank(guanqu)) {
			criteria.andGuanquEqualTo(guanqu);
		}
		criteria.andStatusEqualTo("已发布");
		SimpleDateFormat ssdf = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date curtime = null;
		try {
			curtime = ssdf.parse(ssdf.format(new Date()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		criteria.andStarttimeGreaterThanOrEqualTo(curtime);
		
		if (days != 0) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			java.util.Date time = null;
			try {
				time = sdf.parse(sdf.format(new Date()));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			Date d = new Date();
			try {
				criteria.andStarttimeBetween(time,
						sdf.parse(sdf.format(new Date(d.getTime() + (long) days * 24 * 60 * 60 * 1000))));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		example.setOrderByClause("starttime asc");
		List<Nlctrailer> list = nlctrailerMapper.selectByExample(example);
		PageInfo<Nlctrailer> pageInfo = new PageInfo<>(list);
		long total = pageInfo.getTotal();
		EasyUiDataGridResult result = new EasyUiDataGridResult(total, list);

		return result;
	}

	@Override
	public Nlctrailer selectByTrailerId(String trailerId) {
		NlctrailerExample example = new NlctrailerExample();
		Criteria criteria = example.createCriteria();
		criteria.andTraileridEqualTo(trailerId);
		List<Nlctrailer> list = nlctrailerMapper.selectByExample(example);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
	
	@Override
	public Nlctrailer selectByTrailerTitle(String title) {
		NlctrailerExample example = new NlctrailerExample();
		Criteria criteria = example.createCriteria();
		criteria.andTitleEqualTo(title);
		List<Nlctrailer> list = nlctrailerMapper.selectByExample(example);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public boolean isExistCollect(String id, String loginAccount) {
		NlctrailercollectExample example = new NlctrailercollectExample();
		cn.gov.nlc.pojo.NlctrailercollectExample.Criteria criteria = example.createCriteria();
		criteria.andTraileridEqualTo(id);
		criteria.andLoginaccountEqualTo(loginAccount);
		if (nlctrailercollectMapper.countByExample(example) > 0)
			return true;
		return false;

	}

	@Override
	public void addTrailerCollect(String id, String loginAccount) {
		Nlctrailer oldtrail = selectByTrailerId(id);
		
		Nlctrailercollect nlctrailercollect = new Nlctrailercollect();
		nlctrailercollect.setTrailerid(id);
		nlctrailercollect.setLoginaccount(loginAccount);
		nlctrailercollect.setTime(new Date());
		nlctrailercollect.setTitle(oldtrail.getTitle());
		nlctrailercollectMapper.insert(nlctrailercollect);
		
		extNlctrailerMapper.updateCollectCount(id);
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
		
		NlctrailercollectExample example = new NlctrailercollectExample();
		cn.gov.nlc.pojo.NlctrailercollectExample.Criteria criteria = example.createCriteria();
		criteria.andTraileridIn(list);
		criteria.andLoginaccountEqualTo(loginAccount);
		int res = nlctrailercollectMapper.deleteByExample(example);
		if(0 != res) {
			extNlctrailerMapper.updateCollectCountDecrease(list);
		}
	}

	@Override
	public List<NlctrailerExt> getUserCollect(String loginAccount) {
		return extNlctrailerMapper.getMyCollect(loginAccount);
	}

	@Override
	public List<NlctrailerExt> getUserPraise(String loginAccount) {
		return extNlctrailerMapper.getMyPraise(loginAccount);
	}

	@Override
	public void clearPraise(String loginAccount) {
		NlctrailerpraiseExample example = new NlctrailerpraiseExample();
		cn.gov.nlc.pojo.NlctrailerpraiseExample.Criteria criteria = example.createCriteria();
		criteria.andLoginaccountEqualTo(loginAccount);
		nlctrailerpraiseMapper.deleteByExample(example);
	}

	@Override
	public boolean isExistPraise(String id, String loginAccount) {
		NlctrailerpraiseExample example = new NlctrailerpraiseExample();
		cn.gov.nlc.pojo.NlctrailerpraiseExample.Criteria criteria = example.createCriteria();
		criteria.andTraileridEqualTo(id);
		criteria.andLoginaccountEqualTo(loginAccount);
		if (nlctrailerpraiseMapper.countByExample(example) > 0)
			return true;
		return false;
	}

	@Override
	public void addTrailerPraise(String id, String loginAccount) {
		Nlctrailer oldtrail = selectByTrailerId(id);
		
		Nlctrailerpraise nlctrailerpraise = new Nlctrailerpraise();
		nlctrailerpraise.setTrailerid(id);
		nlctrailerpraise.setLoginaccount(loginAccount);
		nlctrailerpraise.setTime(new Date());
		nlctrailerpraise.setTitle(oldtrail.getTitle());
		nlctrailerpraiseMapper.insert(nlctrailerpraise);

	}

	@Override
	public void cancleTrailerPraise(String id, String loginAccount) {
		List<String> list = java.util.Arrays.asList(id.split(","));
		NlctrailerpraiseExample example = new NlctrailerpraiseExample();
		cn.gov.nlc.pojo.NlctrailerpraiseExample.Criteria criteria = example.createCriteria();
		criteria.andTraileridIn(list);
		criteria.andLoginaccountEqualTo(loginAccount);
		nlctrailerpraiseMapper.deleteByExample(example);

	}

	@Override
	public void updatePraiseCount(String newsId) {
		extNlctrailerMapper.updatePraiseCount(newsId);
	}

	@Override
	public List<Nlctrailer> seaTrailerList(Integer page, Integer rows, String keyword) {
		int iStart = (page - 1) * rows;
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("start", iStart);
		paramMap.put("length", rows);
		paramMap.put("keyword", keyword);
		return extNlctrailerMapper.seaTrailerList(paramMap);
	}

	@Override
	public Map<String,Object> getIndexTrailer() {
		return extNlctrailerMapper.getIndexTrailer();
	}

	/**
	 * 讲座发布与取消
	 * status 1发布，0取消
	 */
	@Override
	public void publish(Integer id, String status, String username) {
		Nlctrailer nlctrailer = nlctrailerMapper.selectByPrimaryKey(id);
		
		if("1".equals(status)) {
			nlctrailer.setStatus("已发布");
		}else {
			nlctrailer.setStatus("未发布");
		}
		
		nlctrailerMapper.updateByPrimaryKey(nlctrailer);
		if("已发布".equals(nlctrailer.getStatus())) {	//已发布状态，才插入站内信、弹窗表
			sysmessageService.insertMessageThfour(nlctrailer.getPushmethod(), Byte.valueOf("0"), "", nlctrailer.getTrailerid(), new Date(), username, nlctrailer.getTitle(), "trailer");
			syswindowService.insertWindowThfour(nlctrailer.getPushmethod(), Byte.valueOf("6"), nlctrailer.getTitle(), nlctrailer.getTrailerid(), new Date(), username);
		}
	}

	@Override
	public JSONObject pull() {
		JSONObject result = new JSONObject();
		
		int page = 1;
		while (true) {
			String strJzyg = mobileapi.testRPC("getJzygDetailsList", "http://webservice.nlcm.neusoft.com", new Object[] { page, 10 });
			if(StringUtils.isBlank(strJzyg)) {
				result.put("result", false);
				result.put("message", "接口连接失败");
				return result;
			}
			
			JSONArray arrJzyg = JSONArray.fromObject(strJzyg);
			if (arrJzyg.size() > 0) {
				for (int i = 0; i < arrJzyg.size(); i++) {
					JSONObject jsonJzyg = JSONObject.fromObject(arrJzyg.get(i));
					Nlctrailer nlctrailer = new Nlctrailer();
					nlctrailer.setTrailerid(UUID.randomUUID().toString().replace("-", ""));
					nlctrailer.setSpeakername(jsonJzyg.getString("speaker_name"));
					nlctrailer.setSpeaktime(jsonJzyg.get("time").toString().toLowerCase().replace("monday", "周一")
							.replace("tuesday", "周二").replace("wednesday", "周三").replace("thursday", "周四")
							.replace("friday", "周五").replace("saturday", "周六").replace("sunday", "周日"));
					nlctrailer.setTitle(jsonJzyg.getString("title"));
					nlctrailer.setSpeaker(jsonJzyg.getString("speaker"));
					nlctrailer.setPlace(jsonJzyg.getString("place"));
					nlctrailer.setColumnid(jsonJzyg.getString("column_"));
					nlctrailer.setColumnname(jsonJzyg.getString("column_name"));
					nlctrailer.setGuanqu(jsonJzyg.getString("place").indexOf("国家图书馆古籍馆") != -1 ? "古籍馆" : "总馆");
					nlctrailer.setStarttime(Common.getDateFromString(
							jsonJzyg.get("time").toString().substring(0, jsonJzyg.get("time").toString().indexOf("("))
									+ jsonJzyg.get("time").toString().substring(
											jsonJzyg.get("time").toString().indexOf(")") + 1, jsonJzyg.get("time")
													.toString().indexOf("-"))));
					nlctrailer.setEndtime(Common.getDateFromString(
							jsonJzyg.get("time").toString().substring(0, jsonJzyg.get("time").toString().indexOf("("))
									+ " " + jsonJzyg.get("time").toString()
											.substring(jsonJzyg.get("time").toString().indexOf("-") + 1)));
					nlctrailer.setSource("手机门户");
					nlctrailer.setStatus("已发布");
					nlctrailer.setTime(new Date());
					nlctrailer.setPraisecount(praiseCountBytitle(nlctrailer.getTitle()));
					nlctrailer.setCollectcount(collectCountBytitle(nlctrailer.getTitle()));
					try {
						if (selectByTrailerTitle(nlctrailer.getTitle()) != null) // 如果讲座已经存在就不添加
							continue;
						insertNlctrailer(nlctrailer);
					} catch (Exception e) {
						
					}
				}
			} else {
				break;
			}
			page++;
		}
		
		result.put("result", true);
		return result;
	}
	
	private int collectCountBytitle(String title) {
		NlctrailercollectExample example = new NlctrailercollectExample();
		cn.gov.nlc.pojo.NlctrailercollectExample.Criteria criteria = example.createCriteria();
		criteria.andTitleEqualTo(title);
		int res = nlctrailercollectMapper.countByExample(example);
		return res;
	}
	
	private int praiseCountBytitle(String title) {
		NlctrailerpraiseExample example = new NlctrailerpraiseExample();
		cn.gov.nlc.pojo.NlctrailerpraiseExample.Criteria criteria = example.createCriteria();
		criteria.andTitleEqualTo(title);
		int res = nlctrailerpraiseMapper.countByExample(example);
		return res;
	}

}
