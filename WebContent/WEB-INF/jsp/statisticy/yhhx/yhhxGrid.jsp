<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../TagLib.jsp"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="../../Pub.jsp" %>
<script type="text/javascript" src="${ctx}/js/myweek/WdatePicker.js"></script>
<meta http-equiv="content-type" content="text/html;charset=UTF-8" />

<script type="text/javascript">
	var tab;
	
	$(function(){
		
		tab = $('#dataTable').datagrid({
			//数据来源
			url:'${ctx}/session/statisticy/yhhxList',
			//斑马纹
			striped:true,
			//主键字段
			//idField:"xxx",
			//表单提交方式
			method:"post",
			//是否只能选择一行
			singleSelect:false,
			showFooter: true,
			//是否分页
			pagination:true,
			//分页信息
			pageSize:10,
			//每页显示条目下拉菜单
			pageList:[10,20],
			//参数
			queryParams:{
				startDate:"${lastmonth}",
				endDate:"${yesterday}"
			},
			rownumbers:true,//行号 
			//分页工具所在位置
			pagePosition:"bottom",
			//冻结的列
			frozenColumns:[[
			        //选择框
			]],
			columns:[[
				{
					field : 'rdrolecode',
					title : '读者类别',
					width : 80,
					formatter : function(value, row, index) {
						if(value == "0000") {
							return "虚拟";
						}else if(value == "JS0001") {
							return "实名";
						}else {
							return "读者卡";
						}
					}
				},
				
				{
					field : 'name',
					title : '读者姓名',
					width : 110
				},
				
				{
					field : 'loginaccount',
					title : '账号',
					width : 200
				},
				
				{
					field : 'cardtype',
					title : '证件类别',
					width : 80,
					formatter : function(value, row, index) {
						if(value == "01") {
							return "身份证";
						}else if(value == "02") {
							return "军官证";
						}else if(value == "03") {
							return "护照";
						}else if(value == "04") {
							return "港澳通行证";
						}else if(value == "05") {
							return "台湾通讯证";
						}
					}
				},
				
				{
					field : 'cardno',
					title : '证件号',
					width : 150
				},
				
				{
					field : 'age',
					title : '年龄',
					width : 80
				},
				
				{
					field : 'sextype',
					title : '性别',
					width : 80,
					formatter : function(value, row, index) {
						if(value == "null") {
							return "";
						}else if(value == "男士" || value == "男") {
							return "男士";
						}else if(value == "女士" || value == "女") {
							return "女士"
						}else {
							return value
						}
					}
				},
				
				{
					field : 'cs',
					title : '使用次数',
					width : 80
				},
				
				{
					field : 'sc',
					title : '使用时长(秒)',
					width : 80
				},
				
				{
					field : 'pv',
					title : '页面访问数',
					width : 80
				},
				
				{
					field : 'uv',
					title : '唯一页面访问数',
					width : 100
				},
				
				{
					field : 'ds',
					title : '下载次数',
					width : 80
				},
				
				{
					field : 'xxxx',
					title : '操作',
					width : 100,
					formatter : function(value, row, index) {
						return "<a href='#' onclick=detail('"+row.loginaccount+"')>用户详情</a>";
					}
				}
			]],
			toolbar : '#tbr',
			onLoadSuccess : function(data) {
				$('#dataTable').datagrid("clearSelections");
			}
		})
	
		$('#dataForm').window({
			title : 'Window',
			width : 500,
			height : 300,
			closed : true,
			minimizable : false,
			top : 50,
			onClose : function() {
				document.getElementById("winSrc").src = "${ctx}/images/ajax-loader.gif";
			},
			modal : true,
			maximizable : true
		});
	})
	
	//用户详情
	function detail(loginaccount) {
		var ws = $("#wstartDate").val();
		var we = $("#wendDate").val();
		$('#dataTable').datagrid("clearSelections");
		openWin2("用户详情", "${ctx}/session/statisticy/detail?loginaccount="+loginaccount+"&startDate="+ws+"&endDate="+we, 800, 500);
	}
	
	//按天统计
	function tongji() {
		var ws = $("#wstartDate").val();
		var we = $("#wendDate").val();
		
		if(!ws || !we) {
			alert("时间不能为空!");
			return;
		}
		
		var cont = "1";
		$.ajax({
			url:"${ctx}/session/statistic/checkdate",
			type:'POST',
			async:false,
			data: {"startDate":ws, "endDate":we},
			success:function(data){
				if(!data.result) {
					cont = "0";
				}
			}
		});
		
		if(cont == "0") {
			alert('起始日期不能比结束日期晚!');
			return ;
		}
		
		tab.datagrid("load", {
			//读者姓名
			"name" : $("#name").val(),
			//读者卡号码 
			"loginaccount" : $("#loginaccount").val(),
			//读者证件号码
			"cardno" : $("#cardno").val(),
			//读者类别
			"rdrolecode" : $("#rdrolecode").val(),
			//证件类别
			"cardtype" : $("#cardtype").val(),
			"startDate":ws,
			"endDate":we
		});
	}

	$(function(){
		$("#resetBtn").click(function(){
			$(':input').not(':button, :submit, :reset, :hidden, #d121, #d122').val('').removeAttr('checked').removeAttr('selected');
			tongji();
		})
	})
	
	//导出到Excel
	function exportInfo() {
		var ws = $("#wstartDate").val();
		var we = $("#wendDate").val();
		
		$("#sdt").val(ws);
		$("#edt").val(we);
		$("#namex").val($("#name").val());
		$("#loginaccountx").val($("#loginaccount").val());
		$("#rdrolecodex").val($("#rdrolecode").val());
		$("#cardtypex").val($("#cardtype").val());
		$("#cardnox").val($("#cardno").val());
		
		document.getElementById("tempForm").action = "${ctx}/session/statisticy/yhhxExport";
		document.getElementById("tempForm").submit();
	}
</script>
</head>
<body onkeydown="if (event.keyCode == 13) {$('#queryBtn').click();} ">
<div>
<table style="width:100%" >
		<tr>
				<td width="10%" align="right">起止日期：</td>
				<td width="30%">
					<input id="d121" type="text" class="Wdate" value="${lastmonthweeks}" onfocus="WdatePicker({
					vel:'wstartDate'})"/>
				--
				<input id="d122" type="text" class="Wdate" value="${yesweeks}" onfocus="WdatePicker({
					vel:'wendDate'})"/>
				
				<input type="hidden" name="wstartDate" id="wstartDate" value="${lastmonth}">
				<input type="hidden" name="wendDate" id="wendDate" value="${yesterday}">
				</td>
				
				<td width="10%" align="right"></td>
				<td width="20%"></td>
				
				<td width="10%" align="right"></td>
				<td width="20%"></td>
			</tr>
	
		<tr>
			<td width="10%" align="right">读者姓名：</td>
			<td width="20%"><input type="text" id="name" style="width:210px" name="name" /></td>
		
			<td width="10%" align="right">读者卡号：</td>
				<td width="20%"><input type="text" id="loginaccount" style="width:210px" name="loginaccount" /></td>
				
				<td width="10%" align="right">读者类别：</td>
				<td width="20%">
					<select id="rdrolecode" name="rdrolecode" style="width:210px;">   
						<option></option>
    					<option value="JS0001">实名用户</option>   
    					<option value="JS0002">读者卡用户</option>   
    					<option value="0000">虚拟用户</option>   
					</select>
				</td>
			</tr>
			
			<tr>
				<td width="10%" align="right">证件类别：</td>
				<td width="20%">
					<select id="cardtype" name="cardtype" style="width:210px;">   
						<option></option>
    					<option value="01">身份证</option>   
    					<option value="02">军官证</option>   
    					<option value="03">护照</option>   
    					<option value="04">港澳通行证</option>   
    					<option value="05">台湾通讯证</option>   
					</select>
				</td>
				
				<td width="10%" align="right">证件号：</td>
				<td width="20%"><input type="text" id="cardno" style="width:210px" name="cardno"/></td>
				
				<td width="10%" align="right"></td>
				<td width="20%"></td>
			</tr>
			
		</table>
		<br/>
		<div align="center">
			<input id="queryBtn" type="button" onclick="tongji()" value="统计" />
			<input id="resetBtn" type="button" value="重置" />
		</div>
	<br/>

	<br/>
	<table id="dataTable">
	</table>
	<div id="tbr">
	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-redo',plain:true" onclick="exportInfo()">导出Excel</a>
	</div>
	
	<form id="tempForm">
		<input type="hidden" name="startDate" id="sdt">
		<input type="hidden" name="endDate" id="edt">
		<input type="hidden" name="name" id="namex">
		<input type="hidden" name="loginaccount" id="loginaccountx">
		<input type="hidden" name="rdrolecode" id="rdrolecodex">
		<input type="hidden" name="cardtype" id="cardtypex">
		<input type="hidden" name="cardno" id="cardnox">
	</form>
</div>

<div id="dataForm" style="overflow:hidden;font-size:10px;display:none;" title="编辑" >
	<iframe id="winSrc" frameborder="0" width="100%" height="100%" src="${ctx}/images/ajax-loader.gif"></iframe>
</div>
</body>
</html>