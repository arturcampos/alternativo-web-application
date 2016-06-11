package app.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import app.model.Evento;
import app.model.Pessoa;

public class EventoDao extends DaoImpl<Evento> {

	public EventoDao(Class<Evento> clazz) {
		super(clazz);
	}

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

	public List<Evento> findEventsByPersonIdAndDate(Long personId, String data) {
		Query query = entitymanager.createNamedQuery("Evento.findEventsByPersonIdAndDate", Evento.class)
						.setParameter("personId", personId)
						.setParameter("date" , data);
		try {
			return (List<Evento>)query.getResultList();
		} catch (NoResultException e) {
			e.printStackTrace();
			return null;
		}
	}

}
