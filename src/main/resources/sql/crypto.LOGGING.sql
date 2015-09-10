-- phpMyAdmin SQL Dump
-- version 3.3.5
-- http://www.phpmyadmin.net
--
-- Machine: 127.0.0.1
-- Genereertijd: 11 Sept 2015 om 05:59
-- Serverversie: 5.1.36
-- PHP-Versie: 5.3.26

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";

--
-- Database: `ctp_trading`
--

--
-- Gegevens worden uitgevoerd voor tabel `LOGGING`
--

INSERT INTO `LOGGING` (`ID`, `TIMESTAMP`, `TRADING_ID`, `INDX`, `LEVEL`, `MESSAGE`) VALUES(1, '2015-05-01 00:40:05', 1, NULL, 'DEBUG', 'Availiable fund coins: 331.88');
INSERT INTO `LOGGING` (`ID`,`TIMESTAMP`, `TRADING_ID`, `INDX`, `LEVEL`, `MESSAGE`) VALUES(2, '2015-05-01 00:40:05', 1, NULL, 'DEBUG', 'Available fund cryptocoins: 0');
INSERT INTO `LOGGING` (`ID`,`TIMESTAMP`, `TRADING_ID`, `INDX`, `LEVEL`, `MESSAGE`) VALUES(3, '2015-05-01 00:40:05', 1, NULL, 'DEBUG', 'Wallet coins: 0');
INSERT INTO `LOGGING` (`ID`,`TIMESTAMP`, `TRADING_ID`, `INDX`, `LEVEL`, `MESSAGE`) VALUES(4, '2015-05-01 00:40:05', 1, NULL, 'DEBUG', 'Wallet crypto coins: 0');
INSERT INTO `LOGGING` (`ID`,`TIMESTAMP`, `TRADING_ID`, `INDX`, `LEVEL`, `MESSAGE`) VALUES(5, '2015-05-01 00:40:05', 1, NULL, 'DEBUG', 'Refund coins: 331.88');
INSERT INTO `LOGGING` (`ID`,`TIMESTAMP`, `TRADING_ID`, `INDX`, `LEVEL`, `MESSAGE`) VALUES(6, '2015-05-01 00:40:05', 1, NULL, 'DEBUG', 'No withdrawal of cryptocoins possible');
INSERT INTO `LOGGING` (`ID`,`TIMESTAMP`, `TRADING_ID`, `INDX`, `LEVEL`, `MESSAGE`) VALUES(7, '2015-05-01 00:40:05', 1, 206, 'INFO', 'Buy signal received');
INSERT INTO `LOGGING` (`ID`,`ID`,`TIMESTAMP`, `TRADING_ID`, `INDX`, `LEVEL`, `MESSAGE`) VALUES(8, '2015-05-01 00:40:06', 1, 206, 'INFO', 'Kraken buy order succesfully created with order reference: O56ROM-67PMX-6Q7RMJ');


