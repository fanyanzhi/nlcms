package cn.gov.nlc.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.gov.nlc.mapper.UserfavoriteMapper;
import cn.gov.nlc.pojo.Userfavorite;
import cn.gov.nlc.pojo.UserfavoriteExample;
import cn.gov.nlc.service.UserfavoriteService;
import cn.gov.nlc.vo.EasyUiDataGridResult;
import cn.gov.nlc.pojo.UserfavoriteExample.Criteria;

@Service
public class UserfavoriteServiceImpl implements UserfavoriteService {

	@Autowired
	UserfavoriteMapper userfavoriteMapper;

	@Override
	public void addFavoriteInfo(Userfavorite userfavorite) {
		userfavoriteMapper.insert(userfavorite);
	}

	@Override
	public int getFavoriteID(String userName, String odatatype, String fileid) {
		UserfavoriteExample example = new UserfavoriteExample();
		Criteria criteria = example.createCriteria();
		criteria.andUsernameEqualTo(userName);
		criteria.andOdatatypeEqualTo(odatatype);
		criteria.andFileidEqualTo(fileid);
		List<Userfavorite> lst = userfavoriteMapper.selectByExample(example);
		if (lst != null && lst.size() > 0)
			return lst.get(0).getId().intValue();
		return -1;
	}

	@Override
	public boolean checkUserFavorite(String userName, String odatatype, String fileid) {
		UserfavoriteExample example = new UserfavoriteExample();
		Criteria criteria = example.createCriteria();
		criteria.andUsernameEqualTo(userName);
		criteria.andOdatatypeEqualTo(odatatype);
		criteria.andFileidEqualTo(fileid);
		if (userfavoriteMapper.countByExample(example) > 0)
			return true;
		return false;
	}

	@Override
	public int getFavoriteCount(String userName) {
		UserfavoriteExample example = new UserfavoriteExample();
		Criteria criteria = example.createCriteria();
		criteria.andUsernameEqualTo(userName);
		return userfavoriteMapper.countByExample(example);
	}

	@Override
	public EasyUiDataGridResult getFavoriteList(String userName, int start, int length) {
		PageHelper.startPage(start, length);
		UserfavoriteExample example = new UserfavoriteExample();
		Criteria criteria = example.createCriteria();
		criteria.andUsernameEqualTo(userName);
		List<Userfavorite> list = userfavoriteMapper.selectByExample(example);
		PageInfo<Userfavorite> pageInfo = new PageInfo<>(list);
		long total = pageInfo.getTotal();
		EasyUiDataGridResult result = new EasyUiDataGridResult(total, list);
		return result;
	}

	@Override
	public void delFavorite(String userName, String id) {
		List<Long> lstId = new ArrayList<Long>();
		String[] arrId = id.split(",");
		for (int i = 0, j = arrId.length; i < j; i++) {
			lstId.add(Long.parseLong(arrId[i]));
		}
		UserfavoriteExample example = new UserfavoriteExample();
		Criteria criteria = example.createCriteria();
		criteria.andUsernameEqualTo(userName);
		criteria.andIdIn(lstId);
		userfavoriteMapper.deleteByExample(example);
	}
}
