package app.control;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import app.dao.PessoaDao;
import app.model.Pessoa;

@ManagedBean
@RequestScoped
public class PessoaController {

	private PessoaDao dao;
	private Pessoa pessoa;
	private Pessoa pessoaAnterior = null;
	private List<Pessoa> pessoaLista;
	private boolean editado;

	@PostConstruct
	public void init() {
		if (this.dao == null) {
			this.dao = new PessoaDao(Pessoa.class);
		}
		buscarTodos();
		this.pessoa = new Pessoa();
	}

	public String salvar() {
		this.dao.save(this.pessoa);
		this.pessoaLista.add(this.pessoa);
		pessoa = new Pessoa();
		return "listar";

	}

	public String buscarPorId(Long id) {
		this.pessoa = this.dao.findById(id);
		return "atualizar";
	}

	public String remover() {
		this.pessoa = this.dao.remove(this.pessoa.getId());
		return "listar";
	}

	public String atualizar(Pessoa pessoa) {
		this.pessoa = pessoa;
		this.pessoaAnterior = pessoa.clone();
		//this.pessoa = pessoa;
		this.editado = true;
		return "atualizar";
	}

	public String salvarAtualizar() {
		this.dao.update(pessoa);
		//this.pessoa = new Pessoa();
		this.editado = false;
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
		this.pessoa.setTipopessoas("");
		return "salvar";
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
}
