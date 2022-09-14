<?php 
set_time_limit(0);

$con = mysqli_connect("localhost","root","","farming");


$head="season,pH,soil_type,elevation,temperature";
$data="$_REQUEST[season],$_REQUEST[pH],$_REQUEST[soil_type],$_REQUEST[elevation],$_REQUEST[temperature]";
/*echo "Headings : ".$head;
echo "<br>";
echo "Datas : ".$data;*/

$myFile = "test.csv";
$fh = fopen($myFile, 'w') or die("can't open file");

//inserting into csv file
$stringData = "$head\n$data";
fwrite($fh, $stringData);
fclose($fh);

//testing protion starts here

$python=`python test.py`;

$val = $python;
$array = explode('#',$val);
//echo $val;

$count = sizeof($array);

if( $count > 0)
{
	for ($i=0; $i < $count-1 ; $i++) 
	{ 
		$crop = trim($array[$i]);
		$sql = " SELECT * FROM crops WHERE name = '$crop' ";
		$res = mysqli_query($con,$sql);
		$row = mysqli_fetch_assoc($res);
		$da['data'][] = $row;
	}
echo json_encode($da);
}
else{
	echo "No data";
}


?>

