package old.info.campersites.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import info.campersites.entity.StopPointsEntity;
import info.campersites.entity.UserEntity;
import info.campersites.entity.UserPreferitiEntity;
import info.campersites.entity.UserPreferitiEntityPK;
import info.campersites.repository.StopPointsRepository;
import info.campersites.repository.UserPreferitiRepository;
import info.campersites.repository.UserRepository;
import info.campersites.service.EmailService;
import old.info.campersites.bo.UserBo;
import old.info.campersites.service.OldUserService;
import old.info.campersites.tools.Tools;

@Service
@Transactional(readOnly = true)
public class OldUserServiceImpl implements OldUserService {
	 
    @Autowired
	private UserRepository userRepository;
    @Autowired
	private StopPointsRepository stopPointsRepository;
    @Autowired
	private UserPreferitiRepository userPreferitiRepository;
    @Autowired
    private EmailService emailService;
	
	@Override
    public UserBo getUserByEmail(String email) {
    	UserBo userBo = null;
    	UserEntity userEntity = userRepository.findByEmail(email);
    	if (userEntity != null) {
    		userBo = mappingEntityToBo(userEntity);
    	}
        return userBo;
    }

	@Override
	public UserBo getUserByNickname(String nickname) {
    	UserBo userBo = null;
    	UserEntity userEntity = userRepository.findByNickname(nickname);
    	if (userEntity != null) {
    		userBo = mappingEntityToBo(userEntity);
    	}
        return userBo;
    }

	@Override
	@Transactional(readOnly = false)
	public UserBo loginUser(String email, String password, String rememberMe) {
    	UserBo userBo = null;
    	UserEntity userEntity = userRepository.findByEmailAndPassword(email, Tools.md5(password));
    	if (userEntity != null && userEntity.getActivation() == null) {
    		userBo = mappingEntityToBo(userEntity);
    		if (rememberMe != null) {
    			String remember = UUID.randomUUID().toString();
    			userBo.setRemember(remember);
    			userEntity.setRemember(remember);
    		}
    		userEntity.setLastLogin(Calendar.getInstance().getTime());
    		userRepository.save(userEntity);
    	}
        return userBo;
    }

	@Override
	@Transactional(readOnly = false)
	public UserBo loginFbUser(Long userId, String nickname, String photoPath, String locale, String fbToken) {
    	UserBo userBo = null;
    	UserEntity userEntity = userRepository.getOne(userId);
    	if (userEntity != null) {
    		userEntity.setLastLogin(Calendar.getInstance().getTime());
    		userEntity.setNickname(nickname);
    		userEntity.setPhotoPath(photoPath);
    		userEntity.setLocale(locale);
    		userEntity.setFbToken(fbToken);
    		userRepository.save(userEntity);
    		userBo = mappingEntityToBo(userEntity);
    	}
        return userBo;
    }

	@Override
	public UserBo getUserById(Long userId) {
    	UserBo userBo = null;
    	UserEntity userEntity = userRepository.getOne(userId);
    	if (userEntity != null) {
    		userBo = mappingEntityToBo(userEntity);
    	}
        return userBo;
    }

	@Override
	public UserBo getUserByFbId(String fbUserId) {
    	UserBo userBo = null;
    	UserEntity userEntity = userRepository.findByFbUserId(fbUserId);
    	if (userEntity != null) {
    		userBo = mappingEntityToBo(userEntity);
    	}
        return userBo;
    }

	@Override
	@Transactional(readOnly = false)
    public UserBo createUser(String email, String password, String nickname, Locale userLocale) {
		UserEntity userEntity = new UserEntity();
		userEntity.setEmail(email);
		userEntity.setPassword(Tools.md5(password));
		userEntity.setNickname(nickname);
		userEntity.setLocale(userLocale.getLanguage());
		userEntity = userRepository.save(userEntity);
		emailService.sendActivation(nickname, email, userEntity.getActivation(), userLocale);
        return mappingEntityToBo(userEntity); 
	}
	
	@Override
	@Transactional(readOnly = false)
    public UserBo createFbUser(String fbUserId, String nickname, String photoPath, String locale, String fbToken) {
		UserEntity userEntity = new UserEntity();
		userEntity.setLastLogin(Calendar.getInstance().getTime());
		userEntity.setNickname(nickname);
		userEntity.setPhotoPath(photoPath);
		userEntity.setLocale(locale);
		userEntity.setFbToken(fbToken);
		userEntity = userRepository.save(userEntity);
        return mappingEntityToBo(userEntity);
	}
	
	@Override
	public boolean isPreferito(Long stopId, Long userId) {
		UserPreferitiEntityPK userPreferitiEntityPK = new UserPreferitiEntityPK(stopId, userId);
		UserPreferitiEntity userPreferitiEntity = userPreferitiRepository.getOne(userPreferitiEntityPK);
		if (userPreferitiEntity != null) {
			return true;
		}
		return false;
	}

	@Override
	@Transactional(readOnly = false)
	public boolean addStopPointToPreferiti(Long stopId, Long userId) {
		UserPreferitiEntity userPreferitiEntity = new UserPreferitiEntity();
		userPreferitiEntity.setStopId(stopId);
		userPreferitiEntity.setUserId(userId);
		userPreferitiRepository.save(userPreferitiEntity);
		StopPointsEntity stopPointEntity = stopPointsRepository.getOne(stopId);
		stopPointEntity.setTotPreferito(stopPointEntity.getTotPreferito()+1);
		stopPointsRepository.save(stopPointEntity);
		return true;
	}

	@Override
	@Transactional(readOnly = false)
	public boolean removeStopPointFromPreferiti(Long stopId, Long userId) {
		UserPreferitiEntityPK userPreferitiEntityPK = new UserPreferitiEntityPK(stopId, userId);
		userPreferitiRepository.delete(userPreferitiEntityPK);
		StopPointsEntity stopPointEntity = stopPointsRepository.getOne(stopId);
		stopPointEntity.setTotPreferito(stopPointEntity.getTotPreferito()-1);
		stopPointsRepository.save(stopPointEntity);
		return true;
	}

	@Override
	@Transactional(readOnly = false)
	public boolean addStopPointToPreferiti(Long stopId, Long userId, Date modified) {
		UserPreferitiEntityPK userPreferitiEntityPK = new UserPreferitiEntityPK(stopId, userId);
		UserPreferitiEntity userPreferitiEntity = userPreferitiRepository.getOne(userPreferitiEntityPK);
		if (userPreferitiEntity == null) {
			addStopPointToPreferiti(stopId, userId);
		} else if (modified.after(userPreferitiEntity.getModified())) {
			userPreferitiRepository.save(userPreferitiEntity);
		}
		return true;
	}

	@Override
	@Transactional(readOnly = false)
	public boolean removeStopPointFromPreferiti(Long stopId, Long userId, Date modified) {
		UserPreferitiEntityPK userPreferitiEntityPK = new UserPreferitiEntityPK(stopId, userId);
		UserPreferitiEntity userPreferitiEntity = userPreferitiRepository.getOne(userPreferitiEntityPK);
		if (userPreferitiEntity != null && modified.after(userPreferitiEntity.getModified())) {
			removeStopPointFromPreferiti(stopId, userId);
		}
		return true;
	}

	private UserBo mappingEntityToBo(UserEntity userEntity) {
		if (userEntity != null) {
			UserBo userBo = new UserBo(userEntity.getUserId());
			userBo.setEmail(userEntity.getEmail());
			userBo.setNickname(userEntity.getNickname());
			userBo.setLocale(new Locale(userEntity.getLocale()));
			userBo.setTotReviews(userEntity.getTotReviews());
			userBo.setTotPreferiti(userEntity.getTotPreferiti());
			userBo.setPhotoPath(userEntity.getPhotoPath());
			userBo.setRemember(userEntity.getRemember());
			userBo.setFbUserId(userEntity.getFbUserId());
			userBo.setFbToken(userEntity.getFbToken());
			userBo.setNewsletter(userEntity.getNewsletter());
			return userBo;
		} else {
			return null;
		}
	}
	
}
