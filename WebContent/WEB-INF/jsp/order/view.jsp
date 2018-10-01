<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../TagLib.jsp"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="../Pub.jsp" %>
<meta http-equiv="content-type" content="text/html;charset=UTF-8" />

<script type="text/javascript">
	var tab;
	
	$(function(){
		//结束时间大于开始时间
		checkEndDate('pstartDate','pendDate');

		//时间框禁止输入
		$(".datebox :text").attr("readonly","readonly");
		
		tab = $('#dataTable').datagrid({
			//数据来源
			url:'${ctx}/session/orderinfo/list',
			//斑马纹
			striped:true,
			//主键字段
			idField:"id",
			//表单提交方式
			method:"post",
			//是否只能选择一行
			singleSelect:false,
			//是否分页
			pagination:true,
			//分页信息
			pageSize:10,
			//每页显示条目下拉菜单
			pageList:[10,20,30],
			//参数
			queryParams:{
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
					field : 'id',
					checkbox : true
				},
				
				{
					field : 'type',
					title : '书类',
					width : 100,
					formatter : function(value, row, index) {
						if(value == "national") {
							return "中文书";
						}else {
							return "外文书";
						}
					}
				},
				
				{
					field : 'orderno',
					title : '订单号',
					width : 200
				},
				
				{
					field : 'tradeno',
					title : '交易号',
					width : 200
				},
				
				{
					field : 'sum',
					title : '支付金额',
					width : 100
				},
				
				{
					field : 'time',
					title : '支付时间',
					width : 150
				},
				
				{
					field : 'loginaccount',
					title : '读者卡号',
					width : 150
				},
				
				{
					field : 'rdrolecode',
					title : '用户类型',
					width : 100,
					formatter : function(value, row, index) {
						if(value == "0000") {
							return "虚拟";
						}else if(value == "JS0001") {
							return "实名";
						}else if(value == "JS0002") {
							return "物理卡";
						}else {
							return "";
						}
					}
				},
				
				{
					field : 'name',
					title : '姓名',
					width : 150
				},
				
				{
					field : 'cardno',
					title : '证件号',
					width : 150
				}
			]],
			toolbar : '#tbr',
			onLoadSuccess : function(data) {
				$('#dataTable').datagrid("clearSelections");
			}
		})
		
	})
	
	
	//导出到Excel
	function exportInfo() {
		var spstartDate = $("#pstartDate").datebox("getValue");
		var spendDate = $("#pendDate").datebox("getValue");
		var sloginaccount = $("#loginaccount").val();
		var sname = $("#name").val();
		var scardno = $("#cardno").val();
		
		$("#spstartDate").val(spstartDate);
		$("#spendDate").val(spendDate);
		$("#sloginaccount").val(sloginaccount);
		$("#sname").val(sname);
		$("#scardno").val(scardno);

		document.getElementById("tempForm").action = "${ctx}/session/orderinfo/export";
		document.getElementById("tempForm").submit();
		
	}
	
	$(function(){
		$("#queryBtn").click(function(){
			var pstartDate = $("#pstartDate").datebox("getValue");
			var pendDate = $("#pendDate").datebox("getValue");
			
			tab.datagrid("load", {
				//用户ID
				"loginaccount" : $("#loginaccount").val(),
				//姓名
				"name" : $("#name").val(),
				//卡号
				"cardno" : $("#cardno").val(),
				//支付起始时间
				"pstartDate" : pstartDate,
				//支付结束时间
				"pendDate" : pendDate
			});
		})
		
		$("#resetBtn").click(function(){
			$(':input').not(':button, :submit, :reset, :hidden').val('').removeAttr('checked').removeAttr('selected');
			$("#pstartDate").datebox("setValue");
			$("#pendDate").datebox("setValue");
			$("#queryBtn").click();
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
</head>
<body onkeydown="if (event.keyCode == 13) {$('#queryBtn').click();} ">
<div>
	<form id="queryForm">
		<table style="width:100%" >
			<tr>
				<td width="10%" align="right">读者卡号：</td>
				<td width="40%"><input type="text" id="loginaccount" style="width:210px" name="loginaccount" /></td>
				
				<td width="10%" align="right">姓名：</td>
				<td width="40%">
					<input type="text" id="name" style="width:210px" name="name" />
				</td>
			</tr>
			
			<tr>
				<td width="10%" align="right">证件号码：</td>
				<td width="40%">
					<input type="text" id="cardno" style="width:210px" name="cardno" />
				</td>
				<td width="10%" align="right">支付时间：</td>
				<td width="40%">
					<input type="text" id="pstartDate" name="pstartDate" class="easyui-datebox" /> -- 
					<input type="text" id="pendDate" name="pendDate" class="easyui-datebox" />
				</td>
				
			</tr>
		</table>
		
		<br/>
		<div align="center">
			<input id="queryBtn" type="button" value="查询" />
			<input id="resetBtn" type="button" value="重置" />
		</div>
	</form>
	
	<br/>
	<table id="dataTable">
	</table>
	<div id="tbr">
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-redo',plain:true" onclick="exportInfo()">导出Excel</a>
	</div>
	
	<form id="tempForm">
	<input type="hidden" name="spstartDate" id="spstartDate" />
	<input type="hidden" name="spendDate" id="spendDate" />
	<input type="hidden" name="sloginaccount" id="sloginaccount" />
	<input type="hidden" name="sname" id="sname" />
	<input type="hidden" name="scardno" id="scardno" />
	</form>
</div>

<div id="dataForm" style="overflow:hidden;font-size:10px;display:none;" title="编辑" >
	<iframe id="winSrc" frameborder="0" width="100%" height="100%" src="${ctx}/images/ajax-loader.gif"></iframe>
</div>
</body>
</html>