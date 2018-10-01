<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../TagLib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="../../Pub.jsp" %>
<script type="text/javascript" src="${ctx}/ichartjs/ichart.1.2.min.js"></script>
<script type="text/javascript" src="${ctx}/js/myweek/WdatePicker.js"></script>
</head>
<body>
<div>
	<hr style="width:90%;" align="left"/>
	<span style="font-weight:bold;">预约续借量</span>(<span id="tjfs" value='month'>按月统计</span>)
<table>
		<tr>
			<td align="right">选择时间：</td>
						<td>
							<input id="d121" type="text" class="Wdate" value="${startDateweeks}" onfocus="WdatePicker({
								vel:'wstartDate'})"/>
							--
							<input id="d122" type="text" class="Wdate" value="${endDateweeks}" onfocus="WdatePicker({
								vel:'wendDate'})"/>
					
							<input type="hidden" name="wstartDate" id="wstartDate" value="${startDate}">
							<input type="hidden" name="wendDate" id="wendDate" value="${endDate}">
						<td align="right">
						<select id="method" style="width:100px;height:30px;" onchange="begindraw()">   
			    					<option value="1" selected="selected">预约</option>   
			    					<option value="2">续借</option> 
						</select>
						</td>
						<td>
							&nbsp;<a href="javascript:void(0)" class="easyui-linkbutton" onclick="tjbt('year')">&nbsp;年&nbsp;</a>
						</td>
						<td>
							&nbsp;<a href="javascript:void(0)" class="easyui-linkbutton" onclick="tjbt('month')">&nbsp;月&nbsp;</a>
						</td>
						<td>
							&nbsp;<a href="javascript:void(0)" class="easyui-linkbutton" onclick="tjbt('week')">&nbsp;周&nbsp;</a>
						</td>
						<td>
							&nbsp;<a href="javascript:void(0)" class="easyui-linkbutton" onclick="tjbt('day')">&nbsp;日&nbsp;</a>
						</td>
		</tr>
</table>

<br/>
  <div id="canvasDiv"></div>
</div>
<script>
var flow = [];
var labels = [];
var tt = "预约";

$(function(){
	
	$.ajax({
		url:"${ctx}/session/statistic/ckyhyyxjList",
		type:'POST',
		async:false,
		data: {"startDate":"${startDate}", "endDate":"${endDate}","type":"month",'flag':'1'},
		success:function(data){
			if(!data.result) {
		 		$('#startDate').datebox('setValue');
				alert('起始日期不能比结束日期晚!');
			}else {
				flow = data.flow;
				labels = data.labels;
				executeDraw();
			}
		}
	});
	
});

function tjbt(type){
	$('#tjfs').attr('value',type);
	begindraw();
}

function begindraw() {
	if(!$('#wstartDate').val() || !$('#wendDate').val()) {
		alert("时间不能为空!");
		return;
	}
	var s = $('#wstartDate').val();
	var e = $('#wendDate').val();
	if(s>e){
		alert('起始日期不能比结束日期晚!');
		return;
	}
	var type = $('#tjfs').attr('value');

	var flag = $("#method").val();
	if(flag == "1"){
		var tt = "预约";
	}else{
		var tt = "续借";
	}
	if(type == "year"){
		s = s.substring(0,4)+"-01-01";
		e = e.substring(0,4)+"-01-01";
		$('#tjfs').text("按年统计");
	}else if(type == "month"){		
		s = s.substring(0,7)+"-01";
		e = e.substring(0,7)+"-01";
		$('#tjfs').text("按月统计");
	}else if(type=="day"){
		$('#tjfs').text("按日统计");
	}else if(type=="week"){
		$('#tjfs').text("按周统计");
	}
	progressLoad();
	$.ajax({
		url:"${ctx}/session/statistic/ckyhyyxjList",
		type:'POST',
		async:false,
		data: {"startDate":s, "endDate":e,"type":type,"flag":flag},
		success:function(data){
			progressClose();
			if(!data.result) {
		 		$('#startDate').datebox('setValue');
				alert('起始日期不能比结束日期晚!');
			}else {
				flow = data.flow;
				labels = data.labels;
				executeDraw();
			}
		}
	});
}

function executeDraw() {
	var data = [
	         	{
	         		name : 'PV',
	         		value:flow,
	         		color:'#ec4646',
	         		line_width:1
	         	}
	       ];
	
	var chart = new iChart.LineBasic2D({
		render : 'canvasDiv',
		data: data,
		align:'center',
		width : 1100,
		height : 350,
		padding : 40,
		tip:{	//提示框
			enable:true,
			shadow:true,
			listeners:{
				 //tip:提示框对象、name:数据名称、value:数据值、text:当前文本、i:数据点的索引
				parseText:function(tip,name,value,text,i){
					return "<span style='color:#005268;font-size:12px;'>"+labels[i]+"月"+tt+"用户数量:"+
					"</span><span style='color:#005268;font-size:12px;'>"+value+"</span>";
				}
			}
		},
		crosshair:{	//十字线
			enable:true,
			line_color:'#ec4646'
		},
		sub_option : {	//折线的配置
			smooth : true,
			label:false,
			hollow:true,
			hollow_inside:true,
			point_size:6
		}, 
		coordinate:{
			offsety:-20,
			//gridlinesVisible:false,
			striped_factor : 0.18,
			axis:{
				color:'#252525',
				width:[0,0,4,0]
			},
			scale:[{
				 position:'left',	
				 start_scale:0,
				 end_scale:500,
				 scale_space:100,
				 scale_size:1,
				 scale_enable : false,
				 label : {color:'#9d987a',font : '微软雅黑',fontsize:11,fontweight:600}
			},{
				 position:'bottom',	
				 label : {color:'#9d987a',font : '微软雅黑',fontsize:11,fontweight:600, rotate:290, offsety:25},
				 scale_enable : false,
				 labels:labels
			}]
		} 
	});
 
	chart.draw();
}

function progressLoad(){  
	$("#winfra").attr("src", "about:blank");
    $("<div class=\"datagrid-mask\" style=\"position:absolute;z-index: 9999;\"></div>").css({display:"block",width:"100%",height:$(window).height()}).appendTo("body");  
    $("<div class=\"datagrid-mask-msg\" style=\"position:absolute;z-index: 9999;\"></div>").html("正在处理，请稍候。。。").appendTo("body").css({display:"block",left:($(document.body).outerWidth(true) - 190) / 2,top:($(window).height() - 45) / 2});  
}

function progressClose(){
	$(".datagrid-mask").remove();  
	$(".datagrid-mask-msg").remove();
}

</script>
</body>
</html>