<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../TagLib.jsp"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="../Pub.jsp" %>
<meta http-equiv="content-type" content="text/html;charset=UTF-8" />
</head>
<body>

<script type="text/javascript">
	$(function() {
		$('#editUserPwdForm').form({
			url : '${ctx}/session/admin/editAdminPwd',
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
					parent.$.messager.alert('提示', res.msg, 'info');
					parent.$.modalDialog.handler.dialog('close');
				} else {
					parent.$.messager.alert('错误', res.msg, 'error');
				}
			}
		});
	});
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title="" style="overflow: hidden;">
			<form id="editUserPwdForm" method="post">
				<table align="center">
					<tr>
						<th>登录名</th>
						<td>${LoginObj.username}</td>
					</tr>
					<tr>
						<th>原密码</th>
						<td><input name="oldPwd" type="password" placeholder="请输入原密码" class="easyui-validatebox" data-options="required:true"></td>
					</tr>
					<tr>
						<th>新密码</th>
						<td><input name="pwd" id="pwd" type="text" placeholder="请输入新密码" class="easyui-validatebox" required data-options="validType:['isBlank','nochinese','length[0,20]']"></td>
					</tr>
					<tr>
						<th>重复密码</th>
						<td><input name="rePwd" type="text" placeholder="请再次输入新密码" class="easyui-validatebox" data-options="same['#pwd']"></td>
					</tr>
				</table>
			</form>
	</div>
</div>
</body>
</html>