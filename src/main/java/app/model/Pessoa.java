package app.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the pessoa database table.
 * 
 */
@Entity
@Table(name = "pessoa", schema = "futurodb")
@NamedQuery(name = "Pessoa.findAll", query = "SELECT p FROM Pessoa p")
public class Pessoa implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	// @GeneratedValue(strategy=GenerationType.IDENTITY)
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

	
	// bi-directional many-to-one association to Documento
	@OneToMany(mappedBy = "pessoa")
	private List<Documento> documentos;

	// bi-directional many-to-one association to Endereco
	@OneToMany(mappedBy = "pessoa")
	private List<Endereco> enderecos;

	// bi-directional many-to-one association to Plastico
	@OneToMany(mappedBy = "pessoa")
	private List<Plastico> plasticos;

	// bi-directional one-to-one association to Professor
	// @OneToOne(mappedBy="pessoa")
	// private Professor professor;

	private String tipopessoa;

	public Pessoa() {
	}
	
	/**
	 * @param id
	 * @param datanasc
	 * @param email
	 * @param estadocivil
	 * @param etnia
	 * @param nacionalidade
	 * @param naturalidade
	 * @param necessidadesespeciais
	 * @param nome
	 * @param nomemae
	 * @param nomepai
	 * @param numerocelular
	 * @param responsavellegal
	 * @param sexo
	 * @param uf
	 * @param documentos
	 * @param enderecos
	 * @param eventos
	 * @param plasticos
	 * @param tipopessoa
	 */
	public Pessoa(Long id, Date datanasc, String email, String estadocivil, String etnia, String nacionalidade,
			String naturalidade, String necessidadesespeciais, String nome, String nomemae, String nomepai,
			String numerocelular, String responsavellegal, String sexo, String uf, List<Documento> documentos,
			List<Endereco> enderecos, List<Plastico> plasticos, String tipopessoa) {
		super();
		this.id = id;
		this.datanasc = datanasc;
		this.email = email;
		this.estadocivil = estadocivil;
		this.etnia = etnia;
		this.nacionalidade = nacionalidade;
		this.naturalidade = naturalidade;
		this.necessidadesespeciais = necessidadesespeciais;
		this.nome = nome;
		this.nomemae = nomemae;
		this.nomepai = nomepai;
		this.numerocelular = numerocelular;
		this.responsavellegal = responsavellegal;
		this.sexo = sexo;
		this.uf = uf;
		this.documentos = documentos;
		this.enderecos = enderecos;
		this.plasticos = plasticos;
		this.tipopessoa = tipopessoa;
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

	public String getTipopessoa() {
		return this.tipopessoa;
	}

	public void setTipopessoas(String tipopessoa) {
		this.tipopessoa = tipopessoa;
	}

	@Override
	public Pessoa clone() {
		return new Pessoa(this.id, this.datanasc, this.email, this.estadocivil, this.etnia, this.nacionalidade,
				this.naturalidade, this.necessidadesespeciais, this.nome, this.nomemae, this.nomepai,
				this.numerocelular, this.responsavellegal, this.sexo, this.uf, this.documentos, this.enderecos,
				this.plasticos, this.tipopessoa);
	}

	public void restaurar(Pessoa pessoa) {
		this.id = pessoa.getId();
		this.datanasc = pessoa.getDatanasc();
		this.email = pessoa.getEmail();
		this.estadocivil = pessoa.getEstadocivil();
		this.etnia = pessoa.getEtnia();
		this.nacionalidade = pessoa.getNacionalidade();
		this.naturalidade = pessoa.getNaturalidade();
		this.necessidadesespeciais = pessoa.getNecessidadesespeciais();
		this.nome = pessoa.getNome();
		this.nomemae = pessoa.getNomemae();
		this.nomepai = pessoa.getNomepai();
		this.numerocelular = pessoa.getNumerocelular();
		this.responsavellegal = pessoa.getResponsavellegal();
		this.sexo = pessoa.getSexo();
		this.uf = pessoa.getUf();
		this.documentos = pessoa.getDocumentos();
		this.enderecos = pessoa.getEnderecos();
		this.plasticos = pessoa.getPlasticos();
		this.tipopessoa = pessoa.getTipopessoa();
	}

}