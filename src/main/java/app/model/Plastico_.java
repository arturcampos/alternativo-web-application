package app.model;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-01-20T20:01:19.807-0200")
@StaticMetamodel(Plastico.class)
public class Plastico_ {
	public static volatile SingularAttribute<Plastico, Long> id;
	public static volatile SingularAttribute<Plastico, Date> dataCadastro;
	public static volatile SingularAttribute<Plastico, String> linhaDigitavel;
	public static volatile SingularAttribute<Plastico, String> status;
	public static volatile SingularAttribute<Plastico, Pessoa> pessoa;
}
