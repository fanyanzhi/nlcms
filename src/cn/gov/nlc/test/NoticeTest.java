package cn.gov.nlc.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.gov.nlc.pojo.Delayorderinfodetail;
import cn.gov.nlc.pojo.Nlcnews;
import cn.gov.nlc.pojo.Nlcnotice;
import cn.gov.nlc.pojo.Sysmessage;
import cn.gov.nlc.service.DelayorderinfodetailService;
import cn.gov.nlc.service.NlcnewsService;
import cn.gov.nlc.service.NlcnoticeService;
import cn.gov.nlc.service.NlcuserService;
import cn.gov.nlc.vo.EasyUiDataGridResult;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/applicationContext-*.xml"})
public class NoticeTest {

	@Autowired
	private NlcuserService nlcuserService;
	@Autowired
	private NlcnoticeService nlcnoticeService;
	@Autowired
	private NlcnewsService nlcnewsService;
	@Autowired
	private DelayorderinfodetailService delayorderinfodetailService;
	
	@Test
	public void noticetest() {
		boolean res = nlcuserService.checkPhone("18811343049");
		System.out.println(res);
		/*EasyUiDataGridResult easyUiDataGridResult = nlcnoticeService.getAppNoticeList(1, 10);
		List<Nlcnotice> rows = (List<Nlcnotice>) easyUiDataGridResult.getRows();
		System.out.println(new ArrayList<>(rows));*/
	}
	
	@Test
	public void noticetest2() {
		EasyUiDataGridResult easyUiDataGridResult = nlcnewsService.getnewsList(1, 10);
		List<Nlcnews> rows = (List<Nlcnews>) easyUiDataGridResult.getRows();
		System.out.println(new ArrayList<>(rows));
	}
	
	@Test
	public void nlcnewsInsertTest() {
		Nlcnews n = new Nlcnews();
		n.setNewsid("111");
		nlcnewsService.insertNews(n);
		System.out.println(n);
		nlcnewsService.insertNews(n);
		System.out.println(n);
		nlcnewsService.insertNews(n);
	}
	
	@Test
	public void sysmessage() {
		JSONObject jsono = new JSONObject();
		jsono.put("orderno", "888888851072804920170414104937");	//商户订单号
		jsono.put("tradeno", "2017041421001004600201579192");	//支付宝订单号
		jsono.put("sum", "1.80");		//金额
		List<Delayorderinfodetail> detailList = delayorderinfodetailService.getListByOrderno("888888851072804920170414104937");
		JSONArray array = JSONArray.fromObject(detailList);
		jsono.put("data", array);
		System.out.println(jsono);
	}
}
