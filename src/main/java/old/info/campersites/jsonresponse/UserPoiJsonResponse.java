package old.info.campersites.jsonresponse;

import info.campersites.bo.UserPoiBo;

import java.util.ArrayList;
import java.util.List;

public class UserPoiJsonResponse {

    private String esito;
    private List<String> errors = new ArrayList<String>();
    private UserPoiBo userPoi;

    public String getEsito() {
		return esito;
	}
	public void setEsito(String esito) {
		this.esito = esito;
	}
	public List<String> getErrors() {
		return errors;
	}
	public void setErrors(List<String> errors) {
		this.errors = errors;
	}
	public UserPoiBo getUserPoi() {
		return userPoi;
	}
	public void setUserPoi(UserPoiBo userPoi) {
		this.userPoi = userPoi;
	}

}
