<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../TagLib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link href="${ctx}/css/public.css" rel="stylesheet" type="text/css" >
<link href="${ctx}/css/touch-books.css" rel="stylesheet" type="text/css" >
<script type="text/javascript" src="${ctx}/js/easyui/jquery.min.js"></script>

<meta http-equiv="content-type" content="text/html;charset=UTF-8" />
</head>

<body>
<div class="wrapper-body">
	<!--head-->
    <header id="f-header">
    	<div class="f-header BGcolor-5db3ca">
        	<h2 class="flogo-touch">
        			<div class="f-item" >新闻</div>
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
    <!--版权信息-->
    <footer id="p-footer">
    	<div class="p-footer">
	  		<p class="p-foot-t">国家图书馆版权所有<br />未经授权禁止复制或建立镜像</p>
		</div>
    </footer>
</div>
<script>
$(function(){
	$.ajax({
		type:"POST",
		url:'${ctx}/session/news/getNewsByNewsid',
		data:{"newsid":"${newsid}"},
		dataType: "json",
		success:function(data){
				$("#Catale_daohang").before(
					'<h3 class="online-book-info-title">'+ data.title + '</h3>'+
	            		'<div class="online-book-info-t">'+ data.content +
	            	'</div>'
				);
			}
		});
})

</script>
</body>
</html>
