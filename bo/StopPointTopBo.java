package info.campersites.bo;

import java.io.Serializable;

import info.campersites.enumerator.EStopPointTypeId;



public class StopPointTopBo implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private Long stopId;
	private String description;
	private String typeId;
	private String locality;
	private String nation;
	private Integer rating;
	
	public Long getStopId() {
		return stopId;
	}
	
	public void setStopId(Long stopId) {
		this.stopId = stopId;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getTypeId() {
		return typeId;
	}
	
	public void setTypeId(EStopPointTypeId typeId) {
		this.typeId = typeId.getValue();
	}

	public String getLocality() {
		return locality;
	}
	
	public void setLocality(String locality) {
		this.locality = locality;
	}
	
	public String getNation() {
		return nation;
	}
	
	public void setNation(String nation) {
		this.nation = nation;
	}
	
	public Integer getRating() {
		return rating;
	}
	
	public void setRating(Integer rating) {
		this.rating = rating;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((stopId == null) ? 0 : stopId.hashCode());
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
		StopPointTopBo other = (StopPointTopBo) obj;
		if (stopId == null) {
			if (other.stopId != null)
				return false;
		} else if (!stopId.equals(other.stopId))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "StopPointNewestBo [stopId=" + stopId + ", description=" + description + ", typeId=" + typeId
				+ ", locality=" + locality + ", nation=" + nation + ", rating=" + rating + "]";
	}

}
