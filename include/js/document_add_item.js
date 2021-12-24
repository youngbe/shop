function document_add_item(element, id)
{
    const xmlHttp = new XMLHttpRequest();
    xmlHttp.open("POST",  root_path+'api/get_item_simple');
    xmlHttp.setRequestHeader("Content-Type", "application/json");
    xmlHttp.responseType="json";
    xmlHttp.send( JSON.stringify({"id": id}));

    xmlHttp.onreadystatechange = (e) => {
        if (xmlHttp.readyState===4)
        {
            if (xmlHttp.status===200)
            {
                if (xmlHttp.response.ret == null || xmlHttp.response.ret!==0)
                {
                }
                else
                {



                    let item=document.createElement('div');
                    //item.style.flexFlow='row wrap';
                    //item.style.alignContent='flex-start';


                    let img=document.createElement('img');
                    img.src=root_path+'api/get_file?id='+xmlHttp.response.cover;
                    //img.style.width="100%";
                    img.style.aspectRatio='16/9';
                    img.style.objectFit='cover';
                    img.classList.add("radius");
                    img.setAttribute("radius", "width 0.03");




                    let placeholder=document.createElement('div');
                    //placeholder.style.width='100%';
                    placeholder.style.height='3.6%';


                    //let title_left_placeholder=document.createElement('div');
                    //title_left_placeholder.style.width='5%';



                    let text=document.createElement('div');
                    //text.style.width='100%';
                    text.style.flexGrow='1';
                    text.style.display='grid';
                    text.style.gridTemplateColumns='3% 70% 24% auto';
                    text.style.gridTemplateRows='min-content auto';
                    text.style.alignItems='center';
                    text.style.placeContent='stretch';


                    let title=document.createElement('div');
                    title.style.gridArea='1 / 2 / 2 / 4';
                    title.style.display='-webkit-box';
                    title.style.webkitBoxOrient='vertical';
                    title.style.webkitLineClamp=2;
                    title.style.overflow= 'hidden';
                    title.style.wordWrap='break-word';
                    title.style.lineHeight=1.2;
                    title.classList.add("font_size");
                    title.setAttribute("font_size", "0.0085");
                    title.innerHTML=xmlHttp.response.name;
                    //this_layout();
                    //parseFloat(title.style.fontSize)*1.1*2+'px';
                    //title.style.height=parseFloat(title.style.fontSize)*1.1*2+'px';
                    //title.style.whiteSpace='normal';
                    //title.style.wordBreak='break-all';
                    //title.style.textOverflow='ellipsis';
                    //title.style.whiteSpace='pre';
                    /*{
                        let canvas=document.createElement('canvas').getContext('2d');
                        canvas.font='mono 48px serif';
                        console.log(title.font);
                        console.log(canvas.measureText('123').width);
                    }*/
                    //let title_right_placeholder=document.createElement('div');
                    //title_right_placeholder.style.flexGrow='1';
                    //let releaser_left_placeholder=document.createElement('div');
                    //releaser_left_placeholder.style.width="2%";
                    //let info=document.createElement('div');
                    //info.style.flexFlow="row";
                    //info.style.flexGrow='1';
                    //info.style.alignItems= "center";

                    let releaser=document.createElement('div');
                    releaser.style.gridArea='2 / 2 / 3 / 3';
                    releaser.style.display='inline-block';
                    releaser.style.textOverflow='ellipsis';
                    releaser.style.overflow='hidden';
                    releaser.style.whiteSpace='nowrap';
                    releaser.classList.add("font_size");
                    releaser.setAttribute("font_size", "0.007");
                    releaser.innerHTML=xmlHttp.response.releaser;

                    let stock=document.createElement('div');
                    stock.style.gridArea='2 / 3 / 3 / 4';
                    stock.style.display='inline-block';
                    stock.style.textOverflow='ellipsis';
                    stock.style.overflow='hidden';
                    stock.style.whiteSpace='nowrap';
                    stock.classList.add("font_size");
                    stock.setAttribute("font_size", "0.007");
                    stock.innerHTML=xmlHttp.response.stock;



                    item.append(img);
                    item.append(placeholder);
                    //item.append(title_left_placeholder);
                    text.append(title);
                    text.append(releaser);
                    text.append(stock);
                    //item.append(title_right_placeholder);
                    //item.append(releaser_left_placeholder);
                    //text.append(title);
                    //info.append(releaser);
                    //info.append(stock);
                    //text.append(info);
                    item.append(text);
                    element.append(item);

                    this_layout();
                }
            }
        }
    }

}
