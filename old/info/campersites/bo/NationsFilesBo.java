package old.info.campersites.bo;

import java.io.Serializable;
import java.util.Date;


public class NationsFilesBo implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String nation;
	private Double filesize;
	private String filename;
	private Date filedate;

	public NationsFilesBo() {
	}

	@Override
	public String toString() {
		return "NationFiles [nation=" + nation + "]";
	}

	public String getNation() {
		return nation;
	}

	public void setNation(String nation) {
		this.nation = nation;
	}

	public Double getFilesize() {
		return filesize;
	}

	public void setFilesize(Double filesize) {
		this.filesize = filesize;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public Date getFiledate() {
		return filedate;
	}

	public void setFiledate(Date filedate) {
		this.filedate = filedate;
	}

}
