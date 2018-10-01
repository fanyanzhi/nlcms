<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../TagLib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<style type="text/css">
td{height:28px;}
table{text-align:center;color:#222; font:12px/20px Arial,"宋体";  background-color:#fff;}
</style>
</head> 
<body> 
<div>
	<table border="1" width="80%" bordercolor="#a0c6e5" style="border:1;border-collapse:collapse;">
	<tr>
		<th>书名</th>
		<th>金额</th>
		<th>逾期时间</th>
	</tr>
	
	<c:forEach items="${reslist }" var="ele" varStatus="vs">
	<tr>
		<td width="150" style="text-align:center">${ele.book }</td>
		<td width="150" style="text-align:center">${ele.amount }</td>
		<td width="150" style="text-align:center">
			<fmt:formatDate value='${ele.dueDate}' pattern='yyyy-MM-dd'/>
		</td>
	</tr>		
	</c:forEach>
	</table>
</div> 
</body> 
</html>