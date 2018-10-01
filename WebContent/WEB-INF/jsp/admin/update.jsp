<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.cnki.com/tag" prefix="my" %>
<%@ include file="../TagLib.jsp"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="../Pub.jsp" %>
<meta http-equiv="content-type" content="text/html;charset=UTF-8" />
<style type="text/css">
.light-info {
    color: #8f5700;
}
</style>
</head>
<body>
<div>
	<form id="adminForm">
		<input type="hidden" id="id" name="id" value="${nlcadmin.id }"/>
		<input type="hidden" name="time" value="<fmt:formatDate value='${nlcadmin.time }' pattern='yyyy-MM-dd HH:mm:ss'/>"/>
		<table width="100%" align="center">
			<tr>
				<td width="20%" align="right">用户名：</td>
				<td width="30%"><input type="text" id="username" readonly="readonly" style="width:210px;color:gray;" name="username" class="easyui-validatebox" data-options="required:true" value="${nlcadmin.username }"/></td>
			</tr>
			
			<tr>	
				<td width="20%" align="right">登录密码：</td>
				<td width="30%"><input type="text" id="password" style="width:210px;background-color:#D9D9D9;" name="password"/>
				<font class="light-info" style="overflow: hidden;padding: 3px;">密码不修改请留空</font>
				</td>
			</tr>
			
			<tr>	
				<td width="20%" align="right">管理员姓名：</td>
				<td width="30%"><input type="text" id="staffname" style="width:210px" name="staffname" class="easyui-validatebox" data-options="validType:'maxLength[10]'" value="${nlcadmin.staffname }"/></td>
			</tr>
			
			<tr>	
				<td width="20%" align="right">所属部门：</td>
				<td width="30%"><input type="text" id="staffdept" style="width:210px" name="staffdept" class="easyui-validatebox" data-options="validType:'maxLength[16]'" value="${nlcadmin.staffdept }"/></td>
			</tr>
			
			<tr>	
				<td width="20%" align="right">办公电话：</td>
				<td width="30%"><input type="text" id="staffphone" style="width:210px" name="staffphone" value="${nlcadmin.staffphone }" class="easyui-validatebox" data-options="validType:['nochinese','maxLength[20]']" /></td>
			</tr>
			
			<tr>	
				<td width="20%" align="right">角色分配：</td>
				<td width="30%">
					<select id="role" name="role" style="width:210px;"> 
    					<option value="2">浏览</option>   
    					<option value="1">编辑</option>   
						<option value="0">超管</option>  
					</select>
				</td>
			</tr>
			
			<my:module/>
			
		</table>
		
		<br/>
		<div align="center">
			<input id="saveBtn" type="button" value="保存" onclick="saveb()"/>
		</div>
		
		<input type="hidden" id="module" name="module" value="${nlcadmin.module }">
	</form>
</div>

<script>
	$(function(){
		$("#role").val("${nlcadmin.role}");
		if(0 == "${nlcadmin.role}") {
			$("input[name='moduleid']").each(function(index, obj){
				$(this).removeAttr("checked");
				$(this).next().css('color','gray');
				$(this).attr('onclick', 'return false');
			});
		}else {
			$("input[name='moduleid']").each(function(index, obj){
				var strArr = "${nlcadmin.moduleid}".split(",");
				for(var i = 0; i < strArr.length; i++) {
					if(this.value == strArr[i]) {
						$(this).attr("checked", "checked");
					}
				}
			});
		}
	})
	
	function saveb() {
		//校验
		var isvalid = $("#adminForm").form('validate');
		if(!isvalid) {
			return ;
		}
		$("#saveBtn").removeAttr('onclick');
		
		var arr = [];
		$("input[name='moduleid']:checked").each(function(index, obj){
			arr.push($(this).next().html());
		});
		$("#module").val(arr.join());
		
		//密码限制
		var pass = $("#password").val();
		if(pass.length > 0) {
			if(!(/^[a-zA-Z]\w{5,17}$/.test(pass))) {
				$("#saveBtn").attr('onclick', 'saveb()');
				alert("密码以字母开头，长度在6~18之间，只能包含字符、数字和下划线!");
				return ;
			} 
		}			
		
		var subData = $("#adminForm").serialize();
			$.ajax({
				url : '${ctx}/session/admin/updateAdmin?r='+Math.random(),
				data : subData,
				type : 'POST',
				success : function(data) {
					var res = eval("("+data+")");
					if(res.result == false) {
						$("#saveBtn").attr('onclick', 'saveb()');
						alert("操作失败");
					}else if(res.result == true) {
						insertLog("修改管理员${nlcadmin.username }信息");
						parent.tab.datagrid('reload');
						parent.closeWin();
					}
				}
			}); 
	}
	
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
	
	$(function(){
		 $("#role").change(function() {
			 var v = $("#role").find("option:selected").val();
			 if(v==2) {
				$("input[name='moduleid']").each(function(index, obj){
					$(this).removeAttr("onclick");
					$(this).next().css('color','');
				});
			 }else if(v==1) {
				 $("input[name='moduleid']").each(function(index, obj){
						$(this).removeAttr("onclick");
						$(this).next().css('color','');
					});
			 }else {
				 $("input[name='moduleid']").each(function(index, obj){
						$(this).removeAttr("checked");
						$(this).next().css('color','gray');
						$(this).attr('onclick', 'return false');
					});
			 }
		 }); 
	})
</script>
</body>
</html>