package cn.gov.nlc.service;

import cn.gov.nlc.pojo.Readtx;

public interface ReadtxService {
	
	public void insertReadtx(Readtx readtx);
	
	public boolean existReadtx(String magazineid);
	
}
