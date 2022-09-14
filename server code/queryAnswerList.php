<?php

include("connection.php");

$sql = "SELECT * from tb_query_answer WHERE qid = '$_REQUEST[qid]' ORDER BY id DESC ";
// echo $sql;
$res = mysqli_query($con,$sql);
while($row = mysqli_fetch_assoc($res))
{
	$userSel = "SELECT * FROM user WHERE id = '$row[uid]' ";
	$ures = mysqli_query($con,$userSel);
	$urow = mysqli_fetch_assoc($ures);
	$uname = $urow['name']; 
	$uLname = $urow['Lname'];
	$name = $uname." ".$uLname; 
	$uimage = $urow['image'];

	$data['data'][] = array('name' => $name, 'image' => $uimage, 'date' => $row['adate'], 'answer' => $row['answer'] );
}

echo json_encode($data);

?>