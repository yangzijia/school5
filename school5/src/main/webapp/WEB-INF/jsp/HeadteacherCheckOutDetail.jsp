<!DOCTYPE html>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />		
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
		$('#append_a').click(function(){
		/*
		*history是Javascript中Window下的对像，用于存储浏览器的历史信息。
		它含有三个方法，go(),back()和forward()，分别用来控制页面的跳转。
		其中：back()表示返回到上一页面，效果相当于go(-1);
        forward()表示返回到下一页面，效果相当于go(1);
        go()用于指定页的跳转，比如go(-2)表示返回到浏览过的前两个页面。
		*/
                 history.go(-1);
		});

});

    
 $(document).ready(function(){
        $('#dg').datagrid({     
		autoRowHeight:false,
		fit:true,
		fitColumns:true,  
        striped: true,  
        border: true,  
        collapsible:true,//是否可折叠的       
        singleSelect:false,//是否单选 
        pagination:true,//分页控件
		pagePosition:'bottom',  
        rownumbers:true,//行号  
        toolbar:'#tb',
        columns:[[  
           	{field:'course',title:'课程名称',width:80} ,
            {field:'coursetype',title:'课程类型',width:80},
            {field:'opinion',title:'处理状态',width:80},  
        ]]
    });  
  });
$(document).ready(function() {
   var rows = [];
		function getData(){
			$.ajax({
				type:'Post',
				url:'headteacherGetCheckOutDetail.html',
				async :false,
				dataType:'json',
				success:function(data){
				
					rows =data.callOver;
					
					$('#yearClass').val(data.grade.yearClass);
					$('#profession').val(data.grade.profession);
					$('#classId').val(data.grade.classId);
					$('#name').val(data.name);
					$('#studentNumber').val(data.studentNumber);						
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
    <table id="dg" ></table>
       	<div style="margin-bottom:5px">
       	<div id="tb" style="height:auto">
       	年级:<input id="yearClass" class="easyui-validatebox" readonly="readonly">
       	专业:<input id="profession" class="easyui-validatebox" readonly="readonly">
       	班级：<input id="classId" class="easyui-validatebox" readonly="readonly">
       	姓名：<input id ="name" class="easyui-validatebox" readonly="readonly">
       	学号：<input id="studentNumber" class="easyui-valivatebox" readonly="readonly">
       	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<a href="javascript:void(0)" id="append_a" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-back'">返回</a>
		</div>
	</div>
  </body>
</html>
