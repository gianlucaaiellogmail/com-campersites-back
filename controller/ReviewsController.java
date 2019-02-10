package info.campersites.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import info.campersites.bo.ReviewNewestBo;
import info.campersites.service.ReviewService;

@RestController
@RequestMapping("/reviews")
public class ReviewsController {

	@Autowired
	private ReviewService reviewService;
	
	@RequestMapping(value = "/newest", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ReviewNewestBo>> getReviewsNewest() throws Exception {
		try {
			return ResponseEntity.ok().body(reviewService.getReviewsNewest());
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

}
