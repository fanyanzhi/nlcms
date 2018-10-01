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
	$(function(){
		tab = $('#dataTable').datagrid({
			//数据来源
			url:'${ctx}/session/statistic/gzyhx/ydyhList',
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
				type:"${type}",
				magazineid:"${magazineid }"
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
					field : 'userName',
					title : '用户ID',
					width : 120
				},
				{
					field : 'address',
					title : '用户IP',
					width : 100
				},
				{
					field : 'xxxx',
					title : '操作',
					width : 100,
					formatter : function(value, row, index) {
							return "<a href='#' onclick=getYdxq('"+row.userName+"')>阅读详情</a>";
					}
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
		document.getElementById("tempForm").action = "${ctx}/session/statistic/gzyhx/ydyhExport";
		document.getElementById("tempForm").submit();
	}
	//查看资源画像  r是期刊、l是听书
	function getYdxq(username) {
		$('#dataTable').datagrid("clearSelections");
		 window.open("${ctx}/session/statistic/gzyhx/ydqk?startDate=${startDate}&endDate=${endDate}&username="+username,"_blank", "height=500,width=600, toolbar=no , menubar=no, scrollbars=no,resizable=no, location=no, status=no,left=200,top=100")
		//window.parent.parent.openWin2("阅读情况", "${ctx}/session/statistic/gzyhx/ydqk?startDate=${startDate}&endDate=${endDate}&username="+username, 1000, 600);
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
		<input type="hidden" name="type" id="type"  value="${type }">
		<input type="hidden" name="magazineid" id="magazineid"  value="${magazineid }">
	</form>
</div>
<div id="dataForm" style="overflow:hidden;font-size:10px;display:none;" title="查看" >
	<iframe id="winSrc" frameborder="0" width="100%" height="100%" src="${ctx}/images/ajax-loader.gif"></iframe>
</div>
</body>
</html>