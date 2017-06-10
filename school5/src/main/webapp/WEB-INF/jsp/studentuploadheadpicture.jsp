<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%
HttpSession hp  = request.getSession();
String nickName =(String)hp.getAttribute("userName");
System.out.println(nickName);
 %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>河北北方学院预警系统</title>
<link rel="stylesheet" type="text/css" href="css/default.css" />
<link rel="stylesheet" type="text/css" href="js/themes/default/easyui.css" />
<link rel="stylesheet" type="text/css" href="js/themes/default/icon.css" />
<link rel="stylesheet" type="text/css" href="jqueryeasyui/themes/default/easyui.css"/>
<link rel="stylesheet" type="text/css" href="jqueryeasyui/themes/icon.css"/>
<link rel="stylesheet" type="text/css" href="jqueryeasyui/demo/demo.css"/>
<script type="text/javascript" src="js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="js/jquery.json-2.4.js"></script>
<script type="text/javascript" src="js/json2.js"></script>
<script type="text/javascript" src="js/jQuery.easyui.js"></script>
<script type="text/javascript" src="jqueryeasyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="jqueryeasyui/easyui-lang-zh_CN.js"></script>

</head>
<body>
	
 <div id="mainPanle" scrolling="no" region="center" 
                 style="background: #ffffff; overflow:hidden;width: 1100px; height: 430px;margin: 15px  auto   0px   auto;overflow: hidden;padding:  0px 0px 0px  0px;border:2px #ffffff solid;"  >    

<div id="form"   style="padding:3px   auto  9px  auto;margin:67px  auto  9px  auto;width:400px;border:solid 2px #91b5e7;height:300px;">
 <c:if test="${!empty error}">
	   <c:out value="${error}" />
</c:if>   
<form  action="<c:url value="webUploadHeadSculpture.html"/>" style="padding:3px   auto  9px  auto;margin:67px  auto  9px  auto;width:240px;border:solid 0px #91b5e7;height:165px;"  method="post" enctype="multipart/form-data">
    	<br/><br/>
		<input name="file" type="file" style="width:200px;position:absolute;opacity:0;filter:alpha(opacity=0);"  onchange="this.form.inputs.value=this.value.substr(this.value.lastIndexOf('\\')+1);"/>
		<input name="inputs" type="input" style="width:121px; height:14px;"  />
		<input  type="button" value="选择图片" style="width:70px; height:20px; border:1px solid #003c74;background:#97b9e6;"/><br/><br/>
          备注：<br/>
            1.支持的文件格式是jpg/png/jpeg/gif<br/>
            2.文件大小应小于1M<br/>
            3.请不要上传与版权/名誉相关的图片
       
       <!--<input   type="file"     size="25"   class="easyui-validatebox"   />-->
       
       <br/><br/>
	&nbsp; &nbsp;&nbsp; &nbsp;<input type="submit" value="上传">&nbsp; &nbsp;&nbsp; &nbsp;<input type="reset" value="取消">
	</form>  
          
<div  >




<div  id="clear"></div>
		</div><!--form-->



    </div><!--mainPanle-->
<!--</div>end   mainPanle1-->




</body>
</html>

