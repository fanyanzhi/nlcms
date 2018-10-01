package cn.gov.nlc.service;

import java.util.List;
import cn.gov.nlc.pojo.Columndic;
import cn.gov.nlc.vo.EasyUiDataGridResult;

public interface ColumndicService {

	/**
	 * 得到全部栏目字典
	 * @return
	 */
	public List<Columndic> getList();
	
	/**
	 * 讲座预告栏目的数据
	 * @param page
	 * @param rows
	 * @return
	 */
	public EasyUiDataGridResult getItemList(Integer page, Integer rows);

	/**
	 * 根据id删除讲座预告栏目的信息，单个删除
	 * @param id
	 * @return
	 */
	public int deleteSingleById(Integer id);
	
	/**
	 * 插入讲座预告栏目
	 * @param columndic
	 */
	public void insertItem(Columndic columndic);
	
	/**
	 * 通过主键查询
	 * @param id
	 * @return
	 */
	public Columndic selectByPrimaryKey(Integer id);
	
	/**
	 * 更新
	 * @param columndic
	 */
	public void updateColumn(Columndic columndic);
	
	/**
	 * 根据id查询讲座预告栏目在讲座预告中是否有使用过
	 */
	public boolean checkTrail(Integer id);
}
