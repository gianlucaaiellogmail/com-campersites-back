package info.campersites.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import info.campersites.entity.ReviewEntity;

public interface ReviewRepository extends JpaRepository<ReviewEntity, Long> {
	
	public List<ReviewEntity> findTop10ByReviewNotNullOrderByModifiedDescInsertedAsc();
	
	public List<ReviewEntity> getByStopPointIdOrderByInsertedDesc(Long stopPointId);

	public ReviewEntity findByStopPointIdAndInsertedUserId(Long stopId, Long userId);

	public List<ReviewEntity> findByStopPointId(Long stopId);

	public List<ReviewEntity> findByInsertedUserId(Long userId);
	
}
