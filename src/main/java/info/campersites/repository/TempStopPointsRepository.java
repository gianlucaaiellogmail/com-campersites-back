package info.campersites.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import info.campersites.entity.TempStopPointsEntity;

public interface TempStopPointsRepository extends JpaRepository<TempStopPointsEntity, Long> {
	
	public TempStopPointsEntity findOne(Long stopId);
	
}
