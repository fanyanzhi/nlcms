/**
 *  Copyright (c) 2015 Neusoft.com corporation All Rights Reserved.
 */

package cn.gov.nlc.aleph;

/**
 *  此处进行功能描述。
 *
 *  @author Hp
 *  @version 1.0
 *
 *  <pre>
 *  使用范例：
 *  创建时间:2015-8-25 下午03:03:55
 *  修改历史：
 *     ver     修改日期     修改人  修改内容
 * ──────────────────────────────────
 *   V1.00   2015-8-25   Hp  初版
 *
 *  </pre>
 *
 */
public class Item {
	@Override
	public String toString() {
		return "Item [barcode=" + barcode + ", call_no_1=" + call_no_1
				+ ", call_no_2=" + call_no_2 + ", description=" + description
				+ ", item_status=" + item_status + ", library=" + library
				+ ", loan_due_date=" + loan_due_date + ", loan_due_hour="
				+ loan_due_hour + ", sub_library=" + sub_library_desc + "]";
	}
	
	//是否能预约
	public boolean hold_allowed;
	//预约
	public String yuyue;
	//单册状态
	public String item_status;
	//单册状态代码
	public String item_status_code;

	//单册状态
	public String item_status_desc;
	//索取号
	public String call_no_1;
	//应还日期
	public String loan_due_date;
	//应还时间
	public String loan_due_hour;
	//库
	public String library;
	//子库
	public String sub_library;
	//子库代码
	public String sub_library_code;
	
	//子库
	public String sub_library_desc;
	//架位导航
	public String call_no_2;
	//请求数
	public String request_count;
	//条码
	public String barcode;
	//描述
	public String description;
	//馆藏信息
	public String guancang;
	
	public String getItem_status_code() {
		return item_status_code;
	}
	public void setItem_status_code(String item_status_code) {
		this.item_status_code = item_status_code;
	}
	public String getSub_library_code() {
		return sub_library_code;
	}
	public void setSub_library_code(String sub_library_code) {
		this.sub_library_code = sub_library_code;
	}
	public boolean isHold_allowed() {
		return hold_allowed;
	}
	public void setHold_allowed(boolean hold_allowed) {
		this.hold_allowed = hold_allowed;
	}
	public String getYuyue() {
		return yuyue;
	}
	public void setYuyue(String yuyue) {
		this.yuyue = yuyue;
	}
	public String getItem_status() {
		return item_status;
	}
	public void setItem_status(String item_status) {
		this.item_status = item_status;
	}
	public String getItem_status_desc() {
		return item_status_desc;
	}
	public void setItem_status_desc(String item_status_desc) {
		this.item_status_desc = item_status_desc;
	}
	public String getCall_no_1() {
		return call_no_1;
	}
	public void setCall_no_1(String call_no_1) {
		this.call_no_1 = call_no_1;
	}
	public String getLoan_due_date() {
		return loan_due_date;
	}
	public void setLoan_due_date(String loan_due_date) {
		this.loan_due_date = loan_due_date;
	}
	public String getLoan_due_hour() {
		return loan_due_hour;
	}
	public void setLoan_due_hour(String loan_due_hour) {
		this.loan_due_hour = loan_due_hour;
	}
	public String getLibrary() {
		return library;
	}
	public void setLibrary(String library) {
		this.library = library;
	}
	public String getSub_library() {
		return sub_library;
	}
	public void setSub_library(String sub_library) {
		this.sub_library = sub_library;
	}
	public String getSub_library_desc() {
		return sub_library_desc;
	}
	public void setSub_library_desc(String sub_library_desc) {
		this.sub_library_desc = sub_library_desc;
	}
	public String getCall_no_2() {
		return call_no_2;
	}
	public void setCall_no_2(String call_no_2) {
		this.call_no_2 = call_no_2;
	}
	public String getRequest_count() {
		return request_count;
	}
	public void setRequest_count(String request_count) {
		this.request_count = request_count;
	}
	public String getBarcode() {
		return barcode;
	}
	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getGuancang() {
		return guancang;
	}
	public void setGuancang(String guancang) {
		this.guancang = guancang;
	}


}


