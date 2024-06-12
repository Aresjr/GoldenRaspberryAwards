# Golden Raspberry Awards
Restful API to list movies and the producer's winning interval at the Golden Raspberry Awards

## Project Specs
<b>
Spring Boot 3.3.0<br>
Java 17<br>
Maven 3.8.7
</b>

-----
## Run project
<b>LINUX / macOS</b>
```bash
./mvnw spring-boot:run
```
<b>WINDOWS</b>
```bash
mvnw.cmd spring-boot:run
```
-----
## CSV Import
When the application starts, it will look for a file named ```movielist.csv``` in the root of the application to import
the movies into the H2 memory database.
The application will keep running on port 8080 and the database can be queried in the following path:
```
http://localhost:8080/h2-console
```
-----
## Endpoints

All the endpoints can be found at the application swagger at:
```dtd
http://localhost:8080/swagger-ui/index.html
```
-----
