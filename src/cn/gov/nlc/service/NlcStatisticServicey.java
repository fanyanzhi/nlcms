package cn.gov.nlc.service;

import java.util.Date;
import java.util.List;

import cn.gov.nlc.vo.Dyfx;
import cn.gov.nlc.vo.DyfxPoExt;
import cn.gov.nlc.vo.EasyUiDataGridResult;
import cn.gov.nlc.vo.Edition;
import cn.gov.nlc.vo.Modelx;
import cn.gov.nlc.vo.Yhhx;

public interface NlcStatisticServicey {

	/**
	 * 活跃用户按天统计的list
	 * @param page
	 * @param rows
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public EasyUiDataGridResult dayHyyhList(Integer page, Integer rows, Date startDate, Date endDate);
	
	/**
	 * 活跃用户按星期统计的list
	 * @param page
	 * @param rows
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public EasyUiDataGridResult weekHyyhList(Integer page, Integer rows, Date startDate, Date endDate);
	
	/**
	 * 活跃用户按月统计的list
	 * @param page
	 * @param rows
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public EasyUiDataGridResult monHyyhList(Integer page, Integer rows, Date startDate, Date endDate);
	
	/**
	 * 沉默用户按周统计的list
	 * @param page
	 * @param rows
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public EasyUiDataGridResult weekCmyhList(Integer page, Integer rows, Date startDate, Date endDate);

	/**
	 * 用户画像的统计
	 * @param page
	 * @param rows
	 * @param startDate
	 * @param endDate
	 * @param yhhx
	 * @return
	 */
	public EasyUiDataGridResult yhhxList(Integer page, Integer rows, Date startDate, Date endDate, Yhhx yhhx);

	/**
	 * 用户画像详情的list
	 * @param page
	 * @param rows
	 * @param startDate
	 * @param endDate
	 * @param loginaccount
	 * @return
	 */
	public EasyUiDataGridResult detailList(Integer page, Integer rows, Date startDate, Date endDate, String loginaccount);
	
	/**
	 * app统计按天统计的list
	 */
	public EasyUiDataGridResult dayApptjList(Integer page, Integer rows, Date startDate, Date endDate);
	
	/**
	 * app统计按周统计的list
	 */
	public EasyUiDataGridResult weekApptjList(Integer page, Integer rows, Date startDate, Date endDate);
	
	/**
	 * app统计按月统计的list
	 */
	public EasyUiDataGridResult monthApptjList(Integer page, Integer rows, Date startDate, Date endDate);
	
	/**
	 * app统计按年统计的list
	 */
	public EasyUiDataGridResult yearApptjList(Integer page, Integer rows, Date startDate, Date endDate);

	/**
	 * 版本安装量table的数据
	 * @param startDate
	 * @param endDate
	 * @param status
	 * @return
	 */
	public List<Edition> EditionTableData(Date startDate, Date endDate, String status);
	
	/**
	 * 装机详情的table的数据
	 * @param startDate
	 * @param endDate
	 * @param status
	 * @return
	 */
	public List<Modelx> modelTableData(Date startDate, Date endDate, String status);

	/**
	 * 地域分析条形图的数据
	 */
	public List<DyfxPoExt> dyfxData(Date startDate, Date endDate, String status);

	/**
	 * 地域分析省的table数据
	 * @param startDate
	 * @param endDate
	 * @param status
	 * @return
	 */
	public List<Dyfx> dyfxTableData(Date startDate, Date endDate, String status, String type);
	
}
