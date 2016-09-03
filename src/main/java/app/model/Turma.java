package app.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * The persistent class for the turma database table.
 *
 */
@Entity
@Table(name = "turma", schema = "futurodb")
@NamedQueries({ @NamedQuery(name = "Turma.findAll", query = "SELECT t FROM Turma t"),
		@NamedQuery(name = "Turma.findByStatus", query = "SELECT t FROM Turma t WHERE t.status LIKE :wantedStatus") })
public class Turma implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String codigo;

	private String status;

	// bi-directional many-to-one association to Aluno
	@OneToMany(mappedBy = "turma", fetch = FetchType.LAZY)
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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
	public Turma clone() {
		Turma turmaClone = new Turma();
		turmaClone.setId(this.id);
		turmaClone.setCodigo(this.codigo);
		turmaClone.setStatus(this.status);
		turmaClone.setAlunos(this.alunos);

		return turmaClone;
	}

	public void restaurar(Turma turma) {
		this.id = turma.getId();
		this.codigo = turma.getCodigo();
		this.alunos = turma.getAlunos();
		this.status = turma.getStatus();
	}

	public boolean exists(List<Turma> turmas){
		for(Turma t : turmas){
			if(this.getCodigo().equals(t.getCodigo())){
				return true;
			}
		}
		return false;
	}
}