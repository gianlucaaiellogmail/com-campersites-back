package info.campersites.bo;

import java.io.Serializable;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;



public class PositionMod implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@NotEmpty(message = Errors.REQUIRED)
	private String positionId;

	@NotNull(message = Errors.REQUIRED)
	@Digits(integer = 2, fraction = 6, message = Errors.PATTERN)
	private Float latitude;

	@NotNull(message = Errors.REQUIRED)
	@Digits(integer = 3, fraction = 6, message = Errors.PATTERN)
	private Float longitude;

	private String userNickname;
	
	private String userLocale;

	private Long userId;

	private String deviceModel;

	private String devicePlatform;

	private String deviceVersion;

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
		PositionMod other = (PositionMod) obj;
		if (positionId == null) {
			if (other.positionId != null)
				return false;
		} else if (!positionId.equals(other.positionId))
			return false;
		return true;
	}

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

	@Override
	public String toString() {
		return "PositionMod [positionId=" + positionId + ", latitude=" + latitude + ", longitude=" + longitude
				+ ", userNickname=" + userNickname + ", userLocale=" + userLocale + ", , userId=" + userId + "deviceModel=" + deviceModel
				+ ", devicePlatform=" + devicePlatform + ", deviceVersion=" + deviceVersion + "]";
	}

}
