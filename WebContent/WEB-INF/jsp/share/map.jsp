<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../TagLib.jsp"%>
<!DOCTYPE html>
<html>
  <head>
        <title>馆区地图馆内指引-国家数字图书馆</title>
        <meta charset="utf-8"></meta>
        <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0,user-scalable=no;" ></meta>
         <!--必填 SEO--> 
        <meta name="keyword" content="中国国家数字图书馆,国图移动客户端读者指南" ></meta>
        <meta name="description" content="中国国家图书馆读者指南"></meta>
		<!--搜索引擎 辅助抓站-->
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <link href="${ctx}/css/allcss.css"  rel="stylesheet" type="text/css">
        <script type="text/javascript" src="${ctx}/js/jquery-1.8.3.js"></script>
    </head>

<body>
<table class="gnzynav">
	<tr>
	<c:if test="${reslist.size() <= 8}">
		<c:forEach items="${reslist}" var="ele" varStatus="vs">
			<td id="button${vs.index}" onclick="addClass${vs.index}()" style="width:${100/reslist.size()}%">
            	<a href="#" >${ele.name}</a>
       		</td>
		</c:forEach>
	</c:if>
	
	<c:if test="${reslist.size() > 8}">
		<c:forEach items="${reslist}" var="ele" varStatus="vs">
			<c:if test="${vs.index < 8}">
				<td id="button${vs.index}" onclick="addClass${vs.index}()" style="width:12.5%">
            		<a href="#" >${ele.name}</a>
       			</td>
       		</c:if>
		</c:forEach>
	</c:if>
    </tr>
</table>

<c:forEach items="${reslist}" var="ele" varStatus="vs">

<div id="zone${vs.index}" style="display:none;">
    <ul class="gnzyb">
    	<c:if test="${not empty ele.src}">
    		<li>
            	<img src="${ele.src}">
       		</li>
    	</c:if>
    	
    	<c:if test="${not empty ele.src2}">
    		<li>
            	<img src="${ele.src2}">
       		</li>
    	</c:if>
    	
    	<c:if test="${not empty ele.src3}">
    		<li>
            	<img src="${ele.src3}">
       		</li>
    	</c:if>
    	
    	<c:if test="${not empty ele.src4}">
    		<li>
            	<img src="${ele.src4}">
       		</li>
    	</c:if>
    	
    	<c:if test="${not empty ele.src5}">
    		<li>
            	<img src="${ele.src5}">
       		</li>
    	</c:if>
    	
    	<c:if test="${not empty ele.src6}">
    		<li>
            	<img src="${ele.src6}">
       		</li>
    	</c:if>
    	
    	<c:if test="${not empty ele.src7}">
    		<li>
            	<img src="${ele.src7}">
       		</li>
    	</c:if>
    	
    	<c:if test="${not empty ele.src8}">
    		<li>
            	<img src="${ele.src8}">
       		</li>
    	</c:if>
    	
    </ul>
</div>

</c:forEach>

<footer> 
  <div class="p-footer">
  	<p class="p-foot-t">国家图书馆版权所有<br>未经授权禁止复制或建立镜像</p>
  </div>
</footer>

<script type="text/javascript">
$(function(){
	addClass0();
})

  function addClass0(){
      $('#button0').addClass("xuazhong");
      $('#button1').removeClass("xuazhong");
      $('#button2').removeClass("xuazhong");
      $('#button3').removeClass("xuazhong");
      $('#button4').removeClass("xuazhong");
      $('#button5').removeClass("xuazhong");
      $('#button6').removeClass("xuazhong");
      $('#button7').removeClass("xuazhong");
      $('#zone0').show();
	  $('#zone1').hide();
	  $('#zone2').hide();
	  $('#zone3').hide();
	  $('#zone4').hide();
	  $('#zone5').hide();
	  $('#zone6').hide();
	  $('#zone7').hide();
	};
	
 function addClass1(){
      $('#button0').removeClass("xuazhong");
      $('#button1').addClass("xuazhong");
      $('#button2').removeClass("xuazhong");
      $('#button3').removeClass("xuazhong");
      $('#button4').removeClass("xuazhong");
      $('#button5').removeClass("xuazhong");
      $('#button6').removeClass("xuazhong");
      $('#button7').removeClass("xuazhong");
      $('#zone0').hide();
	  $('#zone1').show();
	  $('#zone2').hide();
	  $('#zone3').hide();
	  $('#zone4').hide();
	  $('#zone5').hide();
	  $('#zone6').hide();
	  $('#zone7').hide();
	};
	
  function addClass2(){
      $('#button0').removeClass("xuazhong");
      $('#button1').removeClass("xuazhong");
      $('#button2').addClass("xuazhong");
      $('#button3').removeClass("xuazhong");
      $('#button4').removeClass("xuazhong");
      $('#button5').removeClass("xuazhong");
      $('#button6').removeClass("xuazhong");
      $('#button7').removeClass("xuazhong");
      $('#zone0').hide();
	  $('#zone1').hide();
	  $('#zone2').show();
	  $('#zone3').hide();
	  $('#zone4').hide();
	  $('#zone5').hide();
	  $('#zone6').hide();
	  $('#zone7').hide();
	};
	
  function addClass3(){
      $('#button0').removeClass("xuazhong");
      $('#button1').removeClass("xuazhong");
      $('#button2').removeClass("xuazhong");
      $('#button3').addClass("xuazhong");
      $('#button4').removeClass("xuazhong");
      $('#button5').removeClass("xuazhong");
      $('#button6').removeClass("xuazhong");
      $('#button7').removeClass("xuazhong");
      $('#zone0').hide();
	  $('#zone1').hide();
	  $('#zone2').hide();
	  $('#zone3').show();
	  $('#zone4').hide();
	  $('#zone5').hide();
	  $('#zone6').hide();
	  $('#zone7').hide();
	};
	
	function addClass4(){
	      $('#button0').removeClass("xuazhong");
	      $('#button1').removeClass("xuazhong");
	      $('#button2').removeClass("xuazhong");
	      $('#button3').removeClass("xuazhong");
	      $('#button4').addClass("xuazhong");
	      $('#button5').removeClass("xuazhong");
	      $('#button6').removeClass("xuazhong");
	      $('#button7').removeClass("xuazhong");
	      $('#zone0').hide();
		  $('#zone1').hide();
		  $('#zone2').hide();
		  $('#zone3').hide();
		  $('#zone4').show();
		  $('#zone5').hide();
		  $('#zone6').hide();
		  $('#zone7').hide();
	};
	
	function addClass5(){
	      $('#button0').removeClass("xuazhong");
	      $('#button1').removeClass("xuazhong");
	      $('#button2').removeClass("xuazhong");
	      $('#button3').removeClass("xuazhong");
	      $('#button4').removeClass("xuazhong");
	      $('#button5').addClass("xuazhong");
	      $('#button6').removeClass("xuazhong");
	      $('#button7').removeClass("xuazhong");
	      $('#zone0').hide();
		  $('#zone1').hide();
		  $('#zone2').hide();
		  $('#zone3').hide();
		  $('#zone4').hide();
		  $('#zone5').show();
		  $('#zone6').hide();
		  $('#zone7').hide();
	};
	
	function addClass6(){
	      $('#button0').removeClass("xuazhong");
	      $('#button1').removeClass("xuazhong");
	      $('#button2').removeClass("xuazhong");
	      $('#button3').removeClass("xuazhong");
	      $('#button4').removeClass("xuazhong");
	      $('#button5').removeClass("xuazhong");
	      $('#button6').addClass("xuazhong");
	      $('#button7').removeClass("xuazhong");
	      $('#zone0').hide();
		  $('#zone1').hide();
		  $('#zone2').hide();
		  $('#zone3').hide();
		  $('#zone4').hide();
		  $('#zone5').hide();
		  $('#zone6').show();
		  $('#zone7').hide();
	};
	
	function addClass7(){
	      $('#button0').removeClass("xuazhong");
	      $('#button1').removeClass("xuazhong");
	      $('#button2').removeClass("xuazhong");
	      $('#button3').removeClass("xuazhong");
	      $('#button4').removeClass("xuazhong");
	      $('#button5').removeClass("xuazhong");
	      $('#button6').removeClass("xuazhong");
	      $('#button7').addClass("xuazhong");
	      $('#zone0').hide();
		  $('#zone1').hide();
		  $('#zone2').hide();
		  $('#zone3').hide();
		  $('#zone4').hide();
		  $('#zone5').hide();
		  $('#zone6').hide();
		  $('#zone7').show();
	};
	
</script>
</body>
</html>
