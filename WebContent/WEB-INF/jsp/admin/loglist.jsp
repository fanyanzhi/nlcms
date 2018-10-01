<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../TagLib.jsp"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="../Pub.jsp" %>
<meta http-equiv="content-type" content="text/html;charset=UTF-8" />

<script type="text/javascript">
	var tab;
	var gvar = 1;
	
	$(function(){
		//结束时间大于开始时间
		checkEndDate2('startDate','endDate');
		//时间框禁止输入
		$(".datebox :text").attr("readonly","readonly");
		
		tab = $('#dataTable').datagrid({
			//数据来源
			url:'${ctx}/session/adminlog/list',
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
					field : 'id',
					checkbox : true
				},
				
				{
					field : 'username',
					title : '用户名',
					width : 150
				},
				{
					field : 'role',
					title : '角色',
					width : 100,
					formatter : function(value, row, index) {
						if(value == 0) {
							return '超管';
						}else if(value == 1) {
							return '编辑';
						}else {
							return '浏览';
						}
					}
				},
				{
					field : 'ip',
					title : '登录IP',
					width : 150
				},
				{
					field : 'time',
					title : '登录时间',
					width : 120
				},
				
				{
					field : 'operate',
					title : '维护内容',
					width : 500
				},
				
				{
					field : 'operatetime',
					title : '操作时间',
					width : 120
				}
				
				<c:if test="${LoginObj.role == 0}">
				,
				{
					field : 'xxxx',
					title : '操作',
					width : 100,
					formatter : function(value, row, index) {
						return "<a href='#' onclick='deleteInfo("+row.id+")'>删除</a>";
					}
				}
				</c:if>
			]],
			toolbar : '#tbr',
			onLoadSuccess : function(data) {
				$('#dataTable').datagrid("clearSelections");
				gvar = 1;
			}
		})
		
	})
	
	function insertLog(message) {
		var loginTime = "<fmt:formatDate value='${loginTime}' pattern='yyyy-MM-dd HH:mm:ss'/>";
		var loginIp = "${loginIp}";
		var username = "${LoginObj.username}";
		var role = "${LoginObj.role}";
		
		$.ajax({
			url:"${ctx}/session/adminlog/insertLog",
			type:'POST',
			data: {"username":username, "role":role, "ip":loginIp, "time":loginTime, "operate": message},
			success:function(data){
				} 
			});
	}
	
	function deleteInfo(id) {
		if(confirm('确定要删除？')){
			$.ajax({
			url:"${ctx}/session/adminlog/delete/" + id +"?r=" + Math.random(),
			type:'GET',
			success:function(data){
					insertLog("删除管理员日志");
					$('#dataTable').datagrid("reload");
				} 
			});
		}else {
			$('#dataTable').datagrid("clearSelections");
		}
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
	
	function deleteAll() {
		var rows = $('#dataTable').datagrid("getSelections");
		if(rows.length == 0) {
			alert('请先选择数据!');
			return ;
		}
		
		if(confirm('确定要删除？')) {
			
			var idArr = [];
			for(var i = 0; i < rows.length; i++) {
				idArr.push(rows[i].id);
			}
			
			$.ajax({
				url:"${ctx}/session/adminlog/deleteAll?r=" + Math.random(),
				data: "ids=" + idArr.join(),
				type:'POST',
				success:function(data){
						insertLog("删除管理员日志");
						$('#dataTable').datagrid("reload");
					}
				});
		}else {
			$('#dataTable').datagrid("clearSelections");
		}
	}
	
	//导出到Excel
	function exportInfo() {
		document.getElementById("tempForm").action = "${ctx}/session/adminlog/export";
		document.getElementById("tempForm").submit();
	}
	
	$(function(){
		$("#queryBtn").click(function(){
			var startDate = $("#startDate").datebox("getValue");
			var endDate = $("#endDate").datebox("getValue");
			
			
			tab.datagrid("load", {
				//管理员姓名
				"username" : $("#username").val(),
				//权限
				"role" : $("#role").val(),
				//起始时间
				"startDate" : startDate,
				//结束时间
				"endDate" : endDate
			});
		})
		
		$("#resetBtn").click(function(){
			$(':input').not(':button, :submit, :reset, :hidden').val('').removeAttr('checked').removeAttr('selected');
			$("#startDate").datebox("setValue");
			$("#endDate").datebox("setValue");
			$("#queryBtn").click();
		})
		
		
	})
	
</script>
</head>
<body onkeydown="if (event.keyCode == 13) {$('#queryBtn').click();} ">
<div>
	<form id="queryForm">
		<table width="80%" >
			<tr>
				<td width="20%" align="right">用户名：</td>
				<td width="30%"><input type="text" id="username" style="width:210px" name="username" /></td>
				
				<td width="20%" align="right">角色：</td>
				<td width="30%">
					<select id="role" name="role" style="width:210px;">   
    					<option></option>
    					<option value="0">超管</option>   
    					<option value="1">编辑</option>   
    					<option value="2">浏览</option>   
					</select>
				</td>
			</tr>
			
			<tr>
				<td width="20%" align="right">登录时间：</td>
				<td colspan="3">
					<input type="text" id="startDate" name="startDate" class="easyui-datebox" /> -- 
					<input type="text" id="endDate" name="endDate" class="easyui-datebox" />
				</td>
			</tr>
			
		</table>
		
		<div align="center">
				<input id="queryBtn" type="button" value="查询" />
				<input id="resetBtn" type="button" value="重置" />
			</div>
	</form>
	
	<br/>
	<table id="dataTable">
	</table>
	<div id="tbr">
	<c:if test="${LoginObj.role == 0}">
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-ok',plain:true" onclick="selectAll()">全选</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="deleteAll()">删除</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-redo',plain:true" onclick="exportInfo()">导出Excel</a>
	</c:if>
	
	<c:if test="${LoginObj.role != 0}">
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-redo',plain:true" onclick="exportInfo()">导出Excel</a>
	</c:if>
	</div>
	
	<form id="tempForm"></form>
</div>
</body>
</html>