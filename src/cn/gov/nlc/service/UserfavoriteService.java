package cn.gov.nlc.service;

import cn.gov.nlc.pojo.Userfavorite;
import cn.gov.nlc.vo.EasyUiDataGridResult;

public interface UserfavoriteService {
	public void addFavoriteInfo(Userfavorite userfavorite);

	public int getFavoriteID(String userName, String odatatype, String fileid);

	public boolean checkUserFavorite(String userName, String odatatype, String fileid);

	public int getFavoriteCount(String userName);

	public EasyUiDataGridResult getFavoriteList(String userName, int start, int length);

	public void delFavorite(String userName, String id);
}
