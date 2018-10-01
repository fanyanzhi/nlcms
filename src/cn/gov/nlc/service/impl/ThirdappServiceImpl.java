package cn.gov.nlc.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.gov.nlc.base.converter.BeanDateConnverter;
import cn.gov.nlc.mapper.ThirdappMapper;
import cn.gov.nlc.pojo.Thirdapp;
import cn.gov.nlc.pojo.ThirdappExample;
import cn.gov.nlc.pojo.ThirdappExample.Criteria;
import cn.gov.nlc.service.ThirdappService;
import cn.gov.nlc.vo.EasyUiDataGridResult;

@Service
public class ThirdappServiceImpl implements ThirdappService{

	@Autowired
	private ThirdappMapper thirdappMapper;

	@Override
	public EasyUiDataGridResult getList(int page, int rows, Thirdapp thirdapp) {
		String name = thirdapp.getName();
		String os = thirdapp.getOs();
		String status = thirdapp.getStatus();
		
		// 分页
		PageHelper.startPage(page, rows);
		
		ThirdappExample example = new ThirdappExample();
		Criteria criteria = example.createCriteria();
		
		if(StringUtils.isNotBlank(name)) {
			criteria.andNameLike("%"+name+"%");
		}
		
		if(StringUtils.isNotBlank(os)) {
			criteria.andOsEqualTo(os);
		}
		
		if(StringUtils.isNotBlank(status)) {
			criteria.andStatusEqualTo(status);
		}
		
		example.setOrderByClause("creatime desc");
		List<Thirdapp> list = thirdappMapper.selectByExample(example);
		PageInfo<Thirdapp> pageInfo = new PageInfo<>(list);
		long total = pageInfo.getTotal();
		EasyUiDataGridResult result = new EasyUiDataGridResult(total, list);
		return result;
	}

	@Override
	public void insert(Thirdapp thirdapp) {
		thirdappMapper.insert(thirdapp);
	}

	@Override
	public void publish(Integer id, String status) {
		Thirdapp thirdapp = thirdappMapper.selectByPrimaryKey(id);
		thirdapp.setStatus(status);
		thirdappMapper.updateByPrimaryKey(thirdapp);
	}

	@Override
	public int deleteSingleById(Integer id) {
		int result = thirdappMapper.deleteByPrimaryKey(id);
		return result;
	}

	@Override
	public Thirdapp selectByPrimaryKey(Integer id) {
		Thirdapp thirdapp = thirdappMapper.selectByPrimaryKey(id);
		return thirdapp;
	}

	@Override
	public void updateApp(Map<String, String[]> parameterMap) throws Exception {
		String[] ids = parameterMap.get("id");
		Integer id = Integer.parseInt(ids[0]);
		Thirdapp oldThirdapp = thirdappMapper.selectByPrimaryKey(id);
		ConvertUtils.register(new BeanDateConnverter(), Date.class);
		BeanUtils.populate(oldThirdapp, parameterMap);
		thirdappMapper.updateByPrimaryKey(oldThirdapp);
	}

	@Override
	public List<Thirdapp> getByOs(String os) {
		ThirdappExample example = new ThirdappExample();
		Criteria criteria = example.createCriteria();
		criteria.andStatusEqualTo("1");
		criteria.andOsEqualTo(os);
		example.setOrderByClause("creatime desc");
		List<Thirdapp> list = thirdappMapper.selectByExample(example);
		return list;
	}
}
