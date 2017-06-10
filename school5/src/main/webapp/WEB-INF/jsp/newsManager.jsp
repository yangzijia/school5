<!DOCTYPE html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<html>
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
	</head>
  <script type="text/javascript">
$(document).ready(function(){
$('#cc').combobox({    
    url:'getSection.html',    
    valueField:'sectionId',    
    textField:'sectionName' ,
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
            {field:'id',checkbox:true},
            {field:'theme',title:'新闻标题',width:80} ,
            {field:'author',title:'作者',width:80},
            {field:'checker',title:'审核人',width:80},
            {field:'reDate',title:'发表日期',width:80,
            	formatter:function(value,row){
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
            },
            {field:'url',title:'新闻url',width:80},
            {field:'opt',title:'查看',
            	formatter:function(value,row,index){
            	//alert(row.id);
            		var s = '<a href="newsUpdateManager.html?newsId='+row.id+'" target="_blank">点击修改</a> ';  
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
    		$.messager.alert('提示信息','请选择新闻栏目');
			return false;
    	}
    	function getData(){
    		var sectionName=$('#cc').combobox('getText');
    		var obj={"sectionName":sectionName};
    		var str = JSON.stringify(obj);
			$.ajax({
				type:'Post',
				contentType:'application/json',
				data:str,
				url:'showNewsUtil.html',
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
});

$(document).ready(function(){
	$('#delete_a').click(function(){
		var rows = $('#dg').datagrid('getSelections');
		if(rows.length>0){
		 $.messager.confirm("提示信息", "你确定要删除这"+rows.length+"条信息吗?", function (r) {
                             if (r) {
                                 var ids = [];
                                 for (var i = 0; i < rows.length; i++) {
                                 	var id = rows[i].id;
                                     ids.push(id);
                                 }
                                var obj=ids;
                                var str=JSON.stringify(obj);
                               $.ajax({  
							        type:'Post',
							       	contentType:'application/json',
									url:'delNews.html',
									data: str,
							        dateType:'json',  
							        success:function(data){ 
							            var obj = jQuery.parseJSON(data);
							      		if(obj.Status=="Success"){	
							            	$('#dg').datagrid('reload');
							            	for (var i = 0; i < rows.length; i++) {
								            	 var index = $('#dg').datagrid("getRowIndex", rows[i]);
								            	 $('#dg').datagrid('deleteRow',index);
							            	 }
							            	$.messager.alert('提示信息','删除成功');
							            	
							            }else{
							            	$.messager.alert('提示信息','删除失败');
											
							            }
							            
							          }
    							});    
                                 
                             }
                         });
		
		}else{
		
			$.messager.alert("提示信息","请您选择要删除的项!");
		}
	});



});

$(document).ready(function(){
	$('#append_a').click(function(){
                  
			window.open("newsInput1.html");
		});

});
</script>
  <body>
	<table id="dg"></table>
       	<div style="margin-bottom:5px">
       	<div id="tb" style="height:auto">
		<a href="javascript:void(0)" id="append_a" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" >添加</a>
		<a href="javascript:void(0)" id="delete_a" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" >删除</a>
		新闻栏目名称:<input id="cc" name="dept" value="--请选择--">
		<a href="#" id="bt" class="easyui-linkbutton" iconCls="icon-search">查询</a>
		</div>
	</div>
  </body>
</html>
