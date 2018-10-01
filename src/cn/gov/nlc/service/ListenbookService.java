package cn.gov.nlc.service;

import cn.gov.nlc.pojo.Listenbook;

public interface ListenbookService {

	public void insertListenbook(Listenbook listenbook);

	public boolean existListenbook(String bookid);

}
