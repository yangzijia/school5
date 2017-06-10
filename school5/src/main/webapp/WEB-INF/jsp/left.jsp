<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<style type="text/css">

</style>
<link href="css/css.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="css/master.css" />
<script type="text/javascript" src="js/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="js/json2.js"></script>
<script type="text/javascript" src="js/jquery.json-2.4.js"></script>
<script type="text/javascript" src="js/left_nav.js"></script>
<script type="text/javascript">
$(document).ready(function()
{
	var str="";
	$.ajax
	({			
		type:'POST',
		url:'menuGet.html',
		dataType:'json',
		//这个东东很重要奥，就是它取消异步
		async : false,
		success:function(data)
		{	
			var obj=data;
			for(var i=0;i<obj.length;i++){
			   
			   	str+="<div class='root_menu_module'>" +
			   	" <div class='root_menu_top'></div>" +
			   	" <div class='root_menu_middle'>" +
			   	" <div class='root_menu_title'><a href='#'>"+obj[i].parentMenuName+"</a></div>" +
			   	" <ul class='sub_menu'>";
			   	for(var j =0;j<obj[i].childrenMenu.length;j++){
			   		
			   		str+="<li  class='sub_menu_li' >"+obj[i].childrenMenu[j].childrenmenuName+"</li>";
			   	}
			   	str+="</ul>" +
			   	"</div>" +
			   	"<div class='root_menu_bottom'></div>" +
			   	"</div>";
			}
			
			$(".left_nav").append(str);
			
		}
	});		
});

</script>
<script type="text/javascript">

</script>
<!-- 必须放在上面script代码的底下 -->
<script type="text/javascript" src="js/left_nav.js"></script>
</head>
 <body>
<div class="header">     <!--header start-->
  <div class="header_UserInfo">
    <div style="margin-left:950px; margin-top:110px;">
     <span style="font-size:14px;">Welcome</span>&nbsp;&nbsp;
     <span style="font-size:14px; color:#cc3366; font-weight:bold">Admin</span>&nbsp;&nbsp;
     <span style="font-size:14px;"><a href="#">退出</a></span>  
    </div>
  </div>
  <div class="header_shadow" style="width:100%; height:10px; background-image:url(images/adminImages/header_shadowbg.png); background-repeat:repeat-x; background-position:0 0; position:absolute; z-index:99999">
  </div>
</div>                 <!--header end-->

<div class="wrapper" style="width:1260px; height:auto">
<div class="left_nav">     
 <div class="menu_fold">
    <span style="display:block; padding-top:5px; line-height:15px">导航</span>
    <span class="menu_fold_icon"></span>
 </div>
 <br/>
</div>         
<div class="page_content" style="float:left; width:1040px; min-height:800px; background-color:#eee;">
</div>
<div class="clear"></div>
</div>
</body>
</html>
