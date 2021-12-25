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

function add_item()
{
    let form=document.forms['add_item'];
    const xmlHttp = new XMLHttpRequest();
    xmlHttp.open("POST",  root_path+'api/add_item');

    let form_data=new FormData();
    form_data.append("name", form["name"].value);
    form_data.append("price", form['price'].value);
    form_data.append("stock", form["stock"].value);
    form_data.append("cover", form['cover'].files[0]);
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
    if ( get_cookies().get_value( 'user' ) == null )
    {
        alert("您尚未登陆！");
        location.replace(root_path+'login');
    }
};
