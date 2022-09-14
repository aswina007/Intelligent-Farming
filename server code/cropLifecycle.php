<?php

include("connection.php");

$sql = "SELECT * from crop_lifecycle WHERE cid = '1' ";
$res = mysqli_query($con,$sql);
while($row = mysqli_fetch_assoc($res))
{
	$data['data'][] = $row;
}

echo json_encode($data);

?>