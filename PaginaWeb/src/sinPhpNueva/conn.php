<?php

	$cnn = mysql_connect('localhost', 'root', '');
	if (!$cnn) {
   		die('Could not connect: ' . mysql_error());
	} 
	mysql_select_db("insilico",$cnn);
?>
