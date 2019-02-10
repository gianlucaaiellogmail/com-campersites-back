package info.campersites.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import info.campersites.bo.UserBo;
import info.campersites.bo.UserTopBo;
import info.campersites.entity.UserEntity;
import info.campersites.entity.UserStatsEntity;
import info.campersites.repository.StopPointsRepository;
import info.campersites.repository.UserPreferitiRepository;
import info.campersites.repository.UserRepository;
import info.campersites.repository.UserStatsRepository;

@Service
@Transactional(readOnly=true, propagation=Propagation.REQUIRED)
public class UserServiceImpl implements UserService {
	
	@Autowired
	UserStatsRepository userStatsRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	UserPreferitiRepository userPreferitiRepository;
	@Autowired
	StopPointsRepository stopPointsRepository;
	@Autowired
	EmailService emailService;


	@Override
	@Cacheable(value="usersTopCache")
	public List<UserTopBo> getUsersTop() {
		List<UserStatsEntity> usersStatsEntity = userStatsRepository.findTop10ByOrderByScoreDesc();
		return toTopBos(usersStatsEntity);
	}

	@Override
	@Transactional(readOnly = false)
	public UserBo login(final String email, final String password) {
    	UserEntity userEntity = userRepository.findByEmailAndPassword(email, UserServiceImpl.md5(password));
    	UserBo userBo = toBo(userEntity);
   		userEntity.setLastLogin(Calendar.getInstance().getTime());
   		userRepository.save(userEntity);
        return userBo;
    }

	@Override
	@Transactional(readOnly = false)
    public UserBo createUser(final String email, final String password, final String nickname, final Locale userLocale) {
		UserEntity userEntity = new UserEntity();
		userEntity.setEmail(email);
		userEntity.setPassword(UserServiceImpl.md5(password));
		userEntity.setNickname(nickname);
		userEntity.setLocale(userLocale.getLanguage());
		userEntity = userRepository.save(userEntity);
        return toBo(userEntity);
	}
	
	@Override
    public UserBo findByEmail(final String email) {
    	UserBo userBo = null;
    	UserEntity userEntity = userRepository.findByEmail(email);
    	if (userEntity != null) {
    		userBo = toBo(userEntity);
    	}
        return userBo;
    }

	@Override
	public UserBo findByNickname(final String nickname) {
    	UserBo userBo = null;
    	UserEntity userEntity = userRepository.findByNickname(nickname);
    	if (userEntity != null) {
    		userBo = toBo(userEntity);
    	}
        return userBo;
    }

	@Override
	public UserBo findByFbUserId(final String fbUserId) {
		UserBo userBo = null;
		UserEntity userEntity = userRepository.findByFbUserId(fbUserId);
    	if (userEntity != null) {
    		userBo = toBo(userEntity);
    	}
        return userBo;
	}

	@Override
	@Transactional(readOnly = false)
	public UserBo loginFbUser(final String uid, final String nickname, final String photoPath, final String locale, final String accessToken) {
    	UserBo userBo = null;
    	UserEntity userEntity = userRepository.findByFbUserId(uid);
    	if (userEntity != null) {
    		userEntity.setLastLogin(Calendar.getInstance().getTime());
    		userEntity.setNickname(nickname);
    		userEntity.setPhotoPath(photoPath);
    		userEntity.setLocale(locale);
    		userEntity.setFbToken(accessToken);
    		userRepository.save(userEntity);
    		userBo = toBo(userEntity);
    	}
        return userBo;
	}

	@Override
	@Transactional(readOnly = false)
	public UserBo createFbUser(final String uid, final String nickname, final String photoPath, final String locale, final String accessToken) {
		UserEntity userEntity = new UserEntity();
		userEntity.setLastLogin(Calendar.getInstance().getTime());
		userEntity.setNickname(nickname);
		userEntity.setPhotoPath(photoPath);
		userEntity.setLocale(locale);
		userEntity.setFbToken(accessToken);
		userRepository.save(userEntity);
        return toBo(userEntity);
	}
	
	@Override
	@Transactional(readOnly = false)
	public UserBo activateUser(final String code) {
		UserEntity userEntity = userRepository.findByActivation(code);
		if (userEntity != null) {
			userEntity.setActivation(null);
			userEntity.setLastChange(new Date());
			userRepository.save(userEntity);
		}
		return toBo(userEntity);
	}

	@Override
	@Transactional(readOnly = false)
	public void sendRestorePwd(final String email) {
		UserEntity userEntity = userRepository.findByEmail(email);
		userEntity.setRestorePwd(UUID.randomUUID().toString());
		emailService.sendRestorePwd(userEntity.getNickname(), email, userEntity.getRestorePwd(), new Locale(userEntity.getLocale()));
		userRepository.save(userEntity);
	}

	@Override
	@Transactional(readOnly = false)
	public void restorePwd(final String restoreCode, final String password) {
		UserEntity userEntity = userRepository.findByRestorePwd(restoreCode);
		if (userEntity != null) {
			userEntity.setRestorePwd(null);
			userEntity.setPassword(md5(password));
			userEntity.setLastChange(Calendar.getInstance().getTime());
			userRepository.save(userEntity);
		}
	}

	private List<UserTopBo> toTopBos(List<UserStatsEntity> usersStatsEntity) {
		List<UserTopBo> userTop = new ArrayList<UserTopBo>();
		for (UserStatsEntity userStatsEntity : usersStatsEntity) {
			userTop.add(toTopBo(userStatsEntity));
		}
		return userTop;
	}

	private UserTopBo toTopBo(final UserStatsEntity userStatsEntity) {
		UserTopBo userTop = new UserTopBo();
		BeanUtils.copyProperties(userStatsEntity, userTop);
		return userTop;
	}
	
	private UserBo toBo(final UserEntity userEntity) {
		UserBo user = new UserBo();
		BeanUtils.copyProperties(userEntity, user);
		return user;
	}
	
	private static String md5(final String input) {
		String res = "";
		try {
			MessageDigest algorithm = MessageDigest.getInstance("MD5");
			algorithm.reset();
			algorithm.update(input.getBytes());
			byte[] md5 = algorithm.digest();
			String tmp = "";
			for (int i = 0; i < md5.length; i++) {
				tmp = (Integer.toHexString(0xFF & md5[i]));
				if (tmp.length() == 1) {
					res += "0" + tmp;
				} else {
					res += tmp;
				}
			}
		} catch (NoSuchAlgorithmException ex) {}
		return res;
	}

}
