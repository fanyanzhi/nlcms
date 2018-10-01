<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../TagLib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="../Pub.jsp" %>
<script type="text/javascript" src="${ctx}/ichartjs/ichart.1.2.min.js"></script>
</head>
<body>
<div>
<table>
		<tr>
			<td align="right"><p style="font-weight:bold;">选择时间:&nbsp;&nbsp;</p></td>
			<td>
				<input type="text" id="startDate" name="startDate" class="easyui-datebox" /> -- 
				<input type="text" id="endDate" name="endDate" class="easyui-datebox" />&nbsp;
			</td>
			
			<td>
				&nbsp;<input type="button" onclick="btfunc()" value="统计"/>
			</td>
		</tr>
</table>

	<br/>
	&nbsp;<input type="button" value="导出到Excel" onclick="exportInfo()">
	<div>
		<iframe id="winfra" src="about:blank" width="80%" height="500" frameborder=0 marginheight=0></iframe>
	</div>

</div>

<form id="tempForm1">
		<input type="hidden" name="startDate" id="sdt">
		<input type="hidden" name="endDate" id="edt">
</form>
<script>
$(function () {
	$("#winfra").load(function () {
	    var mainheight = $(this).contents().find("body").height() + 30;
	    $(this).height(mainheight);
	});
	
    var db = $('#startDate');
    db.datebox({
        onShowPanel: function () {//显示日趋选择对象后再触发弹出月份层的事件，初始化时没有生成月份层
            span.trigger('click'); //触发click事件弹出月份层
            if (!tds) setTimeout(function () {//延时触发获取月份对象，因为上面的事件触发和对象生成有时间间隔
                tds = p.find('div.calendar-menu-month-inner td');
                tds.click(function (e) {
                    e.stopPropagation(); //禁止冒泡执行easyui给月份绑定的事件
                    var year = /\d{4}/.exec(span.html())[0]//得到年份
                    , month = parseInt($(this).attr('abbr'), 10); //月份，这里不需要+1
                    db.datebox('hidePanel')//隐藏日期对象
                    .datebox('setValue', year + '-' + month); //设置日期的值
                });
            }, 0);
            yearIpt.unbind();//解绑年份输入框中任何事件
        },
        parser: function (s) {
            if (!s) return new Date();
            var arr = s.split('-');
            return new Date(parseInt(arr[0], 10), parseInt(arr[1], 10) - 1, 1);
        },
        formatter: function (d) { return d.getFullYear() + '-' + (d.getMonth() + 1);/*getMonth返回的是0开始的，忘记了。。已修正*/ }
    });
    var p = db.datebox('panel'), //日期选择对象
        tds = false, //日期选择对象中月份
        aToday = p.find('a.datebox-current'),
        yearIpt = p.find('input.calendar-menu-year'),//年份输入框
        //显示月份层的触发控件
        span = aToday.length ? p.find('div.calendar-title span') ://1.3.x版本
        p.find('span.calendar-text'); //1.4.x版本
    if (aToday.length) {//1.3.x版本，取消Today按钮的click事件，重新绑定新事件设置日期框为今天，防止弹出日期选择面板
       
        aToday.unbind('click').click(function () {
            var now=new Date();
            db.datebox('hidePanel').datebox('setValue', now.getFullYear() + '-' + (now.getMonth() + 1));
        });
    }
        
    var eb = $('#endDate');
    eb.datebox({
        onShowPanel: function () {//显示日趋选择对象后再触发弹出月份层的事件，初始化时没有生成月份层
            spanx.trigger('click'); //触发click事件弹出月份层
            if (!tdsx) setTimeout(function () {//延时触发获取月份对象，因为上面的事件触发和对象生成有时间间隔
            	tdsx = px.find('div.calendar-menu-month-inner td');
                tdsx.click(function (e) {
                    e.stopPropagation(); //禁止冒泡执行easyui给月份绑定的事件
                    var year = /\d{4}/.exec(spanx.html())[0]//得到年份
                    , month = parseInt($(this).attr('abbr'), 10); //月份，这里不需要+1
                    eb.datebox('hidePanel')//隐藏日期对象
                    .datebox('setValue', year + '-' + month); //设置日期的值
                });
            }, 0);
            yearIptx.unbind();//解绑年份输入框中任何事件
        },
        parser: function (s) {
            if (!s) return new Date();
            var arr = s.split('-');
            return new Date(parseInt(arr[0], 10), parseInt(arr[1], 10) - 1, 1);
        },
        formatter: function (d) { return d.getFullYear() + '-' + (d.getMonth() + 1);/*getMonth返回的是0开始的，忘记了。。已修正*/ }
    });
    var px = eb.datebox('panel'), //日期选择对象
        tdsx = false, //日期选择对象中月份
        aTodayx = px.find('a.datebox-current'),
        yearIptx = px.find('input.calendar-menu-year'),//年份输入框
        //显示月份层的触发控件
        spanx = aTodayx.length ? px.find('div.calendar-title span') ://1.3.x版本
        px.find('span.calendar-text'); //1.4.x版本
    if (aTodayx.length) {//1.3.x版本，取消Today按钮的click事件，重新绑定新事件设置日期框为今天，防止弹出日期选择面板
       
        aTodayx.unbind('click').click(function () {
            var now=new Date();
            eb.datebox('hidePanel').datebox('setValue', now.getFullYear() + '-' + (now.getMonth() + 1));
        });
    }
        
    $('#startDate').datebox('setValue', "${formermonth}");
	$('#endDate').datebox('setValue', "${yesterday}");
	
	//时间框禁止输入
	$(".datebox :text").attr("readonly","readonly");
	
});


$(function(){
	$("#winfra").attr("src", "${ctx}/session/statistic/ymtjtable?startDate=${formermonth}&endDate=${yesterday}&r="+Math.random());
	
});



function btfunc() {
	if(!$('#startDate').datebox('getValue') || !$('#endDate').datebox('getValue')) {
		$("#winfra").attr("src", "about:blank");
		alert("时间不能为空!");
		return;
	}
	
	var s = $('#startDate').datebox('getValue')+'-01';
	var e = $('#endDate').datebox('getValue')+'-01';
	
	$.ajax({
		url:"${ctx}/session/statistic/checkdate",
		type:'POST',
		async:false,
		data: {"startDate":s, "endDate":e},
		success:function(data){
			if(!data.result) {
		 		$('#startDate').datebox('setValue');
		 		$("#winfra").attr("src", "about:blank");
				alert('起始日期不能比结束日期晚!');
			}else {
				$("#winfra").attr("src", "about:blank");
				setTimeout(function(){$("#winfra").attr("src", "${ctx}/session/statistic/ymtjtable?startDate="+s+"&endDate="+e+"&r="+Math.random());}, 200);
				
			}
		}
	});
}

function exportInfo() {
	if(!$('#startDate').datebox('getValue') || !$('#endDate').datebox('getValue')) {
		alert("时间不能为空!");
		return;
	}
	
	var s = $('#startDate').datebox('getValue')+'-01';
	var e = $('#endDate').datebox('getValue')+'-01';
	
	$("#sdt").val(s);
	$("#edt").val(e);
	document.getElementById("tempForm1").action = "${ctx}/session/statistic/ymtjExport";
	document.getElementById("tempForm1").submit();
}

</script>
</body>
</html>