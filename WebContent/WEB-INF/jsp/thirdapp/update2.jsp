<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../TagLib.jsp"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="../Pub.jsp" %>
<link rel="stylesheet" type="text/css" href="${ctx}/css/cover.css" />
<script type="text/javascript" src="${ctx}/js/jquery.ocupload-1.1.2.js"></script>
<meta http-equiv="content-type" content="text/html;charset=UTF-8" />
	

<style>
.btn_blue{ background:#429cff; color:#FFF; }
.btn_gray{ background:#a6b3ba; color:#FFF;}
.btn{text-transform: uppercase; font-size:12px; font-family:"微软雅黑";
	  height: 28px;
	  margin: 0;
	  overflow: visible; padding:0 18px; border:none;
	  cursor:pointer;vertical-align: middle;
}
</style>
<script type="text/javascript">
	$(function(){
		
		$("#upload1").upload({
			action : "${ctx }/session/ads/upload",
			name : "file",
			onSelect: function () {
                this.autoSubmit = false;
                var allowSize = 5*1024*1024;// 5M 
                var size = $("input[name='file']:eq(0)")[0].files[0].size;
				var obj_type = $("input[name='file']:eq(0)")[0].files[0].type;
                
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
					$("#picsrc1").val(res.picname);
					var html = '<img style="width: 30px; height: 30px;" onclick=amp("'+res.picname+'") src="'+res.picname+'"/><br><span style="font-size:10px;color:#ccc">大小：'+res.size+'</span>';
					$("#s1").empty().append(html);
				}else {
					alert("图片上传失败");
				}
			}
		})
		
		$("#picsrc1").val("${thirdapp.iconurl}");
		var picsrcs = "${thirdapp.iconurl}";
		if(picsrcs) {
			var htmlz = '<img style="width: 30px; height: 30px;" onclick=amp("'+picsrcs+'") src="'+picsrcs+'"/>';
			$("#s1").empty().append(htmlz);
		}
		
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
	
	function amp(purl) {
		window.open(purl,null,"height=800,width=800,status=yes,toolbar=no,menubar=no,location=no");
	}
	
	function stripscript(s) {
		if(s) {
			var pattern = new RegExp("[`~!@#$^&*()=|{}':;',\\[\\].<>/?~！@#￥……&*（）——|{}【】‘；：”“'。，、？]") 
			var rs = ""; 
			for (var i = 0; i < s.length; i++) { 
				rs = rs+s.substr(i, 1).replace(pattern, ''); 
			} 
			return rs;
		}else {
			return "";
		}
	}
	
	function saveb() {
		//校验
		var isvalid = $("#form1").form('validate');
		if(!isvalid) {
			return ;
		}
		
		$("#saveBtn").removeAttr('onclick');
		var id = "${thirdapp.id}";
		var name = $("#name").val();
		var version = $("#version").val();
		var status = $('#status').combobox('getValue');
		var size = $("#size").val();
		
		var downurl = $("#downurl1").val();
		var iconurl = $("#picsrc1").val();
		
		$.ajax({
			url: "${ctx}/session/thirdapp/updateapp",
			data: {"id":id, "name":name, "version":version, "status":status, "downurl":downurl, "iconurl":iconurl, "size":size},
			cache: false,
            type: "POST",
            success: function(data){
            	var res = eval("("+data+")");
				if(res.result == false) {
					$("#saveBtn").attr('onclick', 'saveb()');
					alert("操作失败");
				}else if(res.result == true) {
					alert("操作成功");
					insertLog("编辑其他应用程序 " + name);
					parent.tab.datagrid('reload');
					parent.closeWin();
				}
            }
		})
		
		
	}
</script>
</head>
<body>
<div>
<form id="form1" action="" method="POST">
	<table align="center" width="98%" border="0">
		<tr>
			<td width="10%" align="right">系统：</td>
			<td width="15%">
				苹果
			</td>
			
			<td width="5%" align="right">名称：</td>
			<td width="15%"><input type="text" id="name" name="name" value="${thirdapp.name }" class="easyui-validatebox" required data-options="validType:['specialy','isBlank','length[0,50]']"/></td>
		
			<td width="10%" align="right">版本：</td>
			<td width="15%"><input type="text" id="version" name="version" value="${thirdapp.version }" class="easyui-validatebox" required data-options="validType:['specialy','isBlank','length[0,20]']"/></td>
		
			<td width="5%" align="right">大小：</td>
			<td width="15%"><input type="text" id="size" name="size" value="${thirdapp.size}" class="easyui-validatebox" required data-options="validType:['specialy','isBlank','length[0,20]']"/></td>
		
			<td width="5%" align="right">状态：</td>
			<td width="10%">
				<select name="status" id="status" class="easyui-combobox" data-options="width:100,height:29,editable:false,panelHeight:50">
						<option value="1" <c:if test="${thirdapp.status == 1}">selected="selected"</c:if>>发布</option>
						<option value="0" <c:if test="${thirdapp.status == 0}">selected="selected"</c:if>>下架</option>
				</select>
			</td>
		</tr>
		
		<tr id="iostr">
			<td width="10%" align="right">应用程序url：</td>
			<td colspan="3" id="ttt">
				<input type="text" id="downurl1" style="width:250px"  value="${thirdapp.downurl }" class="easyui-validatebox" required data-options="validType:['specialz','length[0,200]']" />
			</td>
			
		
			<td width="10%" align="right">程序图标：</td>
			<td colspan="5">
				<input type="button" id="upload1" value="上传" ><span id="s1"></span>
			</td>
		</tr>
	</table>
	
	<input type="hidden" name="iconurl1" id="picsrc1">
	<br/>
</form>
	<div align="center">
		<a id="saveBtn" class="easyui-linkbutton" data-options="iconCls:'icon-add'" onclick="saveb()">保存</a>
	</div>
</div>

<div class="overlay"></div>
<div id="AjaxLoading" class="showbox">
	<div class="loadingWord">
		<img src="${ctx }/images/loading.gif" />
		<span id="loading"></span>
	</div>
</div>
</body>
</html>