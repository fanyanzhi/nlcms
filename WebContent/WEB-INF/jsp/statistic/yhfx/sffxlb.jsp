<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../TagLib.jsp"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="../../Pub.jsp" %>
<meta http-equiv="content-type" content="text/html;charset=UTF-8" />

<script type="text/javascript">
	var tab;
	var gvar = 1;
	var p1 = "<fmt:formatDate value='${startDate}' pattern='yyyy-MM-dd'/>";
	var p2 = "<fmt:formatDate value='${endDate}' pattern='yyyy-MM-dd'/>";
	
	$(function(){
		
		tab = $('#dataTable').datagrid({
			//数据来源
			url:'${ctx}/session/statistic/sffxtableList',
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
				startDate:p1,
				endDate:p2,
				type:"${type}"
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
					field : 'month',
					title : '时间',
					width : 80
				},
				{
					field : 'qqfriend',
					title : 'QQ好友',
					width : 80
				},
				{
					field : 'qqzone',
					title : 'QQ空间',
					width : 80
				},
				{
					field : 'wxfriend',
					title : '微信好友',
					width : 80
				},
				{
					field : 'wxquanzi',
					title : '微信朋友圈',
					width : 80
				},
				{
					field : 'wxfavorite',
					title : '微信收藏',
					width : 80
				},
				{
					field : 'sinaweibo',
					title : '新浪微博',
					width : 80
				}
				
			]],
			toolbar : '#tbr',
			onLoadSuccess : function(data) {
				$('#dataTable').datagrid("clearSelections");
				gvar = 1;
				 resize()
			},
			onLoadError:function(data){
				//alert(data);
			}
		})
		
		/* setTimeout(function(){
			$('#dataTable').datagrid("load", {
				startDate:p1,
				endDate:p2
			}) 
		}, 200);
		 */
	})
	
	function resize(){ 
		//取到页面的高度 
		var bodyH = $("body").height(); 
		window.parent.document.getElementById("winfra").height=bodyH; 
	}  
	
	function selectAll() {
		if(gvar == 1) {
			$('#dataTable').datagrid('selectAll');
			gvar = 2;		
		}else if(gvar == 2) {
			$('#dataTable').datagrid('unselectAll');
			gvar = 1;
		}
	}
	
	
	//导出到Excel
	function exportInfo() {
		$("#sdt").val(p1);
		$("#edt").val(p2);
		document.getElementById("tempForm").action = "${ctx}/session/statistic/sffxExport";
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
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-ok',plain:true" onclick="selectAll()">全选</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-redo',plain:true" onclick="exportInfo()">导出Excel</a>
	</div>
	
	<form id="tempForm">
		<input type="hidden" name="startDate" id="sdt">
		<input type="hidden" name="endDate" id="edt">
		<input type="hidden" name="type" id="type" value="${type }">
	</form>
</div>

</body>
</html>