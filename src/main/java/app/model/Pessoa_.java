package app.model;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-01-20T20:01:19.805-0200")
@StaticMetamodel(Pessoa.class)
public class Pessoa_ {
	public static volatile SingularAttribute<Pessoa, Long> id;
	public static volatile SingularAttribute<Pessoa, Date> dataNasc;
	public static volatile SingularAttribute<Pessoa, String> email;
	public static volatile SingularAttribute<Pessoa, String> estadoCivil;
	public static volatile SingularAttribute<Pessoa, String> etnia;
	public static volatile SingularAttribute<Pessoa, String> nacionalidade;
	public static volatile SingularAttribute<Pessoa, String> naturalidade;
	public static volatile SingularAttribute<Pessoa, String> necessidadesEspeciais;
	public static volatile SingularAttribute<Pessoa, String> nome;
	public static volatile SingularAttribute<Pessoa, String> nomeMae;
	public static volatile SingularAttribute<Pessoa, String> nomePai;
	public static volatile SingularAttribute<Pessoa, String> numeroCelular;
	public static volatile SingularAttribute<Pessoa, String> responsavelLegal;
	public static volatile SingularAttribute<Pessoa, String> sexo;
	public static volatile SingularAttribute<Pessoa, String> tipoPessoa;
	public static volatile SingularAttribute<Pessoa, String> uf;
	public static volatile ListAttribute<Pessoa, Documento> documentos;
	public static volatile ListAttribute<Pessoa, Endereco> enderecos;
	public static volatile ListAttribute<Pessoa, Plastico> plasticos;
}
