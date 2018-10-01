package cn.gov.nlc.vo;

public class DyfxPoExt extends DyfxPo{

	private String color;

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	@Override
	public String toString() {
		return "DyfxPoExt [color=" + color + ", getName()=" + getName() + ", getValue()=" + getValue()+"]";
	}
	
	
}
