root='..';


function get_current_path()
{
    let temp=location.pathname.split('/').filter(
        function (item)
        {
            return item !== '';
        }
    );
    let ret='/';
    for (let i of temp)
    {
        ret+=i+'/';
    }
    return ret;
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
