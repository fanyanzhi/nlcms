<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../TagLib.jsp"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="../Pub.jsp" %>
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
		
		$("#upload").upload({
			action : "${ctx }/session/ads/upload",
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
					$("#picsrc").val(res.picname);
					var html = '<img style="width: 30px; height: 30px;" onclick=amp("'+res.picname+'") src="'+res.picname+'"/><br><span style="font-size:10px;color:#ccc">大小：'+res.size+'</span>';
					$("#s1").empty().append(html);
				}else {
					alert("图片上传失败");
				}
			}
		})
		
		
		$("#picsrc").val("${nlcads.src}");
		var picsrc = "${nlcads.src}";
		if(picsrc) {
			var htmlz = '<img style="width: 30px; height: 30px;" onclick=amp("'+picsrc+'") src="'+picsrc+'"/>';
			$("#s1").empty().append(htmlz);
		}
		
		
		$("#saveBtn").click(function(){
			//校验
			var isvalid = $("#form1").form('validate');
			if(!isvalid) {
				return ;
			}
			$("#saveBtn").unbind();
			
			var id = "${nlcads.id}";
			var name = $("#name").val();
			var seq = $('#seq').numberbox('getValue');
			var url = $("#url").val();
			var status = $('#status').combobox('getValue');
			var src = $("#picsrc").val();
			var type = "${nlcads.type}";

			// type   0专题，1广告
			 $.ajax({
				url: "${ctx}/session/ads/updateAds",
				data: {"name":name, "seq":seq, "url":url, "status":status, "src":src, "id":id, "type":type},
				cache: false,
	            type: "POST",
	            success: function(data){
	            	var res = eval("("+data+")");
					if(res.result == false) {
						alert("操作失败");
					}else if(res.result == true) {
						alert("操作成功");
						insertLog("修改广告 " + name);
						parent.tab.datagrid('reload');
						parent.closeWin();
					}
	            }
			}) 
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
	
	
	function amp(purl) {
		window.open(purl,null,"height=800,width=800,status=yes,toolbar=no,menubar=no,location=no");
	}
</script>
</head>
<body>
<div>
<form id="form1" action="" method="POST">
	<table align="center" width="80%" border="0">
		<tr>
			<td width="20%" align="right">分类：</td>
			<td >
				<c:if test="${nlcads.type == 1}">广告</c:if>
				<c:if test="${nlcads.type == 0}">专题</c:if>
			</td>
		</tr>
		
		<tr>
			<td width="20%" align="right">名称：</td>
			<td ><input type="text" id="name" style="width:210px" name="name" value="${nlcads.name }" class="easyui-validatebox" required data-options="validType:['specialy','isBlank','length[0,330]']"/></td>
		</tr>
		
		<tr>
			<td width="20%" align="right">排序：</td>
			<td ><input type="text" id="seq" name="seq" class="easyui-numberbox" data-options="required:true,max:100,min:0" value="${nlcads.seq }"/></td>
		</tr>
		
		<tr>
			<td width="20%" align="right">链接地址：</td>
			<td >
				<c:if test="${nlcads.type == 0}"><input type="text" id="url" style="width:210px;background:#F4F4F4;" name="url" readonly="readonly" value="${nlcads.url }"/></c:if>
				<c:if test="${nlcads.type == 1}"><input type="text" id="url" style="width:210px;" name="url" value="${nlcads.url }" class="easyui-validatebox" data-options="validType:['specialz','length[0,200]']" /></c:if>
			</td>
		</tr>
		
		<tr>
			<td width="20%" align="right">状态：</td>
			<td >
				<!-- 0下架，1上架 -->
				<select name="status" id="status" class="easyui-combobox" data-options="width:150,height:29,editable:false,panelHeight:50">
					<option value="0" <c:if test="${nlcads.status == 0}">selected="selected"</c:if>>下架</option>
					<option value="1" <c:if test="${nlcads.status == 1}">selected="selected"</c:if>>上架</option>
				</select>
			</td>
		</tr>
		
		<tr>
			<td width="20%" align="right">图片：</td>
			<td ><span style="font-size:12px;color:#ccc">(最大5M，尺寸不低于宽720px， 高260px)</span>&nbsp;</td>
		</tr>
		
		<tr>
			<td width="20%" align="right"></td>
			<td ><input type="button" id="upload" value="上传图片" ><span id="s1"></span></td>
		</tr>
	</table>
	
	<input type="hidden" name="src" id="picsrc">
	<br/>
</form>
	<div align="center">
		<a id="saveBtn" class="easyui-linkbutton" data-options="iconCls:'icon-add'">保存</a>
	</div>
</div>
</body>
</html>