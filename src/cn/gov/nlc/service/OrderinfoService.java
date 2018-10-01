package cn.gov.nlc.service;

import java.util.Date;
import java.util.List;
import cn.gov.nlc.pojo.Orderinfo;
import cn.gov.nlc.pojo.OrderinfoExt;
import cn.gov.nlc.vo.EasyUiDataGridResult;
import net.sf.json.JSONObject;

public interface OrderinfoService {
	
	public void insertOrderinfo(Orderinfo orderinfo);
	
	/**
	 * 支付记录
	 * @param page
	 * @param rows
	 * @return
	 */
	public EasyUiDataGridResult getOrderList(Integer page, Integer rows, String loginaccount, String name, String cardno, Date pstartDate, Date pendDate);
	
	/**
	 * 导出excel用的list
	 */
	public List<OrderinfoExt> getExportList(String loginaccount, String name, String cardno, Date pstartDate, Date pendDate);
	
	/**
	 * 通过订单号找订单
	 * @param orderno
	 * @return
	 */
	public Orderinfo selectByOrderno(String orderno);
	
	/**
	 * 更新
	 * @param record
	 */
	public void updateByPrimaryKeySelective(Orderinfo record);
	
	/**
	 * 更新aleph的押金状态
	 * @param orderinfo
	 * @return
	 */
	public JSONObject updateAlephPledge(Orderinfo orderinfo);
}
