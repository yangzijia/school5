<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<html>
<head id="Head1">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="js/themes/default/easyui.css" />
<link rel="stylesheet" type="text/css" href="js/themes/default/icon.css" />
<link rel="stylesheet" type="text/css" href="css/default.css" />
<script type="text/javascript" src="js/jquery-1.4.2.min.js"></script>
<!-- 一定要注意文件名的字母大小 -->
<script type="text/javascript" src="js/jQuery.easyui.js"></script>
<script type="text/javascript" src='js/outlook2.js'> </script> 
<style type="text/css">
a{ outline:none; }
::-moz-focus-inner{border:0px;}/*去除点击链接之后留下的虚线框,ie6.7不起组用 */
</style>

<script type="text/javascript">
$("a").focus(function(){this.blur()});/*适应ie6.7   效果还没有验证*/
</script>

　

<title>河北北方学院预警系统管理员界面</title>
</head>
<body class="easyui-layout" scrolling="no" style="overflow:hidden">
<noscript>
<div style=" position:absolute; z-index:100000; height:2046px;top:0px;left:0px; width:100%; background:white; text-align:center;">
    <img src="images/noscript.gif" alt='抱歉，请开启脚本支持！' />
</div></noscript>

    <div region="north" split="true" border="false" style="overflow: hidden; height: 30px;
        background: url(images/layout-browser-hd-bg.gif) #7f99be repeat-x center 50%;
        line-height: 20px;color: #fff; font-family: Verdana, 微软雅黑,黑体">
        <span style="float:right; padding-right:20px;" class="head"> <a href="j_spring_security_logout" id="loginOut"><font color="#FFFFFF">安全退出</font></a></span>
        <span style="padding-left:10px; font-size: 14px; text-align:center;"><img src="images/blocks.gif" width="20px" height="20" align="absmiddle" /> 河北北方学院预警系统</span>
   
    </div> 
    
    
    
    <div region="south" split="true" style="height: 30px; background: #D2E0F2; ">
        <div class="footer">版权所有   河北北方学院</div>
    </div>
    
    
    <div region="west" split="true" title="导航菜单" style="width:180px;" id="west" border-left="thin">
	<div class="easyui-accordion" fit="true" border="false">
		<!--  导航内容 -->
	</div>

    </div>
    
    
    <div id="mainPanle" scrolling="no" region="center" style="background: #eee; overflow:hidden">
        <div id="tabs" class="easyui-tabs" scrolling="no" fit="true" border="true" style="overflow:hidden;" >
			<div title="首页" style="padding:0px;margin-left:5px;soverflow:hidden;"id="home">
				
				<h1 class="login_txt_bt">欢迎进入河北北方学院预警系统</h1>
				<img src="images/admin-center.jpg" width="1150" height="490" id="img" />

			</div>	
		</div>
    </div>

	<div id="mm" class="easyui-menu" style="width:150px;">
		<div id="mm-tabclose">关闭</div>
		<div id="mm-tabcloseall">全部关闭</div>
		<div id="mm-tabcloseother">除此之外全部关闭</div>
		<div class="menu-sep"></div>
		<div id="mm-tabcloseright">当前页右侧全部关闭</div>
		<div id="mm-tabcloseleft">当前页左侧全部关闭</div>
		<div class="menu-sep"></div>
		<div id="mm-exit">退出</div>
	</div>
</body>
</html>
