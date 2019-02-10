package info.campersites.bo;

import java.io.Serializable;

public class NationBo implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String nation;
	private Long stopPoints;
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nation == null) ? 0 : nation.hashCode());
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
		NationBo other = (NationBo) obj;
		if (nation == null) {
			if (other.nation != null)
				return false;
		} else if (!nation.equals(other.nation))
			return false;
		return true;
	}

	public String getNation() {
		return nation;
	}

	public void setNation(String nation) {
		this.nation = nation;
	}

	public Long getStopPoints() {
		return stopPoints;
	}

	public void setStopPoints(Long stopPoints) {
		this.stopPoints = stopPoints;
	}

	@Override
	public String toString() {
		return "NationBo [nation=" + nation + ", stopPoints=" + stopPoints + "]";
	}
	
}
