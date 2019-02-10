package info.campersites.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.HtmlUtils;

import info.campersites.bo.ReviewBo;
import info.campersites.bo.ReviewNewestBo;
import info.campersites.entity.ReviewEntity;
import info.campersites.entity.StopPointsEntity;
import info.campersites.entity.UserEntity;
import info.campersites.repository.ReviewRepository;
import info.campersites.repository.StopPointsRepository;
import info.campersites.repository.UserRepository;

@Service
@Transactional(readOnly=true, propagation=Propagation.REQUIRED)
public class ReviewServiceImpl implements ReviewService {
	
	@Autowired
	ReviewRepository reviewRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	StopPointsRepository stopPointsRepository;

	@Override
	@Cacheable(value="reviewsNewestCache")
	public List<ReviewNewestBo> getReviewsNewest() {
		List<ReviewEntity> reviewsEntity = reviewRepository.findTop10ByReviewNotNullOrderByModifiedDescInsertedAsc();
    	return toNewestBos(reviewsEntity);
	}

	@Override
    public List<ReviewBo> getByStopPoint(final Long stopId) {
    	List<ReviewEntity> reviewsEntity = reviewRepository.getByStopPointIdOrderByInsertedDesc(stopId);
    	return toBos(reviewsEntity);
    }
	
	@Override
	public Float howRated(Long stopId, Long userId) {
		ReviewEntity reviewEntity = reviewRepository.findByStopPointIdAndInsertedUserId(stopId, userId);
		return reviewEntity != null ? reviewEntity.getRating() : null;
	}
	
	@Override
	@Transactional(readOnly = false)
	@CacheEvict(value = {"stopPointsTopCache", "usersTopCache"}, allEntries = true)
	public Float addRate(Long stopId, Long userId, Float howRated) {
		ReviewEntity reviewEntity = reviewRepository.findByStopPointIdAndInsertedUserId(stopId, userId);
		if (reviewEntity == null) {
			reviewEntity = new ReviewEntity();
			reviewEntity.setStopPointId(stopId);

			// Aggiorno numero di rating utente
			UserEntity userEntity = userRepository.findOne(userId);
			userEntity.setTotReviews(userEntity.getTotReviews()+1);
			userRepository.save(userEntity);
		}
		reviewEntity.setRating(howRated);
		reviewEntity.setModified(Calendar.getInstance().getTime());
		reviewEntity.setModifiedUserId(userId);
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
		StopPointsEntity stopPointEntity = stopPointsRepository.findOne(stopId);
		stopPointEntity.setRating(totRating);
		stopPointsRepository.save(stopPointEntity);
		
		return totRating;
	}

	@Override
	@Transactional(readOnly = false)
	@CacheEvict(value = {"reviewsNewestCache", "usersTopCache"}, allEntries = true)
	public void addReview(Long stopId, Long userId, String testo) {
		ReviewEntity reviewEntity = reviewRepository.findByStopPointIdAndInsertedUserId(stopId, userId);
		if (reviewEntity == null) { 
			// Creo review
			reviewEntity = new ReviewEntity();
			reviewEntity.setStopPointId(stopId);
			reviewEntity.setRating(null);
		}
		reviewEntity.setReview(testo);
		reviewEntity.setModified(Calendar.getInstance().getTime());
		reviewEntity.setModifiedUserId(userId);
		reviewRepository.save(reviewEntity);
	}

	private List<ReviewNewestBo> toNewestBos(List<ReviewEntity> reviewsEntity) {
		List<ReviewNewestBo> reviewsNewest = new ArrayList<ReviewNewestBo>();
		for (ReviewEntity reviewEntity : reviewsEntity) {
			if (reviewEntity.getInsertedUserId() > 0) {
				reviewsNewest.add(toNewestBo(reviewEntity));
			}
		}
		return reviewsNewest;
	}

	private ReviewNewestBo toNewestBo(final ReviewEntity reviewEntity) {
		ReviewNewestBo reviewNewest = new ReviewNewestBo();
		BeanUtils.copyProperties(reviewEntity, reviewNewest);
		reviewNewest.setReview(HtmlUtils.htmlUnescape(reviewNewest.getReview()));
		reviewNewest.setUser(userRepository.findOne(reviewEntity.getInsertedUserId()).getNickname());
		return reviewNewest;
	}
	
	private List<ReviewBo> toBos(List<ReviewEntity> reviewsEntity) {
		List<ReviewBo> reviews = new ArrayList<ReviewBo>();
		for (ReviewEntity reviewEntity : reviewsEntity) {
			if (reviewEntity.getInsertedUserId() > 0 &&
				StringUtils.isNotBlank(reviewEntity.getReview())) {
				reviews.add(toBo(reviewEntity));
			}
		}
		return reviews;
	}

	private ReviewBo toBo(final ReviewEntity reviewEntity) {
		ReviewBo review = new ReviewBo();
		BeanUtils.copyProperties(reviewEntity, review);
		review.setReview(HtmlUtils.htmlUnescape(review.getReview()));
		UserEntity userEntity = userRepository.findOne(reviewEntity.getInsertedUserId());
		review.setUserNickname(userEntity.getNickname());
		review.setUserReviews(userEntity.getTotReviews());
		review.setUserLocale(userEntity.getLocale());
		return review;
	}

}
