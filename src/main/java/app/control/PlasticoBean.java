package app.control;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import app.dao.PlasticoDao;
import app.model.Plastico;

@ManagedBean
@RequestScoped
public class PlasticoBean {
	PlasticoDao dao;
	Plastico plastico;
	List<Plastico> plasticos;

	@PostConstruct
	public void init() {
		dao = new PlasticoDao(Plastico.class);
	}

	public void salvar(Plastico plastico) {
		this.dao.save(plastico);
	}

	public Plastico buscarPorId(Long id) {
		return this.dao.findById(id);
	}

	public Plastico remover(Long id) {
		return this.dao.remove(id);
	}

	public void atualizar(Plastico plastico) {
		this.dao.update(plastico);
	}

	public List<Plastico> buscarTodos() {
		return this.dao.findAll();
	}

	public PlasticoDao getDao() {
		return dao;
	}

	public void setDao(PlasticoDao dao) {
		this.dao = dao;
	}

	public Plastico getPlastico() {
		return plastico;
	}

	public void setPlastico(Plastico plastico) {
		this.plastico = plastico;
	}

	public List<Plastico> getPlasticos() {
		return plasticos;
	}

	public void setPlasticos(List<Plastico> plasticos) {
		this.plasticos = plasticos;
	}

}
