function is_login()
{
    return get_cookies().get_value('user') != null;
}

function logout()
{
    document.cookie="user=; Max-Age=0; path=" + root_path;
    if ( root_path !== '/' )
    {
        document.cookie="user=; Max-Age=0; path=" + root_path.substr(0, root_path.length-1);
    }
    location.replace(root_path);
}