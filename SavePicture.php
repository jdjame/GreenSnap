<?php



	$name = $_POST["name"];

	$image = $_POST["image"];


	$decodedImage = base64_decode($image);
	file_put_contents("Desktop/". $name. ".jpg", $decodedImage);

	
	

	//echo 'Current PHP version: ' . phpversion();

	
?>	