<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../TagLib.jsp"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="../Pub.jsp" %>
<meta http-equiv="content-type" content="text/html;charset=UTF-8" />
<style>
.comments {width:90%;overflow:auto;word-break:break-all;border: 1px solid #CCC; }  
</style>
</head>
<body>
<div>
	<form id="adminForm">
		<table>
			<tr>
				<td width="10%" align="right">题目：</td>
				<td width="20%">
					<input type="text" id="title" style="width:210px" name="title" value="${nlctrailer.title }" class="easyui-validatebox" required data-options="validType:['specialy','isBlank','length[0,60]']" />
				</td>
				
				<td width="20%" align="right">栏目：</td>
				<td width="20%">
					<input id="column" name="columnid" value="${nlctrailer.columnid }">
					<input type="hidden" id="columnname" name="columnname" value="${nlctrailer.columnname }">
				</td>
			</tr>
			
			<tr>
				<td width="10%" align="right">主讲：</td>
				<td width="20%">
					<input type="text" id="speakername" style="width:210px" name="speakername" value="${nlctrailer.speakername}" class="easyui-validatebox" data-options="validType:['specialy', 'length[0,50]']"/>
				</td>
				
				<td width="20%" align="right">馆区：</td>
				<td width="20%">
					<select id="guanqu" name="guanqu" style="width:160px;">   
    					<option value="总馆">总馆</option>   
    					<option value="古籍馆">古籍馆</option>
    					<option value="其他">其他</option>
					</select>
				</td>
			</tr>
			
			<tr>
				<td width="10%" align="right">地点：</td>
				<td colspan="3">
					<input type="text" id="place" style="width:350px" name="place" value="${nlctrailer.place}" class="easyui-validatebox" data-options="validType:['specialy', 'length[0,130]']"/>
				</td>
			</tr>
			
			<tr>
				<td width="20%" align="right">时间：</td>
				<td colspan="3">
					<input type="text" id="starttime" name="starttime" class="easyui-datetimebox" /> -- 
					<input type="text" id="endtime" name="endtime" class="easyui-datetimebox" />
				</td>
			</tr>
			
			<tr>
				<td width="10%" align="right">推送方式：</td>
				<td width="20%">
					<input type="checkbox" name="pushmethod" value="0"/>弹窗
					<input type="checkbox" name="pushmethod" value="1"/>站内信
				</td>
				
				<td width="20%" align="right"></td>
				<td width="20%">
				</td>
			</tr>
			
			<tr>
				<td width="10%" align="right">介绍：</td>
				<td colspan="3">
					<textarea class="comments easyui-validatebox" validType="length[0, 400]" rows="8" id="speaker" name="speaker">${nlctrailer.speaker}</textarea>
				</td>
			</tr>
			
		</table>
		<br/>
	</form>
	
	<br/>
	<div align="center">
		<a id="resBtn" class="easyui-linkbutton" data-options="iconCls:'icon-undo'">重置</a>
		<a id="saveBtn" class="easyui-linkbutton" data-options="iconCls:'icon-add'" onclick="saveb()">保存</a>
		<a id="previewBtn" onclick="previewInfo()" class="easyui-linkbutton" data-options="iconCls:'icon-search'">预览</a>
	</div>
</div>

<script>

	$(function(){
		//推送方式写入值
		$("input[name='pushmethod']").each(function(index, obj){
			var strArr = "${nlctrailer.pushmethod}".split(",");
			for(var i = 0; i < strArr.length; i++) {
				if(this.value == strArr[i]) {
					$(this).attr("checked", "checked");
					//$(this).attr("onclick", "return false");
				}
			}
		});
		
		//时间框禁止输入
		$(".datebox :text").attr("readonly","readonly");
		
		$("#column").combobox({
			url: "${ctx}/session/columndic/list",
            method: 'get',
            valueField: 'columnid',
            textField: 'columnname',
            editable: false,
            panelHeight: 'auto',
            onSelect : function (row) {
            	$("#columnname").val(row.columnname);
            }
		})
		
		$("#guanqu").val("${nlctrailer.guanqu}");
		$("#starttime").datetimebox('setValue', "<fmt:formatDate value='${nlctrailer.starttime}' pattern='yyyy-MM-dd HH:mm:ss'/>");
		$("#endtime").datetimebox('setValue', "<fmt:formatDate value='${nlctrailer.endtime}' pattern='yyyy-MM-dd HH:mm:ss'/>");
		
		//重置按钮的点击事件
		$("#resBtn").click(function(){
			$("#speaker").val('');
		})
			
	})
	
	function saveb() {
		//校验
		var isvalid = $("#adminForm").form('validate');
		if(!isvalid) {
			return ;
		}
		$("#saveBtn").removeAttr('onclick');
		
		var starttime = $("#starttime").datetimebox('getValue');
		var endtime = $("#endtime").datetimebox('getValue');
		
		var cont = "1";
		$.ajax({
			url:"${ctx}/session/statistic/checkdate",
			type:'POST',
			async:false,
			data: {"startDate":starttime, "endDate":endtime},
			success:function(data){
				if(!data.result) {
					cont = "0";
				}
			}
		});
		
		if(cont == "0") {
			$("#saveBtn").attr('onclick', 'saveb()');
			alert('起始日期不能比结束日期晚!');
			return ;
		}
		
		var title = $("#title").val();
		var columnid = $("#column").combobox("getValue");
		var columnname = $("#columnname").val();
		var speakername = $("#speakername").val();
		var guanqu = $("#guanqu").val();
		var place = $("#place").val();
		var speaker = $("#speaker").val();
		//=============推送
		var parr =[]; 
		$('input[name="pushmethod"]:checked').each(function(){ 
			parr.push($(this).val()); 
		}); 
		
		$.ajax({
            url: "${ctx}/session/trailer/updateTrailer?r=" + Math.random(),
            data: {"title":title, "columnid":columnid, "columnname":columnname, "speakername":speakername, "guanqu":guanqu, "place":place, "starttime":starttime, "endtime":endtime, "speaker":speaker, "id":"${nlctrailer.id}", "pushmethod":parr.join()},
            cache: false,
            type: "POST",
            success: function(data){
            	var res = eval("("+data+")");
				if(res.result == false) {
					$("#saveBtn").attr('onclick', 'saveb()');
					alert("操作失败");
				}else if(res.result == true) {
					alert("操作成功");
					insertLog("修改讲座预告 " +title);
					parent.tab.datagrid('reload');
					parent.closeWin();
				}
            }
		}); 
	}
	
	var titles = "";
	var speakers = "";
	var places = "";
	//预览
	function previewInfo() {
		titles = $("#title").val();
		speakers = $("#speaker").val();
		places = $("#place").val();
		
		if (navigator.userAgent.indexOf('Firefox') >= 0){
			window.showModalDialog ('${ctx}/session/trailer/addPreview', '预览', 'dialogHeight=680px; dialogWidth=340px');
		} else {
			window.open('${ctx}/session/trailer/addPreview', null,"height=680,width=340,status=yes,toolbar=no,menubar=no,location=no");
		}
	}
	
	function insertLog(message) {
		var loginTime = "<fmt:formatDate value='${loginTime}' pattern='yyyy-MM-dd HH:mm:ss'/>";
		var loginIp = "${loginIp}";
		var username = "${LoginObj.username}";
		var role = "${LoginObj.role}";
		
		$.ajax({
			url:"${ctx}/session/adminlog/insertLog",
			type:'POST',
			async:false,
			data: {"username":username, "role":role, "ip":loginIp, "time":loginTime, "operate": message},
			success:function(data){
				} 
			});
	}
	
</script>
</body>
</html>
