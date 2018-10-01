<%@ page language="java"  pageEncoding="utf-8"%>
<base target="_self" />
<link rel="stylesheet" type="text/css" href="${ctx}/js/easyui/themes/default/easyui.css"/>
<link rel="stylesheet" type="text/css" href="${ctx}/js/easyui/themes/icon.css"/>
<script type="text/javascript" src="${ctx}/js/easyui/jquery.min.js"></script>
<script type="text/javascript" src="${ctx}/js/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${ctx}/js/easyui/locale/easyui-lang-zh_CN.js"></script>
<script src="${ctx}/js/common.js" type="text/javascript" charset="utf-8"></script>
<script type="text/javascript">
	
	$.extend($.fn.validatebox.defaults.rules, {
		special: {
	        validator: function(value){    
	        	return /^[_a-zA-Z\u4e00-\u9fa50-9]*$/.test(value);    
	        },    
	        message: '只能输入汉字、字母、数字、下划线。'   
	    },
	    specialx: {
	        validator: function(value){    
	        	return /^[\-0-9]*$/.test(value);    
	        },    
	        message: '只能输入数字、中杠。'   
	    },
	    specialy: {
	        validator: function(value){   
	        	return value.indexOf("<")==-1;    
	        },    
	        message: '不能输入<'   
	    },
	    specialz: {
	        validator: function(value){    
	        	return /^((ht|f)tps?):\/\/([\w\-]+(\.[\w\-]+)*\/)*[\w\-]+(\.[\w\-]+)*\/?(\?([\w\-\.,@?^=%&:\/~\+#]*)+)?/.test(value);    
	        },    
	        message: '请输入正确格式的url'   
	    },
    	minLength: {
	        validator: function(value, param){    
	            return value.length >= param[0];    
	        },    
	        message: '最少输入 {0}字符。'   
	    },
	    maxLength: {
	        validator: function(value, param){    
	            return value.length <= param[0];    
	        },    
	        message: '最多输入{0}字符。'   
	    },
		idcard: {// 验证身份证
			validator: function (value) {
				return /^\d{15}(\d{2}[A-Za-z0-9])?$/i.test(value);
			},
			message: '身份证号码格式不正确'
		},
		 phone: {// 验证电话号码
               validator: function (value) {
                   return /^((\d2,3)|(\d{3}\-))?(0\d2,3|0\d{2,3}-)?[1-9]\d{6,7}(\-\d{1,4})?$/i.test(value);
               },
               message: '格式不正确,请使用下面格式:020-88888888'
           },
           mobile: {// 验证手机号码
               validator: function (value) {
                   return /^(13|15|18)\d{9}$/i.test(value);
               },
               message: '手机号码格式不正确'
           },
           intOrFloat: {// 验证整数或小数
               validator: function (value) {
                   return /^\d+(\.\d+)?$/i.test(value);
               },
               message: '请输入数字，并确保格式正确'
           },
           currency: {// 验证货币
               validator: function (value) {
                   return /^\d+(\.\d+)?$/i.test(value);
               },
               message: '货币格式不正确'
           },
           qq: {// 验证QQ,从10000开始
               validator: function (value) {
                   return /^[1-9]\d{4,9}$/i.test(value);
               },
               message: 'QQ号码格式不正确'
           },
           integer: {// 验证整数 可正负数
               validator: function (value) {
                   //return /^[+]?[1-9]+\d*$/i.test(value);
                   return /^([+]?[0-9])|([-]?[0-9])+\d*$/i.test(value);
               },
               message: '请输入整数'
           },
           age: {// 验证年龄
               validator: function (value) {
                   return /^(?:[1-9][0-9]?|1[01][0-9]|120)$/i.test(value);
               },
               message: '年龄必须是0到120之间的整数'
           },
           chinese: {// 验证中文
               validator: function (value) {
                   return /^[\Α-\￥]+$/i.test(value);
               },
               message: '请输入中文'
           },
           english: {// 验证英语
               validator: function (value) {
                   return /^[A-Za-z]+$/i.test(value);
               },
               message: '请输入英文'
           },
           unnormal: {// 验证是否包含空格和非法字符
               validator: function (value) {
                   return /.+/i.test(value);
               },
               message: '输入值不能为空和包含其他非法字符'
           },
           username: {// 验证用户名
               validator: function (value) {
                   return /^[a-zA-Z][a-zA-Z0-9_]{5,15}$/i.test(value);
               },
               message: '用户名不合法（字母开头，允许6-16字节，允许字母数字下划线）'
           },
           jobno: {// 验证jobno
               validator: function (value) {
                   return /^[a-zA-Z][a-zA-Z0-9_]{1,11}$/i.test(value);
               },
               message: 'jobno不合法（字母开头，允许2-12字节，只允许字母数字）'
           },
           faxno: {// 验证传真
               validator: function (value) {
                   //            return /^[+]{0,1}(\d){1,3}[ ]?([-]?((\d)|[ ]){1,12})+$/i.test(value);
                   return /^((\d2,3)|(\d{3}\-))?(0\d2,3|0\d{2,3}-)?[1-9]\d{6,7}(\-\d{1,4})?$/i.test(value);
               },
               message: '传真号码不正确'
           },
           zip: {// 验证邮政编码
               validator: function (value) {
                   return /^[1-9]\d{5}$/i.test(value);
               },
               message: '邮政编码格式不正确'
           },
           ip: {// 验证IP地址
               validator: function (value) {
                   return /d+.d+.d+.d+/i.test(value);
               },
               message: 'IP地址格式不正确'
           },
           name: {// 验证姓名，可以是中文或英文
               validator: function (value) {
                   return /^[\Α-\￥]+$/i.test(value) | /^\w+[\w\s]+\w+$/i.test(value);
               },
               message: '请输入姓名'
           },
           date: {// 验证姓名，可以是中文或英文
               validator: function (value) {
                   //格式yyyy-MM-dd或yyyy-M-d
                   return /^(?:(?!0000)[0-9]{4}([-]?)(?:(?:0?[1-9]|1[0-2])\1(?:0?[1-9]|1[0-9]|2[0-8])|(?:0?[13-9]|1[0-2])\1(?:29|30)|(?:0?[13578]|1[02])\1(?:31))|(?:[0-9]{2}(?:0[48]|[2468][048]|[13579][26])|(?:0[48]|[2468][048]|[13579][26])00)([-]?)0?2\2(?:29))$/i.test(value);
               },
               message: '清输入合适的日期格式'
           },
           msn: {
               validator: function (value) {
                   return /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/.test(value);
               },
               message: '请输入有效的msn账号(例：abc@hotnail(msn/live).com)'
           },
           same: {
               validator: function (value, param) {
                   if ($("#" + param[0]).val() != "" && value != "") {
                       return $("#" + param[0]).val() == value;
                   } else {
                       return true;
                   }
               },
               message: '两次输入的密码不一致！'
           },
           nochinese: {//不能是中文 
           	   validator: function (value) {
           	   	   return /^[^\u4e00-\u9fa5]+$/.test(value);
           	   },
           	   message: '不能输入中文'
           },
           isBlank: {
               validator: function (value, param) { return $.trim(value) != '' },
               message: '不能为空，全空格也不行'
           },
           psword: {
               validator: function (value) {
                   return /^[a-zA-Z]\w{5,17}$/.test(value);
               },
               message: '以字母开头，长度在6~18之间，只能包含字符、数字和下划线'
           },
           eqPwd : {
       			validator : function(value, param) {
       				return value == $(param[0]).val();
       			},
       			message : '密码不一致！'
       	   }
	});  
	
	

	// 把form转成json对象
	(function($){
		$.fn.serializeJson=function(){
			var serializeObj={};
			var array=this.serializeArray();
			var str=this.serialize();
			$(array).each(function(){
				if(serializeObj[this.name]){
					if($.isArray(serializeObj[this.name])){
						serializeObj[this.name].push(this.value);
					}else{
						serializeObj[this.name]=[serializeObj[this.name],this.value];
					}
				}else{
					serializeObj[this.name]=this.value;	
				}
			});
			return serializeObj;
		};
	})(jQuery);
	
	/**
	  * 检查日期格式
	  */
	function checkEndDate(startDateId,endDateId){
		 
		 $('#'+startDateId).datebox({
			 onSelect:function(date){
			 	var startDate = $('#'+startDateId).datebox('getValue');
			 	var endDate = $('#'+endDateId).datebox('getValue');
			 	
			 	if(endDate!="" && endDate <= startDate){
			 		alert('起始日期不能晚于等于'+endDate);
			 		$('#'+startDateId).datebox('setValue');
			 	}
		 	}
		 });
		 
		 $('#'+endDateId).datebox({
			 onSelect:function(date){
			 	var startDate = $('#'+startDateId).datebox('getValue');
			 	var endDate = $('#'+endDateId).datebox('getValue');
			 	
			 	if(startDate!="" && endDate <= startDate){
			 		alert('结束日期不能早于等于'+startDate);
			 		$('#'+endDateId).datebox('setValue');
			 	}
		 	}
		 });  
	 }
	
	/**
	  * 检查日期格式2
	  */
	function checkEndDate2(startDateId,endDateId){
		 $('#'+startDateId).datebox({
			 onSelect:function(date){
			 	var startDate = $('#'+startDateId).datebox('getValue');
			 	var endDate = $('#'+endDateId).datebox('getValue');
			 	
			 	if(endDate!="" && endDate < startDate){
			 		alert('起始日期不能晚于'+endDate);
			 		$('#'+startDateId).datebox('setValue');
			 	}
		 	}
		 });
		 
		 $('#'+endDateId).datebox({
			 onSelect:function(date){
			 	var startDate = $('#'+startDateId).datebox('getValue');
			 	var endDate = $('#'+endDateId).datebox('getValue');
			 	
			 	if(startDate!="" && endDate < startDate){
			 		alert('结束日期不能早于'+startDate);
			 		$('#'+endDateId).datebox('setValue');
			 	}
		 	}
		 });  
	 }
	
	/**
	 * 针对panel window dialog三个组件拖动时会超出父级元素的修正
	 * 如果父级元素的overflow属性为hidden，则修复上下左右个方向
	 * 如果父级元素的overflow属性为非hidden，则只修复上左两个方向
	 * @param left
	 * @param top
	 * @returns
	 */
	var easyuiPanelOnMove = function(left, top) {
		var parentObj = $(this).panel('panel').parent();
		if (left < 0) {
			$(this).window('move', {
				left : 1
			});
		}
		if (top < 0) {
			$(this).window('move', {
				top : 1
			});
		}
		var width = $(this).panel('options').width;
		var height = $(this).panel('options').height;
		var right = left + width;
		var buttom = top + height;
		var parentWidth = parentObj.width();
		var parentHeight = parentObj.height();
		if(parentObj.css("overflow")=="hidden"){
			if(left > parentWidth-width){
				$(this).window('move', {
					"left":parentWidth-width
				});
			}
			if(top > parentHeight-height){
				$(this).window('move', {
					"top":parentHeight-height
				});
			}
		}
	};
	$.fn.panel.defaults.onMove = easyuiPanelOnMove;
	$.fn.window.defaults.onMove = easyuiPanelOnMove;
	$.fn.dialog.defaults.onMove = easyuiPanelOnMove;
	
	
	function loadOpen(name,top) {
		if(name) {
			$("#loading").html(name);
		}
		$(".showbox").show();
		$(".overlay").css({'display':'block','opacity':'0.6'});
		if(!top){
			var top = $(document).scrollTop(); 
			var isParent = false;
			if(top == 0){
				top = $(parent.document).scrollTop(); 
				isParent = true;
			}
			if(isParent){
				top = top+200;
			}else{
				top = 200;
			}
		}
		$(".showbox").stop(true).animate({'margin-top':top/2,'opacity':'1'},0);
	}

	function loadCancel() {
		$(".showbox").stop(true).animate({'margin-top':'0','opacity':'0'},0);
		$(".showbox").hide();
		$(".overlay").css({'display':'none','opacity':'0'});
	}
</script>
