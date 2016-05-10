package app.control;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import app.dao.PessoaDao;
import app.model.Pessoa;

public class PessoaBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private PessoaDao dao = null;
	private Pessoa pessoa;
	private Pessoa pessoaAnterior = null;
	private List<Pessoa> pessoaLista;
	private boolean editado;
	private String msg = null;

	@PostConstruct
	public void init() {
		if (this.dao == null) {
			this.dao = new PessoaDao(Pessoa.class);
		}
		this.pessoaLista = new ArrayList<Pessoa>();
		this.pessoa = new Pessoa();
	}

	public String salvar(){
		System.out.println(this.pessoa.toString());
		
		try{
			this.dao.save(this.pessoa);
			this.pessoaLista.add(this.pessoa);
			pessoa = new Pessoa();
			System.out.println("Passei aqui");
			this.msg = "Informações salvas com sucesso";
		}catch(Exception e){
			this.msg = "Erro ao Salvar informações";
		}
		
		return "Inicio";

	}

	public String buscarPorId(Long id) {
		this.pessoa = this.dao.findById(id);
		return "atualizar";
	}

	public String remover(Long id) {
		this.pessoa = this.dao.remove(id);
		return "Inicio";
	}

	public String atualizar(Pessoa pessoa) {
		this.pessoa = pessoa.clone();
		this.pessoaAnterior = pessoa.clone();
		pessoa = new Pessoa();
		this.editado = true;
		return "atualizar";
	}

	public String salvarAtualizar() {
		this.dao.update(pessoa);
		// this.pessoa = new Pessoa();
		this.editado = false;
		this.pessoa = new Pessoa();
		return "Inicio";
	}

	public String cancelarAtualizar() {
		this.pessoa.restaurar(this.pessoaAnterior);
		this.pessoa = new Pessoa();
		editado = false;
		return "listar";
	}

	public String buscarTodos() {
		pessoaLista = this.dao.findAll();
		return "listar";
	}

	public String limparPessoa() {
		if (this.pessoa != null) {
			this.pessoa.setId(null);
			this.pessoa.setDatanasc(null);
			this.pessoa.setEmail("");
			this.pessoa.setEstadocivil("");
			this.pessoa.setEtnia("");
			this.pessoa.setNacionalidade("");
			this.pessoa.setNaturalidade("");
			this.pessoa.setNecessidadesespeciais("");
			this.pessoa.setNome("");
			this.pessoa.setNomemae("");
			this.pessoa.setNomepai("");
			this.pessoa.setNumerocelular("");
			this.pessoa.setResponsavellegal("");
			this.pessoa.setSexo("");
			this.pessoa.setUf("");
			this.pessoa.setDocumentos(null);
			this.pessoa.setEnderecos(null);
			this.pessoa.setPlasticos(null);
			this.pessoa.setTipopessoa("");
		}
		return "Inicio";
	}

	public boolean isEditado() {
		return editado;
	}

	public void setEditado(boolean editado) {
		this.editado = editado;
	}

	public Pessoa getPessoaAnterior() {
		return pessoaAnterior;
	}

	public void setPessoaAnterior(Pessoa pessoaAnterior) {
		this.pessoaAnterior = pessoaAnterior;
	}

	public PessoaDao getDao() {
		return dao;
	}

	public void setDao(PessoaDao dao) {
		this.dao = dao;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public List<Pessoa> getPessoas() {
		return pessoaLista;
	}

	public void setPessoas(List<Pessoa> pessoas) {
		this.pessoaLista = pessoas;
	}

	public String getMsgErro() {
		return msg;
	}

	public void setMsg(String mensagemErro) {
		this.msg = mensagemErro;
	}
}
