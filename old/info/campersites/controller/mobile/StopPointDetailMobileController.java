package old.info.campersites.controller.mobile;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.HtmlUtils;

import info.campersites.enumerator.EStopPointPlaceTypeId;
import info.campersites.enumerator.EStopPointTypeId;
import info.campersites.enumerator.EValuta;
import old.info.campersites.bo.ImageBo;
import old.info.campersites.bo.ReviewBo;
import old.info.campersites.bo.StopPointBo;
import old.info.campersites.bo.UserBo;
import old.info.campersites.comparetor.ReviewInsertedComparetor;
import old.info.campersites.jsonresponse.StopPointJsonResponse;
import old.info.campersites.jsonresponse.StringJsonResponse;
import old.info.campersites.service.OldImageService;
import old.info.campersites.service.OldReviewService;
import old.info.campersites.service.OldStopPointsService;
import old.info.campersites.service.OldUserService;
import old.info.campersites.tools.Defines;
import old.info.campersites.tools.Errors;
import old.info.campersites.tools.Tools;

@RestController
public class StopPointDetailMobileController {
	 
	@Autowired
	private OldStopPointsService stopPointsService;
	@Autowired
	private OldImageService imageService; 
	@Autowired
	private OldUserService userService; 
	@Autowired
	private OldReviewService reviewService;
	
	private static final String STOP_POINT = "stopPoint";
	protected static final int NUMBER_OF_REVIEWS_PER_PAGE = 5;
	protected static final String REVIEWS = "reviews";
	protected static final String LAST_PAGE = "lastPage";
	
	@RequestMapping(value = "/getJsonStopPoint", method = RequestMethod.GET)
	public @ResponseBody StopPointJsonResponse stopPoint(Model model, HttpServletRequest request, HttpServletResponse response) {
		response.addHeader("Access-Control-Allow-Origin", "*");
		Long stopPoint = Long.parseLong(request.getParameter(STOP_POINT));
		StopPointJsonResponse stopPointJsonResponse = new StopPointJsonResponse();

		StopPointBo stopPointDetail = stopPointsService.getStopPointDetail(stopPoint);
		if (stopPointDetail != null) {
			List<ReviewBo> reviews = stopPointDetail.getReviews();
			Collections.sort(reviews, new ReviewInsertedComparetor(ReviewInsertedComparetor.DESC));
			stopPointDetail.setReviews(reviews);
			stopPointJsonResponse.setStopPoint(stopPointDetail);
			stopPointJsonResponse.setHasPhoto(getUrlsToStopPointImageFiles(stopPoint).size() > 0);
			String userFbId = request.getParameter("userFbId");
			String userId = request.getParameter("userId");
			UserBo currentUser = null;
			if (userFbId != null && !"".equals(userFbId) && !"null".equals(userFbId)) { 
				currentUser = userService.getUserByFbId(userFbId);
			} else if (userId != null && !"".equals(userId) && !"null".equals(userId)) {
				currentUser = userService.getUserById(Long.parseLong(userId));
			}
			if (currentUser != null) {
				stopPointJsonResponse.setPreferito(userService.isPreferito(stopPoint, currentUser.getUserId()));
				stopPointJsonResponse.setHowRated(reviewService.howRated(stopPoint, currentUser.getUserId()));
			}
		} else {
			stopPointJsonResponse.setEsito(Defines.KO);
		}
		return stopPointJsonResponse;
	}
	
	/**
	 * @deprecated
	 */
	@RequestMapping(value = "/getJsonImages", method = RequestMethod.GET)
	public @ResponseBody List<String> getStopPointImages(Model model, HttpServletRequest request, HttpServletResponse response) {
		response.addHeader("Access-Control-Allow-Origin", "*");
		Long stopPoint = Long.parseLong(request.getParameter("stopPoint"));
		return getUrlsToStopPointImageFiles(stopPoint);
	}
	
	@RequestMapping(value = "/getJsonImagesInfo", method = RequestMethod.GET)
	public @ResponseBody List<ImageBo> getStopPointImagesInfo(Model model, HttpServletRequest request, HttpServletResponse response) {
		response.addHeader("Access-Control-Allow-Origin", "*");
		Long stopPoint = Long.parseLong(request.getParameter("stopPoint"));
		return imageService.getImageByStopId(stopPoint);
	}
	
	/**
	 * @deprecated
	 */
	@RequestMapping(value = "/isStopPointPreferitoMobile", method = RequestMethod.GET)
	public @ResponseBody StringJsonResponse isStopPointPreferitoMobile(Model model, HttpServletRequest request, HttpServletResponse response) {
		response.addHeader("Access-Control-Allow-Origin", "*");
		StringJsonResponse stringJsonResponse = new StringJsonResponse();
		String userFbId = request.getParameter("userFbId");
		String userId = request.getParameter("userId");
		UserBo currentUser = null;
		if (userFbId != null && !"".equals(userFbId) && !"null".equals(userFbId)) { 
			currentUser = userService.getUserByFbId(userFbId);
		} else {
			currentUser = userService.getUserById(Long.parseLong(userId));
		}
		Long stopId = Long.parseLong(request.getParameter("stopId"));
		try {
			if (userService.isPreferito(stopId, currentUser.getUserId())) {
				stringJsonResponse.setEsito(Defines.OK);
			} else {
				stringJsonResponse.setEsito(Defines.KO);
			}
			return stringJsonResponse;
		} catch (Exception e) {
			stringJsonResponse.setEsito(Defines.SYSTEM);
			return stringJsonResponse;
		}
	}

	private List<String> getUrlsToStopPointImageFiles(Long stopPoint) {
		List<String> resultURLs = new ArrayList<String>();
		List<ImageBo> images = imageService.getImageByStopId(stopPoint);
		if (!images.isEmpty()) {
			for (ImageBo imageBo : images) {
				resultURLs.add(imageBo.getImageId().toString());
			}
		}
		return resultURLs;
	}
	
	@RequestMapping(value = "/setMobileStopPointToModify/{stopId}/{userFbId}/{userId}", method = RequestMethod.POST)
	public @ResponseBody StopPointJsonResponse setMobileStopPointToModify(Model model, HttpServletRequest request, @PathVariable Long stopId, @PathVariable String userFbId, @PathVariable Long userId, HttpServletResponse response) {
		response.addHeader("Access-Control-Allow-Origin", "*");
		StopPointJsonResponse stopPointJsonResponse = new StopPointJsonResponse();
		UserBo currentUser = null;
		if (userFbId != null && !"".equals(userFbId) && !"null".equals(userFbId)) { 
			currentUser = userService.getUserByFbId(userFbId);
		} else {
			currentUser = userService.getUserById(userId);
		}
		if (currentUser == null) {
			stopPointJsonResponse.setEsito(Defines.SYSTEM);
			return stopPointJsonResponse;
        }
        
        StopPointBo stopPointBo = stopPointsService.getStopPointDetail(stopId);
		if (stopPointBo == null) {
			stopPointJsonResponse.setEsito(Defines.SYSTEM);
			return stopPointJsonResponse;
		}
		
		return mappingRequestToStopPoint(request, stopPointBo, currentUser);
	}

	@RequestMapping(value = "/setMobileStopPointToCreate/{userFbId}/{userId}", method = RequestMethod.POST)
	public @ResponseBody StopPointJsonResponse setMobileStopPointToCreate(Model model, HttpServletRequest request, HttpServletResponse response, @PathVariable String userFbId, @PathVariable Long userId) {
		response.addHeader("Access-Control-Allow-Origin", "*");
		StopPointJsonResponse stopPointJsonResponse = new StopPointJsonResponse();
		UserBo currentUser = null;
		if (userFbId != null && !"".equals(userFbId) && !"null".equals(userFbId)) { 
			currentUser = userService.getUserByFbId(userFbId);
		} else {
			currentUser = userService.getUserById(userId);
		}
		if (currentUser == null) {
			stopPointJsonResponse.setEsito(Defines.SYSTEM);
			return stopPointJsonResponse;
        }
        
		// Creo nuova sosta sosta
		StopPointBo stopPointBo = new StopPointBo();
		
		return mappingRequestToStopPoint(request, stopPointBo, currentUser);
	}

	private StopPointJsonResponse mappingRequestToStopPoint(HttpServletRequest request, StopPointBo stopPointBo, UserBo currentUser) {
		StopPointJsonResponse stopPointJsonResponse = new StopPointJsonResponse();
		
		String latitude = request.getParameter("LatitudeHidden");
		String longitude = request.getParameter("LongitudeHidden");
		String description = request.getParameter("Description");
		String locality = request.getParameter("LocalityHidden");
		String nation = request.getParameter("NationHidden");
		String typeId = request.getParameter("TypeId");
		String homepage = request.getParameter("Homepage");
		String telefono = request.getParameter("Telefono");
		String chiusura = request.getParameter("Chiusura");
		String valuta = request.getParameter("Valuta");
		String prezzoNotturno = request.getParameter("PrezzoNotturno");
		String prezzoOrario = request.getParameter("PrezzoOrario");
		String prezzoGiornaliero = request.getParameter("PrezzoGiornaliero");
		String prezzoSettimanale = request.getParameter("PrezzoSettimanale");
		String prezzoService = request.getParameter("PrezzoService");
		String prezzoCorrente = request.getParameter("PrezzoCorrente");
		
		// Check obbligatori
		if (latitude == null) {
			stopPointJsonResponse.getErrors().add(Errors.LATITUDE_NULL);
		} 
		if (longitude == null) {
			stopPointJsonResponse.getErrors().add(Errors.LONGITUDE_NULL);
		} 
		if (description == null || description.trim().isEmpty()) {
			stopPointJsonResponse.getErrors().add(Errors.DESCRIPTION_NULL);
		} 
		if (locality == null || locality.trim().isEmpty()) {
			stopPointJsonResponse.getErrors().add(Errors.LOCALITY_NULL);
		} 
		if (nation == null || nation.trim().isEmpty()) {
			stopPointJsonResponse.getErrors().add(Errors.NATION_NULL);
		} 
		if (typeId == null) {
			stopPointJsonResponse.getErrors().add(Errors.TYPE_NULL);
		} 
		if (valuta == null) {
			stopPointJsonResponse.getErrors().add(Errors.VALUTA_NULL);
		} 
		if (stopPointJsonResponse.getErrors().size() > 0) {
			stopPointJsonResponse.setEsito(Defines.KO);
			return stopPointJsonResponse;
		}
		
		// Check formati
		if (!Tools.urlFormatCheck(homepage)){
			stopPointJsonResponse.getErrors().add(Errors.FORMAT_URL);
		}
		if (!Tools.chiusuraFormatCheck(chiusura)){
			stopPointJsonResponse.getErrors().add(Errors.FORMAT_CHIUSURA);
		}
		if (!Tools.isValidFloat(latitude)){
			stopPointJsonResponse.getErrors().add(Errors.FORMAT_LATITUDE);
		}
		if (!Tools.isValidFloat(longitude)){
			stopPointJsonResponse.getErrors().add(Errors.FORMAT_LONGITUDE);
		}
		if (!Tools.isValidFloat(prezzoNotturno)){
			stopPointJsonResponse.getErrors().add(Errors.FORMAT_PREZZO_NOTTURNO);
		}
		if (!Tools.isValidFloat(prezzoOrario)){
			stopPointJsonResponse.getErrors().add(Errors.FORMAT_PREZZO_ORARIO);
		}
		if (!Tools.isValidFloat(prezzoGiornaliero)){
			stopPointJsonResponse.getErrors().add(Errors.FORMAT_PREZZO_GIORNALIERO);
		}
		if (!Tools.isValidFloat(prezzoSettimanale)){
			stopPointJsonResponse.getErrors().add(Errors.FORMAT_PREZZO_SETTIMANALE);
		}
		if (!Tools.isValidFloat(prezzoService)){
			stopPointJsonResponse.getErrors().add(Errors.FORMAT_PREZZO_SERVICE);
		}
		if (!Tools.isValidFloat(prezzoCorrente)){
			stopPointJsonResponse.getErrors().add(Errors.FORMAT_PREZZO_CORRENTE);
		}
		if (stopPointJsonResponse.getErrors().size() > 0) {
			stopPointJsonResponse.setEsito(Defines.KO);
			return stopPointJsonResponse;
		}
		
		String prezzoParticolare = request.getParameter("PrezzoParticolare");
		String acqua = request.getParameter("Acqua");
		String scaricoCassetta = request.getParameter("ScaricoCassetta");
		String scaricoPozzetto = request.getParameter("ScaricoPozzetto");
		String corrente = request.getParameter("Corrente");
		String tipoPiazzola = request.getParameter("TipoPiazzola");
		String accessoCustodito = request.getParameter("AccessoCustodito");
		String videosorveglianza = request.getParameter("Videosorveglianza");
		String notte = request.getParameter("Notte");
		String illuminazione = request.getParameter("Illuminazione");
		String ombra = request.getParameter("Ombra");
		String docce = request.getParameter("Docce");
		String bagni = request.getParameter("Bagni");
		String bambini = request.getParameter("Bambini");
		String picnic = request.getParameter("Picnic");
		String animali = request.getParameter("Animali");
		String fermata = request.getParameter("Fermata");
		String wifi = request.getParameter("Wifi");
		String posti = request.getParameter("Posti");
		String maxHH = request.getParameter("MaxHH");
		
		stopPointBo.setLatitude(Float.valueOf(latitude));
		stopPointBo.setLongitude(Float.valueOf(longitude));
		stopPointBo.setDescription(HtmlUtils.htmlEscape(Tools.capitalizeFirstLetterInEveryWord(description.trim().toLowerCase())));
		stopPointBo.setLocality(HtmlUtils.htmlEscape(locality.trim()));
		stopPointBo.setNation(nation.trim().toLowerCase());
		stopPointBo.setTypeId(EStopPointTypeId.fromId(typeId));
		stopPointBo.setHomepage((homepage != null) ? homepage.trim() : null);
		stopPointBo.setTelefono((telefono != null) ? telefono.trim() : null);
		stopPointBo.setChiusura((chiusura != null) ? chiusura.trim() : null);
		stopPointBo.setValuta(EValuta.fromId(valuta));
		stopPointBo.setPrezzoNotturno(Tools.getFloatValue(prezzoNotturno));
		stopPointBo.setPrezzoOrario(Tools.getFloatValue(prezzoOrario));
		stopPointBo.setPrezzoGiornaliero(Tools.getFloatValue(prezzoGiornaliero));
		stopPointBo.setPrezzoSettimanale(Tools.getFloatValue(prezzoSettimanale));
		stopPointBo.setPrezzoParticolare(HtmlUtils.htmlEscape((prezzoParticolare != null) ? Tools.capitalizeFirstLetterInEverySentence(prezzoParticolare.trim().toLowerCase()) : null));
		stopPointBo.setAcqua(Tools.getIntegerValue(acqua));
		stopPointBo.setScaricoCassetta(Tools.getIntegerValue(scaricoCassetta));
		stopPointBo.setScaricoPozzetto(Tools.getIntegerValue(scaricoPozzetto));
		stopPointBo.setPrezzoService(Tools.getFloatValue(prezzoService));
		stopPointBo.setCorrente(Tools.getIntegerValue(corrente));
		stopPointBo.setPrezzoCorrente(Tools.getFloatValue(prezzoCorrente));
		stopPointBo.setTipoPiazzola((tipoPiazzola == null || tipoPiazzola.isEmpty()) ? null : EStopPointPlaceTypeId.fromId(tipoPiazzola));
		stopPointBo.setAccessoCustodito(Tools.getIntegerValue(accessoCustodito));
		stopPointBo.setVideosorveglianza(Tools.getIntegerValue(videosorveglianza));
		stopPointBo.setNotte(Tools.getIntegerValue(notte));
		stopPointBo.setIlluminazione(Tools.getIntegerValue(illuminazione));
		stopPointBo.setOmbra(Tools.getIntegerValue(ombra));
		stopPointBo.setDocce(Tools.getIntegerValue(docce));
		stopPointBo.setBagni(Tools.getIntegerValue(bagni));
		stopPointBo.setBambini(Tools.getIntegerValue(bambini));
		stopPointBo.setPicnic(Tools.getIntegerValue(picnic));
		stopPointBo.setAnimali(Tools.getIntegerValue(animali));
		stopPointBo.setFermata(Tools.getIntegerValue(fermata));
		stopPointBo.setWifi(Tools.getIntegerValue(wifi));
		stopPointBo.setPosti(Tools.getIntegerValue(posti));
		stopPointBo.setMaxHH(Tools.getIntegerValue(maxHH));
		if (stopPointBo.getStopId() == null) {
			stopPointBo.setInserted(Calendar.getInstance().getTime());
			stopPointBo.setInsertedByUser(currentUser.getUserId());
			stopPointBo.setModified(Calendar.getInstance().getTime());
			stopPointBo.setModifiedByUser(currentUser.getUserId());
		} else {
			stopPointBo.setModified(Calendar.getInstance().getTime());
			stopPointBo.setModifiedByUser(currentUser.getUserId());
		}
		stopPointBo.setVisibile(1);
		
		try {
			stopPointBo = stopPointsService.insertOrUpdate(stopPointBo);
			stopPointJsonResponse.setStopPoint(stopPointBo);
			stopPointJsonResponse.setEsito(Defines.OK);
		} catch (Exception e) {
			stopPointJsonResponse.setEsito(Defines.SYSTEM);
		}
		
		return stopPointJsonResponse;
	}
	
}
