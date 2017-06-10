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
           {field:'askid',checkbox:true},
            {field:'type',title:'请假类别',width:80},
            {field:'timestart',title:'开始时间',width:80},
            {field:'timeend',title:'结束时间',width:80},
            {field:'status',title:'假条状态',width:80},
            {field:'opt',title:'详情',
            	formatter:function(value,row,incex){
            	
            		var s = '<a href="studentAskDetail2.html?askid='+row.askid+'">详细信息</a> ';  
            		return s;
            	},width:80
            }
        ]],
    });  
  });

//条件查询请假信息
$(document).ready(function() {
	var rows = [];
    //$('#c').click(function(){  
    $(".a").live("click",function(){   	
    	var text =$(this).text();
    	function getData(){
    		var obj={"type":text};
    		var str = JSON.stringify(obj);
			$.ajax({
				type:'Post',
				contentType:'application/json',
				data:str,
				url:'getStudentAskBynickNameAndtype.html',
				async :false,
				dataType:'json',
				success:function(data){			
					if(data==null){
						
						$.messager.alert('提示信息','没有此类型的请假信息！');	 
					}
						rows = data;				
				}
			});
				return rows;
		}
		//=========================================================================================	
		//分页方法	
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

//=========================================================================================
//对假条进行操作  1 取消申请,只能取消申请中或审批不通过的假条。   
$(document).ready(function(){
	$('#delete_a').click(function(){ 
		var rows = $('#dg').datagrid('getSelections');
		if(rows.length>0){
		 $.messager.confirm("提示信息", "你确定要取消这"+rows.length+"条申请吗?", function (r) {
                             if (r) {
                                 var ids = [];
                                 var status;
                                 for (var i = 0; i < rows.length; i++) {
                                     ids.push(rows[i].askid); 
                                     status = rows[0].status;
                                 }
                             if(status=="申请中"||status=="审批不通过"){
                                var obj={"askids":ids};
                                var str=JSON.stringify(obj);
                               $.ajax({  
							        type:'Post',
							       	contentType:'application/json',
									url:'askDel.html',
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
							            	$.messager.alert('提示信息','取消成功');
							            	
							            }else{
							            	$.messager.alert('提示信息','取消失败,请稍后再试。');
											
							            }
							            
							          }
    							});    
                                
                             	}else{
                     			$.messager.alert("提示信息","请选择正在申请中或审批不通过的假条!");
                     			}  
                               
                             }
                         });
		
			}else{
				$.messager.alert("提示信息","请选择您要取消申请的假条!");
			}
		
	});

});

// 2 销假 
$(document).ready(function(){
	$('#edit_sta').click(function(){
		var rows = $('#dg').datagrid('getSelections');
		if(rows.length>0){
		 $.messager.confirm("提示信息", "你确定要将这"+rows.length+"条申请销假吗?", function (r) {
                             if (r) {                               
                                 var askList = [];
                                 for (var i = 0; i < rows.length; i++) {
                                     var status=rows[0].status;                                 
                                   }  
                             if(status=="审批通过"){
                                 for (var i = 0; i < rows.length; i++) {
                                   var askid=rows[i].askid;
                                   //var status=rows[i].status;
                                   var status="已销假";
                                   var ask={"askid":askid,"status":status};
                                     askList.push(ask);
                                 }                            
                                
                                 var obj={"askList":askList};
                                 var str=JSON.stringify(obj);
                                 
                               $.ajax({  
							        type:'Post',
							       	contentType:'application/json',
									url:'askUpdate.html',
									data: str,
							        dateType:'json',  
							        success:function(data){ 
							            var obj = jQuery.parseJSON(data);
							      		if(obj.Status=="Success"){  	
							            	$.messager.alert('提示信息','销假成功');
											//self.location.reload();  //刷新本页
							            }else{
							            	$.messager.alert('提示信息','销假失败,请稍后再试。');
											//self.location.reload();  //刷新本页
							            }
							            
							          }
    							});    
                                 
                             	}else{
                         		
                     			$.messager.alert("提示信息","请选择审批通过的假条进行销假!");
                     			} 
                               
                             }
                         });
		
			}else{	
				$.messager.alert("提示信息","请选择您要销假的假条!");
			}
		
	
	});

});
</script>
    
  
</head>
	<body>
    <table id="dg"></table>
       	<div style="margin-bottom:5px">
       	<div id="tb" style="height:auto">
        <a href="javascript:void(0)" id="delete_a" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" >取消申请</a>
		<a href="javascript:void(0)" id="edit_sta" class="easyui-linkbutton"  data-options="iconCls:'icon-edit',plain:true" >销假</a>
		<!--  <button id="delete_a" data-options="iconCls:'icon-remove',plain:true">取消申请</button>
		<button id="edit_sta" data-options="iconCls:'icon-edit',plain:true">销假</button>-->
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<!--  假条状态:<input id="cc" name="dept" value="--请选择--" > -->
		<a href="#" id="a" name="dept" class="easyui-linkbutton a">申请中</a>&nbsp;&nbsp;&nbsp;&nbsp;
		<a href="#" id="b" name="dept" class="easyui-linkbutton a">审批通过</a>&nbsp;&nbsp;&nbsp;&nbsp;
		<a href="#" id="c" name="dept" class="easyui-linkbutton a">审批不通过</a>&nbsp;&nbsp;&nbsp;&nbsp;
		<a href="#" id="d" name="dept" class="easyui-linkbutton a">已销假</a>
		<!--  <a href="#" id="bt" class="easyui-linkbutton" iconCls="icon-search">查询</a>-->	
		</div>
	</div>
  </body>
</html>
