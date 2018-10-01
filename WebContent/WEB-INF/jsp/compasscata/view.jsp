<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../TagLib.jsp"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="../Pub.jsp" %>

<!-- 扩展EasyUI -->
<script src="${ctx}/js/easyui/extEasyUI.js" type="text/javascript" charset="utf-8"></script>
<!-- 扩展Jquery -->
<script src="${ctx}/js/extJquery.js" type="text/javascript" charset="utf-8"></script>
<!-- 自定义工具类 -->
<script src="${ctx}/js/easyui/lightmvc.js" type="text/javascript" charset="utf-8"></script>
<!-- 扩展EasyUI图标 -->
<link href="${ctx}/js/easyui/lightmvc.css" rel="stylesheet" type="text/css" />

<meta http-equiv="content-type" content="text/html;charset=UTF-8" />
<title>目录</title>
</head>

<body>
<script type="text/javascript">
	var catalogTree;
	var nodeid;
	
	$(function() {
		catalogTree = $('#catalogTree').tree({
			url : '${ctx}/session/readercompasscata/showTree',
			parentField : 'pid',
			lines : true,
			onClick : function(node) {
					nodeid = node.id;
					$("input[name='cataloguuid']").val(nodeid);
					$("#tstext").val(node.text);
					if("root" != nodeid){
						setEnabled();
						getCatalongContent(node.id)
					}

 			},
 			
 			onDblClick : function(node) {
 				$(this).tree('beginEdit',node.target);
 			},
 			onAfterEdit:function(node) {
				catalogEdit(node);
 			},

 			onContextMenu: function(e, node){
				e.preventDefault();
				// 查找节点
				$('#catalogTree').tree('select', node.target);
				nodeid = node.id;
				// 显示快捷菜单
				if(nodeid != 'root'){
					$('#mm1').menu('show', {
						left: e.pageX,
						top: e.pageY
					});
				}else{
					$('#mm2').menu('show', {
						left: e.pageX,
						top: e.pageY
					});
				}
			}
		});
	});

	function append(){
		$('#dd').dialog({
   		title: '添加目录',
    	width: 400,
    	height: 280,
    	closed: false,
    	cache: false,
    	href: '${ctx}/session/readercompasscata/catalogAddPage?pid='+nodeid,
   		buttons : [ {
					text : '添加',
					handler : function() {
					var f = $('#catalogAddForm');
					f.submit();
				}
				} ]
		});
	}

	//编辑目录名
	function catalogEdit(node){
		var id = node.id;
		var text = node.text;
		if("root" == id) {
			return false;
		}
		
		$.ajax({
			type:"POST",
			url:'${ctx}/session/readercompasscata/catalogEdit',
			data:{"cataloguuid":id, "title":text},
			success:function(data){
				insertLog("修改目录名 " +text);
			}
		});
	}

	function edit(){
		$('#edit').dialog({
   		title: '编辑目录',
    	width: 400,
    	height: 280,
    	closed: false,
    	cache: false,
    	href: '${ctx}/session/readercompasscata/catalogEditPage?cataloguuid=' + nodeid,
   		buttons : [ {
				text : '提交',
				handler : function() {
					var f = $('#catalogEditForm');
					f.submit();
					catalogTree.tree('reload');
				}
			} ]
		});
	}

	function saveContent(){
		var f = $("#contentForm");
		f.submit();
	}

	//得到对应节点的content
	function getCatalongContent(cataloguuid){
		$.ajax({
			type:"POST",
			url:'${ctx}/session/readercompasscata/getCatalongContent',
			data:{"cataloguuid":cataloguuid},
			dataType: "json",
			success:function(data){
				setContent(data.content);
			}
		});
	}

	function del(){
		$.messager.confirm('确认','您确认想要删除该目录及其子目录吗？',function(r){
    		if(r){
		       $.ajax({
					type:"POST",
					url:'${ctx}/session/readercompasscata/delCatalog',
					data:{"cataloguuid":nodeid},
					success:function(data){
						var res = eval("("+data+")");
						if (res.result) {
							insertLog("删除目录 " +nodeid);
							$.messager.alert('提示', '删除成功', 'info');
							catalogTree.tree('reload');
						}else {
							$.messager.alert('提示', '操作失败', 'info');
						}
					}
			    });
   		 	}
		});
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
<br/>
	<div id="mm1" class="easyui-menu" style="width:120px;">
		<div onclick="append()" data-options="iconCls:'icon-add'">追加</div>
		<div onclick="del()" data-options="iconCls:'icon-remove'">删除</div>
		<div onclick="edit()" data-options="iconCls:'icon-remove'">编辑</div>
	</div>

	<div id="mm2" class="easyui-menu" style="width:120px;">
		<div onclick="append()" data-options="iconCls:'icon-add'">追加</div>
	</div>

	<div id="dd" ></div>
	<div id="edit" ></div>
	
	<div class="easyui-layout" data-options="fit:true,border:false">
		<div data-options="region:'center',fit:true,border:false,href:'${ctx }/session/readercompasscata/contentPage?title=${title }'"  title="内容"></div>
		
		<div data-options="region:'west',border:false,split:true" title="目录" style="width:200px; ">
			<div style="display:none">
				<table>
					<tr><td></td></tr>
				</table>
			</div>
			
			<table id="catalogTree" style="width:180px;margin: 10px 10px 10px 10px"></table>
		</div>
	</div>
</body>

</html>