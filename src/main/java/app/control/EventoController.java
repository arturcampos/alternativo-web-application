package app.control;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import app.dao.EventoDao;
import app.model.Evento;

@ManagedBean
@RequestScoped
public class EventoController {

	EventoDao dao;
	Evento evento;
	List<Evento> eventos;

	@PostConstruct
	public void init() {
		dao = new EventoDao(Evento.class);
	}

	public void salvar(Evento evento) {
		this.dao.save(evento);
	}

	public Evento buscarPorId(Long id) {
		return this.dao.findById(id);
	}

	public Evento remover(Long id) {
		return this.dao.remove(id);
	}

	public void atualizar(Evento evento) {
		this.dao.update(evento);
	}

	public List<Evento> buscarTodos() {
		return this.dao.findAll();
	}

	public EventoDao getDao() {
		return dao;
	}

	public void setDao(EventoDao dao) {
		this.dao = dao;
	}

	public Evento getEvento() {
		return evento;
	}

	public void setEvento(Evento evento) {
		this.evento = evento;
	}

	public List<Evento> getEventos() {
		return eventos;
	}

	public void setEventos(List<Evento> eventos) {
		this.eventos = eventos;
	}
}
