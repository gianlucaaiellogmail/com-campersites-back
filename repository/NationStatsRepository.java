package info.campersites.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import info.campersites.entity.NationStatsEntity;

public interface NationStatsRepository extends JpaRepository<NationStatsEntity, Long> {
	
	public List<NationStatsEntity> findAll();
	
}
