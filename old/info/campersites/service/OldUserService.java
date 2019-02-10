package old.info.campersites.service;

import old.info.campersites.bo.UserBo;

import java.util.Date;
import java.util.Locale;

public interface OldUserService {
	 
    UserBo getUserByEmail(String email);
    UserBo getUserByNickname(String nickname);
    UserBo loginUser(String email, String password, String rememberMe);
    UserBo loginFbUser(Long userId, String nickname, String photoPath, String locale, String fbToken);
    UserBo getUserById(Long userId);
    UserBo getUserByFbId(String fbUserId);
    UserBo createUser(String email, String password, String nickname, Locale userLocale);
    UserBo createFbUser(String fbUserId, String nickname, String photoPath, String locale, String fbToken);
    boolean isPreferito(Long stopId, Long userId);
    boolean addStopPointToPreferiti(Long stopId, Long userId);
    boolean removeStopPointFromPreferiti(Long stopId, Long userId);
    boolean addStopPointToPreferiti(Long stopId, Long userId, Date modified);
    boolean removeStopPointFromPreferiti(Long stopId, Long userId, Date modified);

}
