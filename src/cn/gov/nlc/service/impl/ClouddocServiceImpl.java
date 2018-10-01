package cn.gov.nlc.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.gov.nlc.mapper.ClouddocMapper;
import cn.gov.nlc.pojo.Clouddoc;
import cn.gov.nlc.pojo.ClouddocExample;
import cn.gov.nlc.pojo.ClouddocExample.Criteria;
import cn.gov.nlc.service.ClouddocService;
import cn.gov.nlc.vo.EasyUiDataGridResult;

@Service
public class ClouddocServiceImpl implements ClouddocService {

	@Autowired
	ClouddocMapper clouddocMapper;

	@Override
	public void insertClouddoc(Clouddoc clouddoc) {
		clouddocMapper.insert(clouddoc);
	}

	@Override
	public EasyUiDataGridResult getClouddoc(String loginAccount) {
		ClouddocExample example = new ClouddocExample();
		Criteria criteria = example.createCriteria();
		criteria.andUsernameEqualTo(loginAccount);
		criteria.andDeletedEqualTo(0);
		example.setOrderByClause("time desc");
		List<Clouddoc> list = clouddocMapper.selectByExampleWithBLOBs(example);
		EasyUiDataGridResult result = new EasyUiDataGridResult(list.size(), list);
		return result;
	}
	
	@Override
	public void updateClouddoc(String loginAccount, Clouddoc clouddoc) {
		ClouddocExample example = new ClouddocExample();
		Criteria criteria = example.createCriteria();
		criteria.andUsernameEqualTo(loginAccount);
		criteria.andRecidEqualTo(clouddoc.getRecid());
		criteria.andDeletedEqualTo(0);
		clouddocMapper.updateByExampleSelective(clouddoc, example);
	}


}
