package app.control;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import org.apache.log4j.Logger;

import app.dao.AlunoDAO;
import app.model.Aluno;
import app.model.Documento;
import app.model.Endereco;
import app.model.Pessoa;
import app.model.Plastico;
import app.model.Turma;
import app.util.AlunoUtil;
import app.util.ListUtil;
import app.util.MessageHandle;
import app.util.Status;
import app.util.TipoPessoa;

@ManagedBean
@SessionScoped
public class AlunoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private AlunoDAO dao;
	private Aluno aluno;
	private Pessoa pessoa;
	private List<Documento> documentos;
	private Documento documento;
	private List<Endereco> enderecos;
	private List<Turma> turmas;
	private Turma turma;
	private Endereco endereco;
	private Aluno alunoAnterior = null;
	private List<Aluno> alunos;
	private boolean editado;
	private String alunoTab;
	private String documentoTab;
	private String enderecoTab;
	private Plastico plastico;
	private static Logger LOGGER = Logger.getLogger(AlunoBean.class);

	@ManagedProperty(value = "#{documentoBean}")
	private DocumentoBean documentoBean;

	@ManagedProperty(value = "#{plasticoBean}")
	private PlasticoBean plasticoBean;

	@ManagedProperty(value = "#{turmaBean}")
	private TurmaBean turmaBean;

	/**
	 *
	 */
	@PostConstruct
	public void init() {
		LOGGER.info("Inicializando AlunoBean");
		this.dao = new AlunoDAO(Aluno.class);
		this.alunos = new ArrayList<Aluno>();
		this.aluno = new Aluno();
		this.pessoa = new Pessoa();
		this.documentos = new ArrayList<Documento>();
		this.enderecos = new ArrayList<Endereco>();
		this.endereco = new Endereco();
		this.documento = new Documento();
		this.alunoTab = "active";
		this.enderecoTab = "";
		this.documentoTab = "";
		this.plastico = new Plastico();
		this.turma = new Turma();
		this.turmaBean.buscarPorStatus("ATIVO");
		this.setTurmas(this.turmaBean.getTurmas());
	}

	/**
	 *
	 * @return the action to go to save student after create in data base
	 */
	public String salvar() {
		LOGGER.info("Gravando informacoes do aluno");
		try {
			if (this.documentos.size() > 0) {
				this.pessoa.setTipoPessoa(TipoPessoa.ALUNO.toString());

				LOGGER.info("Vinculando documentos a pessoa");
				// adicionando documentos a pessoa
				this.pessoa.setDocumentos(this.documentos);
				vincularDocumento(this.pessoa, this.documentos);
				LOGGER.info("Documentos vinculados");
			} else {
				// Se nao houver documentos cadastrado retorna um aviso
				LOGGER.warn("Lista de documentos vazia: e necessario cadastrar pelo menos 1 (UM) documento");
				MessageHandle.warn("E necessario cadastrar pelo menos 1 (UM) documento");
				return "salvarAluno?faces-redirect=true";
			}

			if (this.enderecos.size() > 0) {
				// adicionando endereco pessoa

				LOGGER.info("Vinculando enderecos a pessoa");
				this.pessoa.setEnderecos(this.enderecos);
				vincularEndereco(this.pessoa, this.enderecos);
				LOGGER.info("Enderecos vinculados");
			} else {
				// Se nao houver endercos cadastrado retorna um aviso
				LOGGER.warn("Lista de enderecos vazia: e necessario cadastrar pelo menos 1 (UM) endereco");
				MessageHandle.warn("E necessario cadastrar pelo menos 1 (UM) endereco");
				return "salvarAluno?faces-redirect=true";
			}
			// alterando status para ativo
			this.aluno.setStatus(Status.ATIVO.toString());

			// Gerando matricula e atribuindo ao aluno
			String matricula = AlunoUtil.GerarMatricula();
			this.aluno.setMatricula(matricula);

			// Converte data e cria plastico
			LOGGER.info("Criando novo cartao Plastico");
			
			Plastico plas = new Plastico();
			plas.setLinhaDigitavel(matricula);
			plas.setDataCadastro(new Date());
			plas.setStatus(Status.ATIVO.toString());
			LOGGER.info(plas.toString());
			
			// adicionando plastico a pessoa
			this.pessoa.addPlastico(plas);

			// adicionando pessoa ao aluno
			this.aluno.setPessoa(this.pessoa);

			// adicionando turma <-> aluno
			this.turma.addAluno(this.aluno);

			// executando metod DAO para salvar aluno
			this.dao.save(this.aluno);

			LOGGER.info("Informacoes salvas com sucesso.\n" + "Nome: " + this.pessoa.getNome() + "\n" + "Matricula: "
					+ this.aluno.getMatricula());
			MessageHandle.info("Informacoes salvas com sucesso.\n" + "Nome: " + this.pessoa.getNome() + "\n" + "Matricula: "
					+ this.aluno.getMatricula());
			
			if(!ListUtil.isValid(this.alunos)){
				this.alunos = this.dao.findByStatus(Status.ATIVO.toString());
			}
			this.alunos.add(0, this.aluno.clone());
			this.aluno = new Aluno();
			this.pessoa = new Pessoa();
			this.documentos = new ArrayList<Documento>();
			this.enderecos = new ArrayList<Endereco>();
			this.endereco = new Endereco();
			this.documento = new Documento();
			this.alunoTab = "active";
			this.enderecoTab = "";
			this.documentoTab = "";
			this.plastico = new Plastico();
			this.turma = new Turma();
			
			
			LOGGER.info("Novo aluno adicionado na lista");
			return "listarAluno?faces-redirect=true";

		} catch (Exception e) {
			LOGGER.error("Erro ao Salvar informacoes", e);
			MessageHandle.error("Erro ao Salvar informacoes: " + e.getMessage());
			return "salvarAluno?faces-redirect=true";
		}
	}

	public String cancelarSalvar() {
		init();
		return "Inicio?faces-redirect=true";
	}

	/**
	 *
	 * @param id
	 * @return
	 */
	public String buscarPorId(Long id) {
		init();
		LOGGER.info("Buscando aluno id: " + id);
		this.aluno = this.dao.findById(id);
		if (this.aluno != null) {
			LOGGER.info("Aluno encontrado: " + this.aluno.getPessoa().getNome());
			MessageHandle.info("Aluno encontrado: " + this.aluno.getPessoa().getNome());
		} else {
			LOGGER.warn("Aluno nao encontrado!");
			MessageHandle.warn("Aluno nao encontrado!");
		}
		return "atualizarAluno?faces-redirect=true";
	}

	/**
	 *
	 * @param id
	 * @return
	 */
	public String remover(Aluno aluno) {
		try {
			LOGGER.info("Removendo aluno");
			if (ListUtil.isValid(this.alunos)) {
				this.aluno = aluno;
				LOGGER.info("Alterando o status do aluno para INATIVO");
				this.aluno.setStatus(Status.INATIVO.toString());

				this.plastico = this.aluno.getPessoa().getPlasticos().get(0);
				if (this.plastico == null) {
					LOGGER.info("Buscando plastico vinculado ao aluno");
					this.plastico = plasticoBean.buscarPorPessoaId(aluno.getPessoa().getId());
				}
				LOGGER.info("Alterando o status do plastico vinculado ao aluno para INATIVO");
				this.plastico.setStatus(Status.INATIVO.toString());
				plasticoBean.atualizar(this.plastico);

				this.dao.update(this.aluno);
				this.alunos.remove(this.aluno);
				LOGGER.info("Aluno removido: " + this.aluno.getPessoa().getNome());
				MessageHandle.info("Aluno removido: " + this.aluno.getPessoa().getNome());
				this.plastico = new Plastico();
				this.aluno = new Aluno();
			} else {
				LOGGER.warn(
						"Houve um problema para remover o aluno: informacao solicitada nao existe, verifique na listagem");
				MessageHandle.warn("Houve um problema para remover o aluno, verifique na listagem");
			}
		} catch (Exception e) {
			LOGGER.error("Houve um problema para remover o aluno: " + e);
			MessageHandle.error("Houve um problema para remover o aluno: " + e.getMessage());
		}
		return "listarAluno?faces-redirect=true";
	}

	/**
	 *
	 * @return the action to go to save student
	 */
	public String adicionarDocumento() {
		LOGGER.info("Adicioando documento:\n" + documento.toString());
		Documento doc = this.documento.clone();
		this.documentos.add(doc);
		this.documento = new Documento();
		this.alunoTab = "";
		this.documentoTab = "active";
		this.enderecoTab = "";

		LOGGER.info("Documento adicionado");
		return "salvarAluno?faces-redirect=true";
	}

	/**
	 *
	 * @return the action to go to save the student
	 */
	public String adicionarAluno() {
		this.alunoTab = "";
		this.alunoTab = "";
		this.documentoTab = "active";
		this.enderecoTab = "";
		return "salvarAluno?faces-redirect=true";
	}

	/**
	 *
	 * @param documento
	 *            - to do removed
	 * @return the action to go to save the student
	 */
	public String removerDocumento(final Documento documento) {

		if (ListUtil.isValid(this.documentos)) {
			this.documentos.remove(documento);
			LOGGER.info("Documento removido com sucesso!");
			this.alunoTab = "";
			this.documentoTab = "active";
			this.enderecoTab = "";
		} else {
			LOGGER.warn("Nao existe documento a ser removido");
			MessageHandle.warn("Nao existe documento a ser removido");
		}
		return "salvarAluno?faces-redirect=true";
	}

	/**
	 *
	 * @return the action to go to save student after add endereco
	 */

	public String adicionarEndereco() {

		Endereco end = this.endereco.clone();
		this.enderecos.add(end);
		this.endereco = new Endereco();
		this.alunoTab = "";
		this.documentoTab = "";
		this.enderecoTab = "active";
		LOGGER.info("Endereco adicionado com Sucesso.\n" + end.toString());
		return "salvarAluno?faces-redirect=true";
	}

	/**
	 *
	 * @param endereco
	 *            - element de entrada
	 * @return the action to page to save the student
	 */
	public String removerEndereco(final Endereco endereco) {

		if (ListUtil.isValid(this.enderecos)) {
			this.enderecos.remove(endereco);
			LOGGER.info("Endereco removido com sucesso!");
		} else {
			LOGGER.warn("Nao existem enderecos a serem removidos");
			MessageHandle.warn("Nao existem enderecos a serem removidos");
		}
		return "salvarAluno?faces-redirect=true";
	}

	/**
	 *
	 * @param p
	 * @param docs
	 */
	private void vincularDocumento(Pessoa p, List<Documento> docs) {
		for (Documento d : docs) {
			d.setPessoa(p);
		}
	}

	/**
	 *
	 * @param p
	 * @param ends
	 */
	private void vincularEndereco(Pessoa p, List<Endereco> ends) {
		for (Endereco e : ends) {
			e.setPessoa(p);
		}
	}

	/**
	 *
	 * @param aluno
	 *            - the student in
	 * @return the action to go to save ou list student
	 */
	public String atualizar(Aluno aluno) {
		LOGGER.info("Iniciando atualizacao do aluno:\n" + aluno.getPessoa().toString() + "\n" + aluno.toString());
		this.aluno = aluno.clone();
		this.alunoAnterior = aluno;
		this.editado = true;

		LOGGER.info("Pronto para atualizar");

		return "atualizarAluno?faces-redirect=true";
	}

	/**
	 *
	 * @return the action to go to save ou list student
	 */
	public String salvarAtualizar() {
		try {
			LOGGER.info("Atualizando aluno:\n" + this.aluno.toString());

			int indice = alunos.indexOf(alunoAnterior);
			turma = alunoAnterior.getTurma();
			turma.addAluno(aluno);
			alunos.remove(this.alunoAnterior);
			alunos.add(indice, aluno);
			this.dao.update(this.aluno);
			
			editado = false;

			MessageHandle.info("Dados de " + this.aluno.getPessoa().getNome() + " atualizados");
			LOGGER.info("Dados de " + this.aluno.getPessoa().getNome() + " atualizados");

			alunoAnterior = null;
			aluno = new Aluno();
			return "listarAluno?faces-redirect=true";
		} catch (Exception e) {
			LOGGER.error("Erro ao atualizar as informacoes", e);
			MessageHandle.error("Erro ao atualizar as informacoes: " + e.getMessage());
			return "atualizarAluno?faces-redirect=true";
		}
	}

	/**
	 * @return the action to go to list the student
	 */
	public String cancelarAtualizar() {
		this.aluno.restaurar(this.alunoAnterior);
		this.alunoAnterior = new Aluno();
		editado = false;
		return "listarAluno?faces-redirect=true";
	}

	/**
	 *
	 */
	public void buscarTodos() {
		init();
		LOGGER.info("Buscando alunos");
		this.alunos = this.dao.findAll();
		if (!ListUtil.isValid(alunos)) {
			LOGGER.info("Nenhum aluno encontrado");
			MessageHandle.info("Nenhum aluno encontrado");
		} else {
			LOGGER.info("Lista de alunos");
		}
	}

	/**
	 *
	 * @param status
	 *            - student's status
	 * @return the action to go to list students
	 */
	public String buscarTodosPorStatus(String status) {
		LOGGER.info("Buscando Alunos " + status);
		this.alunos = this.dao.findByStatus(status);
		if (!ListUtil.isValid(this.alunos)) {
			MessageHandle.warn("Nenhum aluno encontrado");
			LOGGER.warn("Nenhum aluno encontrado");
		}
		return "listarAluno?faces-redirect=true";
	}

	/**
	 *
	 * @param matricula
	 *            - student's registration number
	 * @return the action to go to list student
	 */
	public String buscarPorMatricula(String matricula) {
		init();
		if (matricula.equals("") || matricula == null) {
			LOGGER.warn("Erro ao consultar aluno: Matricula nao pode estar em branco");
			MessageHandle.warn("Erro ao consultar aluno: Matricula nao pode estar em branco");
		} else {
			long matriculaL = Long.parseLong(matricula);
			LOGGER.info("Buscando aluno por matricula: " + String.format("%06d", matriculaL));

			this.alunos = this.dao.findByRegistrationNumber(matricula);
			if (ListUtil.isValid(alunos)) {
				LOGGER.info("Alunos encontrados.");
				MessageHandle.info("Alunos encontrados.");
			} else {
				LOGGER.warn("Matricula nao existe.");
				MessageHandle.warn("Matricula nao existe.");
			}
		}
		return "listarAluno?faces-redirect=true";
	}

	public String buscarPorTurma(Turma turma) {
		turmaBean.buscarPorId(turma.getId());
		if (turmaBean.getTurma() != null) {
			turma = turmaBean.getTurma();
			if (!ListUtil.isValid(turma.getAlunos())) {
				alunos = dao.findByTurmaId(turma.getId());
			} else {
				alunos = turma.getAlunos();
			}
			LOGGER.info("Sucesso!");
		} else {
			LOGGER.info("Turma nao encontrada: " + turma.getCodigo());
			MessageHandle.info("Turma nao encontrada: " + turma.getCodigo());
		}

		return "listarAluno?faces-redirect=true";

	}

	/**
	 *
	 */
	public void limparAluno() {
		this.aluno.setDataEgresso(null);
		this.aluno.setDataIngresso(null);
		this.aluno.setMatricula(null);
		this.aluno.setPessoa(null);
		this.aluno.setTipoCotaIngresso(-1);
		this.aluno.setTurma(null);
		this.aluno.setId(null);
	}

	/**
	 *
	 * @return the action to go to Home
	 */
	public String voltar() {
		init();
		return "Inicio?faces-redirect=true";
	}

	/**
	 *
	 * @return the action to go to list
	 */
	public String listar() {
		init();
		return "listarAluno?faces-redirect=true";
	}

	/**
	 *
	 * @return the value of editado variable
	 */
	public boolean isEditado() {
		return editado;
	}

	/**
	 *
	 * @param editado
	 *            - value to add to editado variable
	 */
	public void setEditado(boolean editado) {
		this.editado = editado;
	}

	/**
	 *
	 * @return
	 */
	public Aluno getAlunoAnterior() {
		return alunoAnterior;
	}

	/**
	 *
	 * @param alunoAnterior
	 */
	public void setAlunoAnterior(Aluno alunoAnterior) {
		this.alunoAnterior = alunoAnterior;
	}

	/**
	 *
	 * @return
	 */
	public AlunoDAO getDao() {
		return dao;
	}

	/**
	 *
	 * @param dao
	 */
	public void setDao(AlunoDAO dao) {
		this.dao = dao;
	}

	/**
	 *
	 * @return
	 */
	public Aluno getAluno() {
		return aluno;
	}

	/**
	 *
	 * @param aluno
	 */
	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}

	/**
	 *
	 * @return
	 */
	public List<Aluno> getAlunos() {
		return alunos;
	}

	/**
	 *
	 * @param alunos
	 */
	public void setAlunos(List<Aluno> alunos) {
		this.alunos = alunos;
	}

	/**
	 *
	 * @return
	 */
	public Pessoa getPessoa() {
		return pessoa;
	}

	/**
	 *
	 * @param pessoa
	 */
	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	/**
	 *
	 * @return
	 */
	public List<Documento> getDocumentos() {
		return documentos;
	}

	/**
	 *
	 * @param documentos
	 */
	public void setDocumentos(List<Documento> documentos) {
		this.documentos = documentos;
	}

	/**
	 *
	 * @return
	 */
	public List<Endereco> getEnderecos() {
		return enderecos;
	}

	/**
	 *
	 * @param enderecos
	 */
	public void setEnderecos(List<Endereco> enderecos) {
		this.enderecos = enderecos;
	}

	/**
	 *
	 * @return
	 */
	public Documento getDocumento() {
		return documento;
	}

	/**
	 *
	 * @param documento
	 */
	public void setDocumento(Documento documento) {
		this.documento = documento;
	}

	/**
	 *
	 * @return
	 */
	public Endereco getEndereco() {
		return endereco;
	}

	/**
	 *
	 * @param endereco
	 */
	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	/**
	 *
	 * @return
	 */
	public String getAlunoTab() {
		return alunoTab;
	}

	/**
	 *
	 * @param alunoTab
	 */
	public void setAlunoTab(String alunoTab) {
		this.alunoTab = alunoTab;
	}

	/**
	 *
	 * @return
	 */
	public String getDocumentoTab() {
		return documentoTab;
	}

	/**
	 *
	 * @param documentoTab
	 */
	public void setDocumentoTab(String documentoTab) {
		this.documentoTab = documentoTab;
	}

	/**
	 *
	 * @return
	 */
	public String getEnderecoTab() {
		return enderecoTab;
	}

	/**
	 *
	 * @param enderecoTab
	 */
	public void setEnderecoTab(String enderecoTab) {
		this.enderecoTab = enderecoTab;
	}

	public Plastico getPlastico() {
		return plastico;
	}

	public void setPlastico(Plastico plastico) {
		this.plastico = plastico;
	}

	public List<Turma> getTurmas() {
		return turmas;
	}

	public void setTurmas(List<Turma> turmas) {
		this.turmas = turmas;
	}

	public Turma getTurma() {
		return turma;
	}

	public void setTurma(Turma turma) {
		this.turma = turma;
	}

	/**
	 * @return the documentoBean
	 */
	public DocumentoBean getDocumentoBean() {
		return documentoBean;
	}

	/**
	 * @param documentoBean
	 *            the documentoBean to set
	 */
	public void setDocumentoBean(DocumentoBean documentoBean) {
		this.documentoBean = documentoBean;
	}

	/**
	 * @return the plasticoBean
	 */
	public PlasticoBean getPlasticoBean() {
		return plasticoBean;
	}

	/**
	 * @param plasticoBean
	 *            the plasticoBean to set
	 */
	public void setPlasticoBean(PlasticoBean plasticoBean) {
		this.plasticoBean = plasticoBean;
	}

	/**
	 * @return the turmaBean
	 */
	public TurmaBean getTurmaBean() {
		return turmaBean;
	}

	/**
	 * @param turmaBean
	 *            the turmaBean to set
	 */
	public void setTurmaBean(TurmaBean turmaBean) {
		this.turmaBean = turmaBean;
	}


}