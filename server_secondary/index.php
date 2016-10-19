<?php

define('SECRET','mySecretServerKey');
define('SEARCH_URL_FORMAT','https://www.google.co.in/search?q=%s&tbm=isch');
define('FAKE_USER_AGENT', "Mozilla/5.0 (X11; Linux i686) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/48.0.2564.116 Safari/537.36");

$headers = apache_request_headers();
$authKey = $headers['Authorization'];

if($authKey===SECRET){

	$keyword = $_GET['keyword'];

	if(isset($keyword)){

		$finalUrl =  sprintf(SEARCH_URL_FORMAT,urlencode($keyword));
		
		$ch = curl_init();
		curl_setopt($ch, CURLOPT_URL, $finalUrl);
		curl_setopt($ch, CURLOPT_RETURNTRANSFER, 1);
		curl_setopt($ch, CURLOPT_USERAGENT, FAKE_USER_AGENT);
		$data = curl_exec($ch);
		curl_close($ch);

		header('Content-Type: text/plain');
		echo $data;


	}else{
        header('Content-Type: application/json');
		$arr = array();
		$arr['error'] = true;
		$arr['message'] = "Keyword missing";
		echo json_encode($arr);
	}
}else{

        header('Content-Type: application/json');
		$arr = array();
		$arr['error'] = true;
		$arr['message'] = "Unauthorized request";
		echo json_encode($arr);

}
		
