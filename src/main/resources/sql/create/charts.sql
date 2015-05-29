-- phpMyAdmin SQL Dump
-- version 3.3.5
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: May 01, 2015 at 03:34 AM
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
-- Table structure for table `CHARTS`
--

CREATE TABLE IF NOT EXISTS `CHARTS` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `TITLE` varchar(120) NOT NULL,
  `TYPE` varchar(10) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=4 ;

--
-- Dumping data for table `CHARTS`
--

INSERT INTO `CHARTS` (`ID`, `TITLE`, `TYPE`) VALUES(1, 'Bitcoin trend data', 'BTC');
INSERT INTO `CHARTS` (`ID`, `TITLE`, `TYPE`) VALUES(2, 'MACD datea', 'MACD');
INSERT INTO `CHARTS` (`ID`, `TITLE`, `TYPE`) VALUES(3, 'Delta trend data', 'DELTA');