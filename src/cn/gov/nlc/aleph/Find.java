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
 *  创建时间:2015-8-21 上午10:14:00
 *  修改历史：
 *     ver     修改日期     修改人  修改内容
 * ──────────────────────────────────
 *   V1.00   2015-8-21   Hp  初版
 *
 *  </pre>
 *
 */
public class Find implements java.io.Serializable{
	public int set_number;
	public int no_records;//检索到的记录数
	public int no_entries;//返回的记录数，最大为1000，no_records小于1000的时，no_records与no_entries相同
	public String session_id;
	public String base;
	public String base_cn;
	public boolean success = true;


	public int getSet_number() {
		return set_number;
	}


	public void setSet_number(int set_number) {
		this.set_number = set_number;
	}


	public int getNo_records() {
		return no_records;
	}


	public void setNo_records(int no_records) {
		this.no_records = no_records;
	}


	public int getNo_entries() {
		return no_entries;
	}


	public void setNo_entries(int no_entries) {
		this.no_entries = no_entries;
	}


	public String getSession_id() {
		return session_id;
	}


	public void setSession_id(String session_id) {
		this.session_id = session_id;
	}


	public String getBase() {
		return base;
	}


	public void setBase(String base) {
		this.base = base;
	}


	public boolean isSuccess() {
		return success;
	}


	public void setSuccess(boolean success) {
		this.success = success;
	}


	public String getBase_cn() {
		return base_cn;
	}


	public void setBase_cn(String base_cn) {
		this.base_cn = base_cn;
	}


	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return set_number+", "+no_records+", "+no_entries+", "+session_id+", "+base;
	}
}
