-- phpMyAdmin SQL Dump
-- version 3.3.5
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: May 01, 2015 at 04:00 AM
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
-- Table structure for table `WITHDRAWALS`
--

CREATE TABLE IF NOT EXISTS `WITHDRAWALS` (
  `TRADING_ID` int(11) NOT NULL,
  `COINS` float NOT NULL,
  `CURRENCY` varchar(10) NOT NULL,
  PRIMARY KEY (`TRADING_ID`,`CURRENCY`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
