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
				//$('#ul').append("<li><a id='bt'>"+item.grade+"</a></li>");
				$('#ul').append("<li class='link' value='"+gradeId+"'><a>"+grade+"</a></li>");
			})					
		}
	});
})
</script>
<script type="text/javascript">
$(document).ready(function(){

//获取其管理的班级
/* $('#aa').combobox({    
    url:'getGradeUtilByHeadTeachernickName.html',    
    valueField:'id',    
    textField:'grade',
    editable:false  
}); */

//获取假条状态
/* $('#cc').combobox({    
    url:'getType.html',    
    valueField:'typeid',  
    //此处typeName 为假条状态  
    textField:'typeName',
    editable:false  
}); */

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
            {field:'askid',title:'请假单号',width:80},
            {field:'studentName',title:'学生姓名',width:80},
            {field:'type',title:'请假类别',width:80},
            {field:'starttime',title:'开始时间',width:80},
            {field:'endtime',title:'结束时间',width:80},
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
    $('.link').click(function(){
    	/* if($('#cc').combobox('getText')=="--请选择--"){
    		$.messager.alert('提示信息','请选择请假类型！');
			return false;
    	} */
    	var gradeId=$(this).val();
    	function getData(){
    		//获取班级value值
			//var gradeId = $('#aa').combobox('getValue'); // 取值 
    		var type="申请中";
    		var obj={"gradeId":gradeId,"type":type};
    		var str = JSON.stringify(obj);
			$.ajax({
				type:'Post',
				contentType:'application/json',
				data:str,
				url:'getStudentAsksByGradeIdandType.html',
				async :false,
				dataType:'json',
				success:function(data){			
					if(data==null){					
						$.messager.alert('提示信息','此班没有请假信息！');	 
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

//=======================================================================
//请假审批 结果1：审批通过
$(document).ready(function(){
	$('#edit_yes').click(function(){
		var rows = $('#dg').datagrid('getSelections');
		if(rows.length>0){
		 $.messager.confirm("提示信息", "你确定这"+rows.length+"条申请审批通过吗?", function (r) {
                             if (r) {
                                 
                                 var askList = [];
                                 for (var i = 0; i < rows.length; i++) {
                                   var askid=rows[i].askid;
                                   //var status=rows[i].status;
                                   var status="审批通过";
                                   var ask={"askid":askid,"status":status};
                                     askList.push(ask);
                                 }                            
                                
                                 var obj={"askList":askList};
                                 var str=JSON.stringify(obj);
                                 alert(str);
                               $.ajax({  
							        type:'Post',
							       	contentType:'application/json',
									url:'askUpdate.html',
									data: str,
							        dateType:'json',  
							        success:function(data){ 
							            var obj = jQuery.parseJSON(data);
							      		if(obj.Status=="Success"){  	
							            	$.messager.alert('提示信息','审批成功');
											//self.location.reload();  //刷新本页，失败
							            }else{
							            	$.messager.alert('提示信息','审批失败,请稍后再试。');
											
							            }
							            
							          }
    							});    
                                 
                             }
                         });
		
		}else{
		
			$.messager.alert("提示信息","请选择您要审批的假条!");
		}
	});
});

//结果2：审批不通过
$(document).ready(function(){
	$('#edit_no').click(function(){
		var rows = $('#dg').datagrid('getSelections');
		if(rows.length>0){
		 $.messager.confirm("提示信息", "你确定这"+rows.length+"条申请审批不通过吗?", function (r) {
                             if (r) {
                                 
                                 var askList = [];
                                 for (var i = 0; i < rows.length; i++) {
                                   var askid=rows[i].askid;
                                   //var status=rows[i].status;
                                   var status="审批不通过";
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
							            	$.messager.alert('提示信息','审批成功');
											//self.location.reload();  //刷新本页，失败
							            }else{
							            	$.messager.alert('提示信息','审批失败,请稍后再试。');
											
							            }
							            
							          }
    							});    
                                 
                             }
                         });
		
		}else{
		
			$.messager.alert("提示信息","请选择您要审批的假条!");
		}
	});
});
</script>
</head>
  <body>
    <table id="dg"></table>
       	<div style="margin-bottom:5px">
       	<div id="tb" style="height:auto">
       	<a href="javascript:void(0)" id="edit_yes" class="easyui-linkbutton"  data-options="iconCls:'icon-ok',plain:true" >审批通过</a>
       	<a href="javascript:void(0)" id="edit_no" class="easyui-linkbutton"  data-options="iconCls:'icon-no',plain:true" >审批不通过</a>
		<!-- <a href="javascript:void(0)" id="delete_sta" class="easyui-linkbutton"  data-options="iconCls:'icon-remove',plain:true" >同意销假</a>-->
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<nav class="nav" id="nav">
			<ul id="ul">
			<li class="current"><a href="#">班级名称</a></li>
			</ul>	
		</nav>
			<!-- 班级名称:<input id="aa" name="dept" value="--请选择--">
	 			  假条状态:<input id="cc" name="dept" value="--请选择--">
		<a href="#" id="bt" class="easyui-linkbutton" iconCls="icon-search">查询</a> -->
		</div>
	</div>
  </body>
</html>
