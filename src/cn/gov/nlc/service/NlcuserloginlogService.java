package cn.gov.nlc.service;

import java.util.List;
import cn.gov.nlc.pojo.Nlcuserloginlog;

public interface NlcuserloginlogService {
	public void insertNlcuserloginlog(Nlcuserloginlog nlcuserloginlog);
	
	/**
	 * 通过username获取
	 * @param username
	 * @return
	 */
	public List<Nlcuserloginlog> getListByUsername(String username);
}
