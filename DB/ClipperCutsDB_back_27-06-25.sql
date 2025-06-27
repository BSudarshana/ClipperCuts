-- MySQL dump 10.13  Distrib 8.0.42, for Win64 (x86_64)
--
-- Host: localhost    Database: clippercuts
-- ------------------------------------------------------
-- Server version	8.0.42

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
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `category` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` VALUES (1,'Hair Care'),(2,'Face Care'),(3,'Skin Care'),(4,'Makeup'),(5,'Body Care');
/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customer`
--

DROP TABLE IF EXISTS `customer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customer` (
  `id` int NOT NULL AUTO_INCREMENT,
  `fullname` varchar(45) DEFAULT NULL,
  `callingname` varchar(45) DEFAULT NULL,
  `code` char(15) DEFAULT NULL,
  `address` text,
  `mobile` char(10) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  `photo` longblob,
  `gender_id` int NOT NULL,
  `doregistered` date DEFAULT NULL,
  `customertype_id` int NOT NULL,
  `customerstatus_id` int NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `code_UNIQUE` (`code`),
  UNIQUE KEY `mobile_UNIQUE` (`mobile`),
  KEY `fk_customer_customertype1_idx` (`customertype_id`),
  KEY `fk_customer_customerstatus1_idx` (`customerstatus_id`),
  KEY `fk_customer_gender1_idx` (`gender_id`),
  CONSTRAINT `fk_customer_customerstatus1` FOREIGN KEY (`customerstatus_id`) REFERENCES `customerstatus` (`id`),
  CONSTRAINT `fk_customer_customertype1` FOREIGN KEY (`customertype_id`) REFERENCES `customertype` (`id`),
  CONSTRAINT `fk_customer_gender1` FOREIGN KEY (`gender_id`) REFERENCES `gender` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=97 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer`
--

LOCK TABLES `customer` WRITE;
/*!40000 ALTER TABLE `customer` DISABLE KEYS */;
INSERT INTO `customer` VALUES (1,'John Doe','John','C1234567','1234 Elm Street','0712345678','john.doe@example.com',NULL,1,'2025-03-30',1,1),(80,'Tom Lee','Tom','C1234569','9101 Pine Road','0734567890','tom.lee@example.com',NULL,1,'2025-03-30',1,2),(81,'Sara Adams','Sara','C1234570','2345 Oak Drive','0745678901','sara.adams@example.com',NULL,2,'2025-03-30',3,2),(82,'Peter Brown','Peter','C1234571','6789 Birch Lane','0756789012','peter.brown@example.com',NULL,1,'2025-03-30',1,3),(83,'Emily Davis','Emily','C1234572','3456 Cedar Street','0767890123','emily.davis@example.com',NULL,2,'2025-03-30',2,1),(84,'Michael Clark','Mike','C1234573','4567 Maple Lane','0778901234','michael.clark@example.com',NULL,1,'2025-03-30',3,1),(85,'Rachel White','Rachel','C1234574','7890 Elm Street','0789012345','rachel.white@example.com',NULL,2,'2025-03-30',1,2),(86,'David Green','David','C1234575','1122 Pine Avenue','0790123456','david.green@example.com',NULL,1,'2025-03-30',2,3),(87,'Linda King','Linda','C1234576','3344 Oak Road','0701234567','linda.king@example.com',NULL,2,'2025-03-30',3,2),(88,'Buddhika Wanniarachchi','Buddhika','C1234583','6789 Birch Lane','0710714400','peter.brown@example.com',NULL,1,'2025-03-30',1,3),(89,'Sugath Nishantha','Nishantha','C1234584','6789 Moratuwa','0710714455','nishantha.brown@example.com',NULL,1,'2025-04-30',1,3),(90,'Sugath Nishantha','Nishantha','C0000001','6789 Moratuwa','0710714465','nishantha.brown@example.com',NULL,1,NULL,1,3),(91,'John Doe','John','C1234599','1234 Elm Street','0712345600','john.doe@example.com',NULL,1,'2025-03-30',1,1),(92,'Amal Perera','Amal','C0000004',NULL,'0710714435','amal@hotmail.com',NULL,1,NULL,1,2),(93,'Kasun Kalhara','Kasun','C0000005','Moratuwa','0777745678','kasun@gmail.com',NULL,1,NULL,1,2),(94,'Dilushi Rathnayake','Dilu','C0000007','411/15, Pepiliyana','0715663851','dilu@gmail.com',NULL,2,NULL,1,3),(95,'Randula Pernando','Randula','C202506220001','No 90, Katubedda, Moratuwa','0774563215','randu@gmail.com',NULL,1,NULL,2,1),(96,'Namal Karunarathna','Namal','C0000010','1234 Elm Street','0712345000','Namal.doe@example.com',NULL,1,'2025-06-30',1,1);
/*!40000 ALTER TABLE `customer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customerstatus`
--

DROP TABLE IF EXISTS `customerstatus`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customerstatus` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customerstatus`
--

LOCK TABLES `customerstatus` WRITE;
/*!40000 ALTER TABLE `customerstatus` DISABLE KEYS */;
INSERT INTO `customerstatus` VALUES (1,'Default'),(2,'Loyal'),(3,'Risk');
/*!40000 ALTER TABLE `customerstatus` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customertype`
--

DROP TABLE IF EXISTS `customertype`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customertype` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customertype`
--

LOCK TABLES `customertype` WRITE;
/*!40000 ALTER TABLE `customertype` DISABLE KEYS */;
INSERT INTO `customertype` VALUES (1,'Regular'),(2,'Occasional'),(3,'Luxury');
/*!40000 ALTER TABLE `customertype` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `designation`
--

DROP TABLE IF EXISTS `designation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `designation` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `designation`
--

LOCK TABLES `designation` WRITE;
/*!40000 ALTER TABLE `designation` DISABLE KEYS */;
/*!40000 ALTER TABLE `designation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `employee`
--

DROP TABLE IF EXISTS `employee`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `employee` (
  `id` int NOT NULL AUTO_INCREMENT,
  `number` char(4) DEFAULT NULL,
  `fullname` varchar(150) DEFAULT NULL,
  `callingname` varchar(45) DEFAULT NULL,
  `photo` longblob,
  `gender_id` int NOT NULL,
  `dobirth` date DEFAULT NULL,
  `nic` char(12) DEFAULT NULL,
  `address` text,
  `mobile` char(10) DEFAULT NULL,
  `land` char(10) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  `emptype_id` int NOT NULL,
  `designation_id` int NOT NULL,
  `doassignment` date DEFAULT NULL,
  `description` text,
  `empstatus_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_employee_gender_idx` (`gender_id`),
  KEY `fk_employee_designation1_idx` (`designation_id`),
  KEY `fk_employee_empstatus1_idx` (`empstatus_id`),
  KEY `fk_employee_emptype1_idx` (`emptype_id`),
  CONSTRAINT `fk_employee_designation1` FOREIGN KEY (`designation_id`) REFERENCES `designation` (`id`),
  CONSTRAINT `fk_employee_empstatus1` FOREIGN KEY (`empstatus_id`) REFERENCES `empstatus` (`id`),
  CONSTRAINT `fk_employee_emptype1` FOREIGN KEY (`emptype_id`) REFERENCES `emptype` (`id`),
  CONSTRAINT `fk_employee_gender` FOREIGN KEY (`gender_id`) REFERENCES `gender` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employee`
--

LOCK TABLES `employee` WRITE;
/*!40000 ALTER TABLE `employee` DISABLE KEYS */;
/*!40000 ALTER TABLE `employee` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `empstatus`
--

DROP TABLE IF EXISTS `empstatus`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `empstatus` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `empstatus`
--

LOCK TABLES `empstatus` WRITE;
/*!40000 ALTER TABLE `empstatus` DISABLE KEYS */;
/*!40000 ALTER TABLE `empstatus` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `emptype`
--

DROP TABLE IF EXISTS `emptype`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `emptype` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `emptype`
--

LOCK TABLES `emptype` WRITE;
/*!40000 ALTER TABLE `emptype` DISABLE KEYS */;
/*!40000 ALTER TABLE `emptype` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `gender`
--

DROP TABLE IF EXISTS `gender`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `gender` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gender`
--

LOCK TABLES `gender` WRITE;
/*!40000 ALTER TABLE `gender` DISABLE KEYS */;
INSERT INTO `gender` VALUES (1,'male'),(2,'female');
/*!40000 ALTER TABLE `gender` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `good_receive_note`
--

DROP TABLE IF EXISTS `good_receive_note`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `good_receive_note` (
  `id` int NOT NULL AUTO_INCREMENT,
  `grn_number` varchar(45) DEFAULT NULL,
  `date` date DEFAULT NULL,
  `total_amount` decimal(8,2) DEFAULT NULL,
  `description` text,
  `grn_status_id` int NOT NULL,
  `employee_id` int NOT NULL,
  `purchaseorder_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_good_receive_note_grn_status1_idx` (`grn_status_id`),
  KEY `fk_good_receive_note_employee1_idx` (`employee_id`),
  KEY `fk_good_receive_note_purchaseorder1_idx` (`purchaseorder_id`),
  CONSTRAINT `fk_good_receive_note_employee1` FOREIGN KEY (`employee_id`) REFERENCES `employee` (`id`),
  CONSTRAINT `fk_good_receive_note_grn_status1` FOREIGN KEY (`grn_status_id`) REFERENCES `grn_status` (`id`),
  CONSTRAINT `fk_good_receive_note_purchaseorder1` FOREIGN KEY (`purchaseorder_id`) REFERENCES `purchaseorder` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `good_receive_note`
--

LOCK TABLES `good_receive_note` WRITE;
/*!40000 ALTER TABLE `good_receive_note` DISABLE KEYS */;
/*!40000 ALTER TABLE `good_receive_note` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `grn_item`
--

DROP TABLE IF EXISTS `grn_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `grn_item` (
  `id` int NOT NULL AUTO_INCREMENT,
  `quantity` int DEFAULT NULL,
  `unitcost` decimal(8,2) DEFAULT NULL,
  `sub_total` decimal(8,2) DEFAULT NULL,
  `good_receive_note_id` int NOT NULL,
  `item_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_grn_item_good_receive_note1_idx` (`good_receive_note_id`),
  KEY `fk_grn_item_item1_idx` (`item_id`),
  CONSTRAINT `fk_grn_item_good_receive_note1` FOREIGN KEY (`good_receive_note_id`) REFERENCES `good_receive_note` (`id`),
  CONSTRAINT `fk_grn_item_item1` FOREIGN KEY (`item_id`) REFERENCES `item` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `grn_item`
--

LOCK TABLES `grn_item` WRITE;
/*!40000 ALTER TABLE `grn_item` DISABLE KEYS */;
/*!40000 ALTER TABLE `grn_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `grn_status`
--

DROP TABLE IF EXISTS `grn_status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `grn_status` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `grn_status`
--

LOCK TABLES `grn_status` WRITE;
/*!40000 ALTER TABLE `grn_status` DISABLE KEYS */;
/*!40000 ALTER TABLE `grn_status` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `item`
--

DROP TABLE IF EXISTS `item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `item` (
  `id` int NOT NULL AUTO_INCREMENT,
  `itemnumber` varchar(45) NOT NULL,
  `name` varchar(45) NOT NULL,
  `dointroduced` date DEFAULT NULL,
  `quantity` decimal(8,2) DEFAULT NULL,
  `itemstatus_id` int NOT NULL,
  `unittype_id` int NOT NULL,
  `itembrand_id` int NOT NULL,
  `subcategory_id` int NOT NULL,
  `sprice` decimal(7,2) DEFAULT NULL,
  `pprice` decimal(7,2) DEFAULT NULL,
  `rop` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_item_itemstatus1_idx` (`itemstatus_id`),
  KEY `fk_item_unittype1_idx` (`unittype_id`),
  KEY `fk_item_itembrand1_idx` (`itembrand_id`),
  KEY `fk_item_subcategory1_idx` (`subcategory_id`),
  CONSTRAINT `fk_item_itembrand1` FOREIGN KEY (`itembrand_id`) REFERENCES `itembrand` (`id`),
  CONSTRAINT `fk_item_itemstatus1` FOREIGN KEY (`itemstatus_id`) REFERENCES `itemstatus` (`id`),
  CONSTRAINT `fk_item_subcategory1` FOREIGN KEY (`subcategory_id`) REFERENCES `subcategory` (`id`),
  CONSTRAINT `fk_item_unittype1` FOREIGN KEY (`unittype_id`) REFERENCES `unittype` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `item`
--

LOCK TABLES `item` WRITE;
/*!40000 ALTER TABLE `item` DISABLE KEYS */;
INSERT INTO `item` VALUES (1,'ITM001','L’Oreal Hair Serum','2024-01-10',25.00,1,1,1,1,2500.00,1800.00,5),(2,'ITM002','Dove Shampoo 200ml','2024-02-05',50.00,1,1,2,1,1200.00,850.00,10),(3,'ITM003','Garnier Face Wash','2024-03-15',40.00,1,1,3,2,900.00,600.00,5),(4,'ITM004','Nivea Moisturizer 100ml','2024-02-20',30.00,1,1,4,3,1100.00,800.00,5),(5,'ITM005','MAC Lipstick Ruby Woo','2024-04-01',15.00,1,2,5,4,5500.00,4000.00,3),(6,'ITM006','Lakmé Eyeliner','2024-03-25',20.00,1,2,6,4,750.00,500.00,5),(7,'ITM007','Maybelline Mascara','2024-04-10',18.00,1,2,7,4,1800.00,1300.00,4),(8,'ITM008','Neutrogena Sunscreen SPF50','2024-05-01',22.00,1,1,8,5,2100.00,1500.00,6),(9,'ITM009','Tresemmé Conditioner 250ml','2024-03-11',35.00,1,1,9,6,1350.00,1000.00,8),(10,'ITM010','The Body Shop Face Mask','2024-04-18',10.00,1,4,10,7,2800.00,2000.00,2),(11,'ITM011','Himalaya Neem Face Wash','2024-02-15',45.00,1,3,11,2,650.00,400.00,10),(12,'ITM012','Olay Night Cream 50g','2024-04-05',12.00,1,4,12,8,3300.00,2500.00,3),(13,'ITM013','Revlon Foundation 30ml','2024-05-12',17.00,1,1,13,9,3600.00,2800.00,4),(14,'ITM014','LUX Body Wash 250ml','2024-03-30',28.00,1,1,14,10,950.00,700.00,6),(15,'ITM015','Vaseline Body Lotion 400ml','2024-01-25',32.00,1,1,15,10,1150.00,900.00,5);
/*!40000 ALTER TABLE `item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `itembrand`
--

DROP TABLE IF EXISTS `itembrand`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `itembrand` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `itembrand`
--

LOCK TABLES `itembrand` WRITE;
/*!40000 ALTER TABLE `itembrand` DISABLE KEYS */;
INSERT INTO `itembrand` VALUES (1,'L’Oreal'),(2,'Dove'),(3,'Garnier'),(4,'Nivea'),(5,'MAC'),(6,'Lakmé'),(7,'Maybelline'),(8,'Neutrogena'),(9,'Tresemmé'),(10,'The Body Shop'),(11,'Himalaya'),(12,'Olay'),(13,'Revlon'),(14,'LUX'),(15,'Vaseline');
/*!40000 ALTER TABLE `itembrand` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `itemstatus`
--

DROP TABLE IF EXISTS `itemstatus`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `itemstatus` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `itemstatus`
--

LOCK TABLES `itemstatus` WRITE;
/*!40000 ALTER TABLE `itemstatus` DISABLE KEYS */;
INSERT INTO `itemstatus` VALUES (1,'Active'),(2,'Inactive'),(3,'Discontinued');
/*!40000 ALTER TABLE `itemstatus` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `module`
--

DROP TABLE IF EXISTS `module`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `module` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `module`
--

LOCK TABLES `module` WRITE;
/*!40000 ALTER TABLE `module` DISABLE KEYS */;
/*!40000 ALTER TABLE `module` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `operation`
--

DROP TABLE IF EXISTS `operation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `operation` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `module_id` int DEFAULT NULL,
  `opetype_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_operation_module1_idx` (`module_id`),
  KEY `fk_operation_opetype1_idx` (`opetype_id`),
  CONSTRAINT `fk_operation_module1` FOREIGN KEY (`module_id`) REFERENCES `module` (`id`),
  CONSTRAINT `fk_operation_opetype1` FOREIGN KEY (`opetype_id`) REFERENCES `opetype` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `operation`
--

LOCK TABLES `operation` WRITE;
/*!40000 ALTER TABLE `operation` DISABLE KEYS */;
/*!40000 ALTER TABLE `operation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `opetype`
--

DROP TABLE IF EXISTS `opetype`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `opetype` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `opetype`
--

LOCK TABLES `opetype` WRITE;
/*!40000 ALTER TABLE `opetype` DISABLE KEYS */;
/*!40000 ALTER TABLE `opetype` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `poitem`
--

DROP TABLE IF EXISTS `poitem`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `poitem` (
  `id` int NOT NULL,
  `quantity` int DEFAULT NULL,
  `sub_total` decimal(8,2) DEFAULT NULL,
  `item_id` int NOT NULL,
  `purchaseorder_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_poitem_item1_idx` (`item_id`),
  KEY `fk_poitem_purchaseorder1_idx` (`purchaseorder_id`),
  CONSTRAINT `fk_poitem_item1` FOREIGN KEY (`item_id`) REFERENCES `item` (`id`),
  CONSTRAINT `fk_poitem_purchaseorder1` FOREIGN KEY (`purchaseorder_id`) REFERENCES `purchaseorder` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `poitem`
--

LOCK TABLES `poitem` WRITE;
/*!40000 ALTER TABLE `poitem` DISABLE KEYS */;
/*!40000 ALTER TABLE `poitem` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `postatus`
--

DROP TABLE IF EXISTS `postatus`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `postatus` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `postatus`
--

LOCK TABLES `postatus` WRITE;
/*!40000 ALTER TABLE `postatus` DISABLE KEYS */;
/*!40000 ALTER TABLE `postatus` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `privilege`
--

DROP TABLE IF EXISTS `privilege`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `privilege` (
  `id` int NOT NULL AUTO_INCREMENT,
  `role_id` int NOT NULL,
  `module_id` int NOT NULL,
  `operation_id` int NOT NULL,
  `authority` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_privilage_role1_idx` (`role_id`),
  KEY `fk_privilage_module1_idx` (`module_id`),
  KEY `fk_privilage_operation1_idx` (`operation_id`),
  CONSTRAINT `fk_privilage_module1` FOREIGN KEY (`module_id`) REFERENCES `module` (`id`),
  CONSTRAINT `fk_privilage_operation1` FOREIGN KEY (`operation_id`) REFERENCES `operation` (`id`),
  CONSTRAINT `fk_privilage_role1` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `privilege`
--

LOCK TABLES `privilege` WRITE;
/*!40000 ALTER TABLE `privilege` DISABLE KEYS */;
/*!40000 ALTER TABLE `privilege` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `purchaseorder`
--

DROP TABLE IF EXISTS `purchaseorder`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `purchaseorder` (
  `id` int NOT NULL AUTO_INCREMENT,
  `po_number` varchar(45) DEFAULT NULL,
  `date` date DEFAULT NULL,
  `total_amount` decimal(8,2) DEFAULT NULL,
  `description` text,
  `postatus_id` int NOT NULL,
  `supplier_id` int NOT NULL,
  `employee_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_purchaseorder_postatus1_idx` (`postatus_id`),
  KEY `fk_purchaseorder_supplier1_idx` (`supplier_id`),
  KEY `fk_purchaseorder_employee1_idx` (`employee_id`),
  CONSTRAINT `fk_purchaseorder_employee1` FOREIGN KEY (`employee_id`) REFERENCES `employee` (`id`),
  CONSTRAINT `fk_purchaseorder_postatus1` FOREIGN KEY (`postatus_id`) REFERENCES `postatus` (`id`),
  CONSTRAINT `fk_purchaseorder_supplier1` FOREIGN KEY (`supplier_id`) REFERENCES `supplier` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `purchaseorder`
--

LOCK TABLES `purchaseorder` WRITE;
/*!40000 ALTER TABLE `purchaseorder` DISABLE KEYS */;
/*!40000 ALTER TABLE `purchaseorder` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `subcategory`
--

DROP TABLE IF EXISTS `subcategory`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `subcategory` (
  `id` int NOT NULL,
  `name` varchar(45) DEFAULT NULL,
  `category_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_subcategory_category1_idx` (`category_id`),
  CONSTRAINT `fk_subcategory_category1` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `subcategory`
--

LOCK TABLES `subcategory` WRITE;
/*!40000 ALTER TABLE `subcategory` DISABLE KEYS */;
INSERT INTO `subcategory` VALUES (1,'Shampoo',1),(2,'Face Wash',2),(3,'Moisturizer',3),(4,'Lipstick',4),(5,'Sunscreen',3),(6,'Conditioner',1),(7,'Face Mask',2),(8,'Night Cream',3),(9,'Foundation',4),(10,'Body Wash',5);
/*!40000 ALTER TABLE `subcategory` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `supplier`
--

DROP TABLE IF EXISTS `supplier`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `supplier` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `registernumber` varchar(45) DEFAULT NULL,
  `address` varchar(100) DEFAULT NULL,
  `contactnumber` varchar(12) DEFAULT NULL,
  `contactperson` varchar(45) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `doregister` date DEFAULT NULL,
  `description` text,
  `suplierstate_id` int NOT NULL,
  `supplierstype_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_supplier_suplierstate1_idx` (`suplierstate_id`),
  KEY `fk_supplier_supplierstype1_idx` (`supplierstype_id`),
  CONSTRAINT `fk_supplier_suplierstate1` FOREIGN KEY (`suplierstate_id`) REFERENCES `supplierstate` (`id`),
  CONSTRAINT `fk_supplier_supplierstype1` FOREIGN KEY (`supplierstype_id`) REFERENCES `supplierstype` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `supplier`
--

LOCK TABLES `supplier` WRITE;
/*!40000 ALTER TABLE `supplier` DISABLE KEYS */;
INSERT INTO `supplier` VALUES (21,'More Glow Distributors','REG202311','No. 18, Main Street, Colombo 04','0771234000','Sunil Perera','contact@moreglow.lk','2023-01-15','Main cosmetics supplier.',1,1),(22,'Salon Essentials Pvt Ltd','REG202302','45/2, Galle Road, Dehiwala','0712345678','Shalini Silva','sales@salonessentials.lk','2023-02-10','Provides shampoos and conditioners.',1,2),(23,'Lanka Hair Supplies','REG202303','88, Kandy Road, Kiribathgoda','0723456789','Sunil Fernando','support@lankahair.com','2023-03-05','Specialist in haircare products.',2,1),(24,'Nails & Co.','REG202304','12A, Negombo Road, Wattala','0781239876','Dilani Jayasuriya','info@nailsco.lk','2023-04-22','Manicure and pedicure supply.',2,2),(25,'BeautyPro Suppliers','REG202305','203, Dharmapala Mawatha, Colombo 07','0767894321','Roshan Wijesekara','beautypro@sup.lk','2023-05-12','Professional beauty equipment.',3,1),(26,'Chic Imports','REG202306','56, Gampaha Road, Ja-Ela','0701234987','Kavindi Senanayake','orders@chicimports.com','2023-06-18','Imported beauty tools.',3,2),(27,'Cosmo Traders','REG202307','134, Old Kesbewa Road, Nugegoda','0752345670','Tharindu Jayalath','cosmo@traders.lk','2023-07-04','Wholesale cosmetic distributor.',1,1),(28,'Hair & Style Ltd','REG202308','2/1, Rajagiriya Junction, Rajagiriya','0743456781','Manori Dias','hairstyle@support.com','2023-08-01','Hair dyes and styling tools.',2,2),(29,'Premium Salon Supplies','REG202309','77, Havelock Road, Colombo 05','0738765432','Duminda Ranatunga','premiumsupp@salon.lk','2023-09-14','Premium product supplier.',1,1),(30,'Luxe Beauty Wholesale','REG202310','10B, Matara Road, Galle','0791234569','Ishara Abeywickrama','luxebw@beauty.lk','2023-10-09','Luxury skincare and tools.',3,2),(31,'Glow more Distributors','REG202311','No. 15, Main Street, Colombo 03','0771234567','Kamal peris','contact@glowdistributors.lk','2023-01-15','Main cosmetics supplier.',1,1),(32,'Glow more Distributors','REG202311','No. 15, Main Street, Colombo 03','0771234567','Kamal peris','contact@glowdistributors.lk','2023-01-15','Main cosmetics supplier.',1,1);
/*!40000 ALTER TABLE `supplier` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `supplierstate`
--

DROP TABLE IF EXISTS `supplierstate`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `supplierstate` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `supplierstate`
--

LOCK TABLES `supplierstate` WRITE;
/*!40000 ALTER TABLE `supplierstate` DISABLE KEYS */;
INSERT INTO `supplierstate` VALUES (1,'Active'),(2,'Inactive'),(3,'Pending Approval');
/*!40000 ALTER TABLE `supplierstate` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `supplierstype`
--

DROP TABLE IF EXISTS `supplierstype`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `supplierstype` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `supplierstype`
--

LOCK TABLES `supplierstype` WRITE;
/*!40000 ALTER TABLE `supplierstype` DISABLE KEYS */;
INSERT INTO `supplierstype` VALUES (1,'Local'),(2,'International');
/*!40000 ALTER TABLE `supplierstype` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `supply`
--

DROP TABLE IF EXISTS `supply`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `supply` (
  `id` int NOT NULL AUTO_INCREMENT,
  `supplier_id` int NOT NULL,
  `category_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_supply_supplier1_idx` (`supplier_id`),
  KEY `fk_supply_category1_idx` (`category_id`),
  CONSTRAINT `fk_supply_category1` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`),
  CONSTRAINT `fk_supply_supplier1` FOREIGN KEY (`supplier_id`) REFERENCES `supplier` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `supply`
--

LOCK TABLES `supply` WRITE;
/*!40000 ALTER TABLE `supply` DISABLE KEYS */;
/*!40000 ALTER TABLE `supply` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `unittype`
--

DROP TABLE IF EXISTS `unittype`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `unittype` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `unittype`
--

LOCK TABLES `unittype` WRITE;
/*!40000 ALTER TABLE `unittype` DISABLE KEYS */;
INSERT INTO `unittype` VALUES (1,'Bottle'),(2,'Piece'),(3,'Tube'),(4,'Jar'),(5,'Pack');
/*!40000 ALTER TABLE `unittype` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `employee_id` int NOT NULL,
  `username` varchar(45) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `salt` varchar(255) DEFAULT NULL,
  `docreated` date DEFAULT NULL,
  `tocreated` time DEFAULT NULL,
  `description` text,
  `usestatus_id` int NOT NULL,
  `usetype_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_user_usestatus1_idx` (`usestatus_id`),
  KEY `fk_user_employee1_idx` (`employee_id`),
  KEY `fk_user_usetype1_idx` (`usetype_id`),
  CONSTRAINT `fk_user_employee1` FOREIGN KEY (`employee_id`) REFERENCES `employee` (`id`),
  CONSTRAINT `fk_user_usestatus1` FOREIGN KEY (`usestatus_id`) REFERENCES `usestatus` (`id`),
  CONSTRAINT `fk_user_usetype1` FOREIGN KEY (`usetype_id`) REFERENCES `usetype` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `userrole`
--

DROP TABLE IF EXISTS `userrole`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `userrole` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `role_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_user_has_role_role1_idx` (`role_id`),
  KEY `fk_user_has_role_user1_idx` (`user_id`),
  CONSTRAINT `fk_user_has_role_role1` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`),
  CONSTRAINT `fk_user_has_role_user1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `userrole`
--

LOCK TABLES `userrole` WRITE;
/*!40000 ALTER TABLE `userrole` DISABLE KEYS */;
/*!40000 ALTER TABLE `userrole` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usestatus`
--

DROP TABLE IF EXISTS `usestatus`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usestatus` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usestatus`
--

LOCK TABLES `usestatus` WRITE;
/*!40000 ALTER TABLE `usestatus` DISABLE KEYS */;
/*!40000 ALTER TABLE `usestatus` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usetype`
--

DROP TABLE IF EXISTS `usetype`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usetype` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usetype`
--

LOCK TABLES `usetype` WRITE;
/*!40000 ALTER TABLE `usetype` DISABLE KEYS */;
/*!40000 ALTER TABLE `usetype` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-06-27 14:16:22
