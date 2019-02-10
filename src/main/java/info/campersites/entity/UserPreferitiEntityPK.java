package info.campersites.entity;

import java.io.Serializable;

public class UserPreferitiEntityPK implements Serializable {
	
	private static final long serialVersionUID = 1L;

	protected Long stopId;
	protected Long userId;

	public UserPreferitiEntityPK() {}

	public UserPreferitiEntityPK(Long stopId, Long userId) {
		this.stopId = stopId;
	    this.userId = userId;
	}

	@Override
	public int hashCode() {
		return stopId.hashCode() + userId.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof UserPreferitiEntityPK){
			UserPreferitiEntityPK userPreferitiEntityPK = (UserPreferitiEntityPK) obj;
            if(!userPreferitiEntityPK.getStopId().equals(stopId)){
                return false;
            }
            if(!userPreferitiEntityPK.getUserId().equals(userId)){
                return false;
            }
            return true;
        }
        return false;
	}

	public Long getStopId() {
		return stopId;
	}

	public void setStopId(Long stopId) {
		this.stopId = stopId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
}
