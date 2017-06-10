<!DOCTYPE html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<% 
HttpSession hp  = request.getSession();
String nickName =(String)hp.getAttribute("userName");
%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>河北北方学院预警系统</title>

 <link rel="stylesheet" type="text/css" href="jqueryeasyui/themes/default/easyui.css"/>
 <link rel="stylesheet" type="text/css" href="jqueryeasyui/themes/icon.css"/>
 <link rel="stylesheet" type="text/css" href="jqueryeasyui/demo/demo.css"/>
 <script type="text/javascript" src="jqueryeasyui/jquery-1.7.2.min.js"></script>
 <script type="text/javascript" src="jqueryeasyui/jquery.easyui.min.js"></script>
 <script type="text/javascript" src="js/jquery.json-2.4.js"></script>
 <script type="text/javascript" src="js/json2.js"></script>
 <script type="text/javascript" src="js/jquery.validate.js" ></script>
				

<script type="text/javascript">
$(document).ready(function(){
$('#cc').combobox({    
    url:'getCollege.html',    
    valueField:'collegeid',    
    textField:'collegeName' ,
    editable:false  
});
        $('#dg').datagrid({     
		autoRowHeight:false,
		fit:false,
		fitColumns:true,  
        striped: true,  
        border: true,  
        collapsible:false,//是否可折叠的       
        singleSelect:false,//是否单选 
        pagination:false,//分页控件
        pageSize:2000,//显示的最多页数
		pagePosition:'bottom',  
        rownumbers:true,//行号  
        toolbar:'#tb',
        columns:[[  
            {field:'gradeId',checkbox:true},
            {field:'yearClass',title:'年级',width:50,editor: { type: 'validatebox', options: { required: true}}} ,
            {field:'profession',title:'专业',width:150,editor: { type: 'validatebox', options: { required: true}}},
            {field:'classId',title:'班级',width:50,editor: { type: 'validatebox', options: { required: true}}},
        ]],
    });  
  });
$(document).ready(function() {
	var rows = [];
    $('#bt').click(function(){
    	if($('#cc').combobox('getText')=="--请选择--"){
    		$.messager.alert('提示信息','请选择学院');
			return false;
    	}
    	function getData(){
    		var collegeName=$('#cc').combobox('getText');
    		var obj={"collegeName":collegeName};
    		var str = JSON.stringify(obj);
    		//alert(str);
			$.ajax({
				type:'Post',
				contentType:'application/json',
				data:str,
				url:'getGradeutilByCollegeName.html',
				async :false,
				dataType:'json',
				success:function(data){
					rows = data;
					//alert(rows);
					
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


<script type="text/javascript">
$(document).ready(function(){
	$("#button").click(function(){
	var rows = $('#dg').datagrid('getSelections');
	 if($('#cc').combobox('getText')=="--请选择--" || $('#cc').combobox('getText')=="" || $('#cc').combobox('getText')==null ){
			alert("学院不能为空!");
			return false;}
			else if	(rows.length<1 ){
			alert("年级专业班级不能为空!");
			return false;}
			else{
	var checked = []; 
	 for (var i = 0; i < rows.length; i++) {
        var a = rows[i].gradeId;
        checked.push(a); 
 	}
 	
    var collegeName=$('#cc').combobox('getText');
    alert(collegeName);
	var t = {
				 "collegeName":collegeName,
				 "grade":checked};
				var str = JSON.stringify(t);
			
				
	$.ajax({  
        type:'Post',
        contentType:'application/json',
		url:'headteacherChangeClass.html',
		data: str,
        dateType:'json',  
        success:function(data){  
            var obj = jQuery.parseJSON(data); 
            alert(obj.Status);
            alert(data);
              if(obj.Status=="Success"){
                    //alert("222");
                   location.href="index.html";
                   
                  }
                   else
                   {
                   		 location.href="#";
                   }
             
             }
          });
  		}
  });
});
</script>
</head>
<body>
	<div id="mainPanle" scrolling="no" region="center"
		style="background: #ffffff; overflow:hidden;width: 500px; height: 350px;margin: 91px  auto   0px   auto;overflow: hidden;padding:  0px 0px 0px  0px;border:2px #91b5e7 solid;">
		<center>
			<form id="form" style="margin-top:50px;">
				<div id="login">
					<span class="login_txt_bt">辅导员修改班级</span>
				</div>
				<br />

				<table id="dg" style="width:450px;height:200px;margin-top:6px;"></table>
				<div style="margin-bottom:5px;">
					<div id="tb" style="height:auto">


						学院名称:<input id="cc" name="dept" value="--请选择--" /> <a href="#"
							id="bt" class="easyui-linkbutton" iconCls="icon-search">查询</a>
					</div>

				</div>

				<input type="button" id="button" value="提交" /> <input type="reset"
					id="res" value="重置" />
			</form>
		</center>
	</div>
</body>
</html>



