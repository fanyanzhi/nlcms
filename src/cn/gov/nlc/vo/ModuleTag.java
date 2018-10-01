package cn.gov.nlc.vo;

import java.io.IOException;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import cn.gov.nlc.pojo.Menu;
import cn.gov.nlc.service.ParamsManagerService;
import cn.gov.nlc.util.AppUtil;

public class ModuleTag extends SimpleTagSupport{
	
	protected ParamsManagerService paramsManagerService = (ParamsManagerService)AppUtil.getBean("paramsManagerService");
	
	private String id;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public void doTag() throws JspException, IOException {
		PageContext pg = (PageContext)this.getJspContext();
		List<Menu> molist = paramsManagerService.getModule();
		
		StringBuffer sb = new StringBuffer();
		sb.append("<tr><td width=\"20%\" align=\"right\">模块选择：</td><td width=\"30%\">");
		if(null != molist && molist.size() > 0) {
			for (Menu menu : molist) {
				sb.append("<input type=\"checkbox\" name=\"moduleid\" value=\""+menu.getId()+"\"><span>"+menu.getTextcn()+"</span> ");
			}
		}
		
		sb.append("</td></tr>");
		pg.getOut().write(sb.toString());
	}


}
