<?php

include("connection.php");

$uid = $_REQUEST['uid'];

$sql = "SELECT name, Lname, image, email from user WHERE id = '$uid' ";
$res = mysqli_query($con,$sql);
$row = mysqli_fetch_assoc($res);
	$data['user'][] = $row;


$sql = "SELECT * from my_crop WHERE uid = '$uid' ";
$res = mysqli_query($con,$sql);
while($row = mysqli_fetch_assoc($res))
{
	$cid = $row['cid'];

	$sql1 = "SELECT * from crops WHERE id = '$cid' ";
	$res1 = mysqli_query($con,$sql1);
	$row1 = mysqli_fetch_assoc($res1);
	$data['crop'][] = $row1;

}

echo json_encode($data);

?>