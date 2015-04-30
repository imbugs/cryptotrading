-- phpMyAdmin SQL Dump
-- version 3.3.5
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: May 01, 2015 at 03:55 AM
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
-- Table structure for table `TRADINGS`
--

CREATE TABLE IF NOT EXISTS `TRADINGS` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `TRADE_PAIR_ID` int(11) NOT NULL,
  `MIN_TRADING_CRYPTOCURRENCY` float DEFAULT NULL,
  `MAX_TRADING_COINS_PERC` int(11) NOT NULL,
  `MAX_TRADING_CRYPTOCOINS_PERC` int(11) NOT NULL,
  `REFUND_PERC` float NOT NULL,
  `CHECK_BAD_BUY` tinyint(1) NOT NULL,
  `CHECK_BAD_SELL` tinyint(1) NOT NULL,
  `CHECK_BAD_SELL_WALLET` int(11) NOT NULL,
  `MIN_PROFIT_PERCENTAGE` float DEFAULT NULL,
  `ENABLED` tinyint(1) NOT NULL,
  `LOGGING` tinyint(1) NOT NULL,
  `RETRIES` int(11) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=7 ;

--
-- Dumping data for table `TRADINGS`
--

INSERT INTO `TRADINGS` (`ID`, `TRADE_PAIR_ID`, `MIN_TRADING_CRYPTOCURRENCY`, `MAX_TRADING_COINS_PERC`, `MAX_TRADING_CRYPTOCOINS_PERC`, `REFUND_PERC`, `CHECK_BAD_BUY`, `CHECK_BAD_SELL`, `CHECK_BAD_SELL_WALLET`, `MIN_PROFIT_PERCENTAGE`, `ENABLED`, `LOGGING`, `RETRIES`) VALUES(4, 2, 0.1, 100, 100, 100, 0, 1, 0, 0.5, 1, 1, 25);