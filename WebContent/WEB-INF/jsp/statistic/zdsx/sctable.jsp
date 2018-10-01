<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../TagLib.jsp"%>
<!DOCTYPE html>
<html>
<head>
</head>
<body>
		<table border="1" cellpadding="0" cellspacing="0" width="80%">
			<tr>
				<th>月份</th>
				<th>IOS</th>
				<th>Android</th>
			</tr>
			<c:forEach items="${resultList }" var="ele" varStatus="vs">
			<tr>
				<td align="center">${ele.monthdate }</td>
				<td align="center">${ele.iosnum }</td>
				<td align="center">${ele.androidnum }</td>
			</tr>
			</c:forEach>
		</table>
</body>
</html>