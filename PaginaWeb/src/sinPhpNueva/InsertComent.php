<?PHP
	$nombre=$_POST["nombre"];
	$email=$_POST["email"];
	$fecha=date("d/m/Y"); 
	$comentario=$_POST["comentario"];
	include("conn.php");
	$result = mysql_query("INSERT INTO comentarios (nombre, email, fecha, comentario) VALUES ('".$nombre."', '".$email."', '".$fecha."', '".$comentario."') ") or die(mysql_error());
	
?>