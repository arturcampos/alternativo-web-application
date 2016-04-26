package app.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;


/**
 * The persistent class for the professor database table.
 * 
 */
@Entity
@NamedQuery(name="Professor.findAll", query="SELECT p FROM Professor p")
@PrimaryKeyJoinColumn(name="Pessoa_id", referencedColumnName="id")
public class Professor extends Pessoa {
	private static final long serialVersionUID = 1L;

	/*@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;*/

	private String formacao;

	private String nivelformacao;

	//bi-directional many-to-many association to Disciplina
	@ManyToMany
	@JoinTable(
		name="disciplina_has_professor"
		, joinColumns={
			@JoinColumn(name="Professor_id")
			}
		, inverseJoinColumns={
			@JoinColumn(name="Disciplina_id")
			}
		)
	private List<Disciplina> disciplinas;

	//bi-directional many-to-one association to Pessoa
	@ManyToOne
	private Pessoa pessoa;

	//bi-directional many-to-one association to Turma
	@OneToMany(mappedBy="professor")
	private List<Turma> turmas;

	public Professor() {
	}

	/*public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}*/

	public String getFormacao() {
		return this.formacao;
	}

	public void setFormacao(String formacao) {
		this.formacao = formacao;
	}

	public String getNivelformacao() {
		return this.nivelformacao;
	}

	public void setNivelformacao(String nivelformacao) {
		this.nivelformacao = nivelformacao;
	}

	public List<Disciplina> getDisciplinas() {
		return this.disciplinas;
	}

	public void setDisciplinas(List<Disciplina> disciplinas) {
		this.disciplinas = disciplinas;
	}

	public Pessoa getPessoa() {
		return this.pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public List<Turma> getTurmas() {
		return this.turmas;
	}

	public void setTurmas(List<Turma> turmas) {
		this.turmas = turmas;
	}

	public Turma addTurma(Turma turma) {
		getTurmas().add(turma);
		turma.setProfessor(this);

		return turma;
	}

	public Turma removeTurma(Turma turma) {
		getTurmas().remove(turma);
		turma.setProfessor(null);

		return turma;
	}

}