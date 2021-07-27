CREATE SCHEMA if not exists "DBPERSON";

SET search_path TO "DBPERSON";

SET FOREIGN_KEY_CHECKS = 0;
TRUNCATE TABLE "TB_ADDRESS";
TRUNCATE TABLE "TB_PERSON";
TRUNCATE TABLE "TB_PHONE";
TRUNCATE TABLE "TB_WEIGHT_HEIGHT";
SET FOREIGN_KEY_CHECKS = 1;

-- Person
insert into "TB_PERSON" ("ID_PERSON", "DT_INCLUSION", "DT_BIRTH", "DOCUMENT_NUMBER", "EMAIL", "FULL_NAME", "NM_FATHER",
                         "NM_MOTHER",
                         "TP_SEX", "UUID")
values (101, CURRENT_TIMESTAMP, '1996-05-22', '52278512048', 'brancante@foo.com', 'brincante da silva', 'jose',
        'roselia', 'M', '9209786f-c1ed-4014-a3fe-5601aacee291');


insert into "TB_PERSON" ("ID_PERSON", "DT_INCLUSION", "DT_BIRTH", "DOCUMENT_NUMBER", "EMAIL", "FULL_NAME", "NM_FATHER",
                         "NM_MOTHER",
                         "TP_SEX", "UUID")
values (102, CURRENT_TIMESTAMP, '1998-03-24', '15286351085', 'brancantedasilva@foo.com', 'mister', 'joao', 'maria',
        'M', '63f097e2-6b1c-47cc-bae6-b1a941ee218b');


insert into "TB_PERSON" ("ID_PERSON", "DT_INCLUSION", "DT_BIRTH", "DOCUMENT_NUMBER", "EMAIL", "FULL_NAME", "NM_FATHER",
                         "NM_MOTHER",
                         "TP_SEX", "UUID")
values (103, CURRENT_TIMESTAMP, '2018-11-24', '21066597090', 'alguemdasilva@foo.com', 'jose augusto', 'Luciano',
        'carla', 'M', '299dd3ad-65f9-43d5-8017-d15de663c5a0');


insert into "TB_PERSON" ("ID_PERSON", "DT_INCLUSION", "DT_BIRTH", "DOCUMENT_NUMBER", "EMAIL", "FULL_NAME", "NM_FATHER",
                         "NM_MOTHER",
                         "TP_SEX", "UUID")
values (104, CURRENT_TIMESTAMP, '2018-11-24', '83765079073', 'zezinho@foo.com', 'jose augusto', 'Luciano', 'carla',
        'M', 'a5aad30d-3b3f-4bae-9154-adebe04c970c');

insert into "TB_PERSON" ("ID_PERSON", "DT_INCLUSION", "DT_BIRTH", "DOCUMENT_NUMBER", "EMAIL", "FULL_NAME", "NM_FATHER",
                         "NM_MOTHER",
                         "TP_SEX", "UUID")
values (105, CURRENT_TIMESTAMP, '2018-11-24', '88872152003', 'pedacinho@foo.com', 'jose augusto', 'Luciano', 'carla',
        'M', 'c09dab5a-36c9-4bbc-a8c8-b3a29afd1cf2');

insert into "TB_PERSON" ("ID_PERSON", "DT_INCLUSION", "DT_BIRTH", "DOCUMENT_NUMBER", "EMAIL", "FULL_NAME", "NM_FATHER",
                         "NM_MOTHER",
                         "TP_SEX", "UUID")
values (106, CURRENT_TIMESTAMP, '2018-11-25', '92125214016', 'haha@foo.com', 'jhonathan', 'Luciano', 'josivania', 'M',
        '2241e15a-e459-47a1-95d5-0b59b2bf424f');


insert into "TB_PERSON" ("ID_PERSON", "DT_INCLUSION", "DT_BIRTH", "DOCUMENT_NUMBER", "EMAIL", "FULL_NAME", "NM_FATHER",
                         "NM_MOTHER",
                         "TP_SEX", "UUID")
values (107, CURRENT_TIMESTAMP, '2018-11-25', '35161434015', 'tttt@foo.com', 'hand', 'marcelino', 'jheniuesa', 'F',
        'f62ee5b3-55b4-4a86-bc6a-a5fb06cc3bcf');


-- Address
insert into "TB_ADDRESS" ("ID_ADDRESS", "DT_INCLUSION", "CITY", "COMPLEMENT", "NEIGHBORHOOD", "ID_PERSON", "STATE",
                          "STREET", "ZIPCODE",
                          "UUID")
values (103, CURRENT_TIMESTAMP, 'Brasilia', 'la manos', 'Setor de Mansoes de Sobradinho', 101, 'DF', 'qms 30a BSB',
        '73080100', 'f3eacd45-8525-49a4-9a95-dcdc3bc395c3');

insert into "TB_ADDRESS" ("ID_ADDRESS", "DT_INCLUSION", "CITY", "COMPLEMENT", "NEIGHBORHOOD", "ID_PERSON", "STATE",
                          "STREET", "ZIPCODE",
                          "UUID")
values (104, CURRENT_TIMESTAMP, 'Brasilia', 'la manos', 'Setor de Mansoes de Sobradinho', 105, 'DF', 'qms 30a BSB',
        '73080100', '75bbc9fc-5ba8-4c73-9245-5001fdaed0da');

-- Telephone
INSERT INTO "TB_PHONE" ("ID_PHONE", "ID_PERSON", "NUMBER", "AREA_CODE", "DT_INCLUSION", "UUID")
VALUES (103, 101, '996585455', 61, CURRENT_TIMESTAMP, 'fa05b736-bd12-4bc4-bb19-b0ab0373cdad');

--Weight
INSERT INTO "TB_WEIGHT_HEIGHT"("ID_WEIGHT_HEIGHT", "ID_PERSON", "WEIGHT", "HEIGHT", "DT_INCLUSION", "UUID")
VALUES (104, 101, '62', '1.77', CURRENT_TIMESTAMP, 'ecd6a0b6-c556-43a4-b136-f12637702353');

INSERT INTO "TB_WEIGHT_HEIGHT"("ID_WEIGHT_HEIGHT", "ID_PERSON", "WEIGHT", "HEIGHT", "DT_INCLUSION", "UUID")
VALUES (105, 101, '65', '1.99', CURRENT_TIMESTAMP, 'ecd6a0b6-c556-43a4-b136-f12637702355');