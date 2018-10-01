	$(function(){
		
		//返回顶部，缓慢向上
		$(".wenFanHui").click(function(){
		$('body,html').animate({scrollTop:0},1000);
		})
		$(".mainNavLeft2").click(function(){
		$(".xingQing").css('dispaly','block');
		})
		
		/*返回顶部按钮 一开始隐藏 滚动一屏距离后显示*/
		$(document).scroll(function(){ 
		var  scrollTop =  $(document).scrollTop(),bodyHeight = $(window).height(); 
		if(scrollTop > bodyHeight){ 
			$('.wenXianfanHuiBox .wenXianfanHui').css('display','block');
		}else{
			$('.wenXianfanHuiBox .wenXianfanHui').css('display','none');
		}
		$(".mainNavLeft2").click(function(){
		$(".xingQing").css('dispaly','none');
	}) 
	})

	})


