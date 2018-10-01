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
		tab = $('#dataTable').datagrid({
			//数据来源
			url:'${ctx}/session/admin/list',
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
					field : 'staffname',
					title : '管理员姓名',
					width : 100
				},
				{
					field : 'staffdept',
					title : '所属部门',
					width : 100
				},
				{
					field : 'staffphone',
					title : '办公电话',
					width : 100
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
						}else if(value == 2){
							return '浏览';
						}
					}
				},
				{
					field : 'module',
					title : '管理模块',
					width : 370
				}
				/* {
					field : 'password',
					title : '密码',
					width : 100
				}, */
				
				<c:if test="${LoginObj.role == 0}">
				,
				{
					field : 'xxxx',
					title : '操作',
					width : 100,
					formatter : function(value, row, index) {
						return '<a href="#" onclick="updateInfo('+row.id+')">修改</a>' + ' | ' + '<a href="#" onclick=deleteInfo('+row.id+','+'"'+row.username+'")>删除</a>';
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
	
	//修改
	function updateInfo(id) {
		$('#dataTable').datagrid("clearSelections");
		openWin2("修改", "${ctx}/session/admin/updateView?id=" + id, 700, 350);
	}
	
	function deleteInfo(id, username) {
		
		if(confirm('确定要删除？')){
			$.ajax({
			url:"${ctx}/session/admin/delete/" + id +"?r=" + Math.random(),
			type:'GET',
			success:function(data){
					insertLog("删除管理员 "+ username);
					$('#dataTable').datagrid("reload");
				} 
			});
		}else {
			$('#dataTable').datagrid("clearSelections");
		}
	}
	
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
			var nameArr = [];
			for(var i = 0; i < rows.length; i++) {
				idArr.push(rows[i].id);
				nameArr.push(rows[i].username);
			}
			
			$.ajax({
				url:"${ctx}/session/admin/deleteAll?r=" + Math.random(),
				data: "ids=" + idArr.join(),
				type:'POST',
				success:function(data){
						insertLog("删除管理员 " + nameArr.join());
						$('#dataTable').datagrid("reload");
					}
				});
		}else {
			$('#dataTable').datagrid("clearSelections");
		}
	}
	
	$(function(){
		$("#queryBtn").click(function(){
			tab.datagrid("load", {
				//管理员姓名
				"username" : $("#username").val(),
				//用户名
				"staffname" : $("#staffname").val(),
				//所属部门
				"staffdept" : $("#staffdept").val(),
				//办公电话
				"staffphone" : $("#staffphone").val(),
				//权限
				"role" : $("#role").val()
			});
		})
		
		$("#resetBtn").click(function(){
			$(':input').not(':button, :submit, :reset, :hidden').val('').removeAttr('checked').removeAttr('selected');
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
</script>
</head>
<body onkeydown="if (event.keyCode == 13) {$('#queryBtn').click();} ">
<div>
	<form id="queryForm">
		<table width="80%" >
			<tr>
				<td width="20%" align="right">用户名：</td>
				<td width="30%"><input type="text" id="username" style="width:210px" name="username" /></td>
				
				<td width="20%" align="right">管理员姓名：</td>
				<td width="30%"><input type="text" id="staffname" style="width:210px" name="staffname" /></td>
			</tr>
			
			<tr>
				<td width="20%" align="right">所属部门：</td>
				<td width="30%"><input type="text" id="staffdept" style="width:210px" name="staffdept" /></td>
				
				<td width="20%" align="right">办公电话：</td>
				<td width="30%"><input type="text" id="staffphone" style="width:210px" name="staffphone" /></td>
			</tr>
			
			<tr>
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
		</table>
		
		<div align="center">
				<input id="queryBtn" type="button" value="查询" />
				<input id="resetBtn" type="button" value="重置" />
			</div>
	</form>
	
	<br/>
	<table id="dataTable">
	</table>
	
	<!-- 超管才能对admin操作 -->
	<c:if test="${LoginObj.role == 0}">
	<div id="tbr">
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-ok',plain:true" onclick="selectAll()">全选</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="deleteAll()">删除</a>
	</div>
	</c:if>
</div>

<div id="dataForm" style="overflow:hidden;font-size:10px;display:none;" title="编辑" >
	<iframe id="winSrc" frameborder="0" width="100%" height="100%" src="${ctx}/images/ajax-loader.gif"></iframe>
</div>
</body>
</html>