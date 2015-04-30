-- phpMyAdmin SQL Dump
-- version 3.3.5
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: May 01, 2015 at 03:39 AM
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
-- Table structure for table `CRYPTOCOIN_TRENDS`
--

CREATE TABLE IF NOT EXISTS `CRYPTOCOIN_TRENDS` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `TRADE_PAIR_ID` int(11) DEFAULT NULL,
  `INDX` int(11) NOT NULL,
  `TREND_ID` int(11) DEFAULT NULL,
  `MACD_ID` int(11) DEFAULT NULL,
  `VALUE` float NOT NULL,
  `DELTA` float DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `TREND` (`TRADE_PAIR_ID`,`INDX`,`TREND_ID`),
  KEY `MACD` (`TRADE_PAIR_ID`,`INDX`,`MACD_ID`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=193 ;