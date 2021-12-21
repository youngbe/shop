function global_layout()
{
    let width_max;
    if(/Android|webOS|iPhone|iPod|BlackBerry/i.test(navigator.userAgent))
    {
        document.documentElement.style.width=screen.width*devicePixelRatio+"px";
        width_max=screen.height*devicePixelRatio*2.2;
    }
    else
    {
        width_max=screen.height*2.2;
    }
    document.getElementById('nav').style.maxWidth=document.getElementById('footer-main').style.maxWidth=width_max+'px';
    document.getElementById('main').style.maxWidth=width_max*0.96+'px';
    let body_width=document.body.clientWidth;
    document.body.style.fontSize=body_width*0.01+'px';

    for (let i of document.getElementsByClassName('radius') )
    {
        let x=i.getAttribute('radius').split(' ');
        if ( x[0] == "width" )
        {
            i.style.borderRadius=i.parentElement.clientWidth*x[1]+'px';
        }
        else
        {
            i.style.borderRadius=i.parentElement.clientHeight*x[1]+'px';
        }
    }
    for (let i of document.getElementsByClassName('fix_src_root') )
    {
        i.src=root_path+i.getAttribute("src");
    }
    return body_width;
}
