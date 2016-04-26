package app.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the turma database table.
 * 
 */
@Entity
@NamedQuery(name="Turma.findAll", query="SELECT t FROM Turma t")
public class Turma implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private TurmaPK id;

	private String codigo;

	//bi-directional many-to-one association to Professor
	@ManyToOne
	private Professor professor;

	//bi-directional many-to-one association to Disciplina
	@ManyToOne
	private Disciplina disciplina;

	public Turma() {
	}

	public TurmaPK getId() {
		return this.id;
	}

	public void setId(TurmaPK id) {
		this.id = id;
	}

	public String getCodigo() {
		return this.codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public Professor getProfessor() {
		return this.professor;
	}

	public void setProfessor(Professor professor) {
		this.professor = professor;
	}

	public Disciplina getDisciplina() {
		return this.disciplina;
	}

	public void setDisciplina(Disciplina disciplina) {
		this.disciplina = disciplina;
	}

}