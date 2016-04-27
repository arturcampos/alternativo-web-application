package app.control;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import app.dao.DocumentoDao;
import app.model.Documento;

@ManagedBean
@SessionScoped
public class DocumentoController {
	DocumentoDao dao;
	Documento documento;
	List<Documento> documentos;

	@PostConstruct
	public void init() {
		dao = new DocumentoDao(Documento.class);
	}

	public void salvar(Documento documento) {
		this.dao.save(documento);
	}

	public Documento buscarPorId(Long id) {
		return this.dao.findById(id);
	}

	public Documento remover(Long id) {
		return this.dao.remove(id);
	}

	public void atualizar(Documento documento) {
		this.dao.update(documento);
	}

	public List<Documento> buscarTodos() {
		return this.dao.findAll();
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

}
