package app.dao;

import java.sql.SQLException;

import app.model.Aluno;

public class AlunoDao extends DaoImpl<Aluno> {

	public AlunoDao(Class<Aluno> clazz) {
		super(clazz);
	}

	public Aluno findByRegistrationNumber(String matricula) throws SQLException {
		return (Aluno) entitymanager.createNamedQuery("Aluno.findLastRegistrationNumber", Aluno.class)
				.setParameter(matricula, "wantedNumber");
	}

	public String findLastRegistrationNumber() {
		return (String) entitymanager.createNamedQuery("Aluno.findLastRegistrationNumber", String.class)
				.getSingleResult();
	}
}
