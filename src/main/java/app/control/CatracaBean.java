package app.control;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.AjaxBehaviorEvent;

import org.apache.log4j.Logger;

import app.dao.AlunoDAO;
import app.dao.EventoDAO;
import app.dao.PessoaDAO;
import app.dao.PlasticoDAO;
import app.dao.ProfessorDAO;
import app.model.Aluno;
import app.model.Evento;
import app.model.Pessoa;
import app.model.Plastico;
import app.model.Professor;
import app.util.MessageHandle;
import app.util.Status;
import app.util.TipoPessoa;
import javafx.scene.input.KeyCode;

@ManagedBean
@ViewScoped
public class CatracaBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String ATIVO = Status.ATIVO.toString();
	private static final String ALUNO = TipoPessoa.ALUNO.toString();
	private String HORA_ENTRADA = null;
	private String HORA_SAIDA = null;
	private PlasticoDAO plasticoDAO = new PlasticoDAO(Plastico.class);
	private PessoaDAO pessoaDAO = new PessoaDAO(Pessoa.class);
	private AlunoDAO alunoDAO = new AlunoDAO(Aluno.class);
	private EventoDAO eventoDAO = new EventoDAO(Evento.class);
	private ProfessorDAO professorDAO = new ProfessorDAO(Professor.class);
	private String retorno = null;
	private String linhaDigitavel = null;
	static Logger LOGGER = Logger.getLogger(CatracaBean.class);

	public CatracaBean() {
	}


	public void novoEvento(AjaxBehaviorEvent event) {

		LOGGER.info("***** Criando novo evento ******");
		retorno = null;
		// Caso a linha digitavel esteja vazia, ERRO
		if ((linhaDigitavel.equals("")) || (linhaDigitavel == null)) {
			retorno = new String(
					"Leitura falhou, tente novamente ou entre em contato com a Equipe do Futuro-Alternativo.");
			LOGGER.error(retorno);
			MessageHandle.error(retorno);
		} else {

			try {
				// Busca plastico atraves da linha digitavel
				LOGGER.info("Buscando Cartao: " + linhaDigitavel);
				Plastico plastico = consultarPlastico(linhaDigitavel);

				// Se o plastico existe e esta ativo
				if ((plastico != null) && (plastico.getStatus().equals(ATIVO))) {
					LOGGER.debug("Cartao " + plastico.getLinhaDigitavel() + " encontrado e Ativo.");

					// Se a pessoa existe E aluno E esta ativa
					Pessoa pessoa = plastico.getPessoa();
					if ((pessoa != null) && (pessoa.getTipoPessoa().equals(ALUNO)) && estaAtivo(pessoa)) {
						LOGGER.info("Pessoa existe - um aluno ativo!");

						LOGGER.info("Buscando eventos cadastrados para a pessoa");
						List<Evento> eventos = consultarEventosEntrada(pessoa.getId());
						if (eventos == null || eventos.isEmpty()) {
							LOGGER.info("Nao ha eventos para o dia. Registrando nova entrada...");
							Date now = new Date();

							String status = comparaEntrada(now);
							Evento evento = new Evento();
							evento.setDataHoraEntrada(now);
							evento.setDataHoraSaida(null);
							evento.setStatus(status);
							evento.setPessoa(pessoa);

							LOGGER.info("Salvando novo envento entrada");
							eventoDAO.save(evento);
							LOGGER.info("Evento criado com sucesso...");
							retorno = info(pessoa, evento, "ENTRADA" + status);
							LOGGER.info(retorno);
							MessageHandle.info(retorno);
						} else {
							Evento evento;

							if (eventos.size() > 1) {
								evento = eventos.get(eventos.size() - 1);
								LOGGER.info(
										"Existe mais de 1 evento para este cartao no dia de hoje, apenas o evento mais recente sera atualizado");
							} else {
								evento = eventos.get(0);
							}

							Date now = new Date();

							LOGGER.info("Validando horario de Saida para criar status do Evento.");
							String status = comparaSaida(now);
							LOGGER.info("Status = " + status);
							evento.setDataHoraSaida(now);
							evento.setStatus(status);
							LOGGER.info("Salvando novo envento saida");
							update(evento);
							retorno = info(pessoa, evento, "SAIDA" + status);
							LOGGER.info(retorno);
							MessageHandle.info(retorno);
						}
					} else {
						linhaDigitavel = "";
						retorno = "Aluno nao encontrado ou INATIVO na base de dados. \n"
								+ "Tente novamente ou entre em contato com a Equipe do Futuro-Alternativo.";
						LOGGER.warn(retorno);
						MessageHandle.error(retorno);
					}
				} else {
					retorno = "O numero da carteirinha [" + linhaDigitavel + "] nao foi encontrado na base de dados. \n"
							+ "Tente novamente ou entre em contato com a Equipe do Futuro-Alternativo.";
					LOGGER.warn(retorno);
					MessageHandle.error(retorno);

				}
			} catch (Exception e) {
				retorno = "Houve um erro ao registradar as informacoes. \n"
						+ "Tente novamente ou entre em contato com a Equipe do Futuro-Alternativo.";
				LOGGER.error(retorno, e);
				MessageHandle.error(retorno);
			}
		}
		linhaDigitavel = null;

	}

	private String info(Pessoa pessoa, Evento evento, String tipoMsg) {
		String mensagem = null;

		if (tipoMsg.equals("ENTRADAOK")) {

			DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			String dataFormatada = df.format(evento.getDataHoraEntrada());

			mensagem = "Bem vindo " + pessoa.getNome() + "\nEntrada registrada: " + dataFormatada;
			LOGGER.info(mensagem);
		} else if (tipoMsg.equals("ENTRADANOK")) {

			DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			String dataFormatada = df.format(evento.getDataHoraEntrada());

			mensagem = pessoa.getNome() + "\n Voce esta entrando no SEGUNDO HORARIO \nEntrada registrada: "
					+ dataFormatada;
			LOGGER.info(mensagem);

		} else if (tipoMsg.equals("SAIDANOK")) {
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			String dataFormatada = df.format(evento.getDataHoraSaida());

			mensagem = pessoa.getNome() + "\nVoce esta saindo antes do fim das Aulas de hoje \nSaida registrada: "
					+ dataFormatada;
			LOGGER.info(mensagem);
		} else if (tipoMsg.equals("SAIDAOK")) {
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			String dataFormatada = df.format(evento.getDataHoraSaida());

			mensagem = "\t" + pessoa.getNome() + "\nAte a proxima aula! \nSaida registrada: " + dataFormatada;
			LOGGER.info(mensagem);

		} else {
			mensagem = "Nao foi especificado tipo de mensagem";
			LOGGER.warn(mensagem);
		}
		return mensagem;
	}

	/**
	 * @author ARTUR
	 * @param linhaDigitavel
	 * @return
	 *
	 */
	private Plastico consultarPlastico(String linhaDigitavel) {
		Plastico plasticoEncontrado = null;
		try {
			plasticoEncontrado = plasticoDAO.findByDigitableLine(linhaDigitavel);
			return plasticoEncontrado;
		} catch (Exception e) {
			LOGGER.error("Erro ao consultar plastico", e);
			return plasticoEncontrado;
		}
	}

	private boolean estaAtivo(Pessoa pessoa) {

		boolean estaAtivo = false;
		try {
			if (pessoa.getTipoPessoa().equals("ALUNO")) {
				Aluno aluno = alunoDAO.findByPersonId(pessoa.getId());
				if ((aluno != null) && (aluno.getStatus().equals(ATIVO))) {
					estaAtivo = true;
				} else {
					estaAtivo = false;
				}
			} else {
				Professor professor = professorDAO.findByPersonId(pessoa.getId());
				if (professor != null) {
					estaAtivo = true;
				} else {
					estaAtivo = false;
				}
			}
			return estaAtivo;
		} catch (Exception e) {
			LOGGER.error("Erro!!", e);
			return estaAtivo;
		}
	}

	private List<Evento> consultarEventosEntrada(Long pessoaId) {
		List<Evento> eventos = new ArrayList<Evento>();
		try {
			Date date = new Date();
			eventos = eventoDAO.findEventsByPersonIdAndDate(pessoaId, date);
			return eventos;
		} catch (Exception e) {
			LOGGER.error("Erro!!", e);
			return eventos;
		}
	}

	private void update(Evento novoEvento) {
		Evento eventoSalvo = eventoDAO.findById(novoEvento.getId());
		if (novoEvento.equals(eventoSalvo)) {
			LOGGER.info("Atualizando evento com data final e status ...");
			eventoDAO.update(novoEvento);
		} else {
			LOGGER.info("Evento nao encontrado para atualizacao ..");
		}
	}

	private String comparaEntrada(Date now) throws ParseException {
		LOGGER.info("Validando horario de entrada para criar status do Evento.");
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		Date entrada;
		String status = "";
		entrada = sdf.parse(HORA_ENTRADA);
		if (now.getHours() == entrada.getHours()) {
			if (now.getMinutes() <= entrada.getMinutes()) {
				status = "OK";
				LOGGER.info("Entrada OK");
			} else {
				status = "NOK";
				LOGGER.info("Entrada no segundo horario...");
			}
		} else if (now.getHours() < entrada.getHours()) {
			status = "OK";
			LOGGER.info("Entrada OK");
		} else {
			status = "NOK";
			LOGGER.info("Entrada no segundo horario...");
		}

		return status;

	}

	private String comparaSaida(Date now) throws ParseException {
		LOGGER.info("Comparando datas");
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		Date saida;
		String status = "";
		saida = sdf.parse(HORA_SAIDA);
		if (now.getHours() == saida.getHours()) {
			if (now.getMinutes() >= saida.getMinutes()) {
				status = "OK";
				LOGGER.info("Saida OK");
			} else
				status = "NOK";
			LOGGER.info("Saida antes do fim da aula...");
		} else if (now.getHours() < saida.getHours()) {
			status = "NOK";
			LOGGER.info("Sa�da antes do fim da aula...");

		} else {
			status = "OK";
			LOGGER.info("Saida OK");
		}
		return status;
	}

	public PlasticoDAO getPlasticoDAO() {
		return plasticoDAO;
	}

	public void setPlasticoDAO(PlasticoDAO plasticoDAO) {
		this.plasticoDAO = plasticoDAO;
	}

	public PessoaDAO getPessoaDAO() {
		return pessoaDAO;
	}

	public void setPessoaDAO(PessoaDAO pessoaDAO) {
		this.pessoaDAO = pessoaDAO;
	}

	public AlunoDAO getAlunoDAO() {
		return alunoDAO;
	}

	public void setAlunoDAO(AlunoDAO alunoDAO) {
		this.alunoDAO = alunoDAO;
	}

	public String getHORA_ENTRADA() {
		return HORA_ENTRADA;
	}

	public void setHORA_ENTRADA(String hORA_ENTRADA) {
		HORA_ENTRADA = hORA_ENTRADA;
	}

	public String getHORA_SAIDA() {
		return HORA_SAIDA;
	}

	public void setHORA_SAIDA(String hORA_SAIDA) {
		HORA_SAIDA = hORA_SAIDA;
	}

	public EventoDAO getEventoDAO() {
		return eventoDAO;
	}

	public void setEventoDAO(EventoDAO eventoDAO) {
		this.eventoDAO = eventoDAO;
	}

	public ProfessorDAO getProfessorDAO() {
		return professorDAO;
	}

	public void setProfessorDAO(ProfessorDAO professorDAO) {
		this.professorDAO = professorDAO;
	}

	public static Logger getLOGGER() {
		return LOGGER;
	}

	public static void setLOGGER(Logger lOGGER) {
		LOGGER = lOGGER;
	}

	public static String getAtivo() {
		return ATIVO;
	}

	public static String getAluno() {
		return ALUNO;
	}

	public String getLinhaDigitavel() {
		return linhaDigitavel;
	}

	public void setLinhaDigitavel(String linhaDigitavel) {
		this.linhaDigitavel = linhaDigitavel;
	}
}
