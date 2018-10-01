package cn.gov.nlc.service;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import cn.gov.nlc.vo.ApptjPo;
import cn.gov.nlc.vo.DyfxPo;
import cn.gov.nlc.vo.DyfxPoExt;
import cn.gov.nlc.vo.EasyUiDataGridResult;
import cn.gov.nlc.vo.NewInstallUserDetail;
import cn.gov.nlc.vo.OsDetail;
import cn.gov.nlc.vo.PageStatistic;
import cn.gov.nlc.vo.RfwlfbPo;
import cn.gov.nlc.vo.SexDis;
import cn.gov.nlc.vo.SharePo;
import cn.gov.nlc.vo.StartCountDetail;
import cn.gov.nlc.vo.UserTypeDis;
import cn.gov.nlc.vo.VirtualUserDetail;
import cn.gov.nlc.vo.XsdfbPo;
import cn.gov.nlc.vo.ZyhxPo;
import cn.gov.nlc.vo.ZyhxTablePo;

public interface NlcStatisticService {

	/**
	 * 实时统计今日新增用户数
	 * @return
	 */
	public Collection<Object> sstjXzyhsjr();
	
	/**
	 * 实时统计今日新增用户总数
	 * @return
	 */
	public Integer sstjXzyhsjr2();
	
	/**
	 * 实时统计今日启动次数
	 */
	public Collection<Object> sstjQdcsjr();
	
	/**
	 * 实时统计今日启动总次数
	 */
	public Integer sstjQdcsjr2();
	
	/**
	 * 用户分析新增用户趋势
	 */
	public List<Integer> xzyhqs(Date startDate, Date endDate);
	
	/**
	 * 用户分析新增用户明细
	 */
	public List<NewInstallUserDetail> xzyhmx(Date startDate, Date endDate);

	/**
	 * 用户分析启动次数趋势
	 */
	public List<Integer> qdcsqs(Date startDate, Date endDate);

	/**
	 * 用户分析启动次数明细
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List<StartCountDetail> qdcsmx(Date startDate, Date endDate);

	/**
	 * 用户数量/用户类型分布 饼图的数据
	 * update by JJJ 20170223 am
	 * @return
	 */
//	public Map<String, String> yhlxfb1();
	public Map<String, String> yhlxfb1(Date startDate ,Date endDate);

	/**
	 * 用户数量/用户类型分布 各类型用户数据
	 * update by JJJ 20170223 am
	 * @return
	 */
	//public List<Integer> yhlxfb2();
	public List<Integer> yhlxfb2(Date startDate ,Date endDate);
	
	/**
	 * 虚拟用户数量数据
	 */
	//public List<Integer> xnyhslDataList(Date startDate, Date endDate);
	public List<VirtualUserDetail>  xnyhslDataList(Date startDate, Date endDate,String type);
	/**
	 * 虚拟用户分页数据  add by JJJ
	 * @param page
	 * @param rows
	 * @return
	 */
	public List<VirtualUserDetail> xnyhtableList(Integer page, Integer rows, Date startDate, Date endDate,String type,Boolean getAll);
//	/**
//	 * 虚拟用户分页数据  update by JJJ
//	 * @param page
//	 * @param rows
//	 * @return
//	 */
//	public EasyUiDataGridResult yhslPic4List(Integer page, Integer rows, Date startDate, Date endDate);
	
	/**
	 * 虚拟用户数据导出excel的list
	 */
	//public List<VirtualUserDetail> xnyhExport(Date startDate, Date endDate);

	/**
	 * 跳转到用户分析/用户属性 第一个图的第一个小图男女占比的饼图数据
	 * update by JJJ 20170222 pm
	 */
	//public Map<String, String> yhsx1p1();
	public Map<String, String> yhsx1p1(Date startDate, Date endDate);

	/**
	 * 跳转到用户分析/用户属性 第一个图的第二个小图男女饼图年龄占比的数据
	 * update by JJJ 20170222 pm
	 */
//	public Map<String, String> yhsx1p2();
	public Map<String, String> yhsx1p2(Date startDate, Date endDate);

	/**
	 * 跳转到用户分析/用户属性 第一个图的第三个小图年龄分布数据
	 * update by JJJ 20170222 pm
	 */
	//public List<UserTypeDis> yhsx1p3();
	public List<UserTypeDis> yhsx1p3(Date startDate, Date endDate);

	/**
	 * 跳转到用户分析/用户属性 第一个图的第三个小图性别分布数据
	 */
	//public List<SexDis> yhsx1p3_2();
	public List<SexDis> yhsx1p3_2(Date startDate,Date endDate);

	/**
	 * 用户分析/用户属性 学历分布的环形数据
	 * update by JJJ 20170222 pm
	 * @return
	 */
	//public Map<String, String> yhsxxl();
	public Map<String, String> yhsxxl(Date startDate,Date endDate);
	

	/**
	 * 用户分析/用户属性 学历分布的table 
	 * update by JJJ 20170222 pm
	 * @return
	 */
//	public List<UserTypeDis> yhsxxlsj();
	public List<UserTypeDis> yhsxxlsj(Date startDate,Date endDate);

	/**
	 * 终端属性/新增用户  os1的数据
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public Map<String, String> xzyhOs1(Date startDate, Date endDate);

	/**
	 * 终端属性/增加用户/数据明细的数据
	 */
	public List<OsDetail> xzyhOsmx(Date startDate, Date endDate);

	/**
	 * 终端属性/新增用户  os2的数据
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public Map<String, String> xzyhOs2(Date startDate, Date endDate);

	/**
	 * 地域分析条形图的数据
	 */
	public List<DyfxPoExt> dyfxData(Date startDate, Date endDate);

	/**
	 * 地域分析中列表的list
	 * @param page
	 * @param rows
	 * @return
	 */
	public EasyUiDataGridResult dyfxList(Integer page, Integer rows, Date startDate, Date endDate);
	
	/**
	 * 地域分析导出excel的数据
	 */
	public List<DyfxPo> dyfxExportList(Date startDate, Date endDate);
	
	/**
	 * 实名用户数量数据
	 */
	//public List<Integer> smyhslDataList(Date startDate, Date endDate);
	public List<VirtualUserDetail> smyhslDataList(Date startDate, Date endDate,String type); 
	/**
	 * 实名用户的用户分页数据
	 * @param page
	 * @param rows
	 * @return
	 */
	//public EasyUiDataGridResult smyhs2List(Integer page, Integer rows, Date startDate, Date endDate);
	public List<VirtualUserDetail> smyhtableList(Integer page, Integer rows, Date startDate, Date endDate,String type,boolean getAll);
	/**
	 * 实名用户数据导出excel的list
	 */
	//public List<VirtualUserDetail> smyhExport(Date startDate, Date endDate);
	
	/**
	 * 持卡用户数量数据
	 */
	//public List<Integer> ckyhslDataList(Date startDate, Date endDate);
	public List<VirtualUserDetail> ckyhslDataList(Date startDate, Date endDate,String type);
	/**
	 * 持卡用户的用户分页数据 update by JJJ
	 * @param page
	 * @param rows
	 * @return
	 */
	//public EasyUiDataGridResult ckyhs2List(Integer page, Integer rows, Date startDate, Date endDate);
	public List<VirtualUserDetail> ckyhtableList(Integer page, Integer rows, Date startDate, Date endDate,String type,boolean getAll);
	
	/**
	 * 持卡用户数据导出excel的list  update by JJJ
	 */
	//public List<VirtualUserDetail> ckyhExport(Date startDate, Date endDate);
	
	/**
	 * 持卡用户统计图中的预约数据 holdrecord表   续借数据 renewrecord表   add by JJJ
	 * @param startDate
	 * @param endDate
	 * @param type  年  月  周  日 
	 * @param flag 1表示预约   2 表示续借
	 * @return
	 */
	public String ckyhyyxjList(Date startDate, Date endDate, String type, String flag);
//	/**
//	 * 持卡用户统计图中的预约数据 holdrecord表
//	 */
//	public List<Integer> ckholdList(Date startDate, Date endDate);
//
//	/**
//	 * 持卡用户统计图中的续借数据 renewrecord表
//	 */
//	public List<Integer> renewList(Date startDate, Date endDate);

	/**
	 * 实时统计/页面统计/表格的页面的list
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List<PageStatistic> ymtjtable(Date startDate, Date endDate);
	
	/**
	 * 启动次数含重复的数据
	 * 人数
	 */
	public Collection<Object> qdcsycList(Date calcdate);
	
	/**
	 * 启动次数去重的数据
	 * 流量
	 */
	public Collection<Object> qdcsqcList(Date calcdate);
	
	/**
	 * 日访问量分布，流量
	 * 去重的
	 */
	public List<Integer> rfwlllList(Date startDate, Date endDate);
	
	/**
	 * add by jjj
	 */
	public List<List<?>> apptjLine(Date startDate, Date endDate,String type,String flag);
	
	/**
	 * app统计iphone下载量折线图数据
	 */
	//public List<Integer> apptjiphonexzList(Date startDate, Date endDate);
	
	/**
	 * app统计android下载量折线图数据
	 */
	//public List<Integer> apptjandroidxzList(Date startDate, Date endDate);
	
	/**add by JJJ 
	 * APP统计的表格(下载量,更新量)
	 * @param rows 
	 * @param page 
	 */
	//public EasyUiDataGridResult tableData(Integer page, Integer rows, Date startDate, Date endDate,String type,String flag,Boolean getAll);
	
	/**
	 * APP统计下载量的表格
	 * @param rows 
	 * @param page 
	 */
	//public EasyUiDataGridResult xztableData(Integer page, Integer rows, Date startDate, Date endDate,String type,Boolean getAll);
	
	/**
	 * app统计iphone更新量折线图数据
	 */
	//public List<Integer> apptjiphonegxList(Date startDate, Date endDate);
	
	/**
	 * app统计android更新量折线图数据
	 */
	//public List<Integer> apptjandroidgxList(Date startDate, Date endDate);
	/**
	 * add by jjj
	 */
	//public List<List<?>> apptjgxList(Date startDate, Date endDate,String type);
	
	/**
	 * APP统计更新量的表格
	 */
	//public EasyUiDataGridResult gxtableData(Integer page, Integer rows,Date startDate, Date endDate,String type,Boolean getAll);

	/**
	 * 资源画像浏览量  update by JJJ 20170223 pm
	 */
	public List<ZyhxPo> zyhxDataList(Date startDate, Date endDate,String type,String zytype,String flag);
	
	/**
	 * 资源画像期刊下载量
	 */
	//public List<ZyhxPo> zyhxqkxzList(Date startDate, Date endDate);
	
	/**
	 * 资源画像期刊收藏量
	 */
	//public List<ZyhxPo> zyhxqkscList(Date startDate, Date endDate);
	
	/**
	 * 资源画像听书浏览量
	 */
	//public List<ZyhxPo> zyhxtsllList(Date startDate, Date endDate);
	
	/**
	 * 资源画像听书下载量
	 */
	//public List<ZyhxPo> zyhxtsxzList(Date startDate, Date endDate);
	
	/**
	 * 资源画像听书收藏量
	 */
	//public List<ZyhxPo> zyhxtsscList(Date startDate, Date endDate);
	
	/**
	 * 资源画像文津诵读浏览量
	 */
	//public List<ZyhxPo> zyhxwjllList(Date startDate, Date endDate);
	
	/**
	 * 资源画像特色专题浏览量
	 */
	//public List<ZyhxPo> zyhxztllList(Date startDate, Date endDate);
	
	/**
	 * 资源画像特色专题收藏量
	 */
	//public List<ZyhxPo> zyhxztscList(Date startDate, Date endDate);
	
	/**
	 * 资源画像总访问量
	 * update by　ＪＪＪ　２０１７０２２４　ａｍ
	 */
	public List<ZyhxPo> zyhxzfwlList(Date startDate, Date endDate,String type,String flag);
	
	/**
	 * 资源画像总访问量下载量
	 */
	//public List<ZyhxPo> zyhxzxzList(Date startDate, Date endDate);
	
	/**
	 * 资源画像总访问量收藏量
	 */
	//public List<ZyhxPo> zyhxzscList(Date startDate, Date endDate);

	/**
	 * 资源画像中的期刊的访问数据的列表  update by JJJ 20170223 pm
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public EasyUiDataGridResult zyhxtableData(Integer page, Integer rows,Date startDate, Date endDate,String type,String zytype,boolean getAll);
	
	/**
	 * 资源画像中的听书的访问数据的列表  update by JJJ 20170223 pm
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	//public List<ZyhxTablePo> zyhxtstableData(Date startDate, Date endDate);
	
	/**
	 * 资源画像中的文津诵读的访问数据的列表  update by JJJ 20170223 pm
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	//public List<ZyhxTablePo> zyhxwjtableData(Date startDate, Date endDate);
	
	/**
	 * 资源画像中的特色专题的访问数据的列表  update by JJJ 20170223 pm
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	//public List<ZyhxTablePo> zyhxzttableData(Date startDate, Date endDate);
	
	/**
	 * 资源画像中的总访问量数据的列表
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public EasyUiDataGridResult zyhxzfwltableData(Integer page, Integer rows,Date startDate, Date endDate,String type,boolean getAll);
	
	/**
	 * 各资源画像的list数据    update by JJJ 20170224 pm
	 * @param page
	 * @param rows
	 * @return
	 */
	public EasyUiDataGridResult gzyhxList(Integer page, Integer rows,String sort, String order, String type, String magazineid, String title,Date startDate,Date endDate,boolean getAll);
	
	/**
	 * 各资源画像--查看资源画像--性别分布的数据
	 *  update by JJJ 20170224 pm
	 */
	public Map<String, String> ckzyhxxbfb(String type, String magazineid,Date startDate,Date endDate);

	/**
	 * 查看资源画像--年龄分布2D条形图数据
	 * update by JJJ 20170224 pm
	 * @param type
	 * @param magazineid
	 * @return
	 */
	public List<DyfxPoExt> ckzyhxnlfb1(String type, String magazineid, Map<String, String> resmap,Date startDate,Date endDate);

	/**
	 * 各资源画像/查看各资源画像中学历分布的图
	 * update by JJJ 20170224 pm
	 */
	public Map<String, String> ckzyhxxlfb(String type, String magazineid,Date startDate,Date endDate);
	
	
	/**
	 * app统计iphone时长折线图数据
	 */
	public List<Integer> apptjiphonescList(Date startDate, Date endDate);
	
	/**
	 * app统计android更新量折线图数据
	 */
	public List<Integer> apptjandroidscList(Date startDate, Date endDate);
	
	/**
	 * APP统计更新量的表格
	 */
	public List<ApptjPo> sctableData(Date startDate, Date endDate);

	/**
	 * 小时段分布table的数据
	 * @param calcdate
	 * @return
	 */
	public List<XsdfbPo> xsdfbTableDate(Date calcdate);
	
	/**
	 * 日访问量分布的list数据
	 * @param page
	 * @param rows
	 * @return
	 */
	public EasyUiDataGridResult rfwlfbList(Integer page, Integer rows, Date startDate, Date endDate);

	/**
	 * 日访问量导出每日的
	 */
	public List<RfwlfbPo> rfwlExport1(Date startDate, Date endDate);
	
	/**
	 * 用户分析启动次数趋势按月统计的
	 */
	public List<Integer> monQdcsqs(Date startDate, Date endDate);
	
	/**
	 * 日访问量用户数量趋势按月统计的
	 * 去重的
	 */
	public List<Integer> monyhslqs(Date startDate, Date endDate);
	
	/**
	 * 日访问量分布的list数据按月统计
	 * @param page
	 * @param rows
	 * @return
	 */
	public EasyUiDataGridResult monRfwlfbList(Integer page, Integer rows, Date startDate, Date endDate);
	
	/**
	 * 日访问量导出每月的
	 */
	public List<RfwlfbPo> monRfwlExport(Date startDate, Date endDate);
	
	/**
	 * 用户分析启动次数趋势按年统计的
	 */
	public List<Integer> nQdcsqs(Date startDate, Date endDate);
	
	/**
	 * 日访问量用户数量趋势按年统计的
	 * 去重的
	 */
	public List<Integer> nyhslqs(Date startDate, Date endDate);
	
	/**
	 * 日访问量分布的list数据按年统计
	 * @param page
	 * @param rows
	 * @return
	 */
	public EasyUiDataGridResult nRfwlfbList(Integer page, Integer rows, Date startDate, Date endDate);
	
	/**
	 * 日访问量导出每年的
	 */
	public List<RfwlfbPo> nRfwlExport(Date startDate, Date endDate);
	
	/**
	 * 用户分析启动次数趋势按周统计的
	 */
	public List<Integer> zQdcsqs(Date startDate, Date endDate);
	
	/**
	 * 日访问量用户数量趋势按周统计的
	 * 去重的
	 */
	public List<Integer> zyhslqs(Date startDate, Date endDate);
	
	/**
	 * 日访问量分布的list数据按周统计
	 * @param page
	 * @param rows
	 * @return
	 */
	public EasyUiDataGridResult zRfwlfbList(Integer page, Integer rows, Date startDate, Date endDate);
	
	/**
	 * 日访问量导出每周的
	 */
	public List<RfwlfbPo> zRfwlExport(Date startDate, Date endDate);
	
	/**
	 * 新增用户中的list数据 按天统计
	 * @param page
	 * @param rows
	 * @return
	 */
	public EasyUiDataGridResult rxzyhList(Integer page, Integer rows, Date startDate, Date endDate);
	
	/**
	 * 新增用户导出每天的统计
	 */
	public List<NewInstallUserDetail> rxzyhExport(Date startDate, Date endDate);
	
	/**
	 * 用户分析新增用户趋势 按月统计
	 */
	public List<Integer> monxzyhqs(Date startDate, Date endDate);
	
	/**
	 * 新增用户中的list数据 按月统计
	 * @param page
	 * @param rows
	 * @return
	 */
	public EasyUiDataGridResult monrxzyhList(Integer page, Integer rows, Date startDate, Date endDate);
	
	/**
	 * 新增用户导出每月的统计
	 */
	public List<NewInstallUserDetail> monrxzyhExport(Date startDate, Date endDate);
	
	/**
	 * 第三方分享  update by JJJ
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	//public EasyUiDataGridResult sffxlb(Integer page, Integer rows, Date startDate, Date endDate);
	public EasyUiDataGridResult sffxtableList(Integer page, Integer rows, Date startDate, Date endDate,String type,boolean gatAll);
	
	public List<Integer> sffxDataList(Date startDate, Date endDate);
	
	public Map<String, String> sffxsj(Date startDate, Date endDate);
	
	public List<SharePo> sffxExport(Date startDate, Date endDate);
	
	/**
	 * 用户分析新增用户趋势 按年统计
	 */
	public List<Integer> nxzyhqs(Date startDate, Date endDate);
	
	/**
	 * 新增用户中的list数据 按年统计
	 * @param page
	 * @param rows
	 * @return
	 */
	public EasyUiDataGridResult nrxzyhList(Integer page, Integer rows, Date startDate, Date endDate);

	/**
	 * 新增用户导出每年的统计
	 */
	public List<NewInstallUserDetail> nrxzyhExport(Date startDate, Date endDate);
	
	/**
	 * 用户分析新增用户趋势 按周统计
	 */
	public List<Integer> zxzyhqs(Date startDate, Date endDate);
	
	/**
	 * 新增用户中的list数据 按周统计
	 * @param page
	 * @param rows
	 * @return
	 */
	public EasyUiDataGridResult zxzyhList(Integer page, Integer rows, Date startDate, Date endDate);
	
	/**
	 * 新增用户导出每周的统计
	 */
	public List<NewInstallUserDetail> zxzyhExport(Date startDate, Date endDate);
	/**
	 * 阅读用户List add by JJJ 20170228 pm 
	 * @param type  r 期刊    l 听书
	 * @param magazineid   资源ID
	 * @return
	 */
	public EasyUiDataGridResult ydyhList(Integer page, Integer rows, String type, String magazineid, Date startDate,
			Date endDate ,boolean getAll);
	/**
	 * 阅读情况List add by JJJ 20170228 pm 
	 * @param username
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public EasyUiDataGridResult readDetailList(Integer page, Integer rows, String username, Date startDate,
			Date endDate,boolean getAll);

}
