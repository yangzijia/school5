$(document).ready(function(){
$(".root_menu_title").toggle(
	function(){
	$(this).next("ul.sub_menu").show();   //显示子菜单
	$(this).css('background-image','url(images/adminImages/close_icon.png)');  //显示子菜单icon显示
	$(this).parent("div.root_menu_middle").css('background-color','#eee');  //选中背景变色		  
	$(this).parent("div.root_menu_middle").prev("div.root_menu_top").css('background-image','url(images/adminImages/selected_root_menu_top_bg.png)');  //选中top背景加载
	$(this).parent("div.root_menu_middle").next("div.root_menu_bottom").css('background-image','url(images/adminImages/selected_root_menu_bottom_bg.png)');  //选中bottom背景加载
	$(this).parent("div.root_menu_middle").parent("div.root_menu_module").siblings("div.root_menu_module").children("div.root_menu_middle").css('background-color','transparent');  //相邻子嗣背景色清除
	$(this).parent("div.root_menu_middle").parent("div.root_menu_module").siblings("div.root_menu_module").children("div.root_menu_middle").prev("div.root_menu_top").css('background-image','none');  //相邻子嗣top背景图清除
	$(this).parent("div.root_menu_middle").parent("div.root_menu_module").siblings("div.root_menu_module").children("div.root_menu_middle").next("div.root_menu_bottom").css('background-image','none');   //相邻子嗣bottom背景图清除
	},
	function(){
	$(this).next("ul.sub_menu").hide();  //隐藏子菜单
	$(this).css('background-image','url(images/adminImages/open_icon.png)');  //隐藏子菜单icon显示
	})
})                //root_menu 切换
	 

$(document).ready(function(){
	$(".sub_menu li").click(function(){
          $(this).parent().parent("div.root_menu_middle").css('background-color','#eee'); //选中背景变色
		  $(this).parent().parent("div.root_menu_middle").prev("div.root_menu_top").css('background-image','url(images/adminImages/selected_root_menu_top_bg.png)');   //选中top背景加载
		  $(this).parent().parent("div.root_menu_middle").next("div.root_menu_bottom").css('background-image','url(images/adminImages/selected_root_menu_bottom_bg.png)');    //选中bottom背景加载
		  $(this).parent().parent().parent("div.root_menu_module").siblings("div.root_menu_module").children("div.root_menu_middle").css('background-color','transparent');   //相邻子嗣背景色清除
		  $(this).parent().parent().parent("div.root_menu_module").siblings("div.root_menu_module").children("div.root_menu_middle").prev("div.root_menu_top").css('background-image','none');      //相邻子嗣top背景图清除
		  $(this).parent().parent().parent("div.root_menu_module").siblings("div.root_menu_module").children("div.root_menu_middle").next("div.root_menu_bottom").css('background-image','none');   //相邻子嗣bottom背景图清除
		  })
	})      //点击 sub_menu 切换
	 
$(document).ready(function(){
	$(".menu_fold").toggle(
	function(){
		$(".left_nav").width(26).height(62).children("[class!=menu_fold]").hide();
		$(this).width(25).height(62).css('background-image','url(images/adminImages/menu_folded_bg.png)');
		$(this).children("span.menu_fold_icon").css('background-image','url(images/adminImages/open_icon.png)')
		},
	function(){
		$(".left_nav").width(220).children().show();
		$(this).width(219).height(29).css('background-image','url(images/adminImages/menu_fold_bg.png)');
		$(this).children("span.menu_fold_icon").css('background-image','url(images/adminImages/close_icon.png)')
		}
	)
	})                 //Left Nav 折叠收起

	 
