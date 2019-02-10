package info.campersites.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "configuration")
public class ConfigurationEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;

	public static final String IMAGES_DIRECTORY = "images_directory";
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "chiave")
	private String chiave;
	
	@Column(name = "valore")
	private String valore;

	public String getChiave() {
		return chiave;
	}

	public String getValore() {
		return valore;
	}

	@Override
	public String toString() {
		return "ConfigurationEntity [chiave=" + chiave + ", valore=" + valore + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((chiave == null) ? 0 : chiave.hashCode());
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
		ConfigurationEntity other = (ConfigurationEntity) obj;
		if (chiave == null) {
			if (other.chiave != null)
				return false;
		} else if (!chiave.equals(other.chiave))
			return false;
		return true;
	}

}
