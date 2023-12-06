# ************************************************************
# Sequel Ace SQL dump
# Version 20062
#
# https://sequel-ace.com/
# https://github.com/Sequel-Ace/Sequel-Ace
#
# Host: localhost (MySQL 5.7.39)
# Database: my_thesis
# Generation Time: 2023-11-30 13:06:39 +0000
# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
SET NAMES utf8mb4;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE='NO_AUTO_VALUE_ON_ZERO', SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


# Dump of table Admin
# ------------------------------------------------------------

DROP TABLE IF EXISTS `Admin`;

CREATE TABLE `Admin` (
  `id` bigint(20) NOT NULL,
  `nom` varchar(64) NOT NULL,
  `prenom` varchar(64) NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `admin_ibfk_1` FOREIGN KEY (`id`) REFERENCES `Login` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `Admin` WRITE;
/*!40000 ALTER TABLE `Admin` DISABLE KEYS */;

INSERT INTO `Admin` (`id`, `nom`, `prenom`)
VALUES
	(1,'Farouk','adminn');

/*!40000 ALTER TABLE `Admin` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table Login
# ------------------------------------------------------------

DROP TABLE IF EXISTS `Login`;

CREATE TABLE `Login` (
  `id` bigint(20) NOT NULL,
  `password` varchar(64) NOT NULL,
  `userType` varchar(32) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `Login` WRITE;
/*!40000 ALTER TABLE `Login` DISABLE KEYS */;

INSERT INTO `Login` (`id`, `password`, `userType`)
VALUES
	(1,'admin','Admin'),
	(329272,'1234','Professor'),
	(737263,'1234','Professor'),
	(1232134133,'1234','Student'),
	(191931080235,'1234','Student'),
	(202031080821,'1234','Student'),
	(202031080851,'1234','Student');

/*!40000 ALTER TABLE `Login` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table Memoire
# ------------------------------------------------------------

DROP TABLE IF EXISTS `Memoire`;

CREATE TABLE `Memoire` (
  `cote` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(128) NOT NULL,
  `professor_id` bigint(20) DEFAULT NULL,
  `date` int(11) DEFAULT NULL,
  `level` varchar(8) DEFAULT NULL,
  `resume` varchar(512) DEFAULT NULL,
  `pdf_url` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`cote`),
  KEY `professor_id` (`professor_id`),
  CONSTRAINT `memoire_ibfk_1` FOREIGN KEY (`professor_id`) REFERENCES `Professor` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `Memoire` WRITE;
/*!40000 ALTER TABLE `Memoire` DISABLE KEYS */;

INSERT INTO `Memoire` (`cote`, `title`, `professor_id`, `date`, `level`, `resume`, `pdf_url`)
VALUES
	(2,'Une ameloiration a faire',737263,2019,'Master','ici on trouve un resume de la memoire','/Users/raynex/Desktop/SI/Prof Informatique (L3) (S6 SI).pdf'),
	(6,'voici un titre de memoire',737263,2023,'Bachelor','voici un resume','/Users/raynex/Desktop/SI/S5/IHM/Cours/Chapitre 01.pdf'),
	(7,'voici un titre de memoire',737263,2023,'Master','voici un resume','/Users/raynex/Desktop/SI/S5/IHM/Cours/Chapitre 01.pdf'),
	(8,'Une memoire a ajouter',737263,2020,'Bachelor','une resume de la memoire','/Users/raynex/Desktop/SI/S5/GL/Methodes Iteratives.png'),
	(9,'Une nouvelle memoire',737263,2019,'Bachelor','this a resume','/Users/raynex/Desktop/SI/Prof Informatique (L3) (S6 SI).pdf'),
	(11,'une nouvelle memoire',329272,2020,'Bachelor','un resume dumb',NULL);

/*!40000 ALTER TABLE `Memoire` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table Professor
# ------------------------------------------------------------

DROP TABLE IF EXISTS `Professor`;

CREATE TABLE `Professor` (
  `id` bigint(20) NOT NULL,
  `nom` varchar(64) NOT NULL,
  `prenom` varchar(64) NOT NULL,
  `speciality` varchar(32) NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `professor_ibfk_1` FOREIGN KEY (`id`) REFERENCES `Login` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `Professor` WRITE;
/*!40000 ALTER TABLE `Professor` DISABLE KEYS */;

INSERT INTO `Professor` (`id`, `nom`, `prenom`, `speciality`)
VALUES
	(329272,'Touazi','Faycal','Informatics'),
	(737263,'Rezzoug','Kamel','Informatics');

/*!40000 ALTER TABLE `Professor` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table Student
# ------------------------------------------------------------

DROP TABLE IF EXISTS `Student`;

CREATE TABLE `Student` (
  `id` bigint(20) NOT NULL,
  `nom` varchar(64) NOT NULL,
  `prenom` varchar(64) NOT NULL,
  `speciality` varchar(32) NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `student_ibfk_1` FOREIGN KEY (`id`) REFERENCES `Login` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `Student` WRITE;
/*!40000 ALTER TABLE `Student` DISABLE KEYS */;

INSERT INTO `Student` (`id`, `nom`, `prenom`, `speciality`)
VALUES
	(1232134133,'DKso','','Informatics'),
	(191931080235,'Belhaddad','Ahmed chawki','Informatics'),
	(202031080821,'Mehdid','Samy Abderraouf','Informatics'),
	(202031080851,'kader','kader','Biologie');

/*!40000 ALTER TABLE `Student` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table StudentMemoire
# ------------------------------------------------------------

DROP TABLE IF EXISTS `StudentMemoire`;

CREATE TABLE `StudentMemoire` (
  `student_id` bigint(20) DEFAULT NULL,
  `memoire_id` int(11) DEFAULT NULL,
  KEY `student_id` (`student_id`),
  KEY `memoire_id` (`memoire_id`),
  CONSTRAINT `studentmemoire_ibfk_1` FOREIGN KEY (`student_id`) REFERENCES `Student` (`id`),
  CONSTRAINT `studentmemoire_ibfk_2` FOREIGN KEY (`memoire_id`) REFERENCES `Memoire` (`cote`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `StudentMemoire` WRITE;
/*!40000 ALTER TABLE `StudentMemoire` DISABLE KEYS */;

INSERT INTO `StudentMemoire` (`student_id`, `memoire_id`)
VALUES
	(191931080235,2),
	(202031080821,2),
	(191931080235,6),
	(202031080821,6),
	(202031080851,6),
	(191931080235,7),
	(202031080821,7),
	(202031080851,7),
	(191931080235,8),
	(202031080821,8),
	(191931080235,8),
	(202031080821,8),
	(202031080821,9),
	(191931080235,9),
	(191931080235,11),
	(202031080821,11),
	(202031080851,11);

/*!40000 ALTER TABLE `StudentMemoire` ENABLE KEYS */;
UNLOCK TABLES;



/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
