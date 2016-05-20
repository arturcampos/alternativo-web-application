package app.util;

import app.dao.AlunoDao;
import app.model.Aluno;

public abstract class AlunoUtil {

	public static String GerarMatricula() throws Exception {

		AlunoDao dao = new AlunoDao(Aluno.class);
		String ultimaMatricula = dao.findLastRegistrationNumber();
		String matricula = "1010";
		if (ultimaMatricula != null && !ultimaMatricula.equals("")) {
			long lMatricula = Long.parseUnsignedLong(ultimaMatricula);
			matricula = String.valueOf(lMatricula + 1l);
		}
		return matricula;

	}
}
