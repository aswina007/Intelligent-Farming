<?php

include("connection.php");

date_default_timezone_set('Asia/Kolkata');
$cdate = date('d-m-y');

$qid = $_REQUEST['qid'];
$uid = $_REQUEST['uid'];
$answer = $_REQUEST['answer'];

$sql = " INSERT INTO tb_query_answer(qid, uid, answer, adate) VALUES ('$qid', '$uid', '$answer', '$cdate') ";
if(mysqli_query($con,$sql))
	echo "success";
else
	echo "Failed";


?>