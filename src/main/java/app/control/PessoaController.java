package app.control;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import app.dao.PessoaDao;
import app.model.Pessoa;

@ManagedBean
@SessionScoped
public class PessoaController {

	PessoaDao dao;
	Pessoa pessoa;
	List<Pessoa> pessoas;

	@PostConstruct
	public void init() {
		dao = new PessoaDao(Pessoa.class);
	}

	public void salvar(Pessoa pessoa) {
		this.dao.save(pessoa);
	}

	public Pessoa buscarPorId(Long id) {
		return this.dao.findById(id);
	}

	public Pessoa remover(Long id) {
		return this.dao.remove(id);
	}

	public void atualizar(Pessoa pessoa) {
		this.dao.update(pessoa);
	}

	public List<Pessoa> buscarTodos() {
		return this.dao.findAll();
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
		return pessoas;
	}

	public void setPessoas(List<Pessoa> pessoas) {
		this.pessoas = pessoas;
	}
}
