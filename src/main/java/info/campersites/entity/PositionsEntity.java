package info.campersites.entity;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "positions")
public class PositionsEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "position_id")
	private String positionId;
	
	@Column(name = "latitude")
	private Float latitude;

	@Column(name = "longitude")
	private Float longitude;

	@Column(name = "nickname", length = 200)
	private String userNickname;
	
	@Column(name = "locale", length = 2)
	private String userLocale;
	
	@Column(name = "user_id")
	private Long userId;

	@Column(name = "device_model")
	private String deviceModel;

	@Column(name = "device_platform")
	private String devicePlatform;

	@Column(name = "device_version")
	private String deviceVersion;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "last_update")
	private Date lastUpdate = Calendar.getInstance().getTime();

	public String getPositionId() {
		return positionId;
	}

	public void setPositionId(String positionId) {
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

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getDeviceModel() {
		return deviceModel;
	}

	public void setDeviceModel(String deviceModel) {
		this.deviceModel = deviceModel;
	}

	public String getDevicePlatform() {
		return devicePlatform;
	}

	public void setDevicePlatform(String devicePlatform) {
		this.devicePlatform = devicePlatform;
	}

	public String getDeviceVersion() {
		return deviceVersion;
	}

	public void setDeviceVersion(String deviceVersion) {
		this.deviceVersion = deviceVersion;
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
		PositionsEntity other = (PositionsEntity) obj;
		if (positionId == null) {
			if (other.positionId != null)
				return false;
		} else if (!positionId.equals(other.positionId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "PositionsEntity [positionId=" + positionId + ", latitude=" + latitude + ", longitude=" + longitude
				+ ", userNickname=" + userNickname + ", userLocale=" + userLocale + ", userId=" + userId
				+ ", deviceModel=" + deviceModel + ", devicePlatform=" + devicePlatform + ", deviceVersion="
				+ deviceVersion + ", lastUpdate=" + lastUpdate + "]";
	}

}
