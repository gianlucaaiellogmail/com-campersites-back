package info.campersites.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import info.campersites.bo.TripBo;
import info.campersites.service.TripService;

@RestController
@RequestMapping("/trip")
public class TripController {

	@Autowired
	private TripService tripService;
	
	@RequestMapping(value = "/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<TripBo>> getTripsByUser(HttpServletRequest request) throws Exception {
		try {
			if (StringUtils.isBlank(request.getHeader("X-User"))) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
			}
			return ResponseEntity.ok().body(tripService.getTripsByUser(Long.parseLong(request.getHeader("X-User"))));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

	@RequestMapping(value = "/", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TripBo> save(@Valid @RequestBody final TripBo tripBo, HttpServletRequest request) throws MethodArgumentNotValidException {
		try {
			if (StringUtils.isBlank(request.getHeader("X-User"))) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
			}
			return ResponseEntity.ok().body(tripService.save(tripBo, Long.parseLong(request.getHeader("X-User"))));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

}
