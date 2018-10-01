package cn.gov.nlc.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import cn.gov.nlc.base.converter.BeanDateConnverter;
import cn.gov.nlc.base.exception.CnkiException;
import cn.gov.nlc.mapper.NlcsubjectcatalogMapper;
import cn.gov.nlc.pojo.Nlcsubjectcatalog;
import cn.gov.nlc.pojo.NlcsubjectcatalogExample;
import cn.gov.nlc.pojo.NlcsubjectcatalogExample.Criteria;
import cn.gov.nlc.service.NlcsubjectcatalogService;

@Service
public class NlcsubjectcatalogServiceImpl implements NlcsubjectcatalogService{

	@Autowired
	private NlcsubjectcatalogMapper nlcsubjectcatalogMapper;
	@Autowired
	private JdbcTemplate jdbcTemplate;

	/**
	 * 通过subjectid查找
	 * @param subjectid
	 * @return
	 */
	@Override
	public List<Nlcsubjectcatalog> findBySubjectid(String subjectid) {
		NlcsubjectcatalogExample example = new NlcsubjectcatalogExample();
		Criteria criteria = example.createCriteria();
		criteria.andSubjectidEqualTo(subjectid);
		example.setOrderByClause("cseq asc");
		List<Nlcsubjectcatalog> list = nlcsubjectcatalogMapper.selectByExample(example);
		return list;
	}

	/**
	 * 通过catalogid得到目录对象 (这里的catalogid不会是root)，为了得到content
	 * @param id
	 * @return
	 */
	@Override
	public Nlcsubjectcatalog getCatalongContentByCatalogid(String catalogid) {
		if("root".equals(catalogid)) {
			return null;
		}
		NlcsubjectcatalogExample example = new NlcsubjectcatalogExample();
		Criteria criteria = example.createCriteria();
		criteria.andCatalogidEqualTo(catalogid);
		List<Nlcsubjectcatalog> list = nlcsubjectcatalogMapper.selectByExampleWithBLOBs(example);
		if(null != list && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * 编辑目录名后根据catalogid保存目录名text
	 */
	@Override
	public void catalogEdit(String catalogid, String title) {
		if("root".equals(catalogid)) {
			throw new CnkiException("catalogid为root");
		}
		Nlcsubjectcatalog nlcsubjectcatalog = new Nlcsubjectcatalog();
		nlcsubjectcatalog.setTitle(title);
		
		NlcsubjectcatalogExample example = new NlcsubjectcatalogExample();
		Criteria criteria = example.createCriteria();
		criteria.andCatalogidEqualTo(catalogid);
		nlcsubjectcatalogMapper.updateByExampleSelective(nlcsubjectcatalog, example);
	}

	/**
	 * 添加新的目录
	 */
	@Override
	public void insertCatalog(Nlcsubjectcatalog nlcsubjectcatalog) {
		nlcsubjectcatalogMapper.insert(nlcsubjectcatalog);
	}

	/**
	 * 根据catalogid保存内容content，(catalogid为root时不保存)
	 */
	@Override
	public void updateContentByCatalogid(String catalogid, String content) {
		if("root".equals(catalogid)) {
			throw new CnkiException("catalogid为root");
		}
		Nlcsubjectcatalog nlcsubjectcatalog = new Nlcsubjectcatalog();
		nlcsubjectcatalog.setContent(content);
		
		NlcsubjectcatalogExample example = new NlcsubjectcatalogExample();
		Criteria criteria = example.createCriteria();
		criteria.andCatalogidEqualTo(catalogid);
		nlcsubjectcatalogMapper.updateByExampleSelective(nlcsubjectcatalog, example);
	}

	/**
	 * 根据catalogid删除目录及其子目录
	 * @param catalogid
	 */
	@Override
	public void deleteCatalogByCatalogid(String catalogid) {
		NlcsubjectcatalogExample example = new NlcsubjectcatalogExample();
		Criteria criteria = example.createCriteria();
		criteria.andCatalogidEqualTo(catalogid);
		List<Nlcsubjectcatalog> rlist = nlcsubjectcatalogMapper.selectByExample(example);
		if(null != rlist && rlist.size() > 0) {
			Nlcsubjectcatalog nlcsubjectcatalog = rlist.get(0);
			Integer id = nlcsubjectcatalog.getId();
			String pid = nlcsubjectcatalog.getPid();
			List<Integer> reslist = new ArrayList<Integer>();
			getChild(id, reslist);
			reslist.add(id);
			NlcsubjectcatalogExample example2 = new NlcsubjectcatalogExample();
			Criteria criteria2 = example2.createCriteria();
			criteria2.andIdIn(reslist);
			nlcsubjectcatalogMapper.deleteByExample(example2);
			
			NlcsubjectcatalogExample example3 = new NlcsubjectcatalogExample();
			Criteria criteria3 = example3.createCriteria();
			criteria3.andPidEqualTo(pid);
			List<Nlcsubjectcatalog> olist = nlcsubjectcatalogMapper.selectByExample(example3);
			if(null == olist || olist.size() == 0) {
				NlcsubjectcatalogExample example4 = new NlcsubjectcatalogExample();
				Criteria criteria4 = example4.createCriteria();
				criteria4.andCatalogidEqualTo(pid);
				List<Nlcsubjectcatalog> xlist = nlcsubjectcatalogMapper.selectByExample(example4);
				if(null != xlist && xlist.size() > 0) {
					Nlcsubjectcatalog nlcsubjectcatalog2 = xlist.get(0);
					nlcsubjectcatalog2.setIsdir("0");
					nlcsubjectcatalogMapper.updateByPrimaryKey(nlcsubjectcatalog2);
				}
			}
			f1(nlcsubjectcatalog.getSubjectid(), "root");
		}
		
	}
	
	private void getChild(Integer id, List<Integer> reslist) {
		String sql = "select id from nlcsubjectcatalog where pid = (SELECT catalogid FROM `nlcsubjectcatalog` where id = ?)";
		Object[] param1 = { id };
		List<Integer> list = jdbcTemplate.queryForList(sql, Integer.class, param1);
		if(null != list && list.size() > 0) {
			for (Integer idr : list) {
				reslist.add(idr);
				getChild(idr, reslist);
			}
		}
	}

	/**
	 * 根据catalogid查询目录 (不含content)
	 */
	@Override
	public Nlcsubjectcatalog getCatalogByCatalogid(String catalogid) {
		NlcsubjectcatalogExample example = new NlcsubjectcatalogExample();
		Criteria criteria = example.createCriteria();
		criteria.andCatalogidEqualTo(catalogid);
		List<Nlcsubjectcatalog> list = nlcsubjectcatalogMapper.selectByExample(example);
		if(null != list && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * 修改目录
	 */
	@Override
	public void updateCatalog(Map<String, String[]> parameterMap) throws Exception {
		String[] ids = parameterMap.get("catalogid");
		String[] pids = parameterMap.get("pid");
		String newpID = pids[0];
		String catalogid = ids[0];
		NlcsubjectcatalogExample example = new NlcsubjectcatalogExample();
		Criteria criteria = example.createCriteria();
		criteria.andCatalogidEqualTo(catalogid);
		List<Nlcsubjectcatalog> list = nlcsubjectcatalogMapper.selectByExample(example);
		if(null != list && list.size() > 0) {
			boolean judgeResult = judgeChildOrGrandSon(catalogid, newpID);
			if(judgeResult) {
				throw new CnkiException("不能挂到自己的子目录下");
			}
			Nlcsubjectcatalog nlcsubjectcatalog = list.get(0);
			ConvertUtils.register(new BeanDateConnverter(), Date.class);
			BeanUtils.populate(nlcsubjectcatalog, parameterMap);
			nlcsubjectcatalogMapper.updateByPrimaryKey(nlcsubjectcatalog);
			
			Integer id = nlcsubjectcatalog.getId();
			List<Integer> reslist = new ArrayList<Integer>();
			getChild(id, reslist);
			
			if(reslist.size() > 0) {
				NlcsubjectcatalogExample example2 = new NlcsubjectcatalogExample();
				Criteria criteria2 = example2.createCriteria();
				criteria2.andIdIn(reslist);
				
				Nlcsubjectcatalog record = new Nlcsubjectcatalog();
				record.setStatus(nlcsubjectcatalog.getStatus());
				nlcsubjectcatalogMapper.updateByExampleSelective(record, example2);
			}
			
			List<Nlcsubjectcatalog> listcata = getAllWithoutRootBySubjectid(nlcsubjectcatalog.getSubjectid());
			if(null != listcata && listcata.size() > 0) {
				for (Nlcsubjectcatalog nlcsubjectcatalog2 : listcata) {
					judgeIsDir(nlcsubjectcatalog2.getSubjectid(), nlcsubjectcatalog2.getCatalogid());
				}
			}
			
			String sql = "update nlcsubjectcatalog set leaforder = null where subjectid = ?";
			Object[] param = {nlcsubjectcatalog.getSubjectid()};
			jdbcTemplate.update(sql, param);
			
			f1(nlcsubjectcatalog.getSubjectid(), "root");
			
			
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
		String sql = "select catalogid from nlcsubjectcatalog where pid = ?";
		Object[] param1 = { catalogid };
		List<String> list = jdbcTemplate.queryForList(sql, String.class, param1);
		if(null != list && list.size() > 0) {
			for (String str : list) {
				reslist.add(str);
				judge(str, reslist);
			}
		}
	}

	/**
	 * 得到当前目录层的上一个、下一个的目录的catalogid
	 * @param catalogid
	 * @return
	 */
	@Override
	public String[] getPreAndNextCatalogid(String pid, Integer cseq, String subjectid) {
		if(null == cseq) {
			return null;
		}
		String[] pages = new String[2];
		
		//找上一个的catalogid
		Integer precseq = cseq - 1;
		NlcsubjectcatalogExample example1 = new NlcsubjectcatalogExample();
		Criteria criteria1 = example1.createCriteria();
		criteria1.andCseqEqualTo(precseq);
		criteria1.andPidEqualTo(pid);
		criteria1.andSubjectidEqualTo(subjectid);
		List<Nlcsubjectcatalog> list1 = nlcsubjectcatalogMapper.selectByExample(example1);
		if(null != list1 && list1.size() > 0) {
			pages[0] = list1.get(0).getCatalogid();
		}else {
			pages[0] = null;
		}
		
		//找下一个catalogid
		Integer nextseq = cseq + 1;
		NlcsubjectcatalogExample example2 = new NlcsubjectcatalogExample();
		Criteria criteria2 = example2.createCriteria();
		criteria2.andCseqEqualTo(nextseq);
		criteria2.andPidEqualTo(pid);
		criteria2.andSubjectidEqualTo(subjectid);
		List<Nlcsubjectcatalog> list2 = nlcsubjectcatalogMapper.selectByExample(example2);
		if(null != list2 && list2.size() > 0) {
			pages[1] = list2.get(0).getCatalogid();
		}else {
			pages[1] = null;
		}
		
		return pages;
	}

	/**
	 * 通过subjectid和pid查找
	 * @param subjectid
	 * @param pid
	 * @return
	 */
	@Override
	public List<Nlcsubjectcatalog> findBySubjectidAndPid(String subjectid, String pid) {
		NlcsubjectcatalogExample example = new NlcsubjectcatalogExample();
		Criteria criteria = example.createCriteria();
		criteria.andPidEqualTo(pid);
		criteria.andSubjectidEqualTo(subjectid);
		example.setOrderByClause("cseq asc");
		List<Nlcsubjectcatalog> list = nlcsubjectcatalogMapper.selectByExample(example);
		return list;
	}

	/**
	 * 插入子，并更新父目录的状态，及leaforder
	 * @param nlcsubjectcatalog
	 */
	@Override
	public void insertCatalogAndUpdateParent(Nlcsubjectcatalog nlcsubjectcatalog) {
		String catalogid = nlcsubjectcatalog.getPid();
		String subjectid = nlcsubjectcatalog.getSubjectid();
		nlcsubjectcatalogMapper.insert(nlcsubjectcatalog);
		Nlcsubjectcatalog pnlcsubjectcatalog = findBySubjectidAndCatalogid(subjectid, catalogid);
		if(null != pnlcsubjectcatalog) {
			pnlcsubjectcatalog.setIsdir("1");
			nlcsubjectcatalogMapper.updateByPrimaryKey(pnlcsubjectcatalog);
		}
		
		String sql = "update nlcsubjectcatalog set leaforder = null where subjectid = ?";
		Object[] param = {nlcsubjectcatalog.getSubjectid()};
		jdbcTemplate.update(sql, param);
		
		f1(nlcsubjectcatalog.getSubjectid(), "root");
	}

	/**
	 * 通过subjectid和catalogid查找
	 * @param subjectid
	 * @param catalogid
	 * @return
	 */
	@Override
	public Nlcsubjectcatalog findBySubjectidAndCatalogid(String subjectid, String catalogid) {
		NlcsubjectcatalogExample example = new NlcsubjectcatalogExample();
		Criteria criteria = example.createCriteria();
		criteria.andCatalogidEqualTo(catalogid);
		criteria.andSubjectidEqualTo(subjectid);
		List<Nlcsubjectcatalog> list = nlcsubjectcatalogMapper.selectByExample(example);
		if(null != list && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public List<Nlcsubjectcatalog> getAllWithoutRoot() {
		NlcsubjectcatalogExample example = new NlcsubjectcatalogExample();
		Criteria criteria = example.createCriteria();
		criteria.andCatalogidNotEqualTo("root");
		List<Nlcsubjectcatalog> list = nlcsubjectcatalogMapper.selectByExample(example);
		return list;
	}
	
	@Override
	public List<Nlcsubjectcatalog> getAllWithoutRootBySubjectid(String subjectid) {
		NlcsubjectcatalogExample example = new NlcsubjectcatalogExample();
		Criteria criteria = example.createCriteria();
		criteria.andCatalogidNotEqualTo("root");
		criteria.andSubjectidEqualTo(subjectid);
		List<Nlcsubjectcatalog> list = nlcsubjectcatalogMapper.selectByExample(example);
		return list;
	}

	@Override
	public void judgeIsDir(String subjectid, String catalogid) {
		NlcsubjectcatalogExample example2 = new NlcsubjectcatalogExample();
		Criteria criteria2 = example2.createCriteria();
		criteria2.andSubjectidEqualTo(subjectid);
		criteria2.andCatalogidEqualTo(catalogid);
		List<Nlcsubjectcatalog> list2 = nlcsubjectcatalogMapper.selectByExample(example2);
		if(null == list2 || list2.size() == 0) {
			return ;
		}
		Nlcsubjectcatalog nlcsubjectcatalog = list2.get(0);
		
		NlcsubjectcatalogExample example = new NlcsubjectcatalogExample();
		Criteria criteria = example.createCriteria();
		criteria.andSubjectidEqualTo(subjectid);
		criteria.andPidEqualTo(catalogid);
		List<Nlcsubjectcatalog> list = nlcsubjectcatalogMapper.selectByExample(example);
		
		if(null != list && list.size() > 0) { //有子树
			nlcsubjectcatalog.setIsdir("1");
		}else {	//无子树
			nlcsubjectcatalog.setIsdir("0");
		}
		nlcsubjectcatalogMapper.updateByPrimaryKeySelective(nlcsubjectcatalog);
	}

	/**
	 * 通过subjectid查找，不包含下架的
	 * @param subjectid
	 * @return
	 */
	@Override
	public List<Nlcsubjectcatalog> findBySubjectidWithoutDown(String subjectid) {
		NlcsubjectcatalogExample example = new NlcsubjectcatalogExample();
		Criteria criteria = example.createCriteria();
		criteria.andSubjectidEqualTo(subjectid);
		criteria.andStatusNotEqualTo("已下架");
		example.setOrderByClause("cseq asc");
		List<Nlcsubjectcatalog> list = nlcsubjectcatalogMapper.selectByExample(example);
		return list;
	}

	/**
	 * 找目录，catalogid是作为pid使用的
	 * @param subjectid
	 * @param catalogid
	 * @return
	 */
	@Override
	public List<Nlcsubjectcatalog> findCatalog(String subjectid, String catalogid) {
		NlcsubjectcatalogExample example = new NlcsubjectcatalogExample();
		Criteria criteria = example.createCriteria();
		criteria.andSubjectidEqualTo(subjectid);
		criteria.andPidEqualTo(catalogid);
		criteria.andStatusNotEqualTo("已下架");
		example.setOrderByClause("cseq asc");
		List<Nlcsubjectcatalog> list = nlcsubjectcatalogMapper.selectByExample(example);
		return list;
	}

	/**
	 * 得到所有的父节点
	 */
	@Override
	public List<Nlcsubjectcatalog> getAllAncestor(String catalogid) {
		if(StringUtils.isBlank(catalogid) || "root".equals(catalogid)) {
			return null;
		}
		
		List<Nlcsubjectcatalog> reslist = new ArrayList<Nlcsubjectcatalog>();
		Nlcsubjectcatalog pojo = getBycataid(catalogid);
		while(null != pojo && !pojo.getPid().equals("root")) {
			pojo = getBycataid(pojo.getPid());
			if(null != pojo) {
				reslist.add(pojo);
			}
		}
		return reslist;
	}
	
	private Nlcsubjectcatalog getBycataid(String catalogid) {
		NlcsubjectcatalogExample example = new NlcsubjectcatalogExample();
		Criteria criteria = example.createCriteria();
		criteria.andCatalogidEqualTo(catalogid);
		List<Nlcsubjectcatalog> list = nlcsubjectcatalogMapper.selectByExample(example);
		if(null != list && list.size() > 0) {
			return list.get(0);
		}
		
		return null;
	}

	/**
	 * 获得所有叶子节点
	 */
	@Override
	public List<Nlcsubjectcatalog> getAllLeaf(String subjectid, String type) {
		if(StringUtils.isBlank(subjectid)) {
			return null;
		}
		NlcsubjectcatalogExample example = new NlcsubjectcatalogExample();
		Criteria criteria = example.createCriteria();
		criteria.andSubjectidEqualTo(subjectid);
		criteria.andStatusEqualTo("已上架");
		criteria.andIsdirEqualTo("0");
		example.setOrderByClause("leaforder asc");
		List<Nlcsubjectcatalog> list = null;
		if("and".equals(type)) {
			list = nlcsubjectcatalogMapper.selectByExampleWithBLOBs(example);
		}else {
			list = nlcsubjectcatalogMapper.selectByExample(example);
		}
		return list;
	}

	@Override
	public void backSearch() {
		NlcsubjectcatalogExample example = new NlcsubjectcatalogExample();
		Criteria criteria = example.createCriteria();
		criteria.andCatalogidEqualTo("root");
		example.setOrderByClause("id asc");
		List<Nlcsubjectcatalog> firstlist = nlcsubjectcatalogMapper.selectByExample(example);
		if(null != firstlist && firstlist.size() > 0) {
			for (Nlcsubjectcatalog nlcsubjectcatalog : firstlist) {
				String sql = "update nlcsubjectcatalog set leaforder = null where subjectid = ?";
				Object[] param = {nlcsubjectcatalog.getSubjectid()};
				jdbcTemplate.update(sql, param);
				
				f1(nlcsubjectcatalog.getSubjectid(), "root");
			}
		}
	}
	
	private void f1(String subjectid, String pid) {
		NlcsubjectcatalogExample example = new NlcsubjectcatalogExample();
		Criteria criteria = example.createCriteria();
		criteria.andSubjectidEqualTo(subjectid);
		criteria.andPidEqualTo(pid);
		example.setOrderByClause("cseq asc");
		List<Nlcsubjectcatalog> list = nlcsubjectcatalogMapper.selectByExample(example);
		if(null != list && list.size() > 0) {
			for (Nlcsubjectcatalog nlcsubjectcatalog : list) {
				if("0".equals(nlcsubjectcatalog.getIsdir())) {
					String sql = "SELECT max(leaforder) FROM `nlcsubjectcatalog` where subjectid = ?";
					Object[] param = {subjectid};
					List<Integer> orderlist = jdbcTemplate.queryForList(sql, Integer.class, param);
					Integer max = 0;
					if(null != orderlist && orderlist.size() > 0) {
						if(null != orderlist.get(0))
							max = orderlist.get(0) + 1;
					}
					String sql2 = "update nlcsubjectcatalog set leaforder = ? where subjectid = ? and catalogid = ?";
					Object[] param2 = {max, subjectid, nlcsubjectcatalog.getCatalogid()};
					jdbcTemplate.update(sql2, param2);
				}else {
					f1(subjectid, nlcsubjectcatalog.getCatalogid());
				}
			}
		}
	}

}
