package info.campersites.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import info.campersites.entity.StopPointsEntity;
import info.campersites.entity.UserPreferitiEntity;
import info.campersites.entity.UserPreferitiEntityPK;
import info.campersites.repository.StopPointsRepository;
import info.campersites.repository.UserPreferitiRepository;

@Service
@Transactional(readOnly=true, propagation=Propagation.REQUIRED)
public class UserPreferitiServiceImpl implements UserPreferitiService {
	
	@Autowired
	UserPreferitiRepository userPreferitiRepository;
	@Autowired
	StopPointsRepository stopPointsRepository;


	@Override
	public Boolean isPreferito(Long stopId, Long userId) {
		return userPreferitiRepository.exists(new UserPreferitiEntityPK(stopId, userId));
	}

	@Override
	@Transactional(readOnly=false)
	public void togglePreferito(Long stopId, Long userId) {
		UserPreferitiEntityPK userPreferitiEntityPK = new UserPreferitiEntityPK(stopId, userId);
		StopPointsEntity stopPointEntity = stopPointsRepository.getOne(stopId);
		if (userPreferitiRepository.exists(userPreferitiEntityPK)) {
			userPreferitiRepository.delete(userPreferitiEntityPK);
			stopPointEntity.setTotPreferito(stopPointEntity.getTotPreferito()-1);
		} else {
			UserPreferitiEntity userPreferitiEntity = new UserPreferitiEntity();
			userPreferitiEntity.setStopId(stopId);
			userPreferitiEntity.setUserId(userId);
			userPreferitiRepository.save(userPreferitiEntity);
			stopPointEntity.setTotPreferito(stopPointEntity.getTotPreferito()+1);
		}
		stopPointsRepository.save(stopPointEntity);
	}

}
