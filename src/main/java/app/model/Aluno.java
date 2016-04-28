package app.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the aluno database table.
 * 
 */
@Entity
@NamedQuery(name="Aluno.findAll", query="SELECT a FROM Aluno a")
@PrimaryKeyJoinColumn(name="Pessoa_id")
public class Aluno extends Pessoa {
	private static final long serialVersionUID = 1L;

	/*@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int pessoa_id;*/

	@Temporal(TemporalType.TIMESTAMP)
	private Date dataegresso;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dataingresso;

	private String matricula;

	private int tipocotaingresso;

	//bi-directional many-to-one association to Turma
	@ManyToOne
	private Turma turma;

	//bi-directional one-to-one association to Pessoa
	@OneToOne
	private Pessoa pessoa;

	public Aluno() {
	}

	/*public int getPessoa_id() {
		return this.pessoa_id;
	}

	public void setPessoa_id(int pessoa_id) {
		this.pessoa_id = pessoa_id;
	}*/

	public Date getDataegresso() {
		return this.dataegresso;
	}

	public void setDataegresso(Date dataegresso) {
		this.dataegresso = dataegresso;
	}

	public Date getDataingresso() {
		return this.dataingresso;
	}

	public void setDataingresso(Date dataingresso) {
		this.dataingresso = dataingresso;
	}

	public String getMatricula() {
		return this.matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public int getTipocotaingresso() {
		return this.tipocotaingresso;
	}

	public void setTipocotaingresso(int tipocotaingresso) {
		this.tipocotaingresso = tipocotaingresso;
	}

	public Turma getTurma() {
		return this.turma;
	}

	public void setTurma(Turma turma) {
		this.turma = turma;
	}

	public Pessoa getPessoa() {
		return this.pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

}