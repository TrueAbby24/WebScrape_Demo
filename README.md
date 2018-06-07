# Web Scraping Demo
This is a part the web project I worked on with the recipes. I wrote all the helper classes so that you don't need to interact with raw JSON at all.
The reason I used Java and PHP on the server side is that I did all the web scraping before implementing the server, which I didn't have time to fix in the time given so I figured out how to exec a Java class from PHP.

## Running the program
Compile all the classes in Main. Ensure that you include the external jars when compiling and running the program.
When running the Java code to test, you can run the WebScrape.java class which combines all the classes and the server.

## Extra Notes
You also used PostMan (https://www.getpostman.com/) to test the server.
I have also included a fav_recipes.php file which is the same as the server which shows how to get a users favourite recipe using 2 tables from the following table structure:
