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
		checkEndDate('zasktime','yasktime');
		checkEndDate('zanstime','yanstime');
		//时间框禁止输入
		$(".datebox :text").attr("readonly","readonly");
		
		tab = $('#dataTable').datagrid({
			//数据来源
			url:'${ctx}/session/suggestion/list',
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
					field : 'anstime',
					title : '回复时间',
					width : 120
				},
				{
					field : 'asktime',
					title : '留言时间',
					width : 120
				},
				{
					field : 'username',
					title : '用户名',
					width : 120
				},
				{
					field : 'email',
					title : '邮箱',
					width : 120
				},
				{
					field : 'qq',
					title : 'qq',
					width : 100
				},
				{
					field : 'phone',
					title : '联系电话',
					width : 100
				},
				{
					field : 'type',
					title : '问题分类',
					width : 100
				},
				{
					field : 'question',
					title : '问题',
					width : 230
				},
				{
					field : 'status',
					title : '状态',
					width : 50
				},
				{
					field : 'xxxx',
					title : '操作',
					width : 150,
					formatter : function(value, row, index) {
						var stat = row.status;
						var tit = "";
						if(stat == '已回复') {
							tit = "编辑";
						}else {
							tit = "回复";
						}
						
						<c:if test="${LoginObj.role != 2}">
							return "<a href='#' onclick=updateInfo("+row.id+",'"+tit+"')>"+tit+"</a> | <a href='#' onclick=deleteInfo("+row.id+","+"'"+row.username+"')>删除</a> | <a href='#' onclick=checkInfo("+row.id+")>查看</a>";
						</c:if>
						
						<c:if test="${LoginObj.role == 2}">
							return "<a href='#' onclick=checkInfo("+row.id+")>查看</a>";
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
	
	function stripscript(s) { 
		if(s) {
			var pattern = new RegExp("[`~!@#$^&*()=|{}':;',\\[\\].<>/?~！@#￥……&*（）——|{}【】‘；：”“'。，、？]") 
			var rs = ""; 
			for (var i = 0; i < s.length; i++) { 
				rs = rs+s.substr(i, 1).replace(pattern, ''); 
			} 
			rs = rs.replace(/ /g, '');
			return rs;
		}else {
			return "";
		}
	}
	
	//修改
	function updateInfo(id, tit) {
		$('#dataTable').datagrid("clearSelections");
		var flag = "";
		if(tit == "编辑") {
			flag = "1";
		}else {
			flag = "2";
		}

		openWin2(tit, "${ctx}/session/suggestion/update?id=" + id + "&flag=" + flag, 700, 500);
	}
	
	//查看
	function checkInfo(id) {
		$('#dataTable').datagrid("clearSelections");

		openWin2("查看", "${ctx}/session/suggestion/check?id=" + id, 700, 500);
	}
	
	function deleteInfo(id, username) {
		if(confirm('确定要删除？')){
			$.ajax({
			url:"${ctx}/session/suggestion/delete/" + id +"?r=" + Math.random(),
			type:'GET',
			success:function(data){
					insertLog("删除 "+ username +" 的一个意见");
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
				url:"${ctx}/session/suggestion/deleteAll?r=" + Math.random(),
				data: "ids=" + idArr.join(),
				type:'POST',
				success:function(data){
						insertLog("删除 " + nameArr.join() +" 的意见");
						$('#dataTable').datagrid("reload");
					}
				});
		}else {
			$('#dataTable').datagrid("clearSelections");
		}
	}
	
	$(function(){
		$("#queryBtn").click(function(){
			var zasktime = $("#zasktime").datebox("getValue");	//留言时间
			var yasktime = $("#yasktime").datebox("getValue");
			
			var zanstime = $("#zanstime").datebox("getValue");	//答复时间
			var yanstime = $("#yanstime").datebox("getValue");
			
			tab.datagrid("load", {
				//用户名
				"username" : $("#username").val(),
				//手机
				"phone" : $("#phone").val(),
				//邮箱
				"email" : $("#email").val(),
				//管理员姓名
				"adminname" : $("#adminname").val(),
				//状态
				"status" : $("#status").val(),
				//留言起始时间
				"zasktime" : zasktime,
				//留言结束时间
				"yasktime" : yasktime,
				//答复起始时间
				"zanstime" : zanstime,
				//答复结束时间
				"yanstime" : yanstime,
				//问题分类
				"type" : $("#type").val()
			});
		})
		
		$("#resetBtn").click(function(){
			$(':input').not(':button, :submit, :reset, :hidden').val('').removeAttr('checked').removeAttr('selected');
			$("#zasktime").datebox("setValue");
			$("#yasktime").datebox("setValue");
			$("#zanstime").datebox("setValue");
			$("#yanstime").datebox("setValue");
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
	
	//导出到Excel
	function exportInfo() {
		var rows = $('#dataTable').datagrid("getSelections");
		if(rows.length == 0) {
			alert('请先选择数据!');
			return ;
		}
		
		var ids = [];
		for(var i = 0; i < rows.length; i++) {
			ids.push(rows[i].id);
		}
		
		$("#ids").val(ids.join());
		document.getElementById("tempForm").action = "${ctx}/session/suggestion/export";
		document.getElementById("tempForm").submit();
	}
</script>
</head>
<body onkeydown="if (event.keyCode == 13) {$('#queryBtn').click();} ">
<div>
	<form id="queryForm">
		<table align="center" width="90%">
			<tr>
				<td width="10%" align="right">用户名：</td>
				<td width="23%"><input type="text" id="username" style="width:210px" name="username" /></td>
				
				<td width="10%" align="right">联系电话：</td>
				<td width="23%"><input type="text" id="phone" style="width:210px" name="phone" /></td>
				
				<td width="10%" align="right">邮箱：</td>
				<td width="23%"><input type="text" id="email" style="width:210px" name="email" /></td>
			</tr>
			
			<tr>
				<td width="10%" align="right">管理员姓名：</td>
				<td width="23%"><input type="text" id="adminname" style="width:210px" name="adminname" /></td>
				
				<td width="10%" align="right">状态：</td>
				<td width="23%">
					<select id="status" name="status" style="width:210px;">   
    					<option></option>
    					<option value="已回复">已回复</option>   
    					<option value="未回复">未回复</option>   
					</select>
				</td>
				
				<td width="10%" align="right">问题分类：</td>
				<td width="23%">
					<select id="type" name="type" style="width:210px;">
    					<option></option>
    					<option value="使用问题">使用问题</option>   
    					<option value="系统错误">系统错误</option>   
    					<option value="读者账号问题">读者账号问题</option>   
    					<option value="产品建议">产品建议</option>   
					</select>
				</td>
			</tr>
			
			
			
			<tr>
				<td width="10%" align="right">留言时间：</td>
				<td colspan="5">
					<input type="text" id="zasktime" name="zasktime" class="easyui-datebox" /> -- 
					<input type="text" id="yasktime" name="yasktime" class="easyui-datebox" />
				</td>
			</tr>
			
			<tr>
				<td width="10%" align="right">回复时间：</td>
				<td colspan="5">
					<input type="text" id="zanstime" name="zanstime" class="easyui-datebox" /> -- 
					<input type="text" id="yanstime" name="yanstime" class="easyui-datebox" />
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
	<c:if test="${LoginObj.role != 2}">
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-ok',plain:true" onclick="selectAll()">全选</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="deleteAll()">删除</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-redo',plain:true" onclick="exportInfo()">导出Excel</a>
	</c:if>
	
	<c:if test="${LoginObj.role == 2}">
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-redo',plain:true" onclick="exportInfo()">导出Excel</a>
	</c:if>
	</div>
	
	<form id="tempForm">
		<input type="hidden" name="ids" id="ids">
	</form>
</div>

<div id="dataForm" style="overflow:hidden;font-size:10px;display:none;" title="编辑" >
	<iframe id="winSrc" frameborder="0" width="100%" height="100%" src="${ctx}/images/ajax-loader.gif"></iframe>
</div>
</body>
</html>