<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<!-- <%
HttpSession hp  = request.getSession();
String nickName =(String)hp.getAttribute("userName");
System.out.println(nickName);
 %>  -->
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>河北北方学院预警系统</title>
<link rel="stylesheet" type="text/css" href="js/themes/default/easyui.css" />
<link rel="stylesheet" type="text/css" href="js/themes/default/icon.css" />
<link rel="stylesheet" type="text/css" href="css/default.css" />


<link  rel="stylesheet"  type="text/css"  href="css/studentRegisterA.css">
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
				url:'showWebAdmin.html',
				async :false,
				dataType:'json',
				success:function(data){
					
					$('#adminPhone').val(data.phone);
					$('#adminName').val(data.name);
					//$('#oldTel').val(data.phone);
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
			if ($oldpass.val() == '') {
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

            var administrator = {"userNickname":$('#Username').val(),"role":"Role_Administrator","oldPassword":$('#txtOldPass').val(), "newPassword":$('#txtNewPass').val()};
            var str = JSON.stringify(administrator);
			$.ajax({  
		        type:"Post",
		        contentType:"application/json",
				url:"administratorChange.html",
				data:str,
		        dateType:"json",  
		        success:function(data){
		        	var obj = jQuery.parseJSON(data);
		        	if(obj.Status=="NotHaveUser"){
		        		$.messager.alert('提示信息','没有此用户!');
		        	}
		        	if(obj.Status=="OldPasswordError"){
		        		$.messager.alert('提示信息','输入的密码不正确!');
		        	}
		        	if(obj.Status=="Success"){
		        		$.messager.alert('提示信息','密码修改成功!');
		        		$('#w').window('close');
		        	}
		        }
		        });
            
        }
		//修改电话号码
        function telLogin() {
            var $oldTel = $('#oldTel');
            var $newTel = $('#newTel');
            var $newUsername = $('#Username');
			
			if ($newUsername.val() == ''){
			    msgShow('系统提示', '请输入昵称！', 'warning');
                return false;
			}
            if ($oldTel.val() == '') {
                msgShow('系统提示', '请输入旧电话号码!', 'warning');
                return false;
            }
            if ($newTel.val() == '') {
                msgShow('系统提示', '请输入新电话号码！', 'warning');
                return false;
            }

            if ($oldTel.val() == $newTel.val()) {
                msgShow('系统提示', '两次输入的电话号码一致,请重新输入电话号码!', 'warning');
                return false;
            }

            var administrator ={"userNickname":$('#Username').val(),"phone":$('#newTel').val(),"role":"Role_Administrator"};
				var str = JSON.stringify(administrator);
				$.ajax({  
		        type:"Post",
		        contentType:"application/json",
				url:"administratorChange.html",
				data:str,
		        dateType:"json",  
		        success:function(data){
		        	obj = jQuery.parseJSON(data) ;
		        	if(obj.Status=="PhoneError"){
		        		$.messager.alert('提示信息','输入的电话格式不正确!');
		        		}
		        	if(obj.Status=="Success"){
		        		$.messager.alert('提示信息','修改电话成功!');
		        		$('#e').window('close');
		        		$('#adminPhone').val($('#newTel').val());
		        		}
		        	}
		        });
            
        }

        $(function() {

            openPwd();
            //
            $('#editpass').click(function() {
                $('#w').window('open');
            });

            $('#btnEp').click(function() {
                serverLogin();
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
        });
</script>
<script type="text/javascript">
	$(document).ready(function(){
		$("#close1").click(function(){
			 $('#txtOldPass').val("");
			 $('#txtNewPass').val("");
             $('#txtRePass').val("");                   
		});
	});

	$(document).ready(function(){
		$("#close2").click(function(){
		
			$('#oldTel').val("");
            $('#newTel').val("");                   
		});
	});
</script>
</head>
<body>

 <!--<div region="north" split="true" border="false" style="overflow: hidden; height: 30px;
        background: url(layout-browser-hd-bg.gif) #7f99be repeat-x center 50%;
        line-height: 20px;color: #fff; font-family: Verdana, 微软雅黑,黑体">
        <span style="float:right; padding-right:20px;" class="head"> <a href="#" id="loginOut"><font color="#FFFFFF">安全退出</font></a></span>
        <span style="padding-left:10px; font-size: 14px; text-align:center;"><img src="blocks.gif" width="20px" height="20" align="absmiddle" /> 河北北方学院预警系统</span>
   
    </div>  -->


	<!--<div  id="mainPanle1"   style="background: #D2E0F2; ">-->
 <div id="mainPanle" scrolling="no" region="center" 
                 style="background: #ffffff; overflow:hidden;width: 1150px; height: 430px;margin: 15px  auto   0px   auto;overflow: hidden;padding:  0px 0px 0px  0px;border:2px #ffffff solid;"  >
        <div  id="main"  style="background: #ffffff; overflow:hidden;width: 98%; height:400px;margin:8px auto auto auto;border:2px #ffffff solid;padding:2px;">




<div id="w" class="easyui-window" title="修改密码" collapsible="false" minimizable="false"
        maximizable="false" icon="icon-save"  style="width: 300px; height: 150px; padding: 5px;
        background: #fafafa;">
        <div class="easyui-layout" fit="true">
            <div region="center" border="false" style="padding: 10px; background: #fff; border: 1px solid #ccc;">
                <table cellpadding=3>
                    <tr>
                        <td>昵称:</td>
                        <td><input  value=<sec:authentication property="principal.username"/> id="Username" type="text" readonly="readonly" class="txt01" /></td>
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
                 <a class="easyui-linkbutton" icon="icon-cancel" href="javascript:void(0)" id="close1">取消</a>
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
                        <td><input id="Username" readonly="readonly" type="text" class="txt01" value=<sec:authentication property="principal.username"/> /></td>
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
                    确定</a> <a class="easyui-linkbutton" icon="icon-cancel" href="javascript:void(0)"
                        id="close2">取消</a>
            </div>
        </div>
    </div><!--e-->

<div id="form"  style="padding:40px   10px  9px  60px;margin:30px  auto  9px  auto;width:390px;border:solid 2px #91b5e7;height:320px;" >
<h2  class="login_txt_bt">欢迎管理员修改</h2>

 <p>
    	<span ><img src="images/nc.png" width="120" height="24"></span>
        <input  type="text"   id="nickName" value=<sec:authentication property="principal.username"/>   name="textfield1"  size="25"  readonly="readonly" class="easyui-validatebox" />
	</p>
   
<p>
    	<span ><img src="images/glyxm.png" width="120" height="24"></span>
        <input  type="text"  id="adminName"   size="25"  readonly="readonly" class="easyui-validatebox"  name="textfield2"  />
	</p>

	<p>
    	<span ><img src="images/sjhm.png" width="120" height="24"></span>
        <input  type="text"   id="adminPhone"   name="textfield3"  size="25"  readonly="readonly" class="easyui-validatebox" />
	</p>

	<p>
	  &nbsp; &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;<input type="submit" name="button" id="editpass" value="修改密码" />
      <input type="submit" name="button2" id="ed" value="修改电话" />
     
	
	</p>
		</div><!--form-->

</div><!--main-->
    </div>


</body>
</html>

