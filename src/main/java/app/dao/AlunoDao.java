package app.dao;

import java.sql.SQLException;
import java.util.List;

import app.model.Aluno;

public class AlunoDao extends DaoImpl<Aluno> {

	/**
	 * 
	 * @param clazz
	 */
	public AlunoDao(Class<Aluno> clazz) {
		super(clazz);
	}

	/**
	 * 
	 * @param registrationNumber
	 * @return
	 * @throws SQLException
	 * @author ARTUR
	 * 
	 */
	public Aluno findByRegistrationNumber(String registrationNumber) throws SQLException {
		return (Aluno) entitymanager.createNamedQuery("Aluno.findByRegistrationNumber", Aluno.class)
				.setParameter("wantedNumber", registrationNumber).getSingleResult();
	}

	/**
	 * 
	 * @return
	 * @throws SQLException
	 * @author ARTUR
	 */
	public String findLastRegistrationNumber() throws SQLException {
		return (String) entitymanager.createNamedQuery("Aluno.findLastRegistrationNumber", String.class)
				.getSingleResult();
	}

	/**
	 * 
	 * @param status
	 * @return
	 * @throws SQLException
	 * @author ARTUR
	 */
	public List<Aluno> findByStatus(String status) throws SQLException {
		return (List<Aluno>) entitymanager.createNamedQuery("Aluno.findByStatus", Aluno.class)
				.setParameter("wantedStatus", status).getResultList();
	}
}
