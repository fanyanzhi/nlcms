<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../TagLib.jsp"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="../Pub.jsp" %>
<meta http-equiv="content-type" content="text/html;charset=UTF-8" />
<script type="text/javascript" src="${ctx}/ueditor/ueditor.config.js"></script>
<script type="text/javascript" src="${ctx}/ueditor/ueditor.all.js"></script>
<style>
.comments {width:90%;overflow:auto;word-break:break-all;border: 1px solid #CCC; }  
</style>
</head>
<body>
<script>
	$(function(){
		var picus = "${nlcsuggestion.picurl}";
		var picArr = picus.split(",");
		if(picArr[0]) {
			var html = "";
			for(var i = 0; i < picArr.length; i++) {
				html += '<img style="width: 300px; height: 300px;" onclick=amp("'+picArr[i]+'") src="'+picArr[i]+'"/><br/>';
			}
			$("#picchain").append(html);
		}
		
		$("#closeBtn").click(function(){
			parent.closeWin();
		});
		
		insertLog("查看 ${nlcsuggestion.username} 的一个意见");
	})
	
	
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
	
	function amp(purl) {
		window.open(purl,null,"height=800,width=800,status=yes,toolbar=no,menubar=no,location=no");
	}
</script>
<div>
	<form id="suggestionForm">
		<table align="center" width="90%">
		
			<tr>
				<td align="right">用户名：</td>
				<td >
					<input type="text" id="username"  name="username" readonly="readonly" style="color:gray;" value="${nlcsuggestion.username }" />
				</td>
			</tr>
			
			<tr>
				<td align="right">留言时间：</td>
				<td >
					<input name="asktime" value="<fmt:formatDate value='${nlcsuggestion.asktime}' pattern='yyyy-MM-dd HH:mm:ss'/>" readonly="readonly" class="easyui-datetimebox" style="width:150px;color:gray;" > 
				</td>
			</tr>
			
			<tr>
				<td align="right">邮箱：</td>
				<td >
					<input type="text" id="email"  name="email" readonly="readonly" style="color:gray;" value="${nlcsuggestion.email }" />
				</td>
			</tr>
			
			<tr>
				<td align="right">qq：</td>
				<td >
					<input type="text" id="qq"  name="qq" readonly="readonly" style="color:gray;" value="${nlcsuggestion.qq }" />
				</td>
			</tr>
			
			<tr>
				<td align="right">联系电话：</td>
				<td >
					<input type="text" id="phone"  name="phone" readonly="readonly" style="color:gray;" value="${nlcsuggestion.phone }" />
				</td>
			</tr>
			
			<tr>
				<td align="right">问题：</td>
				<td>
					<textarea class="comments" rows="5" style="color:gray;" readonly="readonly" id="question" rows="5"  name="question">${nlcsuggestion.question }</textarea>
				</td>
			</tr>
			
			<tr>
				<td align="right">图片：</td>
				<td id="picchain">
				</td>
			</tr>
			
			<tr>
				<td align="right">答复：</td>
				<td>
					<textarea class="comments easyui-validatebox" validType="length[0, 166]" style="color:gray;" readonly="readonly" rows="5" id="answer" name="answer">${nlcsuggestion.answer }</textarea>
				</td>
			</tr>
			
			<tr>
				<td align="right">管理员姓名：</td>
				<td >
					<input type="text" id="adminname"  name="adminname" readonly="readonly" style="color:gray;" value="${nlcsuggestion.adminname }" />
				</td>
			</tr>
			
			<tr>
				<td align="right">回复时间：</td>
				<td >
					<input name="anstime" value="<fmt:formatDate value='${nlcsuggestion.anstime}' pattern='yyyy-MM-dd HH:mm:ss'/>" readonly="readonly" class="easyui-datetimebox" style="width:150px;color:gray;" > 
				</td>
			</tr>
		</table>
	</form>
	
	<br/>
	<div align="center">
			<input id="closeBtn" type="button" value="关闭" />
	</div>
</div>		

</body>
</html>
