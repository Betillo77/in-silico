<?php

	$cnn = mysql_connect('localhost', 'phpuser', 'n=kg*n/s2');
	if (!$cnn) {
   		die('Could not connect: ' . mysql_error());
	} 
	mysql_select_db("insilico",$cnn);
?>
