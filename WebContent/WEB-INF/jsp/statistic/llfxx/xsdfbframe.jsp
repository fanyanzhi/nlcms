<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../TagLib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="../../Pub.jsp" %>
<script type="text/javascript" src="${ctx}/ichartjs/ichart.1.2.min.js"></script>
</head>
<body>
<div>
<table border="0" width="800">
		<tr>
			<td style="font-family:黑体">小时段分布</td>
			<td align="right">选择时间：
				<input type="text" id="calcdate" name="calcdate" class="easyui-datebox" />
				&nbsp;<input type="button" onclick="btfunc()" value="统计"/>
			</td>
		</tr>
</table>
	<br/>
	<div>
	&nbsp;<input type="button" value="导出到Excel" onclick="exportInfo()">
		<div>
			<iframe id="winfra" src="about:blank" width="50%" frameborder=0 marginheight=0></iframe>
		</div>
	</div>

	<div id="canvasDiv"></div>
</div>

<form id="tempForm1">
	<input type="hidden" name="calcdate" id="cdt">
</form>
	
<script>
var flow=[];
var flow2=[];

$(function(){
	$("#winfra").load(function () {
	    var mainheight = $(this).contents().find("body").height() + 30;
	    $(this).height(mainheight);
	});
	
	$('#calcdate').datebox('setValue', "${todaydate}");
	
	progressLoad();
	
	$.ajax({
		url:"${ctx}/session/statistic/qdcsycList",	//含重复的，启动次数
		type:'POST',
		async:false,
		data: {"calcdate":"${todaydate}"},
		success:function(data){
			flow = data;
			
			$.ajax({
				url:"${ctx}/session/statistic/qdcsqcList",	//去重的，用户数量
				type:'POST',
				async:false,
				data: {"calcdate":"${todaydate}"},
				success:function(data){
					progressClose();
					flow2 = data;
					executeDraw();
					$("#winfra").attr("src", "${ctx}/session/statistic/xsdfbTable?calcdate=${todaydate}&r="+Math.random());
				}
			});
		}
	});

});


function btfunc() {
	var calcdate = $('#calcdate').datebox('getValue');
	
	progressLoad();
	
	$.ajax({
		url:"${ctx}/session/statistic/qdcsycList",	//含重复的，启动次数
		type:'POST',
		async:false,
		data: {"calcdate":calcdate},
		success:function(data){
			flow = data;
			
			$.ajax({
				url:"${ctx}/session/statistic/qdcsqcList", //去重的，用户数量
				type:'POST',
				async:false,
				data: {"calcdate":calcdate},
				success:function(data){
					progressClose();
					flow2 = data;
					executeDraw();
					$("#winfra").attr("src", "${ctx}/session/statistic/xsdfbTable?calcdate="+calcdate+"&r="+Math.random());
				}
			});
		}
	});
}


function executeDraw() {
	var data = [
	         	{
	         		name : '启动次数',
	         		value:flow,
	         		color:'#0d8ecf',
	         		line_width:1
	         	},
	         	{
	         		name : '用户数量',
	         		value:flow2,
	         		color:'#ec4646',
	         		line_width:1
	         	}
	         ];
    
	var labels = ["00","","","03","","","06","","","09","","","12","","","15","","","18","","","21","","","24"];
	
	var chart = new iChart.LineBasic2D({
		render : 'canvasDiv',
		data: data,
		align:'center',
		width : 1100,
		height : 300,
		tip:{	//提示框
			enable:true,
			shadow:true,
			listeners:{
				 //tip:提示框对象、name:数据名称、value:数据值、text:当前文本、i:数据点的索引
				parseText:function(tip,name,value,text,i){
					return name+"访问量:"+value;
				}
			}
		},
		crosshair:{	//十字线
			//enable:true,
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
			//height:260,
			striped_factor : 0.18,
			//grid_color:'#4e4e4e',
			axis:{
				color:'#252525',
				width:[0,0,4,0]
			},
			scale:[{
				 position:'left',	
				 start_scale:0,
				// end_scale:500,
				// scale_space:100,
				 scale_size:1,
				 scale_enable : false,
				 label : {color:'#9d987a',font : '微软雅黑',fontsize:11,fontweight:600}
				// scale_color:'#9f9f9f'
			},{
				 position:'bottom',	
				 label : {color:'#9d987a',font : '微软雅黑',fontsize:11,fontweight:600},
				 scale_enable : false,
				 labels:labels
			}]
		}
	});
	
//开始画图
chart.draw();
}

function progressLoad(){  
	$("#winfra").attr("src", "about:blank");
    $("<div class=\"datagrid-mask\" style=\"position:absolute;z-index: 9999;\"></div>").css({display:"block",width:"100%",height:"1000px"}).appendTo("body");  
    $("<div class=\"datagrid-mask-msg\" style=\"position:absolute;z-index: 9999;\"></div>").html("正在处理，请稍候。。。").appendTo("body").css({display:"block",left:($(document.body).outerWidth(true) - 190) / 2,top:($(window).height() - 45) / 2});  
}

function progressClose(){
	$(".datagrid-mask").remove();  
	$(".datagrid-mask-msg").remove();
}

function exportInfo() {
	var calcdate = $('#calcdate').datebox('getValue');
	$("#cdt").val(calcdate);
	
	document.getElementById("tempForm1").action = "${ctx}/session/statistic/xsdfbExport";	//小时段分布
	document.getElementById("tempForm1").submit();
}
</script>
</body>
</html>