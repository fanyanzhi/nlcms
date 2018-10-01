package cn.gov.nlc.service;

import java.util.Date;

import cn.gov.nlc.pojo.SyswindowExt;
import cn.gov.nlc.vo.EasyUiDataGridResult;

public interface SyswindowService {

	/**
	 * 通过4大类插入弹窗(在4大类添加的时候)
	 */
	public void insertWindowThfour(String pushmethod, Byte type, String message, String fid, Date time, String pubname);

	/**
	 * 弹窗展示页面，包括高级查询
	 * @param page
	 * @param rows
	 * @param syswindowExt
	 * @return
	 */
	public EasyUiDataGridResult getwindowList(Integer page, Integer rows, SyswindowExt syswindowExt);
	
}
