package old.info.campersites.service;

import old.info.campersites.bo.ReviewBo;

import java.util.Date;
import java.util.List;

public interface OldReviewService {
	 
    List<ReviewBo> getReviewsByStopPoint(Long stopPoint);
    List<ReviewBo> getReviewsByUser(Long userId);
    boolean createReview(Long stopId, Long userId, Float rating, String reviewTitle, String review);
    boolean createOrUpdateReview(Long stopId, Long userId, String review);
    Float addLikeStopPoint(Long stopId, Long userId);
    Float addUnLikeStopPoint(Long stopId, Long userId);
    boolean createOrUpdateReview(Long stopId, Long userId, String review, Date modified);
    Float addLikeStopPoint(Long stopId, Long userId, Date modified);
    Float addUnLikeStopPoint(Long stopId, Long userId, Date modified);
    Float howRated(Long stopId, Long userId);
    
}
