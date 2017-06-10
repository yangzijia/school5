<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>河北北方学院预警系统</title>
<link rel="stylesheet" type="text/css" href="css/default.css" />
<!--<link rel="stylesheet" type="text/css" href="js/themes/default/easyui.css" />-->
<link rel="stylesheet" type="text/css" href="js/themes/default/icon.css" />
<link  rel="stylesheet"  type="text/css"  href="css/studentRegisterD.css" />
<link rel="stylesheet" type="text/css" href="jqueryeasyui/themes/default/easyui.css"/>
<link rel="stylesheet" type="text/css" href="jqueryeasyui/themes/icon.css"/>
<link rel="stylesheet" type="text/css" href="jqueryeasyui/demo/demo.css"/>
<script type="text/javascript" src="jqueryeasyui/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="jqueryeasyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="js/jquery.json-2.4.js"></script>
<script type="text/javascript" src="js/json2.js"></script>
<script type="text/javascript" src="jqueryeasyui/easyui-lang-zh_CN.js"></script>    
 
<script type="text/javascript">
$(document).ready(function(){
	$.ajax({
		type:'Post',
		url:'headteacherWebShow.html',
		ansyc:'false',
		dataType:'json',
		success:function(data){	
			$('#realName').val(data.name);
			$('#teacherCardId').val(data.cardId);
			$('#teacherPhone').val(data.phone);					
			$('#college').val(data.college);
		},
		
	});
});
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
          //修改班级窗口
          	function openClass() {
            $('#c').window({
                title: '修改班级信息',
                width: 300,
                modal: false,
                shadow: true,
                closed: true,
                height: 180,
                resizable:false
            });
        }
        //关闭修改班级窗口
        function close() {
            $('#c').window('close');
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

          var headteacher ={"userNickname":$('#Username').val(),"role":"Role_HeadTeacher","oldPassword":$('#txtOldPass').val(),"newPassword":$('#txtNewPass').val()};
            var str = JSON.stringify(headteacher);
			$.ajax({  
		        type:"Post",
		        contentType:"application/json",
				url:"headTeacherChange.html",
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
              	var headteacher ={"userNickname":$('#Username').val(),"phone":$('#newTel').val(),"role":"Role_HeadTeacher"};
				var str = JSON.stringify(headteacher);
				$.ajax({  
		        type:"Post",
		        contentType:"application/json",
				url:"headTeacherChange.html",
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
		        		$('#teacherPhone').val($('#newTel').val());
		        		}
		        	}
		        });

        }

        $(function() {
            openPwd();   //修改密码
            $('#editpass').click(function() {
            
                $('#w').window('open');
            });
            $('#btnEp').click(function() {
                serverLogin();
            });
            //响应清空按钮
            	$('#close1').click(function(){
			    resetLoginP();
			});
        });
		$(function() {
			openTel();  //修改电话
			$('#ed').click(function(){
			$('#e').window('open');
			});
			$('#qd').click(function(){
			     telLogin();
		    });
		    //响应清空按钮
		     $('#close2').click(function(){
			    resetLoginT();
			});
        });

 //重置清空密码
	 function resetLoginP() {

			document.getElementById('txtOldPass').value="";
			document.getElementById('txtNewPass').value="";
			document.getElementById('txtRePass').value="";
		            		         
	       }
	 //清空重置电话号码
     function resetLoginT() {
			document.getElementById('oldTel').value="";
			document.getElementById('newTel').value="";
			
           }
   
   
   
//显示辅导员管理的班级
$(document).ready(function(){

        $('#dg').datagrid({     
		autoRowHeight:true,
		fit:false,
		fitColumns:true,  
        striped: true,  
        border: true,  
        collapsible:false,//是否可折叠的       
        singleSelect:false,//是否单选 
        pagination:false,//分页控件
        pageSize:2000,//显示的最多页数
		pagePosition:'bottom',  
        rownumbers:true,//行号  
        columns:[[  
           // {field:'gradeId'},
            {field:'yearClass',title:'年级',align:'center'} ,
            {field:'profession',title:'专业',align:'center'},
            {field:'classId',title:'班级',align:'center'},
        ]],
    });  
  });
$(document).ready(function() {
	var rows = [];
    
    	function getData(){
    		var nickName = document.getElementById('nickName').value;
    		var obj={"nickName":nickName};
    		var str = JSON.stringify(obj);
			$.ajax({
				type:'Post',
				contentType:'application/json',
				data:str,
				url:'getGradeutilByNickName.html',
				async :false,
				dataType:'json',
				success:function(data){
					rows = data;
					//alert(rows);
					
				}
			});
				return rows;
		}
		
		function pagerFilter(data){
			
			if (typeof data.length == 'number' && typeof data.splice == 'function'){	// is array
				data = {
					total: data.length,
					rows: data
				};
			}
			var dg = $('#dg');
			var opts = dg.datagrid('options');
			var pager = dg.datagrid('getPager');
			pager.pagination({
				onSelectPage:function(pageNum, pageSize){
					opts.pageNumber = pageNum;
					opts.pageSize = pageSize;
					pager.pagination('refresh',{
						pageNumber:pageNum,
						pageSize:pageSize
					});
					dg.datagrid('loadData',data);
				}
			});
			if (!data.originalRows){
				data.originalRows = (data.rows);
			}
			var start = (opts.pageNumber-1)*parseInt(opts.pageSize);
			var end = start + parseInt(opts.pageSize);
			data.rows = (data.originalRows.slice(start, end));
			return data;
		}
	$('#dg').datagrid({loadFilter:pagerFilter}).datagrid('loadData', getData());
	});
</script>
</head>
<body>
	
 <div id="mainPanle" scrolling="no" region="center" 
       style="background: #ffffff; overflow:hidden;width: 1100px; height: 500px;margin: 5px  auto   0px   auto;overflow: hidden;padding:  0px 0px 0px  0px;border:2px #ffffff solid;"  >
        <div  id="main"  style="background: #ffffff; overflow:hidden;width: 98%; height:480px;margin:5px auto auto auto;border:2px #ffffff solid;padding:2px;">

<div id="w" class="easyui-window" title="修改密码" collapsible="false" minimizable="false"
        maximizable="false" icon="icon-save"  style="width: 300px; height: 150px; padding: 5px;
        background: #fafafa;">
        <div class="easyui-layout" fit="true">
            <div region="center" border="false" style="padding: 10px; background: #fff; border: 1px solid #ccc;">
                <table cellpadding=3>
                    <tr>
                        <td>昵称:</td>
                        <td><input id="userName" value="<sec:authentication property="principal.username"/>" readonly="readonly" type="text" class="txt01" /></td>
                    </tr>
                    <tr>
                        <td>旧密码:</td>
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
            
            <div region="south" border="false" style="text-align: right; height: 30px; line-height: 0px;">
                <a id="btnEp" class="easyui-linkbutton" icon="icon-ok" href="javascript:void(0)" >
                    确定</a>
					<a class="easyui-linkbutton" icon="icon-cancel" href="javascript:void(0)"
                 id="close1">清空</a>
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
                        <td><input id="Username" value="<sec:authentication property="principal.username"/>" readonly="readonly" type="text" class="txt01" /></td>                
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
            <div region="south" border="false" style="text-align: right; height: 30px; line-height: 0px;">
                <a id="qd" class="easyui-linkbutton" icon="icon-ok" href="javascript:void(0)" >
                    确定</a> <a class="easyui-linkbutton" icon="icon-cancel" href="javascript:void(0)"
                        id="close2">取消</a>
            </div>
        </div>
    </div><!--e-->


    
<div id="form"  style="padding:15px   10px  9px  60px;margin:0px  auto  9px  auto;width:360px;border:solid 2px #91b5e7;height:450px;" >
<h2  class="login_txt_bt">欢迎辅导员修改</h2>  
    <p>
    	<span ><img src="images/nc.png" width="120" height="24" /></span>
        <input  type="text"  id="nickName"   name="nickName1"  size="25"  value="<sec:authentication property="principal.username"/>" readonly="readonly" class="easyui-validatebox"/>
    </p>         

	<p>
    	<span ><img src="images/cid.png" width="120" height="24" /></span>
        <input type="text"  id="teacherCardId"   name="shenfenzheng"  size="25"/>
	</p>

    <p>
    	<span ><img src="images/rn.png" width="120" height="24" /></span>
        <input  type="text" id="realName"  name="realName"   size="25" readonly="readonly" class="easyui-validatebox"/>
    </p>

     <p>
    	<span ><img src="images/sjhm.png" width="120" height="24" /></span>
         <input type="text" id="teacherPhone"  name="teacherPhone" size="25" readonly="readonly" class="easyui-validatebox"/>
	</p>
	
    <p>
      <span ><img src="images/xueyuan1.png" width="120" height="24" /></span>
      <input type="text" id="college" name="college" size="25" readonly="readonly" class="easyui-validatebox"/>
    </p>
   
       	
 	<table id="dg"  style="width:300px;height:180px;margin-top:6px;"></table>
       	 <p>
	  &nbsp; &nbsp;&nbsp; &nbsp;<input type="submit" name="button" id="editpass" value="修改密码"/>
	  &nbsp; &nbsp;
      <input type="submit" name="button2" id="ed" value="修改电话" />&nbsp;
  	  &nbsp; &nbsp;&nbsp; &nbsp;<a href="headteacherChangeclass.html">修改班级</a>
	</p>    
	
		</div><!--form-->

</div><!--main-->
</div><!--mainPanle-->

</body>
</html>

