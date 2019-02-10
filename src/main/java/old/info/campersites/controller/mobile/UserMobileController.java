package old.info.campersites.controller.mobile;

import java.util.Calendar;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.HtmlUtils;

import info.campersites.enumerator.EContactTypeId;
import info.campersites.enumerator.EStopPointPlaceTypeId;
import info.campersites.enumerator.EStopPointTypeId;
import info.campersites.enumerator.EValuta;
import info.campersites.service.EmailService;
import old.info.campersites.bo.OfflineEventsBo;
import old.info.campersites.bo.StopPointBo;
import old.info.campersites.bo.UserBo;
import old.info.campersites.jsonrequest.OfflineEventsJsonRequest;
import old.info.campersites.jsonresponse.StringJsonResponse;
import old.info.campersites.jsonresponse.UserJsonResponse;
import old.info.campersites.service.OldContactService;
import old.info.campersites.service.OldReviewService;
import old.info.campersites.service.OldStopPointsService;
import old.info.campersites.service.OldUserService;
import old.info.campersites.tools.Defines;
import old.info.campersites.tools.Errors;
import old.info.campersites.tools.Tools;

@RestController
public class UserMobileController {
	 
	private static final String REVIEW = "REVIEW";
	private static final String DEL_PREF = "DEL_PREF";
	private static final String ADD_PREF = "ADD_PREF";

	@Autowired
	private OldUserService userService;
	@Autowired
	private OldContactService contactService;
	@Autowired
	private EmailService emailService;
	@Autowired
	private OldReviewService reviewService;
	@Autowired
	private OldStopPointsService stopPointsService;

	@RequestMapping(value = "/registramiMobile", method = RequestMethod.POST)
	public @ResponseBody UserJsonResponse registramiMobile(Model model, HttpServletRequest request, HttpServletResponse response) {
		response.addHeader("Access-Control-Allow-Origin", "*");
		String email = request.getParameter("Email");
		String password = request.getParameter("Password");
		String nickname = request.getParameter("Nickname");
		Locale userLocale = request.getLocale();
		
		UserJsonResponse userJsonResponse = new UserJsonResponse();
		
		if (email == null || email.trim().isEmpty()) {
			userJsonResponse.getErrors().add(Errors.EMAIL_NULL);
		}
		if (password == null || password.trim().isEmpty()) {
			userJsonResponse.getErrors().add(Errors.PASSWORD_NULL);
		}
		if (nickname == null || nickname.trim().isEmpty()) {
			userJsonResponse.getErrors().add(Errors.NICKNAME_NULL);
		}
		if (userJsonResponse.getErrors().size() > 0) {
			userJsonResponse.setEsito(Defines.KO);
			return userJsonResponse;
		}

		UserBo newUser = null;
		nickname = HtmlUtils.htmlEscape(nickname);
		try {
			if (!emailFormatCheck(email)) {
				userJsonResponse.getErrors().add(Errors.FORMAT_EMAIL);
			}
			if (!emailUniqueCheck(email)) {
				userJsonResponse.getErrors().add(Errors.UNIQUE_EMAIL);
			}
			if (!nicknameUniqueCheck(nickname)) {
				userJsonResponse.getErrors().add(Errors.UNIQUE_NICKNAME);
			}
			if (userJsonResponse.getErrors().size() > 0) {
				userJsonResponse.setEsito(Defines.KO);
				return userJsonResponse;
			}

			newUser = userService.createUser(email.toLowerCase(userLocale), password, nickname, userLocale);
			userJsonResponse.setUser(newUser);
			userJsonResponse.setEsito(Defines.OK);
			request.getSession().setAttribute(Defines.CURRENT_USER, newUser);
		} catch (Exception e) {
			userJsonResponse.setEsito(Defines.SYSTEM);
		}
		
		return userJsonResponse;
	}

	@RequestMapping(value = "/entraMobile", method = RequestMethod.POST)
	public @ResponseBody UserJsonResponse entraMobile(Model model, HttpServletRequest request, HttpServletResponse response) {
		response.addHeader("Access-Control-Allow-Origin", "*");
		String email = request.getParameter("Email");
		String password = request.getParameter("Password");

		UserJsonResponse userJsonResponse = new UserJsonResponse();
		UserBo user = null;
        if (email == null || email.trim().isEmpty()) {
			userJsonResponse.getErrors().add(Errors.EMAIL_NULL);
		}
        if (password == null || password.trim().isEmpty()) {
			userJsonResponse.getErrors().add(Errors.PASSWORD_NULL);
		}
        if (userJsonResponse.getErrors().size() > 0) {
			userJsonResponse.setEsito(Defines.KO);
			return userJsonResponse;
		}

		try {
			user = userService.loginUser(email, password, null);
			if (user != null) {
				userJsonResponse.setUser(user);
				userJsonResponse.setEsito(Defines.OK);
				request.getSession().setAttribute(Defines.CURRENT_USER, user);
			} else {
				userJsonResponse.getErrors().add(Errors.LOGIN_ERROR);
				userJsonResponse.setEsito(Defines.KO);
			}
		} catch (Exception e) {
				userJsonResponse.setEsito(Defines.SYSTEM);
		}

		return userJsonResponse;
	}

	@RequestMapping(value = "/segnalaMobileStopPointErrato", method = RequestMethod.GET)
	public @ResponseBody StringJsonResponse segnalaStopPointErratoMobile(Model model, HttpServletRequest request, HttpServletResponse response) {
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
			contactService.createContatto(currentUser.getEmail(), currentUser.getNickname(), "Segnalazione di area sosta errata: " + stopId, currentUser.getLocale(), EContactTypeId.SE);
			stringJsonResponse.setEsito(Defines.OK);
		} catch (Exception e) {
			stringJsonResponse.setEsito(Defines.SYSTEM);
		}
		try {
			emailService.sendContatto(currentUser.getNickname(), currentUser.getEmail(), "Segnalazione di area sosta errata: " + stopId);
		} catch (Exception e) {
		}

		return stringJsonResponse;
	}

	@RequestMapping(value = "/addMobileStopPointToPreferiti", method = RequestMethod.GET)
	public @ResponseBody StringJsonResponse addStopPointToPreferitiMobile(Model model, HttpServletRequest request, HttpServletResponse response) {
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
			if (userService.addStopPointToPreferiti(stopId, currentUser.getUserId())) {
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

	@RequestMapping(value = "/removeMobileStopPointToPreferiti", method = RequestMethod.GET)
	public @ResponseBody StringJsonResponse removeStopPointToPreferitiMobile(Model model, HttpServletRequest request, HttpServletResponse response) {
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
			if (userService.removeStopPointFromPreferiti(stopId, currentUser.getUserId())) {
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

	@RequestMapping(value = "/syncMobileOfflineEvents", method = RequestMethod.POST)
	public @ResponseBody StringJsonResponse syncMobileOfflineEvents(Model model, @RequestBody final OfflineEventsJsonRequest offlineEventsJson, HttpServletResponse response) {
		response.addHeader("Access-Control-Allow-Origin", "*");
		StringJsonResponse stringJsonResponse = new StringJsonResponse();
		String userFbId = offlineEventsJson.getUser().getFbUserId();
		Long userId = offlineEventsJson.getUser().getUserId();
		UserBo currentUser = null;
		if (userFbId != null && !"".equals(userFbId) && !"null".equals(userFbId)) { 
			currentUser = userService.getUserByFbId(userFbId);
		} else {
			currentUser = userService.getUserById(userId);
		}
		for (OfflineEventsBo event : offlineEventsJson.getEvents()) {
			if (ADD_PREF.equals(event.getFunc())) {
				if (userService.addStopPointToPreferiti(event.getStopId(), currentUser.getUserId(), event.getModified())) {
					stringJsonResponse.setEsito(Defines.OK);
				} else {
					stringJsonResponse.setEsito(Defines.KO);
				}
			} else if (DEL_PREF.equals(event.getFunc())) {
				if (userService.removeStopPointFromPreferiti(event.getStopId(), currentUser.getUserId(), event.getModified())) {
					stringJsonResponse.setEsito(Defines.OK);
				} else {
					stringJsonResponse.setEsito(Defines.KO);
				}
			} else if (REVIEW.equals(event.getFunc())) {
				if (event.getReview() != null && !"".equals(event.getReview())) {
					if (reviewService.createOrUpdateReview(event.getStopId(), currentUser.getUserId(), HtmlUtils.htmlEscape(Tools.capitalizeFirstLetterInEverySentence(event.getReview().trim().toLowerCase())), event.getModified())) {
						stringJsonResponse.setEsito(Defines.OK);
					} else {
						stringJsonResponse.setEsito(Defines.SYSTEM);
					}
				}
				if (event.getRating() != null) {
					if (event.getRating() >= 2.5) {
						Float totRating = reviewService.addLikeStopPoint(event.getStopId(), currentUser.getUserId(), event.getModified());
						if (totRating != null) {
							stringJsonResponse.setEsito(Defines.OK);
						} else {
							stringJsonResponse.setEsito(Defines.KO);
						}
					} else {
						Float totRating = reviewService.addUnLikeStopPoint(event.getStopId(), currentUser.getUserId(), event.getModified());
						if (totRating != null) {
							stringJsonResponse.setEsito(Defines.OK);
						} else {
							stringJsonResponse.setEsito(Defines.KO);
						}
					}
				}
			}
		}
		for (StopPointBo stopPoint : offlineEventsJson.getStopPoints()) {
			if (stopPoint.getStopId() != null) {
				StopPointBo stopPointDB = stopPointsService.getStopPointDetail(stopPoint.getStopId());
				if (stopPointDB == null) {
					continue;
				} else if (stopPointDB.getModified().before(stopPoint.getModified())) {
					stopPointsService.insertOrUpdate(mappingStopPointToStopPointDB(stopPoint, stopPointDB, currentUser));
					stringJsonResponse.setEsito(Defines.OK);
				}
			} else {
				StopPointBo stopPointDB = new StopPointBo();
				stopPointsService.insertOrUpdate(mappingStopPointToStopPointDB(stopPoint, stopPointDB, currentUser));
				stringJsonResponse.setEsito(Defines.OK);
			}
		}
		return stringJsonResponse;
	}

	private boolean emailFormatCheck(String email) {
		if (!email.matches(Defines.EMAIL_REGEX)) return false;
		return true;
	}

	private boolean emailUniqueCheck(String email) {
		if (userService.getUserByEmail(email) != null) return false;
		return true;
	}

	private boolean nicknameUniqueCheck(String nickname) {
		if (userService.getUserByNickname(nickname) != null) return false;
		return true;
	}

	private StopPointBo mappingStopPointToStopPointDB(StopPointBo stopPoint, StopPointBo stopPointDB, UserBo currentUser) {
		stopPointDB.setLatitude(stopPoint.getLatitude());
		stopPointDB.setLongitude(stopPoint.getLongitude());
		stopPointDB.setLatitudeDeg(Tools.decimalToDMS(stopPoint.getLatitude()));
		stopPointDB.setLongitudeDeg(Tools.decimalToDMS(stopPoint.getLongitude()));
		stopPointDB.setDescription(HtmlUtils.htmlEscape(Tools.capitalizeFirstLetterInEveryWord(stopPoint.getDescription().trim().toLowerCase())));
		stopPointDB.setLocality(HtmlUtils.htmlEscape(Tools.capitalizeFirstLetterInEveryWord(stopPoint.getLocality().trim().toLowerCase())));
		stopPointDB.setNation(stopPoint.getNation().trim().toLowerCase());
		stopPointDB.setTypeId(EStopPointTypeId.fromId(stopPoint.getTypeId()));
		stopPointDB.setTotPreferito(stopPoint.getTotPreferito());
		stopPointDB.setRating(stopPoint.getRating());
		stopPointDB.setFotoPath(stopPoint.getFotoPath());
		stopPointDB.setHomepage(stopPoint.getHomepage());
		stopPointDB.setValuta(EValuta.fromId(stopPoint.getValuta()));
		stopPointDB.setPrezzoNotturno(stopPoint.getPrezzoNotturno());
		stopPointDB.setPrezzoOrario(stopPoint.getPrezzoOrario());
		stopPointDB.setPrezzoGiornaliero(stopPoint.getPrezzoGiornaliero());
		stopPointDB.setPrezzoSettimanale(stopPoint.getPrezzoSettimanale());
		stopPointDB.setPrezzoParticolare(HtmlUtils.htmlEscape((stopPoint.getPrezzoParticolare() != null) ? Tools.capitalizeFirstLetterInEverySentence(stopPoint.getPrezzoParticolare().trim().toLowerCase()) : null));
		stopPointDB.setAcqua(stopPoint.getAcqua());
		stopPointDB.setScaricoCassetta(stopPoint.getScaricoCassetta());
		stopPointDB.setScaricoPozzetto(stopPoint.getScaricoPozzetto());
		stopPointDB.setPrezzoService(stopPoint.getPrezzoService());
		stopPointDB.setCorrente(stopPoint.getCorrente());
		stopPointDB.setPrezzoCorrente(stopPoint.getPrezzoCorrente());
		stopPointDB.setTipoPiazzola(EStopPointPlaceTypeId.fromId(stopPoint.getTipoPiazzola()));
		stopPointDB.setAccessoCustodito(stopPoint.getAccessoCustodito());
		stopPointDB.setVideosorveglianza(stopPoint.getVideosorveglianza());
		stopPointDB.setNotte(stopPoint.getNotte());
		stopPointDB.setIlluminazione(stopPoint.getIlluminazione());
		stopPointDB.setOmbra(stopPoint.getOmbra());
		stopPointDB.setDocce(stopPoint.getDocce());
		stopPointDB.setBagni(stopPoint.getBagni());
		stopPointDB.setBambini(stopPoint.getBambini());
		stopPointDB.setPicnic(stopPoint.getPicnic());
		stopPointDB.setAnimali(stopPoint.getAnimali());
		stopPointDB.setFermata(stopPoint.getFermata());
		stopPointDB.setWifi(stopPoint.getWifi());
		stopPointDB.setTelefono(stopPoint.getTelefono());
		stopPointDB.setPosti(stopPoint.getPosti());
		stopPointDB.setMaxHH(stopPoint.getMaxHH());
		stopPointDB.setChiusura(stopPoint.getChiusura());
		stopPointDB.setVisibile(stopPoint.getVisibile());
		if (stopPoint.getStopId() == null) {
			stopPoint.setInserted(Calendar.getInstance().getTime());
			stopPoint.setInsertedByUser(currentUser.getUserId());
			stopPoint.setModified(Calendar.getInstance().getTime());
			stopPoint.setModifiedByUser(currentUser.getUserId());
		} else {
			stopPointDB.setModified(Calendar.getInstance().getTime());
			stopPointDB.setModifiedByUser(currentUser.getUserId());
		}
		return stopPointDB;
	}
	
}
