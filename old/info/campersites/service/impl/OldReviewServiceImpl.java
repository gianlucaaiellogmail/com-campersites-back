package old.info.campersites.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.HtmlUtils;

import info.campersites.entity.ReviewEntity;
import info.campersites.entity.StopPointsEntity;
import info.campersites.entity.UserEntity;
import info.campersites.repository.ReviewRepository;
import info.campersites.repository.StopPointsRepository;
import info.campersites.repository.UserRepository;
import old.info.campersites.bo.ReviewBo;
import old.info.campersites.service.OldReviewService;

@Service
@Transactional(readOnly = true)
public class OldReviewServiceImpl implements OldReviewService {
	
	private static final Float like = new Float(5);
	private static final Float unlike = new Float(0);
	 
    @Autowired
	private ReviewRepository reviewRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private StopPointsRepository stopPointsRepository;
	
	@Override
    public List<ReviewBo> getReviewsByStopPoint(Long stopPoint) {
		List<ReviewBo> reviewsBo = new ArrayList<ReviewBo>();
    	List<ReviewEntity> reviewsEntity = reviewRepository.findByStopPointId(stopPoint);
    	for (ReviewEntity reviewEntity : reviewsEntity) {
    		if (reviewEntity.getReview() == null || "".equals(reviewEntity.getReview())) continue;
    		ReviewBo reviewBo = new ReviewBo(reviewEntity.getReviewId());
    		reviewBo.setStopId(reviewEntity.getStopPointId());
    		reviewBo.setUserId(reviewEntity.getInsertedUserId());
    		reviewBo.setReviewTitle(HtmlUtils.htmlUnescape(reviewEntity.getReviewTitle()));
    		reviewBo.setReview(HtmlUtils.htmlUnescape(reviewEntity.getReview()));
    		reviewBo.setRating(reviewEntity.getRating());
    		reviewBo.setInserted(reviewEntity.getInserted());
    		UserEntity userEntity = userRepository.getOne(reviewEntity.getInsertedUserId());
    		reviewBo.setUserNickname(userEntity.getNickname());
    		reviewBo.setUserReviews(userEntity.getTotReviews());
    		reviewBo.setUserTotPreferiti(userEntity.getTotPreferiti());
   			reviewBo.setUserPhotoPath(userEntity.getPhotoPath());
    		reviewBo.setFbUserId(userEntity.getFbUserId());
    		reviewsBo.add(reviewBo);
    	}
        return reviewsBo;
    }
	
	@Override
    public List<ReviewBo> getReviewsByUser(Long userId) {
		List<ReviewBo> reviewsBo = new ArrayList<ReviewBo>();
    	List<ReviewEntity> reviewsEntity = reviewRepository.findByInsertedUserId(userId);
    	for (ReviewEntity reviewEntity : reviewsEntity) {
    		if (reviewEntity.getReview() == null || "".equals(reviewEntity.getReview())) continue;
    		ReviewBo reviewBo = new ReviewBo(reviewEntity.getReviewId());
    		reviewBo.setStopId(reviewEntity.getStopPointId());
    		reviewBo.setUserId(reviewEntity.getInsertedUserId());
    		reviewBo.setReviewTitle(HtmlUtils.htmlUnescape(reviewEntity.getReviewTitle()));
    		reviewBo.setReview(HtmlUtils.htmlUnescape(reviewEntity.getReview()));
    		reviewBo.setRating(reviewEntity.getRating());
    		reviewBo.setInserted(reviewEntity.getInserted());
    		UserEntity userEntity = userRepository.getOne(reviewEntity.getInsertedUserId());
    		reviewBo.setUserNickname(userEntity.getNickname());
    		reviewBo.setUserReviews(userEntity.getTotReviews());
    		reviewBo.setUserTotPreferiti(userEntity.getTotPreferiti());
   			reviewBo.setUserPhotoPath(userEntity.getPhotoPath());
    		reviewBo.setFbUserId(userEntity.getFbUserId());
    		reviewsBo.add(reviewBo);
    	}
        return reviewsBo;
    }
	
	@Override
	@Transactional(readOnly = false)
	@CacheEvict(value = {"reviewsNewestCache", "usersTopCache"}, allEntries = true)
	public boolean createReview(Long stopId, Long userId, Float rating, String reviewTitle, String review) {
		// Creo review
		ReviewEntity reviewEntity = new ReviewEntity();
		reviewEntity.setStopPointId(stopId);
		reviewEntity.setRating(rating);
		reviewEntity.setReviewTitle(reviewTitle);
		reviewEntity.setReview(review);
		reviewEntity.setInserted(Calendar.getInstance().getTime());
		reviewEntity.setInsertedUserId(userId);
		reviewRepository.save(reviewEntity);
		
		// Aggiorno media rating area sosta
		Float totRating = new Float(0);
		List<ReviewEntity> reviewsEntity = reviewRepository.findByStopPointId(stopId);
		if (reviewsEntity.size() > 0) {
			for (ReviewEntity reviewRating : reviewsEntity) {
				totRating = totRating + reviewRating.getRating();
			}
			totRating = totRating / reviewsEntity.size();
		}
		StopPointsEntity stopPointEntity = stopPointsRepository.getOne(stopId);
		stopPointEntity.setRating(totRating);
		stopPointsRepository.save(stopPointEntity);
		
		// Aggiorno numero di rating utente
		UserEntity userEntity = userRepository.getOne(reviewEntity.getInsertedUserId());
		userEntity.setTotReviews(userEntity.getTotReviews()+1);
		userRepository.save(userEntity);
		
		return true;
	}
	
	@Override
	@Transactional(readOnly = false)
	@CacheEvict(value = {"reviewsNewestCache", "usersTopCache"}, allEntries = true)
	public boolean createOrUpdateReview(Long stopId, Long userId, String review) {
		ReviewEntity reviewEntity = reviewRepository.findByStopPointIdAndInsertedUserId(stopId, userId);
		if (reviewEntity == null) { 
			// Creo review
			reviewEntity = new ReviewEntity();
			reviewEntity.setStopPointId(stopId);
			reviewEntity.setRating(null);
		}
		reviewEntity.setReview(review);
		reviewEntity.setInserted(Calendar.getInstance().getTime());
		reviewEntity.setInsertedUserId(userId);
		reviewRepository.save(reviewEntity);
		
		return true;
	}

	@Override
	@Transactional(readOnly = false)
	@CacheEvict(value = {"stopPointsTopCache", "usersTopCache"}, allEntries = true)
	public Float addLikeStopPoint(Long stopId, Long userId) {
		ReviewEntity reviewEntity = reviewRepository.findByStopPointIdAndInsertedUserId(stopId, userId);
		if (reviewEntity == null) { 
			// Creo review
			reviewEntity = new ReviewEntity();
			reviewEntity.setStopPointId(stopId);

			// Aggiorno numero di rating utente
			UserEntity userEntity = userRepository.getOne(userId);
			userEntity.setTotReviews(userEntity.getTotReviews()+1);
			userRepository.save(userEntity);
		}
		reviewEntity.setRating(like);
		reviewEntity.setInserted(Calendar.getInstance().getTime());
		reviewEntity.setInsertedUserId(userId);
		reviewRepository.save(reviewEntity);

		// Aggiorno media rating area sosta
		Float totRating = new Float(0);
		List<ReviewEntity> reviewsEntity = reviewRepository.findByStopPointId(stopId);
		if (reviewsEntity.size() > 0) {
			for (ReviewEntity reviewRating : reviewsEntity) {
				if (reviewRating.getRating() != null) {
					totRating = totRating + reviewRating.getRating();
				}
			}
			totRating = totRating / reviewsEntity.size();
		}
		StopPointsEntity stopPointEntity = stopPointsRepository.getOne(stopId);
		stopPointEntity.setRating(totRating);
		stopPointsRepository.save(stopPointEntity);
		
		return totRating;
	}

	@Override
	@Transactional(readOnly = false)
	@CacheEvict(value = {"stopPointsTopCache", "usersTopCache"}, allEntries = true)
	public Float addUnLikeStopPoint(Long stopId, Long userId) {
		ReviewEntity reviewEntity = reviewRepository.findByStopPointIdAndInsertedUserId(stopId, userId);
		if (reviewEntity == null) { 
			// Creo review
			reviewEntity = new ReviewEntity();
			reviewEntity.setStopPointId(stopId);

			// Aggiorno numero di rating utente
			UserEntity userEntity = userRepository.getOne(userId);
			userEntity.setTotReviews(userEntity.getTotReviews()+1);
			userRepository.save(userEntity);
		}
		reviewEntity.setRating(unlike);
		reviewEntity.setInserted(Calendar.getInstance().getTime());
		reviewEntity.setInsertedUserId(userId);
		reviewRepository.save(reviewEntity);

		// Aggiorno media rating area sosta
		Float totRating = new Float(0);
		List<ReviewEntity> reviewsEntity = reviewRepository.findByStopPointId(stopId);
		if (reviewsEntity.size() > 0) {
			for (ReviewEntity reviewRating : reviewsEntity) {
				if (reviewRating.getRating() != null) {
					totRating = totRating + reviewRating.getRating();
				}
			}
			totRating = totRating / reviewsEntity.size();
		}
		StopPointsEntity stopPointEntity = stopPointsRepository.getOne(stopId);
		stopPointEntity.setRating(totRating);
		stopPointsRepository.save(stopPointEntity);
		
		return totRating;
	}
	
	@Override
	@Transactional(readOnly = false)
	@CacheEvict(value = {"reviewsNewestCache", "usersTopCache"}, allEntries = true)
	public boolean createOrUpdateReview(Long stopId, Long userId, String review, Date modified) {
		ReviewEntity reviewEntity = reviewRepository.findByStopPointIdAndInsertedUserId(stopId, userId);
		if (reviewEntity == null || modified.after(reviewEntity.getInserted())) {
			if (reviewEntity == null) {
				reviewEntity = new ReviewEntity();
			}
			reviewEntity.setStopPointId(stopId);
			reviewEntity.setRating(null);
			reviewEntity.setReview(review);
			reviewEntity.setInserted(modified);
			reviewEntity.setInsertedUserId(userId);
			reviewRepository.save(reviewEntity);
		}
		return true;
	}

	@Override
	@Transactional(readOnly = false)
	@CacheEvict(value = {"stopPointsTopCache", "usersTopCache"}, allEntries = true)
	public Float addLikeStopPoint(Long stopId, Long userId, Date modified) {
		ReviewEntity reviewEntity = reviewRepository.findByStopPointIdAndInsertedUserId(stopId, userId);
		if (reviewEntity == null || modified.after(reviewEntity.getInserted())) {
			if (reviewEntity == null) {
				reviewEntity = new ReviewEntity();	
				// Aggiorno numero di rating utente
				UserEntity userEntity = userRepository.getOne(userId);
				userEntity.setTotReviews(userEntity.getTotReviews()+1);
				userRepository.save(userEntity);
			}
			reviewEntity.setStopPointId(stopId);
			reviewEntity.setRating(like);
			reviewEntity.setInserted(modified);
			reviewEntity.setInsertedUserId(userId);
			reviewRepository.save(reviewEntity);

			// Aggiorno media rating area sosta
			Float totRating = new Float(0);
			List<ReviewEntity> reviewsEntity = reviewRepository.findByStopPointId(stopId);
			if (reviewsEntity.size() > 0) {
				for (ReviewEntity reviewRating : reviewsEntity) {
					if (reviewRating.getRating() != null) {
						totRating = totRating + reviewRating.getRating();
					}
				}
				totRating = totRating / reviewsEntity.size();
			}
			StopPointsEntity stopPointEntity = stopPointsRepository.getOne(stopId);
			stopPointEntity.setRating(totRating);
			stopPointsRepository.save(stopPointEntity);
			return totRating;
		}
		return new Float(0);
	}

	@Override
	@Transactional(readOnly = false)
	@CacheEvict(value = {"stopPointsTopCache", "usersTopCache"}, allEntries = true)
	public Float addUnLikeStopPoint(Long stopId, Long userId, Date modified) {
		ReviewEntity reviewEntity = reviewRepository.findByStopPointIdAndInsertedUserId(stopId, userId);
		if (reviewEntity == null || modified.after(reviewEntity.getInserted())) {
			if (reviewEntity == null) {
				reviewEntity = new ReviewEntity();	
				// Aggiorno numero di rating utente
				UserEntity userEntity = userRepository.getOne(userId);
				userEntity.setTotReviews(userEntity.getTotReviews()+1);
				userRepository.save(userEntity);
			}
			reviewEntity.setStopPointId(stopId);
			reviewEntity.setRating(unlike);
			reviewEntity.setInserted(modified);
			reviewEntity.setInsertedUserId(userId);
			reviewRepository.save(reviewEntity);

			// Aggiorno media rating area sosta
			Float totRating = new Float(0);
			List<ReviewEntity> reviewsEntity = reviewRepository.findByStopPointId(stopId);
			if (reviewsEntity.size() > 0) {
				for (ReviewEntity reviewRating : reviewsEntity) {
					if (reviewRating.getRating() != null) {
						totRating = totRating + reviewRating.getRating();
					}
				}
				totRating = totRating / reviewsEntity.size();
			}
			StopPointsEntity stopPointEntity = stopPointsRepository.getOne(stopId);
			stopPointEntity.setRating(totRating);
			stopPointsRepository.save(stopPointEntity);
			return totRating;
		}
		return new Float(0);
	}
	
	@Override
    public Float howRated(Long stopId, Long userId) {
		ReviewEntity reviewEntity = reviewRepository.findByStopPointIdAndInsertedUserId(stopId, userId);
		if (reviewEntity != null) {
			return reviewEntity.getRating();
		} 
		return null;
    }
	
}
