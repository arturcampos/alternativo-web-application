package app.control;

import static net.sf.dynamicreports.report.builder.DynamicReports.cmp;
import static net.sf.dynamicreports.report.builder.DynamicReports.col;
import static net.sf.dynamicreports.report.builder.DynamicReports.report;
import static net.sf.dynamicreports.report.builder.DynamicReports.type;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;

import app.model.Aluno;
import app.model.Documento;
import app.util.Status;
import net.sf.dynamicreports.report.builder.DynamicReports;
import net.sf.dynamicreports.report.builder.style.StyleBuilder;
import net.sf.dynamicreports.report.constant.HorizontalAlignment;
import net.sf.dynamicreports.report.constant.VerticalAlignment;
import net.sf.dynamicreports.report.datasource.DRDataSource;
import net.sf.dynamicreports.report.exception.DRException;
import net.sf.jasperreports.engine.JRDataSource;

/**
 * @author arturc
 *
 */
@ManagedBean(name = "relatorioBean")
@SessionScoped
public class RelatorioBean implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{alunoBean}")
	private AlunoBean alunoBean;

	@ManagedProperty(value = "#{documentoBean}")
	private DocumentoBean documentoBean;

	@ManagedProperty(value = "#{eventoBean}")
	private EventoBean eventoBean;

	/**
	 * @return the alunoBean
	 */
	public AlunoBean getAlunoBean() {
		return alunoBean;
	}

	/**
	 * @param alunoBean
	 *            the alunoBean to set
	 */
	public void setAlunoBean(AlunoBean alunoBean) {
		this.alunoBean = alunoBean;
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
	 * @return the eventoBean
	 */
	public EventoBean getEventoBean() {
		return eventoBean;
	}

	/**
	 * @param eventoBean
	 *            the eventoBean to set
	 */
	public void setEventoBean(EventoBean eventoBean) {
		this.eventoBean = eventoBean;
	}

	/**
	 *
	 * @param nomeRelatorio
	 */
	public void relatorioAlunoSimples(String nomeRelatorio) {
		System.out.println("Entrou no relat�rio simples");
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		HttpServletResponse res = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext()
				.getResponse();

		try {

			StyleBuilder boldStyle = DynamicReports.stl.style().bold();
			StyleBuilder boldCenteredStyle = DynamicReports.stl.style(boldStyle)
					.setHorizontalAlignment(HorizontalAlignment.CENTER);

			StyleBuilder titleStyle = DynamicReports.stl.style(boldCenteredStyle)
					.setVerticalAlignment(VerticalAlignment.MIDDLE).setFontSize(15);

			StyleBuilder columnTitleStyle = DynamicReports.stl.style(boldCenteredStyle)
					.setBorder(DynamicReports.stl.pen1Point()).setBackgroundColor(Color.LIGHT_GRAY);

			StyleBuilder columnStyle = DynamicReports.stl.style().setHorizontalAlignment(HorizontalAlignment.CENTER)
					.setBorder(DynamicReports.stl.pen1Point());

			report().setColumnTitleStyle(columnTitleStyle)
					// add columns
					.columns(
							// title, field name data type
							col.column("Nome", "nome", type.stringType()), col.column("CPF", "cpf", type.stringType()),
							col.column("Matricula", "matricula", type.stringType()),
							col.column("Data Nascimento", "datanasc", type.dateType()),
							col.column("Assinatura", "item1", type.stringType()))
					.setColumnStyle(columnStyle)
					// shows report title
					// .highlightDetailEvenRows()
					.title(// shows report title
							cmp.horizontalList()
									.add(cmp.text(nomeRelatorio).setStyle(titleStyle)
											.setHorizontalAlignment(HorizontalAlignment.LEFT),
											cmp.text("Futuro-Alternativo").setStyle(titleStyle)
													.setHorizontalAlignment(HorizontalAlignment.RIGHT))
									.newRow()
									.add(cmp.filler()
											.setStyle(DynamicReports.stl.style()
													.setTopBorder(DynamicReports.stl.pen2Point()))
											.setFixedHeight(10)))

					// shows number of page at page footer
					.pageFooter(cmp.pageXofY().setStyle(boldCenteredStyle))
					// set datasource
					.setDataSource(criaDataSourceRelatorioAlunoSimples())
					// create and show report
					.toPdf(baos);

			baos.toByteArray();
			res.setContentType("application/pdf");
			// C�digo abaixo gerar o relat�rio e disponibiliza diretamente
			// na
			// p�gina
			res.setHeader("Content-disposition", "inline;filename=" + nomeRelatorio + ".pdf");
			// C�digo abaixo gerar o relat�rio e disponibiliza para o
			// cliente
			// baixar ou salvar
			// res.setHeader("Content-disposition",
			// "attachment;filename=arquivo.pdf");
			try {
				res.getOutputStream().write(baos.toByteArray());
			} catch (IOException e) {
				error("N�o foi poss�vel criar o relat�rio");
			}
			res.getCharacterEncoding();
			FacesContext.getCurrentInstance().responseComplete();

			System.out.println("Saiu do relat�rio simples");
		} catch (DRException e) {
			error(e.getMessage());
		}

	}

	/**
	 *
	 * @return
	 */
	private JRDataSource criaDataSourceRelatorioAlunoSimples() {
		DRDataSource dataSource = new DRDataSource("nome", "cpf", "matricula", "datanasc", "item1");
		this.alunoBean.buscarTodosPorStatus(Status.ATIVO.toString());
		for (Aluno aluno : this.alunoBean.getAlunos()) {
			Documento cpf = null;
			this.documentoBean.buscarPorPessoa(aluno.getPessoa());
			for (Documento d : this.documentoBean.getDocumentos()) {
				if (d.getTipo().equals("CPF")) {
					cpf = d;
					break;
				}

			}
			dataSource.add(aluno.getPessoa().getNome(), cpf.getNumero(), aluno.getMatricula(),
					aluno.getPessoa().getDataNasc(), null);

		}
		this.alunoBean.getAlunos().clear();
		return dataSource;
	}

	/**
	 * Relat�rio de faltas cometidas por entrar no segundo hor�rio ou sair
	 * antes do hor�rio final
	 */
	public void relatorioFaltaHorarios() {
		System.out.println("Entrou no relat�rio simples");
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		HttpServletResponse res = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext()
				.getResponse();

		try {

			StyleBuilder boldStyle = DynamicReports.stl.style().bold();
			StyleBuilder boldCenteredStyle = DynamicReports.stl.style(boldStyle)
					.setHorizontalAlignment(HorizontalAlignment.CENTER);

			StyleBuilder titleStyle = DynamicReports.stl.style(boldCenteredStyle)
					.setVerticalAlignment(VerticalAlignment.MIDDLE).setFontSize(15);

			StyleBuilder columnTitleStyle = DynamicReports.stl.style(boldCenteredStyle)
					.setBorder(DynamicReports.stl.pen1Point()).setBackgroundColor(Color.LIGHT_GRAY);

			StyleBuilder columnStyle = DynamicReports.stl.style().setHorizontalAlignment(HorizontalAlignment.CENTER)
					.setBorder(DynamicReports.stl.pen1Point());

			report().setColumnTitleStyle(columnTitleStyle)
					// add columns
					.columns(
							// title, field name data type
							col.column("Nome", "nome", type.stringType()),
							col.column("Matricula", "matricula", type.stringType()),
							col.column("Qtd Faltas/Horario", "qtd", type.integerType()))
					.setColumnStyle(columnStyle)
					// shows report title
					// .highlightDetailEvenRows()
					.title(// shows report title
							cmp.horizontalList()
									.add(cmp.text("Faltas cometidas em rela��o aos Hor�rios Entrada/Sa�da")
											.setStyle(titleStyle).setHorizontalAlignment(HorizontalAlignment.LEFT),
											cmp.text("Futuro-Alternativo").setStyle(titleStyle)
													.setHorizontalAlignment(HorizontalAlignment.RIGHT))
									.newRow(2)
									.add(cmp.filler()
											.setStyle(DynamicReports.stl.style()
													.setTopBorder(DynamicReports.stl.pen2Point()))
											.setFixedHeight(10)))

					// shows number of page at page footer
					.pageFooter(cmp.pageXofY().setStyle(boldCenteredStyle))
					// set datasource
					.setDataSource(criaDataSourceFaltaHorarios())
					// create and show report
					.toPdf(baos);

			baos.toByteArray();
			res.setContentType("application/pdf");
			// C�digo abaixo gerar o relat�rio e disponibiliza diretamente na
			// p�gina
			res.setHeader("Content-disposition", "inline;filename=faltas_x_horarios.pdf");
			// C�digo abaixo gerar o relat�rio e disponibiliza para o cliente
			// baixar ou salvar
			// res.setHeader("Content-disposition",
			// "attachment;filename=arquivo.pdf");
			try {
				res.getOutputStream().write(baos.toByteArray());
			} catch (IOException e) {
				error("N�o foi poss�vel cirar o relat�rio");
			}
			res.getCharacterEncoding();
			FacesContext.getCurrentInstance().responseComplete();

			System.out.println("Saiu do relat�rio simples");
		} catch (DRException e) {
			error(e.getMessage());
		}

	}

	/**
	 *
	 * @return
	 */
	private JRDataSource criaDataSourceFaltaHorarios() {
		this.alunoBean.buscarTodosPorStatus(Status.ATIVO.toString());

		DRDataSource dataSource = new DRDataSource("nome", "matricula", "qtd");
		for (Aluno aluno : this.alunoBean.getAlunos()) {
			int qtd = this.eventoBean.buscarFaltasHorariosPorPessoa(aluno.getPessoa());

			dataSource.add(aluno.getPessoa().getNome(), aluno.getMatricula(), qtd);

		}
		this.alunoBean.getAlunos().clear();
		this.alunoBean.limparAluno();
		this.eventoBean.getEventos().clear();
		System.out.println("Fim relatorio faltas horario");
		return dataSource;
	}

	/**
	 * @param data
	 *            - the date to have search present list
	 */
	public void relatorioPresencaPorData(Date data) {
		System.out.println("Entrou no relat�rio presen�a");
		String dataTmp = null;

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		dataTmp = sdf.format(data);

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		HttpServletResponse res = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext()
				.getResponse();

		try {

			StyleBuilder boldStyle = DynamicReports.stl.style().bold();
			StyleBuilder boldCenteredStyle = DynamicReports.stl.style(boldStyle)
					.setHorizontalAlignment(HorizontalAlignment.CENTER);

			StyleBuilder titleStyle = DynamicReports.stl.style(boldCenteredStyle)
					.setVerticalAlignment(VerticalAlignment.MIDDLE).setFontSize(15);

			StyleBuilder columnTitleStyle = DynamicReports.stl.style(boldCenteredStyle)
					.setBorder(DynamicReports.stl.pen1Point()).setBackgroundColor(Color.LIGHT_GRAY);

			StyleBuilder columnStyle = DynamicReports.stl.style().setHorizontalAlignment(HorizontalAlignment.CENTER)
					.setBorder(DynamicReports.stl.pen1Point());

			report().setColumnTitleStyle(columnTitleStyle)
					// add columns
					.columns(
							// title, field name data type
							col.column("Nome", "nome", type.stringType()), col.column("CPF", "cpf", type.stringType()),
							col.column("Matricula", "matricula", type.stringType()),
							col.column("Status", "status", type.stringType()))
					.setColumnStyle(columnStyle)
					// shows report title
					// .highlightDetailEvenRows()
					.title(// shows report title
							cmp.horizontalList()
									.add(cmp.text("Lista de presen�a " + dataTmp)
											.setStyle(titleStyle).setHorizontalAlignment(HorizontalAlignment.LEFT),
											cmp.text("Futuro-Alternativo").setStyle(titleStyle)
													.setHorizontalAlignment(HorizontalAlignment.RIGHT))
									.newRow()
									.add(cmp.filler()
											.setStyle(DynamicReports.stl.style()
													.setTopBorder(DynamicReports.stl.pen2Point()))
											.setFixedHeight(10)))

					// shows number of page at page footer
					.pageFooter(cmp.pageXofY().setStyle(boldCenteredStyle))
					// set datasource
					.setDataSource(criaDataSourceRelatorioPresenca(data))
					// create and show report
					.toPdf(baos);
			baos.toByteArray();
			res.setContentType("application/pdf");
			// C�digo abaixo gerar o relat�rio e disponibiliza diretamente na
			// p�gina
			res.setHeader("Content-disposition", "inline;filename=Lista de presen�a.pdf");
			// Código abaixo gerar o relat�rio e disponibiliza para o cliente
			// baixar ou salvar
			// res.setHeader("Content-disposition",
			// "attachment;filename=arquivo.pdf");
			try {
				res.getOutputStream().write(baos.toByteArray());
			} catch (IOException e) {
				error("N�o foi poss�vel criar o relat�rio: " + e.getMessage());
			}
			res.getCharacterEncoding();
			FacesContext.getCurrentInstance().responseComplete();

			System.out.println("Saiu do relat�rio presen�a");
		} catch (DRException e) {
			error("N�o foi poss�vel criar o relat�rio: " + e.getMessage());
		}

	}

	/**
	 *
	 * @return
	 */
	private JRDataSource criaDataSourceRelatorioPresenca(Date data) {
		DRDataSource dataSource = new DRDataSource("nome", "cpf", "matricula", "status");
		this.alunoBean.buscarTodosPorStatus(Status.ATIVO.toString());
		for (Aluno aluno : this.alunoBean.getAlunos()) {
			// preenchendo documento
			Documento cpf = null;
			this.documentoBean.buscarPorPessoa(aluno.getPessoa());
			for (Documento d : this.documentoBean.getDocumentos()) {
				if (d.getTipo().equals("CPF")) {
					cpf = d;
					break;
				}
			}

			// Definindo presen�a ou falta
			String presenca = null;
			this.eventoBean.buscarPorPessoaEData(aluno.getPessoa(), data);
			if (this.eventoBean.getEventos().isEmpty() || this.eventoBean.getEventos() == null) {
				presenca = "FALTOU";
			} else {
				presenca = "PRESENTE";
			}
			System.out.println(presenca);
			dataSource.add(aluno.getPessoa().getNome(), cpf.getNumero(), aluno.getMatricula(), presenca);
		}
		this.alunoBean.getAlunos().clear();
		return dataSource;
	}

	/**
	 *
	 * @param message
	 */
	public void info(String message) {
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, " Info", message));
	}

	/**
	 *
	 * @param message
	 */
	public void warn(String message) {
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_WARN, " Aten��o!", message));
	}

	/**
	 *
	 * @param message
	 */
	public void error(String message) {
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_ERROR, " Erro!", message));
	}

	/**
	 *
	 * @param message
	 */
	public void fatal(String message) {
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_FATAL, " Fatal!", message));
	}
}
