<!DOCTYPE html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>
<link rel="stylesheet" type="text/css"
	href="jqueryeasyui/themes/default/easyui.css" />
<link rel="stylesheet" type="text/css"
	href="jqueryeasyui/themes/icon.css" />
<link rel="stylesheet" type="text/css" href="jqueryeasyui/demo/demo.css" />
<script type="text/javascript" src="jqueryeasyui/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="jqueryeasyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="js/jquery.json-2.4.js"></script>
<script type="text/javascript" src="js/json2.js"></script>
<script type="text/javascript" src="jqueryeasyui/easyui-lang-zh_CN.js"></script>


</head>
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
            {field:'id',checkbox:true},
            {field:'nickName',title:'昵称',width:80} ,
           
            {field:'roleName',title:'角色',width:80},
           
        ]],
    });  
  });
$(document).ready(function() {
	var rows = [];
    $('#bt').click(function(){ 
    	if($('#cc').searchbox){
    		var m = $('#cc').searchbox('getValue');
    		if(m==""){
    			$.messager.alert('提示信息','请填写昵称');
				return false;
			}
    	}
    	function getData(){
    	
    		var nickName=$('#cc').searchbox('getValue');
    		var obj={"nickName":nickName};
    		var str = JSON.stringify(obj);
			$.ajax({
				type:'Post',
				contentType:'application/json',
				data:str,
				url:'getnickName.html',
				async :false,
				dataType:'json',
				success:function(data){
				
				if(data.Status=="NotHaveUser"){
				
					alert("该昵称不存在！");
				}else if(data.Status=="CannotAnalyzeData"){
					alert("数据无法解析！");
				}else if(data.Status=="Fail"){
					alert("未查询到该昵称的权限！");
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
	$('#delete_a').click(function(){
		var rows = $('#dg').datagrid('getSelections');
		if(rows.length>0){
		if(rows.length>1){
		alert("请选择一个");
		}else{
		var nickName = rows[0].nickName;
        var id = rows[0].id;
        var teahceruser = {"nickName":nickName,"id":id};
		
        var str=JSON.stringify(teahceruser);
                            
        $.ajax({  
		type:'Post',
		contentType:'application/json',
		url:'getAllRole.html',
		data: str,
		dateType:'json',  
		success:function(data){ 
		var obj = jQuery.parseJSON(data);
		$("#Data").empty();//先清空tbody
		var strHTML = "";
		$.each(obj,function(InfoIndex,Info){//遍历json里的数据
				
		if(Info["isHave"]=='yes'){
			      
			  strHTML += "<tr>";
			  strHTML +="<td>"+"<input type='checkbox' name='role' value='"+Info["roleId"]+"' CHECKED> "+"</td>" ;
			       
			  strHTML += "<td>"+Info["roleId"]+"</td>";
			  strHTML += "<td>"+Info["roleName"]+"</td>";
			  strHTML += "<td>"+Info["roleDiscribe"]+"</td>";
			  strHTML += "</tr>";
					
	    }else{
			 
			  strHTML += "<tr>";
			  strHTML +="<td>"+"<input type='checkbox' name='role' value='"+Info["roleId"]+"'>"+"</td>" ;
			  strHTML += "<td>"+Info["roleId"]+"</td>";
		      strHTML += "<td>"+Info["roleName"]+"</td>";
			  strHTML += "<td>"+Info["roleDiscribe"]+"</td>";
			  strHTML += "</tr>";
					
			}
	    });
		
		$("#Data").html(strHTML);//显示到tbody中	
				
				//鼠标滑过变色
				$("tr").bind("mouseover",function(){ 
                $(this).css("background-color","#FFEC8B"); 
                                                   }); 
                $("tr").bind("mouseout",function(){ 
                $(this).css("background-color","#ffffff"); 
                }); 
                
                //对话框的初始化
                $(function(){

					$("#dialog1").dialog({
					autoOpen:false,//该选项默认是true，设置为false则需要事件触发才能弹出对话框
					title:'角色信息列表',//对话框的标题
					width:400,//默认是300
					modal:true,//设置为模态对话			
				});		
			
				});
                
		        //打开弹窗
			  	$("#dialog1").dialog('open');
			  					      		
				}
    		  });    
                                                                            		
		}}else{
		
			$.messager.alert("提示信息","请您选择要修改的项!");
		}
	});

});
</script>
<script type="text/javascript">
  //对话框取消事件
  $(document).ready(function(){
	$('#button2').click(function(){	 
			$("#dialog1").dialog('close');
		});
		
	//对话框确定事件
	$('#button1').click(function(){	 
            var chk_value =[]; 
            $('input[name="role"]:checked').each(function(){ 
            chk_value.push($(this).val()); 
            }); 
            if(chk_value.length==0){
            alert("你还没有选择任何内容！"); 
            }
            else{
            var rows = $('#dg').datagrid('getSelections');
            var nickName = rows[0].nickName;
            var roles = {"nickName":nickName,"roles":chk_value};
            var str = JSON.stringify(roles);
            
            	$.ajax({  
		        type:"Post",
		        contentType:"application/json",
				url:"updateUserRole.html",
				data:str,
		        dateType:"json",  
		        success:function(data){
		        	var obj = jQuery.parseJSON(data);
		        	if(obj.Status=="Success"){
		        		$.messager.alert('提示信息','角色修改成功!');
		        		$("#dialog1").dialog('close');
		        	}else{
		        	   $.messager.alert('提示信息','角色修改失败!');
		        	}
		       	}
		     });
            }
		});
	});
</script>

<body>
	<table id="dg"></table>
	<div style="margin-bottom:5px">
		<div id="tb" style="height:auto">
			 <a
				href="javascript:void(0)" id="delete_a" class="easyui-linkbutton"
				data-options="iconCls:'icon-remove',plain:true">修改角色</a> <a
				href="javascript:void(0)" id="edit_rec" class="easyui-linkbutton"
				data-options="iconCls:'icon-undo',plain:true">撤销编辑</a> 
				 <!--  学院名称:<input
				id="cc" name="dept" value="--请选择--"> <a href="#" id="bt"
				class="easyui-linkbutton" iconCls="icon-search">查询</a>-->
				
         <input id="cc" name="menu" class="easyui-searchbox"  
         data-options="prompt:'Please Input Value',searcher:''" style="width:130px;vertical-align:middle;"></input>
         <a href="#" id="bt" class="easyui-linkbutton" iconCls="icon-search">搜索</a>
        
      
		</div>
		<div id="dialog1">
		<center>
		<table width="100%">
		<thead><tr><td width="15%">选择</td><td width="15%">ID</td><td width="35%">角色</td><td width="35%">角色描述</td></tr></thead>
		<tbody id="Data"></tbody>
		<tr><td></td><td><input type="button" id="button1" name="button1" value="确定"><td><input type="button" id="button2" name="button2" value="取消"></td><td></td></tr>
		</table>
		</center>
		</div>
</body>
</html>
