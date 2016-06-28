package app.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the evento database table.
 *
 */
@Entity
@Table(name="evento", schema="futurodb")
@NamedQueries({
@NamedQuery(name="Evento.findAll", query="SELECT e FROM Evento e"),
@NamedQuery(name="Evento.findEventsByPersonIdAndStatus", query="SELECT e FROM Evento e WHERE e.pessoa.id = :personId AND e.status = :status"),
@NamedQuery(name="Evento.findEventsByPersonIdAndDate", query="SELECT e FROM Evento e WHERE e.pessoa.id = :personId AND date_format(e.dataHoraEntrada, 'yyyy-MM-dd') = date_format(:date, 'yyyy-MM-dd')")})
public class Evento implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dataHoraEntrada;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dataHoraSaida;

	// bi-directional many-to-one association to Pessoa
	@ManyToOne
	private Pessoa pessoa;

	private String status;

	public Evento() {
	}

	public Evento(Date entrada, Date saida, String status, Pessoa pessoa) {
		this.dataHoraEntrada = entrada;
		this.dataHoraSaida = saida;
		this.status = status;
		this.pessoa = pessoa;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDataHoraEntrada() {
		return this.dataHoraEntrada;
	}

	public void setDataHoraEntrada(Date dataHoraEntrada) {
		this.dataHoraEntrada = dataHoraEntrada;
	}

	public Date getDataHoraSaida() {
		return this.dataHoraSaida;
	}

	public void setDataHoraSaida(Date dataHoraSaida) {
		this.dataHoraSaida = dataHoraSaida;
	}

	public Pessoa getPessoa() {
		return this.pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}