<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../TagLib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<script type="text/javascript" src="${ctx}/js/easyui/jquery.min.js"></script>
<script type="text/javascript" src="${ctx}/ichartjs/ichart.1.2.min.js"></script>
</head>
<body>
	<div id="canvasDiv"></div>
	<div id="canvasDiv2"></div>
	
<script>
$(function(){
	var flow=[];
	
	$.ajax({
		url:"${ctx}/session/statistic/sstjXzyhsjr",
		type:'POST',
		async:false,
		success:function(data){
			flow = data;
		}
	});
	
	var data = [
	         	{
	         		name : 'PV',
	         		value:flow,
	         		color:'#ec4646',
	         		line_width:1
	         	}
	         ];
    
	var labels = ["00","","","03","","","06","","","09","","","12","","","15","","","18","","","21","","","24"];
	
	var chart = new iChart.LineBasic2D({
		render : 'canvasDiv',
		data: data,
		align:'center',
		width : 800,
		height : 300,
		tip:{	//提示框
			enable:true,
			shadow:true,
			listeners:{
				 //tip:提示框对象、name:数据名称、value:数据值、text:当前文本、i:数据点的索引
				parseText:function(tip,name,value,text,i){
					return "</span><span style='color:#005268;font-size:20px;'>"+value+"</span>";
				}
			}
		},
		crosshair:{	//十字线
			//enable:true,
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
				// end_scale:1000,
				// scale_space:200,
				// scale_size:1,
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
	
	var num = 0;
	
	$.ajax({
		url:"${ctx}/session/statistic/sstjXzyhsjr2",
		type:'POST',
		async:false,
		success:function(data){
			num = data;
		}
	});
	
	//利用自定义组件构造左侧说明文本
	chart.plugin(new iChart.Custom({
			drawFn:function(){
				//计算位置
				var coo = chart.getCoordinate(),
					x = coo.get('originx'),
					y = coo.get('originy'),
					w = coo.width,
					h = coo.height;
				//在左上侧的位置，渲染一个单位的文字
				chart.target.textAlign('start')
				.textBaseline('bottom')
				.textFont('600 11px 微软雅黑')
				.fillText('新增用户(今日):'+num,x-40,y-12,false,'#9d987a')
				.textBaseline('top')
				.fillText('(时间)',x+w+12,y+h+10,false,'#9d987a');
				
			}
	}));
//开始画图
chart.draw();

// ---------------------------------------------------------------

var flow2=[];
	
	
	$.ajax({
		url:"${ctx}/session/statistic/sstjQdcsjr",
		type:'POST',
		async:false,
		success:function(data){
			flow2 = data;
		}
	});
	
	var data2 = [
	         	{
	         		name : 'PV',
	         		value:flow2,
	         		color:'#ec4646',
	         		line_width:1
	         	}
	];

var chart2 = new iChart.LineBasic2D({
	render : 'canvasDiv2',
	data: data2,
	align:'center',
	width : 800,
	height : 300,
	tip:{	//提示框
		enable:true,
		shadow:true,
		listeners:{
			 //tip:提示框对象、name:数据名称、value:数据值、text:当前文本、i:数据点的索引
			parseText:function(tip,name,value,text,i){
				return "</span><span style='color:#005268;font-size:20px;'>"+value+"</span>";
			}
		}
	},
	crosshair:{	//十字线
		//enable:true,
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
			// end_scale:5000,
			 //scale_space:1000,
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

var num2 = 0;

$.ajax({
	url:"${ctx}/session/statistic/sstjQdcsjr2",
	type:'POST',
	async:false,
	success:function(data){
		num2 = data;
	}
});

//利用自定义组件构造左侧说明文本
chart2.plugin(new iChart.Custom({
		drawFn:function(){
			//计算位置
			var coo = chart2.getCoordinate(),
				x = coo.get('originx'),
				y = coo.get('originy'),
				w = coo.width,
				h = coo.height;
			//在左上侧的位置，渲染一个单位的文字
			chart2.target.textAlign('start')
			.textBaseline('bottom')
			.textFont('600 11px 微软雅黑')
			.fillText('启动次数(今日):'+num2,x-40,y-12,false,'#9d987a')
			.textBaseline('top')
			.fillText('(时间)',x+w+12,y+h+10,false,'#9d987a');
			
		}
}));

//开始画图
chart2.draw();

});
</script>
</body>
</html>