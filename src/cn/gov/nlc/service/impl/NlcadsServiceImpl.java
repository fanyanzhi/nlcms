package cn.gov.nlc.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.gov.nlc.base.converter.BeanDateConnverter;
import cn.gov.nlc.mapper.NlcadsMapper;
import cn.gov.nlc.pojo.Nlcads;
import cn.gov.nlc.pojo.NlcadsExample;
import cn.gov.nlc.pojo.NlcadsExample.Criteria;
import cn.gov.nlc.service.NlcadsService;
import cn.gov.nlc.vo.EasyUiDataGridResult;

@Service
public class NlcadsServiceImpl implements NlcadsService{

	@Autowired
	private NlcadsMapper nlcadsMapper;

	/**
	 * 广告展示的分页查询
	 * @param page
	 * @param rows
	 * @return
	 */
	@Override
	public EasyUiDataGridResult getAdsList(int page, int rows) {
		// 分页
		PageHelper.startPage(page, rows);
		NlcadsExample example = new NlcadsExample();
		example.setOrderByClause("seq asc");
		
		List<Nlcads> list = nlcadsMapper.selectByExample(example);
		PageInfo<Nlcads> pageInfo = new PageInfo<>(list);
		long total = pageInfo.getTotal();
		EasyUiDataGridResult result = new EasyUiDataGridResult(total, list);

		return result;
	}

	/**
	 * 删除广告信息，单个删除
	 */
	@Override
	public int deleteSingleById(Integer id) {
		int result = nlcadsMapper.deleteByPrimaryKey(id);
		return result;
	}

	/**
	 * 广告上下架
	 */
	@Override
	public void shelfAds(Integer id) {
		Nlcads nlcads = nlcadsMapper.selectByPrimaryKey(id);
		Byte status = nlcads.getStatus();
		if(status == 1) {
			nlcads.setStatus((byte)0);
		}else {
			nlcads.setStatus((byte)1);
		}
		nlcads.setTime(new Date());
		
		nlcadsMapper.updateByPrimaryKey(nlcads);
	}

	/**
	 * 添加广告
	 */
	@Override
	public void insertAds(Nlcads nlcads) {
		nlcadsMapper.insert(nlcads);
	}

	/**
	 * 通过主键查询
	 */
	@Override
	public Nlcads selectByPrimaryKey(Integer id) {
		Nlcads nlcads = nlcadsMapper.selectByPrimaryKey(id);
		return nlcads;
	}

	/**
	 * 修改广告
	 */
	@Override
	public void updateAds(Map<String, String[]> parameterMap) throws Exception {
		String[] ids = parameterMap.get("id");
		Integer id = Integer.parseInt(ids[0]);
		Nlcads oldNlcads = nlcadsMapper.selectByPrimaryKey(id);
		ConvertUtils.register(new BeanDateConnverter(), Date.class);
		BeanUtils.populate(oldNlcads, parameterMap);
		oldNlcads.setTime(new Date());
		nlcadsMapper.updateByPrimaryKey(oldNlcads);
	}
	
	public List<Nlcads> getAppNlcAds() {
		NlcadsExample example = new NlcadsExample();
		Criteria criteria = example.createCriteria();
		criteria.andStatusEqualTo((byte) 1);
		example.setOrderByClause("seq");
		List<Nlcads> list = nlcadsMapper.selectByExample(example);
		return list;
	}
	
}
