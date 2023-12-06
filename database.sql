SET NAMES utf8mb4;

-- Create the Login table
CREATE TABLE `Login` (
  `id` bigint(20) NOT NULL,
  `password` varchar(64) NOT NULL,
  `userType` varchar(32) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `Login` (`id`, `password`, `userType`)
VALUES
  (1, 'admin', 'Admin'),
  (329272, '1234', 'Professor'),
  (737263, '1234', 'Professor'),
  (1232134133, '1234', 'Student'),
  (191931080235, '1234', 'Student'),
  (202031080821, '1234', 'Student'),
  (202031080851, '1234', 'Student');

-- Create the Admin table
CREATE TABLE `Admin` (
  `id` bigint(20) NOT NULL,
  `nom` varchar(64) NOT NULL,
  `prenom` varchar(64) NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `admin_ibfk_1` FOREIGN KEY (`id`) REFERENCES `Login` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `Admin` (`id`, `nom`, `prenom`)
VALUES
  (1, 'Farouk', 'adminn');

-- Create the Professor table
CREATE TABLE `Professor` (
  `id` bigint(20) NOT NULL,
  `nom` varchar(64) NOT NULL,
  `prenom` varchar(64) NOT NULL,
  `speciality` varchar(32) NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `professor_ibfk_1` FOREIGN KEY (`id`) REFERENCES `Login` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `Professor` (`id`, `nom`, `prenom`, `speciality`)
VALUES
  (329272, 'Touazi', 'Faycal', 'Informatics'),
  (737263, 'Rezzoug', 'Kamel', 'Informatics');

-- Create the Student table
CREATE TABLE `Student` (
  `id` bigint(20) NOT NULL,
  `nom` varchar(64) NOT NULL,
  `prenom` varchar(64) NOT NULL,
  `speciality` varchar(32) NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `student_ibfk_1` FOREIGN KEY (`id`) REFERENCES `Login` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `Student` (`id`, `nom`, `prenom`, `speciality`)
VALUES
  (1232134133, 'DKso', '', 'Informatics'),
  (191931080235, 'Belhaddad', 'Ahmed chawki', 'Informatics'),
  (202031080821, 'Mehdid', 'Samy Abderraouf', 'Informatics'),
  (202031080851, 'kader', 'kader', 'Biologie');

-- Create the Memoire table
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

INSERT INTO `Memoire` (`cote`, `title`, `professor_id`, `date`, `level`, `resume`, `pdf_url`)
VALUES
  (2, 'Une ameloiration a faire', 737263, 2019, 'Master', 'ici on trouve un resume de la memoire', '/Users/raynex/Desktop/SI/Prof Informatique (L3) (S6 SI).pdf'),
  (6, 'voici un titre de memoire', 737263, 2023, 'Bachelor', 'voici un resume', '/Users/raynex/Desktop/SI/S5/IHM/Cours/Chapitre 01.pdf'),
  (7, 'voici un titre de memoire', 737263, 2023, 'Master', 'voici un resume', '/Users/raynex/Desktop/SI/S5/IHM/Cours/Chapitre 01.pdf'),
  (8, 'Une memoire a ajouter', 737263, 2020, 'Bachelor', 'une resume de la memoire', '/Users/raynex/Desktop/SI/S5/GL/Methodes Iteratives.png'),
  (9, 'Une nouvelle memoire', 737263, 2019, 'Bachelor', 'this a resume', '/Users/raynex/Desktop/SI/Prof Informatique (L3) (S6 SI).pdf'),
  (11, 'une nouvelle memoire', 329272, 2020, 'Bachelor', 'un resume dumb', NULL);

-- Create the StudentMemoire table
CREATE TABLE `StudentMemoire` (
  `student_id` bigint(20) DEFAULT NULL,
  `memoire_id` int(11) DEFAULT NULL,
  KEY `student_id` (`student_id`),
  KEY `memoire_id` (`memoire_id`),
  CONSTRAINT `studentmemoire_ibfk_1` FOREIGN KEY (`student_id`) REFERENCES `Student` (`id`),
  CONSTRAINT `studentmemoire_ibfk_2` FOREIGN KEY (`memoire_id`) REFERENCES `Memoire` (`cote`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `StudentMemoire` (`student_id`, `memoire_id`)
VALUES
  (191931080235, 2),
  (202031080821, 2),
  (191931080235, 6),
  (202031080821, 6),
  (202031080851, 6),
  (191931080235, 7),
  (202031080821, 7),
  (202031080851, 7),
  (191931080235, 8),
  (202031080821, 8),
  (191931080235, 8),
  (202031080821, 8),
  (202031080821, 9),
  (191931080235, 9),
  (191931080235, 11),
  (202031080821, 11),
  (202031080851, 11);
