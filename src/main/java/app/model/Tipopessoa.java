package app.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the tipopessoa database table.
 * 
 */
@Entity
@NamedQuery(name="Tipopessoa.findAll", query="SELECT t FROM Tipopessoa t")
public class Tipopessoa implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	private String tipopessoa;

	//bi-directional one-to-one association to Pessoa
	@OneToOne
	private Pessoa pessoa;

	public Tipopessoa() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTipopessoa() {
		return this.tipopessoa;
	}

	public void setTipopessoa(String tipopessoa) {
		this.tipopessoa = tipopessoa;
	}

	public Pessoa getPessoa() {
		return this.pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

}