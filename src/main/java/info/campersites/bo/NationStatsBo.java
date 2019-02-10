package info.campersites.bo;

import java.io.Serializable;
import java.util.Date;

public class NationStatsBo implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private String nation;
	private Long CA;
	private Long AC;
	private Long AA;
	private Long CS;
	private Long PS;
	private Long totAree;
	private Date lastDate;

	public String getNation() {
		return nation;
	}

	public void setNation(String nation) {
		this.nation = nation;
	}

	public Long getCA() {
		return CA;
	}

	public void setCA(Long cA) {
		CA = cA;
	}

	public Long getAC() {
		return AC;
	}

	public void setAC(Long aC) {
		AC = aC;
	}

	public Long getAA() {
		return AA;
	}

	public void setAA(Long aA) {
		AA = aA;
	}

	public Long getCS() {
		return CS;
	}

	public void setCS(Long cS) {
		CS = cS;
	}

	public Long getPS() {
		return PS;
	}

	public void setPS(Long pS) {
		PS = pS;
	}

	public Long getTotAree() {
		return totAree;
	}

	public void setTotAree(Long totAree) {
		this.totAree = totAree;
	}

	public Date getLastDate() {
		return lastDate;
	}

	public void setLastDate(Date lastDate) {
		this.lastDate = lastDate;
	}

	@Override
	public String toString() {
		return "NationStatsEntity [nation=" + nation + ", CA=" + CA + ", AC=" + AC + ", AA=" + AA + ", CS=" + CS
				+ ", PS=" + PS + ", totAree=" + totAree + ", lastDate=" + lastDate + "]";
	}

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
		NationStatsBo other = (NationStatsBo) obj;
		if (nation == null) {
			if (other.nation != null)
				return false;
		} else if (!nation.equals(other.nation))
			return false;
		return true;
	}

}
