package cn.gov.nlc.service;

import java.util.List;

import cn.gov.nlc.pojo.Usernormalword;

public interface UsernormalwordService {
	
	public void setUsernormalword(String userName, String word);
	
	public void insertUsernormalword(Usernormalword normalword);
	
	public int getUsernormalword(String userName, String word);
	
	public void updSeaCount(int id);
	
	public List<Usernormalword> getHotword(String userName);
}
