package old.info.campersites.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.HtmlUtils;

import info.campersites.entity.UserPoiEntity;
import info.campersites.repository.UserPoiRepository;
import old.info.campersites.bo.UserPoiBo;
import old.info.campersites.service.OldUserPoiService;

@Service
@Transactional(readOnly = true)
public class OldUserPoiServiceImpl implements OldUserPoiService {
	 
    @Autowired
	private UserPoiRepository userPoiRepository;
	
	@Override
    public List<UserPoiBo> getUserPoisBound(String bounds, Long userId) {
		List<UserPoiBo> userPoisBo = new ArrayList<UserPoiBo>();
		//Returns a string of the form "lat_lo,lng_lo,lat_hi,lng_hi" for this bounds, 
		//where "lo" corresponds to the southwest corner of the bounding box, while "hi" corresponds to the northeast corner of that box.
		String[] point = bounds.split(",");
		Float minLatitude = Float.valueOf(point[0]);
		Float minLongitude = Float.valueOf(point[1]);
		Float maxLatitude = Float.valueOf(point[2]);
		Float maxLongitude = Float.valueOf(point[3]);
    	List<UserPoiEntity> userPoisEntity = userPoiRepository.findByLatitudeBetweenAndLongitudeBetweenAndUserId(minLatitude, maxLatitude, minLongitude, maxLongitude, userId);
    	for (UserPoiEntity userPoiEntity : userPoisEntity) {
			UserPoiBo userPoiBo = new UserPoiBo(userPoiEntity.getPoiId());
			userPoiBo.setLatitude(userPoiEntity.getLatitude());
			userPoiBo.setLongitude(userPoiEntity.getLongitude());
			userPoiBo.setUserId(userPoiEntity.getUserId());
			userPoiBo.setName(HtmlUtils.htmlUnescape(userPoiEntity.getName()));
			userPoiBo.setDescription(HtmlUtils.htmlUnescape(userPoiEntity.getDescription()));
			userPoiBo.setIcon(userPoiEntity.getIcon());
			userPoisBo.add(userPoiBo);
    	}
        return userPoisBo;
    }

}
