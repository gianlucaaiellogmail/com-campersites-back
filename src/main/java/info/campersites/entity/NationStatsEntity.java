package info.campersites.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "nation_stats")
public class NationStatsEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "nation", length = 2)
	private String nation;

	@Column(name = "CA")
	private Long CA;
	
	@Column(name = "AC")
	private Long AC;
	
	@Column(name = "AA")
	private Long AA;
	
	@Column(name = "CS")
	private Long CS;
	
	@Column(name = "PS")
	private Long PS;
	
	@Column(name = "totAree")
	private Long totAree;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "last_date")
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
		NationStatsEntity other = (NationStatsEntity) obj;
		if (nation == null) {
			if (other.nation != null)
				return false;
		} else if (!nation.equals(other.nation))
			return false;
		return true;
	}

}
