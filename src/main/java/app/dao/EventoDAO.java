package app.dao;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import app.model.Evento;

public class EventoDAO extends DAOImpl<Evento> {

	public EventoDAO(Class<Evento> clazz) {
		super(clazz);
	}

	@SuppressWarnings("unchecked")
	public List<Evento> findEventsByPersonIdAndStatus(Long personId, String status) {
		Query query = entitymanager.createNamedQuery("Evento.findEventsByPersonIdAndStatus", Evento.class)
				.setParameter("personId", personId)
				.setParameter("status", status);
		try {
			return (List<Evento>)query.getResultList();
		} catch (NoResultException e) {
			e.printStackTrace();
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public List<Evento> findEventsByPersonIdAndDate(Long personId, String data) {
		Query query = entitymanager.createNamedQuery("Evento.findEventsByPersonIdAndDate", Evento.class)
						.setParameter("personId", personId)
						.setParameter("date" , data);
		try {
			return ((List<Evento>)query.getResultList());
		} catch (NoResultException e) {
			e.printStackTrace();
			return null;
		}
	}

}
