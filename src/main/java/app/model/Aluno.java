package app.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the aluno database table.
 * 
 */
@Entity
@Table(name = "aluno", schema = "futurodb")
@NamedQuery(name = "Aluno.findAll", query = "SELECT a FROM Aluno a")
public class Aluno implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dataegresso;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dataingresso;

	private String matricula;

	private int tipocotaingresso;

	// bi-directional many-to-one association to Turma
	@ManyToOne
	private Turma turma;

	// bi-directional one-to-one association to Pessoa
	@OneToOne
	private Pessoa pessoa;
	
	private String status;

	public Aluno() {
	}

	public Aluno(Long id, Date dataegresso, Date dataingresso,
			String matricula, int tipocotaingresso, Turma turma, Pessoa pessoa, String status) {
		super();
		this.id = id;
		this.dataegresso = dataegresso;
		this.dataingresso = dataingresso;
		this.matricula = matricula;
		this.tipocotaingresso = tipocotaingresso;
		this.turma = turma;
		this.pessoa = pessoa;
		this.status = status;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	@Override
	public Aluno clone() {
		return new Aluno(id, dataegresso, dataingresso, matricula,
				tipocotaingresso, turma, pessoa, status);
	}
	
	public void restaurar(Aluno aluno) {
		this.id = aluno.getId();
		this.dataegresso = aluno.getDataegresso();
		this.dataingresso = aluno.getDataingresso();
		this.matricula = aluno.getMatricula();
		this.tipocotaingresso = aluno.getTipocotaingresso();
		this.turma = aluno.getTurma();
		this.pessoa = aluno.getPessoa();
		this.status = aluno.getStatus();
    }

	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getStatus(){
		return this.status;
	}

}