function is_login()
{
    if ( get_cookies().get_value( 'user' ) == null )
    {
        return false;
    }
    else
    {
        return true;
    }
}

function logout()
{
    document.cookie="user=; Max-Age=0; path=" + root_path;
    location.replace(root_path);
}
