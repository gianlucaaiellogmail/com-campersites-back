package info.campersites.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import info.campersites.entity.TripStepEntity;

public interface TripStepRepository extends JpaRepository<TripStepEntity, Long> {

	List<TripStepEntity> findByTripId(Long tripId);
	
}
