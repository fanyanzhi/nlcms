<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../TagLib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="../../Pub.jsp" %>
<script type="text/javascript">
	var tab;
	var p1 = "<fmt:formatDate value='${startDate}' pattern='yyyy-MM-dd'/>";
	var p2 = "<fmt:formatDate value='${endDate}' pattern='yyyy-MM-dd'/>";
	var flag = $('#method', window.parent.document).val();
	
	$(function(){
		
		tab = $('#dataTable').datagrid({
			//数据来源
			url:'${ctx}/session/statistic/tableList',
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
				type:"${type}",
				flag:flag
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
					field : 'monthdate',
					title : '时间',
					width : 220
				},
				
				{
					field : 'iosnum',
					title : 'Iphone',
					width : 80
				},
				
				{
					field : 'androidnum',
					title : 'Android',
					width : 80
				}
			]],
			toolbar : '#tbr',
			onLoadSuccess : function(data) {
				$('#dataTable').datagrid("clearSelections");
				resize()
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
		if(p1=='' || p1=='') {
			alert("时间不能为空!");
			return;
		}
		$("#sdt").val(p1);
		$("#edt").val(p2);
		$("#type").val("${type}");
		$("#flag").val(flag); 
		document.getElementById("tempForm").action = "${ctx}/session/statistic/apptjExport";
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
		<input type="hidden" name="type" id="type">
		<input type="hidden" name="flag" id="flag">
	</form>
</div>
</body>

</html>