package app.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the turma database table.
 * 
 */
@Embeddable
public class TurmaPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private int id;

	@Column(insertable=false, updatable=false)
	private int disciplina_id;

	public TurmaPK() {
	}
	public int getId() {
		return this.id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getDisciplina_id() {
		return this.disciplina_id;
	}
	public void setDisciplina_id(int disciplina_id) {
		this.disciplina_id = disciplina_id;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof TurmaPK)) {
			return false;
		}
		TurmaPK castOther = (TurmaPK)other;
		return 
			(this.id == castOther.id)
			&& (this.disciplina_id == castOther.disciplina_id);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.id;
		hash = hash * prime + this.disciplina_id;
		
		return hash;
	}
}