<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../TagLib.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<script>window.UEDITOR_HOME_URL="${ctx}/ueditor/"</script>
<link href="${ctx}/ueditor/themes/default/css/ueditor.css" type="text/css" rel="stylesheet">
<script type="text/javascript" src="${ctx}/ueditor/ueditor.config.js"></script>
<script type="text/javascript" src="${ctx}/ueditor/ueditor.all.js"></script>
<script type="text/javascript" src="${ctx}/ueditor/lang/zh-cn/zh-cn.js"></script>
<link href="${ctx}/css/btn.css" type="text/css" rel="stylesheet">
</head>
<script type="text/javascript">
	$(function() {
		
		$('#subjectContentForm').form({
			url : '${ctx}/session/subjectcatalog/saveContent',
			onSubmit : function() {
				var id = $("#tsid").val();
				if("root" == id || !id) {
					alert("根目录不可保存内容");
					return false;
				}
				progressLoad();
				var isValid = $(this).form('validate');
				if (!isValid) {
					progressClose();
				}
				return isValid;
			},
			success : function(data) {
				progressClose();
				var res = eval("("+data+")");
				if (res.result) {
					var tx = $("#tstext").val();
					insertLog("编辑特色专题 ${title}的目录 " + tx +"的内容");
					alert("保存成功！");
				}
			}
		});

	});
	function ziyuanFun() {
		window.showModalDialog ('', '资源', 'dialogHeight=800px; dialogWidth=1200');
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

<div style="padding: 2px;">
	<form id="subjectContentForm" method="post">
		<table class="grid">
			<tr>
				<td>
					<a onclick="saveContent();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon_save'">保存</a>
				</td>
				<td>
					<!-- <a onclick="ziyuanFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon_save'">资源</a> -->
					<div style="display:none;">
						<textarea name="content" class="easyui-validatebox"  style="height:10px;"></textarea>
					</div>
					<input id="tsid" name="catalogid" type="hidden" >
					<input id="tstext" type="hidden" />
				</td>
			</tr>
		</table>
	</form>
</div>

<!--style给定宽度可以影响编辑器的最终宽度-->
<script type="text/plain" id="myEditor" style="width:90%;height:400px;">
	<p>请输入目录内容...</p>
</script>


<div class="clear"></div>
<script type="text/javascript">
	//实例化编辑器
	var ue = UE.getEditor('myEditor');
	
	$(function(){
		ue.addListener('blur',function(){
	        getContent();
	    });
		
		ue.addListener("ready", function () {
			setDisabled();
		});
	})
	
	//设置当前区域不可编辑
	function setDisabled() {
		UE.getEditor('myEditor').setDisabled('fullscreen');
	}
		
	//获得编辑器内容
	function getContent() {
	    var arr = [];
	    var textContent = UE.getEditor('myEditor').getContent();
	    arr.push(textContent);
	    var content = arr.join("\n");
		$("textarea[name='content']").val(content);
	}
	    
	function setContent(value){
	    UE.getEditor('myEditor').setContent(value, false);
	}

	//设置当前区域可编辑
	function setEnabled() {
	    UE.getEditor('myEditor').setEnabled();
	} 	
	
</script>
</html>