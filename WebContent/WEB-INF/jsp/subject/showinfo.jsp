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
        			<div class="goback-icon" onClick="history.go(-1)"></div>
        			<div class="f-item" >资源阅读</div>
        	</h2>
        </div>
    </header>
    <!--head over-->
    <!--资源阅读栏目-->
    <section id="online-cont">
    	<div class="online-book-info">
        	<div id="Catale_daohang" class="page-turn" >
				<!-- <a href="" id="prePage" class="page-turn-pre">上一页</a>
   				<a href="" id="catalogk" class="page-turn-item">目录</a>
    			<a href="" id="nextPage" class="page-turn-next">下一页</a> -->
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
var id;
$(function(){
	id = GetQueryString("id").substr(0,32);
	content(id);

})

/* function nextPage(){
	$.ajax({
		type:"POST",
		url:'${ctx}/client/subjectnext',
		data:{id:id},
		dataType: "json",
		success:function(data){

		}
	})
} */

/* function gabackmulu(){
	var url =document.referrer ;
	window.location.href=url + "#cataList";
	return false;
} */

function content(id){
	$.ajax({
	type:"POST",
	url:'${ctx}/session/subjectcatalog/getCatalongContent',
	data:{"catalogId":id},
	dataType: "json",
	success:function(data){
			$("#Catale_daohang").before(
				'<h3 class="online-book-info-title">'+ data.text + '</h3>'+
            		'<div class="online-book-info-t">'+ data.content +
            	'</div>'
			);

			/* if(data.nextPage){
				$("#nextPage").attr("href","/nlcm/client/books/mobilez-showinfo.jsp?id=" + data.nextPage);
			}else{
				$("#nextPage").attr("href","javascript:alert('已经是最后一页');void(0);");
			}
			if(data.prePage){
				$("#prePage").attr("href","/nlcm/client/books/mobilez-showinfo.jsp?id=" + data.prePage);
			}else{
				$("#prePage").attr("href","javascript:alert('已经是第一页');void(0);");
			}
			if(data.subject_id){
				$("#catalogk").attr("href","/nlcm/client/books/mobilez-list.jsp?id=" +data.subject_id+"#cataList");
			} */
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
