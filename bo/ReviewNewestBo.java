package info.campersites.bo;

import java.io.Serializable;
import java.util.Date;



public class ReviewNewestBo implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Long reviewId;
	private Long stopPointId;
	private String review;
	private String user;
	private Date modified;
	
	public Long getReviewId() {
		return reviewId;
	}
	
	public void setReviewId(Long reviewId) {
		this.reviewId = reviewId;
	}
	
	public Long getStopPointId() {
		return stopPointId;
	}
	
	public void setStopPointId(Long stopPointId) {
		this.stopPointId = stopPointId;
	}
	
	public String getReview() {
		return review;
	}
	
	public void setReview(String review) {
		this.review = review;
	}
	
	public String getUser() {
		return user;
	}
	
	public void setUser(String user) {
		this.user = user;
	}
	
	public Date getModified() {
		return modified;
	}
	
	public void setModified(Date modified) {
		this.modified = modified;
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
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ReviewNewestBo other = (ReviewNewestBo) obj;
		if (reviewId == null) {
			if (other.reviewId != null)
				return false;
		} else if (!reviewId.equals(other.reviewId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ReviewNewestBo [reviewId=" + reviewId + ", stopPointId=" + stopPointId + ", review=" + review
				+ ", user=" + user + ", modified=" + modified + "]";
	}
	
}
