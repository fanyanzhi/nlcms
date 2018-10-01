<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../TagLib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="${ctx}/js/jquery-1.8.3.js"></script>
<title>WebAirness_Start</title>

<style type="text/css">
*{ margin:0px; padding:0px;}
.addziybox  { float:left; width:48%; table-layout:fixed; padding:20px; }
.borderight { border-right:1px solid  #ccc;}

 .zyhxtable {   padding:10px;}
 .zyhxtable td{ padding:5px 10px; font-size:12px;}
  .zylxtitle { font-size:14px; color: #006;}
  .zyhxtable td  input{ margin-right:5px; vertical-align:central;}
  .botoptall { font-weight:14px; margin-top:10px;}
  .botoptall input { margin-right:5px;}
  .zyborderbox { border:1px solid #ccc;}
 </style>
</head>

<body>
<table width="100%" border="0" cellspacing="0" cellpadding="0" class="zyhxtable">
   <tr>
   	 <td class="zylxtitle">  展示项选择 </td>
   	 <td></td>
  	 <td class="zylxtitle">  已添加项 </td>
   </tr>

<!-- -- --> 
   <tr>
    <td class="zyborderbox" valign="top">
  
    <!-- left table begin-->
   <table id="tt1" width="100%" border="0" cellspacing="0" cellpadding="0" >
   
   <c:forEach items="${reslist }" var="ele" varStatus="vs">
			<tr>
				<td width="80%"><input name="leftitem" id="le${ele.typeid}" type="checkbox" value="1" />${ele.typename }</td>
    			<td width="20%">
    			
    			<c:if test="${ele.status=='1'}">
    				<input type="button" value="已添加" style="color:gray">
    			</c:if>
    			<c:if test="${ele.status=='0'}">
    				<input type="button" value="添加" onclick="singleAdd('${ele.typeid}', '${ele.typename}', this)">
    			</c:if>
    			</td>
			</tr>
   </c:forEach>
   
	</table>
   <!-- left table end-->
    </td>
    
    
    <td width="50" align="center"><img src="${ctx}/images/arrow.png" alt="" /></td>
    
    
    <td valign="top" class="zyborderbox">
   <!-- right table begin-->
    <table id="tt2" width="100%" border="0" cellspacing="0" cellpadding="0" class="zyhxtable">
    
    <c:forEach items="${reslist }" var="ele" varStatus="vs">
    	<c:if test="${ele.status=='1' }">
    		<tr>
    			<td width="80%"><input name="rightitem" type="checkbox" value="${ele.typeid}" />${ele.typename }</td>
    			<td width="20%"><input type="button" value="删除" onclick="singleDel('${ele.typeid}', '${ele.typename }', this)"></td>
  			</tr>
    	</c:if>
   </c:forEach>
   
	</table> 
     <!-- right table end-->
    </td>
    
   </tr> 
<!-- -- -->
   <c:if test="${LoginObj.role != 2}">
  <tr>
  	 <td><div class="botoptall">
  	 		<input type="button" value="全选" onclick="selectAll1()"/>
       		<input type="button" value="添加" onclick="addAll()"/>
       	</div>
     </td>
  	 <td></td>
     <td><div class="botoptall">
     		<input type="button" value="全选" onclick="selectAll2()"/>
   			<input type="button" value="删除" onclick="delAll()"/>
   			<input type="button" value="保存" onclick="saveres()"/>
   		 </div>
   	 </td>
   </tr>
   </c:if>

</table>
<script type="text/javascript">
var lg = 1;
var rg = 1;

function singleAdd(typeid, typename, e) {
	$(e).val('已添加');
	$(e).css('color','gray');
	$(e).attr('onclick', 'return false');
	
	var html = '<tr><td width="80%"><input name="rightitem" type="checkbox" value="'+typeid+'" >'+typename+'</td>'+
				'<td width="20%"><input type="button" value="删除" onclick=singleDel(\"'+typeid+'\",\"'+typename+'\",this) ></td></tr>';
	$(html).appendTo("#tt2");
}

																//onclick=singleDel(\"'+typeid+'\"'+",this"+')

function singleDel(typeid, typename, e) {
	$(e).parent().parent().remove();
	$("#le"+typeid).parent().next().children().css('color','');
	$("#le"+typeid).parent().next().children().val('添加');
	$("#le"+typeid).parent().next().children().attr('onclick', 'singleAdd(\"'+typeid+'\", \"'+typename+'\", this)');
}
																
function selectAll1() {
	if(lg == 1) {
		$('input[name="leftitem"]').each(function(index, obj){ 
			$(this).attr("checked", "checked");
		});
		lg = 0;
	}else if(lg == 0){
		$('input[name="leftitem"]').each(function(index, obj){ 
			$(this).removeAttr("checked");
		});
		lg = 1;
	}
}	

function selectAll2() {
	if(rg == 1) {
		$('input[name="rightitem"]').each(function(index, obj){ 
			$(this).attr("checked", "checked");
		});
		rg = 0;
	}else if(rg == 0){
		$('input[name="rightitem"]').each(function(index, obj){ 
			$(this).removeAttr("checked");
		});
		rg = 1;
	}
}

function addAll() {
	var count = 0;
	$('input[name="leftitem"]:checked').each(function(index, obj){ 
		count = count + 1;
	});
	if(count == 0) {
		alert("请先选择数据!");
		return ;
	}
	
	$('input[name="leftitem"]:checked').each(function(index, obj){ 
		$(this).parent().next().children().click();
		rg = 1;
	});
}

function delAll() {
	var count = 0;
	$('input[name="rightitem"]:checked').each(function(index, obj){ 
		count = count + 1;
	});
	if(count == 0) {
		alert("请先选择数据!");
		return ;
	}
	
	$('input[name="rightitem"]:checked').each(function(index, obj){ 
		$(this).parent().next().children().click();
	});
}

function saveres() {
	var parr = [];
	$('input[name="rightitem"]').each(function(){ 
		parr.push($(this).val()); 
	});
	
	$.ajax({
		url: "${ctx}/session/readerpicset/save",
		data: {"typeidarr":parr.join()},
        type: "POST",
        success: function(data){
        	var res = eval("("+data+")");
			if(res.result == false) {
				alert("操作失败");
			}else if(res.result == true) {
				insertLog("修改读者画像展示设置 ");
				alert("保存成功");
			}
        }
	})
}

function insertLog(message) {
	var loginTime = "<fmt:formatDate value='${loginTime}' pattern='yyyy-MM-dd HH:mm:ss'/>";
	var loginIp = "${loginIp}";
	var username = "${LoginObj.username}";
	var role = "${LoginObj.role}";
	
	$.ajax({
		url:"${ctx}/session/adminlog/insertLog",
		type:'POST',
		data: {"username":username, "role":role, "ip":loginIp, "time":loginTime, "operate": message},
		success:function(data){
			} 
		});
}

</script>
</body>
</html>
