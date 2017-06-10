<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<title>河北北方学院预警系统</title>
<script type="text/javascript" src="js/jquery-1.7.2.min.js"></script>
<link  rel="stylesheet"  type="text/css"  href="css/styleB.css" />
<link  rel="stylesheet"  type="text/css"  href="css/studentRegister.css" />

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
			/*头像*/
				
			//	$('#touxiang').blur(function(){
				//var nickNamelen=$('#touxiang').val().length;
				//var usern = /^[a-zA-Z0-9_]{1,}$/;
			//	if(nickNamelen==""){
				//	$('#touxiang1').text("请选择头像");
					/*$('#nickName').next(['label']).text("昵称长度在3-6个字符之间");*/
				//	$('#button').attr('disabled','true');
				//}else{
				//	$('#touxiang1').text("");
				//	$('#button').removeAttr("disabled");
				//}
			//});


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
				var teacherPhone=$('#studentPhone').val();
			    var mobile = /^1[3|4|5|8][0-9]\d{8}$/;   
               // var tel = /^\d{3,4}-?\d{7,8}$/;   
                      
				if(teacherPhone==""){
					$('#studentPhone1').text("手机号不能为空");
					$('#button').attr('disabled','true');
				} else if(teacherPhone.match(mobile)){
					
					$('#studentPhone1').text("");
					$('#button').removeAttr("disabled");				
				}else{
					$('#studentPhone1').text("手机号格式不正确");
					}
			});
			/*学生学号验证*/
            $('#studentNum').blur(function(){
				var studentNumlen=$('#studentNum').val().length;
				//var usern = /^[a-zA-Z0-9_]{1,}$/;
				if(studentNumlen==""){
					$('#studentNum1').text("学号是必填项");
					/*$('#nickName').next(['label']).text("昵称长度在3-6个字符之间");*/
					$('#button').attr('disabled','true');
				}else{
					$('#studentNum1').text("");
					$('#button').removeAttr("disabled");
				}
			});
					
		});

</script>
<script type="text/javascript">

$(document).ready(function(){
 	$.ajax({
                type:"Post",
                contentType:"application/json",
				url:"requestCollGra.html",
				dataType:"json",
				success:function(data){
				var obj = eval(data);
				if(obj.college.length>0){
					$("<option value='0'>--请选择--</option>")
				.appendTo("#college");
					for(var i = 0;i<(obj.college.length);i++){
					$("<option ></option>")
					.val(obj.college[i])
					.text(obj.college[i])
					.appendTo("#college");
					}
				}
				if(obj.grade.length>0){
					$("<option value='0'>--请选择--</option>")
				.appendTo("#yearClass");
				for(var i=0;i<obj.grade.length;i++){
					$("<option></option>")
					.val(obj.grade[i])
					.text(obj.grade[i])
					.appendTo("#yearClass");
					}
				}
				
			}
		});
$(document).ready(function(){
    $("#college").change(function(){
		if(($('#college option:selected').text()!="--请选择--")&& ($('#yearClass option:selected').text()!="--请选择--")&&($('#profession option:selected').text()=="")||($('#classId option:selected').text()!="")){		
		$('#classId').empty();
		var collegeName=$('#college option:selected').text();
		var yearClass=$('#yearClass option:selected').text();
		var t = {"collegeName":collegeName,"yearClass":yearClass};
		var str = JSON.stringify(t);
		$.ajax({
				type:"Post",
                contentType:"application/json",
				url:"requestProfession.html",
				dataType:"json",
				data:str,
				success:function(data){
				var obj1 = eval(data);
					if(obj1.Status=="Fail"){
						$('#profession').empty();
					}else{
						$('#profession').empty();
						if(obj1.profession.length>0){
					$("<option value='0'>--请选择--</option>")
					.appendTo("#profession");
					for(var i=0;i<obj1.profession.length;i++){
						$("<option></option>")
						.val(obj1.profession[i])
						.text(obj1.profession[i])
						.appendTo("#profession");
						}
					}
				}
					}
				});
			}else if(($('#yearClass option:selected').text()=="--请选择--")&&($('#profession option:selected').text()=="--请选择--")){
				$('#profession').empty();
			}
		});
	});
});
$(document).ready(function(){
    $("#yearClass").change(function(){
		if(($('#college option:selected').text()!="--请选择--")&&($('#college option:selected').text()!="")&& ($('#yearClass option:selected').text()!="--请选择--")&& ($('#yearClass option:selected').text()!="")){		
		$('#classId').empty();
		var collegeName=$('#college option:selected').text();
		var yearClass=$('#yearClass option:selected').text();
		var t = {"collegeName":collegeName,"yearClass":yearClass};
		var str = JSON.stringify(t);
		$.ajax({
				type:"Post",
                contentType:"application/json",
				url:"requestProfession.html",
				dataType:"json",
				data:str,
				success:function(data){
				var obj1 = eval(data);
					if(obj1.Status=="Fail"){
						$('#profession').empty();
						alert("您选择的年级不正确");
					}else{
						$('#profession').empty();
						if(obj1.profession.length>0){
					$("<option value='0'>--请选择--</option>")
					.appendTo("#profession");
					for(var i=0;i<obj1.profession.length;i++){
						$("<option></option>")
						.val(obj1.profession[i])
						.text(obj1.profession[i])
						.appendTo("#profession");
						}
					}
				}
					}
				});
			}
		});
	});

$(document).ready(function(){
	$("#profession").change(function(){
		if(($('#college option:selected').text()!="--请选择--")&&($('#college option:selected').text()!="")&&($('#yearClass option:selected').text()!="--请选择--")&&($('#yearClass option:selected').text()!="")&&($('#profession option:selected').text()!="--请选择--")&&($('#profession option:selected').text()!="")){
				var collegeName=$('#college option:selected').text();
				var yearClass=$('#yearClass option:selected').text();
				var profession=$('#profession option:selected').text();
				var t ={"collegeName":collegeName,"yearClass":yearClass,"profession":profession};
				var str = JSON.stringify(t);
				//alert("执行。。。。"+str);
		$.ajax({
				type:"Post",
                contentType:"application/json",
				url:"requestClass.html",
				dataType:"json",
				data:str,
				success:function(data){
				var obj1 = eval(data);
					if(obj1.Status=="Fail"){
						$('#classId').empty();
						alert("您选择的年级不正确");
					}else{
						$('#classId').empty();
						if(obj1.classID.length>0){
					$("<option value='0'>--请选择--</option>")
					.appendTo("#classId");
					for(var i=0;i<obj1.classID.length;i++){
						$("<option></option>")
						.val(obj1.classID[i])
						.text(obj1.classID[i])
						.appendTo("#classId");
						}
					}
				}
					}
				});
			}
		});
	});
$(document).ready(function() {
	$("#button").click(function(){
	//alert("kk");
    if($('#college option:selected').text()=="--请选择--" || $('#college option:selected').text()=="" || $('#college option:selected').text()==null ){
			alert("学院不能为空!");
			return false;
	}else if($('#yearClass option:selected').text()=="--请选择--" || $('#yearClass option:selected').text()=="" || $('#yearClass option:selected').text()==null){
			alert("年级不能为空!");
			return false;}
     else if($('#profession option:selected').text()=="--请选择--" || $('#profession option:selected').text()=="" || $('#profession option:selected').text()==null){
			alert("专业不能为空!");
			return false;}	
       else if($('#classId option:selected').text()=="--请选择--" || $('#classId option:selected').text()=="" || $('#classId option:selected').text()==null){
			alert("班级不能为空!");
			return false;}	
	else{

	var nickName=$("#nickName").val();
	var password=$("#password").val();
	var studentCardId=$("#studentCardId").val();
    var studentName=$("#studentName").val();
    var studentPhone=$("#studentPhone").val();
	var studentNum=$("#studentNum").val();
	var collegeName=$("#college option:selected").text();
	var profession=$("#profession option:selected").text();
	var yearClass=$("#yearClass option:selected").text();
	var classId = $("#classId option:selected").text();
	
	var t ={"studentName":studentName,
			"nickname":nickName,
			"id":studentNum,
			"studentPhone":studentPhone,
			"studentCardId":studentCardId,
			"password":password,
			"role":"Role_Student",
			"grade":yearClass,
			"profession":profession,
			"classId":classId,
			"college":collegeName
		};
		//json对象序列化
		var str = JSON.stringify(t);
		//alert("执行："+str);
$.ajax({  
        type:"Post",
        contentType:"application/json",
		url:"registerStudent.html",
		data:str,
        dataType:"json",  
        success:function(data){
			var obj = eval(data);
            //alert(obj.Status);
              if(obj.Status=="Success"){
                   
                   location.href="studentManager.html";
                   
                  }else if(obj.Status=="UserNicknameRepeat"){
                  //alert(1);
                    alert("该用户已注册!");
                    location.href="studentManager.html";
                  }else if(obj.Status=="Fail"){
                    alert("用户注册失败!");
                  location.href="studentManager.html";
                  }
            }
        });
        };
   	});
});
 </script>
</head>
 <body> 
   <!-- <div  id="head" >          
      </div> 
    -->
   <div  id="wrapper"  scrolling="no" align="center" style="width:500px;height:300px;">     
   <center>
   <form id="form" >
     <div  id="login" >   <h2 class="login_txt_bt"  > 学生注册</h2>   </div>
      <div class="div">
    	<span ><img src="images/nc.png" width="120" height="24" /></span><em class="red">*</em>
        <input  type="text"  id="nickName"   name="nickName1"  size="25"/>
	    <br/>
        <label  id="nickName1" class="label_txt"></label>
      </div>
    
<!-- 
     <div    class="div">
            	<span ><img src="images/tx.png" width="120" height="24"></span><em class="red">*</em>
				<input type="file" id="touxiang" name="touxiang"  size="13"/>
				<br/>
        <label  id="touxiang1" class="label_txt"></label>
		</div>

 -->
        <div   class="div">
    	<!--<span for="password"   class="form_txt">密&nbsp;&nbsp;码</span><em class="red">*</em>-->
		<span ><img src="images/mm.png" width="120" height="24" /></span><em class="red">*</em>
         <input type="password" id="password"  name="password1"   size="25"/>
		 <br/>
            <label  id="password1"   class="label_txt"></label>
    	</div>
       
        <div   class="div">
    	<span ><img src="images/qrmm.png" width="120" height="24" /></span><em class="red">*</em>
        <input type="password" id="againpassword"  name="confirm_password1"   size="25"    />
		<br/>
         <label  id="againpassword1" class="label_txt"></label>
    	</div>

        <div   class="div">
    	<span ><img src="images/cid.png" width="120" height="24" /></span><em class="red">*</em>
        <input type="text"  id="studentCardId"   name="shenfenzheng"  size="25"/>
		   <br/>
		 <label  id="studentCardId1" class="label_txt"></label>
    	</div>


        <div   class="div">
    	<span ><img src="images/xsxm.png" width="120" height="24" /></span><em class="red">*</em>
        <input  type="text" id="studentName"  name="studentName"   size="25"/>
		<br/>
		<label   id="studentName1" class="label_txt"></label>
    	</div>

         <div   class="div">
    	<span ><img src="images/sjhm.png" width="120" height="24" /></span><em class="red">*</em>
         <input type="text" id="studentPhone"  name="mobile" size="25"/>
		 <br/>
		 <label id="studentPhone1" class="label_txt"></label>
         </div>
     
	      <div   class="div">
    	   <span ><img src="images/xh.png" width="120" height="24" /></span><em class="red">*</em>
         <input type="text" id="studentNum"  name="studentNum" size="25"/>
		 <br/>
		 <label id="studentNum1" class="label_txt"></label>
         </div>

		 <span class="select">&nbsp;学院:</span><select id="college" style="width:110px">
    </select>
<span class="select">&nbsp;&nbsp;年级:</span><select id="yearClass" style="width:110px">
   </select><br/>
<span class="select">&nbsp;专业:</span><select id="profession" style="width:110px">
   </select>
<span class="select">&nbsp;&nbsp;班级:</span><select id="classId"  style="width:110px">
   </select><br/>

	<input type="button" id="button" value="提交" />	
   <input type="reset" id="res" value="重置"/>
  </form>
    </center>
</div>

   <!--    <div  id="footer">
        <div  id="footer-title"    class="login-buttom-txt">版权所有&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;    河北北方学院信息技术研究所         </div>
       
      </div>   -->
 </body>
</html>

