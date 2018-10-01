<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../TagLib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="../../Pub.jsp" %>
<script type="text/javascript" src="${ctx}/ichartjs/ichart.1.2.min.js"></script>
<script type="text/javascript" src="${ctx}/js/myweek/WdatePicker.js"></script>
</head>
<body>
<div>

<font style="font-family:黑体">新增用户明细</font>
<br/>
<table border="0">
		<tr>
			<td>&nbsp;</td>
			<td>
				<input id="d121" type="text" class="Wdate" value="${lastmonthweeks}" onfocus="WdatePicker({
					vel:'wstartDate'})"/>
				--
				<input id="d122" type="text" class="Wdate" value="${yesweeks}" onfocus="WdatePicker({
					vel:'wendDate'})"/>
				
				<input type="hidden" name="wstartDate" id="wstartDate" value="${lastmonth}">
				<input type="hidden" name="wendDate" id="wendDate" value="${yesterday}">
			</td>
			<td>
				&nbsp;<a href="javascript:void(0)" class="easyui-linkbutton" onclick="stayear()">&nbsp;年&nbsp;</a>
			</td>
			<td>
				&nbsp;<a href="javascript:void(0)" class="easyui-linkbutton" onclick="stamon()">&nbsp;月&nbsp;</a>
			</td>
			<td>
				&nbsp;<a href="javascript:void(0)" class="easyui-linkbutton" onclick="staweek()">&nbsp;周&nbsp;</a>
			</td>
			<td>
				&nbsp;<a href="javascript:void(0)" class="easyui-linkbutton" onclick="staday()">&nbsp;日&nbsp;</a>
			</td>
		</tr>
</table>
	<br/>
	<font id="tit1" style="font-family:黑体">按天统计</font>
	<div>
		<iframe id="winfra" src="about:blank" width="80%" height="400" frameborder="0" marginheight="0"></iframe>
	</div>
	<div id="canvasDiv"></div>
</div>
	<br/>
	<div>
		<div>
			<iframe id="winfra2" src="${ctx}/session/statistic/ckyhs3?startDateweeks=${lastmonthweeks}&endDateweeks=${yesweeks}&startDate=${lastmonth}&endDate=${yesterday}" width="100%" height="550" frameborder=0 marginheight=0></iframe>
		</div>
	</div>

<script>
var flow1 = [];
var flow2 = [];
var flow3 = [];
var flow4 = [];
var labels = [];
var tit1 = "按天统计";

$(function(){
	progressLoad();
	
	$.ajax({
		url:"${ctx}/session/statistic/xzyhqs",
		type:'POST',
		async:false,
		data: {"startDate":"${lastmonth}", "endDate":"${yesterday}"},
		success:function(data){
			progressClose();
			flow1 = data.flow1;
			flow2 = data.flow2;
			flow3 = data.flow3;
			flow4 = data.flow4;
			labels = data.labels;
			executedraw();
			$("#winfra").attr("src", "${ctx}/session/statistic/xzyhGrid?status=day&startDate=${lastmonth}&endDate=${yesterday}&r="+Math.random());
		}
	});

});

//=========================================================================
//按天统计
function staday() {
	var ws = $("#wstartDate").val();
	var we = $("#wendDate").val();
	
	if(!ws || !we) {
		alert("时间不能为空!");
		return;
	}
	
	var cont = "1";
	$.ajax({
		url:"${ctx}/session/statistic/checkdate",
		type:'POST',
		async:false,
		data: {"startDate":ws, "endDate":we},
		success:function(data){
			if(!data.result) {
				cont = "0";
			}
		}
	});
	
	if(cont == "0") {
		alert('起始日期不能比结束日期晚!');
		return ;
	}
	
	tit1 = "按天统计";
	$("#tit1").html("按天统计");
	
	progressLoad();
	
	$.ajax({
		url:"${ctx}/session/statistic/xzyhqs",
		type:'POST',
		async:false,
		data: {"startDate":ws, "endDate":we},
		success:function(data){
			progressClose();
			flow1 = data.flow1;
			flow2 = data.flow2;
			flow3 = data.flow3;
			flow4 = data.flow4;
			labels = data.labels;
			executedraw();
			$("#winfra").attr("src", "${ctx}/session/statistic/xzyhGrid?status=day&startDate="+ws+"&endDate="+we+"&r="+Math.random());
		}
	});
	
}

function executedraw() {
	var data = [
	         	{
	         		name : '虚拟用户',
	         		value:flow1,
	         		color:'#36A1D7',
	         		line_width:1
	         	},
	         	{
	         		name : '实名用户',
	         		value:flow2,
	         		color:'#759E21',
	         		line_width:1
	         	},
	         	{
	         		name : '持卡用户',
	         		value:flow3,
	         		color:'#B7281F',
	         		line_width:1
	         	},
	         	{
	         		name : '新增总数',
	         		value:flow4,
	         		color:'#ec4646',
	         		line_width:1
	         	}
	       ];
	
	var chart = new iChart.LineBasic2D({
		render : 'canvasDiv',
		data: data,
		align:'center',
		title : tit1,
		width : 1100,
		height : 400,
		padding : 40,
		tip:{	//提示框
			enable:true,
			shadow:true,
			listeners:{
				//tip:提示框对象、name:数据名称、value:数据值、text:当前文本、i:数据点的索引
				parseText:function(tip,name,value,text,i){
					return "<span style='color:#005268;font-size:12px;'>"+labels[i]+name+":"+
					"</span><span style='color:#005268;font-size:12px;'>"+value+"</span>";
				}
			}
		},
		crosshair:{	//十字线
			enable:true,
			line_color:'#ec4646'
		},
		legend : {
			enable : true,
			row:1,//设置在一行上显示，与column配合使用
			column : 'max',
			valign:'top',
			sign:'bar',
			background_color:null,//设置透明背景
			offsetx:-40,//设置x轴偏移，满足位置需要
			offsety:-27,
			border : true
		},
		sub_option : {	//折线的配置
			smooth : true,
			label:false,
			hollow:true,
			hollow_inside:true,
			point_size:6
		}, 
		coordinate:{
			//height:260,
			offsety:-20,
			//gridlinesVisible:false,
			striped_factor : 0.18,
			axis:{
				color:'#252525',
				width:[0,0,4,0]
			},
			scale:[{
				 position:'left',	
				 start_scale:0,
				// end_scale:5000,
				// scale_space:1000,
				 scale_size:1,
				 scale_enable : false,
				 label : {color:'#9d987a',font : '微软雅黑',fontsize:11,fontweight:600}
			},{
				 position:'bottom',	
				 label : {color:'#9d987a',font : '微软雅黑',fontsize:11,fontweight:600, rotate:290, offsety:26, offsetx:-10},
				 scale_enable : false,
				 labels:labels
			}]
		}
	});
 
chart.draw();	
}

function progressLoad(){  
	$("#winfra").attr("src", "about:blank");
    $("<div class=\"datagrid-mask\" style=\"position:absolute;z-index: 9999;\"></div>").css({display:"block",width:"100%",height:"1500"}).appendTo("body");  
    $("<div class=\"datagrid-mask-msg\" style=\"position:absolute;z-index: 9999;\"></div>").html("正在处理，请稍候。。。").appendTo("body").css({display:"block",left:($(document.body).outerWidth(true) - 190) / 2,top:($(window).height() - 45) / 2});  
}

function progressClose(){
	$(".datagrid-mask").remove();  
	$(".datagrid-mask-msg").remove();
}

//====================月=================================
function stamon() {
	var ws = $("#wstartDate").val();
	var we = $("#wendDate").val();
	
	if(!ws || !we) {
		alert("时间不能为空!");
		return;
	}
	var monws = ws.substr(0, 7);
	var monwe = we.substr(0, 7);
	
	var s = monws+'-01';
	var e = monwe+'-01';
	
	var cont = "1";
	$.ajax({
		url:"${ctx}/session/statistic/checkdate",
		type:'POST',
		async:false,
		data: {"startDate":ws, "endDate":we},
		success:function(data){
			if(!data.result) {
				cont = "0";
			}
		}
	});
	
	if(cont == "0") {
		alert('起始日期不能比结束日期晚!');
		return ;
	}
	
	tit1 = "按月统计";
	$("#tit1").html("按月统计");
	
	progressLoad();
	
	$.ajax({
		url:"${ctx}/session/statistic/monxzyhqs",
		type:'POST',
		async:false,
		data: {"startDate":s, "endDate":e},
		success:function(data){
			progressClose();
			flow1 = data.flow1;
			flow2 = data.flow2;
			flow3 = data.flow3;
			flow4 = data.flow4;
			labels = data.labels;
			executedraw();
			$("#winfra").attr("src", "${ctx}/session/statistic/xzyhGrid?status=month&startDate="+s+"&endDate="+e+"&r="+Math.random());
		}
	});
	
}
	
//==============年=======================
function stayear() {
	var ws = $("#wstartDate").val();
	var we = $("#wendDate").val();
	
	if(!ws || !we) {
		alert("时间不能为空!");
		return;
	}
	var yearws = ws.substr(0, 4);
	var yearwe = we.substr(0, 4);
	
	var cont = "1";
	$.ajax({
		url:"${ctx}/session/statistic/checkdate",
		type:'POST',
		async:false,
		data: {"startDate":ws, "endDate":we},
		success:function(data){
			if(!data.result) {
				cont = "0";
			}
		}
	});
	
	if(cont == "0") {
		alert('起始日期不能比结束日期晚!');
		return ;
	}
	
	var s = yearws+'-01-01';
	var e = yearwe+'-01-01';
	
	tit1 = "按年统计";
	$("#tit1").html("按年统计");
	
	progressLoad();
	
	$.ajax({
		url:"${ctx}/session/statistic/nxzyhqs",
		type:'POST',
		async:false,
		data: {"startDate":s, "endDate":e},
		success:function(data){
			progressClose();
			flow1 = data.flow1;
			flow2 = data.flow2;
			flow3 = data.flow3;
			flow4 = data.flow4;
			labels = data.labels;
			executedraw();
			$("#winfra").attr("src", "${ctx}/session/statistic/xzyhGrid?status=year&startDate="+s+"&endDate="+e+"&r="+Math.random());
		}
	});
}

//=================周====================
function staweek() {
	var ws = $("#wstartDate").val();
	var we = $("#wendDate").val();
	
	if(!ws || !we) {
		alert("时间不能为空!");
		return;
	}
	
	var cont = "1";
	$.ajax({
		url:"${ctx}/session/statistic/checkdate",
		type:'POST',
		async:false,
		data: {"startDate":ws, "endDate":we},
		success:function(data){
			if(!data.result) {
				cont = "0";
			}
		}
	});
	
	if(cont == "0") {
		alert('起始日期不能比结束日期晚!');
		return ;
	}
	
	progressLoad();
	
	tit1 = "按周统计";
	$("#tit1").html("按周统计");
	
	$.ajax({
		url:"${ctx}/session/statistic/zxzyhqs",
		type:'POST',
		async:false,
		data: {"startDate":ws, "endDate":we},
		success:function(data){
			progressClose();
			flow1 = data.flow1;
			flow2 = data.flow2;
			flow3 = data.flow3;
			flow4 = data.flow4;
			labels = data.labels;
			executedraw();
			$("#winfra").attr("src", "${ctx}/session/statistic/xzyhGrid?status=week&startDate="+ws+"&endDate="+we+"&r="+Math.random());
		}
	});
}
		
</script>
</body>
</html>