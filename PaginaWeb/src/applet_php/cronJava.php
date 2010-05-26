
<?php

/* 
 * Archivo con los comandos correspondientes a el cronograma para
 * ser accesado a traves del applet de Java.
 */

	include("../privado/logic/cron.php");	
	include("../util.php");
	$command = $_POST['command'];
		
	if ($command == "insert") {
		$idProyecto = $_POST['idProyecto'];
		$concepto = $_POST['concepto'];
		$padre = $_POST['padre'];
		$tiempo = $_POST['tiempo'];
		$prereq = $_POST['prereq'];
		$resp = $_POST['resp'];
		$estado = $_POST['estado'];
		if (cronAdd($idProyecto, $concepto, $padre, $tiempo, $prereq, $resp, $estado))
			echo "success\n";
		else
			echo "failure\n";
	} elseif ($command == "update") {
		$idCron = $_POST['id'];
		$concepto = $_POST['concepto'];
		$padre = $_POST['padre'];
		$tiempo = $_POST['tiempo'];
		$prereq = $_POST['prereq'];
		$resp = $_POST['resp'];
		$estado = $_POST['estado'];
		if (cronUpdate($idCron, $concepto, $padre, $tiempo, $prereq, $resp, $estado))
			echo "success\n";
		else
			echo "failure\n";
	} elseif ($command == "delete") {
		$idCron = $_POST['id'];
		if (cronRem($idCron))
			echo "success\n";
		else
			echo "failure\n";
	} elseif ($command == "getCronByProject") {
		$idProyecto = $_POST['idProyecto'];
		$query = "Select Concepto, idCron, Prereq, Padre, Estado, Tiempo, TiempoTrab from Cronograma where idProyecto='$idProyecto';";
		$rs = mysql_query($query) or die("failure");
		while ($row = mysql_fetch_array($rs)) {
			$Concepto = $row['Concepto'];
			$idCron = $row['idCron'];
			$Prereq = $row['Prereq'];
			$Padre = $row['Padre'];
			$Estado = $row['Estado'];
			$tiempo = $row['Tiempo'];
			$tiempoTrab = $row['TiempoTrab'];
			$integrantes = mysql_query("Select UserId from resp_cron where idCron=$idCron;");
			echo "$Concepto,$idCron,";
			//Lista de integrantes separados por espacios
			while ($integrante = mysql_fetch_array($integrantes)) {
				echo $integrante['UserId']," ";
			}
			echo ",$Prereq,$Padre,$Estado,$tiempo,$tiempoTrab\n";
		}
	} else {
		echo "Comando erroneo: $command\n";
	}
?>
