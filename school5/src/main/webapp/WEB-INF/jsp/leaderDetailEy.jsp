<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>河北北方学院预警系统</title>
<link  rel="stylesheet"  type="text/css"  href="css/emergency.css" />
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
			contentType:'application/json',
			url:'leaderGetTeacherEmergencyByTeachernickNameAndMapId.html',
			async :false,
			dataType:'json',
			success:function(data){
				$('#teachernickName').val(data.teachernickName);
				$('#teacherName').val(data.teacherName);
				$('#teacherPhone').val(data.teacherPhone);
				$('#word').val(data.word);
				$('#imag').html('<img  id="img" src="'+data.pictrueURL+'"  alt="现场图片"  >');
				  //这里参考了以下两个站点的介绍 
                     //http://www.w3school.com.cn/html/html_audio.asp 
                    //http://www.zhanxin.info/development/2013-05-17-html5-audio.html 
				if($.browser.msie && $.browser.version=='9.0')
				{
					//alert("IE 9.0");
				$('#newMessageDIV').html('<embed src="'+data.voiceURL+'"    autostart="false" />'); 
				
				}else{ 
				                  //IE9+,Firefox,Chrome均支持<audio/>   Opera,Sfari浏览器不支持
								  //alert('其他');
				$('#newMessageDIV').html('<audio  id="audio"   controls><source src="'+data.voiceURL+'"'
				+ 'type="audio/wav"/><source src="'+data.voiceURL+'" type="audio/mpeg"/></audio>'); 
				} 
				
			}
			});						
									
	});
</script>
</head>
<body>
 <div id="mainPanle" scrolling="no" region="center">
  <div  class="left"  >       
                <div  class="left-center">
                    <p>
    	<span ><img src="images/jsgh.png" width="120" height="24" /></span>
         <input type="text" id="teachernickName"  name="" size="20" readonly="readonly" class="easyui-validatebox"/>
	             </p>
				  <p>
    	<span ><img src="images/jsxm.png" width="120" height="24" /></span>
         <input type="text" id="teacherName"  name="" size="20" readonly="readonly" class="easyui-validatebox"/>
	             </p>
				  <p>
    	<span ><img src="images/sjhm.png" width="120" height="24" /></span>
         <input type="text" id="teacherPhone"  name="" size="20" readonly="readonly" class="easyui-validatebox"/>
	             </p> 
                 <span ><img src="images/lyb.png" width="120" height="24" /></span><br />
                 <textarea id="word" readonly="readonly" style="width:300px;height:230px;margin-top:10px;">                 
               </textarea>                
           </div>
  </div> 
  <div  class="right">
       <div  id="imag">
     </div>
	 <div  id="voice">
    <div id="newMessageDIV"></div>
     </div>
  </div>
</div>
</body>
</html>

