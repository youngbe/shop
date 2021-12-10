
temp_result=[];

document.documentElement.style.overflowY="scroll";

temp_result.push("screen.width:"+screen.width);
temp_result.push("screen.availWidth:"+screen.availWidth);
console.log("screen.width:"+screen.width);
console.log("screen.availWidth:"+screen.availWidth);

temp_result.push( "window.outerWidth"+window.outerWidth );
console.log( "window.outerWidth"+window.outerWidth);
temp_result.push( "window.innerWidth"+window.innerWidth);
console.log( "window.innerWidth"+window.innerWidth);
//console.log( "document.body.offsetWidth:"+document.body.offsetWidth );
//console.log( "document.body.clientWidth:"+document.body.clientWidth );
//console.log( "document.body.scrollWidth:"+document.body.scrollWidth );
//console.log( "document.body.style.width:"+document.body.style.width );
temp_result.push( "document.documentElement.clientWidth:"+document.documentElement.clientWidth );
temp_result.push( "document.documentElement.offsetWidth:"+document.documentElement.offsetWidth );
temp_result.push(  "document.documentElement.scrollWidth:"+document.documentElement.scrollWidth );
temp_result.push(  "document.documentElement.style.width:"+document.documentElement.style.width );
console.log( "document.documentElement.clientWidth:"+document.documentElement.clientWidth );
console.log( "document.documentElement.offsetWidth:"+document.documentElement.offsetWidth );
console.log(  "document.documentElement.scrollWidth:"+document.documentElement.scrollWidth );
console.log(  "document.documentElement.style.width:"+document.documentElement.style.width );


document.documentElement.style.overflowY="hidden";
temp_result.push( "document.documentElement.clientWidth:"+document.documentElement.clientWidth );
temp_result.push( "document.documentElement.offsetWidth:"+document.documentElement.offsetWidth );
temp_result.push(  "document.documentElement.scrollWidth:"+document.documentElement.scrollWidth );
temp_result.push(  "document.documentElement.style.width:"+document.documentElement.style.width );
console.log( "document.documentElement.clientWidth:"+document.documentElement.clientWidth );
console.log( "document.documentElement.offsetWidth:"+document.documentElement.offsetWidth );
console.log(  "document.documentElement.scrollWidth:"+document.documentElement.scrollWidth );
console.log(  "document.documentElement.style.width:"+document.documentElement.style.width );

document.documentElement.style.overflowY=null;


