package old.info.campersites.bo;

import java.io.Serializable;
import java.util.Date;



public class ReviewBo implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Long reviewId;
	private Long stopId;
	private Long userId;
	private String reviewTitle;
	private String review;
	private Float rating;
	private Date inserted;
	private String userNickname;
	private Long userReviews;
	private Long userTotPreferiti;
	private String userPhotoPath;
	private String fbUserId;
	
	public ReviewBo(Long reviewId) {
		this.reviewId = reviewId;
	}

	@Override
	public String toString() {
		return "ReviewBo [reviewId=" + reviewId + ", stopId=" + stopId + ", userNickname=" + userNickname + "]";
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((reviewId == null) ? 0 : reviewId.hashCode());
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
		if (!(obj instanceof ReviewBo)) {
			return false;
		}
		ReviewBo other = (ReviewBo) obj;
		if (reviewId == null) {
			if (other.getReviewId() != null) {
				return false;
			}
		} else if (!reviewId.equals(other.getReviewId())) {
			return false;
		}
		return true;
	}

	public Long getReviewId() {
		return reviewId;
	}

	public void setReviewId(Long reviewId) {
		this.reviewId = reviewId;
	}

	public Long getStopId() {
		return stopId;
	}

	public void setStopId(Long stopId) {
		this.stopId = stopId;
	}

	public String getReview() {
		return review;
	}

	public void setReview(String review) {
		this.review = review;
	}

	public String getReviewTitle() {
		return reviewTitle;
	}

	public void setReviewTitle(String reviewTitle) {
		this.reviewTitle = reviewTitle;
	}

	public Float getRating() {
		return rating;
	}

	public void setRating(Float rating) {
		this.rating = rating;
	}

	public Date getInserted() {
		return inserted;
	}

	public void setInserted(Date inserted) {
		this.inserted = inserted;
	}

	public String getUserNickname() {
		return userNickname;
	}

	public void setUserNickname(String userNickname) {
		this.userNickname = userNickname;
	}

	public Long getUserReviews() {
		return userReviews;
	}

	public void setUserReviews(Long userReviews) {
		this.userReviews = userReviews;
	}

	public Long getUserTotPreferiti() {
		return userTotPreferiti;
	}

	public void setUserTotPreferiti(Long userTotPreferiti) {
		this.userTotPreferiti = userTotPreferiti;
	}

	public String getUserPhotoPath() {
		return userPhotoPath;
	}

	public void setUserPhotoPath(String userPhotoPath) {
		this.userPhotoPath = userPhotoPath;
	}

	public String getFbUserId() {
		return fbUserId;
	}

	public void setFbUserId(String fbUserId) {
		this.fbUserId = fbUserId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

}
