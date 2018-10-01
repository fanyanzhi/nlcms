package cn.gov.nlc.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.gov.nlc.mapper.HotwordMapper;
import cn.gov.nlc.pojo.Hotword;
import cn.gov.nlc.pojo.HotwordExample;
import cn.gov.nlc.pojo.Nlcnotice;
import cn.gov.nlc.pojo.HotwordExample.Criteria;
import cn.gov.nlc.service.HotwordService;
import cn.gov.nlc.vo.EasyUiDataGridResult;

@Service
public class HotwordServiceImpl implements HotwordService {

	@Autowired
	HotwordMapper hotwordMapper;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public void insertHotword(Hotword hotword) {
		hotwordMapper.insert(hotword);
	}

	@Override
	public int getHotwordID(String hotword) {
		HotwordExample example = new HotwordExample();
		Criteria criteria = example.createCriteria();
		criteria.andHotwordEqualTo(hotword);
		List<Hotword> list = hotwordMapper.selectByExample(example);
		if (list == null || list.size() == 0)
			return -1;
		return list.get(0).getId();
	}

	@Override
	public void updSeaCount(int id) {
		String sql = "update hotword set seacount=seacount+1 where id=" + id;
		jdbcTemplate.execute(sql);
	}

	@Override
	public List<Hotword> getHotword() {
		PageHelper.startPage(1, 8);
		HotwordExample example = new HotwordExample();
		example.setOrderByClause("sort asc, seacount desc");
		List<Hotword> list = hotwordMapper.selectByExample(example);
		return list;
	}

	/**
	 * 热词的list
	 * @param page
	 * @param rows
	 * @return
	 */
	@Override
	public EasyUiDataGridResult getHotwordList(Integer page, Integer rows) {
		// 分页
		PageHelper.startPage(page, rows);
		HotwordExample example = new HotwordExample();
		example.setOrderByClause("sort asc, seacount desc, id asc");
		List<Hotword> list = hotwordMapper.selectByExample(example);
		PageInfo<Hotword> pageInfo = new PageInfo<>(list);
		long total = pageInfo.getTotal();
		EasyUiDataGridResult result = new EasyUiDataGridResult(total, list);
		return result;
	}
	
	/**
	 * 删除热词，单个删除
	 */
	@Override
	public int deleteSingleById(Integer id) {
		int result = hotwordMapper.deleteByPrimaryKey(id);
		return result;
	}

	/**
	 * 修改热词值及排序
	 * @param reslist
	 */
	@Override
	public void saveModi(List<Hotword> reslist) {
		if(null != reslist && reslist.size() > 0) {
			for (Hotword hotword : reslist) {
				hotwordMapper.updateByPrimaryKeySelective(hotword);
			}
		}
	}

}
