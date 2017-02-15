package app.dao;

import java.util.List;

import javax.persistence.Query;

import app.model.Aluno;
import app.model.Professor;

public class ProfessorDAO extends DAOImpl<Professor> {

	public ProfessorDAO(Class<Professor> clazz) {
		super(clazz);
	}

	@SuppressWarnings("unchecked")
	public List<Professor> findByName(String name){
		Query query = entitymanager.createNamedQuery("Professor.findByName", Professor.class)
				.setParameter("nome", "%" + name + "%");
			return (List<Professor>) query.getResultList();
	}

	public Professor findByPersonId(Long id) {
		return (Professor) entitymanager.createNamedQuery("Professor.findByPersonId", Professor.class)
				.setParameter("pessoaId", id).getSingleResult();
	}

}
