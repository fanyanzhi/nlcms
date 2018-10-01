<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../TagLib.jsp"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>阅读详情</title>
<%@ include file="../../Pub.jsp" %>
<meta http-equiv="content-type" content="text/html;charset=UTF-8" />

<script type="text/javascript">
	var tab;
	var gvar = 1;
	
	$(function(){
		
		tab = $('#dataTable').datagrid({
			//数据来源
			url:'${ctx}/session/statistic/gzyhx/ydqkList',
			//斑马纹
			striped:true,
			//主键字段
			//idField:"xxx",
			//表单提交方式
			method:"post",
			//是否只能选择一行
			singleSelect:false,
			//是否分页
			pagination:true,
			//分页信息
			pageSize:10,
			//每页显示条目下拉菜单
			pageList:[10,20],
			//参数
			queryParams:{
				startDate:'${startDate}',
				endDate:'${endDate}',
				username:"${username}"
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
					field : 'zyID',
					title : '资源ID',
					width : 120
				},
				{
					field : 'zymc',
					title : '资源名称',
					width : 100
				},{
					field : 'ydcs',
					title : '阅读次数',
					width : 100
				} ,{
					field : 'ydsc',
					title : '阅读时长（分）',
					width : 100
				}
				
			]],
			toolbar : '#tbr',
			onLoadSuccess : function(data) {
				$('#dataTable').datagrid("clearSelections");
				gvar = 1;
				resize();
			}
		})
		
		
	})
	 function resize(){ 
		//取到页面的高度 
		var bodyH = $("body").height(); 
		window.parent.document.getElementById("winfra").height=bodyH; 
	}  
	//导出到Excel
	function exportInfo() {
		document.getElementById("tempForm").action = "${ctx}/session/statistic/gzyhx/ydxqExport";
		document.getElementById("tempForm").submit();
	}

</script>
</head>
<body>
<div>
	<br/>
	<table id="dataTable">
	</table>
	<div id="tbr">
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-redo',plain:true" onclick="exportInfo()">导出Excel</a>
	</div>
	<form id="tempForm">
		<input type="hidden" name="startDate" id="sdt" value="${startDate}">
		<input type="hidden" name="endDate" id="edt"  value="${endDate}">
		<input type="hidden" name="username" id="username"  value="${username }">
	</form>
</div>

</body>
</html>