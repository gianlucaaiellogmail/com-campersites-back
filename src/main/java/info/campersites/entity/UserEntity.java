package info.campersites.entity;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "users")
public class UserEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "user_id")
	private Long userId;
	
	@Column(name = "email", length = 200)
	private String email;

	@Column(name = "password", length = 50)
	private String password;

	@Column(name = "nickname", length = 200)
	private String nickname;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "last_login")
	private Date lastLogin;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "last_change")
	private Date lastChange = Calendar.getInstance().getTime();

	@Column(name = "locale", length = 2)
	private String locale;

	@Column(name = "tot_reviews")
	private Long totReviews = 0L;

	@Column(name = "tot_stoppoints")
	private Long totStopPoints = 0L;

	@Column(name = "tot_preferiti")
	private Long totPreferiti = 0L;

	@Column(name = "photo_path", length = 150)
	private String photoPath;

	@Column(name = "activation", length = 45)
	private String activation = UUID.randomUUID().toString();

	@Column(name = "remember", length = 45)
	private String remember;

	@Column(name = "fb_user_id", length = 45)
	private String fbUserId;

	@Column(name = "fb_token", length = 300)
	private String fbToken;

	@Column(name = "restore_pwd", length = 45)
	private String restorePwd;
	
	@Column(name = "newsletter")
	private Integer newsletter = 1;
	
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public Date getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}

	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

	public Date getLastChange() {
		return lastChange;
	}

	public void setLastChange(Date lastChange) {
		this.lastChange = lastChange;
	}

	public String getPhotoPath() {
		return photoPath;
	}

	public void setPhotoPath(String photoPath) {
		this.photoPath = photoPath;
	}

	public Long getTotReviews() {
		return totReviews;
	}

	public void setTotReviews(Long totReviews) {
		this.totReviews = totReviews;
	}

	public Long getTotStopPoints() {
		return totStopPoints;
	}

	public void setTotStopPoints(Long totStopPoints) {
		this.totStopPoints = totStopPoints;
	}

	public Long getTotPreferiti() {
		return totPreferiti;
	}

	public void setTotPreferiti(Long totPreferiti) {
		this.totPreferiti = totPreferiti;
	}

	public String getActivation() {
		return activation;
	}

	public void setActivation(String activation) {
		this.activation = activation;
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

	public String getRestorePwd() {
		return restorePwd;
	}

	public void setRestorePwd(String restorePwd) {
		this.restorePwd = restorePwd;
	}

	public Integer getNewsletter() {
		return newsletter;
	}

	public void setNewsletter(Integer newsletter) {
		this.newsletter = newsletter;
	}

	@Override
	public String toString() {
		return "UserEntity [userId=" + userId + ", email=" + email + ", password=" + password + ", nickname=" + nickname
				+ ", lastLogin=" + lastLogin + ", lastChange=" + lastChange + ", locale=" + locale + ", totReviews="
				+ totReviews + ", totStopPoints=" + totStopPoints + ", totPreferiti=" + totPreferiti + ", photoPath=" + photoPath + ", activation="
				+ activation + ", remember=" + remember + ", fbUserId=" + fbUserId + ", fbToken=" + fbToken
				+ ", restorePwd=" + restorePwd + ", newsletter=" + newsletter + "]";
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
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserEntity other = (UserEntity) obj;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		return true;
	}

}
