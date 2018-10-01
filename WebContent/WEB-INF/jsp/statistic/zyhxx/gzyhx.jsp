<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../TagLib.jsp"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="../../Pub.jsp" %>
<meta http-equiv="content-type" content="text/html;charset=UTF-8" />

<script type="text/javascript">
	$(function(){
		var todaydate = new Date().toISOString().substring(0,10);
		//选择时间的一些限制 
		$("#startDate").datebox({
			onSelect:function(date){
				var startDate = $('#startDate').datebox('getValue');
				var endDate = $('#endDate').datebox('getValue');
				if(startDate >= todaydate) {
					alert("请选择今天之前的日期!");
					$('#startDate').datebox('setValue');
				}else if(endDate!="" && endDate < startDate){
			 		alert('起始时间不得晚于截止时间');
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
					alert('起始时间不得晚于截止时间');
			 		$('#endDate').datebox('setValue');
			 	}
			}
		});
		$('#startDate').datebox('setValue', "${formermonth}");
		$('#endDate').datebox('setValue', "${yesterday}");
		$(".datebox :text").attr("readonly","readonly");	
	});
	var tab;
	$(function(){
		tab = $('#dataTable').datagrid({
			//数据来源
			url:'${ctx}/session/statistic/gzyhxList',
			//斑马纹
			striped:true,
			//主键字段
			idField:"magazineid",
			//表单提交方式
			method:"post",
			//是否只能选择一行
			singleSelect:false,
			//是否分页
			pagination:true,
			//分页信息
			pageSize:10,
			//每页显示条目下拉菜单
			pageList:[10,20,30],
			//参数
			queryParams:{
				startDate:'${formermonth}',
				endDate:'${yesterday}'
			},
			rownumbers:true,//行号 
			//分页工具所在位置
			pagePosition:"bottom",
			//冻结的列
			frozenColumns:[[
			        //选择框
			]],
			columns:[[
			    {
					field : 'xx',
					checkbox : true
				},
				{
					field : 'type',
					title : '资源类别',
					width : 120,
					formatter : function(value, row, index) {
						if(value == 'r') {
							return "期刊";
						}else {
							return "听书";
						}
					}
				},
				
				{
					field : 'magazineid',
					title : '资源ID',
					width : 150
				},
				
				{
					field : 'title',
					title : '名称',
					width : 150
				},
				
				{
					field : 'browse',
					title : '浏览量',
					width : 80,
					sortable : true,
					formatter : function(value, row, index) {
						if(!value) {
							return "0";
						}
						return value;
					}
				},
				
				{
					field : 'collect',
					title : '收藏量',
					width : 80,
					sortable : true,
					formatter : function(value, row, index) {
						if(!value) {
							return "0";
						}
						return value;
					}
				},
				
				{
					field : 'down',
					title : '下载量',
					width : 80,
					sortable : true,
					formatter : function(value, row, index) {
						if(!value) {
							return "0";
						}
						return value;
					}
				},
				
				{
					field : 'xxxx',
					title : '操作',
					width : 100,
					formatter : function(value, row, index) {
							var titles = (row.title).replace(/ /g, '');
							return "<a href='#' onclick=checkopt('"+row.magazineid+"','"+row.type+"','"+titles+"')>查看资源画像</a>";
					}
				}
			]],
			toolbar : '#tbr',
			onLoadSuccess : function(data) {
				$('#dataTable').datagrid("clearSelections");
			}
		})
		
	})
	
	$(function(){
		$("#queryBtn").click(function(){
			var startDate = $("#startDate").datetimebox('getValue');
			var endDate = $("#endDate").datetimebox('getValue');
			if(!startDate || !endDate) {
				alert("时间不能为空!");
				return ;
			}
			tab.datagrid("load", {
				//名称
				"title" : $("#title").val(),
				//资源ID
				"magazineid" : $("#magazineid").val(),
				//资源类别
				"type" : $("#type").val(),
				'startDate':startDate,
				'endDate':endDate
			});
		})
		
		$("#resetBtn").click(function(){
			$("#title").val('');
			$("#magazineid").val('')
			$("#queryBtn").click();
		})
		
		$('#dataForm').window({
			title : 'Window',
			width : 500,
			height : 300,
			closed : true,
			minimizable : false,
			top : 50,
			onClose : function() {
				document.getElementById("winSrc").src = "${ctx}/images/ajax-loader.gif";
			},
			modal : true,
			maximizable : true
		});
	})
	
	//查看资源画像  r是期刊、l是听书
	function checkopt(id, type, title) {
		var startDate = $("#startDate").datetimebox('getValue');
		var endDate = $("#endDate").datetimebox('getValue');
		if(!startDate || !endDate) {
			alert("时间不能为空!");
			return ;
		}
		$('#dataTable').datagrid("clearSelections");
		openWin2("查看资源画像", "${ctx}/session/statistic/checkdetail?startDate="+startDate+"&endDate="+endDate+"&magazineid="+id+"&type="+type+"&title="+title, 1000, 600);
	}
	//导出到Excel
	function exportInfo() {
		var startDate = $("#startDate").datetimebox('getValue');
		var endDate = $("#endDate").datetimebox('getValue');
		if(!startDate || !endDate) {
			alert("时间不能为空!");
			return ;
		}
		document.getElementById("queryForm").action = "${ctx}/session/statistic/gzyhxExport";
		document.getElementById("queryForm").submit();
	}
</script>
</head>
<body onkeydown="if (event.keyCode == 13) {$('#queryBtn').click();} ">
<div>
	<form id="queryForm">
		<table style="width:80% ;">
			<tr>
				<td width="20%" align="right">名称：</td>
				<td width="20%"><input type="text" id="title" style="width:180px" name="title" /></td>
				
				<td width="20%" align="right">资源ID：</td>
				<td width="30%"><input type="text" id="magazineid" style="width:180px" name="magazineid" /></td>
			</tr>
			<tr>
				
				<td width="20%" align="right">资源类别：</td>
				<td width="20%">
					<select id="type" name="type" style="width:180px;">   
						<option></option>
    					<option value="r">期刊</option>   
    					<option value="l">听书</option>   
					</select>
				</td>
				
				<td width="20%" align="right">选择时间：</td>
				<td width="40%" >
					<input type="text" id="startDate" name="startDate" class="easyui-datebox" /> -- 
					<input type="text" id="endDate" name="endDate" class="easyui-datebox" />&nbsp;
				</td>
			
			</tr>
		</table>
		
		<br/>
		<div align="center">
			<input id="queryBtn" type="button" value="查询" />
			<input id="resetBtn" type="button" value="重置" />
		</div>
	</form>
	
	<br/>
	<table id="dataTable">
	</table>
	<div id="tbr">
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-redo',plain:true" onclick="exportInfo()">导出Excel</a>
	</div>
	
</div>

	<div id="dataForm" style="overflow:hidden;font-size:10px;display:none;" title="查看" >
		<iframe id="winSrc" frameborder="0" width="100%" height="100%" src="${ctx}/images/ajax-loader.gif"></iframe>
	</div>

</body>
</html>