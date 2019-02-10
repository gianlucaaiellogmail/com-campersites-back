package old.info.campersites.jsonrequest;

import old.info.campersites.bo.OfflineEventsBo;
import old.info.campersites.bo.StopPointBo;
import old.info.campersites.bo.UserBo;

import java.util.ArrayList;
import java.util.List;

public class OfflineEventsJsonRequest {

    private UserBo user;
    private List<OfflineEventsBo> events = new ArrayList<OfflineEventsBo>();
    private List<StopPointBo> stopPoints = new ArrayList<StopPointBo>();

    public UserBo getUser() {
		return user;
	}
	public void setUser(UserBo user) {
		this.user = user;
	}
	public List<OfflineEventsBo> getEvents() {
		return events;
	}
	public void setEvents(List<OfflineEventsBo> events) {
		this.events = events;
	}
	public List<StopPointBo> getStopPoints() {
		return stopPoints;
	}
	public void setStopPoints(List<StopPointBo> stopPoints) {
		this.stopPoints = stopPoints;
	}

}
