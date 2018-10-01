package cn.gov.nlc.service;

import java.util.List;

import cn.gov.nlc.pojo.Collectreadtx;
import cn.gov.nlc.pojo.Readtx;

public interface CollectreadtxService {

	public void insertCollectreadtx(Collectreadtx collectreadtx);

	public void deleteCollectreadtx(String loginAccount, String magazineId);

	public List<Readtx> getCollectreadtx(String loginAccount);

	public boolean existCollectreadtx(String loginAccount, String magazineId);
	
	public void updateStatus(String loginAccount, String magazineId, Integer status);

}
