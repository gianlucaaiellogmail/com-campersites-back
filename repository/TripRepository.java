package info.campersites.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import info.campersites.entity.TripEntity;

public interface TripRepository extends JpaRepository<TripEntity, Long> {

	List<TripEntity> findByUserId(Long userId);
	
}
