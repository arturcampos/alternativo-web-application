package app.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;

/**
 * The persistent class for the professor database table.
 * 
 */
@Entity
@NamedQuery(name = "Aluno.findAll", query = "SELECT a FROM Aluno a")
@PrimaryKeyJoinColumn(name="id", referencedColumnName="id")
public class Aluno extends Pessoa {

	private static final long serialVersionUID = 1L;

	private Long id;

	private String matricula;

	private int tipocotaingresso;

	private Date dataingresso;

	private Date dataegresso;

	@OneToOne
	private Turma turma;

	@OneToMany(mappedBy = "aluno")
	private List<Pessoa> pessoas;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMatricula() {
		return matricula;
	}
	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public int getTipocotaingresso() {
		return tipocotaingresso;
	}

	public void setTipocotaingresso(int tipocotaingresso) {
		this.tipocotaingresso = tipocotaingresso;
	}

	public Date getDataingresso() {
		return dataingresso;
	}

	public void setDataingresso(Date dataingresso) {
		this.dataingresso = dataingresso;
	}

	public Date getDataegresso() {
		return dataegresso;
	}

	public void setDataegresso(Date dataegresso) {
		this.dataegresso = dataegresso;
	}

	public Turma getTurma() {
		return turma;
	}

	public void setTurma(Turma turma) {
		this.turma = turma;
	}

	public List<Pessoa> getPessoas() {
		return pessoas;
	}

	public void setPessoas(List<Pessoa> pessoas) {
		this.pessoas = pessoas;
	}
}
