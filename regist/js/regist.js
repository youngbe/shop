root="../"

function addScript(url){
    var script = document.createElement('script');
    script.setAttribute('type','text/javascript');
    script.setAttribute('src',url);
    document.getElementsByTagName('head')[0].appendChild(script);
}

addScript( root+'js/md5.min.js' );


function send()
{
    let form=document.forms['regist'];
    const xmlHttp = new XMLHttpRequest();
    xmlHttp.open("POST",  root+'api/regist');
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
                }
            }
            else
            {
                alert("注册失败："+xmlHttp.status);
            }
        }
    }
}
