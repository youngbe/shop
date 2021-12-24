function this_layout()
{
    const width_max=global_layout();
    const main_width=document.getElementById('main').clientWidth;
    let x;
    if ( (x=document.getElementById('nav')) != null )
    {
        x.style.maxWidth=width_max+'px';
    }
    if ( (x=document.getElementById('head-shader')) != null )
    {
        x.style.height=main_width*0.08+'px';
    }
    if ( (x=document.getElementById('header_video')) != null )
    {
        x.style.height=main_width*0.1061+'px';
    }
    if ( (x=document.getElementById('footer-main')) != null )
    {
        x.style.maxWidth=width_max+'px';
    }
}

window.onload=function()
{
    window.onresize=this_layout;
    if ( get_cookies().get_value( 'user' ) == null )
    {
        $('#header').load(root_path+"include/header_not_login.html", this_layout);
    }
    else
    {
        $('#header').load(root_path+"include/header_login.html", this_layout);
    }
    $('#footer').load(root_path+"include/footer.html", this_layout);
    {
        let item=document.createElement('div');
        document_add_item(document.getElementById('main'), 1);
    }
};
