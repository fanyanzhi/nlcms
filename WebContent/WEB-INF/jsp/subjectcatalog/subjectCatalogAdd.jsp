<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../TagLib.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
</head>
<body>
<script type="text/javascript">
	$(function() {
		//保存新一级目录
		$('#subjectCatalogAddForm').form({
			url : '${ctx}/session/subjectcatalog/subjectCatalogAdd',
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
					insertLog("为特色专题 ${title}添加目录 " + tx);
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
	<form id="subjectCatalogAddForm" method="post">
		<table class="grid">
			<tr>
				<td>目录名称</td>
				<td><input name="subjectid" type="hidden"  value="${subjectid}">
					<input name="pid"  type="hidden"  value="${pid}">
					<input name="title" id="tx" type="text" placeholder="请输入名称" class="easyui-validatebox" required data-options="validType:['specialy','isBlank','length[0,30]']"></td>
			</tr>
			<tr>
				<td>发布时间</td>
				<td><input name="pubtime"  class="easyui-datetimebox" style="width:150px;" ></td>
			</tr>
			<tr>
				<td>下架时间</td>
				<td><input name="downtime"   class="easyui-datetimebox" style="width:150px;"></td>
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