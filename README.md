# Java inheritance into PostgreSQL JSONB mapping

The demo project with mapping of Java objects inheritance 
into PostgreSQL JSONB columns via Jackson and MyBatis.

## Development

To build & test:

```bash
./gradlew build test
```

## Running on localhost

Required `PostgreSQL` running on port `5432` with database schema. 
Init sql script can be find in `resoureces/sql/` folder.

To run application on localhost:

```bash
./gradlew bootRun
```

## Running in Docker (the easiest start)

To run in Docker with PostgreSQL database:

```bash
docker-compose up -d
```

To stop the stack:

```bash
docker-compose down
```
