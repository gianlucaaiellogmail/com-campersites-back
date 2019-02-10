package info.campersites.bo;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;



public class UserPoiBo implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Long poiId;

	@NotNull(message = Errors.REQUIRED)
	@Digits(integer = 2, fraction = 6, message = Errors.PATTERN)
	private Float latitude;

	@NotNull(message = Errors.REQUIRED)
	@Digits(integer = 2, fraction = 6, message = Errors.PATTERN)
	private Float longitude;

	@NotEmpty(message = Errors.REQUIRED)
	@Length(max=250, message = Errors.MAXLENGTH)
	private String name;

	@Length(max=1000, message = Errors.MAXLENGTH)
	private String description;
	
	private Date inserted;
	
	private String icon;
	
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((poiId == null) ? 0 : poiId.hashCode());
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
		UserPoiBo other = (UserPoiBo) obj;
		if (poiId == null) {
			if (other.poiId != null)
				return false;
		} else if (!poiId.equals(other.poiId))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "UserPoiBo [poiId=" + poiId + ", latitude=" + latitude + ", longitude=" + longitude + ", name=" + name + ", description=" + description + ", inserted=" + inserted + ", icon="
				+ icon + "]";
	}

}
