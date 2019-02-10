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

import info.campersites.bo.PositionMarkerBo;
import info.campersites.bo.PositionMod;
import info.campersites.entity.PositionsEntity;
import info.campersites.repository.PositionsRepository;

@Service
@Transactional(readOnly=true, propagation=Propagation.REQUIRED)
public class PositionsServiceImpl implements PositionsService {
	
	@Autowired
	PositionsRepository positionsRepository;

	@Override
	public List<PositionMarkerBo> getPositionsBound(final String bounds) {
		//Returns a string of the form "lat_lo,lng_lo,lat_hi,lng_hi" for this bounds, 
		//where "lo" corresponds to the southwest corner of the bounding box, while "hi" corresponds to the northeast corner of that box.
		String[] point = bounds.split(",");
		Float minLatitude = Float.valueOf(point[0]);
		Float minLongitude = Float.valueOf(point[1]);
		Float maxLatitude = Float.valueOf(point[2]);
		Float maxLongitude = Float.valueOf(point[3]);
		List<PositionsEntity> positionsEntity = positionsRepository.findByLatitudeBetweenAndLongitudeBetween(minLatitude, maxLatitude, minLongitude, maxLongitude);
    	return toMarkerBos(positionsEntity);
	}

	@Override
	@Transactional(readOnly = false)
	public PositionMod modify(final PositionMod positionMod) {
		PositionsEntity positionEntity = positionsRepository.save(toEntity(positionMod));
		return toBo(positionEntity);
	}

	private List<PositionMarkerBo> toMarkerBos(List<PositionsEntity> positionsEntity) {
		List<PositionMarkerBo> positionMarkers = new ArrayList<PositionMarkerBo>();
		for (PositionsEntity positionEntity : positionsEntity) {
			positionMarkers.add(toMarkerBo(positionEntity));
		}
		return positionMarkers;
	}

	private PositionMarkerBo toMarkerBo(final PositionsEntity positionEntity) {
		PositionMarkerBo positionMarker = new PositionMarkerBo();
		BeanUtils.copyProperties(positionEntity, positionMarker);
		if (positionMarker.getUserNickname() != null) positionMarker.setUserNickname(HtmlUtils.htmlUnescape(positionMarker.getUserNickname()));
		return positionMarker;
	}
	
	private PositionMod toBo(final PositionsEntity positionEntity) {
		PositionMod position = new PositionMod();
		BeanUtils.copyProperties(positionEntity, position);
		if (position.getUserNickname() != null) position.setUserNickname(HtmlUtils.htmlUnescape(position.getUserNickname()));
		return position;
	}

	private PositionsEntity toEntity(final PositionMod positionMod) {
		PositionsEntity positionEntity = null;
		if (positionMod.getPositionId() != null) {
			positionEntity = positionsRepository.findByPositionId(positionMod.getPositionId());
		}
		if (positionEntity == null) positionEntity = new PositionsEntity();
		BeanUtils.copyProperties(positionMod, positionEntity);
		positionEntity.setLastUpdate(Calendar.getInstance().getTime());
		return positionEntity;
	}

}
