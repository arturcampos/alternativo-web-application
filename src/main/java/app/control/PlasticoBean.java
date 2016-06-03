package app.control;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.swing.text.DateFormatter;

import app.dao.PlasticoDao;
import app.model.Aluno;
import app.model.Pessoa;
import app.model.Plastico;
import app.model.Professor;
import app.util.Status;

@ManagedBean
@SessionScoped
public class PlasticoBean {
	private PlasticoDao dao;
	private Plastico plastico;
	private Plastico plasticoAnterior;
	private List<Plastico> plasticos;
	private Pessoa pessoa;
	private boolean editado;
	private String tipoPessoa;

	@PostConstruct
	public void init() {
		if (this.dao == null) {
			this.dao = new PlasticoDao(Plastico.class);
		}
		this.plastico = new Plastico();
		this.plasticoAnterior = new Plastico();
		this.pessoa = new Pessoa();
		this.plasticos = new ArrayList<Plastico>();
		this.editado = false;
		this.tipoPessoa = null;
	}

	public String criar(Aluno aluno, Professor professor) {
		String retorno = "salvarPlastico?faces-redirect=true";
		if (aluno != null) {
			this.pessoa = aluno.getPessoa();
		} else if (professor != null) {
			this.pessoa = professor.getPessoa();
		} else {
			warn("Não é possível cadastrar um cartão.\nDúvidas, entre em contato com o Administrador do sistema.");
			retorno = null;
		}
		this.tipoPessoa = this.pessoa.getTipopessoa();
		return retorno;
	}

	public String salvar() {
		String retorno = null;
		try {
			this.plastico.setPessoa(this.pessoa);
			this.plastico.setStatus(Status.ATIVO.toString());

			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			Date dataCadastro;
			dataCadastro = sdf.parse(new Date().toString());
			this.plastico.setDataCadastro(dataCadastro);
			this.dao.save(plastico);

			if (this.tipoPessoa.equals("ALUNO"))
				retorno = "listarAluno?faces-redirect=true";
			else if (this.tipoPessoa.equals("PROFESSOR"))
				retorno = "listarProfessor?faces-redirect=true";
			else
				retorno = null;

			info("Cartão cadastrado com sucesso.");
		} catch (Exception e) {
			error("Erro ao cadastrar dados do cartão: " + e.getMessage());
		}
		return retorno;
	}

	public Plastico buscarPorId(Long id) {
		return this.dao.findById(id);
	}

	public Plastico buscarPorPessoaId(Long id) {
		return this.dao.findByPersonId(id);
	}

	public Plastico remover(Long id) {
		return this.dao.remove(id);
	}

	public void atualizar(Plastico plastico) {
		this.dao.update(plastico);
	}

	public List<Plastico> buscarTodos() {
		return this.dao.findAll();
	}

	public PlasticoDao getDao() {
		return dao;
	}

	public void setDao(PlasticoDao dao) {
		this.dao = dao;
	}

	public Plastico getPlastico() {
		return plastico;
	}

	public void setPlastico(Plastico plastico) {
		this.plastico = plastico;
	}

	public List<Plastico> getPlasticos() {
		return plasticos;
	}

	public void setPlasticos(List<Plastico> plasticos) {
		this.plasticos = plasticos;
	}

	/**
	 * @return the plasticoAnterior
	 */
	public Plastico getPlasticoAnterior() {
		return plasticoAnterior;
	}

	/**
	 * @param plasticoAnterior
	 *            the plasticoAnterior to set
	 */
	public void setPlasticoAnterior(Plastico plasticoAnterior) {
		this.plasticoAnterior = plasticoAnterior;
	}

	/**
	 * @return the editado
	 */
	public boolean isEditado() {
		return editado;
	}

	/**
	 * @param editado
	 *            the editado to set
	 */
	public void setEditado(boolean editado) {
		this.editado = editado;
	}

	/**
	 * @return the pessoa
	 */
	public Pessoa getPessoa() {
		return pessoa;
	}

	/**
	 * @param pessoa
	 *            the pessoa to set
	 */
	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
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
				new FacesMessage(FacesMessage.SEVERITY_WARN, " Atenï¿½ï¿½o!", message));
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
