DROP DATABASE IF EXISTS retog3;
CREATE DATABASE retog3;
USE retog3;

CREATE TABLE SERIE (
serie varchar(10) not null,
fechaFabr date not null,
marca varchar(20) not null,
modelo varchar(20) not null,
PRIMARY KEY (serie));

CREATE TABLE VEHICULO (
matricula varchar(15) not null,
numBastidor int null,
serie varchar(10) not null,
color varchar(15) not null,
esPintado int not null,
numAsientos int not null,
precio double not null,
tipo varchar(10) not null,
PRIMARY KEY (matricula));

CREATE TABLE CAMION (
matricula varchar(15) not null,
carga int not null,
tipoMercancia char not null,
PRIMARY KEY (matricula));

CREATE TABLE COCHE (
matricula varchar(15) not null,
numPuertas int not null,
capMaletero int not null,
PRIMARY KEY (matricula));

CREATE TABLE VEHICULO_R (
matricula varchar(15) not null,
numBastidor int not null,
tipo varchar(10) not null,
precio double not null,
color varchar(15) not null,
numAsientos int not null,
serie int not null,
accion varchar(10) not null,
usuario varchar(20) not null,
fecha date not null,
PRIMARY KEY (matricula));

CREATE TABLE COCHE_R (
matricula varchar(15),
numPuertas int not null,
capMaletero int not null,
PRIMARY KEY (matricula));

CREATE TABLE CAMION_R (
matricula varchar(15),
carga varchar(15) not null,
tipoCarga char not null,
PRIMARY KEY (matricula));

/*
-- TRIGGERS QUE ACTUALIZAN LAS COMPRAS--

DROP TRIGGER IF EXISTS TR_comprar_coche;
CREATE TRIGGER TR_comprar_coche
BEFORE INSERT ON COCHE
FOR EACH ROW
INSERT INTO VEHICULO_R (matricula, numBastidor, tipo, precio, color, numAsientos, serie, accion, usuario, fecha)
VALUES (new.matricula, new.numBastidor, 'Coche', new.precio, new.color, new.numAsientos, new.serie, 'Compra', current_user(), current_date());

DROP TRIGGER IF EXISTS TR_comprar_camion;
CREATE TRIGGER TR_comprar_camion
AFTER INSERT ON CAMION
FOR EACH ROW
INSERT INTO VEHICULO_R (numBastidor, matricula, tipo, precio, color, numAsientos, codSerie, accion, usuario, fecha)
VALUES (new.matricula, new.numBastidor, 'Coche', new.precio, new.color, new.numAsientos, new.serie, 'Compra', current_user(), current_date());

DROP TRIGGER IF EXISTS TR_insert_vehiculo;
CREATE TRIGGER TR_insert_vehiculo
BEFORE INSERT ON coches
FOR EACH ROW
INSERT INTO vehiculos (numBastidor, matricula, tipo, precio, color, numAsientos, codSerie)
VALUES (new.numBastidor, new.matricula, 'Coche', new.precio, new.color, new.numAsientos, new.codSerie);

DROP TRIGGER IF EXISTS TR_insert_vehiculo2;
CREATE TRIGGER TR_insert_vehiculo2
BEFORE INSERT ON camiones
FOR EACH ROW
INSERT INTO vehiculos (numBastidor, matricula, tipo, precio, color, numAsientos, codSerie)
VALUES (new.numBastidor, new.matricula, 'Camion', new.precio, new.color, new.numAsientos, new.codSerie);

-- TRIGGERS QUE ACTUALIZAN LAS VENTAS--

DROP TRIGGER IF EXISTS TR_vender_coche;
CREATE TRIGGER TR_vender_coche
AFTER DELETE ON coches
FOR EACH ROW 
INSERT INTO registro (numBastidor, matricula, tipo, precio, color, numAsientos, codSerie, accion, usuario, fecha)
VALUES (old.numBastidor, old.matricula, 'Coche', old.precio, old.color, old.numAsientos, old.codSerie, 'Venta', current_user(), current_date());

DROP TRIGGER IF EXISTS TR_vender_camion;
CREATE TRIGGER TR_vender_camion
AFTER DELETE ON camiones
FOR EACH ROW 
INSERT INTO registro (numBastidor, matricula, tipo, precio, color, numAsientos, codSerie, accion, usuario, fecha)
VALUES (old.numBastidor, old.matricula, 'Camion', old.precio, old.color, old.numAsientos, old.codSerie, 'Venta', current_user(), current_date());

DROP TRIGGER IF EXISTS TR_delete_vehiculo;
CREATE TRIGGER TR_delete_vehiculo
AFTER DELETE ON coches
FOR EACH ROW
DELETE FROM vehiculos
WHERE numBastidor = old.numBastidor;

DROP TRIGGER IF EXISTS TR_delete_vehiculo2;
CREATE TRIGGER TR_delete_vehiculo2
AFTER DELETE ON camiones
FOR EACH ROW
DELETE FROM vehiculos
WHERE numBastidor = old.numBastidor;

-- TRIGGERS QUE ACTUALIZAN LOS PINTADOS--

DROP TRIGGER IF EXISTS TR_pintar_coche;
CREATE TRIGGER TR_pintar_coche
AFTER UPDATE ON coches
FOR EACH ROW
INSERT INTO registro (numBastidor, matricula, tipo, precio, color, numAsientos, codSerie, accion, usuario, fecha)
VALUES (new.numBastidor, new.matricula, 'Coche', new.precio, new.color, new.numAsientos, new.codSerie, 'Pintada', current_user(), current_date());

DROP TRIGGER IF EXISTS TR_pintar_camion;
CREATE TRIGGER TR_pintar_camion
AFTER UPDATE ON camiones
FOR EACH ROW
INSERT INTO registro (numBastidor, matricula, tipo, precio, color, numAsientos, codSerie, accion, usuario, fecha)
VALUES (new.numBastidor, new.matricula, 'Camion', new.precio, new.color, new.numAsientos, new.codSerie, 'Pintada', current_user(), current_date());

DROP TRIGGER IF EXISTS TR_update_vehiculo;
CREATE TRIGGER TR_update_vehiculo
AFTER UPDATE ON coches
FOR EACH ROW
UPDATE vehiculos
set color = new.color
where numBastidor = new.numBastidor;

DROP TRIGGER IF EXISTS TR_update_vehiculo;
CREATE TRIGGER TR_update_vehiculo
AFTER UPDATE ON camiones
FOR EACH ROW
UPDATE vehiculos
set color = new.color
where numBastidor = new.numBastidor;
*/