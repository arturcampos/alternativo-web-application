package app.dao;

import app.model.Professor;

public class main {

	public static void main(String[] args) {
		
		DaoImpl<Professor> dao = new DaoImpl<Professor>(Professor.class);
		
		Professor a = new Professor();
		
		a.setNome("fulano");
		
		dao.save(a);
		

	}

}
