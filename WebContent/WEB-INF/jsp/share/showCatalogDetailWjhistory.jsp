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
		<link href="${ctx}/css/public.css" rel="stylesheet" type="text/css" />
		<link href="${ctx}/css/touch-books.css" rel="stylesheet" type="text/css" />
        <link href="${ctx}/css/common.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="${ctx}/js/easyui/jquery.min.js"></script>
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
    	<div class="online-book-info">
                <!--文津诵读 每年每月列表-->
            <div class="wjjdsd-listborks">
                <!--文津诵读 每年每月列表 end-->
       			<div id="nativeShare" class="share-function"></div>
        	</div>
        	
        	 <!--目录-->
            <br/><br/>
        	<div class="catale_daoheng">
            	<a href="${ctx}/share/subjectCatalog?subjectid=${subjectid}" class="page-item">目录</a>
            </div>
        	
        	<!-- <div style="position:relative; padding:12px 0; margin:20px 0;border-radius:8px; background-color:#f2f2f2;">
       	    	<span class="fenxin" style=" line-height:44px; position:absolute; top:12px; left:0;">分享到：</span>
				<div class="bdsharebuttonbox" style="clear: both;padding-left: 20%">
			  		<a href="#" style="width:12%;" class="bds_weixin" data-cmd="weixin" title="分享到微信"></a>
			  		<a href="#" style="width:12%;" class="bds_tsina" data-cmd="tsina" title="分享到新浪微博"></a>
			  		<a href="#" style="width:12%;" class="bds_qzone" data-cmd="qzone" title="分享到QQ空间"></a>
		    	</div> 
				<script>window._bd_share_config={"common":{"bdSnsKey":{},"bdText":"","bdMini":"1","bdMiniList":["weixin","tsina","qzone"],"bdPic":"","bdStyle":"1","bdSize":"32"},"share":{}};with(document)0[(getElementsByTagName('head')[0]||body).appendChild(createElement('script')).src='http://bdimg.share.baidu.com/static/api/js/share.js?v=89860593.js?cdnversion='+~(-new Date()/36e5)];</script>
			</div> -->
			
       	</div>
    </section>
    
    <br/>
    <br/>
    
   <!--   <div class="popword"  id="points" style=" z-index:999; ">
       <div class="popwordbox"> </div>
       <p class="p1">
       	<img src="${ctx}/images/xz.png" width="141" height="32">
       </p>
       <a class="p2" id="herflink" href="#" style="color:#fff;">下载客户端</a>
       <p class="p3" id="point" >
       	<img src="${ctx}/images/sc.png" width="14" height="14">
       </p>
   </div>  -->
    
   <!--底部导航-->
        <!-- <footer>
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
        <!-- <a class="fanHui" title="返回顶部" href="#top">返回顶部</a> -->
    <div>
    
    </div>
</div>
<script>
var timeT = 1;
$(function(){
	var id = "${catalogid}";
	content(id);
})

function content(id){
	$.ajax({
	type:"POST",
	url:'${ctx}/share/getCatalongContent',
	data:{"catalogid":id},
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

</script>
</body>
<script language="javascript">
  var DivRef = document.getElementById('point');  
  DivRef.onclick =function(){
	  points.style.display="none";  }



var alink=document.getElementById("herflink")
window.onload = function () {
var u = navigator.userAgent;

if (u.indexOf('Android') > -1 || u.indexOf('Linux') > -1) {//安卓手机
alink.innerHTML = "安卓版下载";
//alert("安卓手机");
 alink.href = "http://viewer.d.cnki.net/CAJViewerCloud.apk";
} else if (u.indexOf('iPhone') > -1) {//苹果手机
alink.innerHTML = "去App Store下载";
alink.href = "https://itunes.apple.com/cn/app/guo-jia-shu-zi-tu-shu-guan/id411870595?mt=8";
//alert("苹果手机");
} else if (u.indexOf('Windows Phone') > -1) {//winphone手机
//alert("winphone手机");
//alink.href = "http://viewer.d.cnki.net/CAJViewerCloud.apk";

}else {
	points.style.display="none";
}
}
</script>
</html>
