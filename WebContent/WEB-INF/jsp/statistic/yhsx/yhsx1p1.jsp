<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../TagLib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="../../Pub.jsp" %>
<script type="text/javascript" src="${ctx}/ichartjs/ichart.1.2.min.js"></script>
</head>
<body>
	<div align="center">
 	 <div id="canvasDiv"></div>
	</div>

<script>
//日期为空 
$(function(){
	var data = [
	        	{name : '女',value : "${resmap.femaleratio}",color:'#BCEE68'},
	        	{name : '男',value : "${resmap.maleratio}",color:'#A020F0'},
        	];
	
	new iChart.Pie2D({
		render : 'canvasDiv',
		data: data,
		offset_angle: 90,
		//title : 'Top 5 Browsers from 1 to 29 Feb 2012',
		legend : {
			enable : true
		},
		showpercent:true,
		decimalsnum:2,
		width : 400,
		height : 100,
		radius:30
	}).draw();


});

</script>
</body>
</html>