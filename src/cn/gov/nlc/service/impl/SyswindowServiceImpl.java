package cn.gov.nlc.service.impl;

import java.util.Date;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.gov.nlc.mapper.SyswindowMapper;
import cn.gov.nlc.pojo.Syswindow;
import cn.gov.nlc.pojo.SyswindowExample;
import cn.gov.nlc.pojo.SyswindowExample.Criteria;
import cn.gov.nlc.pojo.SyswindowExt;
import cn.gov.nlc.service.SyswindowService;
import cn.gov.nlc.util.Jdpush;
import cn.gov.nlc.vo.EasyUiDataGridResult;

@Service
public class SyswindowServiceImpl implements SyswindowService {

	@Autowired
	SyswindowMapper syswindowMapper;

	@Override
	public void insertWindowThfour(String pushmethod, Byte type, String message, String fid, Date time,
			String pubname) {
		if (StringUtils.isNotBlank(pushmethod)) {
			String[] sarr = pushmethod.split(",");
			for (String str : sarr) {
				if ("0".equals(str)) { // 1是站内信，0是弹窗
					Syswindow syswindow = new Syswindow();
					syswindow.setType(type);
					syswindow.setMessage(message);
					syswindow.setPubname(pubname);
					syswindow.setUsername("全体读者");
					syswindow.setFid(fid);
					syswindow.setTime(time);
					syswindowMapper.insert(syswindow);
					String strtype = "";// 0:管理员群发信息；1：交易记录；2：图书催还；3：违规：预约未取、逾期没还，这个还得测试；4：新闻；5：公告；6：讲座；7：专题
					switch (type) {
					case 4:
						strtype = "news";
						break;
					case 5:
						strtype = "notice";
						break;
					case 6:
						strtype = "trailer";
						break;
					case 7:
						strtype = "subject";
						break;
					}
					Jdpush.pushMessage(2, "zi_xun", message, "国家图书馆", strtype, fid);
				}
			}
		}
	}

	/**
	 * 弹窗展示页面，包括高级查询
	 * 
	 * @param page
	 * @param rows
	 * @param syswindowExt
	 * @return
	 */
	@Override
	public EasyUiDataGridResult getwindowList(Integer page, Integer rows, SyswindowExt syswindowExt) {
		String username = syswindowExt.getUsername();
		String pubname = syswindowExt.getPubname();
		Byte type = syswindowExt.getType();
		Date ztime = syswindowExt.getZtime();
		Date ytime = syswindowExt.getYtime();

		// 分页
		PageHelper.startPage(page, rows);
		SyswindowExample example = new SyswindowExample();
		Criteria criteria = example.createCriteria();

		if (StringUtils.isNotBlank(pubname)) {
			criteria.andPubnameEqualTo(pubname);
		}

		if (StringUtils.isNotBlank(username)) {
			criteria.andUsernameEqualTo(username);
		}

		if (null != type) {
			criteria.andTypeEqualTo(type);
		}

		if (null != ztime && null != ytime) {
			criteria.andTimeBetween(ztime, ytime);
		} else if (null != ztime) {
			criteria.andTimeGreaterThanOrEqualTo(ztime);
		} else if (null != ytime) {
			criteria.andTimeLessThanOrEqualTo(ytime);
		}

		example.setOrderByClause("time desc");
		List<Syswindow> list = syswindowMapper.selectByExample(example);
		PageInfo<Syswindow> pageInfo = new PageInfo<>(list);
		long total = pageInfo.getTotal();
		EasyUiDataGridResult result = new EasyUiDataGridResult(total, list);
		return result;
	}

}
