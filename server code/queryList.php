<?php

include("connection.php");

$sql = "SELECT * from tbl_query ";
$res = mysqli_query($con,$sql);
while ($row = mysqli_fetch_assoc($res))
	$data['data'][] = $row;

echo json_encode($data);

?>