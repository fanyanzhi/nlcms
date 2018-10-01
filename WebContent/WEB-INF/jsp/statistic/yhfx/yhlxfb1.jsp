<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../TagLib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="../../Pub.jsp" %>
<script type="text/javascript" src="${ctx}/ichartjs/ichart.1.2.min.js"></script>
</head>
<body>
  <div id="canvasDiv"></div>

<script>
//日期为空 
$(function(){
	var data = [
	        	{name : '实名用户',value : "${resmap.realnum}",color:'#A020F0'},
	        	{name : '持卡用户',value : "${resmap.cardnum}",color:'#BCEE68'},
	        	{name : '虚拟卡用户',value : "${resmap.virnum}",color:'#CD661D'},
        	];
	
	new iChart.Pie2D({
		render : 'canvasDiv',
		data: data,
		//title : 'Top 5 Browsers from 1 to 29 Feb 2012',
		legend : {
			enable : true
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