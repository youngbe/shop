function this_layout(body_width)
{
    document.getElementById('head-shader').style.height=body_width*0.06+'px';
    document.getElementById('header_video').style.height=body_width*0.094+'px';
}

function real_onload()
{
    if ( get_cookies().get_value( 'user' ) == null )
    {
        alert("您尚未登陆！");
        location.replace(root_path+'login');
    }
}

function add_item()
{
    let form=document.forms['add_item'];
    const xmlHttp = new XMLHttpRequest();
    xmlHttp.open("POST",  root_path+'api/add_item');

    let form_data=new FormData();
    form_data.append("name", form["name"].value);
    form_data.append("price", form['price'].value);
    form_data.append("stock", form["stock"].value);
    {
        let index=0;
        for (let i of form["img"].files)
        {
            form_data.append("file"+index, i);
            index++;
        }
        form_data.append("files_num", index.toString());
    }



    xmlHttp.responseType="json";
    xmlHttp.send(form_data);

    xmlHttp.onreadystatechange = (e) => {
        if (xmlHttp.readyState===4)
        {
            if (xmlHttp.status===200)
            {
                if (xmlHttp.response.ret == null || xmlHttp.response.ret!==0)
                {
                    alert("创建商品失败！");
                }
                else
                {
                    alert("创建商品成功！");
                }
            }
            else
            {
                alert("创建商品失败："+xmlHttp.status);
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
                if (x===2)
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
                if (x===2)
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
            if (x===2)
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
