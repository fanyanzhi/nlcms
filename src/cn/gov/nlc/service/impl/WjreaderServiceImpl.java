package cn.gov.nlc.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.gov.nlc.base.converter.BeanDateConnverter;
import cn.gov.nlc.mapper.WjreaderMapper;
import cn.gov.nlc.pojo.Wjreader;
import cn.gov.nlc.pojo.WjreaderExample;
import cn.gov.nlc.pojo.WjreaderExample.Criteria;
import cn.gov.nlc.pojo.WjreaderExt;
import cn.gov.nlc.service.WjreaderService;
import cn.gov.nlc.util.Common;
import cn.gov.nlc.util.Jdpush;
import cn.gov.nlc.vo.EasyUiDataGridResult;
import net.sf.json.JSONObject;

@Service
public class WjreaderServiceImpl implements WjreaderService {

	@Autowired
	WjreaderMapper wjreaderMapper;

	@Override
	public void insertWjreader(Wjreader wjreader) {
		wjreaderMapper.insert(wjreader);
	}

	@Override
	public Wjreader getWjreaderToday() {
		WjreaderExample example = new WjreaderExample();
		Criteria criteria = example.createCriteria();
		criteria.andWjdateEqualTo(new Date());
		criteria.andStatusEqualTo("已发布");
		List<Wjreader> list = wjreaderMapper.selectByExample(example);
		if (null != list && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public Wjreader getWjreaderYestoday() {
		PageHelper.startPage(1, 1);
		WjreaderExample example = new WjreaderExample();
		example.setOrderByClause("wjdate desc");
		List<Wjreader> list = wjreaderMapper.selectByExample(example);
		if (null != list && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public Wjreader getWjreader(String date) {
		WjreaderExample example = new WjreaderExample();
		Criteria criteria = example.createCriteria();
		criteria.andWjdateEqualTo(Common.ConvertToDate(date.replace(".", "-") + " 00:00:00", "yyyy-MM-dd"));
		criteria.andStatusEqualTo("已发布");
		List<Wjreader> list = wjreaderMapper.selectByExample(example);
		if (list != null && list.size() > 0)
			return list.get(0);
		return null;
	}

	/**
	 * 通过wjdate获取
	 * @param wjdate
	 * @return
	 */
	public Wjreader getWjreaderBywjdate(Date wjdate) {
		WjreaderExample example = new WjreaderExample();
		Criteria criteria = example.createCriteria();
		criteria.andWjdateEqualTo(wjdate);
		criteria.andStatusEqualTo("已发布");
		List<Wjreader> list = wjreaderMapper.selectByExample(example);
		if (list != null && list.size() > 0)
			return list.get(0);
		return null;
	}

	@Override
	public EasyUiDataGridResult getWjreaderList(int page, int rows, WjreaderExt wjreaderExt) {
		Date startWjdate = wjreaderExt.getStartWjdate();
		Date endWjdate = wjreaderExt.getEndWjdate();
		String source = wjreaderExt.getSource();
		String status = wjreaderExt.getStatus();
		String quanshi = wjreaderExt.getQuanshi();
		String geyan = wjreaderExt.getGeyan();
		// 分页
		PageHelper.startPage(page, rows);
		
		WjreaderExample example = new WjreaderExample();
		Criteria criteria = example.createCriteria();
		
		if(StringUtils.isNotBlank(source)) {
			criteria.andSourceEqualTo(source);
		}
		
		if(StringUtils.isNotBlank(status)) {
			criteria.andStatusEqualTo(status);
		}
		
		if(StringUtils.isNotBlank(quanshi)) {
			criteria.andQuanshiLike("%"+quanshi+"%");
		}
		
		if(StringUtils.isNotBlank(geyan)) {
			criteria.andGeyanLike("%"+geyan+"%");
		}
		
		if(null != startWjdate && null != endWjdate) {
			criteria.andWjdateBetween(startWjdate, endWjdate);
		} else if(null != startWjdate) {
			criteria.andWjdateGreaterThanOrEqualTo(startWjdate);
		} else if(null != endWjdate) {
			criteria.andWjdateLessThanOrEqualTo(endWjdate);
		}
		
		example.setOrderByClause("wjdate desc");
		List<Wjreader> list = wjreaderMapper.selectByExample(example);
		PageInfo<Wjreader> pageInfo = new PageInfo<>(list);
		long total = pageInfo.getTotal();
		EasyUiDataGridResult result = new EasyUiDataGridResult(total, list);
		return result;
	}

	@Override
	public void publish(Integer id, String status) {
		Wjreader wjreader = wjreaderMapper.selectByPrimaryKey(id);
		if("1".equals(status)) {
			wjreader.setStatus("已发布");
			wjreader.setBkpubtime(new Date());
		}else {
			wjreader.setStatus("未发布");
			wjreader.setBkpubtime(null);
		}
		
		wjreaderMapper.updateByPrimaryKey(wjreader);
	}

	@Override
	public int deleteSingleById(Integer id) {
		int result = wjreaderMapper.deleteByPrimaryKey(id);
		return result;
	}

	@Override
	public JSONObject pull() {
		JSONObject result = new JSONObject();
		
		String wenjin = Common.sendGet("http://m.nlc.cn/client/subjectContentToday?id=ff80808151e7dfd10151eb8a288402e7", null);
		
		if(StringUtils.isBlank(wenjin)) {
			result.put("result", false);
			result.put("message", "接口连接失败");
			return result;
		}
		
		boolean resultb = JudgeExistToday();
		if(true == resultb) {
			result.put("result", true);
			return result;
		}
		
		wenjin = wenjin.substring(wenjin.indexOf("<div class=\\\"wjjdsd-shiju-yl\\\"><p>")
				+ "<div class=\\\"wjjdsd-shiju-yl\\\"><p>".length());
		String shiju = wenjin.substring(0, wenjin.indexOf("</p>"));
		
		wenjin = wenjin.substring(wenjin.indexOf("<p style=\\\"text-align: right;\\\">")
				+ "<p style=\\\"text-align: right;\\\">".length() + 2);
		String sjsource = wenjin.substring(0, wenjin.indexOf("</p>"));
		
		wenjin = wenjin.substring(wenjin.indexOf("<div class=\\\"shiju-ywcont\\\">") + "<div class=\\\"shiju-ywcont\\\">".length());
		String sjyiwen = wenjin.substring(0, wenjin.indexOf("</div>"));
		
		int idiv = 0;
		String quanshi = "";
		if (wenjin.indexOf("shiju-qscont") > 0 && wenjin.indexOf("shiju-qscont") < 200) {
			wenjin = wenjin.substring(wenjin.indexOf("<div class=\\\"shiju-qscont\\\">") + "<div class=\\\"shiju-qscont\\\">".length());
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
		
		wenjin = wenjin.substring(wenjin.indexOf("<p style=\\\"text-align: right;\\\">")
				+ "<p style=\\\"text-align: right;\\\">".length() + 2);
		String gysource = wenjin.substring(0, wenjin.indexOf("</p>"));
		
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
		wjreader.setSource("手机门户");
		wjreader.setStatus("已发布");
		wjreader.setBkpubtime(new Date());
		insertWjreader(wjreader);
		
		result.put("result", true);
		return result;
	}

	@Override
	public boolean JudgeExistToday() {
		WjreaderExample example = new WjreaderExample();
		Criteria criteria = example.createCriteria();
		criteria.andWjdateEqualTo(new Date());
		List<Wjreader> list = wjreaderMapper.selectByExample(example);
		if(null == list || list.size() == 0) {
			return false;
		}
		return true;
	}

	@Override
	public Wjreader selectByPrimaryKey(Integer id) {
		Wjreader wjreader = wjreaderMapper.selectByPrimaryKey(id);
		return wjreader;
	}

	@Override
	public void updateObj(Map<String, String[]> parameterMap) throws Exception {
		String[] ids = parameterMap.get("id");
		Integer id = Integer.parseInt(ids[0]);
		Wjreader oldWjreader = selectByPrimaryKey(id);
		ConvertUtils.register(new BeanDateConnverter(), Date.class);
		BeanUtils.populate(oldWjreader, parameterMap);
		String status = oldWjreader.getStatus();
		if("已发布".equals(status)) {
			oldWjreader.setBkpubtime(new Date());
		}else {
			oldWjreader.setBkpubtime(null);
		}
		
		Date wjdate = oldWjreader.getWjdate();
		DateTime dt = new DateTime(wjdate);
		oldWjreader.setWjyear(dt.getYear()+"");
		oldWjreader.setWjmonth(dt.getMonthOfYear()+"");
		wjreaderMapper.updateByPrimaryKey(oldWjreader);
	}

}
