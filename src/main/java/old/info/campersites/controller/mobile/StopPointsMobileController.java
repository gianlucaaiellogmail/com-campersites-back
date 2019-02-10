package old.info.campersites.controller.mobile;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import info.campersites.entity.ConfigurationEntity;
import info.campersites.repository.ConfigurationRepository;
import old.info.campersites.bo.NationStatsBo;
import old.info.campersites.bo.StopPointBo;
import old.info.campersites.bo.UserBo;
import old.info.campersites.comparetor.StopPointDistanceComparetor;
import old.info.campersites.comparetor.StopPointRatingComparetor;
import old.info.campersites.service.OldStopPointsService;
import old.info.campersites.service.OldUserService;
import old.info.campersites.tools.Tools;

@RestController
public class StopPointsMobileController {
	 
	@Autowired
	private OldStopPointsService stopPointsService;
	@Autowired
	private OldUserService userService;
	@Autowired
	private ConfigurationRepository configurationRepository; 
	
	protected static final int NUMBER_OF_STOPPOINTS_PER_PAGE = 10;
	protected static final String STOP_POINTS = "stopPoints";
	protected static final String LAST_PAGE = "lastPage";
	protected static final String STOP_POINTS_TRIP = "stopPointsTrip";
	protected static final Double DISTANCE = 10.0;
	
	@RequestMapping(value = "/getMobileStopPointsPreferiti", method = RequestMethod.GET)
	public @ResponseBody List<StopPointBo> getMobileStopPointsPreferiti(Model model, HttpServletRequest request, HttpServletResponse response) {
		response.addHeader("Access-Control-Allow-Origin", "*");
		UserBo currentUser = null;
		String userFbId = request.getParameter("userFbId");
		String userId = request.getParameter("userId");
		if (userFbId != null && !"".equals(userFbId) && !"null".equals(userFbId)) { 
			currentUser = userService.getUserByFbId(userFbId);
		} else {
			currentUser = userService.getUserById(Long.parseLong(userId));
		}
		List<StopPointBo> stopPoints = new ArrayList<StopPointBo>();
		Float latitude = Float.parseFloat(request.getParameter("latitude"));
		Float longitude = Float.parseFloat(request.getParameter("longitude"));
		Double maxDistance = Double.parseDouble(request.getParameter("maxDistance"));
		stopPoints = stopPointsService.getStopPointsPreferiti(currentUser);
		DecimalFormat df = new DecimalFormat("##.##", DecimalFormatSymbols.getInstance(Locale.ENGLISH));
		df.setRoundingMode(RoundingMode.HALF_UP);
    	for (StopPointBo stopPoint : stopPoints) {
			Double distance = Tools.distance(latitude, longitude, stopPoint.getLatitude(), stopPoint.getLongitude(), Tools.KILOMETRI);
    		if (maxDistance == 0 || distance <= maxDistance) {
				stopPoint.setDistance(Double.parseDouble(df.format(distance)));
    		}
    	}
		Collections.sort(stopPoints, new StopPointDistanceComparetor());
		return stopPoints;
	}

	@RequestMapping(value = "/getStopPointsFromPoint", method = RequestMethod.GET)
	public @ResponseBody List<StopPointBo> getStopPointsFromPoint(Model model, HttpServletRequest request, HttpServletResponse response) {
		response.addHeader("Access-Control-Allow-Origin", "*");
		Float latitude = Float.parseFloat(request.getParameter("latitude"));
		Float longitude = Float.parseFloat(request.getParameter("longitude"));
		Double maxDistance = Double.parseDouble(request.getParameter("maxDistance"));
		List<StopPointBo> stopPoints = new ArrayList<StopPointBo>();
		stopPoints = stopPointsService.getStopPointsFromPoint(latitude, longitude, maxDistance);
		Collections.sort(stopPoints, new StopPointDistanceComparetor());
		return stopPoints;
	}
	
	@RequestMapping(value = "/getStopPointsBoundMobile", method = RequestMethod.GET)
	public @ResponseBody List<StopPointBo> getStopPointsBoundMobile(Model model, HttpServletRequest request, HttpServletResponse response) {
		response.addHeader("Access-Control-Allow-Origin", "*");
		String bounds = request.getParameter("bounds");
		List<StopPointBo> stopPoints = new ArrayList<StopPointBo>();
		stopPoints = stopPointsService.getStopPointsBound(bounds, null);
		Collections.sort(stopPoints, new StopPointRatingComparetor());
		return stopPoints;
	}
	
//	@RequestMapping(value = "/getMobileStopPointsByNation", method = RequestMethod.GET)
//	public @ResponseBody List<StopPointBo> getMobileStopPointsByNation(Model model, HttpServletRequest request, HttpServletResponse response) {
//		response.addHeader("Access-Control-Allow-Origin", "*");
//		String nation = request.getParameter("nation");
//		List<StopPointBo> stopPoints = stopPointsService.getStopPointsByNation(nation);
//		return stopPoints;
//	}

	@Transactional(readOnly=true, propagation=Propagation.REQUIRED)
	@RequestMapping(value = "/getMobileStopPointsByNation", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public @ResponseBody String getMobileStopPointsByNation(Model model, HttpServletRequest request, HttpServletResponse response) {
		response.addHeader("Access-Control-Allow-Origin", "*");
		String nation = request.getParameter("nation");
		String storageDirectory = configurationRepository.getOne(ConfigurationEntity.IMAGES_DIRECTORY).getValore();
        File jsonFile = new File(storageDirectory + "/" + nation + ".json");
		String fileContent = null;
		try (BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(jsonFile), "UTF8"))) {
			fileContent = in.readLine();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		//System.out.println(fileContent);
		return fileContent;
	}

	@RequestMapping(value = "/getMobileNationStats", method = RequestMethod.GET)
	public @ResponseBody List<NationStatsBo> getMobileNationStats(Model model, HttpServletRequest request, HttpServletResponse response) {
		response.addHeader("Access-Control-Allow-Origin", "*");
		List<NationStatsBo> nationStats = stopPointsService.getNationStats();
		return nationStats;
	}

}
