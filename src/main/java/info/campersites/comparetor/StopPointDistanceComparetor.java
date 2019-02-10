package info.campersites.comparetor;

import java.util.Comparator;

import info.campersites.bo.StopPointMarkerBo;

public class StopPointDistanceComparetor implements Comparator<StopPointMarkerBo> {

	@Override
	public int compare(StopPointMarkerBo o1, StopPointMarkerBo o2) {
		Double distance1 = o1.getDistance();
		Double distance2 = o2.getDistance();
		if (distance1 == null && distance2 == null)	return 0;
		return compareTo(distance1, distance2);
	}
	
	private int compareTo(Double distance1, Double distance2) {
		if (distance1 > distance2) return 1;
		if (distance1 < distance2) return -1;
		return 0;
	}

}
