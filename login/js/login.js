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
    let form=document.forms['login'];
    const xmlHttp = new XMLHttpRequest();
    xmlHttp.open("POST",  root+'api/login');
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
                    location.replace("..");
                }
                else if ( xmlHttp.response.ret===1 )
                {
                    alert("您已经登陆！");
                    location.replace("..");
                }
            }
            else
            {
                alert("登陆失败："+xmlHttp.status);
            }
        }
    }
}