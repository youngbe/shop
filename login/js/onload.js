function this_layout(body_width)
{
    document.getElementById('head-shader').style.height=body_width*0.06+'px';
    document.getElementById('header_video').src=root_path+'src/header.webm';
}

function real_onload()
{
    if ( get_cookies().get_value( 'user' ) != null )
    {
        alert("您已经登陆！");
        location.replace(root_path);
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
            }
            else
            {
                alert("登陆失败："+xmlHttp.status);
            }
        }
    }
}

window.onload=function()
{
    let x=0;
    if ( get_cookies().get_value( 'user' ) == null )
    {
        $('#header').load(root_path+"include/header_not_login.html",
            function()
            {
                ++x;
                if (x==2)
                {
                    this_layout(global_layout());
                    window.onresize=function()
                    {
                        this_layout(global_layout());
                    };
                    real_onload();
                }
            }
        );
    }
    else
    {
        $('#header').load(root_path+"include/header_login.html",
            function()
            {
                ++x;
                if (x==2)
                {
                    this_layout(global_layout());
                    window.onresize=function()
                    {
                        this_layout(global_layout());
                    };
                    real_onload();
                }
            }
        );
    }
    $('#footer').load(root_path+"include/footer.html",
        function()
        {
            ++x;
            if (x==2)
            {
                this_layout(global_layout());
                window.onresize=function()
                {
                    this_layout(global_layout());
                };
                real_onload();
            }
        }
    );
};
