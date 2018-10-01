package cn.gov.nlc.service;

import java.util.List;

import cn.gov.nlc.pojo.Downreadtx;
import cn.gov.nlc.pojo.Readtx;

public interface DownreadtxService {
	
	public void insertDownreadtx(Downreadtx downreadtx);
	
	public void deleteDownreadtx(String loginAccount, String magazineId);
	
	public List<Readtx> getDownreadtx(String loginAccount);
	
	public boolean existDownreadtx(String loginAccount, String magazineId);
	
	public void updateStatus(String loginAccount, String magazineId, Integer status);
}
