-- phpMyAdmin SQL Dump
-- version 3.3.5
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Jun 25, 2015 at 04:32 AM
-- Server version: 5.1.36
-- PHP Version: 5.3.26

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";

--
-- Database: `ctp_trading`
--

-- --------------------------------------------------------

--
-- Table structure for table `MACD`
--

CREATE TABLE IF NOT EXISTS `MACD` (
  `ID` int(11) NOT NULL,
  `SHORT_TREND_ID` int(11) NOT NULL,
  `LONG_TREND_ID` int(11) NOT NULL,
  `DESCRIPTION` varchar(80) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `MACD`
--

INSERT INTO `MACD` (`ID`, `SHORT_TREND_ID`, `LONG_TREND_ID`, `DESCRIPTION`) VALUES(1, 1, 2, 'EMA50-EMA150');
