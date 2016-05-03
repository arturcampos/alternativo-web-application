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

	public void salvar(Pessoa pessoa) {
		this.dao = new PessoaDao(Pessoa.class);
		this.dao.save(pessoa);
		this.pessoaLista.add(pessoa);
		pessoa = new Pessoa();

	}

	public void buscarPorId(Long id) {
		this.pessoa = this.dao.findById(id);
	}

	public String remover() {
		this.pessoa = this.dao.remove(this.pessoa.getId());
		return "removido";
	}

	public void atualizar(Pessoa pessoa) {
		this.pessoaAnterior = pessoa.clone();
		this.pessoa = pessoa;
		this.editado = true;
	}

	public String salvarAtualizar() {
		this.dao.update(pessoa);
		this.pessoa = new Pessoa();
		this.editado = false;
		return "salvo";
	}

	public String cancelarAtualizar() {
		this.pessoa.restaurar(this.pessoaAnterior);
		this.pessoa = new Pessoa();
		editado = false;
		return "cancelado";
	}

	public void buscarTodos() {
		pessoaLista = this.dao.findAll();
	}

	public void limparPessoa() {
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
