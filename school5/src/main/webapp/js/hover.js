//光棒效果
var _bgColor;  //存放原始的 背景颜色
$(document).ready(function()
{   
    //所有的行鼠标移进时变色
    $("tr").hover(function ()
    {
        _bgColor = $(this)[0].style.backgroundColor;
        $(this).css({'background-color' : '#4ba9e6', 'color' : 'blue'});
    }, 
    //移除还原颜色
    function () {
    	var cssObj = {'background-color':_bgColor,'color':''}
        $(this).css(cssObj);
    });
    
    //交替行变色
    var trs =  $("table").find("tr");
    for(i = 0;i<trs.length;i++)
    {
        var obj = trs[i];
        if(i %2 == 0)
            obj.style.backgroundColor = "#f2f9ff";
    }
});