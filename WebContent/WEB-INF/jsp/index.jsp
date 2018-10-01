<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="./TagLib.jsp"%>
<!DOCTYPE html>
<html class="panel-fit">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>国图后台管理</title>
<script type="text/javascript" src="${ctx}/js/jquery-1.8.3.js"></script>
<link id="easyuiTheme" href="${ctx}/js/easyui/themes/default/easyui.css" rel="stylesheet" type="text/css" />
<script src="${ctx}/js/easyui/jquery.easyui.min.js" type="text/javascript" charset="utf-8"></script>
<%-- <script src="${ctx}/js/easyui/easyui-lang-zh_CN.js" type="text/javascript" charset="utf-8"></script> --%>
<script src="${ctx}/js/easyui/extEasyUI.js" type="text/javascript" charset="utf-8"></script>
<script src="${ctx}/js/easyui/lightmvc.js" type="text/javascript" charset="utf-8"></script>
<script src="${ctx}/js/extJquery.js" type="text/javascript" charset="utf-8"></script>
<link href="${ctx}/js/easyui/lightmvc.css" rel="stylesheet" type="text/css" />
<script src="${ctx}/js/common.js" type="text/javascript" charset="utf-8"></script>
<script type="text/javascript">
	$(window).load(function() {
		jQuery("#loading").fadeOut();
	});
</script>
<title>主页</title>
<script type="text/javascript">
	var index_layout;
	var index_tabs;
	var index_tabsMenu;
	var layout_west_tree;
	//var layout_west_tree_url = '${ctx}/session/menutree';
	var layout_west_tree_url = '${ctx}/session/menu';

	$(function() {
		index_layout = jQuery('#index_layout').layout({
			fit : true
		});

		index_tabs = jQuery('#index_tabs').tabs(
				{
					fit : true,
					border : false,
					tools : [
							{
								iconCls : 'icon_home',
								handler : function() {
									index_tabs.tabs('select', 0);
								}
							},
							{
								iconCls : 'icon_refresh',
								handler : function() {
									var t = index_tabs.tabs('getSelected');
									var url = $(t.panel('options').content).attr('src');
									if(url) {
										index_tabs.tabs('update', {
											tab : t,
											options : {
												content : '<iframe src="' + url + '" frameborder="0" style="border:0;width:100%;height:98%;"></iframe>'
											}
										});
									}
								}
							},
							{
								iconCls : 'icon_del',
								handler : function() {
									var index = index_tabs.tabs('getTabIndex', index_tabs.tabs('getSelected'));
									var tab = index_tabs.tabs('getTab', index);
									if (tab.panel('options').closable) {
										index_tabs.tabs('close', index);
									}
								}
							} ]
				});

		layout_west_tree = jQuery('#layout_west_tree').tree({
			url : layout_west_tree_url,
			parentField : 'pid',
			lines : true,
			onClick : function(node) {
				/* if (node.attributes && node.attributes.url) {
					var url = '/nlcms' + node.attributes.url;
					addTab({
						url : url,
						title : node.text,
						iconCls : node.iconCls
					});
				} */
				
				if (node.url) {
					var url = '${ctx}' + node.url;
					addTab({
						url : url,
						title : node.text,
						iconCls : node.iconCls
					});
				}
			}
		});
		
		
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
	});

	function addTab(params) {
		var iframe = '<iframe src="' + params.url + '" frameborder="0" style="border:0;width:100%;height:98%;"></iframe>';
		var t = jQuery('#index_tabs');
		var opts = {
			title : params.title,
			closable : true,
			iconCls : params.iconCls,
			content : iframe,
			border : false,
			fit : true
		};
		if (t.tabs('exists', opts.title)) {
			t.tabs('select', opts.title);
		} else {
			t.tabs('add', opts);
		}
	}

	function logout() {
		jQuery.messager.confirm('提示', '确定要退出?', function(r) {
			if (r) {
				progressLoad();
				jQuery.post('${ctx}/session/admin/logout', function(data) {
					var res = eval("("+data+")");
					if (res.result) {
						progressClose();
						window.location.href = '${ctx}/login';
					}else {
						progressClose();
						alert("注销失败");
					}
				}, 'json');
			}
		});
	}

	function editUserPwd() {
		//openWin2("修改", "${ctx}/session/admin/editPwdPage", 400, 250);
		
		parent.jQuery.modalDialog({
			title : '修改密码',
			width : 300,
			height : 250,
			href : '${ctx}/session/admin/editPwdPagebak',
			buttons : [ {
				text : '修改',
				handler : function() {
					var f = parent.$.modalDialog.handler.find('#editUserPwdForm');
					f.submit();
				}
			} ]
		});
	}
</script>
</head>
<body>
	<div id="loading" style="position: fixed; top: -50%; left: -50%; width: 200%; height: 200%; background: #fff; z-index: 100; overflow: hidden;">
		<img src="${ctx}/images/ajax-loader.gif" style="position: absolute; top: 0; left: 0; right: 0; bottom: 0; margin: auto;" />
	</div>
	<div id="index_layout">
		<div data-options="region:'north'" style="overflow: hidden;" id="header">
			<span style="float: right; padding-right: 20px;">欢迎 <b>APP</b>&nbsp;&nbsp;
				<a href="javascript:void(0)" onclick="editUserPwd()" style="color: #fff">修改密码</a>&nbsp;&nbsp;<a href="javascript:void(0)" onclick="logout()" style="color: #fff">安全退出</a>
				&nbsp;&nbsp;&nbsp;&nbsp;
			</span> <span class="header"></span>
		</div>
		<div data-options="region:'west',split:true" title="菜单" style="width: 200px; overflow: hidden; overflow-y: auto;">
			<div class="well well-small" style="padding: 10px 5px 5px 5px;">
				<ul id="layout_west_tree"></ul>
			</div>
		</div>
		<div data-options="region:'center'" style="overflow: hidden;">
			<div id="index_tabs" style="overflow: hidden;">
				<div title="首页" data-options="border:false" style="overflow: hidden;">
					<div style="padding: 10px 0 10px 10px">
						<h2>系统介绍</h2>
						<div class="light-info">
							<div class="light-tip icon-tip"></div>
							<div>欢迎您使用国图移动APP后台管理系统。</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div data-options="region:'south',border:false" style="height: 30px; overflow: hidden; text-align: center; background-color: #daeef5">国家图书馆版权所有</div>
	</div>
	
<div id="dataForm" style="overflow:hidden;font-size:10px;display:none;" title="编辑" >
	<iframe id="winSrc" frameborder="0" width="100%" height="100%" src="${ctx}/images/ajax-loader.gif"></iframe>
</div>
</body>
</html>