# Golden Raspberry Awards
Restful API to list movies and the producer's winning interval at the Golden Raspberry Awards

## Project Specs
**Spring Boot 3.3.0<br>
Java 17<br>
Maven 3.8.7**

-----
## Run project
**LINUX / macOS**
```bash
./mvnw spring-boot:run
```
**WINDOWS**
```bash
mvnw.cmd spring-boot:run
```
-----
## CSV Import
When the application starts, it will look for a file named ```movielist.csv``` in the root of the application to import
the movies into the H2 memory database.
The application will keep running on port 8080 and the database can be queried in the following path:
http://localhost:8080/h2-console

**Producers** and **Studios** duplicates are not allowed,
this takes more time to import the payload but increases the overall consistency
in the award interval endpoint.

-----
## Endpoints

All the endpoints can be found at the application swagger at:
http://localhost:8080/swagger-ui/index.html

### Endpoint to check the award interval (min and max)
http://localhost:8080/award/intervals

-----
