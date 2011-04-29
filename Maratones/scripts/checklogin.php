<?php
// username and password sent from form 
$myusername=$_POST['myusername']; 
$mypassword=$_POST['mypassword'];

if ($myusername=="insilico" && $mypassword=="tobytierno") {
	session_start();
	$_SESSION["usuario"]=$myusername;
	header("location:maratones.php");
} else {
	echo "Wrong user-name or password";
}
?>
