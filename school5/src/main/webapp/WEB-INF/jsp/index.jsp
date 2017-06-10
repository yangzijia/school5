<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"/>
<%@ page language="java" contentType="text/html;charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>河北北方学院预警系统</title>
<link  rel="stylesheet"  type="text/css"  href="css/styleA.css"  id="sc1"/>
<link  rel="stylesheet"  type="text/css"  href="css/index1.css"   id="sc2"/>

<script type="text/javascript" src="js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="js/jquery.json-2.4.js"></script>

<script type="text/javascript" >

		function reloadVerifyCode()
		{
			$("#verifyImage").attr("src","getVerifyCode.html?random="+Math.random());
		}
		
		jQuery(document).ready(function(){
			//每次加载页面后重新加载验证码	
			//reloadVerifyCode();
		});					
	</script>

<script type="text/javascript"> 
$(document).ready(function(){ 
   var brow=$.browser; 
   var bInfo=""; 
   if(brow.msie) {bInfo="Microsoft Internet Explorer "+brow.version;} 
   if(brow.mozilla) {bInfo="Mozilla Firefox "+brow.version;} 
   if(brow.safari) {bInfo="Apple Safari "+brow.version;} 
   if(brow.opera) {bInfo="Opera "+brow.version;} /*判断浏览器的类型*/
      //alert(bInfo); 
	  if(bInfo== "Microsoft Internet Explorer 6.0" ){
	  
	  //alert(66666);
   sc1.setAttribute("href","css/styleC.css"); /*调用不同的css样式表*/
   sc2.setAttribute("href","css/index3.css"); 
      //alert(7777);
	  };   
  
   
}) ;
</script>

<script type="text/javascript">
 $(function(){  
        $.fn.serializeObject = function(){  
            var o = {};  
            var a = this.serializeArray();  
            $.each(a, function(){  
                if (o[this.name]) {  
                    if (!o[this.name].push) {  
                        o[this.name] = [o[this.name]];  
                    }  
                    o[this.name].push(this.value || '');  
                }  
                else {  
                    o[this.name] = this.value || '';  
                }  
            });  
            return o;  
        };  
         
        $("form #button").click(function(){ 
        
        var jsonuserinfo = $.toJSON($('#form').serializeObject());
            jQuery.ajax({  
                type:"POST",  
                contentType:"application/json",  
                url:"loginCheck.html",  
                data:jsonuserinfo,  
                dataType:"json",  
                success:function(data){  
                   
                   if(data.Status=="NotHaveUser"||data.Status=="PasswordError"){
              
                   	alert("用户名或密码填写不正确!");
                   $("#verifyImage").attr("src","getVerifyCode.html?random="+Math.random());
                   }/*else if(data.Status=="RoleError"){
                   		alert("角色不正确");
                   		$("#verifyImage").attr("src","getVerifyCode.html?random="+Math.random());
                   }*/
                   else if(data.Status=="verifyCodeError"){
                   		alert("验证码不正确");
                   $("#verifyImage").attr("src","getVerifyCode.html?random="+Math.random());
                   }/* else if(data.Status=="Success"){  //去掉是因为需要给客户端返回角色信息
                   	location.href="admin.html";
                   } */
                   else{  
                	   location.href="admin.html";
                   }
                                    
            },  
                error: function(){  
                    alert("error");  
                } 
            });  
        });  
    });  
</script>

	</head>
	<body>
      <div  id="head" >  
        <div id="head-img"> <img src="images/words.png" width="728" height="84" /></div>
      </div> 
   
   <div  id="wrapper">     
     
      <div  class="main">     
        
            <div  id="main-img">         <img src="images/left.png" width="120" height="109" /></div>        
     </div>        
      <div  class="side" >                       
                  
                <form id="form"  action="#"  class="login_txt" method="post">   
                        <!--  <img src="img/key.png" width="16" height="31"> --><span class="login_txt_bt"  >登录</span>   
		      <br/><br/>
			<img src="images/user.gif" />用户名：
			<input type="text" id="userName" name="userName"  />
			<br/>
			<img src="images/luck.gif" /> 密&nbsp;&nbsp;&nbsp; 码：
			<!--<input type="password" id="passWord" name="passWord" value="123456" />-->
			<input type="password" id="passWord" name="passWord" />
			<br/>
			 
			<!-- <div style="display: none;">
				<input type="text" id="clientid" name="clientid" value="34d5c6cfbd89a651e72c605c0ad86767"/>
			</div> -->  
			  <!--<img src="images/user2.gif"/>角&nbsp;&nbsp;&nbsp; 色：
			  <input type="radio" class="radio" name="role" value="Role_Student" checked="checked" /> 学生  
              <input type="radio"  class="radio" name="role" value="Role_Teacher" /> 普通教师  
              <input type="radio"   class="radio"name="role" value="Role_HeadTeacher" />辅导员
			  <input type="radio"  class="radio" name="role" value="Role_Leader" /> 领导
			  <input type="radio"  class="radio" name="role" value="Role_Administrator" /> 管理员
			  <br />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			  <input type="radio"  class="radio" name="role" value="Role_StudentAdmin" /> 学生科
			  <input type="radio"  class="radio" name="role" value="Role_CollegeAdmin" /> 学院教学科
			  <input type="radio"  class="radio" name="role" value="Role_LogisticsAdmin" /> 后勤集团
			  <input type="radio"  class="radio" name="role" value="Role_DormAdmin" /> 宿管科
			  <br />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			  <input type="radio"  class="radio" name="role" value="Role_ClassAdmin" /> 班委  --> 
			  
		    <img src="images/verifyCode.png" /> 验证码：
			<input type="text" id="verifyCode" name="verifyCode" valType="required"/>
			<img id="verifyImage"  src="getVerifyCode.html"  align="middle" style="cursor:pointer;"  onclick="reloadVerifyCode();" title="点击更换验证码" alt="看不清，换一张" />
			<br/>
		            
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" id ="button"value="登录" style="width:50px;height:28px;" class="button" onclick="validate();" />
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="reset" id="button1" value="重置" class="button" style="width:50px;height:28px;"   />
			</form>
			  <!-- <div  id="wrapper2" class="login-buttom-txt"> 本系统未注册用户，请点击<a href="webstudentRegister.html">学生</a>、<a href="webteacherRegister.html">普通教师</a>、<a href="webheadteacherRegister.html">辅导员</a>注册</div> -->  
				<div  id="wrapper2" class="login-buttom-txt">若登录遇到问题，请及时拨打电话15369303137与系统管理员联系。</div>
				<div id="wrapper3"  ><img src="images/login-wel2.png" width="242" height="138" />
				<br />
				<span  class="login_txt1">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				若是ie浏览器，请选择ie8以上版本</span>
				</div>
             </div>
            
            
       <div  id="clear" ></div>
   </div>
  

      <div  id="footer">
          <div  id="footer-title"    class="login-buttom-txt">版权所有&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;    河北北方学院信息技术研究所         </div>
      </div>
</body>
</html>

