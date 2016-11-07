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

import app.util.ListUtil;

/**
 * The persistent class for the turma database table.
 *
 */
@Entity
@Table(name = "turma", schema = "futurodb")
@NamedQueries({ @NamedQuery(name = "Turma.findAll", query = "SELECT t FROM Turma t"),
		@NamedQuery(name = "Turma.findByStatus", query = "SELECT t FROM Turma t WHERE t.status LIKE :wantedStatus"),
		@NamedQuery(name = "Turma.findByCode", query = "SELECT t FROM Turma t WHERE t.codigo LIKE :code") })
public class Turma implements Serializable, IBaseEntity {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String codigo;

	private String status;

	// bi-directional many-to-one association to Aluno
	@OneToMany(mappedBy = "turma", fetch = FetchType.EAGER)
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

	public boolean exists(List<Turma> turmas) {
		if (ListUtil.isValid(turmas)) {
			for (Turma t : turmas) {
				if (this.getCodigo().equals(t.getCodigo())) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public String toString() {
		return "Turma[id=" + id + ", codigo=" + codigo + ", status=" + status + "]";
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((alunos == null) ? 0 : alunos.hashCode());
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Turma))
			return false;
		Turma other = (Turma) obj;
		if (alunos == null) {
			if (other.alunos != null)
				return false;
		} else if (!alunos.equals(other.alunos))
			return false;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		return true;
	}

	@Override
	public Long pegarId() {
		return new Long(id);
	}

	public boolean existeAlunoAtivo() {
		return this.qtdAlunosAtivos() > 0;
	}

	public int qtdAlunosAtivos() {
		int cont = 0;
		if (ListUtil.isValid(alunos)) {
			for (Aluno a : alunos) {
				if (a.getStatus().equals("ATIVO")) {
					cont++;
				}
			}
		}
		return cont;
	}

	public int qtdAlunosInativos() {
		int cont = 0;
		if (ListUtil.isValid(alunos)) {
			for (Aluno a : alunos) {
				if (a.getStatus().equals("INATIVO")) {
					cont++;
				}
			}
		}
		return cont;
	}

}