# Golden Raspberry Awards
### Restful API to list movies and the producer's winning interval at the Golden Raspberry Awards

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
the movies into the H2 memory database.<br>
The application will keep running on port 8080 and the database can be queried in the following path:<br>
http://localhost:8080/h2-console

**Producers** and **Studios** names are unique so on CSV import it checks if it exists before inserting.<br>
This takes more time to import but increases the consistency in the award interval endpoint.

-----
## Endpoints

All the endpoints can be found at the application **swagger** at:<br>
http://localhost:8080/swagger-ui/index.html

### Endpoint to check the award interval (min and max)
http://localhost:8080/swagger-ui/index.html#/award-controller/getAwardIntervals
<br>or call it directly:<br>
http://localhost:8080/award/intervals

-----
## Integration tests

**LINUX / macOS**
```bash
./mvnw clean test
```
**WINDOWS**
```bash
mvnw.cmd clean test
```
Integration tests rely on the valid csv [movielist.csv](movielist.csv) for valid input tests and
[movielist-invalid.csv](movielist-invalid.csv) to make the tests with invalid input,
which has some invalid values for year and winner flag.
Other fields do not have validation for now since they are descriptive they can accept any string.<br>
Invalid lines will not be imported, but it **will not affect the following movie imports**,
valid lines will always be imported so the tests will rely on the number of imported movies.

-----
