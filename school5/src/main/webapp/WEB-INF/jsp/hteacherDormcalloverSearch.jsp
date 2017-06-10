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
<style type="text/css">
.nav {
	position: relative;
	margin: 10px 0 0 0;
}
.nav ul {
	margin: 0;
	padding: 0;
}
.nav li {
	margin: 0 5px 10px 0;
	padding: 0;
	list-style: none;
	display: inline-block;
}
.nav a {
	padding: 3px 12px;
	text-decoration: none;
	color: #999;
	line-height: 100%;
}
.nav a:hover {
	color: #d0d0d0;
}
.nav .current a {
	background: #999;
	color: #fff;
	border-radius: 5px;
}
</style>		
<script type="text/javascript">
$(function(){
	$.ajax({
		type:'Post',
		contentType:'application/json',
		url:'getGradeUtilByHeadTeachernickName.html',
		async :false,
		dataType:'json',
		success:function(data){			
			$.each(data,function(i,item){	
				var gradeId = data[i].id;
				var grade = data[i].grade;
				$('#ul').append("<li class='link' value='"+gradeId+"'><a>"+grade+"</a></li>");
			})					
		}
	});
})
</script>
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
            {field:'kk',checkbox:true},
            {field:'id',title:'序号',width:80},
            {field:'studentName',title:'学生姓名',width:80},
            {field:'phone',title:'联系电话',width:80},
            {field:'name',title:'点名班委',width:80},
            {field:'status',title:'状态',width:80},
            {field:'date',title:'日期',width:80}
        ]],
    });  
  });
  
//条件查询宿舍考勤信息  
$(document).ready(function() {
	var rows = [];
    $('.link').click(function(){
    	var gradeId=$(this).val();
    	function getData(){
    		var date=$("#date").val(); 		
    		var obj={"gradeId":gradeId,"date":date};
    		var str = JSON.stringify(obj);
			$.ajax({
				type:'Post',
				contentType:'application/json',
				data:str,
				url:'WebgetDormcalloverByGrade.html',
				async :false,
				dataType:'json',
				success:function(data){			
					if(data.Status=="Fail"){					
						$.messager.alert('提示信息','没有查询到宿舍考勤信息！');	 
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


$(document).ready(function(){
	$('#delete_sta').click(function(){ 
		var rows = $('#dg').datagrid('getSelections');
		if(rows.length>0){
		 $.messager.confirm("提示信息", "你确定删除这"+rows.length+"条信息吗?", function (r) {
                             if (r) {
                                 var ids = [];
                                 for (var i = 0; i < rows.length; i++) {
                                     ids.push(rows[i].id);
                                 }
                                var obj={"ids":ids};
                                var str=JSON.stringify(obj);
                               $.ajax({  
							        type:'Post',
							       	contentType:'application/json',
									url:'dormcalloverDel.html',
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
							            	 $('#dg').datagrid('reload');
							            	$.messager.alert('提示信息','操作成功');
							            	
							            }else{
							            	$.messager.alert('提示信息','操作失败,请稍后再试。');
											
							            }
							            
							          }
    							});    
                                 
                             }
                         });
		
		}else{
		
			$.messager.alert("提示信息","请选择您要删除的信息!");
		}

	});

});
</script>
</head>
  <body>
    <table id="dg"></table>
       	<div style="margin-bottom:5px">
       	<div id="tb" style="height:auto">
		<a href="javascript:void(0)" id="delete_sta" class="easyui-linkbutton"  data-options="iconCls:'icon-remove',plain:true" >删除</a>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		查询日期：<input type="text" id="date" name="date" style="width:100px" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" /> 
		<nav class="nav" id="nav">
			<ul id="ul">
			<li class="current"><a href="#">班级名称</a></li>
			</ul>	
		</nav>
		</div>
	</div>
  </body>
</html>
