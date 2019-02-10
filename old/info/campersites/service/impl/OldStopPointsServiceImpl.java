package old.info.campersites.service.impl;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.HtmlUtils;

import info.campersites.entity.NationStatsEntity;
import info.campersites.entity.StopPointsEntity;
import info.campersites.entity.UserPreferitiEntity;
import info.campersites.enumerator.EStopPointPlaceTypeId;
import info.campersites.enumerator.EStopPointTypeId;
import info.campersites.enumerator.EValuta;
import info.campersites.repository.NationStatsRepository;
import info.campersites.repository.StopPointsRepository;
import info.campersites.repository.UserPreferitiRepository;
import old.info.campersites.bo.FiltriBo;
import old.info.campersites.bo.NationStatsBo;
import old.info.campersites.bo.StopPointBo;
import old.info.campersites.bo.UserBo;
import old.info.campersites.service.OldReviewService;
import old.info.campersites.service.OldStopPointsService;
import old.info.campersites.tools.Tools;

@Service
@Transactional(readOnly = true)
public class OldStopPointsServiceImpl implements OldStopPointsService {
	 
    @Autowired
	private StopPointsRepository stopPointsRepository;
    @Autowired
	private NationStatsRepository nationStatsRepository;
    @Autowired
	private UserPreferitiRepository userPreferitiRepository;
    @Autowired
	private OldReviewService reviewService;
	
	@Override
    public List<StopPointBo> getStopPointsBound(String bounds, FiltriBo filtriBo) {
		List<StopPointBo> stopPointsBo = new ArrayList<StopPointBo>();
		//Returns a string of the form "lat_lo,lng_lo,lat_hi,lng_hi" for this bounds, 
		//where "lo" corresponds to the southwest corner of the bounding box, while "hi" corresponds to the northeast corner of that box.
		String[] point = bounds.split(",");
		Float minLatitude = Float.valueOf(point[0]);
		Float minLongitude = Float.valueOf(point[1]);
		Float maxLatitude = Float.valueOf(point[2]);
		Float maxLongitude = Float.valueOf(point[3]);
    	List<StopPointsEntity> stopPointsEntity = stopPointsRepository.findByLatitudeBetweenAndLongitudeBetween(minLatitude, maxLatitude, minLongitude, maxLongitude);
    	for (StopPointsEntity stopPointEntity : stopPointsEntity) {
    		if (stopPointEntity.getVisibile() == 0) continue;
    		if (filtriBo != null && filtriBo.isFiltriAttivi()) {
    			// Check eventuali filtri e salto se filtrato
    			if (pointToFilter(stopPointEntity, filtriBo)) continue;
    		}
			StopPointBo stopPointBo = mappingEntityToBo(stopPointEntity);
    		stopPointsBo.add(stopPointBo);
    	}
        return stopPointsBo;
    }

	@Override
	public StopPointBo getStopPointDetail(Long stopPoint) {
    	StopPointsEntity stopPointEntity = stopPointsRepository.getOne(stopPoint);
    	if (stopPointEntity != null) {
    		if (stopPointEntity.getVisibile() == 0) return null;
    		StopPointBo stopPointBo = mappingEntityToBo(stopPointEntity);
    		stopPointBo.setReviews(reviewService.getReviewsByStopPoint(stopPoint));
			return stopPointBo;
    	} else {
    		return null;
    	}
	}
	
	@Override
	@Transactional(readOnly = false)
	@CacheEvict(value = {"nationStatCache", "oldNationStatCache", "stopPointsNewestCache", "usersTopCache"}, allEntries = true)
	public StopPointBo insertOrUpdate(StopPointBo stopPoint) {
		StopPointsEntity stopPointEntity = mappingBoToEntity(stopPoint);
		stopPointEntity = stopPointsRepository.save(stopPointEntity);
		return mappingEntityToBo(stopPointEntity);
	}
	
	@Override
    public List<StopPointBo> getStopPointsPreferiti(UserBo user) {
		List<StopPointBo> stopPointsBo = new ArrayList<StopPointBo>();
		List<StopPointsEntity> stopPointsEntity = new ArrayList<StopPointsEntity>();
    	List<UserPreferitiEntity> userPreferitiEntity = userPreferitiRepository.findByUserId(user.getUserId());
    	List<Long> stopIds = new ArrayList<Long>();
    	for (UserPreferitiEntity userPreferitoEntity : userPreferitiEntity) {
    		stopIds.add(userPreferitoEntity.getStopId());
    	}
    	if (!stopIds.isEmpty()) {
    		stopPointsEntity = stopPointsRepository.findByStopIdIn(stopIds);
    	}
    	for (StopPointsEntity stopPointEntity : stopPointsEntity) {
    		if (stopPointEntity.getVisibile() == 1) {
				StopPointBo stopPointBo = mappingEntityToBo(stopPointEntity);
	    		stopPointsBo.add(stopPointBo);
    		}
    	}
        return stopPointsBo;
    }

	@Override
    public List<StopPointBo> getStopPointsFromPoint(Float latitude, Float longitude, Double maxDistance) {
		DecimalFormat df = new DecimalFormat("##.##", DecimalFormatSymbols.getInstance(Locale.ENGLISH));
		df.setRoundingMode(RoundingMode.HALF_UP);
		List<StopPointBo> stopPointsBo = new ArrayList<StopPointBo>();
		Float rangefromDistance = rangeFromDistance(maxDistance);
    	List<StopPointsEntity> stopPointsEntity = stopPointsRepository.findByLatitudeBetweenAndLongitudeBetween(latitude-rangefromDistance, latitude+rangefromDistance, longitude-rangefromDistance, longitude+rangefromDistance);
    	for (StopPointsEntity stopPointEntity : stopPointsEntity) {
    		if (stopPointEntity.getVisibile() == 1) {
				Double distance = Tools.distance(latitude, longitude, stopPointEntity.getLatitude(), stopPointEntity.getLongitude(), Tools.KILOMETRI);
	    		if (distance <= maxDistance) {
	    			StopPointBo stopPointBo = mappingEntityToBo(stopPointEntity);
					stopPointBo.setFotoPath((stopPointEntity.getFotoPath() == null) ? "/no_image" : stopPointEntity.getFotoPath());
					stopPointBo.setDistance(Double.parseDouble(df.format(distance)));
		    		stopPointsBo.add(stopPointBo);
	    		}
    		}
    	}
        return stopPointsBo;
    }

	@Override
    public List<StopPointBo> getStopPointsByNation(String nation) {
		List<StopPointBo> stopPointsBo = new ArrayList<StopPointBo>();
    	List<StopPointsEntity> stopPointsEntity = stopPointsRepository.findByNation(nation);
    	for (StopPointsEntity stopPointEntity : stopPointsEntity) {
    		if (stopPointEntity.getVisibile() == 1) {
				StopPointBo stopPointBo = mappingEntityToBo(stopPointEntity);
				stopPointBo.setReviews(reviewService.getReviewsByStopPoint(stopPointEntity.getStopId()));
	    		stopPointsBo.add(stopPointBo);
    		}
    	}
        return stopPointsBo;
    }

	@Override
	@Cacheable(value="oldNationStatCache")
    public List<NationStatsBo> getNationStats() {
		List<NationStatsBo> nationsStatsBo = new ArrayList<NationStatsBo>();
    	List<NationStatsEntity> nationsStatsEntity = nationStatsRepository.findAll();
    	for (NationStatsEntity nationStatsEntity : nationsStatsEntity) {
    		NationStatsBo nationStatsBo = new NationStatsBo();
    		nationStatsBo.setNation(nationStatsEntity.getNation());
    		nationStatsBo.setCA(nationStatsEntity.getCA());
    		nationStatsBo.setAC(nationStatsEntity.getAC());
    		nationStatsBo.setAA(nationStatsEntity.getAA());
    		nationStatsBo.setCS(nationStatsEntity.getCS());
    		nationStatsBo.setPS(nationStatsEntity.getPS());
    		nationStatsBo.setTotAree(nationStatsEntity.getTotAree());
    		nationStatsBo.setLastDate(nationStatsEntity.getLastDate());
 			nationsStatsBo.add(nationStatsBo);
    	}
        return nationsStatsBo;
    }

	private Float rangeFromDistance(Double distance) {
		return (float) (distance/10);
	}
	
	private StopPointBo mappingEntityToBo(StopPointsEntity stopPointEntity) {
		StopPointBo stopPointBo = new StopPointBo(stopPointEntity.getStopId());
		stopPointBo.setLatitude(stopPointEntity.getLatitude());
		stopPointBo.setLongitude(stopPointEntity.getLongitude());
		stopPointBo.setLatitudeDeg(Tools.decimalToDMS(stopPointEntity.getLatitude()));
		stopPointBo.setLongitudeDeg(Tools.decimalToDMS(stopPointEntity.getLongitude()));
		stopPointBo.setDescription(HtmlUtils.htmlUnescape(stopPointEntity.getDescription()));
		stopPointBo.setLocality(HtmlUtils.htmlUnescape(stopPointEntity.getLocality()));
		stopPointBo.setNation(stopPointEntity.getNation());
		stopPointBo.setModified(stopPointEntity.getModified());
		stopPointBo.setModifiedByUser(stopPointEntity.getModifiedUserId());
		stopPointBo.setInserted(stopPointEntity.getInserted());
		stopPointBo.setInsertedByUser(stopPointEntity.getInsertedUserId());
		stopPointBo.setTypeId(stopPointEntity.getTypeId());
		stopPointBo.setTotPreferito(stopPointEntity.getTotPreferito());
		stopPointBo.setRating(stopPointEntity.getRating());
		stopPointBo.setFotoPath(stopPointEntity.getFotoPath());
		stopPointBo.setHomepage(stopPointEntity.getHomepage());
		stopPointBo.setValuta(stopPointEntity.getValuta());
		stopPointBo.setPrezzoNotturno(stopPointEntity.getPrezzoNotturno());
		stopPointBo.setPrezzoOrario(stopPointEntity.getPrezzoOrario());
		stopPointBo.setPrezzoGiornaliero(stopPointEntity.getPrezzoGiornaliero());
		stopPointBo.setPrezzoSettimanale(stopPointEntity.getPrezzoSettimanale());
		stopPointBo.setPrezzoParticolare(HtmlUtils.htmlUnescape(stopPointEntity.getPrezzoParticolare()));
		stopPointBo.setAcqua(stopPointEntity.getAcqua());
		stopPointBo.setScaricoCassetta(stopPointEntity.getScaricoCassetta());
		stopPointBo.setScaricoPozzetto(stopPointEntity.getScaricoPozzetto());
		stopPointBo.setPrezzoService(stopPointEntity.getPrezzoService());
		stopPointBo.setCorrente(stopPointEntity.getCorrente());
		stopPointBo.setPrezzoCorrente(stopPointEntity.getPrezzoCorrente());
		stopPointBo.setTipoPiazzola(stopPointEntity.getTipoPiazzola());
		stopPointBo.setAccessoCustodito(stopPointEntity.getAccessoCustodito());
		stopPointBo.setVideosorveglianza(stopPointEntity.getVideosorveglianza());
		stopPointBo.setNotte(stopPointEntity.getNotte());
		stopPointBo.setIlluminazione(stopPointEntity.getIlluminazione());
		stopPointBo.setOmbra(stopPointEntity.getOmbra());
		stopPointBo.setDocce(stopPointEntity.getDocce());
		stopPointBo.setBagni(stopPointEntity.getBagni());
		stopPointBo.setBambini(stopPointEntity.getBambini());
		stopPointBo.setPicnic(stopPointEntity.getPicnic());
		stopPointBo.setAnimali(stopPointEntity.getAnimali());
		stopPointBo.setFermata(stopPointEntity.getFermata());
		stopPointBo.setWifi(stopPointEntity.getWifi());
		stopPointBo.setTelefono(stopPointEntity.getTelefono());
		stopPointBo.setPosti(stopPointEntity.getPosti());
		stopPointBo.setMaxHH(stopPointEntity.getMaxHH());
		stopPointBo.setChiusura(stopPointEntity.getChiusura());
		stopPointBo.setVisibile(stopPointEntity.getVisibile());
		return stopPointBo;
	}
	
	private StopPointsEntity mappingBoToEntity(StopPointBo stopPointBo) {
		StopPointsEntity stopPointsEntity = new StopPointsEntity();
		stopPointsEntity.setStopId(stopPointBo.getStopId());
		stopPointsEntity.setLatitude(stopPointBo.getLatitude());
		stopPointsEntity.setLongitude(stopPointBo.getLongitude());
		stopPointsEntity.setDescription(stopPointBo.getDescription());
		stopPointsEntity.setLocality(stopPointBo.getLocality());
		stopPointsEntity.setNation(stopPointBo.getNation());
		stopPointsEntity.setModified(stopPointBo.getModified());
		stopPointsEntity.setModifiedUserId(stopPointBo.getModifiedByUser());
		stopPointsEntity.setInserted(stopPointBo.getInserted());
		stopPointsEntity.setInsertedUserId(stopPointBo.getInsertedByUser());
		stopPointsEntity.setTypeId(EStopPointTypeId.fromId(stopPointBo.getTypeId()));
		stopPointsEntity.setTotPreferito(stopPointBo.getTotPreferito());
		stopPointsEntity.setRating(stopPointBo.getRating());
		stopPointsEntity.setFotoPath(stopPointBo.getFotoPath());
		stopPointsEntity.setHomepage(stopPointBo.getHomepage());
		stopPointsEntity.setValuta(EValuta.fromId(stopPointBo.getValuta()));
		stopPointsEntity.setPrezzoNotturno(stopPointBo.getPrezzoNotturno());
		stopPointsEntity.setPrezzoOrario(stopPointBo.getPrezzoOrario());
		stopPointsEntity.setPrezzoGiornaliero(stopPointBo.getPrezzoGiornaliero());
		stopPointsEntity.setPrezzoSettimanale(stopPointBo.getPrezzoSettimanale());
		stopPointsEntity.setPrezzoParticolare(stopPointBo.getPrezzoParticolare());
		stopPointsEntity.setAcqua(stopPointBo.getAcqua());
		stopPointsEntity.setScaricoCassetta(stopPointBo.getScaricoCassetta());
		stopPointsEntity.setScaricoPozzetto(stopPointBo.getScaricoPozzetto());
		stopPointsEntity.setPrezzoService(stopPointBo.getPrezzoService());
		stopPointsEntity.setCorrente(stopPointBo.getCorrente());
		stopPointsEntity.setPrezzoCorrente(stopPointBo.getPrezzoCorrente());
		stopPointsEntity.setTipoPiazzola(EStopPointPlaceTypeId.fromId(stopPointBo.getTipoPiazzola()));
		stopPointsEntity.setAccessoCustodito(stopPointBo.getAccessoCustodito());
		stopPointsEntity.setVideosorveglianza(stopPointBo.getVideosorveglianza());
		stopPointsEntity.setNotte(stopPointBo.getNotte());
		stopPointsEntity.setIlluminazione(stopPointBo.getIlluminazione());
		stopPointsEntity.setOmbra(stopPointBo.getOmbra());
		stopPointsEntity.setDocce(stopPointBo.getDocce());
		stopPointsEntity.setBagni(stopPointBo.getBagni());
		stopPointsEntity.setBambini(stopPointBo.getBambini());
		stopPointsEntity.setPicnic(stopPointBo.getPicnic());
		stopPointsEntity.setAnimali(stopPointBo.getAnimali());
		stopPointsEntity.setFermata(stopPointBo.getFermata());
		stopPointsEntity.setWifi(stopPointBo.getWifi());
		stopPointsEntity.setTelefono(stopPointBo.getTelefono());
		stopPointsEntity.setPosti(stopPointBo.getPosti());
		stopPointsEntity.setMaxHH(stopPointBo.getMaxHH());
		stopPointsEntity.setChiusura(stopPointBo.getChiusura());
		if (stopPointBo.getVisibile() != null)
			stopPointsEntity.setVisibile(stopPointBo.getVisibile());
		return stopPointsEntity;
	}
	
	private boolean pointToFilter(StopPointsEntity stopPointEntity, FiltriBo filtriBo) {
		boolean toFilter = false;
		if ((filtriBo.isPS() || filtriBo.isCS() || filtriBo.isAA() || filtriBo.isAC() || filtriBo.isCA()) &&
			(!((filtriBo.isPS() && EStopPointTypeId.PS == stopPointEntity.getTypeId()) ||
			(filtriBo.isCS() && EStopPointTypeId.CS == stopPointEntity.getTypeId()) ||
			(filtriBo.isAA() && EStopPointTypeId.AA == stopPointEntity.getTypeId()) ||
			(filtriBo.isAC() && EStopPointTypeId.AC == stopPointEntity.getTypeId()) ||
			(filtriBo.isCA() && EStopPointTypeId.CA == stopPointEntity.getTypeId())))) toFilter = true;
		if (filtriBo.isSostaGratis() && ((stopPointEntity.getPrezzoGiornaliero() != null && stopPointEntity.getPrezzoGiornaliero() != 0) ||
										 (stopPointEntity.getPrezzoNotturno() != null && stopPointEntity.getPrezzoNotturno() != 0) ||
										 (stopPointEntity.getPrezzoOrario() != null && stopPointEntity.getPrezzoOrario() != 0) ||
										 (stopPointEntity.getPrezzoSettimanale() != null && stopPointEntity.getPrezzoSettimanale() != 0) ||
										 (stopPointEntity.getPrezzoParticolare() != null && !stopPointEntity.getPrezzoParticolare().isEmpty()))) toFilter = true; 
		if (filtriBo.isAcqua() && (stopPointEntity.getAcqua() == null || stopPointEntity.getAcqua() == 0)) toFilter = true;
		if (filtriBo.isScaricoCassetta() && (stopPointEntity.getScaricoCassetta() == null || stopPointEntity.getScaricoCassetta() == 0)) toFilter = true;
		if (filtriBo.isScaricoPozzetto() && (stopPointEntity.getScaricoPozzetto() == null || stopPointEntity.getScaricoPozzetto() == 0)) toFilter = true;
		if (filtriBo.isCorrente() && (stopPointEntity.getCorrente() == null || stopPointEntity.getCorrente() == 0)) toFilter = true;
		if (filtriBo.isServiziGratis() && ((stopPointEntity.getPrezzoService() != null && stopPointEntity.getPrezzoService() != 0) ||
										   (stopPointEntity.getPrezzoCorrente() != null && stopPointEntity.getPrezzoCorrente() != 0))) toFilter = true;
		if ((filtriBo.isSterrato() || filtriBo.isAsfaltato() || filtriBo.isErba() || filtriBo.isMattoni()) &&
			(!((filtriBo.isSterrato() && EStopPointPlaceTypeId.ST == stopPointEntity.getTipoPiazzola()) ||
			(filtriBo.isAsfaltato() && EStopPointPlaceTypeId.AS == stopPointEntity.getTipoPiazzola()) ||
			(filtriBo.isErba() && EStopPointPlaceTypeId.ER == stopPointEntity.getTipoPiazzola()) ||
			(filtriBo.isMattoni() && EStopPointPlaceTypeId.MA == stopPointEntity.getTipoPiazzola())))) toFilter = true;
		if (filtriBo.isAccessoCustodito() && (stopPointEntity.getAccessoCustodito() == null || stopPointEntity.getAccessoCustodito() == 0)) toFilter = true;
		if (filtriBo.isVideosorveglianza() && (stopPointEntity.getVideosorveglianza() == null || stopPointEntity.getVideosorveglianza() == 0)) toFilter = true;
		if (filtriBo.isNotte() && (stopPointEntity.getNotte() == null || stopPointEntity.getNotte() == 0)) toFilter = true;
		if (filtriBo.isIlluminazione() && (stopPointEntity.getIlluminazione() == null || stopPointEntity.getIlluminazione() == 0)) toFilter = true;
		if (filtriBo.isOmbra() && (stopPointEntity.getOmbra() == null || stopPointEntity.getOmbra() == 0)) toFilter = true;
		if (filtriBo.isDocce() && (stopPointEntity.getDocce() == null || stopPointEntity.getDocce() == 0)) toFilter = true;
		if (filtriBo.isBagni() && (stopPointEntity.getBagni() == null || stopPointEntity.getBagni() == 0)) toFilter = true;
		if (filtriBo.isBambini() && (stopPointEntity.getBambini() == null || stopPointEntity.getBambini() == 0)) toFilter = true;
		if (filtriBo.isPicnic() && (stopPointEntity.getPicnic() == null || stopPointEntity.getPicnic() == 0)) toFilter = true;
		if (filtriBo.isAnimali() && (stopPointEntity.getAnimali() == null || stopPointEntity.getAnimali() == 0)) toFilter = true;
		if (filtriBo.isFermata() && (stopPointEntity.getFermata() == null || stopPointEntity.getFermata() == 0)) toFilter = true;
		if (filtriBo.isWifi() && (stopPointEntity.getWifi() == null || stopPointEntity.getWifi() == 0)) toFilter = true;
		if (filtriBo.getPosti() > 0 && stopPointEntity.getPosti() != null && stopPointEntity.getPosti() < filtriBo.getPosti()) toFilter = true;
		if (filtriBo.getMaxHH() > 0 && stopPointEntity.getMaxHH() != null && stopPointEntity.getMaxHH() < filtriBo.getMaxHH()) toFilter = true;
		if (filtriBo.getChiusura() != null &&
			stopPointEntity.getChiusura() != null && !stopPointEntity.getChiusura().isEmpty() && 
			Tools.checkChiusura(filtriBo.getChiusura(), stopPointEntity.getChiusura())) toFilter = true;
		return toFilter;
	}

}
