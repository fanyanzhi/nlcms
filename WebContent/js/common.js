/**打开窗口显示在屏幕的中央*/
function openWin(title,url, width, height) {
	$('#dataForm').show();
	$('#dataForm').window("open");
	var curDomHeight=document.body.scrollHeight;									//网页可见区域高
	var opt = {}
	opt.width = width;				
	opt.height = curDomHeight>height?height:(curDomHeight-6);
	$('#dataForm').window('resize', opt);
	
	$('#dataForm').window('setTitle',title);
	if (width){
		$('#dataForm').window('move',{
		 	left:(document.body.clientWidth-width)/2,   							//(网页可见区域宽-设置的窗口的宽度)/2
	  		top:$(document).scrollTop()+(curDomHeight-height)/2    					//滚动条位置+(网页可见区域高-设置的窗口的高度)/2
		});
	}
	var link="";
	if(url.indexOf("?")>-1){
		link=url+"&rand="+Math.random();
	}else{
		link=url+"?rand="+Math.random();
	}
	document.getElementById("winSrc").src = link;
	try {
		if (formChanged) {
			formChanged = 0;
		}
	} catch (e) {
	}
}

//打开窗口
function openWin2(title,url, width, height) {
		$('#dataForm').show();
		$('#dataForm').window("open");
		var opt = {}
		opt.width = document.body.clientWidth-50;
		opt.height = document.documentElement.clientHeight-50;
		if (width) {
			if(opt.width>width){
				opt.width = width;
			}
		}
		if (height) {
			if(opt.height>height){
				opt.height = height;
			}
		}
		var l=0;
		var t=0;
		if(document.body.clientWidth>opt.width){
			l=document.body.clientWidth-opt.width;
		}
		if(document.documentElement.clientHeight>opt.height){
			t=document.documentElement.clientHeight-opt.height;
		}
		
		$('#dataForm').window('resize', opt);
		$('#dataForm').window('setTitle',title);
		if (width){
			$('#dataForm').window('move',{   
			  left:l/2,   
			  top:t/2   
			});
		}
		var link="";
		if(url.indexOf("?")>-1){
			link=url+"&rand="+Math.random();
		}else{
			link=url+"?rand="+Math.random();
		}
		document.getElementById("winSrc").src = link;
		try {
			if (formChanged) {
				formChanged = 0;
			}
		} catch (e) {
		}
}

//关闭窗口
function closeWin() {
	$('#dataForm').hide();
	$('#dataForm').window("close");
}