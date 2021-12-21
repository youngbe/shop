root='.';


function get_current_path()
{
    let path=location.pathname;
    return path.substring(0, path.lastIndexOf('/')+1);
}
function get_root_path( cur, root )
{
    if ( root.length === 0 )
    {
        return cur;
    }
    let path_list;
    if ( root[0] === '/' )
    {
        path_list=new Array();
    }
    else
    {
        path_list=cur.split('/');
        path_list.pop();
        path_list.shift();
    }
    for (let i of root.split('/').filter(
        function (item)
        {
            return item !== '' && item !== '.';
        }
    ))
    {
        if (i === '..')
        {
            path_list.pop();
        }
        else
        {
            path_list.push(i);
        }
    }
    let ret='/';
    for (let i of path_list)
    {
        ret+=i+'/';
    }
    return ret;
}
const current_path=get_current_path();
const root_path=get_root_path(current_path, root);
/*include_list=[];

function include(src)
{
    if ( include_list.includes(src) )
    {
        return;
    }
    let script = document.createElement('script');
    script.setAttribute('type','text/javascript');
    script.setAttribute('src', root_path+'js/include/'+src);
    document.head.appendChild(script);
    include_list.push(src);
}

console.log(current_path);
console.log(root_path);

include( 'md5.min.js' );
//include( 'cookie.js' );
//fetchInject([
//  '/js/include/cookie.js'
//]);
$.getScript("js/include/cookie.js", function()
    {
        console.log("asda");
        new Cookies();
    });
console.log("fff");
new Cookies();
include( 'login.js' );*/
