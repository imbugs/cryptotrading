CREATE DATABASE  IF NOT EXISTS `crypto` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `crypto`;
-- MySQL dump 10.13  Distrib 5.5.34, for debian-linux-gnu (x86_64)
--
-- Host: localhost    Database: crypto
-- ------------------------------------------------------
-- Server version	5.5.34-0ubuntu0.13.04.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `TRADE_RULES`
--

DROP TABLE IF EXISTS `TRADE_RULES`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `TRADE_RULES` (
  `ID` int(11) NOT NULL,
  `DESCRIPTION` varchar(255) DEFAULT NULL,
  `ENABLED` bit(1) DEFAULT NULL,
  `MARKET` varchar(255) DEFAULT NULL,
  `TYPE` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `TRADE_RULES`
--

LOCK TABLES `TRADE_RULES` WRITE;
/*!40000 ALTER TABLE `TRADE_RULES` DISABLE KEYS */;
INSERT INTO `TRADE_RULES` VALUES (1,'Stierenmarkt, stieren signaal','','BULL','BULL'),(2,'Berenmarkt, beren signaal','','BEAR','BEAR'),(3,'Stierenmarkt, stieren signaal','','BULL','BULL'),(4,'Berenmarkt, beren signaal','','BEAR','BEAR'),(5,'Berenmarkt, beren signaal','','BEAR','BEAR'),(6,'Stierenmarkt, stieren signaal','','BULL','BULL'),(7,'Bull markt handelsregel','','BULL','BULL'),(8,'Beren markt handelsregel','','BEAR','BEAR'),(9,'Verkoop wanneer koers daalt onder 90% van daggemiddelde','','BEAR','BEAR');
/*!40000 ALTER TABLE `TRADE_RULES` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-09-05 22:01:29
