<!DOCTYPE html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>课程表</title>
<link rel="stylesheet" type="text/css" href="jqueryeasyui/themes/default/easyui.css"/>
<link rel="stylesheet" type="text/css" href="jqueryeasyui/themes/icon.css"/>
<link rel="stylesheet" type="text/css" href="jqueryeasyui/demo/demo.css"/>
<script src="https://code.jquery.com/jquery-1.11.2.js"></script>
<script type="text/javascript" src="js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="js/jquery.json-2.4.js"></script>
<script type="text/javascript" src="js/json2.js"></script>
<script type="text/javascript" src="js/jQuery.easyui.js"></script>
<script type="text/javascript" src="jqueryeasyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="jqueryeasyui/easyui-lang-zh_CN.js"></script>
<style type="text/css">
.item{
	text-align:center;
	border:1px solid #499B33;
	background:#fafafa;
	width:100px;
}

.right{
	float:center;
	width:80%;
}
.right table{
	background:#E0ECFF;
	width:100%;
	height:100%;
}
.right td.title{
	font-family:"微软雅黑";
	font-size:16px;
}
.right td{
	text-align:center;
	padding-left:5px;/*修改过*/
	padding-top:15px;/*修改过*/
	background:#E0ECFF;
}
.right td.drop{
	background:#fafafa;
	width:100px;
}
.assigned{
	border:1px solid #BC2A4D;
}
.right td.over{
	background:FBEC88;
}
.right td.time{
	background:#FBEC88;
	width:100px;
	font-family:"微软雅黑";
	font-size:15px;/*修改过*/
}

.caption{
	font-family:"楷体";
	font-size:24px;
}


#tooltipdiv {
 position: absolute;
 border: 1px solid #333;
 background: #f7f5d1;
 padding: 10px 10px 10px 10px;
 color: #333;
 display: none;
}

.divmatnrdesc{
   width:150px;overflow:hidden;
   white-space:nowrap;
   text-overflow:ellipsis;
   
   font-size: 15px;/*修改过*/
   font-family:"微软雅黑";
}
</style>

<script type="text/javascript">
$(document).ready(function(){
	$.ajax({
		type:'Post',
		url:'getCourse.html',
		ansyc:'false',
		dataType:'json',
		success:function(data){
		
			//$('#mon_onetwo').val(data.mon_onetwo); input赋值方法
			var a = data.mon_onetwo;  $('#mon_onetwo').html(a);	 // 同 $('.divmatnrdesc').html(a);					
			var b = data.tue_onetwo;  $('#tue_onetwo').html(b);			
			var c = data.wed_onetwo;  $('#wed_onetwo').html(c);
			var d = data.thu_onetwo;  $('#thu_onetwo').html(d);
			var e = data.fri_onetwo;  $('#fri_onetwo').html(e);
			var f = data.sat_onetwo;  $('#sat_onetwo').html(f);
			//添加的
			var ff = data.sun_onetwo;  $('#sun_onetwo').html(ff);
			
			var g = data.mon_threefour;  $('#mon_threefour').html(g);
			var h = data.tue_threefour;  $('#tue_threefour').html(h);
			var i = data.wed_threefour;  $('#wed_threefour').html(i);
			var j = data.thu_threefour;  $('#thu_threefour').html(j);
			var k = data.fri_threefour;  $('#fri_threefour').html(k);
			var l = data.sat_threefour;  $('#sat_threefour').html(l);
			//添加的
			var ll = data.sun_threefour;  $('#sun_threefour').html(ll);
			
			var m = data.mon_fivesix;  $('#mon_fivesix').html(m);
			var n = data.tue_fivesix;  $('#tue_fivesix').html(n);
			var o = data.wed_fivesix;  $('#wed_fivesix').html(o);
			var p = data.thu_fivesix;  $('#thu_fivesix').html(p);
			var q = data.fri_fivesix;  $('#fri_fivesix').html(q);
			var r = data.sat_fivesix;  $('#sat_fivesix').html(r);
			//添加的
			var rr = data.sun_fivesix;  $('#sun_fivesix').html(rr);
			
			var s = data.mon_seveneight;  $('#mon_seveneight').html(s);
			var t = data.tue_seveneight;  $('#tue_seveneight').html(t);
			var u = data.wed_seveneight;  $('#wed_seveneight').html(u);
			var v = data.thu_seveneight;  $('#thu_seveneight').html(v);
			var w = data.fri_seveneight;  $('#fri_seveneight').html(w);
			var x = data.sat_seveneight;  $('#sat_seveneight').html(x);
			//添加的
			var xxx = data.sun_seveneight;  $('#sun_seveneight').html(xxx);
			
			//修改过的
			var ss = data.mon_nineten;  $('#mon_nineten').html(ss);
			var tt = data.tue_nineten;  $('#tue_nineten').html(tt);
			var uu = data.wed_nineten;  $('#wed_nineten').html(uu);
			var vv = data.thu_nineten;  $('#thu_nineten').html(vv);
			var ww = data.fri_nineten;  $('#fri_nineten').html(ww);
			var xx = data.sat_nineten;  $('#sat_nineten').html(xx);
			//添加的
			var xxxx = data.sun_nineten;  $('#sun_nineten').html(xxxx);
					
		}
	});
});



	$('div.divmatnrdesc').live('mouseover', function(e) {
		   _text=$(this).text();	   
		   //_text=$("#id").attr('value'); 
		     
		   _tooltip = "<div id='tooltipdiv'><font style='font-family:微软简中圆;font-weight:bold;font-size:14px;'> "+_text+"</font></div>";  
		      $("body").append(_tooltip);  
		      $("#tooltipdiv").show();
		   $("#tooltipdiv")
		   .css({
		   "top": (e.pageY+10) + "px",
		   "left":  (e.pageX +10) + "px"
		  }).show("fast");    
		 });
		 
		 $('div.divmatnrdesc').live('mouseout', function(e) {
		  $("#tooltipdiv").remove();
		 });
		 
		 
		 $('div.divmatnrdesc').live('mousemove', function(e) {
		  $("#tooltipdiv")
		  .css({
		   "top": (e.pageY+10) + "px",
		   "left":  (e.pageX+10)  + "px"
		  }).show();    
		 });

</script>
</head>

<body>
<div class="right">  
   <table onmouseover="changeto()" onmouseout="changeback()">  
   <caption class="caption">课程表</caption>
       <tr>  
          <td class="title">时间</td>  
          <td class="title">星期一</td>  
          <td class="title">星期二</td>  
          <td class="title">星期三</td>  
          <td class="title">星期四</td>  
          <td class="title">星期五</td>  
          <td class="title">星期六</td>
          <td class="title">星期日</td>  
        </tr>  
        <tr>  
        
          <td class="time">08:00—09:50</td>           
          <!-- <textarea rows="3" cols="20" id="mon_onetwo" name="mon_onetwo" readonly="readonly" />这样写无法正常显示 -->        
          <td class="drop"><div class='divmatnrdesc' id="mon_onetwo"></div></td>    
          <!--<input type="text" class="textarea"  name="mon_onetwo" readonly="readonly" />-->
                    
          <td class="drop"><div class='divmatnrdesc' id="tue_onetwo"></div></td>   
          <td class="drop"><div class='divmatnrdesc' id="wed_onetwo"></div></td>  
          <td class="drop"><div class='divmatnrdesc' id="thu_onetwo"></div></td>  
          <td class="drop"><div class='divmatnrdesc' id="fri_onetwo"></div></td>  
          <td class="drop"><div class='divmatnrdesc' id="sat_onetwo"></div></td>
          <td class="drop"><div class='divmatnrdesc' id="sun_onetwo"></div></td>      
       </tr>  
       <tr>  
          <td class="time">10:00—11:50</td>  
          <td class="drop"><div class='divmatnrdesc' id="mon_threefour"></div></td>  
          <td class="drop"><div class='divmatnrdesc' id="tue_threefour"></div></td>  
          <td class="drop"><div class='divmatnrdesc' id="wed_threefour"></div></td>  
          <td class="drop"><div class='divmatnrdesc' id="thu_threefour"></div></td>  
          <td class="drop"><div class='divmatnrdesc' id="fri_threefour"></div></td>  
          <td class="drop"><div class='divmatnrdesc' id="sat_threefour"></div></td>
          <td class="drop"><div class='divmatnrdesc' id="sun_threefour"></div></td>  
       </tr>
       <tr>  
          <td class="time">14:40—16:30</td>  
          <td class="drop"><div class='divmatnrdesc' id="mon_fivesix"></div></td>  
          <td class="drop"><div class='divmatnrdesc' id="tue_fivesix"></div></td>  
          <td class="drop"><div class='divmatnrdesc' id="wed_fivesix"></div></td>  
          <td class="drop"><div class='divmatnrdesc' id="thu_fivesix"></div></td>  
          <td class="drop"><div class='divmatnrdesc' id="fri_fivesix"></div></td> 
          <td class="drop"><div class='divmatnrdesc' id="sat_fivesix"></div></td>
          <td class="drop"><div class='divmatnrdesc' id="sun_fivesix"></div></td>   
       </tr> 
       <tr>  
          <td class="time">16:40—18:30</td>  
          <td class="drop"><div class='divmatnrdesc' id="mon_seveneight"></div></td>  
          <td class="drop"><div class='divmatnrdesc' id="tue_seveneight"></div></td>  
          <td class="drop"><div class='divmatnrdesc' id="wed_seveneight"></div></td>  
          <td class="drop"><div class='divmatnrdesc' id="thu_seveneight"></div></td>  
          <td class="drop"><div class='divmatnrdesc' id="fri_seveneight"></div></td>  
          <td class="drop"><div class='divmatnrdesc' id="sat_seveneight"></div></td>
          <td class="drop"><div class='divmatnrdesc' id="sun_seveneight"></div></td>  
       </tr>    
        <tr>  
          <td class="time">18:40—20:30</td>  
          <td class="drop"><div class='divmatnrdesc' id="mon_nineten"></div></td>  
          <td class="drop"><div class='divmatnrdesc' id="tue_nineten"></div></td>  
          <td class="drop"><div class='divmatnrdesc' id="wed_nineten"></div></td>  
          <td class="drop"><div class='divmatnrdesc' id="thu_nineten"></div></td>  
          <td class="drop"><div class='divmatnrdesc' id="fri_nineten"></div></td>  
          <td class="drop"><div class='divmatnrdesc' id="sat_nineten"></div></td> 
          <td class="drop"><div class='divmatnrdesc' id="sun_nineten"></div></td>  
           
       </tr>    
   </table>  
</div>  
</body>
</html>
