<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../TagLib.jsp"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="../Pub.jsp" %>
<meta http-equiv="content-type" content="text/html;charset=UTF-8" />
<script type="text/javascript" src="${ctx}/ueditor/ueditor.config.js"></script>
<script type="text/javascript" src="${ctx}/ueditor/ueditor.all.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery.ocupload-1.1.2.js"></script>
</head>
<body>
<div>
	<form id="adminForm">
		<table align="center" width="90%" border="0">
			<tr>
				<td width="10%" align="right">题目：</td>
				<td width="40%">
					<input type="text" id="question" style="width:300px" name="question" class="easyui-validatebox" required data-options="validType:['specialy','isBlank','length[0,200]']" />
				</td>
				
				<td width="10%" align="right">状态：</td>
				<td width="15%">
					<select name="status" id="status" class="easyui-combobox" data-options="width:100,height:29,editable:false,panelHeight:50">
								<option value="已发布">已发布</option>
								<option value="未发布">未发布</option>
					</select>
				</td>
				
				<td width="10%" align="right">排序：</td>
				<td width="15%">
					<input type="text" id="cseq" name="cseq" class="easyui-numberbox" data-options="required:true,max:1000,min:1"/>
				</td>
			</tr>
		</table>
		<br/>
		<div>
			<script id="editor" type="text/plain" style="height:400px;"></script>
		</div>
	</form>
	
	<br/>
	<div align="center">
		<a id="resBtn" class="easyui-linkbutton" data-options="iconCls:'icon-undo'">重置</a>
		<a id="saveBtn" class="easyui-linkbutton" data-options="iconCls:'icon-add'" onclick="saveb()">保存</a>
		<a id="previewBtn" onclick="previewInfo()" class="easyui-linkbutton" data-options="iconCls:'icon-search'">预览</a>
	</div>
</div>

<script>
	//实例化编辑器
	var ue = UE.getEditor('editor');

	$(function(){
		//重置按钮的点击事件
		$("#resBtn").click(function(){
			ue.setContent("");
		})
	})
	
	function saveb() {
		//校验
		var isvalid = $("#adminForm").form('validate');
		if(!isvalid) {
			return ;
		}
		
		$("#saveBtn").removeAttr('onclick');
		var question = $("#question").val();
		var status = $('#status').combobox('getValue');
		var cseq = $('#cseq').numberbox('getValue');
		var answer = ue.getContent();
		
		$.ajax({
            url: "${ctx}/session/faq/savefaq",
            data: {"question":question, "status":status, "cseq":cseq, "answer":answer},
            cache: false,
            type: "POST",
            success: function(data){
            	var res = eval("("+data+")");
				if(res.result == false) {
					$("#saveBtn").attr('onclick', 'saveb()');
					alert("操作失败");
				}else if(res.result == true) {
					alert("操作成功");
					insertLog("添加常见问题 " + question);
					parent.tab.datagrid('reload');
					parent.closeWin();
				}
            }
		});
	}
	
	var questions = "";
	var answers = "";
	//预览
	function previewInfo() {
		questions = $("#question").val();
		answers = ue.getContent();
		if (navigator.userAgent.indexOf('Firefox') >= 0){
			window.showModalDialog ('${ctx}/session/faq/addPreview', '预览', 'dialogHeight=680px; dialogWidth=340px');
		} else {
			window.open('${ctx}/session/faq/addPreview', null,"height=680,width=340,status=yes,toolbar=no,menubar=no,location=no");
		}
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
	
</script>
</body>
</html>
