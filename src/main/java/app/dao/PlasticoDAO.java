package app.dao;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import app.model.Plastico;

public class PlasticoDAO extends DAOImpl<Plastico> {

	public PlasticoDAO(Class<Plastico> clazz) {
		super(clazz);
	}

	public Plastico findByPersonId(Long id) {
		Query query = entitymanager.createNamedQuery("Plastico.findByPersonId", Plastico.class)
				.setParameter("wantedId", id);
		try {
			Plastico p = (Plastico) query.getSingleResult();
			return p;
		} catch (NoResultException e) {
			System.err.println(e.getMessage());
			return null;
		}

	}
	public Plastico findByDigitableLine(String digitableLine){

		Query query = entitymanager.createNamedQuery("Plastico.findByDigitableLine", Plastico.class)
				.setParameter("digitableLine", digitableLine);
		try {
			Plastico p = (Plastico) query.getSingleResult();
			return p;
		} catch (NoResultException e) {
			System.err.println(e.getMessage());
			return null;
		}

	}
}
