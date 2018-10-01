<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="./TagLib.jsp"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html;charset=UTF-8" />
<title>国图管理登录</title>
<script type="text/javascript" src="${ctx}/js/jquery-1.8.3.js"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/js/easyui/themes/default/easyui.css"/>
<link rel="stylesheet" type="text/css" href="${ctx}/js/easyui/themes/icon.css"/>
<script type="text/javascript" src="${ctx}/js/easyui/jquery.min.js"></script>
<script type="text/javascript" src="${ctx}/js/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${ctx}/js/easyui/locale/easyui-lang-zh_CN.js"></script>
<style type="text/css">
body, ul, ol, li, dl, dt, dd, p, h1, h2, h3, h4, h5, h6, form, fieldset,
	table, td, img, div {
	margin: 0;
	padding: 0;
	border: 0;
}

body {
	background-color: #f5f5f5;
}

/*-- login --*/
#loginhead {
	background: url(<%=ctx %>/images/login/logintopbg.png) no-repeat center center;
	height: 200px;
	padding-top: 150px;
	text-align: center;
	min-width: 1000px;
}

#loginmain {
	width: 100%;
	min-width: 1000px;
}

.logincon {
	width: 440px;
	margin: 60px auto 20px auto;
	height: 200px;
	padding: 40px 0px 20px 20px;
	font-family: "微软雅黑";
	font-size: 14px;
	background-color: #cccccc;
	border: 1px solid #fff;
}

.lgoinright {
	height: 270px;
	padding: 50px 0px 10px 25px;
}

.logincon p {
	height: 40px;
	margin: 9px 10px;
}

.logincon p label {
	width: 60px;
	display: block;
	padding: 3px 5px 0px 0px;
	float: left;
	text-align: right;
}

.logincon p.wrong {
	color: #c00;
	margin-top: 15px;
}

.logincon p input {
	width: 300px;
	border: 1px solid #9b9b9b;
	height: 28px;
	line-height: 28px;
}

.logincon p input.validateipt {
	width: 125px;
}

.logincon p img.validatepic {
	margin-left: 5px;
}

.logincon span .logbtn {
	cursor: pointer;
	width: 180px;
	height: 42px;
	color: #fff;
	font-size: 16px;
	background-color: #008094;
	border: 0px;
	margin: 15px 0px 0 77px;
}

.logincon span .logbtn:hover {
	background-position: 0px -48px;
}
</style>
</head>
<body  onkeydown="if (event.keyCode == 13) {$('#bt').click();} " onload="$('#username').focus()">
	<div id="loginhead">
		<img src="<%=ctx %>/images/login/logo.png" />
	</div>
	<form action="" id="validation_form" method="post">
	<div id="loginmain">
		<div class="logincon">
			<p>
				<label>用户名：</label><input  class="easyui-validatebox" name="username" id="username" type="text" data-options="required:true"/>
			</p>
			<p>
				<label>密&nbsp;&nbsp;&nbsp;&nbsp;码：</label><input  class="easyui-validatebox" name="password" id="password" type="password"  data-options="required:true"/>
			</p>
			<span><input name="bt" id="bt" type="button" class="logbtn" value="登 录" /></span>
			<p class="wrong"></p>

		</div>
	</div>
	</form>
	<script>
	$("#bt").click(function(){
		//校验
		var isvalid = $("#validation_form").form('validate');
		if(!isvalid) {
			return ;
		}
		
		var username = $("#username").val();
		var password = $("#password").val();
		var subData1 = '{"username":"'+username+'", "password":"'+password+'"}';
		
		$.ajax({
			url : '${ctx}/logincontroller',
			data : subData1,
			contentType: "application/json",
			type : 'POST',
			success : function(data) {
				var obj = eval("("+data+")");
				if(false == obj.result) {
					$(".wrong").html(obj.msg);
				}
				if(true == obj.result) {
					location.href = "${ctx}/session/index";
				}
			}
		});
	})
	</script>
</body>
</html>