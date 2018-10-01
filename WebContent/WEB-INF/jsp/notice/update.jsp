<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../TagLib.jsp"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="../Pub.jsp" %>
<meta http-equiv="content-type" content="text/html;charset=UTF-8" />
<script type="text/javascript" src="${ctx}/ueditor/ueditor.config.js"></script>
<script type="text/javascript" src="${ctx}/ueditor/ueditor.all.js"></script>
</head>
<body>
<div>
	<form id="adminForm">
		<table>
			<tr>
				<td width="10%" align="right">标题：</td>
				<td width="20%">
					<input type="text" id="title" style="width:210px" name="title" value="${nlcnotice.title }" class="easyui-validatebox" required data-options="validType:['specialy','isBlank','length[0,200]']" />
				</td>
				
				
				
				<td width="10%" align="right">推送方式：</td>
				<td width="15%">
					<input type="checkbox" name="pushmethod" value="0"/>弹窗
					<input type="checkbox" name="pushmethod" value="1"/>站内信
				</td>
				
				<td width="10%" align="right">内容发布时间：</td>
				<td width="30%">
					<input type="text" id="pubTime" name="pubTime" class="easyui-datetimebox" data-options="required:true" value="<fmt:formatDate value='${nlcnotice.pubTime}' pattern='yyyy-MM-dd HH:mm:ss'/>"/>
				</td>
			</tr>
		</table>
		<br/>
		<div>
			<script id="editor" type="text/plain" style="height:300px;"></script>
		</div>
	</form>
	
	<br/>
	<div align="center">
		<a id="resBtn" class="easyui-linkbutton" data-options="iconCls:'icon-undo'">重置</a>
		<a id="saveBtn" class="easyui-linkbutton" data-options="iconCls:'icon-add'">保存</a>
		<a id="previewBtn" onclick="previewInfo()" class="easyui-linkbutton" data-options="iconCls:'icon-search'">预览</a>
	</div>
</div>

<script>

	//实例化编辑器
	var ue = UE.getEditor('editor');

	$(function(){
		//推送方式写入值
		$("input[name='pushmethod']").each(function(index, obj){
			var strArr = "${nlcnotice.pushmethod}".split(",");
			for(var i = 0; i < strArr.length; i++) {
				if(this.value == strArr[i]) {
					$(this).attr("checked", "checked");
					//$(this).attr("onclick", "return false");
				}
			}
		});
		
		//时间框禁止输入
		$(".datebox :text").attr("readonly","readonly");
		
		//重置按钮的点击事件
		$("#resBtn").click(function(){
			ue.setContent("");
		})
		
		//保存按钮的点击事件
		$("#saveBtn").click(function(){
			//校验
			var isvalid = $("#adminForm").form('validate');
			if(!isvalid) {
				return ;
			}
			$("#saveBtn").unbind();
			
			var title = $("#title").val();
			var pubTime = $("#pubTime").datetimebox('getValue');
			var content = ue.getContent();
			var id = "${nlcnotice.noticeid}";
			//=============推送
			var parr =[]; 
			$('input[name="pushmethod"]:checked').each(function(){ 
				parr.push($(this).val()); 
			}); 
			
			$.ajax({
                url: "${ctx}/session/notice/updateNotice?r=" + Math.random(),
                data: {"content":content, "title":title, "id":id, "pushmethod":parr.join(), "pubTime":pubTime},
                cache: false,
                type: "POST",
                success: function(data){
                	var res = eval("("+data+")");
					if(res.result == false) {
						alert("操作失败");
					}else if(res.result == true) {
						alert("操作成功");
						insertLog("修改公告 " + title);
						parent.tab.datagrid('reload');
						parent.closeWin();
					}
                }
			});
		})	
		
		ue.addListener("ready", function () {
			var s = '${nlcnotice.content}'.replace(/\"/g,"'"); 
			ue.setContent(s);
		});
	})
	
	var titles = "";
	var introduces = "";
	//预览
	function previewInfo() {
		titles = $("#title").val();
		introduces = ue.getContent();
		if (navigator.userAgent.indexOf('Firefox') >= 0){
			window.showModalDialog ('${ctx}/session/notice/addPreview', '预览', 'dialogHeight=680px; dialogWidth=340px');
		} else {
			window.open('${ctx}/session/notice/addPreview', null,"height=680,width=340,status=yes,toolbar=no,menubar=no,location=no");
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
