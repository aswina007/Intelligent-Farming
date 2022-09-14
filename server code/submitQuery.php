<?php

include("connection.php");

date_default_timezone_set('Asia/Kolkata');
$cdate = date('d-m-y');

$uid = $_REQUEST['uid'];
$qtn = $_REQUEST['qtn'];

$sql = " INSERT INTO tbl_query(uid, question, qdate) VALUES ('$uid', '$qtn', '$cdate') ";
if(mysqli_query($con,$sql))
	echo "success";
else
	echo "Failed";


?>