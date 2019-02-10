package info.campersites.service;

import java.util.List;

import info.campersites.bo.UserPoiBo;

public interface UserPoiService {

	List<UserPoiBo> getUserPoisBound(String bounds, Long userId);

	UserPoiBo modify(UserPoiBo userPoiBo, Long userId);

	boolean delete(Long poiId);

}
