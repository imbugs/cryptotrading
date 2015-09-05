-- phpMyAdmin SQL Dump
-- version 3.3.5
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: May 01, 2015 at 03:37 AM
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
-- Table structure for table `CRYPTOCOIN_HISTORY`
--

CREATE TABLE IF NOT EXISTS `CRYPTOCOIN_HISTORY` (
  `INDX` int(9) NOT NULL,
  `TRADE_PAIR_ID` int(11) NOT NULL,
  `TIMESTAMP` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `LOW` float NOT NULL,
  `HIGH` float NOT NULL,
  `OPEN` float NOT NULL,
  `CLOSE` float NOT NULL,
  `VOLUME` float NOT NULL,
  PRIMARY KEY (`INDX`,`TRADE_PAIR_ID`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;