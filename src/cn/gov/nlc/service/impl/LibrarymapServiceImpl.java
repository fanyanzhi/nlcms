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
import cn.gov.nlc.mapper.LibrarymapMapper;
import cn.gov.nlc.pojo.Librarymap;
import cn.gov.nlc.pojo.LibrarymapExample;
import cn.gov.nlc.service.LibrarymapService;
import cn.gov.nlc.vo.EasyUiDataGridResult;

@Service
public class LibrarymapServiceImpl implements LibrarymapService{
	
	@Autowired
	private LibrarymapMapper librarymapMapper;

	/**
	 * 馆区地图展示的list
	 * @param page
	 * @param rows
	 * @return
	 */
	@Override
	public EasyUiDataGridResult getMapList(Integer page, Integer rows) {
		// 分页
		PageHelper.startPage(page, rows);
		LibrarymapExample example = new LibrarymapExample();
		example.setOrderByClause("seq asc");
		List<Librarymap> list = librarymapMapper.selectByExample(example);
		PageInfo<Librarymap> pageInfo = new PageInfo<>(list);
		long total = pageInfo.getTotal();
		EasyUiDataGridResult result = new EasyUiDataGridResult(total, list);
		return result;
	}

	/**
	 * 添加馆区地图
	 */
	@Override
	public void insertMap(Librarymap librarymap) {
		librarymapMapper.insert(librarymap);
	}

	/**
	 * 通过主键查询
	 */
	@Override
	public Librarymap selectByPrimaryKey(Integer id) {
		Librarymap map = librarymapMapper.selectByPrimaryKey(id);
		return map;
	}

	/**
	 * 修改馆区地图
	 */
	@Override
	public void updateMap(Map<String, String[]> parameterMap) throws Exception {
		String[] ids = parameterMap.get("id");
		Integer id = Integer.parseInt(ids[0]);
		Librarymap oldmap = librarymapMapper.selectByPrimaryKey(id);
		ConvertUtils.register(new BeanDateConnverter(), Date.class);
		BeanUtils.populate(oldmap, parameterMap);
		oldmap.setTime(new Date());
		librarymapMapper.updateByPrimaryKey(oldmap);
	}

	/**
	 * 删除馆区地图，单个删除
	 */
	@Override
	public int deleteSingleById(Integer id) {
		int result = librarymapMapper.deleteByPrimaryKey(id);
		return result;
	}

	/**
	 * 得到馆区所有的地图
	 */
	@Override
	public List<Librarymap> getAll() {
		LibrarymapExample example = new LibrarymapExample();
		example.setOrderByClause("seq asc");
		List<Librarymap> list = librarymapMapper.selectByExample(example);
		return list;
	}
}
