class Cookie
{
    constructor(key, value)
    {
        this.key = key;
        this.value = value;
    }
}

class Cookies
{
    constructor()
    {
    }
    get_value(key_str)
    {
        for (let cookie of this.cookies)
        {
            if ( cookie.key===key_str )
            {
                return cookie.value;
            }
        }
    }
    cookies=new Array();
}

function get_cookies()
{
    let x= new Cookies();
    for(let cookie of document.cookie.split(';'))
    {
        x.cookies.push(new Cookie( cookie.split('=')[0].replace(' ', ''), cookie.split('=')[1] ));
    }
    return x;
}
