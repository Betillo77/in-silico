
CREATE TABLE `Components` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `imageId` varchar(50) NOT NULL,
  `leftP` int(11) NOT NULL,
  `rightP` int(11) NOT NULL,
  `topP` int(11) NOT NULL,
  `downP` int(11) NOT NULL,
  `data` blob NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=25858 DEFAULT CHARSET=latin1

ALTER TABLE `MyOCR`.`Components` ENGINE = InnoDB;

CREATE TABLE `MomentTypes` (
  `MomentType` int(11) NOT NULL AUTO_INCREMENT,
  `Description` varchar(50) NOT NULL,
  `VectorSize` int(11) NOT NULL,
  PRIMARY KEY (`MomentType`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1

CREATE TABLE `MyOCR`.`Moments` (
  `ComponentId` INT  NOT NULL,
  `MomentType` INT  NOT NULL,
  `Set` varchar(4),
  `Vector` BLOB  NOT NULL,
  PRIMARY KEY (ComponentId,MomentType)
)
ENGINE = InnoDB;

ALTER TABLE `MyOCR`.`Moments` ADD CONSTRAINT `fk_Mom_Comp` FOREIGN KEY `fk_Mom_Comp` (`ComponentId`)
    REFERENCES `Components` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE;
    
ALTER TABLE `MyOCR`.`Moments` ADD CONSTRAINT `fk_mom_type` FOREIGN KEY `fk_mom_type` (`MomentType`)
    REFERENCES `MomentTypes` (`MomentType`)
    ON DELETE CASCADE
    ON UPDATE CASCADE;

Insert into MomentTypes (Description, VectorSize) values ('HU',7);
