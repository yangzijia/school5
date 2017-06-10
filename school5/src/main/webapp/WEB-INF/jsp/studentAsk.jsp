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

<link rel="stylesheet" type="text/css" href="jqueryeasyui/themes/default/easyui.css" />
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
		url:'getHeadTeaNameBynickName.html',
		ansyc:'false',
		dataType:'json',
		success:function(data){
		//得到辅导员姓名
			$('#teachername').val(data.teacherName);
		}
	});
});

</script>


</head>
<body>
<form id="form" action="#" class="login_txt" method="post">
<div id="mainPanle" scrolling="no" region="center" 
                 style="background: #ffffff; overflow:hidden;width: 1100px; height: 430px;margin: 15px  auto   0px   auto;overflow: hidden;padding:  0px 0px 0px  0px;border:2px #ffffff solid;"  >
<div id="form2"   style="padding:3px   10px  9px  60px;margin:6px  auto  9px  auto;width:650px;border:solid 2px #91b5e7;height:390px;background: #fff;">
    <div  id="form1"  region="center" style="margin:15px 0px 0px 0px;width:608px;">
              <h2  class="login_txt_bt" align="center">在线请假申请</h2>
              <div style="float:left;">
    <p>
    	<span style="display:block;"><strong>昵&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;称：</strong>
		<input type="text" id="Nickname" name="Nickname" value=<sec:authentication property="principal.username" /> size="25" readonly="readonly" />
		</span>
	</p>  
		
	<p>
    	<span style="display:block;"><strong>教师姓名：</strong>
		<input type="text" id="teachername" name="teachername" size="25" readonly="readonly" />
		</span>
	</p>  
		
 	<p>
    	<span style="display:block;"><strong>开始时间：</strong>
		<input type="text" id="StartTime" name="StartTime" style="width:150px" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" />
		</span>
	</p>  		
	<p>
    	<span style="display:block;"><strong>结束时间：</strong>
		<input type="text" id="EndTime" name="EndTime" style="width:150px" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" />
		</span>
	</p>
	<p>
    	<span style="display:block;"><strong>家长姓名：</strong>
		<input type="text" id="ParentsName" name="ParentsName" style="width:150px"  />
		</span>
	</p>
	<p>
    	<span style="display:block;"><strong>家长电话：</strong>
		<input type="text" id="ParentsPhone" name="ParentsPhone" style="width:150px" />
		</span>
	</p>
	</div>
	
	<div style="float:right;">
	<p>
    	<span style="display:block;"><strong>请假类型：</strong>
       <!--<select name="type" id="type" style="width:174px" class="easyui-validatebox">
            <option selected="selected">病假</option>
            <option>事假</option>
            <option>其他</option>
         </select>--> 
         <input type="radio" name="type" value="病假" checked="checked" />病假
         <input type="radio" name="type" value="事假" />事假
         <input type="radio" name="type" value="其他" />其他
        </span>
	</p>
	<p>
    	<span style="display:block;"><strong>是否外宿：</strong>
         <input type="radio" name="sleep" value="是" checked="checked" />是
         <input type="radio" name="sleep" value="否" />否
        </span>
	</p>
	<p>
    	<span style="display:block;"><strong>请假原因：</strong>
    	</span>
    	
    	<span style="display:block;">
		<textarea name="reason" id="reason" cols="39" rows="6" class="easyui-validatebox"></textarea>
		</span>
		
		</p>
		
		
</div>
<p></p><p></p><p></p><p></p><p></p>
		 <input type="button" name="button" id="ask" style="float:right;font-size:14px;margin-left:310px;margin-top: 0px;" value="确认请假" />
	
      </div><!--form1-->
    </div><!--form2-->
	</div>
	</form>
	
	<!-- 与位置有关系 -->
	<script type="text/javascript">
	var $ = jQuery;
	jQuery("#ask").click(function(){   
		    
		var Nickname=$("#Nickname").val();
		var teachername=$("#teachername").val();
		var StartTime=$("#StartTime").val();
		var EndTime=$("#EndTime").val();
		//添加的
		var ParentsName=$("#ParentsName").val();
		var ParentsPhone=$("#ParentsPhone").val();	
		
		var radios=document.getElementsByName("type");
		 for(var i=0;i<radios.length;i++)
        {
            if(radios[i].checked==true)
            {
                //alert("你选择的请假类型是："+radios[i].value);
                var type=radios[i].value;
            }
        }
		 
		var sleeps=document.getElementsByName("sleep");
		    for(var i=0;i<sleeps.length;i++)
		    {
		     if(sleeps[i].type=="radio"&&sleeps[i].checked){
		    	 var sleep = sleeps[i].value;
		     }		    
		    }
    	var reason=$("#reason").val();    		  		
    	if (StartTime > EndTime) {
                msgShow('系统提示', '开始时间必须小于结束时间！', 'warning');
                return false;
        }	
    	//对象	
		var t = { "Nickname":Nickname,
				  "teachername":teachername,
				  "StartTime":StartTime,
				  "EndTime":EndTime,
				  "type":type,
				  "sleep":sleep,
				  "reason":reason,
				  //添加的
				  "ParentsName":ParentsName,
				  "ParentsPhone":ParentsPhone,
				  };
		
		//把object转为json		  		 	
		var str = JSON.stringify(t);	
	        jQuery.ajax({				
			    type:"POST",  
                contentType:"application/json",  
                url:"studentAsk.html",  
                data:str,  
                dataType:"json",  		
				success : function(data) {	
					
						if(data.Status=="Fail"){
							msgShow('系统提示', '请假失败，请重新申请。', 'warning');				
                   		}else if(data.Status=="Auditing"){
                   			$.messager.alert('提示信息','系统正忙，请稍后再试。');                  		    
                   		}else if(data.Status=="Success"){
                   			$.messager.alert('提示信息','申请成功，请耐心等待老师的批准！');                            		
                   		} else if(data.Status=="PhoneError"){
                   			$.messager.alert('提示信息','手机号码格式不正确！');                            		
                   		}            	
				}
			});
		});
</script>

<script type="text/javascript">
function msgShow(title, msgString, msgType) {
	$.messager.alert(title, msgString, msgType);
}
</script>
</body>
</html>

