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
	  height: 23px;
	  margin: 0;
	  overflow: visible; padding:0 18px; border:none;
	  cursor:pointer;vertical-align: middle;
}
</style>
<script type="text/javascript">
	$(function(){
		//结束时间大于开始时间
		checkEndDate('starttime','endtime');
		//时间框禁止输入
		$(".datebox :text").attr("readonly","readonly");
		
		$("#one").upload({
			action : "${ctx }/session/template/upload",
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
					$("#datepic").val(res.picname);
					var html = '<img style="width: 30px; height: 30px;" onclick=amp("'+res.picname+'") src="'+res.picname+'"/><br><span style="font-size:10px;color:#ccc">大小：'+res.size+'</span>';
					$("#s1").empty().append(html);
				}else {
					alert("图片上传失败");
				}
			}
		})
		
		$("#two").upload({
			action : "${ctx }/session/template/upload",
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
					$("#poempic").val(res.picname);
					var html = '<img style="width: 30px; height: 30px;" onclick=amp("'+res.picname+'") src="'+res.picname+'"/><br><span style="font-size:10px;color:#ccc">大小：'+res.size+'</span>';
					$("#s2").empty().append(html);
				}else {
					alert("图片上传失败");
				}
			}
		})
		
		$("#three").upload({
			action : "${ctx }/session/template/upload",
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
					$("#backpic").val(res.picname);
					var html = '<img style="width: 30px; height: 30px;" onclick=amp("'+res.picname+'") src="'+res.picname+'"/><br><span style="font-size:10px;color:#ccc">大小：'+res.size+'</span>';
					$("#s3").empty().append(html);
				}else {
					alert("图片上传失败");
				}
			}
		})
		
		$("#four").upload({
			action : "${ctx }/session/template/upload",
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
					$("#mottopic").val(res.picname);
					var html = '<img style="width: 30px; height: 30px;" onclick=amp("'+res.picname+'") src="'+res.picname+'"/><br><span style="font-size:10px;color:#ccc">大小：'+res.size+'</span>';
					$("#s4").empty().append(html);
				}else {
					alert("图片上传失败");
				}
			}
		})
		
		$("#five").upload({
			action : "${ctx }/session/template/upload",
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
					$("#separatorpic").val(res.picname);
					var html = '<img style="width: 30px; height: 30px;" onclick=amp("'+res.picname+'") src="'+res.picname+'"/><br><span style="font-size:10px;color:#ccc">大小：'+res.size+'</span>';
					$("#s5").empty().append(html);
				}else {
					alert("图片上传失败");
				}
			}
		})
		
		$("#six").upload({
			action : "${ctx }/session/template/upload",
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
					$("#poemsreturnpic").val(res.picname);
					var html = '<img style="width: 30px; height: 30px;" onclick=amp("'+res.picname+'") src="'+res.picname+'"/><br><span style="font-size:10px;color:#ccc">大小：'+res.size+'</span>';
					$("#s6").empty().append(html);
				}else {
					alert("图片上传失败");
				}
			}
		})
		
		$("#seven").upload({
			action : "${ctx }/session/template/upload",
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
					$("#translatepic").val(res.picname);
					var html = '<img style="width: 30px; height: 30px;" onclick=amp("'+res.picname+'") src="'+res.picname+'"/><br><span style="font-size:10px;color:#ccc">大小：'+res.size+'</span>';
					$("#s7").empty().append(html);
				}else {
					alert("图片上传失败");
				}
			}
		})
		
	})
	
	function saveb() {
		//校验
		var isvalid = $("#form1").form('validate');
		if(!isvalid) {
			return ;
		}
		$("#saveBtn").removeAttr('onclick');
		var starttime = $("#starttime").datetimebox('getValue');
		var endtime = $("#endtime").datetimebox('getValue');
		if(!starttime || !endtime) {
			$("#saveBtn").attr('onclick', 'saveb()');
			alert('时间不能为空!');
			return ;
		}
		
		var name = $("#name").val();
		var datepic = $("#datepic").val();
		var poempic = $("#poempic").val();
		var backpic = $("#backpic").val();
		var mottopic = $("#mottopic").val();
		var separatorpic = $("#separatorpic").val();
		var poemsreturnpic = $("#poemsreturnpic").val();
		var translatepic = $("#translatepic").val();
		var isdefault =$("#isdefault")[0].checked?1:0;
		var status = "0";
		$.ajax({
			url: "${ctx}/session/template/addTemplate",
			data: {"name":name,"isdefault":isdefault,"starttime":starttime, "endtime":endtime, "datepic":datepic, "poempic":poempic, "backpic":backpic, "mottopic":mottopic, "status":status, "separatorpic":separatorpic, "poemsreturnpic":poemsreturnpic, "translatepic":translatepic},
			cache: false,
            type: "POST",
            success: function(data){
            	var res = eval("("+data+")");
				if(res.result == false) {
					$("#saveBtn").attr('onclick', 'saveb()');
					alert("操作失败");
				}else if(res.result == true) {
					alert("操作成功");
					insertLog("添加文津诵读模板 " + name);
					parent.tab.datagrid('reload');
					parent.closeWin();
				}
            }
		})
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
	
	//发布
	function publishInfo() {
		//校验
		var isvalid = $("#form1").form('validate');
		if(!isvalid) {
			return ;
		}
		$("#publishBtn").removeAttr("onclick");
		var starttime = $("#starttime").datetimebox('getValue');
		var endtime = $("#endtime").datetimebox('getValue');
		if(!starttime || !endtime) {
			$("#publishBtn").attr('onclick', 'publishInfo()');
			alert('时间不能为空!');
			return ;
		}
		
		var name = $("#name").val();
		var datepic = $("#datepic").val();
		var poempic = $("#poempic").val();
		var backpic = $("#backpic").val();
		var mottopic = $("#mottopic").val();
		var separatorpic = $("#separatorpic").val();
		var poemsreturnpic = $("#poemsreturnpic").val();
		var translatepic = $("#translatepic").val();
		var isdefault =$("#isdefault")[0].checked?1:0;
		var status = "1";
		
		$.ajax({
			url: "${ctx}/session/template/addTemplate",
			data: {"name":name, "isdefault":isdefault, "starttime":starttime, "endtime":endtime, "datepic":datepic, "poempic":poempic, "backpic":backpic, "mottopic":mottopic, "status":status, "separatorpic":separatorpic, "poemsreturnpic":poemsreturnpic, "translatepic":translatepic},
			cache: false,
            type: "POST",
            success: function(data){
            	var res = eval("("+data+")");
				if(res.result == false) {
					$("#publishBtn").attr('onclick', 'publishInfo()');
					alert("操作失败");
				}else if(res.result == true) {
					alert("操作成功");
					insertLog("添加文津诵读模板 " + name);
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
	<table>
		<tr>
			<td width="20%" align="right">模板名称：</td>
			<td width="20%"><input type="text" id="name" name="name" class="easyui-validatebox" required data-options="validType:['specialy','isBlank','length[0,10]']" /></td>
			<td width="20%"><input type="checkbox" id="isdefault" name="isdefault" value="1" />默认样式</td>
			<td width="20%"></td>
			<td width="20%"></td>
		</tr>
		
		<tr>
			<td width="20%" align="right">时间设置：</td>
			<td colspan="4">
				<input type="text" id="starttime" name="starttime" class="easyui-datebox" /> -- 
				<input type="text" id="endtime" name="endtime" class="easyui-datebox" />
			</td>
		</tr>
		
		<tr>
			<td width="20%" align="right">上传选项：<br/><span style="font-size:12px;color:#ccc">(每张图片最大5M)</span></td>
			<td width="20%" align="left"><input type="button" id="one" value="日期图片"><span style="font-size:12px">(尺寸：270x192)</span></td>
			<td width="20%" align="left"><input type="button" id="two" value="诗词名句"><span style="font-size:12px">(尺寸：600x192)</span></td>
			<td width="20%" align="left"><input type="button" id="three" value="整体背景"><span style="font-size:12px">(尺寸：1080x1920)</span></td>
			<td width="20%" align="left"><input type="button" id="four" value="美德格言"><span style="font-size:12px">(尺寸：600x192)</span></td>
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
			<td width="20%" align="left"><input type="button" id="five" value="分隔图片"><span style="font-size:12px">(尺寸：1080x60)</span></td>
			<td width="20%" align="left"><input type="button" id="six" value="返回按键图片"><span style="font-size:12px">(尺寸：450x180)</span></td>
			<td width="20%" align="left"><input type="button" id="seven" value="翻译按键图片"><span style="font-size:12px">(尺寸：60x60)</span></td>
			<td width="20%" align="left"></td>
		</tr>
		
		<tr>
			<td width="20%" align="right"><div style="width: 30px; height: 30px;"></div></td>
			<td width="20%" align="left"><span id="s5"></span></td>
			<td width="20%" align="left"><span id="s6"></span></td>
			<td width="20%" align="left"><span id="s7"></span></td>
			<td width="20%" align="left"></td>
		</tr>
	</table>
	
	<br><br/>
	<input type="hidden" name="datepic" id="datepic">
	<input type="hidden" name="poempic" id="poempic">
	<input type="hidden" name="backpic" id="backpic">
	<input type="hidden" name="mottopic" id="mottopic">
	<input type="hidden" name="separatorpic" id="separatorpic">
	<input type="hidden" name="poemsreturnpic" id="poemsreturnpic">
	<input type="hidden" name="translatepic" id="translatepic">
	
</form>
	<div align="center">
		<a id="saveBtn" class="easyui-linkbutton" data-options="iconCls:'icon-add'" onclick="saveb()">保存</a>
		<a id="publishBtn" onclick="publishInfo()" class="easyui-linkbutton" data-options="iconCls:'icon-print'">发布</a>
	</div>
</div>
</body>
</html>