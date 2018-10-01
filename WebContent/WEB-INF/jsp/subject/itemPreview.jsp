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
<title>资源阅读-国家数字图书馆</title>

<!-- 隐藏zTree的 伸缩图标-->
<style>.ztree li span.switch{display:none}</style>
<base target="_self" />
</head>

<body>
<div class="wrapper-body">
	<!--head-->
    <header id="f-header">
    	<div class="f-header BGcolor-5db3ca">
        	<h2 class="flogo-touch">
        		<div class="f-item">资源阅读</div>
        	</h2>
        </div>
    </header>
    <!--head over-->
    
    <!--资源阅读栏目-->
    <section id="online-cont">
    	<div class="online-book-docc">
        	<h3 id="name" class="online-book-docc-title"><em class="zhuantinames">专题 &gt; </em></h3>
            <dd id="intro" class="online-book-docc-txt"></dd>
       	</div>
    	<div class="online-book-list">
        	<h3 class="online-book-list-title">目录</h3>
        	<div id="cataList" class="ztree"></div>
        </div>
    </section>
    <!--资源阅读栏目 over-->
    <!--版权信息-->
    <footer id="p-footer">
    	<div class="p-footer">
	  		<p class="p-foot-t">国家图书馆版权所有<br />未经授权禁止复制或建立镜像</p>
		</div>
    </footer>
    <!--版权信息 over-->
</div>

<script>
var mid;
$(function(){
 	mid = GetQueryString("id").substr(0,32);
	catalog(mid);
	intro(mid);

})


var setting = {
			view: {
				dblClickExpand: false,
				showLine: true,
				selectedMulti: false,
				showIcon : false,
				nameIsHTML : true
			},
			
			data: {
				simpleData: {
					enable:true,
					idKey: "id",
					pIdKey: "pId",
					rootPId: ""
				}
			},
			
			callback: {
				onClick: zTreeOnClick
			}
};
function catalog(id){
	$.ajax({
	type:"POST",
	url:'${ctx}/session/subjectcatalog/showZtree',
	data:{"subjectid":"${subjectid}"},
	dataType: "json",
	success:function(zNodes){
		var t = $("#cataList");
		t = $.fn.zTree.init(t, setting, zNodes);
	}
	});
}
function zTreeOnClick(event, treeId, treeNode) {
    if(!treeNode.isParent){
    	if(mid != 'ff808081515612b00151563ebd040017'){ //文津经典诵读的cid
    		window.location.href='${ctx}/session/subjectcatalog/showDetail?catalogid=' + treeNode.id;
    	}else{
    		var nodeName = treeNode.name;
    		if(nodeName.indexOf("今日")>=0){
    			location.href='${ctx}/session/subjectcatalog/wjtoday?catalogid=' + treeNode.id;
    		}else{
    			location.href='${ctx}/session/subjectcatalog/wjhistory?catalogid=' + treeNode.id;
    		}
    	}

    }
};

function intro(id){
	$.ajax({
	type:"POST",
	url:'${ctx}/session/subject/unit',
	data:{"subjectid":"${subjectid}"},
	dataType: "json",
	success:function(data){
		$("#name").append(data.title);
		var str = data.introduce;
		if(str == "{}") {
			$("#intro").html("");
		}else {
			$("#intro").html(data.introduce);
		}
	}
	});
}

var LocString=String(window.document.location.href);
function GetQueryString(str){
	var rs=new RegExp("(^|)"+str+"=([^\&]*)(\&|$)","gi").exec(LocString),tmp;
	if(tmp=rs)return tmp[2];
	return "";
}

</script>
</body>
</html>
