package app.control;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;

import app.dao.TurmaDAO;
import app.model.Aluno;
import app.model.Plastico;
import app.model.Turma;

@ManagedBean(name = "turmaBean")
@SessionScoped
public class TurmaBean {
	private TurmaDAO dao;
	private Turma turma;
	private Turma turmaAnterior;
	private List<Turma> turmas;
	private boolean editado;
	private Logger LOGGER = Logger.getLogger(TurmaBean.class);

	@PostConstruct
	public void init() {
		LOGGER.info("recarregando bean Turma");
		this.dao = new TurmaDAO(Turma.class);
		this.turma = new Turma();
		this.turmaAnterior = new Turma();
		this.editado = false;
		this.turmas = new ArrayList<Turma>();

	}

	public String salvar(Turma turma) {
		LOGGER.info("Salvando turma");
		try {
			this.dao.save(turma);
			LOGGER.info("Turma cadastrada com sucesso");
			info("Turma cadastrada com sucesso");
			return "listarTurma?faces-redirect=true";
		} catch (Exception e) {
			LOGGER.error("Erro ao salvar turma", e);
			error("Erro ao salvar turma: " + e.getMessage());
			return "salvarTurma?faces-redirect=true";
		}

	}

	public String buscarPorId(Long id) {
		LOGGER.info("Buscando id: " + id);
		try {
			init();
			this.turma = this.dao.findById(id);
			if (this.turma != null) {
				this.turmas.add(this.turma);
				LOGGER.info("Turma " + id + " encontrada");
				info("Turma " + id + " encontrada");
			} else {
				LOGGER.info("Turma " + id + " nao encontrada");
				warn("Turma " + id + " nao encontrada");
			}
		} catch (Exception e) {
			LOGGER.error("Erro ao buscar turma " + id, e);
			error("Erro ao buscar turma " + id + " " + e.getMessage());
		}
		return "listarTurma?faces-redirect=true";
	}

	public String remover(Turma turma) {
		LOGGER.info("Removendo id: " + turma.getId());
		try {
			if ((turma.getAlunos() != null) && !turma.getAlunos().isEmpty()) {
				LOGGER.info("Existem alunos cadastrados na Turma " + turma.getId() + " e ela nao podera ser removida");
				error("Existem alunos cadastrados na Turma " + turma.getId() + " e ela nao podera ser removida");
			} else {
				this.dao.remove(turma.getId());
				this.turmas.remove(turma);
				LOGGER.info("Turma " + turma.getId() + " removida");
				info("Turma " + turma.getId() + " removida");
			}
		} catch (Exception e) {
			LOGGER.error("Erro ao remover turma " + turma.getId(), e);
			error("Erro ao remover turma " + turma.getId() + " " + e.getMessage());
		}
		return "listarTurma?faces-redirect=true";
	}

	/**
	 *
	 * @param turma
	 *            - the student in
	 * @return the action to go to save ou list student
	 */
	public String atualizar(Turma turma) {
		try {
			LOGGER.info("Iniciando atualizacao do turma: " + turma.getId() );
			this.turma = turma.clone();
			LOGGER.info("Realizando clone");
			this.turmaAnterior = turma;
			this.editado = true;
			LOGGER.info("Pronto para atualizar");
			return "atualizarAluno?faces-redirect=true";
		} catch (Exception e) {
			error("Erro ao direcioar para atualizacao de dados do turma");
			return "listarAluno?faces-redirect=true";
		}
	}

	/**
	 *
	 * @return the action to go to save ou list student
	 */
	public String salvarAtualizar() {
		try {
			LOGGER.info("Atualizando turma");

			this.dao.update(this.turma);
			this.turmas.remove(this.turmaAnterior);
			this.turmas.add(this.turma);
			this.editado = false;

			info("Dados de " + this.turma.getId() + " atualizados");
			LOGGER.info("Dados de " + this.turma.getId() + " atualizados");
			init();
			return "listarTurma?faces-redirect=true";
		} catch (Exception e) {
			LOGGER.error("Erro ao atualizar as informacoes", e);
			error("Erro ao atualizar as informacoes: " + e.getMessage());
			return "listarTurma?faces-redirect=true";
		}
	}

	/**
	 * @return the action to go to list the student
	 */
	public String cancelarAtualizar() {
		this.turma.restaurar(this.turmaAnterior);
		this.turmaAnterior = new Turma();
		editado = false;
		return "listarAluno?faces-redirect=true";
	}

	/**
	 *
	 */
	public String buscarTodos() {
		init();
		LOGGER.info("Buscando turmas");
		this.turmas = this.dao.findAll();
		if (this.turmas.isEmpty() || this.turmas == null) {
			LOGGER.info("Nenhuma turma encontrada");
			info("Nenhuma turma encontrada");
		} else {
			LOGGER.info("Lista de turmas");
		}
		return "listarTurma?faces-redirect=true";
	}

	public String cancelarSalvar() {
		init();
		return "Inicio?faces-redirect=true";
	}

	public TurmaDAO getDao() {
		return dao;
	}

	public void setDao(TurmaDAO dao) {
		this.dao = dao;
	}

	public Turma getTurma() {
		return turma;
	}

	public void setTurma(Turma turma) {
		this.turma = turma;
	}

	public List<Turma> getTurmas() {
		return turmas;
	}

	public void setTurmas(List<Turma> turmas) {
		this.turmas = turmas;
	}

	/**
	 *
	 * @param message
	 */
	public void info(String message) {
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, " Info", message));
	}

	/**
	 *
	 * @param message
	 */
	public void warn(String message) {
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_WARN, " Atencao!", message));
	}

	/**
	 *
	 * @param message
	 */
	public void error(String message) {
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_ERROR, " Erro!", message));
	}

	/**
	 *
	 * @param message
	 */
	public void fatal(String message) {
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_FATAL, " Fatal!", message));
	}

}
