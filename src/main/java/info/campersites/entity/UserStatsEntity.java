package info.campersites.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user_stats")
public class UserStatsEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "user_id")
	private Long userId;

	@Column(name = "nickname")
	private String nickname;
	
	@Column(name = "fb_user_id")
	private String fbUserId;
	
	@Column(name = "score")
	private BigDecimal score;

	public Long getUserId() {
		return userId;
	}

	public String getNickname() {
		return nickname;
	}

	public String getFbUserId() {
		return fbUserId;
	}

	public BigDecimal getScore() {
		return score;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fbUserId == null) ? 0 : fbUserId.hashCode());
		result = prime * result + ((nickname == null) ? 0 : nickname.hashCode());
		result = prime * result + ((score == null) ? 0 : score.hashCode());
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
		UserStatsEntity other = (UserStatsEntity) obj;
		if (fbUserId == null) {
			if (other.fbUserId != null)
				return false;
		} else if (!fbUserId.equals(other.fbUserId))
			return false;
		if (nickname == null) {
			if (other.nickname != null)
				return false;
		} else if (!nickname.equals(other.nickname))
			return false;
		if (score == null) {
			if (other.score != null)
				return false;
		} else if (!score.equals(other.score))
			return false;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "UserStatsEntity [userId=" + userId + ", nickname=" + nickname + ", fbUserId=" + fbUserId + ", score="
				+ score + "]";
	}
	
}
