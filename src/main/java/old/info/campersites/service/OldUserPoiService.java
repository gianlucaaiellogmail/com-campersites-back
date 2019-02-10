package old.info.campersites.service;

import java.util.List;

import old.info.campersites.bo.UserPoiBo;

public interface OldUserPoiService {
	 
    List<UserPoiBo> getUserPoisBound(String bounds, Long userId);

}
