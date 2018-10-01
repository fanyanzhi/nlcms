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
			</td>
			
			<td align="right">
			<select id="method" style="width:100px;height:30px;" onchange="begindraw()">   
    					<option value="1" selected="selected">下载量</option>   
    					<option value="2">更新量</option> 
    					<option value="3">使用时长</option>   
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
	<span id="tjfs" value='month'>按月统计</span>
	<br/>
	<br/>
	<div id="canvasDiv"></div>
</div>

	<br/>
	<div>
	<span id="wz">下载量：</span>
		<div>
			<iframe id="winfra" src="about:blank" width="50%" frameborder=0 marginheight=0></iframe>
		</div>
	
	</div>
	
	<form id="tempForm1">
		<input type="hidden" name="startDate" id="sdt">
		<input type="hidden" name="endDate" id="edt">
	</form>
<script>
var flow = [];
var flow2 = [];
var labels = [];
var tt = "下载量：";
var endf = 5000;
var scalef = 1000;

$(function(){
	progressLoad();
	
	$.ajax({
		url:"${ctx}/session/statistic/apptjLine",
		type:'POST',
		async:false,
		data: {"startDate":"${startDate}", "endDate":"${endDate}","type":"month",'flag':'1'},
		success:function(data){
			if(!data.result) {
				labels = [''];
				flow = [0];
				flow2 = [0];
				progressClose();
				executedraw();
				$("#winfra").attr("src", urlx3+"?type="+type+"&startDate="+s+"&endDate="+e+"&r="+Math.random());
				//alert('无记录');
			}else {
				progressClose();
				labels = data.labels;
				flow = eval(data.flow);
				flow2 = eval(data.flow2);
				executedraw();
				$("#winfra").attr("src", "${ctx}/session/statistic/table?type=month&startDate=${startDate}&endDate=${endDate}&r="+Math.random());
			}
		}
	});
});
	function tjbt(type){
		$('#tjfs').attr('value',type);
		begindraw();
	}


//开始画图
function begindraw() {
	if(!$('#wstartDate').val() || !$('#wendDate').val()) {
		alert("时间不能为空!");
		return;
	}
	
	var s = $('#wstartDate').val();
	var e = $('#wendDate').val();
	var type = $('#tjfs').attr('value');
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
	var flag = $("#method").val();

	var urlx1 = "${ctx}/session/statistic/apptjLine";;
	var urlx3 = "${ctx}/session/statistic/table";
	if(flag == 1) {
		tt = "下载量：";
		$("#wz").html(tt);
	}else if(flag == 2){
		tt = "更新量：";
		$("#wz").html(tt);
	}else if(flag == 3){
		tt = "使用时长：";
		$("#wz").html(tt);
	}else {
		tt = " ";
		$("#wz").html(tt);
	}
	
	progressLoad();
	
	$.ajax({
		url:urlx1,
		type:'POST',
		async:false,
		data: {"startDate":s, "endDate":e,"type":type,'flag':flag},
		success:function(data){
			if(!data.result) {
				labels = [''];
				flow = [0];
				flow2 = [0];
				progressClose();
				executedraw();
				$("#winfra").attr("src", urlx3+"?type="+type+"&startDate="+s+"&endDate="+e+"&r="+Math.random());
				//alert('无记录');
			}else {
				labels = data.labels;
				flow = data.flow;
				flow2 = data.flow2;
				progressClose();
				executedraw();
				$("#winfra").attr("src", urlx3+"?type="+type+"&startDate="+s+"&endDate="+e+"&r="+Math.random());
			}
		}
	});
	
}

function executedraw() {
	var data = [
	         	{
	         		name : 'Iphone',
	         		value:flow,
	         		color:'#0d8ecf',
	         		line_width:1
	         	},
	         	{
	         		name : 'Android',
	         		value:flow2,
	         		color:'#ec4646',
	         		line_width:1
	         	}
	       ];
	
	var chart = new iChart.LineBasic2D({
		render : 'canvasDiv',
		data: data,
		align:'center',
		width : 1100,
		height : 380,
		padding : 50,
		tip:{	//提示框
			enable:true,
			shadow:true,
			listeners:{
				 //tip:提示框对象、name:数据名称、value:数据值、text:当前文本、i:数据点的索引
				parseText:function(tip,name,value,text,i){
					return name+tt+value;
				}
			}
		},
		crosshair:{	//十字线
			enable:true,
			line_color:'#ec4646'
		},
		legend : {
			enable : true,
			row:1,//设置在一行上显示，与column配合使用
			column : 'max',
			valign:'top',
			sign:'bar',
			background_color:null,//设置透明背景
			offsetx:-80,//设置x轴偏移，满足位置需要
			offsety:-7,
			border : true
		},
		sub_option : {	//折线的配置
			smooth : true,
			label:false,
			hollow:true,
			hollow_inside:true,
			point_size:6
		}, 
		coordinate:{
			//offsety:-25,
			//gridlinesVisible:false,
			striped_factor : 0.18,
			axis:{
				color:'#252525',
				width:[0,0,4,0]
			},
			scale:[{
				 position:'left',	
				 start_scale:0,
				 //end_scale:endf,
				 //scale_space:scalef,
				 scale_size:1,
				 scale_enable : false,
				 label : {color:'#9d987a',font : '微软雅黑',fontsize:11,fontweight:600}
			},{
				 position:'bottom',	
				 label : {color:'#9d987a',font : '微软雅黑',fontsize:11,fontweight:600, rotate:290, offsety:20},
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