package cn.gov.nlc.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.gov.nlc.mapper.ColumndicMapper;
import cn.gov.nlc.mapper.NlctrailerMapper;
import cn.gov.nlc.pojo.Columndic;
import cn.gov.nlc.pojo.ColumndicExample;
import cn.gov.nlc.pojo.ColumndicExample.Criteria;
import cn.gov.nlc.pojo.Nlctrailer;
import cn.gov.nlc.pojo.NlctrailerExample;
import cn.gov.nlc.service.ColumndicService;
import cn.gov.nlc.vo.EasyUiDataGridResult;

@Service
public class ColumndicServiceImpl implements ColumndicService{

	@Autowired
	private ColumndicMapper columndicMapper;
	@Autowired
	private NlctrailerMapper nlctrailerMapper;


	/**
	 * 得到全部栏目字典
	 */
	@Override
	public List<Columndic> getList() {
		ColumndicExample example = new ColumndicExample();
		List<Columndic> list = columndicMapper.selectByExample(example);
		return list;
	}

	/**
	 * 讲座预告栏目的数据
	 * @param page
	 * @param rows
	 * @return
	 */
	@Override
	public EasyUiDataGridResult getItemList(Integer page, Integer rows) {
		// 分页
		PageHelper.startPage(page, rows);
		ColumndicExample example = new ColumndicExample();
		example.setOrderByClause("id asc");
		List<Columndic> list = columndicMapper.selectByExample(example);
		PageInfo<Columndic> pageInfo = new PageInfo<>(list);
		long total = pageInfo.getTotal();
		EasyUiDataGridResult result = new EasyUiDataGridResult(total, list);
		return result;
	}

	/**
	 * 根据id删除讲座预告栏目的信息，单个删除
	 * @param id
	 * @return
	 */
	@Override
	public int deleteSingleById(Integer id) {
		int result = columndicMapper.deleteByPrimaryKey(id);
		return result;
	}

	/**
	 * 插入讲座预告栏目
	 */
	@Override
	public void insertItem(Columndic columndic) {
		columndicMapper.insert(columndic);
	}

	/**
	 * 通过主键查询
	 * @param id
	 * @return
	 */
	@Override
	public Columndic selectByPrimaryKey(Integer id) {
		Columndic columndic = columndicMapper.selectByPrimaryKey(id);
		return columndic;
	}

	/**
	 * 更新
	 * @param columndic
	 */
	@Override
	public void updateColumn(Columndic columndic) {
		columndicMapper.updateByPrimaryKeySelective(columndic);
	}

	/**
	 * 根据id查询讲座预告栏目在讲座预告中是否有使用过
	 */
	@Override
	public boolean checkTrail(Integer id) {
		Columndic columndic = columndicMapper.selectByPrimaryKey(id);
		String columnid = columndic.getColumnid();
		
		NlctrailerExample example = new NlctrailerExample();
		cn.gov.nlc.pojo.NlctrailerExample.Criteria criteria = example.createCriteria();
		criteria.andColumnidEqualTo(columnid);
		List<Nlctrailer> list = nlctrailerMapper.selectByExample(example);
		if(null != list && list.size() > 0) {
			return true;
		}
		return false;
	}
}
