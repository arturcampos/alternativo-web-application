package app.control;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.primefaces.model.DefaultStreamedContent;

import app.dao.AlunoDao;
import app.model.Aluno;
import app.model.Documento;
import app.model.Endereco;
import app.model.Pessoa;
import app.model.Plastico;
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
	private Plastico plastico;
	private DefaultStreamedContent reportStream;

	@ManagedProperty(value = "#{documentoBean}")
	private DocumentoBean documentoBean;

	@ManagedProperty(value = "#{plasticoBean}")
	private PlasticoBean plasticoBean;

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
		this.enderecos = new ArrayList<Endereco>();
		this.endereco = new Endereco();
		this.documento = new Documento();
		this.alunoTab = "active";
		this.enderecoTab = "";
		this.documentoTab = "";
		this.plastico = new Plastico();
	}

	/**
	 *
	 * @return the action to go to save student after create in data base
	 */
	public String salvar() {
		try {
			if (this.documentos.size() > 0) {
				this.pessoa.setTipopessoa(TipoPessoa.ALUNO.toString());

				// adicionando documentos à pessoa
				this.pessoa.setDocumentos(this.documentos);
				vincularDocumento(this.pessoa, this.documentos);
			} else {
				// Se n�o houver documentos cadastrado retorna um aviso
				warn("É necessário cadastrar pelo menos 1 (UM) documento");
				return "salvarAluno?faces-redirect=true";
			}

			if (this.enderecos.size() > 0) {
				// adicionando endereço pessoa
				this.pessoa.setEnderecos(this.enderecos);
				vincularEndereco(this.pessoa, this.enderecos);
			} else {
				// Se n�o houver endereços cadastrado retorna um aviso
				warn("É necessário cadastrar pelo menos 1 (UM) endereço");
				return "salvarAluno?faces-redirect=true";
			}
			// alterando status para ativo
			this.aluno.setStatus(Status.ATIVO.toString());

			// Gerando matricula e atribuindo ao aluno
			String matricula = AlunoUtil.GerarMatricula();
			this.aluno.setMatricula(matricula);

			// Converte data e cria plástico
			Date dataCadastro;
			try {
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				String formatado = sdf.format(new Date());
				dataCadastro = sdf.parse(formatado);
			} catch (ParseException e) {
				dataCadastro = new Date();
			}
			criarPlastico(matricula, dataCadastro);

			// adicionando plástico à pessoa
			this.pessoa.addPlastico(plastico);

			// adicionando pessoa ao aluno
			this.aluno.setPessoa(this.pessoa);

			// executando metod DAO para salvar aluno
			this.dao.save(this.aluno);
			Aluno novoAluno = this.aluno;

			info("Informa��es salvas com sucesso.\n" + "Nome: " + this.pessoa.getNome() + "\n" + "Matricula: "
					+ this.aluno.getMatricula());
			init();
			this.alunos.add(novoAluno);
			return "listarAluno?faces-redirect=true";

		} catch (Exception e) {
			error("Erro ao Salvar informações: " + e.getMessage());
			return "salvarAluno?faces-redirect=true";
		}
	}

	public String cancelarSalvar() {
		init();
		return "Inicio?faces-redirect=true";
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
				warn("Aluno n�o encontrado!");
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
	 * @return the action to go to save student
	 */
	public String adicionarDocumento() {
		try {
			if (this.documentos == null) {
				this.documentos = new ArrayList<Documento>();
			}
			Documento doc = this.documento.clone();
			this.documentos.add(doc);
			this.documento = new Documento();
			this.alunoTab = "";
			this.documentoTab = "active";
			this.enderecoTab = "";
		} catch (Exception e) {
			error("Erro ao adicionar documento � lista");
		}
		return "salvarAluno?faces-redirect=true";
	}

	/**
	 * 
	 * @return the action to go to save the student
	 */
	public String adicionarAluno() {
		this.alunoTab = "";
		this.alunoTab = "";
		this.documentoTab = "active";
		this.enderecoTab = "";
		return "salvarAluno?faces-redirect=true";
	}

	/**
	 *
	 * @param documento - to do removed
	 * @return the action to go to save the student
	 */
	public String removerDocumento(final Documento documento) {

		if ((this.documentos != null) && (!this.documentos.isEmpty())) {
			this.documentos.remove(documento);
			info("Documento removido com sucesso!");
			this.alunoTab = "";
			this.documentoTab = "active";
			this.enderecoTab = "";
		} else {

			warn("Não existe endereço à ser removido");
		}
		return "salvarAluno?faces-redirect=true";
	}

	/**
	 *
	 * @return the action to go to save student after add endereço
	 */

	public String adicionarEndereco() {
		try {
			if (this.enderecos == null) {
				this.enderecos = new ArrayList<Endereco>();
			}
			Endereco end = this.endereco.clone();
			this.enderecos.add(end);
			this.endereco = new Endereco();
			this.alunoTab = "";
			this.documentoTab = "";
			this.enderecoTab = "active";

		} catch (Exception e) {
			error("Erro ao adicionar endereco à lista");
			return "salvarAluno?faces-redirect=true";
		}

		return "salvarAluno?faces-redirect=true";
	}

	/**
	 *
	 * @param endereco - element de entrada
	 * @return the action to page to save the student
	 */
	public String removerEndereco(final Endereco endereco) {

		if ((this.enderecos != null) && (!this.enderecos.isEmpty())) {
			this.enderecos.remove(endereco);
			info("Endereço removido com sucesso!");
		} else {
			warn("Não existem endereços à serem removidos");
		}
		return "salvarAluno?faces-redirect=true";
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
	 * @param aluno - the student in
	 * @return the action to go to save ou list student
	 */
	public String atualizar(Aluno aluno) {
		try {
			this.aluno = aluno.clone();
			this.alunoAnterior = aluno.clone();
			this.editado = true;
			return "atualizarAluno?faces-redirect=true";
		} catch (Exception e) {
			error("Erro ao direcioar para atualiza��o de dados do aluno");
			return "listarAluno?faces-redirect=true";
		}
	}

	/**
	 *
	 * @return the action to go to save ou list student
	 */
	public String salvarAtualizar() {
		try {
			this.dao.update(this.aluno);
			this.alunos.remove(alunoAnterior);
			this.alunos.add(aluno);
			this.editado = false;
			info("Dados de " + this.aluno.getPessoa().getNome() + " atualizados");
			this.aluno = new Aluno();
			this.alunoAnterior = new Aluno();
			return "listarAluno?faces-redirect=true";
		} catch (Exception e) {
			error("Erro ao atualizar as informa��es!");
			return "atualizarAluno?faces-redirect=true";
		}
	}

	/**
	 *@return the action to go to list the student
	 */
	public String cancelarAtualizar() {
		this.aluno.restaurar(this.alunoAnterior);
		this.aluno = new Aluno();
		editado = false;
		return "listarAluno?faces-redirect=true";
	}

	/**
	 *
	 */
	public void buscarTodos() {
		this.alunos = this.dao.findAll();
	}

	/**
	 *
	 * @param status - student's status
	 * @return the action to go to list students
	 */
	public String buscarTodosPorStatus(String status) {
		try {
			if (status.equals(Status.ATIVO.toString()) || status.equals(Status.INATIVO.toString())) {
				this.alunos = this.dao.findByStatus(status);
			} else {
				error("Status " + status + "inv�lido para consulta");
			}

		} catch (Exception e) {
			error("Erro ao consultar dados: " + e.getMessage());
			;
		}
		return "listarAluno?faces-redirect=true";
	}

	/**
	 *
	 * @param matricula - student's registration number
	 * @return the action to go to list student
	 */
	public String buscarPorMatricula(String matricula) {
		try {
			if (this.alunos == null) {
				this.alunos = new ArrayList<Aluno>();
			} else {
				this.alunos.clear();
			}
			Aluno aluno = this.dao.findByRegistrationNumber(matricula);
			if (aluno != null) {
				this.aluno = aluno.clone();
				this.alunos.add(this.aluno);
				this.plastico = this.plasticoBean.buscarPorPessoaId(this.aluno.getPessoa().getId());
				info("Consulta realizada com sucesso");
			} else {
				warn("Matricula n�o existe.");
			}
		} catch (Exception e) {
			e.printStackTrace();
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
	 * @param matricula - the student's registration number
	 * @param dataCadastro - the studant's data of plastic creation
	 */
	public void criarPlastico(String matricula, Date dataCadastro) {
		this.plastico.setLinhaDigitavel(matricula);
		this.plastico.setDataCadastro(dataCadastro);
		this.plastico.setStatus(Status.ATIVO.toString());
	}

	/**
	 * 
	 * @return the action to go to Home
	 */
	public String voltar() {
		init();
		return "Inicio?faces-redirect=true";
	}

	/**
	 * 
	 * @return the action to go to list
	 */
	public String listar(){
		init();
		return "listarAluno?faces-redirect=true";
	}

	/**
	 *
	 * @return the value of editado variable
	 */
	public boolean isEditado() {
		return editado;
	}

	/**
	 *
	 * @param editado - value to add to editado variable
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

	public Plastico getPlastico() {
		return plastico;
	}

	public void setPlastico(Plastico plastico) {
		this.plastico = plastico;
	}

	/**
	 * @return the documentoBean
	 */
	public DocumentoBean getDocumentoBean() {
		return documentoBean;
	}

	/**
	 * @param documentoBean
	 *            the documentoBean to set
	 */
	public void setDocumentoBean(DocumentoBean documentoBean) {
		this.documentoBean = documentoBean;
	}

	/**
	 * @return the plasticoBean
	 */
	public PlasticoBean getPlasticoBean() {
		return plasticoBean;
	}

	/**
	 * @param plasticoBean
	 *            the plasticoBean to set
	 */
	public void setPlasticoBean(PlasticoBean plasticoBean) {
		this.plasticoBean = plasticoBean;
	}

	/**
	 * @return the reportStream
	 */
	public DefaultStreamedContent getReportStream() {
		return reportStream;
	}

	/**
	 * @param reportStream
	 *            the reportStream to set
	 */
	public void setReportStream(DefaultStreamedContent reportStream) {
		this.reportStream = reportStream;
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
				new FacesMessage(FacesMessage.SEVERITY_WARN, " Aten��o!", message));
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