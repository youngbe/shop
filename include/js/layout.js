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
    document.getElementById('main').style.maxWidth=width_max*0.96+'px';
    const main_width=document.getElementById('main').clientWidth;

    for (let i of document.getElementsByClassName('radius') )
    {
        let x=i.getAttribute('radius').split(' ');
        if ( x[0] === "width" )
        {
            i.style.borderRadius=i.parentElement.clientWidth*x[1]+'px';
        }
        else
        {
            i.style.borderRadius=i.parentElement.clientHeight*x[1]+'px';
        }
    }
    for (let i of document.getElementsByClassName('set_root') )
    {
        for (let i2 of i.getAttribute("set_root_items").split(' '))
        {
            i.setAttribute(i2, root_path+i.getAttribute(i2));
        }
        i.classList.remove("set_root");
    }
    for ( let i of document.getElementsByClassName('item_board') )
    {
        i.style.gridTemplateColumns="repeat(auto-fill, "+main_width*0.16+"px)";
        i.style.gridTemplateRows="repeat(auto-fill, "+main_width*0.131+"px)";
        i.style.gridRowGap=main_width*0.015+"px";
        i.style.gridColumnGap=main_width*0.007+"px";
        i.style.gridAutoRows=main_width*0.16+"px";
    }
    for ( let i of document.getElementsByClassName('font_size') )
    {
        i.style.fontSize=parseFloat(i.getAttribute("font_size"))*main_width+'px';
    }
    return width_max;
}
