<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../TagLib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="../../Pub.jsp" %>
<script type="text/javascript" src="${ctx}/ichartjs/ichart.1.2.min.js"></script>
<style type="text/css">
.span{font-size:12px;color:#ccc}
td{height:25px;}
table{text-align:center; color:#222; font:12px/20px Arial,"宋体";  background-color:#fff;}
</style>
<script>
//日期为空 
$(function(){
	var data = [
	        	{name : '实名用户',value : "${resmap.realnum}",color:'#A020F0'},
	        	{name : '持卡用户',value : "${resmap.cardnum}",color:'#BCEE68'},
	        	{name : '虚拟卡用户',value : "${resmap.virnum}",color:'#CD661D'},
        	];
	
	new iChart.Pie2D({
		render : 'canvasDiv',
		data: data,
		//title : 'Top 5 Browsers from 1 to 29 Feb 2012',
		legend : {
			enable : true,
			valign : 'top'
		},
		showpercent:true,
		decimalsnum:2,
		width : 700,
		height : 300,
		radius:140
	}).draw();


});
//导出到Excel
function exportInfo() {
	document.getElementById("tempForm").action = "${ctx}/session/statistic/yhsjExport";
	document.getElementById("tempForm").submit();
}
</script>

</head>
<body>
	<table  >
	<tr>
		<td><div id="canvasDiv"></div></td>
		<td style="width: 240px;vertical-align: top;">
		<div >
			<font style="font-family:黑体;font-size:16px;">各类型用户数据：</font>
			<br/>
			<table border="1" width="100%" bordercolor="#a0c6e5" style="border:1;border-collapse:collapse;">
			
				<tr>
					<td width="60%" >用户类型</td>
					<td>数量</td>
				</tr>
				<tr>
					<td width="60%" >持卡用户</td>
					<td>${numList[1]}</td>
				</tr>
				<tr>
					<td width="60%" >实名用户</td>
					<td>${numList[0]}</td>
				</tr>
				
				<tr>
					<td width="60%" >虚拟用户</td>
					<td>${numList[2]}</td>
				</tr>
				<tr>
					<td width="60%" >总计</td>
					<td>${numList[3]}</td>
				</tr>
			</table>
			<br/>
			<input type="button" value="导出到Excel" onclick="exportInfo()" >
			<form id="tempForm">
				<input type="hidden" name="startDate" id="sdt" value="${startDate }">
				<input type="hidden" name="endDate" id="edt" value="${endDate }">
			</form>
		</div>
		</td>
	</tr>
	</table>

</body>
</html>