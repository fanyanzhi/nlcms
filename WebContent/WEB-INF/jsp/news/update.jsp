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
		<input type="hidden" name="src" id="aa">
	
		<table>
			<tr>
				<td width="5%" align="right">标题：</td>
				<td width="10%">
					<input type="text" id="title" style="width:210px" name="title" value="${nlcnews.title }" class="easyui-validatebox" required data-options="validType:['specialy','isBlank','length[0,200]']" />
				</td>
				
				<td width="8%" align="right">标题图片：<br/><span style="font-size:12px;color:#ccc">(最大5M)</span>&nbsp;&nbsp;&nbsp;</td>
				<td width="10%" align="left">
					<input type="button" id="up" value="上传图片" >
					<span id="s1"></span>
					<input type="button" id="clear" value="清除" >
				</td>
				
				<td width="10%" align="right">推送方式：</td>
				<td width="10%">
					<input type="checkbox" name="pushmethod" value="0"/>弹窗
					<input type="checkbox" name="pushmethod" value="1"/>站内信
				</td>
				
				<td width="10%" align="right">内容发布时间：</td>
				<td width="10%">
					<input type="text" id="pubTime" name="pubTime" class="easyui-datetimebox" value="<fmt:formatDate value='${nlcnews.pubTime}' pattern='yyyy-MM-dd HH:mm:ss'/>"/>
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
			var strArr = "${nlcnews.pushmethod}".split(",");
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
		
		//上传标题图片
		$("#up").upload({
			action : "${ctx }/session/template/upload",
			name : "file",
			onSelect: function () {
                this.autoSubmit = false;
                var allowSize = 5*1024*1024;// 5M 
                var size = $("input[name='file']")[0].files[0].size;
                var obj_type = $("input[name='file']")[0].files[0].type;
                
                if((obj_type.indexOf("png")>-1) || (obj_type.indexOf("jpg")>-1) || (obj_type.indexOf("jpeg")>-1) || (obj_type.indexOf("gif")>-1) || (obj_type.indexOf("bmp")>-1)) {
                	this.submit();
                }else {
                	alert("非图片格式!");
                	return ;
                }
                
                if(size >= allowSize) {
                	alert("图片大小超过5M");
                } else if(size == 0) {
                	alert("图片大小为0！");
                } else {
                	this.submit();
                }
            },
			onComplete : function(data, self, element){
				var res = eval("("+data+")");
				if(res.result) {
					$("#aa").val(res.picname);
					var html = '<img style="width: 50px; height: 50px;" onclick=amp("'+res.picname+'") src="'+res.picname+'"/><br><span style="font-size:10px;color:#ccc">大小：'+res.size+'</span>';
					$("#s1").empty().append(html);
				}else {
					alert("图片上传失败");
				}
			}
		})
		
		var z1 = "${nlcnews.src}";
		if(z1) {
			$("#aa").val(z1);
			var html1 = '<img style="width: 30px; height: 30px;" onclick=amp("'+z1+'") src="'+z1+'"/>';
			$("#s1").empty().append(html1);
		}
		
		$("#clear").click(function(){
			$("#s1").empty();
			$("#aa").val('');
		})
		
		
		//保存按钮的点击事件
		$("#saveBtn").click(function(){
			//校验
			var isvalid = $("#adminForm").form('validate');
			if(!isvalid) {
				return ;
			}
			
			var pubTime = $("#pubTime").datetimebox('getValue');
			if(!pubTime) {
				alert("内容发布时间必填!");
				return ;
			}
			
			$("#saveBtn").unbind();
			
			var title = $("#title").val();
			var content = ue.getContent();
			var id = "${nlcnews.id}";
			var src = $("#aa").val();
			//=============推送
			var parr =[]; 
			$('input[name="pushmethod"]:checked').each(function(){ 
				parr.push($(this).val()); 
			}); 
			
			$.ajax({
                url: "${ctx}/session/news/updateNews?r=" + Math.random(),
                data: {"content":content, "title":title, "id":id, "src":src, "pushmethod":parr.join(), "pubTime":pubTime},
                cache: false,
                type: "POST",
                success: function(data){
                	var res = eval("("+data+")");
					if(res.result == false) {
						alert("操作失败");
					}else if(res.result == true) {
						alert("操作成功");
						insertLog("修改新闻 " +title);
						parent.tab.datagrid('reload');
						parent.closeWin();
					}
                }
			});
		})	
		
		ue.addListener("ready", function () {
			var s = '${nlcnews.content}'.replace(/\"/g,"'"); 
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
			window.showModalDialog ('${ctx}/session/news/addPreview', '预览', 'dialogHeight=680px; dialogWidth=340px');
		} else {
			window.open('${ctx}/session/news/addPreview', null,"height=680,width=340,status=yes,toolbar=no,menubar=no,location=no");
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
	
	function amp(purl) {
		window.open(purl,null,"height=800,width=800,status=yes,toolbar=no,menubar=no,location=no");
	}
	
</script>
</body>
</html>
