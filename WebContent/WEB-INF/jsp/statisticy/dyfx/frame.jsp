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

<font style="font-family:黑体">新增用户明细</font>
<br/>
<table>
		<tr>
			<td>&nbsp;</td>
			<td>
				<input id="d121" type="text" class="Wdate" value="${lastmonthweeks}" onfocus="WdatePicker({
					vel:'wstartDate'})"/>
				--
				<input id="d122" type="text" class="Wdate" value="${yesweeks}" onfocus="WdatePicker({
					vel:'wendDate'})"/>
				
				<input type="hidden" name="wstartDate" id="wstartDate" value="${lastmonth}">
				<input type="hidden" name="wendDate" id="wendDate" value="${yesterday}">
			</td>
			<td>
				&nbsp;<a href="javascript:void(0)" class="easyui-linkbutton" onclick="stayear()">&nbsp;年&nbsp;</a>
			</td>
			<td>
				&nbsp;<a href="javascript:void(0)" class="easyui-linkbutton" onclick="stamon()">&nbsp;月&nbsp;</a>
			</td>
			<td>
				&nbsp;<a href="javascript:void(0)" class="easyui-linkbutton" onclick="staweek()">&nbsp;周&nbsp;</a>
			</td>
			<td>
				&nbsp;<a href="javascript:void(0)" class="easyui-linkbutton" onclick="staday()">&nbsp;日&nbsp;</a>
			</td>
		</tr>
</table>
	<br/>
	<font id="tit1" style="font-family:黑体">按天统计</font>
	<br/>
	<br/>
	<font style="font-family:黑体">省数据明细</font>
	<br/>
	<input type="button" value="导出到Excel" onclick="exportInfo(1)">
	<div>
		<iframe id="winfra" src="about:blank" width="80%" height="400" frameborder="0" marginheight="0"></iframe>
	</div>
	<br/>
	<br/>
	<font style="font-family:黑体">市数据明细</font>
	<br/>
	<input type="button" value="导出到Excel" onclick="exportInfo(0)">
	<div>
		<iframe id="winfrax" src="about:blank" width="80%" height="400" frameborder="0" marginheight="0"></iframe>
	</div>
	<br/>
	<strong id="wenzi"></strong>
	<br/>
	<div id="canvasDiv"></div>
	<br/>
</div>

<form id="tempForm">
	<input type="hidden" name="startDate" id="sdt">
	<input type="hidden" name="endDate" id="edt">
	<input type="hidden" name="status" id="sts">
	<input type="hidden" name="type" id="typ">
</form>
<script>
var flow = [];
var tit1 = "按天统计";
var flag = "day";

//省导出到Excel
function exportInfo(type) {
	var ws = $("#wstartDate").val();
	var we = $("#wendDate").val();
	
	$("#sdt").val(ws);
	$("#edt").val(we);
	$("#sts").val(flag);
	$("#typ").val(type);
	
	document.getElementById("tempForm").action = "${ctx}/session/statisticy/dyfxExport";
	document.getElementById("tempForm").submit();
}


$(function(){
	$("#winfra").load(function () {
	    var mainheight = $(this).contents().find("body").height() + 30;
	    $(this).height(mainheight);
	});
	
	$("#winfrax").load(function () {
	    var mainheight = $(this).contents().find("body").height() + 30;
	    $(this).height(mainheight);
	});
	
	progressLoad();
	
	$.ajax({
		url:"${ctx}/session/statisticy/dyfxData",
		type:'POST',
		async:false,
		data: {"startDate":"${lastmonth}", "endDate":"${yesterday}", "status":"day"},
		success:function(data){
			progressClose();
			flow = data;
			$("#wenzi").html("top"+data.length+"省市");
			executedraw();
			$("#winfra").attr("src", "${ctx}/session/statisticy/dyfxProTable?type=1&status=day&startDate=${lastmonth}&endDate=${yesterday}&r="+Math.random());
			$("#winfrax").attr("src", "${ctx}/session/statisticy/dyfxProTable?type=0&status=day&startDate=${lastmonth}&endDate=${yesterday}&r="+Math.random());
		}
	});

});

//=========================================================================
//按天统计
function staday() {
	var ws = $("#wstartDate").val();
	var we = $("#wendDate").val();
	
	if(!ws || !we) {
		alert("时间不能为空!");
		return;
	}
	
	var cont = "1";
	$.ajax({
		url:"${ctx}/session/statistic/checkdate",
		type:'POST',
		async:false,
		data: {"startDate":ws, "endDate":we},
		success:function(data){
			if(!data.result) {
				cont = "0";
			}
		}
	});
	
	if(cont == "0") {
		alert('起始日期不能比结束日期晚!');
		return ;
	}
	
	tit1 = "按天统计";
	$("#tit1").html("按天统计");
	flag = "day";
	
	progressLoad();
	
	$.ajax({
		url:"${ctx}/session/statisticy/dyfxData",
		type:'POST',
		async:false,
		data: {"startDate":ws, "endDate":we, "status":"day"},
		success:function(data){
			progressClose();
			flow = data;
			$("#wenzi").html("top"+data.length+"省市");
			executedraw();
			$("#winfra").attr("src", "${ctx}/session/statisticy/dyfxProTable?type=1&status=day&startDate="+ws+"&endDate="+we+"&r="+Math.random());
			$("#winfrax").attr("src", "${ctx}/session/statisticy/dyfxProTable?type=0&status=day&startDate="+ws+"&endDate="+we+"&r="+Math.random());
		}
	});
	
}

function executedraw() {
	new iChart.Bar2D({
		render : 'canvasDiv',
		data: flow,
		width : 800,
		height : 400,
		coordinate:{
			scale:[{
				 position:'bottom',	
				 start_scale:0
			}]
		}
	}).draw();

}

function progressLoad(){  
	$("#winfra").attr("src", "about:blank");
    $("<div class=\"datagrid-mask\" style=\"position:absolute;z-index: 9999;\"></div>").css({display:"block",width:"100%",height:"1500"}).appendTo("body");  
    $("<div class=\"datagrid-mask-msg\" style=\"position:absolute;z-index: 9999;\"></div>").html("正在处理，请稍候。。。").appendTo("body").css({display:"block",left:($(document.body).outerWidth(true) - 190) / 2,top:($(window).height() - 45) / 2});  
}

function progressClose(){
	$(".datagrid-mask").remove();  
	$(".datagrid-mask-msg").remove();
}

//====================月=================================
function stamon() {
	var ws = $("#wstartDate").val();
	var we = $("#wendDate").val();
	
	if(!ws || !we) {
		alert("时间不能为空!");
		return;
	}
	var monws = ws.substr(0, 7);
	var monwe = we.substr(0, 7);
	
	var s = monws+'-01';
	var e = monwe+'-01';
	
	var cont = "1";
	$.ajax({
		url:"${ctx}/session/statistic/checkdate",
		type:'POST',
		async:false,
		data: {"startDate":ws, "endDate":we},
		success:function(data){
			if(!data.result) {
				cont = "0";
			}
		}
	});
	
	if(cont == "0") {
		alert('起始日期不能比结束日期晚!');
		return ;
	}
	
	tit1 = "按月统计";
	$("#tit1").html("按月统计");
	flag = "month";
	
	progressLoad();
	
	$.ajax({
		url:"${ctx}/session/statisticy/dyfxData",
		type:'POST',
		async:false,
		data: {"startDate":s, "endDate":e, "status":"month"},
		success:function(data){
			progressClose();
			flow = data;
			$("#wenzi").html("top"+data.length+"省市");
			executedraw();
			$("#winfra").attr("src", "${ctx}/session/statisticy/dyfxProTable?type=1&status=month&startDate="+s+"&endDate="+e+"&r="+Math.random());
			$("#winfrax").attr("src", "${ctx}/session/statisticy/dyfxProTable?type=0&status=month&startDate="+s+"&endDate="+e+"&r="+Math.random());
		}
	});
	
}
	
//==============年=======================
function stayear() {
	var ws = $("#wstartDate").val();
	var we = $("#wendDate").val();
	
	if(!ws || !we) {
		alert("时间不能为空!");
		return;
	}
	var yearws = ws.substr(0, 4);
	var yearwe = we.substr(0, 4);
	
	var cont = "1";
	$.ajax({
		url:"${ctx}/session/statistic/checkdate",
		type:'POST',
		async:false,
		data: {"startDate":ws, "endDate":we},
		success:function(data){
			if(!data.result) {
				cont = "0";
			}
		}
	});
	
	if(cont == "0") {
		alert('起始日期不能比结束日期晚!');
		return ;
	}
	
	var s = yearws+'-01-01';
	var e = yearwe+'-01-01';
	
	tit1 = "按年统计";
	$("#tit1").html("按年统计");
	flag = "year";
	
	progressLoad();
	
	$.ajax({
		url:"${ctx}/session/statisticy/dyfxData",
		type:'POST',
		async:false,
		data: {"startDate":s, "endDate":e, "status":"year"},
		success:function(data){
			progressClose();
			flow = data;
			$("#wenzi").html("top"+data.length+"省市");
			executedraw();
			$("#winfra").attr("src", "${ctx}/session/statisticy/dyfxProTable?type=1&status=year&startDate="+s+"&endDate="+e+"&r="+Math.random());
			$("#winfrax").attr("src", "${ctx}/session/statisticy/dyfxProTable?type=0&status=year&startDate="+s+"&endDate="+e+"&r="+Math.random());
		}
	});
}

//=================周====================
function staweek() {
	var ws = $("#wstartDate").val();
	var we = $("#wendDate").val();
	
	if(!ws || !we) {
		alert("时间不能为空!");
		return;
	}
	
	var cont = "1";
	$.ajax({
		url:"${ctx}/session/statistic/checkdate",
		type:'POST',
		async:false,
		data: {"startDate":ws, "endDate":we},
		success:function(data){
			if(!data.result) {
				cont = "0";
			}
		}
	});
	
	if(cont == "0") {
		alert('起始日期不能比结束日期晚!');
		return ;
	}
	
	progressLoad();
	
	tit1 = "按周统计";
	$("#tit1").html("按周统计");
	flag = "week";
	
	$.ajax({
		url:"${ctx}/session/statisticy/dyfxData",
		type:'POST',
		async:false,
		data: {"startDate":ws, "endDate":we, "status":"week"},
		success:function(data){
			progressClose();
			flow = data;
			$("#wenzi").html("top"+data.length+"省市");
			executedraw();
			$("#winfra").attr("src", "${ctx}/session/statisticy/dyfxProTable?type=1&status=week&startDate="+ws+"&endDate="+we+"&r="+Math.random());
			$("#winfrax").attr("src", "${ctx}/session/statisticy/dyfxProTable?type=0&status=week&startDate="+ws+"&endDate="+we+"&r="+Math.random());
		}
	});
}
		
</script>
</body>
</html>