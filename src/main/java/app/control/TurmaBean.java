package app.control;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import app.dao.TurmaDAO;
import app.model.Turma;

@ManagedBean
@RequestScoped
public class TurmaBean {
	TurmaDAO dao;
	Turma turma;
	List<Turma> turmas;

	@PostConstruct
	public void init() {
		dao = new TurmaDAO(Turma.class);
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

	public TurmaDAO getDao() {
		return dao;
	}

	public void setDao(TurmaDAO dao) {
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
