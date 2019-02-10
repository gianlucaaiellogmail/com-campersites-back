package info.campersites.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import info.campersites.entity.UserPreferitiEntity;
import info.campersites.entity.UserPreferitiEntityPK;

public interface UserPreferitiRepository extends JpaRepository<UserPreferitiEntity, UserPreferitiEntityPK> {

	List<UserPreferitiEntity> findByUserId(Long userId);
	
}
