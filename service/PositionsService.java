package info.campersites.service;

import java.util.List;

import info.campersites.bo.PositionMarkerBo;
import info.campersites.bo.PositionMod;

public interface PositionsService {
	List<PositionMarkerBo> getPositionsBound(String bounds);
	PositionMod modify(PositionMod positionMod);
}
