
<?php
	include("../../conn.php");

	/*
	 * Inserta una entrada en el cronograma. El idProyecto debe existir, el concepto es el nombre
	 * mostrado en la entrada del cronograma, el tiempo es lo pronosticado (entero - horas hombre),
	 * Prereq es una cadena con los prerequisitos y Resp es una cadena de UserId separados por coma.
	 * Importante asegurarse que existan todos los responsables pasados como argumento en la lista.
	 */
	function cronAdd($idProyecto, $Concepto, $Padre, $Tiempo, $Prereq, $Resp, $estado) {
		$sqlIns = "Insert into Cronograma (idProyecto,Concepto,Padre,Tiempo,estado,Prereq,TiempoTrab)".
			" values ('$idProyecto','$Concepto',$Padre,$Tiempo,$estado,'$Prereq',0);";
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
	
	/*
	 * Elimina una entrada en el cronograma. 
	 */
	function cronRem($idCron) {
		$defStatus=0; //estado por defecto
		mysql_query("Delete from resp_cron Where idCron=$idCron;");
		mysql_query("Delete from ContCron Where idCron=$idCron;");
		mysql_query("Delete from Cronograma where idCron=$idCron;");		
	}
	
	/*
	 * Actualiza una entrada en el cronograma. El idProyecto e idCron deben existir, el concepto es el nombre
	 * mostrado en la entrada del cronograma, el tiempo trabajado no se podrá cambiar desde esta función,
	 * Prereq es una cadena con los prerequisitos y Resp es una cadena de UserId separados por coma.
	 * Importante asegurarse que existan todos los responsables pasados como argumento en la lista. Nótece que
	 * independiente de si cambian o no los responsables estos siempre se eliminaran y agregaran de nuevo.
	 */
	function cronUpdate($idCron, $Concepto, $Padre, $Tiempo, $Prereq, $Resp, $estado) {
		$defStatus=0; //estado por defecto
		$sqlUpdate = "Update Cronograma SET Concepto='$Concepto',Padre=$Padre,".
			"Tiempo=$Tiempo,estado=$estado,Prereq='$Prereq' where idCron=$idCron;";
		mysql_query("Delete from resp_cron Where idCron=$idCron;");
		if ( mysql_query($sqlUpdate) ) {
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
