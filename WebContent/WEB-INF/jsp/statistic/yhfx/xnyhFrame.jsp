<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../TagLib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" type="text/css"	href="${ctx}/js/easyui/themes/default/easyui.css" />
<link rel="stylesheet" type="text/css"	href="${ctx}/js/easyui/themes/icon.css" />
<script type="text/javascript" src="${ctx}/js/jquery-1.8.3.js"></script>
<script type="text/javascript"	src="${ctx}/js/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript"	src="${ctx}/js/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="${ctx}/ichartjs/ichart.1.2.min.js"></script>
<script type="text/javascript" src="${ctx}/js/myweek/WdatePicker.js"></script>
</head>
<body>
	<div>
		<div>
			<form>
				<font style="color: red">虚拟用户数量</font>
				<!-- <hr style="width:90%;" align="left"/> -->
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
				</table>
			</form>
		</div>
		<br />
		<div>
			<strong>虚拟用户数据</strong> (<span id="tjfs" value='month'>按月统计</span>)
			<div>
				<iframe id="winfra" src="about:blank" width="100%" height="400px"
					frameborder=0 marginheight=0></iframe>
			</div>
		</div>
		<br/><br/>
		<div id="canvasDiv"></div>
	</div>
	<script>
var flow = [];
var labels = [];
$(function(){
	progressLoad();
	
	$.ajax({
		url:"${ctx}/session/statistic/yhslPic3List",
		type:'POST',
		async:false,
		data: {"startDate":"${startDate}", "endDate":"${endDate}","type":"month"},
		success:function(data){
			progressClose();
			if(!data.result) {
				alert('起始日期不能比结束日期晚!');
			}else {
				flow = data.flow;
				labels = data.labels;
				executedraw();
				$("#winfra").attr("src", "${ctx}/session/statistic/xnyhtable?type=month&startDate=${startDate}&endDate=${endDate}&r="+Math.random());
			}
		}
	});
	
	
})
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
	if(s>e){
		alert('起始日期不能比结束日期晚!');
		return;
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
	progressLoad();
	
	$.ajax({
		url:"${ctx}/session/statistic/yhslPic3List",
		type:'POST',
		async:false,
		data: {"startDate":s, "endDate":e,"type":type},
		success:function(data){
			progressClose();
			if(!data.result) {
				alert('起始日期不能比结束日期晚!');
			}else {
				flow = data.flow;
				labels = data.labels;
				executedraw();
				$("#winfra").attr("src", "${ctx}/session/statistic/xnyhtable?type="+type+"&startDate="+s+"&endDate="+e+"&r="+Math.random());
			}
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
	
	var chart = new iChart.LineBasic2D({
		render : 'canvasDiv',
		data: data,
		align:'center',
		width : 1100,
		height : 350,
		padding : 50,
		tip:{	//提示框
			enable:true,
			shadow:true,
			listeners:{
				 //tip:提示框对象、name:数据名称、value:数据值、text:当前文本、i:数据点的索引
				parseText:function(tip,name,value,text,i){
					return "<span style='color:#005268;font-size:12px;'>"+labels[i]+"月新增虚拟用户数量:"+
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
			//offsety:-30,
			//gridlinesVisible:false,
			striped_factor : 0.18,
			axis:{
				color:'#252525',
				width:[0,0,4,0]
			},
			scale:[{
				 position:'left',	
				 start_scale:0,
				 //end_scale:5000,
				 //scale_space:1000,
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
    $("<div class=\"datagrid-mask\" style=\"position:absolute;z-index: 9999;\"></div>").css({display:"block",width:"100%",height:"1000px"}).appendTo("body");  
    $("<div class=\"datagrid-mask-msg\" style=\"position:absolute;z-index: 9999;\"></div>").html("正在处理，请稍候。。。").appendTo("body").css({display:"block",left:($(document.body).outerWidth(true) - 190)/2,top:($(window).height() - 45)/2});  
}

function progressClose(){
	$(".datagrid-mask").remove();  
	$(".datagrid-mask-msg").remove();
}
</script>
</body>
</html>