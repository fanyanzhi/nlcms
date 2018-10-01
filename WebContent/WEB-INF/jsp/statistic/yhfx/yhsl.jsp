<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../TagLib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="../../Pub.jsp" %>
<script type="text/javascript" src="${ctx}/js/myweek/WdatePicker.js"></script>
<script >
	function tj() {
		var startDate = $('#wstartDate').val();
		var endDate = $('#wendDate').val();
		
		if(!startDate || !endDate) {
			alert("时间不能为空!");
			return ;
		}
		
		if(endDate < startDate){
            alert('起始时间不能比结束时间晚');
            return ;
		}
		
		$("#winfra").attr("src", "${ctx}/session/statistic/yhlxfb?startDate="+startDate+"&endDate="+endDate+"&r="+Math.random());
	}
</script>
</head>
<body>
	<table>
		<tr>
			<td align="right">选择时间：</td>
			<td>
			<input id="d121" type="text" class="Wdate" value="${startDateweeks}" onfocus="WdatePicker({vel:'wstartDate'})"/>
			--
			<input id="d122" type="text" class="Wdate" value="${endDateweeks}" onfocus="WdatePicker({vel:'wendDate'})"/>
			<input type="hidden" name="wstartDate" id="wstartDate" value="${startDate}">
			<input type="hidden" name="wendDate" id="wendDate" value="${endDate}">
				
				<!-- <input type="text" id="startDate" name="startDate" class="easyui-datebox" /> -- 
				<input type="text" id="endDate" name="endDate" class="easyui-datebox" />&nbsp; -->
			</td>
			
			<td align="right">
			</td>
			
			<td>
				&nbsp;<input type="button" id="bt1" onclick="tj()" value="统计"/>
			</td>
		</tr>
	</table>
	<br/>
	<div>
	用户类型分布:
		<div>
			<iframe id="winfra" src="${ctx}/session/statistic/yhlxfb?startDate=${startDate }&endDate=${endDate }" width="100%" height="320" frameborder=0 marginheight=0></iframe>
		</div>
	</div>
	
</body>
</html>