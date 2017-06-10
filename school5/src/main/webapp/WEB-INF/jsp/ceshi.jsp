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
</head>
<body>
	<h2>DataGrid Complex Toolbar</h2>
	<div class="demo-info">
		<div class="demo-tip icon-tip"></div>
		<div>The DataGrid toolbar can be defined from a &lt;div/&gt; markup, so you can define the layout of toolbar easily.</div>
	</div>
	<div style="margin:10px 0;"></div>
	<table class="easyui-datagrid" title="DataGrid Complex Toolbar" style="width:900px;height:250px"
			data-options="rownumbers:true,singleSelect:true,toolbar:'#tb'">
		<thead>
			<tr>
				<th data-options="field:'itemid',width:80">Item ID</th>
				<th data-options="field:'productid',width:100">Product</th>
				<th data-options="field:'listprice',width:80,align:'right'">List Price</th>
				<th data-options="field:'unitcost',width:80,align:'right'">Unit Cost</th>
				<th data-options="field:'attr1',width:240">Attribute</th>
				<th data-options="field:'status',width:60,align:'center'">Status</th>
			</tr>
		</thead>
	</table>
	<div id="tb" style="padding:5px;height:auto">
		<div style="margin-bottom:5px">
			<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true">添加</a>
		
			<a href="#" class="easyui-linkbutton" iconCls="icon-save" plain="true">保存</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-cut" plain="true">剪切</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true">删除</a>
			<a href="#" id="edit_a" class="easyui-linkbutton" iconCls="icon-edit" plain="true" >修改</a>
			Date From: <input class="easyui-datebox" style="width:80px">
			To: <input class="easyui-datebox" style="width:80px">
			父菜单名称: 
			<select class="easyui-combobox" panelHeight="auto" style="width:100px">
				<option value="java">Java</option>
				<option value="c">C</option>
				<option value="basic">Basic</option>
				<option value="perl">Perl</option>
				<option value="python">Python</option>
			</select>
			<a href="#" class="easyui-linkbutton" iconCls="icon-search">查询</a>
		</div>
	</div>
</body>
</html>
