<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../TagLib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="../../Pub.jsp" %>
<script type="text/javascript" src="${ctx}/ichartjs/ichart.1.2.min.js"></script>
<style type="text/css"> 
	#exhibitor114{ margin:0 auto;width:auto;height:100%} 
	#exhibitorleft{ float:left; width:50%; height:100%;} 
	#exhibitorright{ float:left; width:50%;height:100%;} 
</style> 
</head> 

<body> 
<div id="exhibitor114" style="float:inherit"> 
	<div id="exhibitorleft">
		<div id="canvasDiv" align="right"></div>
	</div> 
	
	<div id="exhibitorright">
		<div id="canvasDiv2"></div>
	</div> 
	
</div> 
<script>
//日期为空 
$(function(){
	var data = [
	        	{name : '18岁以下',value : "${resmap.felt18}",color:'#4572a7'},
	        	{name : '19~23岁',value : "${resmap.febetween19and23}",color:'#aa4643'},
	        	{name : '24~30岁',value : "${resmap.febetween24and30}",color:'#89a54e'},
	        	{name : '31~40岁',value : "${resmap.febetween31and40}",color:'#80699b'},
	        	{name : '40岁以上',value : "${resmap.feabove40}",color:'#92a8cd'}
        	];

	
	var chart = new iChart.Donut2D({
		render : 'canvasDiv',
		data: data,
		center : {
			text:'女士',
			color:'#3e576f',
			shadow:true,
			shadow_blur : 2,
			shadow_color : '#557797',
			shadow_offsetx : 0,
			shadow_offsety : 0,
			fontsize : 20
		},
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
		shadow : true,
		shadow_blur : 6,
		shadow_color : '#aaaaaa',
		shadow_offsetx : 0,
		shadow_offsety : 0,
		background_color:'#fefefe',
		offset_angle:0,//逆时针偏移120度
		showpercent:true,
		decimalsnum:2,
		width : 400,
		height : 200,
		radius:60
	});
	
	chart.draw();

	var data2 = [
	        	{name : '18岁以下',value : "${resmap.malelt18}",color:'#4572a7'},
	        	{name : '19~23岁',value : "${resmap.malebetween19and23}",color:'#aa4643'},
	        	{name : '24~30岁',value : "${resmap.malebetween24and30}",color:'#89a54e'},
	        	{name : '31~40岁',value : "${resmap.malebetween31and40}",color:'#80699b'},
	        	{name : '40岁以上',value : "${resmap.maleabove40}",color:'#92a8cd'}
        	];

	
	var chart2 = new iChart.Donut2D({
		render : 'canvasDiv2',
		data: data2,
		center : {
			text:'男士',
			color:'#3e576f',
			shadow:true,
			shadow_blur : 2,
			shadow_color : '#557797',
			shadow_offsetx : 0,
			shadow_offsety : 0,
			fontsize : 20
		},
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
		shadow : true,
		shadow_blur : 6,
		shadow_color : '#aaaaaa',
		shadow_offsetx : 0,
		shadow_offsety : 0,
		background_color:'#fefefe',
		offset_angle:0,//逆时针偏移120度
		showpercent:true,
		decimalsnum:2,
		width : 400,
		height : 200,
		radius:60
	});
	
	chart2.draw();
});

</script>
</body> 
</html>