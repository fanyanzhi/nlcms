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
			url:'${ctx}/session/ads/list',
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
					field : 'name',
					title : '名称',
					width : 150
				},
				
				{
					field : 'src',
					title : '图片',
					width : 100,
					formatter : function(value, row, index) {
						if(value) {
								return '<img style="width: 30px; height: 30px;" onclick=amp("'+value+'") src="'+value+'"/>';
						}else {
							return '';
						}
					}
				},
				
				{
					field : 'url',
					title : '链接地址',
					width : 200
				},
				
				{
					field : 'seq',
					title : '顺序',
					width : 80
				},
				
				{	// 0专题，1广告
					field : 'type',
					title : '分类',
					width : 100,
					formatter : function(value, row, index) {
						if(value == 0) {
							return '专题';
						}else if(value == 1) {
							return '广告';
						}
					}
				},
				
				{	// 0下架，1上架
					field : 'status',
					title : '状态',
					width : 100,
					formatter : function(value, row, index) {
						if(value == 0) {
							return '下架';
						}else if(value == 1) {
							return '上架';
						}
					}
				}
				
				<c:if test="${LoginObj.role != 2}">
				,
				{
					field : 'xxxx',
					title : '操作',
					width : 150,
					formatter : function(value, row, index) {
						var sta = row.status;
						var statext = "";
						if(sta == 0) {	//现在处在下架状态，需要上架
							statext = "上架";
						}else {			//现在处在上架状态，需要下架
							statext = "下架";
						}
						var tem = stripscript(row.name);
						return "<a href='#' onclick='updateInfo("+row.id+")'>编辑</a> | <a href='#' onclick=deleteInfo("+row.id+","+"'"+tem+"')>删除</a> | <a href='#' onclick=operInfo("+row.id+",'"+tem+"','"+statext+"')>"+statext+"</a>";
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
		if(s){
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
	
	function deleteInfo(id, name) {
		
		if(confirm('确定要删除？')){
			$.ajax({
			url:"${ctx}/session/ads/delete/" + id +"?r=" + Math.random(),
			type:'GET',
			success:function(data){
					insertLog("删除广告 "+name);
					$('#dataTable').datagrid("reload");
				} 
			});
		}else {
			$('#dataTable').datagrid("clearSelections");
		}
	}
	
	//上下架
	function operInfo(id, name, statext) {
		$('#dataTable').datagrid("clearSelections");
		
		if(confirm('确定要'+statext+'？')){
			$.ajax({
			url:"${ctx}/session/ads/shelf/" + id +"?r=" + Math.random(),
			type:'GET',
			success:function(data){
					  var res = eval("("+data+")");
					  if(res.result == false) {
						alert("操作失败");
					  }else if(res.result == true) {
						insertLog("广告 "+name+" "+statext);
						$('#dataTable').datagrid("reload");
					  }
				} 
			});
		}
		
	}
	
	function updateInfo(id) {
		$('#dataTable').datagrid("clearSelections");
		
		openWin2("编辑", "${ctx}/session/ads/update?id=" + id, 600, 400);
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
		openWin2("新增", "${ctx}/session/ads/add", 600, 400);
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