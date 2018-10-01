package cn.gov.nlc.pojo;

/**
 * 订单信息的扩展，保存了用户类型
 * @author DAYI
 *
 */
public class OrderinfoExt extends Orderinfo{

	private String rdrolecode;

	public String getRdrolecode() {
		return rdrolecode;
	}

	public void setRdrolecode(String rdrolecode) {
		this.rdrolecode = rdrolecode;
	}
	
}
