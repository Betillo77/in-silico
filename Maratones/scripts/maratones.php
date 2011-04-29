<?php
	session_start();
	if(!$_SESSION["usuario"]){
		header("location:login.html");
	}
	if ($_POST['iface']) {
		$iface=$_POST['iface']; 
		$dburl=$_POST['dburl'];
		$cmdOut = array();
		echo "bash ./pc2/scriptApache $iface $dburl<br/>";
		$lastLine = exec("./scriptApache $iface $dburl", $cmdOut, $retVal);
		#$lastLine = exec("ifconfig", $cmdOut, $retVal);
		
		if ($lastLine != "0") {
			echo "El script se ejecutó con errores:<br />";
			echo "Codigo del error: $retVal<br/>Última Línea: $lastLine<br/>";
			foreach ($cmdOut as $i) {
				echo "$i<br/>";
			}
		} else {
			echo "El servidor se ejecutó correctamente, el pid es $lastLine";
		}
	}
?>

<table width="400" border="0" align="center" cellpadding="0" cellspacing="1" bgcolor="#CCCCCC">
<tr>
<form name="form1" method="post" action="maratones.php">
<td>
<table width="100%" border="0" cellpadding="3" cellspacing="1" bgcolor="#FFFFFF">
<tr>
<td colspan="3"><strong>Sitio de Maratones</strong></td>
</tr>
<tr>
<td width="200">Nombre de la tarjeta de red</td>
<td width="6">:</td>
<td width="294"><input name="iface" type="text" id="iface" value="eth0"></td>
</tr>
<tr>
<td>URL de db.zip</td>
<td>:</td>
<td><input name="dburl" type="text" id="dburl" value="http://dl.dropbox.com/u/7089915/db.zip"></td>
</tr>
<tr>
<td>&nbsp;</td>
<td>&nbsp;</td>
<td><input type="submit" name="Submit" value="Start pc2server"></td>
</tr>
</table>
</td>
</form>
</tr>
</table>
