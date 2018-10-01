<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../TagLib.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
</head>
<body>
<script type="text/javascript">
	$(function() {
		//保存新一级目录
		$('#catalogAddForm').form({
			url : '${ctx}/session/readercompasscata/savecatalog',
			onSubmit : function() {
				progressLoad();
				var isValid = $(this).form('validate');
				if (!isValid) {
					progressClose();
				}
				return isValid;
			},
			success : function(data) {
				progressClose();
				var res = eval("("+data+")");
				if (res.result) {
					var tx = $("#tx").val();
					insertLog("为读者指南添加目录 " + tx);
					$('#catalogTree').tree('reload');
					$('#dd').dialog('close');
				}
			}
		});
	});
	
	function insertLog(message) {
		var loginTime = "<fmt:formatDate value='${loginTime}' pattern='yyyy-MM-dd HH:mm:ss'/>";
		var loginIp = "${loginIp}";
		var username = "${LoginObj.username}";
		var role = "${LoginObj.role}";
		
		$.ajax({
			url:"${ctx}/session/adminlog/insertLog",
			type:'POST',
			async:false,
			data: {"username":username, "role":role, "ip":loginIp, "time":loginTime, "operate": message},
			success:function(data){
				} 
			});
	}
</script>

<div style="padding: 30px;">
	<form id="catalogAddForm" method="post">
		<table class="grid">
			<tr>
				<td>名称</td>
				<td>
					<input name="pid"  type="hidden"  value="${pid}">
					<input name="title" id="tx" type="text" placeholder="请输入名称" class="easyui-validatebox" required data-options="validType:['specialy','isBlank','length[0,40]']"></td>
			</tr>
			<tr>
				<td>状态</td>
				<td>
					<select name="status" class="easyui-combobox" data-options="width:100,height:29,editable:false,panelHeight:50">
							<option value="已上架">上架</option>
							<option value="已下架">下架</option>
					</select>
				</td>
			</tr>
			<tr>
				<td>排序</td>
				<td><input name="cseq" type="text" placeholder="请输入排序号" class="easyui-numberbox" data-options="required:true,max:1000,min:1"  ></td>
			</tr>

	</table>
	</form>
</div>
</body>
</html>