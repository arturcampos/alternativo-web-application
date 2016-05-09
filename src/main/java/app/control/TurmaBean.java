package app.control;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import app.dao.TurmaDao;
import app.model.Turma;

@ManagedBean
@RequestScoped
public class TurmaBean {
	TurmaDao dao;
	Turma turma;
	List<Turma> turmas;

	@PostConstruct
	public void init() {
		dao = new TurmaDao(Turma.class);
	}

	public void salvar(Turma turma) {
		this.dao.save(turma);
	}

	public Turma buscarPorId(Long id) {
		return this.dao.findById(id);
	}

	public Turma remover(Long id) {
		return this.dao.remove(id);
	}

	public void atualizar(Turma turma) {
		this.dao.update(turma);
	}

	public List<Turma> buscarTodos() {
		return this.dao.findAll();
	}

	public TurmaDao getDao() {
		return dao;
	}

	public void setDao(TurmaDao dao) {
		this.dao = dao;
	}

	public Turma getTurma() {
		return turma;
	}

	public void setTurma(Turma turma) {
		this.turma = turma;
	}

	public List<Turma> getTurmas() {
		return turmas;
	}

	public void setTurmas(List<Turma> turmas) {
		this.turmas = turmas;
	}

}
