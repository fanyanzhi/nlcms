package cn.gov.nlc.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.gov.nlc.mapper.OlcchotwordMapper;
import cn.gov.nlc.pojo.Olcchotword;
import cn.gov.nlc.pojo.OlcchotwordExample;
import cn.gov.nlc.pojo.OlcchotwordExample.Criteria;
import cn.gov.nlc.service.OlcchotwordService;
import cn.gov.nlc.vo.EasyUiDataGridResult;

@Service
public class OlcchotwordServiceImpl implements OlcchotwordService {

	@Autowired
	OlcchotwordMapper olcchotwordMapper;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	/**
	 * 插入
	 */
	@Override
	public void insertHotword(Olcchotword olcchotword) {
		olcchotwordMapper.insert(olcchotword);
	}

	/**
	 * 通过热词得到热词id
	 */
	@Override
	public int getHotwordID(String hotword) {
		OlcchotwordExample example = new OlcchotwordExample();
		Criteria criteria = example.createCriteria();
		criteria.andHotwordEqualTo(hotword);
		List<Olcchotword> list = olcchotwordMapper.selectByExample(example);
		if (list == null || list.size() == 0)
			return -1;
		return list.get(0).getId();
	}

	/**
	 * 增长计数seacount
	 */
	@Override
	public void updSeaCount(int id) {
		String sql = "update olcchotword set seacount=seacount+1 where id=" + id;
		jdbcTemplate.execute(sql);
	}

	/**
	 * 得到前8
	 */
	@Override
	public List<Olcchotword> getHotword() {
		PageHelper.startPage(1, 8);
		OlcchotwordExample example = new OlcchotwordExample();
		example.setOrderByClause("sort asc, seacount desc");
		List<Olcchotword> list = olcchotwordMapper.selectByExample(example);
		return list;
	}

	/**
	 * 分页
	 */
	@Override
	public EasyUiDataGridResult getHotwordList(Integer page, Integer rows) {
		// 分页
		PageHelper.startPage(page, rows);
		OlcchotwordExample example = new OlcchotwordExample();
		example.setOrderByClause("sort asc, seacount desc, id asc");
		List<Olcchotword> list = olcchotwordMapper.selectByExample(example);
		PageInfo<Olcchotword> pageInfo = new PageInfo<>(list);
		long total = pageInfo.getTotal();
		EasyUiDataGridResult result = new EasyUiDataGridResult(total, list);
		return result;
	}

	/**
	 * 删除热词，单个删除
	 */
	@Override
	public int deleteSingleById(Integer id) {
		int result = olcchotwordMapper.deleteByPrimaryKey(id);
		return result;
	}

	/**
	 * 修改热词值及排序
	 */
	@Override
	public void saveModi(List<Olcchotword> reslist) {
		if(null != reslist && reslist.size() > 0) {
			for (Olcchotword olcchotword : reslist) {
				olcchotwordMapper.updateByPrimaryKeySelective(olcchotword);
			}
		}
	}

}
