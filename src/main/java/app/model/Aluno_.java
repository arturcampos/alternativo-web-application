package app.model;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-01-20T20:01:19.793-0200")
@StaticMetamodel(Aluno.class)
public class Aluno_ {
	public static volatile SingularAttribute<Aluno, Long> id;
	public static volatile SingularAttribute<Aluno, Date> dataEgresso;
	public static volatile SingularAttribute<Aluno, Date> dataIngresso;
	public static volatile SingularAttribute<Aluno, String> matricula;
	public static volatile SingularAttribute<Aluno, String> status;
	public static volatile SingularAttribute<Aluno, Integer> tipoCotaIngresso;
	public static volatile SingularAttribute<Aluno, Pessoa> pessoa;
	public static volatile SingularAttribute<Aluno, Turma> turma;
}
