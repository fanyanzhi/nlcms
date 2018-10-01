<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../TagLib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="format-detection" content="telephone=no">
<title>国图APP下载</title>
<script type="text/javascript" src="${ctx}/js/easyui/jquery.min.js"></script>

<style>
body,ul,li,p,h1,h2,h3,h4,h5,h6,form,img,div,footer,header,section,cite,input,a,blockquote{margin:0;padding:0;border:0;} 
 body{  background:#1076a6; background-size:100% ; width:100%; min-width:320px; font:14px/28px Arial,"Microsoft YaHei"; line-height:150%;}
body,html { height:100%; z-index:999; }
.topword { 	height:68%;	background:#2295fe;	background-size:100%;	position:relative;}
.word {  background-size:269px 59px; width:269px; height:59px; position:absolute;  left:50%; top:10%; margin-left:-135px; text-indent:-9999px; overflow:hidden;}
.bgbottom { }
.word {animation:mymove  1s ease-out 1 forwards;
-webkit-animation:mymove 1s  ease-out 1 forwards; }
 
.downbg { width:100%;}
.downbg img{ width:100%; }
p.botdownbox { position:fixed; bottom:30%; height:40px; width:100%;   }
p.botdownbox:hover { background-color:#2295fe; color:#fff;     }
.botdownbox a.botdown{border:2px solid #008094; border-radius:50px; line-height:40px; color:#fff; font-size:18px; text-align:center; width:75%; margin: 0px auto;
 display:block; outline: none; text-decoration: none; cursor: pointer; }
.popwordbox,.popword,.popword p  { height:48px; line-height:48px; width:100%;  position:fixed; left:0px; top:0px; }
.popwordbox {  background-color:#000; filter:alpha(opacity=50); opacity:0.5;  }
.popword { }
.popword p { width:90%; margin:0px 5%;text-align:left; background: url(${ctx}/images/close.png) no-repeat right center; background-size:16px 16px; color:#fff; z-index:999;}
</style>
</head>

<body>
    <div class="downbg">
    <img src="${ctx}/images/bg.png">
    <p class="botdownbox">	
    	<a class="botdown"  id="herflink" href="http://202.96.31.95/apkupdate/NLC.apk">下载客户端</a>
    </p>
    </div>
    
   <div class="popword" id="point" style="display:none;">
       <div class="popwordbox"> </div>
       <p>1.点击右上角的“ <img src="${ctx}/images/weixinmore.png" width="2" height="12" /> ”按钮，2.选择“在浏览器中打开”</p>
   </div>
   
  <!--判断手机安卓或ios，则链接地址不同；判断是不是微信，则显不显示div-->
<script language="javascript">
var alink=document.getElementById("herflink");
var DivRef = document.getElementById('point');
DivRef.onclick =function(){
	var u = navigator.userAgent;
	if (u.indexOf('Android') > -1 || u.indexOf('Linux') > -1) {//安卓手机
	//alert("安卓手机");
	 alink.href = "http://202.96.31.95/apkupdate/NLC.apk";
	} else if (u.indexOf('iPhone') > -1) {//苹果手机
	alink.href = "https://itunes.apple.com/cn/app/guo-jia-shu-zi-tu-shu-guan/id411870595?mt=8";
	//alert("苹果手机");
	} else if (u.indexOf('Windows Phone') > -1) {//winphone手机
	alert("winphone手机,暂无");
	alink.href = "#";
	}
	    if(isWeiXin()){
		DivRef.style.display="block";}
		
	}
	  DivRef.onclick =function(){
		  this.style.display="none";  }
	  function isWeiXin(){
	    var ua = window.navigator.userAgent.toLowerCase();
	    if(ua.match(/MicroMessenger/i) == 'micromessenger'){
	        return true;
	    }else{
	        return false;
	    }
	}

</script>
</body>
</html>
