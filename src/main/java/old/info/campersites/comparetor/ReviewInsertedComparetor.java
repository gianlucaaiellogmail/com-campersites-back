package old.info.campersites.comparetor;

import old.info.campersites.bo.ReviewBo;

import java.util.Comparator;
import java.util.Date;

public class ReviewInsertedComparetor implements Comparator<ReviewBo> {
	
	public static final String ASC = "ASC";
	public static final String DESC = "DESC";
	private String order;

	public ReviewInsertedComparetor(String order) {
		this.order = order;
	}
	
	@Override
	public int compare(ReviewBo o1, ReviewBo o2) {
		Date inserted1 = o1.getInserted();
		Date inserted2 = o2.getInserted();
		if (inserted1 == null && inserted2 == null)	return 0;
		if (DESC.equals(order))
			return compareTo(inserted2, inserted1);
		return compareTo(inserted1, inserted2);
	}
	
	private int compareTo(Date inserted1, Date inserted2) {
		if (inserted1.before(inserted2)) return -1;
		if (inserted1.after(inserted2)) return 1;
		return 0;
	}

}
