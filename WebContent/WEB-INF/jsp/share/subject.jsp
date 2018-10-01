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
        <link href="${ctx}/css/common.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="${ctx}/js/easyui/jquery.min.js"></script>
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
<!-- header 专题详情页-->
    <div class="comheadbox">
    	<div class="comhead">
            专题详情
        </div>
    </div>
    <!--目录名、正文内容-->
    <div class="contentbox" >
    	<div class="mulu">
        	<h1>${nlcsubject.title }</h1>
        </div>
        <div class="content">
        	<p>${nlcsubject.introduce }</p>
        	<p><br></p>
        </div>
        <!--目录-->
        <div class="catale_daoheng">
        	<!-- <a href="" >上一页</a> -->
        	<a href="${ctx}/share/subjectCatalog?subjectid=${nlcsubject.subjectid}" class="page-item">目录</a>
        	<!-- <a href="" >下一页</a> -->
            
            
        </div>
    <!--分享-->
        <!-- <div class="sharebox">
        	<span class="fenxin">分享到：</span>
            <span><a href="" class="wechat">微信好友 </a></span>
            <span><a href="" class="wechat-friends">微信朋友圈</a></span>
            <span><a href="" class="wblog">新浪微博</a></span>
            <span><a href="" class="qzone">QQ空间</a></span>
        </div> -->
        
        <!-- <div style="position:relative; padding:12px 0; margin:20px 0;border-radius:8px; background-color:#f2f2f2;">
       	    <span class="fenxin" style=" line-height:44px; position:absolute; top:12px; left:0;">分享到：</span>
			<div class="bdsharebuttonbox coperation clearfix" style="clear: both;padding-left: 20%">
			  <a href="#" style="width:12%;" class="bds_weixin" data-cmd="weixin" title="分享到微信"></a>
			  <a href="#" style="width:12%;" class="bds_tsina" data-cmd="tsina" title="分享到新浪微博"></a>
			  <a href="#" style="width:12%;" class="bds_qzone" data-cmd="qzone" title="分享到QQ空间"></a>
		    </div> 
			<script>window._bd_share_config={"common":{"bdSnsKey":{},"bdText":"","bdMini":"1","bdMiniList":["weixin","tsina","qzone"],"bdPic":"","bdStyle":"1","bdSize":"32"},"share":{}};with(document)0[(getElementsByTagName('head')[0]||body).appendChild(createElement('script')).src='http://bdimg.share.baidu.com/static/api/js/share.js?v=89860593.js?cdnversion='+~(-new Date()/36e5)];</script>
		</div> -->
    </div>
    <!--底部导航-->
    <br/>
    <br/>
   
    <div>
    
    </div>
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