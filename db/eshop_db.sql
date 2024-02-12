-- MySQL dump 10.13  Distrib 8.0.34, for Win64 (x86_64)
--
-- Host: mysql80.r5.websupport.sk    Database: eshop_db
-- ------------------------------------------------------
-- Server version	8.0.23

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `app_user`
--

DROP TABLE IF EXISTS `app_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `app_user` (
  `id` char(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `surname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `role` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'ROLE_USER',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  UNIQUE KEY `username_UNIQUE` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `app_user`
--

LOCK TABLES `app_user` WRITE;
/*!40000 ALTER TABLE `app_user` DISABLE KEYS */;
INSERT INTO `app_user` VALUES ('d828ea79-c8f1-11ee-b215-309c23b0824d','user','user','user','$2a$12$wmjut..qJp7XZbi2q5veiOHcCou8uxdLVqo7I1NKlWWn1kkadCy9i','ROLE_USER'),('d828f0b1-c8f1-11ee-b215-309c23b0824d','admin','admin','admin','$2a$12$E1aEveSxNJ75jJ1CR.TGnOUTPQ1qOfffy08fre9Purym2y2H5eVUG','ROLE_ADMIN');
/*!40000 ALTER TABLE `app_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cart_item`
--

DROP TABLE IF EXISTS `cart_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cart_item` (
  `id` char(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `id_user` char(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `id_item` char(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `volume` int NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `FKnyv9elili7rv0sc82s3e91i0r` (`id_item`),
  KEY `FK7rjm2yx2xivcfhook7wvs885v` (`id_user`),
  CONSTRAINT `FK7rjm2yx2xivcfhook7wvs885v` FOREIGN KEY (`id_user`) REFERENCES `app_user` (`id`),
  CONSTRAINT `FKnyv9elili7rv0sc82s3e91i0r` FOREIGN KEY (`id_item`) REFERENCES `item` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cart_item`
--

LOCK TABLES `cart_item` WRITE;
/*!40000 ALTER TABLE `cart_item` DISABLE KEYS */;
/*!40000 ALTER TABLE `cart_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `item`
--

DROP TABLE IF EXISTS `item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `item` (
  `id` char(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `product_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `product_code` int NOT NULL,
  `price` double NOT NULL,
  `volume` int NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `ID_UNIQUE` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `item`
--

LOCK TABLES `item` WRITE;
/*!40000 ALTER TABLE `item` DISABLE KEYS */;
INSERT INTO `item` VALUES ('e485735f-c8f1-11ee-b215-309c23b0824d','Samsung Galaxy S21',3423,699.99,0),('e485772b-c8f1-11ee-b215-309c23b0824d','Google Pixel 6',7887,599.99,2),('e4857815-c8f1-11ee-b215-309c23b0824d','MacBook Air',7614,999.99,1),('e48578f4-c8f1-11ee-b215-309c23b0824d','Sony PlayStation 5',1188,499.99,2),('e48579ed-c8f1-11ee-b215-309c23b0824d','Xbox Series X',5559,499.99,3),('e4857aa9-c8f1-11ee-b215-309c23b0824d','Nintendo Switch',7386,299.99,0),('e4857bf8-c8f1-11ee-b215-309c23b0824d','Dell XPS 13',4984,899.99,3),('e4857d49-c8f1-11ee-b215-309c23b0824d','HP Spectre x360',1185,1099.99,4),('e4857e8c-c8f1-11ee-b215-309c23b0824d','Bose QuietComfort 35 II',7424,299.99,3),('e4857fb6-c8f1-11ee-b215-309c23b0824d','Sony WH-1000XM4',4852,349.99,4),('e48580f0-c8f1-11ee-b215-309c23b0824d','JBL Flip 5',4752,119.99,3),('e485821b-c8f1-11ee-b215-309c23b0824d','Apple Watch Series 7',9834,399.99,1),('e485835b-c8f1-11ee-b215-309c23b0824d','Fitbit Charge 5',3023,179.99,2),('e4858485-c8f1-11ee-b215-309c23b0824d','GoPro HERO10 Black',9241,399.99,5),('e48586cc-c8f1-11ee-b215-309c23b0824d','DJI Mavic Air 2',3976,799.99,4),('e4858808-c8f1-11ee-b215-309c23b0824d','Kindle Paperwhite',8974,129.99,5),('e485892f-c8f1-11ee-b215-309c23b0824d','iPad Pro',4036,799.99,1),('e4858a52-c8f1-11ee-b215-309c23b0824d','Samsung Galaxy Tab S7',3131,649.99,7),('e4858b62-c8f1-11ee-b215-309c23b0824d','Microsoft Surface Pro 7',7991,749.99,3),('e4858ca2-c8f1-11ee-b215-309c23b0824d','Asus ROG Phone 5',4792,699.99,1),('e4858ddf-c8f1-11ee-b215-309c23b0824d','Alienware m15 R4',1663,1999.99,8),('e4858f1c-c8f1-11ee-b215-309c23b0824d','LG OLED55C1',9935,1499.99,7),('e485909a-c8f1-11ee-b215-309c23b0824d','Sonos One',2024,199.99,8),('e48591d9-c8f1-11ee-b215-309c23b0824d','Canon EOS R5',8137,3899.99,6),('e4859320-c8f1-11ee-b215-309c23b0824d','Nikon Z6 II',1509,1999.99,5),('e4859436-c8f1-11ee-b215-309c23b0824d','Garmin Fenix 6',1917,599.99,3),('e4859552-c8f1-11ee-b215-309c23b0824d','Roku Streaming Stick+',7278,49.99,4),('e4859653-c8f1-11ee-b215-309c23b0824d','Philips Hue White and Color Ambiance',7533,99.99,3);
/*!40000 ALTER TABLE `item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order`
--

DROP TABLE IF EXISTS `order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order` (
  `id` char(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `id_user` char(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `date_time` datetime NOT NULL,
  `total_price` decimal(10,2) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `FKjxax6v0cwq6w6ekpylt0kd0ci` (`id_user`),
  CONSTRAINT `FKjxax6v0cwq6w6ekpylt0kd0ci` FOREIGN KEY (`id_user`) REFERENCES `app_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order`
--

LOCK TABLES `order` WRITE;
/*!40000 ALTER TABLE `order` DISABLE KEYS */;
INSERT INTO `order` VALUES ('582f43eb-f7e8-431b-a495-00812b60302e','d828f0b1-c8f1-11ee-b215-309c23b0824d','2024-02-11 15:37:28',1599.97),('82598980-a4e9-4a71-9319-ad1574061c5c','d828ea79-c8f1-11ee-b215-309c23b0824d','2024-02-12 10:38:28',599.99);
/*!40000 ALTER TABLE `order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_item`
--

DROP TABLE IF EXISTS `order_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order_item` (
  `id` char(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `id_user` char(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `id_item` char(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `volume` int NOT NULL,
  `id_order` char(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `FK9gfdm3wvhd3eyvvs1u5vdkau9` (`id_user`),
  KEY `FK1wa74xlhlae38x57e0hec77ef` (`id_item`),
  KEY `FK91bchbncxidkjypdysx5pvwyb` (`id_order`),
  CONSTRAINT `FK1wa74xlhlae38x57e0hec77ef` FOREIGN KEY (`id_item`) REFERENCES `item` (`id`),
  CONSTRAINT `FK91bchbncxidkjypdysx5pvwyb` FOREIGN KEY (`id_order`) REFERENCES `order` (`id`),
  CONSTRAINT `FK9gfdm3wvhd3eyvvs1u5vdkau9` FOREIGN KEY (`id_user`) REFERENCES `app_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_item`
--

LOCK TABLES `order_item` WRITE;
/*!40000 ALTER TABLE `order_item` DISABLE KEYS */;
INSERT INTO `order_item` VALUES ('154345d0-aa1a-4e8c-8f3c-c118af2a7e27','d828ea79-c8f1-11ee-b215-309c23b0824d','e485772b-c8f1-11ee-b215-309c23b0824d',1,'82598980-a4e9-4a71-9319-ad1574061c5c'),('735643a5-d1e2-45c0-b111-bc4debb8d104','d828f0b1-c8f1-11ee-b215-309c23b0824d','e485772b-c8f1-11ee-b215-309c23b0824d',1,'582f43eb-f7e8-431b-a495-00812b60302e'),('c6ce682a-399e-4dbb-9c24-ccdd7e371565','d828f0b1-c8f1-11ee-b215-309c23b0824d','e48579ed-c8f1-11ee-b215-309c23b0824d',2,'582f43eb-f7e8-431b-a495-00812b60302e');
/*!40000 ALTER TABLE `order_item` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-02-12 11:39:26
