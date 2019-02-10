package info.campersites.service;

import java.util.List;
import java.util.Locale;

import info.campersites.bo.UserBo;
import info.campersites.bo.UserTopBo;

public interface UserService {
	
	List<UserTopBo> getUsersTop();
	UserBo login(String email, String password);
	UserBo createUser(String email, String password, String nickname, Locale userLocale);
	UserBo findByEmail(String email);
	UserBo findByNickname(String nickname);
	UserBo findByFbUserId(String fbUserId);
	UserBo loginFbUser(String uid, String nickname, String photoPath, String locale, String accessToken);
	UserBo createFbUser(String uid, String nickname, String photoPath, String locale, String accessToken);
	UserBo activateUser(String code);
	void sendRestorePwd(String email);
	void restorePwd(String restoreCode, String password);
	
}
