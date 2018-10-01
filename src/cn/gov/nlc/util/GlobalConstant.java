package cn.gov.nlc.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class GlobalConstant {

	public static final String SESSION_INFO = "sessionInfo";

	public static final Integer ENABLE = 0; // 启用
	public static final Integer DISABLE = 1; // 禁用

	public static final String PLACE_GRID = "place_grid";
	public static final String PLACE_GRID_UV = "place_grid_uv";
	public static final String MAP_DATA = "mapData";
	public static final String MAP_DATA_UV = "mapDataUv";
	public static final String TYPE_GRID = "type_grid";
	public static final String TYPE_GRID_UV = "type_grid_uv";
	public static final String ZTYS = "ztys";// 主题样式

	public static final Integer DEFAULT = 0; // 默认
	public static final Integer NOT_DEFAULT = 1; // 非默认

	public static final Map sexlist = new HashMap() {
		{
			put("0", "男");
			put("1", "女");
		}
	};
	public static final Map statelist = new HashMap() {
		{
			put("0", "启用");
			put("1", "停用");
		}
	};
	public static final Map ztyslist = new HashMap() {
		{
			put("0", "正常");
			put("1", "节日");
			put("2", "哀悼日");
		}
	};
	public static final Map yearlist = new TreeMap() {
		{
			put("2015年", "2015");
			put("2016年", "2016");
			put("2017年", "2017");
			put("2018年", "2018");
			put("2019年", "2019");
			put("2020年", "2020");
			put("2021年", "2021");
			put("2022年", "2022");
		}
	};
	public static final Map monthlist = new TreeMap() {
		{
			put("01月", "01");
			put("02月", "02");
			put("03月", "03");
			put("04月", "04");
			put("05月", "05");
			put("06月", "06");
			put("07月", "07");
			put("08月", "08");
			put("09月", "09");
			put("10月", "10");
			put("11月", "11");
			put("12月", "12");
		}
	};

	public static final List<String> col_list = new ArrayList<String>() {
		{
			add("网站首页");
			add("信息资讯");
			add("资源阅读");
			add("我的国图");
			add("应用下载");
			add("动态数据");
			add("移动检索");
			add("外部链接");
		}
	};
	public static final Map<String, String> col_list_map = new HashMap<String, String>() {
		{
			put("wzsy", "网站首页");
			put("xxzx", "信息资讯");
			put("zyyd", "资源阅读");
			put("dzfw", "我的国图");
			put("yyxz", "应用下载");
			put("dtsj", "动态数据");
			put("ydjs", "移动检索");
			put("wblj", "外部链接");
		}
	};

	public static final Map<String, String> provincelist = new HashMap<String, String>() {
		{
			put("浙江", "zj");
			put("甘肃", "gs");
			put("宁夏", "nx");
			put("安徽", "ah");
			put("黑龙江", "hl");
			put("吉林", "jl");
			put("辽宁", "ln");
			put("河北", "hb");
			put("北京", "bj");
			put("天津", "tj");
			put("山东", "sd");
			put("江苏", "js");
			put("福建", "fj");
			put("广东", "gd");
			put("广西", "gx");
			put("云南", "yn");
			put("西藏", "xz");
			put("新疆", "xj");
			put("陕西", "sa");
			put("内蒙古", "nm");
			put("山西", "sx");
			put("上海", "sh");
			put("青海", "qh");
			put("四川", "sc");
			put("重庆", "cq");
			put("河南", "he");
			put("湖北", "hu");
			put("湖南", "hn");
			put("贵州", "gz");
			put("江西", "jx");
			put("海南", "ha");
		}
	};

	public static final Map<String, String> baseList = new HashMap<String, String>() {
		{
			put("NLC01S", "中文及特藏数据库");
			put("NLC01", "中文及特藏数据库");
			put("B1BOK", "中文普通图书库");
			put("B1AVE", "音像制品和电子资源（含中外文）");
			put("B1ETH", "民语文献");
			put("B1SER", "中文期刊");
			put("B1GTB", "台港图书及海外出版的中文图书");
			put("B1CAP", "地方志、家谱文献");
			put("B1NWP", "中文报纸");
			put("B1AN2", "普通古籍（含新线装）");
			put("B1DDB", "学位论文");
			put("B1ANC", "善本古籍文献");
			put("B1FID", "联合国资料");
			put("B1MIC", "中文缩微文献");
			put("NLC09", "外文文献数据总库");
			put("NLC09S", "外文文献数据总库");
			put("B9FBK", "外文图书");
			put("B9ANC", "外文善本");
			put("B9PER", "外文期刊");
			put("B9MIC", "外文缩微文献");
			put("B9MUS", "外文乐谱");
			put("B9NEW", "外文报纸（含台港外文报纸）");
			put("B9CAR", "外文地图");
			put("B9FGD", "国际组织和外国政府出版物");
		}
	};

	public static final Map<String, String> subLibraryList = new HashMap<String, String>() {
		{
			put("ZWWJ", "中文图书借阅区");
			put("WJCN", "南区基藏外文图书外借出纳台");
			put("WJDY", "中文图书第一外借库");
			put("WJDR", "中文图书第二外借库");
			put("SEWX", "少年儿童图书馆");
			put("XNZC", "中文图书采选组");
			put("SKBC", "书刊保存本库");
			put("ZC-BJ", "专藏-巴金子库");
			put("ZWJC", "中文基藏");
			put("ZWTS", "北区中文图书区");
			put("CJYL", "古籍馆残疾人阅览室");
			put("FGDZ", "古籍馆电子阅览室");
			put("FGYX", "古籍馆音像资料视听室");
			put("FGBK", "古籍馆中文报刊阅览室");
			put("FGFZ", "古籍馆地方志家谱阅览室");
			put("FGGQ", "方志馆地方文献第二阅览室");
			put("FGPG", "古籍馆普通古籍阅览室");
			put("DFWXY", "古籍馆家谱旧方志阅览室");
			put("DFWXR", "古籍馆家谱旧方志阅览室");
			put("TCYL", "南区综合阅览室");
			put("BSLW", "南区学位论文阅览室");
			put("BZDR", "北区中文报纸区");
			put("BCBK", "报纸保存本库");
			put("BZDY", "北区中文报纸区");
			put("BZDS", "北区中文报纸区");
			put("DHYL", "南区敦煌吐鲁番资料阅览室");
			put("DZDY", "第一电子阅览室");
			put("DZDR", "暂存音视频文献");
			put("XNJH", "国际交换组");
			put("GJZZ", "南区国际组织与外国政府出版物室");
			put("GJSS", "南区综合阅览室");
			put("WWGJ", "南区外文文献第一阅览室");
			put("JCSK", "南区基藏书刊临时阅览室");
			put("YLCN", "南区综合阅览室");
			put("WWYL", "外文文献阅览区");
			put("QSWX", "古籍馆清史文献中心");
			put("RBWK", "日本出版物文库阅览室");
			put("SBYL", "古籍馆善本阅览室");
			put("FGJY", "古籍馆中文图书借阅室");
			put("SWYL", "缩微文献阅览室");
			put("SWKF", "缩微中心库");
			put("TCJC", "南区台港澳文献阅览室");
			put("TGKK", "台港期刊(一般)");
			put("TGSK", "台港期刊(特殊)");
			put("TGQK", "南区台港澳文献阅览室");
			put("TGTS", "南区台港澳文献阅览室");
			put("TGHZB", "南区台港澳文献阅览室");
			put("TSTGB", "台港特殊中文报纸");
			put("TGYCN", "南区台港澳文献阅览室");
			put("TSWB", "台港外文报纸(特殊)");
			put("YBWB", "外文报纸库");
			put("WBCN", "南区外报出纳台");
			put("TSGX", "图书馆学资料室");
			put("WKDY", "外文文献第二阅览室");
			put("WKDS", "南区外文期刊第三阅览室");
			put("WWDY", "外文文献阅览区");
			put("WWDR", "外文文献阅览区");
			put("WWDS", "外文文献阅览区");
			put("XWJY", "外文图书阅览外借室");
			put("WWJC", "外文藏书");
			put("WXJS", "文献检索室");
			put("XWTS", "外文文献第一阅览室");
			put("REWTS", "外文文献第一阅览室");
			put("DWXYZ", "外文文献第一阅览室");
			put("WKDR", "外文文献第二阅览室");
			put("WWXB", "外文文献第二阅览室");
			put("YSSJ", "艺术设计特藏资料室");
			put("YBYX", "暂存音视频文献");
			put("TSYX", "音像资料库(特殊)");
			put("NJYL", "南区年鉴 新方志阅览室");
			put("MZYW", "南区中国少数民族文献阅览室");
			put("BCKK", "中文期刊周转库");
			put("ZKWJ", "中文期刊复本库");
			put("ZKQK", "北区中文期刊区");
			put("ZSQK", "北区中文期刊区");
			put("JDTS", "北区经典图书区");
			put("QKJC", "外文期刊库");
			put("XNQK", "中文期刊组");
			put("ILLSL", "馆际互借库");
			put("ILLCN", "馆际互借出纳台");
			put("ILLCR", "馆际互借出纳台二");
			put("ILLCS", "馆际互借出纳台三");
			put("SZGX", "北区数字共享空间");
			put("ZWZL", "中文资料阅览室");
			put("FLWX", "法律参考阅览室");
			put("ZGWX", "海外中国问题研究资料中心");
			put("ZBZCN", "北区中文报纸区");
			put("ZKYCN", "北区中文期刊区");
			put("ZQKBC", "北区中文期刊保存本库");
			put("ZQKMG", "北区民国期刊库");
			put("ZQKJC", "北区中文期刊基藏本库");
			put("FZJC", "年鉴 新方志阅览室");
			put("WSZL", "年鉴 新方志阅览室");
			put("FZYL", "年鉴 新方志阅览室");
			put("VWKCF", "外文期刊采访");

		}
	};
}
