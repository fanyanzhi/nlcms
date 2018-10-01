<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../TagLib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="../../Pub.jsp" %>
<script type="text/javascript" src="${ctx}/js/easyui/jquery.min.js"></script>
</head>
<body>
	<br/>
	<table style="width:98%;">
		<tr>
			<td style="font-weight:bold;" width="33%" colspan="2">资源名称：${title }</td>
			<td style="font-weight:bold;" width="33%">资源ID：${magazineid }</td>
		</tr>
	</table>
	
	<br/>
	<div>
	<p style="font-weight:bold;">阅读用户</p>
		<div>
			<iframe id="winfra" src="${ctx}/session/statistic/gzyhx/ydyh?startDate=${startDate }&endDate=${endDate }&magazineid=${magazineid}&type=${type}" width="100%" height="250" frameborder=0 marginheight=0></iframe>
		</div>
	</div>
	
	<br/>
	
	<span style="font-weight:bold;">读者属性分析</span>
	<span style="float:right;valign:middle;margin-right: 10px"><input type="button" value="导出到Excel" onclick="exportInfo()"></span>
	
	<hr style="width:98%;" align="left"/>
	
	<br/>
	<div>
	<p style="font-weight:bold;">性别分布数据</p>
		<div>
			<iframe id="winfra1" src="${ctx}/session/statistic/gzyhx/leaf1?startDate=${startDate }&endDate=${endDate }&magazineid=${magazineid}&type=${type}" width="100%" height="250" frameborder=0 marginheight=0></iframe>
		</div>
	</div>
	
	<br/>
	<div>
	<p style="font-weight:bold;">年龄分布数据</p>
		<div>
			<iframe id="winfra2" src="${ctx}/session/statistic/gzyhx/leaf2?startDate=${startDate }&endDate=${endDate }&magazineid=${magazineid}&type=${type}" width="100%" height="250" frameborder=0 marginheight=0></iframe>
		</div>
	</div>
	
	<br/>
	<div>
	<p style="font-weight:bold;">学历分布数据</p>
		<div>
			<iframe id="winfra3" src="${ctx }/session/statistic/gzyhx/leaf3?startDate=${startDate }&endDate=${endDate }&magazineid=${magazineid}&type=${type}" width="100%" height="250" frameborder=0 marginheight=0></iframe>
		</div>
	</div>
	
	<br/>

<form id="tempForm3">
		<input type="hidden" name="type" value="${type}">
		<input type="hidden" name="title" value="${title}">
		<input type="hidden" name="startDate" value="${startDate}">
		<input type="hidden" name="endDate" value="${endDate}">
		<input type="hidden" name="magazineid" value="${magazineid}">
</form>
</body>
<script>
//导出到Excel
function exportInfo() {
	document.getElementById("tempForm3").action = "${ctx}/session/statistic/zleafExport";	//本资源数据分布全部导出
	document.getElementById("tempForm3").submit();
}
</script>
</html>