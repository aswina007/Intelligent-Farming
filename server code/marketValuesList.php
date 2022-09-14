<?php

include("connection.php");

$crop = $_REQUEST['crop'];
$year = $_REQUEST['year'];
$month = $_REQUEST['month'];

$sql = " SELECT district,price FROM tbl_market WHERE crop = '$crop' AND year = '$year' AND month = '$month'  ";
$res = mysqli_query($con,$sql);

if(mysqli_num_rows($res) > 0)
{
	while($row = mysqli_fetch_assoc($res))
	{
		$data['data'][] = $row;
	}
	echo json_encode($data);
}
else{
	echo "Failed";
}

?>