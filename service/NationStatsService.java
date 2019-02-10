package info.campersites.service;

import java.util.List;

import info.campersites.bo.NationStatsBo;

public interface NationStatsService {
	List<NationStatsBo> findAll();
}
