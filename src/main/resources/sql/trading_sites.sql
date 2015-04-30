-- phpMyAdmin SQL Dump
-- version 3.3.5
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: May 01, 2015 at 03:56 AM
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
-- Table structure for table `TRADING_SITES`
--

CREATE TABLE IF NOT EXISTS `TRADING_SITES` (
  `CODE` varchar(10) NOT NULL,
  `DESCRIPTION` varchar(30) NOT NULL,
  `URL` varchar(128) NOT NULL,
  PRIMARY KEY (`CODE`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `TRADING_SITES`
--

INSERT INTO `TRADING_SITES` (`CODE`, `DESCRIPTION`, `URL`) VALUES('BTCE', 'Btc-e', 'www.btc-e.com');
INSERT INTO `TRADING_SITES` (`CODE`, `DESCRIPTION`, `URL`) VALUES('KRAKEN', 'Kraken', 'www.kraken.com');
INSERT INTO `TRADING_SITES` (`CODE`, `DESCRIPTION`, `URL`) VALUES('BITSTAMP', 'Bitstamp', 'www.bitstamp.net');