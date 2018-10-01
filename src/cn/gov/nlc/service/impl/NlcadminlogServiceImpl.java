package cn.gov.nlc.service.impl;

import java.util.Date;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.gov.nlc.mapper.NlcadminlogMapper;
import cn.gov.nlc.pojo.Nlcadminlog;
import cn.gov.nlc.pojo.NlcadminlogExample;
import cn.gov.nlc.pojo.NlcadminlogExample.Criteria;
import cn.gov.nlc.pojo.NlcadminlogExt;
import cn.gov.nlc.service.NlcadminlogService;
import cn.gov.nlc.vo.EasyUiDataGridResult;

@Service
public class NlcadminlogServiceImpl implements NlcadminlogService{
	
	@Autowired
	private NlcadminlogMapper nlcadminlogMapper;
	
	/**
	 * 管理员日志内容的分页查询
	 * @param page
	 * @param rows
	 * @return
	 */
	@Override
	public EasyUiDataGridResult getAdminList(int page, int rows, NlcadminlogExt nlcadminlogExt) {
		String username = nlcadminlogExt.getUsername();
		Byte role = nlcadminlogExt.getRole();
		Date startDate = nlcadminlogExt.getStartDate();
		Date endDate = nlcadminlogExt.getEndDate();
		
		//分页
		PageHelper.startPage(page, rows);
		NlcadminlogExample example = new NlcadminlogExample();
		Criteria criteria = example.createCriteria();
		
		if(StringUtils.isNotBlank(username)) {
			criteria.andUsernameLike("%"+username+"%");
		}
		
		if(null != role) {
			criteria.andRoleEqualTo(role);
		}
		
		if(null != startDate && null != endDate) {
			DateTime dt = new DateTime(endDate);
			dt = dt.plusDays(1);
			criteria.andTimeGreaterThanOrEqualTo(startDate);
			criteria.andTimeLessThan(dt.toDate());
		}else if(null != startDate) {
			criteria.andTimeGreaterThanOrEqualTo(startDate);
		}else if(null != endDate) {
			DateTime dt = new DateTime(endDate);
			dt = dt.plusDays(1);
			criteria.andTimeLessThan(dt.toDate());
		}
		example.setOrderByClause("operateTime desc");
		List<Nlcadminlog> list = nlcadminlogMapper.selectByExample(example);
		PageInfo<Nlcadminlog> pageInfo = new PageInfo<>(list);
		long total = pageInfo.getTotal();
		EasyUiDataGridResult result = new EasyUiDataGridResult(total, list);
		return result;
	}

	/**
	 * 删除日志信息，单个删除
	 */
	@Override
	public int deleteSingleById(Integer id) {
		int result = nlcadminlogMapper.deleteByPrimaryKey(id);
		return result;
	}

	/**
	 * 查询所有的日志信息
	 */
	@Override
	public List<Nlcadminlog> getAll() {
		NlcadminlogExample example = new NlcadminlogExample();
		List<Nlcadminlog> list = nlcadminlogMapper.selectByExample(example);
		return list;
	}

	/**
	 * 插入管理员日志
	 */
	@Override
	public void insertNlcadminlog(Nlcadminlog nlcadminlog) {
		nlcadminlog.setOperatetime(new Date());
		nlcadminlogMapper.insert(nlcadminlog);
	}

}
