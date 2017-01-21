package app.model;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-01-20T20:01:19.802-0200")
@StaticMetamodel(Evento.class)
public class Evento_ {
	public static volatile SingularAttribute<Evento, Long> id;
	public static volatile SingularAttribute<Evento, Date> dataHoraEntrada;
	public static volatile SingularAttribute<Evento, Date> dataHoraSaida;
	public static volatile SingularAttribute<Evento, String> status;
	public static volatile SingularAttribute<Evento, Pessoa> pessoa;
}
