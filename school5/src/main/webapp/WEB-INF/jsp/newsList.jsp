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
            {field:'author',title:'新闻来源',width:80} ,//修改此处
            {field:'reDate',title:'发布时间',width:80,formatter:function(value,row){
            		 var datetime = new Date();
    				datetime.setTime(row.reDate);
				    var year = datetime.getFullYear();
				    var month = datetime.getMonth() + 1 < 10 ? "0" + (datetime.getMonth() + 1) : datetime.getMonth() + 1;
				    var date = datetime.getDate() < 10 ? "0" + datetime.getDate() : datetime.getDate();
				    var hour = datetime.getHours()< 10 ? "0" + datetime.getHours() : datetime.getHours();
				    var minute = datetime.getMinutes()< 10 ? "0" + datetime.getMinutes() : datetime.getMinutes();
				    var second = datetime.getSeconds()< 10 ? "0" + datetime.getSeconds() : datetime.getSeconds();
				    return year + "-" + month + "-" + date+" "+hour+":"+minute+":"+second;
            	}
            },//修改此处
            {field:'theme',title:'新闻标题',
            	formatter:function(value,row,incex){
            	//alert(row.id);
            		var s = '<a href="news/'+row.id+'/web.html" target="_blank">'+row.theme+'</a>';  
            		return s;
            	},width:80
            }//修改此处
        ]],
    });  
  });
$(document).ready(function(){
    	function getData(){
			$.ajax({
				type:'Post',
				contentType:'application/json',
				url:'showWebNewsList.html',
				async :false,
				dataType:'json',
				success:function(data){
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
</script>
</head>
	<body>
    	<table id="dg"></table>
    </body>
</html>
