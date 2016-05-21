package app.control;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import app.dao.AlunoDao;
import app.model.Aluno;
import app.model.Documento;
import app.model.Endereco;
import app.model.Pessoa;
import app.util.AlunoUtil;
import app.util.Status;
import app.util.TipoPessoa;

@ManagedBean
@SessionScoped
public class AlunoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private AlunoDao dao;
	private Aluno aluno;
	private Pessoa pessoa;
	private List<Documento> documentos;
	private Documento documento;
	private List<Endereco> enderecos;
	private Endereco endereco;
	private Aluno alunoAnterior = null;
	private List<Aluno> alunos;
	private boolean editado;
	private String alunoTab;
	private String documentoTab;
	private String enderecoTab;

	/**
	 * 
	 */
	@PostConstruct
	public void init() {
		System.out.println("init");
		if (this.dao == null) {
			this.dao = new AlunoDao(Aluno.class);
		}
		this.dao = new AlunoDao(Aluno.class);
		this.alunos = new ArrayList<Aluno>();
		this.aluno = new Aluno();
		this.pessoa = new Pessoa();
		this.documentos = new ArrayList<Documento>();
		this.setDocumento(new Documento());
		this.enderecos = new ArrayList<Endereco>();
		this.endereco = new Endereco();
		this.documento = new Documento();
		this.setAlunoTab("active");
		this.setEnderecoTab("");
		this.setDocumentoTab("");
	}
	
	
	/**
	 * 
	 * @return
	 */
	public String salvar() {
		try {

			this.pessoa.setTipopessoa(TipoPessoa.ALUNO.toString());

			// adicionando documentos à pessoa
			this.pessoa.setDocumentos(this.documentos);
			vincularDocumento(this.pessoa, this.documentos);

			// adicionando endereço pessoa
			this.pessoa.setEnderecos(this.enderecos);
			vincularEndereco(this.pessoa, this.enderecos);

			// alterando status para ativo
			this.aluno.setStatus(Status.ATIVO.toString());

			// Gerando matricula e atribuindo ao aluno
			String matricula = AlunoUtil.GerarMatricula();
			this.aluno.setMatricula(matricula);

			// adicionando pessoa à alunos
			this.aluno.setPessoa(this.pessoa);

			// executando metod DAO para salvar aluno
			this.dao.save(this.aluno);

			this.alunos.add(this.aluno);
			info("Informações salvas com sucesso.\n" + "Nome: " + this.pessoa.getNome() + "\n" + "Matricula: "
					+ this.aluno.getMatricula());
			init();
			return "salvarAluno?faces-redirect=true";
		} catch (Exception e) {
			error("Erro ao Salvar informações: " + e.getMessage());
			return "salvarAluno?faces-redirect=true";
		}
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	public String buscarPorId(Long id) {
		try {

			this.aluno = this.dao.findById(id);
			if (this.aluno != null) {
				info("Aluno encontrado: " + this.aluno.getPessoa().getNome());
			} else {
				warn("Aluno não encontrado!");
			}
			return "atualizar";
		} catch (Exception e) {
			error("Erro ao consultar dados do aluno!");
			return "atualizarAluno";
		}
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	public String remover(Long id) {
		try {
			this.aluno = this.dao.findById(id);
			if (this.alunos != null && !this.alunos.isEmpty()) {
				this.aluno.setStatus(Status.INATIVO.toString());
				this.dao.update(this.aluno);
				this.alunos.remove(this.aluno);
				info("Aluno removido: " + this.aluno.getPessoa().getNome());
				this.aluno = new Aluno();
			} else {
				warn("Houve um problema para remover o aluno, verifique na listagem");
			}
			return "listarAluno";
		} catch (Exception e) {
			error("Houve um problema para remover o aluno, verifique na listagem");
		}
		return "listar";
	}

	/**
	 * 
	 * @param actionEvent
	 */
	public void adicionarDocumento(ActionEvent actionEvent) {
		try {
			if (this.documentos == null) {
				this.documentos = new ArrayList<Documento>();
			}
			Documento doc = this.documento.clone();
			this.documentos.add(doc);
			info("Documento " + documento.getTipo() + " " + documento.getNumero() + " adicionado!");
			this.documento = new Documento();

			this.alunoTab = "";
			this.documentoTab = "active";
			this.enderecoTab = "";
		} catch (Exception e) {
			error("Erro ao adicionar documento à lista");
		}
	}

	/**
	 * 
	 * @param documento
	 */
	public void removerDocumento(final Documento documento) {

		if ((this.documentos != null) && (!this.documentos.isEmpty())) {
			this.documentos.remove(documento);
			info("Documento removido com sucesso!");
		} else {
			warn("Não existem documentos à serem removidos");
		}
	}

	/**
	 * 
	 * @param actionEvent
	 */
	public void adicionarEndereco(ActionEvent actionEvent) {
		try {
			if (this.enderecos == null) {
				this.enderecos = new ArrayList<Endereco>();
			}
			Endereco end = this.endereco.clone();
			this.enderecos.add(end);
			info("Endereco adicionado!");
			this.endereco = new Endereco();
			this.alunoTab = "";
			this.documentoTab = "";
			this.enderecoTab = "active";

		} catch (Exception e) {
			error("Erro ao adicionar endereco à lista");
		}
	}

	/**
	 * 
	 * @param endereco
	 */
	public void removerEndereco(final Endereco endereco) {

		if ((this.enderecos != null) && (!this.enderecos.isEmpty())) {
			this.enderecos.remove(endereco);
			info("Endereço removido com sucesso!");
		} else {
			warn("Não existem endereços à serem removidos");
		}
	}

	/**
	 * 
	 * @param p
	 * @param docs
	 */
	private void vincularDocumento(Pessoa p, List<Documento> docs) {
		for (Documento d : docs) {
			d.setPessoa(p);
		}
	}

	/**
	 * 
	 * @param p
	 * @param ends
	 */
	private void vincularEndereco(Pessoa p, List<Endereco> ends) {
		for (Endereco e : ends) {
			e.setPessoa(p);
		}
	}

	/**
	 * 
	 * @param aluno
	 * @return
	 */
	public String atualizar(Aluno aluno) {
		try {
			this.aluno = aluno.clone();
			this.alunoAnterior = aluno.clone();
			this.editado = true;
			return "atualizarAluno?faces-redirect=true";
		} catch (Exception e) {
			error("Erro ao direcioar para atualização de dados do aluno");
			return "listarAluno?faces-redirect=true";
		}
	}

	/**
	 * 
	 * @return
	 */
	public String salvarAtualizar() {
		try {
			this.dao.update(this.aluno);
			this.editado = false;
			info("Dados de " + this.aluno.getPessoa().getNome() + " atualizados");
			this.aluno = new Aluno();
			return "Inicio";
		} catch (Exception e) {
			error("Erro ao atualizar as informações!");
			return "atualizar";
		}
	}

	/**
	 * 
	 */
	public void cancelarAtualizar() {
		this.aluno.restaurar(this.alunoAnterior);
		this.aluno = new Aluno();
		editado = false;
	}

	/**
	 * 
	 */
	public void buscarTodos() {
		this.alunos = this.dao.findAll();
	}

	/**
	 * 
	 * @param status
	 * @return
	 */
	public String buscarTodosPorStatus(String status) {
		System.out.println(status);
		String retorno = null;
		try {
			if (status.equals(Status.ATIVO.toString()) || status.equals(Status.INATIVO.toString())) {
				this.alunos = this.dao.findByStatus(status);
				retorno = "listarAluno?faces-redirect=true";
			} else {
				error("Status " + status + "inválido para consulta");
			}

		} catch (Exception e) {
			error("Erro ao consultar dados: " + e.getMessage());
		}
		return retorno;
	}

	/**
	 * 
	 * @param matricula
	 * @return
	 */
	public String buscarPorMatricula(String matricula) {
		try {
			System.out.println(matricula);
			if (this.alunos == null) {
				this.alunos = new ArrayList<Aluno>();
			}
			Aluno aluno = this.dao.findByRegistrationNumber(matricula);
			if(aluno != null){
				this.alunos.add(aluno);
			}
			else{
				warn("Matricula não existe.");
			}
		} catch (Exception e) {
			error("Erro ao consultar aluno - " + e.getMessage());
		}
		return "listarAluno?faces-redirect=true";
	}

	/**
	 * 
	 */
	public void limparAluno() {
		this.aluno.setDataEgresso(null);
		this.aluno.setDataIngresso(null);
		this.aluno.setMatricula(null);
		this.aluno.setPessoa(null);
		this.aluno.setTipoCotaIngresso(-1);
		this.aluno.setTurma(null);
		this.aluno.setId(null);
	}

	/**
	 * 
	 * @return
	 */
	public boolean isEditado() {
		return editado;
	}

	/**
	 * 
	 * @param editado
	 */
	public void setEditado(boolean editado) {
		this.editado = editado;
	}

	/**
	 * 
	 * @return
	 */
	public Aluno getAlunoAnterior() {
		return alunoAnterior;
	}

	/**
	 * 
	 * @param alunoAnterior
	 */
	public void setAlunoAnterior(Aluno alunoAnterior) {
		this.alunoAnterior = alunoAnterior;
	}

	/**
	 * 
	 * @return
	 */
	public AlunoDao getDao() {
		return dao;
	}

	/**
	 * 
	 * @param dao
	 */
	public void setDao(AlunoDao dao) {
		this.dao = dao;
	}

	/**
	 * 
	 * @return
	 */
	public Aluno getAluno() {
		return aluno;
	}

	/**
	 * 
	 * @param aluno
	 */
	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}
	
	/**
	 * 
	 * @return
	 */
	public List<Aluno> getAlunos() {
		return alunos;
	}

	/**
	 * 
	 * @param alunos
	 */
	public void setAlunos(List<Aluno> alunos) {
		this.alunos = alunos;
	}
	
	/**
	 * 
	 * @return
	 */
	public Pessoa getPessoa() {
		return pessoa;
	}

	/**
	 * 
	 * @param pessoa
	 */
	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	/**
	 * 
	 * @return
	 */
	public List<Documento> getDocumentos() {
		return documentos;
	}

	/**
	 * 
	 * @param documentos
	 */
	public void setDocumentos(List<Documento> documentos) {
		this.documentos = documentos;
	}

	/**
	 * 
	 * @return
	 */
	public List<Endereco> getEnderecos() {
		return enderecos;
	}

	/**
	 * 
	 * @param enderecos
	 */
	public void setEnderecos(List<Endereco> enderecos) {
		this.enderecos = enderecos;
	}

	/**
	 * 
	 * @return
	 */
	public Documento getDocumento() {
		return documento;
	}

	/**
	 * 
	 * @param documento
	 */
	public void setDocumento(Documento documento) {
		this.documento = documento;
	}

	/**
	 * 
	 * @return
	 */
	public Endereco getEndereco() {
		return endereco;
	}

	/**
	 * 
	 * @param endereco
	 */
	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	/**
	 * 
	 * @return
	 */
	public String getAlunoTab() {
		return alunoTab;
	}
	/**
	 * 
	 * @param alunoTab
	 */
	public void setAlunoTab(String alunoTab) {
		this.alunoTab = alunoTab;
	}

	/**
	 * 
	 * @return
	 */
	public String getDocumentoTab() {
		return documentoTab;
	}

	/**
	 * 
	 * @param documentoTab
	 */
	public void setDocumentoTab(String documentoTab) {
		this.documentoTab = documentoTab;
	}

	/**
	 * 
	 * @return
	 */
	public String getEnderecoTab() {
		return enderecoTab;
	}

	/**
	 * 
	 * @param enderecoTab
	 */
	public void setEnderecoTab(String enderecoTab) {
		this.enderecoTab = enderecoTab;
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