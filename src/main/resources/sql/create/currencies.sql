-- phpMyAdmin SQL Dump
-- version 3.3.5
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: May 01, 2015 at 03:40 AM
-- Server version: 5.1.36
-- PHP Version: 5.3.26

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `ctp_trading`
--

-- --------------------------------------------------------

--
-- Table structure for table `CURRENCIES`
--

CREATE TABLE IF NOT EXISTS `CURRENCIES` (
  `CODE` varchar(10) NOT NULL,
  `DESCRIPTION` varchar(20) NOT NULL,
  `SYMBOL` varchar(10) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`CODE`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `CURRENCIES`
--

INSERT INTO `CURRENCIES` (`CODE`, `DESCRIPTION`, `SYMBOL`) VALUES('USD', 'United States Dollar', '$');
INSERT INTO `CURRENCIES` (`CODE`, `DESCRIPTION`, `SYMBOL`) VALUES('EUR', 'Euro', '€');
INSERT INTO `CURRENCIES` (`CODE`, `DESCRIPTION`, `SYMBOL`) VALUES('BTC', 'Bitcoin', 'BTC');
INSERT INTO `CURRENCIES` (`CODE`, `DESCRIPTION`, `SYMBOL`) VALUES('LTC', 'Litecoin', 'LTC');
INSERT INTO `CURRENCIES` (`CODE`, `DESCRIPTION`, `SYMBOL`) VALUES('PPC', 'Peercoin', 'PPC');