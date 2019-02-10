package old.info.campersites.bo;

import java.io.Serializable;
import java.util.Date;



public class UserPoiBo implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Long poiId;
	private Float latitude;
	private Float longitude;
	private Long userId;
	private String name;
	private String description;
	private Date inserted;
	private String icon;

	public UserPoiBo(Long poiId) {
		this.poiId = poiId;
	}

	public UserPoiBo() {
	}

	@Override
	public String toString() {
		return "UserPoiBo [poiId=" + poiId + ", userId=" + userId + ", name=" + name + "]";
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((poiId == null) ? 0 : poiId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof UserPoiBo)) {
			return false;
		}
		UserPoiBo other = (UserPoiBo) obj;
		if (poiId == null) {
			if (other.getPoiId() != null) {
				return false;
			}
		} else if (!poiId.equals(other.getPoiId())) {
			return false;
		}
		return true;
	}

	public Long getPoiId() {
		return poiId;
	}

	public void setPoiId(Long poiId) {
		this.poiId = poiId;
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

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getInserted() {
		return inserted;
	}

	public void setInserted(Date inserted) {
		this.inserted = inserted;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

}
