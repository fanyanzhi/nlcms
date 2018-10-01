<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../TagLib.jsp"%>
<!DOCTYPE html>
<html>
<head>
</head>
<body>
	<br/>
	<div>
	<font style="color:red">持卡用户数量</font>
	<hr style="width:90%;" align="left"/>
		<div>
			<iframe id="winfra" src="${ctx}/session/statistic/ckyhsFrame?startDateweeks=${startDateweeks}&endDateweeks=${endDateweeks}&startDate=${startDate}&endDate=${endDate}" width="100%" height="900" frameborder=0 marginheight=0></iframe>
		</div>
	</div>
	
	<br/>
	<div>
		<div>
			<iframe id="winfra" src="${ctx}/session/statistic/ckyhs3?startDateweeks=${startDateweeks}&endDateweeks=${endDateweeks}&startDate=${startDate}&endDate=${endDate}" width="100%" height="550" frameborder=0 marginheight=0></iframe>
		</div>
	</div>
</body>
</html>