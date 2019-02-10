package info.campersites.bo;

import java.io.Serializable;

import org.hibernate.validator.constraints.NotEmpty;

public class UserFb implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@NotEmpty(message = Errors.REQUIRED)
	private String uid;
	
	@NotEmpty(message = Errors.REQUIRED)
	private String accessToken;

	@NotEmpty(message = Errors.REQUIRED)
	private String nickname;

	@NotEmpty(message = Errors.REQUIRED)
	private String photoPath;

	@NotEmpty(message = Errors.REQUIRED)
	private String locale;

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getPhotoPath() {
		return photoPath;
	}

	public void setPhotoPath(String photoPath) {
		this.photoPath = photoPath;
	}

	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((uid == null) ? 0 : uid.hashCode());
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
		UserFb other = (UserFb) obj;
		if (uid == null) {
			if (other.uid != null)
				return false;
		} else if (!uid.equals(other.uid))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "UserFb [uid=" + uid + ", accessToken=" + accessToken + ", nickname=" + nickname + ", photoPath="
				+ photoPath + ", locale=" + locale + "]";
	}

}
