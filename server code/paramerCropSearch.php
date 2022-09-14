<?php

include("connection.php");

	$crop = $_POST['crop'];
	$season = $_POST['season'];
	$ph = $_POST['ph'];
	$soil = $_POST['soil'];
	$elivation = $_POST['elivation'];
	$temp = $_POST['temp'];

	echo $crop." ".$season." ".$ph." ".$soil." ".$elivation." ".$temp;



?>