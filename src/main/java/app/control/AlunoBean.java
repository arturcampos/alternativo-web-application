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

	public String salvar() {
		try {
			
			this.pessoa.setTipopessoa(TipoPessoa.ALUNO.toString());

			// adicionando documentos à pessoa
			this.pessoa.setDocumentos(this.documentos);

			// adicionando endereço pessoa
			this.pessoa.setEnderecos(this.enderecos);

			// alterando status para ativo
			this.aluno.setStatus(Status.ATIVO.toString());
			
			//Gerando matricula e atribuindo ao aluno
			String matricula = AlunoUtil.GerarMatricula();
			this.aluno.setMatricula(matricula);
			
			// adicionando pessoa à alunos
			this.aluno.setPessoa(this.pessoa);

			System.out.println(this.aluno.toString());
			System.out.println(this.pessoa.toString());
			// executando metod DAO para salvar aluno
			this.dao.save(aluno);

			this.alunos.add(aluno);
			init();
			info("Informações salvas com sucesso");
			return "salvarAluno?faces-redirect=true";
		} catch (Exception e) {
			error("Erro ao Salvar informações: " + e.getMessage());
			return "salvarAluno";
		}
	}

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

	public String remover(Long id) {
		try {
			this.aluno = this.dao.remove(id);
			if (this.alunos != null && !this.alunos.isEmpty()) {
				this.alunos.remove(this.aluno);
				info("Aluno removido: " + this.aluno.getPessoa().getNome());
				this.aluno = new Aluno();
			} else {
				warn("Houve um problema para remover o aluno, verifique na listagem");
			}
			return "listarAluno";
		} catch (Exception e) {

		}
		return "listar";
	}

	public void adicionarDocumento(ActionEvent actionEvent) {
		try{
			if (this.documentos == null) {
				this.documentos = new ArrayList<Documento>();
			}
			Documento doc = this.documento.clone();
			this.documentos.add(doc);
			info("Documento "+ documento.getTipo() + " "+ documento.getNumero() +" adicionado!");
			this.documento = new Documento();
			
			
			this.alunoTab = "";
			this.documentoTab = "active";
			this.enderecoTab = "";
		}catch(Exception e){
			error("Erro ao adicionar documento à lista");
		}
	}
	
	public void removerDocumento(final Documento documento) {
		
		if ((this.documentos != null) && (!this.documentos.isEmpty())) {
			this.documentos.remove(documento);
			info("Documento removido com sucesso!");
		} else {
			warn("Não existem documentos à serem removidos");
		}
	}

	public void adicionarEndereco(ActionEvent actionEvent) {
		try{
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
			
		}catch(Exception e){
			error("Erro ao adicionar endereco à lista");
		}
	}
	
public void removerEndereco(ActionEvent actionEvent, final Endereco endereco) {
		
		if ((this.enderecos != null) && (!this.enderecos.isEmpty())) {
			this.enderecos.remove(endereco);
			info("Endereço removido com sucesso!");
		} else {
			warn("Não existem endereços à serem removidos");
		}
	}
	
	public String atualizar(Aluno aluno) {
		try {
			this.aluno = aluno.clone();
			this.alunoAnterior = aluno.clone();
			this.editado = true;
			return "atualizar";
		} catch (Exception e) {
			return "atualizar";
		}
	}

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

	public void cancelarAtualizar() {
		this.aluno.restaurar(this.alunoAnterior);
		this.aluno = new Aluno();
		editado = false;
	}

	public void buscarTodos() {
		this.alunos = this.dao.findAll();
	}
	
	public String buscarPorMatricula(String matricula){
		try{
			if(this.alunos == null){
				this.alunos = new ArrayList<Aluno>();
			}
			this.alunos.add(this.dao.findByRegistrationNumber(matricula));
		}catch(Exception e){
			
		}
		return "listarAluno";
	}

	public void limparAluno() {
		this.aluno.setDataEgresso(null);
		this.aluno.setDataIngresso(null);
		this.aluno.setMatricula(null);
		this.aluno.setPessoa(null);
		this.aluno.setTipoCotaIngresso(-1);
		this.aluno.setTurma(null);
		this.aluno.setId(null);
	}

	public boolean isEditado() {
		return editado;
	}

	public void setEditado(boolean editado) {
		this.editado = editado;
	}

	public Aluno getAlunoAnterior() {
		return alunoAnterior;
	}

	public void setAlunoAnterior(Aluno alunoAnterior) {
		this.alunoAnterior = alunoAnterior;
	}

	public AlunoDao getDao() {
		return dao;
	}

	public void setDao(AlunoDao dao) {
		this.dao = dao;
	}

	public Aluno getAluno() {
		return aluno;
	}

	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}

	public List<Aluno> getAlunos() {
		return alunos;
	}

	public void setAlunos(List<Aluno> alunos) {
		this.alunos = alunos;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public List<Documento> getDocumentos() {
		return documentos;
	}

	public void setDocumentos(List<Documento> documentos) {
		this.documentos = documentos;
	}

	public List<Endereco> getEnderecos() {
		return enderecos;
	}

	public void setEnderecos(List<Endereco> enderecos) {
		this.enderecos = enderecos;
	}

	public Documento getDocumento() {
		return documento;
	}

	public void setDocumento(Documento documento) {
		this.documento = documento;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public String getAlunoTab() {
		return alunoTab;
	}

	public void setAlunoTab(String alunoTab) {
		this.alunoTab = alunoTab;
	}

	public String getDocumentoTab() {
		return documentoTab;
	}

	public void setDocumentoTab(String documentoTab) {
		this.documentoTab = documentoTab;
	}

	public String getEnderecoTab() {
		return enderecoTab;
	}

	public void setEnderecoTab(String enderecoTab) {
		this.enderecoTab = enderecoTab;
	}

	public void info(String message) {
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", message));
	}

	public void warn(String message) {
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_WARN, "Warning!", message));
	}

	public void error(String message) {
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", message));
	}

	public void fatal(String message) {
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_FATAL, "Fatal!", message));
	}

}