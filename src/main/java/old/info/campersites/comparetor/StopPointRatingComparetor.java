package old.info.campersites.comparetor;

import old.info.campersites.bo.StopPointBo;

import java.util.Comparator;

public class StopPointRatingComparetor implements Comparator<StopPointBo> {

	@Override
	public int compare(StopPointBo o1, StopPointBo o2) {
		// A rating nulli assegno un default di 2,5 per il confronto con gli altri
		Float rating1 = o1.getRating();
		Float rating2 = o2.getRating();
		if (rating1 == null && rating2 == null)	return 0;
		if (rating1 == null)
			return compareTo(new Float(2.5), rating2);
		if (rating2 == null)
			return compareTo(rating1, new Float(2.5));
		return compareTo(rating1, rating2);
	}
	
	private int compareTo(Float rating1, Float rating2) {
		if (rating1 < rating2) return 1;
		if (rating1 > rating2) return -1;
		return 0;
	}

}
