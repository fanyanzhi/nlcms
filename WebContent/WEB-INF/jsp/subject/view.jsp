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
			url:'${ctx}/session/subject/list',
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
					field : 'sort',
					title : '推荐顺序',
					width : 80,
					formatter : function(value, row, index) {
						if(10000 == value) {
							return '';
						}else {
							return value;
						}
					}
				},
				
				{
					field : 'title',
					title : '专题名称',
					width : 300
				},
				
				{
					field : 'createtime',
					title : '创建时间',
					width : 125
				},
				
				{
					field : 'pubtime',
					title : '发布时间',
					width : 125
				},
				
				{
					field : 'creater',
					title : '创建人',
					width : 120
				},
				
				{
					field : 'status',
					title : '状态',
					width : 130
				},
				
				{
					field : 'source',
					title : '来源',
					width : 100
				},
				
				{
					field : 'xxxx',
					title : '操作',
					width : 220,
					formatter : function(value, row, index) {
						var flag = row.status;
						var subjectid = row.subjectid;
						var textvalue = "发布";
						var status = 1;	//1是上架， 0是下架
						if(flag == '已发布') {
							textvalue = '取消';
							status = 0;
						}
						var titles = stripscript(row.title);
						
						<c:if test="${LoginObj.role != 2}">
							return "<a href='#' onclick='updateInfo("+row.id+")'>编辑</a> | <a href='#' onclick=deleteInfo("+row.id+","+"'"+titles+"')>删除</a> | <a href='#' onclick=dirInfo('"+subjectid+"',"+"'"+titles+"')>目录</a> | <a href='#' onclick=previewInfo('"+subjectid+"')>预览</a> | <a href='#' onclick=operateFun("+row.id+",'"+titles+"','"+textvalue+"')>"+textvalue+"</a>";
						</c:if>
						
						<c:if test="${LoginObj.role == 2}">
							return "<a href='#' onclick=previewInfo('"+subjectid+"')>预览</a>";
						</c:if>
					}
				}
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
	
	function deleteInfo(id, title) {
		if(confirm('确定要删除？')){
			$.ajax({
			url:"${ctx}/session/subject/delete/" + id +"?r=" + Math.random(),
			type:'GET',
			success:function(data){
					insertLog("删除专题 "+title);
					$('#dataTable').datagrid("reload");
				} 
			});
		} 
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
	
	//预览
	function previewInfo(subjectid) {
		if (navigator.userAgent.indexOf('Firefox') >= 0){
			window.showModalDialog ('${ctx}/session/subject/itemPreview?subjectid=' + subjectid, '预览', 'dialogHeight=680px; dialogWidth=340px');
		} else {
			window.open('${ctx}/session/subject/itemPreview?subjectid=' + subjectid, null,"height=680,width=340,status=yes,toolbar=no,menubar=no,location=no");
		}	
	}
	
	//1是上架， 0是下架
	function operateFun(id, title, textvalue) {
		$('#dataTable').datagrid("clearSelections");

		if(confirm('确定要'+textvalue+'？')){
			$.ajax({
			url:"${ctx}/session/subject/shelf/" + id +"?r=" + Math.random(),
			type:'GET',
			success:function(data){
					  var res = eval("("+data+")");
					  if(res.result == false) {
						alert("操作失败");
					  }else if(res.result == true) {
						insertLog("特色专题 "+title+" "+textvalue);
						$('#dataTable').datagrid("reload");
					  }
				} 
			});
		}
	}
	
	//目录
	function dirInfo(subjectid, title) {
		openWin2("", "${ctx}/session/subject/catalogPage?subjectid=" + subjectid +"&title=" + title, 900, 600);
	}
	
	//修改
	function updateInfo(id) {
		openWin2("修改", "${ctx}/session/subject/update?id=" + id, 1300, 700);
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
	
	//推荐排序
	function ftops() {
		var rows = $('#dataTable').datagrid("getSelections");
		if(rows.length == 0) {
			alert('请先选择数据!');
			return ;
		}
		
		var statusx = rows[0].status;
		var id = rows[0].id;
		
		if('已发布' == statusx) {
			openWin2("推荐排序", "${ctx}/session/subject/sort?id=" + id, 600, 300);
		}else {
			alert('只能推荐已发布的专题!');
			return ;
		}
	}
	
	//添加
	function addnews() {
		$('#dataTable').datagrid("clearSelections");
		openWin2("添加", "${ctx}/session/subject/add", 1300, 700);
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
			url:"${ctx}/session/subject/pull",
			type:'POST',
			async:false,
			cache: false,
			success:function(data){
				progressClose();
				
				if(data.result == false) {
					alert("操作失败，"+data.message);
				}else if(data.result == true) {
					alert("操作成功");
					insertLog("拉取专题 ");
					tab.datagrid('reload');
				}
			}
		});
	}
	
	
	//首页专题显示数量
	function enumx() {
		openWin2("首页专题显示数量", "${ctx}/session/subject/enumshow", 400, 180);
	}

</script>
</head>
<body>
<div>
	<table id="dataTable">
	</table>
	<div id="tbr">
		<c:if test="${LoginObj.role != 2}">
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-redo',plain:true" onclick="ftops()">推荐排序</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="addnews()">添加</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-reload',plain:true" onclick="pull()">拉取</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true" onclick="enumx()">首页专题显示数量</a>
		</c:if>
	</div>
</div>

<div id="dataForm" style="overflow:hidden;font-size:10px;display:none;" title="编辑" >
	<iframe id="winSrc" frameborder="0" width="100%" height="100%" src="${ctx}/images/ajax-loader.gif"></iframe>
</div>
</body>
</html>