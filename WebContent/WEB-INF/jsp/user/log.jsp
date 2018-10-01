<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../TagLib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="../Pub.jsp" %>
<script type="text/javascript" src="${ctx}/ichartjs/ichart.1.2.min.js"></script>
</head> 
<body> 
<div>
基本信息:
	<table border="1" width="40%" bordercolor="#a0c6e5" style="border:1;border-collapse:collapse;">
	<tr>
		<td width="150" style="text-align:center">读者类别</td>
		<td width="150" style="text-align:center">
			<c:if test="${nlcuser.rdrolecode == '0000'}">虚拟</c:if>
			<c:if test="${nlcuser.rdrolecode == 'JS0001'}">实名</c:if>
			<c:if test="${nlcuser.rdrolecode == 'JS0002'}">物理卡</c:if>
		</td>
	</tr>
	
	<tr>
		<td width="150" style="text-align:center">读者姓名</td>
		<td width="150" style="text-align:center">
			${nlcuser.name}
		</td>
	</tr>
	
	<tr>
		<td width="150" style="text-align:center">读者证件类别</td>
		<td width="150" style="text-align:center">
			${nlcuser.cardtype}
		</td>
	</tr>
	
	<tr>
		<td width="150" style="text-align:center">读者证件号码</td>
		<td width="150" style="text-align:center">
			${nlcuser.cardno}
		</td>
	</tr>
	
	<tr>
		<td width="150" style="text-align:center">性别</td>
		<td width="150" style="text-align:center">
			${nlcuser.sextype}
		</td>
	</tr>
	
	<tr>
		<td width="150" style="text-align:center">出生日期</td>
		<td width="150" style="text-align:center">
			<fmt:formatDate value='${nlcuser.birthday}' pattern='yyyy-MM-dd'/>
		</td>
	</tr>
	
	<tr>
		<td width="150" style="text-align:center">学历</td>
		<td width="150" style="text-align:center">
			${nlcuser.education}
		</td>
	</tr>
	
	<tr>
		<td width="150" style="text-align:center">职称</td>
		<td width="150" style="text-align:center">
			${nlcuser.technical}
		</td>
	</tr>
	
	<tr>
		<td width="150" style="text-align:center">所属国家名称</td>
		<td width="150" style="text-align:center">
			${nlcuser.country}
		</td>
	</tr>
	</table>
	<br/>
	<br/>
登录日志：
	<table border="1" width="50%" bordercolor="#a0c6e5" style="border:1;border-collapse:collapse;">
	<tr>
		<td width="150" style="text-align:center">登录IP</td>
		<td width="150" style="text-align:center">登录时间</td>
	</tr>
	
	<c:forEach items="${reslist }" var="ele" varStatus="vs">
	<tr>
		<td width="150" style="text-align:center">${ele.address }</td>
		<td width="150" style="text-align:center">
			<fmt:formatDate value='${ele.time}' pattern='yyyy-MM-dd HH:mm:ss'/>
		</td>
	</tr>		
	</c:forEach>
	</table>
	
</div> 
</body> 
</html>