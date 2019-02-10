package info.campersites.entity;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "trips")
public class TripEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "trip_id")
	private Long tripId;
	
	@Column(name = "user_id")
	private Long userId;

	@Column(name = "partenza", length = 100)
	private String partenza;

	@Column(name = "arrivo", length = 100)
	private String arrivo;

	@Column(name = "name", length = 100)
	private String name;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "modified")
	private Date modified = Calendar.getInstance().getTime();

	@Column(name = "no_autostrade")
	private Integer noAutostrade;

	@Column(name = "no_pedaggi")
	private Integer noPedaggi;
	
	@Column(name = "no_traghetti")
	private Integer noTraghetti;

	public Long getTripId() {
		return tripId;
	}

	public void setTripId(Long tripId) {
		this.tripId = tripId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getModified() {
		return modified;
	}

	public void setModified(Date modified) {
		this.modified = modified;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((tripId == null) ? 0 : tripId.hashCode());
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
		TripEntity other = (TripEntity) obj;
		if (tripId == null) {
			if (other.tripId != null)
				return false;
		} else if (!tripId.equals(other.tripId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "TripEntity [tripId=" + tripId + ", userId=" + userId + ", partenza=" + partenza + ", arrivo=" + arrivo
				+ ", name=" + name + ", modified=" + modified + ", noAutostrade=" + noAutostrade + ", noPedaggi="
				+ noPedaggi + ", noTraghetti=" + noTraghetti + "]";
	}

}
