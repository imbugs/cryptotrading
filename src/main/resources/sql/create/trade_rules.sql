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
-- Table structure for table `TRADE_RULES`
--

CREATE TABLE IF NOT EXISTS `TRADE_RULES` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `TYPE` varchar(5) NOT NULL,
  `MARKET` varchar(10) NOT NULL,
  `DESCRIPTION` varchar(128) NOT NULL,
  `ENABLED` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=10 ;

--
-- Dumping data for table `TRADE_RULES`
--

INSERT INTO `TRADE_RULES` (`ID`, `TYPE`, `MARKET`, `DESCRIPTION`, `ENABLED`) VALUES(1, 'BULL', 'BULL', 'Stierenmarkt, stieren signaal', 1);
INSERT INTO `TRADE_RULES` (`ID`, `TYPE`, `MARKET`, `DESCRIPTION`, `ENABLED`) VALUES(6, 'BULL', 'BULL', 'Stierenmarkt, stieren signaal', 1);
INSERT INTO `TRADE_RULES` (`ID`, `TYPE`, `MARKET`, `DESCRIPTION`, `ENABLED`) VALUES(5, 'BEAR', 'BEAR', 'Berenmarkt, beren signaal', 1);
INSERT INTO `TRADE_RULES` (`ID`, `TYPE`, `MARKET`, `DESCRIPTION`, `ENABLED`) VALUES(2, 'BEAR', 'BEAR', 'Berenmarkt, beren signaal', 1);
INSERT INTO `TRADE_RULES` (`ID`, `TYPE`, `MARKET`, `DESCRIPTION`, `ENABLED`) VALUES(4, 'BEAR', 'BEAR', 'Berenmarkt, beren signaal', 1);
INSERT INTO `TRADE_RULES` (`ID`, `TYPE`, `MARKET`, `DESCRIPTION`, `ENABLED`) VALUES(3, 'BULL', 'BULL', 'Stierenmarkt, stieren signaal', 1);
INSERT INTO `TRADE_RULES` (`ID`, `TYPE`, `MARKET`, `DESCRIPTION`, `ENABLED`) VALUES(7, 'BULL', 'BULL', 'Bull markt handelsregel', 1);
INSERT INTO `TRADE_RULES` (`ID`, `TYPE`, `MARKET`, `DESCRIPTION`, `ENABLED`) VALUES(8, 'BEAR', 'BEAR', 'Beren markt handelsregel', 1);
INSERT INTO `TRADE_RULES` (`ID`, `TYPE`, `MARKET`, `DESCRIPTION`, `ENABLED`) VALUES(9, 'BEAR', 'BEAR', 'Verkoop wanneer koers daalt onder 90% van daggemiddelde', 1);