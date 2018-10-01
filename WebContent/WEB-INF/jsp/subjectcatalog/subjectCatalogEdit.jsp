<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../TagLib.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
</head>

<script type="text/javascript">
	$(function() {

		var subjectid = $("input[name='subjectid']").val();
		$('#pid').combotree({
			url : '${ctx}/session/subjectcatalog/showTree?subjectid=' + subjectid,
			parentField : 'pid',
			lines : true,
			panelHeight : 'auto',
			value :'${nlcsubjectcatalog.pid}'
		});

		$('#subjectCatalogEditForm').form({
			url : '${ctx}/session/subjectcatalog/editCatalog',
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
					insertLog("为特色专题 ${title}编辑目录 " + tx);
					$('#catalogTree').tree('reload');
					$('#edit').dialog('close');
				}else {
					alert(res.message);
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
<div style="padding: 3px;">
	<form id="subjectCatalogEditForm" method="post">
		<table class="grid">

			<tr>
				<td>名称</td>
				<td><input name="subjectid" type="hidden"  value="${nlcsubjectcatalog.subjectid}">
					<input name="catalogid" type="hidden"  value="${nlcsubjectcatalog.catalogid}">
					<input name="title" id="tx" type="text" value="${nlcsubjectcatalog.title}"  class="easyui-validatebox" required data-options="validType:['specialy','isBlank','length[0,30]']" ></td>
			</tr>
			<tr>
				<td>发布时间</td>
				<td><input name="pubtime" value="<fmt:formatDate value='${nlcsubjectcatalog.pubtime}' pattern='yyyy-MM-dd HH:mm:ss'/>"  class="easyui-datetimebox" style="width:150px;" ></td>

			</tr>
			<tr>
				<td>下架时间</td>
				<td><input name="downtime" value="<fmt:formatDate value='${nlcsubjectcatalog.downtime}' pattern='yyyy-MM-dd HH:mm:ss'/>"  class="easyui-datetimebox" style="width:150px;" ></td>

			</tr>
			<tr>
				<td>序号</td>
				<td><input name="cseq" type="text" value="${nlcsubjectcatalog.cseq}"  class="easyui-numberbox" data-options="required:true,max:1000,min:1"  ></td>
			</tr>
			<tr>
				<td>上级目录</td>
				<td><select id="pid" name="pid" style="width: 140px; height: 29px;"></select></td>
			</tr>
			<tr>
				<td>上下架状态</td>
				<td>
					<select name="status" class="easyui-combobox" data-options="width:150,height:29,editable:false,panelHeight:50">
							<option value="已上架" <c:if test="${'已上架' == nlcsubjectcatalog.status}">selected="selected"</c:if>>已上架</option>
							<option value="已下架" <c:if test="${'已下架' == nlcsubjectcatalog.status}">selected="selected"</c:if>>已下架</option>
					</select>
				</td>
			</tr>
		</table>
	</form>
</div>

</html>