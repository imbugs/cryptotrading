-- phpMyAdmin SQL Dump
-- version 3.3.5
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: May 01, 2015 at 03:48 AM
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
-- Table structure for table `PROGRESS`
--

CREATE TABLE IF NOT EXISTS `PROGRESS` (
  `PROCESS_NAME` varchar(80) NOT NULL,
  `TOTAL` int(11) NOT NULL,
  `CURRENT` int(11) NOT NULL,
  `STATUS` varchar(10) NOT NULL,
  PRIMARY KEY (`PROCESS_NAME`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `PROGRESS`
--
