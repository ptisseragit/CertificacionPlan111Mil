-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `agro` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci ;
USE `agro` ;

-- -----------------------------------------------------
-- Table `mydb`.`Campo`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `agro`.`Campo` (
  `idCampo` INT NOT NULL AUTO_INCREMENT COMMENT '',
  `nombreCampo` VARCHAR(250) NOT NULL COMMENT '',
  `superficieCampo` DOUBLE NOT NULL COMMENT '',
  `estadoCampo` ENUM('Creado', 'Parcialmente Trabajado', 'Completamente Trabajado', 'En Desuso') NOT NULL COMMENT '',
  PRIMARY KEY (`idCampo`)  COMMENT '')
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Lote`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `agro`.`Lote` (
  `idLote` INT NOT NULL AUTO_INCREMENT COMMENT '',
  `numeroLote` INT NOT NULL COMMENT '',
  `superficieLote` DOUBLE NOT NULL COMMENT '',
  `tipoSueloLote` INT NOT NULL COMMENT '',
  `idCampoLote` INT NOT NULL COMMENT '',
  PRIMARY KEY (`idLote`)  COMMENT '',
  INDEX `FK_CampoLote_idx` (`idCampoLote` ASC)  COMMENT '',
  CONSTRAINT `FK_CampoLote`
    FOREIGN KEY (`idCampoLote`)
    REFERENCES `agro`.`Campo` (`idCampo`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
