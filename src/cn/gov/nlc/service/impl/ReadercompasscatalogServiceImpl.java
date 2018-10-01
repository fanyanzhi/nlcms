package cn.gov.nlc.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import cn.gov.nlc.base.converter.BeanDateConnverter;
import cn.gov.nlc.base.exception.CnkiException;
import cn.gov.nlc.mapper.ReadercompasscatalogMapper;
import cn.gov.nlc.pojo.Readercompasscatalog;
import cn.gov.nlc.pojo.ReadercompasscatalogExample;
import cn.gov.nlc.pojo.ReadercompasscatalogExample.Criteria;
import cn.gov.nlc.pojo.ReadercompasscatalogWithBLOBs;
import cn.gov.nlc.service.ReadercompasscatalogService;

@Service
public class ReadercompasscatalogServiceImpl implements ReadercompasscatalogService{

	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private ReadercompasscatalogMapper readercompasscatalogMapper;
	
	@Override
	public List<Readercompasscatalog> getAll() {
		ReadercompasscatalogExample example = new ReadercompasscatalogExample();
		example.setOrderByClause("cseq asc");
		List<Readercompasscatalog> list = readercompasscatalogMapper.selectByExample(example);
		return list;
	}

	@Override
	public void insertCatalogAndUpdateParent(ReadercompasscatalogWithBLOBs po) {
		String pid = po.getPid();
		readercompasscatalogMapper.insert(po);
		Readercompasscatalog pReadercompasscatalog = findByCataloguuid(pid);
		if(null != pReadercompasscatalog) {
			pReadercompasscatalog.setIsdir("1");
			readercompasscatalogMapper.updateByPrimaryKey(pReadercompasscatalog);
		}
		
	}

	@Override
	public Readercompasscatalog findByCataloguuid(String cataloguuid) {
		ReadercompasscatalogExample example = new ReadercompasscatalogExample();
		Criteria criteria = example.createCriteria();
		criteria.andCataloguuidEqualTo(cataloguuid);
		List<Readercompasscatalog> list = readercompasscatalogMapper.selectByExample(example);
		if(null != list && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public ReadercompasscatalogWithBLOBs getCatalongContentByCataloguuid(String cataloguuid) {
		if("root".equals(cataloguuid)) {
			return null;
		}
		ReadercompasscatalogExample example = new ReadercompasscatalogExample();
		Criteria criteria = example.createCriteria();
		criteria.andCataloguuidEqualTo(cataloguuid);
		List<ReadercompasscatalogWithBLOBs> list = readercompasscatalogMapper.selectByExampleWithBLOBs(example);
		if(null != list && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public void updateContentByCataloguuid(String cataloguuid, String content, String contenthtml) {
		if("root".equals(cataloguuid)) {
			throw new CnkiException("cataloguuid为root");
		}
		ReadercompasscatalogWithBLOBs po = new ReadercompasscatalogWithBLOBs();
		po.setContent(content);
		po.setContenthtml(contenthtml);
		ReadercompasscatalogExample example = new ReadercompasscatalogExample();
		Criteria criteria = example.createCriteria();
		criteria.andCataloguuidEqualTo(cataloguuid);
		readercompasscatalogMapper.updateByExampleSelective(po, example);
	}

	@Override
	public void catalogEdit(String cataloguuid, String title) {
		if("root".equals(cataloguuid)) {
			throw new CnkiException("cataloguuid为root");
		}
		ReadercompasscatalogWithBLOBs po = new ReadercompasscatalogWithBLOBs();
		po.setTitle(title);
		ReadercompasscatalogExample example = new ReadercompasscatalogExample();
		Criteria criteria = example.createCriteria();
		criteria.andCataloguuidEqualTo(cataloguuid);
		readercompasscatalogMapper.updateByExampleSelective(po, example);
	}

	@Override
	public void deleteCatalogByCatalogid(String cataloguuid) {
		ReadercompasscatalogExample example = new ReadercompasscatalogExample();
		Criteria criteria = example.createCriteria();
		criteria.andCataloguuidEqualTo(cataloguuid);
		List<Readercompasscatalog> rlist = readercompasscatalogMapper.selectByExample(example);
		if(null != rlist && rlist.size() > 0) {
			Readercompasscatalog readercompasscatalog = rlist.get(0);
			Integer id = readercompasscatalog.getId();
			String pid = readercompasscatalog.getPid();
			List<Integer> reslist = new ArrayList<Integer>();
			getChild(id, reslist);
			reslist.add(id);
			ReadercompasscatalogExample example2 = new ReadercompasscatalogExample();
			Criteria criteria2 = example2.createCriteria();
			criteria2.andIdIn(reslist);
			readercompasscatalogMapper.deleteByExample(example2);
			
			ReadercompasscatalogExample example3 = new ReadercompasscatalogExample();
			Criteria criteria3 = example3.createCriteria();
			criteria3.andPidEqualTo(pid);
			List<Readercompasscatalog> olist = readercompasscatalogMapper.selectByExample(example3);
			if(null == olist || olist.size() == 0) {
				ReadercompasscatalogExample example4 = new ReadercompasscatalogExample();
				Criteria criteria4 = example4.createCriteria();
				criteria4.andCataloguuidEqualTo(pid);
				List<Readercompasscatalog> xlist = readercompasscatalogMapper.selectByExample(example4);
				if(null != xlist && xlist.size() > 0) {
					Readercompasscatalog readercompasscatalog2 = xlist.get(0);
					readercompasscatalog2.setIsdir("0");
					readercompasscatalogMapper.updateByPrimaryKey(readercompasscatalog2);
				}
			}
		}
	}
	
	private void getChild(Integer id, List<Integer> reslist) {
		String sql = "select id from readercompasscatalog where pid = (SELECT cataloguuid FROM `readercompasscatalog` where id = ?)";
		Object[] param1 = { id };
		List<Integer> list = jdbcTemplate.queryForList(sql, Integer.class, param1);
		if(null != list && list.size() > 0) {
			for (Integer idr : list) {
				reslist.add(idr);
				getChild(idr, reslist);
			}
		}
	}

	@Override
	public void updateCatalog(Map<String, String[]> parameterMap) throws Exception {
		String[] ids = parameterMap.get("cataloguuid");
		String[] pids = parameterMap.get("pid");
		String newpID = pids[0];
		String cataloguuid = ids[0];
		ReadercompasscatalogExample example = new ReadercompasscatalogExample();
		Criteria criteria = example.createCriteria();
		criteria.andCataloguuidEqualTo(cataloguuid);
		List<Readercompasscatalog> list = readercompasscatalogMapper.selectByExample(example);
		if(null != list && list.size() > 0) {
			boolean judgeResult = judgeChildOrGrandSon(cataloguuid, newpID);
			if(judgeResult) {
				throw new CnkiException("不能挂到自己的子目录下");
			}
			Readercompasscatalog readercompasscatalog = list.get(0);
			ConvertUtils.register(new BeanDateConnverter(), Date.class);
			BeanUtils.populate(readercompasscatalog, parameterMap);
			readercompasscatalogMapper.updateByPrimaryKey(readercompasscatalog);
			
			Integer id = readercompasscatalog.getId();
			List<Integer> reslist = new ArrayList<Integer>();
			getChild(id, reslist);
			
			if(reslist.size() > 0) {
				ReadercompasscatalogExample example2 = new ReadercompasscatalogExample();
				Criteria criteria2 = example2.createCriteria();
				criteria2.andIdIn(reslist);
				
				ReadercompasscatalogWithBLOBs record = new ReadercompasscatalogWithBLOBs();
				record.setStatus(readercompasscatalog.getStatus());
				readercompasscatalogMapper.updateByExampleSelective(record, example2);
			}
			
			List<Readercompasscatalog> listAll = getAllWithoutRoot();
			if(null != listAll && listAll.size() > 0) {
				for (Readercompasscatalog readercompasscatalog2 : listAll) {
					judgeDir(readercompasscatalog2);
				}
			}
		}else {
			throw new CnkiException("更新失败");
		}
	}
	
	private boolean judgeChildOrGrandSon(String oldCataid, String newpID) {
		List<String> reslist = new ArrayList<String>();
		judge(oldCataid, reslist);
		if(reslist.size() > 0) {
			if(reslist.contains(newpID)) {
				return true;
			}
		}
		
		return false;
	}
	
	private void judge(String catalogid, List<String> reslist) {
		String sql = "select cataloguuid from readercompasscatalog where pid = ?";
		Object[] param1 = { catalogid };
		List<String> list = jdbcTemplate.queryForList(sql, String.class, param1);
		if(null != list && list.size() > 0) {
			for (String str : list) {
				reslist.add(str);
				judge(str, reslist);
			}
		}
	}

	@Override
	public List<Readercompasscatalog> getAllWithoutRoot() {
		ReadercompasscatalogExample example = new ReadercompasscatalogExample();
		Criteria criteria = example.createCriteria();
		criteria.andCataloguuidNotEqualTo("root");
		List<Readercompasscatalog> list = readercompasscatalogMapper.selectByExample(example);
		return list;
	}
	
	private void judgeDir(Readercompasscatalog readercompasscatalog) {
		ReadercompasscatalogExample example = new ReadercompasscatalogExample();
		Criteria criteria = example.createCriteria();
		criteria.andPidEqualTo(readercompasscatalog.getCataloguuid());
		List<Readercompasscatalog> list = readercompasscatalogMapper.selectByExample(example);
		
		if(null != list && list.size() > 0) { //有子树
			readercompasscatalog.setIsdir("1");
		}else {	//无子树
			readercompasscatalog.setIsdir("0");
		}
		
		readercompasscatalogMapper.updateByPrimaryKey(readercompasscatalog);
	}

	@Override
	public List<Readercompasscatalog> getFirst() {
		ReadercompasscatalogExample example = new ReadercompasscatalogExample();
		Criteria criteria = example.createCriteria();
		criteria.andPidEqualTo("root");
		criteria.andStatusEqualTo("已上架");
		example.setOrderByClause("cseq asc");
		List<Readercompasscatalog> list = readercompasscatalogMapper.selectByExample(example);
		return list;
	}

	@Override
	public List<Readercompasscatalog> getChildCataloguuid(String cataloguuid) {
		ReadercompasscatalogExample example = new ReadercompasscatalogExample();
		Criteria criteria = example.createCriteria();
		criteria.andPidEqualTo(cataloguuid);
		criteria.andStatusEqualTo("已上架");
		example.setOrderByClause("cseq asc");
		List<Readercompasscatalog> list = readercompasscatalogMapper.selectByExample(example);
		return list;
	}
	
	
}
