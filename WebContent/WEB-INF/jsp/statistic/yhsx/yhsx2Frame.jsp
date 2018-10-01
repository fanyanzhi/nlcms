<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../TagLib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="../../Pub.jsp" %>
<script type="text/javascript" src="${ctx}/ichartjs/ichart.1.2.min.js"></script>
<style type="text/css"> 
	#exhibitor114{ margin:0 auto;width:auto;height:100%} 
	#exhibitorleft{ float:left; width:40%; height:100%;} 
	#exhibitorright{ float:left; width:60%;height:100%;} 
	td{height:25px;}
	table{text-align:center;color:#222; font:12px/20px Arial,"宋体";  background-color:#fff;}
</style> 
</head> 


<body> 
<div id="exhibitor114" style="float:inherit"> 
	<div id="exhibitorleft">
		<div id="canvasDiv"></div>
	</div> 
	
	<div id="exhibitorright">
	<table cellpadding="0" cellspacing="0" width="80%" align="right">
			<tr>
				<td width="300" style="text-align:left">学历分布数据：</td>
				<td width="150" style="text-align:center"></td>
				<td width="150" style="text-align:center"></td>
				<td width="150" style="text-align:center"></td>
				<td width="150" style="text-align:center"></td>
				<td width="120" style="text-align:center"><input type="button" value="导出到Excel" onclick="exportInfo1()"></td>
			</tr>
	</table>
	<table border="1" width="80%" bordercolor="#a0c6e5" align="right" style="border:1;border-collapse:collapse;">
			<tr>
				<td width="150" style="text-align:center">用户类型</td>
				<td width="150" style="text-align:center">专科以下</td>
				<td width="150" style="text-align:center">专科</td>
				<td width="150" style="text-align:center">本科</td>
				<td width="150" style="text-align:center">硕士</td>
				<td width="150" style="text-align:center">博士</td>
				<td width="120" style="text-align:center">总计</td>
			</tr>
			
			<c:forEach items="${xlList }" var="ele" varStatus="vs">
			<tr>
				<td style="text-align:center">${ele.text }</td>
				<td style="text-align:center">${ele.level1 }</td>
				<td style="text-align:center">${ele.level2 }</td>
				<td style="text-align:center">${ele.level3 }</td>
				<td style="text-align:center">${ele.level4 }</td>
				<td style="text-align:center">${ele.level5 }</td>
				<td style="text-align:center">${ele.sum }</td>
			</tr>
			</c:forEach>
	</table>
	
	<form id="tempForm1">
		<input type="hidden" name="startDate" id="sdt" value='${startDate}'>
		<input type="hidden" name="endDate" id="edt" value='${endDate}'>
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
		offset_angle:0,//逆时针偏移120度
		showpercent:true,
		decimalsnum:2,
		width : 550,
		height : 200,
		radius:60
	});
	
	chart.draw();
});

//导出到Excel
function exportInfo1() {
	document.getElementById("tempForm1").action = "${ctx}/session/statistic/yhsxxlExport";	//用户属性学历分布 
	document.getElementById("tempForm1").submit();
}

</script>
</body> 
</html>