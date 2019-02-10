package old.info.campersites.bo;

import java.io.Serializable;
import java.util.Date;

public class OfflineEventsBo implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Long stopId;
	private Long userId;
	private String func;
	private Float rating;
	private String review;
	private Date modified;

	@Override
	public String toString() {
		return "OfflineEvents [userId=" + userId + ", stopId=" + stopId + ", func=" + func + "]";
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((stopId == null) ? 0 : stopId.hashCode()) + ((userId == null) ? 0 : userId.hashCode());
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
		if (!(obj instanceof OfflineEventsBo)) {
			return false;
		}
		OfflineEventsBo other = (OfflineEventsBo) obj;
		if (userId == null) {
			if (other.getUserId() != null) {
				return false;
			}
		} else if (!userId.equals(other.getUserId())) {
			return false;
		}
		if (stopId == null) {
			if (other.getStopId() != null) {
				return false;
			}
		} else if (!stopId.equals(other.getStopId())) {
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

	public Long getStopId() {
		return stopId;
	}

	public void setStopId(Long stopId) {
		this.stopId = stopId;
	}

	public String getFunc() {
		return func;
	}

	public void setFunc(String func) {
		this.func = func;
	}

	public Float getRating() {
		return rating;
	}

	public void setRating(Float rating) {
		this.rating = rating;
	}

	public String getReview() {
		return review;
	}

	public void setReview(String review) {
		this.review = review;
	}

	public Date getModified() {
		return modified;
	}

	public void setModified(Date modified) {
		this.modified = modified;
	}

}
