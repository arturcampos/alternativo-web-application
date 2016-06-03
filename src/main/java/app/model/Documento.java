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
 * The persistent class for the documento database table.
 *
 */
@Entity
@Table(name = "Documento", schema = "futurodb")

@NamedQueries({
	@NamedQuery(name = "Documento.findAll", query = "SELECT d FROM Documento d"),
	@NamedQuery(name = "Documento.findByPersonId", query = "SELECT d FROM Documento d WHERE d.pessoa.id = :personId")
})
public class Documento implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String numero;

	private String tipo;

	@Temporal(TemporalType.DATE)
	private Date dataExpedicao;

	@Temporal(TemporalType.DATE)
	private Date dataValidade;

	private String orgaoEmissor;

	private String uf;

	// bi-directional many-to-one association to Pessoa
	@ManyToOne
	private Pessoa pessoa;

	public Documento() {
	}

	public Documento(Long id, String numero, String tipo, Date dataExpedicao, Date dataValidade, String orgaoEmissor,
			String uf, Pessoa pessoa) {
		this.id = id;
		this.numero = numero;
		this.tipo = tipo;
		this.dataExpedicao = dataExpedicao;
		this.dataValidade = dataValidade;
		this.orgaoEmissor = orgaoEmissor;
		this.uf = uf;
		this.pessoa = pessoa;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDataExpedicao() {
		return this.dataExpedicao;
	}

	public void setDataExpedicao(Date dataExpedicao) {
		this.dataExpedicao = dataExpedicao;
	}

	public Date getDataValidade() {
		return this.dataValidade;
	}

	public void setDataValidade(Date dataValidade) {
		this.dataValidade = dataValidade;
	}

	public String getOrgaoEmissor() {
		return this.orgaoEmissor;
	}

	public void setOrgaoEmissor(String orgaoemissor) {
		this.orgaoEmissor = orgaoemissor;
	}

	public String getUf() {
		return this.uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Pessoa getPessoa() {
		return this.pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	@Override
	public String toString() {
		return new String("Número:" + this.numero + "\nOrgão Emissor:" + this.orgaoEmissor + "\nData de Emissão:"
				+ this.dataExpedicao + "\nData Validade:" + this.dataValidade);
	}

	@Override
	public Documento clone() {
		return new Documento(this.id, this.numero, this.tipo, this.dataExpedicao, this.dataValidade, this.orgaoEmissor,
				this.uf, this.pessoa);
	}

	public void restaurar(Documento documento) {
		this.id = documento.getId();
		this.dataExpedicao = documento.getDataExpedicao();
		this.dataValidade = documento.getDataValidade();
		this.uf = documento.getUf();
		this.numero = documento.getNumero();
		this.tipo = documento.getTipo();
		this.orgaoEmissor = documento.getOrgaoEmissor();

	}


}