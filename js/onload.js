function this_layout(body_width)
{
    document.getElementById('head-shader').style.height=body_width*0.06+'px';
    document.getElementById('header_video').style.height=body_width*0.094+'px';
}

function real_onload()
{
}

window.onload=function()
{
    let x=0;
    if ( get_cookies().get_value( 'user' ) == null )
    {
        $('#header').load(root_path+"include/header_not_login.html",
            function()
            {
                ++x;
                if (x==2)
                {
                    this_layout(global_layout());
                    window.onresize=function()
                    {
                        this_layout(global_layout());
                    };
                    real_onload();
                }
            }
        );
    }
    else
    {
        $('#header').load(root_path+"include/header_login.html",
            function()
            {
                ++x;
                if (x==2)
                {
                    this_layout(global_layout());
                    window.onresize=function()
                    {
                        this_layout(global_layout());
                    };
                    real_onload();
                }
            }
        );
    }
    $('#footer').load(root_path+"include/footer.html",
        function()
        {
            ++x;
            if (x==2)
            {
                this_layout(global_layout());
                window.onresize=function()
                {
                    this_layout(global_layout());
                };
                real_onload();
            }
        }
    );
};
