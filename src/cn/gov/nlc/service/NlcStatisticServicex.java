package cn.gov.nlc.service;

import java.util.Date;
import java.util.List;

import cn.gov.nlc.pojo.GgwDetailPo;
import cn.gov.nlc.pojo.Hotword;
import cn.gov.nlc.vo.DetailPo;
import cn.gov.nlc.vo.EasyUiDataGridResult;
import cn.gov.nlc.vo.PersonPo;
import cn.gov.nlc.vo.WjDetailPo;
import cn.gov.nlc.vo.WjYmfwPo;
import cn.gov.nlc.vo.YmfwPo;
import cn.gov.nlc.vo.YmfwfxPo;

public interface NlcStatisticServicex {

	/**
	 * 页面访问量按天统计的pv
	 */
	public List<Integer> dayymfwpv(Date startDate, Date endDate);
	
	/**
	 * 页面访问量按天统计的uv
	 */
	public List<Integer> dayymfwuv(Date startDate, Date endDate);
	
	/**
	 * 页面访问量按天的list数据
	 * @param page
	 * @param rows
	 * @return
	 */
	public EasyUiDataGridResult dayymfwList(Integer page, Integer rows, Date startDate, Date endDate, String tableName);
	
	/**
	 * 页面访问导出每天的
	 */
	public List<YmfwPo> dayymfwExport(Date startDate, Date endDate, String tableName);
	
	/**
	 * 页面访问量按月统计的pv
	 */
	public List<Integer> monymfwpv(Date startDate, Date endDate);
	
	/**
	 * 页面访问量按月统计的uv
	 */
	public List<Integer> monymfwuv(Date startDate, Date endDate);
	
	/**
	 * 页面访问量按月的list数据
	 * @param page
	 * @param rows
	 * @return
	 */
	public EasyUiDataGridResult monymfwList(Integer page, Integer rows, Date startDate, Date endDate, String tableName);
	
	/**
	 * 页面访问导出每月的
	 */
	public List<YmfwPo> monymfwExport(Date startDate, Date endDate, String tableName);
	
	/**
	 * 页面访问量按年统计的pv
	 */
	public List<Integer> yearymfwpv(Date startDate, Date endDate);
	
	/**
	 * 页面访问量按年统计的uv
	 */
	public List<Integer> yearymfwuv(Date startDate, Date endDate);
	
	/**
	 * 页面访问量按年的list数据
	 * @param page
	 * @param rows
	 * @return
	 */
	public EasyUiDataGridResult yearymfwList(Integer page, Integer rows, Date startDate, Date endDate, String tableName);
	
	/**
	 * 页面访问导出每年的
	 */
	public List<YmfwPo> yearymfwExport(Date startDate, Date endDate, String tableName);
	
	/**
	 * 页面访问量按周统计的pv
	 */
	public List<Integer> weekymfwpv(Date startDate, Date endDate);
	
	/**
	 * 页面访问量按周统计的uv
	 */
	public List<Integer> weekymfwuv(Date startDate, Date endDate);
	
	/**
	 * 页面访问量按周的list数据
	 * @param page
	 * @param rows
	 * @return
	 */
	public EasyUiDataGridResult weekymfwList(Integer page, Integer rows, Date startDate, Date endDate, String tableName);
	
	/**
	 * 页面访问导出每周的
	 */
	public List<YmfwPo> weekymfwExport(Date startDate, Date endDate, String tableName);
	
	/**
	 * hotword的list数据按天的
	 */
	public EasyUiDataGridResult dayHotwordList(Integer page, Integer rows, Date startDate, Date endDate, String tableName);
	
	/**
	 * hotword的list数据按月的
	 */
	public EasyUiDataGridResult monHotwordList(Integer page, Integer rows, Date startDate, Date endDate, String tableName);
	
	/**
	 * hotword的list数据按周的
	 */
	public EasyUiDataGridResult weekHotwordList(Integer page, Integer rows, Date startDate, Date endDate, String tableName);
	
	/**
	 * hotword的list数据按年的
	 */
	public EasyUiDataGridResult yearHotwordList(Integer page, Integer rows, Date startDate, Date endDate, String tableName);
	
	/**
	 * 热词导出每天的
	 */
	public List<Hotword> dayHotwordExport(Date startDate, Date endDate, String tableName);
	
	/**
	 * 热词导出每周的
	 */
	public List<Hotword> weekHotwordExport(Date startDate, Date endDate, String tableName);
	
	/**
	 * 热词导出每月的
	 */
	public List<Hotword> monHotwordExport(Date startDate, Date endDate, String tableName);
	
	/**
	 * 热词导出每年的
	 */
	public List<Hotword> yearHotwordExport(Date startDate, Date endDate, String tableName);
	
	/**
	 * 广告位页面访问量详情按天的list数据
	 */
	public EasyUiDataGridResult dayGgwDetailList(Integer page, Integer rows, Date startDate, Date endDate);
	
	/**
	 * 广告位页面访问量详情按周的list数据
	 */
	public EasyUiDataGridResult weekGgwDetailList(Integer page, Integer rows, Date startDate, Date endDate);
	
	/**
	 * 广告位页面访问量详情按月的list数据
	 */
	public EasyUiDataGridResult monGgwDetailList(Integer page, Integer rows, Date startDate, Date endDate);
	
	/**
	 * 广告位页面访问量详情按年的list数据
	 */
	public EasyUiDataGridResult yearGgwDetailList(Integer page, Integer rows, Date startDate, Date endDate);
	
	/**
	 * 广告位页面访问量详情导出按天
	 */
	public List<GgwDetailPo> dayGgwDetailExport(Date startDate, Date endDate);
	
	/**
	 * 广告位页面访问量详情导出按周
	 */
	public List<GgwDetailPo> weekGgwDetailExport(Date startDate, Date endDate);
	
	/**
	 * 广告位页面访问量详情导出按月
	 */
	public List<GgwDetailPo> monGgwDetailExport(Date startDate, Date endDate);
	
	/**
	 * 广告位页面访问量详情导出按年
	 */
	public List<GgwDetailPo> yearGgwDetailExport(Date startDate, Date endDate);
	
	//=============带分享量的===============
	
	/**
	 * 页面访问量带分享量按天的list数据
	 */
	public EasyUiDataGridResult dayymfwfxList(Integer page, Integer rows, Date startDate, Date endDate, String tableName);
	
	/**
	 * 页面访问量带分享量按月的list数据
	 */
	public EasyUiDataGridResult monymfwfxList(Integer page, Integer rows, Date startDate, Date endDate, String tableName);
	
	/**
	 * 页面访问量带分享量按周的list数据
	 */
	public EasyUiDataGridResult weekymfwfxList(Integer page, Integer rows, Date startDate, Date endDate, String tableName);
	
	/**
	 * 页面访问量带分享量按年的list数据
	 */
	public EasyUiDataGridResult yearymfwfxList(Integer page, Integer rows, Date startDate, Date endDate, String tableName);
	
	/**
	 * 页面访问含分享量导出每天的
	 */
	public List<YmfwfxPo> dayymfwfxExport(Date startDate, Date endDate, String tableName);
	
	/**
	 * 页面访问含分享量导出每周的
	 */
	public List<YmfwfxPo> weekymfwfxExport(Date startDate, Date endDate, String tableName);
	
	/**
	 * 页面访问含分享量导出每月的
	 */
	public List<YmfwfxPo> monymfwfxExport(Date startDate, Date endDate, String tableName);
	
	/**
	 * 页面访问导出每年的
	 */
	public List<YmfwfxPo> yearymfwfxExport(Date startDate, Date endDate, String tableName);
	
	/**
	 * 页面访问量详情含分享按天的list数据
	 */
	public EasyUiDataGridResult dayDetailFxList(Integer page, Integer rows, Date startDate, Date endDate, String tableName, String tableName2, String segment);
	
	/**
	 * 页面访问量详情含分享按周的list数据
	 */
	public EasyUiDataGridResult weekDetailFxList(Integer page, Integer rows, Date startDate, Date endDate, String tableName, String tableName2, String segment);
	
	/**
	 * 页面访问量详情含分享按月的list数据
	 */
	public EasyUiDataGridResult monDetailFxList(Integer page, Integer rows, Date startDate, Date endDate, String tableName, String tableName2, String segment);
	
	/**
	 * 页面访问量详情含分享按年的list数据
	 */
	public EasyUiDataGridResult yearDetailFxList(Integer page, Integer rows, Date startDate, Date endDate, String tableName, String tableName2, String segment);
	
	/**
	 * 页面访问量详情含分享导出按天
	 */
	public List<DetailPo> dayDetailFxExport(Date startDate, Date endDate, String tableName);
	
	/**
	 * 页面访问量详情含分享导出按周
	 */
	public List<DetailPo> weekDetailFxExport(Date startDate, Date endDate, String tableName);
	
	/**
	 * 页面访问量详情含分享导出按月
	 */
	public List<DetailPo> monDetailFxExport(Date startDate, Date endDate, String tableName);
	
	/**
	 * 页面访问量详情含分享导出按年
	 */
	public List<DetailPo> yearDetailFxExport(Date startDate, Date endDate, String tableName);
	
	/**
	 * 文津页面访问总量按天的list数据
	 */
	public EasyUiDataGridResult dayymfwWjList(Integer page, Integer rows, Date startDate, Date endDate);
	
	/**
	 * 文津页面访问总量按月的list数据
	 */
	public EasyUiDataGridResult monymfwWjList(Integer page, Integer rows, Date startDate, Date endDate);
	
	/**
	 * 文津页面访问总量按周的list数据
	 */
	public EasyUiDataGridResult weekymfwWjList(Integer page, Integer rows, Date startDate, Date endDate);
	
	/**
	 * 文津页面访问总量按年的list数据
	 */
	public EasyUiDataGridResult yearymfwWjList(Integer page, Integer rows, Date startDate, Date endDate);
	
	/**
	 * 文津页面访问导出每天的
	 */
	public List<WjYmfwPo> dayWjymfwfxExport(Date startDate, Date endDate);
	
	/**
	 * 文津页面访问导出每周的
	 */
	public List<WjYmfwPo> weekWjymfwfxExport(Date startDate, Date endDate);
	
	/**
	 * 文津页面访问导出每月的
	 */
	public List<WjYmfwPo> monWjymfwfxExport(Date startDate, Date endDate);
	
	/**
	 * 文津页面访问导出每年的
	 */
	public List<WjYmfwPo> yearWjymfwfxExport(Date startDate, Date endDate);
	
	/**
	 * 文津页面访问量详情按天的list数据
	 */
	public EasyUiDataGridResult dayWjDetailFxList(Integer page, Integer rows, Date startDate, Date endDate);
	
	/**
	 * 文津页面访问量详情按周的list数据
	 */
	public EasyUiDataGridResult weekWjDetailFxList(Integer page, Integer rows, Date startDate, Date endDate);
	
	/**
	 * 文津页面访问量详情按月的list数据
	 */
	public EasyUiDataGridResult monWjDetailFxList(Integer page, Integer rows, Date startDate, Date endDate);
	
	/**
	 * 文津页面访问量详情按年的list数据
	 */
	public EasyUiDataGridResult yearWjDetailFxList(Integer page, Integer rows, Date startDate, Date endDate);
	
	/**
	 * 文津页面访问量详情导出按天
	 */
	public List<WjDetailPo> dayWjDetailFxExport(Date startDate, Date endDate);
	
	/**
	 * 文津页面访问量详情导出按周
	 */
	public List<WjDetailPo> weekWjDetailFxExport(Date startDate, Date endDate);
	
	/**
	 * 文津页面访问量详情导出按月
	 */
	public List<WjDetailPo> monWjDetailFxExport(Date startDate, Date endDate);
	
	/**
	 * 文津页面访问量详情导出按年
	 */
	public List<WjDetailPo> yearWjDetailFxExport(Date startDate, Date endDate);

	/**
	 * 个人访问量详情
	 * @param startDate
	 * @param endDate
	 * @param status
	 * @return
	 */
	public List<PersonPo> personDetailtableData(Date startDate, Date endDate, String status);
}

