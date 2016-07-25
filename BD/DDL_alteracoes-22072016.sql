-- --------------------------------------------
-- removendo chave estrangeira disciplina
ALTER TABLE turma
DROP FOREIGN KEY fk_Turma_Disciplina1;

-- removendo columna disciplina_id
ALTER TABLE turma
DROP COLUMN Disciplina_id;
-- --------------------------------------------

-- --------------------------------------------
-- removendo chave estrangeira professor
ALTER TABLE turma
DROP FOREIGN KEY fk_Turma_Professor1;

-- removendo columna professor_id
ALTER TABLE turma
DROP COLUMN Professor_id;


-- ----------------------------------------------------------
-- create table tipo penalidades
CREATE TABLE `tipo_penalidade` (
  `id` int(11) NOT NULL,
  `tipo` varchar(100) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB DEFAULT CHARSET=utf8;

-- -------------------------------------------------------
-- create table penalidades
CREATE TABLE `penalidades` (
  `id` int(11) NOT NULL,
  `descricao` varchar(255) NOT NULL,
  `observacao` varchar(255) NULL,
  `TipoPenalidade_id` int(11) NOT NULL,
  `Aluno_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_Penalidades_TipoPenalidade1_idx` (`TipoPenalidade_id` ASC),
  INDEX `fk_Penalidades_Aluno1_idx` (`Aluno_id` ASC),
  CONSTRAINT `fk_Penalidades_TipoPenalidade1`
    FOREIGN KEY (`TipoPenalidade_id`)
    REFERENCES `tipo_penalidade` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Penalidades_Aluno1`
    FOREIGN KEY (`Aluno_id`)
    REFERENCES `aluno` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `usuario` (
  `id` int(11) NOT NULL,
  `nomeusuario` varchar(100) NOT NULL,
  `senha` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;