<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../TagLib.jsp"%>
<!DOCTYPE html>
<html>
  <head>
        <title>文津经典诵读-国家数字图书馆</title>
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
    <div class="wentitile">
        <span><fmt:formatDate value='${wjreader.wjdate }' pattern='yyyy-MM-dd 09:00'/></span>
        <b>来源：国家数字图书馆客户端</b>
    
    </div>
<!--诗词名句-->
    <div class="scmj">
        <p class="gs">${wjreader.shiju }</p>
        <p class="zuozhe">—${wjreader.sjsource }</p>
    </div>
    <div class="yiwen">
        <p>${wjreader.sjyiwen }</p>
    </div>
    <div class="qs">
        <p class="qsbt"><b>【</b>全诗<b>】</b></p>
        <p class="qsnr">
           <!--  历览前贤国与家，成由勤俭破由奢。<br>
            何须琥珀方为枕，岂得真珠始是车。<br>
            几人曾预南熏曲，终古苍梧哭翠华。<br>
            运去不逢青海马，力穷难拔蜀山蛇。<br> -->
            ${wjreader.quanshi }
        </p>
    </div>
<!--美德格言-->
    <div class="scmj" style="background-image:url(${ctx}/images/mdgy.png);">
        <p class="gs">${wjreader.geyan }</p>
        <p class="zuozhe">—${wjreader.gysource }</p>
    </div>
    <div class="yiwen" style=" padding-bottom:80px;">
        <p>${wjreader.gyyiwen }</p>
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
