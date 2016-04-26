package app.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the evento database table.
 * 
 */
@Entity
@NamedQuery(name="Evento.findAll", query="SELECT e FROM Evento e")
public class Evento implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@Temporal(TemporalType.TIMESTAMP)
	private Date datahoraentrada;

	@Temporal(TemporalType.TIMESTAMP)
	private Date datahorasaida;

	//bi-directional many-to-one association to Pessoa
	@ManyToOne
	private Pessoa pessoa;

	public Evento() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDatahoraentrada() {
		return this.datahoraentrada;
	}

	public void setDatahoraentrada(Date datahoraentrada) {
		this.datahoraentrada = datahoraentrada;
	}

	public Date getDatahorasaida() {
		return this.datahorasaida;
	}

	public void setDatahorasaida(Date datahorasaida) {
		this.datahorasaida = datahorasaida;
	}

	public Pessoa getPessoa() {
		return this.pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

}