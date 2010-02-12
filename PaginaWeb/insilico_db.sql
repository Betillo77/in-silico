-- MySQL Administrator dump 1.4
--
-- ------------------------------------------------------
-- Server version	5.1.37-1ubuntu5.1


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


--
-- Create schema insilico
--

CREATE DATABASE IF NOT EXISTS insilico;
USE insilico;

--
-- Definition of table `insilico`.`ContCron`
--

DROP TABLE IF EXISTS `insilico`.`ContCron`;
CREATE TABLE  `insilico`.`ContCron` (
  `idTrans` int(11) NOT NULL AUTO_INCREMENT,
  `fecha` date NOT NULL,
  `idCron` int(10) unsigned NOT NULL,
  `UserId` varchar(20) NOT NULL,
  `Tiempo` int(11) NOT NULL,
  PRIMARY KEY (`idTrans`),
  KEY `fk_cc_cron` (`idCron`),
  KEY `fk_cc_integrant` (`UserId`),
  CONSTRAINT `fk_cc_cron` FOREIGN KEY (`idCron`) REFERENCES `Cronograma` (`idCron`),
  CONSTRAINT `fk_cc_integrant` FOREIGN KEY (`UserId`) REFERENCES `Integrante` (`UserId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `insilico`.`ContCron`
--

/*!40000 ALTER TABLE `ContCron` DISABLE KEYS */;
LOCK TABLES `ContCron` WRITE;
UNLOCK TABLES;
/*!40000 ALTER TABLE `ContCron` ENABLE KEYS */;


--
-- Definition of table `insilico`.`Cronograma`
--

DROP TABLE IF EXISTS `insilico`.`Cronograma`;
CREATE TABLE  `insilico`.`Cronograma` (
  `idCron` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `idProyecto` varchar(5) NOT NULL,
  `Concepto` varchar(50) NOT NULL,
  `Padre` int(11) DEFAULT NULL,
  `Tiempo` smallint(6) NOT NULL,
  `Estado` tinyint(4) NOT NULL,
  `TiempoTrab` smallint(6) NOT NULL,
  `Prereq` varchar(50) NOT NULL,
  PRIMARY KEY (`idCron`),
  KEY `cron_proy` (`idProyecto`),
  CONSTRAINT `fk_cron_proy` FOREIGN KEY (`idProyecto`) REFERENCES `Proyecto` (`IdProyecto`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `insilico`.`Cronograma`
--

/*!40000 ALTER TABLE `Cronograma` DISABLE KEYS */;
LOCK TABLES `Cronograma` WRITE;
INSERT INTO `insilico`.`Cronograma` VALUES  (1,'porta','Burocracia.com',NULL,2,0,0,''),
 (2,'porta','Burocracia2.com',NULL,2,0,0,'');
UNLOCK TABLES;
/*!40000 ALTER TABLE `Cronograma` ENABLE KEYS */;


--
-- Definition of table `insilico`.`Integrante`
--

DROP TABLE IF EXISTS `insilico`.`Integrante`;
CREATE TABLE  `insilico`.`Integrante` (
  `UserId` varchar(20) NOT NULL,
  `Nombre` varchar(50) NOT NULL,
  `Email` varchar(30) NOT NULL,
  `Password` varchar(30) NOT NULL,
  `Nacimiento` datetime NOT NULL,
  `Codigo` varchar(20) DEFAULT NULL,
  `Talla` varchar(5) DEFAULT NULL,
  PRIMARY KEY (`UserId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `insilico`.`Integrante`
--

/*!40000 ALTER TABLE `Integrante` DISABLE KEYS */;
LOCK TABLES `Integrante` WRITE;
INSERT INTO `insilico`.`Integrante` VALUES  ('juan','Juan David Arias','juandavid1024@hotmail.com','123','1989-08-07 00:00:00',NULL,NULL),
 ('sebas','Sebastián Gómez','sgomez_gonzalez@yahoo.es','1234','1987-12-16 00:00:00','1088258845','L');
UNLOCK TABLES;
/*!40000 ALTER TABLE `Integrante` ENABLE KEYS */;


--
-- Definition of table `insilico`.`Noticias`
--

DROP TABLE IF EXISTS `insilico`.`Noticias`;
CREATE TABLE  `insilico`.`Noticias` (
  `idNoticia` int(11) NOT NULL AUTO_INCREMENT,
  `Resumen` varchar(1023) NOT NULL,
  `Titulo` varchar(50) NOT NULL,
  `Imagen` varchar(50) NOT NULL,
  `Link` varchar(100) DEFAULT NULL,
  `Mostrar` tinyint(1) NOT NULL,
  PRIMARY KEY (`idNoticia`)
) ENGINE=MyISAM AUTO_INCREMENT=17 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `insilico`.`Noticias`
--

/*!40000 ALTER TABLE `Noticias` DISABLE KEYS */;
LOCK TABLES `Noticias` WRITE;
INSERT INTO `insilico`.`Noticias` VALUES  (1,'la IA trata de «desarrollar sistemas que piensen y actúen racionalmente» V. Julián, V. Botti.??podemos pensar que la IA, en su conjunto, trata realmente de construir precisamente dichas entidades autónomas e inteligentes (los agentes inteligentes).? V. Julián, V. Botti.','Maratones de Programación','imagenes/momento4.jpg','',1),
 (2,'\\\"El algoritmo hormiga o algoritmo de las hormigas es una técnica probabilística utilizada para solucionar problemas de cómputo; este algoritmo está inspirado en el comportamiento que presentan las hormigas para encontrar las trayectorias desde la colonia hasta el alimento.\\\" Wikipedia.','Redes Neuronales','imagenes/momento3.jpg','',1),
 (3,'\\\"Las redes de neuronas artificiales (denominadas habitualmente como RNA o en inglés como: \\\"ANN\\\") son un paradigma de aprendizaje y procesamiento automático inspirado en la forma en que funciona el sistema nervioso de los animales. Se trata de un sistema de interconexión de neuronas en una red que colabora para producir un estímulo de salida. En inteligencia artificial es frecuente referirse a ellas como redes de neuronas o redes neuronales\\\" Wikipedia.','Inteligencia Artificial','imagenes/momento1.jpg','',1),
 (4,'Cada año, se celebra la maratón de programación  acm icpc; un concurso en el que participan estudiantes universitarios de todo el mundo, en donde puedes medir tus habilidades de programación y capacidad para trabajar en equipo, además de tener la posibilidad de ganar un premio nada despreciable. Podemos prepararnos juntos y representar a nuestra universidad, anímate!','Colonia de hormigas','imagenes/momento2.jpg','',1);
INSERT INTO `insilico`.`Noticias` VALUES  (16,'Se descubriron el link del generador de pines.','Hackers se toman la utp','imagenes/5.jpg','http://www.utp.edu.co',0);
UNLOCK TABLES;
/*!40000 ALTER TABLE `Noticias` ENABLE KEYS */;


--
-- Definition of table `insilico`.`Proyecto`
--

DROP TABLE IF EXISTS `insilico`.`Proyecto`;
CREATE TABLE  `insilico`.`Proyecto` (
  `IdProyecto` varchar(5) NOT NULL,
  `Nombre` varchar(50) NOT NULL,
  `descGeneral` varchar(300) DEFAULT NULL,
  `descDetallada` varchar(1024) DEFAULT NULL,
  `Estado` tinyint(4) NOT NULL,
  PRIMARY KEY (`IdProyecto`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `insilico`.`Proyecto`
--

/*!40000 ALTER TABLE `Proyecto` DISABLE KEYS */;
LOCK TABLES `Proyecto` WRITE;
INSERT INTO `insilico`.`Proyecto` VALUES  ('porta','Portal web del semillero','El portal web de in-silico permitira trabajar en las tareas administativas y ademas el trabajo sobre proyectos',NULL,0);
UNLOCK TABLES;
/*!40000 ALTER TABLE `Proyecto` ENABLE KEYS */;


--
-- Definition of table `insilico`.`comentarios`
--

DROP TABLE IF EXISTS `insilico`.`comentarios`;
CREATE TABLE  `insilico`.`comentarios` (
  `n_comentario` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(50) NOT NULL,
  `email` varchar(50) NOT NULL,
  `fecha` varchar(20) NOT NULL,
  `comentario` varchar(1000) NOT NULL,
  PRIMARY KEY (`n_comentario`),
  KEY `n_comentario` (`n_comentario`),
  KEY `n_comentario_2` (`n_comentario`)
) ENGINE=MyISAM AUTO_INCREMENT=30 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `insilico`.`comentarios`
--

/*!40000 ALTER TABLE `comentarios` DISABLE KEYS */;
LOCK TABLES `comentarios` WRITE;
INSERT INTO `insilico`.`comentarios` VALUES  (22,'kdfk','jeison-9402@hotmail.com','07/02/2010','ksjd'),
 (23,'juan','jeffyner_95_04@hotmail.com','07/02/2010','jdhdjas hds'),
 (24,'Juan David','juandavid_1024@hotmail.com','07/02/2010','kjjksd sdjsk sik'),
 (25,'juan','juandavid_1024@hotmail.com','07/02/2010','jsd dfsdklfskl '),
 (26,'juan','juandavid_1024@hotmail.com','07/02/2010','jsd dfsdklfskl '),
 (27,'kdjks','juandavid_1024@hotmail.com','07/02/2010','jdfjdk'),
 (28,'sdsd','juandavid_1024@hotmail.com','07/02/2010','c,xfds'),
 (29,'Sebastian','sebasutp@gmail.com','09/02/2010','Hola, esto es una prueba');
UNLOCK TABLES;
/*!40000 ALTER TABLE `comentarios` ENABLE KEYS */;


--
-- Definition of table `insilico`.`resp_cron`
--

DROP TABLE IF EXISTS `insilico`.`resp_cron`;
CREATE TABLE  `insilico`.`resp_cron` (
  `idCron` int(10) unsigned NOT NULL,
  `UserId` varchar(20) NOT NULL,
  PRIMARY KEY (`idCron`,`UserId`),
  KEY `fk_interante` (`UserId`),
  KEY `cron_index` (`idCron`),
  CONSTRAINT `fk_cron` FOREIGN KEY (`idCron`) REFERENCES `Cronograma` (`idCron`),
  CONSTRAINT `fk_interante` FOREIGN KEY (`UserId`) REFERENCES `Integrante` (`UserId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `insilico`.`resp_cron`
--

/*!40000 ALTER TABLE `resp_cron` DISABLE KEYS */;
LOCK TABLES `resp_cron` WRITE;
INSERT INTO `insilico`.`resp_cron` VALUES  (1,'juan'),
 (1,'sebas'),
 (2,'juan'),
 (2,'sebas');
UNLOCK TABLES;
/*!40000 ALTER TABLE `resp_cron` ENABLE KEYS */;




/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
