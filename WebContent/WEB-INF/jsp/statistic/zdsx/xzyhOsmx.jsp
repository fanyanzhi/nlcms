<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../TagLib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<style type="text/css">
td{height:25px;}
table{text-align:center;color:#222; font:12px/20px Arial,"宋体";  background-color:#fff;}
</style>
</head>
<body>
		<table border="1" width="80%" bordercolor="#a0c6e5" style="border:1;border-collapse:collapse;">
			<tr>
				<td width="20%" style="text-align:center">系统</td>
				<td width="20%" style="text-align:center">装机量</td>
				<td width="20%" style="text-align:center">用户占比</td>
				<td width="20%" style="text-align:center">启动次数</td>
				<td width="20%" style="text-align:center">启动占比</td>
			</tr>
			<c:forEach items="${resList }" var="ele" varStatus="vs">
			<tr>
				<td style="text-align:center">${ele.baseos }</td>
				<td style="text-align:center">${ele.newUserNum }</td>
				<td style="text-align:center">${ele.newUserRatio }</td>
				<td style="text-align:center">${ele.newStartNum }</td>
				<td style="text-align:center">${ele.newStartRatio }</td>
			</tr>
			</c:forEach>
		</table>
</body>
</html>