CREATE TYPE MONITORING_OBJECT_TYPE AS ENUM (
  'VEHICLE', 'EMPLOYEE'
);

CREATE TABLE monitoring_objects (
  id                  SERIAL PRIMARY KEY,
  type                MONITORING_OBJECT_TYPE NOT NULL,
  latitude            DOUBLE PRECISION,
  longitude           DOUBLE PRECISION,
  course              REAL,
  speed               REAL,
  attributes          JSONB NOT NULL DEFAULT '{}'::JSONB
);
