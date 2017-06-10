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


<script type="text/javascript">
$(document).ready(function(){
	$.ajax({
		type:'Post',
		url:'getStudentWebShow.html',
		ansyc:'false',
		dataType:'json',
		success:function(data){
		    $('#nickName').val(data.nickName);
			$('#studentName').val(data.name);
			$('#studentCardId').val(data.cardId);
			$('#studentPhone').val(data.phone);
			$('#college').val(data.college);
			$('#yearClass').val(data.grade);
			$('#profession').val(data.profession);
			$('#studentClass').val(data.studentClass);
			$('#studentNumber').val(data.studentNumber);
			//$('#oldTel').val(data.phone);
			
			if(data.imgUrl!=null || data.imgUrl!=""){
			$('#img1').html('<img  id="img" src="'+data.imgUrl+'"  alt="头像"  style="width:200px;height:280px;"  >');
			}
			
		}
	});
});

</script>

</head>
<body>




	<!--<div  id="mainPanle1"   style="background: #D2E0F2; ">-->
 <div id="mainPanle" scrolling="no" region="center" 
                 style="background: #ffffff; overflow:hidden;width: 1100px; height: 430px;margin: 15px  auto   0px   auto;overflow: hidden;padding:  0px 0px 0px  0px;border:2px #ffffff solid;"  >
        <div  id="main"  style="background: #ffffff; overflow:hidden;width: 98%; height:400px;margin:8px auto auto auto;border:2px #ffffff solid;padding:2px;">

<div id="form"   style="padding:3px   10px  9px  60px;margin:6px  auto  9px  auto;width:650px;border:solid 2px #91b5e7;height:379px;">
    <div  id="form1"  align="center"  style="margin:5px 0px 0px 0px ">
              <h3  class="login_txt_bt">学生详细信息</h3>
           

     <p>
    	<span ><img src="images/nc.png" width="120" height="24" /></span>
        <input  type="text"  id="nickName"   name="textfield1"  size="25" readonly="readonly" class="easyui-validatebox" />
     </p>  
     <p>
    	<span ><img src="images/xsxm.png" width="120" height="24" /></span>
        <input  type="text" id="studentName"    size="25"  readonly="readonly" class="easyui-validatebox"  name="textfield3"  />
     </p>
     <p>
    	<span ><img src="images/xsxh.png" width="120" height="24" /></span>
        <input  type="text" id="studentNumber"    size="25"  readonly="readonly" class="easyui-validatebox"  name="textfield3" />
     </p>
	
     <p>
    	<span ><img src="images/sjhm.png" width="120" height="24" /></span>
        <input  type="text"  id="studentPhone"   name="textfield4"  size="25"  readonly="readonly" class="easyui-validatebox" />
     </p>

     <p>
    	<span ><img src="images/cid.png" width="120" height="24" /></span>
        <input  type="text"   id="studentCardId"   name="textfield5"  size="25"  readonly="readonly" class="easyui-validatebox" />
     </p>

     <p>
       <img src="images/xy.png"  width="49" height="15" /><input type="text" id="college" name="textfield6"  readonly="readonly" />
       <img src="images/nj.png"  width="49" height="15" /><input type="text" id="yearClass" name="textfield7"  readonly="readonly" /><br/><br/>
       <img src="images/zy.png" width="49" height="15" /><input type="text" id="profession" name="textfield8"  readonly="readonly" />
	   <img src="images/bj.png" width="49" height="15" /><input type="text" id="studentClass" name="textfield8"  readonly="readonly" />
	 </p>
            <p>
            </br>
            </br>
             <input type="button" name="button" id="goback" style="font-size:14px;" onclick="window.history.back(-1);" value="返回" />
            </p>
     <br/>
	</div><!--form1-->
          <div id="img"  style="width:210px;height:310px;margin:20px auto;padding-left:3px;padding-bottom:2px;border:solid 2px #97b9e6;">
          <div id="img1"  style="width:200px;border:solid 2px #97b9e6;height:280px;margin:15px auto;background:url(images/touxiang.png ) no-repeat"></div>
          </div>

<div  id="clear"></div>
 </div><!--form-->


</div><!--main-->
    </div><!--mainPanle-->
</div><!--end   mainPanle1-->




</body>
</html>

