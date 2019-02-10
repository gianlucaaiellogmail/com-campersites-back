package info.campersites.service;

import java.util.List;

import info.campersites.bo.ReviewBo;
import info.campersites.bo.ReviewNewestBo;

public interface ReviewService {
	List<ReviewNewestBo> getReviewsNewest();
	List<ReviewBo> getByStopPoint(Long stopId);
	Float howRated(Long stopId, Long userId);
	Float addRate(Long stopId, Long userId, Float howRated);
	void addReview(Long stopId, Long userId, String testo);
}
