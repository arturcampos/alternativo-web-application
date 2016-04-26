package app.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the plastico database table.
 * 
 */
@Entity
@NamedQuery(name="Plastico.findAll", query="SELECT p FROM Plastico p")
public class Plastico implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private PlasticoPK id;

	@Temporal(TemporalType.TIMESTAMP)
	private Date datacadastro;

	private String linhadigitavel;

	private String status;

	//bi-directional many-to-one association to Pessoa
	@ManyToOne
	private Pessoa pessoa;

	public Plastico() {
	}

	public PlasticoPK getId() {
		return this.id;
	}

	public void setId(PlasticoPK id) {
		this.id = id;
	}

	public Date getDatacadastro() {
		return this.datacadastro;
	}

	public void setDatacadastro(Date datacadastro) {
		this.datacadastro = datacadastro;
	}

	public String getLinhadigitavel() {
		return this.linhadigitavel;
	}

	public void setLinhadigitavel(String linhadigitavel) {
		this.linhadigitavel = linhadigitavel;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Pessoa getPessoa() {
		return this.pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

}