
﻿$(function(){
	
	InitLeftMenu();
	tabClose();
	tabCloseEven();
})

function InitLeftMenu() {
$(".easyui-accordion").empty();
$.ajax({			
	type:'POST',
	url:'menuGet.html',
	//为了与全局同步设置异步为false赋值全局变量也应该这样
	async : false,
	dataType:'json',
	success:function(data)
	{	var menulist = "";
		var obj = data;
		
		for(var i=0;i<obj.length;i++){
			if(obj[i].childrenMenu!=null && typeof obj[i].childrenMenu != "undefined"){
				menulist+= '<div title="'+obj[i].parentMenuName+'" icon="icon-sys" style="overflow:auto;">';
				menulist+= '<ul>';
			   	for(var j =0;j<obj[i].childrenMenu.length;j++){
			   		
					menulist+= '<li><div><a target="mainFrame"  onfocus="this.blur()"  href='+obj[i].childrenMenu[j].url+'>'+obj[i].childrenMenu[j].childrenmenuName + '</a></div></li> ';								
			   	/*onfocus="this.blur()"     是我加的   为了适应ie6.7的     效果还没有验证*/
			   	}
			   	 menulist += '</ul></div>';
			}else{
			menulist+= '<div title="'+obj[i].parentMenuName+'" style="overflow:auto;"></div>';
			
		}
	}
		$(".easyui-accordion").append(menulist);
	}
});
	$('.easyui-accordion li a').click(function(){
		var tabTitle = $(this).text();
		var url = $(this).attr("href");

		
 $("#home ~ div").remove();
 $(".tabs-inner").parent().next("li").remove();/*去除已生成的div及其它的标签*/

 
		addTab(tabTitle,url);


		$('.easyui-accordion li div').removeClass("selected");
		$(this).parent().addClass("selected");
	}).hover(function(){
		$(this).parent().addClass("hover");
	},function(){
		$(this).parent().removeClass("hover");
	});

	$(".easyui-accordion").accordion();
}
function addTab(subtitle,url){
	if(!$('#tabs').tabs('exists',subtitle)){
		$('#tabs').tabs('add',{
			title:subtitle,
			content:createFrame(url),
			closable:true,
			width:$('#mainPanle').width()-10,
			height:$('#mainPanle').height()-26
		});
	}else{
		$('#tabs').tabs('select',subtitle);
	}
	tabClose();
}

function createFrame(url)
{
	var s = '<iframe name="mainFrame" scrolling="no"   frameborder="0"  src="'+url+'" style="width:100%;height:100%;overflow:hidden;"></iframe>';
	return s;
}

function tabClose()
{
	/*双击关闭TAB选项卡*/
	$(".tabs-inner").dblclick(function(){
		var subtitle = $(this).children("span").text();
		$('#tabs').tabs('close',subtitle);
	})

	$(".tabs-inner").bind('contextmenu',function(e){
		$('#mm').menu('show', {
			left: e.pageX,
			top: e.pageY,
		});
		
		var subtitle =$(this).children("span").text();
		$('#mm').data("currtab",subtitle);
		
		return false;
	});
}
//绑定右键菜单事件
function tabCloseEven()
{
	//关闭当前
	$('#mm-tabclose').click(function(){
		var currtab_title = $('#mm').data("currtab");
		$('#tabs').tabs('close',currtab_title);
	})
	//全部关闭
	$('#mm-tabcloseall').click(function(){
		$('.tabs-inner span').each(function(i,n){
			var t = $(n).text();
			$('#tabs').tabs('close',t);
		});	
	});
	//关闭除当前之外的TAB
	$('#mm-tabcloseother').click(function(){
		var currtab_title = $('#mm').data("currtab");
		$('.tabs-inner span').each(function(i,n){
			var t = $(n).text();
			if(t!=currtab_title)
				$('#tabs').tabs('close',t);
		});	
	});
	//关闭当前右侧的TAB
	$('#mm-tabcloseright').click(function(){
		var nextall = $('.tabs-selected').nextAll();
		if(nextall.length==0){
			//msgShow('系统提示','后边没有啦~~','error');
			alert('后边没有啦~~');
			return false;
		}
		nextall.each(function(i,n){
			var t=$('a:eq(0) span',$(n)).text();
			$('#tabs').tabs('close',t);
		});
		return false;
	});
	//关闭当前左侧的TAB
	$('#mm-tabcloseleft').click(function(){
		var prevall = $('.tabs-selected').prevAll();
		if(prevall.length==0){
			alert('到头了，前边没有啦~~');
			return false;
		}
		prevall.each(function(i,n){
			var t=$('a:eq(0) span',$(n)).text();
			$('#tabs').tabs('close',t);
		});
		return false;
	});

	
	//退出
	$("#mm-exit").click(function(){
		alert("mm");
		$('#mm').menu('hide');
	})
}

//弹出信息窗口 title:标题 msgString:提示信息 msgType:信息类型 [error,info,question,warning]
function msgShow(title, msgString, msgType) {
	$.messager.alert(title, msgString, msgType);
}

function clockon() {
    var now = new Date();
    var year = now.getFullYear(); //getFullYear getYear
    var month = now.getMonth();
    var date = now.getDate();
    var day = now.getDay();
    var hour = now.getHours();
    var minu = now.getMinutes();
    var sec = now.getSeconds();
    var week;
    month = month + 1;
    if (month < 10) month = "0" + month;
    if (date < 10) date = "0" + date;
    if (hour < 10) hour = "0" + hour;
    if (minu < 10) minu = "0" + minu;
    if (sec < 10) sec = "0" + sec;
    var arr_week = new Array("星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六");
    week = arr_week[day];
    var time = "";
    time = year + "年" + month + "月" + date + "日" + " " + hour + ":" + minu + ":" + sec + " " + week;

    $("#bgclock").html(time);

    var timer = setTimeout("clockon()", 200);
}

