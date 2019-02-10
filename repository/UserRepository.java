package info.campersites.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import info.campersites.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
	
	public UserEntity findOne(Long userId);
	
	public List<UserEntity> findAll();

	public UserEntity findByEmailAndPassword(String email, String password);

	public UserEntity findByEmail(String email);

	public UserEntity findByNickname(String nickname);

	public UserEntity findByFbUserId(String fbUserId);

	public UserEntity findByActivation(String code);

	public UserEntity findByRestorePwd(String restoreCode);
	
}
