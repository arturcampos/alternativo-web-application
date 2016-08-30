/**
 *
 */
package app.control;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;

import app.dao.InfracaoDAO;
import app.dao.TipoInfracaoDAO;
import app.model.Aluno;
import app.model.Infracao;
import app.model.TipoInfracao;

/**
 * @author artur
 *
 */

@ManagedBean
@SessionScoped
public class InfracaoBean implements Serializable{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private InfracaoDAO infracaoDao;
	private Infracao infracao;
	private List<Infracao> infracoes;
	private TipoInfracaoDAO tipoInfracaoDao;
	private TipoInfracao tipoInfracao;
	private List<TipoInfracao> tiposInfracao;
	private Aluno aluno;
	private static Logger LOGGER = Logger.getLogger(InfracaoBean.class);

	@PostConstruct
	public void init(){
		LOGGER.info("Inicializando infracaoBean...");
		infracaoDao = new InfracaoDAO(Infracao.class);
		tipoInfracaoDao = new TipoInfracaoDAO(TipoInfracao.class);
		tipoInfracao = new TipoInfracao();
		infracao = new Infracao();
		infracoes = new ArrayList<Infracao>();
		tiposInfracao = new ArrayList<TipoInfracao>();
	}

	public String salvarInfracao(){
		LOGGER.info("Criando infracao para o aluno de matrícula: " + aluno.getMatricula());
		try{
			LOGGER.info("Adicionando parametros necessários à infracao");
			infracao.setAluno(aluno);
			infracao.setTipoInfracao(tipoInfracao);
			aluno.addInfracao(infracao);
			LOGGER.info("Salvando a infracao na base de dados");
			infracaoDao.save(infracao);
			LOGGER.info("Sucesso");
			info("Sucesso");
			init();
		}catch( Exception e){
			LOGGER.error("Erro ao criar infracao", e);
			error("Erro ao criar infracao: " + e.getMessage());
		}
		return "criarInfracao?faces-redirect=true";
	}


	public String salvarTipoInfracao(){

		try{
			tipoInfracaoDao.save(tipoInfracao);
			LOGGER.info("Tipo de infracao cadastrado com sucesso!");
			info("Tipo de infracao cadastrado com sucesso!");
		}catch(Exception e){
			LOGGER.error("Erro ao salvar tipo de infracao", e);
			info("Erro ao salvar tipo de infracao: " + e.getMessage());
		}

		return "salvarTipoInfracao?faces-redirect=true";
	}

	/**
	 * @return the infracaoDao
	 */
	public InfracaoDAO getInfracaoDao() {
		return infracaoDao;
	}

	/**
	 * @param infracaoDao the infracaoDao to set
	 */
	public void setInfracaoDao(InfracaoDAO infracaoDao) {
		this.infracaoDao = infracaoDao;
	}

	/**
	 * @return the infracao
	 */
	public Infracao getInfracao() {
		return infracao;
	}

	/**
	 * @param infracao the infracao to set
	 */
	public void setInfracao(Infracao infracao) {
		this.infracao = infracao;
	}

	/**
	 * @return the infracoes
	 */
	public List<Infracao> getInfracaos() {
		return infracoes;
	}

	/**
	 * @param infracoes the infracoes to set
	 */
	public void setInfracaos(List<Infracao> infracoes) {
		this.infracoes = infracoes;
	}

	/**
	 * @return the tipoInfracaoDao
	 */
	public TipoInfracaoDAO getTipoInfracaoDao() {
		return tipoInfracaoDao;
	}

	/**
	 * @param tipoInfracaoDao the tipoInfracaoDao to set
	 */
	public void setTipoInfracaoDao(TipoInfracaoDAO tipoInfracaoDao) {
		this.tipoInfracaoDao = tipoInfracaoDao;
	}

	/**
	 * @return the tipoInfracao
	 */
	public TipoInfracao getTipoInfracao() {
		return tipoInfracao;
	}

	/**
	 * @param tipoInfracao the tipoInfracao to set
	 */
	public void setTipoInfracao(TipoInfracao tipoInfracao) {
		this.tipoInfracao = tipoInfracao;
	}

	/**
	 * @return the tiposInfracaos
	 */
	public List<TipoInfracao> getTiposInfracao() {
		return tiposInfracao;
	}

	/**
	 * @param tiposInfracaos the tiposInfracaos to set
	 */
	public void setTiposInfracao(List<TipoInfracao> tiposInfracao) {
		this.tiposInfracao = tiposInfracao;
	}

	public static Logger getLOGGER() {
		return LOGGER;
	}

	public static void setLOGGER(Logger lOGGER) {
		LOGGER = lOGGER;
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
