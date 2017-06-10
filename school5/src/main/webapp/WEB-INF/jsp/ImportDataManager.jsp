<!DOCTYPE html>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
<script type="text/javascript">
	var dFile = document.getElementById('file');
	var dImg = document.getElementsByTagName('file')[0];
	var dInfo = document.getElementById('info');
	dFile.onchange = function(){
	if(!dFile.value.match(/.jpg|.gif|.png|.bmp/i)){
		alert('File type must be: .jpg, .gif, .bmp or .png !');
		return;
	}
	if(dFile.files){
		dImg.src = dFile.files[0].getAsDataURL();
		alert(dImg.src);
	}else if(dFile.value.indexOf('\\') > -1 || dFile.value.indexOf('\/') > -1){
		dImg.src = dFile.value;
		alert(dImg.src);
	}
}
</script>
<script type="text/javascript">
function check(file){
	if(file.value==null||file.value==""){
		alert("请您选择要导入的Excel数据!");
    	return false;
	}
}
</script>
</head>
 
<body>
<c:if test="${!empty error}">
	        <c:out value="${error}" />
</c:if>   
<form action="<c:url value="importData.html"/>" method="post" enctype="multipart/form-data">
	导入Excel数据：
	<br/> 
	导入学生信息：
	<input type="file" name="file" id="file">
	<input type="submit" onclick="return check(file)" value="上传">
</form>
<br/>
<form action="<c:url value="importUserData.html"/>" method="post" enctype="multipart/form-data">
	导入登录用户信息：<input type="file" name="file" id="file">
	<input type="submit" onclick="return check(file)" value="上传">
</form>
<br/>
<form action="<c:url value="importRollAlertData.html"/>" method="post" enctype="multipart/form-data">
	导入不及格、重修、留降级信息：
	<input type="file" name="file" id="file">
	<input type="submit" onclick="return check(file)" value="上传">
</form>


</body> 
</html>
