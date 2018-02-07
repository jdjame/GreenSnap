<?php

$command = 'python dummy.py' ;

$temp = exec('python3 ~/Applications/MAMP/htdocs/McHacks/dummy.py');

echo 3;

$python = `$command`;
	echo $python;
?>