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
		
		$("#one").upload({
			action : "${ctx }/session/librarymap/upload",
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
					$("#src").val(res.picname);
					var html = '<img style="width: 30px; height: 30px;" onclick=amp("'+res.picname+'") src="'+res.picname+'"/><br><span style="font-size:10px;color:#ccc">大小：'+res.size+'</span>';
					$("#s1").empty().append(html);
				}else {
					alert("图片上传失败");
				}
			}
		})
		
		$("#two").upload({
			action : "${ctx }/session/librarymap/upload",
			name : "file",
			onSelect: function () {
                this.autoSubmit = false;
                var allowSize = 5*1024*1024;// 5M 
                var size = $("input[name='file']:eq(1)")[0].files[0].size;
				var obj_type = $("input[name='file']:eq(1)")[0].files[0].type;
                
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
					$("#src2").val(res.picname);
					var html = '<img style="width: 30px; height: 30px;" onclick=amp("'+res.picname+'") src="'+res.picname+'"/><br><span style="font-size:10px;color:#ccc">大小：'+res.size+'</span>';
					$("#s2").empty().append(html);
				}else {
					alert("图片上传失败");
				}
			}
		})
		
		$("#three").upload({
			action : "${ctx }/session/librarymap/upload",
			name : "file",
			onSelect: function () {
                this.autoSubmit = false;
                var allowSize = 5*1024*1024;// 5M 
                var size = $("input[name='file']:eq(2)")[0].files[0].size;
				var obj_type = $("input[name='file']:eq(2)")[0].files[0].type;
                
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
					$("#src3").val(res.picname);
					var html = '<img style="width: 30px; height: 30px;" onclick=amp("'+res.picname+'") src="'+res.picname+'"/><br><span style="font-size:10px;color:#ccc">大小：'+res.size+'</span>';
					$("#s3").empty().append(html);
				}else {
					alert("图片上传失败");
				}
			}
		})
		
		$("#four").upload({
			action : "${ctx }/session/librarymap/upload",
			name : "file",
			onSelect: function () {
                this.autoSubmit = false;
                var allowSize = 5*1024*1024;// 5M 
                var size = $("input[name='file']:eq(3)")[0].files[0].size;
				var obj_type = $("input[name='file']:eq(3)")[0].files[0].type;
                
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
					$("#src4").val(res.picname);
					var html = '<img style="width: 30px; height: 30px;" onclick=amp("'+res.picname+'") src="'+res.picname+'"/><br><span style="font-size:10px;color:#ccc">大小：'+res.size+'</span>';
					$("#s4").empty().append(html);
				}else {
					alert("图片上传失败");
				}
			}
		})
		
		$("#five").upload({
			action : "${ctx }/session/librarymap/upload",
			name : "file",
			onSelect: function () {
                this.autoSubmit = false;
                var allowSize = 5*1024*1024;// 5M 
                var size = $("input[name='file']:eq(4)")[0].files[0].size;
				var obj_type = $("input[name='file']:eq(4)")[0].files[0].type;
                
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
					$("#src5").val(res.picname);
					var html = '<img style="width: 30px; height: 30px;" onclick=amp("'+res.picname+'") src="'+res.picname+'"/><br><span style="font-size:10px;color:#ccc">大小：'+res.size+'</span>';
					$("#s5").empty().append(html);
				}else {
					alert("图片上传失败");
				}
			}
		})
		
		$("#six").upload({
			action : "${ctx }/session/librarymap/upload",
			name : "file",
			onSelect: function () {
                this.autoSubmit = false;
                var allowSize = 5*1024*1024;// 5M 
                var size = $("input[name='file']:eq(5)")[0].files[0].size;
				var obj_type = $("input[name='file']:eq(5)")[0].files[0].type;
                
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
					$("#src6").val(res.picname);
					var html = '<img style="width: 30px; height: 30px;" onclick=amp("'+res.picname+'") src="'+res.picname+'"/><br><span style="font-size:10px;color:#ccc">大小：'+res.size+'</span>';
					$("#s6").empty().append(html);
				}else {
					alert("图片上传失败");
				}
			}
		})
		
		$("#seven").upload({
			action : "${ctx }/session/librarymap/upload",
			name : "file",
			onSelect: function () {
                this.autoSubmit = false;
                var allowSize = 5*1024*1024;// 5M 
                var size = $("input[name='file']:eq(6)")[0].files[0].size;
				var obj_type = $("input[name='file']:eq(6)")[0].files[0].type;
                
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
					$("#src7").val(res.picname);
					var html = '<img style="width: 30px; height: 30px;" onclick=amp("'+res.picname+'") src="'+res.picname+'"/><br><span style="font-size:10px;color:#ccc">大小：'+res.size+'</span>';
					$("#s7").empty().append(html);
				}else {
					alert("图片上传失败");
				}
			}
		})
		
		$("#eight").upload({
			action : "${ctx }/session/librarymap/upload",
			name : "file",
			onSelect: function () {
                this.autoSubmit = false;
                var allowSize = 5*1024*1024;// 5M 
                var size = $("input[name='file']:eq(7)")[0].files[0].size;
				var obj_type = $("input[name='file']:eq(7)")[0].files[0].type;
                
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
					$("#src8").val(res.picname);
					var html = '<img style="width: 30px; height: 30px;" onclick=amp("'+res.picname+'") src="'+res.picname+'"/><br><span style="font-size:10px;color:#ccc">大小：'+res.size+'</span>';
					$("#s8").empty().append(html);
				}else {
					alert("图片上传失败");
				}
			}
		})
		
		
		$("#saveBtn").click(function(){
			//校验
			var isvalid = $("#form1").form('validate');
			if(!isvalid) {
				return ;
			}
			$("#saveBtn").unbind();
			
			var name = $("#name").val();
			var src = $("#src").val();
			var src2 = $("#src2").val();
			var src3 = $("#src3").val();
			var src4 = $("#src4").val();
			var src5 = $("#src5").val();
			var src6 = $("#src6").val();
			var src7 = $("#src7").val();
			var src8 = $("#src8").val();
			var seq = $('#seq').numberbox('getValue');

			$.ajax({
				url: "${ctx}/session/librarymap/addmap",
				data: {"name":name, "src":src, "src2":src2, "src3":src3, "src4":src4, "src5":src5, "src6":src6, "src7":src7, "src8":src8, "seq":seq},
				cache: false,
	            type: "POST",
	            success: function(data){
	            	var res = eval("("+data+")");
					if(res.result == false) {
						alert("操作失败");
					}else if(res.result == true) {
						alert("操作成功");
						insertLog("馆区地图添加 " + name);
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
	<table align="center">
		<tr>
			<td width="20%" align="right">名称：</td>
			<td width="20%"><input type="text" id="name" name="name" class="easyui-validatebox" required data-options="validType:['specialy','isBlank','length[0,30]']"/></td>
			<td width="20%"></td>
			<td width="20%"></td>
			<td width="20%"></td>
		</tr>
		
		<tr>
			<td width="20%" align="right">排序：</td>
			<td width="20%"><input type="text" id="seq" name="seq" class="easyui-numberbox" data-options="required:true,max:100,min:0"/></td>
			<td width="20%"></td>
			<td width="20%"></td>
			<td width="20%"></td>
		</tr>
		
		<tr>
			<td width="20%" align="right">上传选项：<br/><span style="font-size:12px;color:#ccc">(每张图片最大5M)</span></td>
			<td width="20%" align="left"><input type="button" id="one" value="图片1"></td>
			<td width="20%" align="left"><input type="button" id="two" value="图片2"></td>
			<td width="20%" align="left"><input type="button" id="three" value="图片3"></td>
			<td width="20%" align="left"><input type="button" id="four" value="图片4"></td>
		</tr>
		
		<tr>
			<td width="20%" align="right"><div style="width: 30px; height: 30px;"></div></td>
			<td width="20%" align="left"><span id="s1"></span></td>
			<td width="20%" align="left"><span id="s2"></span></td>
			<td width="20%" align="left"><span id="s3"></span></td>
			<td width="20%" align="left"><span id="s4"></span></td>
		</tr>
		
		<tr>
			<td width="20%" align="right"></td>
			<td width="20%" align="left"><input type="button" id="five" value="图片5"></td>
			<td width="20%" align="left"><input type="button" id="six" value="图片6"></td>
			<td width="20%" align="left"><input type="button" id="seven" value="图片7"></td>
			<td width="20%" align="left"><input type="button" id="eight" value="图片8"></td>
		</tr>
		
		<tr>
			<td width="20%" align="right"><div style="width: 30px; height: 30px;"></div></td>
			<td width="20%" align="left"><span id="s5"></span></td>
			<td width="20%" align="left"><span id="s6"></span></td>
			<td width="20%" align="left"><span id="s7"></span></td>
			<td width="20%" align="left"><span id="s8"></span></td>
		</tr>
	</table>
	
	<br><br/>
	<input type="hidden" name="src" id="src">
	<input type="hidden" name="src2" id="src2">
	<input type="hidden" name="src3" id="src3">
	<input type="hidden" name="src4" id="src4">
	<input type="hidden" name="src5" id="src5">
	<input type="hidden" name="src6" id="src6">
	<input type="hidden" name="src7" id="src7">
	<input type="hidden" name="src8" id="src8">
	
</form>
	<div align="center">
		<a id="saveBtn" class="easyui-linkbutton" data-options="iconCls:'icon-add'">保存</a>
	</div>
</div>
</body>
</html>