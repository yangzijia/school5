<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
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
			{field:'snumber',title:'学号',width:80} ,
			{field:'course',title:'课程',width:80},
			{field:'score',title:'分数',width:80},    
            {field:'coursetype',title:'课程类型',width:80} ,
            {field:'opinion',title:'处理状态',width:80},
            //{field:'status',title:'考勤状况',width:80},
            //{field:'teacherName',title:'任课教师',width:80},
            ]],
        });  
$(document).ready(function(){
      var rows = [];
       function getData(){
    		
    		var obj={"snumber":"","course":"","score":"","coursetype":"","opinion":""};
    		var str = JSON.stringify(obj);
			$.ajax({
				type:'Post',
				contentType:'application/json',
				data:str,
				url:'getStudentAlert.html',
				async :false,
				dataType:'json',
				success:function(data){
				//alert(data);
					if(data.Status=="CannotAnalyzeData"){
					$.messager.alert('提示信息','没有预警成绩状态！');
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
});
</script>

  </head>
  
  <body>
   <table id="dg"></table>
  </body>
</html>
