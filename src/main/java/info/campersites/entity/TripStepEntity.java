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
@Table(name = "trip_steps")
public class TripStepEntity implements Serializable {
	
	public TripStepEntity() {
	}

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "step_id")
	private Long stepId;
	
	@Column(name = "trip_id")
	private Long tripId;

	@Column(name = "name", length = 100)
	private String name;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "modified")
	private Date modified = Calendar.getInstance().getTime();

	public Long getStepId() {
		return stepId;
	}

	public void setStepId(Long stepId) {
		this.stepId = stepId;
	}

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

	public Date getModified() {
		return modified;
	}

	public void setModified(Date modified) {
		this.modified = modified;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((stepId == null) ? 0 : stepId.hashCode());
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
		TripStepEntity other = (TripStepEntity) obj;
		if (stepId == null) {
			if (other.stepId != null)
				return false;
		} else if (!stepId.equals(other.stepId))
			return false;
		if (tripId == null) {
			if (other.tripId != null)
				return false;
		} else if (!tripId.equals(other.tripId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "TripStepEntity [stepId=" + stepId + ", tripId=" + tripId + ", name=" + name + ", modified=" + modified
				+ "]";
	}

}
