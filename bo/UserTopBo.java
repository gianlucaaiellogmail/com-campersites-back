package info.campersites.bo;

import java.io.Serializable;
import java.math.BigDecimal;

public class UserTopBo implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Long userId;
	private String nickname;
	private String fbUserId;
	private BigDecimal score;
	
	public Long getUserId() {
		return userId;
	}
	
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	public String getNickname() {
		return nickname;
	}
	
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	public String getFbUserId() {
		return fbUserId;
	}
	
	public void setFbUserId(String fbUserId) {
		this.fbUserId = fbUserId;
	}
	
	public BigDecimal getScore() {
		return score;
	}
	
	public void setScore(BigDecimal score) {
		this.score = score;
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
		UserTopBo other = (UserTopBo) obj;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "UserTopBo [userId=" + userId + ", nickname=" + nickname + ", fbUserId=" + fbUserId + ", score="
				+ score + "]";
	}
	
}
