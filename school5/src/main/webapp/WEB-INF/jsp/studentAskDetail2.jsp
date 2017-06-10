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
		url:'getstudentAskDetailByaskId.html',
		ansyc:'false',
		dataType:'json',
		success:function(data){
			$('#studentName').val(data.studentName);
			$('#teacherName').val(data.teacherName);
			$('#snumber').val(data.snumber);
			$('#phone').val(data.phone);
			$('#StartTime').val(data.StartTime);
			$('#EndTime').val(data.EndTime);
			$('#type').val(data.type);
			$('#status').val(data.status);
			$('#reason').val(data.reason);
			$('#ParentsName').val(data.ParentsName);
			$('#ParentsPhone').val(data.ParentsPhone);
		}
	});
});

</script>


</head>
<body>
<form id="form" action="#" class="login_txt" method="post">
<div id="mainPanle" scrolling="no" align="center" 
                 style="background: #ffffff; overflow:hidden;width: 1100px; height: 430px;margin: 15px  auto   0px   auto;overflow: hidden;padding:  0px 0px 0px  0px;border:2px #ffffff solid;"  >
<div id="form2"   style="padding:3px   10px  9px  60px;margin:6px  auto  9px  auto;width:650px;border:solid 2px #91b5e7;height:392px;background: #fff;">
    <div  id="form1"  align="center" style="margin:15px 0px 0px 0px;width:608px;">
              <h2  class="login_txt_bt" align="center">请假信息</h2>
           
	<p><!-- style="display:block"   block 块级元素，默认情况下（不浮动不绝对定位），该元素后的内容会自动换行。相当于它的后面加了一个<br>。 -->
    	<span><strong>学生姓名：</strong>
		<input type="text" id="studentName" name="studentName" size="25" readonly="readonly" />
		</span>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<span><strong>教师姓名：</strong>
		<input type="text" id="teacherName" name="teacherName" size="25" readonly="readonly" />
		</span>
	</p>  
		
	<p>
    	<span><strong>学生学号：</strong>
		<input type="text" id="snumber" name="snumber" size="25" readonly="readonly" />
		</span>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<span><strong>联系电话：</strong>
		<input type="text" id="phone" name="phone" size="25" readonly="readonly" />
		</span>
	</p>  
		
 	<p>
    	<span><strong>开始时间：</strong>
		<input type="text" id="StartTime" name="StartTime" style="width:172px" readonly="readonly" />
		</span>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<span><strong>结束时间：</strong>
		<input type="text" id="EndTime" name="EndTime" style="width:172px" readonly="readonly" />
		</span>
	</p>  
	<p>
    	<span><strong>家长姓名：</strong>
		<input type="text" id="ParentsName" name="ParentsName" style="width:172px" readonly="readonly" />
		</span>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<span><strong>家长电话：</strong>
		<input type="text" id="ParentsPhone" name="ParentsPhone" style="width:172px" readonly="readonly" />
		</span>
	</p>  		
	<p>
		<span><strong>假条状态：</strong>
         <input type="text" id="status" name="status" style="width:172px" readonly="readonly" />
        </span>
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    	<span><strong>请假类型：</strong>
         <input type="text" id="type" name="type" style="width:172px" readonly="readonly" />
        </span>
	</p>
	
	<p>
    	<span><strong>请假原因：</strong>
    	</span>
    	
    	<span style="display:block">
		<textarea name="reason" id="reason" cols="84" rows="4" class="easyui-validatebox" readonly="readonly" ></textarea>
		</span>
		
		<br/>
		 <input type="button" name="button" id="goback" style="font-size:14px;" onclick="javascript:history.go(-1);" value="返回" />
	</p>
	

      </div><!--form1-->
    </div><!--form2-->
	</div>
	</form>

</body>
</html>

