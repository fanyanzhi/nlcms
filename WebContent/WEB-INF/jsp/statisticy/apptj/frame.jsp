<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../TagLib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="../../Pub.jsp" %>
<script type="text/javascript" src="${ctx}/js/myweek/WdatePicker.js"></script>
</head>
<body>
<div>

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
		<iframe id="winfra" src="about:blank" width="100%" height="700" frameborder="0" marginheight="0"></iframe>
	</div>
</div>

<script>
var tit1 = "按天统计";

$(function(){
	$("#winfra").attr("src", "${ctx}/session/statisticy/apptjGrid?status=day&startDate=${lastmonth}&endDate=${yesterday}&r="+Math.random());
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
	
	$("#winfra").attr("src", "about:blank");
	$("#winfra").attr("src", "${ctx}/session/statisticy/apptjGrid?status=day&startDate="+ws+"&endDate="+we+"&r="+Math.random());
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

	$("#winfra").attr("src", "about:blank");
	$("#winfra").attr("src", "${ctx}/session/statisticy/apptjGrid?status=month&startDate="+s+"&endDate="+e+"&r="+Math.random());
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
	
	$("#winfra").attr("src", "about:blank");
	$("#winfra").attr("src", "${ctx}/session/statisticy/apptjGrid?status=year&startDate="+s+"&endDate="+e+"&r="+Math.random());
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

	tit1 = "按周统计";
	$("#tit1").html("按周统计");
	
	$("#winfra").attr("src", "about:blank");
	$("#winfra").attr("src", "${ctx}/session/statisticy/apptjGrid?status=week&startDate="+ws+"&endDate="+we+"&r="+Math.random());
}
		
</script>
</body>
</html>