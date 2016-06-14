package app.util;

import java.util.Properties;

import app.dao.AlunoDao;
import app.model.Aluno;

public abstract class AlunoUtil {

	private static Properties prop = AbstractPropertyReader.propertyReader();

	public static String GerarMatricula() throws Exception {
		String matricula = prop.getProperty("matricula.inicial");
		AlunoDao dao = new AlunoDao(Aluno.class);
		String ultimaMatricula = dao.findLastRegistrationNumber();

		if (ultimaMatricula != null && !ultimaMatricula.equals("")) {
			long lMatricula = Long.parseUnsignedLong(ultimaMatricula);
			matricula = String.format("%09d", (lMatricula + 1l));
		}
		return matricula;

	}
}
