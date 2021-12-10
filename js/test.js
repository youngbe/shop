function send()
{
    const xmlHttp = new XMLHttpRequest();
    xmlHttp.open("POST", 'http://192.168.79.128:8080/untitled_war_exploded/ig2');
    //xmlHttp.send( "abcd" );
    xmlHttp.send( JSON.stringify({ "email": "hello@user.com", "response": { "name": "Tester" } }) );

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
