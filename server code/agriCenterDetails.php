<?php

include("connection.php");

$sid = $_REQUEST['sid'];

$sql = "SELECT * from agri_centers WHERE id = '$sid' ";
$res = mysqli_query($con,$sql);
$row = mysqli_fetch_assoc($res);
	$data['center'][] = $row;


/*$sql = "SELECT * from my_crop WHERE uid = '$uid' ";
$res = mysqli_query($con,$sql);
while($row = mysqli_fetch_assoc($res))
{
	$cid = $row['cid'];

	$sql1 = "SELECT * from crops WHERE id = '$cid' ";
	$res1 = mysqli_query($con,$sql1);
	$row1 = mysqli_fetch_assoc($res1);
	$data['crop'][] = $row1;

}*/

echo json_encode($data);

?>