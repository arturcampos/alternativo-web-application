package app.control;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import app.dao.EventoDao;
import app.model.Evento;
import app.model.Pessoa;

@ManagedBean
@SessionScoped
public class EventoBean {

	private EventoDao dao;
	private Evento evento;
	private List<Evento> eventos;

	@PostConstruct
	public void init() {
		if (dao == null) {
			dao = new EventoDao(Evento.class);
		}
		evento = new Evento();
		eventos = new ArrayList<Evento>();
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

	public int buscarFaltasHorariosPorPessoa(Pessoa pessoa) {
		int qtd = 0;
		this.eventos = this.dao.findEventsByPersonIdAndStatus(pessoa.getId(), "NOK");
		if (this.eventos == null) {
			qtd = 0;
		} else if (this.eventos.isEmpty()) {
			qtd = 0;
		} else {
			qtd = this.eventos.size();
		}

		return qtd;

	}

	public String buscarPorPessoaEData(Pessoa pessoa, Date data) {
		String dataTmp = null;

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		dataTmp = sdf.format(data);
		this.eventos = this.dao.findEventsByPersonIdAndDate(pessoa.getId(), dataTmp);

		for (Evento e : this.eventos) {
			System.out.println(e.getId());
			System.out.println(e.getDataHoraEntrada());
		}
		return "listarEvento?faces-redirect=true";
	}
}
