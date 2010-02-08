function left(entrada)
{
if(actual==0)
{
actual=experiencia.length-1;
empezar(actual);
}
else{actual--;empezar(actual);}
}
function rigth(actual)
{
if(actual==experiencia.length-1)
{
actual=0;
empezar(actual);
}
else{actual++;empezar(actual);}


}

function empezar(integrante)
{
	if(experiencia.length>0)
	{
	document.getElementById('intextrecpub').innerHTML="";
	document.getElementById("fotofea").src=fotoFea[integrante];
	document.getElementById("loading").innerHTML="CARGANDO...";
	window.scrollBy(0,200);
	var temp="Sujeto: "+sujeto[integrante];
	texto(temp,0,1,0,integrante);
	
	
	}


}

function texto(elemento,indice1,indice2,indice3,integrante)
{
document.getElementById('perfiles').scrollTop = document.getElementById('perfiles').scrollHeight;
if(indice2<elemento.length+1)
{
var temp=elemento.substring(indice1,indice2);
var entrar=false;
var temporal_index1=indice1+1;
var temporal_index2=indice2+1;
if(temp=="<")
	{var temp2="";
	temporal_index1=indice1;
	temporal_index2=indice2;
	entrar=true;
		while(temp2!=">")
		{
		temporal_index1=temporal_index1+1;
		temporal_index2=temporal_index2+1;
		temp2=elemento.substring(temporal_index1,temporal_index2);
		}
		temp=elemento.substring(indice1,temporal_index2);
		temporal_index1=temporal_index1+1;
		temporal_index2=temporal_index2+1;		
	}
document.getElementById('intextrecpub').innerHTML+=temp;
var element=elemento;
var index=temporal_index1;
var index2=temporal_index2;

var index3=indice3;
var t=setTimeout('texto("'+element+'",'+index+","+index2+","+index3+","+integrante+")",20);
}
else{var index3=indice3;
	index3++;
	document.getElementById('intextrecpub').innerHTML+="<br />";
	document.getElementById('intextrecpub').innerHTML+="<br />";

	switch(index3)
	{
	case 1:var temp="Intereses: "+intereses[integrante];
		   texto(temp,0,1,index3,integrante);
		   break;
	case 2:var temp="Experiencia: "+experiencia[integrante];
		   texto(temp,0,1,index3,integrante);
		   break;
	case 3:var temp="<dl><dt>Publicaciones: </dt>";
			for(i=0;i<links[integrante].length;i++)
			{
			temp+="<dd>"+publicaciones[integrante][i]+"</dd>";
			}
			temp+="</dl>";
			texto(temp,0,1,index3,integrante);
			break;
	case 4: var temp="<dl><dt>Links: </dt>";
			for(i=0;i<links[integrante].length;i++)
			{
			temp+="<dd>"+"<a href='"+links[integrante][i]+"'>"+links[integrante][i]+"</a></dd>";
			}
			temp+="</dl>";
			texto(temp,0,1,index3,integrante);
			break;
	default:	
			document.getElementById("loading").innerHTML="LISTO!";
			break;
	}
	}
	}


