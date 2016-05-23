package app.control;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import app.dao.DocumentoDao;
import app.model.Aluno;
import app.model.Documento;
import app.model.Professor;

@ManagedBean(name = "documentoBean")
@ViewScoped
public class DocumentoBean {
	private DocumentoDao dao;
	private Documento documento;
	private List<Documento> documentos;
	private Aluno aluno;
	private Professor professor;
	private Documento documentoAnterior;
	private boolean editado;

	@PostConstruct
	public void init() {
		if (dao == null) {
			dao = new DocumentoDao(Documento.class);
		}
		this.documento = new Documento();
		this.documentos = new ArrayList<Documento>();
		this.setAluno(new Aluno());
		this.setProfessor(new Professor());
	}

	public String buscarPorId(Long id) {
		this.documento = this.dao.findById(id);
		if(this.documento != null){
			info("Sucesso");
		}
		else{
			warn("Documetno não encotnrado");
		}
		return "listarDocumento?faces-redirect=true"; 
	}

	public Documento remover(Long id) {
		return this.dao.remove(id);
	}

	public String atualizar(Documento documento) {
		try {
			this.documento = documento.clone();
			this.documento = documento.clone();
			this.setEditado(true);
			return "atualizarDocumento?faces-redirect=true";
		} catch (Exception e) {
			error("Erro ao direcioar para atualização de dados do documento");
			return "listarDocumento?faces-redirect=true";
		}
	}

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

	public String listarDocumentos(Aluno aluno, Professor professor) {
		if (this.aluno != null) {
			this.aluno = aluno;
			Long id = aluno.getPessoa().getId();
			this.documentos = this.dao.findByPersonId(id);
		} else if (professor != null) {
			long id = professor.getPessoa().getId();
			this.documentos = this.dao.findByPersonId(id);
			this.professor = professor;
		} else {
			warn("Nenhum documento encontrado para os parametros enviados.");
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

	public Aluno getAluno() {
		return aluno;
	}

	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}

	public Professor getProfessor() {
		return professor;
	}

	public void setProfessor(Professor professor) {
		this.professor = professor;
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
