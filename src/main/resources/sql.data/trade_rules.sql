-- phpMyAdmin SQL Dump
-- version 3.3.5
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Jun 11, 2015 at 05:18 AM
-- Server version: 5.1.36
-- PHP Version: 5.3.26

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";

--
-- Database: `ctp_trading`
--

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
