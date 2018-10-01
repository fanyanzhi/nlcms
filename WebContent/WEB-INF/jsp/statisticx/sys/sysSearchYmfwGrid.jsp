<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../TagLib.jsp"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="../../Pub.jsp" %>
<meta http-equiv="content-type" content="text/html;charset=UTF-8" />

<script type="text/javascript">
	var tab;
	var p1 = "<fmt:formatDate value='${startDate}' pattern='yyyy-MM-dd'/>";
	var p2 = "<fmt:formatDate value='${endDate}' pattern='yyyy-MM-dd'/>";
	
	$(function(){
		
		tab = $('#dataTable').datagrid({
			//数据来源
			url:'${ctx}/session/statisticx/sysSearchYmfwList',
			//斑马纹
			striped:true,
			//主键字段
			//idField:"xxx",
			//表单提交方式
			method:"post",
			//是否只能选择一行
			singleSelect:false,
			showFooter: true,
			//是否分页
			pagination:true,
			//分页信息
			pageSize:10,
			//每页显示条目下拉菜单
			pageList:[10,20],
			//参数
			queryParams:{
				startDate:p1,
				endDate:p2,
				status:"${status}"
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
					field : 'time',
					title : '时间',
					width : 220
				},
				
				{
					field : 'pv',
					title : 'pv',
					width : 80
				},
				
				{
					field : 'uv',
					title : 'uv',
					width : 80
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
		$("#sdt").val(p1);
		$("#edt").val(p2);
		$("#sts").val("${status}");
		document.getElementById("tempForm").action = "${ctx}/session/statisticx/sysYmfwExport";
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
		<input type="hidden" name="startDate" id="sdt">
		<input type="hidden" name="endDate" id="edt">
		<input type="hidden" name="status" id="sts">
	</form>
</div>
</body>
</html>