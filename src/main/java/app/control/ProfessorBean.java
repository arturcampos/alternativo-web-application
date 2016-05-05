package app.control;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import app.dao.ProfessorDao;
import app.model.Professor;

@ManagedBean
@SessionScoped
public class ProfessorBean {

	ProfessorDao dao;
	Professor professor;
	List<Professor> professors;

	@PostConstruct
	public void init() {
		dao = new ProfessorDao(Professor.class);
	}

	public void salvar(Professor professor) {
		this.dao.save(professor);
	}

	public Professor buscarPorId(Long id) {
		return this.dao.findById(id);
	}

	public Professor remover(Long id) {
		return this.dao.remove(id);
	}

	public void atualizar(Professor professor) {
		this.dao.update(professor);
	}

	public List<Professor> buscarTodos() {
		return this.dao.findAll();
	}

	public ProfessorDao getDao() {
		return dao;
	}

	public void setDao(ProfessorDao dao) {
		this.dao = dao;
	}

	public Professor getProfessor() {
		return professor;
	}

	public void setProfessor(Professor professor) {
		this.professor = professor;
	}

	public List<Professor> getProfessors() {
		return professors;
	}

	public void setProfessors(List<Professor> professors) {
		this.professors = professors;
	}
}
