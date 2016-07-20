package app.util;

import java.util.Properties;

import org.apache.log4j.Logger;

import app.dao.AlunoDAO;
import app.model.Aluno;

public abstract class AlunoUtil {

	private static Properties prop = AbstractPropertyReader.propertyReader();
	private static Logger logger = Logger.getLogger(AlunoUtil.class);

	public static String GerarMatricula() throws Exception {
		logger.info("Criando matricula");
		String matricula = prop.getProperty("matricula.inicial");
		AlunoDAO dao = new AlunoDAO(Aluno.class);
		logger.info("Consultando ultima matricula gravado no banco de dados");
		String ultimaMatricula = dao.findLastRegistrationNumber();

		if (ultimaMatricula != null && !ultimaMatricula.equals("")) {
			logger.info("Ultima matricula: " + ultimaMatricula);
			long lMatricula = Long.parseUnsignedLong(ultimaMatricula);
			matricula = String.format("%06d", (lMatricula + 1l));
			logger.info("Nova matricula: " + matricula);
		}
		return matricula;

	}
}
