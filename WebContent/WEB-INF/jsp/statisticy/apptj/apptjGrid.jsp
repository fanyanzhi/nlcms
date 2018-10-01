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
			url:'${ctx}/session/statisticy/apptjList',
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
		document.getElementById("tempForm").action = "${ctx}/session/statisticy/apptjExport";
		document.getElementById("tempForm").submit();
	}

</script>
</head>
<body>
<div>
	<br/>
	<table id="dataTable">
		<thead>
			<tr>
				<th></th>
				<th colspan="2">新增用户</th>
				<th colspan="2">启动次数</th>
				<th colspan="2">下载量</th>
				<th colspan="2">更新量</th>
				<th colspan="2">使用时长</th>
			</tr>
			<tr>
				<th data-options="field:'time',width:200,align:'center'">日期</th>
				<th data-options="field:'iosnew',width:100,align:'center'">IOS</th>
				<th data-options="field:'andnew',width:100,align:'center'">Android</th>
				<th data-options="field:'iosqd',width:100,align:'center'">IOS</th>
				<th data-options="field:'andqd',width:100,align:'center'">Android</th>
				<th data-options="field:'iosxzl',width:100,align:'center'">IOS</th>
				<th data-options="field:'andxzl',width:100,align:'center'">Android</th>
				<th data-options="field:'iosgx',width:100,align:'center'">IOS</th>
				<th data-options="field:'andgx',width:100,align:'center'">Android</th>
				<th data-options="field:'iossc',width:100,align:'center'">IOS</th>
				<th data-options="field:'andsc',width:100,align:'center'">Android</th>
			</tr>
		</thead>
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