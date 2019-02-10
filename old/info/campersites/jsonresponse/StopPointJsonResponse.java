package old.info.campersites.jsonresponse;

import old.info.campersites.bo.StopPointBo;

import java.util.ArrayList;
import java.util.List;

public class StopPointJsonResponse {

    private String esito;
    private List<String> errors = new ArrayList<String>();
    private StopPointBo stopPoint;
    private boolean isPreferito;
    private Float howRated;
    private boolean hasPhoto;

    public String getEsito() {
		return esito;
	}
	public void setEsito(String esito) {
		this.esito = esito;
	}
	public StopPointBo getStopPoint() {
		return stopPoint;
	}
	public void setStopPoint(StopPointBo stopPoint) {
		this.stopPoint = stopPoint;
	}
	public List<String> getErrors() {
		return errors;
	}
	public void setErrors(List<String> errors) {
		this.errors = errors;
	}
	public boolean isPreferito() {
		return isPreferito;
	}
	public void setPreferito(boolean isPreferito) {
		this.isPreferito = isPreferito;
	}
	public Float getHowRated() {
		return howRated;
	}
	public void setHowRated(Float howRated) {
		this.howRated = howRated;
	}
	public boolean isHasPhoto() {
		return hasPhoto;
	}
	public void setHasPhoto(boolean hasPhoto) {
		this.hasPhoto = hasPhoto;
	}

}
