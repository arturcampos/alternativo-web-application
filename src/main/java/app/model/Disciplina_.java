package app.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-01-20T20:01:19.795-0200")
@StaticMetamodel(Disciplina.class)
public class Disciplina_ {
	public static volatile SingularAttribute<Disciplina, Long> id;
	public static volatile SingularAttribute<Disciplina, String> cargaHoraria;
	public static volatile SingularAttribute<Disciplina, String> nome;
	public static volatile SingularAttribute<Disciplina, Integer> turmaId;
	public static volatile ListAttribute<Disciplina, Professor> professors;
}
