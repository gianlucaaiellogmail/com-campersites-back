package info.campersites.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import info.campersites.entity.ImageEntity;

public interface ImageRepository extends JpaRepository<ImageEntity, Long> {
	
	public List<ImageEntity> getByStopIdOrderByDateCreatedDesc(Long stopId);
	
}
