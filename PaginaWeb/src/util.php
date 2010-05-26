<?php

/* 
 *  Archivo con funciones de utilidad general para los módulos de la aplicación
 */
 
	include("conn.php");

	// Retorna el primer valor retornado por la consulta SQL, o def si no hay resultado
	function executeScalar($sql,$def="") {
		$rs = mysql_query($sql) or die("failure : sql syntax error");
		if ($row = mysql_fetch_array($rs)) {
			mysql_free_result($rs);
			return $row[0];
		}
		return $def;
	}
	
	//retorna la primera fila retornada por la consulta SQL
	function executeRow($sql,$def="") {
		$rs = mysql_query($sql) or die("failure : sql syntax error");
		if ($row = mysql_fetch_array($rs)) {
			mysql_free_result($rs);
			return $row;
		}
		return $def;
	}
	
	//"Imprime" el resultado de una consulta con valores separados por coma
	function executeCSV($sql) {
		$rs = mysql_query($sql) or die("failure : sql syntax error");
		while ($row = mysql_fetch_array($rs)) {
			$last = count($row)-1;
			for ( $i=0; $i<$last; $i++) {
				echo $row[$i], ",";
			}
			echo $row[$last],"\n";
		}
		mysql_free_result($rs);
		return $def;
	}
	
	/*
	 * Genera una tabla HTML con el encabezado dado mostrando los datos
	 * generados por la consulta. No retorna nada, solo imprime datos al usuario.
	 * headList es una lista (Cadena separada por comas que debe tener la misma 
	 * longitud que campos el query SQL) y contiene los nombres de las columnas.
	 */
	function genTable($query, $headList) {
		echo "<table>\n<tr>\n";
		$tok = strtok($headList,",");
		$cols=0;
		while ($tok !== false) {
			echo "<td>$tok</td>\n";
			$tok = strtok(",");
			$cols++;
		}
		echo "</tr>\n";
		$rs = mysql_query($query);
		while ($row = mysql_fetch_array($rs)) {
			echo "<tr>\n";
			for ( $i=0; $i<$cols; $i++) {
				echo "<td>$row[$i]</td>\n";
			}
			echo "</tr>\n";
		}
		echo "</table>\n";
	}
?>
