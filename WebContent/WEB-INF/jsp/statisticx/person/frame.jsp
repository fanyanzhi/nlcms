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

<font style="font-family:黑体">个人</font>
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
	<font id="tit1" style="font-family:黑体">按天统计</font><br/>
	<font id="tit1" style="font-family:黑体">访问总量:</font>
	<div>
		<iframe id="winfra" src="about:blank" width="80%" height="400" frameborder="0" marginheight="0"></iframe>
	</div>
	<font id="tit1" style="font-family:黑体">访问详情:</font>
	<br/>
	<input type="button" value="导出到Excel" onclick="exportInfo()">
	<br/>
	<div>
		<iframe id="winfra2" src="about:blank" width="50%" frameborder="0" marginheight="0"></iframe>
	</div>
</div>

	<form id="tempForm">
		<input type="hidden" name="startDate" id="sdt">
		<input type="hidden" name="endDate" id="edt">
		<input type="hidden" name="status" id="sts">
	</form>
<script>
var tit1 = "按天统计";
var flag = "day";

$(function(){
	$("#winfra2").load(function () {
	    var mainheight = $(this).contents().find("body").height() + 30;
	    $(this).height(mainheight);
	});
	
	$("#winfra").attr("src", "${ctx}/session/statisticx/personYmfwGrid?status=day&startDate=${lastmonth}&endDate=${yesterday}&r="+Math.random());
	$("#winfra2").attr("src", "${ctx}/session/statisticx/personDetailYmfwTable?status=day&startDate=${lastmonth}&endDate=${yesterday}&r="+Math.random());
});

//导出到Excel
function exportInfo() {
	var ws = $("#wstartDate").val();
	var we = $("#wendDate").val();
	
	$("#sdt").val(ws);
	$("#edt").val(we);
	$("#sts").val(flag);
	
	document.getElementById("tempForm").action = "${ctx}/session/statisticx/personDetailYmfwExport";
	document.getElementById("tempForm").submit();
}

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
	
	flag = "day";
	tit1 = "按天统计";
	$("#tit1").html("按天统计");
	
	$("#winfra").attr("src", "about:blank");
	$("#winfra").attr("src", "${ctx}/session/statisticx/personYmfwGrid?status=day&startDate="+ws+"&endDate="+we+"&r="+Math.random());
	$("#winfra2").attr("src", "about:blank");
	$("#winfra2").attr("src", "${ctx}/session/statisticx/personDetailYmfwTable?status=day&startDate="+ws+"&endDate="+we+"&r="+Math.random()); 
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
	
	flag = "month";
	tit1 = "按月统计";
	$("#tit1").html("按月统计");

	$("#winfra").attr("src", "about:blank");
	$("#winfra").attr("src", "${ctx}/session/statisticx/personYmfwGrid?status=month&startDate="+s+"&endDate="+e+"&r="+Math.random());
	$("#winfra2").attr("src", "about:blank");
	$("#winfra2").attr("src", "${ctx}/session/statisticx/personDetailYmfwTable?status=month&startDate="+ws+"&endDate="+we+"&r="+Math.random());
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
	
	flag = "year";
	tit1 = "按年统计";
	$("#tit1").html("按年统计");
	
	$("#winfra").attr("src", "about:blank");
	$("#winfra").attr("src", "${ctx}/session/statisticx/personYmfwGrid?status=year&startDate="+s+"&endDate="+e+"&r="+Math.random());
	$("#winfra2").attr("src", "about:blank");
	$("#winfra2").attr("src", "${ctx}/session/statisticx/personDetailYmfwTable?status=year&startDate="+ws+"&endDate="+we+"&r="+Math.random());
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
	
	flag = "week";
	tit1 = "按周统计";
	$("#tit1").html("按周统计");
	
	$("#winfra").attr("src", "about:blank");
	$("#winfra").attr("src", "${ctx}/session/statisticx/personYmfwGrid?status=week&startDate="+ws+"&endDate="+we+"&r="+Math.random());
	$("#winfra2").attr("src", "about:blank");
	$("#winfra2").attr("src", "${ctx}/session/statisticx/personDetailYmfwTable?status=week&startDate="+ws+"&endDate="+we+"&r="+Math.random());
}
		
</script>
</body>
</html>