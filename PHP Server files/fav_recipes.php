<?php
$dbhost = "localhost";
$dbuser = "root";
$dbpass = "f565593aad3897ad";
$db = "wfd_db";
$mysqli = new mysqli($dbhost, $dbuser, $dbpass, $db);

$email = $_GET["email"];
$statement = mysqli_prepare($mysqli, "SELECT User_id FROM users WHERE email_address=?");
mysqli_stmt_bind_param($statement, "s", $email);
mysqli_stmt_execute($statement);
mysqli_stmt_bind_result($statement, $userID);
while (mysqli_stmt_fetch($statement))
  $userID;
mysqli_stmt_close($statement);
$statement = mysqli_prepare($mysqli, "SELECT recipe_id FROM fav_recipes WHERE User_id='".$userID."'");
mysqli_stmt_execute($statement);
$recipes = "";
$count = 0;
mysqli_stmt_bind_result($statement, $recipeID);
while (mysqli_stmt_fetch($statement)) {
  if ($count == 0)
    $recipes = $recipeID;
  else
    $recipes = $recipes.",".$recipeID;
  $count++;
}

echo "[".$recipes."]";
mysqli_stmt_close($statement);
mysqli_close($mysqli);
?>
