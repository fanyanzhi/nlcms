<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../TagLib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<style type="text/css">
td{height:28px;}
table{text-align:center;color:#222; font:12px/20px Arial,"宋体";  background-color:#fff;}
</style>
</head>
<body>
		<table border="1" width="80%" bordercolor="#a0c6e5" style="border:1;border-collapse:collapse;">
			<tr>
				<th>日期</th>
				<th>省市</th>
				<th>新增用户</th>
			</tr>
			<c:forEach items="${resultList }" var="ele" varStatus="vs">
			<tr>
				<td align="center">${ele.time }</td>
				<td align="center">${ele.location }</td>
				<td align="center">${ele.num }</td>
			</tr>
			</c:forEach>
		</table>
</body>
</html>