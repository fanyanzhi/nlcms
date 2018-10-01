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
		
		tab = $('#dataTable').datagrid({
			//数据来源
			url:'${ctx}/session/template/list',
			//斑马纹
			striped:true,
			//主键字段
			idField:"id",
			//表单提交方式
			method:"post",
			//是否只能选择一行
			singleSelect:true,
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
					field : 'name',
					title : '模板名称',
					width : 80
				},
				
				{
					field : 'starttime',
					title : '起始时间',
					width : 120
				},
				
				{
					field : 'endtime',
					title : '截止时间',
					width : 120
				},
				
				{
					field : 'datepic',
					title : '日期图片',
					width : 80,
					formatter : function(value, row, index) {
						if(value) {
							return '<img style="width: 30px; height: 30px;" onclick=amp("'+value+'") src="'+value+'"/>';
						}else {
							return '';
						}
					}
				},
				
				{
					field : 'poempic',
					title : '诗词名句',
					width : 80,
					formatter : function(value, row, index) {
						if(value) {
							return '<img style="width: 30px; height: 30px;" onclick=amp("'+value+'") src="'+value+'"/>';
						}else {
							return '';
						}
					}
				},
				
				{
					field : 'backpic',
					title : '整体背景',
					width : 80,
					formatter : function(value, row, index) {
						if(value) {
							return '<img style="width: 30px; height: 30px;" onclick=amp("'+value+'") src="'+value+'"/>';
						}else {
							return '';
						}
					}
				},
				
				{
					field : 'mottopic',
					title : '美德格言',
					width : 80,
					formatter : function(value, row, index) {
						if(value) {
							return '<img style="width: 30px; height: 30px;" onclick=amp("'+value+'") src="'+value+'"/>';
						}else {
							return '';
						}
					}
				},
				
				{
					field : 'separatorpic',
					title : '分隔图片',
					width : 80,
					formatter : function(value, row, index) {
						if(value) {
							return '<img style="width: 30px; height: 30px;" onclick=amp("'+value+'") src="'+value+'"/>';
						}else {
							return '';
						}
					}
				},
				
				{
					field : 'poemsreturnpic',
					title : '返回按键图片',
					width : 80,
					formatter : function(value, row, index) {
						if(value) {
							return '<img style="width: 30px; height: 30px;" onclick=amp("'+value+'") src="'+value+'"/>';
						}else {
							return '';
						}
					}
				},
				
				{
					field : 'translatepic',
					title : '翻译按键图片',
					width : 80,
					formatter : function(value, row, index) {
						if(value) {
							return '<img style="width: 30px; height: 30px;" onclick=amp("'+value+'") src="'+value+'"/>';
						}else {
							return '';
						}
					}
				},
				
				{
					field : 'status',
					title : '状态',
					width : 80,
					formatter : function(value, row, index) {
						if(value == "1") {
							return "已发布";
						}else {
							return "未发布";
						}
					}
				},
				
				{
					field : 'isdefault',
					title : '默认',
					width : 80,
					formatter : function(value, row, index) {
						if(value == "1") {
							return "是";
						}else {
							return "否";
						}
					}
				},
				
				{
					field : 'time',
					title : '创建时间',
					width : 120
				}
				
				<c:if test="${LoginObj.role != 2}">
				,
				{
					field : 'xxxx',
					title : '操作',
					width : 150,
					formatter : function(value, row, index) {
						var flag = row.status;
						var textvalue = "发布";
						var param = 1;	//1是发布， 0是取消
						if(flag == '1') {
							textvalue = '取消';
							param = 0;
						}
						var tem = stripscript(row.name);
						return "<a href='#' onclick='updateInfo("+row.id+")'>修改</a> | <a href='#' onclick=deleteInfo("+row.id+","+"'"+tem+"','"+row.isdefault+"')>删除</a> | <a href='#' onclick=operateFun('"+row.id+"',"+param+",'"+tem+"','"+row.isdefault+"')>"+textvalue+"</a>";
					}
				}
				</c:if>
			]],
			toolbar : '#tbr',
			onLoadSuccess : function(data) {
				$('#dataTable').datagrid("clearSelections");
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
	
	function deleteInfo(id, name, isdefault) {
		if(isdefault == '1') {
			alert('默认模板不能删除!');
			return ;
		}
		
		if(confirm('确定要删除？')){
			$.ajax({
			url:"${ctx}/session/template/delete/" + id +"?r=" + Math.random(),
			type:'GET',
			success:function(data){
					insertLog("删除文津诵读模板 "+name);
					$('#dataTable').datagrid("reload");
				} 
			});
		}else {
			$('#dataTable').datagrid("clearSelections");
		}
	}
	
	function updateInfo(id) {
		$('#dataTable').datagrid("clearSelections");
		openWin2("修改", "${ctx}/session/template/update?id=" + id, 800, 500);
	}
	
	
	$(function(){
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
	
	//新增
	function addObject() {
		openWin2("新增", "${ctx}/session/template/add", 800, 500);
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
	
	function amp(purl) {
		window.open(purl,null,"height=800,width=800,status=yes,toolbar=no,menubar=no,location=no");
	}
	
	//param 1发布，0取消
	function operateFun(id, param, name, isdefault) {
		$('#dataTable').datagrid("clearSelections");
		
		if(param == '0' && isdefault == '1') {
			alert('默认模板不能取消发布！');
			return ;
		}
		
		var confirmword = "";
		if("1" == param) {
			confirmword = "发布";
		}else {
			confirmword = "取消发布";
		}
		
		if(confirm('确定要'+confirmword+'？')){
			$.ajax({
				url:"${ctx}/session/template/publish?id="+id+"&status="+param+"&r=" + Math.random(),
				type:'GET',
				success:function(data){
						  var res = eval("("+data+")");
						  if(res.result == false) {
							alert("操作失败");
						  }else if(res.result == true) {
							insertLog("文津诵读模板 "+name+" "+confirmword);
							$('#dataTable').datagrid("reload");
						  }
					} 
				});
		}
	}
</script>
</head>
<body>
<div>
	<table id="dataTable">
	</table>
	
	<c:if test="${LoginObj.role != 2}">
	<div id="tbr">
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="addObject()">新增</a>
	</div>
	</c:if>
	
</div>

<div id="dataForm" style="overflow:hidden;font-size:10px;display:none;" title="编辑" >
	<iframe id="winSrc" frameborder="0" width="100%" height="100%" src="${ctx}/images/ajax-loader.gif"></iframe>
</div>
</body>
</html>