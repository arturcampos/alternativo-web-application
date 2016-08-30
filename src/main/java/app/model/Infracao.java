package app.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the infracao database table.
 *
 */
@Entity
@Table(name="infracao")
@NamedQuery(name="Infracao.findAll", query="SELECT p FROM Infracao p")
public class Infracao implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	private String descricao;

	private String observacao;

	//bi-directional many-to-one association to Aluno
	@ManyToOne
	private Aluno aluno;

	//bi-directional many-to-one association to TipoInfracao
	@ManyToOne
	private TipoInfracao tipoInfracao;

	public Infracao() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescricao() {
		return this.descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getObservacao() {
		return this.observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public Aluno getAluno() {
		return this.aluno;
	}

	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}

	public TipoInfracao getTipoInfracao() {
		return this.tipoInfracao;
	}

	public void setTipoInfracao(TipoInfracao tipoInfracao) {
		this.tipoInfracao = tipoInfracao;
	}

}