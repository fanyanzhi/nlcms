package cn.gov.nlc.vo;

/**
 * 机型
 * @author DAYI
 *
 */
public class Modelx {

	private String time;
	
	private String model;
	//新增装机
	private int addInstallNum;
	//启动次数
	private int startNum;
	
	private String modelName;
	
	public String getModelName() {
		return modelName;
	}
	public void setModelName(String modelName) {
		this.modelName = modelName;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public int getAddInstallNum() {
		return addInstallNum;
	}
	public void setAddInstallNum(int addInstallNum) {
		this.addInstallNum = addInstallNum;
	}
	public int getStartNum() {
		return startNum;
	}
	public void setStartNum(int startNum) {
		this.startNum = startNum;
	}
	
}
