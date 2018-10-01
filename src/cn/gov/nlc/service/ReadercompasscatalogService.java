package cn.gov.nlc.service;

import java.util.List;
import java.util.Map;

import cn.gov.nlc.pojo.Readercompasscatalog;
import cn.gov.nlc.pojo.ReadercompasscatalogWithBLOBs;

public interface ReadercompasscatalogService {

	public List<Readercompasscatalog> getAll();
	
	public void insertCatalogAndUpdateParent(ReadercompasscatalogWithBLOBs po);
	
	public Readercompasscatalog findByCataloguuid(String cataloguuid);

	public ReadercompasscatalogWithBLOBs getCatalongContentByCataloguuid(String cataloguuid);

	public void updateContentByCataloguuid(String cataloguuid, String content, String contenthtml);

	public void catalogEdit(String cataloguuid, String title);

	public void deleteCatalogByCatalogid(String cataloguuid);

	public void updateCatalog(Map<String, String[]> parameterMap) throws Exception;
	
	public List<Readercompasscatalog> getAllWithoutRoot();
	
	public List<Readercompasscatalog> getFirst();
	
	public List<Readercompasscatalog> getChildCataloguuid(String cataloguuid);
}
