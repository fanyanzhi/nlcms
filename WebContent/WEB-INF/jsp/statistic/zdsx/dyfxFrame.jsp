<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../TagLib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="../../Pub.jsp" %>
<script type="text/javascript" src="${ctx}/ichartjs/ichart.1.2.min.js"></script>
<style>
.btn_blue{ background:#429cff; color:#FFF; }
.btn_gray{ background:#a6b3ba; color:#FFF;}
.btn{text-transform: uppercase; font-size:12px; font-family:"微软雅黑";
	  height: 23px;
	  margin: 0;
	  overflow: visible; padding:0 12px; border:none;
	  cursor:pointer;vertical-align: middle;
}
</style>
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
	<strong id="wenzi"></strong>
	<br/>
	<div id="canvasDiv"></div>
	<br/>
	<div>
	<strong>省市数据明细</strong>
		<div>
			<iframe id="winfra" src="about:blank" width="100%" height="500" frameborder=0 marginheight=0></iframe>
		</div>
	</div>
	
<script>
var flow;
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
		url:"${ctx}/session/statistic/dyfxData",
		type:'POST',
		async:false,
		data: {"startDate":"${lastweek}", "endDate":"${yesterday}"},
		success:function(data){
			progressClose();
			flow = data;
			$("#wenzi").html("top"+data.length+"省市");
			executedraw();
			$("#winfra").attr("src", "${ctx}/session/statistic/dyfxGrid?startDate=${lastweek}&endDate=${yesterday}&r="+Math.random());
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
		url:"${ctx}/session/statistic/dyfxData",
		type:'POST',
		async:false,
		data: {"startDate":startDate, "endDate":endDate},
		success:function(data){
			progressClose();
			flow = data;
			$("#wenzi").html("top"+data.length+"省市");
			executedraw();
			$("#winfra").attr("src", "${ctx}/session/statistic/dyfxGrid?startDate="+startDate+"&endDate="+endDate+"&r="+Math.random());
		}
	});
}

function executedraw() {
	new iChart.Bar2D({
		render : 'canvasDiv',
		data: flow,
		//showpercent:true,
		//decimalsnum:2,
		width : 800,
		height : 400,
		coordinate:{
			scale:[{
				 position:'bottom',	
				 start_scale:0
				 //end_scale:5000,
				 //scale_space:500,
				/*  listeners:{
					parseText:function(t,x,y){
						return {text:t+"%"}
					}
				} */
			}]
		}
	}).draw();

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