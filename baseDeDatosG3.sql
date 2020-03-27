DROP DATABASE IF EXISTS retog3;
CREATE DATABASE retog3;
USE retog3;

CREATE TABLE SERIE (
serie varchar(10) not null,
fechaFabr date not null default '00-00-0000',
marca varchar(20) not null default '0',
modelo varchar(20) not null default '0',
PRIMARY KEY (serie));

CREATE TABLE VEHICULO (
matricula varchar(15) not null,
numBastidor int null default '0',
serie varchar(10) not null default '0',
color varchar(15) not null default '0',
esPintado bool not null default '0',
numAsientos int not null default '0',
precio double not null default '0',
tipo varchar(10) not null default '0',
PRIMARY KEY (matricula));

CREATE TABLE CAMION (
matricula varchar(15) not null,
carga varchar(15) not null default '0',
tipoMercancia char not null default '0',
PRIMARY KEY (matricula));

CREATE TABLE COCHE (
matricula varchar(15) not null,
numPuertas int not null default '0',
capMaletero int not null default '0',
PRIMARY KEY (matricula));

CREATE TABLE VEHICULO_R (
matricula varchar(15) not null,
numBastidor int not null default '0',
tipo varchar(10) not null default '0',
precio double not null default '0',
color varchar(15) not null default '0',
numAsientos int not null default '0',
serie varchar(10) not null default '0',
accion varchar(10) not null default '0',
PRIMARY KEY (matricula));

CREATE TABLE COCHE_R (
matricula varchar(15),
numPuertas int not null default '0',
capMaletero int not null default '0',
PRIMARY KEY (matricula));

CREATE TABLE CAMION_R (
matricula varchar(15),
carga varchar(15) not null default '0',
tipoMercancia char not null default '0',
PRIMARY KEY (matricula));

-- TRIGGERS QUE ACTUALIZAN LOS PINTADOS--

DROP TRIGGER IF EXISTS TR_update;
CREATE TRIGGER TR_update
AFTER UPDATE ON vehiculo
FOR EACH ROW 
UPDATE VEHICULO_R
SET color = new.color, esPintado = 1
WHERE matricula = new.matricula;

DROP TRIGGER IF EXISTS TR_update;
CREATE TRIGGER TR_update
BEFORE DELETE ON vehiculo
FOR EACH ROW 
UPDATE VEHICULO_R
SET accion = 'Vendido'
WHERE matricula = vehiculo.matricula;


-- TRIGGERS QUE INSERTAN LOS DATOS EN EL HISTORIAL --
DROP TRIGGER IF EXISTS TR_registro;
CREATE TRIGGER TR_registro
AFTER INSERT ON VEHICULO
FOR EACH ROW
INSERT INTO VEHICULO_R (matricula, numBastidor, tipo, precio, color, numAsientos, serie, accion)
VALUES (new.matricula, new.numBastidor, new.tipo, new.precio, new.color, new.numAsientos, new.serie, 'Compra');

DROP TRIGGER IF EXISTS TR_registro_c;
CREATE TRIGGER TR_registro_c
AFTER INSERT ON COCHE
FOR EACH ROW
INSERT INTO COCHE_R (matricula, numPuertas, capMaletero)
VALUES (new.matricula, new.numPuertas, new.capMaletero);

DROP TRIGGER IF EXISTS TR_registro_ca;
CREATE TRIGGER TR_registro_ca
AFTER INSERT ON CAMION
FOR EACH ROW
INSERT INTO CAMION_R (matricula, carga, tipoMercancia)
VALUES (new.matricula, new.carga, new.tipoMercancia);
