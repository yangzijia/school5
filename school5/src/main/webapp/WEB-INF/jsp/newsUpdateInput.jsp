
<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>河北北方学院预警系统</title>
<link rel="stylesheet" type="text/css"
	href="jqueryeasyui/themes/default/easyui.css" />
<link rel="stylesheet" type="text/css"
	href="jqueryeasyui/themes/icon.css" />
<link rel="stylesheet" type="text/css" href="jqueryeasyui/demo/demo.css" />
<script type="text/javascript" src="jqueryeasyui/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="jqueryeasyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="js/jquery.json-2.4.js"></script>
<script type="text/javascript" src="js/json2.js"></script>
<script type="text/javascript" src="jqueryeasyui/easyui-lang-zh_CN.js"></script>


<!-- 引入配置文件 -->
<script type="text/javascript" charset="utf-8"
	src="Ueditor/ueditor.config.js"></script>
<!-- 引入编辑器的源码文件 -->
<script type="text/javascript" charset="utf-8"
	src="Ueditor/ueditor.all.min.js"> </script>
<!--建议手动加在语言，避免在ie下有时因为加载语言失败导致编辑器加载失败-->
<!--这里加载的语言文件会覆盖你在配置项目里添加的语言类型，比如你在配置项目里配置的是英文，这里加载的中文，那最后就是中文-->
<script type="text/javascript" charset="utf-8"
	src="Ueditor/lang/zh-cn/zh-cn.js"></script>
<style type="text/css">
div {
	width: 100%;
}
</style>
<script type="text/javascript">
UE.getEditor('editor');
$(document).ready(function(){
	$.ajax({
		type:'post',
		url:'getNewsByNewId.html',
		dataType:'json',
		success:function(data){	
			
			$('#newsTitle').val(data.theme);
			$('#newsAuthor').val(data.author);
			$('#checker').val(data.checker);
			//调用UE编辑器的setContent()方法来获取从数据库拿到的数据，并显示在编辑器上
			UE.getEditor('editor').setContent(data.content, true);
			 }
		});
	
	$.ajax({
        type:'Post',
		url:'getSection.html',
		dateType:'json',
		success:function(data){
		//alert(data);
		$("<option value='0'>--请选择栏目--</option>").appendTo("#section");
		
	var myobj=eval(data); 
    for(var i=0;i<myobj.length;i++){ 
	$("<option ></option>")
			.val(myobj[i].sectionName)
			.text(myobj[i].sectionName)
			.appendTo("#section");	
			} 
		}
	
});
	});
	
	
	   
</script>

</head>

<body>

	<form id="editor_id" name="newsadd" method="post">
		<div id="login">
			<a class="login_txt_bt"><h1>新闻消息录入</h1></a>
		</div>
		<br />
		<div>
			新闻标题：&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" id=newsTitle
				name="newsTitle" style="width: 220px; height: 15px;" tabindex="1"></input>
		</div>
		<br />
		<div>
			新闻作者：&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" id=newsAuthor
				name="newsAuthor" style="width: 150px; height: 15px;" tabindex="2"></input>
		</div>
		<br />
		<div>
			审核人为：&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" id=checker
				name="checker" style="width: 150px; height: 15px;" tabindex="2"></input>
		</div>
		<br />
		<div>
			新闻栏目：&nbsp;&nbsp;&nbsp;&nbsp;<select id="section" name="section"
				style="width: 105px; height: 20px;" tabindex="3"></select>
		</div>
		<br />
		<div>
			<!--  <textarea name="content" id="content" style="width:1300px;height:200px;"></textarea>-->
			<script id="editor" name="content" type="text/plain"
				style="width: 1000px; height: 200px;"></script>
		</div>
		<br /> <input type="button" id="button" name="button" value="提交"tabindex="6" />
	</form>
	<span><font color="red">注:录入新闻时，确认左上角的“HTML代码”选择按钮未选中。新闻录入完
	</font></span><br />
      <span><font color="red">成时选中“HTML代码按钮”,生成HTML代码之后再提交。</font></span>
	<br />
<script type="text/javascript">
//实例化编辑器  
//建议使用工厂方法getEditor创建和引用编辑器实例，如果在某个闭包下引用该编辑器，直接调用UE.getEditor('editor')就能拿到相关的实例  
var ue = UE.getEditor('editor');  

jQuery("#button").click(function(){ 
      var newsTitle=$("#newsTitle").val();
      var newsAuthor=$("#newsAuthor").val();
      var checker=$("#checker").val();
      var section=$("#section").val();
      //调用UE编辑器的getContent()方法来获取输入到编辑器的内容
      var editor=UE.getEditor('editor').getContent();
      //json对象 
    var t = { "content":editor,"newsTitle":newsTitle, "newsAuthor":newsAuthor,"checker":checker,"section":section};
      
  //把object转为json字符串                         
    var str = JSON.stringify(t);  
      jQuery.ajax({                     
        type:"POST",  
        contentType:"application/json",  
        url:"updateNews.html",  
        data:str,  
        dataType:"json",          
                success : function(data) { 
                  alert(1);
                      if(data.Status=="Success"){
                                  msgShow('系统提示', '新闻修改成功！', 'warning');   
                                  window.location.href="newsInput1.html";
                      }else if(data.Status=="CannotAnalyzeData"){
                            $.messager.alert('提示信息','没有数据，请重新编写数据！');   
                            window.location.href="newsInput1.html";
                      }else if(data.Status=="Fail"){
                            $.messager.alert('提示信息','新闻修改失败！'); 
                            window.location.href="newsInput1.html";
                      }             
                }
          });
});
//显示提示消息的方法
function msgShow(title, msgString, msgType) {
    $.messager.alert(title, msgString, msgType);
}
</script>     
</body>
</html>



