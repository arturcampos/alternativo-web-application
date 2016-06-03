package app.dao;

import java.util.List;

import javax.persistence.Query;

import app.model.Professor;

public class ProfessorDao extends DaoImpl<Professor> {

	public ProfessorDao(Class<Professor> clazz) {
		super(clazz);
	}

	public List<Professor> findByName(String name){
		Query query = entitymanager.createNamedQuery("Professor.findByName", Professor.class)
				.setParameter("nome", name);
			return (List<Professor>) query.getResultList();
	}

}
