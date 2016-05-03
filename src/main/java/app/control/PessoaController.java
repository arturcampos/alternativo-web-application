package app.control;

import java.util.ArrayList;
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
		this.pessoaLista = new ArrayList<Pessoa>();
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

	public void remover(Long id) {
		this.pessoa = this.dao.remove(id);
	}

	public void atualizar(Pessoa pessoa) {
		this.pessoaAnterior = pessoa.clone();
		this.pessoa = pessoa;
		this.editado = true;
	}

	public void salvarAtualizar() {
		this.dao = new PessoaDao(Pessoa.class);
		this.dao.update(pessoa);
		this.pessoa = new Pessoa();
		this.editado = false;
	}

	public void cancelarAtualizar() {
		this.pessoa.restaurar(this.pessoaAnterior);
		this.pessoa = new Pessoa();
		editado = false;
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
