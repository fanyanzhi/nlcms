<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../TagLib.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<title>信息资讯-国家数字图书馆</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0,user-scalable=no;" ></meta>
    <!--必填 SEO--> 
    <meta name="keyword" content="掌上国图,中国国家数字图书馆,数字图书馆" ></meta>
    <meta name="description" content="中国国家图书馆是国家总书库，国家书目中心，国家古籍保护中心，国家典籍博物馆。"></meta>
	<!--搜索引擎 辅助抓站-->
	<meta name="robots" content="index,follow"></meta>
	<meta name="apple-mobile-web-app-capable" content="yes">
	<meta name="apple-mobile-web-app-status-bar-style" content="black">
	<meta name="format-detection" content="telephone=no">
	<!--css-->
	<link href="${ctx}/css/public.css" rel="stylesheet" type="text/css" >
	<link href="${ctx}/css/touch-books.css" rel="stylesheet" type="text/css" >
	<script type="text/javascript" src="${ctx}/js/easyui/jquery.min.js"></script>
	<script type="text/javascript" src="${ctx}/js/zTree/jquery.ztree.core-3.5.js"></script>
	<link rel="stylesheet" href="${ctx}/css/zTree/zTreeStyle/zTreeStyle.css" type="text/css">
	<!-- 隐藏zTree的 伸缩图标-->
	<style>.ztree li span.switch{display:none}</style>
	<base target="_self" />
<style>
    body,ul,li,p,h1,h2,h3,h4,h5,h6,form,img,div,footer,header,section,cite,input,a,blockquote{list-style:none;margin:0;padding:0;border:0; font-weight:normal;} 
    body,html {  line-height:150%; }
	body{min-width:317px;}
	a{ text-decoration:none; out-line: none}


.downbox{ overflow:hidden; width:100%; padding:10px 0; position:fixed;
 bottom:0; left:0; min-width:295px;}
.downleft{ float:left;padding-left:8px;}
.downleft img{display:block; width:50px;}
.downfont{color:#FFF; float:left;padding-left:6px; font-size:18px; line-height:50px;}
.downright{ float:right;padding-right:8px; padding-top:7px;  }
.downright a{ display:block;padding-right:14px; padding:8px 15px;

 font-size:18px; color:#00b7ee; line-height:20px;
 background:#fff; border-radius:20px;}
 .btm{ width:100%; height:70px; background-color:#00b7ee;position:fixed;
 bottom:0; left:0;filter:alpha(Opacity=90);-moz-opacity:0.9;opacity: 0.9;}
 .close{ display:block; width:26px; height:26px; background-image:url(${ctx}/images/close2.png);
  background-repeat:no-repeat; background-size:100% 100%;
  position:fixed; bottom:57px; right:2px;}
</style>
</head>

<body>
<div class="wrapper-body">
	<!--head-->
    <header id="f-header">
    	<div class="f-header BGcolor-5db3ca">
        	<h2 class="flogo-touch">
        	</h2>
        </div>
    </header>
    <!--head over-->
    
    <!--资源阅读栏目-->
    <section id="online-cont">
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
var mid = "${subjectid}";

$(function(){
	catalog(mid);
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
					IdKey: "pId",
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
	url:'${ctx}/share/showZtree',
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
    		window.location.href='${ctx}/share/showCatalogDetail?catalogid=' + treeNode.id;
    	}else{
    		var nodeName = treeNode.name;
    		if(nodeName.indexOf("今日")>=0){
    			//文津今日目录的展开页
    			location.href='${ctx}/share/showCatalogDetailWjtoday?subjectid=${subjectid}&catalogid=' + treeNode.id;
    		}else{
    			location.href='${ctx}/share/showCatalogDetailWjhistory?subjectid=${subjectid}&catalogid=' + treeNode.id;
    		}
    	}

    }
};


</script>
<!-- 下载-->
<div id="adv" class="adv">
    <div class="btm"></div>
	<div class="downbox" id="one">
    	<div class="downleft">
        	<img src="${ctx }/images/icon180.png">
        </div>
    	<div class="downfont">
                国家数字图书馆
        </div>
    	<div class="downright">
            <a href="http://m.nlc.cn/nlcm/client/appdown/down-info.jsp?id=ff80808150031142015003d399fa000e"  id="herflink">下载APP</a>
        </div>
        
    </div>
    <a  id="close" class="close"></a>
</div>

<script  language="javascript">
 var Close = document.getElementById('close');  
 var Adv = document.getElementById('adv');  
 Close.onclick =function(){
 Adv.style.display="none";  }
</script>
</body>
</html>
