package cn.gov.nlc.service;

import java.util.Map;
import cn.gov.nlc.pojo.Picture;
import cn.gov.nlc.vo.EasyUiDataGridResult;

public interface PictureService {

	/**
	 * 图片设置展示的list
	 * @param page
	 * @param rows
	 * @return
	 */
	public EasyUiDataGridResult getPicList(Integer page, Integer rows);
	
	/**
	 * 通过主键查询
	 */
	public Picture selectByPrimaryKey(Integer id);

	/**
	 * 修改图片
	 */
	public void updatePic(Map<String, String[]> parameterMap) throws Exception;
	
	/**
	 * 添加图片项
	 */
	public void insertPic(Picture picture);
	
	/**
	 * 删除图片项，单个删除
	 */
	public int deleteSingleById(Integer id);
	
	/**
	 * 
	 */
	public String getPictureByName(String picName);
}
