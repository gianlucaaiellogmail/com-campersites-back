package info.campersites.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import info.campersites.entity.UserPoiEntity;

public interface UserPoiRepository extends JpaRepository<UserPoiEntity, Long> {

	List<UserPoiEntity> findByLatitudeBetweenAndLongitudeBetweenAndUserId(Float minLatitude, Float maxLatitude, Float minLongitude, Float maxLongitude, Long userId);
	
}
