<?php

include("connection.php");

date_default_timezone_set('Asia/Kolkata');
$cdate = date('d-m-y');

	$uid = $_POST['userId'];
	$fname = $_POST['fname'];
	$lname = $_POST['lname'];
	$email = $_POST['email'];
	$mobile = $_POST['mobile'];
	$username = $_POST['username'];
	$password = $_POST['password'];


$sql = " UPDATE user SET name='$fname', Lname='$lname', phone_no='$mobile', email='$email', username='$username', password='$password' WHERE id='$uid' ";
if(mysqli_query($con,$sql))
	echo "success";
else
	echo "Failed";


?>