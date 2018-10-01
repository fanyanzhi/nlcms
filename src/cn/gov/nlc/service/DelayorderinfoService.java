package cn.gov.nlc.service;

import java.util.Date;
import java.util.List;
import cn.gov.nlc.pojo.DelayOrderinfoExt;
import cn.gov.nlc.pojo.Delayorderinfo;
import cn.gov.nlc.vo.EasyUiDataGridResult;
import net.sf.json.JSONObject;

public interface DelayorderinfoService {

	/**
	 * 插入
	 * @param delayorderinfo
	 */
	public void insert(Delayorderinfo delayorderinfo);
	
	/**
	 * 更改订单的状态
	 * @param orderno
	 * @param tradeno
	 */
	public void updateStatus(String orderno, String tradeno);
	
	/**
	 * 获取用户欠滞纳金的详情
	 * @param loginAccount
	 * @return
	 */
	public JSONObject delayCashDetail(String loginAccount);
	
	/**
	 * 更新滞纳金状态
	 * @param borId
	 * @param ip
	 * @param sum
	 * @return
	 */
	public JSONObject updateDelayCash(String loginAccount, String borId, String ip, String orderno, String tradeno, String allSum);

	/**
	 * 支付记录
	 * @param page
	 * @param rows
	 * @param loginaccount
	 * @param name
	 * @param cardno
	 * @param pstartDate
	 * @param pendDate
	 * @return
	 */
	public EasyUiDataGridResult getOrderList(Integer page, Integer rows, String loginaccount, String name, String cardno, Date pstartDate, Date pendDate);
	
	/**
	 * 导出excel
	 */
	public List<DelayOrderinfoExt> getExportList(String loginaccount, String name, String cardno, Date pstartDate, Date pendDate);
	
	/**
	 * 通过商户订单号取得
	 * @param orderno
	 * @return
	 */
	public Delayorderinfo getByOrderno(String orderno);
}
