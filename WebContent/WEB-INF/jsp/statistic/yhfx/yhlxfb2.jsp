<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../TagLib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<style type="text/css">
.span{font-size:12px;color:#ccc}
td{height:25px;}
table{text-align:center; color:#222; font:12px/20px Arial,"宋体";  background-color:#fff;}
</style>
</head>
<body>
<font style="font-family:黑体">各类型用户数据：</font>
<br/>
	<table border="1" width="50%" bordercolor="#a0c6e5" style="border:1;border-collapse:collapse;">
			<tr>
				<td width="60%" >用户类型</td>
				<td>数量</td>
			</tr>
			<tr>
				<td width="60%" >实名用户</td>
				<td>${numList[0]}</td>
			</tr>
			<tr>
				<td width="60%" >持卡用户</td>
				<td>${numList[1]}</td>
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
		<input type="button" value="导出到Excel" onclick="exportInfo()">
		<form id="tempForm"></form>
<script>
//导出到Excel
function exportInfo() {
	document.getElementById("tempForm").action = "${ctx}/session/statistic/yhsjExport";
	document.getElementById("tempForm").submit();
}
</script>
</body>
</html>