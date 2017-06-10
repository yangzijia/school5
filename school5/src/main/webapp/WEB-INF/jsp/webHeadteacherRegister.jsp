<!DOCTYPE html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>河北北方学院预警系统</title>

 <link rel="stylesheet" type="text/css" href="jqueryeasyui/themes/default/easyui.css"/>
		       <link rel="stylesheet" type="text/css" href="jqueryeasyui/themes/icon.css"/>
		       <link rel="stylesheet" type="text/css" href="jqueryeasyui/demo/demo.css"/>
		       <link  rel="stylesheet"  type="text/css"  href="css/styleB.css">
               <link  rel="stylesheet"  type="text/css"  href="css/webHeadteacherRegister.css">
		       
		       <script type="text/javascript" src="jqueryeasyui/jquery-1.7.2.min.js"></script>
		       <script type="text/javascript" src="jqueryeasyui/jquery.easyui.min.js"></script>
		       <script type="text/javascript" src="js/jquery.json-2.4.js"></script>
		       <script type="text/javascript" src="js/json2.js"></script>
		       <script type="text/javascript" src="js/jquery.validate.js" ></script>
				<script type="text/javascript" src="js/jquery.validate.messages_cn.js"></script>
		       <script type="text/javascript" src="js/jqueryeasyui/easyui-lang-zh_CN.js"></script>
 <script type="text/javascript">
	$(document).ready(function() {	
				/*昵称验证*/
				$('#nickName').blur(function(){
				var nickNamelen=$('#nickName').val().length;
				//var usern = /^[a-zA-Z0-9_]{1,}$/;
				if(nickNamelen<6 || nickNamelen>12){
					$('#nickName1').text("昵称长度在6-12个字符之间");
					/*$('#nickName').next(['label']).text("昵称长度在3-6个字符之间");*/
					$('#button').attr('disabled','true');
				}else{
					$('#nickName1').text("");
					$('#button').removeAttr("disabled");
				}
			});
			/*密码验证*/	
			$('#password').blur(function(){
				var passlen=$('#password').val().length;
				if(passlen<6 || passlen>12){
					$('#password1').text("密码长度在6-12个字符之间");
					$('#button').attr('disabled','true');
				}else{
					$('#password1').text("");
					$('#button').removeAttr("disabled");
				}
			});	
			/*确认密码验证*/
				$('#againpassword').blur(function(){
				var againpasslen=$('#againpassword').val().length;
				var passval=$("#password").val();
				var apassval=$("#againpassword").val();
				
				if(againpasslen<6||againpasslen>12){
					$('#againpassword1').text("密码长度在6-12个字符之间");
				$('#button').attr('disabled','true');	
				}else if(passval!=apassval){
					$('#againpassword1').text("两次输入的密码不一致");
					$('#button').attr('disabled','true');
				}else{
					$('#againpassword1').text("");
					$('#button').removeAttr("disabled");
				}
			});	

			/*身份证验证*/
			
			$("#teacherCardId").blur(function(){
             var teacherCardId=$("#teacherCardId").val()
             isIDCard2=/(^\d{15}$)|(^\d{18}$)|(^\d{17})(\d|X|x)$/.test(teacherCardId);
             var year = teacherCardId. substr(6,2);
             var month = teacherCardId. substr(10,2);
             var day = teacherCardId. substr(12,2);
			 
             if(teacherCardId==""){
				 $('#teacherCardId1').text("身份证号码不能为空!");
 				$('#button').attr('disabled','true');
			 }else if(isIDCard2==false||month>12||day>31||year>20||year<19){
             $('#teacherCardId1').text("身份证格式不正确");
 				$('#button').attr('disabled','true');
             }else{
			  $('#teacherCardId1').text("");
			  $('#button').removeAttr("disabled");
			}

});			
			/*姓名验证*/
			
			$('#studentName').blur(function(){
				var teacherNamelen=$('#studentName').val();
				//var usern = /^[a-zA-Z0-9_]{1,}$/;
				if(teacherNamelen<2 || teacherNamelen>4){
					$('#studentName1').text("姓名长度在3-6个字符之间");
					$('#button').attr('disabled','true');
				}else{
					$('#studentName1').text("");
					$('#button').removeAttr("disabled");
				}
			});
			
			
			/*手机号验证*/
			
			$('#teacherPhone').blur(function(){
				var teacherPhone=$('#teacherPhone').val();
			    var mobile = /^1[3|4|5|8][0-9]\d{8}$/;   
               // var tel = /^\d{3,4}-?\d{7,8}$/;   
                      
				if(teacherPhone==""){
					$('#teacherPhone1').text("手机号不能为空");
					$('#button').attr('disabled','true');
				} else if(teacherPhone.match(mobile)){
					
					$('#teacherPhone1').text("");
					$('#button').removeAttr("disabled");				
				}else{
					$('#teacherPhone1').text("手机号格式不正确");
					}
			});
			
					
		});

</script>
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
	
/*$(document).ready(function(){
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
					var rows = $('#dg').datagrid('getChanges');
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

}); */

</script>


<!--webheadteacherGegister.html-->

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
 	
	

	var nickName=$("#nickName").val();
	alert(nickName);
	alert(checked);
	var password=$("#password").val();
	var teacherCardId=$("#teacherCardId").val();
    var teacherName=$("#studentName").val();
    var teacherPhone=$("#teacherPhone").val();
    var collegeName=$('#cc').combobox('getText');
    alert(collegeName);
	var t = {
				 "nickname":nickName,
				 "password":password,
				 "role":"Role_HeadTeacher",
				 "teacherCardId":teacherCardId,
				 "headTeacherName":teacherName,
				 "teacherPhone":teacherPhone,
				 "collegeName":collegeName,
				 "grade":checked};
				var str = JSON.stringify(t);
			
				
	$.ajax({  
        type:'Post',
        contentType:'application/json',
		url:'registerHeadteacher.html',
		data: str,
        dateType:'json',  
        success:function(data){  
            var obj = jQuery.parseJSON(data); 
            alert(obj.Status);
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
  <div  id="head">        
      </div> 
   
   <div  id="wrapper">  
 
 

    <center>
   <form id="form" >
         <div  id="login">    <span class="login_txt_bt"  >辅导员注册</span>   </div><br/>
          <p>
    	<span ><img src="images/nc.png" width="120" height="24"></span><em class="red">*</em>
        <input  type="text"  id="nickName"   name="nickName1"  size="25"/>
	    <br/>
        <label  id="nickName1" class="label_txt"></label>
    		</p>
          

        <p>
    	<!--<span for="password"   class="form_txt">密&nbsp;&nbsp;码</span><em class="red">*<em>-->
		<span ><img src="images/mm.png" width="120" height="24"></span><em class="red">*</em>
         <input type="password" id="password"  name="password1"   size="25"/>
		 <br/>
            <label  id="password1"   class="label_txt"></label>
    	</p>
       

	   

        <p>
    	<span ><img src="images/qrmm.png" width="120" height="24"></span><em class="red">*</em>
        <input type="password" id="againpassword"  name="confirm_password1"   size="25"    />
		<br/>
         <label  id="againpassword1" class="label_txt"></label>
    	</p>

		<p>
    	<span ><img src="images/cid.png" width="120" height="24"></span><em class="red">*</em>
        <input type="text"  id="teacherCardId"   name="shenfenzheng"  size="25"/>
		<br/>
		 <label  id="teacherCardId1" class="label_txt"></label>
    	</p>


        <p>
    	<span ><img src="images/rn.png" width="120" height="24"></span><em class="red">*</em>
        <input  type="text" id="studentName"  name="studentName"   size="25"/>
		<br/>
		<label   id="studentName1" class="label_txt"></label>
    	</p>

         <p>
    	<span ><img src="images/sjhm.png" width="120" height="24"></span><em class="red">*</em>
         <input type="text" id="teacherPhone"  name="teacherPhone" size="25"/>
		 <br/>
		 <label id="teacherPhone1" class="label_txt"></label>
         </p>
           
		   <table id="dg"  style="width:342px;height:150px;margin-top:6px;"></table>
       	<div style="margin-bottom:5px;">
       	<div id="tb" style="height:auto">
		

		学院名称:<input id="cc" name="dept" value="--请选择--">
		<a href="#" id="bt" class="easyui-linkbutton" iconCls="icon-search">查询</a>
	</div>
		
        </div>
	    
	<input type="button" id="button" value="提交" />	
   <input type="reset" id="res" value="重置"/>
  </form>
    </center>
     </div>  
    
 
  

      <div  id="footer">
          <div  id="footer-title"    class="login-buttom-txt">版权所有&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;    河北北方学院信息技术研究所         </div>
       
      </div>
 </body>
</html>


<body>
</body>
</html>
