var c=0;
var t2;
var t;
var timer_is_on=0;
var entrar=true;
var contador=0;
var counter=0;
var semaforo=0;
function subir()
{	
		if(contador<(-1*(src.length*76))+1)
		{
		contador=0;
		}
		document.getElementById('filmina').style.top=contador+'px';
		if(counter<76)
		{		
		counter++;
		contador-=1;
		var t1=setTimeout("subir()",20);
		}

}


function cargar()
{
if(entrar)
{
	for(i=0;i<src.length;i++)
	{
	document.getElementById('filmina').innerHTML+='<tr><td><img 					class="celda_fotos" src="'+src[i]+'"/></td></tr>';
	}
document.getElementById('filmina').innerHTML+='<tr><td><img 	class="celda_fotos" src="'+src[0]+'"/></td></tr>';
document.getElementById('filmina').innerHTML+='<tr><td><img 	class="celda_fotos" src="'+src[1]+'"/></td></tr>';
document.getElementById('filmina').innerHTML+='<tr><td><img 	class="celda_fotos" src="'+src[2]+'"/></td></tr>';
entrar=false;
}
}

function timedCount()
{semaforo++;
cargar();
document.getElementById('postTitle').innerHTML=titulos[c];
document.getElementById('postBody').innerHTML=datos[c];
document.getElementById('imagenDelMomento').src=src[c];
c=c+1;
if(c==4)
{
c=0;
}

t=setTimeout("timedCount()",15000);
counter=0;
if(semaforo>1)
{
subir();
}
}

function doTimer()
{
if (!timer_is_on)
  {
  timer_is_on=1;
  timedCount();
  }
}