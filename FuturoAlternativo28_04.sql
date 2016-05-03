SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema futurodb
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema futurodb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `futurodb` DEFAULT CHARACTER SET utf8 ;
USE `futurodb` ;

-- -----------------------------------------------------
-- Table `futurodb`.`Pessoa`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `futurodb`.`Pessoa` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(255) NOT NULL,
  `datanasc` DATETIME NOT NULL,
  `sexo` varchar(255) NOT NULL,
  `naturalidade` varchar(255) NOT NULL,
  `uf` varchar(255) NOT NULL,
  `nomepai` varchar(255) NOT NULL,
  `nomemae` varchar(255) NOT NULL,
  `responsavellegal` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `numerocelular` varchar(255) NOT NULL,
  `necessidadesespeciais` varchar(255) NOT NULL,
  `etnia` varchar(255) NOT NULL,
  `nacionalidade` varchar(255) NOT NULL,
  `estadocivil` varchar(255) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `futurodb`.`Documento`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `futurodb`.`Documento` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dataexpedicao` DATETIME NOT NULL,
  `datavalidade` DATETIME NOT NULL,
  `orgaoemissor` varchar(255) NOT NULL,
  `uf` varchar(255) NOT NULL,
  `Pessoa_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_Documento_Pessoa_idx` (`Pessoa_id` ASC),
  CONSTRAINT `fk_Documento_Pessoa`
    FOREIGN KEY (`Pessoa_id`)
    REFERENCES `futurodb`.`Pessoa` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `futurodb`.`Endereco`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `futurodb`.`Endereco` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `tipo` varchar(255) NOT NULL,
  `logradouro` varchar(255) NOT NULL,
  `numero` INT NOT NULL,
  `bairro` varchar(255) NOT NULL,
  `cidade` varchar(255) NOT NULL,
  `uf` varchar(255) NOT NULL,
  `cep` varchar(255) NOT NULL,
  `enderecocorrespondencia` varchar(255) NOT NULL,
  `telefone` varchar(255) NOT NULL,
  `Pessoa_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_Endereco_Pessoa1_idx` (`Pessoa_id` ASC),
  CONSTRAINT `fk_Endereco_Pessoa1`
    FOREIGN KEY (`Pessoa_id`)
    REFERENCES `futurodb`.`Pessoa` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `futurodb`.`TipoPessoa`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `futurodb`.`TipoPessoa` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `tipopessoa` varchar(255) NOT NULL,
  `Pessoa_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_TipoPessoa_Pessoa1_idx` (`Pessoa_id` ASC),
  CONSTRAINT `fk_TipoPessoa_Pessoa1`
    FOREIGN KEY (`Pessoa_id`)
    REFERENCES `futurodb`.`Pessoa` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `futurodb`.`Plastico`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `futurodb`.`Plastico` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `linhadigitavel` varchar(255) NOT NULL,
  `datacadastro` DATETIME NOT NULL,
  `status` varchar(255) NOT NULL,
  `Pessoa_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_Plastico_Pessoa1_idx` (`Pessoa_id` ASC),
  CONSTRAINT `fk_Plastico_Pessoa1`
    FOREIGN KEY (`Pessoa_id`)
    REFERENCES `futurodb`.`Pessoa` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `futurodb`.`Evento`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `futurodb`.`Evento` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `datahoraentrada` DATETIME NOT NULL,
  `datahorasaida` DATETIME NOT NULL,
  `Pessoa_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_Evento_Pessoa1_idx` (`Pessoa_id` ASC),
  CONSTRAINT `fk_Evento_Pessoa1`
    FOREIGN KEY (`Pessoa_id`)
    REFERENCES `futurodb`.`Pessoa` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `futurodb`.`Disciplina`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `futurodb`.`Disciplina` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(255) NOT NULL,
  `cargahoraria` varchar(255) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `futurodb`.`Professor`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `futurodb`.`Professor` (
  `nivelformacao` varchar(255) NOT NULL,
  `formacao` varchar(255) NOT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `Pessoa_id` int(11) NOT NULL,
  INDEX `fk_Professor_Pessoa1_idx` (`id` ASC),
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_Professor_Pessoa1`
    FOREIGN KEY (`Pessoa_id`)
    REFERENCES `futurodb`.`Pessoa` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `futurodb`.`Turma`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `futurodb`.`Turma` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `codigo` varchar(255) NOT NULL,
  `Disciplina_id` int(11) NOT NULL,
  `Professor_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_Turma_Disciplina1_idx` (`Disciplina_id` ASC),
  INDEX `fk_Turma_Professor1_idx` (`Professor_id` ASC),
  CONSTRAINT `fk_Turma_Disciplina1`
    FOREIGN KEY (`Disciplina_id`)
    REFERENCES `futurodb`.`Disciplina` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Turma_Professor1`
    FOREIGN KEY (`Professor_id`)
    REFERENCES `futurodb`.`Professor` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `futurodb`.`Aluno`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `futurodb`.`Aluno` (
  `matricula` varchar(255) NOT NULL,
  `tipocotaingresso` INT NOT NULL,
  `dataingresso` DATETIME NOT NULL,
  `dataegresso` DATETIME NOT NULL,
  `Turma_id` int(11) NOT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `Pessoa_id` int(11) NOT NULL,
  INDEX `fk_Aluno_Turma1_idx` (`Turma_id` ASC),
  INDEX `fk_Aluno_Pessoa1_idx` (`id` ASC),
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_Aluno_Turma1`
    FOREIGN KEY (`Turma_id`)
    REFERENCES `futurodb`.`Turma` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Aluno_Pessoa1`
    FOREIGN KEY (`Pessoa_id`)
    REFERENCES `futurodb`.`Pessoa` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `futurodb`.`Professor_has_Disciplina`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `futurodb`.`Professor_has_Disciplina` (
  `Professor_id` int(11) NOT NULL,
  `Disciplina_id` int(11) NOT NULL,
  PRIMARY KEY (`Professor_id`, `Disciplina_id`),
  INDEX `fk_Professor_has_Disciplina_Disciplina1_idx` (`Disciplina_id` ASC),
  INDEX `fk_Professor_has_Disciplina_Professor1_idx` (`Professor_id` ASC),
  CONSTRAINT `fk_Professor_has_Disciplina_Professor1`
    FOREIGN KEY (`Professor_id`)
    REFERENCES `futurodb`.`Professor` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Professor_has_Disciplina_Disciplina1`
    FOREIGN KEY (`Disciplina_id`)
    REFERENCES `futurodb`.`Disciplina` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS; 
