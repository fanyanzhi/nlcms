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
			url:'${ctx}/session/faq/list',
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
					field : 'cseq',
					title : '推荐顺序',
					width : 80,
					formatter : function(value, row, index) {
						if(!value || value==1000) {
							return '';
						}else {
							return value;
						}
					}
				},
				
				{
					field : 'question',
					title : '题目',
					width : 450
				},
				
				{
					field : 'status',
					title : '状态',
					width : 100
				},
				
				{
					field : 'xxxx',
					title : '操作',
					width : 200,
					formatter : function(value, row, index) {
						var flag = row.status;
						var textvalue = "发布";
						var status = 1;	//1是发布， 0是取消
						if(flag == '已发布') {
							textvalue = '取消';
							status = 0;
						}
						
						<c:if test="${LoginObj.role != 2}">
							var tem = stripscript(row.question);
							return "<a href='#' onclick='updateInfo("+row.id+")'>编辑</a> | <a href='#' onclick=deleteInfo("+row.id+","+"'"+tem+"')>删除</a> | <a href='#' onclick=previewInfo('"+row.id+"')>预览</a> | <a href='#' onclick=operateFun("+row.id+","+status+",'"+tem+"')>"+textvalue+"</a>";
						</c:if>
						
						<c:if test="${LoginObj.role == 2}">
							return "<a href='#' onclick=previewInfo('"+row.id+"')>预览</a>";
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
	
	function deleteInfo(id, name) {
		if(confirm('确定要删除？')){
			$.ajax({
			url:"${ctx}/session/faq/delete/" + id +"?r=" + Math.random(),
			type:'GET',
			success:function(data){
					insertLog("常见问题删除 "+ name);
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
			var nameArr = [];
			for(var i = 0; i < rows.length; i++) {
				idArr.push(rows[i].id);
				nameArr.push(rows[i].question);
			}
			
			$.ajax({
				url:"${ctx}/session/faq/deleteAll?r=" + Math.random(),
				data: "ids=" + idArr.join(),
				type:'POST',
				success:function(data){
						insertLog("删除常见问题 " + nameArr.join());
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
				"question" : $("#question").val(),
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
	
	//预览
	function previewInfo(id) {
		$('#dataTable').datagrid("clearSelections");
		if (navigator.userAgent.indexOf('Firefox') >= 0){
			window.showModalDialog ('${ctx}/session/faq/itemPreview?id=' + id, '预览', 'dialogHeight=680px; dialogWidth=340px');
		} else {
			window.open('${ctx}/session/faq/itemPreview?id=' + id, null,"height=680,width=340,status=yes,toolbar=no,menubar=no,location=no");
		}
	}
	
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
				url:"${ctx}/session/faq/publish?id="+id+"&status="+status+"&r=" + Math.random(),
				type:'GET',
				success:function(data){
						  var res = eval("("+data+")");
						  if(res.result == false) {
							alert("操作失败");
						  }else if(res.result == true) {
							insertLog("常见问题 "+title+" "+confirmword);
							$('#dataTable').datagrid("reload");
						  }
					} 
				});
		}
		
	}
	
	//修改
	function updateInfo(id) {
		$('#dataTable').datagrid("clearSelections");
		openWin2("修改", "${ctx}/session/faq/update?id="+id, 1100, 700);
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
	
	//添加
	function addnews() {
		$('#dataTable').datagrid("clearSelections");
		openWin2("添加", "${ctx}/session/faq/add", 1100, 700);
	}
	
	//推荐排序
	function ftops() {
		var rows = $('#dataTable').datagrid("getSelections");
		if(rows.length == 0) {
			alert('请先选择数据!');
			return ;
		}else if(rows.length > 1) {
			alert('请只选择一行');
			return ;
		}
		
		var id = rows[0].id;
		openWin2("推荐排序", "${ctx}/session/faq/sort?id=" + id, 500, 200);
	}
	
</script>
</head>
<body onkeydown="if (event.keyCode == 13) {$('#queryBtn').click();} ">
<div>
	<form id="queryForm">
		<table style="width:80%">
			<tr>
				<td width="20%" align="right">题目：</td>
				<td width="30%"><input type="text" id="question" style="width:300px" name="question" /></td>
				
				<td width="20%" align="right">状态：</td>
				<td width="30%">
					<select id="status" name="status" style="width:150px;">   
						<option></option>
    					<option value="已发布">已发布</option>   
    					<option value="未发布">未发布</option>   
					</select>
				</td>
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
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-redo',plain:true" onclick="ftops()">推荐排序</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-ok',plain:true" onclick="selectAll()">全选</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="addnews()">添加</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="deleteAll()">删除</a>
	</c:if>
	</div>
	<form id="tempForm"></form>
</div>

<div id="dataForm" style="overflow:hidden;font-size:10px;display:none;" title="编辑" >
	<iframe id="winSrc" frameborder="0" width="100%" height="100%" src="${ctx}/images/ajax-loader.gif"></iframe>
</div>
</body>
</html>