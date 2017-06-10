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
    url:'getCollege.html',    
    valueField:'collegeid',    
    textField:'collegeName' ,
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
            {field:'teacherName',title:'老师姓名',width:80} ,
            {field:'teacherPhone',title:'联系方式',width:80},
            {field:'teacherCardId',title:'身份证号',width:80},
            {field:'nickName',title:'登录昵称',width:80},
            {field:'opt',title:'查看',
            	formatter:function(value,rec,incex){
            		var s = '<a href="headTeacherDetail.html"  onclick="">详细信息</a> ';  
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
    		$.messager.alert('提示信息','请选择学院');
			return false;
    	}
    	function getData(){
    		var collegeName=$('#cc').combobox('getText');
    		var obj={"collegeName":collegeName};
    		var str = JSON.stringify(obj);
			$.ajax({
				type:'Post',
				contentType:'application/json',
				data:str,
				url:'showHeadteacher.html',
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
		$('#append_a').click(function(){
			//window.location.href="webheadteacherRegister.html";
                  window.open("webheadteacherRegister.html");  
                  /*  if ( endEditing()) {
                    	添加时如果没有正在编辑的行，则在datagrid的第一行插入一行
                        $('#dg').datagrid('appendRow',{id:''});
						editIndex = $('#dg').datagrid('getRows').length-1;
						$('#dg').datagrid('selectRow', editIndex)
								.datagrid('beginEdit', editIndex);
					}*/
			
		});
		
		
		$('#accept_a').click(function(){
			if(endEditing()){
					var ss = [];
					var rows = $('#dg').datagrid('getChanges');
					if(rows.length>0){
					for(var i=0; i<rows.length; i++){
						 var teacherName = rows[i].teacherName;
                                     var nickName = rows[i].nickName;
                                     var teacherPhone = rows[i].teacherPhone;
                                     var teacherCardId=rows[i].teacherCardId;
                                     var teacherandUSer={"teacherName":teacherName,"nickName":nickName,"teacherPhone":teacherPhone,"teacherCardId":teacherCardId};
                         ss.push(teacherandUSer);
					}
					var obj={"teacherList":ss};
					var str=JSON.stringify(obj);
					$.messager.confirm('确认','您确认想要插入这'+rows.length+'条数据吗?',function(r){    
					if (r){    
						$.ajax({  
						        type:'Post',
						       	contentType:'application/json',
								url:'teacherAdd.html',
								data: str,
						        dateType:'json',  
						        success:function(data){  
						            var obj = jQuery.parseJSON(data);
						            if(obj.Status=="Success"){	
						            	$.messager.alert('提示信息','插入成功初始密码为123456');
						            	$('#dg').datagrid('acceptChanges');
						            }else{
						            	$.messager.alert('提示信息','插入失败');
						            	$('#dg').datagrid('rejectChanges');
										editIndex = undefined;
						            }
						            
						          }
		    			});    
				} 
			});
		}else{
		
			return false;
			}
		}
});
		$('#edit_rec').click(function(){
			$('#dg').datagrid('rejectChanges');
			editIndex = undefined;
			if($('#edit_sta').text()=="保存修改"){
				 $('#edit_sta').linkbutton({    
					iconCls: 'icon-edit' , 
					text:'修改'
				});
			}
			
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
                                 	var nickName = rows[i].nickName;
                                 	var id = rows[i].id;
                                 	var teahceruser = {"nickName":nickName,"id":id};
                                     ids.push(teahceruser);
                                 }
                                var obj=ids;
                                var str=JSON.stringify(obj);
                               $.ajax({  
							        type:'Post',
							       	contentType:'application/json',
									url:'delTeacher.html',
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
	$('#edit_sta').click(function(){
		if($('#edit_sta').text()=="修改"){
			var rows = $('#dg').datagrid('getSelections');
			if(rows.length<1) {
			$.messager.alert('提示信息','请您选择要修改的行!');
			return false;
			}
		    $('#edit_sta').linkbutton({    
				iconCls: 'icon-edit' , 
				text:'保存修改'
			});
		    for (var i = 0; i < rows.length; i++) {
		        	 var index = $('#dg').datagrid("getRowIndex", rows[i]);
		        	 $('#dg').datagrid('selectRow', index)
					.datagrid('beginEdit', index);
			}
		}else if($('#edit_sta').text()=="保存修改"){
			var rows = $('#dg').datagrid('getSelections');
			for(var i=0;i<rows.length;i++){
				var index = $('#dg').datagrid("getRowIndex",rows[i]);
				if ($('#dg').datagrid('validateRow', index)){
				//添加时先判断是否有开启编辑的行，如果有则把开户编辑的那行结束编辑
					$('#dg').datagrid('endEdit', index);
				} else {
					return false;
				}
			
			}
			var rows1 = $('#dg').datagrid('getSelections');
			
			if(rows1.length>0){
		 		$.messager.confirm("提示信息", "你确定要修改这"+rows1.length+"条信息吗?", function (r) {
                             if (r) {
                                 var ids = [];
                                 for (var i = 0; i < rows1.length; i++) {
                                     var id=rows1[i].id;
                                     var teacherName = rows1[i].teacherName;
                                     var nickName = rows1[i].nickName;
                                     var teacherPhone = rows1[i].teacherPhone;
                                     var teacherCardId=rows1[i].teacherCardId;
                                     var teacherandUSer={"id":id,"teacherName":teacherName,"nickName":nickName,"teacherPhone":teacherPhone,"teacherCardId":teacherCardId};
                                     ids.push(teacherandUSer);
                                 }
                                var obj={"teacherList":ids};
                                var str=JSON.stringify(obj);
                               $.ajax({  
							        type:'Post',
							       	contentType:'application/json',
									url:'teacherUpdate.html',
									data: str,
							        dateType:'json',  
							        success:function(data){  
							            var obj = jQuery.parseJSON(data);
							      		if(obj.Status=="Success"){	
							     
							            	$.messager.alert('提示信息','更新成功');
			 								$('#edit_sta').linkbutton({    
												iconCls: 'icon-edit' , 
												text:'修改'
												});	
							            	
							            }else{
							            	$.messager.alert('提示信息','更新失败');
											
							            }
							            
							          }
    							});    
                                 
                             }
                         });
		
		}else{
		
			$.messager.alert("提示信息","请您选择要更新的项!");
		}
			
		}
	
	});
});
$(document).ready(function(){
$('#password_rec').click(function(){
		var rows = $('#dg').datagrid('getSelections');
		if(rows.length>0){
		 $.messager.confirm("提示信息", "你确定要重置这"+rows.length+"项密码吗?", function (r) {
                             if (r) {
                                 var ids = [];
                                 for (var i = 0; i < rows.length; i++) {
                                 	var nickName = rows[i].nickName;
                                     ids.push(nickName);
                                 }
                                var obj=ids;
                                var str=JSON.stringify(obj);
                               $.ajax({  
							        type:'Post',
							       	contentType:'application/json',
									url:'resettingPassword.html',
									data: str,
							        dateType:'json',  
							        success:function(data){ 
							            var obj = jQuery.parseJSON(data);
							      		if(obj.Status=="Success"){	
							            	$('#dg').datagrid('reload');
							            	$.messager.alert('提示信息','修改成功重置后密码为123456');
							            	
							            }else{
							            	$.messager.alert('提示信息','重置失败');
											
							            }
							            
							          }
    							});    
                                 
                             }
                         });
		
		}else{
		
			$.messager.alert("提示信息","请您选择要重置密码的项!");
		}
	});
});

</script>
  <body>
	<table id="dg"></table>
       	<div style="margin-bottom:5px">
       	<div id="tb" style="height:auto">
		<a href="javascript:void(0)" id="append_a" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" >添加</a>
		<a href="javascript:void(0)" id="delete_a" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" >删除</a>
		<a href="javascript:void(0)" id="edit_rec" class="easyui-linkbutton" data-options="iconCls:'icon-undo',plain:true">撤销编辑</a>
		<a href="javascript:void(0)" id="password_rec" class="easyui-linkbutton" data-options="plain:true">密码重置 </a>
		学院名称:<input id="cc" name="dept" value="--请选择--">
		<a href="#" id="bt" class="easyui-linkbutton" iconCls="icon-search">查询</a>
	</div>
  </body>
</html>
