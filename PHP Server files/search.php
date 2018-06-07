<?php
$terms = json_encode($_GET["terms"]);
  $args = '/opt/local/bin/java -cp .:./Main/json-simple-1.1.1.jar:./Main/jsoup-1.11.3.jar:./Main/unirest-java-1.4.9.jar \'-Dsun.security.pkcs11.enable-solaris=false\' Main/SearchServer '.$terms.' 2>&1';
  $output = "";
  echo exec($args, $output);
?>
