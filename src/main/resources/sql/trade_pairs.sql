-- phpMyAdmin SQL Dump
-- version 3.3.5
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: May 01, 2015 at 03:53 AM
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
-- Table structure for table `TRADE_PAIRS`
--

CREATE TABLE IF NOT EXISTS `TRADE_PAIRS` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `TRADING_SITE_CODE` varchar(10) NOT NULL,
  `CURRENCY_CODE` varchar(10) NOT NULL,
  `CRYPTO_CURRENCY_CODE` varchar(10) NOT NULL,
  `TRANSACTION_FEE` float NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=4 ;

--
-- Dumping data for table `TRADE_PAIRS`
--

INSERT INTO `TRADE_PAIRS` (`ID`, `TRADING_SITE_CODE`, `CURRENCY_CODE`, `CRYPTO_CURRENCY_CODE`, `TRANSACTION_FEE`) VALUES
(2, 'KRAKEN', 'EUR', 'BTC', 0.2);