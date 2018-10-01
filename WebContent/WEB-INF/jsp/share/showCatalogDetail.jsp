<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../TagLib.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>信息资讯-国家数字图书馆</title>
        <meta charset="utf-8"></meta>
        <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0,user-scalable=no;" ></meta>
         <!--必填 SEO--> 
        <meta name="keyword" content="掌上国图,中国国家数字图书馆,数字图书馆" ></meta>
        <meta name="description" content="中国国家图书馆是国家总书库，国家书目中心，国家古籍保护中心，国家典籍博物馆。"></meta>
		<!--搜索引擎 辅助抓站-->
		<meta name="robots" content="index,follow"></meta>
		<!--css-->
        <link href="${ctx}/css/common.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="${ctx}/js/easyui/jquery.min.js"></script>
	</head>
<body>
<!-- header 专题详情页-->
    <div class="comheadbox">
    	<div class="comhead">
            详情
        </div>
    </div>
    <!--目录名、正文内容-->
    <div class="contentbox" >
    	<div class="mulu">
        	<h1>${title }</h1>
        </div>
        <div class="content">
        	<p>${content }</p>
        	<p><br></p>
        </div>
        <!--目录-->
        <div class="catale_daoheng">
        <c:if test="${!empty prepage}">
        	<a href="${ctx}/share/showCatalogDetail?catalogid=${prepage}">上一页</a>
        </c:if>
        <c:if test="${empty prepage}">
        	<a href="javascript:alert('已经是第一页');void(0)">上一页</a>
        </c:if>
        
        	<a href="${ctx}/share/subjectCatalog?subjectid=${subjectid}" class="page-item">目录</a>
        	
        <c:if test="${!empty nextpage}">
        	<a href="${ctx}/share/showCatalogDetail?catalogid=${nextpage}" >下一页</a>
        </c:if>
        <c:if test="${empty nextpage}">
        	<a href="javascript:alert('已经是最后一页');void(0)">下一页</a>
        </c:if>
            
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
    
   
       <!--  <footer>
        	<div class="footer_nav">
                <a href=""  title="联系我们">
                    手机版
                </a>
                <a href="" title="PC版">
                    PC版
                </a>
                <a href="" title="客户端">
                    客户端下载
                </a>
       		</div>
            <p class="footer_p">
            国家图书馆版权所有<br>未经授权禁止复制或建立镜像
            </p>
        </footer> -->
    <!--返回顶部-->
       <!--  <a class="fanHui" title="返回顶部" href="#top">返回顶部</a> -->
    <div>
    
    </div>
</body>
</html>