<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../TagLib.jsp"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="../Pub.jsp" %>
<meta http-equiv="content-type" content="text/html;charset=UTF-8" />
</head>
<body>
<div align="center">
	<form id="messageForm">
		<table border="0" align="center" width="90%" >
			<tr>
				<td width="20%" align="right">内容发布时间：</td>
				<td>
					<input type="text" id="wjdate" name="wjdate" class="easyui-datebox"/>
				</td>
			</tr>
			
			<tr>
				<td width="20%" align="right">来源：</td>
				<td>
					<select id="source" name="source" style="width:210px;">   
    					<option value="手机门户">手机门户</option>   
    					<option value="原创">原创</option>   
					</select>
				</td>
			</tr>
			
			<tr>
				<td width="20%" align="right">状态：</td>
				<td>
					<select id="status" name="status" style="width:210px;">   
    					<option value="已发布">已发布</option>   
    					<option value="未发布">未发布</option>   
					</select>
				</td>
			</tr>
		
			<tr>
				<td width="20%" align="right">诗词原文：</td>
				<td>
					<input type="text" id="shiju" style="width:550px" name="shiju" class="easyui-validatebox" required data-options="validType:['isBlank','length[0,100]']"/>
				</td>
			</tr>
			
			<tr>
				<td width="20%" align="right">诗词来源：</td>
				<td>
					<input type="text" id="sjsource" style="width:550px" name="sjsource" class="easyui-validatebox" data-options="validType:['length[0,100]']"/>
				</td>
			</tr>
			
			<tr>
				<td width="20%" align="right">诗词译文：</td>
				<td>
					<input type="text" id="sjyiwen" style="width:550px" name="sjyiwen" class="easyui-validatebox" data-options="validType:['length[0,200]']"/>
				</td>
			</tr>
			
			<tr>
				<td width="20%" align="right">全诗：</td>
				<td>
					<input type="text" id="quanshi" style="width:550px" name="quanshi" class="easyui-validatebox" data-options="validType:['length[0,500]']"/>
				</td>
			</tr>
			
			<tr>
				<td width="20%" align="right">诗词音频url：</td>
				<td>
					<input type="text" id="sjurl" style="width:550px" name="sjurl" class="easyui-validatebox" data-options="validType:['specialz', 'length[0,100]']"/>
				</td>
			</tr>
			
			<tr>
				<td width="20%" align="right">格言原文：</td>
				<td>
					<input type="text" id="geyan" style="width:550px" name="geyan" class="easyui-validatebox" required data-options="validType:['isBlank','length[0,100]']"/>
				</td>
			</tr>
			
			<tr>
				<td width="20%" align="right">格言来源：</td>
				<td>
					<input type="text" id="gysource" style="width:550px" name="gysource" class="easyui-validatebox" data-options="validType:['length[0,100]']"/>
				</td>
			</tr>
			
			<tr>
				<td width="20%" align="right">格言译文：</td>
				<td>
					<input type="text" id="gyyiwen" style="width:550px" name="gyyiwen" class="easyui-validatebox" data-options="validType:['length[0,200]']"/>
				</td>
			</tr>
		</table>
	</form>
	
	<br/>
	<div align="center">
		<a id="saveBtn" class="easyui-linkbutton" data-options="iconCls:'icon-add'" onclick="saveb()">保存</a>
		<a id="previewBtn" onclick="previewInfo()" class="easyui-linkbutton" data-options="iconCls:'icon-search'">预览</a>
	</div>
</div>

<script>

	$(function(){
		//时间框禁止输入
		$(".datebox :text").attr("readonly","readonly");
	})
	
	function saveb() {
		//校验
		var isvalid = $("#messageForm").form('validate');
		if(!isvalid) {
			return ;
		}
		
		var wjdate = $("#wjdate").datetimebox('getValue');
		if(!wjdate) {
			alert("内容发布时间必填!");
			return ;
		}
		
		$("#saveBtn").removeAttr('onclick');
		
		var source = $("#source").val();
		var status = $("#status").val();
		var shiju = $("#shiju").val();
		var sjsource = $("#sjsource").val();
		var sjyiwen = $("#sjyiwen").val();
		var sjurl = $("#sjurl").val();
		var quanshi = $("#quanshi").val();
		var geyan = $("#shiju").val();
		var gysource = $("#gysource").val();
		var gyyiwen = $("#gyyiwen").val();
		
		$.ajax({
            url: "${ctx}/session/wjreader/save",
            data: {"wjdate":wjdate, "source":source, "status":status, "shiju":shiju, "sjsource":sjsource, "sjyiwen":sjyiwen, "sjurl":sjurl, "quanshi":quanshi, "geyan":geyan, "gysource":gysource, "gyyiwen":gyyiwen},
            cache: false,
            type: "POST",
            success: function(data){
            	var res = eval("("+data+")");
				if(res.result == false) {
					alert("操作失败");
				}else if(res.result == true) {
					alert("操作成功");
					insertLog("添加文津 " + shiju);
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
	
	var titles = "";
	var introduces = "";
	//预览
	function previewInfo() {
		titles = $("#title").val();
		if (navigator.userAgent.indexOf('Firefox') >= 0){
			window.showModalDialog ('${ctx}/session/wjreader/addPreview', '预览', 'dialogHeight=680px; dialogWidth=340px');
		} else {
			window.open('${ctx}/session/wjreader/addPreview', null,"height=680,width=340,status=yes,toolbar=no,menubar=no,location=no");
		}
	}
	
</script>
</body>
</html>
