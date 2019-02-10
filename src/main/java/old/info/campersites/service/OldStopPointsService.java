package old.info.campersites.service;

import java.util.List;

import old.info.campersites.bo.FiltriBo;
import old.info.campersites.bo.NationStatsBo;
import old.info.campersites.bo.StopPointBo;
import old.info.campersites.bo.UserBo;

public interface OldStopPointsService {
	 
    List<StopPointBo> getStopPointsBound(String bounds, FiltriBo filtriBo);
    StopPointBo getStopPointDetail(Long stopPoint);
    StopPointBo insertOrUpdate(StopPointBo stopPoint);
    List<StopPointBo> getStopPointsPreferiti(UserBo user);
    List<StopPointBo> getStopPointsFromPoint(Float latitude, Float longitude, Double maxDistance);
    List<StopPointBo> getStopPointsByNation(String nation);
    List<NationStatsBo> getNationStats();
    
}
