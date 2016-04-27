package app.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the pessoa database table.
 * 
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Pessoa implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@Temporal(TemporalType.TIMESTAMP)
	private Date datanasc;

	private String email;

	private String estadocivil;

	private String etnia;

	private String nacionalidade;

	private String naturalidade;

	private String necessidadesespeciais;

	private String nome;

	private String nomemae;

	private String nomepai;

	private String numerocelular;

	private String responsavellegal;

	private String sexo;

	private String uf;

	//bi-directional many-to-one association to Documento
	@OneToMany(mappedBy="pessoa")
	private List<Documento> documentos;

	//bi-directional many-to-one association to Endereco
	@OneToMany(mappedBy="pessoa")
	private List<Endereco> enderecos;

	//bi-directional many-to-one association to Evento
	@OneToMany(mappedBy="pessoa")
	private List<Evento> eventos;

	//bi-directional many-to-one association to Aluno
	@ManyToOne
	private Aluno aluno;

	//bi-directional many-to-one association to Plastico
	@OneToMany(mappedBy="pessoa")
	private List<Plastico> plasticos;

	//bi-directional many-to-one association to Professor
	@OneToMany(mappedBy="pessoa")
	private List<Professor> professors;

	//bi-directional many-to-one association to Tipopessoa
	@OneToMany(mappedBy="pessoa")
	private List<Tipopessoa> tipopessoas;

	public Pessoa() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDatanasc() {
		return this.datanasc;
	}

	public void setDatanasc(Date datanasc) {
		this.datanasc = datanasc;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEstadocivil() {
		return this.estadocivil;
	}

	public void setEstadocivil(String estadocivil) {
		this.estadocivil = estadocivil;
	}

	public String getEtnia() {
		return this.etnia;
	}

	public void setEtnia(String etnia) {
		this.etnia = etnia;
	}

	public String getNacionalidade() {
		return this.nacionalidade;
	}

	public void setNacionalidade(String nacionalidade) {
		this.nacionalidade = nacionalidade;
	}

	public String getNaturalidade() {
		return this.naturalidade;
	}

	public void setNaturalidade(String naturalidade) {
		this.naturalidade = naturalidade;
	}

	public String getNecessidadesespeciais() {
		return this.necessidadesespeciais;
	}

	public void setNecessidadesespeciais(String necessidadesespeciais) {
		this.necessidadesespeciais = necessidadesespeciais;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getNomemae() {
		return this.nomemae;
	}

	public void setNomemae(String nomemae) {
		this.nomemae = nomemae;
	}

	public String getNomepai() {
		return this.nomepai;
	}

	public void setNomepai(String nomepai) {
		this.nomepai = nomepai;
	}

	public String getNumerocelular() {
		return this.numerocelular;
	}

	public void setNumerocelular(String numerocelular) {
		this.numerocelular = numerocelular;
	}

	public String getResponsavellegal() {
		return this.responsavellegal;
	}

	public void setResponsavellegal(String responsavellegal) {
		this.responsavellegal = responsavellegal;
	}

	public String getSexo() {
		return this.sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public String getUf() {
		return this.uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	public List<Documento> getDocumentos() {
		return this.documentos;
	}

	public void setDocumentos(List<Documento> documentos) {
		this.documentos = documentos;
	}

	public Documento addDocumento(Documento documento) {
		getDocumentos().add(documento);
		documento.setPessoa(this);

		return documento;
	}

	public Documento removeDocumento(Documento documento) {
		getDocumentos().remove(documento);
		documento.setPessoa(null);

		return documento;
	}

	public List<Endereco> getEnderecos() {
		return this.enderecos;
	}

	public void setEnderecos(List<Endereco> enderecos) {
		this.enderecos = enderecos;
	}

	public Endereco addEndereco(Endereco endereco) {
		getEnderecos().add(endereco);
		endereco.setPessoa(this);

		return endereco;
	}

	public Endereco removeEndereco(Endereco endereco) {
		getEnderecos().remove(endereco);
		endereco.setPessoa(null);

		return endereco;
	}

	public List<Evento> getEventos() {
		return this.eventos;
	}

	public void setEventos(List<Evento> eventos) {
		this.eventos = eventos;
	}

	public Evento addEvento(Evento evento) {
		getEventos().add(evento);
		evento.setPessoa(this);

		return evento;
	}

	public Evento removeEvento(Evento evento) {
		getEventos().remove(evento);
		evento.setPessoa(null);

		return evento;
	}

	public Aluno getAluno() {
		return this.aluno;
	}

	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}

	public List<Plastico> getPlasticos() {
		return this.plasticos;
	}

	public void setPlasticos(List<Plastico> plasticos) {
		this.plasticos = plasticos;
	}

	public Plastico addPlastico(Plastico plastico) {
		getPlasticos().add(plastico);
		plastico.setPessoa(this);

		return plastico;
	}

	public Plastico removePlastico(Plastico plastico) {
		getPlasticos().remove(plastico);
		plastico.setPessoa(null);

		return plastico;
	}

	public List<Professor> getProfessors() {
		return this.professors;
	}

	public void setProfessors(List<Professor> professors) {
		this.professors = professors;
	}

	public Professor addProfessor(Professor professor) {
		getProfessors().add(professor);
		professor.setPessoa(this);

		return professor;
	}

	public Professor removeProfessor(Professor professor) {
		getProfessors().remove(professor);
		professor.setPessoa(null);

		return professor;
	}

	public List<Tipopessoa> getTipopessoas() {
		return this.tipopessoas;
	}

	public void setTipopessoas(List<Tipopessoa> tipopessoas) {
		this.tipopessoas = tipopessoas;
	}

	public Tipopessoa addTipopessoa(Tipopessoa tipopessoa) {
		getTipopessoas().add(tipopessoa);
		tipopessoa.setPessoa(this);

		return tipopessoa;
	}

	public Tipopessoa removeTipopessoa(Tipopessoa tipopessoa) {
		getTipopessoas().remove(tipopessoa);
		tipopessoa.setPessoa(null);

		return tipopessoa;
	}

}