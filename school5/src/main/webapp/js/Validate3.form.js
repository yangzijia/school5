$.extend(validatePrompt, {
    teacherName:{
        onFocus:"2-20位字符，可由中文或英文组成",
        succeed:"",
        isNull:"请输入联系人姓名",
        error:{
            badLength:"联系人姓名长度只能在2-20位字符之间",
            badFormat:"联系人姓名只能由中文或英文组成"
        }
    },
    department:{
        onFocus:"",
        succeed:"",
        isNull:"请选择联系人所在部门",
        error:""
    },
    tel:{
        onFocus:"请填写联系人常用的电话，以便于我们联系，如：0527-88105500",
        succeed:"",
        isNull:"请输入联系人固定电话",
        error:"电话格式错误，请重新输入"
    },
    teacherPhone:{
        onFocus:"请输入手机号",
        succeed:"",
        isNull:"请输入您的手机号码",
        error:"手机号格式错误，请重新输入"
    },
 teacherCardId:{
        onFocus:"请输入身份证号码",
        succeed:"",
        isNull:"请输入您的身份证号码",
        error:"身份证号码格式错误，请重新输入"
    },
    companyname:{
        onFocus:"请填写工商局注册的全称。4-40位字符，可由中英文、数字及“_”、“-”、()、（）组成",
        succeed:"",
        isNull:"请输入公司名称",
        error:{
            badLength:"公司名称长度只能在4-40位字符之间",
            badFormat:"公司名称只能由中文、英文、数字及“_”、“-”、()、（）组成"
        }
    },
    companyarea:{
        onFocus:"请选择公司所在地",
        succeed:"",
        isNull:"请选择公司所在地",
        error:""
    },
    companyaddr:{
        onFocus:"请详细填写公司经营地址　如：北京市海淀区苏州街20号银丰大厦B座3层",
        succeed:"",
        isNull:"请输入公司地址",
        error:{
            badLength:"公司地址长度只能在4-50位字符之间",
            badFormat:"公司地址只能由中文、英文、数字及“_”、“-”、()、（）、#组成"
        }
    },
    purpose:{
        onFocus:"",
        succeed:"",
        isNull:"请选择购买类型/用途",
        error:""
    },
    companysite:{
        onFocus:"如：http://www.360buy.com",
        succeed:"",
        isNull:"请输入公司网址",
        error:{
            badLength:"公司网址长度只能在80位字符之内",
            badFormat:"公司网址格式不正确。应如：http://www.360buy.com"
        }
    }
});

$.extend(validateFunction, {
    teacherName:function(option) {
        
        var format = validateRules.isteacherName(option.value);
        if (!length) {
            validateSettings.error.run(option, option.prompts.error.badLength);
        } else {
            if (!format) {
                validateSettings.error.run(option, option.prompts.error.badFormat);
            }
            else {
                validateSettings.succeed.run(option);
            }
        }
    },
    department:function(option) {
        var bool = (option.value == -1);
        if (bool) {
            validateSettings.isNull.run(option, "");
        }
        else {
            validateSettings.succeed.run(option);
        }
    },
    tel:function(option) {
        var format = validateRules.isTel(option.value);
        if (!format) {
            validateSettings.error.run(option, option.prompts.error);
        }
        else {
            validateSettings.succeed.run(option);
        }
    },
    teacherPhone:function(option) {
        var format = validateRules.isteacherPhone(option.value);
        if (!format) {
            validateSettings.error.run(option, option.prompts.error);
        }
        else {
            validateSettings.succeed.run(option);
        }
    },

teacherCardId:function(option) {
        var format = validateRules.isteacherCardId(option.value);
        if (!format) {
            validateSettings.error.run(option, option.prompts.error);
        }
        else {
            validateSettings.succeed.run(option);
        }
    },
    companyname:function(option) {
        var length = validateRules.betweenLength(option.value.replace(/[^\x00-\xff]/g, "**"), 4, 40);
        var format = validateRules.isCompanyname(option.value);
        if (!length) {
            validateSettings.error.run(option, option.prompts.error.badLength);
        }
        else {
            if (!format) {
                validateSettings.error.run(option, option.prompts.error.badFormat);
            } else {
                validateSettings.succeed.run(option);
            }
        }
    },
    companyarea:function(option) {
        var bool = (option.value == -1);
        if (bool) {
            validateSettings.isNull.run(option, "");
        }
        else {
            validateSettings.succeed.run(option);
        }
    },
    companyaddr:function(option) {
        var length = validateRules.betweenLength(option.value.replace(/[^\x00-\xff]/g, "**"), 4, 50);
        var format = validateRules.isCompanyaddr(option.value);
        if (!length) {
            validateSettings.error.run(option, option.prompts.error.badLength);
        } else {
            if (!format) {
                validateSettings.error.run(option, option.prompts.error.badFormat);
            }
            else {
                validateSettings.succeed.run(option);
            }
        }
    },
    purpose:function(option) {
        var purpose = $("input:checkbox[@name='purposetype']");
        if (validateFunction.checkGroup(purpose)) {
            validateSettings.succeed.run(option);
        } else {
            validateSettings.error.run(option, option.prompts.isNull);
        }
    },
    companysite:function(option) {
        var length = validateRules.betweenLength(option.value, 0, 80);
        var format = validateRules.isCompanysite(option.value);
        if (!length) {
            validateSettings.error.run(option, option.prompts.error.badLength);
        } else {
            if (!format) {
                validateSettings.error.run(option, option.prompts.error.badFormat);
            }
            else {
                validateSettings.succeed.run(option);
            }
        }
    },
    FORM_validate:function() {
        $("#nickName").jdValidate(validatePrompt.nickName, validateFunction.nickName, true);
		 $("#oldPassword").jdValidate(validatePrompt.oldPassword, validateFunction.oldPassword, true);
        $("#password").jdValidate(validatePrompt.password, validateFunction.password, true);
        $("#againpassword").jdValidate(validatePrompt.againpassword, validateFunction.againpassword, true);
        $("#authcode").jdValidate(validatePrompt.authcode, validateFunction.authcode, true);
        $("#teacherName").jdValidate(validatePrompt.teacherName, validateFunction.teacherName, true);
        $("#department").jdValidate(validatePrompt.department, validateFunction.department, true);
        $("#tel").jdValidate(validatePrompt.tel, validateFunction.tel, true);
        $("#teacherPhone").jdValidate(validatePrompt.teacherPhone,validateFunction.teacherPhone,true);
$("#teacherCardId").jdValidate(validatePrompt.teacherCardId,validateFunction.teacherCardId,true);
        $("#mail").jdValidate(validatePrompt.mail, validateFunction.mail, true);
        $("#companyname").jdValidate(validatePrompt.companyname, validateFunction.companyname, true);
        $("#companyaddr").jdValidate(validatePrompt.companyaddr, validateFunction.companyaddr, true);
        $("#companysite").jdValidate(validatePrompt.companysite,validateFunction.companysite,true);
        $("#purpose").jdValidate(validatePrompt.purpose, validateFunction.purpose, true);
        return validateFunction.FORM_submit(["#nickName","#password","#againpassword","#mail","#teacherName","#department","#tel","#companyname","#companyaddr","#purpose"]);
    }
});




//默认下用户名框获得焦点
setTimeout(function() {
    $("#nickName").get(0).focus();
}, 0);
//用户名验证
$("#nickName").jdValidate(validatePrompt.nickName, validateFunction.nickName);

$("#oldPassword").jdValidate(validatePrompt.oldPassword, validateFunction.oldPassword);

//密码验证
$("#password").bind("keyup",function(){
	validateFunction.passwordstrength();
}).jdValidate(validatePrompt.password, validateFunction.password)
//二次密码验证
$("#againpassword").jdValidate(validatePrompt.againpassword, validateFunction.againpassword);
//邮箱验证
$("#mail").jdValidate(validatePrompt.mail, validateFunction.mail);
//推荐人用户名
$("#referrer").bind("keydown",function(){
	$(this).css({"color":"#333333","font-size":"14px"});
}).bind("keyup",function(){
	if($(this).val() == "" || $(this).val() == "可不填"){
		$(this).css({ "color": "#999999", "font-size": "12px" });
	}
}).bind("blur",function(){
	if($(this).val() == "" || $(this).val() == "可不填"){
		$(this).css({"color":"#999999","font-size":"12px"}).jdValidate(validatePrompt.referrer, validateFunction.referrer, "可不填");
	}
})
//验证码验证
$("#authcode").jdValidate(validatePrompt.authcode, validateFunction.authcode);
//联系人姓名验证
$("#teacherName").jdValidate(validatePrompt.teacherName, validateFunction.teacherName);
//部门验证
$("#department").jdValidate(validatePrompt.department, validateFunction.department);
//固定电话验证
$("#tel").jdValidate(validatePrompt.tel, validateFunction.tel);
//手机验证

$("#teacherPhone").jdValidate(validatePrompt.teacherPhone, validateFunction.teacherPhone);

$("#teacherCardId").jdValidate(validatePrompt.teacherCardId, validateFunction.teacherCardId);
//公司名称验证
$("#companyname").jdValidate(validatePrompt.companyname, validateFunction.companyname);
//公司地址验证
$("#companyaddr").jdValidate(validatePrompt.companyaddr, validateFunction.companyaddr);
//公司网址验证
$("#companysite").jdValidate(validatePrompt.companysite, validateFunction.companysite);
//显示密码事件
$("#viewpassword").bind("click", function() {
    if ($(this).attr("checked") == true) {
        validateFunction.showPassword("text");
        $("#o-password").addClass("passwordbg");
    } else {
        validateFunction.showPassword("password");
        $("#o-password").removeClass("passwordbg");
    }
});
//购买类型/用途验证
$("input:checkbox[@name='purposetype']").bind("click", function() {
    var value1 = $("#purpose").val();
    var value2 = $(this).val();
    if ($(this).attr("checked") == true) {
        if (value1.indexOf(value2) == -1) {
            $("#purpose").val(value1 + value2);
            $("#purpose").attr("sta", 2);
            $("#purpose_error").html("");
            $("#purpose_succeed").addClass("succeed");
        }
    } else {
        if (value1.indexOf(value2) != -1) {
            value1 = value1.replace(value2, "");
            $("#purpose").val(value1);
            if ($("#purpose").val() == "") {
                $("#purpose").attr("sta", 0);
                $("#purpose_succeed").removeClass("succeed");
            }
        }
    }
});
//键盘输入验证码验证
$("#authcode").bind('keyup', function(event) {
    if (event.keyCode == 13) {
        $("#button").click();
    }
});
//确认协议才能提交
$("#protocol").click(function() {
    if ($("#protocol").attr("checked") != true) {
        $("#button").attr({ "disabled": "disabled" });
		$("#button").addClass("disabled");
    }
    else {
        $("#button").removeAttr("disabled");
		$("#button").removeClass("disabled");
    }
});
//表单提交验证和服务器请求
$("#button").click(function() {
    var flag = validateFunction.FORM_validate();
	var checked = []; 
	$('input:checkbox:checked').each(function() { 
	var a = $(this).val();
	alert(a);
 	checked.push(a); 
 	}); 
 	alert(checked); 
	
	
	var nickName=$("#nickName").val();
	var password=$("#password").val();
	var teacherCardId=$("#teacherCardId").val();
        var teacherName=$("#teacherName").val();
        var teacherPhone=$("#teacherPhone").val();
        var collegeName=$("#type option:selected").text();
	 var t = {
				 "nickName":nickName,
				 "password":password,
				 "role":null,
				 "teacherCardId":teacherCardId,
				 "headteacherName":teacherName,
				 "teacherPhone":teacherPhone,
				 "collegeName":collegeName,
				 "grade":checked};
				var str = JSON.stringify(t);
				alert(str);
	
    if (flag) {
        $(this).attr({"disabled":"disabled"}).attr({"value":"提交中,请稍等"});
        $.ajax({
            type:'Post',
       contentType:'application/json',
		url:'HeadteacherRegister.html',
		data: str,
        dateType:'json',  
        success:function(data){  
            //alert("名字:" + data.userName + "密码:" + data.password+"角色"+data.role);
           var obj = jQuery.parseJSON(data); 
            alert(obj.Status);

            }
        });
    }
});