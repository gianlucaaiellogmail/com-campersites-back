package old.info.campersites.bo;

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
	private boolean downloaded;
	private Date lastDate;

	public NationStatsBo() {
	}

	@Override
	public String toString() {
		return "NationStats [nation=" + nation + "]";
	}

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

	public boolean isDownloaded() {
		return downloaded;
	}

	public void setDownloaded(boolean downloaded) {
		this.downloaded = downloaded;
	}

	public Date getLastDate() {
		return lastDate;
	}

	public void setLastDate(Date lastDate) {
		this.lastDate = lastDate;
	}

}
