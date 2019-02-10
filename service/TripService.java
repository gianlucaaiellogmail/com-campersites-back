package info.campersites.service;

import java.util.List;

import info.campersites.bo.TripBo;

public interface TripService {
	 
	List<TripBo> getTripsByUser(Long userId);
	TripBo save(TripBo tripBo, Long userId);
	
}
