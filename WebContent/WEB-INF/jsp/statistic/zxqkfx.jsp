<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../TagLib.jsp"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="../Pub.jsp" %>
<meta http-equiv="content-type" content="text/html;charset=UTF-8" />

<script type="text/javascript">
	var tab;
	
	$(function(){
		
		tab = $('#dataTable').datagrid({
			//数据来源
			url:'${ctx}/session/statistic/zxqkfxList',
			//斑马纹
			striped:true,
			//主键字段
			idField:"id",
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
					field : 'loginaccount',
					title : '用户ID',
					width : 120
				},
				
				{
					field : 'time',
					title : '来访时间',
					width : 120
				},
				
				{
					field : 'location',
					title : '用户地域',
					width : 80
				},
				
				{
					field : 'address',
					title : '用户IP',
					width : 120
				}
				
			]],
			toolbar : '#tbr',
			onLoadSuccess : function(data) {
				$('#dataTable').datagrid("clearSelections");
			}
		})
		
	})
	
	//导出到Excel
	function exportInfo() {
		document.getElementById("tempForm").action = "${ctx}/session/statistic/zxqkfxExport";
		document.getElementById("tempForm").submit();
	}
	
</script>
</head>
<body>
<div>
	<br/>
	<table id="dataTable"></table>
</div>

<div id="tbr">
	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-redo',plain:true" onclick="exportInfo()">导出Excel</a>
</div>

<form id="tempForm"></form>
</body>
</html>