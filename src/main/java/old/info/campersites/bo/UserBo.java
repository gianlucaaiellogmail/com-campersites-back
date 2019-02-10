package old.info.campersites.bo;

import java.io.Serializable;
import java.util.Locale;

public class UserBo implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Long userId;
	private String email;
	private String nickname;
	private Locale locale;
	private Long totReviews;
	private Long totPreferiti;
	private String photoPath;
	private String remember;
	private String fbUserId;
	private String fbToken;
	private Integer newsletter;

	public UserBo(Long userId) {
		this.userId = userId;
	}

	public UserBo() {
	}

	@Override
	public String toString() {
		return "Utente [userId=" + userId + ", email=" + email + ", nickname=" + nickname + "]";
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
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
		if (!(obj instanceof UserBo)) {
			return false;
		}
		UserBo other = (UserBo) obj;
		if (userId == null) {
			if (other.getUserId() != null) {
				return false;
			}
		} else if (!userId.equals(other.getUserId())) {
			return false;
		}
		return true;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	public Long getTotReviews() {
		return totReviews;
	}

	public void setTotReviews(Long totReviews) {
		this.totReviews = totReviews;
	}

	public Long getTotPreferiti() {
		return totPreferiti;
	}

	public void setTotPreferiti(Long totPreferiti) {
		this.totPreferiti = totPreferiti;
	}

	public String getPhotoPath() {
		return photoPath;
	}

	public void setPhotoPath(String photoPath) {
		this.photoPath = photoPath;
	}

	public String getRemember() {
		return remember;
	}

	public void setRemember(String remember) {
		this.remember = remember;
	}

	public String getFbUserId() {
		return fbUserId;
	}

	public void setFbUserId(String fbUserId) {
		this.fbUserId = fbUserId;
	}

	public String getFbToken() {
		return fbToken;
	}

	public void setFbToken(String fbToken) {
		this.fbToken = fbToken;
	}

	public Integer getNewsletter() {
		return newsletter;
	}

	public void setNewsletter(Integer newsletter) {
		this.newsletter = newsletter;
	}

}
