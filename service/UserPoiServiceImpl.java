package info.campersites.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.HtmlUtils;

import info.campersites.bo.UserPoiBo;
import info.campersites.entity.UserPoiEntity;
import info.campersites.repository.UserPoiRepository;

@Service
@Transactional(readOnly=true, propagation=Propagation.REQUIRED)
public class UserPoiServiceImpl implements UserPoiService {

	@Autowired
	UserPoiRepository userPoiRepository;
	
	@Override
	public List<UserPoiBo> getUserPoisBound(String bounds, Long userId) {
		//Returns a string of the form "lat_lo,lng_lo,lat_hi,lng_hi" for this bounds, 
		//where "lo" corresponds to the southwest corner of the bounding box, while "hi" corresponds to the northeast corner of that box.
		String[] point = bounds.split(",");
		Float minLatitude = Float.valueOf(point[0]);
		Float minLongitude = Float.valueOf(point[1]);
		Float maxLatitude = Float.valueOf(point[2]);
		Float maxLongitude = Float.valueOf(point[3]);
		List<UserPoiEntity> userPoisEntity = userPoiRepository.findByLatitudeBetweenAndLongitudeBetweenAndUserId(minLatitude, maxLatitude, minLongitude, maxLongitude, userId);
    	return toUserPoiBos(userPoisEntity);
	}

	@Override
	@Transactional(readOnly = false)
	public UserPoiBo modify(UserPoiBo userPoiBo, Long userId) {
		UserPoiEntity userPoiEntity = userPoiRepository.save(toEntity(userPoiBo, userId));
		return toUserPoiBo(userPoiEntity);
	}

	@Override
	@Transactional(readOnly = false)
	public boolean delete(Long poiId) {
		userPoiRepository.delete(poiId);
		return true;
	}
	
	private List<UserPoiBo> toUserPoiBos(List<UserPoiEntity> userPoisEntity) {
		List<UserPoiBo> userPoisBo = new ArrayList<UserPoiBo>();
		for (UserPoiEntity userPoiEntity : userPoisEntity) {
			userPoisBo.add(toUserPoiBo(userPoiEntity));
		}
		return userPoisBo;
	}

	private UserPoiBo toUserPoiBo(final UserPoiEntity userPoiEntity) {
		UserPoiBo userPoiBo = new UserPoiBo();
		BeanUtils.copyProperties(userPoiEntity, userPoiBo);
		userPoiBo.setDescription(HtmlUtils.htmlUnescape(userPoiBo.getDescription()));
		userPoiBo.setName(HtmlUtils.htmlUnescape(userPoiBo.getName()));
		return userPoiBo;
	}

	private UserPoiEntity toEntity(final UserPoiBo userPoiBo, final Long userId) {
		UserPoiEntity userPoiEntity = new UserPoiEntity();
		BeanUtils.copyProperties(userPoiBo, userPoiEntity);
		userPoiEntity.setUserId(userId);
		userPoiEntity.setInserted(Calendar.getInstance().getTime());
		return userPoiEntity;
	}

}
