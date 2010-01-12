
<?php
	$limit=4;
	echo "var datos=new Array($limit);var titulos=new Array($limit);var src=new Array($limit);var links=new Array($limit);\n";
	include("conn.php");
		$result = mysql_query("Select Resumen,Titulo,Imagen,Link from insilico.Noticias where Mostrar=1");
		if (!$result) {
    		echo "Usuario no existe";
		} else {
			$i=0;
			while ($row = mysql_fetch_array($result)) {
				echo "datos[$i]=\"".$row["Resumen"]."\";\n ";
				echo "titulos[$i]=\"".$row["Titulo"]."\";\n ";
				echo "src[$i]=\"".$row["Imagen"]."\";\n ";
				echo "links[$i]=\"".$row["Link"]."\";\n ";
				$i=$i+1;
				if ($i == $limit) break;
			} 			
		}
		mysql_close($cnn);
?>
