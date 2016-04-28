package app.control;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import app.dao.AlunoDao;
import app.model.Aluno;

@ManagedBean
@SessionScoped
public class AlunoController {

	AlunoDao dao;
	Aluno aluno;
	List<Aluno> alunos;

	@PostConstruct
	public void init() {
		dao = new AlunoDao(Aluno.class);
	}

	public void salvar(Aluno aluno) {
		this.dao.save(aluno);
	}

	public Aluno buscarPorId(Long id) {
		return this.dao.findById(id);
	}

	public Aluno remover(Long id) {
		return this.dao.remove(id);
	}

	public void atualizar(Aluno aluno) {
		this.dao.update(aluno);
	}

	public List<Aluno> buscarTodos() {
		return this.dao.findAll();
	}

	public AlunoDao getDao() {
		return dao;
	}

	public void setDao(AlunoDao dao) {
		this.dao = dao;
	}

	public Aluno getAluno() {
		return aluno;
	}

	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}

	public List<Aluno> getAlunos() {
		return alunos;
	}

	public void setAlunos(List<Aluno> alunos) {
		this.alunos = alunos;
	}
}
