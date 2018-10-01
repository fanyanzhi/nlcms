<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../TagLib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="../../Pub.jsp" %>
<script type="text/javascript" src="${ctx}/ichartjs/ichart.1.2.min.js"></script>
<style type="text/css"> 
	#exhibitor114{ margin:0 auto;width:auto;height:100%} 
	#exhibitorleft{ float:left; width:45%; height:100%;} 
	#exhibitorright{ float:left; width:55%;height:100%;} 
</style> 
</head> 


<body> 
<div id="exhibitor114" style="float:inherit"> 
	<div id="exhibitorleft">
		<div id="canvasDiv"></div>
	</div> 
	
	<div id="exhibitorright">
	</div> 
</div> 
<script>
$(function(){
	var data = [
	        	{name : '实名用户',value : "${resmap.realnum}",color:'#A020F0'},
	        	{name : '持卡用户',value : "${resmap.cardnum}",color:'#BCEE68'},
	        	{name : '虚拟卡用户',value : "${resmap.virnum}",color:'#CD661D'},
        	];
	
	new iChart.Pie2D({
		render : 'canvasDiv',
		data: data,
		legend : {
			enable : false
		},
		showpercent:true,
		decimalsnum:2,
		width : 700,
		height : 300,
		radius:140
	}).draw();
});


</script>
</body> 
</html>