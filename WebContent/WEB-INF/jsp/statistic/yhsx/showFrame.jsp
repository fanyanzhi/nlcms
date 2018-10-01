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
	$("#winfra").attr("src", "${ctx}/session/statistic/yhsx1?startDate="+startDate+"&endDate="+endDate+"&r="+Math.random());
	$("#winfra2").attr("src", "${ctx }/session/statistic/yhsx2?startDate="+startDate+"&endDate="+endDate+"&r="+Math.random());
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
			</td>
			
			<td align="right">
			</td>
			
			<td>
				&nbsp;<input type="button" id="bt1" onclick="tj()" value="统计"/>
			</td>
		</tr>
	</table>
	<div>
	年龄、性别分布:
	<hr style="width:98%"/>
		<div>
			<iframe id="winfra" src="${ctx}/session/statistic/yhsx1?startDate=${startDate}&endDate=${endDate}" width="100%" height="500" frameborder=0 marginheight=0></iframe>
		</div>
	</div>
	
	<br/>
	<div>
	学历分布:
	<hr style="width:98%"/>
		<div>
			<iframe id="winfra2" src="${ctx }/session/statistic/yhsx2?startDate=${startDate}&endDate=${endDate}" width="100%" height="200" frameborder=0 marginheight=0></iframe>
		</div>
	</div>
	
	<br/>
</body>
</html>