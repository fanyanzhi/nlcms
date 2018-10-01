package cn.gov.nlc.service;

import java.util.List;

import cn.gov.nlc.pojo.Downlistenbook;
import cn.gov.nlc.pojo.Listenbook;

public interface DownlistenbookService {
	
	public void insertDownlistenbook(Downlistenbook downlistenbook);
	
	public void deleteDownlistenbook(String loginAccount, String bookid);
	
	public boolean existdownlistenbook(String loginAccount, String bookid);
	
	public List<Listenbook> getDownlistenbook(String loginAccount);
	
	public void updateStatus(String loginAccount, String bookid, Integer status);
}
