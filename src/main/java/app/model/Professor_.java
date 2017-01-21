package app.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-01-20T20:01:19.809-0200")
@StaticMetamodel(Professor.class)
public class Professor_ {
	public static volatile SingularAttribute<Professor, Long> id;
	public static volatile SingularAttribute<Professor, String> formacao;
	public static volatile SingularAttribute<Professor, String> nivelFormacao;
	public static volatile ListAttribute<Professor, Disciplina> disciplinas;
	public static volatile SingularAttribute<Professor, Pessoa> pessoa;
}
