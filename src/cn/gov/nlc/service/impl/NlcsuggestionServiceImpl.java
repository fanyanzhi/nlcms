package cn.gov.nlc.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.gov.nlc.mapper.NlcsuggestionMapper;
import cn.gov.nlc.pojo.Nlcsuggestion;
import cn.gov.nlc.pojo.NlcsuggestionExample;
import cn.gov.nlc.pojo.NlcsuggestionExample.Criteria;
import cn.gov.nlc.pojo.NlcsuggestionExt;
import cn.gov.nlc.pojo.Sysmessage;
import cn.gov.nlc.service.NlcsuggestionService;
import cn.gov.nlc.service.SysmessageService;
import cn.gov.nlc.vo.EasyUiDataGridResult;

@Service
public class NlcsuggestionServiceImpl implements NlcsuggestionService{

	@Autowired
	private NlcsuggestionMapper nlcsuggestionMapper;
	@Autowired
	private SysmessageService sysmessageService;

	/**
	 * 意见反馈展示页面
	 * 
	 * @param page
	 * @param rows
	 * @return
	 */
	@Override
	public EasyUiDataGridResult getSuggestionList(int page, int rows, NlcsuggestionExt nlcsuggestionExt) {
		String username = nlcsuggestionExt.getUsername();
		String phone = nlcsuggestionExt.getPhone();
		String email = nlcsuggestionExt.getEmail();
		String adminname = nlcsuggestionExt.getAdminname();
		String status = nlcsuggestionExt.getStatus();
		Date zasktime = nlcsuggestionExt.getZasktime(); //留言时间
		Date yasktime = nlcsuggestionExt.getYasktime();
		Date zanstime = nlcsuggestionExt.getZanstime();	//答复时间
		Date yanstime = nlcsuggestionExt.getYanstime();
		String type = nlcsuggestionExt.getType();
		
		// 分页
		PageHelper.startPage(page, rows);
		NlcsuggestionExample example = new NlcsuggestionExample();
		Criteria criteria = example.createCriteria();
		
		if(StringUtils.isNotBlank(username)) {
			criteria.andUsernameEqualTo(username);
		}
		
		if(StringUtils.isNotBlank(phone)) {
			criteria.andPhoneEqualTo(phone);
		}
		
		if(StringUtils.isNotBlank(email)) {
			criteria.andEmailEqualTo(email);
		}
		
		if(StringUtils.isNotBlank(adminname)) {
			criteria.andAdminnameEqualTo(adminname);
		}
		
		if(StringUtils.isNotBlank(status)) {
			criteria.andStatusEqualTo(status);
		}
		
		if (null != zasktime && null != yasktime) {
			criteria.andAsktimeBetween(zasktime, yasktime);
		} else if (null != zasktime) {
			criteria.andAsktimeGreaterThanOrEqualTo(zasktime);
		} else if (null != yasktime) {
			criteria.andAsktimeLessThanOrEqualTo(yasktime);
		}

		if (null != zanstime && null != yanstime) {
			criteria.andAnstimeBetween(zanstime, yanstime);
		} else if (null != zanstime) {
			criteria.andAnstimeGreaterThanOrEqualTo(zanstime);
		} else if (null != yanstime) {
			criteria.andAnstimeLessThanOrEqualTo(yanstime);
		}
		
		if(StringUtils.isNotBlank(type)) {
			criteria.andTypeLike("%"+type+"%");
		}
			
		
		example.setOrderByClause("asktime desc");
		List<Nlcsuggestion> list = nlcsuggestionMapper.selectByExample(example);
		PageInfo<Nlcsuggestion> pageInfo = new PageInfo<>(list);
		long total = pageInfo.getTotal();
		EasyUiDataGridResult result = new EasyUiDataGridResult(total, list);

		return result;
	}
	
	/**
	 * 删除意见，单个删除
	 */
	@Override
	public int deleteSingleById(Integer id) {
		int result = nlcsuggestionMapper.deleteByPrimaryKey(id);
		return result;
	}

	/**
	 * 查询所有的意见信息
	 */
	@Override
	public List<Nlcsuggestion> getAll() {
		NlcsuggestionExample example = new NlcsuggestionExample();
		example.setOrderByClause("asktime desc");
		List<Nlcsuggestion> list = nlcsuggestionMapper.selectByExample(example);
		return list;
	}

	/**
	 * 根据主键查找意见
	 */
	@Override
	public Nlcsuggestion selectByPrimaryKey(Integer id) {
		Nlcsuggestion nlcsuggestion = nlcsuggestionMapper.selectByPrimaryKey(id);
		return nlcsuggestion;
	}

	/**
	 * 意见编辑/回复
	 * flag 1是编辑，2是回复
	 */
	@Override
	public void updateSuggestion(Integer id, String flag, String answer, String adminname) {
		Nlcsuggestion nlcsuggestion = nlcsuggestionMapper.selectByPrimaryKey(id);
		nlcsuggestion.setAdminname(adminname);
		nlcsuggestion.setAnstime(new Date());
		nlcsuggestion.setAnswer(answer);
		
		if("2".equals(flag)) {
			nlcsuggestion.setStatus("已回复");
		}
		nlcsuggestionMapper.updateByPrimaryKey(nlcsuggestion);
		String message = JSON.toJSONString(nlcsuggestion);
		
		Sysmessage sysmessage = new Sysmessage();
		sysmessage.setType(Byte.valueOf("8"));
		sysmessage.setTitle("意见回复");
		sysmessage.setMessage(message);
		sysmessage.setSort("suggestion");
		sysmessage.setUsername(nlcsuggestion.getUsername());
		sysmessage.setTime(new Date());
		sysmessageService.insertSysmessageObject(sysmessage);
	}

	/**
	 * 插入意见
	 */
	@Override
	public void insertSuggestion(Nlcsuggestion nlcsuggestion) {
		nlcsuggestionMapper.insert(nlcsuggestion);
	}

	/**
	 * 通过username查找list
	 */
	@Override
	public List<Nlcsuggestion> getListByUsername(String username) {
		NlcsuggestionExample example = new NlcsuggestionExample();
		Criteria criteria = example.createCriteria();
		criteria.andUsernameEqualTo(username);
		example.setOrderByClause("asktime desc");
		List<Nlcsuggestion> list = nlcsuggestionMapper.selectByExample(example);
		return list;
	}

	@Override
	public List<Nlcsuggestion> getSelect(String ids) {
		if(StringUtils.isBlank(ids)) {
			return null;
		}
		
		String[] strs = ids.split(",");
		List<String> idliststr = Arrays.asList(strs);
		List<Integer> idList = new ArrayList<Integer>();
		for (String str : idliststr) {
			idList.add(new Integer(str));
		}
		
		NlcsuggestionExample example = new NlcsuggestionExample();
		Criteria criteria = example.createCriteria();
		criteria.andIdIn(idList);
		example.setOrderByClause("asktime desc");
		List<Nlcsuggestion> list = nlcsuggestionMapper.selectByExample(example);
		return list;
	}

}
