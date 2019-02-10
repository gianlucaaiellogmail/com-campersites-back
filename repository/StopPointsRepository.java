package info.campersites.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import info.campersites.entity.StopPointsEntity;

public interface StopPointsRepository extends JpaRepository<StopPointsEntity, Long> {
	
	public StopPointsEntity findOne(Long stopId);
	
	public List<StopPointsEntity> findByLatitudeBetweenAndLongitudeBetween(Float minLatitude, Float maxLatitude, Float minLongitude, Float maxLongitude);
	
	public List<StopPointsEntity> findByLatitudeBetweenAndLongitudeBetweenAndVisibile(Float minLatitude, Float maxLatitude, Float minLongitude, Float maxLongitude, Integer visibile);
	
	public List<StopPointsEntity> findTop10ByOrderByModifiedDescInsertedAsc();
	
	public List<StopPointsEntity> findTop10ByOrderByRatingDescModifiedDesc();
	
	public Long countByModifiedUserId(Long userId);

	public Page<StopPointsEntity> findByNation(String nation, Pageable pageable);

	public List<StopPointsEntity> findByStopIdIn(List<Long> stopIds);

	public List<StopPointsEntity> findByNation(String nation);

	public List<StopPointsEntity> findByNationAndVisibile(String nation, Integer visibile);

}
