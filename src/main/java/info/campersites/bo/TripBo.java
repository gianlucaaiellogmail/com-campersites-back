package info.campersites.bo;

import java.io.Serializable;
import java.util.List;



public class TripBo implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Long tripId;
	private String name;
	private String partenza;
	private String arrivo;
	private Integer noAutostrade;
	private Integer noPedaggi;
	private Integer noTraghetti;
	private List<String> tappe;
	
	public Long getTripId() {
		return tripId;
	}

	public void setTripId(Long tripId) {
		this.tripId = tripId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPartenza() {
		return partenza;
	}

	public void setPartenza(String partenza) {
		this.partenza = partenza;
	}

	public String getArrivo() {
		return arrivo;
	}

	public void setArrivo(String arrivo) {
		this.arrivo = arrivo;
	}

	public Integer getNoAutostrade() {
		return noAutostrade;
	}

	public void setNoAutostrade(Integer noAutostrade) {
		this.noAutostrade = noAutostrade;
	}

	public Integer getNoPedaggi() {
		return noPedaggi;
	}

	public void setNoPedaggi(Integer noPedaggi) {
		this.noPedaggi = noPedaggi;
	}

	public Integer getNoTraghetti() {
		return noTraghetti;
	}

	public void setNoTraghetti(Integer noTraghetti) {
		this.noTraghetti = noTraghetti;
	}

	public List<String> getTappe() {
		return tappe;
	}

	public void setTappe(List<String> tappe) {
		this.tappe = tappe;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((arrivo == null) ? 0 : arrivo.hashCode());
		result = prime * result + ((partenza == null) ? 0 : partenza.hashCode());
		result = prime * result + ((tappe == null) ? 0 : tappe.hashCode());
		result = prime * result + ((tripId == null) ? 0 : tripId.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		TripBo other = (TripBo) obj;
		if (arrivo == null) {
			if (other.arrivo != null)
				return false;
		} else if (!arrivo.equals(other.arrivo))
			return false;
		if (partenza == null) {
			if (other.partenza != null)
				return false;
		} else if (!partenza.equals(other.partenza))
			return false;
		if (tappe == null) {
			if (other.tappe != null)
				return false;
		} else if (!tappe.equals(other.tappe))
			return false;
		if (tripId == null) {
			if (other.tripId != null)
				return false;
		} else if (!tripId.equals(other.tripId))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "TripBo [tripId=" + tripId + ", name=" + name + ", partenza=" + partenza + ", arrivo=" + arrivo
				+ ", noAutostrade=" + noAutostrade + ", noPedaggi=" + noPedaggi + ", noTraghetti=" + noTraghetti
				+ ", tappe=" + tappe + "]";
	}

}
