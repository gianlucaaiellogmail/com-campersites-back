package old.info.campersites.comparetor;

import old.info.campersites.bo.StopPointBo;

import java.util.Comparator;

public class StopPointDistanceComparetor implements Comparator<StopPointBo> {

	@Override
	public int compare(StopPointBo o1, StopPointBo o2) {
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
