<!DOCTYPE html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>河北北方学院预警系统</title>

 <link  rel="stylesheet"  type="text/css"  href="styleB.css"/>
<link  rel="stylesheet"  type="text/css"  href="studentChange.css"/>
<script type="text/javascript" src="js/jquery-1.7.2.min.js"></script>


<script type="text/javascript" src="js/jquery.json-2.4.js"></script>
<script type="text/javascript" src="js/json2.js"></script>
<script type="text/javascript">
	$(document).ready(function() {	
				/*昵称验证*/
				$('#nickName').blur(function(){
				var nickNamelen=$('#nickName').val().length;
				//var usern = /^[a-zA-Z0-9_]{1,}$/;
				if(nickNamelen<6 || nickNamelen>12){
					$('#nickName1').text("昵称长度在6-12个字符之间");
					/*$('#nickName').next(['label']).text("昵称长度在3-6个字符之间");*/
					$('#button').attr('disabled','true');
				}else{
					$('#nickName1').text("");
					$('#button').removeAttr("disabled");
				}
			});

			/*旧密码验证*/	
			$('#password').blur(function(){
				var oldpasslen=$('#oldpassword').val().length;
				if(oldpasslen<6 || oldpasslen>12){
					$('#oldpassword1').text("旧密码长度在6-12个字符之间");
					$('#button').attr('disabled','true');
				}else{
					$('#oldpassword1').text("");
					$('#button').removeAttr("disabled");
				}
			});	
			/*密码验证*/	
			$('#password').blur(function(){
				var passlen=$('#password').val().length;
				if(passlen<6 || passlen>12){
					$('#password1').text("密码长度在6-12个字符之间");
					$('#button').attr('disabled','true');
				}else{
					$('#password1').text("");
					$('#button').removeAttr("disabled");
				}
			});	
			/*确认密码验证*/
				$('#againpassword').blur(function(){
				var againpasslen=$('#againpassword').val().length;
				var passval=$("#password").val();
				var apassval=$("#againpassword").val();
				
				if(againpasslen<6||againpasslen>12){
					$('#againpassword1').text("密码长度在6-12个字符之间");
				$('#button').attr('disabled','true');	
				}else if(passval!=apassval){
					$('#againpassword1').text("两次输入的密码不一致");
					$('#button').attr('disabled','true');
				}else{
					$('#againpassword1').text("");
					$('#button').removeAttr("disabled");
				}
			});	

			/*身份证验证*/
			
			$("#studentCardId").blur(function(){
             var teacherCardId=$("#studentCardId").val()
             isIDCard2=/(^\d{15}$)|(^\d{18}$)|(^\d{17})(\d|X|x)$/.test(teacherCardId);
             var year = teacherCardId. substr(6,2);
             var month = teacherCardId. substr(10,2);
             var day = teacherCardId. substr(12,2);
			 
             if(teacherCardId==""){
				 $('#studentCardId1').text("身份证号码不能为空!");
 				$('#button').attr('disabled','true');
			 }else if(isIDCard2==false||month>12||day>31||year>20||year<19){
             $('#studentCardId1').text("身份证格式不正确");
 				$('#button').attr('disabled','true');
             }else{
			  $('#studentCardId1').text("");
			  $('#button').removeAttr("disabled");
			}

});			
			/*姓名验证*/
			
			$('#studentName').blur(function(){
				var teacherNamelen=$('#studentName').val();
				//var usern = /^[a-zA-Z0-9_]{1,}$/;
				if(teacherNamelen<2 || teacherNamelen>4){
					$('#studentName1').text("姓名长度在3-6个字符之间");
					$('#button').attr('disabled','true');
				}else{
					$('#studentName1').text("");
					$('#button').removeAttr("disabled");
				}
			});
			
			
			/*手机号验证*/
			
			$('#studentPhone').blur(function(){
				var studentPhone=$('#studentPhone').val();
			    var mobile = /^1[3|4|5|8][0-9]\d{8}$/;   
               // var tel = /^\d{3,4}-?\d{7,8}$/;   
                      
				if(studentPhone==""){
					$('#studentPhone1').text("手机号不能为空");
					$('#button').attr('disabled','true');
				} else if(studentPhone.match(mobile)){
					
					$('#studentPhone1').text("");
					$('#button').removeAttr("disabled");				
				}else{
					$('#studentPhone1').text("手机号格式不正确");
					}
			});
			
					
		});

</script>
<script type="text/javascript">
$(document).ready(function(){
	$("#button").click(function(){
	var userNickname=$("#nickName").val();
	var oldPassword=$("#oldpassword").val();
	var newPassword=$("#newpassword").val();
	var userPhone=$("#studentPhone").val();
		var t = { "userNickname":userNickname,
					"oldPassword":oldPassword,
					"newPassword":newPassword,
					"userPhone":userPhone,
				 	"role":"Role_Student"};
		var str = JSON.stringify(t);
		alert(str);
$.ajax({  
        type:"Post",
        contentType:"application/json",
		url:"userChange.html",
		data:str,
        dateType:"json",  
        success:function(data){  
            //alert("名字:" + data.userName + "密码:" + data.password+"角色"+data.role);
         var obj = jQuery.parseJSON(data); 
            alert(obj.Status);
          }
   	});
   });
});

</script>



</head>
 <body>
  <div  id="head" >   
      </div> 
   
   <div  id="wrapper">  
    <center>
   <form id="form" >
         <div  id="login">    <span class="login_txt_bt"  >学生修改</span>   </div><br/>
          <p>
    	<span ><img src="img/nc.png" width="120" height="24"></span><em class="red">*<em>
        <input  type="text"  id="nickName"   name="nickName1"  size="25"/>
	    <br/>
        <label  id="nickName1" class="label_txt"></label>
    		</p>
          
          <p>
    	<!--<span for="password"   class="form_txt">密&nbsp;&nbsp;码</span><em class="red">*<em>-->
		<span ><img src="img/jmm.png" width="120" height="24"></span><em class="red">*<em>
         <input type="oldpassword" id="oldpassword"  name="oldpassword1"   size="25"/>
		 <br/>
            <label  id="oldpassword1"   class="label_txt"></label>
    	</p>

        <p>
    	<!--<span for="password"   class="form_txt">密&nbsp;&nbsp;码</span><em class="red">*<em>-->
		<span ><img src="img/mm.png" width="120" height="24"></span><em class="red">*<em>
         <input type="password" id="password"  name="password1"   size="25"/>
		 <br/>
            <label  id="password1"   class="label_txt"></label>
    	</p>
       

	   

        <p>
    	<span ><img src="img/qrmm.png" width="120" height="24"></span><em class="red">*<em>
        <input type="password" id="againpassword"  name="confirm_password1" size="25" />
		<br/>
         <label  id="againpassword1" class="label_txt"></label>
    	</p>

		      

         <p>
    	<span ><img src="img/xsjhm.png" width="120" height="24"></span><em class="red">*<em>
         <input type="text" id="studentPhone"  name="mobile" size="25"/>
		 <br/>
		 <label id="studentPhone1" class="label_txt"></label>
         </p>
           
		 
	    
	<input type="button" id="button" value="提交" />	
   <input type="reset" id="res" value="重置"/>
  </form>
    </center>
	  </div>
  

      <div  id="footer">
        <div  id="footer-title"    class="login-buttom-txt">版权所有&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;    河北北方学院信息技术研究所         </div>
      </div>
 </body>
</html>

