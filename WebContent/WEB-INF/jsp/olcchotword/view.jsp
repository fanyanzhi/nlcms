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
	var editRow = undefined; //定义全局变量：当前编辑的行
	
	$(function(){
		tab = $('#dataTable').datagrid({
			//数据来源
			url:'${ctx}/session/olcchotword/list',
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
			pageSize:20,
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
					field : 'hotword',
					title : '热词',
					width : 150,
					editor: { type: 'validatebox', options: {required: true, validType:['specialy','isBlank','length[0, 100]']} }
				},
				
				{
					field : 'seacount',
					title : '搜索次数',
					width : 100
				},
				
				{
					field : 'sort',
					title : '推荐顺序',
					width : 100,
					formatter : function(value, row, index) {
						if(10000 == value) {
							return '';
						}else {
							return value;
						}
					},
					editor: { type: 'numberbox', options: {required:true,max:10000,min:1}}
				}
				
				<c:if test="${LoginObj.role != 2}">
				,
				{
					field : 'xxxx',
					title : '操作',
					width : 100,
					formatter : function(value, row, index) {
						var hotwords = stripscript(row.hotword);
						return "<a href='#' onclick=deleteInfo("+row.id+","+"'"+hotwords+"')>删除</a>";
					}
				}
				</c:if>
			]],
			toolbar : '#tbr',
			onLoadSuccess : function(data) {
				$('#dataTable').datagrid("clearSelections");
				editRow = undefined;
				gvar = 1;
			},
			<c:if test="${LoginObj.role != 2}">
			onClickCell: function(rowIndex, field, value){
			   	if(field=='sort' || field == 'hotword'){
				    if (editRow != undefined) {
			        	$('#dataTable').datagrid("endEdit", editRow);
			        }
			        if (editRow == undefined) {
			        	$('#dataTable').datagrid("beginEdit", rowIndex);
			        	editRow = rowIndex;
			        }
		        }else {
		        	$('#dataTable').datagrid("endEdit", editRow);
		        }
			},
			</c:if>
			onAfterEdit:function(index,rowData,changes){
		    	editRow = undefined;  
	            $('#dataTable').datagrid('refreshRow', index);  
		    }

		})
		
	})
	
	function selectAll() {
		if(gvar == 1) {
			$('#dataTable').datagrid('selectAll');
			gvar = 2;		
		}else if(gvar == 2) {
			$('#dataTable').datagrid('unselectAll');
			gvar = 1;
		}
	}
	
	function deleteInfo(id, title) {
		if(confirm('确定要删除？')){
			$.ajax({
			url:"${ctx}/session/olcchotword/delete/" + id +"?r=" + Math.random(),
			type:'GET',
			success:function(data){
					insertLog("删除olcc热词 "+title);
					$('#dataTable').datagrid("reload");
				} 
			});
		}else {
			$('#dataTable').datagrid("clearSelections");
		}
	}
	
	function deleteAll() {
		var rows = $('#dataTable').datagrid("getSelections");
		if(rows.length == 0) {
			alert('请先选择数据!');
			return ;
		}
		
		if(confirm('确定要删除？')) {
			var idArr = [];
			var titleArr = [];
			for(var i = 0; i < rows.length; i++) {
				idArr.push(rows[i].id);
				var hotwords = stripscript(rows[i].hotword);
				titleArr.push(hotwords);
			}
			
			$.ajax({
				url:"${ctx}/session/olcchotword/deleteAll?r=" + Math.random(),
				data: "ids=" + idArr.join(),
				type:'POST',
				success:function(data){
						insertLog("删除olcc热词 "+titleArr.join());	
						$('#dataTable').datagrid("reload");
					}
				});
		}else {
			$('#dataTable').datagrid("clearSelections");
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
	
	function stripscript(s) { 
		if(s) {
			var pattern = new RegExp("[`~!@#$^&*()=|{}':;',\\[\\].<>/?~！@#￥……&*（）——|{}【】‘；：”“'。，、？]") 
			var rs = ""; 
			for (var i = 0; i < s.length; i++) { 
				rs = rs+s.substr(i, 1).replace(pattern, ''); 
			} 
			rs = rs.replace(/ /g, '');
			rs = $.trim(rs);
			return rs;
		}else {
			return "";
		}
	}
	
	//导出到Excel
	function exportInfo() {
		document.getElementById("tempForm").action = "${ctx}/session/";
		document.getElementById("tempForm").submit();
		
	}
	
	//修改
	function modifyInfo() {
        var rows = tab.datagrid("getSelections");
        if(rows.length == 0) {
			alert('请先选择数据!');
			return ;
		}else if (rows.length == 1) {
			 //修改之前先关闭已经开启的编辑行，当调用endEdit该方法时会触发onAfterEdit事件
            if (editRow != undefined) {
            	tab.datagrid("endEdit", editRow);
            }
            //当无编辑行时
            if (editRow == undefined) {
            	//获取到当前选择行的下标
                var index = tab.datagrid("getRowIndex", rows[0]);
              	//开启编辑
                tab.datagrid("beginEdit", index);
              	//把当前开启编辑的行赋值给全局变量editRow
                editRow = index;
                //当开启了当前选择行的编辑状态之后，
                //应该取消当前列表的所有选择行，要不然双击之后无法再选择其他行进行编辑
                tab.datagrid("clearSelections");
            }
        }else {
        	alert('请只选择一条数据!');
			return ;
        }
	}
	
	//撤销
	function cancelInfo() {
		if(editRow != undefined) {
			$('#dataTable').datagrid("endEdit", editRow);
	        editRow = undefined;
		}
    	tab.datagrid("rejectChanges");
        tab.datagrid("clearSelections");
	}
	
	//保存
	function saveInfo() {
		var errornums = $(".validatebox-invalid").size();
		if(errornums > 0) {
			alert("请满足校验规则!");
			return ;
		}
		
		if(editRow != undefined) {
			$('#dataTable').datagrid("endEdit", editRow);
	        editRow = undefined;
		}
		
		var rows = $('#dataTable').datagrid('getChanges');
		if(rows.length > 0) {
			var effectRow =  JSON.stringify(rows);
			
			var wordarr = [];
			for(var i = 0; i < rows.length; i++) {
				var hotwords = stripscript(rows[i].hotword);
				wordarr.push(hotwords);
			}
			
			$.ajax({
				cache : false,
				async : false,
				type : 'post',
				url : "${ctx}/session/olcchotword/saveModi",
				data : {json : effectRow},
				dataType: "json",
				success : function(data){
					var res = eval("("+data+")");
					if(res.result == false) {
						alert("操作失败");
					}else if(res.result == true) {
						insertLog("修改olcc热词 " +wordarr.join());
						tab.datagrid('reload');
					}
					
				} 
			});

		}else {
			alert("无新数据保存");
			return ;
		}
	}
	
</script>
</head>
<body>
<div>
	<table id="dataTable">
	</table>
	<div id="tbr">
		<c:if test="${LoginObj.role != 2}">
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="saveInfo()">保存</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="modifyInfo()">修改</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="cancelInfo()">撤销</a>
		</c:if>
		
		<c:if test="${LoginObj.role == 2}">
			<!-- <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-redo',plain:true" onclick="exportInfo()">导出Excel</a> -->
		</c:if>
	</div>
	
	<form id="tempForm">
	</form>
</div>

<div id="dataForm" style="overflow:hidden;font-size:10px;display:none;" title="编辑" >
	<iframe id="winSrc" frameborder="0" width="100%" height="100%" src="${ctx}/images/ajax-loader.gif"></iframe>
</div>
</body>
</html>