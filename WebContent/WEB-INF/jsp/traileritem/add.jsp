<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../TagLib.jsp"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="../Pub.jsp" %>
<meta http-equiv="content-type" content="text/html;charset=UTF-8" />
<style>
.comments {width:100%;overflow:auto;word-break:break-all;border: 1px solid #CCC; }  
</style>
</head>
<body>
<div align="center">
	<form id="messageForm">
		<table>
			<tr>
				<td width="20%" align="left">栏目：</td>
				<td>
					<input type="text" id="columnname" style="width:200px" name="columnname" class="easyui-validatebox" required data-options="validType:['specialy','isBlank','length[0,60]']"/>
				</td>
			</tr>
		</table>
	</form>
	
	<br/>
	<div align="center">
		<a id="saveBtn" class="easyui-linkbutton" data-options="iconCls:'icon-add'">保存</a>
	</div>
</div>

<script>

	$(function(){
		//保存按钮的点击事件
		$("#saveBtn").click(function(){
			//校验
			var isvalid = $("#messageForm").form('validate');
			if(!isvalid) {
				return ;
			}
			$("#saveBtn").unbind();
			
			var columnname = $("#columnname").val();
			
			$.ajax({
                url: "${ctx}/session/columndic/addItem",
                data: {"columnname":columnname},
                cache: false,
                type: "POST",
                success: function(data){
                	var res = eval("("+data+")");
					if(res.result == false) {
						alert("操作失败");
					}else if(res.result == true) {
						alert("操作成功");
						insertLog("添加讲座预告栏目 " + columnname);
						parent.tab.datagrid('reload');
						parent.closeWin();
					}
                }
			}); 
		})	
			
	})
	
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
</body>
</html>
