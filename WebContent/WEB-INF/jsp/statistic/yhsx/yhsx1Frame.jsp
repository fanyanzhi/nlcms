<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../TagLib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<script type="text/javascript">
</script>
</head>
<frameset rows="25%,45%,30%">
  <frame frameborder="0" src="${ctx}/session/statistic/yhsx1p1?startDate=${startDate}&endDate=${endDate}" />
  <frame frameborder="0" src="${ctx}/session/statistic/yhsx1p2?startDate=${startDate}&endDate=${endDate}" />
  <frame frameborder="0" src="${ctx}/session/statistic/yhsx1p3?startDate=${startDate}&endDate=${endDate}"/>
</frameset>
</html>