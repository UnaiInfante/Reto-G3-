DROP DATABASE IF EXISTS RETOG3;
CREATE DATABASE RETOG3;
USE RETOG3;

CREATE TABLE VEHICULOS (
numBastidor int,
matricula varchar(7),
precio double,
color varchar(15),
numAsientos int,
codRegistro int,
codSerie int,
PRIMARY KEY (numBastidor),
FOREIGN KEY (codRegistro) REFERENCES REGISTROS (codRegistro));

CREATE TABLE CAMIONES (
numBastidor int,
carga int,
tipoMercancia char,
PRIMARY KEY (numBastidor),
FOREIGN KEY (numBastidor) REFERENCES VEHICULOS (numBastidor));

CREATE TABLE COCHES (
numBastidor int,
numPuertas int,
capMaletero int,
PRIMARY KEY (numBastidor),
FOREIGN KEY (numBastidor) REFERENCES VEHICULOS (numBastidor));

CREATE TABLE SERIE (
codSerie int,
fechaFabr date,
marca varchar(20),
modelo varchar(20),
PRIMARY KEY (codSerie));

CREATE TABLE VEHICULOSOPERACION (
numBastidor int,
codOperacion int,
PRIMARY KEY (numBastidor, codOperacion));

CREATE TABLE OPERACIONES (
codOperacion int,
tipo varchar(10),
usuario varchar(20),
fecha date,
codRegistro int,
PRIMARY KEY (codOperacion));

CREATE TABLE REGISTRO (
codRegistro int,
fecha date,
usuario varchar(20),
PRIMARY KEY (codRegistro));