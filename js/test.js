function send()
{
    const xmlHttp = new XMLHttpRequest();
    xmlHttp.open("POST", 'api/regist');
    xmlHttp.setRequestHeader("Content-Type", "application/json");
    xmlHttp.send( JSON.stringify({"nick_name": "sdfdsfsd", "password": "asdasd"}
) );

    xmlHttp.onreadystatechange = (e) => {
        if (xmlHttp.readyState==4)
        {
            if (xmlHttp.status==200)
            {
                console.log(xmlHttp.responseText);
            }
            else
            {
                alert("Problem retrieving XML data");
            }
        }
    }
}
