package app.control;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import app.dao.AlunoDao;
import app.model.Aluno;
import app.model.Documento;
import app.model.Endereco;
import app.model.Pessoa;
import app.util.Status;
import app.util.TipoPessoa;

@ManagedBean
@SessionScoped
public class AlunoBean {

	private AlunoDao dao;
	private Aluno aluno;
	private Pessoa pessoa;
	private List<Documento> documentos;
	private Documento documento;
	private List<Endereco> enderecos;
	private Endereco endereco;
	private Aluno alunoAnterior = null;
	private List<Aluno> alunoLista;
	private boolean editado;

	@PostConstruct
	public void init() {
		if (this.dao == null) {
			this.dao = new AlunoDao(Aluno.class);
		}
		this.dao = new AlunoDao(Aluno.class);
		this.alunoLista = new ArrayList<Aluno>();
		this.aluno = new Aluno();
		this.pessoa = new Pessoa();
		this.documentos = new ArrayList<Documento>();
		this.setDocumento(new Documento());
		this.enderecos = new ArrayList<Endereco>();
		this.setEndereco(new Endereco());
	}

	public String salvar(Aluno aluno) {
		try {

			this.pessoa.setTipopessoa(TipoPessoa.ALUNO.toString());

			// adicionando documentos � pessoa
			this.pessoa.setDocumentos(this.documentos);

			// adicionando endere�os � pessoa
			this.pessoa.setEnderecos(this.enderecos);

			// alterando status para ativo
			this.aluno.setStatus(Status.ATIVO.toString());
			// adicionando pessoa � alunos
			this.aluno.setPessoa(this.pessoa);

			// 
			// executando metod DAO para salvar aluno
			this.dao.save(aluno);

			this.alunoLista.add(aluno);
			aluno = new Aluno();
			info("Informações salvas com sucesso");
			return "salvar";
		} catch (Exception e) {
			error("Erro ao Salvar informações: " + e.getMessage());
			return "salvar";
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
			return "atualizar";
		}
	}

	public String remover(Long id) {
		try {
			this.aluno = this.dao.remove(id);
			if (!this.alunoLista.isEmpty() && this.alunoLista != null) {
				this.alunoLista.remove(this.aluno);
				info("Aluno removido: " + this.aluno.getPessoa().getNome());
				this.aluno = new Aluno();
			} else {
				warn("Houve um problema para remover o aluno, verifique na listagem");
			}
			return "listar";
		} catch (Exception e) {

		}
		return "listar";
	}

	public String adicionarDocumento() {
		try{
			if (this.documentos == null) {
				this.documentos = new ArrayList<Documento>();
			}

			this.documentos.add(this.documento);
			info("Documento "+ documento.getNumero() +" adicionado!");
			this.documento = new Documento();
			
		}catch(Exception e){
			error("Erro ao adicionar documento à lista");
		}
		
		
		return null;
	}
	
	public String removerDocumento(Documento documento){
		if((this.documentos != null) && (!this.documentos.isEmpty())){
			this.documentos.remove(documento);
			info("Documento removido com sucesso!");
		}
		else{
			warn("Não existem documentos à serem removidos");
		}
		
		
		
		return null; 	
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
			info("Dados de " + this.aluno.getPessoa().getNome()
					+ " atualizados");
			this.aluno = new Aluno();
			return "Inicio";
		} catch (Exception e) {
			error("Erro ao atualizar as informa��es!");
			return "atualizar";
		}
	}

	public void cancelarAtualizar() {
		this.aluno.restaurar(this.alunoAnterior);
		this.aluno = new Aluno();
		editado = false;
	}

	public void buscarTodos() {
		this.alunoLista = this.dao.findAll();
	}

	public void limparAluno() {
		this.aluno.setDataegresso(null);
		this.aluno.setDataingresso(null);
		this.aluno.setMatricula(null);
		this.aluno.setPessoa(null);
		this.aluno.setTipocotaingresso(-1);
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
		return alunoLista;
	}

	public void setAlunos(List<Aluno> alunos) {
		this.alunoLista = alunos;
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

	public void info(String message) {
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", message));
	}

	public void warn(String message) {
		FacesContext.getCurrentInstance().addMessage(
				null,
				new FacesMessage(FacesMessage.SEVERITY_WARN, "Warning!",
						message));
	}

	public void error(String message) {
		FacesContext.getCurrentInstance()
				.addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!",
								message));
	}

	public void fatal(String message) {
		FacesContext.getCurrentInstance()
				.addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_FATAL, "Fatal!",
								message));
	}

}
