<?php

include("connection.php");

$cropid = $_REQUEST['cropid'];
$userid = $_REQUEST['userid'];

$sql = " INSERT INTO my_crop(uid, cid) VALUES ('$userid','$cropid') ";
mysqli_query($con,$sql);

?>