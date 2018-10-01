<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../TagLib.jsp"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="../Pub.jsp" %>
<meta http-equiv="content-type" content="text/html;charset=UTF-8" />

<style type="text/css">
th,td{height:40px;}
table{text-align:center;color:#222; font:15px/20px Arial,"宋体";  background-color:#fff;}
</style>
</head>
<body>
<div>
<br/>
<br/>
	<table border="1" width="50%" align="center"  bordercolor="#a0c6e5" style="border:1;border-collapse:collapse;">
		<tr>
			<th width="35%" align="center">读者信息类别</th>
			<th width="40%" align="center">推送方式设置</th>
			<c:if test="${LoginObj.role != 2}">
			<th width="25%" align="center">操作</th>
			</c:if>
		</tr>
		
		<tr id="tr1">
			<td width="35%" align="center">交易信息记录</td>
			<td width="40%" align="center">
				<input type="checkbox" name="pushmethod" value="0"/>弹窗
				<input type="checkbox" name="pushmethod" value="1"/>站内信
			</td>
			<c:if test="${LoginObj.role != 2}">
			<td width="25%" align="center">
				<a onclick="bt1()" class="easyui-linkbutton" data-options="iconCls:'icon-add'">保存</a>
			</td>
			</c:if>
		</tr>
		
		<tr id="tr2">
			<td width="35%" align="center">图书催还通知</td>
			<td width="40%" align="center">
				<input type="checkbox" name="pushmethod" value="0"/>弹窗
				<input type="checkbox" name="pushmethod" value="1"/>站内信
			</td>
			<c:if test="${LoginObj.role != 2}">
			<td width="25%" align="center">
				<a onclick="bt2()" class="easyui-linkbutton" data-options="iconCls:'icon-add'">保存</a>
			</td>
			</c:if>
		</tr>
		
		<tr id="tr3">
			<td width="35%" align="center">新增违约记录</td>
			<td width="40%" align="center">
				<input type="checkbox" name="pushmethod" value="0"/>弹窗
				<input type="checkbox" name="pushmethod" value="1"/>站内信
			</td>
			<c:if test="${LoginObj.role != 2}">
			<td width="25%" align="center">
				<a onclick="bt3()" class="easyui-linkbutton" data-options="iconCls:'icon-add'">保存</a>
			</td>
			</c:if>
		</tr>
		
	</table>
</div>
<script>
$(function(){
	 $("#tr1 input[name='pushmethod']").each(function(index, obj){
		var strArr = "${trade}".split(",");
		for(var i = 0; i < strArr.length; i++) {
			if(this.value == strArr[i]) {
				$(this).attr("checked", "checked");
			}
		}
	});
	
	$("#tr2 input[name='pushmethod']").each(function(index, obj){
		var strArr = "${bookback}".split(",");
		for(var i = 0; i < strArr.length; i++) {
			if(this.value == strArr[i]) {
				$(this).attr("checked", "checked");
			}
		}
	});
	
	$("#tr3 input[name='pushmethod']").each(function(index, obj){
		var strArr = "${breaklaw}".split(",");
		for(var i = 0; i < strArr.length; i++) {
			if(this.value == strArr[i]) {
				$(this).attr("checked", "checked");
			}
		}
	}); 
})

//交易信息记录
function bt1() {
	var parr =[]; 
	$('#tr1 input[name="pushmethod"]:checked').each(function(){ 
		parr.push($(this).val()); 
	}); 
	
	var param = parr.join();
	var reg1 = /0/;
	if(reg1.test(param)) {
		var reg2 = /1/;
		if(!reg2.test(param)) {
			alert("操作失败！选择了弹窗，就必须选择上站内信.");
			return ;
		}
	}
	
	$.ajax({
        url: "${ctx}/session/infosetup/update?r=" + Math.random(),
        data: {"id":"${tradeid}", "pushmethod":param},
        type: "POST",
        success: function(data){
        	var res = eval("("+data+")");
			if(res.result == false) {
				alert("操作失败");
			}else if(res.result == true) {
				insertLog("修改交易信息记录推送方式");
				alert("保存成功");
			}
        }
	});
}

//图书催还通知
function bt2() {
	var parr =[]; 
	$('#tr2 input[name="pushmethod"]:checked').each(function(){ 
		parr.push($(this).val()); 
	}); 
	
	var param = parr.join();
	var reg1 = /0/;
	if(reg1.test(param)) {
		var reg2 = /1/;
		if(!reg2.test(param)) {
			alert("操作失败！选择了弹窗，就必须选择上站内信.");
			return ;
		}
	}
	
	$.ajax({
        url: "${ctx}/session/infosetup/update?r=" + Math.random(),
        data: {"id":"${bookbackid}", "pushmethod":param},
        type: "POST",
        success: function(data){
        	var res = eval("("+data+")");
			if(res.result == false) {
				alert("操作失败");
			}else if(res.result == true) {
				insertLog("修改图书催还通知推送方式");
				alert("保存成功");
			}
        }
	});
}

//新增违约记录
function bt3() {
	var parr =[]; 
	$('#tr3 input[name="pushmethod"]:checked').each(function(){ 
		parr.push($(this).val()); 
	}); 
	
	var param = parr.join();
	var reg1 = /0/;
	if(reg1.test(param)) {
		var reg2 = /1/;
		if(!reg2.test(param)) {
			alert("操作失败！选择了弹窗，就必须选择上站内信.");
			return ;
		}
	}
	
	$.ajax({
        url: "${ctx}/session/infosetup/update?r=" + Math.random(),
        data: {"id":"${breaklawid}", "pushmethod":param},
        type: "POST",
        success: function(data){
        	var res = eval("("+data+")");
			if(res.result == false) {
				alert("操作失败");
			}else if(res.result == true) {
				insertLog("修改新增违约记录推送方式");
				alert("保存成功");
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
		data: {"username":username, "role":role, "ip":loginIp, "time":loginTime, "operate": message},
		success:function(data){
			} 
		});
}

</script>
</body>
</html>
