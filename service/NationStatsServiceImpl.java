package info.campersites.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import info.campersites.bo.NationStatsBo;
import info.campersites.entity.NationStatsEntity;
import info.campersites.repository.NationStatsRepository;

@Service
@Transactional(readOnly=true, propagation=Propagation.REQUIRED)
public class NationStatsServiceImpl implements NationStatsService {
	
	@Autowired
	NationStatsRepository nationStatsRepository;

	@Override
	@Cacheable(value="nationStatCache")
	public List<NationStatsBo> findAll() {
		List<NationStatsEntity> nationStatsEntity = nationStatsRepository.findAll();
    	return toBos(nationStatsEntity);
	}

	private List<NationStatsBo> toBos(List<NationStatsEntity> nationStatsEntity) {
		List<NationStatsBo> nationStats = new ArrayList<NationStatsBo>();
		for (NationStatsEntity nationStatEntity : nationStatsEntity) {
			nationStats.add(toBo(nationStatEntity));
		}
		return nationStats;
	}

	private NationStatsBo toBo(final NationStatsEntity nationStatEntity) {
		NationStatsBo nationStats = new NationStatsBo();
		BeanUtils.copyProperties(nationStatEntity, nationStats);
		return nationStats;
	}
	
}
