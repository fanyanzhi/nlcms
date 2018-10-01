<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../TagLib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" type="text/css" href="${ctx}/js/easyui/themes/default/easyui.css"/>
<link rel="stylesheet" type="text/css" href="${ctx}/js/easyui/themes/icon.css"/>
<script type="text/javascript" src="${ctx}/js/jquery-1.8.3.js"></script>
<script type="text/javascript" src="${ctx}/js/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${ctx}/js/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="${ctx}/ichartjs/ichart.1.2.min.js"></script>
<script type="text/javascript" src="${ctx}/js/myweek/WdatePicker.js"></script>

</head>
<body>
<div>
	<table>
		<tr>
			<td align="right">选择时间：</td>
						<td>
							<!-- <input type="text" id="startDate" name="startDate"
								class="easyui-datebox" /> -- <input type="text" id="endDate"
								name="endDate" class="easyui-datebox" />&nbsp;</td> -->
							<input id="d121" type="text" class="Wdate" value="${startDateweeks}" onfocus="WdatePicker({
								vel:'wstartDate'})"/>
							--
							<input id="d122" type="text" class="Wdate" value="${endDateweeks}" onfocus="WdatePicker({
								vel:'wendDate'})"/>
					
							<input type="hidden" name="wstartDate" id="wstartDate" value="${startDate}">
							<input type="hidden" name="wendDate" id="wendDate" value="${endDate}">
						<td align="right"></td>
						<!-- <td><input type="button" id="bt1" value="统计" /></td> -->
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
		
		<tr>
			<td align="left" style="font-weight:bold;">选择资源类别：</td>
			
			<td>
				<input type="radio" name="zytype" value="0" checked="checked" />期刊
				<input type="radio" name="zytype" value="1" />听书
				<input type="radio" name="zytype" value="2" />文津诵读
				<input type="radio" name="zytype" value="3" />特色专题
				<input type="radio" name="zytype" value="4" />总访问量
			</td>
			
			<td>
			</td>
		</tr>
		
		<tr>
			<td align="left" style="font-weight:bold;">选择资源属性：</td>
			
			<td>
				<input type="radio" id="de1" name="zyattr" value="0" checked="checked" /><span>浏览量</span>
				<input type="radio" id="de2" name="zyattr" value="1" /><span>下载量</span>
				<input type="radio" id="de3" name="zyattr" value="2" /><span>收藏量</span>
			</td>
			
			<td>
			</td>
		</tr>
	</table>
	<br/>
	<span id="tjfs" value='month'>按月统计</span>
	<br/>
	<div id="canvasDiv"></div>
</div>

	<br/>
	<div>
	<p id="wz">期刊访问数据</p>
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
var tit = "期刊浏览量";
var tableurl = "";
var excelurl = "${ctx}/session/statistic/zyhxqkExport";

$(function(){
	
	$("input:radio[name=zytype]").click(function(){
		canceldis();
		
		var typeval = $(this).val();
		if(typeval == 0) {
			$("#de1").attr("checked","checked");
		}else if(typeval == 1) {
			$("#de1").attr("checked","checked");
		}else if(typeval == 2) {
			$("#de2").attr("disabled","disabled");	//文津诵读没有下载量、收藏量统计
			$("#de2").next().css('color','gray');
			$("#de3").attr("disabled","disabled");
			$("#de3").next().css('color','gray');
			$("#de1").attr("checked","checked");
		}else if(typeval == 3) {
			$("#de2").attr("disabled","disabled");	//特色专题没有下载量统计
			$("#de2").next().css('color','gray');
			$("#de1").attr("checked","checked");
		}else {
			$("#de1").attr("checked","checked");
		}
		begindraw();
		
	});
	
	$("input:radio[name=zyattr]").click(function(){
		begindraw();
		
	});
	
	progressLoad();
	
	$.ajax({
		url:"${ctx}/session/statistic/zyhxDataList",
		type:'POST',
		async:false,
		data: {"startDate":"${startDate}", "endDate":"${endDate}","type":"month","zytype":"0","flag":"lll"},
		success:function(data){
			progressClose();
			if(!data.result) {
				alert('起始日期不能晚于结束日期!');
			}else {
				progressClose();
				flow = data.list;
				$("#winfra").attr("src", "${ctx}/session/statistic/zyhxtable?type=month&zytype=0&startDate=${startDate}&endDate=${endDate}&r="+Math.random());
				executedraw();
			}
		}
	});

});

function canceldis() {
	$("input:radio[name=zyattr]").each(function(){
		$(this).removeAttr("disabled");
		$(this).next().removeAttr("style");
		$(this).removeAttr('checked');
	});
}
function tjbt(type){
	$('#tjfs').attr('value',type);
	begindraw();
}


//点击按键开始统计
function begindraw() {
	var s = $('#wstartDate').val();
	var e = $('#wendDate').val();
    if(!s || !e) {
        alert("时间不能为空!");
        return ;
    }
	if(e < s){
        alert('起始时间不能比结束时间晚!');
        return ;
	 }
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
	
	var zytype = $('input:radio[name=zytype]:checked').val();
	var zyattr = $('input:radio[name=zyattr]:checked').val();

	var tit1 = "";
	var tit2 = "";
	if(zytype == 0) {	//期刊
		$("#wz").html("期刊访问数据");
		tit1 = "期刊";
	}else if(zytype == 1) {	//听书
		$("#wz").html("听书访问数据");
		tit1 = "听书";
	}else if(zytype == 2) {	//文津诵读
		$("#wz").html("文津诵读访问数据");
		tit1 = "文津诵读";
	}else if(zytype == 3) {	//特色专题
		$("#wz").html("特色专题访问数据");
		tit1 = "特色专题";
	}else if(zytype == 4) {	//总访问量
		$("#wz").html("总访问量数据");
		tit1 = "总访问";
	}
	var flag = "lll";
	if(zyattr == 0) {	//浏览量
		tit2 = "浏览量";
		flag = "lll";
	}else if(zyattr == 1) {	//下载量
		tit2 = "下载量";
		flag = "xzl";
	}else if(zyattr == 2) {	//收藏量
		tit2 = "收藏量";
		flag = "scl";
	}
	tit =tit1+tit2;
	
	progressLoad();
	
	 $.ajax({
		url:"${ctx}/session/statistic/zyhxDataList",
		type:'POST',
		async:false,
		data: {"startDate":s, "endDate":e,"type":type,"zytype":zytype,"flag":flag},
		success:function(data){
			progressClose();
			
			if(!data.result) {
				alert('起始日期不能比结束日期晚!');
			}else {
				progressClose();
				flow = data.list;
				$("#winfra").attr("src", "${ctx}/session/statistic/zyhxtable?type="+type+"&zytype="+zytype+"&startDate="+s+"&endDate="+e+"&r="+Math.random());
				executedraw();
			}
		}
	}); 
	
}
function executedraw() {
    var data = flow;
	
	new iChart.Column2D({
		render : 'canvasDiv',
		data: data,
		title : tit,
		width : 1100,
		height : 400,
		label : {
			fontsize:11,
			textAlign:'right',
			textBaseline:'middle',
			rotate:-45,
			color : '#666666'
		},
		coordinate:{
			offsety:-25,
			background_color:'#fefefe',
			scale:[{
				 position:'left',	
				 start_scale:0,
				 //end_scale:10000,
				 //scale_space:2000,
				 listeners:{
					parseText:function(t,x,y){
						return {text:t}
					}
				}
			}
			]
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