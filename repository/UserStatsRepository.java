package info.campersites.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import info.campersites.entity.UserStatsEntity;

public interface UserStatsRepository extends JpaRepository<UserStatsEntity, Long> {
	
	public List<UserStatsEntity> findTop10ByOrderByScoreDesc();
	
}
