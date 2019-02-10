package info.campersites.bo;

import java.io.Serializable;

import org.hibernate.validator.constraints.NotEmpty;



public class ReviewNew implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@NotEmpty(message = Errors.REQUIRED)
	private String testo;

	public String getTesto() {
		return testo;
	}

	public void setTesto(String testo) {
		this.testo = testo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((testo == null) ? 0 : testo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ReviewNew other = (ReviewNew) obj;
		if (testo == null) {
			if (other.testo != null)
				return false;
		} else if (!testo.equals(other.testo))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ReviewNew [testo=" + testo + "]";
	}
	
}
