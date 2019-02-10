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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import info.campersites.bo.PositionMarkerBo;
import info.campersites.bo.PositionMod;
import info.campersites.service.PositionsService;

@RestController
@RequestMapping("/positions")
public class PositionsController {

	@Autowired
	private PositionsService positionsService;
	
	@RequestMapping(value = "/bound/{bounds}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<PositionMarkerBo>> getPositionsBound(@PathVariable(value="bounds") final String bounds, HttpServletRequest request) throws Exception {
		try {
			if (StringUtils.isBlank(bounds)) return ResponseEntity.badRequest().body(null);
			return ResponseEntity.ok().body(positionsService.getPositionsBound(bounds));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

	@RequestMapping(value = "/", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PositionMod> modify(@Valid @RequestBody final PositionMod positionMod, HttpServletRequest request) throws MethodArgumentNotValidException {
		try {
			return ResponseEntity.ok().body(positionsService.modify(positionMod));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

}
