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
@Table(name = "reviews_info")
public class ReviewsInfoEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "review_id")
	private Long reviewId;
	
	@Column(name = "stop_id")
	private Long stopId;

	@Column(name = "user_id")
	private Long userId;

	@Column(name = "review", length = 2000)
	private String review;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "inserted")
	private Date inserted = Calendar.getInstance().getTime();

	@Column(name = "nickname")
	private String userNickname;

	@Column(name = "tot_reviews")
	private Long userReviews;

	@Column(name = "locale")
	private String userLocale;

	public Long getReviewId() {
		return reviewId;
	}

	public Long getStopId() {
		return stopId;
	}

	public Long getUserId() {
		return userId;
	}

	public String getReview() {
		return review;
	}

	public Date getInserted() {
		return inserted;
	}

	public String getUserNickname() {
		return userNickname;
	}

	public Long getUserReviews() {
		return userReviews;
	}

	public String getUserLocale() {
		return userLocale;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((inserted == null) ? 0 : inserted.hashCode());
		result = prime * result + ((review == null) ? 0 : review.hashCode());
		result = prime * result + ((reviewId == null) ? 0 : reviewId.hashCode());
		result = prime * result + ((stopId == null) ? 0 : stopId.hashCode());
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
		result = prime * result + ((userLocale == null) ? 0 : userLocale.hashCode());
		result = prime * result + ((userNickname == null) ? 0 : userNickname.hashCode());
		result = prime * result + ((userReviews == null) ? 0 : userReviews.hashCode());
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
		ReviewsInfoEntity other = (ReviewsInfoEntity) obj;
		if (inserted == null) {
			if (other.inserted != null)
				return false;
		} else if (!inserted.equals(other.inserted))
			return false;
		if (review == null) {
			if (other.review != null)
				return false;
		} else if (!review.equals(other.review))
			return false;
		if (reviewId == null) {
			if (other.reviewId != null)
				return false;
		} else if (!reviewId.equals(other.reviewId))
			return false;
		if (stopId == null) {
			if (other.stopId != null)
				return false;
		} else if (!stopId.equals(other.stopId))
			return false;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		if (userLocale == null) {
			if (other.userLocale != null)
				return false;
		} else if (!userLocale.equals(other.userLocale))
			return false;
		if (userNickname == null) {
			if (other.userNickname != null)
				return false;
		} else if (!userNickname.equals(other.userNickname))
			return false;
		if (userReviews == null) {
			if (other.userReviews != null)
				return false;
		} else if (!userReviews.equals(other.userReviews))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ReviewsInfoEntity [reviewId=" + reviewId + ", stopId=" + stopId + ", userId=" + userId
				+ ", review=" + review + ", inserted=" + inserted + ", userNickname=" + userNickname + ", userReviews="
				+ userReviews + ", userLocale=" + userLocale + "]";
	}

}
