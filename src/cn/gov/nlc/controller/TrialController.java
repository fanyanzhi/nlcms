package cn.gov.nlc.controller;

import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gov.nlc.pojo.Wjreader;
import cn.gov.nlc.service.WjreaderService;
import cn.gov.nlc.util.Common;
import cn.gov.nlc.util.Jdpush;

@Controller
public class TrialController {

	private static final Logger logger = Logger.getLogger(cn.gov.nlc.controller.TrialController.class);
	
	@Autowired
	private WjreaderService wjreaderService;
	
	@RequestMapping("/pushtest")
	@ResponseBody
	public void testPush(String content) {
		String wenjin = Common.sendGet("http://m.nlc.cn/client/subjectContentToday?id=ff80808151e7dfd10151eb8a288402e7",
				null);
		wenjin = wenjin.substring(wenjin.indexOf("<div class=\\\"wjjdsd-shiju-yl\\\"><p>")
				+ "<div class=\\\"wjjdsd-shiju-yl\\\"><p>".length());
		String shiju = wenjin.substring(0, wenjin.indexOf("</p>"));
		// System.out.println(shiju);
		wenjin = wenjin.substring(wenjin.indexOf("<p style=\\\"text-align: right;\\\">")
				+ "<p style=\\\"text-align: right;\\\">".length() + 2);
		String sjsource = wenjin.substring(0, wenjin.indexOf("</p>"));
		// System.out.println(sjsource);
		wenjin = wenjin.substring(
				wenjin.indexOf("<div class=\\\"shiju-ywcont\\\">") + "<div class=\\\"shiju-ywcont\\\">".length());
		String sjyiwen = wenjin.substring(0, wenjin.indexOf("</div>"));
		int idiv = 0;
		String quanshi = "";
		if (wenjin.indexOf("shiju-qscont") > 0 && wenjin.indexOf("shiju-qscont") < 200) {
			wenjin = wenjin.substring(
					wenjin.indexOf("<div class=\\\"shiju-qscont\\\">") + "<div class=\\\"shiju-qscont\\\">".length());
			idiv = wenjin.indexOf("</div>");
			quanshi = wenjin.substring(0, idiv);
		}
		wenjin = wenjin.substring(idiv + 6);
		String sjurl = "";
		if (wenjin.indexOf("http://") < 200) {
			wenjin = wenjin.substring(wenjin.indexOf("http://"));

			if (wenjin.indexOf(".mp3") > 0 && wenjin.indexOf(".mp3") < 100) {
				sjurl = wenjin.substring(0, wenjin.indexOf(".mp3") + 4);
			}
			if (wenjin.indexOf(".3gp") > 0 && wenjin.indexOf(".3gp") < 100) {
				sjurl = wenjin.substring(0, wenjin.indexOf(".3gp") + 4);
			}
		}
		wenjin = wenjin.substring(wenjin.indexOf("<div class=\\\"wjjdsd-geyan-yl\\\"><p>")
				+ "<div class=\\\"wjjdsd-geyan-yl\\\"><p>".length());
		String geyan = wenjin.substring(0, wenjin.indexOf("</p>"));
		// System.out.println(geyan);
		wenjin = wenjin.substring(wenjin.indexOf("<p style=\\\"text-align: right;\\\">")
				+ "<p style=\\\"text-align: right;\\\">".length() + 2);
		String gysource = wenjin.substring(0, wenjin.indexOf("</p>"));
		// System.out.println(gysource);
		wenjin = wenjin.substring(
				wenjin.indexOf("<div class=\\\"geyan-ywcont\\\">") + "<div class=\\\"geyan-ywcont\\\">".length());
		String gyyiwen = wenjin.substring(0, wenjin.indexOf("</div>"));
		Wjreader wjreader = new Wjreader();
		wjreader.setPid("ff808081519e421901519edfb677013e");
		wjreader.setShiju(shiju);
		wjreader.setSjsource(sjsource);
		wjreader.setSjyiwen(sjyiwen);
		wjreader.setQuanshi(quanshi);
		wjreader.setSjurl(sjurl);
		wjreader.setGeyan(geyan);
		wjreader.setGysource(gysource);
		wjreader.setGyyiwen(gyyiwen);
		wjreader.setWjdate(new Date());
		Calendar cal = Calendar.getInstance();
		wjreader.setWjmonth((cal.get(Calendar.MONTH) + 1) + "");
		wjreader.setWjyear(cal.get(Calendar.YEAR) + "");
		logger.info("【文津推送测试】");
		Jdpush.pushMessage(2, "wen_jin", wjreader.getShiju(), "国家图书馆"+content, "wenjin", "");
	}
	
	@RequestMapping("/insertAndPushTodayWj")
	@ResponseBody
	public void insertAndPushTodayWj(String content) {
		String wenjin = Common.sendGet("http://m.nlc.cn/client/subjectContentToday?id=ff80808151e7dfd10151eb8a288402e7",
				null);
		wenjin = wenjin.substring(wenjin.indexOf("<div class=\\\"wjjdsd-shiju-yl\\\"><p>")
				+ "<div class=\\\"wjjdsd-shiju-yl\\\"><p>".length());
		String shiju = wenjin.substring(0, wenjin.indexOf("</p>"));
		// System.out.println(shiju);
		wenjin = wenjin.substring(wenjin.indexOf("<p style=\\\"text-align: right;\\\">")
				+ "<p style=\\\"text-align: right;\\\">".length() + 2);
		String sjsource = wenjin.substring(0, wenjin.indexOf("</p>"));
		// System.out.println(sjsource);
		wenjin = wenjin.substring(
				wenjin.indexOf("<div class=\\\"shiju-ywcont\\\">") + "<div class=\\\"shiju-ywcont\\\">".length());
		String sjyiwen = wenjin.substring(0, wenjin.indexOf("</div>"));
		int idiv = 0;
		String quanshi = "";
		if (wenjin.indexOf("shiju-qscont") > 0 && wenjin.indexOf("shiju-qscont") < 200) {
			wenjin = wenjin.substring(
					wenjin.indexOf("<div class=\\\"shiju-qscont\\\">") + "<div class=\\\"shiju-qscont\\\">".length());
			idiv = wenjin.indexOf("</div>");
			quanshi = wenjin.substring(0, idiv);
		}
		wenjin = wenjin.substring(idiv + 6);
		String sjurl = "";
		if (wenjin.indexOf("http://") < 200) {
			wenjin = wenjin.substring(wenjin.indexOf("http://"));

			if (wenjin.indexOf(".mp3") > 0 && wenjin.indexOf(".mp3") < 100) {
				sjurl = wenjin.substring(0, wenjin.indexOf(".mp3") + 4);
			}
			if (wenjin.indexOf(".3gp") > 0 && wenjin.indexOf(".3gp") < 100) {
				sjurl = wenjin.substring(0, wenjin.indexOf(".3gp") + 4);
			}
		}
		wenjin = wenjin.substring(wenjin.indexOf("<div class=\\\"wjjdsd-geyan-yl\\\"><p>")
				+ "<div class=\\\"wjjdsd-geyan-yl\\\"><p>".length());
		String geyan = wenjin.substring(0, wenjin.indexOf("</p>"));
		// System.out.println(geyan);
		wenjin = wenjin.substring(wenjin.indexOf("<p style=\\\"text-align: right;\\\">")
				+ "<p style=\\\"text-align: right;\\\">".length() + 2);
		String gysource = wenjin.substring(0, wenjin.indexOf("</p>"));
		// System.out.println(gysource);
		wenjin = wenjin.substring(
				wenjin.indexOf("<div class=\\\"geyan-ywcont\\\">") + "<div class=\\\"geyan-ywcont\\\">".length());
		String gyyiwen = wenjin.substring(0, wenjin.indexOf("</div>"));
		Wjreader wjreader = new Wjreader();
		wjreader.setPid("ff808081519e421901519edfb677013e");
		wjreader.setShiju(shiju);
		wjreader.setSjsource(sjsource);
		wjreader.setSjyiwen(sjyiwen);
		wjreader.setQuanshi(quanshi);
		wjreader.setSjurl(sjurl);
		wjreader.setGeyan(geyan);
		wjreader.setGysource(gysource);
		wjreader.setGyyiwen(gyyiwen);
		wjreader.setWjdate(new Date());
		Calendar cal = Calendar.getInstance();
		wjreader.setWjmonth((cal.get(Calendar.MONTH) + 1) + "");
		wjreader.setWjyear(cal.get(Calendar.YEAR) + "");
		logger.info("【文津插入和推送】");
		Jdpush.pushMessage(2, "wen_jin", wjreader.getShiju(), "国家图书馆"+content, "wenjin", "");
		wjreaderService.insertWjreader(wjreader);
	}
}
