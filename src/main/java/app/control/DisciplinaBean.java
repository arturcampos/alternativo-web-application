package app.control;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import app.dao.DisciplinaDAO;
import app.model.Disciplina;

@ManagedBean
@RequestScoped
public class DisciplinaBean {

	DisciplinaDAO dao;
	Disciplina disciplina;
	List<Disciplina> disciplinas;

	@PostConstruct
	public void init() {
		dao = new DisciplinaDAO(Disciplina.class);
	}

	public void salvar(Disciplina disciplina) {
		this.dao.save(disciplina);
	}

	public Disciplina buscarPorId(Long id) {
		return this.dao.findById(id);
	}

	public Disciplina remover(Long id) {
		return this.dao.remove(id);
	}

	public void atualizar(Disciplina disciplina) {
		this.dao.update(disciplina);
	}

	public List<Disciplina> buscarTodos() {
		return this.dao.findAll();
	}

	public DisciplinaDAO getDao() {
		return dao;
	}

	public void setDao(DisciplinaDAO dao) {
		this.dao = dao;
	}

	public Disciplina getDisciplina() {
		return disciplina;
	}

	public void setDisciplina(Disciplina disciplina) {
		this.disciplina = disciplina;
	}

	public List<Disciplina> getDisciplinas() {
		return disciplinas;
	}

	public void setDisciplinas(List<Disciplina> disciplinas) {
		this.disciplinas = disciplinas;
	}
}
