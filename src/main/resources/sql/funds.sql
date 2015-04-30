-- phpMyAdmin SQL Dump
-- version 3.3.5
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: May 01, 2015 at 03:41 AM
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
-- Table structure for table `FUNDS`
--

CREATE TABLE IF NOT EXISTS `FUNDS` (
  `TRADE_PAIR_ID` int(11) NOT NULL,
  `CURRENCY` varchar(10) NOT NULL,
  `COINS` float NOT NULL,
  UNIQUE KEY `TRADE_PAIR_ID` (`TRADE_PAIR_ID`,`CURRENCY`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

