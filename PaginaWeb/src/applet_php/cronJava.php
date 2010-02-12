
<?php
	include("../privado/cron.php");	
	include("../util.php");
	$command = $_POST['command'];
		
	if ($command == "insert") {
		$idProyecto = $_POST['idProyecto'];
		$concepto = $_POST['concepto'];
		$padre = $_POST['padre'];
		$tiempo = $_POST['tiempo'];
		$prereq = $_POST['prereq'];
		$resp = $_POST['resp'];
		if (cronAdd($idProyecto, $concepto, $padre, $tiempo, $prereq, $resp))
			echo "success\n";
		else
			echo "failure\n";
	} elseif ($command == "getCronByProject") {
		$idProyecto = $_POST['idProyecto'];
		$campos = $_POST['campos'];
		executeCSV("Select $campos from Cronograma where idProyecto='$idProyecto';");
	}
?>
