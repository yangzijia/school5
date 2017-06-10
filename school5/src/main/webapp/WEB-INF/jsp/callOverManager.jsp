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
		striped:true,  
        border:true,  
        collapsible:false,//是否可折叠的       
        singleSelect:false,//是否单选 
        pagination:true,//分页控件
		pagePosition:'bottom',  
        rownumbers:true,//行号  
        //写上width属性这样才能使用fit属性时平均列宽度
        columns:[[  
            {field:'kk',checkbox:true},
            {field:'id',title:'学号',width:80} ,
            {field:'studentName',title:'学生姓名',width:80},
            {field:'studentPhone',title:'联系方式',width:80},
            {field:'nickName',title:'登录昵称',width:80},
            {field:'opt',title:'查看',
            	formatter:function(value,rec,incex){
            		var s = '<a href="studentDetail.html"  onclick="">查看</a> ';  
            		return s;
            	},width:80
            }
        ]],
        toolbar:'#tb',
        fitColumns:true
    });  
  });
$(document).ready(function() {
	var rows = [];
	$('#bt').click(function(){
	function getData(){
    	var yearClass=$('#yearClass option:selected').text();
		var profession = $('#profession option:selected').text();
		var classId = $('#classId option:selected').text();
		var student ={"yearClass":yearClass,"profession":profession,"classId":classId};
		var str = JSON.stringify(student);
			$.ajax({
				type:'Post',
				contentType:'application/json',
				data:str,
				url:'showStudent.html',
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
			if(endEditing()){
					var ss = [];
					var rows = $('#dg').datagrid('getChanges');
					if(rows.length>0){
					for(var i=0; i<rows.length; i++){
						 var name = rows[i].name;
                         var nickName = rows[i].nickName;
                         var phone = rows[i].phone;
                         var LeaderandUSer={"id":id,"name":name,"nickName":nickName,"phone":phone};
                         ss.push(LeaderandUSer);
					}
					var obj={"leaderList":ss};
					var str=JSON.stringify(obj);
					$.messager.confirm('确认','您确认想要插入'+rows.length+'条数据吗？',function(r){    
					if (r){    
						$.ajax({  
						        type:'Post',
						       	contentType:'application/json',
								url:'leaderAdd.html',
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
                                 	var leaderanduser = {"nickName":nickName,"id":id};
                                     ids.push(leaderanduser);
                                 }
                                var obj={"leaderList":ids};
                                var str=JSON.stringify(obj);
                               $.ajax({  
							        type:'Post',
							       	contentType:'application/json',
									url:'leaderDel.html',
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
                                     var name = rows1[i].name;
                                     var nickName = rows1[i].nickName;
                                     var phone = rows1[i].phone;
                                     var LeaderandUSer={"id":id,"name":name,"nickName":nickName,"phone":phone};
                                     ids.push(LeaderandUSer);
                                 }
                                var obj={"leaderList":ids};
                                var str=JSON.stringify(obj);
                               $.ajax({  
							        type:'Post',
							       	contentType:'application/json',
									url:'leaderUpdate.html',
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
                                 	var user = {"nickName":nickName};
                                     ids.push(user);
                                 }
                                var obj={"userList":ids};
                                var str=JSON.stringify(obj);
                               $.ajax({  
							        type:'Post',
							       	contentType:'application/json',
									url:'passwordRec.html',
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
//选择学院年级专业班级
$(document).ready(function(){
 	$.ajax({
                type:"Post",
                contentType:"application/json",
				url:"requestCollGra.html",
				dataType:"json",
				success:function(data){
				var obj = eval(data);
				if(obj.college.length>0){
					$("<option value='0'>--请选择--</option>")
				.appendTo("#college");
					for(var i = 0;i<(obj.college.length);i++){
					$("<option ></option>")
					.val(obj.college[i])
					.text(obj.college[i])
					.appendTo("#college");
					}
				}
				if(obj.grade.length>0){
					$("<option value='0'>--请选择--</option>")
				.appendTo("#yearClass");
				for(var i=0;i<obj.grade.length;i++){
					$("<option></option>")
					.val(obj.grade[i])
					.text(obj.grade[i])
					.appendTo("#yearClass");
					}
				}
				
			}
		});
$(document).ready(function(){
    $("#college").change(function(){
		if(($('#college option:selected').text()!="--请选择--")&& ($('#yearClass option:selected').text()!="--请选择--")&&($('#profession option:selected').text()=="")||($('#classId option:selected').text()!="")){		
		$('#classId').empty();
		var collegeName=$('#college option:selected').text();
		var yearClass=$('#yearClass option:selected').text();
		var t = {"collegeName":collegeName,"yearClass":yearClass};
		var str = JSON.stringify(t);
		$.ajax({
				type:"Post",
                contentType:"application/json",
				url:"requestProfession.html",
				dataType:"json",
				data:str,
				success:function(data){
				var obj1 = eval(data);
					if(obj1.Status=="Fail"){
						$('#profession').empty();
					}else{
						$('#profession').empty();
						if(obj1.profession.length>0){
					$("<option value='0'>--请选择--</option>")
					.appendTo("#profession");
					for(var i=0;i<obj1.profession.length;i++){
						$("<option></option>")
						.val(obj1.profession[i])
						.text(obj1.profession[i])
						.appendTo("#profession");
						}
					}
				}
					}
				});
			}else if(($('#yearClass option:selected').text()=="--请选择--")&&($('#profession option:selected').text()=="--请选择--")){
				$('#profession').empty();
			}
		});
	});
});
$(document).ready(function(){
    $("#yearClass").change(function(){
		if(($('#college option:selected').text()!="--请选择--")&&($('#college option:selected').text()!="")&& ($('#yearClass option:selected').text()!="--请选择--")&& ($('#yearClass option:selected').text()!="")){		
		$('#classId').empty();
		var collegeName=$('#college option:selected').text();
		var yearClass=$('#yearClass option:selected').text();
		var t = {"collegeName":collegeName,"yearClass":yearClass};
		var str = JSON.stringify(t);
		$.ajax({
				type:"Post",
                contentType:"application/json",
				url:"requestProfession.html",
				dataType:"json",
				data:str,
				success:function(data){
				var obj1 = eval(data);
					if(obj1.Status=="Fail"){
						$('#profession').empty();
						alert("您选择的年级不正确");
					}else{
						$('#profession').empty();
						if(obj1.profession.length>0){
					$("<option value='0'>--请选择--</option>")
					.appendTo("#profession");
					for(var i=0;i<obj1.profession.length;i++){
						$("<option></option>")
						.val(obj1.profession[i])
						.text(obj1.profession[i])
						.appendTo("#profession");
						}
					}
				}
					}
				});
			}
		});
	});

$(document).ready(function(){
	$("#profession").change(function(){
		if(($('#college option:selected').text()!="--请选择--")&&($('#college option:selected').text()!="")&&($('#yearClass option:selected').text()!="--请选择--")&&($('#yearClass option:selected').text()!="")&&($('#profession option:selected').text()!="--请选择--")&&($('#profession option:selected').text()!="")){
				var collegeName=$('#college option:selected').text();
				var yearClass=$('#yearClass option:selected').text();
				var profession=$('#profession option:selected').text();
				var t ={"collegeName":collegeName,"yearClass":yearClass,"profession":profession};
				var str = JSON.stringify(t);
		$.ajax({
				type:"Post",
                contentType:"application/json",
				url:"requestClass.html",
				dataType:"json",
				data:str,
				success:function(data){
				var obj1 = eval(data);
					if(obj1.Status=="Fail"){
						$('#classId').empty();
						alert("您选择的年级不正确");
					}else{
						$('#classId').empty();
						if(obj1.classID.length>0){
					$("<option value='0'>--请选择--</option>")
					.appendTo("#classId");
					for(var i=0;i<obj1.classID.length;i++){
						$("<option></option>")
						.val(obj1.classID[i])
						.text(obj1.classID[i])
						.appendTo("#classId");
						}
					}
				}
					}
				});
			}
		});
	});
$(document).ready(function() {
	$("#button").click(function(){
	var nickName=$("#nickname").val();
	var password=$("#password").val();
	var studentCardId=$("#studentCardId").val();
    var studentName=$("#studentName").val();
    var studentPhone=$("#studentPhone").val();
	var studentNum=$("#studentNum").val();
	var collegeName=$("#college option:selected").text();
	var profession=$("#profession option:selected").text();
	var yearClass=$("#yearClass option:selected").text();
	var classId = $("#classId option:selected").text();
	
	var t ={"studentName":studentName,
			"nickname":nickName,
			"id":studentNum,
			"studentPhone":studentPhone,
			"studentCardId":studentCardId,
			"password":password,
			"role":null,
			"grade":yearClass,
			"profession":profession,
			"classId":classId,
			"college":collegeName
		};
		var str = JSON.stringify(t);
$.ajax({  
        type:"Post",
        contentType:"application/json",
		url:"registerStudent.html",
		data:str,
        dataType:"json",  
        success:function(data){
			var obj = eval(data); 
            alert(obj.Status);
          }
        });
   	});
});

</script>
</head>
<body>
	<table id="dg" ></table>
       	<div style="margin-bottom:5px">
<div id="tb" style="height:auto">
日期:<input class="easyui-datebox" style="width:80px">		
学院: <select id="college"  style="width:80px">
 </select>
年级:<select id="yearClass"  style="width:80px">
  </select>
 专业： <select id="profession"  style="width:80px">
   </select>
 班级： <select id="classId"  style="width:80px">
   </select>
 课程名称： <select id="classId"  style="width:80px">
   </select>
课程节数:<select id="classId"  style="width:80px">
</select>
   
<a href="#" id="bt" class="easyui-linkbutton" iconCls="icon-search">查询</a>
</div>
</div>

</body>
</html>

