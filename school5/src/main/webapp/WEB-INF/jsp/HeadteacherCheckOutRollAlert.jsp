<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>河北北方学院预警系统</title>
<link rel="stylesheet" type="text/css" href="css/default.css" />
<link rel="stylesheet" type="text/css" href="js/themes/default/easyui.css" />
<link rel="stylesheet" type="text/css" href="js/themes/default/icon.css" />
<link  rel="stylesheet"  type="text/css"  href="css/studentRegisterE.css" />

<link rel="stylesheet" type="text/css" href="jqueryeasyui/themes/default/easyui.css"/>
<link rel="stylesheet" type="text/css" href="jqueryeasyui/themes/icon.css"/>
<link rel="stylesheet" type="text/css" href="jqueryeasyui/demo/demo.css"/>
<script type="text/javascript" src="js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="js/jquery.json-2.4.js"></script>
<script type="text/javascript" src="js/json2.js"></script>
<script type="text/javascript" src="js/jQuery.easyui.js"></script>
<script type="text/javascript" src="jqueryeasyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="jqueryeasyui/easyui-lang-zh_CN.js"></script>

<script language="javascript" type="text/javascript" src="calendar/WdatePicker.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	$.ajax({
		type:'Post',
		url:'getstudentDetailStudentNumber.html',
		ansyc:'false',
		dataType:'json',
		success:function(data){
		    //alert(data);
			$('#name').val(data.name);
			$('#score').val(data.score);
			$('#course').val(data.course);
			$('#coursetype').val(data.coursetype);
			$('#opinion').val(data.opinion);
		}
	});
});

</script>


  </head>
  
  <body>
    <form id="form" action="#" class="login_txt" method="post">
     <div  id="mainPanle" scrolling="no" align="center" style="background: #ffffff; overflow:hidden;width: 1100px; height: 500px;margin: 15px  auto   0px   auto;overflow: hidden;padding:  0px 0px 0px  0px;border:2px #ffffff solid;">
      <div id="form2"   style="padding:3px   10px  9px  60px;margin:6px  auto  9px  auto;width:500px;border:solid 2px #91b5e7;height:350px;background: #fff;">
       <div  id="form1"  align="center" style="margin:15px 0px 0px 0px;width:450px;">
            <h2  class="login_txt_bt" align="center">不及格成绩</h2>
            <p>
            <span><strong>学生姓名：</strong>
		    <input type="text" id="name" name="name" size="25" readonly="readonly" />
		    </span>
            </p>
            <p>
            <span><strong>学科名称：</strong>
			<input type="text" id="course" name="course" size="25" readonly="readonly" />
			</span>
			</p>
            <p>
            <span><strong>学科成绩：</strong>
		     <input type="text" id="score" name="score" size="25" readonly="readonly" />
		    </span>
            </p>
            <p>
            <span><strong>学科类型：</strong>
			<input type="text" id="coursetype" name="coursetype" size="25" readonly="readonly" />
			</span>
            </p>
            <p>
            <span><strong>处理状态：</strong>
			<input type="text" id="opinion" name="opinion" size="25" readonly="readonly" />
            </p>
            <p>
            </br>
             <input type="button" name="button" id="goback" style="font-size:14px;" onclick="javascript:history.go(-1);" value="返回" />
            </p>
       </div>
      </div>
     </div>
    </form>
  </body>
</html>
