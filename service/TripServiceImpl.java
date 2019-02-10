package info.campersites.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import info.campersites.bo.TripBo;
import info.campersites.entity.TripEntity;
import info.campersites.entity.TripStepEntity;
import info.campersites.repository.TripRepository;
import info.campersites.repository.TripStepRepository;

@Service
@Transactional(readOnly=true, propagation=Propagation.REQUIRED)
public class TripServiceImpl implements TripService {
	 
    @Autowired
	private TripRepository tripRepository;
    @Autowired
	private TripStepRepository tripStepRepository;
	
	@Override
	public List<TripBo> getTripsByUser(Long userId) {
		List<TripBo> tripsBo = new ArrayList<TripBo>();
    	List<TripEntity> tripsEntity = tripRepository.findByUserId(userId);
    	for (TripEntity tripEntity : tripsEntity) {
    		List<TripStepEntity> tripStepsEntity = tripStepRepository.findByTripId(tripEntity.getTripId());
    		tripsBo.add(toBo(tripEntity, tripStepsEntity));
    	}
        return tripsBo;
	}

	@Override
	@Transactional(readOnly = false)
	public TripBo save(TripBo tripBo, Long userId) {
		TripEntity tripEntity = tripRepository.save(toEntity(tripBo, userId));
		List<TripStepEntity> tripStepsEntity = new ArrayList<TripStepEntity>();
		for (String tappa : tripBo.getTappe()) {
			TripStepEntity tripStepEntity = new TripStepEntity();
			tripStepEntity.setName(tappa);
			tripStepEntity.setTripId(tripEntity.getTripId());
			tripStepEntity = tripStepRepository.save(tripStepEntity);
			tripStepsEntity.add(tripStepEntity);
		}
		return toBo(tripEntity, tripStepsEntity);
	}
	
	private TripBo toBo(TripEntity tripEntity, List<TripStepEntity> tripStepsEntity) {
		TripBo tripBo = new TripBo();
		BeanUtils.copyProperties(tripEntity, tripBo);
		List<String> tappe = new ArrayList<String>();
		for (TripStepEntity tripStepEntity : tripStepsEntity) {
			tappe.add(tripStepEntity.getName());
		}
		tripBo.setTappe(tappe);
		return tripBo;
	}
	
	private TripEntity toEntity(TripBo tripBo, Long userId) {
		TripEntity tripEntity = new TripEntity();
		BeanUtils.copyProperties(tripBo, tripEntity);
		tripEntity.setUserId(userId);
		return tripEntity;
	}

}
