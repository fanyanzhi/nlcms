<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../TagLib.jsp"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="../Pub.jsp" %>
<link rel="stylesheet" type="text/css" href="${ctx}/css/mycnki.css"/>
<meta http-equiv="content-type" content="text/html;charset=UTF-8" />
</head>
<body onload="InitDateControl()">
<div>
<table cellSpacing="0" cellPadding="0" width="95%" align="center" border="0">
	<tr height="30px" valign="middle">
		<td class="text-hei" align="right" width="100px">
			统计周期：
	    </td>

	    <td class="text-hei">
			&nbsp;<SELECT class="text-hei" id="TimeType" onchange="changeListState(this.options[this.selectedIndex].value)" name="TimeType">
				<OPTION value="all" selected>全部</OPTION>
				<OPTION value="Year">单位时间年统计</OPTION>
				<OPTION value="Month">单位时间月统计</OPTION>
				<OPTION value="Day">单位时间日统计</OPTION>
				<OPTION value="YearTime">时间段年统计</OPTION>
				<OPTION value="MonthTime">时间段月统计</OPTION>
				<OPTION value="DayTime">时间段日统计</OPTION>
			</SELECT>
						
			<select name="Year" id="Year" class="text-hei"></select>&nbsp;
			<select name="Month" id="Month" class="text-hei"></select>
			<select name="Day" id="Day" class="text-hei"></select>
			&nbsp;&nbsp;&nbsp;&nbsp;按时间段统计截止时间：
			<select name="EndYear" id="EndYear" class="text-hei"></select>&nbsp;
			<select name="EndMonth" id="EndMonth" class="text-hei"></select>
			<select name="EndDay" id="EndDay" class="text-hei"></select>
		</td>
	</tr>
</table>
</div>
</body>
<script language="javascript" type="text/javascript">
	var NewDate = new Date();
	var CurrentControl;

	function InitDateControl() {
		var YearControlList = ["Year", "EndYear"];
		for (i = 0; i < YearControlList.length; i++) {
			CurrentControl = document.getElementById(YearControlList[i]);
			
			for(l = 2004; l<NewDate.getFullYear() + 2; l++) {
				CurrentControl.options[CurrentControl.options.length] = new Option(l, l);
				if(l == NewDate.getFullYear())
					CurrentControl.options[CurrentControl.options.length - 1].selected = true;
			}
		}

		var MonthControlList = ["Month", "EndMonth"];
		for (i = 0; i < MonthControlList.length; i++) {
			CurrentControl = document.getElementById(MonthControlList[i]);
                   
			for(l = 1;l<13;l++) {
				CurrentControl.options[CurrentControl.options.length] = new Option(l, l);
				if(l == NewDate.getMonth() + 1)
					CurrentControl.options[CurrentControl.options.length - 1].selected = true;
			}
		}

		var DayControlList = ["Day", "EndDay"];
		for (i = 0; i < DayControlList.length; i++) {
			CurrentControl = document.getElementById(DayControlList[i]);
            
			for(l = 1;l<32;l++) {
				CurrentControl.options[CurrentControl.options.length] = new Option(l, l);
                if(l == NewDate.getDate())
                	CurrentControl.options[CurrentControl.options.length - 1].selected = true;
			}
		}
	}
	
	//提交数据----start----
	function ShowSearchImage() {
		var StartYear = document.getElementById("Year").options[document.getElementById("Year").selectedIndex].value;
		var StartMonth = document.getElementById("Month").options[document.getElementById("Month").selectedIndex].value;
		var StartDay = document.getElementById("Day").options[document.getElementById("Day").selectedIndex].value;
		var EndYear = document.getElementById("EndYear").options[document.getElementById("EndYear").selectedIndex].value;
		var EndMonth = document.getElementById("EndMonth").options[document.getElementById("EndMonth").selectedIndex].value;
		var EndDay = document.getElementById("EndDay").options[document.getElementById("EndDay").selectedIndex].value;
			
		if(document.getElementById("TimeType").options[document.getElementById("TimeType").selectedIndex].value == "YearTime") {
			if(Number(StartYear) > Number(EndYear)) {
				alert("开始年份不能大于结束年份，请重新选择统计周期");
				return false;
			}
		}
			    
		if(document.getElementById("TimeType").options[document.getElementById("TimeType").selectedIndex].value == "MonthTime") {
			if(Number(StartYear) > Number(EndYear)) {
				alert("开始年份不能大于结束年份，请重新选择统计周期");
			    return false;
			}
			        
			if(Number(StartYear) == Number(EndYear)) {
				if(Number(StartMonth) > Number(EndMonth)) {
					alert("开始月份不能大于结束月份，请重新选择统计周期");
			        return false;
				}
			}
		}
			    
		if(document.getElementById("TimeType").options[document.getElementById("TimeType").selectedIndex].value == "DayTime") {
			var StartTimeStr = StartYear + "-" + StartMonth + "-" + StartDay;
			var EndTimeStr = EndYear + "-" + EndMonth + "-" + EndDay;
			var StartTimeArray = StartTimeStr.split("-");
			var StartTimeObject = new Date(StartTimeArray[0],StartTimeArray[1]-1,StartTimeArray[2]);
			var EndTimeArray = EndTimeStr.split("-");
			var EndTimeObject = new Date(EndTimeArray[0],EndTimeArray[1]-1,EndTimeArray[2]);
    			    
			if(StartTimeObject > EndTimeObject) {
				alert("统计开始时间不能大于结束时间，请重新选择统计周期");
			    return false;
			}
    			    
			if(EndTimeObject.getTime() - StartTimeObject.getTime() > 5184000001) {
				alert("每次只能统计两个月内的日志，请重新选择查询时间");
			    return false;
			}
		}
				
		return true;
	}
	//提交数据----end----
	
	
	function changeListState(state) {
		if(state == "all") {
			document.getElementById("Year").disabled = true;
			document.getElementById("Month").disabled = true;
			document.getElementById("Day").disabled = true;
			document.getElementById("EndYear").disabled = true;
			document.getElementById("EndMonth").disabled = true;
			document.getElementById("EndDay").disabled = true;
		}
		
		if(state == "Year") {
			document.getElementById("Year").disabled = false;
			document.getElementById("Month").disabled = true;
			document.getElementById("Day").disabled = true;
			document.getElementById("EndYear").disabled = true;
			document.getElementById("EndMonth").disabled = true;
			document.getElementById("EndDay").disabled = true;
		}
		
		if(state == "Month") {
			document.getElementById("Year").disabled = false;
			document.getElementById("Month").disabled = false;
			document.getElementById("Day").disabled = true;
			document.getElementById("EndYear").disabled = true;
			document.getElementById("EndMonth").disabled = true;
			document.getElementById("EndDay").disabled = true;
		}
				
		if(state == "Day") {
			document.getElementById("Year").disabled = false;
			document.getElementById("Month").disabled = false;
			document.getElementById("Day").disabled = false;
			document.getElementById("EndYear").disabled = true;
			document.getElementById("EndMonth").disabled = true;
			document.getElementById("EndDay").disabled = true;
		}
		
		if(state == "YearTime") {
			document.getElementById("Year").disabled = false;
			document.getElementById("Month").disabled = true;
			document.getElementById("Day").disabled = true;
			document.getElementById("EndYear").disabled = false;
			document.getElementById("EndMonth").disabled = true;
			document.getElementById("EndDay").disabled = true;
		}
		
		if(state == "MonthTime") {
			document.getElementById("Year").disabled = false;
			document.getElementById("Month").disabled = false;
			document.getElementById("Day").disabled = true;
			document.getElementById("EndYear").disabled = false;
			document.getElementById("EndMonth").disabled = false;
			document.getElementById("EndDay").disabled = true;
		}
		
		if(state == "DayTime") {
			document.getElementById("Year").disabled = false;
			document.getElementById("Month").disabled = false;
			document.getElementById("Day").disabled = false;
			document.getElementById("EndYear").disabled = false;
			document.getElementById("EndMonth").disabled = false;
			document.getElementById("EndDay").disabled = false;
		}
	}
			
	changeListState("all");
</script>
</html>