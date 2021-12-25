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

function document_add_manage_item(element, id)
{
    const xmlHttp = new XMLHttpRequest();
    xmlHttp.open("POST",  root_path+'api/get_item_simple');
    xmlHttp.setRequestHeader("Content-Type", "application/json");
    xmlHttp.responseType="json";
    xmlHttp.send( JSON.stringify({"id": id}));

    xmlHttp.onreadystatechange = (e) => {
        if (xmlHttp.readyState===4)
        {
            if (xmlHttp.status===200)
            {
                if (xmlHttp.response.ret == null || xmlHttp.response.ret!==0)
                {
                }
                else
                {
                    let item=document.createElement('div');
                    item.style.flexFlow="row";
                    let info=document.createElement('div');
                    info.innerHTML="商品id:"+id+';';
                    info.innerHTML+="商品名："+xmlHttp.response.name+';';
                    let button=document.createElement('a');
                    button.setAttribute("href", "javascript:void();");
                    if (xmlHttp.response.on_market === 0)
                    {
                        info.innerHTML+="商品在售;";
                        button.innerHTML="下架商品";
                    }
                    else
                    {
                        info.innerHTML+="商品已下架;";
                        button.innerHTML="上架商品";
                    }
                    button.onclick=function ()
                    {
                        const xmlHttp = new XMLHttpRequest();
                        xmlHttp.open("GET",  root_path+'api/unmarket_item?id='+id);
                        xmlHttp.setRequestHeader("Content-Type", "application/json");
                        xmlHttp.responseType="json";
                        xmlHttp.send();
                        xmlHttp.onreadystatechange = (e) => {
                            if (xmlHttp.readyState===4) {
                                if (xmlHttp.status === 200 && xmlHttp.response.ret != null && xmlHttp.response.ret === 0) {
                                    alert("商品下架成功！");
                                    location.reload();
                                }
                                else
                                {
                                    alert("商品下架失败！");
                                }
                            }
                        }
                    };

                    item.append(info);
                    item.append(button);
                    element.append(item);
                    this_layout();
                }
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

    const xmlHttp = new XMLHttpRequest();
    xmlHttp.open("GET",  root_path+'api/get_my_items');
    xmlHttp.responseType="json";
    xmlHttp.send( );

    xmlHttp.onreadystatechange = (e) => {
        if (xmlHttp.readyState===4) {
            if (xmlHttp.status === 200) {
                if (xmlHttp.response.ret == null || xmlHttp.response.ret !== 0) {
                    alert("获取商品失败!");
                } else {
                    for(let i of xmlHttp.response.items)
                    {
                        document_add_manage_item(document.getElementById('main-content'), i);
                    }
                }
            }
        }
    }
};
