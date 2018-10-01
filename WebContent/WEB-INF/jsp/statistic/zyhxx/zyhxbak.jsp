<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../TagLib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" type="text/css" href="${ctx}/js/easyui/themes/default/easyui.css"/>
<link rel="stylesheet" type="text/css" href="${ctx}/js/easyui/themes/icon.css"/>
<script type="text/javascript" src="${ctx}/js/jquery-1.8.3.js"></script>
<script type="text/javascript" src="${ctx}/js/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${ctx}/js/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="${ctx}/ichartjs/ichart.1.2.min.js"></script>
</head>
<body>
<div>
	<table>
		<tr>
			<td align="left" style="font-weight:bold;">选择时间：</td>
			
			<td>
				<input type="text" id="startDate" name="startDate" class="easyui-datebox" /> -- 
				<input type="text" id="endDate" name="endDate" class="easyui-datebox" />&nbsp;
			</td>
			
			<td>
				&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" onclick="begindraw()" value="统计"/>
			</td>
		</tr>
		
		<tr>
			<td align="left" style="font-weight:bold;">选择资源类别：</td>
			
			<td>
				<input type="radio" name="zytype" value="0" checked="checked" />期刊
				<input type="radio" name="zytype" value="1" />听书
				<input type="radio" name="zytype" value="2" />文津诵读
				<input type="radio" name="zytype" value="3" />特色专题
				<input type="radio" name="zytype" value="4" />总访问量
			</td>
			
			<td>
			</td>
		</tr>
		
		<tr>
			<td align="left" style="font-weight:bold;">选择资源属性：</td>
			
			<td>
				<input type="radio" id="de1" name="zyattr" value="0" /><span>浏览量</span>
				<input type="radio" id="de2" name="zyattr" value="1" checked="checked" /><span>下载量</span>
				<input type="radio" id="de3" name="zyattr" value="2" /><span>收藏量</span>
			</td>
			
			<td>
			</td>
		</tr>
	</table>
	<br/>
	<div id="canvasDiv"></div>
</div>

	<br/>
	<div>
	<p id="wz">期刊访问数据</p>
	&nbsp;<input type="button" value="导出到Excel" onclick="exportInfo()">
		<div>
			<iframe id="winfra" src="about:blank" width="50%" frameborder=0 marginheight=0></iframe>
		</div>
	
	</div>
	
	<form id="tempForm1">
		<input type="hidden" name="startDate" id="sdt">
		<input type="hidden" name="endDate" id="edt">
	</form>
<script>
var flow = [];
var tit = "期刊下载量";
var tableurl = "";
var excelurl = "${ctx}/session/statistic/zyhxqkExport";

$(function(){
	$("#winfra").load(function () {
	    var mainheight = $(this).contents().find("body").height() + 30;
	    $(this).height(mainheight);
	});
	
	//默认期刊，没有浏览量统计
	$("#de1").attr("disabled","disabled");
	$("#de1").next().css('color','gray');
	
	$("input:radio[name=zytype]").click(function(){
		canceldis();
		
		var typeval = $(this).val();
		if(typeval == 0) {
			$("#de1").attr("disabled","disabled");	//期刊没有浏览量统计
			$("#de1").next().css('color','gray');
			$("#de2").attr("checked","checked");
		}else if(typeval == 1) {
			$("#de1").attr("disabled","disabled");	//听书没有浏览量统计
			$("#de1").next().css('color','gray');
			$("#de2").attr("checked","checked");
		}else if(typeval == 2) {
			$("#de2").attr("disabled","disabled");	//文津诵读没有下载量、收藏量统计
			$("#de2").next().css('color','gray');
			$("#de3").attr("disabled","disabled");
			$("#de3").next().css('color','gray');
			$("#de1").attr("checked","checked");
		}else if(typeval == 3) {
			$("#de2").attr("disabled","disabled");	//特色专题没有下载量统计
			$("#de2").next().css('color','gray');
			$("#de1").attr("checked","checked");
		}else {
			$("#de1").attr("checked","checked");
		}
		
	});
	
	var db = $('#startDate');
    db.datebox({
        onShowPanel: function () {//显示日趋选择对象后再触发弹出月份层的事件，初始化时没有生成月份层
            span.trigger('click'); //触发click事件弹出月份层
            if (!tds) setTimeout(function () {//延时触发获取月份对象，因为上面的事件触发和对象生成有时间间隔
                tds = p.find('div.calendar-menu-month-inner td');
                tds.click(function (e) {
                    e.stopPropagation(); //禁止冒泡执行easyui给月份绑定的事件
                    var year = /\d{4}/.exec(span.html())[0]//得到年份
                    , month = parseInt($(this).attr('abbr'), 10); //月份，这里不需要+1
                    db.datebox('hidePanel')//隐藏日期对象
                    .datebox('setValue', year + '-' + month); //设置日期的值
                });
            }, 0);
            yearIpt.unbind();//解绑年份输入框中任何事件
        },
        parser: function (s) {
            if (!s) return new Date();
            var arr = s.split('-');
            return new Date(parseInt(arr[0], 10), parseInt(arr[1], 10) - 1, 1);
        },
        formatter: function (d) { return d.getFullYear() + '-' + (d.getMonth() + 1);/*getMonth返回的是0开始的，忘记了。。已修正*/ }
    });
    var p = db.datebox('panel'), //日期选择对象
        tds = false, //日期选择对象中月份
        aToday = p.find('a.datebox-current'),
        yearIpt = p.find('input.calendar-menu-year'),//年份输入框
        //显示月份层的触发控件
        span = aToday.length ? p.find('div.calendar-title span') ://1.3.x版本
        p.find('span.calendar-text'); //1.4.x版本
    if (aToday.length) {//1.3.x版本，取消Today按钮的click事件，重新绑定新事件设置日期框为今天，防止弹出日期选择面板
       
        aToday.unbind('click').click(function () {
            var now=new Date();
            db.datebox('hidePanel').datebox('setValue', now.getFullYear() + '-' + (now.getMonth() + 1));
        });
    }
        
    var eb = $('#endDate');
    eb.datebox({
        onShowPanel: function () {//显示日趋选择对象后再触发弹出月份层的事件，初始化时没有生成月份层
            spanx.trigger('click'); //触发click事件弹出月份层
            if (!tdsx) setTimeout(function () {//延时触发获取月份对象，因为上面的事件触发和对象生成有时间间隔
            	tdsx = px.find('div.calendar-menu-month-inner td');
                tdsx.click(function (e) {
                    e.stopPropagation(); //禁止冒泡执行easyui给月份绑定的事件
                    var year = /\d{4}/.exec(spanx.html())[0]//得到年份
                    , month = parseInt($(this).attr('abbr'), 10); //月份，这里不需要+1
                    eb.datebox('hidePanel')//隐藏日期对象
                    .datebox('setValue', year + '-' + month); //设置日期的值
                });
            }, 0);
            yearIptx.unbind();//解绑年份输入框中任何事件
        },
        parser: function (s) {
            if (!s) return new Date();
            var arr = s.split('-');
            return new Date(parseInt(arr[0], 10), parseInt(arr[1], 10) - 1, 1);
        },
        formatter: function (d) { return d.getFullYear() + '-' + (d.getMonth() + 1);/*getMonth返回的是0开始的，忘记了。。已修正*/ }
    });
    var px = eb.datebox('panel'), //日期选择对象
        tdsx = false, //日期选择对象中月份
        aTodayx = px.find('a.datebox-current'),
        yearIptx = px.find('input.calendar-menu-year'),//年份输入框
        //显示月份层的触发控件
        spanx = aTodayx.length ? px.find('div.calendar-title span') ://1.3.x版本
        px.find('span.calendar-text'); //1.4.x版本
    if (aTodayx.length) {//1.3.x版本，取消Today按钮的click事件，重新绑定新事件设置日期框为今天，防止弹出日期选择面板
       
        aTodayx.unbind('click').click(function () {
            var now=new Date();
            eb.datebox('hidePanel').datebox('setValue', now.getFullYear() + '-' + (now.getMonth() + 1));
        });
    }
        
    $('#startDate').datebox('setValue', "${formermonth}");
	$('#endDate').datebox('setValue', "${yesterday}");
	
	//时间框禁止输入
	$(".datebox :text").attr("readonly","readonly");	
	
	
	progressLoad();
	
	$.ajax({
		url:"${ctx}/session/statistic/zyhxqkxz",
		type:'POST',
		async:false,
		data: {"startDate":"${formermonth}", "endDate":"${yesterday}"},
		success:function(data){
			progressClose();
			
			if(!data.result) {
		 		$('#startDate').datebox('setValue');
				alert('起始日期不能比结束日期晚!');
			}else {
				progressClose();
				flow = data.list;
				$("#winfra").attr("src", "${ctx}/session/statistic/zyhxqktable?startDate=${formermonth}&endDate=${yesterday}&r="+Math.random());
				executedraw();
			}
		}
	});

});

function canceldis() {
	$("input:radio[name=zyattr]").each(function(){
		$(this).removeAttr("disabled");
		$(this).next().removeAttr("style");
		$(this).removeAttr('checked');
	});
}



//点击按键开始统计
function begindraw() {
	if(!$('#startDate').datebox('getValue') || !$('#endDate').datebox('getValue')) {
		alert("时间不能为空!");
		return;
	}
	
	var s = $('#startDate').datebox('getValue')+'-01';
	var e = $('#endDate').datebox('getValue')+'-01';
	
	var zytype = $('input:radio[name=zytype]:checked').val();
	var zyattr = $('input:radio[name=zyattr]:checked').val();

	var urlcy = "";
	if(zytype == 0) {	//期刊
		excelurl = "${ctx}/session/statistic/zyhxqkExport";
		$("#wz").html("期刊访问数据");
		tableurl = "${ctx}/session/statistic/zyhxqktable";
		if(zyattr == 1) {	//期刊下载量
			urlcy = "${ctx}/session/statistic/zyhxqkxz";
			tit = "期刊下载量";
		}else if(zyattr == 2) {	//期刊收藏量
			urlcy = "${ctx}/session/statistic/zyhxqksc";
			tit = "期刊收藏量";
		}
	}else if(zytype == 1) {	//听书
		excelurl = "${ctx}/session/statistic/zyhxtsExport";
		$("#wz").html("听书访问数据");
		tableurl = "${ctx}/session/statistic/zyhxtstable";
		if(zyattr == 1) {	//听书下载量
			urlcy = "${ctx}/session/statistic/zyhxtsxz";
			tit = "听书下载量";
		}else if(zyattr == 2) {	//听书收藏量
			urlcy = "${ctx}/session/statistic/zyhxtssc";
			tit = "听书收藏量";
		}
	}else if(zytype == 2) {	//文津诵读
		excelurl = "${ctx}/session/statistic/zyhxwjExport";
		$("#wz").html("文津诵读访问数据");
		tableurl = "${ctx}/session/statistic/zyhxwjtable";
		if(zyattr == 0) {	//文津诵读浏览量
			urlcy = "${ctx}/session/statistic/zyhxwjll";
			tit = "文津诵读浏览量";
		}
	}else if(zytype == 3) {	//特色专题
		excelurl = "${ctx}/session/statistic/zyhxztExport";
		$("#wz").html("特色专题访问数据");
		tableurl = "${ctx}/session/statistic/zyhxzttable";
		if(zyattr == 0) {	//特色专题浏览量
			urlcy = "${ctx}/session/statistic/zyhxztll";
			tit = "特色专题浏览量";
		}else if(zyattr == 2) {	//特色专题收藏量
			urlcy = "${ctx}/session/statistic/zyhxztsc";
			tit = "特色专题收藏量";
		}
	}else if(zytype == 4) {	//总访问量
		excelurl = "${ctx}/session/statistic/zyhxzflExport";
		$("#wz").html("总访问量数据");
		tableurl = "${ctx}/session/statistic/zyhxzfltable";
		if(zyattr == 0) {	//总访问量浏览量
			urlcy = "${ctx}/session/statistic/zyhxzll";
			tit = "总访问量浏览量";
		}else if(zyattr == 1) {	//总访问量下载量
			urlcy = "${ctx}/session/statistic/zyhxzxz";
			tit = "总访问量下载量";
		}else if(zyattr == 2) {	//总访问量收藏量
			urlcy = "${ctx}/session/statistic/zyhxzsc";
			tit = "总访问量收藏量";
		}
	}
	
	progressLoad();
	
	$.ajax({
		url:urlcy,
		type:'POST',
		async:false,
		data: {"startDate":s, "endDate":e},
		success:function(data){
			progressClose();
			
			if(!data.result) {
		 		$('#startDate').datebox('setValue');
				alert('起始日期不能比结束日期晚!');
			}else {
				progressClose();
				flow = data.list;
				$("#winfra").attr("src", tableurl+"?startDate="+s+"&endDate="+e+"&r="+Math.random());
				executedraw();
			}
		}
	});
	
}

function executedraw() {
    var data = flow;
	
	new iChart.Column2D({
		render : 'canvasDiv',
		data: data,
		title : tit,
		width : 800,
		height : 320,
		label : {
			fontsize:11,
			textAlign:'right',
			textBaseline:'middle',
			rotate:-45,
			color : '#666666'
		},
		coordinate:{
			offsety:-25,
			background_color:'#fefefe',
			scale:[{
				 position:'left',	
				 start_scale:0,
				 end_scale:500,
				 scale_space:100,
				 listeners:{
					parseText:function(t,x,y){
						return {text:t}
					}
				}
			}
			]
		}
	}).draw();
}

function progressLoad(){  
	$("#winfra").attr("src", "about:blank");
    $("<div class=\"datagrid-mask\" style=\"position:absolute;z-index: 9999;\"></div>").css({display:"block",width:"100%",height:$(window).height()}).appendTo("body");  
    $("<div class=\"datagrid-mask-msg\" style=\"position:absolute;z-index: 9999;\"></div>").html("正在处理，请稍候。。。").appendTo("body").css({display:"block",left:($(document.body).outerWidth(true) - 190) / 2,top:($(window).height() - 45) / 2});  
}

function progressClose(){
	$(".datagrid-mask").remove();  
	$(".datagrid-mask-msg").remove();
}

function exportInfo() {
	if(!$('#startDate').datebox('getValue') || !$('#endDate').datebox('getValue')) {
		alert("时间不能为空!");
		return;
	}
	
	var s = $('#startDate').datebox('getValue')+'-01';
	var e = $('#endDate').datebox('getValue')+'-01';
	
	$("#sdt").val(s);
	$("#edt").val(e);
	document.getElementById("tempForm1").action = excelurl;
	document.getElementById("tempForm1").submit();
}

</script>
</body>
</html>