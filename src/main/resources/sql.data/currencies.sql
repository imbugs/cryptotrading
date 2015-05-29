-- phpMyAdmin SQL Dump
-- version 3.3.5
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: May 29, 2015 at 04:45 AM
-- Server version: 5.1.36
-- PHP Version: 5.3.26

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";

--
-- Database: `ctp_trading`
--

--
-- Dumping data for table `CURRENCIES`
--

INSERT INTO `CURRENCIES` (`DTYPE`,`CODE`, `DESCRIPTION`, `SYMBOL`) VALUES('CUR','USD', 'United States Dollar', '$');
INSERT INTO `CURRENCIES` (`DTYPE`,`CODE`, `DESCRIPTION`, `SYMBOL`) VALUES('CUR','EUR', 'Euro', '€');
INSERT INTO `CURRENCIES` (`DTYPE`,`CODE`, `DESCRIPTION`, `SYMBOL`) VALUES('CRC','BTC', 'Bitcoin', 'BTC');
INSERT INTO `CURRENCIES` (`DTYPE`,`CODE`, `DESCRIPTION`, `SYMBOL`) VALUES('CRC','LTC', 'Litecoin', 'LTC');
INSERT INTO `CURRENCIES` (`DTYPE`,`CODE`, `DESCRIPTION`, `SYMBOL`) VALUES('CRC','PPC', 'Peercoin', 'PPC');
