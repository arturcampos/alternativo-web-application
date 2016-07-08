package app.dao;

import app.model.Pessoa;

public class PessoaDAO extends DAOImpl<Pessoa> {

	public PessoaDAO(Class<Pessoa> clazz) {
		super(clazz);
	}
}
