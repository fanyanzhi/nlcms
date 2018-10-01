package cn.gov.nlc.service;

import cn.gov.nlc.pojo.Binduser;

public interface BinduserService {
	
	public Binduser getNlcuserinfo(String token);
	
	public void insertBinduser(Binduser binduser);
}
