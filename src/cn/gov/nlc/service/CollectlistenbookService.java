package cn.gov.nlc.service;

import java.util.List;

import cn.gov.nlc.pojo.Collectlistenbook;
import cn.gov.nlc.pojo.Listenbook;

public interface CollectlistenbookService {
	
	public void insertCollectlistenbook(Collectlistenbook collectlistenbook);
	
	public void deleteCollectlistenbook(String loginAccount, String bookid);
	
	public boolean existcollectlistenbook(String loginAccount, String bookid);
	
	public List<Listenbook> getCollectlistenbook(String loginAccount);
	
	public void updateStatus(String loginAccount, String bookid, Integer status);
}
