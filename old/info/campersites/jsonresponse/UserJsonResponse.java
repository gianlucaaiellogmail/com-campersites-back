package old.info.campersites.jsonresponse;

import old.info.campersites.bo.UserBo;

import java.util.ArrayList;
import java.util.List;

public class UserJsonResponse {

    private String esito;
    private List<String> errors = new ArrayList<String>();
    private UserBo user;

    public String getEsito() {
		return esito;
	}
	public void setEsito(String esito) {
		this.esito = esito;
	}
	public UserBo getUser() {
		return user;
	}
	public void setUser(UserBo user) {
		this.user = user;
	}
	public List<String> getErrors() {
		return errors;
	}
	public void setErrors(List<String> errors) {
		this.errors = errors;
	}

}
