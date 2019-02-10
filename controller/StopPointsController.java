package info.campersites.controller;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import info.campersites.bo.ReviewNew;
import info.campersites.bo.StopPointBo;
import info.campersites.bo.StopPointMarkerBo;
import info.campersites.bo.StopPointMod;
import info.campersites.bo.StopPointNewestBo;
import info.campersites.bo.StopPointTopBo;
import info.campersites.comparetor.StopPointDistanceComparetor;
import info.campersites.service.ReviewService;
import info.campersites.service.StopPointsService;
import info.campersites.service.UserPreferitiService;
import info.campersites.utils.Tools;

@RestController
@RequestMapping("/stoppoints")
public class StopPointsController {

	@Autowired
	private StopPointsService stopPointsService;
	@Autowired
	private	ReviewService reviewService;
	@Autowired
	private UserPreferitiService userPreferitiService;
	
	@RequestMapping(value = "/bound/{bounds}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<StopPointMarkerBo>> getStopPointsBound(@PathVariable(value="bounds") final String bounds, HttpServletRequest request) throws Exception {
		boolean visibile = false;
		try {
			if (StringUtils.isBlank(bounds)) return ResponseEntity.badRequest().body(null);
			if (StringUtils.isNotBlank(request.getHeader("X-Visibile"))) visibile = Boolean.parseBoolean(request.getHeader("X-Visibile"));
			return ResponseEntity.ok().body(stopPointsService.getStopPointsBound(bounds, visibile));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

	@RequestMapping(value = "/newest", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<StopPointNewestBo>> getStopPointsNewest() throws Exception {
		try {
			return ResponseEntity.ok().body(stopPointsService.getStopPointsNewest());
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

	@RequestMapping(value = "/top", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<StopPointTopBo>> getStopPointsTop() throws Exception {
		try {
			return ResponseEntity.ok().body(stopPointsService.getStopPointsTop());
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

	@RequestMapping(value = "/{stopId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<StopPointBo> getStopPoint(@PathVariable(value="stopId") final Long stopId, HttpServletRequest request) throws Exception {
		try {
			if (stopId == null) return ResponseEntity.badRequest().body(null);
			Long userId = request.getHeader("X-User") != null ? Long.parseLong(request.getHeader("X-User")) : null;
			StopPointBo stopPointBo = stopPointsService.getStopPoint(stopId, userId);
			if (stopPointBo.getStopId() != null) {
				return ResponseEntity.ok().body(stopPointBo);
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

	@RequestMapping(value = "/nation/{nation}/{page}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Page<StopPointMarkerBo>> getByNation(@PathVariable(value="nation") final String nation, @PathVariable(value="page") final Integer page) throws Exception {
		try {
			if (StringUtils.isBlank(nation) || page == null || page < 1) return ResponseEntity.badRequest().body(null);
			return ResponseEntity.ok().body(stopPointsService.getByNation(nation, page));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

	@RequestMapping(value = "/", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<StopPointBo> modify(@Valid @RequestBody final StopPointMod stopPointMod, HttpServletRequest request) throws MethodArgumentNotValidException {
		try {
			if (StringUtils.isBlank(request.getHeader("X-User"))) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
			}
			return ResponseEntity.ok().body(stopPointsService.modify(stopPointMod, Long.parseLong(request.getHeader("X-User"))));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

	@RequestMapping(value = "/{stopId}/rate/{howRated}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<StopPointBo> rateIt(@PathVariable(value="stopId") final Long stopId, @PathVariable(value="howRated") final Float howRated, HttpServletRequest request) throws Exception {
		try {
			if (StringUtils.isBlank(request.getHeader("X-User"))) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
			}
			reviewService.addRate(stopId, Long.parseLong(request.getHeader("X-User")), howRated);
			return ResponseEntity.ok().body(stopPointsService.getStopPoint(stopId, Long.parseLong(request.getHeader("X-User"))));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

	@RequestMapping(value = "/preferito/{stopId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<StopPointBo> togglePreferito(@PathVariable(value="stopId") final Long stopId, HttpServletRequest request) throws Exception {
		try {
			if (StringUtils.isBlank(request.getHeader("X-User"))) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
			}
			userPreferitiService.togglePreferito(stopId, Long.parseLong(request.getHeader("X-User")));
			return ResponseEntity.ok().body(stopPointsService.getStopPoint(stopId, Long.parseLong(request.getHeader("X-User"))));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

	@RequestMapping(value = "/{stopId}/review", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<StopPointBo> reviewIt(@PathVariable(value="stopId") final Long stopId, @Valid @RequestBody final ReviewNew review, HttpServletRequest request) throws Exception {
		try {
			if (StringUtils.isBlank(request.getHeader("X-User"))) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
			}
			reviewService.addReview(stopId, Long.parseLong(request.getHeader("X-User")), review.getTesto());
			return ResponseEntity.ok().body(stopPointsService.getStopPoint(stopId, Long.parseLong(request.getHeader("X-User"))));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

	@RequestMapping(value = "/preferiti", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<StopPointMarkerBo>> getPreferiti(@RequestParam(value="position", required = false) final String position, HttpServletRequest request) throws Exception {
		try {
			if (StringUtils.isBlank(request.getHeader("X-User"))) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
			}
			List<StopPointMarkerBo> stopPoints = new ArrayList<StopPointMarkerBo>();
			stopPoints = stopPointsService.getPreferiti(Long.parseLong(request.getHeader("X-User")));
			
			if (StringUtils.isNotBlank(position)) {
				String[] coordinates = position.split(",");
				Float latitude = Float.valueOf(coordinates[0]);
				Float longitude = Float.valueOf(coordinates[1]);
				DecimalFormat df = new DecimalFormat("##.##", DecimalFormatSymbols.getInstance(request.getLocale()));
				df.setRoundingMode(RoundingMode.HALF_UP);
		    	for (StopPointMarkerBo stopPoint : stopPoints) {
					Double distance = Tools.distance(latitude, longitude, stopPoint.getLatitude(), stopPoint.getLongitude());
					stopPoint.setDistance(Double.parseDouble(df.format(distance)));
		    	}
				Collections.sort(stopPoints, new StopPointDistanceComparetor());
			}

			return ResponseEntity.ok().body(stopPoints);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

}
