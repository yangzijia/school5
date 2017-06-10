<!DOCTYPE html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />	
<title></title>
<link rel="stylesheet" type="text/css" href="jqueryeasyui/themes/default/easyui.css"/>
<link rel="stylesheet" type="text/css" href="jqueryeasyui/themes/icon.css"/>
<link rel="stylesheet" type="text/css" href="jqueryeasyui/demo/demo.css"/>
<script type="text/javascript" src="jqueryeasyui/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="jqueryeasyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="js/jquery.json-2.4.js"></script>
<script type="text/javascript" src="js/json2.js"></script>
<script type="text/javascript" src="jqueryeasyui/easyui-lang-zh_CN.js"></script>
<script language="javascript" type="text/javascript" src="calendar/WdatePicker.js"></script>
<script type="text/javascript">

//新添加的------->贾少游
//页面初始化的时候加载所有考勤数据
$(document).ready(function(){
        $('#dg').datagrid({     
		autoRowHeight:false,
		fit:true,
		fitColumns:true,  
        striped: true,  
        border: true,  
        collapsible:false,//是否可折叠的       
        singleSelect:false,//是否单选 
        pagination:true,//分页控件
		pagePosition:'bottom',  
        rownumbers:true,//行号  
        toolbar:'#tb',
        columns:[[  
			{field:'yearClass',title:'年级',width:80} ,
			{field:'profession',title:'专业',width:80},
			{field:'classId',title:'班级',width:80},    
            {field:'studentNumber',title:'学号',width:80} ,
            {field:'studentName',title:'学生姓名',width:80},
            {field:'status',title:'考勤状况',width:80},
            {field:'teacherName',title:'任课教师',width:80},
            {field:'opt',title:'查看',
            	formatter:function(value,row,incex){
            		var s = '<a href="leaderCheckOutDetail.html?StudentNumber='+row.studentNumber+'&StudentName='+row.studentName+'">详细信息</a> ';  
            		return s;
            	},width:80
            }
            ]],
        });  

       function getData(){
    		
    		var obj={"yearClass":"","profession":"","classId":"","STime":"","ETime":""};
    		var str = JSON.stringify(obj);
			$.ajax({
				type:'Post',
				contentType:'application/json',
				data:str,
				url:'getwebLeaderGetCheckOut.html',
				async :false,
				dataType:'json',
				success:function(data){
					if(data.Status=="Fail"){
					$.messager.alert('提示信息','此时间段内没有缺勤信息！');
				}
					rows = data;
					
				}
			});
				return rows;
		}
		
		function pagerFilter(data){
			
			if (typeof data.length == 'number' && typeof data.splice == 'function'){

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

//获取每个学院每个专业每个班级考勤情况
$(document).ready(function() {
	var rows = [];
    $('#bt').click(function(){
    	function getData(){
    		var yearClass=$('#yearClass option:selected').text();
    		var profession = $('#profession option:selected').text();
    		var classId = $('#classId option:selected').text(); 	
    		var STime=$("#STime").val();
    		var ETime=$("#ETime").val();
    		
    		if (STime=="" || ETime=="") {
    			$.messager.alert('提示信息','起止时间不能为空！');
                return false;
             }
    		if (STime > ETime) {
    			$.messager.alert('提示信息','开始时间必须小于结束时间！');
                return false;
        	} 
    		var obj={"yearClass":yearClass,"profession":profession,"classId":classId,"STime":STime,"ETime":ETime};
    		var str = JSON.stringify(obj);
			$.ajax({
				type:'Post',
				contentType:'application/json',
				data:str,
				url:'getwebLeaderGetCheckOut.html',
				async :false,
				dataType:'json',
				success:function(data){
					if(data.Status=="Fail"){
					$.messager.alert('提示信息','此时间段内没有缺勤信息！');
				}
					rows = data;
					
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
});

		
//选择学院、年级、专业、班级
$(document).ready(function(){
	$.ajax({
            type:"Post",
            contentType:"application/json",
			url:"requestCollGra.html",
			dataType:"json",
			success:function(data){
			//转换为json对象
			var obj = eval(data);
			//alert(obj);
			////括号中的意思是输出college的子对象数量
			if(obj.college.length>0){
				$("<option value='0'>--请选择--</option>")
				//appendTo() 方法在被选元素的结尾（仍然在内部）插入指定内容。
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
</script>
    
  
</head>
	<body>
    <table id="dg"></table>
       	<div style="margin-bottom:5px">
       	<div id="tb" style="height:auto">
学院: <select id="college"  style="width:80px">
 </select>
年级:<select id="yearClass"  style="width:80px">
 </select>
专业： <select id="profession"  style="width:80px">
  </select>
班级： <select id="classId"  style="width:80px">
 </select> 
		&nbsp;&nbsp;
		起止时间：<input type="text" id="STime" name="STime" style="width:100px" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" /> 
		&nbsp;&nbsp;&nbsp;&nbsp;
		<input type="text" id="ETime" name="ETime" style="width:100px" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" /> 
		<a href="#" id="bt" class="easyui-linkbutton" iconCls="icon-search">查询</a>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		各班学生缺勤信息
		</div>
	</div>
  </body>
</html>
