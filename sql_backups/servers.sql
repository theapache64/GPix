-- MySQL dump 10.13  Distrib 5.5.47, for debian-linux-gnu (x86_64)
--
-- Host: 35.161.57.139    Database: gpix
-- ------------------------------------------------------
-- Server version	5.5.52-0ubuntu0.14.04.1

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
-- Table structure for table `servers`
--

DROP TABLE IF EXISTS `servers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `servers` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `_name` varchar(10) NOT NULL,
  `authorization_key` varchar(20) NOT NULL,
  `data_url_format` text NOT NULL,
  `is_active` tinyint(4) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`),
  UNIQUE KEY `_name` (`_name`)
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `servers`
--

LOCK TABLES `servers` WRITE;
/*!40000 ALTER TABLE `servers` DISABLE KEYS */;
INSERT INTO `servers` VALUES (1,'SERVER 1','mySecretServerKey','http://gpix_server1.net23.net/?keyword=%s',1),(2,'SERVER 2','mySecretServerKey','http://gpix-server2.000webhostapp.com/?keyword=%s',1),(3,'SERVER 3','mySecretServerKey','http://gpix-server3.000webhostapp.com/?keyword=%s',1),(4,'SERVER 4','mySecretServerKey','http://gpix_server4.netai.net/?keyword=%s',1),(5,'SERVER 5','mySecretServerKey','http://gpix_server5.comuf.com/?keyword=%s',1),(7,'SERVER 1.1','mySecretServerKey','http://theapache64server1.6te.net/?keyword=%s',1),(8,'6te.net 1','mySecretServerKey','http://theapache64server1.6te.net/?keyword=%s',1),(9,'6te.net 2','mySecretServerKey','http://theapache64server2.6te.net/?keyword=%s',1),(10,'6te.net 3','mySecretServerKey','http://theapache64server3.6te.net/?keyword=%s',1),(11,'6te.net 4','mySecretServerKey','http://theapache64server4.6te.net/?keyword=%s',1),(12,'6te.net 5','mySecretServerKey','http://theapache64server5.6te.net/?keyword=%s',1),(13,'6te.net 6','mySecretServerKey','http://theapache64server6.6te.net/?keyword=%s',1),(14,'6te.net 7','mySecretServerKey','http://theapache64server7.6te.net/?keyword=%s',1),(15,'6te.net 8','mySecretServerKey','http://theapache64server8.6te.net/?keyword=%s',1),(16,'6te.net 9','mySecretServerKey','http://theapache64server9.6te.net/?keyword=%s',1),(17,'6te.net 10','mySecretServerKey','http://theapache64server10.6te.net/?keyword=%s',1),(18,'6te.net 11','mySecretServerKey','http://theapache64server11.6te.net/?keyword=%s',1),(19,'6te.net 12','mySecretServerKey','http://theapache64server12.6te.net/?keyword=%s',1),(20,'6te.net 13','mySecretServerKey','http://theapache64server13.6te.net/?keyword=%s',1),(21,'6te.net 14','mySecretServerKey','http://theapache64server14.6te.net/?keyword=%s',1),(22,'6te.net 15','mySecretServerKey','http://theapache64server15.6te.net/?keyword=%s',1),(23,'6te.net 16','mySecretServerKey','http://theapache64server16.6te.net/?keyword=%s',1),(24,'6te.net 17','mySecretServerKey','http://theapache64server17.6te.net/?keyword=%s',1),(25,'6te.net 18','mySecretServerKey','http://theapache64server18.6te.net/?keyword=%s',1),(26,'6te.net 19','mySecretServerKey','http://theapache64server19.6te.net/?keyword=%s',1),(27,'6te.net 20','mySecretServerKey','http://theapache64server20.6te.net/?keyword=%s',1),(28,'6te.net 21','mySecretServerKey','http://theapache64server21.6te.net/?keyword=%s',1),(29,'6te.net 22','mySecretServerKey','http://theapache64server22.6te.net/?keyword=%s',1),(30,'6te.net 23','mySecretServerKey','http://theapache64server23.6te.net/?keyword=%s',1),(31,'6te.net 24','mySecretServerKey','http://theapache64server24.6te.net/?keyword=%s',1),(32,'6te.net 25','mySecretServerKey','http://theapache64server25.6te.net/?keyword=%s',1),(33,'6te.net 26','mySecretServerKey','http://theapache64server26.6te.net/?keyword=%s',1),(34,'6te.net 27','mySecretServerKey','http://theapache64server27.6te.net/?keyword=%s',1),(35,'6te.net 28','mySecretServerKey','http://theapache64server28.6te.net/?keyword=%s',1),(36,'6te.net 29','mySecretServerKey','http://theapache64server29.6te.net/?keyword=%s',1),(37,'6te.net 30','mySecretServerKey','http://theapache64server30.6te.net/?keyword=%s',1);
/*!40000 ALTER TABLE `servers` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-11-06  7:21:55
