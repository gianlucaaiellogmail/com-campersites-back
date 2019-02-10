package old.info.campersites.controller.mobile;

import java.util.ArrayList;
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

import old.info.campersites.bo.ReviewBo;
import old.info.campersites.bo.StopPointBo;
import old.info.campersites.bo.UserBo;
import old.info.campersites.jsonresponse.StopPointJsonResponse;
import old.info.campersites.jsonresponse.StringJsonResponse;
import old.info.campersites.service.OldReviewService;
import old.info.campersites.service.OldUserService;
import old.info.campersites.tools.Defines;
import old.info.campersites.tools.Errors;
import old.info.campersites.tools.Tools;

@RestController
public class ReviewMobileController {
	
	@Autowired
	private OldReviewService reviewService;
	@Autowired
	private OldUserService userService;
	 
	/**
	 * @deprecated
	 */
	@RequestMapping(value = "/createReviewMobile/{stopId}/{userFbId}/{userId}", method = RequestMethod.POST)
	public @ResponseBody StringJsonResponse createReviewMobile(Model model, HttpServletRequest request, HttpServletResponse response, @PathVariable Long stopId, @PathVariable String userFbId, @PathVariable Long userId) {
		response.addHeader("Access-Control-Allow-Origin", "*");
		StringJsonResponse stringJsonResponse = new StringJsonResponse();
		UserBo currentUser = null;
		if (userFbId != null && !"".equals(userFbId) && !"null".equals(userFbId)) { 
			currentUser = userService.getUserByFbId(userFbId);
		} else {
			currentUser = userService.getUserById(userId);
		}

		String reviewTitle = request.getParameter("ReviewTitle");
    	String review = request.getParameter("Review");
    	String rating = request.getParameter("ReviewRating");
    	if (reviewTitle == null || reviewTitle.trim().isEmpty()) {
    		stringJsonResponse.getErrors().add(Errors.REVIEW_TITLE_NULL);
    	}
    	if (review == null || review.trim().isEmpty()) {
    		stringJsonResponse.getErrors().add(Errors.REVIEW_NULL);
    	}
    	if (rating == null || rating.isEmpty()) {
    		stringJsonResponse.getErrors().add(Errors.RATING_NULL);
    	}
    	if (stringJsonResponse.getErrors().size() > 0) {
    		stringJsonResponse.setEsito(Defines.KO);
			return stringJsonResponse;
    	}

		try {
			if (reviewService.createReview(stopId, currentUser.getUserId(), Float.valueOf(rating), HtmlUtils.htmlEscape(Tools.capitalizeFirstLetterInEverySentence(reviewTitle.trim().toLowerCase())), HtmlUtils.htmlEscape(Tools.capitalizeFirstLetterInEverySentence(review.trim().toLowerCase())))) {
				stringJsonResponse.setEsito(Defines.OK);
			} else {
				stringJsonResponse.setEsito(Defines.SYSTEM);
			}
			return stringJsonResponse;
		} catch (Exception e) {
			stringJsonResponse.setEsito(Defines.SYSTEM);
			return stringJsonResponse;
		}
	}

	@RequestMapping(value = "/createNewReviewMobile/{stopId}/{userFbId}/{userId}", method = RequestMethod.POST)
	public @ResponseBody StringJsonResponse createNewReviewMobile(Model model, HttpServletRequest request, HttpServletResponse response, @PathVariable Long stopId, @PathVariable String userFbId, @PathVariable Long userId) {
		response.addHeader("Access-Control-Allow-Origin", "*");
		StringJsonResponse stringJsonResponse = new StringJsonResponse();
		UserBo currentUser = null;
		if (userFbId != null && !"".equals(userFbId) && !"null".equals(userFbId)) { 
			currentUser = userService.getUserByFbId(userFbId);
		} else {
			currentUser = userService.getUserById(userId);
		}

    	String review = request.getParameter("Review");
    	if (review == null || review.trim().isEmpty()) {
    		stringJsonResponse.getErrors().add(Errors.REVIEW_NULL);
    	}
    	if (stringJsonResponse.getErrors().size() > 0) {
    		stringJsonResponse.setEsito(Defines.KO);
			return stringJsonResponse;
    	}

		try {
			if (reviewService.createOrUpdateReview(stopId, currentUser.getUserId(), HtmlUtils.htmlEscape(Tools.capitalizeFirstLetterInEverySentence(review.trim().toLowerCase())))) {
				stringJsonResponse.setEsito(Defines.OK);
			} else {
				stringJsonResponse.setEsito(Defines.SYSTEM);
			}
			return stringJsonResponse;
		} catch (Exception e) {
			stringJsonResponse.setEsito(Defines.SYSTEM);
			return stringJsonResponse;
		}
	}

	@RequestMapping(value = "/addLikeStopPointMobile/{stopId}/{userFbId}/{userId}", method = RequestMethod.POST)
	public @ResponseBody StopPointJsonResponse addLikeStopPointMobile(Model model, HttpServletRequest request, HttpServletResponse response, @PathVariable Long stopId, @PathVariable String userFbId, @PathVariable Long userId) {
		response.addHeader("Access-Control-Allow-Origin", "*");
		StopPointJsonResponse stopPointJsonResponse = new StopPointJsonResponse();
		UserBo currentUser = null;
		if (userFbId != null && !"".equals(userFbId) && !"null".equals(userFbId)) { 
			currentUser = userService.getUserByFbId(userFbId);
		} else {
			currentUser = userService.getUserById(userId);
		}

		try {
			Float totRating = reviewService.addLikeStopPoint(stopId, currentUser.getUserId());
			if (totRating != null) {
				stopPointJsonResponse.setEsito(Defines.OK);
				StopPointBo stopPointBo = new StopPointBo();
				stopPointBo.setRating(totRating);
				stopPointJsonResponse.setStopPoint(stopPointBo);
			} else {
				stopPointJsonResponse.setEsito(Defines.KO);
			}
			return stopPointJsonResponse;
		} catch (Exception e) {
			stopPointJsonResponse.setEsito(Defines.SYSTEM);
			return stopPointJsonResponse;
		}
	}

	@RequestMapping(value = "/addUnLikeStopPoint/{stopId}/{userFbId}/{userId}", method = RequestMethod.POST)
	public @ResponseBody StopPointJsonResponse addUnLikeStopPointMobile(Model model, HttpServletRequest request, HttpServletResponse response, @PathVariable Long stopId, @PathVariable String userFbId, @PathVariable Long userId) {
		response.addHeader("Access-Control-Allow-Origin", "*");
		StopPointJsonResponse stopPointJsonResponse = new StopPointJsonResponse();
		UserBo currentUser = null;
		if (userFbId != null && !"".equals(userFbId) && !"null".equals(userFbId)) { 
			currentUser = userService.getUserByFbId(userFbId);
		} else {
			currentUser = userService.getUserById(userId);
		}

		try {
			Float totRating = reviewService.addUnLikeStopPoint(stopId, currentUser.getUserId());
			if (totRating != null) {
				stopPointJsonResponse.setEsito(Defines.OK);
				StopPointBo stopPointBo = new StopPointBo();
				stopPointBo.setRating(totRating);
				stopPointJsonResponse.setStopPoint(stopPointBo);
			} else {
				stopPointJsonResponse.setEsito(Defines.KO);
			}
			return stopPointJsonResponse;
		} catch (Exception e) {
			stopPointJsonResponse.setEsito(Defines.SYSTEM);
			return stopPointJsonResponse;
		}
	}

	@RequestMapping(value = "/getMobileUserReviews", method = RequestMethod.GET)
	public @ResponseBody List<ReviewBo> getMobileUserReviews(Model model, HttpServletRequest request, HttpServletResponse response) {
		response.addHeader("Access-Control-Allow-Origin", "*");
		UserBo currentUser = null;
		String userFbId = request.getParameter("userFbId");
		String userId = request.getParameter("userId");
		if (userFbId != null && !"".equals(userFbId) && !"null".equals(userFbId)) { 
			currentUser = userService.getUserByFbId(userFbId);
		} else {
			currentUser = userService.getUserById(Long.parseLong(userId));
		}
		List<ReviewBo> reviews = new ArrayList<ReviewBo>();
		reviews = reviewService.getReviewsByUser(currentUser.getUserId());
		return reviews;
	}

}
