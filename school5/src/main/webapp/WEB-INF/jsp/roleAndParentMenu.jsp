<!DOCTYPE html>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
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
<script type="text/javascript">
$(document).ready(function(){
$('#cc').combobox({    
    url:'roleNameGet.html',    
    valueField:'id',    
    textField:'remark' ,
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
        pageSize:200,
        pageList:[200],
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
    function getData(){
			$.ajax({
				type:'Post',
				contentType:'application/json',
				url:'getParentMenuUtil.html',
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


$(document).ready(function(){
	$('#accept_a').click(function(){
		var rows = $('#dg').datagrid('getSelections');
		if(rows.length>0){
		if($('#cc').combobox('getText')=="--请选择--"){
    		$.messager.alert('提示信息','请选择权限名');
			return false;}
		 $.messager.confirm("提示信息", "你确定要加这"+rows.length+"条父菜单到"+$('#cc').combobox('getText')+"吗?", function (r) {
                             if (r) {
                                 var ids = [];
                                 for (var i = 0; i < rows.length; i++) {
                                     ids.push(rows[i].id);
                                 }
                                 var roleNameId = $('#cc').combobox('getValue');
                                var obj={"roleNameId":roleNameId,"parentMenuIdList":ids};
                                var str=JSON.stringify(obj);
                               $.ajax({  
							        type:'Post',
							       	contentType:'application/json',
									url:'addParentMenuToRole.html',
									data: str,
							        dateType:'json',  
							        success:function(data){ 
							            var obj = jQuery.parseJSON(data);
							      		if(obj.Status=="Success"){	
							            	$('#dg').datagrid('reload');
							            	for (var i = 0; i < rows.length; i++) {
								            	 //var index = $('#dg').datagrid("getRowIndex", rows[i]);
								            	 //$('#dg').datagrid('deleteRow',index);
							            	 }
							            	 $('#dg').datagrid('reload');
							            	$.messager.alert('提示信息','添加父菜单成功');
							            	
							            }else{
							            	$.messager.alert('提示信息','添加父菜单失败');
											
							            }
							            
							          }
    							});    
                                 
                             }
                         });
		
		}else{
		
			$.messager.alert("提示信息","请您选择要添加的项!");
		}
	});

});

</script>
    
  
</head>
	<body>
		<table id="dg" data-options="toolbar:'#tb'">
		<thead>
			<tr>
				<th data-options="field:'menuName',editor:{type:'validatebox',options:{required:true}}" width="80">主菜单名</th>
			</tr>
		</thead>
       	</table>
       	<div id="tb" style="padding:5px;height:auto">
		<div style="margin-bottom:5px">
		<a href="javascript:void(0)" id="accept_a" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:true" >保存</a>
			权限名称:<input id="cc" name="dept" value="--请选择--">
		</div>
		</div>
	</body>
</html>
