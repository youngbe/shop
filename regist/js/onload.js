function this_layout()
{
    const width_max=global_layout();
    const main_width=document.getElementById('main').clientWidth;
    let x;
    if ( (x=document.getElementById('nav')) != null )
    {
        x.style.maxWidth=width_max+'px';
    }
    if ( (x=document.getElementById('head-shader')) != null )
    {
        x.style.height=main_width*0.08+'px';
    }
    if ( (x=document.getElementById('header_video')) != null )
    {
        x.style.height=main_width*0.1061+'px';
    }
    if ( (x=document.getElementById('footer-main')) != null )
    {
        x.style.maxWidth=width_max+'px';
    }
}

function send()
{
    let form=document.forms['regist'];
    const xmlHttp = new XMLHttpRequest();
    xmlHttp.open("POST",  root_path+'api/regist');
    xmlHttp.setRequestHeader("Content-Type", "application/json");
    xmlHttp.responseType="json";
    xmlHttp.send(JSON.stringify(  {"nick_name": form["nick_name"].value, "password": md5(form["password"].value)} ));

    xmlHttp.onreadystatechange = (e) => {
        if (xmlHttp.readyState==4)
        {
            if (xmlHttp.status==200)
            {
                if (xmlHttp.response.ret == null || xmlHttp.response.ret!=0)
                {
                    alert("注册失败！");
                }
                else
                {
                    alert("注册成功！用户id为："+xmlHttp.response.id+"\n请记住您的id");
                    location.replace(root_path);
                }
            }
            else
            {
                alert("注册失败："+xmlHttp.status);
            }
        }
    }
}

window.onload=function()
{
    window.onresize=this_layout;
    if ( get_cookies().get_value( 'user' ) == null )
    {
        $('#header').load(root_path+"include/header_not_login.html", this_layout);
    }
    else
    {
        $('#header').load(root_path+"include/header_login.html", this_layout);
    }
    $('#footer').load(root_path+"include/footer.html", this_layout);
    if ( get_cookies().get_value( 'user' ) != null )
    {
        alert("您已经登陆！");
        location.replace(root_path);
    }
};
