
<?php
	include("../conn.php");	
	if ($_POST['submit'] ) {
		$titulo = $_POST['titulo'];
		$link = $_POST['link'];
		$imagen = "imagenes/".$_FILES["file"]["name"];
		$resumen = $_POST['resumen'];
		$mostrar = ($_POST['mostrar']) ? 1 : 0;
		
		if ($_GET['idNoticia']) {
			$idNot=$_GET['idNoticia'];
			if ($_FILES["file"]["name"]) {
				mysql_query("Update Noticias Set Titulo='$titulo', Link='$link', Imagen='$imagen', Resumen='$resumen' where idNoticia=$idNot");
				move_uploaded_file($_FILES["file"]["tmp_name"], "../imagenes/".$_FILES["file"]["name"]);
			} else {
				mysql_query("Update Noticias Set Titulo='$titulo', Link='$link', Resumen='$resumen', Mostrar=$mostrar where idNoticia=$idNot");
			}
		} else {
			mysql_query("Insert into Noticias (Resumen, Titulo, Imagen, Link, Mostrar) values ('$resumen', '$titulo', '$imagen', '$link', $mostrar)");
			move_uploaded_file($_FILES["file"]["tmp_name"], "../imagenes/".$_FILES["file"]["name"]);
		}
	}
	if ($_GET['idBorrar']) {
		$idNot=$_GET['idBorrar'];
		$result = mysql_query("Select Imagen from Noticias where idNoticia=$idNot");
		$row = mysql_fetch_array($result);
		$imgFile = $row["Imagen"];
		unlink("../".$imgFile);
		mysql_query("Delete from Noticias where IdNoticia=$idNot");		
	}
?>

<table>
<?php
	include("../conn.php");	
	$result = mysql_query("Select idNoticia, Titulo, Mostrar from Noticias");
	echo "<tr> <td>Titulo</td> <td>Mostrar</td> <td>Comandos</td> </tr>\n";
	while ($row = mysql_fetch_array($result)) {
		$idNot=$row["idNoticia"];
		$titulo=$row["Titulo"];
		$mostrar=$row["Mostrar"];
		$editar="<a href=\"noticias.php?idNoticia=$idNot\">editar</a>";
		$borrar="<a href=\"noticias.php?idBorrar=$idNot\">borrar</a>";
		echo "<tr> <td>$titulo</td> <td>$mostrar</td> <td>$editar&nbsp$borrar</td> </tr>\n";
	}
?>
</table>
<br />


<?php
	if ($_GET['idNoticia']) {
		include("../conn.php");
		$idNot=$_GET['idNoticia'];
		$result = mysql_query("Select Resumen, Titulo, Imagen, Link, Mostrar from Noticias where IdNoticia=$idNot");
		if (!$result) {
			echo "Error";
		} else {
			$row=mysql_fetch_array($result);
		}
	}
?>

<?php
	function get_noticias() {
		if ($_GET['idNoticia']) {
			echo "?idNoticia=".$_GET['idNoticia'];
		}
	}
?>

<form method="post" action="noticias.php<?php get_noticias()?>" enctype="multipart/form-data">
	Titulo: <input type="text" name="titulo" value=<?php echo "'".$row["Titulo"]."'" ?> /> <br />
	Link: <input type="text" name="link" value=<?php echo "'".$row["Link"]."'" ?> /> <br />
	Imagen: <input type="file" name="file" id="file" file=<?php echo "'".$row["Imagen"]."'" ?> /> <br />
	Resumen: <textarea name="resumen"><?php echo $row["Resumen"] ?></textarea> <br />
	Mostrar: <input type="checkbox" name="mostrar" <?php if($row["Mostrar"]==1) echo "checked"; ?> />
	<br /><input type="submit" value="Enviar" name="submit" />
	<input type="button" value="Nuevo" name="nuevo" onclick='window.location="./noticias.php"' />
</form>


