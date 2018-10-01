<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../TagLib.jsp"%>
<!DOCTYPE html>
<html>
<head>
</head>
<body>
		<table border="1" cellpadding="0" cellspacing="0" width="80%">
			<tr>
				<td width="60%" >日期</td>
				<td>启动次数</td>
			</tr>
			<c:forEach items="${startList }" var="ele" varStatus="vs">
			<tr>
				<td width="60%" >${ele.period }</td>
				<td>${ele.num }</td>
			</tr>
			</c:forEach>
		</table>
</body>
</html>