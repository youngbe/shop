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
    let form=document.forms['login'];
    const xmlHttp = new XMLHttpRequest();
    xmlHttp.open("POST",  root_path+'api/login');
    xmlHttp.setRequestHeader("Content-Type", "application/json");
    xmlHttp.responseType="json";
    xmlHttp.send(JSON.stringify(  {"id": form["id"].value, "password": md5(form["password"].value)} ));

    xmlHttp.onreadystatechange = (e) => {
        if (xmlHttp.readyState===4)
        {
            if (xmlHttp.status===200)
            {
                if (xmlHttp.response.ret == null)
                {
                    alert("登陆失败！");
                }
                else if (xmlHttp.response.ret===0)
                {
                    alert("登陆成功！");
                    location.replace(root_path);
                }
                else if ( xmlHttp.response.ret===1 )
                {
                    alert("您已经登陆！");
                    location.replace(root_path);
                }
                else
                {
                    alert("登陆失败：错误码："+xmlHttp.response.ret);
                }
            }
            else
            {
                alert("登陆失败：http error:"+xmlHttp.status);
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
