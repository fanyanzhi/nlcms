<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../TagLib.jsp"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="../Pub.jsp" %>
<meta http-equiv="content-type" content="text/html;charset=UTF-8" />

<script type="text/javascript">
	var tab;
	
	$(function(){
		
		tab = $('#dataTable').datagrid({
			//数据来源
			url:'${ctx}/session/columndic/dlist',
			//斑马纹
			striped:true,
			//主键字段
			idField:"id",
			//表单提交方式
			method:"post",
			//是否只能选择一行
			singleSelect:true,
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
					field : 'columnname',
					title : '栏目',
					width : 200
				}
				
				<c:if test="${LoginObj.role != 2}">
				,
				{
					field : 'xxxx',
					title : '操作',
					width : 120,
					formatter : function(value, row, index) {
						var tem = stripscript(row.columnname);
						return "<a href='#' onclick='updateInfo("+row.id+")'>编辑</a> | <a href='#' onclick=deleteInfo("+row.id+","+"'"+tem+"')>删除</a>";
					}
				}
				</c:if>
			]],
			toolbar : '#tbr',
			onLoadSuccess : function(data) {
				$('#dataTable').datagrid("clearSelections");
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
	
	function deleteInfo(id, message) {
		var alertstr = "";
		$.ajax({
			url:"${ctx}/session/columndic/checktrail/" + id +"?r=" + Math.random(),
			type:'GET',
			async:false,
			success:function(data){
				var res = eval("("+data+")");
					if(res.result == false) {
						alertstr = '确定要删除？'
					}else {
						alertstr = '该栏目下有已发布的讲座，确定要删除？'
					}
				} 
		});
		
		
		if(confirm(alertstr)){
			$.ajax({
			url:"${ctx}/session/columndic/delete/" + id +"?r=" + Math.random(),
			type:'GET',
			success:function(data){
					insertLog("删除讲座预告栏目 "+message);
					$('#dataTable').datagrid("reload");
				} 
			});
		}else {
			$('#dataTable').datagrid("clearSelections");
		}
	}
	
	$(function(){
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
		openWin2("修改", "${ctx}/session/columndic/update?id=" + id, 513, 150);
	}
	
	//新增
	function addObject() {
		openWin2("新增", "${ctx}/session/columndic/add", 513, 150);
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
<body>
<div>
	<br/>
	<table id="dataTable">
	</table>
	
	<c:if test="${LoginObj.role != 2}">
	<div id="tbr">
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="addObject()">新增</a>
	</div>
	</c:if>
	
</div>

<div id="dataForm" style="overflow:hidden;font-size:10px;display:none;" title="编辑" >
	<iframe id="winSrc" frameborder="0" width="100%" height="100%" src="${ctx}/images/ajax-loader.gif"></iframe>
</div>
</body>
</html>