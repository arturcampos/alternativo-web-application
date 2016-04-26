package app.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the plastico database table.
 * 
 */
@Embeddable
public class PlasticoPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private int id;

	@Column(insertable=false, updatable=false)
	private int pessoa_id;

	public PlasticoPK() {
	}
	public int getId() {
		return this.id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getPessoa_id() {
		return this.pessoa_id;
	}
	public void setPessoa_id(int pessoa_id) {
		this.pessoa_id = pessoa_id;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof PlasticoPK)) {
			return false;
		}
		PlasticoPK castOther = (PlasticoPK)other;
		return 
			(this.id == castOther.id)
			&& (this.pessoa_id == castOther.pessoa_id);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.id;
		hash = hash * prime + this.pessoa_id;
		
		return hash;
	}
}