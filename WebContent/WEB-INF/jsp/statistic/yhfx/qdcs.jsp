<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../TagLib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="../../Pub.jsp" %>
<script type="text/javascript" src="${ctx}/ichartjs/ichart.1.2.min.js"></script>
</head>
<body>
<form>
	<table>
		<tr>
			<td align="right">选择时间：</td>
			<td>
				<input type="text" id="startDate" name="startDate" class="easyui-datebox" /> -- 
				<input type="text" id="endDate" name="endDate" class="easyui-datebox" />
			</td>
			
			<td align="right"></td>
			<td>
				<input type="button" id="bt1" value="统计"/>
			</td>
		</tr>
	</table>
</form>
	<br/>
	启动次数趋势:
	<div id="canvasDiv"></div>
	<br/>
	<div>
	启动次数明细:
		<div>
			<iframe id="winfra" src="about:blank" width="40%" frameborder=0 marginheight=0></iframe>
		</div>
	</div>
	
<script>
var chart;
var flow = [];
var labels = [];
//日期为空 
$(function(){
	var todaydate = getNowFormatDate();
	
	//选择时间的一些限制 
	$("#startDate").datebox({
		onSelect:function(date){
			var startDate = $('#startDate').datebox('getValue');
			var endDate = $('#endDate').datebox('getValue');
			if(startDate >= todaydate) {
				alert("请选择今天之前的日期!");
				$('#startDate').datebox('setValue');
			}else if(endDate!="" && endDate < startDate){
		 		alert('选择日期不能晚于'+endDate);
		 		$('#startDate').datebox('setValue');
		 	}
		}
	});
	
	$("#endDate").datebox({
		onSelect:function(date){
			var startDate = $('#startDate').datebox('getValue');
			var endDate = $('#endDate').datebox('getValue');
			if(endDate >= todaydate) {
				alert("请选择今天之前的日期!");
				$('#endDate').datebox('setValue');
			}else if(startDate!="" && endDate < startDate){
		 		alert('选择日期不能早于'+startDate);
		 		$('#endDate').datebox('setValue');
		 	}
		}
	});
	
	$('#startDate').datebox('setValue', "${lastweek}");
	$('#endDate').datebox('setValue', "${yesterday}");
	
	//时间框禁止输入
	$(".datebox :text").attr("readonly","readonly");
	
	
	progressLoad();
	
	$.ajax({
		url:"${ctx}/session/statistic/qdcsqs",
		type:'POST',
		async:false,
		data: {"startDate":"${lastweek}", "endDate":"${yesterday}"},
		success:function(data){
			progressClose();
			flow = data.flow;
			labels = data.labels;
			executedraw();
			$("#winfra").attr("src", "${ctx}/session/statistic/qdcsmx?startDate=${lastweek}&endDate=${yesterday}&r="+Math.random());
		}
	});
	
	

	$("#bt1").click(function(){
		begindraw();
	}) 

});


function getNowFormatDate() {
    var date = new Date();
    var seperator1 = "-";
    var seperator2 = ":";
    var year = date.getFullYear();
    var month = date.getMonth() + 1;
    var strDate = date.getDate();
    if (month >= 1 && month <= 9) {
        month = "0" + month;
    }
    if (strDate >= 0 && strDate <= 9) {
        strDate = "0" + strDate;
    }
    var currentdate = year + seperator1 + month + seperator1 + strDate;
    return currentdate;
}

//开始画图
function begindraw() {
	var startDate = $("#startDate").datetimebox('getValue');
	var endDate = $("#endDate").datetimebox('getValue');
	if(!startDate || !endDate) {
		alert("时间不能为空!");
		return ;
	}
	
	progressLoad();
	
	$.ajax({
		url:"${ctx}/session/statistic/qdcsqs",
		type:'POST',
		async:false,
		data: {"startDate":startDate, "endDate":endDate},
		success:function(data){
			progressClose();
			flow = data.flow;
			labels = data.labels;
			executedraw();
			$("#winfra").attr("src", "${ctx}/session/statistic/qdcsmx?startDate="+startDate+"&endDate="+endDate+"&r="+Math.random());
		}
	});
}

function executedraw() {
	var data = [
	         	{
	         		name : 'PV',
	         		value:flow,
	         		color:'#ec4646',
	         		line_width:1
	         	}
	       ];
	
	chart = new iChart.LineBasic2D({
		render : 'canvasDiv',
		data: data,
		align:'center',
		width : 1100,
		height : 330,
		tip:{	//提示框
			enable:true,
			shadow:true,
			listeners:{
				 //tip:提示框对象、name:数据名称、value:数据值、text:当前文本、i:数据点的索引
				parseText:function(tip,name,value,text,i){
					return "<span style='color:#005268;font-size:12px;'>"+labels[i]+"启动次数:"+
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
			offsety:-25,
			//gridlinesVisible:false,
			striped_factor : 0.18,
			axis:{
				color:'#252525',
				width:[0,0,4,0]
			},
			scale:[{
				 position:'left',	
				 start_scale:0,
				 end_scale:5000,
				 scale_space:1000,
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