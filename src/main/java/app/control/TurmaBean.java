package app.control;

import java.io.Serializable;
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
import app.model.Turma;
import app.util.ListUtil;
import app.util.Status;

@ManagedBean(name = "turmaBean")
@SessionScoped
public class TurmaBean implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private TurmaDAO dao;
	private Turma turma;
	private Turma turmaAnterior;
	private List<Turma> turmas;
	private List<Aluno> alunos;

	private Logger LOGGER = Logger.getLogger(TurmaBean.class);

	@PostConstruct
	public void init() {
		LOGGER.info("Metodo init()");
		this.dao = new TurmaDAO(Turma.class);
		this.turma = new Turma();
		this.turmaAnterior = new Turma();
		this.alunos = new ArrayList<Aluno>();
		if (!ListUtil.isValid(this.turmas)) {
			this.turmas = dao.findByStatus(Status.ATIVO.toString());
		}
	}

	public String salvar() {
		this.turmas = dao.findByStatus("ATIVO");
		if (this.turma.exists(this.turmas)) {
			LOGGER.warn("Ja existe uma turma ativa com o nome solicitado: " + this.turma.getCodigo());
			warn("Ja existe uma turma ativa com o nome solicitado: " + this.turma.getCodigo());
		} else {
			LOGGER.info("Salvando turma com status ativo");
			this.turma.setStatus("ATIVO");
			this.dao.save(this.turma);
			LOGGER.info("Turma " + this.turma.getCodigo() + " criada com sucesso");
			info("Turma " + this.turma.getCodigo() + " criada com sucesso");
			init();
		}
		return "salvarTurma?faces-redirect=true";
	}

	public String buscarPorId(Long id) {
		LOGGER.info("Buscando turma com id: " + id);
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
		return "listarTurma?faces-redirect=true";
	}

	public String remover(Turma turma) {
		LOGGER.info("Removendo turma com id: " + turma.getId());

		if (turma.existeAlunoAtivo()) {
			LOGGER.info("Existem alunos cadastrados na Turma " + turma.getId() + " e ela nao podera ser removida");
			error("Existem alunos cadastrados na Turma " + turma.getId() + " e ela nao podera ser removida");
		} else {
			try {
				turma.setStatus(Status.INATIVO.toString());
				this.dao.update(turma);
				this.turmas.remove(turma);
				LOGGER.info("Turma " + turma.getId() + " removida");
				info("Turma " + turma.getId() + " removida");
			} catch (Exception e) {
				LOGGER.error("Erro ao remover turma " + turma.getId(), e);
				error("Erro ao remover turma " + turma.getId() + " " + e.getMessage());
			}
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
		LOGGER.info("Iniciando atualizacao do turma: " + turma.getCodigo());
		this.turma = turma.clone();
		this.turmaAnterior = turma;
		return "atualizarAluno?faces-redirect=true";
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

			info("Dados de " + this.turma.getCodigo() + " atualizados");
			LOGGER.info("Dados de " + this.turma.getCodigo() + " atualizados");
			init();
			return "listarTurma?faces-redirect=true";
		} catch (Exception e) {
			LOGGER.error("Erro ao atualizar as informacoes", e);
			error("Erro ao atualizar as informacoes: " + e.getMessage());
			return "listarTurma?faces-redirect=true";
		}
	}

	public String cancelarAtualizar() {
		this.turma.restaurar(this.turmaAnterior);
		this.turmaAnterior = new Turma();
		return "listarAluno?faces-redirect=true";
	}

	public String buscarTodos() {
		init();
		LOGGER.info("Buscando turmas...");
		this.turmas = this.dao.findAll();
		if (!ListUtil.isValid(this.turmas)){
			LOGGER.warn("Nenhuma turma encontrada");
			warn("Nenhuma turma encontrada");
		}
		return "listarTurma?faces-redirect=true";
	}

	public String cancelarSalvar() {
		init();
		return "Inicio?faces-redirect=true";
	}

	public String buscarPorStatus(String status) {
		this.turmas = dao.findByStatus(status);
		if (!ListUtil.isValid(this.turmas)){
			LOGGER.warn("Nao existem turmas ativas");
			warn("Nao existem turmas ativas");
		}
		return "listarTurmas?faces-redirect=true";
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

	public List<Aluno> getAlunos() {
		return alunos;
	}

	public void setAlunos(List<Aluno> alunos) {
		this.alunos = alunos;
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
