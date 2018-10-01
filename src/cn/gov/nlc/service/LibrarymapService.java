package cn.gov.nlc.service;

import java.util.List;
import java.util.Map;
import cn.gov.nlc.pojo.Librarymap;
import cn.gov.nlc.vo.EasyUiDataGridResult;

public interface LibrarymapService {

	/**
	 * 馆区地图展示的list
	 * @param page
	 * @param rows
	 * @return
	 */
	public EasyUiDataGridResult getMapList(Integer page, Integer rows);
	
	/**
	 * 添加馆区地图
	 */
	public void insertMap(Librarymap librarymap);
	
	/**
	 * 通过主键查询
	 */
	public Librarymap selectByPrimaryKey(Integer id);
	
	/**
	 * 修改馆区地图
	 */
	public void updateMap(Map<String, String[]> parameterMap) throws Exception;
	
	/**
	 * 删除馆区地图，单个删除
	 */
	public int deleteSingleById(Integer id);
	
	/**
	 * 得到馆区所有的地图
	 */
	public List<Librarymap> getAll();
}
