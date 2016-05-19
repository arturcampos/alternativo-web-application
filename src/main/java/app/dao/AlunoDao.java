package app.dao;

import java.util.List;

import app.model.Aluno;

public class AlunoDao extends DaoImpl<Aluno> {

	public AlunoDao(Class<Aluno> clazz) {
		super(clazz);
	}

	public List<Aluno> findByRegistrationNumber(String matricula) {
		// TODO Auto-generated method stub
		return null;
	}


}
