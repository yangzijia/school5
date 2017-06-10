<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="description" content=""/>
<meta name="keywords"  content=""/>
<title>河北北方学院预警系统</title>
<link  rel="stylesheet"  type="text/css"  href="css/styleB.css"/>
<link  rel="stylesheet"  type="text/css"  href="css/webteacherregister.css"/>
<script type="text/javascript" src="js/jquery-1.7.2.min.js"/></script>
<script type="text/javascript" src="js/jquery.json-2.4.js"/></script>
<script type="text/javascript" src="js/json2.js"/></script>
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
			
			$("#teacherCardId").blur(function(){
             var teacherCardId=$("#teacherCardId").val()
             isIDCard2=/(^\d{15}$)|(^\d{18}$)|(^\d{17})(\d|X|x)$/.test(teacherCardId);
             var year = teacherCardId. substr(6,2);
             var month = teacherCardId. substr(10,2);
             var day = teacherCardId. substr(12,2);
			 
             if(teacherCardId==""){
				 $('#teacherCardId1').text("身份证号码不能为空!");
 				$('#button').attr('disabled','true');
			 }else if(isIDCard2==false||month>12||day>31||year>20||year<19){
             $('#teacherCardId1').text("身份证格式不正确");
 				$('#button').attr('disabled','true');
             }else{
			  $('#teacherCardId1').text("");
			  $('#button').removeAttr("disabled");
			}

});			
			/*姓名验证*/
			
			$('#teacherName').blur(function(){
				var teacherNamelen=$('#teacherName').val();
				//var usern = /^[a-zA-Z0-9_]{1,}$/;
				if(teacherNamelen<2 || teacherNamelen>4){
					$('#teacherName1').text("姓名长度在3-6个字符之间");
					$('#button').attr('disabled','true');
				}else{
					$('#teacherName1').text("");
					$('#button').removeAttr("disabled");
				}
			});
			
			
			/*手机号验证*/
			
			$('#teacherPhone').blur(function(){
				var teacherPhone=$('#teacherPhone').val();
			    var mobile = /^1[3|4|5|8][0-9]\d{8}$/;   
               // var tel = /^\d{3,4}-?\d{7,8}$/;   
                      
				if(teacherPhone==""){
					$('#teacherPhone1').text("手机号不能为空");
					$('#button').attr('disabled','true');
				} else if(teacherPhone.match(mobile)){
					
					$('#teacherPhone1').text("");
					$('#button').removeAttr("disabled");				
				}else{
					$('#teacherPhone1').text("手机号格式不正确");
					$('#button').attr('disabled','true');
					}
			});
			
					
		});

</script>
<script type="text/javascript">
$(document).ready(function(){
	$("#button").click(function(){
	var nickName=$("#nickName").val();
	var password=$("#password").val();
	var teacherCardId=$("#teacherCardId").val();
    var teacherName=$("#teacherName").val();
    var teacherPhone=$("#teacherPhone").val();
		var t = { "teacherName":teacherName,
					"nickname":nickName,
					"teacherPhone":teacherPhone,
					"teacherCardId":teacherCardId,
					 "password":password,
				 	"role":"Role_Teacher"};
		var str = JSON.stringify(t);
		//alert(str);
$.ajax({  
        type:"Post",
        contentType:"application/json",
		url:"registerTeacher.html",
		data:str,
        dateType:"json",  
        success:function(data){  
            //alert("名字:" + data.userName + "密码:" + data.password+"角色"+data.role);
         var obj = jQuery.parseJSON(data); 
            alert(obj.Status);
            //alert(111);
            
                    if(obj.Status=="Success"){
                    //alert("222");
                   location.href="index.html";
                   
                  }
                   else
                   {
                   		 location.href="#";
                   }
          }
   	});
   });
});

</script>
<title></title>
</head>
<body>
      <div  id="head">        
      </div> 
   
   <div  id="wrapper">         
            
           
       
				  
                        
						  <center>
      <form id="form" >
         <div  id="login">    <span class="login_txt_bt"  >普通教师注册</span>   </div><br/>
          <p>
    	<span ><img src="images/nc.png" width="120" height="24"></span><em class="red">*</em>
        <input  type="text"  id="nickName"   name="nickName1"  size="25"/>
	    <br/>
        <label  id="nickName1" class="label_txt"></label>
    		</p>

        <p>
    	<!--<span for="password"   class="form_txt">密&nbsp;&nbsp;码</span><em class="red">*<em>-->
		<span ><img src="images/mm.png" width="120" height="24"></span><em class="red">*</em>
         <input type="password" id="password"  name="password1"   size="25"/>
		 <br/>
            <label  id="password1"   class="label_txt"></label>
    	</p>
       

	   

        <p>
    	<span ><img src="images/qrmm.png" width="120" height="24"></span><em class="red">*</em>
        <input type="password" id="againpassword"  name="confirm_password1"   size="25"    />
		<br/>
         <label  id="againpassword1" class="label_txt"></label>
    	</p>

		<p>
    	<span ><img src="images/cid.png" width="120" height="24"></span><em class="red">*</em>
        <input type="text"  id="teacherCardId"   name="shenfenzheng"  size="25"/>
		<br/>
		 <label  id="teacherCardId1" class="label_txt"></label>
    	</p>


        <p>
    	<span ><img src="images/jsxm.png" width="120" height="24"></span><em class="red">*</em>
        <input  type="text" id="teacherName"  name="teacherName1"   size="25"/>
		<br/>
		<label   id="teacherName1" class="label_txt"></label>
    	</p>

         <p>
    	<span ><img src="images/sjhm.png" width="120" height="24"></span><em class="red">*</em>
         <input type="text" id="teacherPhone"  name="mobile" size="25"/>
		 <br/>
		 <label id="teacherPhone1" class="label_txt"></label>
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
