<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../TagLib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width,user-scalable=no,initial-scale=1,maximum-scale=1">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="keywords" content="掌上国图,中国国家数字图书馆,数字图书馆">
<meta name="description" content="中国国家图书馆是国家总书库，国家书目中心，国家古籍保护中心，国家典籍博物馆。">
<title>资源阅读-国家数字图书馆</title>
<link href="${ctx}/css/public.css" rel="stylesheet" type="text/css" >
<link href="${ctx}/css/touch-books.css" rel="stylesheet" type="text/css" >
<script type="text/javascript" src="${ctx}/js/easyui/jquery.min.js"></script>
</head>

<body>
<div class="wrapper-body">
	<!--head-->
    <header id="f-header">
    	<div class="f-header BGcolor-5db3ca">
    		<h2 class="flogo-touch">
        			<div class="goback-icon" onClick="history.go(-1)"></div>
        			<div class="f-item" >资源阅读</div>
        	</h2>
        </div>
    </header>
    <!--head over-->
    <!--资源阅读栏目-->
    <section id="online-cont">
    	<div class="online-book-info">
                <!--文津诵读 每年每月列表-->
            <div class="wjjdsd-listborks">
                <!--文津诵读 每年每月列表 end-->
       			<div id="nativeShare" class="share-function"></div>
        	</div>
       	</div>
    </section>
    
    <!--资源阅读栏目 over-->
    <!--版权信息-->
    <footer id="p-footer">
    	<div class="p-footer">
	  		<p class="p-foot-t">国家图书馆版权所有<br>未经授权禁止复制或建立镜像</p>
		</div>
    </footer>
</div>
<script>
var timeT = 1;
$(function(){
	var id = GetQueryString("id").substr(0,32);;
	content(id);
})

function content(id){
	$.ajax({
	type:"POST",
	url:'${ctx}/session/subjectcatalog/getCatalongContent',
	data:{"catalogId":id},
	dataType: "json",
	success:function(data){

			$("#nativeShare").before(
            		data.content
			);

			var Medias = document.getElementsByTagName("audio");

			var eventTester = function(e){
				for(var i=0;Medias.length;i++){
					var Media = Medias[i];
					Media.addEventListener( e, function(){

						var omedio = document.getElementById(timeT);

						if(null !=omedio){
							omedio.pause();
						}
						timeT = Math.random();
						var ids = $(this).children().attr('name')
						$(this).replaceWith('<audio id="'+ timeT+'" controls="controls">'+
					    '<source type="audio/mpeg" src="'+ ids +'"></source>'+
					'</audio>');
						var omedia = document.getElementById(timeT);
						omedia.play();
		        },  false);
				}

		    }
			eventTester('play'); 
	}
	});
}

var LocString=String(window.document.location.href);
function GetQueryString(str){
	var rs=new RegExp("(^|)"+str+"=([^\&]*)(\&|$)","gi").exec(LocString),tmp;
	if(tmp=rs)return tmp[2];
	return "没有这个参数";
}
</script>
</body>
</html>
