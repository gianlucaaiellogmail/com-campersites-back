package info.campersites.entity;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "reviews")
public class ReviewEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "review_id")
	private Long reviewId;
	
	@Column(name = "stop_id")
	private Long stopPointId;

	@Column(name = "review_title", length = 100)
	private String reviewTitle;

	@Column(name = "review", length = 2000)
	private String review;

	@Column(name = "rating")
	private Float rating;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "inserted")
	private Date inserted = Calendar.getInstance().getTime();

	@Column(name = "inserted_by_user")
	private Long insertedUserId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "modified")
	private Date modified = Calendar.getInstance().getTime();

	@Column(name = "modified_by_user")
	private Long modifiedUserId;

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

	public String getReviewTitle() {
		return reviewTitle;
	}

	public void setReviewTitle(String reviewTitle) {
		this.reviewTitle = reviewTitle;
	}

	public String getReview() {
		return review;
	}

	public void setReview(String review) {
		this.review = review;
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

	public Long getInsertedUserId() {
		return insertedUserId;
	}

	public void setInsertedUserId(Long insertedUserId) {
		this.insertedUserId = insertedUserId;
	}

	public Date getModified() {
		return modified;
	}

	public void setModified(Date modified) {
		this.modified = modified;
	}

	public Long getModifiedUserId() {
		return modifiedUserId;
	}

	public void setModifiedUserId(Long modifiedUserId) {
		this.modifiedUserId = modifiedUserId;
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
		ReviewEntity other = (ReviewEntity) obj;
		if (reviewId == null) {
			if (other.reviewId != null)
				return false;
		} else if (!reviewId.equals(other.reviewId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ReviewEntity [reviewId=" + reviewId + ", stopPointId=" + stopPointId + ", reviewTitle=" + reviewTitle
				+ ", review=" + review + ", rating=" + rating + ", inserted=" + inserted + ", insertedUserId="
				+ insertedUserId + ", modified=" + modified + ", modifiedUserId=" + modifiedUserId + "]";
	}

}
