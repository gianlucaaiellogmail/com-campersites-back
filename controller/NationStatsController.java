package info.campersites.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import info.campersites.bo.NationStatsBo;
import info.campersites.service.NationStatsService;

@RestController
@RequestMapping("/nationStats")
public class NationStatsController {

	@Autowired
	private NationStatsService nationStatsService;
	
	@RequestMapping(value = "/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<NationStatsBo>> findAll() throws Exception {
		try {
			return ResponseEntity.ok().body(nationStatsService.findAll());
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

}
