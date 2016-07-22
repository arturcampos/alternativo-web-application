package app.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the turma database table.
 *
 */
@Entity
@Table(name="turma", schema="futurodb")
@NamedQuery(name="Turma.findAll", query="SELECT t FROM Turma t")
public class Turma implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	private String codigo;

	//bi-directional many-to-one association to Aluno
	@OneToMany(mappedBy="turma", fetch=FetchType.EAGER)
	private List<Aluno> alunos;

	public Turma() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCodigo() {
		return this.codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public List<Aluno> getAlunos() {
		return this.alunos;
	}

	public void setAlunos(List<Aluno> alunos) {
		this.alunos = alunos;
	}

	public Aluno addAluno(Aluno aluno) {
		getAlunos().add(aluno);
		aluno.setTurma(this);

		return aluno;
	}

	public Aluno removeAluno(Aluno aluno) {
		getAlunos().remove(aluno);
		aluno.setTurma(null);

		return aluno;
	}

	@Override
	public Turma clone(){
		Turma turmaClone = new Turma();
		turmaClone.setId(this.id);
		turmaClone.setCodigo(this.codigo);
		turmaClone.setAlunos(this.alunos);

		return turmaClone;
	}

	public void restaurar(Turma turma){
		this.id = turma.getId();
		this.codigo = turma.getCodigo();
		this.alunos = turma.getAlunos();
	}

}