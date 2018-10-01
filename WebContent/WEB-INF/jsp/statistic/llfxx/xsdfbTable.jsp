<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../TagLib.jsp"%>
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
				<th>时间</th>
				<th>启动次数</th>
				<th>用户数量</th>
			</tr>
			<c:forEach items="${resultList }" var="ele" varStatus="vs">
			<tr>
				<td align="center">${ele.time }</td>
				<td align="center">${ele.qdcs }</td>
				<td align="center">${ele.yhsl }</td>
			</tr>
			</c:forEach>
		</table>
</body>
</html>