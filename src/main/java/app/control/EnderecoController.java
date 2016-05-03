package app.control;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import app.dao.EnderecoDao;
import app.model.Endereco;

@ManagedBean
@SessionScoped
public class EnderecoController {
	EnderecoDao dao;
	Endereco endereco;
	List<Endereco> enderecos;

	@PostConstruct
	public void init() {
		dao = new EnderecoDao(Endereco.class);
	}

	public void salvar(Endereco endereco) {
		this.dao.save(endereco);
	}

	public Endereco buscarPorId(Long id) {
		return this.dao.findById(id);
	}

	public Endereco remover(Long id) {
		return this.dao.remove(id);
	}

	public void atualizar(Endereco endereco) {
		this.dao.update(endereco);
	}

	public List<Endereco> buscarTodos() {
		return this.dao.findAll();
	}

	public EnderecoDao getDao() {
		return dao;
	}

	public void setDao(EnderecoDao dao) {
		this.dao = dao;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public List<Endereco> getEnderecos() {
		return enderecos;
	}

	public void setEnderecos(List<Endereco> enderecos) {
		this.enderecos = enderecos;
	}

}
