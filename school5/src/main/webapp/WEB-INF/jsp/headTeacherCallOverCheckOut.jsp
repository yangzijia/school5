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
$(document).ready(function(){
	$('#cc').combobox({    
	    url:'getGradeUtilByHeadTeachernickName.html',    
	    valueField:'id',    
	    textField:'grade' ,
	    editable:false  
	});
	
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
            {field:'status',title:'考勤状况',width:80} ,
            {field:'teacherName',title:'任课教师',width:80},
            {field:'opt',title:'查看',
            	formatter:function(value,row,index){
            		var s = '<a href="leaderCheckOutDetail.html?StudentNumber='+row.studentNumber+'&StudentName='+row.studentName+'">详细信息</a> ';  
            		return s;
            	},width:80
            }
        ]],
    });  
  });
  
$(document).ready(function() {
	var rows = [];
    $('#bt').click(function(){
    	if($('#cc').combobox('getText')=="--请选择--"){
    		$.messager.alert('提示信息','请选择班级');
			return false;
    	}
    	function getData(){
    		var gradeId=$('#cc').combobox('getValue');
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
    		var obj={"gradeID":gradeId,"STime":STime,"ETime":ETime};
    		var str = JSON.stringify(obj);
			$.ajax({
				type:'Post',
				contentType:'application/json',
				data:str,
				url:'WebcheckOutcomeByGradeID.html',
				async :false,
				dataType:'json',
				success:function(data){
				if(data.Status=="Fail"){
					$.messager.alert('提示信息','此时间段没有缺勤信息！');
				}			
					rows = data.callOverUtilsList;					
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
</script>
    
  
</head>
	<body>
    <table id="dg"></table>
       	<div style="margin-bottom:5px">
       	<div id="tb" style="height:auto">
		班级名称:<input id="cc" name="dept" value="--请选择--" style="width:170px;">
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
