<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../TagLib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<style type="text/css">
.span{font-size:12px;color:#ccc}
td{height:25px;}
body{color:#222; font:12px/20px Arial,"宋体";  background-color:#fff;}
</style>
</head>
<body>
		<table border="1" width="80%" bordercolor="#a0c6e5" style="border:1;border-collapse:collapse;">
			<tr>
				<td>&nbsp;</td>
				<th>列表页访问次数</th>
				<th>详情页访问次数</th>
				<th>列表页停留时间</th>
				<th>详情页停留时间</th>
			</tr>
			<c:forEach items="${reslist }" var="ele" varStatus="vs">
			<tr>
				<td align="center">${ele.module }</td>
				<td align="center">${ele.listnum }</td>
				<td align="center">${ele.detailnum }</td>
				<td align="center">${ele.listRemainTime }</td>
				<td align="center">${ele.detailRemainTime }</td>
			</tr>
			</c:forEach>
		</table>
</body>
</html>