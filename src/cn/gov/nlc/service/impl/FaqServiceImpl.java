package cn.gov.nlc.service.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.gov.nlc.base.converter.BeanDateConnverter;
import cn.gov.nlc.mapper.FaqMapper;
import cn.gov.nlc.pojo.Faq;
import cn.gov.nlc.pojo.FaqExample;
import cn.gov.nlc.pojo.FaqExample.Criteria;
import cn.gov.nlc.service.FaqService;
import cn.gov.nlc.vo.EasyUiDataGridResult;

@Service
public class FaqServiceImpl implements FaqService{
	
	@Autowired
	private FaqMapper faqMapper;
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public EasyUiDataGridResult getList(int page, int rows, Faq faq) {
		String question = faq.getQuestion();
		String status = faq.getStatus();
		
		//分页
		PageHelper.startPage(page, rows);
		
		FaqExample example = new FaqExample();
		Criteria criteria = example.createCriteria();
		
		if(StringUtils.isNotBlank(question)) {
			criteria.andQuestionLike("%"+question+"%");
		}
		
		if(StringUtils.isNotBlank(status)) {
			criteria.andStatusEqualTo(status);
		}
		
		example.setOrderByClause("cseq asc");
		List<Faq> list = faqMapper.selectByExample(example);
		PageInfo<Faq> pageInfo = new PageInfo<>(list);
		long total = pageInfo.getTotal();
		EasyUiDataGridResult result = new EasyUiDataGridResult(total, list);
		return result;
	}

	@Override
	public void insert(Faq faq) {
		faqMapper.insert(faq);
	}

	@Override
	public void publish(Integer id, String status) {
		Faq faq = faqMapper.selectByPrimaryKey(id);
		if("1".equals(status)) {
			faq.setStatus("已发布");
			faq.setBkpubtime(new Date());
		}else {
			faq.setStatus("未发布");
			faq.setBkpubtime(null);
		}
		faq.setUptime(new Date());
		faqMapper.updateByPrimaryKey(faq);
	}

	@Override
	public int deleteSingleById(Integer id) {
		int result = faqMapper.deleteByPrimaryKey(id);
		return result;
	}

	@Override
	public Faq selectByPrimaryKey(Integer id) {
		Faq faq = faqMapper.selectByPrimaryKey(id);
		return faq;
	}

	@Override
	public void updateFaq(Map<String, String[]> parameterMap) throws Exception {
		String[] ids = parameterMap.get("id");
		Integer id = Integer.parseInt(ids[0]);
		Faq oldFaq = faqMapper.selectByPrimaryKey(id);
		ConvertUtils.register(new BeanDateConnverter(), Date.class);
		BeanUtils.populate(oldFaq, parameterMap);
		faqMapper.updateByPrimaryKey(oldFaq);
	}

	@Override
	public void sortFaq(Integer id, Integer cseq) {
		String sqls = "select id from faq where cseq = " + cseq;
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
		
		Faq faq = new Faq();
		faq.setId(id);
		faq.setCseq(cseq);
		faqMapper.updateByPrimaryKeySelective(faq);
		
		if(null != oldid) {
			Faq oldFaq = new Faq();
			oldFaq.setId(Integer.valueOf(oldid + ""));
			oldFaq.setCseq(1000);
			faqMapper.updateByPrimaryKeySelective(oldFaq);
		}
		
	}

	@Override
	public void updateObj(Map<String, String[]> parameterMap) throws Exception {
		String[] ids = parameterMap.get("id");
		Integer id = Integer.parseInt(ids[0]);
		Faq oldFaq = faqMapper.selectByPrimaryKey(id);
		ConvertUtils.register(new BeanDateConnverter(), Date.class);
		BeanUtils.populate(oldFaq, parameterMap);
		String status = oldFaq.getStatus();
		if("已发布".equals(status)) {
			oldFaq.setBkpubtime(new Date());
		}else {
			oldFaq.setBkpubtime(null);
		}
		
		oldFaq.setUptime(new Date());
		faqMapper.updateByPrimaryKey(oldFaq);
	}

	@Override
	public List<Faq> getAllList() {
		FaqExample example = new FaqExample();
		Criteria criteria = example.createCriteria();
		criteria.andStatusEqualTo("已发布");
		example.setOrderByClause("cseq asc");
		List<Faq> list = faqMapper.selectByExample(example);
		return list;
	}

}
