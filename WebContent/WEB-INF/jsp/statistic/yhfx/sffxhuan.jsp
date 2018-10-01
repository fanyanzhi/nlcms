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
</style> 
</head> 
<body> 
<div id="exhibitor114" style="float:inherit"> 
	<div id="exhibitorleft">
		<div id="canvasDiv"></div>
	</div> 
</div> 
<script>
$(function(){
	var data = [
	        	{name : 'QQ好友',value : "${resmap.qqfriend}",color:'#fedd74'},
	        	{name : 'QQ空间',value : "${resmap.qqzone}",color:'#82d8ef'},
	        	{name : '微信好友',value : "${resmap.wxfriend}",color:'#f76864'},
	        	{name : '微信收藏',value : "${resmap.wxfavorite}",color:'#80bd91'},
	        	{name : '微信朋友圈',value : "${resmap.wxquanzi}",color:'#fd9fc1'},
	        	{name : '新浪微博',value : "${resmap.sinaweibo}",color:'#fd9fc1'}
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
		width : 900,
		height : 200,
		radius:60
	});
	
	chart.draw();
});
</script>
</body> 
</html>