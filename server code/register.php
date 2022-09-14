<?php

include("connection.php");

$fname = $_POST['fname'];
$lname = $_POST['lname'];
$phone = $_POST['phone'];
$email = $_POST['email'];
$latitude = $_POST['latitude'];
$longitude = $_POST['longitude'];
$username = $_POST['username'];
$password = $_POST['password'];
$image = $_POST['image'];

$random_number = mt_rand(100000, 999999);

if($image == "")
{

	$sql = " INSERT INTO user(username, password, name, Lname, phone_no, latitude, longitude, image, email) VALUES ('$username','$password','$fname','$lname','$phone','$latitude','$longitude','','$email') ";

	if(mysqli_query($con,$sql))
		echo "success";
	else
		echo "Failed";

}
else{

	$filename = $random_number.".jpeg";
	$path = "admin/tbl_user/uploads/$filename";
	file_put_contents($path,base64_decode($image));

	/*$randNumResult = mysqli_query($con, "SELECT order_number FROM tbl_order_master ");

	while($randNumRow = mysqli_fetch_assoc($randNumResult))
	{
		$randumNo = $randNumRow['order_number'];
		if($six_digit_random_number == $randumNo)
		{
			$six_digit_random_number = mt_rand(100000, 999999);
		}
	}*/


	$sql = " INSERT INTO user(username, password, name, Lname, phone_no, latitude, longitude, image, email) VALUES ('$username','$password','$fname','$lname','$phone','$latitude','$longitude','$filename','$email') ";

	if(mysqli_query($con,$sql))
		echo "success";
	else
		echo "Failed";

}




?>