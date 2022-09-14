<?php

include("connection.php");
$data = array();


$sql = "SELECT * from user WHERE id = '$_REQUEST[userId]' ";
$res = mysqli_query($con,$sql);
while ($row = mysqli_fetch_assoc($res))
	$data['data'][] = $row;

echo json_encode($data);

?>