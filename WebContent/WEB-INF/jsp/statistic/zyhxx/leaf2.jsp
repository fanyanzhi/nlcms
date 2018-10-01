<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../TagLib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<script type="text/javascript" src="${ctx}/js/jquery-1.8.3.js"></script>
<script type="text/javascript" src="${ctx}/ichartjs/ichart.1.2.min.js"></script>
<style type="text/css"> 
	#exhibitor114{ margin:0 auto;width:auto;height:100%} 
	#exhibitorleft{ float:left; width:55%; height:100%;} 
	#exhibitorright{ float:left; width:45%;height:100%;} 
</style> 
</head> 


<body> 
<div id="exhibitor114" style="float:inherit"> 
	<div id="exhibitorleft">
		<div id="canvasDiv"></div>
	</div> 
	
	<div id="exhibitorright">
	<span style="float:right;valign:middle;"><input type="button" value="导出到Excel" onclick="exportInfo1()"></span>
	<br/>
	<br/>
	<table border="1" align="right" cellpadding="0" cellspacing="0" width="50%">
		<tr>
			<td width="150" style="text-align:center;font-family:黑体;">18岁以下</td>
			<td width="150" style="text-align:center;">${resmap.res5}</td>
		</tr>
		
		<tr>
			<td width="150" style="text-align:center;font-family:黑体;">19~23岁</td>
			<td width="150" style="text-align:center;">${resmap.res4}</td>
		</tr>

		<tr>
			<td width="150" style="text-align:center;font-family:黑体;">24~30岁</td>
			<td width="150" style="text-align:center;">${resmap.res3}</td>
		</tr>
		
		<tr>
			<td width="150" style="text-align:center;font-family:黑体;">31~40岁</td>
			<td width="150" style="text-align:center;">${resmap.res2}</td>
		</tr>
		
		<tr>
			<td width="150" style="text-align:center;font-family:黑体;">40岁以上</td>
			<td width="150" style="text-align:center;">${resmap.res1}</td>
		</tr>
		
		<tr>
			<td width="150" style="text-align:center;font-family:黑体;">信息缺失</td>
			<td width="150" style="text-align:center;">${resmap.xxqs}</td>
		</tr>
		
		<tr>
			<td width="150" style="text-align:center;font-family:黑体;">总计</td>
			<td width="150" style="text-align:center;">${resmap.sum}</td>
		</tr>
	</table>
	<form id="tempform2">
		<input type="hidden" name="type" value="${type}">
		<input type="hidden" name="startDate" value="${startDate}">
		<input type="hidden" name="endDate" value="${endDate}">
		<input type="hidden" name="magazineid" value="${magazineid}">
	</form>
	</div> 
</div> 
<script>
$(function(){
	
	var data = ${reslist};
	
	new iChart.Bar2D({
		render : 'canvasDiv',
		data: data,
		offsetx: 20,
		//showpercent:true,
		//decimalsnum:2,
		width : 600,
		height : 200,
		coordinate:{
			scale:[{
				 position:'bottom',	
				 start_scale:0
				// end_scale:100,
				// scale_space:20,
				/*  listeners:{
					parseText:function(t,x,y){
						return {text:t+"%"}
					}
				} */
			}]
		}
	}).draw();
});

//导出到Excel
function exportInfo1() {
	document.getElementById("tempform2").action = "${ctx}/session/statistic/leaf2Export";	//年龄分布数据导出
	document.getElementById("tempform2").submit();
}

</script>
</body> 
</html>