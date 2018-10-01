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
		checkEndDate2('pstartDate','pendDate');

		//时间框禁止输入
		$(".datebox :text").attr("readonly","readonly");
		
		tab = $('#dataTable').datagrid({
			//数据来源
			url:'${ctx}/session/user/list',
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
					field : 'rdrolecode',
					title : '读者类别',
					width : 120,
					formatter : function(value, row, index) {
						if(value == "0000") {
							return "虚拟";
						}else if(value == "JS0001") {
							return "实名";
						}else {
							return "物理卡";
						}
					}
				},
				
				{
					field : 'name',
					title : '读者姓名',
					width : 150
				},
				
				{
					field : 'loginaccount',
					title : '账号',
					width : 200
				},
				
				{
					field : 'cardtype',
					title : '证件类别',
					width : 150,
					formatter : function(value, row, index) {
						if(value == "01") {
							return "身份证";
						}else if(value == "02") {
							return "军官证";
						}else if(value == "03") {
							return "护照";
						}else if(value == "04") {
							return "港澳通行证";
						}else if(value == "05") {
							return "台湾通讯证";
						}
					}
				},
				
				{
					field : 'cardno',
					title : '证件号',
					width : 150
				},
				
				{
					field : 'xxxx',
					title : '操作',
					width : 150,
					formatter : function(value, row, index) {
						<c:if test="${LoginObj.role != 2}">
							return "<a href='#' onclick='logInfo("+row.id+")'>查看登录日志</a> | <a href='#' onclick=deleteInfo("+row.id+","+"'"+row.loginaccount+"')>删除</a>";
						</c:if>
						
						<c:if test="${LoginObj.role == 2}">
						return "<a href='#' onclick='logInfo("+row.id+")'>查看登录日志</a>";
						</c:if>
						
					}
				}
			]],
			toolbar : '#tbr',
			onLoadSuccess : function(data) {
				$('#dataTable').datagrid("clearSelections");
				gvar = 1;
			}
		})
		
	})
	
	function deleteInfo(id, account) {
		if(confirm('确定要删除？')){
			$.ajax({
			url:"${ctx}/session/user/delete/" + id +"?r=" + Math.random(),
			type:'GET',
			success:function(data){
					insertLog("删除用户 "+account);
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
			var titleArr = [];
			for(var i = 0; i < rows.length; i++) {
				idArr.push(rows[i].id);
				titleArr.push(rows[i].loginaccount);
			}
			
			$.ajax({
				url:"${ctx}/session/user/deleteAll?r=" + Math.random(),
				data: "ids=" + idArr.join(),
				type:'POST',
				success:function(data){
						insertLog("删除用户 "+titleArr.join());
						$('#dataTable').datagrid("reload");
					}
				});
		}else {
			$('#dataTable').datagrid("clearSelections");
		}
	}
	
	//导出到Excel
	function exportInfo() {
		document.getElementById("tempForm").action = "${ctx}/session/user/export";
		document.getElementById("tempForm").submit();
	}
	
	$(function(){
		$("#queryBtn").click(function(){
			var pstartDate = $("#pstartDate").datebox("getValue");
			var pendDate = $("#pendDate").datebox("getValue");
			
			tab.datagrid("load", {
				//读者姓名
				"name" : $("#name").val(),
				//读者卡号码 
				"loginaccount" : $("#loginaccount").val(),
				//读者证件号码
				"cardno" : $("#cardno").val(),
				//读者类别
				"rdrolecode" : $("#rdrolecode").val(),
				//证件类别
				"cardtype" : $("#cardtype").val(),
				//登录起始时间
				"pstartDate" : pstartDate,
				//登录结束时间
				"pendDate" : pendDate
			});
		})
		
		$("#resetBtn").click(function(){
			$(':input').not(':button, :submit, :reset, :hidden').val('').removeAttr('checked').removeAttr('selected');
			$("#pstartDate").datebox("setValue");
			$("#pendDate").datebox("setValue");
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
	
	
	//查看登录日志
	function logInfo(id) {
		$('#dataTable').datagrid("clearSelections");
		openWin2("查看登录日志", "${ctx}/session/user/logInfo?id=" + id, 800, 500);
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
</script>
</head>
<body onkeydown="if (event.keyCode == 13) {$('#queryBtn').click();} ">
<div>
	<form id="queryForm">
		<table style="width:100%">
			<tr>
				<td width="10%" align="right">读者姓名：</td>
				<td width="20%"><input type="text" id="name" style="width:210px" name="name" /></td>
				
				<td width="10%" align="right">读者卡号：</td>
				<td width="20%"><input type="text" id="loginaccount" style="width:210px" name="loginaccount" /></td>
				
				<td width="10%" align="right">读者类别：</td>
				<td width="20%">
					<select id="rdrolecode" name="rdrolecode" style="width:210px;">   
						<option></option>
    					<option value="JS0001">实名用户</option>   
    					<option value="JS0002">读者卡用户</option>   
    					<option value="0000">虚拟用户</option>   
					</select>
				</td>
			</tr>
			
			<tr>
				<td width="10%" align="right">证件类别：</td>
				<td width="20%">
					<select id="cardtype" name="cardtype" style="width:210px;">   
						<option></option>
    					<option value="01">身份证</option>   
    					<option value="02">军官证</option>   
    					<option value="03">护照</option>   
    					<option value="04">港澳通行证</option>   
    					<option value="05">台湾通讯证</option>   
					</select>
				</td>
				
				<td width="10%" align="right">证件号：</td>
				<td width="20%"><input type="text" id="cardno" style="width:210px" name="cardno"/></td>
				
				<td width="10%" align="right"></td>
				<td width="20%"></td>
			</tr>
		</table>
		
		<table style="width:100%">
			<tr>
				<td width="10%" align="right">登录时间：</td>
				<td width="30%">
					<input type="text" id="pstartDate" name="pstartDate" class="easyui-datebox" /> -- 
					<input type="text" id="pendDate" name="pendDate" class="easyui-datebox" />
				</td>
				
				<td width="10%" align="right"></td>
				<td width="10%"></td>
				
				<td width="10%" align="right"></td>
				<td width="20%"></td>
				
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
	<c:if test="${LoginObj.role != 2}">
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-ok',plain:true" onclick="selectAll()">全选</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="deleteAll()">删除</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-redo',plain:true" onclick="exportInfo()">导出Excel</a>
	</c:if>
	
	<c:if test="${LoginObj.role == 2}">
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-redo',plain:true" onclick="exportInfo()">导出Excel</a>
	</c:if>
	</div>
	
	
	<form id="tempForm"></form>
</div>

<div id="dataForm" style="overflow:hidden;font-size:10px;display:none;" title="编辑" >
	<iframe id="winSrc" frameborder="0" width="100%" height="100%" src="${ctx}/images/ajax-loader.gif"></iframe>
</div>
</body>
</html>