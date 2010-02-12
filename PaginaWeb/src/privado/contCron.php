
<?php
	include("../util.php");
	

	/**
	 * Inserta un registro en la contabilidad del cronograma y actualiza
	 * el tiempo guardado en la tabla "cronograma". Retorna verdadero si hay éxito
	 */
	function contcronAdd($idCron, $fecha, $UserId, $tiempo) {
		$cronT = executeScalar("Select TiempoTrab from Cronograma where idCron=$idCron;");
		if ($cronT != "") {
			$cronT += $tiempo;
			if (mysql_query("Update Cronograma Set TiempoTrab=$cronT Where idCron=$idCron;") ) {
				mysql_query("Insert Into ContCron (IdCron,fecha,UserId,Tiempo) Values ($idCron,$fecha,'$UserId',$tiempo);"
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Inserta un registro en la contabilidad del cronograma y actualiza
	 * el tiempo guardado en la tabla "cronograma". Retorna verdadero si hay éxito
	 */
	function contcronDel($idTrans) {
		$row = executeRow("Select IdCron, Tiempo from ContCron where idTrans=$idTrans;");
		$idCron=$row[0];
		$tiempo=$row[1];
		$cronT = executeScalar("Select TiempoTrab from Cronograma where idCron=$idCron;");
		if ($cronT != "") {
			$cronT -= $tiempo;
			if (mysql_query("Update Cronograma Set TiempoTrab=$cronT Where idCron=$idCron;") ) {
				mysql_query("Delete from ContCron where idTrans=$idTrans");
				return true;
			}
		}
		return false;
	}

?>
