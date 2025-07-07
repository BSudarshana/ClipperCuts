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
-- Table structure for table ` package`
--

DROP TABLE IF EXISTS ` package`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE ` package` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL,
  `price` decimal(10,2) DEFAULT NULL,
  `duration` int DEFAULT NULL,
  `status` tinyint DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table ` package`
--

LOCK TABLES ` package` WRITE;
/*!40000 ALTER TABLE ` package` DISABLE KEYS */;
/*!40000 ALTER TABLE ` package` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table ` package_has_appointment`
--

DROP TABLE IF EXISTS ` package_has_appointment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE ` package_has_appointment` (
  `id` int NOT NULL AUTO_INCREMENT,
  ` Package_id` int NOT NULL,
  `appointment_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_ Package_has_appointment_appointment1_idx` (`appointment_id`),
  KEY `fk_ Package_has_appointment_ Package1_idx` (` Package_id`),
  CONSTRAINT `fk_ Package_has_appointment_ Package1` FOREIGN KEY (` Package_id`) REFERENCES ` package` (`id`),
  CONSTRAINT `fk_ Package_has_appointment_appointment1` FOREIGN KEY (`appointment_id`) REFERENCES `appointment` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table ` package_has_appointment`
--

LOCK TABLES ` package_has_appointment` WRITE;
/*!40000 ALTER TABLE ` package_has_appointment` DISABLE KEYS */;
/*!40000 ALTER TABLE ` package_has_appointment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table ` package_has_invoice`
--

DROP TABLE IF EXISTS ` package_has_invoice`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE ` package_has_invoice` (
  `id` int NOT NULL AUTO_INCREMENT,
  ` Package_id` int NOT NULL,
  `invoice_id` int NOT NULL,
  `prpice` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_ Package_has_invoice_invoice1_idx` (`invoice_id`),
  KEY `fk_ Package_has_invoice_ Package1_idx` (` Package_id`),
  CONSTRAINT `fk_ Package_has_invoice_ Package1` FOREIGN KEY (` Package_id`) REFERENCES ` package` (`id`),
  CONSTRAINT `fk_ Package_has_invoice_invoice1` FOREIGN KEY (`invoice_id`) REFERENCES `invoice` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table ` package_has_invoice`
--

LOCK TABLES ` package_has_invoice` WRITE;
/*!40000 ALTER TABLE ` package_has_invoice` DISABLE KEYS */;
/*!40000 ALTER TABLE ` package_has_invoice` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table ` package_has_service`
--

DROP TABLE IF EXISTS ` package_has_service`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE ` package_has_service` (
  `id` int NOT NULL AUTO_INCREMENT,
  ` Package_id` int NOT NULL,
  `service_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_ Package_has_service_service1_idx` (`service_id`),
  KEY `fk_ Package_has_service_ Package1_idx` (` Package_id`),
  CONSTRAINT `fk_ Package_has_service_ Package1` FOREIGN KEY (` Package_id`) REFERENCES ` package` (`id`),
  CONSTRAINT `fk_ Package_has_service_service1` FOREIGN KEY (`service_id`) REFERENCES `service` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table ` package_has_service`
--

LOCK TABLES ` package_has_service` WRITE;
/*!40000 ALTER TABLE ` package_has_service` DISABLE KEYS */;
/*!40000 ALTER TABLE ` package_has_service` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `appointment`
--

DROP TABLE IF EXISTS `appointment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `appointment` (
  `id` int NOT NULL AUTO_INCREMENT,
  `appointment_date` date NOT NULL,
  `appointment_time` time DEFAULT NULL,
  `description` varchar(500) DEFAULT NULL,
  `appointmentstatus_id` int NOT NULL,
  `customer_id` int NOT NULL,
  `employee_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_appointment_appointmentstatus1_idx` (`appointmentstatus_id`),
  KEY `fk_appointment_customer1_idx` (`customer_id`),
  KEY `fk_appointment_employee1_idx` (`employee_id`),
  CONSTRAINT `fk_appointment_appointmentstatus1` FOREIGN KEY (`appointmentstatus_id`) REFERENCES `appointmentstatus` (`id`),
  CONSTRAINT `fk_appointment_customer1` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`id`),
  CONSTRAINT `fk_appointment_employee1` FOREIGN KEY (`employee_id`) REFERENCES `employee` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `appointment`
--

LOCK TABLES `appointment` WRITE;
/*!40000 ALTER TABLE `appointment` DISABLE KEYS */;
/*!40000 ALTER TABLE `appointment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `appointmentservice`
--

DROP TABLE IF EXISTS `appointmentservice`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `appointmentservice` (
  `id` int NOT NULL AUTO_INCREMENT,
  `appointment_id` int NOT NULL,
  `service_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_appointment_has_service_service1_idx` (`service_id`),
  KEY `fk_appointment_has_service_appointment1_idx` (`appointment_id`),
  CONSTRAINT `fk_appointment_has_service_appointment1` FOREIGN KEY (`appointment_id`) REFERENCES `appointment` (`id`),
  CONSTRAINT `fk_appointment_has_service_service1` FOREIGN KEY (`service_id`) REFERENCES `service` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `appointmentservice`
--

LOCK TABLES `appointmentservice` WRITE;
/*!40000 ALTER TABLE `appointmentservice` DISABLE KEYS */;
/*!40000 ALTER TABLE `appointmentservice` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `appointmentstatus`
--

DROP TABLE IF EXISTS `appointmentstatus`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `appointmentstatus` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `appointmentstatus`
--

LOCK TABLES `appointmentstatus` WRITE;
/*!40000 ALTER TABLE `appointmentstatus` DISABLE KEYS */;
/*!40000 ALTER TABLE `appointmentstatus` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Table structure for table `customerfeedback`
--

DROP TABLE IF EXISTS `customerfeedback`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customerfeedback` (
  `id` int NOT NULL,
  `date` date DEFAULT NULL,
  `comment` text,
  `customer_id` int NOT NULL,
  `appointment_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_customerfeedback_customer1_idx` (`customer_id`),
  KEY `fk_customerfeedback_appointment1_idx` (`appointment_id`),
  CONSTRAINT `fk_customerfeedback_appointment1` FOREIGN KEY (`appointment_id`) REFERENCES `appointment` (`id`),
  CONSTRAINT `fk_customerfeedback_customer1` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customerfeedback`
--

LOCK TABLES `customerfeedback` WRITE;
/*!40000 ALTER TABLE `customerfeedback` DISABLE KEYS */;
/*!40000 ALTER TABLE `customerfeedback` ENABLE KEYS */;
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
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `designation`
--

LOCK TABLES `designation` WRITE;
/*!40000 ALTER TABLE `designation` DISABLE KEYS */;
INSERT INTO `designation` VALUES (1,'Salon Manager'),(2,'Receptionist'),(3,'Beautician'),(4,'Hairdresser'),(5,'Cleaner'),(6,'Cashier'),(7,'Assistant Beautician');
/*!40000 ALTER TABLE `designation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `discount`
--

DROP TABLE IF EXISTS `discount`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `discount` (
  `id` int NOT NULL AUTO_INCREMENT,
  `value` decimal(10,2) DEFAULT NULL,
  `maxvalue` decimal(10,2) DEFAULT NULL,
  `discounttype_id` int NOT NULL,
  `promotion_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_discount_discounttype1_idx` (`discounttype_id`),
  KEY `fk_discount_promotion1_idx` (`promotion_id`),
  CONSTRAINT `fk_discount_discounttype1` FOREIGN KEY (`discounttype_id`) REFERENCES `discounttype` (`id`),
  CONSTRAINT `fk_discount_promotion1` FOREIGN KEY (`promotion_id`) REFERENCES `promotion` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `discount`
--

LOCK TABLES `discount` WRITE;
/*!40000 ALTER TABLE `discount` DISABLE KEYS */;
/*!40000 ALTER TABLE `discount` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `discounttype`
--

DROP TABLE IF EXISTS `discounttype`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `discounttype` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `discounttype`
--

LOCK TABLES `discounttype` WRITE;
/*!40000 ALTER TABLE `discounttype` DISABLE KEYS */;
/*!40000 ALTER TABLE `discounttype` ENABLE KEYS */;
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
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employee`
--

LOCK TABLES `employee` WRITE;
/*!40000 ALTER TABLE `employee` DISABLE KEYS */;
INSERT INTO `employee` VALUES (1,'E001','Nadeesha Fernando','Nadeesha',NULL,2,'1985-06-15','856789012V','No.12, Galle Road, Colombo 3','0771234567','0112345678','nadeesha@gmail.com',1,1,'2020-01-10','Experienced salon manager',1),(2,'E002','Tharindu Perera','Tharindu',NULL,1,'1992-03-25','923456789V','No.88, Kandy Road, Kurunegala','0712345678','0371234567','tharindu.p@gmail.com',1,2,'2021-05-12','Receptionist with customer care experience',1),(3,'E003','Nipuni Jayasinghe','Nipuni',NULL,2,'1994-11-30','944567890V','No.7, Negombo Road, Wattala','0769876543',NULL,'nipuni.j@gmail.com',1,3,'2022-02-20','Senior beautician',1),(4,'E004','Sanduni Silva','Sanduni',NULL,2,'1998-01-20','984567891V','No.22, Beach Road, Matara','0751234569',NULL,'sanduni.s@gmail.com',2,3,'2023-06-01','Junior beautician',1),(5,'E005','Dinuka Madushanka','Dinuka',NULL,1,'1990-08-14','905678912V','No.5, Lake Road, Anuradhapura','0704567890',NULL,'dinuka.m@gmail.com',1,4,'2021-07-10','Hairdresser with 5 years experience',1),(6,'E006','Nimali Rajapaksha','Nimali',NULL,2,'1996-09-05','964567893V','No.40, New Town, Gampaha','0781234560',NULL,'nimali.r@gmail.com',3,3,'2024-01-15','Part-time beautician',1),(7,'E007','Kasun Abeywickrama','Kasun',NULL,1,'1988-12-19','883456789V','No.12, Temple Road, Kalutara','0723456789','0345678901','kasun.a@gmail.com',1,6,'2020-09-22','Handles cash transactions',1),(8,'E008','Sachini De Silva','Sachini',NULL,2,'1993-04-08','934567891V','No.17, Main Street, Ratnapura','0752345678',NULL,'sachini.d@gmail.com',2,2,'2024-03-10','Contract receptionist',1),(9,'E009','Thisara Bandara','Thisara',NULL,1,'1995-07-30','954567892V','No.55, Park Road, Badulla','0779876543',NULL,'thisara.b@gmail.com',1,5,'2023-04-11','Cleaner with hotel experience',1),(10,'E010','Upeka Rathnayake','Upeka',NULL,2,'1997-10-02','974567893V','No.33, River Road, Polonnaruwa','0713456789',NULL,'upeka.r@gmail.com',3,7,'2024-06-15','Assistant beautician',1),(11,'E011','Sahan Ranasinghe','Sahan',NULL,1,'1991-02-12','913456789V','No.9, City Road, Jaffna','0745678901',NULL,'sahan.r@gmail.com',1,4,'2022-10-01','Senior hairdresser',1),(12,'E012','Ishara Weerakkody','Ishara',NULL,2,'1999-05-19','994567894V','No.6, Central Road, Nuwara Eliya','0765432198',NULL,'ishara.w@gmail.com',2,7,'2024-05-05','New recruit',1),(13,'E013','Ravindu Karunaratne','Ravindu',NULL,1,'1987-11-11','873456789V','No.10, Forest Lane, Kegalle','0709876543',NULL,'ravindu.k@gmail.com',1,5,'2021-03-08','Senior cleaner',1),(14,'E014','Hansika Perera','Hansika',NULL,2,'1990-06-22','905678913V','No.20, Flower Road, Colombo 7','0723456790',NULL,'hansika.p@gmail.com',1,3,'2021-08-25','Makeup specialist',2),(15,'E015','Kavindu Jayalath','Kavindu',NULL,1,'1993-09-14','935678912V','No.44, Canal Road, Panadura','0787654321',NULL,'kavindu.j@gmail.com',1,6,'2020-11-18','Senior cashier',3);
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
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `empstatus`
--

LOCK TABLES `empstatus` WRITE;
/*!40000 ALTER TABLE `empstatus` DISABLE KEYS */;
INSERT INTO `empstatus` VALUES (1,'Active'),(2,'On Leave'),(3,'Resigned');
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
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `emptype`
--

LOCK TABLES `emptype` WRITE;
/*!40000 ALTER TABLE `emptype` DISABLE KEYS */;
INSERT INTO `emptype` VALUES (1,'Permanent'),(2,'Contract'),(3,'Part-Time');
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
-- Table structure for table `invoice`
--

DROP TABLE IF EXISTS `invoice`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `invoice` (
  `id` int NOT NULL AUTO_INCREMENT,
  `invoicedate` datetime DEFAULT NULL,
  `totalamoun` decimal(10,2) DEFAULT NULL,
  `discount` decimal(10,2) DEFAULT NULL,
  `tax` decimal(10,2) DEFAULT NULL,
  `final_amount` decimal(10,2) DEFAULT NULL,
  `paymentstatus_id` int NOT NULL,
  `appointment_id` int NOT NULL,
  `promotion_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_invoice_paymentstatus1_idx` (`paymentstatus_id`),
  KEY `fk_invoice_appointment1_idx` (`appointment_id`),
  KEY `fk_invoice_promotion1_idx` (`promotion_id`),
  CONSTRAINT `fk_invoice_appointment1` FOREIGN KEY (`appointment_id`) REFERENCES `appointment` (`id`),
  CONSTRAINT `fk_invoice_paymentstatus1` FOREIGN KEY (`paymentstatus_id`) REFERENCES `paymentstatus` (`id`),
  CONSTRAINT `fk_invoice_promotion1` FOREIGN KEY (`promotion_id`) REFERENCES `promotion` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `invoice`
--

LOCK TABLES `invoice` WRITE;
/*!40000 ALTER TABLE `invoice` DISABLE KEYS */;
/*!40000 ALTER TABLE `invoice` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `invoice_item`
--

DROP TABLE IF EXISTS `invoice_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `invoice_item` (
  `id` int NOT NULL AUTO_INCREMENT,
  `quantity` decimal(8,2) DEFAULT NULL,
  `price` decimal(10,2) DEFAULT NULL,
  `invoice_id` int NOT NULL,
  `item_id` int NOT NULL,
  `location_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_invoice_has_item_item1_idx` (`item_id`),
  KEY `fk_invoice_has_item_invoice1_idx` (`invoice_id`),
  KEY `fk_invoice_item_location1_idx` (`location_id`),
  CONSTRAINT `fk_invoice_has_item_invoice1` FOREIGN KEY (`invoice_id`) REFERENCES `invoice` (`id`),
  CONSTRAINT `fk_invoice_has_item_item1` FOREIGN KEY (`item_id`) REFERENCES `item` (`id`),
  CONSTRAINT `fk_invoice_item_location1` FOREIGN KEY (`location_id`) REFERENCES `location` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `invoice_item`
--

LOCK TABLES `invoice_item` WRITE;
/*!40000 ALTER TABLE `invoice_item` DISABLE KEYS */;
/*!40000 ALTER TABLE `invoice_item` ENABLE KEYS */;
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
-- Table structure for table `itemstock_location`
--

DROP TABLE IF EXISTS `itemstock_location`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `itemstock_location` (
  `id` int NOT NULL AUTO_INCREMENT,
  `item_id` int NOT NULL,
  `location_id` int NOT NULL,
  `quantity` decimal(10,2) DEFAULT NULL,
  `lastupdate` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_item_has_location_location1_idx` (`location_id`),
  KEY `fk_item_has_location_item1_idx` (`item_id`),
  CONSTRAINT `fk_item_has_location_item1` FOREIGN KEY (`item_id`) REFERENCES `item` (`id`),
  CONSTRAINT `fk_item_has_location_location1` FOREIGN KEY (`location_id`) REFERENCES `location` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `itemstock_location`
--

LOCK TABLES `itemstock_location` WRITE;
/*!40000 ALTER TABLE `itemstock_location` DISABLE KEYS */;
/*!40000 ALTER TABLE `itemstock_location` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `location`
--

DROP TABLE IF EXISTS `location`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `location` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `description` varchar(250) DEFAULT NULL,
  `locationtype_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_location_locationtype1_idx` (`locationtype_id`),
  CONSTRAINT `fk_location_locationtype1` FOREIGN KEY (`locationtype_id`) REFERENCES `locationtype` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `location`
--

LOCK TABLES `location` WRITE;
/*!40000 ALTER TABLE `location` DISABLE KEYS */;
/*!40000 ALTER TABLE `location` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `locationtype`
--

DROP TABLE IF EXISTS `locationtype`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `locationtype` (
  `id` int NOT NULL,
  `name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `locationtype`
--

LOCK TABLES `locationtype` WRITE;
/*!40000 ALTER TABLE `locationtype` DISABLE KEYS */;
/*!40000 ALTER TABLE `locationtype` ENABLE KEYS */;
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
-- Table structure for table `payment`
--

DROP TABLE IF EXISTS `payment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `payment` (
  `id` int NOT NULL AUTO_INCREMENT,
  `payment_date` datetime DEFAULT NULL,
  `amount` decimal(10,2) DEFAULT NULL,
  `remarks` varchar(255) DEFAULT NULL,
  `invoice_id` int NOT NULL,
  `paymentmethod_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_payment_invoice1_idx` (`invoice_id`),
  KEY `fk_payment_paymentmethod1_idx` (`paymentmethod_id`),
  CONSTRAINT `fk_payment_invoice1` FOREIGN KEY (`invoice_id`) REFERENCES `invoice` (`id`),
  CONSTRAINT `fk_payment_paymentmethod1` FOREIGN KEY (`paymentmethod_id`) REFERENCES `paymentmethod` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `payment`
--

LOCK TABLES `payment` WRITE;
/*!40000 ALTER TABLE `payment` DISABLE KEYS */;
/*!40000 ALTER TABLE `payment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `paymentmethod`
--

DROP TABLE IF EXISTS `paymentmethod`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `paymentmethod` (
  `id` int NOT NULL,
  `name` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `paymentmethod`
--

LOCK TABLES `paymentmethod` WRITE;
/*!40000 ALTER TABLE `paymentmethod` DISABLE KEYS */;
/*!40000 ALTER TABLE `paymentmethod` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `paymentstatus`
--

DROP TABLE IF EXISTS `paymentstatus`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `paymentstatus` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `paymentstatus`
--

LOCK TABLES `paymentstatus` WRITE;
/*!40000 ALTER TABLE `paymentstatus` DISABLE KEYS */;
/*!40000 ALTER TABLE `paymentstatus` ENABLE KEYS */;
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
-- Table structure for table `promotion`
--

DROP TABLE IF EXISTS `promotion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `promotion` (
  `id` int NOT NULL AUTO_INCREMENT,
  `title` varchar(100) DEFAULT NULL,
  `description` varchar(250) DEFAULT NULL,
  `startdate` date DEFAULT NULL,
  `enddate` varchar(45) DEFAULT NULL,
  `promotionstatus_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_promotion_promotionstatus1_idx` (`promotionstatus_id`),
  CONSTRAINT `fk_promotion_promotionstatus1` FOREIGN KEY (`promotionstatus_id`) REFERENCES `promotionstatus` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `promotion`
--

LOCK TABLES `promotion` WRITE;
/*!40000 ALTER TABLE `promotion` DISABLE KEYS */;
/*!40000 ALTER TABLE `promotion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `promotionstatus`
--

DROP TABLE IF EXISTS `promotionstatus`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `promotionstatus` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `promotionstatus`
--

LOCK TABLES `promotionstatus` WRITE;
/*!40000 ALTER TABLE `promotionstatus` DISABLE KEYS */;
/*!40000 ALTER TABLE `promotionstatus` ENABLE KEYS */;
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
-- Table structure for table `question`
--

DROP TABLE IF EXISTS `question`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `question` (
  `id` int NOT NULL AUTO_INCREMENT,
  `qutext` varchar(45) NOT NULL,
  `isactive` tinyint DEFAULT NULL,
  PRIMARY KEY (`id`,`qutext`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `question`
--

LOCK TABLES `question` WRITE;
/*!40000 ALTER TABLE `question` DISABLE KEYS */;
/*!40000 ALTER TABLE `question` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `response`
--

DROP TABLE IF EXISTS `response`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `response` (
  `id` int NOT NULL AUTO_INCREMENT,
  `rating` int DEFAULT NULL,
  `customerfeedback_id` int NOT NULL,
  `question_id` int NOT NULL,
  `question_qutext` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_response_customerfeedback1_idx` (`customerfeedback_id`),
  KEY `fk_response_question1_idx` (`question_id`,`question_qutext`),
  CONSTRAINT `fk_response_customerfeedback1` FOREIGN KEY (`customerfeedback_id`) REFERENCES `customerfeedback` (`id`),
  CONSTRAINT `fk_response_question1` FOREIGN KEY (`question_id`, `question_qutext`) REFERENCES `question` (`id`, `qutext`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `response`
--

LOCK TABLES `response` WRITE;
/*!40000 ALTER TABLE `response` DISABLE KEYS */;
/*!40000 ALTER TABLE `response` ENABLE KEYS */;
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
-- Table structure for table `service`
--

DROP TABLE IF EXISTS `service`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `service` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(75) DEFAULT NULL,
  `duration` int DEFAULT NULL,
  `price` decimal(8,2) DEFAULT NULL,
  `servicestatus_id` int NOT NULL,
  `servicecategory_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_service_servicestatus1_idx` (`servicestatus_id`),
  KEY `fk_service_servicecategory1_idx` (`servicecategory_id`),
  CONSTRAINT `fk_service_servicecategory1` FOREIGN KEY (`servicecategory_id`) REFERENCES `servicecategory` (`id`),
  CONSTRAINT `fk_service_servicestatus1` FOREIGN KEY (`servicestatus_id`) REFERENCES `servicestatus` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `service`
--

LOCK TABLES `service` WRITE;
/*!40000 ALTER TABLE `service` DISABLE KEYS */;
/*!40000 ALTER TABLE `service` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `service_has_employee`
--

DROP TABLE IF EXISTS `service_has_employee`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `service_has_employee` (
  `id` int NOT NULL AUTO_INCREMENT,
  `service_id` int NOT NULL,
  `employee_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_service_has_employee_employee1_idx` (`employee_id`),
  KEY `fk_service_has_employee_service1_idx` (`service_id`),
  CONSTRAINT `fk_service_has_employee_employee1` FOREIGN KEY (`employee_id`) REFERENCES `employee` (`id`),
  CONSTRAINT `fk_service_has_employee_service1` FOREIGN KEY (`service_id`) REFERENCES `service` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `service_has_employee`
--

LOCK TABLES `service_has_employee` WRITE;
/*!40000 ALTER TABLE `service_has_employee` DISABLE KEYS */;
/*!40000 ALTER TABLE `service_has_employee` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `service_has_promotion`
--

DROP TABLE IF EXISTS `service_has_promotion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `service_has_promotion` (
  `id` int NOT NULL AUTO_INCREMENT,
  `service_id` int NOT NULL,
  `promotion_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_service_has_promotion_promotion1_idx` (`promotion_id`),
  KEY `fk_service_has_promotion_service1_idx` (`service_id`),
  CONSTRAINT `fk_service_has_promotion_promotion1` FOREIGN KEY (`promotion_id`) REFERENCES `promotion` (`id`),
  CONSTRAINT `fk_service_has_promotion_service1` FOREIGN KEY (`service_id`) REFERENCES `service` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `service_has_promotion`
--

LOCK TABLES `service_has_promotion` WRITE;
/*!40000 ALTER TABLE `service_has_promotion` DISABLE KEYS */;
/*!40000 ALTER TABLE `service_has_promotion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `servicecategory`
--

DROP TABLE IF EXISTS `servicecategory`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `servicecategory` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `servicecategory`
--

LOCK TABLES `servicecategory` WRITE;
/*!40000 ALTER TABLE `servicecategory` DISABLE KEYS */;
/*!40000 ALTER TABLE `servicecategory` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `servicestatus`
--

DROP TABLE IF EXISTS `servicestatus`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `servicestatus` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `servicestatus`
--

LOCK TABLES `servicestatus` WRITE;
/*!40000 ALTER TABLE `servicestatus` DISABLE KEYS */;
/*!40000 ALTER TABLE `servicestatus` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `stocktransfer`
--

DROP TABLE IF EXISTS `stocktransfer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `stocktransfer` (
  `id` int NOT NULL AUTO_INCREMENT,
  `transferdate` date DEFAULT NULL,
  `note` varchar(200) DEFAULT NULL,
  `from_location_id` int NOT NULL,
  `to_location_id` int NOT NULL,
  `employee_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_stocktransfer_location1_idx` (`from_location_id`),
  KEY `fk_stocktransfer_location2_idx` (`to_location_id`),
  KEY `fk_stocktransfer_employee1_idx` (`employee_id`),
  CONSTRAINT `fk_stocktransfer_employee1` FOREIGN KEY (`employee_id`) REFERENCES `employee` (`id`),
  CONSTRAINT `fk_stocktransfer_location1` FOREIGN KEY (`from_location_id`) REFERENCES `location` (`id`),
  CONSTRAINT `fk_stocktransfer_location2` FOREIGN KEY (`to_location_id`) REFERENCES `location` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `stocktransfer`
--

LOCK TABLES `stocktransfer` WRITE;
/*!40000 ALTER TABLE `stocktransfer` DISABLE KEYS */;
/*!40000 ALTER TABLE `stocktransfer` ENABLE KEYS */;
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
-- Table structure for table `transferitem`
--

DROP TABLE IF EXISTS `transferitem`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `transferitem` (
  `id` int NOT NULL AUTO_INCREMENT,
  `quantity` decimal(8,2) DEFAULT NULL,
  `stocktransfer_id` int NOT NULL,
  `item_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_transferitem_stocktransfer1_idx` (`stocktransfer_id`),
  KEY `fk_transferitem_item1_idx` (`item_id`),
  CONSTRAINT `fk_transferitem_item1` FOREIGN KEY (`item_id`) REFERENCES `item` (`id`),
  CONSTRAINT `fk_transferitem_stocktransfer1` FOREIGN KEY (`stocktransfer_id`) REFERENCES `stocktransfer` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `transferitem`
--

LOCK TABLES `transferitem` WRITE;
/*!40000 ALTER TABLE `transferitem` DISABLE KEYS */;
/*!40000 ALTER TABLE `transferitem` ENABLE KEYS */;
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

-- Dump completed on 2025-07-07 23:07:16
