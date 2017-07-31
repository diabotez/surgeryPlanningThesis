-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               5.7.18-0ubuntu0.16.04.1 - (Ubuntu)
-- Server OS:                    Linux
-- HeidiSQL Version:             9.4.0.5125
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- Dumping database structure for surgery_planning
DROP DATABASE IF EXISTS `surgery_planning`;
CREATE DATABASE IF NOT EXISTS `surgery_planning` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `surgery_planning`;

-- Dumping structure for table surgery_planning.departments
DROP TABLE IF EXISTS `departments`;
CREATE TABLE IF NOT EXISTS `departments` (
  `departmentId` varchar(32) NOT NULL,
  `departmentName` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Table for department details';

-- Dumping data for table surgery_planning.departments: ~4 rows (approximately)
DELETE FROM `departments`;
/*!40000 ALTER TABLE `departments` DISABLE KEYS */;
INSERT INTO `departments` (`departmentId`, `departmentName`) VALUES
	('DPT12', 'Knee Surgery'),
	('DPT349', 'department'),
	('DPT382', 'dpt'),
	('DPT774', 'medicalDepartment');
/*!40000 ALTER TABLE `departments` ENABLE KEYS */;

-- Dumping structure for table surgery_planning.doctors
DROP TABLE IF EXISTS `doctors`;
CREATE TABLE IF NOT EXISTS `doctors` (
  `doctorId` varchar(32) NOT NULL,
  `firstName` varchar(128) NOT NULL,
  `lastName` varchar(128) NOT NULL,
  `coordinatorId` varchar(32) NOT NULL,
  `departmentId` varchar(32) NOT NULL,
  `teamId` varchar(32) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Table for doctors details';

-- Dumping data for table surgery_planning.doctors: ~19 rows (approximately)
DELETE FROM `doctors`;
/*!40000 ALTER TABLE `doctors` DISABLE KEYS */;
INSERT INTO `doctors` (`doctorId`, `firstName`, `lastName`, `coordinatorId`, `departmentId`, `teamId`) VALUES
	('D32', 'Clark', 'Kent', 'D26', 'DPT12', 'TM52'),
	('D1', 'Emilee', 'Bromberg', 'D1', 'DPT349', 'TM21'),
	('D5', 'Ami', 'Keister', 'D4', 'DPT349', 'TM67'),
	('D12', 'Jamey', 'Mangold', 'D12', 'DPT349', 'TM60'),
	('D16', 'Golda', 'Hunton', 'D26', 'DPT349', 'TM52'),
	('D2', 'Neomi', 'Rinaldo', 'D1', 'DPT349', 'TM21'),
	('D3', 'Jona', 'Gandeee', 'D1', 'DPT349', 'TM21'),
	('D4', 'Hester', 'Therrien', 'D4', 'DPT349', 'TM67'),
	('D6', 'Gennie', 'Schexnayder', 'D4', 'DPT349', 'TM67'),
	('D7', 'Syble', 'Ruggieri', 'D4', 'DPT349', 'TM67'),
	('D13', 'Tilda', 'Launius', 'D12', 'DPT349', 'TM60'),
	('D14', 'Alde', 'Burnes', 'D54', 'DPT349', 'TM6'),
	('D15', 'Treena', 'Escalante', 'D12', 'DPT349', 'TM60'),
	('D8', 'Izola', 'Vandermark', 'D8', 'DPT349', 'TM72'),
	('D9', 'Hannelore', 'Dedrick', 'D8', 'DPT349', 'TM72'),
	('D10', 'Lurlene', 'Vandam', 'D8', 'DPT349', 'TM72'),
	('D11', 'Karey', 'Maguire', 'D8', 'DPT349', 'TM72'),
	('D26', 'Diana', 'Prince', 'D26', 'DPT382', 'TM52'),
	('D54', 'Botez', 'Diana', 'D54', 'DPT774', 'TM6');
/*!40000 ALTER TABLE `doctors` ENABLE KEYS */;

-- Dumping structure for table surgery_planning.medicalTeams
DROP TABLE IF EXISTS `medicalTeams`;
CREATE TABLE IF NOT EXISTS `medicalTeams` (
  `teamId` varchar(32) NOT NULL,
  `teamName` varchar(128) NOT NULL,
  `teamLeaderId` varchar(32) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Table of medical team details';

-- Dumping data for table surgery_planning.medicalTeams: ~6 rows (approximately)
DELETE FROM `medicalTeams`;
/*!40000 ALTER TABLE `medicalTeams` DISABLE KEYS */;
INSERT INTO `medicalTeams` (`teamId`, `teamName`, `teamLeaderId`) VALUES
	('TM52', 'Superwoman', 'D26'),
	('TM21', 'Rainbows', 'D1'),
	('TM67', 'Blue Sea', 'D4'),
	('TM60', 'Happy Team', 'D12'),
	('TM72', 'coder fault', 'D8'),
	('TM6', 'Diana', 'D54');
/*!40000 ALTER TABLE `medicalTeams` ENABLE KEYS */;

-- Dumping structure for table surgery_planning.operatingRoomDetails
DROP TABLE IF EXISTS `operatingRoomDetails`;
CREATE TABLE IF NOT EXISTS `operatingRoomDetails` (
  `orId` varchar(32) NOT NULL,
  `scheduleDate` date NOT NULL,
  `startTime` time NOT NULL,
  `endTime` time NOT NULL,
  `teamId` varchar(32) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='details about operating rooms in the operating theater';

-- Dumping data for table surgery_planning.operatingRoomDetails: ~20 rows (approximately)
DELETE FROM `operatingRoomDetails`;
/*!40000 ALTER TABLE `operatingRoomDetails` DISABLE KEYS */;
INSERT INTO `operatingRoomDetails` (`orId`, `scheduleDate`, `startTime`, `endTime`, `teamId`) VALUES
	('OR15', '2017-07-22', '08:15:00', '14:35:00', 'TM21'),
	('OR15', '2017-07-21', '08:15:00', '14:35:00', 'TM21'),
	('OR15', '2017-07-20', '08:15:00', '14:35:00', 'TM21'),
	('OR15', '2017-07-23', '08:15:00', '14:35:00', 'TM67'),
	('OR15', '2017-07-24', '08:15:00', '14:35:00', 'TM72'),
	('OR15', '2017-07-25', '08:15:00', '14:35:00', 'TM60'),
	('OR15', '2017-07-26', '08:00:00', '15:00:00', 'TM67'),
	('OR34', '2017-07-15', '08:25:00', '14:45:00', 'TM72'),
	('OR34', '2017-07-16', '08:25:00', '14:45:00', 'TM72'),
	('OR34', '2017-07-17', '08:25:00', '14:45:00', 'TM72'),
	('OR34', '2017-06-14', '08:00:00', '15:00:00', 'TM72'),
	('OR12', '2017-06-25', '08:35:00', '14:55:00', 'TM60'),
	('OR12', '2017-07-01', '08:00:00', '15:00:00', 'TM21'),
	('OR20', '2017-07-01', '15:30:00', '20:00:00', 'TM72'),
	('OR4', '2017-06-14', '14:00:00', '18:20:00', 'TM52'),
	('OR4', '2017-06-12', '15:25:00', '20:05:00', 'TM52'),
	('OR13', '2017-06-14', '14:00:00', '19:20:00', 'TM67'),
	('OR4', '2017-06-21', '14:00:00', '19:00:00', 'TM60'),
	('OR13', '2017-06-16', '14:00:00', '16:45:00', 'TM67'),
	('OR4', '2017-06-23', '14:00:00', '16:10:00', 'TM6');
/*!40000 ALTER TABLE `operatingRoomDetails` ENABLE KEYS */;

-- Dumping structure for table surgery_planning.operationTheater
DROP TABLE IF EXISTS `operationTheater`;
CREATE TABLE IF NOT EXISTS `operationTheater` (
  `orId` varchar(32) NOT NULL,
  `orName` varchar(255) NOT NULL,
  `orType` enum('M','E') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Table of operation theater details';

-- Dumping data for table surgery_planning.operationTheater: ~6 rows (approximately)
DELETE FROM `operationTheater`;
/*!40000 ALTER TABLE `operationTheater` DISABLE KEYS */;
INSERT INTO `operationTheater` (`orId`, `orName`, `orType`) VALUES
	('OR15', 'room 1', 'M'),
	('OR34', 'room 2', 'M'),
	('OR12', 'room 3', 'M'),
	('OR20', 'room 5', 'E'),
	('OR4', 'Operation room', 'E'),
	('OR13', 'quirofano', 'E');
/*!40000 ALTER TABLE `operationTheater` ENABLE KEYS */;

-- Dumping structure for table surgery_planning.pathologies
DROP TABLE IF EXISTS `pathologies`;
CREATE TABLE IF NOT EXISTS `pathologies` (
  `pathologyId` varchar(32) NOT NULL,
  `pathologyName` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Table for pathology details';

-- Dumping data for table surgery_planning.pathologies: ~62 rows (approximately)
DELETE FROM `pathologies`;
/*!40000 ALTER TABLE `pathologies` DISABLE KEYS */;
INSERT INTO `pathologies` (`pathologyId`, `pathologyName`) VALUES
	('PTG6752', 'ptg0'),
	('PTG8862', 'ptg1'),
	('PTG8947', 'ptg6'),
	('PTG5985', 'ptg3'),
	('PTG3131', 'ptg4'),
	('PTG7381', 'ptg5'),
	('PTG8384', 'ptg7'),
	('PTG170', 'ptg8'),
	('PTG2575', 'ptg9'),
	('PTG7990', 'ptg10'),
	('PTG4189', 'ptg11'),
	('PTG3580', 'ptg12'),
	('PTG2070', 'ptg13'),
	('PTG9331', 'ptg14'),
	('PTG7519', 'ptg15'),
	('PTG7255', 'ptg16'),
	('PTG8512', 'ptg17'),
	('PTG5563', 'ptg18'),
	('PTG4748', 'ptg19'),
	('PTG2281', 'ptg20'),
	('PTG5908', 'ptg21'),
	('PTG3981', 'ptg22'),
	('PTG3498', 'ptg23'),
	('PTG4494', 'ptg24'),
	('PTG6187', 'ptg25'),
	('PTG2425', 'ptg26'),
	('PTG4592', 'ptg27'),
	('PTG9356', 'ptg28'),
	('PTG3405', 'ptg29'),
	('PTG1352', 'ptg30'),
	('PTG8935', 'ptg31'),
	('PTG3880', 'ptg32'),
	('PTG9281', 'ptg33'),
	('PTG2639', 'ptg34'),
	('PTG8939', 'ptg35'),
	('PTG8827', 'ptg36'),
	('PTG4512', 'ptg37'),
	('PTG2133', 'ptg38'),
	('PTG313', 'ptg39'),
	('PTG1919', 'ptg40'),
	('PTG5853', 'ptg41'),
	('PTG3222', 'ptg42'),
	('PTG2336', 'ptg43'),
	('PTG4927', 'ptg44'),
	('PTG5862', 'ptg45'),
	('PTG2879', 'ptg46'),
	('PTG3005', 'ptg47'),
	('PTG1661', 'ptg48'),
	('PTG5741', 'ptg49'),
	('PTG3681', 'ptg50'),
	('PTG1611', 'ptg51'),
	('PTG3120', 'ptg52'),
	('PTG731', 'ptg53'),
	('PTG8617', 'ptg54'),
	('PTG4291', 'ptg55'),
	('PTG6932', 'ptg56'),
	('PTG9685', 'ptg57'),
	('PTG5543', 'ptg58'),
	('PTG5716', 'ptg59'),
	('PTG1667', 'pathology'),
	('PTG7349', 'path'),
	('PTG4736', 'ptg2');
/*!40000 ALTER TABLE `pathologies` ENABLE KEYS */;

-- Dumping structure for table surgery_planning.patientDetails
DROP TABLE IF EXISTS `patientDetails`;
CREATE TABLE IF NOT EXISTS `patientDetails` (
  `patientId` varchar(32) NOT NULL,
  `surgeryId` varchar(32) NOT NULL,
  `pathologyId` varchar(32) NOT NULL,
  `doctorId` varchar(32) NOT NULL,
  `teamLeaderId` varchar(32) NOT NULL,
  `admissionDate` date NOT NULL,
  `scheduled` enum('Y','N') NOT NULL,
  `completed` enum('Y','N') NOT NULL,
  `scheduledDate` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Table for patients'' surgery details';

-- Dumping data for table surgery_planning.patientDetails: ~61 rows (approximately)
DELETE FROM `patientDetails`;
/*!40000 ALTER TABLE `patientDetails` DISABLE KEYS */;
INSERT INTO `patientDetails` (`patientId`, `surgeryId`, `pathologyId`, `doctorId`, `teamLeaderId`, `admissionDate`, `scheduled`, `completed`, `scheduledDate`) VALUES
	('P4045150', 'SRG6169', 'PTG8862', 'D2', 'D1', '2017-06-01', 'Y', 'N', '2017-07-21'),
	('P7805710', 'SRG1862', 'PTG4736', 'D11', 'D8', '2017-05-15', 'Y', 'N', '2017-07-15'),
	('P318908', 'SRG6317', 'PTG5985', 'D6', 'D4', '2017-06-01', 'Y', 'N', '2017-07-26'),
	('P8244481', 'SRG7601', 'PTG3131', 'D9', 'D8', '2017-06-01', 'Y', 'N', '2017-07-01'),
	('P4301623', 'SRG6139', 'PTG7381', 'D9', 'D8', '2017-06-01', 'Y', 'N', '2017-07-16'),
	('P3396591', 'SRG262', 'PTG8947', 'D14', 'D54', '2017-06-01', 'N', 'N', NULL),
	('P5796177', 'SRG4621', 'PTG8384', 'D8', 'D8', '2017-06-01', 'Y', 'N', '2017-07-01'),
	('P1194274', 'SRG654', 'PTG170', 'D13', 'D54', '2017-06-01', 'N', 'N', NULL),
	('P482598', 'SRG4642', 'PTG2575', 'D8', 'D8', '2017-06-01', 'Y', 'N', '2017-07-16'),
	('P8800815', 'SRG2871', 'PTG7990', 'D14', 'D54', '2017-06-01', 'Y', 'Y', '2017-06-21'),
	('P5021575', 'SRG2615', 'PTG4189', 'D13', 'D54', '2017-06-01', 'N', 'N', NULL),
	('P7061655', 'SRG951', 'PTG3580', 'D10', 'D8', '2017-06-01', 'N', 'N', NULL),
	('P7424396', 'SRG269', 'PTG2070', 'D7', 'D4', '2017-06-01', 'Y', 'N', '2017-07-23'),
	('P3990993', 'SRG9270', 'PTG9331', 'D4', 'D4', '2017-06-01', 'N', 'N', NULL),
	('P6413303', 'SRG6326', 'PTG7519', 'D14', 'D54', '2017-06-01', 'N', 'N', NULL),
	('P2260140', 'SRG2492', 'PTG7255', 'D6', 'D4', '2017-06-01', 'N', 'N', NULL),
	('P4035586', 'SRG5314', 'PTG8512', 'D8', 'D8', '2017-06-01', 'N', 'N', NULL),
	('P1961180', 'SRG3367', 'PTG5563', 'D10', 'D8', '2017-06-01', 'N', 'N', NULL),
	('P3002268', 'SRG4768', 'PTG4748', 'D8', 'D8', '2017-06-01', 'Y', 'N', '2017-07-15'),
	('P7226154', 'SRG8205', 'PTG2281', 'D15', 'D54', '2017-06-01', 'N', 'N', NULL),
	('P8236233', 'SRG811', 'PTG5908', 'D3', 'D1', '2017-04-01', 'N', 'N', NULL),
	('P2962060', 'SRG3347', 'PTG3981', 'D4', 'D4', '2017-06-01', 'N', 'N', NULL),
	('P9641308', 'SRG7350', 'PTG3498', 'D2', 'D1', '2017-05-26', 'Y', 'N', '2017-07-01'),
	('P619590', 'SRG9753', 'PTG4494', 'D2', 'D1', '2017-05-30', 'Y', 'N', '2017-07-21'),
	('P143088', 'SRG9544', 'PTG6187', 'D15', 'D54', '2017-06-01', 'N', 'N', NULL),
	('P1192102', 'SRG5781', 'PTG2425', 'D1', 'D1', '2017-05-29', 'N', 'N', NULL),
	('P7363825', 'SRG8956', 'PTG4592', 'D16', 'D26', '2017-06-01', 'N', 'N', NULL),
	('P2392779', 'SRG8792', 'PTG9356', 'D10', 'D8', '2017-06-01', 'Y', 'N', '2017-07-16'),
	('P3096150', 'SRG3928', 'PTG3405', 'D13', 'D54', '2017-06-01', 'Y', 'N', '2017-06-21'),
	('P549868', 'SRG372', 'PTG1352', 'D16', 'D26', '2017-06-01', 'Y', 'Y', '2017-06-14'),
	('P4450544', 'SRG4680', 'PTG8935', 'D14', 'D54', '2017-06-01', 'N', 'N', NULL),
	('P4670218', 'SRG125', 'PTG3880', 'D13', 'D54', '2017-06-01', 'N', 'N', NULL),
	('P9608683', 'SRG437', 'PTG9281', 'D10', 'D8', '2017-06-01', 'N', 'N', NULL),
	('P6006595', 'SRG6234', 'PTG2639', 'D13', 'D54', '2017-06-01', 'N', 'N', NULL),
	('P5753535', 'SRG5152', 'PTG8939', 'D16', 'D26', '2017-06-01', 'N', 'N', NULL),
	('P8756253', 'SRG3853', 'PTG8827', 'D9', 'D8', '2017-06-01', 'N', 'N', NULL),
	('P825825', 'SRG2291', 'PTG4512', 'D8', 'D8', '2017-06-01', 'N', 'N', NULL),
	('P869168', 'SRG8754', 'PTG2133', 'D8', 'D8', '2017-06-01', 'N', 'N', NULL),
	('P4605964', 'SRG4461', 'PTG313', 'D6', 'D4', '2017-06-01', 'N', 'N', NULL),
	('P3260375', 'SRG5393', 'PTG1919', 'D16', 'D26', '2017-06-01', 'N', 'N', NULL),
	('P2819578', 'SRG3913', 'PTG5853', 'D3', 'D1', '2016-03-01', 'Y', 'Y', '2017-07-20'),
	('P8070675', 'SRG1879', 'PTG3222', 'D1', 'D1', '2016-06-01', 'Y', 'N', '2017-07-01'),
	('P35811', 'SRG9383', 'PTG2336', 'D13', 'D54', '2017-06-01', 'N', 'N', NULL),
	('P5291424', 'SRG7210', 'PTG4927', 'D9', 'D8', '2017-06-01', 'N', 'N', NULL),
	('P8224348', 'SRG1425', 'PTG5862', 'D11', 'D8', '2017-06-01', 'N', 'N', NULL),
	('P9344289', 'SRG2894', 'PTG2879', 'D16', 'D26', '2017-06-01', 'N', 'N', NULL),
	('P4657169', 'SRG1002', 'PTG3005', 'D11', 'D8', '2017-06-01', 'N', 'N', NULL),
	('P2887461', 'SRG292', 'PTG1661', 'D7', 'D4', '2017-06-01', 'Y', 'N', '2017-07-26'),
	('P3859290', 'SRG2192', 'PTG5741', 'D9', 'D8', '2017-06-01', 'N', 'N', NULL),
	('P7543160', 'SRG589', 'PTG3681', 'D6', 'D4', '2017-06-01', 'Y', 'N', '2017-07-23'),
	('P8372680', 'SRG3296', 'PTG1611', 'D14', 'D54', '2017-06-01', 'N', 'N', NULL),
	('P2104636', 'SRG3177', 'PTG3120', 'D3', 'D1', '2017-04-30', 'Y', 'N', '2017-07-01'),
	('P2742137', 'SRG9308', 'PTG731', 'D2', 'D1', '2017-05-01', 'Y', 'Y', '2017-07-20'),
	('P9899515', 'SRG7414', 'PTG8617', 'D12', 'D54', '2017-06-01', 'N', 'N', NULL),
	('P729035', 'SRG9150', 'PTG4291', 'D14', 'D54', '2017-06-01', 'N', 'N', NULL),
	('P5010003', 'SRG1990', 'PTG6932', 'D4', 'D4', '2017-06-01', 'N', 'N', NULL),
	('P8204008', 'SRG8120', 'PTG9685', 'D8', 'D8', '2017-06-01', 'N', 'N', NULL),
	('P8069559', 'SRG8098', 'PTG5543', 'D9', 'D8', '2017-06-01', 'N', 'N', NULL),
	('P1781316', 'SRG108', 'PTG5716', 'D8', 'D8', '2017-06-01', 'N', 'N', NULL),
	('P9788327', 'SRG1862', 'PTG8947', 'D4', 'D4', '2017-06-01', 'N', 'N', NULL),
	('P9015519', 'SRG4438', 'PTG6752', 'D16', 'D26', '2017-06-01', 'Y', 'Y', '2017-06-14');
/*!40000 ALTER TABLE `patientDetails` ENABLE KEYS */;

-- Dumping structure for table surgery_planning.patients
DROP TABLE IF EXISTS `patients`;
CREATE TABLE IF NOT EXISTS `patients` (
  `patientId` varchar(32) NOT NULL,
  `firstName` varchar(128) NOT NULL,
  `lastName` varchar(128) NOT NULL,
  `sex` enum('M','F','O') NOT NULL,
  `dob` date NOT NULL DEFAULT '9999-12-31',
  `remarks` varchar(255) NOT NULL DEFAULT 'No remarks!'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Table for patitents details';

-- Dumping data for table surgery_planning.patients: ~65 rows (approximately)
DELETE FROM `patients`;
/*!40000 ALTER TABLE `patients` DISABLE KEYS */;
INSERT INTO `patients` (`patientId`, `firstName`, `lastName`, `sex`, `dob`, `remarks`) VALUES
	('P9015519', 'Paulita', 'Jacobsen', 'M', '1993-02-02', 'No remarks!'),
	('P4045150', 'Les', 'Cosio', 'M', '9999-12-31', 'No remarks!'),
	('P7805710', 'Loise', 'Harting', 'F', '2001-03-12', 'No remarks!'),
	('P318908', 'Loriene', 'Wasielewski', 'F', '9999-12-31', 'No remarks!'),
	('P8244481', 'Teresa', 'Sill', 'F', '2000-02-13', 'No remarks!'),
	('P4301623', 'Laurice', 'Pratts', 'M', '1998-04-03', 'No remarks!'),
	('P3396591', 'Tamie', 'Acebedo', 'F', '1999-06-04', 'No remarks!'),
	('P5796177', 'Newton', 'Ganley', 'O', '9999-12-31', 'No remarks!'),
	('P1194274', 'Shelly', 'Zollinger', 'O', '9999-12-31', 'No remarks!'),
	('P482598', 'Sylvester', 'Walko', 'O', '9999-12-31', 'No remarks!'),
	('P8800815', 'Obdulia', 'Rosado', 'O', '9999-12-31', 'No remarks!'),
	('P5021575', 'Tamela', 'Buonocore', 'O', '9999-12-31', 'No remarks!'),
	('P7061655', 'Kellie', 'Southern', 'O', '9999-12-31', 'No remarks!'),
	('P7424396', 'Clarissa', 'Raab', 'O', '9999-12-31', 'No remarks!'),
	('P3990993', 'Walton', 'Ballengee', 'O', '9999-12-31', 'No remarks!'),
	('P6413303', 'Destiny', 'Difalco', 'O', '9999-12-31', 'No remarks!'),
	('P2260140', 'Dana', 'Gouin', 'F', '2011-12-31', 'Some remarks!'),
	('P4035586', 'Ilda', 'Rabago', 'O', '9999-12-31', 'No remarks!'),
	('P1961180', 'Margaretta', 'Carbone', 'O', '9999-12-31', 'No remarks!'),
	('P3002268', 'Aiko', 'Valliere', 'O', '9999-12-31', 'No remarks!'),
	('P7226154', 'Kim', 'Bou', 'O', '9999-12-31', 'No remarks!'),
	('P8236233', 'Herschel', 'Eslinger', 'O', '9999-12-31', 'No remarks!'),
	('P2962060', 'Brianne', 'Elbert', 'O', '9999-12-31', 'No remarks!'),
	('P9641308', 'Jarred', 'Mor', 'O', '9999-12-31', 'No remarks!'),
	('P619590', 'Zana', 'Rankin', 'O', '9999-12-31', 'No remarks!'),
	('P143088', 'Chassidy', 'Dupont', 'O', '9999-12-31', 'No remarks!'),
	('P1192102', 'Veola', 'Mcilrath', 'O', '9999-12-31', 'No remarks!'),
	('P7363825', 'Inez', 'Hochman', 'O', '9999-12-31', 'No remarks!'),
	('P2392779', 'Margaretta', 'Vigliotti', 'O', '9999-12-31', 'No remarks!'),
	('P3096150', 'Regine', 'Vandemark', 'O', '9999-12-31', 'No remarks!'),
	('P549868', 'Dawne', 'Shakespeare', 'O', '9999-12-31', 'No remarks!'),
	('P4450544', 'Christoper', 'Breton', 'O', '9999-12-31', 'No remarks!'),
	('P4670218', 'Dusti', 'Gillie', 'O', '9999-12-31', 'No remarks!'),
	('P9608683', 'Hilaria', 'Pidgeon', 'O', '9999-12-31', 'No remarks!'),
	('P6006595', 'Fredericka', 'Parshall', 'O', '9999-12-31', 'No remarks!'),
	('P5753535', 'Suk', 'Rodreguez', 'O', '9999-12-31', 'No remarks!'),
	('P8756253', 'Adella', 'Dyer', 'O', '9999-12-31', 'No remarks!'),
	('P825825', 'Dionna', 'Hudkins', 'O', '9999-12-31', 'No remarks!'),
	('P869168', 'Shaneka', 'Lard', 'O', '9999-12-31', 'No remarks!'),
	('P4605964', 'Tanja', 'Sayers', 'O', '9999-12-31', 'No remarks!'),
	('P3260375', 'Robena', 'Lafever', 'O', '9999-12-31', 'No remarks!'),
	('P2819578', 'Coleman', 'Godoy', 'O', '9999-12-31', 'No remarks!'),
	('P8070675', 'Zachary', 'Pintor', 'O', '9999-12-31', 'No remarks!'),
	('P35811', 'Lucille', 'Fehr', 'O', '9999-12-31', 'No remarks!'),
	('P5291424', 'Michell', 'Rhodus', 'O', '9999-12-31', 'No remarks!'),
	('P8224348', 'Waylon', 'Thornley', 'O', '9999-12-31', 'No remarks!'),
	('P9344289', 'Jeremy', 'Tebo', 'O', '9999-12-31', 'No remarks!'),
	('P4657169', 'Kari', 'Cebula', 'O', '9999-12-31', 'No remarks!'),
	('P2887461', 'Marquerite', 'Wickliffe', 'O', '9999-12-31', 'No remarks!'),
	('P3859290', 'Jena', 'Otte', 'O', '9999-12-31', 'No remarks!'),
	('P7543160', 'Tonette', 'Shibata', 'O', '9999-12-31', 'No remarks!'),
	('P8372680', 'Lakisha', 'Czajkowski', 'O', '9999-12-31', 'No remarks!'),
	('P2104636', 'Lang', 'Feagins', 'O', '9999-12-31', 'No remarks!'),
	('P2742137', 'Jacqualine', 'Robare', 'O', '9999-12-31', 'No remarks!'),
	('P9899515', 'Windy', 'Newhard', 'O', '9999-12-31', 'No remarks!'),
	('P729035', 'Darrel', 'Cauble', 'O', '9999-12-31', 'No remarks!'),
	('P5010003', 'Sharita', 'Widrick', 'O', '9999-12-31', 'No remarks!'),
	('P8204008', 'Violeta', 'Bussiere', 'O', '9999-12-31', 'No remarks!'),
	('P8069559', 'Donette', 'Costillo', 'O', '9999-12-31', 'No remarks!'),
	('P1781316', 'Alecia', 'Ledbetter', 'O', '9999-12-31', 'No remarks!'),
	('P9788327', 'Marian', 'Rusu', 'O', '9999-12-31', 'No remarks!'),
	('P3077005', '1323ecd', 'admin', 'F', '2001-03-03', 'No remarks!'),
	('P6667678', 'md', 'Arif', 'M', '1990-10-18', 'No remarks!'),
	('P768231', 'fzsd', 'admin', 'M', '2001-07-07', 'No remarks!'),
	('P6671212', 'Dalwar', 'Arif', 'M', '1989-10-15', 'Other remarks!');
/*!40000 ALTER TABLE `patients` ENABLE KEYS */;

-- Dumping structure for table surgery_planning.surgeries
DROP TABLE IF EXISTS `surgeries`;
CREATE TABLE IF NOT EXISTS `surgeries` (
  `surgeryId` varchar(32) NOT NULL,
  `surgeryName` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Table for surgery names';

-- Dumping data for table surgery_planning.surgeries: ~62 rows (approximately)
DELETE FROM `surgeries`;
/*!40000 ALTER TABLE `surgeries` DISABLE KEYS */;
INSERT INTO `surgeries` (`surgeryId`, `surgeryName`) VALUES
	('SRG4438', 'srg0'),
	('SRG6169', 'srg1'),
	('SRG1862', 'srg2'),
	('SRG6317', 'srg3'),
	('SRG7601', 'srg4'),
	('SRG6139', 'srg5'),
	('SRG262', 'srg6'),
	('SRG4621', 'srg7'),
	('SRG654', 'srg8'),
	('SRG4642', 'srg9'),
	('SRG2871', 'srg10'),
	('SRG2615', 'srg11'),
	('SRG951', 'srg12'),
	('SRG269', 'srg13'),
	('SRG9270', 'srg14'),
	('SRG6326', 'srg15'),
	('SRG2492', 'srg16'),
	('SRG5314', 'srg17'),
	('SRG3367', 'srg18'),
	('SRG4768', 'srg19'),
	('SRG8205', 'srg20'),
	('SRG811', 'srg21'),
	('SRG3347', 'srg22'),
	('SRG7350', 'srg23'),
	('SRG9753', 'srg24'),
	('SRG9544', 'srg25'),
	('SRG5781', 'srg26'),
	('SRG8956', 'srg27'),
	('SRG8792', 'srg28'),
	('SRG3928', 'srg29'),
	('SRG372', 'srg30'),
	('SRG4680', 'srg31'),
	('SRG125', 'srg32'),
	('SRG437', 'srg33'),
	('SRG6234', 'srg34'),
	('SRG5152', 'srg35'),
	('SRG3853', 'srg36'),
	('SRG2291', 'srg37'),
	('SRG8754', 'srg38'),
	('SRG4461', 'srg39'),
	('SRG5393', 'srg40'),
	('SRG3913', 'srg41'),
	('SRG1879', 'srg42'),
	('SRG9383', 'srg43'),
	('SRG7210', 'srg44'),
	('SRG1425', 'srg45'),
	('SRG2894', 'srg46'),
	('SRG1002', 'srg47'),
	('SRG292', 'srg48'),
	('SRG2192', 'srg49'),
	('SRG589', 'srg50'),
	('SRG3296', 'srg51'),
	('SRG3177', 'srg52'),
	('SRG9308', 'srg53'),
	('SRG7414', 'srg54'),
	('SRG9150', 'srg55'),
	('SRG1990', 'srg56'),
	('SRG8120', 'srg57'),
	('SRG8098', 'srg58'),
	('SRG108', 'srg59'),
	('SRG2350', 'surgery'),
	('SRG5988', 'surgery2');
/*!40000 ALTER TABLE `surgeries` ENABLE KEYS */;

-- Dumping structure for table surgery_planning.surgeryDetails
DROP TABLE IF EXISTS `surgeryDetails`;
CREATE TABLE IF NOT EXISTS `surgeryDetails` (
  `surgeryId` varchar(32) NOT NULL,
  `pathologyId` varchar(32) NOT NULL,
  `avgDuration` int(11) NOT NULL,
  `stdDeviation` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Table for surgery details';

-- Dumping data for table surgery_planning.surgeryDetails: ~61 rows (approximately)
DELETE FROM `surgeryDetails`;
/*!40000 ALTER TABLE `surgeryDetails` DISABLE KEYS */;
INSERT INTO `surgeryDetails` (`surgeryId`, `pathologyId`, `avgDuration`, `stdDeviation`) VALUES
	('SRG6169', 'PTG8862', 194, 56),
	('SRG1862', 'PTG8947', 177, 29),
	('SRG6317', 'PTG5985', 184, 26),
	('SRG7601', 'PTG3131', 121, 21),
	('SRG6139', 'PTG7381', 81, 21),
	('SRG262', 'PTG8947', 97, 21),
	('SRG4621', 'PTG8384', 97, 21),
	('SRG654', 'PTG170', 97, 21),
	('SRG4642', 'PTG2575', 85, 35),
	('SRG2871', 'PTG7990', 150, 55),
	('SRG2615', 'PTG4189', 153, 23),
	('SRG951', 'PTG3580', 97, 21),
	('SRG269', 'PTG2070', 150, 42),
	('SRG9270', 'PTG9331', 97, 21),
	('SRG6326', 'PTG7519', 150, 42),
	('SRG2492', 'PTG7255', 86, 32),
	('SRG5314', 'PTG8512', 97, 21),
	('SRG3367', 'PTG5563', 83, 22),
	('SRG4768', 'PTG4748', 121, 21),
	('SRG8205', 'PTG2281', 103, 40),
	('SRG811', 'PTG5908', 105, 24),
	('SRG3347', 'PTG3981', 104, 30),
	('SRG7350', 'PTG3498', 110, 43),
	('SRG9753', 'PTG4494', 85, 35),
	('SRG9544', 'PTG6187', 153, 23),
	('SRG5781', 'PTG2425', 145, 38),
	('SRG8956', 'PTG4592', 145, 38),
	('SRG8792', 'PTG9356', 143, 24),
	('SRG3928', 'PTG3405', 83, 22),
	('SRG372', 'PTG1352', 97, 21),
	('SRG4680', 'PTG8935', 155, 48),
	('SRG125', 'PTG3880', 99, 20),
	('SRG437', 'PTG9281', 149, 38),
	('SRG6234', 'PTG2639', 105, 21),
	('SRG5152', 'PTG8939', 45, 12),
	('SRG3853', 'PTG8827', 68, 19),
	('SRG2291', 'PTG4512', 156, 42),
	('SRG8754', 'PTG2133', 97, 21),
	('SRG4461', 'PTG313', 111, 23),
	('SRG5393', 'PTG1919', 145, 27),
	('SRG3913', 'PTG5853', 153, 23),
	('SRG1879', 'PTG3222', 86, 20),
	('SRG9383', 'PTG2336', 145, 27),
	('SRG7210', 'PTG4927', 170, 40),
	('SRG1425', 'PTG5862', 71, 25),
	('SRG2894', 'PTG2879', 145, 27),
	('SRG1002', 'PTG3005', 81, 21),
	('SRG292', 'PTG1661', 145, 27),
	('SRG2192', 'PTG5741', 110, 43),
	('SRG589', 'PTG3681', 153, 23),
	('SRG3296', 'PTG1611', 103, 40),
	('SRG3177', 'PTG3120', 133, 41),
	('SRG9308', 'PTG731', 150, 42),
	('SRG7414', 'PTG8617', 110, 23),
	('SRG9150', 'PTG4291', 45, 12),
	('SRG1990', 'PTG6932', 110, 43),
	('SRG8120', 'PTG9685', 133, 24),
	('SRG8098', 'PTG5543', 75, 23),
	('SRG108', 'PTG5716', 150, 37),
	('SRG4438', 'PTG7381', 115, 28),
	('SRG2350', 'PTG1667', 234, 43);
/*!40000 ALTER TABLE `surgeryDetails` ENABLE KEYS */;

-- Dumping structure for table surgery_planning.users
DROP TABLE IF EXISTS `users`;
CREATE TABLE IF NOT EXISTS `users` (
  `userName` varchar(32) NOT NULL,
  `hashedPassword` varchar(512) NOT NULL,
  `userType` varchar(32) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='table for user log in details';

-- Dumping data for table surgery_planning.users: ~4 rows (approximately)
DELETE FROM `users`;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` (`userName`, `hashedPassword`, `userType`) VALUES
	('admin', 'FkvWGdR32eFqCm6VgVdqIg==', 'HEAD_OF_DEPARTMENT'),
	('doctor', '9xb2+L3DaPcZwOZsPTZ7UA==', 'MEDIC'),
	('teamlead', 'WaxnGZ5e67VTZ2SFmzCVdQ==', 'COORDINATOR'),
	('assistant', 'L0EzwqSfpHb1hHr7CY5a3g==', 'ASSISTANT');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
