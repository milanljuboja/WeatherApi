#Application for fetching information about wheather and storing it into database
##How to run and compile
For running the application one approach is running from the Eclipse IDE, and other approach is running from command-line.

If you want to use first approach run the `main` method which is in the class `ProjectApplication`.
*Right click on class ProjectApplication->Run As->Java Application*

If you want to use second approach there's no need to have IDE open/running. 
Also, since server is embedded in JAR file there's no need to have separate server installed/running.
There's two options for running the application from command-line:
*1.* Use `java-jar`
*2.* Use Spring Boot Maven Plugin to package executable jar file and also to run the app, with commands `mvnw package` and `mvnw spring-boot:run`
 	
To compile the application from command-line use command `mvnw compile` or if correct Maven version is installed use `mvn compile`.

## Test
To test the application use tests implemented in `WeatherControllerTests` class (Right click on class WeatherControllerTests->Run As->JUnit Test) or try manual testing in web browser.

To check route `/city/{city-name}` write in your browser URL address `http://localhost:8080/weather/city/London` providing a valid city name. The response should be JSON with weather informations for city London. 

To check route `/city/findCityByZipCode/{zip}` write in your browser URL address `http://localhost:8080/city/findCityByZipCode/{zip}` providing a valid zip code.
The response should be JSON with weather informations for zip code 19403.

To check route `/city/findCityByCoordinates/{lat}/{lon}` write in your browser URL address `http://localhost:8080/city/139.01/35.02` providing a valid city name. The response should be JSON with weather informations for city Tawarano.

## Improvements

This application aimed at implementing besides all the defined requirements such as fetching weather information for the current city, storing them into database and updating the data in the database. Fetching data is done via database. The application implements 2 roles user and admin that are both defined in the mongo database. User authentication/authorization using tokens is implemented. The application is aimed at providing subscriptions for the users for the current cities and sends automatic mail to users with the information about the current weather. The application stores history of weather into the database and the history of weather access service will be implemented. The junit testing has not been finished completely.