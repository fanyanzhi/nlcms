package cn.gov.nlc.service;

import java.util.List;
import java.util.Map;

import cn.gov.nlc.pojo.Thirdapp;
import cn.gov.nlc.vo.EasyUiDataGridResult;

public interface ThirdappService {

	public EasyUiDataGridResult getList(int page, int rows, Thirdapp thirdapp);
	
	public void insert(Thirdapp thirdapp);
	
	public void publish(Integer id, String status);
	
	public int deleteSingleById(Integer id);
	
	public Thirdapp selectByPrimaryKey(Integer id);
	
	public void updateApp(Map<String, String[]> parameterMap) throws Exception;
	
	public List<Thirdapp> getByOs(String os);
}
