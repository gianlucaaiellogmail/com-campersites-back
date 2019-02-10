package info.campersites.bo;

import java.io.Serializable;
import java.util.Date;



public class PositionMarkerBo implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Long positionId;
	private Float latitude;
	private Float longitude;
	private String userNickname;
	private String userLocale;
	private Date lastUpdate;
	
	public Long getPositionId() {
		return positionId;
	}
	public void setPositionId(Long positionId) {
		this.positionId = positionId;
	}
	public Float getLatitude() {
		return latitude;
	}
	public void setLatitude(Float latitude) {
		this.latitude = latitude;
	}
	public Float getLongitude() {
		return longitude;
	}
	public void setLongitude(Float longitude) {
		this.longitude = longitude;
	}
	public String getUserNickname() {
		return userNickname;
	}
	public void setUserNickname(String userNickname) {
		this.userNickname = userNickname;
	}
	public String getUserLocale() {
		return userLocale;
	}
	public void setUserLocale(String userLocale) {
		this.userLocale = userLocale;
	}
	public Date getLastUpdate() {
		return lastUpdate;
	}
	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((positionId == null) ? 0 : positionId.hashCode());
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
		PositionMarkerBo other = (PositionMarkerBo) obj;
		if (positionId == null) {
			if (other.positionId != null)
				return false;
		} else if (!positionId.equals(other.positionId))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "PositionMarkerBo [positionId=" + positionId + ", latitude=" + latitude + ", longitude=" + longitude
				+ ", userNickname=" + userNickname + ", userLocale=" + userLocale + ", lastUpdate=" + lastUpdate + "]";
	}

}
