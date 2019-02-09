# Java inheritance into PostgreSQL JSONB mapping

The demo project with mapping of Java objects inheritance 
into PostgreSQL JSONB columns via Jackson and MyBatis.

## Development

To build & test:

```bash
./gradlew build test
```

## Running on localhost

To run application on localhost:

```bash
./gradlew bootRun
```

Required `PostgreSQL` running on port `5432` with database schema. 
It can be done with Docker container:

```bash
docker run \
    --name postgres \
    -e POSTGRES_DB="postgres" \
    -e POSTGRES_USER="postgres" \
    -e POSTGRES_PASSWORD="postgres" \
    -v postgres_data:/var/lib/postgresql \
    -v ${PWD}/src/main/resources/sql/init.sql:/docker-entrypoint-initdb.d/1.init.sql \
    -v ${PWD}/src/main/resources/sql/data.sql:/docker-entrypoint-initdb.d/2.data.sql \
    -p 5432:5432 \
    -d postgres
```

## Running in Docker (the easiest start)

To package the application into jar file:

```bash
./gradlew bootRepackage
```

To build the application docker image:

```bash
docker-compose build
```

To run in Docker with PostgreSQL database:

```bash
docker-compose up -d
```

To stop the application and PostgreSQL database:

```bash
docker-compose down
```

---

## Examples

### Vehicle

SQL:

```sql
id         | 1
type       | VEHICLE
latitude   | 45.047579
longitude  | 38.963983
course     | 10
speed      | 15
attributes | {"model": "Toyota Camry", "regNumber": "A 123 BC 777"}
```

JSON:

```json
{
    "id": 1,
    "type": "VEHICLE",
    "latitude": 45.047579,
    "longitude": 38.963983,
    "course": 10,
    "speed": 15,
    "model": "Toyota Camry",
    "regNumber": "A 123 BC 777"
}
```

### Employee

SQL:

```sql
id         | 3
type       | EMPLOYEE
latitude   | 45.026158
longitude  | 38.927763
course     | 30
speed      | 35
attributes | {"name": "John Doe", "phoneNumber": "8-800 2000 600"}
```

JSON:

```json
{
    "id": 3,
    "type": "EMPLOYEE",
    "latitude": 45.026158,
    "longitude": 38.927763,
    "course": 30,
    "speed": 35,
    "name": "John Doe",
    "phoneNumber": "8-800 2000 600"
}
```

---

## P.S.

Thanks to @0ffer, it was his idea and initial implementation.
