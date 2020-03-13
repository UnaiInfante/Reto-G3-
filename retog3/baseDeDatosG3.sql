DROP DATABASE IF EXISTS retog3;
CREATE DATABASE retog3;
USE retog3;

CREATE TABLE registro (
codRegistro int,
usuario varchar(20),
fecha date,
PRIMARY KEY (codRegistro));

CREATE TABLE vehiculos (
numBastidor int,
matricula varchar(7),
precio double,
color varchar(15),
numAsientos int,
codRegistro int,
codSerie int,
PRIMARY KEY (numBastidor),
FOREIGN KEY (codRegistro) REFERENCES registro (codRegistro));

CREATE TABLE camiones (
numBastidor int,
carga int,
tipoMercancia char,
PRIMARY KEY (numBastidor),
FOREIGN KEY (numBastidor) REFERENCES vehiculos (numBastidor));

CREATE TABLE coches (
numBastidor int,
numPuertas int,
capMaletero int,
PRIMARY KEY (numBastidor),
FOREIGN KEY (numBastidor) REFERENCES vehiculos (numBastidor));

CREATE TABLE series (
codSerie int,
fechaFabr date,
marca varchar(20),
modelo varchar(20),
PRIMARY KEY (codSerie));

CREATE TABLE operaciones (
codOperacion int,
tipo varchar(10),
usuario varchar(20),
fecha date,
codRegistro int,
PRIMARY KEY (codOperacion));

CREATE TABLE vehiculos_operacion (
numBastidor int,
codOperacion int,
PRIMARY KEY (numBastidor, codOperacion));


