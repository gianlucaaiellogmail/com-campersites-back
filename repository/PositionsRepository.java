package info.campersites.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import info.campersites.entity.PositionsEntity;

public interface PositionsRepository extends JpaRepository<PositionsEntity, String> {
	
	public List<PositionsEntity> findByLatitudeBetweenAndLongitudeBetween(Float minLatitude, Float maxLatitude, Float minLongitude, Float maxLongitude);
	
	public PositionsEntity findByPositionId(String positionId);
	
}
