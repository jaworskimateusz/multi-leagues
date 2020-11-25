-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema moje_ligi
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `moje_ligi` DEFAULT CHARACTER SET utf8 ;
USE `moje_ligi` ;

--
-- Table structure for table `users`
--

CREATE TABLE IF NOT EXISTS `moje_ligi`.`users` (
  `user_id` INT NOT NULL AUTO_INCREMENT,
  `username` varchar(45) NOT NULL,
  `password` char(68) NOT NULL,
  `enabled` tinyint(1) NOT NULL,
  `role` varchar(45) NOT NULL,
  `imie` VARCHAR(45) NOT NULL,
  `nazwisko` VARCHAR(45) NOT NULL,
  `pesel` VARCHAR(11) NOT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB;

--
-- Table structure for table `authorities`
--

CREATE TABLE IF NOT EXISTS `moje_ligi`.`authorities` (
  `user_id` INT NOT NULL,
  `authority` varchar(50) NOT NULL,
  UNIQUE KEY `authorities_idx_1` (`user_id`,`authority`),
  CONSTRAINT `authorities_ibfk_1`
  FOREIGN KEY (`user_id`)
  REFERENCES `moje_ligi`.`users` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
) ENGINE=InnoDB;

-- -----------------------------------------------------
-- Table `moje_ligi`.`wpisowe`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `moje_ligi`.`wpisowe` (
  `id_wpisowe` INT NOT NULL AUTO_INCREMENT,
  `typ` TINYINT NOT NULL,
  `data_uiszczenia` DATETIME NOT NULL,
  `zaplacono` TINYINT NOT NULL,
  `user_id` INT NULL,
  PRIMARY KEY (`id_wpisowe`),
  INDEX `fk_wpisowe_zawodnicy1_idx` (`user_id` ASC) VISIBLE,
  CONSTRAINT `fk_wpisowe_zawodnicy1`
    FOREIGN KEY (`user_id`)
    REFERENCES `moje_ligi`.`users` (`user_id`)
    ON DELETE SET NULL
    ON UPDATE SET NULL)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `moje_ligi`.`turnieje`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `moje_ligi`.`turnieje` (
  `id_turnieju` INT NOT NULL AUTO_INCREMENT,
  `liczba_meczy` INT NOT NULL,
  `nazwa` VARCHAR(45) NOT NULL,
  `lokalizacja` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id_turnieju`))
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `moje_ligi`.`dyscyplina`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `moje_ligi`.`dyscyplina` (
  `id_dyscypliny` INT NOT NULL AUTO_INCREMENT,
  `typ` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id_dyscypliny`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `moje_ligi`.`ligi`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `moje_ligi`.`ligi` (
  `id_ligi` INT NOT NULL AUTO_INCREMENT,
  `poziom` VARCHAR(45) NOT NULL,
  `id_obiektu` INT NULL,
  `opis` VARCHAR(100) NOT NULL,
  `id_dyscypliny` INT NULL,
  PRIMARY KEY (`id_ligi`),
  INDEX `fk_ligi_obiekty1_idx` (`id_obiektu` ASC) VISIBLE,
  INDEX `fk_ligi_dyscyplina1_idx` (`id_dyscypliny` ASC) VISIBLE,
  CONSTRAINT `fk_ligi_obiekty1`
    FOREIGN KEY (`id_obiektu`)
    REFERENCES `moje_ligi`.`obiekty` (`id_obiektu`)
    ON DELETE NO ACTION
    ON UPDATE CASCADE,
  CONSTRAINT `fk_ligi_dyscyplina1`
    FOREIGN KEY (`id_dyscypliny`)
    REFERENCES `moje_ligi`.`dyscyplina` (`id_dyscypliny`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `moje_ligi`.`sezony`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `moje_ligi`.`sezony` (
  `id_sezonu` INT NOT NULL AUTO_INCREMENT,
  `numer` INT NOT NULL,
  `opis` VARCHAR(200) NOT NULL,
  `id_ligi` INT NOT NULL,
  PRIMARY KEY (`id_sezonu`),
  INDEX `fk_sezony_ligi1_idx` (`id_ligi` ASC) VISIBLE,
  CONSTRAINT `fk_sezony_ligi1`
    FOREIGN KEY (`id_ligi`)
    REFERENCES `moje_ligi`.`ligi` (`id_ligi`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `moje_ligi`.`kolejki`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `moje_ligi`.`kolejki` (
  `id_kolejki` INT NOT NULL AUTO_INCREMENT,
  `dyscyplina` VARCHAR(45) NOT NULL,
  `numer` INT NOT NULL,
  `id_sezonu` INT NOT NULL,
  PRIMARY KEY (`id_kolejki`),
  INDEX `fk_kolejki_sezony1_idx` (`id_sezonu` ASC) VISIBLE,
  CONSTRAINT `fk_kolejki_sezony1`
    FOREIGN KEY (`id_sezonu`)
    REFERENCES `moje_ligi`.`sezony` (`id_sezonu`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `moje_ligi`.`mecze`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `moje_ligi`.`mecze` (
  `id_meczu` INT NOT NULL AUTO_INCREMENT,
  `termin` DATETIME NOT NULL,
  `miejsce` VARCHAR(45) NOT NULL,
  `wynik_pierwszego` INT NULL,
  `wynik_drugiego` INT NULL,
  `id_pierwszego_zawodnika` INT NULL,
  `id_drugiego_zawodnika` INT NULL,
  `id_kolejki` INT DEFAULT NULL,
  `id_turnieju` INT DEFAULT NULL,
  PRIMARY KEY (`id_meczu`),
  INDEX `fk_mecze_kolejki1_idx` (`id_kolejki` ASC) VISIBLE,
  INDEX `fk_mecze_turniejei1_idx` (`id_turnieju` ASC) VISIBLE,
  CONSTRAINT `fk_mecze_kolejki1`
    FOREIGN KEY (`id_kolejki`)
    REFERENCES `moje_ligi`.`kolejki` (`id_kolejki`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_mecze_turnieje1`
    FOREIGN KEY (`id_turnieju`)
    REFERENCES `moje_ligi`.`turnieje` (`id_turnieju`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `moje_ligi`.`sety`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `moje_ligi`.`sety` (
  `id_setu` INT NOT NULL,
  `numer` INT NOT NULL,
  `punkty_pierwszego` INT NULL,
  `punkty_drugiego` VARCHAR(45) NULL,
  `id_meczu` INT NOT NULL,
  PRIMARY KEY (`id_setu`),
  INDEX `fk_sety_mecze1_idx` (`id_meczu` ASC) VISIBLE,
  CONSTRAINT `fk_sety_mecze1`
    FOREIGN KEY (`id_meczu`)
    REFERENCES `moje_ligi`.`mecze` (`id_meczu`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `moje_ligi`.`wpisowe_kolejki`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `moje_ligi`.`wpisowe_kolejki` (
  `id_wpisowe` INT NOT NULL,
  `id_kolejki` INT NOT NULL,
  PRIMARY KEY (`id_wpisowe`, `id_kolejki`),
  INDEX `fk_wpisowe_has_kolejki_kolejki1_idx` (`id_kolejki` ASC) VISIBLE,
  INDEX `fk_wpisowe_has_kolejki_wpisowe1_idx` (`id_wpisowe` ASC) VISIBLE,
  CONSTRAINT `fk_wpisowe_has_kolejki_wpisowe1`
    FOREIGN KEY (`id_wpisowe`)
    REFERENCES `moje_ligi`.`wpisowe` (`id_wpisowe`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_wpisowe_has_kolejki_kolejki1`
    FOREIGN KEY (`id_kolejki`)
    REFERENCES `moje_ligi`.`kolejki` (`id_kolejki`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `moje_ligi`.`zawodnicy_ligi`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `moje_ligi`.`zawodnicy_ligi` (
  `user_id` INT NOT NULL,
  `id_ligi` INT NOT NULL,
  PRIMARY KEY (`user_id`, `id_ligi`),
  INDEX `fk_zawodnicy_has_ligi_ligi1_idx` (`id_ligi` ASC) VISIBLE,
  INDEX `fk_zawodnicy_has_ligi_zawodnicy1_idx` (`user_id` ASC) VISIBLE,
  CONSTRAINT `fk_zawodnicy_has_ligi_zawodnicy1`
    FOREIGN KEY (`user_id`)
    REFERENCES `moje_ligi`.`users` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_zawodnicy_has_ligi_ligi1`
    FOREIGN KEY (`id_ligi`)
    REFERENCES `moje_ligi`.`ligi` (`id_ligi`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

INSERT INTO `moje_ligi`.`users`
VALUES
(1,'mati','$2a$10$Gf6ln1gEsljuFOEkGi2/6eFhYmueU5Wp42JCulP8sZv/i4uJv1OUS',1, 'WORKER', 'Mateusz', 'Jaw',98040512421),
(2,'marta','$2a$10$LIVPX4xTMEnFNrKZTOWKjuOIlMSBqUXZr6f4YtQWjr2sVMbjZKLfG',1, 'ACCOUNTANT','Maria', 'Em',99040512421),
(3,'jakub','$2a$10$LIVPX4xTMEnFNrKZTOWKjuOIlMSBqUXZr6f4YtQWjr2sVMbjZKLfG',1, 'WORKER','Kuba', 'S',95040512421),
(4,'dario','$2a$10$LIVPX4xTMEnFNrKZTOWKjuOIlMSBqUXZr6f4YtQWjr2sVMbjZKLfG',1, 'PLAYER','Dario', 'Pietrek',98040512421);

--
-- Dumping data for table `authorities`
--

INSERT INTO `moje_ligi`.`authorities`
VALUES
(1,'ROLE_WORKER'),
(2,'ROLE_ACCOUNTANT'),
(3,'ROLE_WORKER'),
(4,'ROLE_PLAYER');


INSERT INTO `moje_ligi`.`dyscyplina` (`typ`)
VALUES ('Boks'),('Tenis ziemny'),('Tenis stołowy'),('Dwa ognie');

INSERT INTO `moje_ligi`.`ligi`
(`poziom`,`opis`,`id_obiektu`, `id_dyscypliny`) VALUES('WYS','Liga tenisa stołowego', null, 1);

INSERT INTO `moje_ligi`.`wpisowe`
(`typ`,`data_uiszczenia`,`zaplacono`,`user_id`)
VALUES
(1,STR_TO_DATE('18/02/2019 11:15','%d/%m/%Y %H:%i'),1,1);

INSERT INTO `moje_ligi`.`sezony`
(`numer`,`opis`,`id_ligi`) VALUES (1,'Sezon wiosenny',2);

INSERT INTO `moje_ligi`.`kolejki`
(`dyscyplina`,`numer`,`id_sezonu`) VALUES ('Boks', '1', 3);

INSERT INTO `moje_ligi`.`mecze` (`termin`,`miejsce`,`wynik_pierwszego`,`wynik_drugiego`,`id_kolejki`)
VALUES (STR_TO_DATE('18/02/2019 11:15','%d/%m/%Y %H:%i'),'Warszawianka',14,10,7),
(STR_TO_DATE('18/02/2019 11:15','%d/%m/%Y %H:%i'),'Arena',null,null,7);

INSERT INTO `moje_ligi`.`mecze`
(`termin`,`miejsce`,`wynik_pierwszego`,`wynik_drugiego`,`id_pierwszego_zawodnika`,`id_drugiego_zawodnika`,`id_kolejki`)
VALUES(STR_TO_DATE('18/02/2019 11:15','%d/%m/%Y %H:%i'),'Warszawianka',null,null,null, null,13);



SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
