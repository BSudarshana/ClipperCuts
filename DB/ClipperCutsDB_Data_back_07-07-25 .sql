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
-- Dumping data for table ` package`
--

LOCK TABLES ` package` WRITE;
/*!40000 ALTER TABLE ` package` DISABLE KEYS */;
/*!40000 ALTER TABLE ` package` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table ` package_has_appointment`
--

LOCK TABLES ` package_has_appointment` WRITE;
/*!40000 ALTER TABLE ` package_has_appointment` DISABLE KEYS */;
/*!40000 ALTER TABLE ` package_has_appointment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table ` package_has_invoice`
--

LOCK TABLES ` package_has_invoice` WRITE;
/*!40000 ALTER TABLE ` package_has_invoice` DISABLE KEYS */;
/*!40000 ALTER TABLE ` package_has_invoice` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table ` package_has_service`
--

LOCK TABLES ` package_has_service` WRITE;
/*!40000 ALTER TABLE ` package_has_service` DISABLE KEYS */;
/*!40000 ALTER TABLE ` package_has_service` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `appointment`
--

LOCK TABLES `appointment` WRITE;
/*!40000 ALTER TABLE `appointment` DISABLE KEYS */;
/*!40000 ALTER TABLE `appointment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `appointmentservice`
--

LOCK TABLES `appointmentservice` WRITE;
/*!40000 ALTER TABLE `appointmentservice` DISABLE KEYS */;
/*!40000 ALTER TABLE `appointmentservice` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `appointmentstatus`
--

LOCK TABLES `appointmentstatus` WRITE;
/*!40000 ALTER TABLE `appointmentstatus` DISABLE KEYS */;
/*!40000 ALTER TABLE `appointmentstatus` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` VALUES (1,'Hair Care'),(2,'Face Care'),(3,'Skin Care'),(4,'Makeup'),(5,'Body Care');
/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `customer`
--

LOCK TABLES `customer` WRITE;
/*!40000 ALTER TABLE `customer` DISABLE KEYS */;
INSERT INTO `customer` VALUES (1,'John Doe','John','C1234567','1234 Elm Street','0712345678','john.doe@example.com',NULL,1,'2025-03-30',1,1),(80,'Tom Lee','Tom','C1234569','9101 Pine Road','0734567890','tom.lee@example.com',NULL,1,'2025-03-30',1,2),(81,'Sara Adams','Sara','C1234570','2345 Oak Drive','0745678901','sara.adams@example.com',NULL,2,'2025-03-30',3,2),(82,'Peter Brown','Peter','C1234571','6789 Birch Lane','0756789012','peter.brown@example.com',NULL,1,'2025-03-30',1,3),(83,'Emily Davis','Emily','C1234572','3456 Cedar Street','0767890123','emily.davis@example.com',NULL,2,'2025-03-30',2,1),(84,'Michael Clark','Mike','C1234573','4567 Maple Lane','0778901234','michael.clark@example.com',NULL,1,'2025-03-30',3,1),(85,'Rachel White','Rachel','C1234574','7890 Elm Street','0789012345','rachel.white@example.com',NULL,2,'2025-03-30',1,2),(86,'David Green','David','C1234575','1122 Pine Avenue','0790123456','david.green@example.com',NULL,1,'2025-03-30',2,3),(87,'Linda King','Linda','C1234576','3344 Oak Road','0701234567','linda.king@example.com',NULL,2,'2025-03-30',3,2),(88,'Buddhika Wanniarachchi','Buddhika','C1234583','6789 Birch Lane','0710714400','peter.brown@example.com',NULL,1,'2025-03-30',1,3),(89,'Sugath Nishantha','Nishantha','C1234584','6789 Moratuwa','0710714455','nishantha.brown@example.com',NULL,1,'2025-04-30',1,3),(90,'Sugath Nishantha','Nishantha','C0000001','6789 Moratuwa','0710714465','nishantha.brown@example.com',NULL,1,NULL,1,3),(91,'John Doe','John','C1234599','1234 Elm Street','0712345600','john.doe@example.com',NULL,1,'2025-03-30',1,1),(92,'Amal Perera','Amal','C0000004',NULL,'0710714435','amal@hotmail.com',NULL,1,NULL,1,2),(93,'Kasun Kalhara','Kasun','C0000005','Moratuwa','0777745678','kasun@gmail.com',NULL,1,NULL,1,2),(94,'Dilushi Rathnayake','Dilu','C0000007','411/15, Pepiliyana','0715663851','dilu@gmail.com',NULL,2,NULL,1,3),(95,'Randula Pernando','Randula','C202506220001','No 90, Katubedda, Moratuwa','0774563215','randu@gmail.com',NULL,1,NULL,2,1),(96,'Namal Karunarathna','Namal','C0000010','1234 Elm Street','0712345000','Namal.doe@example.com',NULL,1,'2025-06-30',1,1);
/*!40000 ALTER TABLE `customer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `customerfeedback`
--

LOCK TABLES `customerfeedback` WRITE;
/*!40000 ALTER TABLE `customerfeedback` DISABLE KEYS */;
/*!40000 ALTER TABLE `customerfeedback` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `customerstatus`
--

LOCK TABLES `customerstatus` WRITE;
/*!40000 ALTER TABLE `customerstatus` DISABLE KEYS */;
INSERT INTO `customerstatus` VALUES (1,'Default'),(2,'Loyal'),(3,'Risk');
/*!40000 ALTER TABLE `customerstatus` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `customertype`
--

LOCK TABLES `customertype` WRITE;
/*!40000 ALTER TABLE `customertype` DISABLE KEYS */;
INSERT INTO `customertype` VALUES (1,'Regular'),(2,'Occasional'),(3,'Luxury');
/*!40000 ALTER TABLE `customertype` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `designation`
--

LOCK TABLES `designation` WRITE;
/*!40000 ALTER TABLE `designation` DISABLE KEYS */;
INSERT INTO `designation` VALUES (1,'Salon Manager'),(2,'Receptionist'),(3,'Beautician'),(4,'Hairdresser'),(5,'Cleaner'),(6,'Cashier'),(7,'Assistant Beautician');
/*!40000 ALTER TABLE `designation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `discount`
--

LOCK TABLES `discount` WRITE;
/*!40000 ALTER TABLE `discount` DISABLE KEYS */;
/*!40000 ALTER TABLE `discount` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `discounttype`
--

LOCK TABLES `discounttype` WRITE;
/*!40000 ALTER TABLE `discounttype` DISABLE KEYS */;
/*!40000 ALTER TABLE `discounttype` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `employee`
--

LOCK TABLES `employee` WRITE;
/*!40000 ALTER TABLE `employee` DISABLE KEYS */;
INSERT INTO `employee` VALUES (1,'E001','Nadeesha Fernando','Nadeesha',NULL,2,'1985-06-15','856789012V','No.12, Galle Road, Colombo 3','0771234567','0112345678','nadeesha@gmail.com',1,1,'2020-01-10','Experienced salon manager',1),(2,'E002','Tharindu Perera','Tharindu',NULL,1,'1992-03-25','923456789V','No.88, Kandy Road, Kurunegala','0712345678','0371234567','tharindu.p@gmail.com',1,2,'2021-05-12','Receptionist with customer care experience',1),(3,'E003','Nipuni Jayasinghe','Nipuni',NULL,2,'1994-11-30','944567890V','No.7, Negombo Road, Wattala','0769876543',NULL,'nipuni.j@gmail.com',1,3,'2022-02-20','Senior beautician',1),(4,'E004','Sanduni Silva','Sanduni',NULL,2,'1998-01-20','984567891V','No.22, Beach Road, Matara','0751234569',NULL,'sanduni.s@gmail.com',2,3,'2023-06-01','Junior beautician',1),(5,'E005','Dinuka Madushanka','Dinuka',NULL,1,'1990-08-14','905678912V','No.5, Lake Road, Anuradhapura','0704567890',NULL,'dinuka.m@gmail.com',1,4,'2021-07-10','Hairdresser with 5 years experience',1),(6,'E006','Nimali Rajapaksha','Nimali',NULL,2,'1996-09-05','964567893V','No.40, New Town, Gampaha','0781234560',NULL,'nimali.r@gmail.com',3,3,'2024-01-15','Part-time beautician',1),(7,'E007','Kasun Abeywickrama','Kasun',NULL,1,'1988-12-19','883456789V','No.12, Temple Road, Kalutara','0723456789','0345678901','kasun.a@gmail.com',1,6,'2020-09-22','Handles cash transactions',1),(8,'E008','Sachini De Silva','Sachini',NULL,2,'1993-04-08','934567891V','No.17, Main Street, Ratnapura','0752345678',NULL,'sachini.d@gmail.com',2,2,'2024-03-10','Contract receptionist',1),(9,'E009','Thisara Bandara','Thisara',NULL,1,'1995-07-30','954567892V','No.55, Park Road, Badulla','0779876543',NULL,'thisara.b@gmail.com',1,5,'2023-04-11','Cleaner with hotel experience',1),(10,'E010','Upeka Rathnayake','Upeka',NULL,2,'1997-10-02','974567893V','No.33, River Road, Polonnaruwa','0713456789',NULL,'upeka.r@gmail.com',3,7,'2024-06-15','Assistant beautician',1),(11,'E011','Sahan Ranasinghe','Sahan',NULL,1,'1991-02-12','913456789V','No.9, City Road, Jaffna','0745678901',NULL,'sahan.r@gmail.com',1,4,'2022-10-01','Senior hairdresser',1),(12,'E012','Ishara Weerakkody','Ishara',NULL,2,'1999-05-19','994567894V','No.6, Central Road, Nuwara Eliya','0765432198',NULL,'ishara.w@gmail.com',2,7,'2024-05-05','New recruit',1),(13,'E013','Ravindu Karunaratne','Ravindu',NULL,1,'1987-11-11','873456789V','No.10, Forest Lane, Kegalle','0709876543',NULL,'ravindu.k@gmail.com',1,5,'2021-03-08','Senior cleaner',1),(14,'E014','Hansika Perera','Hansika',NULL,2,'1990-06-22','905678913V','No.20, Flower Road, Colombo 7','0723456790',NULL,'hansika.p@gmail.com',1,3,'2021-08-25','Makeup specialist',2),(15,'E015','Kavindu Jayalath','Kavindu',NULL,1,'1993-09-14','935678912V','No.44, Canal Road, Panadura','0787654321',NULL,'kavindu.j@gmail.com',1,6,'2020-11-18','Senior cashier',3);
/*!40000 ALTER TABLE `employee` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `empstatus`
--

LOCK TABLES `empstatus` WRITE;
/*!40000 ALTER TABLE `empstatus` DISABLE KEYS */;
INSERT INTO `empstatus` VALUES (1,'Active'),(2,'On Leave'),(3,'Resigned');
/*!40000 ALTER TABLE `empstatus` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `emptype`
--

LOCK TABLES `emptype` WRITE;
/*!40000 ALTER TABLE `emptype` DISABLE KEYS */;
INSERT INTO `emptype` VALUES (1,'Permanent'),(2,'Contract'),(3,'Part-Time');
/*!40000 ALTER TABLE `emptype` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `gender`
--

LOCK TABLES `gender` WRITE;
/*!40000 ALTER TABLE `gender` DISABLE KEYS */;
INSERT INTO `gender` VALUES (1,'male'),(2,'female');
/*!40000 ALTER TABLE `gender` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `good_receive_note`
--

LOCK TABLES `good_receive_note` WRITE;
/*!40000 ALTER TABLE `good_receive_note` DISABLE KEYS */;
/*!40000 ALTER TABLE `good_receive_note` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `grn_item`
--

LOCK TABLES `grn_item` WRITE;
/*!40000 ALTER TABLE `grn_item` DISABLE KEYS */;
/*!40000 ALTER TABLE `grn_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `grn_status`
--

LOCK TABLES `grn_status` WRITE;
/*!40000 ALTER TABLE `grn_status` DISABLE KEYS */;
/*!40000 ALTER TABLE `grn_status` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `invoice`
--

LOCK TABLES `invoice` WRITE;
/*!40000 ALTER TABLE `invoice` DISABLE KEYS */;
/*!40000 ALTER TABLE `invoice` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `invoice_item`
--

LOCK TABLES `invoice_item` WRITE;
/*!40000 ALTER TABLE `invoice_item` DISABLE KEYS */;
/*!40000 ALTER TABLE `invoice_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `item`
--

LOCK TABLES `item` WRITE;
/*!40000 ALTER TABLE `item` DISABLE KEYS */;
INSERT INTO `item` VALUES (1,'ITM001','L’Oreal Hair Serum','2024-01-10',25.00,1,1,1,1,2500.00,1800.00,5),(2,'ITM002','Dove Shampoo 200ml','2024-02-05',50.00,1,1,2,1,1200.00,850.00,10),(3,'ITM003','Garnier Face Wash','2024-03-15',40.00,1,1,3,2,900.00,600.00,5),(4,'ITM004','Nivea Moisturizer 100ml','2024-02-20',30.00,1,1,4,3,1100.00,800.00,5),(5,'ITM005','MAC Lipstick Ruby Woo','2024-04-01',15.00,1,2,5,4,5500.00,4000.00,3),(6,'ITM006','Lakmé Eyeliner','2024-03-25',20.00,1,2,6,4,750.00,500.00,5),(7,'ITM007','Maybelline Mascara','2024-04-10',18.00,1,2,7,4,1800.00,1300.00,4),(8,'ITM008','Neutrogena Sunscreen SPF50','2024-05-01',22.00,1,1,8,5,2100.00,1500.00,6),(9,'ITM009','Tresemmé Conditioner 250ml','2024-03-11',35.00,1,1,9,6,1350.00,1000.00,8),(10,'ITM010','The Body Shop Face Mask','2024-04-18',10.00,1,4,10,7,2800.00,2000.00,2),(11,'ITM011','Himalaya Neem Face Wash','2024-02-15',45.00,1,3,11,2,650.00,400.00,10),(12,'ITM012','Olay Night Cream 50g','2024-04-05',12.00,1,4,12,8,3300.00,2500.00,3),(13,'ITM013','Revlon Foundation 30ml','2024-05-12',17.00,1,1,13,9,3600.00,2800.00,4),(14,'ITM014','LUX Body Wash 250ml','2024-03-30',28.00,1,1,14,10,950.00,700.00,6),(15,'ITM015','Vaseline Body Lotion 400ml','2024-01-25',32.00,1,1,15,10,1150.00,900.00,5);
/*!40000 ALTER TABLE `item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `itembrand`
--

LOCK TABLES `itembrand` WRITE;
/*!40000 ALTER TABLE `itembrand` DISABLE KEYS */;
INSERT INTO `itembrand` VALUES (1,'L’Oreal'),(2,'Dove'),(3,'Garnier'),(4,'Nivea'),(5,'MAC'),(6,'Lakmé'),(7,'Maybelline'),(8,'Neutrogena'),(9,'Tresemmé'),(10,'The Body Shop'),(11,'Himalaya'),(12,'Olay'),(13,'Revlon'),(14,'LUX'),(15,'Vaseline');
/*!40000 ALTER TABLE `itembrand` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `itemstatus`
--

LOCK TABLES `itemstatus` WRITE;
/*!40000 ALTER TABLE `itemstatus` DISABLE KEYS */;
INSERT INTO `itemstatus` VALUES (1,'Active'),(2,'Inactive'),(3,'Discontinued');
/*!40000 ALTER TABLE `itemstatus` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `itemstock_location`
--

LOCK TABLES `itemstock_location` WRITE;
/*!40000 ALTER TABLE `itemstock_location` DISABLE KEYS */;
/*!40000 ALTER TABLE `itemstock_location` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `location`
--

LOCK TABLES `location` WRITE;
/*!40000 ALTER TABLE `location` DISABLE KEYS */;
/*!40000 ALTER TABLE `location` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `locationtype`
--

LOCK TABLES `locationtype` WRITE;
/*!40000 ALTER TABLE `locationtype` DISABLE KEYS */;
/*!40000 ALTER TABLE `locationtype` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `module`
--

LOCK TABLES `module` WRITE;
/*!40000 ALTER TABLE `module` DISABLE KEYS */;
/*!40000 ALTER TABLE `module` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `operation`
--

LOCK TABLES `operation` WRITE;
/*!40000 ALTER TABLE `operation` DISABLE KEYS */;
/*!40000 ALTER TABLE `operation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `opetype`
--

LOCK TABLES `opetype` WRITE;
/*!40000 ALTER TABLE `opetype` DISABLE KEYS */;
/*!40000 ALTER TABLE `opetype` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `payment`
--

LOCK TABLES `payment` WRITE;
/*!40000 ALTER TABLE `payment` DISABLE KEYS */;
/*!40000 ALTER TABLE `payment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `paymentmethod`
--

LOCK TABLES `paymentmethod` WRITE;
/*!40000 ALTER TABLE `paymentmethod` DISABLE KEYS */;
/*!40000 ALTER TABLE `paymentmethod` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `paymentstatus`
--

LOCK TABLES `paymentstatus` WRITE;
/*!40000 ALTER TABLE `paymentstatus` DISABLE KEYS */;
/*!40000 ALTER TABLE `paymentstatus` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `poitem`
--

LOCK TABLES `poitem` WRITE;
/*!40000 ALTER TABLE `poitem` DISABLE KEYS */;
/*!40000 ALTER TABLE `poitem` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `postatus`
--

LOCK TABLES `postatus` WRITE;
/*!40000 ALTER TABLE `postatus` DISABLE KEYS */;
/*!40000 ALTER TABLE `postatus` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `privilege`
--

LOCK TABLES `privilege` WRITE;
/*!40000 ALTER TABLE `privilege` DISABLE KEYS */;
/*!40000 ALTER TABLE `privilege` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `promotion`
--

LOCK TABLES `promotion` WRITE;
/*!40000 ALTER TABLE `promotion` DISABLE KEYS */;
/*!40000 ALTER TABLE `promotion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `promotionstatus`
--

LOCK TABLES `promotionstatus` WRITE;
/*!40000 ALTER TABLE `promotionstatus` DISABLE KEYS */;
/*!40000 ALTER TABLE `promotionstatus` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `purchaseorder`
--

LOCK TABLES `purchaseorder` WRITE;
/*!40000 ALTER TABLE `purchaseorder` DISABLE KEYS */;
/*!40000 ALTER TABLE `purchaseorder` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `question`
--

LOCK TABLES `question` WRITE;
/*!40000 ALTER TABLE `question` DISABLE KEYS */;
/*!40000 ALTER TABLE `question` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `response`
--

LOCK TABLES `response` WRITE;
/*!40000 ALTER TABLE `response` DISABLE KEYS */;
/*!40000 ALTER TABLE `response` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `service`
--

LOCK TABLES `service` WRITE;
/*!40000 ALTER TABLE `service` DISABLE KEYS */;
/*!40000 ALTER TABLE `service` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `service_has_employee`
--

LOCK TABLES `service_has_employee` WRITE;
/*!40000 ALTER TABLE `service_has_employee` DISABLE KEYS */;
/*!40000 ALTER TABLE `service_has_employee` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `service_has_promotion`
--

LOCK TABLES `service_has_promotion` WRITE;
/*!40000 ALTER TABLE `service_has_promotion` DISABLE KEYS */;
/*!40000 ALTER TABLE `service_has_promotion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `servicecategory`
--

LOCK TABLES `servicecategory` WRITE;
/*!40000 ALTER TABLE `servicecategory` DISABLE KEYS */;
/*!40000 ALTER TABLE `servicecategory` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `servicestatus`
--

LOCK TABLES `servicestatus` WRITE;
/*!40000 ALTER TABLE `servicestatus` DISABLE KEYS */;
/*!40000 ALTER TABLE `servicestatus` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `stocktransfer`
--

LOCK TABLES `stocktransfer` WRITE;
/*!40000 ALTER TABLE `stocktransfer` DISABLE KEYS */;
/*!40000 ALTER TABLE `stocktransfer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `subcategory`
--

LOCK TABLES `subcategory` WRITE;
/*!40000 ALTER TABLE `subcategory` DISABLE KEYS */;
INSERT INTO `subcategory` VALUES (1,'Shampoo',1),(2,'Face Wash',2),(3,'Moisturizer',3),(4,'Lipstick',4),(5,'Sunscreen',3),(6,'Conditioner',1),(7,'Face Mask',2),(8,'Night Cream',3),(9,'Foundation',4),(10,'Body Wash',5);
/*!40000 ALTER TABLE `subcategory` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `supplier`
--

LOCK TABLES `supplier` WRITE;
/*!40000 ALTER TABLE `supplier` DISABLE KEYS */;
INSERT INTO `supplier` VALUES (21,'More Glow Distributors','REG202311','No. 18, Main Street, Colombo 04','0771234000','Sunil Perera','contact@moreglow.lk','2023-01-15','Main cosmetics supplier.',1,1),(22,'Salon Essentials Pvt Ltd','REG202302','45/2, Galle Road, Dehiwala','0712345678','Shalini Silva','sales@salonessentials.lk','2023-02-10','Provides shampoos and conditioners.',1,2),(23,'Lanka Hair Supplies','REG202303','88, Kandy Road, Kiribathgoda','0723456789','Sunil Fernando','support@lankahair.com','2023-03-05','Specialist in haircare products.',2,1),(24,'Nails & Co.','REG202304','12A, Negombo Road, Wattala','0781239876','Dilani Jayasuriya','info@nailsco.lk','2023-04-22','Manicure and pedicure supply.',2,2),(25,'BeautyPro Suppliers','REG202305','203, Dharmapala Mawatha, Colombo 07','0767894321','Roshan Wijesekara','beautypro@sup.lk','2023-05-12','Professional beauty equipment.',3,1),(26,'Chic Imports','REG202306','56, Gampaha Road, Ja-Ela','0701234987','Kavindi Senanayake','orders@chicimports.com','2023-06-18','Imported beauty tools.',3,2),(27,'Cosmo Traders','REG202307','134, Old Kesbewa Road, Nugegoda','0752345670','Tharindu Jayalath','cosmo@traders.lk','2023-07-04','Wholesale cosmetic distributor.',1,1),(28,'Hair & Style Ltd','REG202308','2/1, Rajagiriya Junction, Rajagiriya','0743456781','Manori Dias','hairstyle@support.com','2023-08-01','Hair dyes and styling tools.',2,2),(29,'Premium Salon Supplies','REG202309','77, Havelock Road, Colombo 05','0738765432','Duminda Ranatunga','premiumsupp@salon.lk','2023-09-14','Premium product supplier.',1,1),(30,'Luxe Beauty Wholesale','REG202310','10B, Matara Road, Galle','0791234569','Ishara Abeywickrama','luxebw@beauty.lk','2023-10-09','Luxury skincare and tools.',3,2),(31,'Glow more Distributors','REG202311','No. 15, Main Street, Colombo 03','0771234567','Kamal peris','contact@glowdistributors.lk','2023-01-15','Main cosmetics supplier.',1,1),(32,'Glow more Distributors','REG202311','No. 15, Main Street, Colombo 03','0771234567','Kamal peris','contact@glowdistributors.lk','2023-01-15','Main cosmetics supplier.',1,1);
/*!40000 ALTER TABLE `supplier` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `supplierstate`
--

LOCK TABLES `supplierstate` WRITE;
/*!40000 ALTER TABLE `supplierstate` DISABLE KEYS */;
INSERT INTO `supplierstate` VALUES (1,'Active'),(2,'Inactive'),(3,'Pending Approval');
/*!40000 ALTER TABLE `supplierstate` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `supplierstype`
--

LOCK TABLES `supplierstype` WRITE;
/*!40000 ALTER TABLE `supplierstype` DISABLE KEYS */;
INSERT INTO `supplierstype` VALUES (1,'Local'),(2,'International');
/*!40000 ALTER TABLE `supplierstype` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `supply`
--

LOCK TABLES `supply` WRITE;
/*!40000 ALTER TABLE `supply` DISABLE KEYS */;
/*!40000 ALTER TABLE `supply` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `transferitem`
--

LOCK TABLES `transferitem` WRITE;
/*!40000 ALTER TABLE `transferitem` DISABLE KEYS */;
/*!40000 ALTER TABLE `transferitem` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `unittype`
--

LOCK TABLES `unittype` WRITE;
/*!40000 ALTER TABLE `unittype` DISABLE KEYS */;
INSERT INTO `unittype` VALUES (1,'Bottle'),(2,'Piece'),(3,'Tube'),(4,'Jar'),(5,'Pack');
/*!40000 ALTER TABLE `unittype` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `userrole`
--

LOCK TABLES `userrole` WRITE;
/*!40000 ALTER TABLE `userrole` DISABLE KEYS */;
/*!40000 ALTER TABLE `userrole` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `usestatus`
--

LOCK TABLES `usestatus` WRITE;
/*!40000 ALTER TABLE `usestatus` DISABLE KEYS */;
/*!40000 ALTER TABLE `usestatus` ENABLE KEYS */;
UNLOCK TABLES;

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

-- Dump completed on 2025-07-07 23:09:16
