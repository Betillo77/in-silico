
<?php
	if ($_POST['submit'] ) {
		$titulo = $_POST['titulo'];
		$link = $_POST['link'];
		$imagen = "imagenes/".$_FILES["file"]["name"];
		$resumen = $_POST['resumen'];
		
		include("../conn.php");		
		if ($_GET['idNoticia']) {
			$idNot=$_GET['idNoticia'];
			if ($_FILES["file"]["name"]) {
				mysql_query("Update Noticias Set Titulo='$titulo', Link='$link', Imagen='$imagen', Resumen='$resumen' where idNoticia=$idNot");
				move_uploaded_file($_FILES["file"]["tmp_name"], "../imagenes/".$_FILES["file"]["name"]);
			} else {
				mysql_query("Update Noticias Set Titulo='$titulo', Link='$link', Resumen='$resumen' where idNoticia=$idNot");
			}
		} else {
			mysql_query("Insert into Noticias (Resumen, Titulo, Imagen, Link, Mostrar) values ('$resumen', '$titulo', '$imagen', '$link', 1)");
			move_uploaded_file($_FILES["file"]["tmp_name"], "../imagenes/".$_FILES["file"]["name"]);
		}
	}
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
	<input type="submit" value="Enviar" name="submit" />
</form>


