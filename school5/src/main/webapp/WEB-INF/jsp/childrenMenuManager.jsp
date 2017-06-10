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
$('#cc').combobox({    
    url:'parentMenuGet.html',    
    valueField:'id',    
    textField:'menuName' ,
    editable:false  
});

});
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
        //toolbar:'#tb',
        frozenColumns:[[  
            {field:'id',checkbox:true}  
        ]]
    });  
  });
$(document).ready(function() {
	var rows = [];
    $('#bt').click(function(){
    	if($('#cc').combobox('getText')=="--请选择--"){
    		$.messager.alert('提示信息','请选择父菜单');
			return false;
    	}
    	function getData(){
			var parentMenunName=$('#cc').combobox('getText');
    		var parentMenu ={"parentMenuName":parentMenunName};
    		var parentMenuStr = JSON.stringify(parentMenu);
			$.ajax({
				type:'Post',
				contentType:'application/json',
				url:'getChildrenMenu.html',
				async :false,
				data:parentMenuStr,
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
		if($('#edit_sta').text()=="保存修改"){
			return false;
		}
		if($('#type option:selected').text()=="--请选择--"){
			$.messager.alert('提示信息','请选择父菜单');
			return false;
		}else{
			if(endEditing()){
					var ss = [];
					var rows = $('#dg').datagrid('getChanges','inserted');
					if(rows.length>0){
					for(var i=0; i<rows.length; i++){
						var childrenMenuname = rows[i].childrenmenuName;
                        var chidrenUrl = rows[i].url;
                       	var childrenMenu={"childrenmenuName":childrenMenuname,"url":chidrenUrl};
                       	ss.push(childrenMenu);
					}
					var parentMenuId =$('#cc').combobox('getValue');
					//alert("parentMenuId"+$('#cc').combobox('getValue'));
					var obj={"parentMenuId":parentMenuId,"childrenMenuList":ss};
					var str=JSON.stringify(obj);
					alert(str);
					$.messager.confirm('确认','您确认想要插入'+$('#cc').combobox('getText')+'菜单下吗？',function(r){    
					if (r){    
						$.ajax({  
						        type:'Post',
						       	contentType:'application/json',
								url:'childrenMenuAdd.html',
								data: str,
						        dateType:'json',  
						        success:function(data){  
						            var obj = jQuery.parseJSON(data);
						            if(obj.Status=="Success"){	
						            	$.messager.alert('提示信息','插入成功');
						            	$('#dg').datagrid('acceptChanges');
						            }else{
						            	$.messager.alert('提示信息','插入失败url已存在！');
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
                                     ids.push(rows[i].id);
                                 }
                                var obj={"childrenMenuId":ids};
                                var str=JSON.stringify(obj);
                               $.ajax({  
							        type:'Post',
							       	contentType:'application/json',
									url:'childrenMenuDel.html',
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
                                     var childrenMenuname = rows1[i].childrenmenuName;
                                     var chidrenUrl = rows1[i].url;
                                     var childrenMenu={"id":id,"childrenmenuName":childrenMenuname,"url":chidrenUrl};
                                     ids.push(childrenMenu);
                                 }
                                var obj={"childrenMenuList":ids};
                                var str=JSON.stringify(obj);
                               $.ajax({  
							        type:'Post',
							       	contentType:'application/json',
									url:'childrenMenuUpdate.html',
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
		<table id="dg" data-options="toolbar:'#tb'">
		<thead>
			<tr>
            	<th data-options="field:'childrenmenuName',editor:{type:'validatebox',options:{required:true}}" width="80">菜单名</th>
				<th data-options="field:'url',editor:{type:'validatebox',options:{required:true}}" width="80">地址</th>
				<th data-options="field:'remark',editor:{type:'validatebox',options:{required:false}}" width="80">备注</th>
			</tr>
		</thead>
       	</table>
       	<div id="tb" style="padding:5px;height:auto">
		<div style="margin-bottom:5px">
		<a href="javascript:void(0)" id="append_a" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" >添加</a>
		<a href="javascript:void(0)" id="delete_a" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" >删除</a>
		<a href="javascript:void(0)" id="accept_a" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:true" >保存</a>
		<a href="javascript:void(0)" id="edit_sta" class="easyui-linkbutton"  data-options="iconCls:'icon-edit',plain:true" >修改</a>
		<a href="javascript:void(0)" id="edit_rec" class="easyui-linkbutton" data-options="iconCls:'icon-undo',plain:true">撤销编辑</a>
		<!--  <div class="combo-panel panel-body panel-body-noheader" title style="width: 148px; height: 90px ! important;">
		</div>-->
		父菜单名称:<input id="cc" name="dept" value="--请选择--" height="90px">
		<a href="#" id="bt" class="easyui-linkbutton" iconCls="icon-search">查询</a>
			</div>
		</div>
	</body>
</html>
