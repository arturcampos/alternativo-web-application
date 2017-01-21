package app.model;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-01-20T20:01:19.797-0200")
@StaticMetamodel(Documento.class)
public class Documento_ {
	public static volatile SingularAttribute<Documento, Long> id;
	public static volatile SingularAttribute<Documento, Date> dataExpedicao;
	public static volatile SingularAttribute<Documento, Date> dataValidade;
	public static volatile SingularAttribute<Documento, String> numero;
	public static volatile SingularAttribute<Documento, String> orgaoEmissor;
	public static volatile SingularAttribute<Documento, String> tipo;
	public static volatile SingularAttribute<Documento, String> uf;
	public static volatile SingularAttribute<Documento, Pessoa> pessoa;
}
