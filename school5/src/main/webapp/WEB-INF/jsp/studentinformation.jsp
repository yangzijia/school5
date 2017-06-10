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
//上传头像
	function openSutdentUploadHeadPicture(){
		location.href="openStduentUpload.html";
	}
</script>
<script type="text/javascript">
$(document).ready(function(){
	$.ajax({
		type:'Post',
		url:'studentWebShow.html',
		ansyc:'false',
		dataType:'json',
		success:function(data){
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

<script type="text/javascript">
function msgShow(title, msgString, msgType) {
$.messager.alert(title, msgString, msgType);
}
        //设置修改密码窗口
        function openPwd() {
            $('#w').window({
                title: '修改密码',
                width: 300,
                modal: true,
                shadow: true,
                closed: true,
                height: 180,
                resizable:false
            });
        }
        //关闭修改密码窗口
        function close() {
            $('#w').window('close');
        }

        //设置修改电话号码窗口
		function openTel() {
            $('#e').window({
                title: '修改电话号码',
                width: 300,
                modal: false,
                shadow: true,
                closed: true,
                height: 180,
                resizable:false
            });
        }
        //关闭修改电话号码窗口
        function close() {
            $('#e').window('close');
        }

        //修改密码
        function serverLogin() {
		var $oldpass=$('#txtOldPass');
            var $newpass = $('#txtNewPass');
            var $rePass = $('#txtRePass');
           	var $newuserName = $('#userName');
						            
            if ($newuserName.val() == ''){
            
				 msgShow('系统提示', '请输入昵称！', 'warning');				
                 return false;
			}

             if ($oldpass.val() == ''){
             
			    msgShow('系统提示', '请输入旧密码！', 'warning');
                return false;
			}
      
            if ($newpass.val() == '') {
				 
                msgShow('系统提示', '请输入新密码！', 'warning');
                return false;
            }
            if ($rePass.val() == '') {
			
                msgShow('系统提示', '请再一次输入新密码！', 'warning');
                return false;
            }

			if($oldpass.val() == $newpass.val()){
				msgShow('系统提示', '旧密码与新密码密码一致,请重新输入!', 'warning');
                return false;
			}
			
            if ($newpass.val() != $rePass.val()) {
                msgShow('系统提示', '新密码与确认密码不一致,请重新输入!', 'warning');
                return false;
            }
			
           var student = {"userNickname":$('#Username').val(),"role":"Role_Student","oldPassword":$('#txtOldPass').val(), "newPassword":$('#txtNewPass').val()};
            var str = JSON.stringify(student);
			$.ajax({  
		        type:"Post",
		        contentType:"application/json",
				url:"studentChange.html",
				data:str,
		        dateType:"json",  
		        success:function(data){
		        	var obj = jQuery.parseJSON(data);
		        	if(obj.Status=="NotHaveUser"){
		        		$.messager.alert('提示信息','没有此用户!');
		        	}
		        	if(obj.Status=="OldPasswordError"){
		        		$.messager.alert('提示信息','输入的旧密码不正确!');
		        	}
		        	if(obj.Status=="Success"){
		        		$.messager.alert('提示信息','密码修改成功!');
		        		$('#w').window('close');
		        	}
		       	}
		        });
            
        }
        
        //重置清空密码
	 function resetLoginP() {

			document.getElementById('txtOldPass').value="";
			document.getElementById('txtNewPass').value="";
			document.getElementById('txtRePass').value="";
		            		         
	                   }
		//修改电话号码
        function telLogin() {
            var $oldTel = $('#oldTel');
            var $newTel = $('#newTel');
            var $newUsername = $('#Username');
			//var mobile = /^1[3|4|5|8][0-9]\d{8}$/;  /*手机号码验证*/

			if ($newUsername.val() == ''){
			
			    msgShow('系统提示', '请输入昵称！', 'warning');								
                return false;
			}

            if ($oldTel.val() == ''  ) {
			
				msgShow('系统提示', '请输入旧电话号码!', 'warning');
                return false;
            }
            if ($newTel.val() == '' ) {
			
			 msgShow('系统提示', '请输入新电话号码！', 'warning');
                return false;
            }

            if ($oldTel.val() == $newTel.val()) {
                msgShow('系统提示', '两次输入的电话号码一致,请重新输入电话号码!', 'warning');
                return false;
            }

            var student ={"userNickname":$('#Username').val(),"phone":$('#newTel').val(),"role":"Role_Student"};
				var str = JSON.stringify(student);
				$.ajax({  
		        type:"Post",
		        contentType:"application/json",
				url:"studentChange.html",
				data:str,
		        dateType:"json",  
		        success:function(data){
		        	obj = jQuery.parseJSON(data) ;
		        	if(obj.Status=="NotHaveUser"){
		        		$.messager.alert('提示信息','没有此用户!');
		        	}
		        	if(obj.Status=="PhoneError"){
		        		$.messager.alert('提示信息','输入的电话格式不正确!');
		        		}
		        	if(obj.Status=="Success"){
		        		$.messager.alert('提示信息','修改电话成功!');
		        		$('#e').window('close');
		        		$('#studentPhone').val($('#newTel').val());
		        		}
		        	}
		        });
        }

		//清空重置电话号码
           function resetLoginT() {
			document.getElementById('oldTel').value="";
			document.getElementById('newTel').value="";
			
           }

      //修改密码
        $(function() {

            openPwd();
            
            $('#editpass').click(function() {
                $('#w').window('open');
            });

            $('#btnEp').click(function() {
                serverLogin();
            });
			$('#close1').click(function(){
			    resetLoginP();
			});
        });



		$(function() {

			openTel();
			//
			$('#ed').click(function(){
			     $('#e').window('open');
			});
			$('#qd').click(function(){
			     telLogin();
		    });
           $('#close2').click(function(){
			     resetLoginT();
			});
        });
</script>
</head>
<body>




	<!--<div  id="mainPanle1"   style="background: #D2E0F2; ">-->
 <div id="mainPanle" scrolling="no" region="center" 
                 style="background: #ffffff; overflow:hidden;width: 1100px; height: 430px;margin: 15px  auto   0px   auto;overflow: hidden;padding:  0px 0px 0px  0px;border:2px #ffffff solid;"  >
        <div  id="main"  style="background: #ffffff; overflow:hidden;width: 98%; height:400px;margin:8px auto auto auto;border:2px #ffffff solid;padding:2px;">





<div id="w" class="easyui-window" title="修改密码" collapsible="false" minimizable="false"
        maximizable="false" icon="icon-save"  style="width: 300px; height: 150px; padding: 5px;
        background: #fafafa;">
        <div class="easyui-layout" fit="true">
            <div region="center" border="false" style="padding: 10px; background: #fff; border: 1px solid #ccc;">
                <table cellpadding=3>
                    <tr>
                        <td>昵称:</td>
                        <td><input id="userName" type="text" readonly="readonly" class="txt01" value="<sec:authentication property="principal.username" />" /></td>                  
                    </tr>
					<tr>
                        <td>旧密码：</td>
                        <td><input id="txtOldPass" type="password" class="txt01" /></td>
                    </tr>
                    <tr>
                        <td>新密码：</td>
                        <td><input id="txtNewPass" type="password" class="txt01" /></td>
                    </tr>
                    <tr>
                        <td>确认密码：</td>
                        <td><input id="txtRePass" type="password" class="txt01" /></td>
                    </tr>
                </table>
            </div>
            <div region="south" border="false" style="text-align: right; height: 30px; line-height: 30px;">
                <a id="btnEp" class="easyui-linkbutton" icon="icon-ok" href="javascript:void(0)" >
                    确定</a>
		   <a class="easyui-linkbutton" icon="icon-cancel" href="javascript:void(0)" id="close1">清空</a>
            </div>
        </div>
    </div><!--w-->

    <div id="e" class="easyui-window" title="修改电话号码" collapsible="false" minimizable="false"
        maximizable="false" icon="icon-save"  style="width: 300px; height: 150px; padding: 5px;
        background: #fafafa;">
        <div class="easyui-layout" fit="true">
            <div region="center" border="false" style="padding: 10px; background: #fff; border: 1px solid #ccc;">
                <table cellpadding=3>
                    <tr>
                        <td>昵称:</td>
                        <td><input id="Username" type="text" readonly="readonly" class="txt01" value="<sec:authentication property="principal.username" />" /></td>                     
                    </tr>
                    <tr>
                        <td>旧电话号码:</td>
                        <td><input id="oldTel" type="text" class="txt01" /></td>
                    </tr>
                    <tr>
                        <td>新电话号码:</td>
                        <td><input id="newTel" type="text" class="txt01" /></td>
                    </tr>
                </table>
            </div>
            <div region="south" border="false" style="text-align: right; height: 30px; line-height: 30px;">
                <a id="qd" class="easyui-linkbutton" icon="icon-ok" href="javascript:void(0)" >
                    确定</a> 
					<a class="easyui-linkbutton" icon="icon-cancel" href="javascript:void(0)"
                      id="close2" >清空</a><!--修改的地方  加了id值，去了onclick()事件-->
            </div>
        </div>
    </div><!--e-->


<div id="form"   style="padding:3px   10px  9px  60px;margin:6px  auto  9px  auto;width:650px;border:solid 2px #91b5e7;height:379px;">
    <div  id="form1"  style="margin:5px 0px 0px 0px ">
              <h2  class="login_txt_bt">欢迎学生修改</h2>
           

<p>
    	<span ><img src="images/nc.png" width="120" height="24" /></span>
        <input  type="text"  id="nickName"   name="textfield1"  size="25" value="<sec:authentication property="principal.username" />" readonly="readonly" class="easyui-validatebox" />
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

	<br/>
	<p>
    &nbsp; &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;<input type="submit" name="button" id="editpass" value="修改密码" />

      <input type="submit" name="button2" id="ed" value="修改电话"/>

      <!--<input type="submit" name="button3" id="re" value="返回" style="background:url(/images/button_span_bg.gif ) no-repeat;"/>--><!--修改的地方-->
	  </p>



    </div><!--form1-->
<div   id="img"  style="width:220px;height:350px;margin:20px auto;padding-left:3px;padding-bottom:2px;border:solid 2px #97b9e6;">

      <div   id="img1"  style="width:200px;border:solid 2px #97b9e6;height:280px;margin:15px auto;background:url(images/touxiang.png ) no-repeat">        

            </div>
     <input type="button" name="button" onclick="openSutdentUploadHeadPicture()" id="" value="上传头像" />
<div  >





<div  id="clear"></div>
		</div><!--form-->


</div><!--main-->
    </div><!--mainPanle-->
<!--</div>end   mainPanle1-->




</body>
</html>

