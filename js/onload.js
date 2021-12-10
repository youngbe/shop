function layout()
{
    let width_max;
    if(/Android|webOS|iPhone|iPod|BlackBerry/i.test(navigator.userAgent))
    {
        document.documentElement.style.width=screen.width*devicePixelRatio+"px";
        width_max=screen.height*devicePixelRatio*16/9;
    }
    else
    {
        width_max=screen.height*16/9;
    }
    document.getElementById('nav').style.maxWidth=document.getElementById('footer-main').style.maxWidth=width_max+'px';
    document.getElementById('main').style.maxWidth=width_max*0.96+'px';
    let body_width=document.body.clientWidth;
    document.body.style.fontSize=body_width*0.01+'px';
    document.getElementById('head-shader').style.height=body_width*0.06+'px';











    document.getElementById('main').innerHTML="screen.width:"+screen.width+"<br>";
    document.getElementById('main').innerHTML+="screen.availWidth:"+screen.availWidth+"<br>";

    document.getElementById('main').innerHTML+= "window.outerWidth"+window.outerWidth+"<br>";
    // 重要
    document.getElementById('main').innerHTML+= "window.innerWidth"+window.innerWidth+"<br>";
    document.getElementById('main').innerHTML+= "document.body.offsetWidth:"+document.body.offsetWidth +"<br>";
    document.getElementById('main').innerHTML+= "document.body.clientWidth:"+document.body.clientWidth +"<br>";
    document.getElementById('main').innerHTML+= "document.body.scrollWidth:"+document.body.scrollWidth +"<br>";
    document.getElementById('main').innerHTML+= "document.body.style.width:"+document.body.style.width +"<br>";
    // 重要
    document.getElementById('main').innerHTML+= "document.documentElement.clientWidth:"+document.documentElement.clientWidth +"<br>";
    document.getElementById('main').innerHTML+= "document.documentElement.offsetWidth:"+document.documentElement.offsetWidth +"<br>";
    document.getElementById('main').innerHTML+=  "document.documentElement.scrollWidth:"+document.documentElement.scrollWidth +"<br>";
    document.getElementById('main').innerHTML+=  "document.documentElement.style.width:"+document.documentElement.style.width +"<br>";
    document.getElementById('main').innerHTML+="window.devicePixelRatio" + window.devicePixelRatio;
}


window.onload=function()
{
    layout();
    window.onresize=layout;
};

