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
}
