<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../TagLib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="../../Pub.jsp" %>
<script type="text/javascript" src="${ctx}/ichartjs/ichart.1.2.min.js"></script>
<style type="text/css"> 
	#exhibitor114{ margin:0 auto;width:auto;height:100%} 
	#exhibitorleft{ float:left; width:60%; height:100%;} 
	#exhibitorright{ float:left; width:40%;height:100%;} 
	td{height:25px;}
	table{text-align:center;color:#222; font:12px/20px Arial,"宋体";  background-color:#fff;}
</style> 
</head> 

<body> 
<div id="exhibitor114" style="float:inherit"> 
	<div id="exhibitorleft">
	<div style="width:90%;">
	<span style="float:left">年龄分布数据：</span>
	<span style="float:right;valign:middle;"><input type="button" value="导出到Excel" onclick="exportInfo1()"></span>
	</div>
	<table border="1" width="90%" bordercolor="#a0c6e5" style="border:1;border-collapse:collapse;">
			<tr>
				<td width="150" style="text-align:center">用户类型</td>
				<td width="150" style="text-align:center">18岁以下</td>
				<td width="150" style="text-align:center">19~23岁</td>
				<td width="150" style="text-align:center">24~30岁</td>
				<td width="150" style="text-align:center">31~40岁</td>
				<td width="150" style="text-align:center">40岁以上</td>
				<td width="120" style="text-align:center">总计</td>
			</tr>
			
			<c:forEach items="${ageList }" var="ele" varStatus="vs">
			<tr>
				<td style="text-align:center">${ele.text }</td>
				<td style="text-align:center">${ele.level1 }</td>
				<td style="text-align:center">${ele.level2 }</td>
				<td style="text-align:center">${ele.level3 }</td>
				<td style="text-align:center">${ele.level4 }</td>
				<td style="text-align:center">${ele.level5 }</td>
				<td style="text-align:center">${ele.sum }</td>
			</tr>
			</c:forEach>
	</table>
	
	<form id="tempForm1">
		<input type="hidden" name="startDate" id="sdt" value='${startDate}'>
		<input type="hidden" name="endDate" id="edt" value='${endDate}'>
	</form>
	</div> 
	
	<div id="exhibitorright">
	<div style="width:90%;">
	<span style="float:left">性别分布数据：</span>
	<span style="float:right;valign:middle;"><input type="button" value="导出到Excel" onclick="exportInfo2()"></span>
	</div>
	<table border="1" width="90%" bordercolor="#a0c6e5" style="border:1;border-collapse:collapse;">
			<tr>
				<td width="150" style="text-align:center">用户类型</td>
				<td width="150" style="text-align:center">男</td>
				<td width="150" style="text-align:center">女</td>
				<td width="120" style="text-align:center">总计</td>
			</tr>
			
			<c:forEach items="${sexList }" var="ele" varStatus="vs">
			<tr>
				<td style="text-align:center">${ele.text }</td>
				<td style="text-align:center">${ele.level1 }</td>
				<td style="text-align:center">${ele.level2 }</td>
				<td style="text-align:center">${ele.sum }</td>
			</tr>
			</c:forEach>
	</table>
	
	<form id="tempForm2">
		<input type="hidden" name="startDate" id="sdt" value='${startDate}'>
		<input type="hidden" name="endDate" id="edt" value='${endDate}'>
	</form>
	</div> 
	
</div> 
<script>
//导出到Excel
function exportInfo1() {
	document.getElementById("tempForm1").action = "${ctx}/session/statistic/yhsxnlExport";	//用户属性年龄分布
	document.getElementById("tempForm1").submit();
}

function exportInfo2() {
	document.getElementById("tempForm2").action = "${ctx}/session/statistic/yhsxxbExport";	//用户属性年龄分布
	document.getElementById("tempForm2").submit();
}
</script>
</body> 
</html>