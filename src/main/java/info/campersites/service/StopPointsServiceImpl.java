package info.campersites.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.HtmlUtils;

import info.campersites.bo.StopPointBo;
import info.campersites.bo.StopPointMarkerBo;
import info.campersites.bo.StopPointMod;
import info.campersites.bo.StopPointNewestBo;
import info.campersites.bo.StopPointTopBo;
import info.campersites.entity.StopPointsEntity;
import info.campersites.entity.UserEntity;
import info.campersites.entity.UserPreferitiEntity;
import info.campersites.enumerator.EStopPointPlaceTypeId;
import info.campersites.enumerator.EStopPointTypeId;
import info.campersites.enumerator.EValuta;
import info.campersites.repository.StopPointsRepository;
import info.campersites.repository.UserPreferitiRepository;
import info.campersites.repository.UserRepository;

@Service
@Transactional(readOnly=true, propagation=Propagation.REQUIRED)
public class StopPointsServiceImpl implements StopPointsService {
	
	private static final int PAGE_SIZE = 40;
	
	@Autowired
	StopPointsRepository stopPointsRepository;
	@Autowired
	ReviewService reviewService;
	@Autowired
	ImageService imageService;
	@Autowired
	UserService userService;
	@Autowired
	UserPreferitiService userPreferitiService;
	@Autowired
	UserPreferitiRepository userPreferitiRepository;
	@Autowired
	UserRepository userRepository;

	@Override
	public List<StopPointMarkerBo> getStopPointsBound(final String bounds, final boolean visibile) {
		//Returns a string of the form "lat_lo,lng_lo,lat_hi,lng_hi" for this bounds, 
		//where "lo" corresponds to the southwest corner of the bounding box, while "hi" corresponds to the northeast corner of that box.
		String[] point = bounds.split(",");
		Float minLatitude = Float.valueOf(point[0]);
		Float minLongitude = Float.valueOf(point[1]);
		Float maxLatitude = Float.valueOf(point[2]);
		Float maxLongitude = Float.valueOf(point[3]);
		List<StopPointsEntity> stopPointsEntity = null;
    	if (visibile) {
    		stopPointsEntity = stopPointsRepository.findByLatitudeBetweenAndLongitudeBetween(minLatitude, maxLatitude, minLongitude, maxLongitude);
    	} else {
    		stopPointsEntity = stopPointsRepository.findByLatitudeBetweenAndLongitudeBetweenAndVisibile(minLatitude, maxLatitude, minLongitude, maxLongitude, 1);
    	}
    	return toMarkerBos(stopPointsEntity, visibile);
	}

	@Override
	@Cacheable(value="stopPointsNewestCache")
	public List<StopPointNewestBo> getStopPointsNewest() {
		List<StopPointsEntity> stopPointsEntity = stopPointsRepository.findTop10ByOrderByModifiedDescInsertedAsc();
    	return toNewestBos(stopPointsEntity);
	}

	@Override
	@Cacheable(value="stopPointsTopCache")
	public List<StopPointTopBo> getStopPointsTop() {
		List<StopPointsEntity> stopPointsEntity = stopPointsRepository.findTop10ByOrderByRatingDescModifiedDesc();
    	return toTopBos(stopPointsEntity);
	}

	@Override
	public StopPointBo getStopPoint(final Long stopId, final Long userId) {
		StopPointsEntity stopPointEntity = stopPointsRepository.findOne(stopId);
		StopPointBo stopPoint = new StopPointBo();
		if (stopPointEntity != null) {
			stopPoint = toBo(stopPointEntity, userId);
			stopPoint.setReviews(reviewService.getByStopPoint(stopPoint.getStopId()));
			stopPoint.setPhotos(imageService.getByStopPoint(stopPoint.getStopId()));
		}
    	return stopPoint;
	}

	@Override
	public Page<StopPointMarkerBo> getByNation(final String nation, final Integer page) {
		Pageable request = new PageRequest(page - 1, PAGE_SIZE, Sort.Direction.DESC, "rating");
		Page<StopPointsEntity> stopPointsEntity = stopPointsRepository.findByNation(nation, request);
		List<StopPointMarkerBo> stopPointMarkers = toMarkerBos(stopPointsEntity.getContent(), false);
		return new PageImpl<StopPointMarkerBo>(stopPointMarkers, request, stopPointsEntity.getTotalElements());
	}
	
	@Override
	@Transactional(readOnly = false)
	@CacheEvict(value = {"nationStatCache", "oldNationStatCache", "stopPointsNewestCache", "usersTopCache"}, allEntries = true)
	public StopPointBo modify(final StopPointMod stopPointMod, final Long userId) {
		StopPointsEntity stopPointEntity = stopPointsRepository.save(toEntity(stopPointMod, userId));
		
		// Aggiorno numero di aree modificate utente
		UserEntity userEntity = userRepository.findOne(userId);
		userEntity.setTotStopPoints(userEntity.getTotStopPoints()+1);
		userRepository.save(userEntity);
		
		return toBo(stopPointEntity, userId);
	}

	@Override
	public List<StopPointMarkerBo> getPreferiti(Long userId) {
		List<StopPointsEntity> stopPointsEntity = new ArrayList<StopPointsEntity>();
    	List<UserPreferitiEntity> userPreferitiEntity = userPreferitiRepository.findByUserId(userId);
    	List<Long> stopIds = new ArrayList<Long>();
    	for (UserPreferitiEntity userPreferitoEntity : userPreferitiEntity) {
    		stopIds.add(userPreferitoEntity.getStopId());
    	}
    	if (!stopIds.isEmpty()) {
    		stopPointsEntity = stopPointsRepository.findByStopIdIn(stopIds);
    	}
    	return toMarkerBos(stopPointsEntity, false);
	}

	private List<StopPointMarkerBo> toMarkerBos(List<StopPointsEntity> stopPointsEntity, final boolean visibile) {
		List<StopPointMarkerBo> stopPointMarkers = new ArrayList<StopPointMarkerBo>();
		for (StopPointsEntity stopPointEntity : stopPointsEntity) {
			if (visibile || (!visibile && stopPointEntity.getVisibile() == 1)) {
				stopPointMarkers.add(toMarkerBo(stopPointEntity));
			}
		}
		return stopPointMarkers;
	}

	private StopPointMarkerBo toMarkerBo(final StopPointsEntity stopPointEntity) {
		StopPointMarkerBo stopPointMarker = new StopPointMarkerBo();
		BeanUtils.copyProperties(stopPointEntity, stopPointMarker);
		stopPointMarker.setPrezzo("-");
		float totale = 0;
		if (stopPointEntity.getPrezzoGiornaliero() != null) {
			stopPointMarker.setPrezzo(stopPointEntity.getPrezzoGiornaliero().toString());
			totale = totale + stopPointEntity.getPrezzoGiornaliero();
		} else if (stopPointEntity.getPrezzoOrario() != null) {
			stopPointMarker.setPrezzo(stopPointEntity.getPrezzoOrario().toString());
			totale = totale + stopPointEntity.getPrezzoOrario();
		} else if (stopPointEntity.getPrezzoNotturno() != null) {
			stopPointMarker.setPrezzo(stopPointEntity.getPrezzoNotturno().toString());
			totale = totale + stopPointEntity.getPrezzoNotturno();
		} else if (stopPointEntity.getPrezzoSettimanale() != null) {
			stopPointMarker.setPrezzo(stopPointEntity.getPrezzoSettimanale().toString());
			totale = totale + stopPointEntity.getPrezzoSettimanale();
		} else if (StringUtils.isNotBlank(stopPointEntity.getPrezzoParticolare())) {
			stopPointMarker.setPrezzo(stopPointEntity.getPrezzoParticolare().toString());
		}
		if (totale == 0 && StringUtils.isBlank(stopPointEntity.getPrezzoParticolare()))
			stopPointMarker.setPrezzo("0");
		stopPointMarker.setDescription(HtmlUtils.htmlUnescape(stopPointMarker.getDescription()));
		stopPointMarker.setLocality(HtmlUtils.htmlUnescape(stopPointMarker.getLocality()));
		stopPointMarker.setRating(Math.round(((stopPointEntity.getRating() * 100) / 5)));
		return stopPointMarker;
	}
	
	private List<StopPointNewestBo> toNewestBos(List<StopPointsEntity> stopPointsEntity) {
		List<StopPointNewestBo> stopPointNewest = new ArrayList<StopPointNewestBo>();
		for (StopPointsEntity stopPointEntity : stopPointsEntity) {
			stopPointNewest.add(toNewestBo(stopPointEntity));
		}
		return stopPointNewest;
	}

	private StopPointNewestBo toNewestBo(final StopPointsEntity stopPointEntity) {
		StopPointNewestBo stopPointNewest = new StopPointNewestBo();
		BeanUtils.copyProperties(stopPointEntity, stopPointNewest);
		stopPointNewest.setDescription(HtmlUtils.htmlUnescape(stopPointNewest.getDescription()));
		stopPointNewest.setLocality(HtmlUtils.htmlUnescape(stopPointNewest.getLocality()));
		return stopPointNewest;
	}
	
	private List<StopPointTopBo> toTopBos(List<StopPointsEntity> stopPointsEntity) {
		List<StopPointTopBo> stopPointTop = new ArrayList<StopPointTopBo>();
		for (StopPointsEntity stopPointEntity : stopPointsEntity) {
			stopPointTop.add(toTopBo(stopPointEntity));
		}
		return stopPointTop;
	}

	private StopPointTopBo toTopBo(final StopPointsEntity stopPointEntity) {
		StopPointTopBo stopPointTop = new StopPointTopBo();
		BeanUtils.copyProperties(stopPointEntity, stopPointTop);
		stopPointTop.setDescription(HtmlUtils.htmlUnescape(stopPointTop.getDescription()));
		stopPointTop.setLocality(HtmlUtils.htmlUnescape(stopPointTop.getLocality()));
		stopPointTop.setRating(Math.round(((stopPointEntity.getRating() * 100) / 5)));
		return stopPointTop;
	}
	
	private StopPointBo toBo(final StopPointsEntity stopPointEntity, final Long userId) {
		StopPointBo stopPoint = new StopPointBo();
		BeanUtils.copyProperties(stopPointEntity, stopPoint);
		stopPoint.setDescription(HtmlUtils.htmlUnescape(stopPoint.getDescription()));
		stopPoint.setLocality(HtmlUtils.htmlUnescape(stopPoint.getLocality()));
		stopPoint.setRating(Math.round(((stopPointEntity.getRating() * 100) / 5)));
		if (userId != null) {
			stopPoint.setPreferito(userPreferitiService.isPreferito(stopPoint.getStopId(), userId));
			stopPoint.setHowRated(reviewService.howRated(stopPoint.getStopId(), userId));
		}
		return stopPoint;
	}

	private StopPointsEntity toEntity(final StopPointMod stopPointMod, final Long userId) {
		StopPointsEntity stopPointEntity = new StopPointsEntity();
		if (stopPointMod.getStopId() != null) {
			stopPointEntity = stopPointsRepository.getOne(stopPointMod.getStopId());
		}
		BeanUtils.copyProperties(stopPointMod, stopPointEntity);
		stopPointEntity.setTypeId(EStopPointTypeId.fromId(stopPointMod.getTypeId()));
		stopPointEntity.setValuta(EValuta.fromId(stopPointMod.getValuta()));
		stopPointEntity.setTipoPiazzola(EStopPointPlaceTypeId.fromId(stopPointMod.getTipoPiazzola()));
		stopPointEntity.setNation(stopPointEntity.getNation().toLowerCase());
		stopPointEntity.setVisibile(1);
		if (stopPointEntity.getStopId() == null) {
			stopPointEntity.setInserted(Calendar.getInstance().getTime());
			stopPointEntity.setInsertedUserId(userId);
			stopPointEntity.setModified(Calendar.getInstance().getTime());
			stopPointEntity.setModifiedUserId(userId);
		} else {
			stopPointEntity.setModified(Calendar.getInstance().getTime());
			stopPointEntity.setModifiedUserId(userId);
		}
		return stopPointEntity;
	}

}
