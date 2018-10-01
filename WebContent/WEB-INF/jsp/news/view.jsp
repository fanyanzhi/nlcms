<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../TagLib.jsp"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="../Pub.jsp" %>
<meta http-equiv="content-type" content="text/html;charset=UTF-8" />

<script type="text/javascript">
	var tab;
	var gvar = 1;
	
	$(function(){
		//结束时间大于开始时间
		checkEndDate('pstartDate','pendDate');
		checkEndDate('sstartDate','sendDate');
		//时间框禁止输入
		$(".datebox :text").attr("readonly","readonly");
		
		tab = $('#dataTable').datagrid({
			//数据来源
			url:'${ctx}/session/news/list',
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
					field : 'tops',
					title : '置顶',
					width : 50,
					formatter : function(value, row, index) {
						if(value == '1') {
							return "置顶";
						}else {
							return "";
						}
					}
				},
				
				{
					field : 'subTime',
					title : '上传时间',
					width : 125
				},
				
				{
					field : 'title',
					title : '新闻标题',
					width : 230
				},
				
				{
					field : 'praisecount',
					title : '点赞',
					width : 80,
					sortable : true
				},
				
				{
					field : 'collectcount',
					title : '收藏',
					width : 80,
					sortable : true
				},
				
				{
					field : 'subPerson',
					title : '上传人',
					width : 100
				},
				
				{
					field : 'source',
					title : '来源',
					width : 100
				},
				
				{
					field : 'pubTime',
					title : '内容发布时间',
					width : 125,
					sortable : true
				},
				
				{
					field : 'bkpbtime',
					title : '后台发布时间',
					width : 125,
					sortable : true
				},
				
				{
					field : 'status',
					title : '状态',
					width : 100
				},
				
				{
					field : 'xxxx',
					title : '操作',
					width : 200,
					formatter : function(value, row, index) {
						var flag = row.status;
						var textvalue = "发布";
						var status = 1;	//1是发布， 0是取消
						if(flag == '已发布') {
							textvalue = '取消';
							status = 0;
						}
						
						<c:if test="${LoginObj.role != 2}">
							var tem = stripscript(row.title);
							return "<a href='#' onclick='updateInfo("+row.id+")'>编辑</a> | <a href='#' onclick=deleteInfo("+row.id+","+"'"+tem+"',"+"'"+row.tops+"')>删除</a> | <a href='#' onclick=previewInfo('"+row.newsid+"')>预览</a> | <a href='#' onclick=operateFun("+row.id+","+status+",'"+tem+"')>"+textvalue+"</a>";
						</c:if>
						
						<c:if test="${LoginObj.role == 2}">
							return "<a href='#' onclick=previewInfo('"+row.newsid+"')>预览</a>";
						</c:if>
					}
				}
			]],
			toolbar : '#tbr',
			onLoadSuccess : function(data) {
				$('#dataTable').datagrid("clearSelections");
				gvar = 1;
			}
		})
		
	})
	
	function stripscript(s) {
		if(s) {
			var pattern = new RegExp("[`~!@#$^&*()=|{}':;',\\[\\].<>/?~！@#￥……&*（）——|{}【】‘；：”“'。，、？]") 
			var rs = ""; 
			for (var i = 0; i < s.length; i++) { 
				rs = rs+s.substr(i, 1).replace(pattern, ''); 
			} 
			rs = rs.replace(/ /g, '');
			return rs;
		}else {
			return "";
		}
	}
	
	function deleteInfo(id, title, tops) {
		if(tops == '1') {
			alert('不能删除置顶项，请先取消置顶！');
			return ;
		}
		
		if(confirm('确定要删除？')){
			$.ajax({
			url:"${ctx}/session/news/delete/" + id +"?r=" + Math.random(),
			type:'GET',
			success:function(data){
					insertLog("删除新闻 "+title);
					$('#dataTable').datagrid("reload");
				} 
			});
		}else {
			$('#dataTable').datagrid("clearSelections");
		}
	}
	
	
	function selectAll() {
		if(gvar == 1) {
			$('#dataTable').datagrid('selectAll');
			gvar = 2;		
		}else if(gvar == 2) {
			$('#dataTable').datagrid('unselectAll');
			gvar = 1;
		}
	}
	
	//取消置顶
	function cantops() {
		var rows = $('#dataTable').datagrid("getSelections");
		if(rows.length == 0) {
			alert('请先选择数据!');
			return ;
		}else if(rows.length > 1) {
			alert('只能选择一条!');
			return ;
		}
		
		var tops = rows[0].tops;
		var id = rows[0].id;
		var title = stripscript(rows[0].title);
		
		if('1' != tops) { 
			alert("请选择已置顶新闻!");
			return ;
		}else {
			if(confirm('确定要取消置顶？')){
				$.ajax({
				url:"${ctx}/session/news/cantop/" + id +"?r=" + Math.random(),
				type:'GET',
				success:function(data){
							var res = eval("("+data+")");
					  		if(res.result == false) {
								alert("操作失败");
					  		}else if(res.result == true) {
					  			insertLog("取消置顶新闻 "+title);
								$('#dataTable').datagrid("reload");
					  		}
					} 
				});
			}else {
				$('#dataTable').datagrid("clearSelections");
			}
		}
	}
	
	//置顶
	function ftops() {
		var rows = $('#dataTable').datagrid("getSelections");
		if(rows.length == 0) {
			alert('请先选择数据!');
			return ;
		}else if(rows.length > 1) {
			alert('只能选择一条!');
			return ;
		}
		
		var statusx = rows[0].status;
		var id = rows[0].id;
		var title = stripscript(rows[0].title);
		
		if('已发布' == statusx) {
			if(confirm('确定要置顶？')){
				$.ajax({
				url:"${ctx}/session/news/settop/" + id +"?r=" + Math.random(),
				type:'GET',
				success:function(data){
							var res = eval("("+data+")");
					  		if(res.result == false) {
								alert("操作失败");
					  		}else if(res.result == true) {
					  			insertLog("置顶新闻 "+title);
								$('#dataTable').datagrid("reload");
					  		}
					} 
				});
			}else {
				$('#dataTable').datagrid("clearSelections");
			}
		}else {
			alert('只能置顶已发布的新闻!');
			return ;
		}
	}
	
	function deleteAll() {
		var rows = $('#dataTable').datagrid("getSelections");
		if(rows.length == 0) {
			alert('请先选择数据!');
			return ;
		}
		
		for(var i = 0; i < rows.length; i++) {
			var to = rows[i].tops;
			if(to == '1') {
				alert('不能删除置顶项，请先取消置顶！');
				return ;
			}
		}
		
		if(confirm('确定要删除？')) {
			
			var idArr = [];
			var titleArr = [];
			for(var i = 0; i < rows.length; i++) {
				idArr.push(rows[i].id);
				titleArr.push(rows[i].title);
			}
			
			$.ajax({
				url:"${ctx}/session/news/deleteAll?r=" + Math.random(),
				data: "ids=" + idArr.join(),
				type:'POST',
				success:function(data){
						insertLog("删除新闻 "+titleArr.join());
						$('#dataTable').datagrid("reload");
					}
				});
		}else {
			$('#dataTable').datagrid("clearSelections");
		}
	}
	
	//导出到Excel
	function exportInfo() {
		document.getElementById("tempForm").action = "${ctx}/session/news/export";
		document.getElementById("tempForm").submit();
	}
	
	$(function(){
		$("#queryBtn").click(function(){
			var pstartDate = $("#pstartDate").datebox("getValue");
			var pendDate = $("#pendDate").datebox("getValue");
			
			var sstartDate = $("#sstartDate").datebox("getValue");
			var sendDate = $("#sendDate").datebox("getValue");
			
			
			tab.datagrid("load", {
				//新闻标题
				"title" : $("#title").val(),
				//上传人 
				"subPerson" : $("#subPerson").val(),
				//发布起始时间
				"pstartDate" : pstartDate,
				//发布结束时间
				"pendDate" : pendDate,
				//上传起始时间
				"sstartDate" : sstartDate,
				//上传结束时间
				"sendDate" : sendDate,
				//状态
				"status" : $("#status").val(),
				//来源
				"source" : $("#source").val()
			});
		})
		
		$("#resetBtn").click(function(){
			$(':input').not(':button, :submit, :reset, :hidden').val('').removeAttr('checked').removeAttr('selected');
			$("#pstartDate").datebox("setValue");
			$("#pendDate").datebox("setValue");
			$("#sstartDate").datebox("setValue");
			$("#sendDate").datebox("setValue");
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
	
	//预览
	function previewInfo(newsid) {
		$('#dataTable').datagrid("clearSelections");
		if (navigator.userAgent.indexOf('Firefox') >= 0){
			window.showModalDialog ('${ctx}/session/news/itemPreview?newsid=' + newsid, '预览', 'dialogHeight=680px; dialogWidth=340px');
		} else {
			window.open('${ctx}/session/news/itemPreview?newsid=' + newsid, null,"height=680,width=340,status=yes,toolbar=no,menubar=no,location=no");
		}
	}
	
	//status 1发布，0取消
	function operateFun(id, status, title) {
		$('#dataTable').datagrid("clearSelections");
		
		var confirmword = "";
		if("1" == status) {
			confirmword = "发布";
		}else {
			confirmword = "取消发布";
		}
		
		if(confirm('确定要'+confirmword+'？')){
			$.ajax({
				url:"${ctx}/session/news/publish?id="+id+"&status="+status+"&r=" + Math.random(),
				type:'GET',
				success:function(data){
						  var res = eval("("+data+")");
						  if(res.result == false) {
							alert("操作失败");
						  }else if(res.result == true) {
							insertLog("新闻 "+title+" "+confirmword);
							$('#dataTable').datagrid("reload");
						  }
					} 
				});
		}
		
	}
	
	//修改
	function updateInfo(id) {
		$('#dataTable').datagrid("clearSelections");
		openWin2("修改", "${ctx}/session/news/update?id=" + id, 1300, 700);
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
	
	//添加
	function addnews() {
		$('#dataTable').datagrid("clearSelections");
		openWin2("添加", "${ctx}/session/news/add", 1300, 700);
	}
	
	function progressLoad(){  
	    $("<div class=\"datagrid-mask\" style=\"position:absolute;z-index: 9999;\"></div>").css({display:"block",width:"100%",height:"1500"}).appendTo("body");  
	    $("<div class=\"datagrid-mask-msg\" style=\"position:absolute;z-index: 9999;\"></div>").html("正在处理，请稍候。。。").appendTo("body").css({display:"block",left:($(document.body).outerWidth(true) - 190) / 2,top:($(window).height() - 45) / 2});  
	}

	function progressClose(){
		$(".datagrid-mask").remove();  
		$(".datagrid-mask-msg").remove();
	}
	
	function pull() {
		progressLoad();
		
		$.ajax({
			url:"${ctx}/session/news/pull",
			type:'POST',
			async:false,
			cache: false,
			success:function(data){
				progressClose();
				
				if(data.result == false) {
					alert("操作失败，"+data.message);
				}else if(data.result == true) {
					alert("操作成功");
					insertLog("拉取新闻");
					tab.datagrid('reload');
				}
			}
		});
	}
	
</script>
</head>
<body onkeydown="if (event.keyCode == 13) {$('#queryBtn').click();} ">
<div>
	<form id="queryForm">
		<table style="width:80%">
			<tr>
				<td width="20%" align="right">新闻标题：</td>
				<td width="30%"><input type="text" id="title" style="width:210px" name="title" /></td>
				
				<td width="20%" align="right">上传人：</td>
				<td width="30%"><input type="text" id="subPerson" style="width:210px" name="subPerson" /></td>
			</tr>
			
			<tr>
				<td width="20%" align="right">内容发布时间：</td>
				<td colspan="3">
					<input type="text" id="pstartDate" name="pstartDate" class="easyui-datebox" /> -- 
					<input type="text" id="pendDate" name="pendDate" class="easyui-datebox" />
				</td>
			</tr>
			
			<tr>
				<td width="20%" align="right">上传时间：</td>
				<td colspan="3">
					<input type="text" id="sstartDate" name="sstartDate" class="easyui-datebox" /> -- 
					<input type="text" id="sendDate" name="sendDate" class="easyui-datebox" />
				</td>
			</tr>
			
			<tr>
				<td width="20%" align="right">状态：</td>
				<td width="30%">
					<select id="status" name="status" style="width:210px;">   
						<option></option>
    					<option value="已发布">已发布</option>   
    					<option value="未发布">未发布</option>   
					</select>
				</td>
				
				<td width="20%" align="right">来源：</td>
				<td width="30%">
					<select id="source" name="source" style="width:210px;">   
						<option></option>
    					<option value="手机门户">手机门户</option>   
    					<option value="原创">原创</option>   
					</select>
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
	<c:if test="${LoginObj.role != 2}">
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-ok',plain:true" onclick="selectAll()">全选</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="addnews()">添加</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="deleteAll()">删除</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true" onclick="ftops()">置顶</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true" onclick="cantops()">取消置顶</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-redo',plain:true" onclick="exportInfo()">导出Excel</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-reload',plain:true" onclick="pull()">拉取</a>
	</c:if>
	
	<c:if test="${LoginObj.role == 2}">
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-redo',plain:true" onclick="exportInfo()">导出Excel</a>
	</c:if>
	</div>
	
	<form id="tempForm"></form>
</div>

<div id="dataForm" style="overflow:hidden;font-size:10px;display:none;" title="编辑" >
	<iframe id="winSrc" frameborder="0" width="100%" height="100%" src="${ctx}/images/ajax-loader.gif"></iframe>
</div>
</body>
</html>