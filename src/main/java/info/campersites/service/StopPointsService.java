package info.campersites.service;

import java.util.List;

import org.springframework.data.domain.Page;

import info.campersites.bo.StopPointBo;
import info.campersites.bo.StopPointMarkerBo;
import info.campersites.bo.StopPointMod;
import info.campersites.bo.StopPointNewestBo;
import info.campersites.bo.StopPointTopBo;

public interface StopPointsService {
	List<StopPointMarkerBo> getStopPointsBound(String bounds, boolean visibile);
	List<StopPointNewestBo> getStopPointsNewest();
	List<StopPointTopBo> getStopPointsTop();
	StopPointBo getStopPoint(Long stopId, Long userId);
	Page<StopPointMarkerBo> getByNation(String nation, Integer page);
	StopPointBo modify(StopPointMod stopPointMod, Long userId);
	List<StopPointMarkerBo> getPreferiti(Long userId);
}
