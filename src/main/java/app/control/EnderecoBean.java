package app.control;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import app.dao.EnderecoDao;
import app.model.Documento;
import app.model.Endereco;
import app.model.Pessoa;

@ManagedBean
@SessionScoped
public class EnderecoBean {
	private EnderecoDao dao;
	private Endereco endereco;
	private Endereco enderecoAnterior;
	private List<Endereco> enderecos;
	private Pessoa pessoa;
	private boolean editado;


	@PostConstruct
	public void init() {
		if(this.dao == null){
			this.dao = new EnderecoDao(Endereco.class);
		}
		this.endereco = new Endereco();
		this.enderecos = new ArrayList<Endereco>();
		this.pessoa = new Pessoa();

	}

	public void salvar(Endereco endereco) {
		this.dao.save(endereco);
	}

	public Endereco buscarPorId(Long id) {
		return this.dao.findById(id);
	}

	public Endereco remover(Long id) {
		return this.dao.remove(id);
	}

	public String atualizar(Endereco endereco) {
		try {
			this.endereco = endereco.clone();
			this.enderecoAnterior.clone();
			this.editado = true;
			return "atualizarEndereco?faces-redirect=true";
		} catch (Exception e) {
			error("Erro ao direcioar para atualização de dados do endereco");
			return "listarEndereco?faces-redirect=true";
		}
	}

	public String salvarAtualizar() {
		try {
			this.dao.update(this.endereco);
			this.enderecos.remove(enderecoAnterior);
			this.enderecos.add(endereco);
			this.editado = false;
			info("Endereco atualizado.");
			this.endereco = new Endereco();
			this.enderecoAnterior = new Endereco();
			return "listarEndereco?faces-redirect=true";
		} catch (Exception e) {
			error("Erro ao atualizar as informações!");
			return "atualizarEndereco?faces-redirect=true";
		}
	}

	public List<Endereco> buscarTodos() {
		return this.dao.findAll();
	}

	public void atualizarEnderecos(Pessoa pessoa) {
		this.pessoa = pessoa;
		this.enderecos = pessoa.getEnderecos();
		if(this.enderecos.isEmpty() || this.enderecos == null){
			warn("Nenhum endereço encontrado para " + pessoa.getNome());
		}
	}
	public EnderecoDao getDao() {
		return dao;
	}

	public void setDao(EnderecoDao dao) {
		this.dao = dao;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public List<Endereco> getEnderecos() {
		return enderecos;
	}

	public void setEnderecos(List<Endereco> enderecos) {
		this.enderecos = enderecos;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

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
				new FacesMessage(FacesMessage.SEVERITY_WARN, " Atenção!", message));
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
