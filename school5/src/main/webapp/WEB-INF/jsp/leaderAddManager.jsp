<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
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
        $('#dg').datagrid({
         width:$(document).width(),
        height:$(document).height(),
		autoRowHeight:false,
		fit:true,
		fitColumns:true,  
        striped:true,  
        border:true,  
        collapsible:false,//是否可折叠的       
        singleSelect:false,//是否单选 
        pagination:true,//分页控件
		pagePosition:'bottom',  
        rownumbers:true,//行号  
        toolbar:'#tb',
        frozenColumns:[[  
            {field:'id',checkbox:true},  
        ]]
    });  
  });
$(document).ready(function() {
	var rows = [];
    	function getData(){
			$.ajax({
				type:'Post',
				contentType:'application/json',
				url:'showLeader.html',
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
			if(endEditing()){
					var ss = [];
					var rows = $('#dg').datagrid('getChanges');
					if(rows.length>0){
					for(var i=0; i<rows.length; i++){
						 var name = rows[i].name;
                         var nickName = rows[i].nickName;
                         var phone = rows[i].phone;
                         var LeaderandUSer={"name":name,"nickName":nickName,"phone":phone};
                         ss.push(LeaderandUSer);
					}
					//var obj={"leaderList":ss};
					//var str=JSON.stringify(obj);
					var str=JSON.stringify(ss);
					$.messager.confirm('确认','您确认想要插入'+rows.length+'条数据吗？',function(r){    
					if (r){    
						$.ajax({  
						        type:'Post',
						       	contentType:'application/json',
								url:'saveLeader.html',
								data: str,
						        dateType:'json',  
						        success:function(data){  
						            var obj = jQuery.parseJSON(data);
						            if(obj.Status=="Success"){	
						            	$.messager.alert('提示信息','插入成功初始密码为123456');
						            	$('#dg').datagrid('acceptChanges');
						            }else if(obj.Status=="NicknameCodedError"){
						            	$.messager.alert('提示信息','昵称格式不正确由_、中文、英文字母组成');
						            	$('#dg').datagrid('rejectChanges');
										editIndex = undefined;
						            }else if(obj.Status=="PhoneError"){
						            	$.messager.alert('提示信息','电话号码格式不正确');
						            	$('#dg').datagrid('rejectChanges');
										editIndex = undefined;
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
});
</script>
  </head>
  <body style="overflow:hidden;">
 	<table id="dg">   

		<thead>
			<tr>
            	<th data-options="field:'name',editor:{type:'validatebox',options:{required:true}}" width="80">领导姓名</th>
            	<th data-options="field:'phone',editor:{type:'validatebox',options:{required:true}}" width="80">联系方式</th>
            	<th data-options="field:'nickName',editor:{type:'validatebox',options:{required:true}}" width="80">登录昵称</th>
			</tr>
		</thead>
       	</table>
       	<div style="margin-bottom:5px">
       	<div id="tb" style="height:auto">
		<a href="javascript:void(0)" id="append_a" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" >添加</a>
		<a href="javascript:void(0)" id="accept_a" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:true" >保存</a>
		
	</div>
</div>
</html>
