<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
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
　
<title>Insert title here</title>
<script type="text/javascript">
   	
    	function tijiaopushs(){
    		var titles = $("#titles").val();
    		var texts = $("#texts").val();
    		var sendtypes = $("#sendtype").val();
    		//alert(titles+","+texts+","+sendtypes);
    		var ts = {
                	"title":titles,
                	"text":texts,
                	"sendtype":sendtypes,
                	"iostype":"ios"
                };
    		var str = JSON.stringify(ts);
    		//alert(str);
    		$.ajax({
				type:"POST",  
                contentType:"application/json",  
                url:"apppushinfo.html",  
                data:str,  
                dataType:"json",  
				success : function(data) {
					alert(data.status);
				}
			});
    	}
    	function getpushInfos(){
    		$.ajax({
				type:"POST",  
                contentType:"application/json",  
                url:"getpushinfos.html",  
                dataType:"json",  
				success : function(data) {
				    var strs = "";
					$.each(data, function() {
						strs = strs + "<tr onclick='deletethisinfo("+this.id+",this)'><td>"+this.id+"</td><td>"+this.push_sender+"</td><td>"+this.push_time+"</td><td>"+this.push_title+"</td><td>"+this.push_text+"</td></tr>";
					})
					$("#show_pushinfos").html(strs);
				}
			});
    	}
    	function deletethisinfo(obj,k){
    		var push_id = {"push_id":obj}
    		var str = JSON.stringify(push_id);
    		if(confirm("是否确定删除这条计划？")){
    			$.ajax({
				type:"POST",  
                contentType:"application/json",  
                url:"deletepushinfos.html",  
                data:str,  
                dataType:"json",  
				success : function(data) {
				    if(data.status=="success"){
				    	alert("删除成功！");
				    	//$(k).parent().remove();
				    }
				}
			});
    		}
    	}
    </script>
</head>
<body>
		标题：<input type="text" name="titles" id="titles" style="width: 203px; "/><br><br />
		内容：<textarea rows="3" cols="7" name="texts" id="texts" style="height: 78px; width: 203px; "></textarea><br><br />
		类型：<select id="sendtype" style="width: 203px;">
			<option value="LeaderAll">给所有人推送</option>
			<option value="LeaderAllStudent">给所有学生推送</option>
			<option value="LeaderAllHeadTeacher">给所有老师推送</option>
			<option value="LeaderAllLeader">给所有领导推送</option>
		</select>
		
		<br><br><input type="submit" value="提交" onclick="tijiaopushs()"/>
		
		<br><br><input type="button" value="获取接受的推送消息" onclick="getpushInfos()"/>
		<br><br>
		<div>
			<table border="1">
				<tr>
					<th>id</th>
					<th>领导</th>
					<th>时间</th>
					<th>标题</th>
					<th>内容</th>
				</tr>
				<tbody  id="show_pushinfos">
					
				</tbody>
			</table>
		</div>
</body>
</html>