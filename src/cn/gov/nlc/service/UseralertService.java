package cn.gov.nlc.service;

import java.util.List;

import cn.gov.nlc.pojo.Useralert;

public interface UseralertService {

	public void insertUseralert(Useralert useralert);
	
	public boolean existThisAlert(String loginAccount, String type, String fileid);
	
	public List<Useralert> getUseralert();
	
	public boolean cancelUseralert(String loginAccount, String type, String fileid);
}
