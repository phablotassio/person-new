SET search_path TO "DBPERSON";

CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE IF NOT EXISTS "TB_ADDRESS"
(
    "ID_ADDRESS"   BIGSERIAL,
    "UUID"              VARCHAR(50) UNIQUE NOT NULL DEFAULT uuid_generate_v4(),
    "ID_PERSON"    BIGSERIAL UNIQUE NOT NULL,
    "ZIPCODE"      VARCHAR(13)      NOT NULL,
    "CITY"         VARCHAR(40)      NOT NULL,
    "STREET"      VARCHAR(70)      NOT NULL,
    "NEIGHBORHOOD" VARCHAR(70),
    "STATE"        VARCHAR(30)      NOT NULL,
    "COMPLEMENT"  VARCHAR(70),
    "DT_INCLUSION" TIMESTAMP        NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY ("ID_PERSON") REFERENCES "TB_PERSON" ("ID_PERSON"),
    PRIMARY KEY ("ID_ADDRESS")
);