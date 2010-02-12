
<?php
	include("../conn.php");

	/*
	 * Inserta una entrada en el cronograma. El idProyecto debe existir, el concepto es el nombre
	 * mostrado en la entrada del cronograma, el tiempo es lo pronosticado (entero - horas hombre),
	 * Prereq es una cadena con los prerequisitos y Resp es una cadena de UserId separados por coma.
	 * Importante asegurarse que existan todos los responsables pasados como argumento en la lista.
	 */
	function cronAdd($idProyecto, $Concepto, $Padre, $Tiempo, $Prereq, $Resp) {
		$defStatus=0; //estado por defecto
		$sqlIns = "Insert into Cronograma (idProyecto,Concepto,Padre,Tiempo,estado,Prereq,TiempoTrab)".
			" values ('$idProyecto','$Concepto',$Padre,$Tiempo,$defStatus,'$Prereq',0);";
		echo $sqlIns,"\n";
		if ( mysql_query($sqlIns) ) {
			$idCron = mysql_insert_id();
			$tok = strtok($Resp,",");
			while ($tok !== false) {
				mysql_query("Insert into resp_cron (UserId,idCron) values ('$tok',$idCron);");
				$tok = strtok(",");
			}
			return true;
		}
		return false;
	}
?>
