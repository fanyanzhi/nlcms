package cn.gov.nlc.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.gov.nlc.base.converter.BeanDateConnverter;
import cn.gov.nlc.mapper.SysmessageMapper;
import cn.gov.nlc.mapper.SysmessageflagMapper;
import cn.gov.nlc.pojo.Nlcnews;
import cn.gov.nlc.pojo.Sysmessage;
import cn.gov.nlc.pojo.SysmessageExample;
import cn.gov.nlc.pojo.SysmessageExt;
import cn.gov.nlc.pojo.SysmessageExample.Criteria;
import cn.gov.nlc.pojo.Sysmessageflag;
import cn.gov.nlc.service.SysmessageService;
import cn.gov.nlc.vo.EasyUiDataGridResult;

@Service
public class SysmessageServiceImpl implements SysmessageService {

	@Autowired
	SysmessageMapper sysmessageMapper;

	@Autowired
	SysmessageflagMapper sysmessageflagMapper;

	/**
	 * 插入站内信信息 type
	 * 0:管理员群发信息；1：交易记录；2：图书催还；3：违规：预约未取、逾期没还，这个还得测试：图书催还和预约未还都需要跑任务计划
	 * 4：新闻；5：公告；6：讲座；7：专题' //2016-11-13晚
	 * 4,5,6,7暂时归到管理员群发信息里面。app前台没有专门的针对4，5，6的展示，暂时先换成0（群发信息）
	 * 8是意见回复
	 */
	@Override
	public void insertSysmessage(int type, String message, String loginAccount) {
		Sysmessage sysmessage = new Sysmessage();
		sysmessage.setMessage(message);
		sysmessage.setType((byte) type);
		sysmessage.setUsername(loginAccount);
		sysmessage.setTime(new Date());
		sysmessageMapper.insert(sysmessage);
	}

	@Override
	public int getSysmessageCountByType(int type, String clientid) {
		SysmessageExample example = new SysmessageExample();
		Criteria criteria = example.createCriteria();
		criteria.andTypeEqualTo((byte) type);
		Date timestamp = getTimeFlag(type, clientid);
		if (timestamp != null) {	//若不为空，则时间要比上次的大
			criteria.andTimeGreaterThan(timestamp);
		}else if(type == 0) {	//若为空，且获取的是管理员群发信息，则要近半年的
			criteria.andTimeGreaterThan((new DateTime()).minusMonths(6).toDate());
		}
		return sysmessageMapper.countByExample(example);
	}
	
	@Override
	public int getSysmessageCountByTypeAndAccount(int type, String loginAccount, String clientid) {
		SysmessageExample example = new SysmessageExample();
		Criteria criteria = example.createCriteria();
		criteria.andUsernameEqualTo(loginAccount);
		criteria.andTypeEqualTo((byte) type);
		Date timestamp = getTimeFlag(type, clientid);
		if (timestamp != null) {	
			criteria.andTimeGreaterThan(timestamp);
		}
		return sysmessageMapper.countByExample(example);
	}


	/**
	 * 
	 */
	@Override
	public List<Sysmessage> getSysmessage(int type, String clientid) {
		SysmessageExample example = new SysmessageExample();
		Criteria criteria = example.createCriteria();
		criteria.andTypeEqualTo((byte) type);
		Date timestamp = getTimeFlag(type, clientid);
		if (timestamp != null) {	//若不为空，则时间要比上次的大
			criteria.andTimeGreaterThan(timestamp);
		}else if(type == 0) {	//若为空，且获取的是管理员群发信息，则要近半年的
			criteria.andTimeGreaterThan((new DateTime()).minusMonths(6).toDate());
		}
		List<Sysmessage> list = sysmessageMapper.selectByExample(example);
		if (timestamp != null)
			updateTimeFlag(type, clientid);
		else
			insertSysmessageFlag(type, clientid);
		return list;
	}
	
	@Override
	public List<Sysmessage> getSysmessageByAccount(int type, String loginAccount, String clientid) {
		SysmessageExample example = new SysmessageExample();
		Criteria criteria = example.createCriteria();
		criteria.andUsernameEqualTo(loginAccount);
		criteria.andTypeEqualTo((byte) type);
		Date timestamp = getTimeFlag(type, clientid);
		if (timestamp != null)
			criteria.andTimeGreaterThan(timestamp);
		List<Sysmessage> list = sysmessageMapper.selectByExample(example);
		if (timestamp != null)
			updateTimeFlag(type, clientid);
		else
			insertSysmessageFlag(type, clientid);
		return list;
	}


	/**
	 * 获取上次获取时间
	 * 
	 * @param type
	 * @param loginAccount
	 * @return
	 */
	public Date getTimeFlag(int type, String clientid) {
		cn.gov.nlc.pojo.SysmessageflagExample example = new cn.gov.nlc.pojo.SysmessageflagExample();
		cn.gov.nlc.pojo.SysmessageflagExample.Criteria criteria = example.createCriteria();
		criteria.andTypeEqualTo((byte) type);
		criteria.andClientidEqualTo(clientid);
		List<Sysmessageflag> list = sysmessageflagMapper.selectByExample(example);
		if (list != null && list.size() > 0)
			return list.get(0).getUpdatetime();
		return null;
	}

	/**
	 * 更新timeflag
	 * 
	 * @param type
	 * @param loginAccount
	 */
	public void updateTimeFlag(int type, String clientid) {
		Sysmessageflag record = new Sysmessageflag();
		record.setTime(new Date());
		record.setUpdatetime(new Date());
		cn.gov.nlc.pojo.SysmessageflagExample example = new cn.gov.nlc.pojo.SysmessageflagExample();
		cn.gov.nlc.pojo.SysmessageflagExample.Criteria criteria = example.createCriteria();
		criteria.andTypeEqualTo((byte) type);
		criteria.andClientidEqualTo(clientid);
		sysmessageflagMapper.updateByExampleSelective(record, example);
	}

	/**
	 * 插入timeflag
	 * 
	 * @param sysmessageflag
	 */
	public void insertSysmessageFlag(int type, String clientid) {
		Sysmessageflag sysmessageflag = new Sysmessageflag();
		sysmessageflag.setType((byte) type);
		sysmessageflag.setClientid(clientid);
		sysmessageflag.setTime(new Date());
		sysmessageflag.setUpdatetime(new Date());
		sysmessageflagMapper.insert(sysmessageflag);
	}

	/**
	 * 站内信展示的数据
	 */
	@Override
	public EasyUiDataGridResult getmessageList(Integer page, Integer rows, SysmessageExt sysmessageExt) {
		String username = sysmessageExt.getUsername();
		String pubname = sysmessageExt.getPubname();
		Byte type = sysmessageExt.getType();
		Date ztime = sysmessageExt.getZtime();
		Date ytime = sysmessageExt.getYtime();

		// 分页
		PageHelper.startPage(page, rows);

		SysmessageExample example = new SysmessageExample();
		Criteria criteria = example.createCriteria();

		if (StringUtils.isNotBlank(pubname)) {
			criteria.andPubnameEqualTo(pubname);
		}

		if (StringUtils.isNotBlank(username)) {
			criteria.andUsernameEqualTo(username);
		}

		if (null != type) {
			criteria.andTypeEqualTo(type);
		}

		if (null != ztime && null != ytime) {
			criteria.andTimeBetween(ztime, ytime);
		} else if (null != ztime) {
			criteria.andTimeGreaterThanOrEqualTo(ztime);
		} else if (null != ytime) {
			criteria.andTimeLessThanOrEqualTo(ytime);
		}

		example.setOrderByClause("time desc");
		List<Sysmessage> list = sysmessageMapper.selectByExample(example);
		PageInfo<Sysmessage> pageInfo = new PageInfo<>(list);
		long total = pageInfo.getTotal();
		EasyUiDataGridResult result = new EasyUiDataGridResult(total, list);
		return result;
	}

	/**
	 * 通过主键删除站内信
	 */
	@Override
	public int deleteSingleById(Integer id) {
		int result = sysmessageMapper.deleteByPrimaryKey(id);
		return result;
	}

	/**
	 * 在后台插入站内信，类型都是0
	 */
	@Override
	public void insertMessage(Sysmessage sysmessage) {
		sysmessageMapper.insert(sysmessage);
	}

	/**
	 * 通过主键查询
	 */
	@Override
	public Sysmessage selectByPrimaryKey(Integer id) {
		Sysmessage sysmessage = sysmessageMapper.selectByPrimaryKey(id);
		return sysmessage;
	}

	/**
	 * 修改站内信
	 */
	@Override
	public void updateMessage(Map<String, String[]> parameterMap, String pubname) throws Exception {
		String[] ids = parameterMap.get("id");
		Integer id = Integer.parseInt(ids[0]);
		Sysmessage oldSysmessage = sysmessageMapper.selectByPrimaryKey(id);
		ConvertUtils.register(new BeanDateConnverter(), Date.class);
		BeanUtils.populate(oldSysmessage, parameterMap);
		oldSysmessage.setPubname(pubname);
		oldSysmessage.setTime(new Date());
		sysmessageMapper.updateByPrimaryKey(oldSysmessage);
	}

	/**
	 * 通过4大类插入站内信(在4大类添加的时候)
	 * type:'0:管理员群发信息；1：交易记录；2：图书催还；3：违规：预约未取、逾期没还，这个还得测试；4：新闻；5：公告；6：讲座；7：专题',
	 */
	@Override
	public void insertMessageThfour(String pushmethod, Byte type, String message, String fid, Date time, String pubname, String title, String sort) {
		if (StringUtils.isNotBlank(pushmethod)) {
			String[] sarr = pushmethod.split(",");
			for (String str : sarr) {
				if ("1".equals(str)) { // 1是站内信，0是弹窗
					Sysmessage sysmessage = new Sysmessage();
					sysmessage.setType(type);
					sysmessage.setMessage(message);
					sysmessage.setPubname(pubname);
					sysmessage.setUsername("全体读者");
					sysmessage.setFid(fid);
					sysmessage.setTime(time);
					sysmessage.setTitle(title);
					sysmessage.setSort(sort);
					sysmessageMapper.insert(sysmessage);
				}
			}
		}
	}

	@Override
	public void insertSysmessageObject(Sysmessage sysmessage) {
		sysmessageMapper.insert(sysmessage);
	}

}
