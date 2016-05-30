package app.control;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import app.dao.DocumentoDao;
import app.model.Documento;
import app.model.Pessoa;

@ManagedBean(name = "documentoBean")
@SessionScoped
public class DocumentoBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private DocumentoDao dao;
	private Documento documento;
	private List<Documento> documentos;
	private Pessoa pessoa;
	private Documento documentoAnterior;
	private boolean editado;

	@PostConstruct
	public void init() {
		if (dao == null) {
			dao = new DocumentoDao(Documento.class);
		}
		this.documento = new Documento();
		this.documentos = new ArrayList<Documento>();
		this.pessoa = new Pessoa();
	}

	public String buscarPorId(Long id) {
		this.documento = this.dao.findById(id);
		if (this.documento != null) {
			info("Sucesso");
		} else {
			warn("Documetno não encotnrado");
		}
		return "listarDocumento?faces-redirect=true";
	}

	public Documento remover(Long id) {
		return this.dao.remove(id);
	}

	/**
	 *
	 * @param documento
	 * @return
	 */
	public String atualizar(Documento documento) {
		try {
			this.documento = documento.clone();
			this.documentoAnterior = documento.clone();
			this.editado = true;
			return "atualizarDocumento?faces-redirect=true";
		} catch (Exception e) {
			error("Erro ao direcioar para atualização de dados do documento");
			return "listarDocumento?faces-redirect=true";
		}
	}

	/**
	 *
	 * @return
	 */
	public String salvarAtualizar() {
		try {
			this.dao.update(this.documento);
			this.documentos.remove(documentoAnterior);
			this.editado = false;
			info("Documento " + this.documento.getTipo() + " " + this.documento.getNumero() + " atualizados");
			this.documento = new Documento();
			this.documentoAnterior = new Documento();
			return "listarDocumentos?faces-redirect=true";
		} catch (Exception e) {
			error("Erro ao atualizar as informações!");
			return "atualizarDocumentos?faces-redirect=true";
		}
	}

	public List<Documento> buscarTodos() {
		return this.dao.findAll();
	}

	/**
	 *
	 * @param pessoa
	 * @return
	 */
	public String atualizarDocumentos(Pessoa pessoa) {
		System.out.println("atualizar documentos\n");
		System.out.println(pessoa.toString());
		this.pessoa = pessoa;
		this.documentos = dao.findByPersonId(pessoa.getId());

		if (this.documentos.isEmpty() || this.documentos == null) {
			warn("Nenhum documento encontrado para " + pessoa.getNome());
		}
		return "listarDocumentos?faces-redirect=true";
	}

	public DocumentoDao getDao() {
		return dao;
	}

	public void setDao(DocumentoDao dao) {
		this.dao = dao;
	}

	public Documento getDocumento() {
		return documento;
	}

	public void setDocumento(Documento documento) {
		this.documento = documento;
	}

	public List<Documento> getDocumentos() {
		return documentos;
	}

	public void setDocumentos(List<Documento> documentos) {
		this.documentos = documentos;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public Documento getDocumentoAnterior() {
		return documentoAnterior;
	}

	public void setDocumentoAnterior(Documento documentoAnterior) {
		this.documentoAnterior = documentoAnterior;
	}

	public boolean isEditado() {
		return editado;
	}

	public void setEditado(boolean editado) {
		this.editado = editado;
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
