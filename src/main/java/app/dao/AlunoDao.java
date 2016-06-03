package app.dao;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

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
	public Aluno findByRegistrationNumber(String registrationNumber) {
		Query query = entitymanager.createNamedQuery("Aluno.findByRegistrationNumber", Aluno.class)
				.setParameter("wantedNumber", registrationNumber);
		try {
			return (Aluno) query.getSingleResult();
		} catch (NoResultException e) {
			e.printStackTrace();
			return null;
		}
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
