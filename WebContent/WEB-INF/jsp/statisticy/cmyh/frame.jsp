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

<font style="font-family:黑体">沉默用户</font>
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
			<!-- <td>
				&nbsp;<a href="javascript:void(0)" class="easyui-linkbutton" disabled>&nbsp;年&nbsp;</a>
			</td>
			<td>
				&nbsp;<a href="javascript:void(0)" class="easyui-linkbutton" onclick="stamon()">&nbsp;月&nbsp;</a>
			</td> -->
			<td>
				&nbsp;<a href="javascript:void(0)" class="easyui-linkbutton" onclick="staweek()">&nbsp;周&nbsp;</a>
			</td>
			<!-- <td>
				&nbsp;<a href="javascript:void(0)" class="easyui-linkbutton" onclick="staday()">&nbsp;日&nbsp;</a>
			</td> -->
		</tr>
</table>
	<br/>
	<font id="tit1" style="font-family:黑体">沉默用户：按周统计，指用户仅在安装日（及安装次日）启动，且在后续时间内无启动行为</font>
	<div>
		<iframe id="winfra" src="about:blank" width="80%" height="800" frameborder="0" marginheight="0"></iframe>
	</div>
</div>

<script>

$(function(){
	$("#winfra").attr("src", "${ctx}/session/statisticy/cmyhGrid?startDate=${lastmonth}&endDate=${yesterday}&r="+Math.random());
});

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

	
	$("#winfra").attr("src", "about:blank");
	$("#winfra").attr("src", "${ctx}/session/statisticy/cmyhGrid?startDate="+ws+"&endDate="+we+"&r="+Math.random());
}
		
</script>
</body>
</html>