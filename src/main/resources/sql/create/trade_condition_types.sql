-- phpMyAdmin SQL Dump
-- version 3.3.5
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: May 01, 2015 at 03:52 AM
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
-- Table structure for table `TRADE_CONDITION_TYPES`
--

CREATE TABLE IF NOT EXISTS `TRADE_CONDITION_TYPES` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(20) NOT NULL,
  `DESCRIPTION` varchar(128) NOT NULL,
  `MESSAGE_FORMAT` varchar(256) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=29 ;

--
-- Dumping data for table `TRADE_CONDITION_TYPES`
--

INSERT INTO `TRADE_CONDITION_TYPES` (`ID`, `NAME`, `DESCRIPTION`, `MESSAGE_FORMAT`) VALUES(1, 'POS_MACD_CHANGE', 'MACD rises from below zero to above zero', 'MACD <b>%s</b> stijgt door nullijn in een periode van <b>%d</b>');
INSERT INTO `TRADE_CONDITION_TYPES` (`ID`, `NAME`, `DESCRIPTION`, `MESSAGE_FORMAT`) VALUES(2, 'NEG_MACD_CHANGE', 'MACD moves from above zero to below zero', 'MACD <b>%s</b> daalt door nullijn  in een periode van <b>%d</b>');
INSERT INTO `TRADE_CONDITION_TYPES` (`ID`, `NAME`, `DESCRIPTION`, `MESSAGE_FORMAT`) VALUES(3, 'MACD_POSITIVE', 'MACD must be positive, greater then zero', 'MACD <b>%s</b> moet positief zijn voor een periode van <b>%d</b>');
INSERT INTO `TRADE_CONDITION_TYPES` (`ID`, `NAME`, `DESCRIPTION`, `MESSAGE_FORMAT`) VALUES(4, 'MACD_NEGATIVE', 'MACD must be less then zero', 'MACD <b>%s</b> moet negatief zijn voor een periode van <b>%d</b>');
INSERT INTO `TRADE_CONDITION_TYPES` (`ID`, `NAME`, `DESCRIPTION`, `MESSAGE_FORMAT`) VALUES(5, 'BTC_GT_TREND', 'Cryptcoin rate greater then trendline', 'Koers hoger dan trendlijn <b>%s</b> over een periode van <b>%d</b>');
INSERT INTO `TRADE_CONDITION_TYPES` (`ID`, `NAME`, `DESCRIPTION`, `MESSAGE_FORMAT`) VALUES(6, 'BTC_LT_TREND', 'Cryptocoin rate less then trendline', 'Koers lager dan trendlijn <b>%s</b> over een periode van <b>%d</b>');
INSERT INTO `TRADE_CONDITION_TYPES` (`ID`, `NAME`, `DESCRIPTION`, `MESSAGE_FORMAT`) VALUES(7, 'MACD_GT_PERC_TREND', 'MACD must be greater then a percentage of a trend', 'MACD <b>%s</b> moet hoger zijn dan <b>%3.2f</b> procent</b> van trendlijn <b>%s</b> voor een periode van <b>%d</b>');
INSERT INTO `TRADE_CONDITION_TYPES` (`ID`, `NAME`, `DESCRIPTION`, `MESSAGE_FORMAT`) VALUES(8, 'MACD_LS_PERC_TREND', 'MACD must be less then a percentage of a trend', 'MACD <b>%s</b> moet lager zijn dan <b>%3.2f procent</b> van trendlijn <b>%s</b> voor een periode van <b>%d</b>');
INSERT INTO `TRADE_CONDITION_TYPES` (`ID`, `NAME`, `DESCRIPTION`, `MESSAGE_FORMAT`) VALUES(9, 'POS_MACD_PERC', 'Positive MACD change in terms of percentage of a reference MACD', 'Stijging van MACD <b>%s</b> met  <b>%d procent</b> ten opzichte van MACD <b>%s</b> in een periode van <b>%d</b>');
INSERT INTO `TRADE_CONDITION_TYPES` (`ID`, `NAME`, `DESCRIPTION`, `MESSAGE_FORMAT`) VALUES(10, 'NEG_MACD_PERC', 'Negative MACD change in terms of percentage of a reference MACD', 'Daling van MACD <b>%s</b> met  <b>%3.2d procent</b> ten opzichte van MACD <b>%s</b> in een periode van <b>%d</b>');
INSERT INTO `TRADE_CONDITION_TYPES` (`ID`, `NAME`, `DESCRIPTION`, `MESSAGE_FORMAT`) VALUES(11, 'MACD_INCREASE', 'Gradual MACD increase over given period', 'Tussen <b>%3.2f en %3.2f procent</b>  stijging van MACD <b>%s</b>  in een periode van <b>%d</b>');
INSERT INTO `TRADE_CONDITION_TYPES` (`ID`, `NAME`, `DESCRIPTION`, `MESSAGE_FORMAT`) VALUES(12, 'MACD_DECREASE', 'Gradual MACD decrease over given period', 'Tussen <b>%3.2f en %3.2f procent</b> daling van MACD <b>%s</b>  in een periode van <b>%d</b>');
INSERT INTO `TRADE_CONDITION_TYPES` (`ID`, `NAME`, `DESCRIPTION`, `MESSAGE_FORMAT`) VALUES(13, 'MACD_DIFF_GT_PERC', 'MACD difference must be a percentage of reference MACD', 'Verschil tussen MACD <b>%s</b> en MACD <b>%s </b> moet groter zijn dan <b>%3.2f</b> procent van MACD <b>%s</b> voor een periode van <b>%d</b>');
INSERT INTO `TRADE_CONDITION_TYPES` (`ID`, `NAME`, `DESCRIPTION`, `MESSAGE_FORMAT`) VALUES(14, 'TREND_DIFF_GT_PERC', 'Trend difference must be a percentage of reference Trend', 'Trendverschil tussen <b>%s</b> en <b>%s </b> moet groter zijn dan <b>%3.2f</b> procent van trend <b>%s</b> voor een periode van <b>%d</b>');
INSERT INTO `TRADE_CONDITION_TYPES` (`ID`, `NAME`, `DESCRIPTION`, `MESSAGE_FORMAT`) VALUES(17, 'TREND_INCREASE', 'Gradual trend increase over given period', 'Tussen <b>%3.2f en %3.2f procent</b> stijging van trend <b>%s</b> in een periode van <b>%d</b>');
INSERT INTO `TRADE_CONDITION_TYPES` (`ID`, `NAME`, `DESCRIPTION`, `MESSAGE_FORMAT`) VALUES(15, 'MACD_DIFF_LT_PERC', 'MACD difference must be less then a percentage of reference MACD', 'Verschil tussen MACD <b>%s</b> en MACD <b>%s </b> moet kleiner zijn dan <b>%3.2f</b> procent van MACD <b>%s</b> voor een periode van <b>%d</b>');
INSERT INTO `TRADE_CONDITION_TYPES` (`ID`, `NAME`, `DESCRIPTION`, `MESSAGE_FORMAT`) VALUES(16, 'TREND_DIFF_LT_PERC', 'Trend difference must be less then a percentage of reference Trend', 'Trendverschil tussen <b>%s</b> en <b>%s </b> moet kleiner zijn dan <b>%3.2f</b> procent van trend <b>%s</b> voor een periode van <b>%d</b>');
INSERT INTO `TRADE_CONDITION_TYPES` (`ID`, `NAME`, `DESCRIPTION`, `MESSAGE_FORMAT`) VALUES(18, 'TREND_DECREASE', 'Gradual trend decrease over given period', 'Tussen <b>%3.2f en %3.2f procent</b> daling van trend <b>%s</b> in een periode van <b>%d</b>');
INSERT INTO `TRADE_CONDITION_TYPES` (`ID`, `NAME`, `DESCRIPTION`, `MESSAGE_FORMAT`) VALUES(19, 'POS_TREND_CHANGE', 'Positive trend change', 'Positieve trendverandering voor trend <b>%s</b> in een periode van <b>%d</b>');
INSERT INTO `TRADE_CONDITION_TYPES` (`ID`, `NAME`, `DESCRIPTION`, `MESSAGE_FORMAT`) VALUES(20, 'NEG_TREND_CHANGE', 'Negative trend change', 'Negatieve trendverandering voor trend <b>%s</b> in een periode van <b>%d</b>');
INSERT INTO `TRADE_CONDITION_TYPES` (`ID`, `NAME`, `DESCRIPTION`, `MESSAGE_FORMAT`) VALUES(21, 'POS_TREND', 'Positive trend', 'Positieve trend voor trend <b>%s</b> in een periode van <b>%d</b>');
INSERT INTO `TRADE_CONDITION_TYPES` (`ID`, `NAME`, `DESCRIPTION`, `MESSAGE_FORMAT`) VALUES(22, 'NEG_TREND', 'Negative trend', 'Negatieve trend voor trend <b>%s</b> in een periode van <b>%d</b>');
INSERT INTO `TRADE_CONDITION_TYPES` (`ID`, `NAME`, `DESCRIPTION`, `MESSAGE_FORMAT`) VALUES(23, 'BTC_LT_RATE', 'Exchange rate less then value', 'Koers lager dan waarde <b>%s</b> over een periode van <b>%d</b>');
INSERT INTO `TRADE_CONDITION_TYPES` (`ID`, `NAME`, `DESCRIPTION`, `MESSAGE_FORMAT`) VALUES(24, 'BTC_GT_RATE', 'Exchange rate greater then value', 'Koers hoger dan waarde <b>%s</b> over een periode van <b>%d</b>');
INSERT INTO `TRADE_CONDITION_TYPES` (`ID`, `NAME`, `DESCRIPTION`, `MESSAGE_FORMAT`) VALUES(25, 'DELTA_GT_PERC_TREND', 'Trend change less than a percentage of trend', 'Trendverandering lager dan waarde <b>%3.2f</b> procent van trend <b>%s</b> : over een periode van <b>%d</b>');
INSERT INTO `TRADE_CONDITION_TYPES` (`ID`, `NAME`, `DESCRIPTION`, `MESSAGE_FORMAT`) VALUES(26, 'DELTA_LT_PERC_TREND', 'Trend change less than a percentage of trend', 'Trendverandering lager dan waarde <b>%3.2f</b> procent van trend <b>%s</b> : over een periode van <b>%d</b>');
INSERT INTO `TRADE_CONDITION_TYPES` (`ID`, `NAME`, `DESCRIPTION`, `MESSAGE_FORMAT`) VALUES(27, 'BTC_GT_PERC_TREND', 'Cryptcoin rate greater then percentage of trendline', 'Koers hoger dan <b>%3.2f procent</b> van trendlijn <b>%s</b> over een periode van <b>%d</b>');
INSERT INTO `TRADE_CONDITION_TYPES` (`ID`, `NAME`, `DESCRIPTION`, `MESSAGE_FORMAT`) VALUES(28, 'BTC_LT_PERC_TREND', 'Cryptocoin rate less then percentage of trendline', 'Koers lager dan <b>%3.2f procent</b> van trendlijn <b>%s</b> over een periode van <b>%d</b>');