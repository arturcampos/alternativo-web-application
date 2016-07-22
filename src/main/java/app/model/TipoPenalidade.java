package app.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the tipo_penalidade database table.
 * 
 */
@Entity
@Table(name="tipo_penalidade")
@NamedQuery(name="TipoPenalidade.findAll", query="SELECT t FROM TipoPenalidade t")
public class TipoPenalidade implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	private String tipo;

	//bi-directional many-to-one association to Penalidade
	@OneToMany(mappedBy="tipoPenalidade")
	private List<Penalidade> penalidades;

	public TipoPenalidade() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTipo() {
		return this.tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public List<Penalidade> getPenalidades() {
		return this.penalidades;
	}

	public void setPenalidades(List<Penalidade> penalidades) {
		this.penalidades = penalidades;
	}

	public Penalidade addPenalidade(Penalidade penalidade) {
		getPenalidades().add(penalidade);
		penalidade.setTipoPenalidade(this);

		return penalidade;
	}

	public Penalidade removePenalidade(Penalidade penalidade) {
		getPenalidades().remove(penalidade);
		penalidade.setTipoPenalidade(null);

		return penalidade;
	}

}