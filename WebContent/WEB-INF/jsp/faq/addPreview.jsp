<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../TagLib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link href="${ctx}/css/public.css" rel="stylesheet" type="text/css" >
<link href="${ctx}/css/touch-books.css" rel="stylesheet" type="text/css" >
<script type="text/javascript" src="${ctx}/js/easyui/jquery.min.js"></script>
<script type="text/javascript" src="${ctx}/js/zTree/jquery.ztree.core-3.5.js"></script>
<link rel="stylesheet" href="${ctx}/css/zTree/zTreeStyle/zTreeStyle.css" type="text/css">

<meta http-equiv="content-type" content="text/html;charset=UTF-8" />
</head>

<body>
<div class="wrapper-body">
	<!--head-->
    <header id="f-header">
    	<div class="f-header BGcolor-5db3ca">
        	<h2 class="flogo-touch">
        			<div class="f-item" >常见问题</div>
        	</h2>
        </div>
    </header>
    <!--head over-->
    <!--资源阅读栏目-->
    <section id="online-cont">
    	<div class="online-book-info">
        	<div id="Catale_daohang" class="page-turn" >
			</div>
       	</div>
    </section>
    <!--资源阅读栏目 over-->
</div>
<script>
$(function(){
	var questions = window.opener.questions;
	var answers = window.opener.answers;
	
	$("#Catale_daohang").before(
			'<h3 class="online-book-info-title">'+questions+'</h3>'+
        		'<div class="online-book-info-t">' +answers +
        	'</div>'
	);
})

</script>
</body>
</html>
