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
<div>
	<form id="messageForm">
		<table align="center">
			
			<tr>
				<td width="20%" align="left" colspan="2">To：${sysmessage.username }</td>
			</tr>
			
			<tr>
				<td width="20%" align="left">标题：</td>
				<td>
					<input type="text" id="title" style="width:300px" name="title" class="easyui-validatebox"  required data-options="validType:['specialy','isBlank','length[0,60]']" value="${sysmessage.title }"/>
				</td>
			</tr>
			
			<tr>
				<td width="20%" align="left">消息内容：</td>
				<td>
					<textarea class="comments easyui-validatebox" validType="length[0, 1000]" rows="4" id="message" name="message" style="width: 460px; height: 150px;">${sysmessage.message }</textarea>
				</td>
			</tr>
			
			<!-- <tr>
				<td width="20%" align="left">发布时间：</td>
				<td>
					<input type="text" id="time" name="time" class="easyui-datetimebox" /> 
				</td>
			</tr> -->
			
		</table>
		<br/>
	</form>
	
	<br/>
	<div align="center">
		<a id="resBtn" class="easyui-linkbutton" data-options="iconCls:'icon-undo'">重置</a>
		<a id="saveBtn" class="easyui-linkbutton" data-options="iconCls:'icon-add'">保存</a>
	</div>
</div>

<script>

	$(function(){
		//时间框禁止输入
		$(".datebox :text").attr("readonly","readonly");
		
		//$("#time").datetimebox('setValue', "<fmt:formatDate value='${sysmessage.time}' pattern='yyyy-MM-dd HH:mm:ss'/>");
		
		//重置按钮的点击事件
		$("#resBtn").click(function(){
			$("#message").val('');
		})
		
		//保存按钮的点击事件
		$("#saveBtn").click(function(){
			//校验
			var isvalid = $("#messageForm").form('validate');
			if(!isvalid) {
				return ;
			}
			$("#saveBtn").unbind();
			
			/* var time = $("#time").datetimebox('getValue');
			if(!time) {
				alert("时间不能为空");
				return ;
			} */
			
			var title = $("#title").val();
			var message = $("#message").val();
			var id = "${sysmessage.id}";
			
			$.ajax({
                url: "${ctx}/session/message/updateMessage",
                data: {"title":title, "message":message, "id":id},
                cache: false,
                type: "POST",
                success: function(data){
                	var res = eval("("+data+")");
					if(res.result == false) {
						alert("操作失败");
					}else if(res.result == true) {
						alert("操作成功");
						insertLog("修改站内信 " + message);
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
