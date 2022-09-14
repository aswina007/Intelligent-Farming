<?php

include("connection.php");

$usel = " SELECT * FROM user WHERE id = '$_REQUEST[uid]' ";
$result = mysqli_query($con,$usel);
$roww = mysqli_fetch_assoc($result);
$lat = $roww['latitude'];
$lon = $roww['longitude'];

$sql = " SELECT id,ac_centre,ac_latitude,ac_longitude,SQRT( POW(69.1 * (ac_latitude - '$lat'), 2) + POW(69.1 * ('$lon' - ac_longitude) * COS(ac_latitude / 57.3), 2)) AS distance FROM agri_centers HAVING distance < 5 ORDER BY distance DESC  ";
$res = mysqli_query($con,$sql);
while($row = mysqli_fetch_assoc($res))
{
	$data['data'][] = $row;
}

$sql = " SELECT id,name,latitude,longitude,SQRT( POW(69.1 * (latitude - '$lat'), 2) + POW(69.1 * ('$lon' - longitude) * COS(latitude / 57.3), 2)) AS distance FROM user WHERE id != '$_REQUEST[uid]'  HAVING distance < 5 ORDER BY distance DESC  ";
$res = mysqli_query($con,$sql);
while($row = mysqli_fetch_assoc($res))
{
	$data['user'][] = array('id' => $row['id'] , 'name' => $row['name'], 'latitude' => $row['latitude'], 'longitude' => $row['longitude'], 'type' => 'user');
}

echo json_encode($data);

?>