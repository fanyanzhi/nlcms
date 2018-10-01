<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.cnki.com/tag" prefix="my" %>
<%@ include file="../TagLib.jsp"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="../Pub.jsp" %>
<meta http-equiv="content-type" content="text/html;charset=UTF-8" />
</head>
<body>
<div>
	<form id="adminForm">
		<table width="100%" align="center">
			<tr>
				<td width="20%" align="right">用户名：</td>
				<td width="30%"><input type="text" id="username" style="width:210px" name="username" class="easyui-validatebox" required data-options="validType:['username']" /></td>
			</tr>
			
			<tr>	
				<td width="20%" align="right">登录密码：</td>
				<td width="30%"><input type="text" id="password" style="width:210px" name="password" class="easyui-validatebox" required data-options="validType:['psword']" /></td>
			</tr>
			
			<tr>
				<td width="20%" align="right">确认密码：</td>
				<td width="30%"><input type="text" id="repassword" style="width:210px" class="easyui-validatebox" data-options="required:true,validType:'eqPwd[\'#adminForm input[name=password]\']'"></td>
			</tr>
			
			<tr>	
				<td width="20%" align="right">管理员姓名：</td>
				<td width="30%"><input type="text" id="staffname" style="width:210px" name="staffname" class="easyui-validatebox" data-options="validType:['special','maxLength[10]']" /></td>
			</tr>
			
			<tr>	
				<td width="20%" align="right">所属部门：</td>
				<td width="30%"><input type="text" id="staffdept" style="width:210px" name="staffdept" class="easyui-validatebox" data-options="validType:['special','maxLength[16]']" /></td>
			</tr>
			
			<tr>	
				<td width="20%" align="right">办公电话：</td>
				<td width="30%"><input type="text" id="staffphone" style="width:210px" name="staffphone" class="easyui-validatebox" data-options="validType:['specialx','maxLength[20]']"  /></td>
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
			<a id="saveBtn" class="easyui-linkbutton" data-options="iconCls:'icon-add'" onclick="saveb()">保存</a>
		</div>
		
		<input type="hidden" id="module" name="module">
	</form>
</div>

<script>
	
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
		
		var subData = $("#adminForm").serialize();
		//检查用户名存在 
		var username = $("#username").val();
			
			$.ajax({
				url : '${ctx}/session/admin/checkUsername?r='+Math.random(),
				data : "username="+username,
				type : 'POST',
				success : function(data) {
					var res = eval("("+data+")");
					if(res.result == false) {
						$("#saveBtn").attr('onclick', 'saveb()');
						alert("用户名已存在");
					}else if(res.result == true) {
						$.ajax({
							url : '${ctx}/session/admin/insertAdmin?r='+Math.random(),
							data : subData,
							type : 'POST',
							success : function(data) {
								var res = eval("("+data+")");
								if(res.result == false) {
									$("#saveBtn").attr('onclick', 'saveb()');
									alert("操作失败");
								}else if(res.result == true) {
									alert("操作成功");
									insertLog("新增管理员"+username);
									var t = parent.index_tabs.tabs('getTab','管理员列表');
									if(t) {
										var url = $(t.panel('options').content).attr('src');
										if(url) {
											parent.index_tabs.tabs('update', {
												tab : t,
												options : {
													content : '<iframe src="' + url + '" frameborder="0" style="border:0;width:100%;height:98%;"></iframe>'
												}
											});
										}
									}
									var currTab = parent.index_tabs.tabs('getSelected');
									parent.index_tabs.tabs('close', currTab.panel('options').title);
								}
							}
						}); 
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