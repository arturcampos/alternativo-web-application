package app.control;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.apache.log4j.Logger;

import app.dao.EventoDAO;
import app.model.Evento;
import app.model.Pessoa;
import app.util.ListUtil;

@ManagedBean(name="eventoBean")
@SessionScoped
public class EventoBean implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private EventoDAO dao;
	private Evento evento;
	private List<Evento> eventos;
	private static Logger LOGGER = Logger.getLogger(RelatorioBean.class);

	@PostConstruct
	public void init() {
		if (dao == null) {
			dao = new EventoDAO(Evento.class);
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

	public EventoDAO getDao() {
		return dao;
	}

	public void setDao(EventoDAO dao) {
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

	public int buscarInfracoesHorariosPorPessoa(Pessoa pessoa) {
		int qtd = 0;
		this.eventos = this.dao.findEventsByPersonIdAndStatus(pessoa.getId(), "NOK");
		if(ListUtil.isValid(this.eventos)){
			qtd = this.eventos.size();
		}else{
			qtd = 0;
		}
		return qtd;

	}

	public String buscarPorPessoaEData(Pessoa pessoa, Date data) {
		this.eventos = this.dao.findEventsByPersonIdAndDate(pessoa.getId(), data);

		for (Evento e : this.eventos) {
			LOGGER.info(e.toString());
		}
		return "listarEvento?faces-redirect=true";
	}
}
