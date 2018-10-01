<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../TagLib.jsp"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="../Pub.jsp" %>
<meta http-equiv="content-type" content="text/html;charset=UTF-8" />

<style type="text/css">
.span{font-size:12px;color:#ccc}
td{height:25px;}
body{color:#222; font:12px/20px Arial,"宋体";  background-color:#fff;}
</style>

</head>
<body>
<div>
	<form id="adminForm">
		<table border="1" width="90%" align="center" bordercolor="#a0c6e5" style="border:1;border-collapse:collapse;">
		<tr>
			<td width="20%" align="right">专题名称：</td>
			<td >
				${nlcsubject.title }
			</td>
		</tr>
		
		<tr>
			<td width="20%" align="right">排序：</td>
			<td ><input type="text" id="sort" name="sort" class="easyui-numberbox" data-options="required:true,max:10000,min:1"  value="${nlcsubject.sort }"/></td>
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
			var isvalid = $("#adminForm").form('validate');
			if(!isvalid) {
				return ;
			}
			
			var title = "${nlcsubject.title }";
			var sort = $('#sort').numberbox('getValue');
			
			$.ajax({
                url: "${ctx}/session/subject/sortSubject",
                data: {"id":"${nlcsubject.id}", "sort":sort},
                cache: false,
                type: "POST",
                success: function(data){
                	var res = eval("("+data+")");
					if(res.result == false) {
						alert("操作失败");
					}else if(res.result == true) {
						alert("操作成功");
						insertLog("特色专题推荐排序 " +title);
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
