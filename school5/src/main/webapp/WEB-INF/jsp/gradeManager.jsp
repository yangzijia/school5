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
            {field:'gradeId',checkbox:true},
            {field:'yearClass',title:'年级',width:80,editor: { type: 'validatebox', options: { required: true}}} ,
            {field:'profession',title:'专业',width:80,editor: { type: 'validatebox', options: { required: true}}},
            {field:'classId',title:'班级',width:80,editor: { type: 'validatebox', options: { required: true}}},
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
				url:'getGradeutilByCollegeName.html',
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
		var  editIndex = undefined;
		function endEditing(){
			if (editIndex == undefined){return true;}
			//验证是否为必填，如果必填不能为空
			if ($('#dg').datagrid('validateRow', editIndex)){
			//添加时先判断是否有开启编辑的行，如果有则把开户编辑的那行结束编辑
				$('#dg').datagrid('endEdit', editIndex);
				editIndex = undefined;
				return true;
			} else {
				return false;
			}
		}
		$('#append_a').click(function(){
                    
                    if ( endEditing()) {
                    //添加时如果没有正在编辑的行，则在datagrid的第一行插入一行
                        $('#dg').datagrid('appendRow',{id:''});
						editIndex = $('#dg').datagrid('getRows').length-1;
						$('#dg').datagrid('selectRow', editIndex)
								.datagrid('beginEdit', editIndex);
					}
			
		});
		
		
		$('#accept_a').click(function(){
		if($('#type option:selected').text()=="--请选择--"){
			$.messager.alert('提示信息','请选择学院');
			return false;
		}else{
			if(endEditing()){
					var ss = [];
					var rows = $('#dg').datagrid('getChanges','inserted');
					if(rows.length>0){
					for(var i=0; i<rows.length; i++){
						 var yearClass = rows[i].yearClass;
                         var profession = rows[i].profession;
                         var classId = rows[i].classId;
                         var grade={"yearClass":yearClass,"profession":profession,"classId":classId};
                         ss.push(grade);
					}
					 var collegeid=$('#cc').combobox('getValue');
					var obj={"collegeid":collegeid,"gradeList":ss};
					var str=JSON.stringify(obj);
					alert(str);
					$.messager.confirm('确认','您确认想要插入这'+rows.length+'条数据插入到'+$('#cc').combobox('getText')+'下吗?',function(r){    
					if (r){    
						$.ajax({  
						        type:'Post',
						       	contentType:'application/json',
								url:'gradeAdd.html',
								data: str,
						        dateType:'json',  
						        success:function(data){  
						            var obj = jQuery.parseJSON(data);
						            if(obj.Status=="Success"){	
						            	$('#dg').datagrid('acceptChanges');
						            	$.messager.alert('提示信息','插入成功');
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
                                 	var yearClass = rows[i].yearClass;
	                         	    var profession = rows[i].profession;
	                         		var classId = rows[i].classId;
	                         		var grade={"yearClass":yearClass,"profession":profession,"classId":classId};
                                     ids.push(grade);
                                 }
                                var obj={"gradeList":ids};
                                var str=JSON.stringify(obj);
                               $.ajax({  
							        type:'Post',
							       	contentType:'application/json',
									url:'gradeDel.html',
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
	                                 var yearClass = rows1[i].yearClass;
	                         	     var profession = rows1[i].profession;
	                         		 var classId = rows1[i].classId;
	                         		 var gradeId=rows1[i].gradeId;
	                         		 var grade={"gradeId":gradeId,"yearClass":yearClass,"profession":profession,"classId":classId};
                                     ids.push(grade);
                                 }
                                var obj={"gradeList":ids};
                                var str=JSON.stringify(obj);
                               $.ajax({  
							        type:'Post',
							       	contentType:'application/json',
									url:'gradeUpdate.html',
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

</script>
    
  
</head>
	<body>
    <table id="dg"></table>
       	<div style="margin-bottom:5px">
       	<div id="tb" style="height:auto">
		<a href="javascript:void(0)" id="append_a" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" >添加</a>
		<a href="javascript:void(0)" id="delete_a" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" >删除</a>
		<a href="javascript:void(0)" id="accept_a" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:true">保存</a> 
		<a href="javascript:void(0)" id="edit_rec" class="easyui-linkbutton" data-options="iconCls:'icon-undo',plain:true">撤销编辑</a>
		<a href="javascript:void(0)" id="edit_sta" class="easyui-linkbutton"  data-options="iconCls:'icon-edit',plain:true" >修改</a>
		学院名称:<input id="cc" name="dept" value="--请选择--">
		<a href="#" id="bt" class="easyui-linkbutton" iconCls="icon-search">查询</a>
	</div>
  </body>
</html>
