package info.campersites.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import info.campersites.entity.ReviewsInfoEntity;

public interface ReviewsInfoRepository extends JpaRepository<ReviewsInfoEntity, Long> {
	
	public List<ReviewsInfoEntity> findByStopId(Long stopId);
	
}
