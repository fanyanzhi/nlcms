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
			url:'${ctx}/session/thirdapp/list',
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
					field : 'name',
					title : '名称',
					width : 120
				},
				{
					field : 'os',
					title : '系统',
					width : 120
				},
				{
					field : 'size',
					title : '大小',
					width : 120
				},
				{
					field : 'version',
					title : '版本号',
					width : 100
				},
				{
					field : 'status',
					title : '状态',
					width : 100,
					formatter : function(value, row, index) {
						if(value == '1') {
							return "已发布";
						}else {
							return "未发布";
						}
					}
				}
				
				<c:if test="${LoginObj.role != 2}">
				,
				{
					field : 'xxxx',
					title : '操作',
					width : 150,
					formatter : function(value, row, index) {
						var flag = row.status;
						var textvalue = "发布";
						var status = 1;	//1是发布， 0是取消
						if(flag == '1') {
							textvalue = '取消';
							status = 0;
						}
						
						var tem = stripscript(row.name);
						return "<a href='#' onclick='updateInfo("+row.id+")'>编辑</a> | <a href='#' onclick=deleteInfo("+row.id+","+"'"+tem+"')>删除</a> | <a href='#' onclick=operateFun("+row.id+","+status+",'"+tem+"')>"+textvalue+"</a>";
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
	
	//status 1发布，0取消
	function operateFun(id, status, title) {
		$('#dataTable').datagrid("clearSelections");
		
		var confirmword = "";
		if("1" == status) {
			confirmword = "发布";
		}else {
			confirmword = "取消发布";
		}
		
		if(confirm('确定要'+confirmword+'？')){
			$.ajax({
				url:"${ctx}/session/thirdapp/publish?id="+id+"&status="+status+"&r=" + Math.random(),
				type:'GET',
				success:function(data){
						  var res = eval("("+data+")");
						  if(res.result == false) {
							alert("操作失败");
						  }else if(res.result == true) {
							insertLog("其他应用程序 "+title+" "+confirmword);
							$('#dataTable').datagrid("reload");
						  }
					} 
				});
		}
		
	}
	
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
	function updateInfo(id) {
		$('#dataTable').datagrid("clearSelections");
		openWin2("修改", "${ctx}/session/thirdapp/update?id=" + id, 1300, 250);
	}
	
	function deleteInfo(id, name) {
		if(confirm('确定要删除？')){
			$.ajax({
			url:"${ctx}/session/thirdapp/delete/" + id +"?r=" + Math.random(),
			type:'GET',
			success:function(data){
					insertLog("其他应用程序删除 "+ name);
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
				nameArr.push(rows[i].name);
			}
			
			$.ajax({
				url:"${ctx}/session/thirdapp/deleteAll?r=" + Math.random(),
				data: "ids=" + idArr.join(),
				type:'POST',
				success:function(data){
						insertLog("删除其他应用程序 " + nameArr.join());
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
				"name" : $("#name").val(),
				"os" : $("#os").val(),
				"status" : $("#status").val()
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
	
	//添加
	function addapp() {
		$('#dataTable').datagrid("clearSelections");
		openWin2("添加", "${ctx}/session/thirdapp/add", 1300, 250);
	}
	
</script>
</head>
<body onkeydown="if (event.keyCode == 13) {$('#queryBtn').click();} ">
<div>
	<form id="queryForm">
		<table align="center" width="90%">
			<tr>
				<td width="10%" align="right">名称：</td>
				<td width="23%"><input type="text" id="name" style="width:210px" name="name" /></td>
				
				<td width="10%" align="right">系统：</td>
				<td width="23%">
					<select id="os" name="os" style="width:210px;">   
    					<option></option>
    					<option value="android">安卓</option>   
    					<option value="ios">苹果</option>   
					</select>
				</td>
				
				<td width="10%" align="right">状态：</td>
				<td width="23%">
					<select id="status" name="status" style="width:210px;">
    					<option></option>
    					<option value="1">已发布</option>   
    					<option value="0">未发布</option>   
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
	<div id="tbr">
	<c:if test="${LoginObj.role != 2}">
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-ok',plain:true" onclick="selectAll()">全选</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="addapp()">添加</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="deleteAll()">删除</a>
	</c:if>
	</div>
</div>

<div id="dataForm" style="overflow:hidden;font-size:10px;display:none;" title="编辑" >
	<iframe id="winSrc" frameborder="0" width="100%" height="100%" src="${ctx}/images/ajax-loader.gif"></iframe>
</div>
</body>
</html>