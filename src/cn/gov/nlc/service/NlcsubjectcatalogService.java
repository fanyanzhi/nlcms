package cn.gov.nlc.service;

import java.util.List;
import java.util.Map;

import cn.gov.nlc.pojo.Nlcsubjectcatalog;

public interface NlcsubjectcatalogService {

	/**
	 * 通过subjectid查找
	 * @param subjectid
	 * @return
	 */
	public List<Nlcsubjectcatalog> findBySubjectid(String subjectid);
	
	/**
	 * 通过catalogid得到目录对象 (这里的catalogid不会是root)，为了得到content
	 * @param id
	 * @return
	 */
	public Nlcsubjectcatalog getCatalongContentByCatalogid(String catalogid);
	
	/**
	 * 编辑目录名后保存目录名，根据catalogid
	 * @param catalogid
	 * @param text
	 */
	public void catalogEdit(String catalogid, String title);
	
	/**
	 * 添加新的目录
	 */
	public void insertCatalog(Nlcsubjectcatalog nlcsubjectcatalog);
	
	/**
	 * 根据catalogid保存内容content，(catalogid为root时不保存)
	 */
	public void updateContentByCatalogid(String catalogid, String content);
	
	/**
	 * 根据catalogid删除目录及其子目录
	 * @param catalogid
	 */
	public void deleteCatalogByCatalogid(String catalogid);
	
	/**
	 * 根据catalogid查询目录 (不含content)
	 */
	public Nlcsubjectcatalog getCatalogByCatalogid(String catalogid);
	
	/**
	 * 修改目录
	 */
	public void updateCatalog(Map<String, String[]> parameterMap) throws Exception;

	/**
	 * 得到当前目录层的上一个、下一个的目录的catalogid
	 * @param catalogid
	 * @return
	 */
	public String[] getPreAndNextCatalogid(String pid, Integer cseq, String subjectid);
	
	/**
	 * 通过subjectid和pid查找
	 * @param subjectid
	 * @param pid
	 * @return
	 */
	public List<Nlcsubjectcatalog> findBySubjectidAndPid(String subjectid, String pid);
	
	/**
	 * 插入子，并更新父目录的状态
	 * @param nlcsubjectcatalog
	 */
	public void insertCatalogAndUpdateParent(Nlcsubjectcatalog nlcsubjectcatalog);
	
	/**
	 * 通过subjectid和catalogid查找
	 * @param subjectid
	 * @param catalogid
	 * @return
	 */
	public Nlcsubjectcatalog findBySubjectidAndCatalogid(String subjectid, String catalogid);
	
	public List<Nlcsubjectcatalog> getAllWithoutRoot();
	
	public List<Nlcsubjectcatalog> getAllWithoutRootBySubjectid(String subjectid);
	
	public void judgeIsDir(String subjectid, String catalogid);
	
	/**
	 * 通过subjectid查找，不包含下架的
	 * @param subjectid
	 * @return
	 */
	public List<Nlcsubjectcatalog> findBySubjectidWithoutDown(String subjectid);
	
	/**
	 * 找目录，catalogid是作为pid使用的
	 * @param subjectid
	 * @param catalogid
	 * @return
	 */
	public List<Nlcsubjectcatalog> findCatalog(String subjectid, String catalogid);
	
	/**
	 * 得到所有的父节点
	 */
	public List<Nlcsubjectcatalog> getAllAncestor(String catalogid);
	
	/**
	 * 得到所有的叶子节点带content
	 */
	public List<Nlcsubjectcatalog> getAllLeaf(String subjectid, String type);
	
	public void backSearch();
}
