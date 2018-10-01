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
import cn.gov.nlc.mapper.PictureMapper;
import cn.gov.nlc.pojo.NlcnewscollectExample;
import cn.gov.nlc.pojo.Picture;
import cn.gov.nlc.pojo.PictureExample;
import cn.gov.nlc.service.PictureService;
import cn.gov.nlc.vo.EasyUiDataGridResult;

@Service
public class PictureServiceImpl implements PictureService{

	@Autowired
	private PictureMapper pictureMapper;

	/**
	 * 图片设置展示的list
	 * @param page
	 * @param rows
	 * @return
	 */
	@Override
	public EasyUiDataGridResult getPicList(Integer page, Integer rows) {
		// 分页
		PageHelper.startPage(page, rows);
		PictureExample example = new PictureExample();
		List<Picture> list = pictureMapper.selectByExample(example);
		PageInfo<Picture> pageInfo = new PageInfo<>(list);
		long total = pageInfo.getTotal();
		EasyUiDataGridResult result = new EasyUiDataGridResult(total, list);
		return result;
	}

	/**
	 * 通过主键查询
	 */
	@Override
	public Picture selectByPrimaryKey(Integer id) {
		Picture pic = pictureMapper.selectByPrimaryKey(id);
		return pic;
	}

	/**
	 * 修改图片
	 */
	@Override
	public void updatePic(Map<String, String[]> parameterMap) throws Exception {
		String[] ids = parameterMap.get("id");
		Integer id = Integer.parseInt(ids[0]);
		Picture oldPic = pictureMapper.selectByPrimaryKey(id);
		ConvertUtils.register(new BeanDateConnverter(), Date.class);
		BeanUtils.populate(oldPic, parameterMap);
		oldPic.setTime(new Date());
		pictureMapper.updateByPrimaryKey(oldPic);
	}

	/**
	 * 添加图片项
	 */
	@Override
	public void insertPic(Picture picture) {
		pictureMapper.insert(picture);
	}

	/**
	 * 根据主键删除
	 */
	@Override
	public int deleteSingleById(Integer id) {
		int result = pictureMapper.deleteByPrimaryKey(id);
		return result;
	}
	
	public String getPictureByName(String picName){
		PictureExample example = new PictureExample();
		cn.gov.nlc.pojo.PictureExample.Criteria criteria = example.createCriteria();
		criteria.andNameEqualTo(picName);
		List<Picture> picture = pictureMapper.selectByExample(example);
		if(picture != null && picture.size()>0)
			return picture.get(0).getSrc();
		return "";
	}
}
