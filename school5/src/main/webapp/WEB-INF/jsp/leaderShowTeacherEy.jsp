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
	
//获取预警类型
$('#cc').combobox({    
    url:'webgetLongLatType.html',    
    valueField:'typeid',  
    textField:'longlattype',
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
        	{field:'mapId',checkbox:true}, 
        	{field:'longlattype',title:'预警类型',width:80} ,
        	{field:'longLatDate',title:'发送时间',width:80} ,
            {field:'studentNumber',title:'教师工号',width:80} ,
            {field:'studentName',title:'教师姓名',width:80},
            {field:'word',title:'情况说明',width:80},
            {field:'geography',title:'地理位置',width:80},
            {field:'opt',title:'查看',
            	formatter:function(value,row,index){
            		var s = '<a href="LeaderShowTeacherEyDetail.html?mapId='+row.mapId+'">详细信息</a> ';  
            		return s;
            	},width:80
            }
        ]],
    });  
  });

//初始化加载所有预警信息
$(document).ready(function() {
	var rows = [];
    //$('#bt').click(function(){
   	function getData(){
   		var longlattype = "";
   		var STime = "";
   		var ETime = "";
   		var obj ={"longlattype":longlattype,"STime":STime,"ETime":ETime}
   		var str = JSON.stringify(obj);
			$.ajax({
				type:'Post',
				contentType:'application/json',
				data:str,
				url:'LeadergetTeacherEmergencyByGrade.html',
				async :false,
				dataType:'json',
				success:function(data){
				if(data.Status=="Fail"){
					$.messager.alert('提示信息','没有紧急情况信息！');
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
//});	

//按类型查询预警信息
$(document).ready(function() {
	var rows = [];
    $('#bt').click(function(){
    	if($('#cc').combobox('getText')=="--请选择--"){
    		$.messager.alert('提示信息','请选择预警类型！');
			return false;
    	}
   	function getData(){
   		var longlattype=$('#cc').combobox('getText');
   		var obj ={"longlattype":longlattype}
   		var str = JSON.stringify(obj);
			$.ajax({
				type:'Post',
				contentType:'application/json',
				data:str,
				url:'LeadergetTeacherEmergencyByType.html',
				async :false,
				dataType:'json',
				success:function(data){
				if(data.Status=="Fail"){
					$.messager.alert('提示信息','没有此类型的紧急情况信息！');
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

//按日期查询预警信息
$(document).ready(function() {
	var rows = [];
    $('#time').click(function(){
   	function getData(){
   		var longlattype=$('#cc').combobox('getText');
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
   		var obj ={"longlattype":longlattype,"STime":STime,"ETime":ETime}
   		var str = JSON.stringify(obj);
			$.ajax({
				type:'Post',
				contentType:'application/json',
				data:str,
				url:'LeadergetTeacherEmergencyByGrade.html',
				async :false,
				dataType:'json',
				success:function(data){
				if(data.Status=="Fail"){
					$.messager.alert('提示信息','此时间段没有紧急情况信息！');
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

</script>
    
  
</head>
	<body>
    <table id="dg"></table>
       	<div style="margin-bottom:5px">
       	<div id="tb" style="height:auto">
       	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
       	预警类型：<input id="cc" name="dept" value="--请选择--">
		<a href="javascript:void(0)" id="bt" class="easyui-linkbutton" iconCls="icon-search">查询</a>
		&nbsp;&nbsp;
		起止时间：<input type="text" id="STime" name="STime" style="width:100px" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" /> 
		&nbsp;&nbsp;&nbsp;&nbsp;
		<input type="text" id="ETime" name="ETime" style="width:100px" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" /> 
		<a href="#" id="time" class="easyui-linkbutton" iconCls="icon-search">查询</a>
		</div>
		</div>
  </body>
</html>
