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
			<td width="150" style="text-align:center;font-family:黑体;">专科以下</td>
			<td width="150" style="text-align:center;">${resmap.qitaNum }</td>
		</tr>
		
		<tr>
			<td width="150" style="text-align:center;font-family:黑体;">专科</td>
			<td width="150" style="text-align:center;">${resmap.zhuankeNum }</td>
		</tr>

		<tr>
			<td width="150" style="text-align:center;font-family:黑体;">本科</td>
			<td width="150" style="text-align:center;">${resmap.benkeNum }</td>
		</tr>
		
		<tr>
			<td width="150" style="text-align:center;font-family:黑体;">硕士</td>
			<td width="150" style="text-align:center;">${resmap.shuoshiNum }</td>
		</tr>
		
		<tr>
			<td width="150" style="text-align:center;font-family:黑体;">博士</td>
			<td width="150" style="text-align:center;">${resmap.boshiNum }</td>
		</tr>
		
		<tr>
			<td width="150" style="text-align:center;font-family:黑体;">总计</td>
			<td width="150" style="text-align:center;">${resmap.sum }</td>
		</tr>
	</table>
	<form id="tempForm3">
		<input type="hidden" name="type" value="${type}">
		<input type="hidden" name="startDate" value="${startDate}">
		<input type="hidden" name="endDate" value="${endDate}">
		<input type="hidden" name="magazineid" value="${magazineid}">
	</form>
	</div> 
</div> 
<script>
$(function(){
	var data = [
	        	{name : '本科',value : "${resmap.benkeRatio}",color:'#fedd74'},
	        	{name : '专科',value : "${resmap.zhuankeRatio}",color:'#82d8ef'},
	        	{name : '专科以下',value : "${resmap.qitaRatio}",color:'#f76864'},
	        	{name : '硕士',value : "${resmap.shuoshiRatio}",color:'#80bd91'},
	        	{name : '博士',value : "${resmap.boshiRatio}",color:'#fd9fc1'}
        	];
	
	var chart = new iChart.Donut2D({
		render : 'canvasDiv',
		data: data,
		sub_option : {
			label : {
				background_color:null,
				sign:false,//设置禁用label的小图标
				padding:'0 4',
				border:{
					enable:false,
					color:'#666666'
				},
				fontsize:12,
				fontweight:500,
				color : '#4572a7'
			},
			border : {
				width : 2,
				color : '#ffffff'
			}
		},
		
		legend : {
			enable : true,
			shadow:true,
			background_color:null,
			border:false,
			legend_space:30,//图例间距
			line_height:20,//设置行高
			sign_space:5,//小图标与文本间距
			sign_size:10,//小图标大小
			color:'#6f6f6f',
			fontsize:18//文本大小
		},
		
		shadow : true,
		shadow_blur : 6,
		shadow_color : '#aaaaaa',
		shadow_offsetx : 0,
		shadow_offsety : 0,
		background_color:'#fefefe',
		offset_angle:0,
		showpercent:true,
		decimalsnum:2,
		width : 600,
		height : 200,
		radius:60
	});
	
	chart.draw();
});

//导出到Excel
function exportInfo1() {
	document.getElementById("tempForm3").action = "${ctx}/session/statistic/leaf3Export";	//学历分布数据导出 
	document.getElementById("tempForm3").submit();
}

</script>
</body> 
</html>