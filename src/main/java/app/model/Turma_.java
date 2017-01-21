package app.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-01-20T19:56:44.871-0200")
@StaticMetamodel(Turma.class)
public class Turma_ {
	public static volatile SingularAttribute<Turma, Long> id;
	public static volatile SingularAttribute<Turma, String> codigo;
	public static volatile SingularAttribute<Turma, String> status;
	public static volatile ListAttribute<Turma, Aluno> alunos;
}
