package cn.gov.nlc.service;

import cn.gov.nlc.pojo.Clouddoc;
import cn.gov.nlc.vo.EasyUiDataGridResult;

public interface ClouddocService {
	
	public void insertClouddoc(Clouddoc clouddoc);
	
	public EasyUiDataGridResult getClouddoc(String loginAccount);
	
	public void updateClouddoc(String loginAccount, Clouddoc clouddoc);
}
