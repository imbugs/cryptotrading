-- phpMyAdmin SQL Dump
-- version 3.3.5
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: May 29, 2015 at 06:03 PM
-- Server version: 5.1.36
-- PHP Version: 5.3.26

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";

--
-- Database: `ctp_trading`
--

--
-- Dumping data for table `TRENDS`
--

INSERT INTO `TRENDS` (`ID`, `TREND_TYPE`, `PERIOD`, `SMOOTHING_TREND_ID`) VALUES(2, 'EMA', 150, NULL);
INSERT INTO `TRENDS` (`ID`, `TREND_TYPE`, `PERIOD`, `SMOOTHING_TREND_ID`) VALUES(1, 'EMA', 50, NULL);
INSERT INTO `TRENDS` (`ID`, `TREND_TYPE`, `PERIOD`, `SMOOTHING_TREND_ID`) VALUES(3, 'EMA', 30, NULL);
INSERT INTO `TRENDS` (`ID`, `TREND_TYPE`, `PERIOD`, `SMOOTHING_TREND_ID`) VALUES(4, 'SMA', 30, 3);
INSERT INTO `TRENDS` (`ID`, `TREND_TYPE`, `PERIOD`, `SMOOTHING_TREND_ID`) VALUES(5, 'EMA', 10, NULL);
INSERT INTO `TRENDS` (`ID`, `TREND_TYPE`, `PERIOD`, `SMOOTHING_TREND_ID`) VALUES(6, 'SMA', 10, 5);
INSERT INTO `TRENDS` (`ID`, `TREND_TYPE`, `PERIOD`, `SMOOTHING_TREND_ID`) VALUES(7, 'EMA', 1440, NULL);
