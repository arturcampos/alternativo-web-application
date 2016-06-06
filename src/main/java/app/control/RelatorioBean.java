package app.control;

import static net.sf.dynamicreports.report.builder.DynamicReports.cmp;
import static net.sf.dynamicreports.report.builder.DynamicReports.col;
import static net.sf.dynamicreports.report.builder.DynamicReports.report;
import static net.sf.dynamicreports.report.builder.DynamicReports.type;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;

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
                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                    .setFontSize(15);

			StyleBuilder columnTitleStyle = DynamicReports.stl.style(boldCenteredStyle)
					.setBorder(DynamicReports.stl.pen1Point()).setBackgroundColor(Color.LIGHT_GRAY);

			  StyleBuilder columnStyle = DynamicReports.stl.style()
			  .setHorizontalAlignment(HorizontalAlignment.CENTER)
			  .setBorder(DynamicReports.stl.pen1Point());


			report().setColumnTitleStyle(columnTitleStyle)
					// add columns
					.columns(
							// title, field name data type
							col.column("Nome", "nome", type.stringType()),
							col.column("CPF", "cpf", type.stringType()),
							col.column("Matricula", "matricula", type.stringType()),
							col.column("Data Nascimento", "datanasc", type.dateType()),
							col.column("Assinatura", "item1", type.stringType())).setColumnStyle(columnStyle)
					// shows report title
					// .highlightDetailEvenRows()
					.title(//shows report title
						     cmp.horizontalList()
						  .add(
						    cmp.text(nomeRelatorio).setStyle(titleStyle).setHorizontalAlignment(HorizontalAlignment.LEFT),
						    cmp.text("Futuro-Alternativo").setStyle(titleStyle).setHorizontalAlignment(HorizontalAlignment.RIGHT))
						  .newRow()
						  .add(cmp.filler().setStyle(DynamicReports.stl.style().setTopBorder(DynamicReports.stl.pen2Point())).setFixedHeight(10)))

					// shows number of page at page footer
					.pageFooter(cmp.pageXofY().setStyle(boldCenteredStyle))
					// set datasource
					.setDataSource(criaDataSourceRelatorioAlunoSimples())
					// create and show report
					.toPdf(baos);

			baos.toByteArray();
			res.setContentType("application/pdf");
			// C�digo abaixo gerar o relat�rio e disponibiliza diretamente na
			// p�gina
			res.setHeader("Content-disposition", "inline;filename="+nomeRelatorio +".pdf");
			// C�digo abaixo gerar o relat�rio e disponibiliza para o cliente
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
