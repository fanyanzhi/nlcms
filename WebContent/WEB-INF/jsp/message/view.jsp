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
		checkEndDate('ztime','ytime');
		//时间框禁止输入
		$(".datebox :text").attr("readonly","readonly");
		
		tab = $('#dataTable').datagrid({
			//数据来源
			url:'${ctx}/session/message/list',
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
					field : 'type',
					title : '分类',
					width : 120,
					formatter : function(value, row, index) {
						if(value == 0) {
							return '管理员群发信息';
						}else if(value == 1) {
							return '交易记录';
						}else if(value == 2) {
							return '图书催还';
						}else if(value == 3) {
							return '违规';
						}else if(value == 4) {
							return '新闻';
						}else if(value == 5) {
							return '公告';
						}else if(value == 6) {
							return '讲座';
						}else if(value == 7) {
							return '专题';
						}else if(value == 8) {
							return '意见回复';
						}
					}
				},
				
				{
					field : 'title',
					title : '标题',
					width : 120
				},
				
				{
					field : 'message',
					title : '消息内容',
					width : 300
				},
				
				{
					field : 'username',
					title : '客户姓名',
					width : 100
				},
				
				{
					field : 'pubname',
					title : '发布者姓名',
					width : 100
				},
				
				{
					field : 'time',
					title : '发布时间',
					width : 150
				}
				
				<c:if test="${LoginObj.role != 2}">
				,
				{
					field : 'xxxx',
					title : '操作',
					width : 200,
					formatter : function(value, row, index) {
						var tem = stripscript(row.message);
						return "<a href='#' onclick='updateInfo("+row.id+")'>编辑</a> | <a href='#' onclick=deleteInfo("+row.id+","+"'"+tem+"')>删除</a>";
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
	
	function stripscript(s) { 
		if(s){
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
	
	function deleteInfo(id, message) {
		
		if(confirm('确定要删除？')){
			$.ajax({
			url:"${ctx}/session/message/delete/" + id +"?r=" + Math.random(),
			type:'GET',
			success:function(data){
					insertLog("删除站内信 "+message);
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
			var messArr = [];
			for(var i = 0; i < rows.length; i++) {
				idArr.push(rows[i].id);
				messArr.push(rows[i].message);
			}
			
			$.ajax({
				url:"${ctx}/session/message/deleteAll?r=" + Math.random(),
				data: "ids=" + idArr.join(),
				type:'POST',
				success:function(data){
						insertLog("删除站内信 " + messArr.join());
						$('#dataTable').datagrid("reload");
					}
				});
		}else {
			$('#dataTable').datagrid("clearSelections");
		}
	}
	
	
	$(function(){
		$("#queryBtn").click(function(){
			//var ztime = $("#ztime").datebox("getValue");
			//var ytime = $("#ytime").datebox("getValue");
			
			tab.datagrid("load", {
				//新闻标题
				"username" : $("#username").val(),
				//上传人 
				"pubname" : $("#pubname").val(),
				/* "ztime" : ztime,
				"ytime" : ytime, */
				//分类
				"type" : $("#type").val()
			});
		})
		
		$("#resetBtn").click(function(){
			$(':input').not(':button, :submit, :reset, :hidden').val('').removeAttr('checked').removeAttr('selected');
			//$("#ztime").datebox("setValue");
			//$("#ytime").datebox("setValue");
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
	
	
	//修改
	function updateInfo(id) {
		$('#dataTable').datagrid("clearSelections");
		openWin2("修改", "${ctx}/session/message/update?id=" + id, 680, 385);
	}
	
	//新增
	function addObject() {
		openWin2("新增", "${ctx}/session/message/add", 680, 385);
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
		<table style="width:80%">
			<tr>
				<td width="20%" align="right">客户姓名：</td>
				<td width="30%"><input type="text" id="username" style="width:210px" name="username" /></td>
				
				<td width="20%" align="right">发布者姓名：</td>
				<td width="30%"><input type="text" id="pubname" style="width:210px" name="pubname" /></td>
			</tr>
			
			<!-- <tr>
				<td width="20%" align="right">发布时间：</td>
				<td colspan="3">
					<input type="text" id="ztime" name="ztime" class="easyui-datebox" /> -- 
					<input type="text" id="ytime" name="ytime" class="easyui-datebox" />
				</td>
			</tr> -->
			
			<tr>
				<td width="20%" align="right">分类：</td>
				<td width="30%">
					<select id="type" name="type" style="width:210px;">   
						<option></option>
    					<option value="0">管理员群发信息</option>   
    					<option value="1">交易记录</option>   
    					<option value="2">图书催还</option>   
    					<option value="3">违规</option>
    					<option value="4">新闻</option>
    					<option value="5">公告</option>
    					<option value="6">讲座</option>
    					<option value="7">专题</option>
    					<option value="8">意见回复</option>
					</select>
				</td>
				
				<td width="20%" align="right"></td>
				<td width="30%"></td>
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
	
	<c:if test="${LoginObj.role != 2}">
	<div id="tbr">
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-ok',plain:true" onclick="selectAll()">全选</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="deleteAll()">删除</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="addObject()">新增</a>
	</div>
	</c:if>
	
</div>

<div id="dataForm" style="overflow:hidden;font-size:10px;display:none;" title="编辑" >
	<iframe id="winSrc" frameborder="0" width="100%" height="100%" src="${ctx}/images/ajax-loader.gif"></iframe>
</div>
</body>
</html>