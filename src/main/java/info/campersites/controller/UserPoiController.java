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

import info.campersites.bo.UserPoiBo;
import info.campersites.service.UserPoiService;

@RestController
@RequestMapping("/userpois")
public class UserPoiController {

	@Autowired
	private UserPoiService userPoiService;

	@RequestMapping(value = "/bound/{bounds}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<UserPoiBo>> getUserPoisBound(@PathVariable(value="bounds") final String bounds, HttpServletRequest request) throws Exception {
		try {
			if (StringUtils.isBlank(request.getHeader("X-User"))) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
			}
			if (StringUtils.isBlank(bounds)) return ResponseEntity.badRequest().body(null);
			return ResponseEntity.ok().body(userPoiService.getUserPoisBound(bounds, Long.parseLong(request.getHeader("X-User"))));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

	@RequestMapping(value = "/", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserPoiBo> modify(@Valid @RequestBody final UserPoiBo userPoiBo, HttpServletRequest request) throws MethodArgumentNotValidException {
		try {
			if (StringUtils.isBlank(request.getHeader("X-User"))) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
			}
			return ResponseEntity.ok().body(userPoiService.modify(userPoiBo, Long.parseLong(request.getHeader("X-User"))));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

	@RequestMapping(value = "/delete/{poiId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> delete(@PathVariable(value="poiId") final Long poiId, HttpServletRequest request) throws Exception {
		try {
			if (StringUtils.isBlank(request.getHeader("X-User"))) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
			}
			boolean result = userPoiService.delete(poiId);
			if (result) {
				return ResponseEntity.ok().body(null);
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

}
